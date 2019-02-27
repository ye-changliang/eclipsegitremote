package com.tydic.bigdata.domain.hive;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "comm_hive_z_config")
public class HiveSet {
	   @Id
	   @GeneratedValue(strategy= GenerationType.IDENTITY)
	   @Column(name = "id")
       private Long id;//集群id

	/*   @Column(name = "clusterId")
       private Long clusterId;//集群id
*/	   
	   @Column(name = "clusterName")
       private String clusterName;//集群名称
	   
	  /* @Column(name = "hiveId")
       private Long hiveId;//连接id
*/	   
	   @Column(name = "hiveAddress")
	   private String hiveAddress;//连接地址
	   
	   @Column(name = "hiveName")
       private String hiveName;//连接分类
	   
	   @Column(name = "userName")
       private String userName;//租户名称
	   
	   @Column(name = "userPassword")
       private String userPassword;//租户密码
	   
	  @Column(name = "totalNum")
       private Integer totalNum;//连接告警级别
	   
	   @Column(name = "connectWarn")
       private String connectWarn;//连接告警级别
	   
	   @Column(name = "setTime")
	   @DateTimeFormat(pattern="yyyy-MM-dd")
       private Date setTime;//配置时间
   
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		/*public Long getClusterId() {
			return clusterId;
		}
	
		public void setClusterId(Long clusterId) {
			this.clusterId = clusterId;
		}*/
	
		public String getClusterName() {
			return clusterName;
		}
	
		public void setClusterName(String clusterName) {
			this.clusterName = clusterName;
		}
	
		/*public Long getHiveId() {
			return hiveId;
		}
	
		public void setHiveId(Long hiveId) {
			this.hiveId = hiveId;
		}
	*/
		public String getHiveAddress() {
			return hiveAddress;
		}
	
		public void setHiveAddress(String hiveAddress) {
			this.hiveAddress = hiveAddress;
		}
	
		public String getHiveName() {
			return hiveName;
		}
	
		public void setHiveName(String hiveName) {
			this.hiveName = hiveName;
		}
	
		public String getUserName() {
			return userName;
		}
	
		public void setUserName(String userName) {
			this.userName = userName;
		}
	
		public String getUserPassword() {
			return userPassword;
		}
	
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
		
		public Integer getTotalNum() {
			return totalNum;
		}
	
		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}
	
		public String getConnectWarn() {
			return connectWarn;
		}
	
		public void setConnectWarn(String connectWarn) {
			this.connectWarn = connectWarn;
		}
	
		public Date getSetTime() {
			return setTime;
		}
	
		public void setSetTime(Date setTime) {
			this.setTime = setTime;
		}
       
}
