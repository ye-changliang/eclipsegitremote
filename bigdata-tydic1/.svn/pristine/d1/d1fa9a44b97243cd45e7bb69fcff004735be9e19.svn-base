package com.tydic.bigdata.datasource;

public class Test {
    private static int hive1_num=0;
    private static int hive2_num=0;

    public Object executeHiveSql(String sql){
        BaseDao dao = null;
        if(hive1_num<hive2_num){
             dao = DbFactory.getBaseDao(DbFactory.TYPE.HIVE,"hive1");
        }else{
             dao = DbFactory.getBaseDao(DbFactory.TYPE.HIVE,"hive2");
        }
        dao.execute(sql);
        return null;
    }

}
