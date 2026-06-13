package com.rodya.tryme.gateway.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡策略
 */
@Component
public class RandomLoadBalance implements LoadBalanceStrategy {
    
    private final Random random = new Random();
    
    // TODO: 实现随机选择算法
    // 1. 从可用实例列表中随机选择一个
    // 2. 可以考虑权重随机
    
    @Override
    public String choose(List<String> instances) {
        // TODO: 实现随机选择逻辑
        return null;
    }
}
