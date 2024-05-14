package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DatComputeData implements BaseBean {

    private String tranid;

    private String samplecode;

    // 任务单号;
    private String taskid;

    // 试验样品编号;
    private String sampletran;

    private String sampleid;

    private int groupserial;

    private int specserial;

    private String classsource;

    private String classfield;

    private String fieldcode;

    private String fieldtype;

    private String displayvalue;

    private String submitvalue;

    private String validcode;

    private String datasource;

    // 检测依据编号;
    private String teststandard;

    // 检测依据名称;
    private String teststandardname;

    // 计算公式显示;
    private String displayformula;

    // 计算公式;
    private String actformula;

    // 计算函数;
    private String funcformula;

    // 取值方式;
    private String funcvalue;

    // 小数位数;
    private int digitnumber;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据名称;
    private String judgestandardname;

    // 判定方式;
    private String judgefuncvalue;

    // 取值公式;
    private String funcrela;

    private boolean istask;

    private boolean isgroup;

    private boolean isserial;

    private int testserial;

    private String defvalue;

    private double avefactor;

    private double comparefactor;

    private String parameterids;

    private DataDeal deal;

    private SelectBean<DatComputeData> item;

    public DatComputeData() {
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
        return ToolUtils.CompareProperty((DatComputeData) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "试验样品编号:sampletran" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.samplecode = "";
        this.taskid = "";
        this.sampletran = "";
        this.groupserial = 0;
        this.specserial = 0;
        this.classsource = "";
        this.parameterids = "";
        this.fieldcode = "";
        this.fieldtype = "";
        this.displayvalue = "";
        this.submitvalue = "";
        this.validcode = "";
        this.sampleid = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.displayformula = "";
        this.actformula = "";
        this.funcformula = "";
        this.funcvalue = "";
        this.digitnumber = 0;
        this.judgestandard = "";
        this.judgestandardname = "";
        this.judgefuncvalue = "";
        this.funcrela = "";
        this.istask = false;
        this.datasource = "";
        this.isgroup = false;
        this.isserial = false;
        this.classfield = "";
        this.testserial = 0;
        this.defvalue = "";
        this.avefactor = 0;
        this.comparefactor = 0;
    }

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }

    public double getAvefactor() {
        return avefactor;
    }

    public void setAvefactor(double avefactor) {
        this.avefactor = avefactor;
    }

    public double getComparefactor() {
        return comparefactor;
    }

    public void setComparefactor(double comparefactor) {
        this.comparefactor = comparefactor;
    }

    public int getTestserial() {
        return testserial;
    }

    public void setTestserial(int testserial) {
        this.testserial = testserial;
    }

    public String getDefvalue() {
        return defvalue;
    }

    public void setDefvalue(String defvalue) {
        this.defvalue = defvalue;
    }

    public String getClassfield() {
        return classfield;
    }

    public void setClassfield(String classfield) {
        this.classfield = classfield;
    }

    public boolean isIsgroup() {
        return isgroup;
    }

    public void setIsgroup(boolean isgroup) {
        this.isgroup = isgroup;
    }

    public boolean isIsserial() {
        return isserial;
    }

    public void setIsserial(boolean isserial) {
        this.isserial = isserial;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public boolean isIstask() {
        return istask;
    }

    public void setIstask(boolean istask) {
        this.istask = istask;
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

    public String getDisplayformula() {
        return displayformula;
    }

    public void setDisplayformula(String displayformula) {
        this.displayformula = displayformula;
    }

    public String getActformula() {
        return actformula;
    }

    public void setActformula(String actformula) {
        this.actformula = actformula;
    }

    public String getFuncformula() {
        return funcformula;
    }

    public void setFuncformula(String funcformula) {
        this.funcformula = funcformula;
    }

    public String getFuncvalue() {
        return funcvalue;
    }

    public void setFuncvalue(String funcvalue) {
        this.funcvalue = funcvalue;
    }

    public int getDigitnumber() {
        return digitnumber;
    }

    public void setDigitnumber(int digitnumber) {
        this.digitnumber = digitnumber;
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

    public String getJudgefuncvalue() {
        return judgefuncvalue;
    }

    public void setJudgefuncvalue(String judgefuncvalue) {
        this.judgefuncvalue = judgefuncvalue;
    }

    public String getFuncrela() {
        return funcrela;
    }

    public void setFuncrela(String funcrela) {
        this.funcrela = funcrela;
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

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public int getGroupserial() {
        return groupserial;
    }

    public void setGroupserial(int groupserial) {
        this.groupserial = groupserial;
    }

    public String getClasssource() {
        return classsource;
    }

    public void setClasssource(String classsource) {
        this.classsource = classsource;
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

    public int getSpecserial() {
        return specserial;
    }

    public void setSpecserial(int specserial) {
        this.specserial = specserial;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getDisplayvalue() {
        return displayvalue;
    }

    public void setDisplayvalue(String displayvalue) {
        this.displayvalue = displayvalue;
    }

    public String getSubmitvalue() {
        return submitvalue;
    }

    public void setSubmitvalue(String submitvalue) {
        this.submitvalue = submitvalue;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<DatComputeData> getItem() {
        if (item == null)
            item = new SelectBean<DatComputeData>();

        return item;
    }

    public void setItem(SelectBean<DatComputeData> item) {
        this.item = item;
    }

}
