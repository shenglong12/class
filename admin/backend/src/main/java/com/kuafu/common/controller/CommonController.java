package com.kuafu.common.controller;

import com.google.common.collect.Maps;
import com.kuafu.common.config.AppConfig;
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
