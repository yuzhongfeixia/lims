package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskJudge implements BaseBean {

    // 采样编号;
    private String samplecode;

    // 受检单位;
    private String testedname;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 抽样地点;
    private String getaddr;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测数据;
    private Double submitvalue;

    // 判定结果;
    private String actvalue;

    private String sampletype;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskJudge> item;

    public BusTaskJudge() {
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
        return ToolUtils.CompareProperty((BusTaskJudge) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "采样编号:samplecode", "样品类别:sampletype", "受检单位:testedname", "样品编号:sampleid",
                "样品名称:samplename", "抽样地点:getaddr", "检测参数编号:parameterid", "检测参数名称:parametername", "检测数据:submitvalue",
                "判定结果:actvalue" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplecode = "";
        this.sampletype = "";
        this.testedname = "";
        this.sampleid = "";
        this.samplename = "";
        this.getaddr = "";
        this.parameterid = "";
        this.parametername = "";
        this.submitvalue = 0.0;
        this.actvalue = "";
    }

    public String getSampletype() {
        return sampletype;
    }

    public void setSampletype(String sampletype) {
        this.sampletype = sampletype;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
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

    public String getGetaddr() {
        return getaddr;
    }

    public void setGetaddr(String getaddr) {
        this.getaddr = getaddr;
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

    public Double getSubmitvalue() {
        return submitvalue;
    }

    public void setSubmitvalue(Double submitvalue) {
        this.submitvalue = submitvalue;
    }

    public String getActvalue() {
        return actvalue;
    }

    public void setActvalue(String actvalue) {
        this.actvalue = actvalue;
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

    public SelectBean<BusTaskJudge> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskJudge>();

        return item;
    }

    public void setItem(SelectBean<BusTaskJudge> item) {
        this.item = item;
    }

}
