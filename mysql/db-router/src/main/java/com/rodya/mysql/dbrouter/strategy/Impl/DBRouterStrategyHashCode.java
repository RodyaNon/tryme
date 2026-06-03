package com.rodya.mysql.dbrouter.strategy.Impl;

import com.rodya.mysql.dbrouter.config.DBRouterConfig;
import com.rodya.mysql.dbrouter.dynamic.DBContextHolder;
import com.rodya.mysql.dbrouter.strategy.IDBRouterStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * HashCode路由
 */
@Slf4j
public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private final DBRouterConfig dbRouterConfig;

    public DBRouterStrategyHashCode(DBRouterConfig dbRouterConfig) {
        this.dbRouterConfig = dbRouterConfig;
    }

    @Override
    public void doRouter(String dbKey){
        int size = dbRouterConfig.getDbCount() * dbRouterConfig.getTbCount();
        // 绕动函数
        int idx = (size - 1) & (dbKey.hashCode() ^ (dbKey.hashCode() >>> 16));

        int dbIdx = idx / dbRouterConfig.getTbCount() + 1;
        int tbIdx = idx - dbRouterConfig.getTbCount() * (dbIdx - 1);

        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));
        log.info("数据库路由，分库分表结果：dbIdx={},tbIdx={}", dbIdx, tbIdx);
    }

    @Override
    public void setDBKey(int dbIdx){
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
    }

    @Override
    public void setTableKey(int tbIdx){
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));
    }

    @Override
    public int dbCount(){
        return dbRouterConfig.getDbCount();
    }

    @Override
    public int tbCount(){
        return dbRouterConfig.getTbCount();
    }

    @Override
    public void clear(){
        DBContextHolder.clearDBKey();
        DBContextHolder.clearTBKey();
    }


}
