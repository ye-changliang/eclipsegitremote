package com.tydic.bigdata.domain.kafka;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comm_sys_fkr_config")
public class KafKa {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long kafkaId;

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
     * flume配置文件
     */
    @Column(name = "config_content")
    private String kafkaConfigContent;
    /**
     * kafka配置名
     */
    @Column(name = "config_name")
    private String kafkaConfig;
    /**
     * 配置文件路径
     */
    @Column(name = "config_path")
    private String kafkaConfigPath;
    /**
     * 配置文件描述
     */
    @Column(name = "config_desc")
    private String kafkaConfigDesc;
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
     * kafka 状态
     */
    @Column(name = "status")
    private int status;

    /**
     *配置文件类型，设置kafka
     */
    @Column(name = "config_type")
    private String configType="kafka";



    public Long getKafkaId() {
        return kafkaId;
    }

    public void setKafkaId(Long kafkaId) {
        this.kafkaId = kafkaId;
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

    public String getKafkaConfigContent() {
        return kafkaConfigContent;
    }

    public void setKafkaConfigContent(String kafkaConfigContent) {
        this.kafkaConfigContent = kafkaConfigContent;
    }

    public String getKafkaConfig() {
        return kafkaConfig;
    }

    public void setKafkaConfig(String kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public String getKafkaConfigPath() {
        return kafkaConfigPath;
    }

    public void setKafkaConfigPath(String kafkaConfigPath) {
        this.kafkaConfigPath = kafkaConfigPath;
    }

    public String getKafkaConfigDesc() {
        return kafkaConfigDesc;
    }

    public void setKafkaConfigDesc(String kafkaConfigDesc) {
        this.kafkaConfigDesc = kafkaConfigDesc;
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


}
