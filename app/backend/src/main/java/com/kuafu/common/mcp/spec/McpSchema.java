package com.kuafu.common.mcp.spec;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class McpSchema {

    private McpSchema() {
    }

    public static final String LATEST_PROTOCOL_VERSION = "2024-11-05";

    public static final String JSONRPC_VERSION = "2.0";

    // ---------------------------
    // Method Names
    // ---------------------------

    // Lifecycle Methods
    public static final String METHOD_INITIALIZE = "initialize";

    public static final String METHOD_NOTIFICATION_INITIALIZED = "notifications/initialized";

    public static final String METHOD_PING = "ping";

    // Tool Methods
    public static final String METHOD_TOOLS_LIST = "tools/list";

    public static final String METHOD_TOOLS_CALL = "tools/call";

    public static final String METHOD_NOTIFICATION_TOOLS_LIST_CHANGED = "notifications/tools/list_changed";

    // Resources Methods
    public static final String METHOD_RESOURCES_LIST = "resources/list";

    public static final String METHOD_RESOURCES_READ = "resources/read";

    public static final String METHOD_NOTIFICATION_RESOURCES_LIST_CHANGED = "notifications/resources/list_changed";

    public static final String METHOD_RESOURCES_TEMPLATES_LIST = "resources/templates/list";

    public static final String METHOD_RESOURCES_SUBSCRIBE = "resources/subscribe";

    public static final String METHOD_RESOURCES_UNSUBSCRIBE = "resources/unsubscribe";

    // Prompt Methods
    public static final String METHOD_PROMPT_LIST = "prompts/list";

    public static final String METHOD_PROMPT_GET = "prompts/get";

    public static final String METHOD_NOTIFICATION_PROMPTS_LIST_CHANGED = "notifications/prompts/list_changed";

    public static final String METHOD_COMPLETION_COMPLETE = "completion/complete";

    // Logging Methods
    public static final String METHOD_LOGGING_SET_LEVEL = "logging/setLevel";

    public static final String METHOD_NOTIFICATION_MESSAGE = "notifications/message";

    // Roots Methods
    public static final String METHOD_ROOTS_LIST = "roots/list";

    public static final String METHOD_NOTIFICATION_ROOTS_LIST_CHANGED = "notifications/roots/list_changed";

    // Sampling Methods
    public static final String METHOD_SAMPLING_CREATE_MESSAGE = "sampling/createMessage";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static final class ErrorCodes {
        /**
         * Invalid JSON was received by the server.
         */
        public static final int PARSE_ERROR = -32700;

        /**
         * The JSON sent is not a valid Request object.
         */
        public static final int INVALID_REQUEST = -32600;

        /**
         * The method does not exist / is not available.
         */
        public static final int METHOD_NOT_FOUND = -32601;

        /**
         * Invalid method parameter(s).
         */
        public static final int INVALID_PARAMS = -32602;

        /**
         * Internal JSON-RPC error.
         */
        public static final int INTERNAL_ERROR = -32603;
    }

    private static final TypeReference<HashMap<String, Object>> MAP_TYPE_REF = new TypeReference<HashMap<String, Object>>() {
    };

    public static JSONRPCMessage deserializeJsonRpcMessage(ObjectMapper objectMapper, String jsonText)
            throws IOException {

        log.debug("Received JSON message: {}", jsonText);

        Map<String, Object> map = objectMapper.readValue(jsonText, MAP_TYPE_REF);

        // Determine message type based on specific JSON structure
        if (map.containsKey("method") && map.containsKey("id")) {
            return objectMapper.convertValue(map, JSONRPCRequest.class);
        } else if (map.containsKey("method") && !map.containsKey("id")) {
            return objectMapper.convertValue(map, JSONRPCNotification.class);
        } else if (map.containsKey("result") || map.containsKey("error")) {
            return objectMapper.convertValue(map, JSONRPCResponse.class);
        }

        throw new IllegalArgumentException("Cannot deserialize JSONRPCMessage: " + jsonText);
    }

    public static interface JSONRPCMessage {
        String jsonrpc();
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class JSONRPCRequest implements JSONRPCMessage {
        @JsonProperty("jsonrpc")
        private String jsonrpc;

        @JsonProperty("method")
        private String method;

        @JsonProperty("id")
        private Object id;

        @JsonProperty("params")
        private Object params;

        public JSONRPCRequest() {
        }

        public JSONRPCRequest(String jsonrpc, String method, Object id, Object params) {
            this.jsonrpc = jsonrpc;
            this.method = method;
            this.id = id;
            this.params = params;
        }

        @Override
        public String jsonrpc() {
            return jsonrpc;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class JSONRPCNotification implements JSONRPCMessage {

        @JsonProperty("jsonrpc")
        private String jsonrpc;

        @JsonProperty("method")
        private String method;

        @JsonProperty("params")
        private Object params;

        public JSONRPCNotification() {
        }

        public JSONRPCNotification(String jsonrpc, String method, Object params) {
            this.jsonrpc = jsonrpc;
            this.method = method;
            this.params = params;
        }

        @Override
        public String jsonrpc() {
            return jsonrpc;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class JSONRPCResponse implements JSONRPCMessage {

        @JsonProperty("jsonrpc")
        private String jsonrpc;

        @JsonProperty("id")
        private Object id;

        @JsonProperty("result")
        private Object result;

        @JsonProperty("error")
        private JSONRPCError error;

        public JSONRPCResponse() {

        }

        public JSONRPCResponse(String jsonrpc, Object id, Object result, JSONRPCError error) {
            this.jsonrpc = jsonrpc;
            this.id = id;
            this.result = result;
            this.error = error;
        }

        @Override
        public String jsonrpc() {
            return jsonrpc;
        }

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class JSONRPCError {
            @JsonProperty("code")
            private int code;

            @JsonProperty("message")
            private String message;

            @JsonProperty("data")
            private Object data;

            public JSONRPCError(int code, String message, Object data) {
                this.code = code;
                this.message = message;
                this.data = data;
            }
        }

    }


    public interface Request {
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InitializeRequest implements Request {
        @JsonProperty("protocolVersion")
        private String protocolVersion;
        @JsonProperty("capabilities")
        private ClientCapabilities capabilities;
        @JsonProperty("clientInfo")
        private Implementation clientInfo;

        public InitializeRequest(String protocolVersion, ClientCapabilities capabilities, Implementation clientInfo) {
            this.protocolVersion = protocolVersion;
            this.capabilities = capabilities;
            this.clientInfo = clientInfo;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InitializeResult {

        @JsonProperty("protocolVersion")
        private String protocolVersion;

        @JsonProperty("capabilities")
        private ServerCapabilities capabilities;

        @JsonProperty("serverInfo")
        private Implementation serverInfo;

        @JsonProperty("instructions")
        private String instructions;

        public InitializeResult() {
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ClientCapabilities {

        @JsonProperty("experimental")
        private Map<String, Object> experimental;
        @JsonProperty("roots")
        private RootCapabilities roots;
        @JsonProperty("sampling")
        private Sampling sampling;

        public ClientCapabilities() {

        }

        public ClientCapabilities(Map<String, Object> experimental, RootCapabilities roots,
                                  Sampling sampling) {
            this.experimental = experimental;
            this.roots = roots;
            this.sampling = sampling;
        }


        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class RootCapabilities {
            @JsonProperty("listChanged")
            Boolean listChanged;

            public RootCapabilities() {
            }

            public RootCapabilities(Boolean listChanged) {
                this.listChanged = listChanged;
            }
        }

        public static class Sampling {

        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private Map<String, Object> experimental;
            private RootCapabilities roots;
            private Sampling sampling;

            public Builder experimental(Map<String, Object> experimental) {
                this.experimental = experimental;
                return this;
            }

            public Builder roots(Boolean listChanged) {
                this.roots = new RootCapabilities(listChanged);
                return this;
            }

            public Builder sampling() {
                this.sampling = new Sampling();
                return this;
            }

            public ClientCapabilities build() {

                return new ClientCapabilities(experimental, roots, sampling);
            }
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Implementation {
        @JsonProperty("name")
        private String name;
        @JsonProperty("version")
        private String version;

        public Implementation() {

        }

        public Implementation(String name, String version) {
            this.name = name;
            this.version = version;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ServerCapabilities {


        @JsonProperty("completions")
        private CompletionCapabilities completions;

        @JsonProperty("experimental")
        private Map<String, Object> experimental;

        @JsonProperty("logging")
        private LoggingCapabilities logging;

        @JsonProperty("prompts")
        private PromptCapabilities prompts;

        @JsonProperty("resources")
        private ResourceCapabilities resources;

        @JsonProperty("tools")
        private ToolCapabilities tools;

        public ServerCapabilities() {
        }

        public ServerCapabilities(CompletionCapabilities completions,
                                  Map<String, Object> experimental,
                                  LoggingCapabilities logging,
                                  PromptCapabilities prompts,
                                  ResourceCapabilities resources,
                                  ToolCapabilities tools) {
            this.completions = completions;
            this.experimental = experimental;
            this.logging = logging;
            this.prompts = prompts;
            this.resources = resources;
            this.tools = tools;
        }


        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        public static class CompletionCapabilities {

            public CompletionCapabilities() {
            }
        }

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        public static class LoggingCapabilities {
            public LoggingCapabilities() {

            }
        }

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        @Data
        public static class PromptCapabilities {
            @JsonProperty("listChanged")
            private Boolean listChanged;

            public PromptCapabilities() {
            }

            public PromptCapabilities(Boolean listChanged) {
                this.listChanged = listChanged;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        @Data
        public static class ResourceCapabilities {
            @JsonProperty("subscribe")
            private Boolean subscribe;
            @JsonProperty("listChanged")
            private Boolean listChanged;

            public ResourceCapabilities() {
            }

            public ResourceCapabilities(Boolean subscribe, Boolean listChanged) {
                this.subscribe = subscribe;
                this.listChanged = listChanged;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        @Data
        public static class ToolCapabilities {
            @JsonProperty("listChanged")
            private Boolean listChanged;

            public ToolCapabilities() {
            }

            public ToolCapabilities(Boolean listChanged) {
                this.listChanged = listChanged;
            }
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private CompletionCapabilities completions;
            private Map<String, Object> experimental;
            private LoggingCapabilities logging = new LoggingCapabilities();
            private PromptCapabilities prompts;
            private ResourceCapabilities resources;
            private ToolCapabilities tools;

            public Builder completions() {
                this.completions = new CompletionCapabilities();
                return this;
            }

            public Builder experimental(Map<String, Object> experimental) {
                this.experimental = experimental;
                return this;
            }

            public Builder logging() {
                this.logging = new LoggingCapabilities();
                return this;
            }

            public Builder prompts(Boolean listChanged) {
                this.prompts = new PromptCapabilities(listChanged);
                return this;
            }

            public Builder resources(Boolean subscribe, Boolean listChanged) {
                this.resources = new ResourceCapabilities(subscribe, listChanged);
                return this;
            }

            public Builder tools(Boolean listChanged) {
                this.tools = new ToolCapabilities(listChanged);
                return this;
            }

            public ServerCapabilities build() {
                return new ServerCapabilities(completions, experimental, logging, prompts, resources, tools);
            }
        }
    }


    @Getter
    public enum Role {
        @JsonProperty("user")
        USER,
        @JsonProperty("assistant")
        ASSISTANT
    }

    public interface Annotated {

        Annotations annotations();

    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Annotations {
        @JsonProperty("audience")
        private List<Role> audience;
        @JsonProperty("priority")
        private Double priority;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Resource implements Annotated {
        @JsonProperty("uri")
        private String uri;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("mimeType")
        private String mimeType;
        @JsonProperty("annotations")
        private Annotations annotations;

        @Override
        public Annotations annotations() {
            return this.annotations;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResourceTemplate implements Annotated {
        @JsonProperty("uriTemplate")
        private String uriTemplate;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("mimeType")
        private String mimeType;

        @JsonProperty("annotations")
        private Annotations annotations;

        @Override
        public Annotations annotations() {
            return this.annotations;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ListResourcesResult {
        @JsonProperty("resources")
        private List<Resource> resources;
        @JsonProperty("nextCursor")
        private String nextCursor;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ListResourceTemplatesResult {
        @JsonProperty("resourceTemplates")
        private List<ResourceTemplate> resourceTemplates;
        @JsonProperty("nextCursor")
        private String nextCursor;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ReadResourceRequest {
        @JsonProperty("uri")
        private String uri;

        public ReadResourceRequest(String uri) {
            this.uri = uri;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ReadResourceResult {
        @JsonProperty("contents")
        List<ResourceContents> contents;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class SubscribeRequest {
        @JsonProperty("uri")
        private String uri;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UnsubscribeRequest {
        @JsonProperty("uri")
        private String uri;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({@JsonSubTypes.Type(value = TextResourceContents.class, name = "text"),
            @JsonSubTypes.Type(value = BlobResourceContents.class, name = "blob")})
    public interface ResourceContents {
        String uri();

        String mimeType();
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class TextResourceContents implements ResourceContents {
        @JsonProperty("uri")
        private String uri;

        @JsonProperty("mimeType")
        private String mimeType;

        @JsonProperty("text")
        private String text;

        @Override
        public String uri() {
            return this.uri;
        }

        @Override
        public String mimeType() {
            return this.mimeType;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class BlobResourceContents implements ResourceContents {
        @JsonProperty("uri")
        private String uri;

        @JsonProperty("mimeType")
        private String mimeType;

        @JsonProperty("blob")
        private String blob;

        @Override
        public String uri() {
            return this.uri;
        }

        @Override
        public String mimeType() {
            return this.mimeType;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Prompt {
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("arguments")
        private List<PromptArgument> arguments;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PromptArgument {
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("required")
        private Boolean required;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PromptMessage {
        @JsonProperty("role")
        private Role role;
        @JsonProperty("content")
        private Content content;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ListPromptsResult {
        @JsonProperty("prompts")
        private List<Prompt> prompts;
        @JsonProperty("nextCursor")
        private String nextCursor;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetPromptRequest implements Request {
        @JsonProperty("name")
        private String name;
        @JsonProperty("arguments")
        private Map<String, Object> arguments;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetPromptResult {
        @JsonProperty("description")
        private String description;
        @JsonProperty("messages")
        private List<PromptMessage> messages;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ListToolsResult {
        @JsonProperty("tools")
        private List<Tool> tools;

        @JsonProperty("nextCursor")
        private String nextCursor;

        public ListToolsResult() {
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class JsonSchema {
        @JsonProperty("type")
        private String type;
        @JsonProperty("properties")
        private Map<String, Object> properties;
        @JsonProperty("required")
        private List<String> required;
        @JsonProperty("additionalProperties")
        private Boolean additionalProperties;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Tool {
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("inputSchema")
        private JsonSchema inputSchema;

        public Tool() {
        }

        public Tool(String name, String description, JsonSchema schema) {
            this.name = name;
            this.description = description;
            this.inputSchema = schema;
        }

        public Tool(String name, String description, String schema) {
            this(name, description, parseSchema(schema));
        }
    }


    private static JsonSchema parseSchema(String schema) {
        try {
            return OBJECT_MAPPER.readValue(schema, JsonSchema.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid schema: " + schema, e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CallToolRequest implements Request {
        @JsonProperty("name")
        private String name;
        @JsonProperty("arguments")
        private Map<String, Object> arguments;

        public CallToolRequest() {
        }

        public CallToolRequest(String name, String jsonArguments) {
            this(name, parseJsonArguments(jsonArguments));
        }

        public CallToolRequest(String name, Map<String, Object> arguments) {
            this.name = name;
            this.arguments = arguments;
        }


        private static Map<String, Object> parseJsonArguments(String jsonArguments) {
            try {
                return OBJECT_MAPPER.readValue(jsonArguments, MAP_TYPE_REF);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid arguments: " + jsonArguments, e);
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CallToolResult {

        @JsonProperty("content")
        private List<Content> content;
        @JsonProperty("isError")
        private Boolean isError;

        public CallToolResult() {
        }

        public CallToolResult(List<Content> content, Boolean isError) {
            this.content = content;
            this.isError = isError;
        }

        public CallToolResult(String content, Boolean isError) {
            this(Lists.newArrayList(new TextContent(content)), isError);
        }


        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private List<Content> content = new ArrayList<>();
            private Boolean isError;


            public Builder content(List<Content> content) {
                this.content = content;
                return this;
            }

            public Builder textContent(List<String> textContent) {
                textContent.stream()
                        .map(TextContent::new)
                        .forEach(this.content::add);
                return this;
            }

            public Builder addContent(Content contentItem) {
                if (this.content == null) {
                    this.content = new ArrayList<>();
                }
                this.content.add(contentItem);
                return this;
            }

            public Builder addTextContent(String text) {
                return addContent(new TextContent(text));
            }

            public Builder isError(Boolean isError) {
                this.isError = isError;
                return this;
            }

            public CallToolResult build() {
                return new CallToolResult(content, isError);
            }
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ModelPreferences {
        @JsonProperty("hints")
        private List<ModelHint> hints;

        @JsonProperty("costPriority")
        private Double costPriority;

        @JsonProperty("speedPriority")
        private Double speedPriority;

        @JsonProperty("intelligencePriority")
        private Double intelligencePriority;

        public ModelPreferences(List<ModelHint> hints,
                                Double costPriority,
                                Double speedPriority,
                                Double intelligencePriority) {
            this.hints = hints;
            this.costPriority = costPriority;
            this.speedPriority = speedPriority;
            this.intelligencePriority = intelligencePriority;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private List<ModelHint> hints;
            private Double costPriority;
            private Double speedPriority;
            private Double intelligencePriority;

            public Builder hints(List<ModelHint> hints) {
                this.hints = hints;
                return this;
            }

            public Builder addHint(String name) {
                if (this.hints == null) {
                    this.hints = new ArrayList<>();
                }
                this.hints.add(new ModelHint(name));
                return this;
            }

            public Builder costPriority(Double costPriority) {
                this.costPriority = costPriority;
                return this;
            }

            public Builder speedPriority(Double speedPriority) {
                this.speedPriority = speedPriority;
                return this;
            }

            public Builder intelligencePriority(Double intelligencePriority) {
                this.intelligencePriority = intelligencePriority;
                return this;
            }

            public ModelPreferences build() {
                return new ModelPreferences(hints, costPriority, speedPriority, intelligencePriority);
            }

        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ModelHint {
        @JsonProperty("name")
        private String name;

        public ModelHint(String name) {
            this.name = name;
        }

        public static ModelHint of(String name) {
            return new ModelHint(name);
        }

    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class SamplingMessage {
        @JsonProperty("role")
        private Role role;
        @JsonProperty("content")
        private Content content;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CreateMessageRequest implements Request {

        @JsonProperty("messages")
        private List<SamplingMessage> messages;

        @JsonProperty("modelPreferences")
        private ModelPreferences modelPreferences;

        @JsonProperty("systemPrompt")
        private String systemPrompt;

        @JsonProperty("includeContext")
        private ContextInclusionStrategy includeContext;

        @JsonProperty("temperature")
        private Double temperature;

        @JsonProperty("maxTokens")
        private int maxTokens;

        @JsonProperty("stopSequences")
        List<String> stopSequences;

        @JsonProperty("metadata")
        Map<String, Object> metadata;

        public CreateMessageRequest(List<SamplingMessage> messages,
                                    ModelPreferences modelPreferences,
                                    String systemPrompt,
                                    ContextInclusionStrategy includeContext,
                                    Double temperature,
                                    int maxTokens, List<String> stopSequences,
                                    Map<String, Object> metadata) {
            this.messages = messages;
            this.modelPreferences = modelPreferences;
            this.systemPrompt = systemPrompt;
            this.includeContext = includeContext;
            this.temperature = temperature;
            this.maxTokens = maxTokens;
            this.stopSequences = stopSequences;
            this.metadata = metadata;
        }


        public enum ContextInclusionStrategy {
            @JsonProperty("none") NONE,
            @JsonProperty("thisServer") THIS_SERVER,
            @JsonProperty("allServers") ALL_SERVERS
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private List<SamplingMessage> messages;
            private ModelPreferences modelPreferences;
            private String systemPrompt;
            private ContextInclusionStrategy includeContext;
            private Double temperature;
            private int maxTokens;
            private List<String> stopSequences;
            private Map<String, Object> metadata;

            public Builder messages(List<SamplingMessage> messages) {
                this.messages = messages;
                return this;
            }

            public Builder modelPreferences(ModelPreferences modelPreferences) {
                this.modelPreferences = modelPreferences;
                return this;
            }

            public Builder systemPrompt(String systemPrompt) {
                this.systemPrompt = systemPrompt;
                return this;
            }

            public Builder includeContext(ContextInclusionStrategy includeContext) {
                this.includeContext = includeContext;
                return this;
            }

            public Builder temperature(Double temperature) {
                this.temperature = temperature;
                return this;
            }

            public Builder maxTokens(int maxTokens) {
                this.maxTokens = maxTokens;
                return this;
            }

            public Builder stopSequences(List<String> stopSequences) {
                this.stopSequences = stopSequences;
                return this;
            }

            public Builder metadata(Map<String, Object> metadata) {
                this.metadata = metadata;
                return this;
            }

            public CreateMessageRequest build() {
                return new CreateMessageRequest(messages, modelPreferences, systemPrompt,
                        includeContext, temperature, maxTokens, stopSequences, metadata);
            }

        }


    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CreateMessageResult {
        @JsonProperty("role")
        private Role role;
        @JsonProperty("content")
        private Content content;
        @JsonProperty("model")
        private String model;
        @JsonProperty("stopReason")
        private StopReason stopReason;

        public enum StopReason {
            @JsonProperty("endTurn") END_TURN,
            @JsonProperty("stopSequence") STOP_SEQUENCE,
            @JsonProperty("maxTokens") MAX_TOKENS
        }

        public CreateMessageResult(Role role, Content content, String model, StopReason stopReason) {
            this.role = role;
            this.content = content;
            this.model = model;
            this.stopReason = stopReason;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private Role role = Role.ASSISTANT;
            private Content content;
            private String model;
            private StopReason stopReason = StopReason.END_TURN;

            public Builder role(Role role) {
                this.role = role;
                return this;
            }

            public Builder content(Content content) {
                this.content = content;
                return this;
            }

            public Builder model(String model) {
                this.model = model;
                return this;
            }

            public Builder stopReason(StopReason stopReason) {
                this.stopReason = stopReason;
                return this;
            }

            public Builder message(String message) {
                this.content = new TextContent(message);
                return this;
            }

            public CreateMessageResult build() {
                return new CreateMessageResult(role, content, model, stopReason);
            }
        }

    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PaginatedRequest {
        @JsonProperty("cursor")
        private String cursor;

        public PaginatedRequest() {
        }

        public PaginatedRequest(String cursor) {
            this.cursor = cursor;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PaginatedResult {
        @JsonProperty("nextCursor")
        private String nextCursor;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ProgressNotification {
        @JsonProperty("progressToken")
        private String progressToken;

        @JsonProperty("progress")
        private double progress;

        @JsonProperty("total")
        private Double total;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class LoggingMessageNotification {
        @JsonProperty("level")
        private LoggingLevel level;
        @JsonProperty("logger")
        private String logger;
        @JsonProperty("data")
        private String data;

        public LoggingMessageNotification(LoggingLevel level, String logger, String data) {
            this.level = level;
            this.logger = logger;
            this.data = data;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private LoggingLevel level = LoggingLevel.INFO;
            private String logger = "server";
            private String data;

            public Builder level(LoggingLevel level) {
                this.level = level;
                return this;
            }

            public Builder logger(String logger) {
                this.logger = logger;
                return this;
            }

            public Builder data(String data) {
                this.data = data;
                return this;
            }

            public LoggingMessageNotification build() {
                return new LoggingMessageNotification(level, logger, data);
            }
        }


    }


    public enum LoggingLevel {
        @JsonProperty("debug") DEBUG(0),
        @JsonProperty("info") INFO(1),
        @JsonProperty("notice") NOTICE(2),
        @JsonProperty("warning") WARNING(3),
        @JsonProperty("error") ERROR(4),
        @JsonProperty("critical") CRITICAL(5),
        @JsonProperty("alert") ALERT(6),
        @JsonProperty("emergency") EMERGENCY(7);

        private final int level;

        LoggingLevel(int level) {
            this.level = level;
        }

        public int level() {
            return level;
        }

    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class SetLevelRequest {
        @JsonProperty("level")
        private LoggingLevel level;

        public SetLevelRequest(LoggingLevel level) {
            this.level = level;
        }
    }


    public interface CompleteReference {
        String type();

        String identifier();
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PromptReference implements CompleteReference {
        @JsonProperty("type")
        private String type;

        @JsonProperty("name")
        private String name;

        public PromptReference(String name) {
            this("ref/prompt", name);
        }

        public PromptReference(String type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String type() {
            return this.type;
        }

        @Override
        public String identifier() {
            return this.name;
        }

    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResourceReference implements CompleteReference {
        @JsonProperty("type")
        private String type;
        @JsonProperty("uri")
        private String uri;

        public ResourceReference(String uri) {
            this("ref/resource", uri);
        }

        public ResourceReference(String type, String uri) {
            this.type = type;
            this.uri = uri;
        }

        @Override
        public String type() {
            return this.type;
        }

        @Override
        public String identifier() {
            return this.uri;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CompleteRequest implements Request {
        @JsonProperty("ref")
        private CompleteReference ref;
        @JsonProperty("argument")
        private CompleteArgument argument;

        @Data
        public static class CompleteArgument {
            @JsonProperty("name")
            String name;
            @JsonProperty("value")
            String value;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CompleteResult {
        @JsonProperty("completion")
        private CompleteCompletion completion;

        @Data
        public static class CompleteCompletion {
            @JsonProperty("values")
            private List<String> values;

            @JsonProperty("total")
            private Integer total;

            @JsonProperty("hasMore")
            private Boolean hasMore;
        }
    }


    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({@JsonSubTypes.Type(value = TextContent.class, name = "text"),
            @JsonSubTypes.Type(value = ImageContent.class, name = "image"),
            @JsonSubTypes.Type(value = EmbeddedResource.class, name = "resource")})
    public interface Content {
        default String type() {
            if (this instanceof TextContent) {
                return "text";
            } else if (this instanceof ImageContent) {
                return "image";
            } else if (this instanceof EmbeddedResource) {
                return "resource";
            }
            throw new IllegalArgumentException("Unknown content type: " + this);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class TextContent implements Content {
        @JsonProperty("audience")
        private List<Role> audience;
        @JsonProperty("priority")
        private Double priority;
        @JsonProperty("text")
        private String text;

        public TextContent() {
        }

        public TextContent(String content) {
            this(null, null, content);
        }

        public TextContent(List<Role> audience, Double priority, String text) {
            this.audience = audience;
            this.priority = priority;
            this.text = text;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ImageContent implements Content {
        @JsonProperty("audience")
        private List<Role> audience;

        @JsonProperty("priority")
        private Double priority;

        @JsonProperty("data")
        private String data;

        @JsonProperty("mimeType")
        private String mimeType;

        public ImageContent() {

        }
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class EmbeddedResource implements Content {
        @JsonProperty("audience")
        private List<Role> audience;
        @JsonProperty("priority")
        private Double priority;
        @JsonProperty("resource")
        private ResourceContents resource;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Root {
        @JsonProperty("uri")
        private String uri;
        @JsonProperty("name")
        private String name;
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ListRootsResult {
        @JsonProperty("roots")
        private List<Root> roots;

        public ListRootsResult(List<Root> roots) {
            this.roots = roots;
        }
    }

}
