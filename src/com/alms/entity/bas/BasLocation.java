package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class BasLocation implements BaseBean {

    // 地区编号;
    private String locationid;

    // 地区名称;
    private String locationname;

    // 上级地区编号;
    private String locationpid;

    // 地区等级;
    private String locationlevel;

    private DataDeal deal;

    private SelectBean<BasArea> item;

    private SearchParams search;

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getLocationpid() {
        return locationpid;
    }

    public void setLocationpid(String locationpid) {
        this.locationpid = locationpid;
    }

    public BasLocation() {
        this.OnInit();
    }

    @Override
    public void OnInit() {

    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public String OnDebug() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        return rtn;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasArea> getItem() {
        if (item == null)
            item = new SelectBean<BasArea>();

        return item;
    }

    public void setItem(SelectBean<BasArea> item) {
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
        return ToolUtils.CompareProperty((BasArea) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "地区编号:areaid", "地区名称:areaname", "城市编号:cityid", "城市名称:cityname", "省份名称编号:provinceid",
                "省份名称:provincename", "国家编号:countryid", "国家名称:countryname" };
    }

    public String getLocationlevel() {
        return locationlevel;
    }

    public void setLocationlevel(String locationlevel) {
        this.locationlevel = locationlevel;
    }

}
