package com.kuafu.llm.loader;

import cn.hutool.core.io.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;


/**
 * pdf文档的加载器
 */
public class PdfLoader implements Loader {
    @Override
    public String loader(String file) {
        try {
            FileInputStream inputStream = new FileInputStream(FileUtil.file(file));
            return loader(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loader(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return loader(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loader(InputStream inputStream) {
        try {
            // 加载 PDF 文档
            PDDocument document = PDDocument.load(inputStream);

            // 创建 PDFTextStripper 实例
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // 获取 PDF 文档的文本内容
            String text = pdfStripper.getText(document);


            // 关闭 PDF 文档
            document.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
