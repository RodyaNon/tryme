package com.rodya.tryme.gateway.controller;

import com.rodya.tryme.gateway.handler.GatewayHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关主控制器 - 拦截所有请求并进行路由转发
 */
@RestController
public class GatewayController {
    
    private final GatewayHandler gatewayHandler;
    
    public GatewayController(GatewayHandler gatewayHandler) {
        this.gatewayHandler = gatewayHandler;
    }
    
    /**
     * 处理所有请求
     * 
     * @param exchange 服务器交换对象
     * @return 处理结果
     */
    @RequestMapping("/**")
    public Mono<Void> proxy(ServerWebExchange exchange) {
        // TODO: 可以在这里添加预处理逻辑
        return gatewayHandler.handle(exchange)
                .onErrorResume(e -> {
                    // TODO: 统一异常处理
                    // 1. 记录错误日志
                    // 2. 返回适当的错误响应
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }
}
