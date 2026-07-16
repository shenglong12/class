package com.kuafu.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动检测项目根目录，将相对路径转为绝对路径。
 * 解决不同启动方式（右键 Run Java、F5、mvn spring-boot:run）cwd 不同导致路径不一致的问题。
 *
 * 检测逻辑：从 user.dir 向上查找，直到找到包含 demo/ 或 .git 目录的路径作为项目根目录。
 */
public class ProjectPathEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> props = new HashMap<>();

        // 自动设置 SQLITE_PATH（如果未通过环境变量显式设置）
        if (isNotExplicitlySet(environment, "SQLITE_PATH")) {
            String projectRoot = findProjectRoot();
            String sqlitePath = new File(projectRoot, "app/data/sqlite/demo.sqlite").getAbsolutePath();
            props.put("SQLITE_PATH", sqlitePath);
        }

        // 自动设置 UPLOAD_PATH（如果未通过环境变量显式设置）
        if (isNotExplicitlySet(environment, "UPLOAD_PATH")) {
            String projectRoot = findProjectRoot();
            String uploadPath = new File(projectRoot, "uploadPath").getAbsolutePath();
            props.put("UPLOAD_PATH", uploadPath);
        }

        if (!props.isEmpty()) {
            environment.getPropertySources()
                    .addFirst(new MapPropertySource("projectPathConfig", props));
        }
    }

    /**
     * 判断环境变量是否已经显式设置（通过系统属性、OS 环境变量等）
     */
    private boolean isNotExplicitlySet(ConfigurableEnvironment environment, String key) {
        String value = environment.getProperty(key);
        return value == null || value.isEmpty();
    }

    /**
     * 从 user.dir 开始向上查找项目根目录。
     * 项目根目录的标志：包含 demo/ 或 .git 目录。
     */
    private String findProjectRoot() {
        File dir = new File(System.getProperty("user.dir"));

        while (dir != null) {
            if (new File(dir, "demo").isDirectory() || new File(dir, ".git").isDirectory()) {
                return dir.getAbsolutePath();
            }
            dir = dir.getParentFile();
        }

        // 兜底：返回 user.dir
        return System.getProperty("user.dir");
    }
}
