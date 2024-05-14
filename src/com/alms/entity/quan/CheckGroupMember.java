package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CheckGroupMember implements BaseBean {

    // 小组编号;
    private String groupid;

    // 小组成员;
    private String userid;

    // 操作员姓名;
    private String username;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CheckGroupMember> item;

    public CheckGroupMember() {
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
        return ToolUtils.CompareProperty((CheckGroupMember) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "小组编号:groupid", "小组成员:userid", "操作员姓名:username" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.groupid = "";
        this.userid = "";
        this.username = "";
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
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

    public SelectBean<CheckGroupMember> getItem() {
        if (item == null)
            item = new SelectBean<CheckGroupMember>();

        return item;
    }

    public void setItem(SelectBean<CheckGroupMember> item) {
        this.item = item;
    }

}
