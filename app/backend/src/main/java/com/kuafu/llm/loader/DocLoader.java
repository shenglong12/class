package com.kuafu.llm.loader;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;

public class DocLoader implements Loader {

    @Override
    public String loader(String file) {

        try {
            FileInputStream inputStream = new FileInputStream(FileUtil.file(file));
            return loader(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loader(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return loader(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loader(InputStream inputStream) {
        try {
            StringBuilder contentBuilder = new StringBuilder();
            HWPFDocument document = new HWPFDocument(inputStream);

            WordExtractor extractor = new WordExtractor(document);

            String[] paragraphText = extractor.getParagraphText();
            for (String paragraph : paragraphText) {
                contentBuilder.append(paragraph);
            }
            return contentBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
