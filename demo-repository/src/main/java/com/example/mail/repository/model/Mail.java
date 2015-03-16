package com.example.mail.repository.model;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class Mail {


    private Long id;

    private Integer size;

    private Long mailboxId;

    private String UIDL;

    private Date createDate = new Date();

    private Long messageId;

    public Mail(Integer size, Long mailboxId, String UIDL) {
        this.size = size;
        this.mailboxId = mailboxId;
        this.UIDL = UIDL;
        this.createDate = new Date();
    }

    public Mail(Integer size, Long mailboxId, Long messageId,String UIDL) {
        this.size = size;
        this.mailboxId = mailboxId;
        this.messageId = messageId;
        this.UIDL = UIDL;
    }

    public Mail() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public Long getMailboxId() {
        return mailboxId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMailboxId(Long mailboxId) {
        this.mailboxId = mailboxId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
