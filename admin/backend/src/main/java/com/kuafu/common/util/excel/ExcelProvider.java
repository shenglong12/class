package com.kuafu.common.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.UUID;
import com.kuafu.web.api.ApiBusinessService;
import lombok.extern.slf4j.Slf4j;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static com.kuafu.common.util.ReflectUtils.createDateStyle;


@Component
@Slf4j
public class ExcelProvider {


    private static final int BATCH_COUNT = 100;

    @Resource
    private ApiBusinessService apiBusinessService;

    /**
     * 导入 Excel 数据
     *
     * @param file         excel文件
     * @param clazz        实体类
     * @param saveFunction Servcie
     * @param <T>          实体类范型
     * @throws IOException
     */
    @Transactional
    public <T> void importData(MultipartFile file, Class<T> clazz, Consumer<List<T>> saveFunction) {

        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, clazz, new ReadListener<T>() {
                private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

                @Override
                public void invoke(T data, AnalysisContext context) {
                    cachedDataList.add(data);
                    if (cachedDataList.size() >= BATCH_COUNT) {
                        saveData();
                        cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    saveData();
                }

                private void saveData() {
                    if (cachedDataList.isEmpty()) {
                        throw new BusinessException("导入的数据为空");
                    }
                    try {
                        saveFunction.accept(cachedDataList);
                    } catch (Exception e) {
                        throw new BusinessException("数据导入失败，列名或数据可能不匹配");
                    }
                }
            }).sheet().doRead();
        } catch (ExcelDataConvertException excelDataConvertException) {
            throw new BusinessException("请根据模版对比,检查excel中数据该是文本还是数字或者是表头与数据错误");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }


