package com.tydic.bigdata.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;


/**
 * 使用环境：
 * 1.@MappedSuperclass注解使用在父类上面，是用来标识父类的
 * <p>
 * 2.@MappedSuperclass标识的类表示其不能映射到数据库表，因为其不是一个完整的实体类，但是它所拥有的属性能够隐射在其子类对用的数据库表中
 * <p>
 * 3.@MappedSuperclass标识得嘞不能再有@Entity或@Table注解
 * @author tao
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(updatable = false, nullable = false)
    private Instant createTime;

    @Column(nullable = false)
    @JsonIgnore
    private Instant updateTime;

    @PrePersist
    public void prePersist() {
        createTime = updateTime = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateTime = Instant.now();
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}