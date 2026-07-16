package com.kuafu.api.config;


import com.kuafu.api.factory.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "api-proxy")
@PropertySource(value = "classpath:api-config.yml", factory = YamlPropertySourceFactory.class)
@Data
public class ApiProxyConfig {
    private List<ApiConfig> apis;

    @Data
    public static class ApiConfig {
        private String apiId;
        private String description;
        private String targetUrl;
        private String method;
        private List<Header> headers;
        private boolean isRestFul;
        private List<Param> params;

        @Data
        public static class Header {
            private String name;
            private String type; // constant or variable
            private String value;
        }

        @Data
        public static class Param {
            private String name;
            private String type; // constant or variable
            private String value;
        }
    }
}
