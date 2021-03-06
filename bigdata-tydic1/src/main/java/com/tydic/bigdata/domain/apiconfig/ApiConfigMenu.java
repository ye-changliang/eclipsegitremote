package com.tydic.bigdata.domain.apiconfig;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * @desc api 配置表
 */
@Entity
@Table(name = "comm_api_config")
public class ApiConfigMenu implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * 接口名称
     */
    @Column(name = "api_name")
    private String apiName;


    /**
     * 接口编号
     */
    @Column(name = "api_code")
    private String apiCode;

    /**
     * 接口方式
     */
    @Column(name = "api_way")
    private Integer apiWay;

    /**
     * 接口类型
     */
    @Column(name = "api_type")
    private Integer apiType;

    /**
     * 接口url
     */
    @Column(name = "api_url")
    private String apiUrl;

    /**
     * 接口出参
     */
    @Column(name = "api_out_params")
    private String apiOutParams;

    /**
     * 接口入参
     */
    @Column(name = "api_in_params")
    private String apiInParams;
    /**
     * 输出数据分隔符
     */
    @Column(name = "data_split")
    private Integer dataSplit;

    /**
     * kafka 编号
     */
    @Column(name = "kafka_code")
    private String kafkaCode;

    /**
     * kafka topic
     */
    @Column(name = "kafaka_topic")
    private String kafkaTopic;
    /**
     * 是否可用
     */
    @Column(name = "status")
    private Integer status;


    /**
     * 更新时间
     */
    @Column(nullable = false, name = "update_time")
    private Instant updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_user")
    private String updateUser;


    /**
     * 新增操作
     */
    @PrePersist
    public void prePersist() {
        updateTime = Instant.now();
        updateUser = "test";
        status = 1;
        apiCode = UUID.randomUUID().toString();
    }

    /**
     * 更新操作  更新时间
     */
    @PreUpdate
    public void preUpdate() {
        updateTime = Instant.now();
        updateUser = "testUpdate";
        status = 1;
    }


    /**
     * get set 方法
     */


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public Integer getApiWay() {
        return apiWay;
    }

    public void setApiWay(Integer apiWay) {
        this.apiWay = apiWay;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiOutParams() {
        return apiOutParams;
    }

    public void setApiOutParams(String apiOutParams) {
        this.apiOutParams = apiOutParams;
    }

    public String getApiInParams() {
        return apiInParams;
    }

    public void setApiInParams(String apiInParams) {
        this.apiInParams = apiInParams;
    }

    public Integer getDataSplit() {
        return dataSplit;
    }

    public void setDataSplit(Integer dataSplit) {
        this.dataSplit = dataSplit;
    }

    public String getKafkaCode() {
        return kafkaCode;
    }

    public void setKafkaCode(String kafkaCode) {
        this.kafkaCode = kafkaCode;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
