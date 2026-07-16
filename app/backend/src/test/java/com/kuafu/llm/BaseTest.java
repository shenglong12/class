package com.kuafu.llm;

import com.google.common.collect.Maps;
import com.kuafu.common.llm.LlmDifyApi;
import com.kuafu.common.llm.MessageResponse;
import com.kuafu.common.llm.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class BaseTest {


    @Test
    public void test_dify() {

        LlmDifyApi api = new LlmDifyApi();
        api.setBaseUrl("http://60.204.199.245:6680/v1");
        api.setDifyKey("app-gzhOFAgZd4jpjRvyoRoQ6Z4e");

        Map<String, Object> inputs = Maps.newHashMap();
        inputs.put("appid", "5cbf294e");
        inputs.put("apisecret", "53cb5a83d96bcdce7722f93b866c2725");
        inputs.put("apikey", "2b7d78c15fce10aa59c9f76dad33572c");

        String query = "生成一张河流的图片";

        Response response = api.chat(query, inputs, null, null);

        log.info("{}", response.content());
    }
}
