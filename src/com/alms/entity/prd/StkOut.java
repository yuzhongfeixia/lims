package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkOut implements BaseBean {

    // 出库单号;
    private String tranid;

    // 实际单据号;
    private String outfact;

    // 仓库编号;
    private String storeid;

    // 仓库名称;
    private String storename;

    // 出库日期;
    private java.util.Date outdate;

    // 出库类型;
    private String stockouttype;

    // 出库类型名称;
    private String stockouttypename;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 领出人;
    private String outuser;

    // 领出人姓名;
    private String outusername;

    // 审核人;
    private String checkuser;

    // 审核人姓名;
    private String checkusername;

    // 审核人说明;
    private String checkdesc;

    // 记帐员;
    private String recouser;

    // 记帐员姓名;
    private String recousername;

    // 记录日期;
    private java.util.Date recodate;

    // 备注;
    private String remark;

    // 机构编号;
    private String deptid;

    // 物品编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 出库总数;
    private double factnumber;

    // 计量单位
    private String prdunitname;

    // 开始检测日期;
    private String begindate;

    // 结束检测日期;
    private String enddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkOut> item;

    public StkOut() {
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
        return ToolUtils.CompareProperty((StkOut) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "物品编号:prdid", "物品名称:prdname", "出库总数:factnumber", "单位:prdunitname", "出库单号:tranid",
                "机构编号:deptid", "实际单据号:outfact", "仓库编号:storeid", "仓库名称:storename", "出库日期:outdate", "出库类型:stockouttype",
                "出库类型名称:stockouttypename", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "领出人:outuser", "领出人姓名:outusername", "审核人:checkuser", "审核人姓名:checkusername",
                "审核人说明:checkdesc", "记帐员:recouser", "记帐员姓名:recousername", "记录日期:recodate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.outfact = "";
        this.storeid = "";
        this.storename = "";
        this.outdate = ToolUtils.GetMinDate();
        this.stockouttype = "";
        this.stockouttypename = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.outuser = "";
        this.outusername = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdesc = "";
        this.recouser = "";
        this.recousername = "";
        this.recodate = ToolUtils.GetMinDate();
        this.remark = "";
        this.deptid = "";
        this.prdid = "";
        this.prdname = "";
        this.factnumber = 0;
        this.prdunitname = "";
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public double getFactnumber() {
        return factnumber;
    }

    public void setFactnumber(double factnumber) {
        this.factnumber = factnumber;
    }

    public String getPrdunitname() {
        return prdunitname;
    }

    public void setPrdunitname(String prdunitname) {
        this.prdunitname = prdunitname;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getOutfact() {
        return outfact;
    }

    public void setOutfact(String outfact) {
        this.outfact = outfact;
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

    public java.util.Date getOutdate() {
        return outdate;
    }

    public void setOutdate(java.util.Date outdate) {
        this.outdate = outdate;
    }

    public String getStockouttype() {
        return stockouttype;
    }

    public void setStockouttype(String stockouttype) {
        this.stockouttype = stockouttype;
    }

    public String getStockouttypename() {
        return stockouttypename;
    }

    public void setStockouttypename(String stockouttypename) {
        this.stockouttypename = stockouttypename;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getOutuser() {
        return outuser;
    }

    public void setOutuser(String outuser) {
        this.outuser = outuser;
    }

    public String getOutusername() {
        return outusername;
    }

    public void setOutusername(String outusername) {
        this.outusername = outusername;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getRecouser() {
        return recouser;
    }

    public void setRecouser(String recouser) {
        this.recouser = recouser;
    }

    public String getRecousername() {
        return recousername;
    }

    public void setRecousername(String recousername) {
        this.recousername = recousername;
    }

    public java.util.Date getRecodate() {
        return recodate;
    }

    public void setRecodate(java.util.Date recodate) {
        this.recodate = recodate;
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

    public SelectBean<StkOut> getItem() {
        if (item == null)
            item = new SelectBean<StkOut>();

        return item;
    }

    public void setItem(SelectBean<StkOut> item) {
        this.item = item;
    }

}
