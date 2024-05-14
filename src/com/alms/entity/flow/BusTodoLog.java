package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTodoLog implements BaseBean {

    // 待办序号;
    private int todoserial;

    // 业务编号;
    private String tranid;

    // 业务代码;
    private String busflow;

    // 业务节点;
    private String flownode;

    // 下达日期;
    private java.util.Date senddate;

    // 执行日期;
    private java.util.Date trandate;

    // 执行人;
    private String tranuser;

    // 执行部门;
    private String trandept;

    // 部门简称;
    private String trandeptshort;

    // 项目机构;
    private String labid;

    // 项目机构名称;
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

    private SelectBean<BusTodoLog> item;

    public BusTodoLog() {
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
        return ToolUtils.CompareProperty((BusTodoLog) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "待办序号:todoserial", "业务编号:tranid", "业务代码:busflow", "业务节点:flownode", "下达日期:senddate",
                "执行日期:trandate", "执行人:tranuser", "执行部门:trandept", "部门简称:trandeptshort", "项目机构:labid", "项目机构名称:labname",
                "样品编号:sampleid", "样品名称:samplename", "当前意见:tododesc", "当前状态描述:todostatusdesc", "是否当前流程:isnowflow",
                "事宜类型:todotype" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.todoserial = 0;
        this.tranid = "";
        this.busflow = "";
        this.flownode = "";
        this.senddate = ToolUtils.GetMinDate();
        this.trandate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.trandept = "";
        this.trandeptshort = "";
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

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
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

    public String getTrandept() {
        return trandept;
    }

    public void setTrandept(String trandept) {
        this.trandept = trandept;
    }

    public String getTrandeptshort() {
        return trandeptshort;
    }

    public void setTrandeptshort(String trandeptshort) {
        this.trandeptshort = trandeptshort;
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

    public SelectBean<BusTodoLog> getItem() {
        if (item == null)
            item = new SelectBean<BusTodoLog>();

        return item;
    }

    public void setItem(SelectBean<BusTodoLog> item) {
        this.item = item;
    }

}
