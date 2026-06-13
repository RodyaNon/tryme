package com.rodya.tryme.gateway.context;

import lombok.Data;

/**
 * 网关上下文 - 存储请求级别的共享数据
 */
@Data
public class GatewayContext {
    
    /**
     * 请求ID（用于链路追踪）
     */
    private String requestId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 请求开始时间
     */
    private Long startTime;
    
    /**
     * 匹配的路由ID
     */
    private String routeId;
    
    /**
     * 目标服务URL
     */
    private String targetUrl;
    
    /**
     * 自定义属性（用于在过滤器之间传递数据）
     */
    private Object attributes;
}
