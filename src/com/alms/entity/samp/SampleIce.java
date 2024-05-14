package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleIce implements BaseBean {

    // 冰柜编号;
    private String iceid;

    // 冰柜名称;
    private String icename;

    // 冰柜型号;
    private String icespec;

    // 容量;
    private double icecapa;

    // 出厂日期;
    private java.util.Date factorydate;

    // 存放地点;
    private String icestore;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleIce> item;

    public SampleIce() {
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
        return ToolUtils.CompareProperty((SampleIce) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "冰柜编号:iceid", "冰柜名称:icename", "冰柜型号:icespec", "容量:icecapa", "出厂日期:factorydate",
                "存放地点:icestore" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.iceid = "";
        this.icename = "";
        this.icespec = "";
        this.icecapa = 0;
        this.factorydate = ToolUtils.GetMinDate();
        this.icestore = "";
    }

    public String getIceid() {
        return iceid;
    }

    public void setIceid(String iceid) {
        this.iceid = iceid;
    }

    public String getIcename() {
        return icename;
    }

    public void setIcename(String icename) {
        this.icename = icename;
    }

    public String getIcespec() {
        return icespec;
    }

    public void setIcespec(String icespec) {
        this.icespec = icespec;
    }

    public double getIcecapa() {
        return icecapa;
    }

    public void setIcecapa(double icecapa) {
        this.icecapa = icecapa;
    }

    public java.util.Date getFactorydate() {
        return factorydate;
    }

    public void setFactorydate(java.util.Date factorydate) {
        this.factorydate = factorydate;
    }

    public String getIcestore() {
        return icestore;
    }

    public void setIcestore(String icestore) {
        this.icestore = icestore;
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

    public SelectBean<SampleIce> getItem() {
        if (item == null)
            item = new SelectBean<SampleIce>();

        return item;
    }

    public void setItem(SelectBean<SampleIce> item) {
        this.item = item;
    }

}
