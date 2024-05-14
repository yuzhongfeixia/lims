package com.alms.entity.form;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class SendSample implements BaseBean {

    private String month;

    private double growcount;

    private String deptid;

    private String gettype;

    private String begindate;

    private String enddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SendSample> item;

    public SendSample() {
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
        return ToolUtils.CompareProperty((SendSample) item, this, this.OnProperties());
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
        this.month = "";
        this.growcount = 0;
        this.deptid = "";
        this.gettype = "";
        this.begindate = "";
        this.enddate = "";
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getGrowcount() {
        return growcount;
    }

    public void setGrowcount(double growcount) {
        this.growcount = growcount;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
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

    public SelectBean<SendSample> getItem() {
        if (item == null)
            item = new SelectBean<SendSample>();

        return item;
    }

    public void setItem(SelectBean<SendSample> item) {
        this.item = item;
    }
}