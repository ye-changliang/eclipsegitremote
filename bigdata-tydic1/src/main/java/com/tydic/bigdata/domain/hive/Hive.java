package com.tydic.bigdata.domain.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.stringLiteralSequence_return;

@Entity
@Table(name = "comm_hive_resource")
public class Hive {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;//唯一识别id


	@Column(name = "pid")
	private String pid;//父级id
	
	@Column(name = "sid")
    private String sid;//子id
	
	@Column(name = "eName")
    private String eName;//英文名称

	@Column(name = "DeName")
	private String DeName;//中文描述
	
	@Column(name = "type")
	private int type;//类别 1 集群  2连接 3租户
	
 
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getDeName() {
		return DeName;
	}

	public void setDeName(String deName) {
		DeName = deName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
