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
@Table(name = "comm_hive_register")
public class HiveReg {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;//租户id
	
	@Column(name = "loginId")
	private String loginId;//4a工号
	
	@Column(name ="clusterName")
	private String clusterName;
	
	@Column(name = "userName")
    private String userName;//租户名称
	
	@Column(name = "userKey")
    private String userKey;//租户密钥
	
	@Column(name = "regTime")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date regTime;//注册时间
	
	@Column(name = "startDate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;//生效时间
	
	@Column(name = "endDate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//失效时间
	
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
}
