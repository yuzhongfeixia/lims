package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusCatalogParam implements BaseBean {

    // 业务编号;
    private String tranid;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 样品大类编号;
    private String samplecatalog;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测依据编号;
    private String teststandard;

    // 检测依据名称;
    private String teststandardname;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据名称;
    private String judgestandardname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusCatalogParam> item;

    public BusCatalogParam() {
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
        return ToolUtils.CompareProperty((BusCatalogParam) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "样品编号:sampleid", "样品名称:samplename", "样品大类编号:samplecatalog",
                "检测参数编号:parameterid", "检测参数名称:parametername", "检测依据编号:teststandard", "检测依据名称:teststandardname",
                "判定依据编号:judgestandard", "判定依据名称:judgestandardname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.sampleid = "";
        this.samplename = "";
        this.samplecatalog = "";
        this.parameterid = "";
        this.parametername = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.judgestandard = "";
        this.judgestandardname = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getSamplecatalog() {
        return samplecatalog;
    }

    public void setSamplecatalog(String samplecatalog) {
        this.samplecatalog = samplecatalog;
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

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
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

    public SelectBean<BusCatalogParam> getItem() {
        if (item == null)
            item = new SelectBean<BusCatalogParam>();

        return item;
    }

    public void setItem(SelectBean<BusCatalogParam> item) {
        this.item = item;
    }

}
