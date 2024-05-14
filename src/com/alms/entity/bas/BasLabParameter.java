package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasLabParameter implements BaseBean {

    // 机构编号;
    private String labid;

    // 机构名称;
    private String deptname;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数编号;
    private String parameterid;

    private String parameterids;

    // 检测参数名称;
    private String parametername;

    // 检测室主任;
    private String labuser;

    // 检测室检测样品分类;
    private String labcate;

    // 检测室检测样品分类;
    private String labcatename;

    // 检测室具备的功能;
    private String labremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasLabParameter> item;

    public BasLabParameter() {
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
        return ToolUtils.CompareProperty((BasLabParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "机构编号:labid", "机构名称:deptname", "样品编号:sampleid", "样品名称:samplename", "检测参数编号:parameterid",
                "检测参数名称:parametername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.labid = "";
        this.deptname = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parameterids = "";
        this.parametername = "";
        this.labuser = "";
        this.labcate = "";
        this.labcatename = "";
        this.labremark = "";
    }

    public String getLabuser() {
        return labuser;
    }

    public void setLabuser(String labuser) {
        this.labuser = labuser;
    }

    public String getLabcate() {
        return labcate;
    }

    public void setLabcate(String labcate) {
        this.labcate = labcate;
    }

    public String getLabcatename() {
        return labcatename;
    }

    public void setLabcatename(String labcatename) {
        this.labcatename = labcatename;
    }

    public String getLabremark() {
        return labremark;
    }

    public void setLabremark(String labremark) {
        this.labremark = labremark;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
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

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
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

    public SelectBean<BasLabParameter> getItem() {
        if (item == null)
            item = new SelectBean<BasLabParameter>();

        return item;
    }

    public void setItem(SelectBean<BasLabParameter> item) {
        this.item = item;
    }

}
