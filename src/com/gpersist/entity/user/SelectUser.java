package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SelectUser implements BaseBean {

    // 操作员编号;
    private String userid;

    // 操作员姓名;
    private String username;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SelectUser> item;

    public SelectUser() {
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
        return ToolUtils.CompareProperty((SelectUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.userid = "";
        this.username = "";
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

    public SelectBean<SelectUser> getItem() {
        if (item == null)
            item = new SelectBean<SelectUser>();

        return item;
    }

    public void setItem(SelectBean<SelectUser> item) {
        this.item = item;
    }

}
