package com.kuafu.common.llm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LLmResponse implements Response {

    @Expose
    @SerializedName("code")
    private int code;

    @Expose
    @SerializedName("data")
    private BlockData data;

    @Expose
    @SerializedName("message")
    private String message;


    @Data
    public static class BlockData {
        @Expose
        @SerializedName("answer")
        private String answer;
    }

    @Override
    public String content() {
        if (data != null) {
            return data.getAnswer();
        } else {
            return message;
        }
    }
}
