package com.kuafu;


import com.kuafu.common.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Map;


@Slf4j
public class BaseTest {

    private String pythonCmd = "D:\\software\\anaconda3\\envs\\charts;D:\\software\\anaconda3\\envs\\charts\\Library\\mingw-w64\\bin;D:\\software\\anaconda3\\envs\\charts\\Library\\usr\\bin;D:\\software\\anaconda3\\envs\\charts\\Library\\bin;D:\\software\\anaconda3\\envs\\charts\\Scripts;D:\\software\\anaconda3\\envs\\charts\\bin;D:\\software\\anaconda3\\condabin;";

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
            e.printStackTrace();
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
    public void test_pdf() throws Exception {

        PDDocument document = PDDocument.load(new File("/Users/jiangfei/11.pdf"));
        int totalPages = document.getNumberOfPages();

        PDFTextStripper stripper = new PDFTextStripper();
        for (int i = 0; i < totalPages; i++) {
            PDPage page = document.getPage(i);

            // 提取文字
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            String text = stripper.getText(document);

            log.info("=={}", text);

            PDResources resources = page.getResources();
            Iterable<COSName> cosNames = resources.getXObjectNames();
            if (cosNames != null) {
                Iterator<COSName> cosNamesIter = cosNames.iterator();
                while (cosNamesIter.hasNext()) {
                    COSName cosName = cosNamesIter.next();
                    if (resources.isImageXObject(cosName)) {
                        PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
                        BufferedImage image = Ipdmage.getImage();
                        try (FileOutputStream out = new FileOutputStream(
                                "./" + UUID.randomUUID() + ".png")) {
                            ImageIO.write(image, "png", out);
                        } catch (IOException e) {
                        }
                    }
                }
            }

        }


    }


}
