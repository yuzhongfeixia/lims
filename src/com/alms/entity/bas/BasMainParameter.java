package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasMainParameter implements BaseBean {

    // 样品主类编号;
    private String samplemain;

    // 样品主类名称;
    private String samplemainname;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 参数单位;
    private String paramunit;
    // 参数检测限;
    private String deteclimit;
    // 检测参数排序
    private int parameterorder;

    public String getDeteclimit() {
        return deteclimit;
    }

    public void setDeteclimit(String deteclimit) {
        this.deteclimit = deteclimit;
    }

    // 判定;
    private String testjudge;

    // 标准值;
    private String standvalue;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据编码;
    private String judgestandardcode;

    // 判定依据名称;
    private String judgestandardname;

    // 检测依据编号;
    private String teststandard;

    // 检测依据编码;
    private String teststandardcode;

    // 检测依据名称;
    private String teststandardname;

    // 所属类型;
    private String belongtype;

    // 所属类型;
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

    private String sampleid;

    private String labid;

    private String samplecode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasMainParameter> item;

    public BasMainParameter() {
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
        return ToolUtils.CompareProperty((BasMainParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品主类编号:samplemain", "样品主类名称:samplemainname", "检测限:deteclimit", "检测参数排序:parameterorder",
                "检测参数编号:parameterid", "检测参数名称:parametername", "参数单位:paramunit", "判定:testjudge", "标准值:standvalue",
                "判定依据编号:judgestandard", "判定依据编码:judgestandardcode", "判定依据名称:judgestandardname", "检测依据编号:teststandard",
                "检测依据编码:teststandardcode", "检测依据名称:teststandardname", "所属类型:belongtype", "所属类型:belongtypename",
                "规格1:standtype1", "规格2:standtype2", "规格3:standtype3", "规格4:standtype4", "规格5:standtype5" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplemain = "";
        this.samplemainname = "";
        this.parameterid = "";
        this.parametername = "";
        this.paramunit = "";
        this.deteclimit = "";
        this.setParameterorder(0);
        this.testjudge = "";
        this.standvalue = "";
        this.judgestandard = "";
        this.judgestandardcode = "";
        this.judgestandardname = "";
        this.teststandard = "";
        this.teststandardcode = "";
        this.teststandardname = "";
        this.belongtype = "";
        this.belongtypename = "";
        this.standtype1 = "";
        this.standtype2 = "";
        this.standtype3 = "";
        this.standtype4 = "";
        this.standtype5 = "";
        this.sampleid = "";
        this.labid = "";
        this.samplecode = "";
    }

    public String getBelongtypename() {
        return belongtypename;
    }

    public void setBelongtypename(String belongtypename) {
        this.belongtypename = belongtypename;
    }

    public String getSamplemain() {
        return samplemain;
    }

    public void setSamplemain(String samplemain) {
        this.samplemain = samplemain;
    }

    public String getSamplemainname() {
        return samplemainname;
    }

    public void setSamplemainname(String samplemainname) {
        this.samplemainname = samplemainname;
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

    public String getParamunit() {
        return paramunit;
    }

    public void setParamunit(String paramunit) {
        this.paramunit = paramunit;
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

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
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

    public SelectBean<BasMainParameter> getItem() {
        if (item == null)
            item = new SelectBean<BasMainParameter>();

        return item;
    }

    public void setItem(SelectBean<BasMainParameter> item) {
        this.item = item;
    }

    public int getParameterorder() {
        return parameterorder;
    }

    public void setParameterorder(int parameterorder) {
        this.parameterorder = parameterorder;
    }

}
