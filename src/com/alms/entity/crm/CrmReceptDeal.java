package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmReceptDeal implements BaseBean {

    // 处理编号;
    private String tranid;

    // 关联接待编号;
    private String receptid;

    // 投诉对象;
    private String complainobject;

    // 投诉时间;
    private java.util.Date complaindate;

    // 投诉时效
    private String prescription;

    // 受理时间;
    private java.util.Date acceptdate;

    // 联系人;
    private String linkman;

    // 联系电话;
    private String linktele;

    // 邮编;
    private String linkpost;

    // 联系人地址;
    private String linkaddr;

    // 投诉申诉内容;
    private String complaindesc;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 接受人;
    private String acceptuser;

    // 接受人姓名;
    private String acceptusername;

    // 受理后处理情况;
    private String acceptdesc;

    // 批准人;
    private String audituser;

    // 批准人姓名;
    private String auditusername;

    // 批准人时间;
    private java.util.Date auditdate;

    // 处理结论;
    private String dealresult;

    // 主任;
    private String allowuser;

    // 主任姓名;
    private String allowusername;

    // 主任时间;
    private java.util.Date allowdate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmReceptDeal> item;

    public CrmReceptDeal() {
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
        return ToolUtils.CompareProperty((CrmReceptDeal) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "处理编号:tranid", "关联接待编号:receptid", "投诉对象:complainobject", "投诉时间:complaindate",
                "受理时间:acceptdate", "联系人:linkman", "联系电话:linktele", "邮编:linkpost", "联系人地址:linkaddr",
                "投诉申诉内容:complaindesc", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "接受人:acceptuser", "接受人姓名:acceptusername", "受理后处理情况:acceptdesc",
                "批准人:audituser", "批准人姓名:auditusername", "批准人时间:auditdate", "处理结论:dealresult", "主任:allowuser",
                "主任姓名:allowusername", "主任时间:allowdate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.receptid = "";
        this.complainobject = "";
        this.complaindate = ToolUtils.GetMinDate();
        this.acceptdate = ToolUtils.GetMinDate();
        this.linkman = "";
        this.linktele = "";
        this.linkpost = "";
        this.linkaddr = "";
        this.complaindesc = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.acceptuser = "";
        this.acceptusername = "";
        this.acceptdesc = "";
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.dealresult = "";
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getReceptid() {
        return receptid;
    }

    public void setReceptid(String receptid) {
        this.receptid = receptid;
    }

    public String getComplainobject() {
        return complainobject;
    }

    public void setComplainobject(String complainobject) {
        this.complainobject = complainobject;
    }

    public java.util.Date getComplaindate() {
        return complaindate;
    }

    public void setComplaindate(java.util.Date complaindate) {
        this.complaindate = complaindate;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public java.util.Date getAcceptdate() {
        return acceptdate;
    }

    public void setAcceptdate(java.util.Date acceptdate) {
        this.acceptdate = acceptdate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktele() {
        return linktele;
    }

    public void setLinktele(String linktele) {
        this.linktele = linktele;
    }

    public String getLinkpost() {
        return linkpost;
    }

    public void setLinkpost(String linkpost) {
        this.linkpost = linkpost;
    }

    public String getLinkaddr() {
        return linkaddr;
    }

    public void setLinkaddr(String linkaddr) {
        this.linkaddr = linkaddr;
    }

    public String getComplaindesc() {
        return complaindesc;
    }

    public void setComplaindesc(String complaindesc) {
        this.complaindesc = complaindesc;
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

    public String getAcceptuser() {
        return acceptuser;
    }

    public void setAcceptuser(String acceptuser) {
        this.acceptuser = acceptuser;
    }

    public String getAcceptusername() {
        return acceptusername;
    }

    public void setAcceptusername(String acceptusername) {
        this.acceptusername = acceptusername;
    }

    public String getAcceptdesc() {
        return acceptdesc;
    }

    public void setAcceptdesc(String acceptdesc) {
        this.acceptdesc = acceptdesc;
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

    public String getDealresult() {
        return dealresult;
    }

    public void setDealresult(String dealresult) {
        this.dealresult = dealresult;
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

    public SelectBean<CrmReceptDeal> getItem() {
        if (item == null)
            item = new SelectBean<CrmReceptDeal>();

        return item;
    }

    public void setItem(SelectBean<CrmReceptDeal> item) {
        this.item = item;
    }

}
