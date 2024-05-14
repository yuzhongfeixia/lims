package com.alms.entity.form;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class PrdType implements BaseBean {

    private String prdtypename;

    private double prdcount;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdType> item;

    public PrdType() {
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
        return ToolUtils.CompareProperty((PrdType) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.prdtypename = "";
        this.prdcount = 0;
    }

    public String getPrdtypename() {
        return prdtypename;
    }

    public void setPrdtypename(String prdtypename) {
        this.prdtypename = prdtypename;
    }

    public double getPrdcount() {
        return prdcount;
    }

    public void setPrdcount(double prdcount) {
        this.prdcount = prdcount;
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

    public SelectBean<PrdType> getItem() {
        if (item == null)
            item = new SelectBean<PrdType>();

        return item;
    }

    public void setItem(SelectBean<PrdType> item) {
        this.item = item;
    }
}
