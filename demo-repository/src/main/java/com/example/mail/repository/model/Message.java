package com.example.mail.repository.model;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-12.
 */
public class Message {

    private Long id;

    private String header;

    private String body;

    private Date createDate = new Date();

    private Date updateDate =  new Date();

    public Message(String header, String body) {
        this.body = body;
        this.header = header;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }



}
