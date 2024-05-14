package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CheckGroup implements BaseBean {

    // 小组编号;
    private String groupid;

    // 小组名称;
    private String groupname;

    // 小组组长;
    private String groupleader;

    // 组长名称;
    private String leadername;

    // 小组是否启用;
    private boolean isuse;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CheckGroup> item;

    public CheckGroup() {
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
        return ToolUtils.CompareProperty((CheckGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "小组编号:groupid", "小组名称:groupname", "小组组长:groupleader", "组长名称:leadername", "小组是否启用:isuse",
                "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.groupid = "";
        this.groupname = "";
        this.groupleader = "";
        this.leadername = "";
        this.isuse = false;
        this.remark = "";
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

    public String getGroupleader() {
        return groupleader;
    }

    public void setGroupleader(String groupleader) {
        this.groupleader = groupleader;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public boolean getIsuse() {
        return isuse;
    }

    public void setIsuse(boolean isuse) {
        this.isuse = isuse;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<CheckGroup> getItem() {
        if (item == null)
            item = new SelectBean<CheckGroup>();

        return item;
    }

    public void setItem(SelectBean<CheckGroup> item) {
        this.item = item;
    }

}
