package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkOutDetail implements BaseBean {

    // 出库单号;
    private String tranid;

    // 耗材条码;
    private String prdcode;

    // 物品编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 耗材规格
    private String prdstandard;

    // 计量单位
    private String prdunit;

    // 计量单位
    private String prdunitname;

    // 出库数量;
    private double prdnumber;

    // 出库单位数量;
    private double unitnumber;

    // 出库总数;
    private double factnumber;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkOutDetail> item;

    public StkOutDetail() {
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
        return ToolUtils.CompareProperty((StkOutDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "出库单号:tranid", "耗材条码:prdcode", "物品编号:prdid", "试剂耗材名称:prdname", "出库数量:prdnumber",
                "出库单位数量:unitnumber", "出库总数:factnumber", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.prdcode = "";
        this.prdid = "";
        this.prdname = "";
        this.prdstandard = "";
        this.prdunit = "";
        this.prdnumber = 0;
        this.unitnumber = 0;
        this.factnumber = 0;
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<StkOutDetail> getItem() {
        if (item == null)
            item = new SelectBean<StkOutDetail>();

        return item;
    }

    public void setItem(SelectBean<StkOutDetail> item) {
        this.item = item;
    }

}
