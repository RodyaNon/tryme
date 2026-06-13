package com.rodya.tryme.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 网关配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperties {
    
    /**
     * 路由规则列表
     */
    private List<RouteDefinition> routes;
    
    /**
     * 全局过滤器配置
     */
    private Map<String, Object> globalFilters;
    
    /**
     * 默认超时时间（毫秒）
     */
    private Long defaultTimeout = 30000L;
    
    /**
     * 是否启用负载均衡
     */
    private Boolean enableLoadBalance = true;
    
    @Data
    public static class RouteDefinition {
        /**
         * 路由ID
         */
        private String id;
        
        /**
         * 匹配路径
         */
        private String path;
        
        /**
         * 目标服务URL
         */
        private String targetUrl;
        
        /**
         * 服务名称（用于服务发现）
         */
        private String serviceName;
        
        /**
         * 是否启用
         */
        private Boolean enabled = true;
        
        /**
         * 路由级别过滤器
         */
        private List<FilterConfig> filters;
        
        /**
         * 重试次数
         */
        private Integer retryCount = 0;
    }
    
    @Data
    public static class FilterConfig {
        /**
         * 过滤器名称
         */
        private String name;
        
        /**
         * 过滤器参数
         */
        private Map<String, Object> args;
    }
}
