package com.gpersist.webservice.entity;

public class WsSearch {

    // 服务商编号
    private String appid;

    // 用户编号
    private String userid;

    private String mac;

    private String search;

    private int start;

    private int end;

    private int pagesize;

    public WsSearch() {

    }

    public void OnInit() {
        this.appid = "";
        this.userid = "";
        this.mac = "";
        this.search = "";
        this.start = 0;
        this.end = 0;
        this.pagesize = 50;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

}
