package com.alms.entity.flow;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class FlowNodeUser implements BaseBean {

    // 节点编号;
    private String flownode;

    // 节点名称;
    private String flownodename;

    // 业务流程;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 人员编号;
    private String userid;

    // 操作员姓名;
    private String username;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowNodeUser> item;

    public FlowNodeUser() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getBusflow())) {
            msg.setErrmsg("业务名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getFlownode())) {
            msg.setErrmsg("节点编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getUserid())) {
            msg.setErrmsg("人员编号不能为空！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((FlowNodeUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "节点编号:flownode", "业务流程:busflow", "人员编号:userid" };
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
        this.userid = "";
        this.username = "";
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

    public SelectBean<FlowNodeUser> getItem() {
        if (item == null)
            item = new SelectBean<FlowNodeUser>();

        return item;
    }

    public void setItem(SelectBean<FlowNodeUser> item) {
        this.item = item;
    }

}
