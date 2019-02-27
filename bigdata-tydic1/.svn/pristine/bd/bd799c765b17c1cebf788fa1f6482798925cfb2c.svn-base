package com.tydic.bigdata.domain.hostServleInfo;

import javax.persistence.*;

@Entity
@Table(name = "comm_sys_host_serve")
public class HostServeInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "HOST_SERVE_ID")
    private Long hostServeId;

    /**
     * 主机ID
     */
    @Column(name = "HOST_ID")
    private Long hostId;

    /**
     * 租户
     */
    @Column(name = "RENT")
    private String rent;

    /**
     * 主机
     */
    @Column(name = "HOST")
    private String host;

    /**
     * 服务名
     */
    @Column(name = "HOST_SERVE_NAME")
    private String hostServeName;

    /**
     * 服务路径
     */
    @Column(name = "HOST_SERVE_PATH")
    private String hostServePath;

    public Long getHostServeId() {
        return hostServeId;
    }

    public void setHostServeId(Long hostServeId) {
        this.hostServeId = hostServeId;
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

    public String getHostServeName() {
        return hostServeName;
    }

    public void setHostServeName(String hostServeName) {
        this.hostServeName = hostServeName;
    }

    public String getHostServePath() {
        return hostServePath;
    }

    public void setHostServePath(String hostServePath) {
        this.hostServePath = hostServePath;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
