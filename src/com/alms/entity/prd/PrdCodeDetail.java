package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdCodeDetail implements BaseBean {

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

    // 实际数量;
    private double factnumber;

    // 业务类型;
    private String stktype;

    // 业务类型名称;
    private String stktypename;

    // 业务日期;
    private java.util.Date trandate;

    // 业务员;
    private String tranuser;

    // 操作员姓名;
    private String username;

    // 业务机构;
    private String labid;

    // 机构名称;
    private String deptname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdCodeDetail> item;

    public PrdCodeDetail() {
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
        return ToolUtils.CompareProperty((PrdCodeDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "条码编号:prdcode", "物品编号:prdid", "试剂耗材名称:prdname", "数量:prdnumber", "实际数量:factnumber",
                "业务类型:stktype", "业务类型名称:stktypename", "业务日期:trandate", "业务员:tranuser", "操作员姓名:username", "业务机构:labid",
                "机构名称:deptname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.prdcode = "";
        this.prdid = "";
        this.prdname = "";
        this.prdstandard = "";
        this.prdnumber = 0;
        this.factnumber = 0;
        this.stktype = "";
        this.stktypename = "";
        this.trandate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.username = "";
        this.labid = "";
        this.deptname = "";
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

    public double getFactnumber() {
        return factnumber;
    }

    public void setFactnumber(double factnumber) {
        this.factnumber = factnumber;
    }

    public String getStktype() {
        return stktype;
    }

    public void setStktype(String stktype) {
        this.stktype = stktype;
    }

    public String getStktypename() {
        return stktypename;
    }

    public void setStktypename(String stktypename) {
        this.stktypename = stktypename;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
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

    public SelectBean<PrdCodeDetail> getItem() {
        if (item == null)
            item = new SelectBean<PrdCodeDetail>();

        return item;
    }

    public void setItem(SelectBean<PrdCodeDetail> item) {
        this.item = item;
    }

}
