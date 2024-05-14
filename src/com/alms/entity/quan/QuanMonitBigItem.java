package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanMonitBigItem implements BaseBean {

    // 大项编号;
    private String bigitemid;

    // 大项名称;
    private String bigitemname;

    // 排序;
    private int orderid;

    // 是否使用;
    private boolean isactive;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanMonitBigItem> item;

    public QuanMonitBigItem() {
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
        return ToolUtils.CompareProperty((QuanMonitBigItem) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "大项编号:bigitemid", "大项名称:bigitemname", "排序:orderid", "是否使用:isactive" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.bigitemid = "";
        this.bigitemname = "";
        this.orderid = 0;
        this.isactive = false;
    }

    public String getBigitemid() {
        return bigitemid;
    }

    public void setBigitemid(String bigitemid) {
        this.bigitemid = bigitemid;
    }

    public String getBigitemname() {
        return bigitemname;
    }

    public void setBigitemname(String bigitemname) {
        this.bigitemname = bigitemname;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
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

    public SelectBean<QuanMonitBigItem> getItem() {
        if (item == null)
            item = new SelectBean<QuanMonitBigItem>();

        return item;
    }

    public void setItem(SelectBean<QuanMonitBigItem> item) {
        this.item = item;
    }

}
