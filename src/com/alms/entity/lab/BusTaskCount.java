package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskCount implements BaseBean {

    // 任务单号;
    private String taskid;

    private String sampletran;

    // 所属样品;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测依据编号;
    private String teststandard;

    // 检测依据名称;
    private String teststandardname;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据名称;
    private String judgestandardname;

    private int testcount;

    private boolean ismutil;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskCount> item;

    public BusTaskCount() {
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
        return ToolUtils.CompareProperty((BusTaskTest) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.taskid = "";
        this.sampletran = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.judgestandard = "";
        this.judgestandardname = "";
        this.testcount = 0;
        this.ismutil = false;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
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

    public int getTestcount() {
        return testcount;
    }

    public void setTestcount(int testcount) {
        this.testcount = testcount;
    }

    public boolean isIsmutil() {
        return ismutil;
    }

    public void setIsmutil(boolean ismutil) {
        this.ismutil = ismutil;
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

    public SelectBean<BusTaskCount> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskCount>();

        return item;
    }

    public void setItem(SelectBean<BusTaskCount> item) {
        this.item = item;
    }

}
