package com.kuafu.orc.service;

import com.kuafu.common.config.AppConfig;
import com.kuafu.common.constant.Constants;
import com.kuafu.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service("LocalOcrService")
@Slf4j
public class LocalOcrServiceImpl implements OcrService {

    @Value("${ocr.tessdata}")
    private String tessDataPath;

    private final Tesseract tesseract = new Tesseract();

    @PostConstruct
    public void init() {
        try {
            tesseract.setLanguage("chi_sim");

            //判断配置的目录是否存在
            boolean flag = Files.exists(Paths.get(tessDataPath));

            String classPath = System.getProperty("java.class.path");

            if (classPath.contains(".jar") && !classPath.contains(":")) {
                //通过jar的方式启动
                log.info("========= app to startup by java -jar");
                if (!flag) {
                    InputStream tessdataStream = LocalOcrServiceImpl.class.getClassLoader().getResourceAsStream("tessdata/chi_sim.traineddata");
                    Files.createDirectories(Paths.get(tessDataPath));

                    File trainedDataFile = new File(tessDataPath, "chi_sim.traineddata");
                    FileOutputStream fos = new FileOutputStream(trainedDataFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = tessdataStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.flush();
                    fos.close();

                    log.info("===============save ocr tessdata....");
                }
                tesseract.setDatapath(tessDataPath);

            } else {
                log.info("========= app to startup by idea....");
                tesseract.setDatapath("src/main/resources/tessdata");
            }
        } catch (Exception e) {
            log.info("local ocr init fail=========", e);
        }
    }

    @Override
    public String ocr(String fileName) {
        try {
            File imageFile;
            if (StringUtils.startsWith(fileName, Constants.RESOURCE_PREFIX)) {
                // 本地资源
                String filePath = AppConfig.getProfile() + StringUtils.replace(fileName, Constants.RESOURCE_PREFIX, "");
                imageFile = new File(filePath);

            } else if (StringUtils.startsWithIgnoreCase(fileName, "http://")
                    || StringUtils.startsWithIgnoreCase(fileName, "https://")) {
                imageFile = downloadImage(fileName);
            } else {
                imageFile = new File(fileName);
            }

            if (!imageFile.exists()) {
                log.error("ocr fail file not exists");
                return "";
            }

            return tesseract.doOCR(imageFile);
        } catch (TesseractException | IOException e) {
            log.error("ocr fail {}", e.getMessage());
            return "";
        }
    }

    private static final OkHttpClient client = new OkHttpClient();

    public static File downloadImage(String imageUrl) throws IOException {
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image: HTTP " + response.code());
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Empty response body");
            }

            String contentType = body.contentType() != null ? body.contentType().toString() : "";
            String extension = guessExtension(contentType, imageUrl);

            File tempFile = File.createTempFile("ocr_", extension);

            InputStream in = null;
            OutputStream out = null;
            try {
                in = body.byteStream();
                out = new FileOutputStream(tempFile);
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (in != null) in.close();
                if (out != null) out.close();
            }

            tempFile.deleteOnExit();
            return tempFile;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private static String guessExtension(String contentType, String imageUrl) {
        // Content-Type 优先
        if (contentType.contains("jpeg") || contentType.contains("jpg")) {
            return ".jpg";
        } else if (contentType.contains("png")) {
            return ".png";
        } else if (contentType.contains("bmp")) {
            return ".bmp";
        } else if (contentType.contains("gif")) {
            return ".gif";
        }

        // 从 URL 尾部猜
        if (imageUrl.contains(".")) {
            String lowerUrl = imageUrl.toLowerCase();
            if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg")) return ".jpg";
            if (lowerUrl.endsWith(".png")) return ".png";
            if (lowerUrl.endsWith(".bmp")) return ".bmp";
            if (lowerUrl.endsWith(".gif")) return ".gif";
        }

        // 默认
        return ".jpg";
    }


}
