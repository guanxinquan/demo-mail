package com.example.mail.dns.model;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class MXRecord {

    private String name;

    private Integer preference;

    private boolean isChecked;

    public MXRecord(String name, Integer preference, boolean isChecked) {
        this.name = name;
        this.preference = preference;
        this.isChecked = isChecked;
    }

    public MXRecord() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPreference() {
        return preference;
    }

    public void setPreference(Integer preference) {
        this.preference = preference;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
