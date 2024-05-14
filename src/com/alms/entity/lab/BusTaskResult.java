package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskResult implements BaseBean {

    private String taskid;

    private String samplecode;

    private String sampletran;

    // 任务单号;
    private String sampleid;

    // 检测员编号;
    private String judgestatusname;

    // 检测员姓名;
    private String standvalue;

    private String paramunit;

    // 检测科室;
    private String labid;

    private String submitvalue;

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    private String parametername;

    private String teststandardname;

    private String judgestandardname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskResult> item;

    public BusTaskResult() {
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
        return ToolUtils.CompareProperty((BusTaskResult) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplecode = "";
        this.sampletran = "";
        this.sampleid = "";
        this.taskid = "";
        this.judgestatusname = "";
        this.standvalue = "";
        this.paramunit = "";
        this.parametername = "";
        this.submitvalue = "";
        this.labid = "";
        this.teststandardname = "";
        this.judgestandardname = "";
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getJudgestatusname() {
        return judgestatusname;
    }

    public void setJudgestatusname(String judgestatusname) {
        this.judgestatusname = judgestatusname;
    }

    public String getStandvalue() {
        return standvalue;
    }

    public void setStandvalue(String standvalue) {
        this.standvalue = standvalue;
    }

    public String getParamunit() {
        return paramunit;
    }

    public void setParamunit(String paramunit) {
        this.paramunit = paramunit;
    }

    public String getSubmitvalue() {
        return submitvalue;
    }

    public void setSubmitvalue(String submitvalue) {
        this.submitvalue = submitvalue;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
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

    public SelectBean<BusTaskResult> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskResult>();

        return item;
    }

    public void setItem(SelectBean<BusTaskResult> item) {
        this.item = item;
    }

}
