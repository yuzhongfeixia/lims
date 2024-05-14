package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class FlowButton implements BaseBean {

    // 按钮类型编号;
    private String flowbtn;

    // 按钮类型名称;
    private String flowbtnname;

    // 按钮缺省标识;
    private String nodebutton;

    // 按钮缺省提示;
    private String btnmsg;

    // 是否输入确认信息;
    private boolean isenter;

    // 按钮缺省调用动作;
    private String btnaction;

    // 处理描述;
    private String todostatusdesc;

    // 步骤策略;
    private int nodestep;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowButton> item;

    public FlowButton() {
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
        return ToolUtils.CompareProperty((FlowButton) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "按钮类型编号:flowbtn", "按钮类型名称:flowbtnname", "按钮缺省标识:nodebutton", "按钮缺省提示:btnmsg",
                "是否输入确认信息:isenter", "按钮缺省调用动作:btnaction", "处理描述:todostatusdesc", "步骤策略:nodestep" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.flowbtn = "";
        this.flowbtnname = "";
        this.nodebutton = "";
        this.btnmsg = "";
        this.isenter = false;
        this.btnaction = "";
        this.todostatusdesc = "";
        this.nodestep = 0;
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

    public String getNodebutton() {
        return nodebutton;
    }

    public void setNodebutton(String nodebutton) {
        this.nodebutton = nodebutton;
    }

    public String getBtnmsg() {
        return btnmsg;
    }

    public void setBtnmsg(String btnmsg) {
        this.btnmsg = btnmsg;
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

    public SelectBean<FlowButton> getItem() {
        if (item == null)
            item = new SelectBean<FlowButton>();

        return item;
    }

    public void setItem(SelectBean<FlowButton> item) {
        this.item = item;
    }

}
