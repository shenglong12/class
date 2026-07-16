package com.kuafu.common.util.excel;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.SpreadsheetVersion;

import java.lang.reflect.Field;

@UtilityClass
public class ExcelTool {

    public static void resetCellMaxTextLength() {
        SpreadsheetVersion excel2007 = SpreadsheetVersion.EXCEL2007;
        if (Integer.MAX_VALUE != excel2007.getMaxTextLength()) {
            Field field;
            try {
                field = excel2007.getClass().getDeclaredField("_maxTextLength");
                field.setAccessible(true);
                field.set(excel2007,Integer.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
