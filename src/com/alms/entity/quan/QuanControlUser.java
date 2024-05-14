package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanControlUser implements BaseBean {

    // 评价编号;
    private String tranid;

    // 参加人员;
    private String userid;

    // 操作员姓名;
    private String username;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanControlUser> item;

    public QuanControlUser() {
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
        return ToolUtils.CompareProperty((QuanControlUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "评价编号:tranid", "参加人员:userid", "操作员姓名:username" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.userid = "";
        this.username = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public SelectBean<QuanControlUser> getItem() {
        if (item == null)
            item = new SelectBean<QuanControlUser>();

        return item;
    }

    public void setItem(SelectBean<QuanControlUser> item) {
        this.item = item;
    }

}
