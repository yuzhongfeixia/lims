package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskTest implements BaseBean {

    // 任务单号;
    private String taskid;

    // 样品编号;
    private String sampleid;

    // 样品编号;
    private String samplecode;

    // 样品名称;
    private String samplename;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    private String testjudge;

    // 标准值(指标);
    private String standvalue;

    // 是否合格;
    private boolean isok;

    // 检测依据;
    private String teststandard;

    private String teststandardcode;

    // 检测依据名称;
    private String teststandardname;

    // 判定依据编号;
    private String judgestandard;

    private String judgestandardcode;

    // 判定依据名称;
    private String judgestandardname;

    private String submitvalue;

    private String paramunit;
    // 检测限;
    private String deteclimit;

    // 检测参数排序
    private int parameterorder;
    // ph规格
    private String phlevel;

    public String getDeteclimit() {
        return deteclimit;
    }

    public void setDeteclimit(String deteclimit) {
        this.deteclimit = deteclimit;
    }

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskTest> item;

    public BusTaskTest() {
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
        return new String[] { "任务单号:taskid", "单位:paramunit", "检测限:deteclimit", "检测参数排序:parameterorder", "样品编号:sampleid",
                "样品编号:samplecode", "样品名称:samplename", "检测参数:parameterid", "检测参数名称:parametername", "标准值(指标):standvalue",
                "是否合格:isok", "检测依据:teststandard", "检测依据名称:teststandardname", "判定依据编号:judgestandard",
                "判定依据名称:judgestandardname", "ph规格：phlevel" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.taskid = "";
        this.samplecode = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.testjudge = "";
        this.standvalue = "";
        this.isok = false;
        this.teststandard = "";
        this.teststandardcode = "";
        this.teststandardname = "";
        this.judgestandard = "";
        this.judgestandardcode = "";
        this.judgestandardname = "";
        this.submitvalue = "";
        this.paramunit = "";
        this.deteclimit = "";
        this.setParameterorder(0);
        this.phlevel = "";
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

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
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

    public String getTestjudge() {
        return testjudge;
    }

    public void setTestjudge(String testjudge) {
        this.testjudge = testjudge;
    }

    public String getStandvalue() {
        return standvalue;
    }

    public void setStandvalue(String standvalue) {
        this.standvalue = standvalue;
    }

    public boolean getIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public String getTeststandard() {
        return teststandard;
    }

    public void setTeststandard(String teststandard) {
        this.teststandard = teststandard;
    }

    public String getTeststandardcode() {
        return teststandardcode;
    }

    public void setTeststandardcode(String teststandardcode) {
        this.teststandardcode = teststandardcode;
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

    public String getJudgestandardcode() {
        return judgestandardcode;
    }

    public void setJudgestandardcode(String judgestandardcode) {
        this.judgestandardcode = judgestandardcode;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
    }

    public String getSubmitvalue() {
        return submitvalue;
    }

    public void setSubmitvalue(String submitvalue) {
        this.submitvalue = submitvalue;
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

    public SelectBean<BusTaskTest> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskTest>();

        return item;
    }

    public void setItem(SelectBean<BusTaskTest> item) {
        this.item = item;
    }

    public String getPhlevel() {
        return phlevel;
    }

    public void setPhlevel(String phlevel) {
        this.phlevel = phlevel;
    }

    public int getParameterorder() {
        return parameterorder;
    }

    public void setParameterorder(int parameterorder) {
        this.parameterorder = parameterorder;
    }

}
