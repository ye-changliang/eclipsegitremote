package com.tydic.bigdata.domain.redis;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comm_sys_fkr_config")
public class Redis {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long redisId;

    /**
     * 主机IP
     */
    @Column(name = "Ip")
    private String ip;


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
     * redis配置文件
     */
    @Column(name = "config_content")
    private String redisConfigContent;
    /**
     * redis配置名
     */
    @Column(name = "config_name")
    private String redisConfig;
    /**
     * 配置文件路径
     */
    @Column(name = "config_path")
    private String redisConfigPath;
    /**
     * 配置文件描述
     */
    @Column(name = "config_desc")
    private String redisConfigDesc;
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
     * redis 状态
     */
    @Column(name = "status")
    private int status;

    /**
     *配置文件类型，设置redis
     */
    @Column(name = "config_type")
    private String configType="redis";

    /**
     *配置文件机构名
     */
    @Column(name = "config_org")
    private String org;

    public Long getRedisId() {
        return redisId;
    }

    public void setRedisId(Long redisId) {
        this.redisId = redisId;
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

    public String getRedisConfigContent() {
        return redisConfigContent;
    }

    public void setRedisConfigContent(String redisConfigContent) {
        this.redisConfigContent = redisConfigContent;
    }

    public String getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(String redisConfig) {
        this.redisConfig = redisConfig;
    }

    public String getRedisConfigPath() {
        return redisConfigPath;
    }

    public void setRedisConfigPath(String redisConfigPath) {
        this.redisConfigPath = redisConfigPath;
    }

    public String getRedisConfigDesc() {
        return redisConfigDesc;
    }

    public void setRedisConfigDesc(String redisConfigDesc) {
        this.redisConfigDesc = redisConfigDesc;
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

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
