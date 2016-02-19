package com.auxgroup.backupsms.entities;

/**
 * Created by Administrator on 2016-2-19.
 */
public class SmsInfo {
    private int id;
    private String address;
    private long date;
    private int type;
    private String body;

    @Override
    public String toString() {
        return "SmsInfo{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SmsInfo(String address, String body, long date, int id, int type) {
        this.address = address;
        this.body = body;
        this.date = date;
        this.id = id;
        this.type = type;
    }

    public SmsInfo() {
    }
}
