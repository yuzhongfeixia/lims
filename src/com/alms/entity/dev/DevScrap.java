package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevScrap implements BaseBean {

    // 申请编号;
    private String tranid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 设备状态编号
    private String devstatus;

    // 设备状态名称
    private String devstatusname;

    // 生产厂家
    private String factoryname;

    // 设备型号
    private String devstandard;

    // 出厂编号
    private String factorycode;

    // 业务动作;
    private String flowaction;

    // 业务状态;
    private String flowstatus;

    // 申请事由;
    private String applyreason;

    // 申请部门;
    private String trandept;

    // 申请部门名称;
    private String trandeptname;

    // 申请负责人;
    private String tranuser;

    // 申请负责人姓名;
    private String tranusername;

    // 申请日期;
    private java.util.Date trandate;

    // 鉴定意见;
    private String auditdesc;

    // 鉴定部门;
    private String auditdept;

    // 鉴定部门名称;
    private String auditdeptname;

    // 鉴定人;
    private String audituser;

    // 鉴定人姓名;
    private String auditusername;

    // 鉴定日期;
    private java.util.Date auditdate;

    // 设备管理员意见;
    private String checkdesc;

    // 设备管理员;
    private String checkuser;

    // 设备管理员姓名;
    private String checkusername;

    // 设备管理员日期;
    private java.util.Date checkdate;

    // 批准意见;
    private String approvedesc;

    // 批准人(主任或副主任);
    private String approveuser;

    // 批准人姓名;
    private String approveusername;

    // 批准日期;
    private java.util.Date approvedate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevScrap> item;

    public DevScrap() {
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
        return ToolUtils.CompareProperty((DevScrap) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "设备编号:devid", "设备名称:devname", "设备状态:devstatus", "设备状态名称:devstatusname",
                "生产厂家:factoryname", "设备型号:devstandard", "出厂编号:factorycode", "业务动作:flowaction", "业务状态:flowstatus",
                "申请事由:applyreason", "申请部门:trandept", "申请部门名称:trandeptname", "申请负责人:tranuser", "申请负责人姓名:tranusername",
                "申请日期:trandate", "鉴定意见:auditdesc", "鉴定部门:auditdept", "鉴定部门名称:auditdeptname", "鉴定人:audituser",
                "鉴定人姓名:auditusername", "鉴定日期:auditdate", "设备管理员意见:checkdesc", "设备管理员:checkuser",
                "设备管理员姓名:checkusername", "设备管理员日期:checkdate", "批准意见:approvedesc", "批准人(主任或副主任):approveuser",
                "批准人姓名:approveusername", "批准日期:approvedate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.devid = "";
        this.devname = "";
        this.devstatus = "";
        this.devstatusname = "";
        this.factoryname = "";
        this.devstandard = "";
        this.factorycode = "";
        this.flowaction = "";
        this.flowstatus = "";
        this.applyreason = "";
        this.trandept = "";
        this.trandeptname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.auditdept = "";
        this.auditdeptname = "";
        this.audituser = "";
        this.auditusername = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.checkdesc = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.approvedesc = "";
        this.approveuser = "";
        this.approveusername = "";
        this.approvedate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getDevstatusname() {
        return devstatusname;
    }

    public void setDevstatusname(String devstatusname) {
        this.devstatusname = devstatusname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
    }

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getApplyreason() {
        return applyreason;
    }

    public void setApplyreason(String applyreason) {
        this.applyreason = applyreason;
    }

    public String getTrandept() {
        return trandept;
    }

    public void setTrandept(String trandept) {
        this.trandept = trandept;
    }

    public String getTrandeptname() {
        return trandeptname;
    }

    public void setTrandeptname(String trandeptname) {
        this.trandeptname = trandeptname;
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

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc;
    }

    public String getAuditdept() {
        return auditdept;
    }

    public void setAuditdept(String auditdept) {
        this.auditdept = auditdept;
    }

    public String getAuditdeptname() {
        return auditdeptname;
    }

    public void setAuditdeptname(String auditdeptname) {
        this.auditdeptname = auditdeptname;
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

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
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

    public String getApprovedesc() {
        return approvedesc;
    }

    public void setApprovedesc(String approvedesc) {
        this.approvedesc = approvedesc;
    }

    public String getApproveuser() {
        return approveuser;
    }

    public void setApproveuser(String approveuser) {
        this.approveuser = approveuser;
    }

    public String getApproveusername() {
        return approveusername;
    }

    public void setApproveusername(String approveusername) {
        this.approveusername = approveusername;
    }

    public java.util.Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(java.util.Date approvedate) {
        this.approvedate = approvedate;
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

    public SelectBean<DevScrap> getItem() {
        if (item == null)
            item = new SelectBean<DevScrap>();

        return item;
    }

    public void setItem(SelectBean<DevScrap> item) {
        this.item = item;
    }

}
