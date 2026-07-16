package com.kuafu;

import com.google.common.collect.Maps;
import com.kuafu.web.dynamic.VoConverter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

@Slf4j
public class BaseTest {

    private String pythonCmd = "D:\\software\\anaconda3\\envs\\charts;D:\\software\\anaconda3\\envs\\charts\\Library\\mingw-w64\\bin;D:\\software\\anaconda3\\envs\\charts\\Library\\usr\\bin;D:\\software\\anaconda3\\envs\\charts\\Library\\bin;D:\\software\\anaconda3\\envs\\charts\\Scripts;D:\\software\\anaconda3\\envs\\charts\\bin;D:\\software\\anaconda3\\condabin;";


    @Test
    public void test_vo() throws Exception {
        String tableName = "users";
        Map<String, Object> mapData = Maps.newConcurrentMap();
        mapData.put("users_id", 1);
        mapData.put("username", "test");

        Object obj = VoConverter.convert(tableName, mapData);
        log.info("{}", obj);
    }


    @Test
    public void test1() {
        String[] commands;
        //commands = new String[]{"/bin/sh", "-c", "echo $PATH"};
        commands = new String[]{"cmd", "/C", "python demo.py"};
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> env = processBuilder.environment();
        String path = pythonCmd + env.get("Path");
        env.put("Path", path);
        File workDir = new File("python");
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        processBuilder.directory(workDir);


        processBuilder.command(commands);
        try {
            // Start the process
            Process process = processBuilder.start();

            // Read output
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {

        }
    }


    @Test
    public void test2() {
        Long a1 = 156L;
        Long a2 = 156L;

        log.info("{}", a1.equals(a2));
        log.info("{}", a1 == a2);
    }

    @Test
    public void test_orc() {
        String path = "/Users/jiangfei/Documents/kuafu/111.png";
        Tesseract tesseract = new Tesseract();

        // 设置语言模型路径（tessdata 文件夹）
        tesseract.setDatapath("src/main/resources/tessdata"); // or use absolute path

        // 设置语言：英文 = eng，简体中文 = chi_sim，多个语言用 + 连接
        tesseract.setLanguage("chi_sim");

        try {
            // 指定本地图片文件路径
            File imageFile = new File(path);

            // 开始识别
            String result = tesseract.doOCR(imageFile);

            // 输出结果
            System.out.println("识别结果：");
            System.out.println(result);

        } catch (TesseractException e) {

        }
    }

}
