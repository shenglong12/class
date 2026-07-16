package com.kuafu.common.component;


import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

import static com.kuafu.common.constant.LiteCmpConstants.RESULT;


/**
 * requestData对象
 */
public class RequestDataContext {
    private final ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap();

    public RequestDataContext() {
    }

    private <T> void putDataMap(String key, T t) {
        if (StringUtils.isNull(t)) {
            throw new NullPointerException("data can't accept null param");
        } else {
            this.dataMap.put(key, t);
        }
    }

    public boolean hasData(String key) {
        return this.dataMap.containsKey(key);
    }

    public <T> T getData(String key) {
        return (T) this.dataMap.get(key);
    }

    public <T> void setData(String key, T t) {
        this.putDataMap(key, t);
    }

    public BaseResponse getResult() {
        BaseResponse response = getData(RESULT);
        return response;
    }
}
