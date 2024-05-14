package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusRecord implements BaseBean {

    // 原始记录表编号;
    private String recordid;

    // 任务单号;
    private String taskid;

    private String tranid;

    private String sampletran;

    // 表单编号;
    private String formid;

    // 表单名称;
    private String formname;

    // 所属样品;
    private String sampleid;

    // 样品名称;
    private String samplename;

    private int formcount;

    private int groupcount;

    private int groupfinish;

    private int speccount;

    private int pagegroup;

    private int pagespec;

    private String crtuser;

    private String username;

    private java.util.Date crtdate;

    private String testtype;

    private String samplecode;

    private String samplestatus;

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

    private String parameterid;

    private String parametername;

    private String startdate;

    private String enddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusRecord> item;

    public BusRecord() {
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
        return ToolUtils.CompareProperty((BusRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "原始记录表编号:recordid", "任务单号:taskid", "表单编号:formid", "所属样品:sampleid", "样品编号:samplecode",
                "创建者:tranuser", "创建者:tranusername", "创建时间:trandate", "审批人:approveuser", "审批人:approveusername",
                "审批时间:approvedate", "审核人:aduituser", "审核人:aduitusername", "审核时间:approvedate", "状态:recordstatus",
                "状态:recordstatusname", "状态:flowstatusname", "修改描述:modifydesc", "检测参数:parameterid", "检测参数:parametername",
                "开始日期:startdate", "结束日期:enddate", "样品状态:samplestatus" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.recordid = "";
        this.modifydesc = "";
        this.samplecode = "";
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
        this.tranid = "";
        this.formid = "";
        this.formname = "";
        this.sampleid = "";
        this.samplename = "";
        this.crtuser = "";
        this.username = "";
        this.crtdate = ToolUtils.GetNowDate();
        this.testtype = "";
        this.formcount = 0;
        this.groupcount = 0;
        this.groupfinish = 0;
        this.speccount = 0;
        this.pagegroup = 0;
        this.sampletran = "";
        this.pagespec = 0;
        this.parameterid = "";
        this.parametername = "";
        this.startdate = "";
        this.enddate = "";
        this.setSamplestatus("");

    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
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

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
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

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public int getPagespec() {
        return pagespec;
    }

    public void setPagespec(int pagespec) {
        this.pagespec = pagespec;
    }

    public int getPagegroup() {
        return pagegroup;
    }

    public void setPagegroup(int pagegroup) {
        this.pagegroup = pagegroup;
    }

    public int getFormcount() {
        return formcount;
    }

    public void setFormcount(int formcount) {
        this.formcount = formcount;
    }

    public int getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(int groupcount) {
        this.groupcount = groupcount;
    }

    public int getGroupfinish() {
        return groupfinish;
    }

    public void setGroupfinish(int groupfinish) {
        this.groupfinish = groupfinish;
    }

    public int getSpeccount() {
        return speccount;
    }

    public void setSpeccount(int speccount) {
        this.speccount = speccount;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
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

    public SelectBean<BusRecord> getItem() {
        if (item == null)
            item = new SelectBean<BusRecord>();

        return item;
    }

    public void setItem(SelectBean<BusRecord> item) {
        this.item = item;
    }

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

}
