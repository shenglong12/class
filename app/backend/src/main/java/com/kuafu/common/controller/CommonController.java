package com.kuafu.common.controller;

import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import com.kuafu.common.config.AppConfig;
import com.kuafu.common.config.MessageConfig;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.file.FileUploadUtils;
import com.kuafu.common.file.FileUtils;
import com.kuafu.common.storage.StorageService;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.ServletUtils;
import com.kuafu.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

@RestController
@RequestMapping("/common")
public class CommonController {

    private static final String FILE_DELIMETER = ",";


    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(
            "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpeg", "3gp");

    private static final Map<String, String> agentMap = Maps.newHashMap();

    static {
        agentMap.put("192.168.0.116", "2");
        agentMap.put("192.168.0.68", "63");
        agentMap.put("192.168.0.223", "64");
        agentMap.put("192.168.0.7", "65");
        agentMap.put("192.168.0.17", "66");
        agentMap.put("192.168.0.14", "67");
        agentMap.put("192.168.0.162", "68");
        agentMap.put("192.168.0.242", "69");
        agentMap.put("192.168.0.29", "70");
        agentMap.put("192.168.0.54", "71");
        agentMap.put("192.168.0.152", "72");
        agentMap.put("192.168.0.61", "73");
        agentMap.put("192.168.0.4", "74");
        agentMap.put("192.168.0.150", "75");
        agentMap.put("192.168.0.230", "76");
        agentMap.put("192.168.0.83", "77");
        agentMap.put("192.168.0.160", "78");
    }

    @Resource
    private MessageConfig messageConfig;

    @Value("${server.port:6789}")
    private Integer serverPort;

    @Resource
    private StorageService storageService;

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public BaseResponse uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String fileName = storageService.upload(file);
            if (messageConfig.isEnable()) {
                sendMessage(fileName, messageConfig);
            }
            Map<String, String> data = Maps.newHashMap();
            data.put("url", fileName);
            data.put("fileName", fileName);
            data.put("newFileName", FileUtils.getName(fileName));
            data.put("originalFilename", file.getOriginalFilename());
            return ResultUtils.success(data);
        } catch (Exception e) {
            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 发送消息
     *
     * @param fileName
     * @param messageConfig
     */
    private void sendMessage(String fileName, MessageConfig messageConfig) {
        final String appId = messageConfig.getAppId();
        final String notifyUrl = messageConfig.getNotifyUrl();

        // 获取文件扩展名
        String extension = getFileExtension(fileName);
        // 判断扩展名是否在视频格式列表中
        if (VIDEO_EXTENSIONS.contains(extension.toLowerCase())) {
//           如果是视频发送消息
            final HashMap<String, Object> body = new HashMap<>();
            body.put("msg_type", "text");
            final HashMap<String, Object> contentMap = new HashMap<>();
            String ip = getLocalIp();
            if (agentMap.containsKey(ip)) {
                String agentId = agentMap.get(ip);
                String agentUrl = "/backend" + agentId + serverPort;
                contentMap.put("text", "appId:" + appId + ",上传了视频,url 路径为" + getUrl() + agentUrl + fileName);
            } else {
                contentMap.put("text", "appId:" + appId + ",上传了视频,url 路径为" + getUrl() + fileName);
            }
            body.put("content", contentMap);
            HttpUtil.post(notifyUrl, JSON.toJSONString(body));
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // 没有扩展名或扩展名为空
        }
        return fileName.substring(lastDotIndex + 1);
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public BaseResponse uploadFiles(List<MultipartFile> files) throws Exception {
        try {
            // 上传文件路径
            String filePath = AppConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            Map<String, String> data = Maps.newHashMap();
            data.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            data.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            data.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            data.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ResultUtils.success(data);
        } catch (Exception e) {
            return ResultUtils.error(e.getMessage());
        }
    }

    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        final String backendUrl = request.getHeader("BackendAddress");
//        if (StringUtils.isNotEmpty(backendUrl)) {
//            return backendUrl;
////            return getDomain(request).replace(ServletUtils.getRequest().getContextPath(),"") + "/" + processBackedUrl(backendUrl);
//        }
        if (StringUtils.isEmpty(backendUrl) || StringUtils.equalsIgnoreCase(backendUrl, "/")) {
            return getDomain(request);
        }
        return getDomain(request) + backendUrl;
    }


    /**
     * 截取真实的路径 https://preview.kuafuai.net/backend98788/common/upload
     *
     * @param backendUrl
     * @return
     */
    private String processBackedUrl(String backendUrl) {
        String contextPath = ServletUtils.getRequest().getServletContext().getContextPath();
        StringBuffer path = new StringBuffer();
        if (backendUrl.startsWith("http://")) {
            backendUrl = backendUrl.replace("http://", "");
        }
        if (backendUrl.startsWith("https://")) {
            backendUrl = backendUrl.replace("https://", "");
        }


        if (backendUrl.endsWith("/")) {
            backendUrl = backendUrl.substring(0, backendUrl.length() - 1);
        }

//        if (backendUrl.endsWith(contextPath)) {
//            backendUrl = backendUrl.replace(contextPath, "");
//        }

        if (backendUrl.contains("/")) {
            final String[] split = backendUrl.split("/");
            if (split.length >= 2) {
                for (int i = 1; i < split.length; i++) {
                    path.append(split[i]).append("/");
                }
            }
            path.delete(path.length() - 1, path.length());
        }


        return path.toString();


    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    public String getLocalIp() {
        try {
            List<String> ipList = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // 跳过回环接口和未启用的接口
                if (iface.isLoopback() || !iface.isUp()) continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLinkLocalAddress()) {
                        ipList.add(addr.getHostAddress());
                    }
                }
            }
            return ipList.isEmpty() ? "" : ipList.get(0); // 返回第一个非回环 IP
        } catch (SocketException e) {
            return "";
        }
    }
}
