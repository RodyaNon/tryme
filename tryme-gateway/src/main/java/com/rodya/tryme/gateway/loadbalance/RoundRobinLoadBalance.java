package com.rodya.tryme.gateway.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡策略
 */
@Component
public class RoundRobinLoadBalance implements LoadBalanceStrategy {
    
    private final AtomicInteger position = new AtomicInteger(0);
    
    // TODO: 实现轮询算法
    // 1. 按顺序选择服务实例
    // 2. 到达列表末尾后从头开始
    // 3. 考虑线程安全
    
    @Override
    public String choose(List<String> instances) {
        // TODO: 实现轮询选择逻辑
        return null;
    }
}
