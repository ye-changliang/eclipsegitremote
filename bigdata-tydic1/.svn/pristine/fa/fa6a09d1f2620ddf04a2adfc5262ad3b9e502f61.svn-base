package com.tydic.bigdata.domain.hive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
//日志监控管理表
@Table(name = "comm_hive_logtask")
public class HiveTask {
       
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;//集群id
	
	@Column(name = "taskName")
    private String taskName;//集群名称
	
	@Column(name = "scriptsql")
    private String scriptsql;//集群名称
	
	@Column(name = "startTime")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	
	@Column(name = "endTime")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;

	@Column(name = "clusterName")
	private String clusterName;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "hiveName")
	private String hiveName;
    
	@Column(name = "state")
	private Integer state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getScriptsql() {
		return scriptsql;
	}

	public void setScriptsql(String scriptsql) {
		this.scriptsql = scriptsql;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getHiveName() {
		return hiveName;
	}

	public void setHiveName(String hiveName) {
		this.hiveName = hiveName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		state = state;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
