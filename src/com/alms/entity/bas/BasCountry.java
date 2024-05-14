package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class BasCountry implements BaseBean {

    // 城市编号;
    private String countryid;

    // 城市名称;
    private String countryname;

    private DataDeal deal;

    private SelectBean<BasCountry> item;

    private SearchParams search;

    public BasCountry() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getCountryname())) {
            msg.setErrmsg("请输入国家名称！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.countryid = "";
        this.countryname = "";
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasCountry> getItem() {
        if (item == null)
            item = new SelectBean<BasCountry>();

        return item;
    }

    public void setItem(SelectBean<BasCountry> item) {
        this.item = item;
    }

    public SearchParams getSearch() {
        if (search == null) {
            search = new SearchParams();
        }
        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BasCountry) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "国家编号:countryid", "国家名称:countryname" };
    }

}
