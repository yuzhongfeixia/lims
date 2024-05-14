package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSampleParameter implements BaseBean {

    // 样品编号;
    private String sampleid;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 测定值;
    private String standvalue;

    // 判定;
    private String testjudge;

    // 标准值单位;
    private String paramunit;

    // 规格属性;
    private String belongtype;

    // 规格属性;
    private String belongtypename;

    // 规格1;
    private String standtype1;

    // 规格2;
    private String standtype2;

    // 规格3;
    private String standtype3;

    // 规格4;
    private String standtype4;

    // 规格5;
    private String standtype5;

    private String teststandard;

    private String teststandardcode;

    private String teststandardname;

    private DataDeal deal;

    private SelectBean<BasSampleParameter> item;

    public BasSampleParameter() {
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
        return ToolUtils.CompareProperty((BasSampleParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "检测参数编号:parameterid", "检测参数名称:parametername", "判定:testjudge",
                "标准值单位:paramunit", "测定值:samplevalue", "判定:testjudgename", "标准值单位:paramunitname", "规格属性:belongtype",
                "规格1:standtype1", "规格2:standtype2", "规格3:standtype3", "规格4:standtype4", "规格5:standtype5",
                "规格属性:belongtypename", "标准编号:teststandard", "标准代号:teststandardcode", "标准名称:teststandardname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.testjudge = "";
        this.parameterid = "";
        this.parametername = "";
        this.paramunit = "";
        this.standvalue = "";
        this.belongtype = "";
        this.belongtypename = "";
        this.standtype1 = "";
        this.standtype2 = "";
        this.standtype3 = "";
        this.standtype4 = "";
        this.standtype5 = "";
        this.teststandard = "";
        this.teststandardcode = "";
        this.teststandardname = "";
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

    public String getBelongtypename() {
        return belongtypename;
    }

    public void setBelongtypename(String belongtypename) {
        this.belongtypename = belongtypename;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
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

    public String getStandvalue() {
        return standvalue;
    }

    public void setStandvalue(String standvalue) {
        this.standvalue = standvalue;
    }

    public String getTestjudge() {
        return testjudge;
    }

    public void setTestjudge(String testjudge) {
        this.testjudge = testjudge;
    }

    public String getParamunit() {
        return paramunit;
    }

    public void setParamunit(String paramunit) {
        this.paramunit = paramunit;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasSampleParameter> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleParameter>();

        return item;
    }

    public void setItem(SelectBean<BasSampleParameter> item) {
        this.item = item;
    }

    public String getBelongtype() {
        return belongtype;
    }

    public void setBelongtype(String belongtype) {
        this.belongtype = belongtype;
    }

    public String getStandtype1() {
        return standtype1;
    }

    public void setStandtype1(String standtype1) {
        this.standtype1 = standtype1;
    }

    public String getStandtype2() {
        return standtype2;
    }

    public void setStandtype2(String standtype2) {
        this.standtype2 = standtype2;
    }

    public String getStandtype3() {
        return standtype3;
    }

    public void setStandtype3(String standtype3) {
        this.standtype3 = standtype3;
    }

    public String getStandtype4() {
        return standtype4;
    }

    public void setStandtype4(String standtype4) {
        this.standtype4 = standtype4;
    }

    public String getStandtype5() {
        return standtype5;
    }

    public void setStandtype5(String standtype5) {
        this.standtype5 = standtype5;
    }

}
