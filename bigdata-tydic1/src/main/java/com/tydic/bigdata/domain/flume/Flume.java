package com.tydic.bigdata.domain.flume;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comm_sys_fkr_config")
public class Flume {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long flumeId;

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
     * 主机id
     */
    @Column(name = "host_id")
    private Long hostId;

    /**
     * flume配置文件
     */
    @Column(name = "config_content")
    private String flumeConfigContent;
    /**
     * flume配置名
     */
    @Column(name = "config_name")
    private String flumeConfig;
    /**
     * 配置文件路径
     */
    @Column(name = "config_path")
    private String flumeConfigPath;
    /**
     * 配置文件描述
     */
    @Column(name = "config_desc")
    private String flumeConfigDesc;
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
     * flume 状态
     */
    @Column(name = "status")
    private int status;

    /**
     *配置文件类型，设置flume
     */
    @Column(name = "config_type")
    private String configType="flume";

    /**
     *配置文件机构名
     */
    @Column(name = "config_org")
    private String org;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getFlumeId() {
        return flumeId;
    }

    public void setFlumeId(Long flumeId) {
        this.flumeId = flumeId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFlumeConfig() {
        return flumeConfig;
    }

    public void setFlumeConfig(String flumeConfig) {
        this.flumeConfig = flumeConfig;
    }

    public String getFlumeConfigPath() {
        return flumeConfigPath;
    }

    public void setFlumeConfigPath(String flumeConfigPath) {
        this.flumeConfigPath = flumeConfigPath;
    }

    public String getFlumeConfigDesc() {
        return flumeConfigDesc;
    }

    public void setFlumeConfigDesc(String flumeConfigDesc) {
        this.flumeConfigDesc = flumeConfigDesc;
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

    public String getFlumeConfigContent() {
        return flumeConfigContent;
    }

    public void setFlumeConfigContent(String flumeConfigContent) {
        this.flumeConfigContent = flumeConfigContent;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
