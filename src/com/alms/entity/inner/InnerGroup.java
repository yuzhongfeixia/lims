package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerGroup implements BaseBean {

    // 小组编号;
    private String groupid;

    // 小组名称;
    private String groupname;

    // 备注;
    private String groupremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerGroup> item;

    public InnerGroup() {
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
        return ToolUtils.CompareProperty((InnerGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "小组编号:groupid", "小组名称:groupname", "备注:groupremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.groupid = "";
        this.groupname = "";
        this.groupremark = "";
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupremark() {
        return groupremark;
    }

    public void setGroupremark(String groupremark) {
        this.groupremark = groupremark;
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

    public SelectBean<InnerGroup> getItem() {
        if (item == null)
            item = new SelectBean<InnerGroup>();

        return item;
    }

    public void setItem(SelectBean<InnerGroup> item) {
        this.item = item;
    }

}
