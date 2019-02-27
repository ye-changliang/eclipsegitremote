package com.tydic.bigdata.dynamic.hive.bean;
/*
 * 实体bean
 * 对应Mysql中的Hive配置
 */
public class HiveConfig {
    private Integer id;
    private String clusterName;
    private String hiveName;
    private String hiveAddress;
    private String userName;
    private String userPassword;
    private Integer totalNum;
    private Double connectWarn;
    private String authNo;

    public Integer getAvalibleConnectNum(){
        return (int)Math.floor(this.getTotalNum()*this.getConnectWarn());
    }

    public String getJdbcUrl(){
        return "jdbc:hive2://"+this.getHiveAddress();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHiveName() {
        return hiveName;
    }

    public void setHiveName(String hiveName) {
        this.hiveName = hiveName;
    }

    public String getHiveAddress() {
        return hiveAddress;
    }

    public void setHiveAddress(String hiveAddress) {
        this.hiveAddress = hiveAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Double getConnectWarn() {
        return connectWarn;
    }

    public void setConnectWarn(Double connectWarn) {
        this.connectWarn = connectWarn;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }
}
