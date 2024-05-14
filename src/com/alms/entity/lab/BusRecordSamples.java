package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

//多样品原始记录实例
public class BusRecordSamples implements BaseBean {

    // 任务单号;
    private String taskid;

    private String tranuser;

    private String tranusername;

    private java.util.Date trandate;

    private String aduituser;

    private String aduitusername;

    private java.util.Date aduitdate;

    private String approveuser;

    private String approveusername;

    private java.util.Date approvedate;

    private String recordstatus;

    private String recordstatusname;

    private String flowstatusname;

    private String modifydesc;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusRecordSamples> item;

    public SelectBean<BusRecordSamples> getItem() {
        return item;
    }

    public void setItem(SelectBean<BusRecordSamples> item) {
        this.item = item;
    }

    public BusRecordSamples() {
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
        return ToolUtils.CompareProperty((BusRecordSamples) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "创建者:tranuser", "创建者:tranusername", "创建时间:trandate", "审批人:approveuser",
                "审批人:approveusername", "审批时间:approvedate", "审核人:aduituser", "审核人:aduitusername", "审核时间:approvedate",
                "状态:recordstatus", "状态:recordstatusname", "状态:flowstatusname", "修改描述:modifydesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.modifydesc = "";
        this.tranuser = "";
        this.tranusername = "";
        // this.trandate = ToolUtils.GetNowDate();
        this.approveuser = "";
        this.approveusername = "";
        // this.approvedate = ToolUtils.GetNowDate();
        this.aduituser = "";
        this.aduitusername = "";
        // this.approvedate = ToolUtils.GetNowDate();
        this.recordstatus = "";
        this.recordstatusname = "";
        this.flowstatusname = "";
        this.taskid = "";

    }

    public String getModifydesc() {
        return modifydesc;
    }

    public void setModifydesc(String modifydesc) {
        this.modifydesc = modifydesc;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getAduituser() {
        return aduituser;
    }

    public void setAduituser(String aduituser) {
        this.aduituser = aduituser;
    }

    public String getAduitusername() {
        return aduitusername;
    }

    public void setAduitusername(String aduitusername) {
        this.aduitusername = aduitusername;
    }

    public java.util.Date getAduitdate() {
        return aduitdate;
    }

    public void setAduitdate(java.util.Date aduitdate) {
        this.aduitdate = aduitdate;
    }

    public String getApproveuser() {
        return approveuser;
    }

    public void setApproveuser(String approveuser) {
        this.approveuser = approveuser;
    }

    public String getApproveusername() {
        return approveusername;
    }

    public void setApproveusername(String approveusername) {
        this.approveusername = approveusername;
    }

    public java.util.Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(java.util.Date approvedate) {
        this.approvedate = approvedate;
    }

    public String getRecordstatus() {
        return recordstatus;
    }

    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    public String getRecordstatusname() {
        return recordstatusname;
    }

    public void setRecordstatusname(String recordstatusname) {
        this.recordstatusname = recordstatusname;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
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

}
