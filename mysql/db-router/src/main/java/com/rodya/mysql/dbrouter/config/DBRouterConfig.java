package com.rodya.mysql.dbrouter.config;

import org.springframework.stereotype.Component;

public class DBRouterConfig {

    private int dbCount;

    private int tbCount;

    private String routerKey;

    public DBRouterConfig(){
    }

    public DBRouterConfig(int dbCount, int tbCount, String routerKey){
        this.dbCount = dbCount;
        this.tbCount = tbCount;
        this.routerKey = routerKey;
    }

    public int getDbCount() {
        return dbCount;
    }
    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }
    public int getTbCount() {
        return tbCount;
    }
    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }
    public String getRouterKey() {
        return routerKey;
    }
    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }
}
