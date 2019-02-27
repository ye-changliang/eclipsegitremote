package com.tydic.bigdata.domain.spark;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comm_sys_fkr_config")
public class Spark {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long sparkId;

    /**
     * 主机IP
     */
    @Column(name = "Ip")
    private String ip;

    /**
     * 应用名
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 主机服务id
     */
    @Column(name = "host_serve_id")
    private Long hostServeId;

    /**
     * 租户
     */
    @Column(name = "rent")
    private String rent;

    /**
     * 主机id
     */
    @Column(name = "host_id")
    private Long hostId;

    /**
     * spark配置文件内容
     */
    @Column(name = "config_content")
    private String sparkConfigContent;
    /**
     * spark配置名
     */
    @Column(name = "config_name")
    private String sparkConfig;
    /**
     * 配置文件路径
     */
    @Column(name = "config_path")
    private String sparkConfigPath;
    /**
     * 配置文件描述
     */
    @Column(name = "config_desc")
    private String sparkConfigDesc;
    /**
     * 修改时间
     */
    @Column(name = "Update_date")
    private Date updateDate;
    /**
     * 修改人4a工号
     */
    @Column(name = "Update_4a_code")
    private String update4aCode;

    /**
     * spark 状态
     */
    @Column(name = "status")
    private int status;

    /**
     *配置文件类型，设置spark
     */
    @Column(name = "config_type")
    private String configType="spark";


    public Long getSparkId() {
        return sparkId;
    }

    public void setSparkId(Long sparkId) {
        this.sparkId = sparkId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getHostServeId() {
        return hostServeId;
    }

    public void setHostServeId(Long hostServeId) {
        this.hostServeId = hostServeId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getSparkConfigContent() {
        return sparkConfigContent;
    }

    public void setSparkConfigContent(String sparkConfigContent) {
        this.sparkConfigContent = sparkConfigContent;
    }

    public String getSparkConfig() {
        return sparkConfig;
    }

    public void setSparkConfig(String sparkConfig) {
        this.sparkConfig = sparkConfig;
    }

    public String getSparkConfigPath() {
        return sparkConfigPath;
    }

    public void setSparkConfigPath(String sparkConfigPath) {
        this.sparkConfigPath = sparkConfigPath;
    }

    public String getSparkConfigDesc() {
        return sparkConfigDesc;
    }

    public void setSparkConfigDesc(String sparkConfigDesc) {
        this.sparkConfigDesc = sparkConfigDesc;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdate4aCode() {
        return update4aCode;
    }

    public void setUpdate4aCode(String update4aCode) {
        this.update4aCode = update4aCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}

