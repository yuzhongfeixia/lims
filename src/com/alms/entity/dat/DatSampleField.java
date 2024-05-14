package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DatSampleField implements BaseBean {

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

    // 类字段来源名称;
    private String classfieldname;

    // 字段类型;
    private String fieldtype;

    // 字段类型名称
    private String fieldtypename;

    // 是否分组;
    private boolean isgroup;

    // 是否序号;
    private boolean isserial;

    // 数据来源;
    private String datasource;

    // 数据来源
    private String datasourcename;

    // 检测参数;
    private String parameterids;

    // 检测参数名称;
    private String parameternames;

    private String parameterid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DatSampleField> item;

    public DatSampleField() {
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
        return ToolUtils.CompareProperty((DatSampleField) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "类来源编号:classsource", "类字段来源编号:classfield", "类字段来源名称:classfieldname",
                "字段类型:fieldtype", "是否分组:isgroup", "是否序号:isserial", "数据来源:datasource", "检测参数:parameterids",
                "检测参数名称:parameternames" };
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
        this.fieldtype = "";
        this.isgroup = false;
        this.isserial = false;
        this.datasource = "";
        this.parameterids = "";
        this.parameternames = "";
        this.fieldtypename = "";
        this.datasourcename = "";
        this.parameterid = "";
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
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

    public String getFieldtypename() {
        return fieldtypename;
    }

    public void setFieldtypename(String fieldtypename) {
        this.fieldtypename = fieldtypename;
    }

    public String getDatasourcename() {
        return datasourcename;
    }

    public void setDatasourcename(String datasourcename) {
        this.datasourcename = datasourcename;
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

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public boolean getIsgroup() {
        return isgroup;
    }

    public void setIsgroup(boolean isgroup) {
        this.isgroup = isgroup;
    }

    public boolean getIsserial() {
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

    public SelectBean<DatSampleField> getItem() {
        if (item == null)
            item = new SelectBean<DatSampleField>();

        return item;
    }

    public void setItem(SelectBean<DatSampleField> item) {
        this.item = item;
    }

}
