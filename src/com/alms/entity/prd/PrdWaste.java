package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdWaste implements BaseBean {

    // 申请编号;
    private String tranid;

    // 申请人;
    private String tranuser;

    // 申请人姓名;
    private String tranusername;

    // 申请日期;
    private java.util.Date trandate;

    // 科室名称;
    private String labid;

    // 科室名称
    private String deptname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 室主任;
    private String comfirmuser;

    // 室主任姓名;
    private String comfirmusername;

    // 办公室主任;
    private String audituser;

    // 办公室主任姓名;
    private String auditusername;

    // 办公室主任时间;
    private java.util.Date auditdate;

    // 办公室意见;
    private String auditdesc;

    // 分管主任;
    private String checkuser;

    // 分管主任姓名;
    private String checkusername;

    // 分管主任时间;
    private java.util.Date checkdate;

    // 分管主任意见;
    private String checkdesc;

    // 主任;
    private String agreeuser;

    // 主任姓名;
    private String agreeusername;

    // 主任签字日期;
    private java.util.Date agreedate;

    // 废弃物处理单位;
    private String wasterunit;

    // 废弃物处理方签字;
    private String wastersign;

    // 废弃物处理签字日期;
    private java.util.Date wasterdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdWaste> item;

    public PrdWaste() {
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
        return ToolUtils.CompareProperty((PrdWaste) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "申请人:tranuser", "申请人姓名:tranusername", "申请日期:trandate", "科室名称:labid",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "室主任:comfirmuser", "室主任姓名:comfirmusername", "办公室主任:audituser", "办公室主任姓名:auditusername",
                "办公室主任时间:auditdate", "办公室意见:auditdesc", "分管主任:checkuser", "分管主任姓名:checkusername", "分管主任时间:checkdate",
                "分管主任意见:checkdesc", "主任:agreeuser", "主任姓名:agreeusername", "主任签字日期:agreedate", "废弃物处理单位:wasterunit",
                "废弃物处理方签字:wastersign", "废弃物处理签字日期:wasterdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.labid = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.comfirmuser = "";
        this.comfirmusername = "";
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.checkuser = "";
        this.checkusername = "";
        // this.checkdate = ToolUtils.GetMinDate();
        this.checkdesc = "";
        this.agreeuser = "";
        this.agreeusername = "";
        // this.agreedate = ToolUtils.GetMinDate();
        this.wasterunit = "";
        this.wastersign = "";

    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getAgreeuser() {
        return agreeuser;
    }

    public void setAgreeuser(String agreeuser) {
        this.agreeuser = agreeuser;
    }

    public String getAgreeusername() {
        return agreeusername;
    }

    public void setAgreeusername(String agreeusername) {
        this.agreeusername = agreeusername;
    }

    public java.util.Date getAgreedate() {
        return agreedate;
    }

    public void setAgreedate(java.util.Date agreedate) {
        this.agreedate = agreedate;
    }

    public String getWasterunit() {
        return wasterunit;
    }

    public void setWasterunit(String wasterunit) {
        this.wasterunit = wasterunit;
    }

    public String getWastersign() {
        return wastersign;
    }

    public void setWastersign(String wastersign) {
        this.wastersign = wastersign;
    }

    public java.util.Date getWasterdate() {
        return wasterdate;
    }

    public void setWasterdate(java.util.Date wasterdate) {
        this.wasterdate = wasterdate;
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

    public SelectBean<PrdWaste> getItem() {
        if (item == null)
            item = new SelectBean<PrdWaste>();

        return item;
    }

    public void setItem(SelectBean<PrdWaste> item) {
        this.item = item;
    }

}
