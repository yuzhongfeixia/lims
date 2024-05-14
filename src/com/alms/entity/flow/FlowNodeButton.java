package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class FlowNodeButton implements BaseBean {

    // 节点编号;
    private String flownode;

    // 节点名称;
    private String flownodename;

    // 业务流程;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 按钮标识;
    private String nodebutton;

    // 按钮显示序号;
    private int btnorder;

    // 按钮标题;
    private String btntitle;

    // 按钮提示;
    private String btnmsg;

    // 按钮类型;
    private String flowbtn;

    // 按钮类型名称;
    private String flowbtnname;

    // 是否输入确认信息;
    private boolean isenter;

    // 按钮调用动作;
    private String btnaction;

    // 处理描述;
    private String todostatusdesc;

    // 步骤策略;
    private int nodestep;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowNodeButton> item;

    public FlowNodeButton() {
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
        return ToolUtils.CompareProperty((FlowNodeButton) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "节点编号:flownode", "节点名称:flownodename", "业务流程:busflow", "业务名称:busflowname",
                "按钮标识:nodebutton", "按钮显示序号:btnorder", "按钮标题:btntitle", "按钮提示:btnmsg", "按钮类型:flowbtn",
                "按钮类型名称:flowbtnname", "是否输入确认信息:isenter", "按钮调用动作:btnaction", "处理描述:todostatusdesc", "步骤策略:nodestep" };
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
        this.nodebutton = "";
        this.btnorder = 0;
        this.btntitle = "";
        this.btnmsg = "";
        this.flowbtn = "";
        this.flowbtnname = "";
        this.isenter = false;
        this.btnaction = "";
        this.todostatusdesc = "";
        this.nodestep = 0;
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

    public String getNodebutton() {
        return nodebutton;
    }

    public void setNodebutton(String nodebutton) {
        this.nodebutton = nodebutton;
    }

    public int getBtnorder() {
        return btnorder;
    }

    public void setBtnorder(int btnorder) {
        this.btnorder = btnorder;
    }

    public String getBtntitle() {
        return btntitle;
    }

    public void setBtntitle(String btntitle) {
        this.btntitle = btntitle;
    }

    public String getBtnmsg() {
        return btnmsg;
    }

    public void setBtnmsg(String btnmsg) {
        this.btnmsg = btnmsg;
    }

    public String getFlowbtn() {
        return flowbtn;
    }

    public void setFlowbtn(String flowbtn) {
        this.flowbtn = flowbtn;
    }

    public String getFlowbtnname() {
        return flowbtnname;
    }

    public void setFlowbtnname(String flowbtnname) {
        this.flowbtnname = flowbtnname;
    }

    public boolean getIsenter() {
        return isenter;
    }

    public void setIsenter(boolean isenter) {
        this.isenter = isenter;
    }

    public String getBtnaction() {
        return btnaction;
    }

    public void setBtnaction(String btnaction) {
        this.btnaction = btnaction;
    }

    public String getTodostatusdesc() {
        return todostatusdesc;
    }

    public void setTodostatusdesc(String todostatusdesc) {
        this.todostatusdesc = todostatusdesc;
    }

    public int getNodestep() {
        return nodestep;
    }

    public void setNodestep(int nodestep) {
        this.nodestep = nodestep;
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

    public SelectBean<FlowNodeButton> getItem() {
        if (item == null)
            item = new SelectBean<FlowNodeButton>();

        return item;
    }

    public void setItem(SelectBean<FlowNodeButton> item) {
        this.item = item;
    }

}
