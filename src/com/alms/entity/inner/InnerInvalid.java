package com.alms.entity.inner;

import java.util.Date;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerInvalid implements BaseBean {

    // 业务编号;
    private String tranid;

    // 发生部门编号;
    private String occurdept;

    // 发生部门名称;
    private String occurdeptname;

    // 不符合来源;
    private String correctsource;

    // 不符合来源名称;
    private String correctsourcename;

    // 不符合识别人;
    private String invaliduser;

    // 不符合识别人姓名;
    private String invalidusername;

    // 不符合识别人日期
    private Date invaliduserdate;

    // 责任人;
    private String tranuser;

    // 责任人姓名;
    private String tranusername;

    // 责任人时间;
    private java.util.Date trandate;

    // 不符合描述;
    private String invaliddesc;

    // 不符合评价;
    private String invalidadv;

    // 不符合评价
    private String invalidadvname;

    // 不符合原因;
    private String invalidreason;

    // 纠正措施;
    private String invalidmeasure;

    // 部门负责人;
    private String deptuser;

    // 部门负责人姓名;
    private String deptusername;

    // 部门负责人时间;
    private java.util.Date deptdate;

    // 部门负责人意见;
    private String deptdesc;

    // 审核人;
    private String audituser;

    // 审核人姓名;
    private String auditusername;

    // 审核人时间;
    private java.util.Date auditdate;

    // 纠正措施已完成;
    private boolean isfinish;

    // 验证人;
    private String validuser;

    // 验证人姓名;
    private String validusername;

    // 验证人时间;
    private java.util.Date validdate;

    // 纠正措施已验证;
    private boolean isvalid;

    // 验证结果;
    private String validresult;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerInvalid> item;

    public InnerInvalid() {
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
        return ToolUtils.CompareProperty((InnerInvalid) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "发生部门编号:occurdept", "发生部门名称:occurdeptname", "不符合来源:correctsource",
                "不符合来源名称:correctsourcename", "不符合识别人:invaliduser", "不符合识别人姓名:invalidusername", "责任人:tranuser",
                "责任人姓名:tranusername", "责任人时间:trandate", "不符合描述:invaliddesc", "不符合评价:invalidadv", "不符合原因:invalidreason",
                "纠正措施:invalidmeasure", "部门负责人:deptuser", "部门负责人姓名:deptusername", "部门负责人时间:deptdate", "部门负责人意见:deptdesc",
                "审核人:audituser", "审核人姓名:auditusername", "审核人时间:auditdate", "纠正措施已完成:isfinish", "验证人:validuser",
                "验证人姓名:validusername", "验证人时间:validdate", "纠正措施已验证:isvalid", "验证结果:validresult", "批准人:allowuser",
                "批准人姓名:allowusername", "批准人时间:allowdate", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.occurdept = "";
        this.occurdeptname = "";
        this.correctsource = "";
        this.correctsourcename = "";
        this.invaliduser = "";
        this.invalidusername = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.invaliddesc = "";
        this.invalidadv = "";
        this.invalidreason = "";
        this.invalidmeasure = "";
        this.deptuser = "";
        this.deptusername = "";
        // this.deptdate = ToolUtils.GetMinDate();
        this.deptdesc = "";
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.isfinish = false;
        this.validuser = "";
        this.validusername = "";
        // this.validdate = ToolUtils.GetMinDate();
        this.isvalid = false;
        this.validresult = "";
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getOccurdept() {
        return occurdept;
    }

    public void setOccurdept(String occurdept) {
        this.occurdept = occurdept;
    }

    public String getOccurdeptname() {
        return occurdeptname;
    }

    public void setOccurdeptname(String occurdeptname) {
        this.occurdeptname = occurdeptname;
    }

    public String getCorrectsource() {
        return correctsource;
    }

    public void setCorrectsource(String correctsource) {
        this.correctsource = correctsource;
    }

    public String getCorrectsourcename() {
        return correctsourcename;
    }

    public void setCorrectsourcename(String correctsourcename) {
        this.correctsourcename = correctsourcename;
    }

    public String getInvaliduser() {
        return invaliduser;
    }

    public void setInvaliduser(String invaliduser) {
        this.invaliduser = invaliduser;
    }

    public String getInvalidusername() {
        return invalidusername;
    }

    public void setInvalidusername(String invalidusername) {
        this.invalidusername = invalidusername;
    }

    public Date getInvaliduserdate() {
        return invaliduserdate;
    }

    public void setInvaliduserdate(Date invaliduserdate) {
        this.invaliduserdate = invaliduserdate;
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

    public String getInvaliddesc() {
        return invaliddesc;
    }

    public void setInvaliddesc(String invaliddesc) {
        this.invaliddesc = invaliddesc;
    }

    public String getInvalidadv() {
        return invalidadv;
    }

    public void setInvalidadv(String invalidadv) {
        this.invalidadv = invalidadv;
    }

    public String getInvalidadvname() {
        return invalidadvname;
    }

    public void setInvalidadvname(String invalidadvname) {
        this.invalidadvname = invalidadvname;
    }

    public String getInvalidreason() {
        return invalidreason;
    }

    public void setInvalidreason(String invalidreason) {
        this.invalidreason = invalidreason;
    }

    public String getInvalidmeasure() {
        return invalidmeasure;
    }

    public void setInvalidmeasure(String invalidmeasure) {
        this.invalidmeasure = invalidmeasure;
    }

    public String getDeptuser() {
        return deptuser;
    }

    public void setDeptuser(String deptuser) {
        this.deptuser = deptuser;
    }

    public String getDeptusername() {
        return deptusername;
    }

    public void setDeptusername(String deptusername) {
        this.deptusername = deptusername;
    }

    public java.util.Date getDeptdate() {
        return deptdate;
    }

    public void setDeptdate(java.util.Date deptdate) {
        this.deptdate = deptdate;
    }

    public String getDeptdesc() {
        return deptdesc;
    }

    public void setDeptdesc(String deptdesc) {
        this.deptdesc = deptdesc;
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

    public boolean getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    public String getValiduser() {
        return validuser;
    }

    public void setValiduser(String validuser) {
        this.validuser = validuser;
    }

    public String getValidusername() {
        return validusername;
    }

    public void setValidusername(String validusername) {
        this.validusername = validusername;
    }

    public java.util.Date getValiddate() {
        return validdate;
    }

    public void setValiddate(java.util.Date validdate) {
        this.validdate = validdate;
    }

    public boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getValidresult() {
        return validresult;
    }

    public void setValidresult(String validresult) {
        this.validresult = validresult;
    }

    public String getAllowuser() {
        return allowuser;
    }

    public void setAllowuser(String allowuser) {
        this.allowuser = allowuser;
    }

    public String getAllowusername() {
        return allowusername;
    }

    public void setAllowusername(String allowusername) {
        this.allowusername = allowusername;
    }

    public java.util.Date getAllowdate() {
        return allowdate;
    }

    public void setAllowdate(java.util.Date allowdate) {
        this.allowdate = allowdate;
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

    public SelectBean<InnerInvalid> getItem() {
        if (item == null)
            item = new SelectBean<InnerInvalid>();

        return item;
    }

    public void setItem(SelectBean<InnerInvalid> item) {
        this.item = item;
    }

}
