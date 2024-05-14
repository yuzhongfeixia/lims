package com.gpersist.webservice.entity;

public class WsGet {

    // 需要获取数据的编号
    private String itemid;

    // 服务商编号
    private String appid;

    // 用户编号
    private String userid;

    private String mac;

    public WsGet() {
        OnInit();
    }

    public void OnInit() {
        this.itemid = "";
        this.appid = "";
        this.userid = "";
        this.mac = "";
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
