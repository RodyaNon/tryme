package com.rodya.mysql.dbrouter.config;

import com.rodya.mysql.dbrouter.DBRouterJoinPoint;
import com.rodya.mysql.dbrouter.dynamic.DynamicDataSource;
import com.rodya.mysql.dbrouter.strategy.IDBRouterStrategy;
import com.rodya.mysql.dbrouter.strategy.Impl.DBRouterStrategyHashCode;
import com.rodya.mysql.dbrouter.util.PropertyUtil;
import com.rodya.mysql.dbrouter.util.StringUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.*;

@Configuration
public class DataSourceAutoConfig implements EnvironmentAware {

    private String prefix = "rodya-db-router.jdbc.datasource";

    private static final String TAG_GLOBAL = "global";

    private static final String TAG_POOL = "pool";

    private final Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    private int dbCount;

    private int tbCount;

    private String routerKey;

    /**
     * 获取数据源配置
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        dbCount = Integer.parseInt(Objects.requireNonNull(environment.getProperty(prefix + ".dbCount")));
        tbCount = Integer.parseInt(Objects.requireNonNull(environment.getProperty(prefix + ".tbCount")));
        routerKey = environment.getProperty(prefix + ".routerKey");
        String dataSources = environment.getProperty(prefix + ".list");
        // 全局配置
        Map<String, Object> globalInfo = getGlobalProps(environment, prefix + '.'+ TAG_GLOBAL);
        for (String dbInfo : dataSources.split(",")) {
            final String dbPrefix = prefix + '.' + dbInfo;
            Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, dbPrefix, Map.class);
            injectGlobal(dataSourceProps, globalInfo);
            dataSourceMap.put(dbInfo, dataSourceProps);
        }
    }

    /**
     * 创建数据源
     * @return
     */
    @Bean("mysqlDataSource")
    public DataSource createDataSource() {
        Map<Object,Object> targetDataSources = new HashMap<>();
        for (String dbInfo : dataSourceMap.keySet()) {
            Map<String, Object> objMap = dataSourceMap.get(dbInfo);
            DataSource dataSource = createDataSource(objMap);
            targetDataSources.put(dbInfo, dataSource);
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    private DataSource createDataSource(Map<String, Object> dataSourceProps) {
        try {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.setUrl(dataSourceProps.get("url").toString());
            dataSourceProperties.setUsername(dataSourceProps.get("username").toString());
            dataSourceProperties.setPassword(dataSourceProps.get("password").toString());

            String driverClassName = dataSourceProps.get("driver-class-name").toString();
            if (driverClassName != null) {
                dataSourceProperties.setDriverClassName(driverClassName);
            }

            String typeClassName = dataSourceProps.get("type-class-name") == null ? "com.zaxxer.hikari.HikariDataSource" : dataSourceProps.get("type-class-name").toString();
            DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type((Class<DataSource>) Class.forName(typeClassName)).build();

            MetaObject metaObject = SystemMetaObject.forObject(dataSource);
            Map<String, Object> poolConfig = (Map<String, Object>) (dataSourceProps.containsKey(TAG_POOL) ? dataSourceProps.get(TAG_POOL) : Collections.EMPTY_MAP);
            for (Map.Entry<String, Object> entry : poolConfig.entrySet()) {
                String key = StringUtils.middleScoreToCamelCase(entry.getKey());
                if(metaObject.hasSetter(key)){
                    metaObject.setValue(key, entry.getValue());
                }
            }
            return dataSource;
        } catch (ClassNotFoundException e){
            throw new IllegalArgumentException("数据源类型不存在",e);
        }
    }

    /**
     * 分库分表切面,统一写在配置类,适合管理
     * @return
     */
    @Bean(name = "db-router-point")
    @ConditionalOnMissingBean
    public DBRouterJoinPoint point(DBRouterConfig dbRouterConfig, IDBRouterStrategy dbRouterStrategy) {
        return new DBRouterJoinPoint(dbRouterConfig, dbRouterStrategy);
    }

    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount, routerKey);
    }
    // todo 哈希策略
    @Bean
    public IDBRouterStrategy dbRouterStrategy(DBRouterConfig dbRouterConfig) {
        return new DBRouterStrategyHashCode(dbRouterConfig);
    }

    private Map<String, Object> getGlobalProps(Environment environment, String prefix) {
        try{
            return PropertyUtil.handle(environment, prefix, Map.class);
        } catch (Exception e){
            return Collections.EMPTY_MAP;
        }
    }

    // todo 嵌套关系
    private void injectGlobal(Map<String, Object> dataSourceProps, Map<String, Object> globalInfo) {
        for (String key : globalInfo.keySet()) {
            if (!dataSourceProps.containsKey(key)) {
                dataSourceProps.put(key, globalInfo.get(key));
            }else if(dataSourceProps.get(key) instanceof Map){
                injectGlobal((Map<String, Object>) dataSourceProps.get(key), (Map<String, Object>) globalInfo.get(key));
            }
        }
    }


}
