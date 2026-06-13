package com.rodya.dbrouter.dynamic;

public class DBContextHolder {
    private static final ThreadLocal<String> dbkey = new ThreadLocal<>();
    private static final ThreadLocal<String> tbkey = new ThreadLocal<>();
    public static void setDBKey(String dbKey) {
        dbkey.set(dbKey);
    }
    public static String getDBKey() {
        return dbkey.get();
    }
    public static void setTBKey(String tbKey) {
        tbkey.set(tbKey);
    }
    public static String getTBKey() {
        return tbkey.get();
    }
    public static void clearDBKey() {
        dbkey.remove();
    }
    public static void clearTBKey() {
        tbkey.remove();
    }
}
