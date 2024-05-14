package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdPoisonDetail implements BaseBean {

    // 申请编号;
    private String tranid;

    // 明细序号;
    private int prdserial;

    // 耗材编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 耗材使用量;
    private double prdcount;

    // 数量单位;
    private String prdunit;

    // 计量单位名称;
    private String prdunitname;

    // 备注;
    private String poiremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdPoisonDetail> item;

    public PrdPoisonDetail() {
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
        return ToolUtils.CompareProperty((PrdPoisonDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "明细序号:prdserial", "耗材编号:prdid", "试剂耗材名称:prdname", "耗材使用量:prdcount",
                "数量单位:prdunit", "计量单位名称:prdunitname", "备注:poiremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.prdserial = 0;
        this.prdid = "";
        this.prdname = "";
        this.prdcount = 0;
        this.prdunit = "";
        this.prdunitname = "";
        this.poiremark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getPrdserial() {
        return prdserial;
    }

    public void setPrdserial(int prdserial) {
        this.prdserial = prdserial;
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

    public double getPrdcount() {
        return prdcount;
    }

    public void setPrdcount(double prdcount) {
        this.prdcount = prdcount;
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

    public String getPoiremark() {
        return poiremark;
    }

    public void setPoiremark(String poiremark) {
        this.poiremark = poiremark;
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

    public SelectBean<PrdPoisonDetail> getItem() {
        if (item == null)
            item = new SelectBean<PrdPoisonDetail>();

        return item;
    }

    public void setItem(SelectBean<PrdPoisonDetail> item) {
        this.item = item;
    }

}
