package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdVerify implements BaseBean {

    // 验证编号;
    private String verifyid;

    // 申报编号;
    private String tranid;

    // 试剂耗材编号;
    private String prdid;

    // 试剂耗材名称
    private String prdname;

    // 规格型号;
    private String prdstandard;

    // 生产厂家;
    private String factoryname;

    // 出厂批号;
    private String factorycode;

    private String prdunit;

    private String prdunitname;

    private String prdtype;

    // 供应商;
    private String tradeid;

    private String tradename;

    // 进货时间;
    private java.util.Date buydate;

    // 进货量;
    private double buycount;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务人员;
    private String tranuser;

    // 业务人员姓名;
    private String tranusername;

    // 业务时间;
    private java.util.Date trandate;

    // 办公室负责人;
    private String audituser;

    // 办公室负责人姓名;
    private String auditusername;

    // 办公室审核时间;
    private java.util.Date auditdate;

    // 办公室审核意见;
    private String auditdesc;

    // 检测室负责人;
    private String comfirmuser;

    // 检测室负责人姓名;
    private String comfirmusername;

    // 检测室时间;
    private java.util.Date comfirmdate;

    // 检测室意见;
    private String comfirmdesc;

    // 主管;
    private String checkuser;

    // 主管姓名;
    private String checkusername;

    // 主管时间;
    private java.util.Date checkdate;

    // 主管意见;
    private String checkdesc;

    // 备注;
    private String remark;

    private int prdserial;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdVerify> item;

    public PrdVerify() {
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
        return ToolUtils.CompareProperty((PrdVerify) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "验证编号:verifyid", "耗材序号:prdserial", "申报编号:tranid", "试剂耗材编号:prdid", "规格型号:prdstandard",
                "生产厂家:factoryname", "出厂批号:factorycode", "供应商:tradeid", "进货时间:buydate", "进货量:buycount",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务人员:tranuser",
                "业务人员姓名:tranusername", "验证意见:trandesc", "办公室负责人:audituser", "办公室负责人姓名:auditusername",
                "办公室审核时间:auditdate", "办公室审核意见:auditdesc", "检测室负责人:comfirmuser", "检测室负责人姓名:comfirmusername",
                "检测室时间:comfirmdate", "检测室意见:comfirmdesc", "主管:checkuser", "主管姓名:checkusername", "主管时间:checkdate",
                "主管意见:checkdesc", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.verifyid = "";
        this.tranid = "";
        this.prdid = "";
        this.prdstandard = "";
        this.factoryname = "";
        this.factorycode = "";
        this.tradeid = "";
        this.buydate = ToolUtils.GetMinDate();
        this.buycount = 0;
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        // this.trandate = ToolUtils.GetMinDate();
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.comfirmuser = "";
        this.comfirmusername = "";
        // this.comfirmdate = ToolUtils.GetMinDate();
        this.comfirmdesc = "";
        this.checkuser = "";
        this.checkusername = "";
        // this.checkdate = ToolUtils.GetMinDate();
        this.checkdesc = "";
        this.remark = "";
        this.prdname = "";
        this.prdunit = "";
        this.prdunitname = "";
        this.prdserial = 0;
    }

    public int getPrdserial() {
        return prdserial;
    }

    public void setPrdserial(int prdserial) {
        this.prdserial = prdserial;
    }

    public String getPrdunitname() {
        return prdunitname;
    }

    public void setPrdunitname(String prdunitname) {
        this.prdunitname = prdunitname;
    }

    public String getTradename() {
        return tradename;
    }

    public void setTradename(String tradename) {
        this.tradename = tradename;
    }

    public String getPrdunit() {
        return prdunit;
    }

    public void setPrdunit(String prdunit) {
        this.prdunit = prdunit;
    }

    public String getPrdtype() {
        return prdtype;
    }

    public void setPrdtype(String prdtype) {
        this.prdtype = prdtype;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
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

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
    }

    public double getBuycount() {
        return buycount;
    }

    public void setBuycount(double buycount) {
        this.buycount = buycount;
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

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getAudituser() {
        return audituser;
    }

    public void setAudituser(String audituser) {
        this.audituser = audituser;
    }

    public String getAuditusername() {
        return auditusername;
    }

    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername;
    }

    public java.util.Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(java.util.Date auditdate) {
        this.auditdate = auditdate;
    }

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc;
    }

    public String getComfirmuser() {
        return comfirmuser;
    }

    public void setComfirmuser(String comfirmuser) {
        this.comfirmuser = comfirmuser;
    }

    public String getComfirmusername() {
        return comfirmusername;
    }

    public void setComfirmusername(String comfirmusername) {
        this.comfirmusername = comfirmusername;
    }

    public java.util.Date getComfirmdate() {
        return comfirmdate;
    }

    public void setComfirmdate(java.util.Date comfirmdate) {
        this.comfirmdate = comfirmdate;
    }

    public String getComfirmdesc() {
        return comfirmdesc;
    }

    public void setComfirmdesc(String comfirmdesc) {
        this.comfirmdesc = comfirmdesc;
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

    public java.util.Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(java.util.Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
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

    public SelectBean<PrdVerify> getItem() {
        if (item == null)
            item = new SelectBean<PrdVerify>();

        return item;
    }

    public void setItem(SelectBean<PrdVerify> item) {
        this.item = item;
    }

}
