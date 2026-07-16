package com.kuafu.common.llm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MessageResponse implements Response {

    @Expose
    @SerializedName("event")
    private String event;

    @Expose
    @SerializedName("task_id")
    private String taskId;

    @Expose
    @SerializedName("conversation_id")
    private String conversation_id;

    @Expose
    @SerializedName("mode")
    private String mode;

    @Expose
    @SerializedName("answer")
    private String answer;

    @Override
    public String content() {
        return getAnswer();
    }
}
