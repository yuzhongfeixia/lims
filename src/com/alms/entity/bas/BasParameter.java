package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasParameter implements BaseBean {

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测参数检索;
    private String parametercn;

    // 检测参数英文;
    private String parameteren;

    // 计量单位;
    private String testunit;

    // 检测依据计量单位名称;
    private String testunitname;

    // 检测参数状态;
    private String parameterstatus;

    // 检测参数关联参数名称;
    private String statusname;

    // 关联参数名称;
    private String connectparameter;

    // 关联参数名称;
    private String connectparameterID;

    // 对应的原始记录
    private String formid;

    // 对应的原始记录
    private String formname;

    // 对应的检测室
    private String labid;

    // 对应的检测室
    private String labname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasParameter> item;

    public BasParameter() {
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
        return ToolUtils.CompareProperty((BasParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "检测参数编号:parameterid", "参数状态: parameterstatus", "参数状态名称: statusname",
                "关联参数编号: connectparameterID", "关联参数名称: connectparameter", "对应检测室:labid", "对应检测室:labname",
                "检测参数名称:parametername", "对应原始记录表编号:formid", "对应原始记录表名称:formname", "检测参数检索:parametercn",
                "检测参数英文:parameteren", "计量单位:testunit", "检测依据计量单位名称:testunitname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.parameterid = "";
        this.parametername = "";
        this.parameterstatus = "";
        this.statusname = "";
        this.formid = "";
        this.formname = "";
        this.parametercn = "";
        this.parameteren = "";
        this.testunit = "";
        this.testunitname = "";
        this.labid = "";
        this.labname = "";
        this.connectparameter = "";
        this.connectparameterID = "";
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
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

    public String getParametercn() {
        return parametercn;
    }

    public void setParametercn(String parametercn) {
        this.parametercn = parametercn;
    }

    public String getParameteren() {
        return parameteren;
    }

    public void setParameteren(String parameteren) {
        this.parameteren = parameteren;
    }

    public String getTestunit() {
        return testunit;
    }

    public void setTestunit(String testunit) {
        this.testunit = testunit;
    }

    public String getTestunitname() {
        return testunitname;
    }

    public void setTestunitname(String testunitname) {
        this.testunitname = testunitname;
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

    public SelectBean<BasParameter> getItem() {
        if (item == null)
            item = new SelectBean<BasParameter>();

        return item;
    }

    public void setItem(SelectBean<BasParameter> item) {
        this.item = item;
    }

    public String getParameterstatus() {
        return parameterstatus;
    }

    public void setParameterstatus(String parameterstatus) {
        this.parameterstatus = parameterstatus;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getConnectparameter() {
        return connectparameter;
    }

    public void setConnectparameter(String connectparameter) {
        this.connectparameter = connectparameter;
    }

    public String getConnectparameterID() {
        return connectparameterID;
    }

    public void setConnectparameterID(String connectparameterID) {
        this.connectparameterID = connectparameterID;
    }

}
