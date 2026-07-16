package com.kuafu.llm.loader;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.List;

public class DocxLoader implements Loader {
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
            XWPFDocument document = new XWPFDocument(OPCPackage.open(inputStream));

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                contentBuilder.append(paragraph.getText());
            }
            return contentBuilder.toString();
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
