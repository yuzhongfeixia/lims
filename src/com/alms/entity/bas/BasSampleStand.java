package com.alms.entity.bas;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BasSampleStand implements BaseBean {

    private String sampleid;

    private int standlevel;

    private String standtype;

    private String standtypename;

    private String standlabel;

    private String standid;

    private int samplecount;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasSampleStand> item;

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public int getStandlevel() {
        return standlevel;
    }

    public void setStandlevel(int standlevel) {
        this.standlevel = standlevel;
    }

    public String getStandtype() {
        return standtype;
    }

    public void setStandtype(String standtype) {
        this.standtype = standtype;
    }

    public String getStandtypename() {
        return standtypename;
    }

    public void setStandtypename(String standtypename) {
        this.standtypename = standtypename;
    }

    public String getStandlabel() {
        return standlabel;
    }

    public void setStandlabel(String standlabel) {
        this.standlabel = standlabel;
    }

    public String getStandid() {
        return standid;
    }

    public void setStandid(String standid) {
        this.standid = standid;
    }

    public int getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(int samplecount) {
        this.samplecount = samplecount;
    }

    public BasSampleStand() {
        this.OnInit();
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.standlevel = 0;
        this.standtype = "";
        this.standtypename = "";
        this.standlabel = "";
        this.standid = "";
        this.samplecount = 0;
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
        return ToolUtils.CompareProperty((BasSample) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
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

    public SelectBean<BasSampleStand> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleStand>();

        return item;
    }

    public void setItem(SelectBean<BasSampleStand> item) {
        this.item = item;
    }

}
