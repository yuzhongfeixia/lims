package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdCode implements BaseBean {

    // 条码编号;
    private String prdcode;

    // 物品编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 规格型号
    private String prdstandard;

    // 计量单位
    private String prdunit;

    // 计量单位名称
    private String prdunitname;

    // 数量;
    private double prdnumber;

    // 单位数量;
    private double unitnumber;

    // 实际数量;
    private double factnumber;

    // 剩余数量;
    private double lastnumber;

    // 购买日期;
    private java.util.Date buydate;

    // 有效期;
    private int validmonth;

    // 仓库编号;
    private String storeid;

    // 仓库名称;
    private String storename;

    // 最低库存量
    private String prdmin;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdCode> item;

    public PrdCode() {
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
        return ToolUtils.CompareProperty((PrdCode) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "条码编号:prdcode", "物品编号:prdid", "试剂耗材名称:prdname", "数量:prdnumber", "单位数量:unitnumber",
                "实际数量:factnumber", "剩余数量:lastnumber", "购买日期:buydate", "有效期:validmonth", "仓库编号:storeid",
                "仓库名称:storename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.prdcode = "";
        this.prdid = "";
        this.prdstandard = "";
        this.prdunit = "";
        this.prdname = "";
        this.prdnumber = 0;
        this.unitnumber = 0;
        this.factnumber = 0;
        this.lastnumber = 0;
        this.buydate = ToolUtils.GetMinDate();
        this.validmonth = 0;
        this.storeid = "";
        this.storename = "";
        this.prdmin = "";
    }

    public String getPrdmin() {
        return prdmin;
    }

    public void setPrdmin(String prdmin) {
        this.prdmin = prdmin;
    }

    public String getPrdcode() {
        return prdcode;
    }

    public void setPrdcode(String prdcode) {
        this.prdcode = prdcode;
    }

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getPrdstandard() {
        return prdstandard;
    }

    public void setPrdstandard(String prdstandard) {
        this.prdstandard = prdstandard;
    }

    public String getPrdunit() {
        return prdunit;
    }

    public void setPrdunit(String prdunit) {
        this.prdunit = prdunit;
    }

    public String getPrdunitname() {
        return prdunitname;
    }

    public void setPrdunitname(String prdunitname) {
        this.prdunitname = prdunitname;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public double getPrdnumber() {
        return prdnumber;
    }

    public void setPrdnumber(double prdnumber) {
        this.prdnumber = prdnumber;
    }

    public double getUnitnumber() {
        return unitnumber;
    }

    public void setUnitnumber(double unitnumber) {
        this.unitnumber = unitnumber;
    }

    public double getFactnumber() {
        return factnumber;
    }

    public void setFactnumber(double factnumber) {
        this.factnumber = factnumber;
    }

    public double getLastnumber() {
        return lastnumber;
    }

    public void setLastnumber(double lastnumber) {
        this.lastnumber = lastnumber;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
    }

    public int getValidmonth() {
        return validmonth;
    }

    public void setValidmonth(int validmonth) {
        this.validmonth = validmonth;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
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

    public SelectBean<PrdCode> getItem() {
        if (item == null)
            item = new SelectBean<PrdCode>();

        return item;
    }

    public void setItem(SelectBean<PrdCode> item) {
        this.item = item;
    }

}
