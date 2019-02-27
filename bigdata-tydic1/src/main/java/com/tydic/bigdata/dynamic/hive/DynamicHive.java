package com.tydic.bigdata.dynamic.hive;

import com.tydic.bigdata.datasource.BaseDao;
import com.tydic.bigdata.dynamic.hive.bean.HiveConfig;
import com.tydic.bigdata.dynamic.hive.bean.UserBean;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/*
 * 提供hive动态链接的功能
 */
public class DynamicHive {
    private static Logger log = Logger.getLogger(DynamicHive.class.getName());
    public static int UPDATE = 1;//插入、更新、删除操作
    public static int SELECT = 0;//查询操作

    private UserBean user;
    private String authCode;
    private boolean isValid;
    private HiveKeeper hk;
    private String updateDateStr;


    public DynamicHive(String authCode){
        this.setAuthCode(authCode);
        this.validateAuthCode();
        if(this.isValid()==true){
            this.setHk(new HiveKeeper(this.getUser().getUserName()));
        }
    }

    // 验证authCode是否有效
    private void validateAuthCode() {
        this.setUser(ServiceUtils.getUserInfo(this.getAuthCode()));
        if(this.getUser()==null){
            this.setValid(false);
        }else{
            this.setValid(true);
        }
        this.setUpdateDateStr(DateFormatUtils.format(new Date(),"yyyyMMdd"));//记录验证的时间戳
    }

    //执行HQL语句
    public List<Map<String,String>> execute(int type, String sql, Object[] params){
        if(DateFormatUtils.format(new Date(),"yyyyMMdd").equals(this.getUpdateDateStr())==false){//每天验证一次，如果当前时间与时间戳不匹配，则发起验证
            this.validateAuthCode();
        }
        if(this.isValid()==true) {
            HiveConfig hive = this.getHk().getAvailableHiveConfig(this.getUser().getUserName());//获得一个当前可用的hive信息
            //开始通过hive对象获取jdbc连接
            BaseDao hiveDao = null;
            if(hive==null){//mysql还没有该租户的hive连接数信息
                hiveDao = this.getHk().getAvailableDao();//随机获取一个hive的jdbc
            }else {
                hiveDao = this.getHk().getAvailableDao(hive);//获取该hive的jdbc
            }
            if(hiveDao==null){//如果当前无可用的jdbc
                log.info("data link's number is full");
                return null;
            }

            //执行对应的hive sql
            List <Map <String, String>> result = ServiceUtils.execute(type, sql, params, hive, hiveDao);
            return result;
        }else{
            log.info("no authorized:"+this.getAuthCode());
            return null;
        }
    }

    public List<Map<String,String>> execute(int type,String sql){
        return execute(type,sql,null);
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public HiveKeeper getHk() {
        return hk;
    }

    public void setHk(HiveKeeper hk) {
        this.hk = hk;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
