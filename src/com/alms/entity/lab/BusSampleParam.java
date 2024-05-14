package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusSampleParam implements BaseBean {

    private String tranid;

    private String samplecode;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测依据编号;
    private String teststandard;

    // 检测参数排序
    private int parameterorder;

    // 检测限;
    private String deteclimit;

    // 检测依据名称;
    private String teststandardcode;

    private String teststandardname;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据名称;
    private String judgestandardcode;

    private String judgestandardname;

    private String testjudge;

    private String standvalue;

    private String fieldcode;

    private String labid;

    private String taskid;

    private String paramunit;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusSampleParam> item;

    public BusSampleParam() {
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
        return ToolUtils.CompareProperty((BusSampleParam) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "单位:paramunit", "样品编号:sampleid", "检测限:deteclimit", "任务单编号:taskid",
                "检测参数排序:parameterorder", "样品名称:samplename", "检测参数编号:parameterid", "检测参数名称:parametername",
                "检测依据编号:teststandard", "检测依据名称:teststandardname", "判定依据编号:judgestandard", "判定依据名称:judgestandardname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.samplecode = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.parameterorder = 0;
        this.deteclimit = "";
        this.teststandard = "";
        this.teststandardcode = "";
        this.teststandardname = "";
        this.paramunit = "";
        this.judgestandard = "";
        this.judgestandardcode = "";
        this.judgestandardname = "";
        this.testjudge = "";
        this.standvalue = "";
        this.labid = "";
        this.taskid = "";
        this.fieldcode = "";
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

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getTeststandardcode() {
        return teststandardcode;
    }

    public void setTeststandardcode(String teststandardcode) {
        this.teststandardcode = teststandardcode;
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

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
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

    public SelectBean<BusSampleParam> getItem() {
        if (item == null)
            item = new SelectBean<BusSampleParam>();

        return item;
    }

    public void setItem(SelectBean<BusSampleParam> item) {
        this.item = item;
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

    public int getParameterorder() {
        return parameterorder;
    }

    public void setParameterorder(int parameterorder) {
        this.parameterorder = parameterorder;
    }

    public String getDeteclimit() {
        return deteclimit;
    }

    public void setDeteclimit(String deteclimit) {
        this.deteclimit = deteclimit;
    }

}
