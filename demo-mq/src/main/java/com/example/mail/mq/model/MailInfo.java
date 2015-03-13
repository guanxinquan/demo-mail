package com.example.mail.mq.model;

import java.util.List;

/**
 * Created by guanxinquan on 15-3-12.
 */
public class MailInfo {
    private String from;

    private List<String> tos;

    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTos() {
        return tos;
    }

    public void setTos(List<String> tos) {
        this.tos = tos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
