package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanMonitOption implements BaseBean {

    // 选择项编号;
    private String optionid;

    // 大项编号;
    private String bigitemid;

    // 大项名称;
    private String bigitemname;

    // 小项编号;
    private String samitemid;

    // 小项名称;
    private String samitemname;

    // 选择项名称;
    private String optionname;

    // 是否使用;
    private boolean isactive;

    // 排序;
    private int orderid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanMonitOption> item;

    public QuanMonitOption() {
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
        return ToolUtils.CompareProperty((QuanMonitOption) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "选择项编号:optionid", "大项编号:bigitemid", "大项名称:bigitemname", "小项编号:samitemid",
                "小项名称:samitemname", "选择项名称:optionname", "是否使用:isactive", "排序:orderid" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.optionid = "";
        this.bigitemid = "";
        this.bigitemname = "";
        this.samitemid = "";
        this.samitemname = "";
        this.optionname = "";
        this.isactive = false;
        this.orderid = 0;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
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

    public String getSamitemid() {
        return samitemid;
    }

    public void setSamitemid(String samitemid) {
        this.samitemid = samitemid;
    }

    public String getSamitemname() {
        return samitemname;
    }

    public void setSamitemname(String samitemname) {
        this.samitemname = samitemname;
    }

    public String getOptionname() {
        return optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
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

    public SelectBean<QuanMonitOption> getItem() {
        if (item == null)
            item = new SelectBean<QuanMonitOption>();

        return item;
    }

    public void setItem(SelectBean<QuanMonitOption> item) {
        this.item = item;
    }

}
