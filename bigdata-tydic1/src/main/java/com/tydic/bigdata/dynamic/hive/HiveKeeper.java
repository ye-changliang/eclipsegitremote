package com.tydic.bigdata.dynamic.hive;

import com.tydic.bigdata.datasource.BaseDao;
import com.tydic.bigdata.dynamic.hive.bean.HiveConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
/*
 * 该类是个单例模式
 * 功能1：缓存hive的链接配置，避免每次都从mysql查询
 */
public class HiveKeeper {
    private static Logger log = Logger.getLogger(HiveKeeper.class.getName());
    private static Map<String,HiveConfig> HIVE_CONFIG = new HashMap<String,HiveConfig>();
    private static String USERNAME;

    static{
        /*
         * 启动一个后台监听线程，每10秒将当前hive连接池的状态更新到Mysql
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    for (Map.Entry <String, HiveConfig> e : HiveKeeper.HIVE_CONFIG.entrySet()) {
                        HiveConfig hc = e.getValue();
                        ServiceUtils.updateMoniter(hc);
                    }
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //将Mysql中hive的连接信息载入内存
    private void init() {
        List<HiveConfig> infos = ServiceUtils.getHiveInfo(HiveKeeper.USERNAME);
        for(HiveConfig h : infos){
            h.setAuthNo(HiveKeeper.USERNAME);
            HIVE_CONFIG.put(h.getHiveName(),h);
        }
    }

    //获取可用的hive dao
    public BaseDao getAvailableDao(HiveConfig h) {
        return ServiceUtils.getHiveDao(h);
    }
    //随机获取一个hive
    public BaseDao getAvailableDao() {
        HiveConfig h = null;
        for(Map.Entry<String,HiveConfig> e : HIVE_CONFIG.entrySet()){
            h = e.getValue();
            break;
        }
        return ServiceUtils.getHiveDao(h);
    }

    //根据负载均衡，获取可用的hive连接
    public HiveConfig getAvailableHiveConfig(String userName) {
        String hiveName = ServiceUtils.getMinHiveName(userName);//获取当前连接数最小的hiveName
        HiveConfig hive = HIVE_CONFIG.get(hiveName);//从缓存获取对应的hive对象
        if(hive==null){
            return null;
        }
        return hive;
    }

    public HiveKeeper(String userName){
        HiveKeeper.USERNAME = userName;
        this.init();
    }
}
