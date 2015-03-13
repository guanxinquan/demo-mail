package com.example.mail.repository.model;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class Mail {
    private Integer id;

    private Integer size;

    private Integer mailboxId;

    private String UIDL;

    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUIDL() {
        return UIDL;
    }

    public void setUIDL(String UIDL) {
        this.UIDL = UIDL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getMailboxId() {
        return mailboxId;
    }

    public void setMailboxId(Integer mailboxId) {
        this.mailboxId = mailboxId;
    }

}
