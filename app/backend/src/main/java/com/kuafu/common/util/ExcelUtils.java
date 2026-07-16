package com.kuafu.common.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ExcelUtils {

    public static List<List<String>> readExcel(MultipartFile file) {
        List<List<String>> result = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            DecimalFormat decimalFormat = new DecimalFormat("#.######"); // 可以根据需要设置格式

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowList = new ArrayList<>();
                for (Cell cell : row) {
                    // 根据需要处理单元格数据
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.STRING) {
                        rowList.add(cell.getStringCellValue());
                    } else if (cellType == CellType.NUMERIC) {
                        String formattedValue = decimalFormat.format(cell.getNumericCellValue());
                        rowList.add(formattedValue);
                    } else {
                        rowList.add(cell.getStringCellValue());
                    }
                }
                result.add(rowList);
            }

            inputStream.close();

        } catch (IOException e) {
        }
        return result;
    }
}
