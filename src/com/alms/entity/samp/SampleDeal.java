package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleDeal implements BaseBean {

    // 业务编号;
    private String tranid;

    // 样品编号
    private String samplecode;

    // 样品名称;
    private String samplename;

    // 样品检测结束日期
    private java.util.Date finishdate;

    // 收样日期
    private java.util.Date collectdate;

    // 处置方式
    private String dealway;

    // 保存时间期限;
    private java.util.Date storeend;

    // 处置日期;
    private java.util.Date dealdate;

    // 处置方法;
    private String dealmethod;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 处置人;
    private String dealuser;

    // 处置人姓名;
    private String dealusername;

    // 处置人时间;
    private java.util.Date dealuserdate;

    // 处置状态;
    private String dealstatus;

    // 处置状态名称;
    private String dealstatusname;

    // 批准人;
    private String approveuser;

    // 批准人姓名;
    private String approveusername;

    private String taskid;

    // 批准人时间;
    private java.util.Date approvedate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleDeal> item;

    public SampleDeal() {
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
        return ToolUtils.CompareProperty((SampleDeal) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "样品名称:samplename", "保存时间期限:storeend", "处置日期:dealdate", "处置方法:dealmethod",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "处置人:dealuser",
                "处置人姓名:dealusername", "处置人时间:dealuserdate", "处置状态:dealstatus", "处置状态名称:dealstatusname",
                "批准人:approveuser", "批准人姓名:approveusername", "批准人时间:approvedate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.samplecode = "";
        this.samplename = "";
        this.storeend = ToolUtils.GetMinDate();
        this.dealdate = ToolUtils.GetMinDate();
        this.dealmethod = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.dealuser = "";
        this.dealusername = "";
        this.dealuserdate = ToolUtils.GetMinDate();
        this.dealstatus = "";
        this.dealstatusname = "";
        this.approveuser = "";
        this.approveusername = "";
        this.taskid = "";
        // this.approvedate = ToolUtils.GetMinDate();
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public java.util.Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(java.util.Date finishdate) {
        this.finishdate = finishdate;
    }

    public java.util.Date getCollectdate() {
        return collectdate;
    }

    public void setCollectdate(java.util.Date collectdate) {
        this.collectdate = collectdate;
    }

    public String getDealway() {
        return dealway;
    }

    public void setDealway(String dealway) {
        this.dealway = dealway;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public java.util.Date getStoreend() {
        return storeend;
    }

    public void setStoreend(java.util.Date storeend) {
        this.storeend = storeend;
    }

    public java.util.Date getDealdate() {
        return dealdate;
    }

    public void setDealdate(java.util.Date dealdate) {
        this.dealdate = dealdate;
    }

    public String getDealmethod() {
        return dealmethod;
    }

    public void setDealmethod(String dealmethod) {
        this.dealmethod = dealmethod;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getDealuser() {
        return dealuser;
    }

    public void setDealuser(String dealuser) {
        this.dealuser = dealuser;
    }

    public String getDealusername() {
        return dealusername;
    }

    public void setDealusername(String dealusername) {
        this.dealusername = dealusername;
    }

    public java.util.Date getDealuserdate() {
        return dealuserdate;
    }

    public void setDealuserdate(java.util.Date dealuserdate) {
        this.dealuserdate = dealuserdate;
    }

    public String getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(String dealstatus) {
        this.dealstatus = dealstatus;
    }

    public String getDealstatusname() {
        return dealstatusname;
    }

    public void setDealstatusname(String dealstatusname) {
        this.dealstatusname = dealstatusname;
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

    public SelectBean<SampleDeal> getItem() {
        if (item == null)
            item = new SelectBean<SampleDeal>();

        return item;
    }

    public void setItem(SelectBean<SampleDeal> item) {
        this.item = item;
    }

}
