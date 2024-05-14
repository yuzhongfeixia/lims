package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class FlowRoleUser implements BaseBean {

    private String noderole;

    private String noderolename;

    private String userid;

    private String username;

    private String deptid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowRoleUser> item;

    public FlowRoleUser() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getNoderole())) {
            msg.setErrmsg("流程角色编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getUserid())) {
            msg.setErrmsg("用户编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FlowRoleUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.noderole = "";
        this.noderolename = "";
        this.userid = "";
        this.username = "";
        this.deptid = "";
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
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

    public String getNoderole() {
        return noderole;
    }

    public void setNoderole(String noderole) {
        this.noderole = noderole;
    }

    public String getNoderolename() {
        return noderolename;
    }

    public void setNoderolename(String noderolename) {
        this.noderolename = noderolename;
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

    public SelectBean<FlowRoleUser> getItem() {
        if (item == null)
            item = new SelectBean<FlowRoleUser>();

        return item;
    }

    public void setItem(SelectBean<FlowRoleUser> item) {
        this.item = item;
    }

}
