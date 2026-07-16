package com.kuafu.common.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;


@UtilityClass
public class ReflectUtils {

    // 创建可复用的日期样式
    public static CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        return style;
    }
}
