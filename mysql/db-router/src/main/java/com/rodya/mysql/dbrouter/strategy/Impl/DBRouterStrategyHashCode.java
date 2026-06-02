package com.rodya.mysql.dbrouter.strategy.Impl;

import com.rodya.mysql.dbrouter.config.DBRouterConfig;
import com.rodya.mysql.dbrouter.strategy.IDBRouterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 路由策略
 */
@Slf4j
public class DBRouterStrategyHashCode implements IDBRouterStrategy {
    private final DBRouterConfig dbRouterConfig;

    public DBRouterStrategyHashCode(DBRouterConfig dbRouterConfig) {
        this.dbRouterConfig = dbRouterConfig;
    }

}
