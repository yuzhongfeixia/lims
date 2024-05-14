package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class FlowNodeRole implements BaseBean {

    // 节点编号;
    private String flownode;

    // 节点名称;
    private String flownodename;

    // 业务流程;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 执行角色;
    private String noderole;

    // 角色名称;
    private String noderolename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowNodeRole> item;

    public FlowNodeRole() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getFlownode())) {
            msg.setErrmsg("节点编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FlowNodeRole) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "节点编号:flownode", "节点名称:flownodename", "业务流程:busflow", "业务名称:busflowname", "执行角色:noderole",
                "角色名称:rolename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.flownode = "";
        this.flownodename = "";
        this.busflow = "";
        this.busflowname = "";
        this.noderole = "";
        this.noderolename = "";
    }

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
    }

    public String getFlownodename() {
        return flownodename;
    }

    public void setFlownodename(String flownodename) {
        this.flownodename = flownodename;
    }

    public String getBusflow() {
        return busflow;
    }

    public void setBusflow(String busflow) {
        this.busflow = busflow;
    }

    public String getBusflowname() {
        return busflowname;
    }

    public void setBusflowname(String busflowname) {
        this.busflowname = busflowname;
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

    public SelectBean<FlowNodeRole> getItem() {
        if (item == null)
            item = new SelectBean<FlowNodeRole>();

        return item;
    }

    public void setItem(SelectBean<FlowNodeRole> item) {
        this.item = item;
    }

}
