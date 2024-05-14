package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanMonitSamItem implements BaseBean {

    // 小项编号;
    private String samitemid;

    // 大项编号;
    private String bigitemid;

    // 大项名称;
    private String bigitemname;

    // 小项名称;
    private String samitemname;

    // 是否选择型;
    private boolean ischoose;

    // 是否使用;
    private boolean isactive;

    // 排序;
    private int orderid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanMonitSamItem> item;

    public QuanMonitSamItem() {
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
        return ToolUtils.CompareProperty((QuanMonitSamItem) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "小项编号:samitemid", "大项编号:bigitemid", "大项名称:bigitemname", "小项名称:samitemname",
                "是否选择型:ischoose", "是否使用:isactive", "排序:orderid" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samitemid = "";
        this.bigitemid = "";
        this.bigitemname = "";
        this.samitemname = "";
        this.ischoose = false;
        this.isactive = false;
        this.orderid = 0;
    }

    public String getSamitemid() {
        return samitemid;
    }

    public void setSamitemid(String samitemid) {
        this.samitemid = samitemid;
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

    public String getSamitemname() {
        return samitemname;
    }

    public void setSamitemname(String samitemname) {
        this.samitemname = samitemname;
    }

    public boolean getIschoose() {
        return ischoose;
    }

    public void setIschoose(boolean ischoose) {
        this.ischoose = ischoose;
    }

    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public SelectBean<QuanMonitSamItem> getItem() {
        if (item == null)
            item = new SelectBean<QuanMonitSamItem>();

        return item;
    }

    public void setItem(SelectBean<QuanMonitSamItem> item) {
        this.item = item;
    }

}
