package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class FlowRole implements BaseBean {

    // 执行角色编号;
    private String noderole;

    // 执行角色名称;
    private String noderolename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowRole> item;

    public FlowRole() {
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
        return ToolUtils.CompareProperty((FlowRole) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "执行角色编号:noderole", "执行角色名称:noderolename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.noderole = "";
        this.noderolename = "";
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

    public SelectBean<FlowRole> getItem() {
        if (item == null)
            item = new SelectBean<FlowRole>();

        return item;
    }

    public void setItem(SelectBean<FlowRole> item) {
        this.item = item;
    }

}
