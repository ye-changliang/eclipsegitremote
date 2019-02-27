package com.tydic.bigdata.domain.taskconfig;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;

/**
 * @desc
 */
@Entity
@Table(name = "comm_sys_task_record")
public class TaskInfo {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "record_Id")
    private Long id;
    /**
     * 名称
     */
    @Column(name = "task_Name")
    private String name;

    /**
     * 名称
     */
    @Column(name = "task_Id")
    private String taskId;

    /**
     * taskCode
     */
    @Column(name = "create_Date")
    private String createDate;

    /**
     * 开发人员
     */
    @Column(name = "develop_User")
    private String developUser;

    /**
     * 申请时间
     */
    @Column(name = "apply_Time")
    private Instant applyTime;


    /**
     * 申请状态
     */
    @Column(name = "apply_Status")
    private Integer applyStatus;
    /**
     * 申请状态
     */
    @Column(name = "apply_user")
    private String applyUser;

    /**
     * 审核人
     */
    @Column(name = "audit_User")
    private String auditUser;


    /**
     * 审核时间
     */
    @Column(name = "audit_Time")
    private Instant auditTime;


    /**
     * 发布人
     */
    @Column(name = "release_User")
    private String releaseUser;


    /**
     * 发布时间
     */
    @Column(name = "release_Time")
    private Instant releaseTime;

    /**
     * 发布状态
     */
    @Column(name = "release_Status")
    private Integer releaseStatus;

    /**
     * 是否删除状态
     */
    @Column(name = "delete_Status")
    private Integer deleteStatus;

    /**
     * 任务类型
     */
    @Column(name = "task_Type")
    private Integer taskType;

    /**
     * 运行状态
     */
    @Transient
    private transient Map jobStatus;


    public Map getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Map jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDevelopUser() {
        return developUser;
    }

    public void setDevelopUser(String developUser) {
        this.developUser = developUser;
    }

    public Instant getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Instant applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Instant getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Instant auditTime) {
        this.auditTime = auditTime;
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }

    public Instant getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Instant releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
