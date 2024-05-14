package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanControlSamp implements BaseBean {

    // 评价编号;
    private String tranid;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 样品状态;
    private String samplestatus;

    // 样品来源;
    private String samplesource;

    // 使用标准或方法;
    private String usestdmethod;

    // 测试/验证结果;
    private String testresult;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanControlSamp> item;

    public QuanControlSamp() {
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
        return ToolUtils.CompareProperty((QuanControlSamp) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "评价编号:tranid", "样品编号:sampleid", "样品名称:samplename", "样品状态:samplestatus",
                "样品来源:samplesource", "使用标准或方法:usestdmethod", "测试/验证结果:testresult" };
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
        this.samplestatus = "";
        this.samplesource = "";
        this.usestdmethod = "";
        this.testresult = "";
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

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    public String getSamplesource() {
        return samplesource;
    }

    public void setSamplesource(String samplesource) {
        this.samplesource = samplesource;
    }

    public String getUsestdmethod() {
        return usestdmethod;
    }

    public void setUsestdmethod(String usestdmethod) {
        this.usestdmethod = usestdmethod;
    }

    public String getTestresult() {
        return testresult;
    }

    public void setTestresult(String testresult) {
        this.testresult = testresult;
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

    public SelectBean<QuanControlSamp> getItem() {
        if (item == null)
            item = new SelectBean<QuanControlSamp>();

        return item;
    }

    public void setItem(SelectBean<QuanControlSamp> item) {
        this.item = item;
    }

}
