package com.gpersist.entity.log;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class LoginLog implements BaseBean {

    // 操作员编号;
    private String userid;

    // 操作员姓名;
    private String username;

    // 所属部门;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 登录登出系统时间;
    private java.util.Date logindate;

    // 登录ip;
    private String loginip;

    // 登录ip数字;
    private int loginipnumber;

    // 说明;
    private String logindesc;

    private SearchParams search;

    private SelectBean<LoginLog> item;

    public LoginLog() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((LoginLog) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "操作员编号:userid", "操作员姓名:username", "所属部门:deptid", "机构名称:deptname", "登录登出系统时间:logindate",
                "登录IP:loginip", "登录IP数字:loginipnumber", "说明:logindesc" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.userid = "";
        this.username = "";
        this.deptid = "";
        this.deptname = "";
        this.logindate = ToolUtils.GetMinDate();
        this.loginip = "";
        this.loginipnumber = 0;
        this.logindesc = "";
    }

    public int getLoginipnumber() {
        return loginipnumber;
    }

    public void setLoginipnumber(int loginipnumber) {
        this.loginipnumber = loginipnumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public java.util.Date getLogindate() {
        return logindate;
    }

    public void setLogindate(java.util.Date logindate) {
        this.logindate = logindate;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getLogindesc() {
        return logindesc;
    }

    public void setLogindesc(String logindesc) {
        this.logindesc = logindesc;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SearchParams getSearch() {
        if (search == null)
            search = new SearchParams();

        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    public SelectBean<LoginLog> getItem() {
        if (item == null)
            item = new SelectBean<LoginLog>();

        return item;
    }

    public void setItem(SelectBean<LoginLog> item) {
        this.item = item;
    }

}
