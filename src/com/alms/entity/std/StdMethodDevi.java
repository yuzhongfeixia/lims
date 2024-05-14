package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdMethodDevi implements BaseBean {

    // 业务编号;
    private String tranid;

    // 检测方法名称;
    private String methodname;

    // 方法偏离情况;
    private String methodsource;

    // 理由;
    private String reason;

    // 试验记录;
    private String trialrecord;

    // 提出人;
    private String propuser;

    // 提出人姓名;
    private String propusername;

    // 提出时间;
    private java.util.Date propdate;

    // 审查人;
    private String audituser;

    // 审查人姓名;
    private String auditusername;

    // 审查时间;
    private java.util.Date auditdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 确认结果;
    private String sureresult;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 技术负责人时间;
    private java.util.Date techdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdMethodDevi> item;

    public StdMethodDevi() {
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
        return ToolUtils.CompareProperty((StdMethodDevi) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "检测方法名称:methodname", "方法偏离情况:methodsource", "理由:reason",
                "试验记录:trialrecord", "提出人:propuser", "提出人姓名:propusername", "提出时间:propdate", "审查人:audituser",
                "审查人姓名:auditusername", "审查时间:auditdate", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "确认结果:sureresult", "技术负责人:techuser", "技术负责人姓名:techusername",
                "技术负责人时间:techdate", "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.methodname = "";
        this.methodsource = "";
        this.reason = "";
        this.trialrecord = "";
        this.propuser = "";
        this.propusername = "";
        this.propdate = ToolUtils.GetMinDate();
        this.audituser = "";
        this.auditusername = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.sureresult = "";
        this.techuser = "";
        this.techusername = "";
        this.techdate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getMethodsource() {
        return methodsource;
    }

    public void setMethodsource(String methodsource) {
        this.methodsource = methodsource;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTrialrecord() {
        return trialrecord;
    }

    public void setTrialrecord(String trialrecord) {
        this.trialrecord = trialrecord;
    }

    public String getPropuser() {
        return propuser;
    }

    public void setPropuser(String propuser) {
        this.propuser = propuser;
    }

    public String getPropusername() {
        return propusername;
    }

    public void setPropusername(String propusername) {
        this.propusername = propusername;
    }

    public java.util.Date getPropdate() {
        return propdate;
    }

    public void setPropdate(java.util.Date propdate) {
        this.propdate = propdate;
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

    public String getSureresult() {
        return sureresult;
    }

    public void setSureresult(String sureresult) {
        this.sureresult = sureresult;
    }

    public String getTechuser() {
        return techuser;
    }

    public void setTechuser(String techuser) {
        this.techuser = techuser;
    }

    public String getTechusername() {
        return techusername;
    }

    public void setTechusername(String techusername) {
        this.techusername = techusername;
    }

    public java.util.Date getTechdate() {
        return techdate;
    }

    public void setTechdate(java.util.Date techdate) {
        this.techdate = techdate;
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

    public SelectBean<StdMethodDevi> getItem() {
        if (item == null)
            item = new SelectBean<StdMethodDevi>();

        return item;
    }

    public void setItem(SelectBean<StdMethodDevi> item) {
        this.item = item;
    }

}
