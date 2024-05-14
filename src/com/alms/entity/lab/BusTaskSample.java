package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskSample implements BaseBean {

    // 任务单号;
    private String taskid;

    // 委托编号;
    private String tranid;

    // 样品编号;
    private String sampleid;

    private String samplecode;

    private String sampletran;

    // 开始检测日期;
    private java.util.Date begintestdate;

    // 结束检测日期;
    private java.util.Date endtestdate;

    // 完成判定;
    private boolean isjudge;

    // 判定状态;
    private String judgestatus;

    // 判定状态名称;
    private String judgestatusname;

    // 不合格参数以及结果;
    private String reportresult;

    // 技术要求;
    private String reportrequest;

    private String collectstatusname;

    private String checkdesc;

    private String auditstatus;

    private String auditstatusname;

    private String testuser;

    private String testusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskSample> item;

    public BusTaskSample() {
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
        return ToolUtils.CompareProperty((BusTaskSample) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "检测员:testusername", "问题描述:checkdesc", "状态:auditstatus",
                "状态名称:auditstatusname", "委托编号:tranid", "样品编号:sampleid", "开始检测日期:begintestdate", "结束检测日期:endtestdate",
                "完成判定:isjudge", "判定状态:judgestatus", "判定状态名称:judgestatusname", "不合格参数以及结果:reportresult",
                "技术要求:reportrequest" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.taskid = "";
        this.checkdesc = "";
        this.auditstatus = "";
        this.auditstatusname = "";
        this.tranid = "";
        this.sampleid = "";
        this.samplecode = "";
        this.sampletran = "";
        this.begintestdate = null;
        this.endtestdate = null;
        this.isjudge = false;
        this.judgestatus = "";
        this.judgestatusname = "";
        this.reportresult = "";
        this.reportrequest = "";
        this.collectstatusname = "";
        this.testuser = "";
        this.testusername = "";
    }

    public String getTestusername() {
        return testusername;
    }

    public void setTestusername(String testusername) {
        this.testusername = testusername;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(String auditstatus) {
        this.auditstatus = auditstatus;
    }

    public String getAuditstatusname() {
        return auditstatusname;
    }

    public void setAuditstatusname(String auditstatusname) {
        this.auditstatusname = auditstatusname;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public java.util.Date getBegintestdate() {
        return begintestdate;
    }

    public void setBegintestdate(java.util.Date begintestdate) {
        this.begintestdate = begintestdate;
    }

    public java.util.Date getEndtestdate() {
        return endtestdate;
    }

    public void setEndtestdate(java.util.Date endtestdate) {
        this.endtestdate = endtestdate;
    }

    public boolean getIsjudge() {
        return isjudge;
    }

    public void setIsjudge(boolean isjudge) {
        this.isjudge = isjudge;
    }

    public String getJudgestatus() {
        return judgestatus;
    }

    public void setJudgestatus(String judgestatus) {
        this.judgestatus = judgestatus;
    }

    public String getJudgestatusname() {
        return judgestatusname;
    }

    public void setJudgestatusname(String judgestatusname) {
        this.judgestatusname = judgestatusname;
    }

    public String getReportresult() {
        return reportresult;
    }

    public void setReportresult(String reportresult) {
        this.reportresult = reportresult;
    }

    public String getReportrequest() {
        return reportrequest;
    }

    public void setReportrequest(String reportrequest) {
        this.reportrequest = reportrequest;
    }

    public String getCollectstatusname() {
        return collectstatusname;
    }

    public void setCollectstatusname(String collectstatusname) {
        this.collectstatusname = collectstatusname;
    }

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
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

    public SelectBean<BusTaskSample> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskSample>();

        return item;
    }

    public void setItem(SelectBean<BusTaskSample> item) {
        this.item = item;
    }

}
