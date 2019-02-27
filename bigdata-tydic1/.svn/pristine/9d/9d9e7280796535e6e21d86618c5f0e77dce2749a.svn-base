package com.tydic.bigdata.dynamic.hive;

import com.tydic.bigdata.datasource.BaseDao;
import com.tydic.bigdata.dynamic.hive.bean.HiveConfig;
import com.tydic.bigdata.dynamic.hive.bean.UserBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
 * 统一的服务工具类
 */
public class ServiceUtils {
    /*
     * 通过密钥获取租户信息
     */
    public static UserBean getUserInfo(String authCode){
        List<UserBean> list = DaoUtils.getUserInfo(authCode);
        if(list==null || list.size()==0){
            return null;
        }else{
            return list.get(0);
        }
    }

    /*
     * 通过租户获取可访问的hive列表
     */
    public static List<HiveConfig> getHiveInfo(String userName) {
        return DaoUtils.getHiveConfig(userName);
    }

    /*
     * 获取当前租户下，负荷最小的hive名
     */
    public static String getMinHiveName(String userName) {
        return DaoUtils.getMinHiveName(userName);
    }

    /*
     * 通过hive对象获取jdbc操作对象
     */
    public static BaseDao getHiveDao(HiveConfig hive) {
        return DaoUtils.getHiveDao(hive);
    }

    /*
     * 执行Hive Sql方法
     * type:0-查询  1-更新
     */
    public static List<Map<String,String>> execute(int type, String sql, Object[] params, HiveConfig hive, BaseDao hiveDao) {
        String sqlId = uuid();//生成SQL_ID
        DaoUtils.addExeNum(sqlId,sql,hive);//执行前准备：记录要执行的SQL日志
        List<Map<String,String>> result = null;
        switch(type){
            case 0://查询
                result = hiveDao.getMap(sql,params);
                break;
            case 1://更新
                hiveDao.execute(sql,params);
                break;
            default :
                break;
        }
        DaoUtils.minusExeNum(sqlId,hive);//执行完毕：更新SQL执行完成的时间
        return result;
    }

    /*
     * 生成日志的主键，主键采用UUID
     */
    private static String uuid(){
        return UUID.randomUUID().toString();
    }

    //更新jdbc连接池的信息
    public static void updateMoniter(HiveConfig hc){
        try {
            BaseDao dao = getHiveDao(hc);
            if(dao!=null) {
                DaoUtils.updateMonitor(hc, dao.getConnectionNum());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
