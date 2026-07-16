package com.kuafu.llm.loader;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedSizeTextSplit {

    private final int chunkSize;

    public FixedSizeTextSplit(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public List<String> split(File file, String fileType) {
        Loader loader;
        if (StringUtils.equalsIgnoreCase(fileType, "doc")) {
            loader = new DocLoader();
        } else if (StringUtils.equalsIgnoreCase(fileType, "docx")) {
            loader = new DocxLoader();
        } else if (StringUtils.equalsIgnoreCase(fileType, "pdf")) {
            loader = new PdfLoader();
        } else {
            loader = new TextLoader();
        }

        String content = loader.loader(file);

        return splitText(content, this.chunkSize);
    }

    private List<String> splitText(String text, int chunkSize) {
        // 移除所有空格和换行符
        String cleanedText = text.replaceAll("\\s+", ""); // \\s+ 匹配任意空白字符（空格、换行、制表符等）

        // 按指定大小切分文本
        List<String> chunks = new ArrayList<>();
        int length = cleanedText.length();
        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(cleanedText.substring(i, Math.min(length, i + chunkSize)));
        }
        return chunks.isEmpty() ? Collections.singletonList(cleanedText) : chunks;
    }
}
