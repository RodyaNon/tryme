package com.rodya.dbrouter.strategy;

public interface IDBRouterStrategy {

    void doRouter(String dbKey);

    void setDBKey(int dbInx);

    void setTableKey(int tbInx);

    int dbCount();

    int tbCount();

    void clear();
}
