package com.tydic.bigdata.domain.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//日志监控管理表
@Table(name = "comm_hive_monitor")
public class HiveMonitor {
	@Id
	@Column(name = "id")
	private Long id;//集群id
	
	@Column(name = "clusterName")
    private String clusterName;//集群名称
    
    @Column(name = "hiveName")
    private String hiveName;//连接分类
    
    @Column(name = "totalNum")
    private Integer totalNum;//最大连接数
    
	@Column(name = "nowNum")
    private Integer nowNum;//当前连接数
    
    @Column(name = "idleNum")
    private Integer idleNum;//可用连接数
    
    
    public String getHiveName() {
		return hiveName;
	}
	public void setHiveName(String hiveName) {
		this.hiveName = hiveName;
	}
	public Integer getIdleNum() {
		return idleNum;
	}
	public void setIdleNum(Integer idleNum) {
		this.idleNum = idleNum;
	}


    public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}


	public Integer getNowNum() {
		return nowNum;
	}
	public void setNowNum(int nowNum) {
		this.nowNum = nowNum;
	}
}
