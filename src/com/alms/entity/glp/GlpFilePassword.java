package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFilePassword implements BaseBean {

    // 用户编号;
    private String userid;

    // 操作员姓名;
    private String username;

    // 密码;
    private String filepassword;

    // 确认密码;
    private String repassword;

    // 旧密码
    private String oldpassword;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFilePassword> item;

    public GlpFilePassword() {
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
        return ToolUtils.CompareProperty((GlpFilePassword) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "用户编号:userid", "操作员姓名:username", "密码:filepassword" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.userid = "";
        this.username = "";
        this.filepassword = "";
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

    public String getFilepassword() {
        return filepassword;
    }

    public void setFilepassword(String filepassword) {
        this.filepassword = filepassword;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public SearchParams getSearch() {
        if (search == null)
            search = new SearchParams();

        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<GlpFilePassword> getItem() {
        if (item == null)
            item = new SelectBean<GlpFilePassword>();

        return item;
    }

    public void setItem(SelectBean<GlpFilePassword> item) {
        this.item = item;
    }

}
