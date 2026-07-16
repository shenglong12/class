package com.kuafu.common.shell;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ShellExecutor {

    private final ProcessBuilder processBuilder;
    private final StringBuilder builder;

    private ShellExecutor(String command, String directory, Map<String, String> env, boolean createDirectory) {
        String[] commands;
        if (!PlatformDependent.isWindows()) {
            commands = new String[]{"/bin/sh", "-c", command};
        } else {
            commands = new String[]{"cmd", "/C", command};
        }

        processBuilder = new ProcessBuilder(commands);

        //设置环境变量
        if (env != null && !env.isEmpty()) {

            Map<String, String> processEnv = processBuilder.environment();

            for (Map.Entry<String, String> entry : env.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (processEnv.containsKey(key)) {
                    value = value + processEnv.get(key);
                    log.info("merge env key: {} ,value: {}", key, value);
                }
                processEnv.put(key, value);
            }
        }

        //设置工作目录
        File workDir = new File(directory);
        if (!workDir.exists() && createDirectory) {
            //目录不存在，并且创建目录
            workDir.mkdirs();
            processBuilder.directory(workDir);
        } else if (workDir.exists() && workDir.isDirectory()) {
            //目录存在
            processBuilder.directory(workDir);
        }

        //设置错误流
        processBuilder.redirectErrorStream(true);

        builder = new StringBuilder();
    }


    public static ShellExecutor execute(String command, String directory) {
        return new ShellExecutor(command, directory, Maps.newHashMap(), false);
    }

    public static ShellExecutor execute(String command, String directory, boolean createDirectory) {
        return new ShellExecutor(command, directory, Maps.newHashMap(), createDirectory);
    }

    public static ShellExecutor execute(String command, String directory, Map<String, String> env) {
        return new ShellExecutor(command, directory, env, false);
    }

    public static ShellExecutor execute(String command, String directory, boolean createDirectory, Map<String, String> env) {
        return new ShellExecutor(command, directory, env, createDirectory);
    }

    public boolean start() {
        try {
            Process process = processBuilder.start();
            CountDownLatch latch = new CountDownLatch(1);

            new Thread(() -> {

                BufferedReader reader = null;
                try {
                    InputStream inputStream = process.getInputStream();
                    if (inputStream == null) {
                        latch.countDown();
                        return;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                } catch (Exception ignored) {
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Exception ignored) {
                        }
                    }
                }

                latch.countDown();
            }).start();

            //等待命令结束
            int exitCode = process.waitFor();

            //等待线程执行结束
            latch.await();

            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }


    public String getContent() {
        if (builder.length() > 0) {
            return builder.toString().trim();
        }
        return builder.toString();
    }
}
