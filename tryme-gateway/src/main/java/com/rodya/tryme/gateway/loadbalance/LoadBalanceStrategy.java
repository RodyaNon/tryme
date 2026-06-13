package com.rodya.tryme.gateway.loadbalance;

import java.util.List;

/**
 * 负载均衡策略接口
 */
public interface LoadBalanceStrategy {
    
    /**
     * 选择一个服务实例
     * 
     * @param instances 可用的服务实例列表
     * @return 选中的服务实例URL
     */
    String choose(List<String> instances);
}
