package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskTester implements BaseBean {

    private String tranid;

    private String sampleid;

    private String samplename;

    // 任务单号;
    private String taskid;

    // 检测员编号;
    private String testuser;

    // 检测员姓名;
    private String testusername;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    private String parameterids;

    private String parameternames;

    private String teststandard;

    private String teststandardname;

    private String judgestandard;

    private String judgestandardname;

    private String testjudge;

    private String standvalue;

    private String paramunit;

    private String recordid;

    private String sampletran;

    private String samplecode;

    private boolean ischoice;

    private String deteclimit;

    public String getDeteclimit() {
        return deteclimit;
    }

    public void setDeteclimit(String deteclimit) {
        this.deteclimit = deteclimit;
    }

    // 开始检测日期;
    private java.util.Date begintestdate;

    // 结束检测日期;
    private java.util.Date endtestdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskTester> item;

    public BusTaskTester() {
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
        return ToolUtils.CompareProperty((BusTaskTester) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "单位:paramunit", "检测限:deteclimit", "是否选择:ischoice", "样品编号:sampleid",
                "检测员编号:testuser", "检测员姓名:testusername", "检测参数:parameterid", "检测参数名称:parametername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.paramunit = "";
        this.sampleid = "";
        this.taskid = "";
        this.testuser = "";
        this.testusername = "";
        this.parameterid = "";
        this.parametername = "";
        this.parameterids = "";
        this.parameternames = "";
        this.recordid = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.judgestandard = "";
        this.judgestandardname = "";
        this.standvalue = "";
        this.testjudge = "";
        this.ischoice = true;
        this.samplecode = "";
        this.samplename = "";
        this.deteclimit = "";
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

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getParamunit() {
        return paramunit;
    }

    public void setParamunit(String paramunit) {
        this.paramunit = paramunit;
    }

    public boolean isIschoice() {
        return ischoice;
    }

    public void setIschoice(boolean ischoice) {
        this.ischoice = ischoice;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
    }

    public String getTestusername() {
        return testusername;
    }

    public void setTestusername(String testusername) {
        this.testusername = testusername;
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

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }

    public String getParameternames() {
        return parameternames;
    }

    public void setParameternames(String parameternames) {
        this.parameternames = parameternames;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getTeststandard() {
        return teststandard;
    }

    public void setTeststandard(String teststandard) {
        this.teststandard = teststandard;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getJudgestandard() {
        return judgestandard;
    }

    public void setJudgestandard(String judgestandard) {
        this.judgestandard = judgestandard;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
    }

    public String getStandvalue() {
        return standvalue;
    }

    public void setStandvalue(String standvalue) {
        this.standvalue = standvalue;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getTestjudge() {
        return testjudge;
    }

    public void setTestjudge(String testjudge) {
        this.testjudge = testjudge;
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

    public SelectBean<BusTaskTester> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskTester>();

        return item;
    }

    public void setItem(SelectBean<BusTaskTester> item) {
        this.item = item;
    }

}
