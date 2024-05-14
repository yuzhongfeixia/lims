package com.alms.entity.lab;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BusConsignSample implements BaseBean {

    private String samplecode;

    // 业务编号(委托编号);
    private String tranid;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 生产厂家编号;
    private String factid;

    // 生产厂家名称;
    private String factname;

    // 商标;
    private String trademark;

    // 规格型号;
    private String samplestand;

    // 样品等级;
    private String samplelvl;

    // 样品数量;
    private int samplecount;

    // 样品状态;
    private String samplestatus;

    // 生产批号;
    private String prdcode;

    private String getaddr;

    private String samplebase;

    private String parameterids;

    private String parameternames;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusConsignSample> item;

    public BusConsignSample() {
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
        return ToolUtils.CompareProperty((BusConsignSample) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号(委托编号):tranid", "样品编号:sampleid", "样品名称:samplename", "生产厂家编号:factid",
                "生产厂家名称:factname", "商标:trademark", "规格型号:samplestand", "样品等级:samplelvl", "样品数量:samplecount",
                "样品状态:samplestatus", "生产批号:prdcode" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplecode = "";
        this.tranid = "";
        this.sampleid = "";
        this.samplename = "";
        this.factid = "";
        this.factname = "";
        this.trademark = "";
        this.samplestand = "";
        this.samplelvl = "";
        this.samplecount = 0;
        this.samplestatus = "";
        this.prdcode = "";
        this.parameterids = "";
        this.parameternames = "";
        this.getaddr = "";
        this.samplebase = "";
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
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

    public String getFactid() {
        return factid;
    }

    public void setFactid(String factid) {
        this.factid = factid;
    }

    public String getFactname() {
        return factname;
    }

    public void setFactname(String factname) {
        this.factname = factname;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getSamplestand() {
        return samplestand;
    }

    public void setSamplestand(String samplestand) {
        this.samplestand = samplestand;
    }

    public String getSamplelvl() {
        return samplelvl;
    }

    public void setSamplelvl(String samplelvl) {
        this.samplelvl = samplelvl;
    }

    public int getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(int samplecount) {
        this.samplecount = samplecount;
    }

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    public String getPrdcode() {
        return prdcode;
    }

    public void setPrdcode(String prdcode) {
        this.prdcode = prdcode;
    }

    public String getGetaddr() {
        return getaddr;
    }

    public void setGetaddr(String getaddr) {
        this.getaddr = getaddr;
    }

    public String getSamplebase() {
        return samplebase;
    }

    public void setSamplebase(String samplebase) {
        this.samplebase = samplebase;
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

    public SelectBean<BusConsignSample> getItem() {
        if (item == null)
            item = new SelectBean<BusConsignSample>();

        return item;
    }

    public void setItem(SelectBean<BusConsignSample> item) {
        this.item = item;
    }

}
