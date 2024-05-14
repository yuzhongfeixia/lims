package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTodo implements BaseBean {

    // 待办序号;
    private int todoserial;

    // 业务编号;
    private String tranid;

    // 业务代码;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 业务节点;
    private String flownode;

    // 节点名称;
    private String flownodename;

    // 下达日期;
    private java.util.Date senddate;

    // 执行日期;
    private java.util.Date trandate;

    // 执行人;
    private String tranuser;

    // 操作员姓名;
    private String username;

    // 执行部门;
    private String trandept;

    // 机构名称;
    private String deptname;

    // 试验室;
    private String labid;

    // 试验室名称;
    private String labname;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 当前意见;
    private String tododesc;

    // 当前状态描述;
    private String todostatusdesc;

    // 是否当前流程;
    private boolean isnowflow;

    // 事宜类型;
    private String todotype;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTodo> item;

    public BusTodo() {
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
        return ToolUtils.CompareProperty((BusTodo) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "待办序号:todoserial", "业务编号:tranid", "业务代码:busflow", "业务名称:busflowname", "业务节点:flownode",
                "节点名称:flownodename", "下达日期:senddate", "执行日期:trandate", "执行人:tranuser", "操作员姓名:username",
                "执行部门:trandept", "机构名称:deptname", "试验室:labid", "试验室名称:labname", "样品编号:sampleid", "样品名称:samplename",
                "当前意见:tododesc", "当前状态描述:todostatusdesc", "是否当前流程:isnowflow", "事宜类型:todotype" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    public String[] OnCountExclusions() {
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.todoserial = 0;
        this.tranid = "";
        this.busflow = "";
        this.busflowname = "";
        this.flownode = "";
        this.flownodename = "";
        // this.senddate = ToolUtils.GetMinDate();
        // this.trandate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.username = "";
        this.trandept = "";
        this.deptname = "";
        this.labid = "";
        this.labname = "";
        this.sampleid = "";
        this.samplename = "";
        this.tododesc = "";
        this.todostatusdesc = "";
        this.isnowflow = false;
        this.todotype = "";
    }

    public int getTodoserial() {
        return todoserial;
    }

    public void setTodoserial(int todoserial) {
        this.todoserial = todoserial;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public java.util.Date getSenddate() {
        return senddate;
    }

    public void setSenddate(java.util.Date senddate) {
        this.senddate = senddate;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTrandept() {
        return trandept;
    }

    public void setTrandept(String trandept) {
        this.trandept = trandept;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getTododesc() {
        return tododesc;
    }

    public void setTododesc(String tododesc) {
        this.tododesc = tododesc;
    }

    public String getTodostatusdesc() {
        return todostatusdesc;
    }

    public void setTodostatusdesc(String todostatusdesc) {
        this.todostatusdesc = todostatusdesc;
    }

    public boolean getIsnowflow() {
        return isnowflow;
    }

    public void setIsnowflow(boolean isnowflow) {
        this.isnowflow = isnowflow;
    }

    public String getTodotype() {
        return todotype;
    }

    public void setTodotype(String todotype) {
        this.todotype = todotype;
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

    public SelectBean<BusTodo> getItem() {
        if (item == null)
            item = new SelectBean<BusTodo>();

        return item;
    }

    public void setItem(SelectBean<BusTodo> item) {
        this.item = item;
    }

}
