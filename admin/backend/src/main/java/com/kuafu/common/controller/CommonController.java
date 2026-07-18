package com.kuafu.common.controller;

import com.google.common.collect.Maps;
import com.kuafu.common.config.AppConfig;
import com.kuafu.common.constant.Constants;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.file.FileUploadUtils;
import com.kuafu.common.file.FileUtils;
import com.kuafu.common.storage.StorageService;
import com.kuafu.common.util.ServletUtils;
import com.kuafu.common.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class CommonController {

    private static final String FILE_DELIMETER = ",";

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
     * 二维码专用上传
     * 保存到 upload/QR/ 目录，以"教学楼_教室号.png"命名
     * 如果该教室已有二维码，先删除旧的再保存新的
     */
    @PostMapping("/uploadQrcode")
    public BaseResponse uploadQrcode(MultipartFile file, String buildingName, String roomNumber) throws Exception {
        try {
            if (buildingName == null || roomNumber == null) {
                return ResultUtils.error("教学楼和教室编号不能为空");
            }

            // 二维码专用目录：uploadPath/upload/QR/
            String qrcodeDir = AppConfig.getUploadPath() + "/QR";
            File dir = new File(qrcodeDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 文件名：教学楼_教室号.png
            String ext = FileUploadUtils.getExtension(file);
            String fileName = buildingName + "_" + roomNumber + "." + ext;
            File destFile = new File(dir, fileName);

            // 如果已存在，先删除旧的
            if (destFile.exists()) {
                destFile.delete();
            }

            // 保存文件
            file.transferTo(destFile);

            // 返回URL路径
            String url = Constants.RESOURCE_PREFIX + "/upload/QR/" + fileName;
            Map<String, String> data = Maps.newHashMap();
            data.put("url", url);
            data.put("fileName", fileName);
            return ResultUtils.success(data);
        } catch (Exception e) {
            return ResultUtils.error(e.getMessage());
        }
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

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
