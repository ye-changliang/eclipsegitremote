package com.tydic.bigdata.domain.hostInfo;

import javax.persistence.*;

/**
 * 主机实体
 */
@Entity
@Table(name = "comm_sys_host_info")
public class HostInfo {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "HOST_ID")
    private Long hostId;

    /**
     * 主机
     */
    @Column(name = "HOST")
    private String host;

    /**
     * 用户名
     */
    @Column(name = "USERNAME")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String passWord;

    /**
     * 端口
     */
    @Column(name = "POST")
    private String post;

    /**
     *  状态
     */
    @Column(name = "status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
