package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkInDetail implements BaseBean {

    // 入库单号;
    private String tranid;

    // 条码前缀;
    private String prdcodeprefix;

    // 物品编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 物品规格
    private String prdstandard;

    // 入库单位
    private String prdunit;

    // 入库单位名称
    private String prdunitname;

    // 入库数量;
    private double prdnumber;

    // 入库单价;
    private double prdprice;

    // 单位数量;
    private double unitnumber;

    // 入库总数;
    private double factnumber;

    // 入库金额;
    private double prdamount;

    // 供应商编号;
    private String tradeid;

    // 供应商名称
    private String tradename;

    // 生产厂商;
    private String factoryname;

    // 生产日期;
    private java.util.Date factorydate;

    // 有效期;
    private int validdate;

    // 购买人;
    private String buyuser;

    private String prdtype;

    private String prdtypename;

    // 购买人姓名;
    private String buyusername;

    // 购买日期;
    private java.util.Date buydate;

    // 备注;
    private String remark;

    private String verifyid;

    // 物品购置申请序号
    private int prdserial;

    // 物品购置申请编号
    private String applyid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkInDetail> item;

    public StkInDetail() {
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
        return ToolUtils.CompareProperty((StkInDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "物品购置申请序号:prdserial", "物品购置申请编号:applyid", "入库单号:tranid", "条码前缀:prdcodeprefix",
                "物品编号:prdid", "试剂耗材名称:prdname", "入库数量:prdnumber", "入库单价:prdprice", "单位数量:unitnumber", "入库总数:factnumber",
                "入库金额:prdamount", "供应商编号:tradeid", "生产厂商:factoryname", "生产日期:factorydate", "有效期:validdate",
                "购买人:buyuser", "购买人姓名:buyusername", "购买日期:buydate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.prdcodeprefix = "";
        this.prdid = "";
        this.prdname = "";
        this.prdunit = "";
        this.prdstandard = "";
        this.prdnumber = 0;
        this.prdprice = 0;
        this.unitnumber = 0;
        this.factnumber = 0;
        this.prdamount = 0;
        this.tradeid = "";
        this.factoryname = "";
        this.factorydate = ToolUtils.GetMinDate();
        this.validdate = 0;
        this.buyuser = "";
        this.buyusername = "";
        this.buydate = ToolUtils.GetMinDate();
        this.remark = "";
        this.verifyid = "";
        this.prdtype = "";
        this.prdtypename = "";
        this.prdserial = 0;
        this.applyid = "";
    }

    public int getPrdserial() {
        return prdserial;
    }

    public void setPrdserial(int prdserial) {
        this.prdserial = prdserial;
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public String getPrdtypename() {
        return prdtypename;
    }

    public void setPrdtypename(String prdtypename) {
        this.prdtypename = prdtypename;
    }

    public String getPrdtype() {
        return prdtype;
    }

    public void setPrdtype(String prdtype) {
        this.prdtype = prdtype;
    }

    public String getVerifyid() {
        return verifyid;
    }

    public void setVerifyid(String verifyid) {
        this.verifyid = verifyid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getPrdcodeprefix() {
        return prdcodeprefix;
    }

    public void setPrdcodeprefix(String prdcodeprefix) {
        this.prdcodeprefix = prdcodeprefix;
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

    public double getPrdprice() {
        return prdprice;
    }

    public void setPrdprice(double prdprice) {
        this.prdprice = prdprice;
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

    public double getPrdamount() {
        return prdamount;
    }

    public void setPrdamount(double prdamount) {
        this.prdamount = prdamount;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getTradename() {
        return tradename;
    }

    public void setTradename(String tradename) {
        this.tradename = tradename;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public java.util.Date getFactorydate() {
        return factorydate;
    }

    public void setFactorydate(java.util.Date factorydate) {
        this.factorydate = factorydate;
    }

    public int getValiddate() {
        return validdate;
    }

    public void setValiddate(int validdate) {
        this.validdate = validdate;
    }

    public String getBuyuser() {
        return buyuser;
    }

    public void setBuyuser(String buyuser) {
        this.buyuser = buyuser;
    }

    public String getBuyusername() {
        return buyusername;
    }

    public void setBuyusername(String buyusername) {
        this.buyusername = buyusername;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
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

    public SelectBean<StkInDetail> getItem() {
        if (item == null)
            item = new SelectBean<StkInDetail>();

        return item;
    }

    public void setItem(SelectBean<StkInDetail> item) {
        this.item = item;
    }

}
