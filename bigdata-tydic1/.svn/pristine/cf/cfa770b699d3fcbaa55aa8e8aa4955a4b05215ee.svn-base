package com.tydic.bigdata.domain.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comm_hive_ref")
public class HiveRef {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "userName")
	private String userName;//用户名称

	@Column(name = "hiveName")
	private String hiveName;//hive连接名称
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getHiveName() {
		return hiveName;
	}


	public void setHiveName(String hiveName) {
		this.hiveName = hiveName;
	}

	
	
}
