package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DatSampleParameter implements BaseBean {

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 类来源编号;
    private String classsource;

    // 类来源名称;
    private String classsourcename;

    // 类字段来源编号;
    private String classfield;

    // 类字段名称;
    private String classfieldname;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DatSampleParameter> item;

    public DatSampleParameter() {
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
        return ToolUtils.CompareProperty((DatSampleParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "样品名称:samplename", "类来源编号:classsource", "类来源名称:classsourcename",
                "类字段来源编号:classfield", "类字段名称:classfieldname", "检测参数:parameterid", "检测参数名称:parametername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.samplename = "";
        this.classsource = "";
        this.classsourcename = "";
        this.classfield = "";
        this.classfieldname = "";
        this.parameterid = "";
        this.parametername = "";
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

    public String getClassfieldname() {
        return classfieldname;
    }

    public void setClassfieldname(String classfieldname) {
        this.classfieldname = classfieldname;
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

    public SelectBean<DatSampleParameter> getItem() {
        if (item == null)
            item = new SelectBean<DatSampleParameter>();

        return item;
    }

    public void setItem(SelectBean<DatSampleParameter> item) {
        this.item = item;
    }

}
