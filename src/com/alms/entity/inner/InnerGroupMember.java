package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerGroupMember implements BaseBean {

    // 小组编号;
    private String groupid;

    // 成员;
    private String memberuser;

    // 成员姓名;
    private String memberusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerGroupMember> item;

    public InnerGroupMember() {
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
        return ToolUtils.CompareProperty((InnerGroupMember) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "小组编号:groupid", "成员:memberuser", "成员姓名:memberusername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.groupid = "";
        this.memberuser = "";
        this.memberusername = "";
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMemberuser() {
        return memberuser;
    }

    public void setMemberuser(String memberuser) {
        this.memberuser = memberuser;
    }

    public String getMemberusername() {
        return memberusername;
    }

    public void setMemberusername(String memberusername) {
        this.memberusername = memberusername;
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

    public SelectBean<InnerGroupMember> getItem() {
        if (item == null)
            item = new SelectBean<InnerGroupMember>();

        return item;
    }

    public void setItem(SelectBean<InnerGroupMember> item) {
        this.item = item;
    }

}
