package com.kuafu.common.util;

import lombok.experimental.UtilityClass;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@UtilityClass
public class WordImportUtils {
    
    public static String readWord(String path) throws Exception {
        File file = new File(path);
        String suffix =  StringUtils.substringAfterLast(path, ".");
        FileInputStream fileInputStream = new FileInputStream((file.getAbsolutePath()));
        return readWord(fileInputStream, suffix);
    }
 
    public static String readWord(InputStream inputStream, String suffix) throws Exception{
        if ("doc".equals(suffix)) {
            HWPFDocument document = new HWPFDocument(inputStream);
            WordExtractor extractor = new WordExtractor(document);
            return extractor.getText();
        } else if ("docx".equals(suffix)) {
            OPCPackage opcPackage = OPCPackage.open(inputStream);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            return extractor.getText();
        } else if("pdf".equals(suffix)){
            RandomAccessBuffer rab = new RandomAccessBuffer(inputStream);
            PDFParser pdfParser = new PDFParser(rab);
            pdfParser.parse();
            PDDocument document = pdfParser.getPDDocument();
            // 获取页码
            int pages = document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            return stripper.getText(document);
        }else{
            return null;
        }
    }
 
   
 
}