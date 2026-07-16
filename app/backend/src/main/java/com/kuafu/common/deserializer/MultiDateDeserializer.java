package com.kuafu.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kuafu.common.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultiDateDeserializer extends JsonDeserializer<Date> {

    private static final List<SimpleDateFormat> dateFormats = new ArrayList<>();

    static {
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        dateFormats.add(new SimpleDateFormat("yyyy/MM/dd"));
        dateFormats.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        // 你可以根据需要添加更多格式
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getText();
        if (StringUtils.isEmpty(dateStr)){
           return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                return format.parse(dateStr);
            } catch (ParseException e) {
                // 忽略并尝试下一个格式
            }
        }
        throw new RuntimeException("无法解析日期: " + dateStr);
    }
}

