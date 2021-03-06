package com.tydic.bigdata.domain.shortlink;


import javax.persistence.*;

/**
 * @desc  第一步建立实体类
 */

@Entity
@Table(name = "comm_sys_privilege_test")
public class ShortLink {


    /**
     * 主键
     */
    @Id
    @GeneratedValue
    @Column(name = "PRIVILEGE_ID")
    private Long id;
    /**
     * 名称
     */
    @Column(name = "PRIVILEGE_NAME")
    private String name;
    /**
     * 短链接
     */
    @Column(name = "short_url")
    private String shortUrl;
    /**
     * url
     */
    @Column(name = "SYS_URL")
    private String url;

    @Column(name = "VALID_FLAG")
    private Integer valid;

    @Column(name = "PRIVILEGE_Level")
    private Integer privilgeLeve = 1;

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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getPrivilgeLeve() {
        return privilgeLeve;
    }

    public void setPrivilgeLeve(Integer privilgeLeve) {
        this.privilgeLeve = privilgeLeve;
    }
}
