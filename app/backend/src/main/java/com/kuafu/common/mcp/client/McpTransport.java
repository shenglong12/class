package com.kuafu.common.mcp.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kuafu.common.mcp.spec.McpSchema;

public interface McpTransport {

    void connect(McpClientEventHandler handler);

    boolean sendMessage(McpSchema.JSONRPCMessage message);


    <T> T unmarshalFrom(Object data, TypeReference<T> typeRef);
}
