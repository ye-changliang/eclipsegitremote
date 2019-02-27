package com.tydic.bigdata.domain.apiconfig;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comm_real_api_kafka_prop")
public class ApiKafKaConfig {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * 生产者编号
     */
    @Column(name = "producer_code")
    private String kafkaCode;

    /**
     * key
     */
    @Column(name = "`key`")
    private String key;

    /**
     * value
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 是否可用
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 更新操作
     */

    @PrePersist
    public void prePersist() {
        status = 1;
    }

    @PreUpdate
    public void preUpdate() {
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

    public String getKafkaCode() {
        return kafkaCode;
    }

    public void setKafkaCode(String kafkaCode) {
        this.kafkaCode = kafkaCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
