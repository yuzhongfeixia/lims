package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysRole implements BaseBean {

    // 角色编号;
    private int roleid;

    // 角色名称;
    private String rolename;

    private String accountid;

    private String accountname;

    private DataDeal deal;

    private SelectBean<SysRole> item;

    public SysRole() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysRole) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "角色编号:roleid", "角色名称:rolename" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.roleid = 0;
        this.rolename = "";
        this.accountid = "";
        this.accountname = "";
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SysRole> getItem() {
        if (item == null)
            item = new SelectBean<SysRole>();

        return item;
    }

    public void setItem(SelectBean<SysRole> item) {
        this.item = item;
    }

}
