package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DatSampleTest implements BaseBean {

    // 样品代号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 执行序号;
    private int testserial;

    // 所属类编号;
    private String classsource;

    // 类来源名称;
    private String classsourcename;

    // 类字段编号;
    private String classfield;

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

    // 函数名称;
    private String funcmathname;

    // 取值方式;
    private String funcvalue;

    // 函数名称;
    private String funcgetname;

    // 小数位数;
    private int digitnumber;

    // 缺省值;
    private String defvalue;

    // 平均系数;
    private double avefactor;

    // 平均对比系数;
    private double comparefactor;

    // 检测参数;
    private String parameterid;

    // 检测参数;
    private String parametername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DatSampleTest> item;

    public DatSampleTest() {
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
        return ToolUtils.CompareProperty((DatSampleTest) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品代号:sampleid", "样品名称:samplename", "检测参数编号:parameterid", "检测参数:parametername",
                "执行序号:testserial", "所属类编号:classsource", "类来源名称:classsourcename", "类字段编号:classfield",
                "检测依据编号:teststandard", "检测依据名称:teststandardname", "计算公式显示:displayformula", "计算公式:actformula",
                "计算函数:funcformula", "函数名称:funcmathname", "取值方式:funcvalue", "函数名称:funcgetname", "小数位数:digitnumber",
                "缺省值:defvalue", "平均系数:avefactor", "平均对比系数:comparefactor" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.samplename = "";
        this.testserial = 0;
        this.classsource = "";
        this.classsourcename = "";
        this.classfield = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.displayformula = "";
        this.actformula = "";
        this.funcformula = "";
        this.funcmathname = "";
        this.funcvalue = "";
        this.funcgetname = "";
        this.digitnumber = 0;
        this.defvalue = "";
        this.avefactor = 0;
        this.comparefactor = 0;
        this.parameterid = "";
        this.parametername = "";
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

    public int getTestserial() {
        return testserial;
    }

    public void setTestserial(int testserial) {
        this.testserial = testserial;
    }

    public String getClasssource() {
        return classsource;
    }

    public void setClasssource(String classsource) {
        this.classsource = classsource;
    }

    public String getClasssourcename() {
        return classsourcename;
    }

    public void setClasssourcename(String classsourcename) {
        this.classsourcename = classsourcename;
    }

    public String getClassfield() {
        return classfield;
    }

    public void setClassfield(String classfield) {
        this.classfield = classfield;
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

    public String getFuncmathname() {
        return funcmathname;
    }

    public void setFuncmathname(String funcmathname) {
        this.funcmathname = funcmathname;
    }

    public String getFuncvalue() {
        return funcvalue;
    }

    public void setFuncvalue(String funcvalue) {
        this.funcvalue = funcvalue;
    }

    public String getFuncgetname() {
        return funcgetname;
    }

    public void setFuncgetname(String funcgetname) {
        this.funcgetname = funcgetname;
    }

    public int getDigitnumber() {
        return digitnumber;
    }

    public void setDigitnumber(int digitnumber) {
        this.digitnumber = digitnumber;
    }

    public String getDefvalue() {
        return defvalue;
    }

    public void setDefvalue(String defvalue) {
        this.defvalue = defvalue;
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

    public SelectBean<DatSampleTest> getItem() {
        if (item == null)
            item = new SelectBean<DatSampleTest>();

        return item;
    }

    public void setItem(SelectBean<DatSampleTest> item) {
        this.item = item;
    }

}
