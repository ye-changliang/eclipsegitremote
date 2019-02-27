package com.tydic.bigdata.dynamic.hive;

import com.tydic.bigdata.datasource.BaseDao;
import com.tydic.bigdata.datasource.DbFactory;
import com.tydic.bigdata.dynamic.hive.bean.HiveConfig;
import com.tydic.bigdata.dynamic.hive.bean.UserBean;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/*
 *统一的MYSQL操作工具类
 */
public class DaoUtils {
    private static Logger log = Logger.getLogger(DaoUtils.class.getName());
    private static String hiveDriver = "org.apache.hive.jdbc.HiveDriver";
    //获取mysql数据连接
    private static BaseDao getConfigBaseDao(){
        return DbFactory.getBaseDao(DbFactory.TYPE.MYSQL,"eom");
    }

    //验证授权码是否有效
    public static List<UserBean> getUserInfo(String userKey){
        List<UserBean> list = getConfigBaseDao().getObject("select loginId,userName,userKey,startDate,endDate from comm_hive_register where userKey=? and startDate<now() and endDate>now() limit 1",new String[]{userKey},UserBean.class);
        return list;
    }


    //查询数据库中配置的hive数据源
    public static List<HiveConfig> getHiveConfig(String userName){
        return getConfigBaseDao().getObject("select b.id,b.clusterName,b.hiveName,b.hiveAddress,b.userName,b.userPassword,b.totalNum,b.connectWarn from comm_hive_register a,comm_hive_z_config b where a.userName=? and a.clusterName=b.clusterName",new String[]{userName},HiveConfig.class);
    }

    //获取执行数量最小的hive
    public static String getMinHiveName(String userName){
        return getConfigBaseDao().getString("select x.hiveName from (select a.hiveName,sum(a.nowNum) nowNum from comm_hive_monitor a,comm_hive_z_config b,comm_hive_register c where c.userName=? and c.clusterName=b.clusterName and b.hiveName=a.hiveName group by a.hiveName) x order by x.nowNum asc limit 1",new String[]{userName});
    }

    /*
     * 创建数据连接池
     * 1、如果当前Hive连接数充足，则直接按照properties配置maxsize和minsize来创建连接池
     * 2、如果当前Hive连接数不充足，则maxsize=剩余可用连接数，minsize=1
     */
    public static BaseDao getHiveDao(HiveConfig hive) {
        int maxSize = Integer.parseInt(CommUtils.getVal("hive.pool.maxsize"));//获取配置的maxsize
        int minSize = Integer.parseInt(CommUtils.getVal("hive.pool.minsize"));//获取配置的minsize
        //获取当前可用的连接数
        int totalNum = getConfigBaseDao().getInt("select sum(totalNum) from comm_hive_monitor where hiveName=?",new String[]{hive.getHiveName()});

        //根据当前可用连接数来创建连接池
        if(hive.getAvalibleConnectNum()>totalNum){
            if(hive.getAvalibleConnectNum()-totalNum>maxSize) {
                return DbFactory.getBaseDao(hive.getHiveName(), hive.getJdbcUrl(), hive.getUserName(), hive.getUserPassword(), hiveDriver,maxSize,minSize);
            }else{
                return DbFactory.getBaseDao(hive.getHiveName(), hive.getJdbcUrl(), hive.getUserName(), hive.getUserPassword(), hiveDriver,hive.getAvalibleConnectNum()-totalNum,1);
            }
        }else{
            return null;
        }
    }

    //hive执行数+1，记录执行sql
    public synchronized static void addExeNum(String id,String sql,HiveConfig hive) {
        //getConfigBaseDao().execute("update comm_hive_config set exe_num=exe_num+1 where id=? limit 1",new String[]{hive.getId()+""});
        getConfigBaseDao().execute("insert into comm_hive_logtask(id,scriptsql,userName,clusterName,hiveName,startTime,state) values(?,?,?,?,?,now(),1)",new String[]{id,sql,hive.getAuthNo(),hive.getClusterName(),hive.getHiveName()});
    }

    //hive执行数-1，更新sql执行结束时间
    public synchronized static void minusExeNum(String id,HiveConfig hive) {
        getConfigBaseDao().execute("update comm_hive_logtask set endTime=now(),state=0 where id=? limit 1",new String[]{id});
        //getConfigBaseDao().execute("update comm_hive_config set exe_num=exe_num-1 where id=? and exe_num>0 limit 1",new String[]{hive.getId()+""});
    }

    public static void updateMonitor(HiveConfig hc, int[] connectionNum) {
        getConfigBaseDao().execute("insert into comm_hive_monitor(clusterName,hiveName,userName,totalNum,nowNum,idleNum) " +
                "values (?,?,?,?,?,?) on duplicate key update totalNum=values(totalNum),nowNum=values(nowNum),idleNum=values(idleNum)",new String[]{hc.getClusterName(),hc.getHiveName(),hc.getAuthNo(),connectionNum[2]+"",connectionNum[0]+"",connectionNum[1]+""});
    }
}
