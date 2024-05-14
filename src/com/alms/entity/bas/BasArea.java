package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.ToolUtils;

public class BasArea implements BaseBean {

    // 地区编号;
    private String areaid;

    // 地区名称;
    private String areaname;

    // 所属城市编号;
    private String cityid;

    // 所属城市名称;
    private String cityname;

    private DataDeal deal;

    private SelectBean<BasArea> item;

    private SearchParams search;

    public BasArea() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getAreaname())) {
            msg.setErrmsg("请输入地区名称！" + ToolUtils.GetNewLines());
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
        this.areaid = "";
        this.areaname = "";
        this.cityid = "";
        this.cityname = "";
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
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
        return new String[] { "地区编号:areaid", "地区名称:areaname", "城市编号:cityid", "城市名称:cityname" };
    }

}
