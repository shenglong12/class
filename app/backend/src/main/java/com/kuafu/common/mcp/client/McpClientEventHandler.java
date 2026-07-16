package com.kuafu.common.mcp.client;

import com.kuafu.common.mcp.spec.McpSchema;

public interface McpClientEventHandler {

    void event(McpSchema.JSONRPCMessage message);
}
