package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserPlanYear implements BaseBean {

    // 业务编号;
    private String tranid;

    // 部门机构;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 计划年度;
    private String tranyear;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 申请人员;
    private String tranuser;

    // 申请人名称;
    private String tranusername;

    // 申请日期;
    private java.util.Date trandate;

    // 申请说明;
    private String tranremark;

    // 审核人;
    private String aduituser;

    // 审核人姓名;
    private String aduitusername;

    // 审核日期;
    private java.util.Date aduitdate;

    // 审核说明;
    private String aduitremark;

    // 审批人;
    private String checkuser;

    // 审批人姓名;
    private String checkusername;

    // 审批日期;
    private java.util.Date checkdate;

    // 审批说明;
    private String checkremark;

    // 修改序号;
    private int modifyserial;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserPlanYear> item;

    public UserPlanYear() {
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
        return ToolUtils.CompareProperty((UserPlanYear) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "部门机构:deptid", "机构名称:deptname", "计划年度:tranyear", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "申请人员:tranuser",
                "申请人名称:tranusername", "申请日期:trandate", "申请说明:tranremark", "审核人:aduituser", "审核人姓名:aduitusername",
                "审核日期:aduitdate", "审核说明:aduitremark", "审批人:checkuser", "审批人姓名:checkusername", "审批日期:checkdate",
                "审批说明:checkremark", "修改序号:modifyserial" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.deptid = "";
        this.deptname = "";
        this.tranyear = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.tranremark = "";
        this.aduituser = "";
        this.aduitusername = "";
        // this.aduitdate = ToolUtils.GetMinDate();
        this.aduitremark = "";
        this.checkuser = "";
        this.checkusername = "";
        // this.checkdate = ToolUtils.GetMinDate();
        this.checkremark = "";
        this.modifyserial = 0;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getTranyear() {
        return tranyear;
    }

    public void setTranyear(String tranyear) {
        this.tranyear = tranyear;
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

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
    }

    public String getAduituser() {
        return aduituser;
    }

    public void setAduituser(String aduituser) {
        this.aduituser = aduituser;
    }

    public String getAduitusername() {
        return aduitusername;
    }

    public void setAduitusername(String aduitusername) {
        this.aduitusername = aduitusername;
    }

    public java.util.Date getAduitdate() {
        return aduitdate;
    }

    public void setAduitdate(java.util.Date aduitdate) {
        this.aduitdate = aduitdate;
    }

    public String getAduitremark() {
        return aduitremark;
    }

    public void setAduitremark(String aduitremark) {
        this.aduitremark = aduitremark;
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

    public String getCheckremark() {
        return checkremark;
    }

    public void setCheckremark(String checkremark) {
        this.checkremark = checkremark;
    }

    public int getModifyserial() {
        return modifyserial;
    }

    public void setModifyserial(int modifyserial) {
        this.modifyserial = modifyserial;
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

    public SelectBean<UserPlanYear> getItem() {
        if (item == null)
            item = new SelectBean<UserPlanYear>();

        return item;
    }

    public void setItem(SelectBean<UserPlanYear> item) {
        this.item = item;
    }

}
