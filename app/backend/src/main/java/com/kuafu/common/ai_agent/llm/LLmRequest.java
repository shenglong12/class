package com.kuafu.common.ai_agent.llm;

import com.kuafu.common.http.AbstractModel;

import java.util.HashMap;
import java.util.Map;

public class LLmRequest extends AbstractModel {
    private  String key;
    @Override
    public Map<String, String> GetHeader() {

        HashMap<String, String> map = new HashMap<>();
        map.put("Authorization",key);
        return map;
    }

    public LLmRequest(String key) {
        this.key=key;
    }

    @Override
    protected void toMap(HashMap<String, String> map, String prefix) {

    }
}
