package com.tydic.bigdata.datasource;

import java.util.ResourceBundle;

public class DbFactory {
    public static BaseDao getBaseDao(String type, String db){
        ResourceBundle resource = ResourceBundle.getBundle("db-conf");
        BaseDao dao = getBaseDao(db,resource.getString("db."+type+"."+db+".url"),
                resource.getString("db."+type+"."+db+".usr"),
                resource.getString("db."+type+"."+db+".pwd"),
                resource.getString("db."+type+".driver"));
        return dao;
    }

    public static  BaseDao getBaseDao(String dataSourceName, String url, String usr, String pwd, String driver,Integer maxIdle,Integer minIdle){
        DataSource db = new DataSource();
        db.setDataSourceName(dataSourceName);
        db.setUrl(url);
        db.setUsername(usr);
        db.setPwd(pwd);
        db.setDriver(driver);
        db.setMaxIdle(maxIdle);
        db.setMinIdle(minIdle);
        try {
            BaseDao dao = JdbcTemplateDao.getBaseDao(db);
            return dao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  BaseDao getBaseDao(String dataSourceName, String url, String usr, String pwd, String driver){
        return getBaseDao(dataSourceName,url,usr,pwd,driver,20,5);
    }

    public static class TYPE {
        public static String MYSQL = "mysql";
        public static String ORACLE = "oracle";
        public static String HIVE = "hive";
    }
}