    /**
     * pdf 处理
     *
     * @param file
     * @param clazz
     * @param saveFunction
     * @param <T>
     */
    public <T> void pdfData(MultipartFile file, Class<T> clazz, Consumer<List<T>> saveFunction) {
        try {

            String classInfo = class2text(clazz);
            String classJson = class2jsonText(clazz);
            String prompt = "你是一个数据分析师，你负责将文档里的数据整理成有结构的数据。\n" +
                    "数据结构如下：\n" + "```\n" + classInfo + "\n```\n"
                    + "\n请按照要求的“输出结构”格式整体输出。"
                    + "\n要求输出JSON(并用JSON代码块格式包裹)，JSON中不要添加注释内容，以确保可以被程序解析。\n"
                    + "输出的JSON格式：\n```\n" + classJson + "\n```\n";

            log.info("==========={}", prompt);

            PDDocument document = PDDocument.load(file.getInputStream());
            int totalPages = document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            for (int i = 0; i < totalPages; i++) {
                try {
                    // 提取文字
                    stripper.setStartPage(i + 1);
                    stripper.setEndPage(i + 1);
                    StringBuilder text = new StringBuilder(stripper.getText(document));

                    PDPage page = document.getPage(i);
                    PDResources resources = page.getResources();
                    Iterable<COSName> cosNames = resources.getXObjectNames();
                    if (cosNames != null) {
                        for (COSName cosName : cosNames) {
                            if (resources.isImageXObject(cosName)) {
                                PDImageXObject pdfImage = (PDImageXObject) resources.getXObject(cosName);
                                BufferedImage image = pdfImage.getImage();
                                text.append(apiBusinessService.pic2word(image, "png"));
                            }
                        }
                    }

                    log.info("============={}", text);
                    String aiValue = apiBusinessService.text2text(text.toString(), prompt);
                    log.info("==========={}", aiValue);

                    String jsonValue = aiValue.replaceAll("(?s)^```\\w*\\n(.*)```$", "$1").trim();

                    List<T> listValue = JSON.parseArray(jsonValue, clazz);

                    log.info("==========={}", listValue);
                    saveFunction.accept(listValue);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }

        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    public String class2text(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder textBuilder = new StringBuilder();
        for (Field field : fields) {
            ExcelProperty excelPropertyAnno = field.getAnnotation(ExcelProperty.class);
            if (excelPropertyAnno != null) {
                String[] value = excelPropertyAnno.value();
                if (value.length > 0) {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    textBuilder.append(jsonProperty.value()).append(":").append(value[0]).append("\n");
                }
            }
        }
        return textBuilder.toString();
    }

    public String class2jsonText(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("[{");
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String value_info;
            String default_info;
            if (fieldType == String.class) {
                value_info = "string ";
                default_info = "无";
            } else if (Integer.class.isAssignableFrom(fieldType)) {
                value_info = "int ";
                default_info = "0";
            } else if (Double.class.isAssignableFrom(fieldType)) {
                value_info = "double ";
                default_info = "0";
            } else if (Number.class.isAssignableFrom(fieldType)) {
                value_info = "double ";
                default_info = "0";
            } else if (fieldType == Date.class) {
                value_info = "date ";
                default_info = "2025-06-01 00:00:00";
            } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                value_info = "boolean ";
                default_info = "1";
            } else {
                value_info = "string ";
                default_info = "无";
            }


            ExcelProperty excelPropertyAnno = field.getAnnotation(ExcelProperty.class);
            if (excelPropertyAnno != null) {
                String[] value = excelPropertyAnno.value();
                if (value.length > 0) {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    textBuilder.append("\"").append(jsonProperty.value()).append("\"")
                            .append(":")
                            .append("\"").append(value_info)
                            .append("//")
                            .append(value[0]).append(" ,默认值:").append(default_info).append("\"").append(",");

                }
            }
        }

        // 去掉最后一个逗号（如果存在）
        if (textBuilder.charAt(textBuilder.length() - 1) == ',') {
            textBuilder.deleteCharAt(textBuilder.length() - 1);
        }

        textBuilder.append("}]");
        return textBuilder.toString();
    }

    /**
     * 下载 Excel 模版
     *
     * @return WorkBook
     */
    public Workbook downloadExcelTemplate(Class<?> clazz) {
        // 1. 创建Workbook并设置表头
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("模板");
        Row headerRow = sheet.createRow(0);
        // 设置表头背景色
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());

        // 2. 反射获取字段注解生成表头
        Field[] fields = clazz.getDeclaredFields();
        int cell = 0;
        for (Field field : fields) {
            ExcelProperty excelPropertyAnno = field.getAnnotation(ExcelProperty.class);
            if (excelPropertyAnno != null) {
                String[] value = excelPropertyAnno.value();
                if (value.length > 0) {
                    Cell headerRowCell = headerRow.createCell(cell);
                    headerRowCell.setCellValue(value[0]);
                    headerRowCell.setCellStyle(headerStyle);
                    cell++;
                }
            }
        }
        // 创建行数据
        Row dataRow = sheet.createRow(1);
        int cellIndex = 0;
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            Cell dataRowCell = dataRow.createCell(cellIndex);
            ExcelProperty excelPropertyAnno = field.getAnnotation(ExcelProperty.class);
            if (excelPropertyAnno != null) {
                // 类型判断逻辑
                if (fieldType == String.class) {
                    dataRowCell.setCellValue("这里写入文字");
                } else if (Integer.class.isAssignableFrom(fieldType)) {
                    dataRowCell.setCellValue((1));
                } else if (Double.class.isAssignableFrom(fieldType)) {
                    dataRowCell.setCellValue((1.0));
                } else if (Number.class.isAssignableFrom(fieldType)) {
                    dataRowCell.setCellValue(((Number) 1.0).doubleValue());
                } else if (fieldType == Date.class) {
                    dataRowCell.setCellValue(new Date());
                    CellStyle dateStyle = createDateStyle(dataRowCell.getSheet().getWorkbook());
                    dataRowCell.setCellStyle(dateStyle);
                } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                    dataRowCell.setCellValue(true);
                } else {
                    dataRowCell.setCellValue("其它");
                }
                cellIndex++;
            }
        }
        return workbook;
    }
}
