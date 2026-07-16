package com.kuafu.common.mcp.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@Data
public class ServerParameters {

    private static final List<String> DEFAULT_INHERITED_ENV_VARS = System.getProperty("os.name")
            .toLowerCase()
            .contains("win")
            ? Arrays.asList("APPDATA", "HOMEDRIVE", "HOMEPATH", "LOCALAPPDATA", "PATH", "PROCESSOR_ARCHITECTURE",
            "SYSTEMDRIVE", "SYSTEMROOT", "TEMP", "USERNAME", "USERPROFILE")
            : Arrays.asList("HOME", "LOGNAME", "PATH", "SHELL", "TERM", "USER");


    @JsonProperty("command")
    private String command;

    @JsonProperty("args")
    private List<String> args = new ArrayList<>();

    @JsonProperty("env")
    private Map<String, String> env;


    private ServerParameters(String command, List<String> args, Map<String, String> env) {

        this.command = command;
        this.args = args;
        this.env = new HashMap<>(getDefaultEnvironment());
        if (env != null && !env.isEmpty()) {
            this.env.putAll(env);
        }
    }

    public static Builder builder(String command) {
        return new Builder(command);
    }

    public static class Builder {
        private String command;

        private List<String> args = new ArrayList<>();

        private Map<String, String> env = new HashMap<>();

        public Builder(String command) {

            this.command = command;
        }

        public Builder args(String... args) {

            this.args = Arrays.asList(args);
            return this;
        }

        public Builder args(List<String> args) {

            this.args = new ArrayList<>(args);
            return this;
        }

        public Builder arg(String arg) {

            this.args.add(arg);
            return this;
        }

        public Builder env(Map<String, String> env) {
            if (env != null && !env.isEmpty()) {
                this.env.putAll(env);
            }
            return this;
        }

        public Builder addEnvVar(String key, String value) {

            this.env.put(key, value);
            return this;
        }

        public ServerParameters build() {
            return new ServerParameters(command, args, env);
        }
    }


    private static Map<String, String> getDefaultEnvironment() {
        return System.getenv()
                .entrySet()
                .stream()
                .filter(entry -> DEFAULT_INHERITED_ENV_VARS.contains(entry.getKey()))
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> !entry.getValue().startsWith("()"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
