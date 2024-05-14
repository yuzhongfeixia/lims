package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ChangeApply implements BaseBean {

    // 业务编号;
    private String tranid;

    // 申请部门;
    private String changedept;

    // 申请部门名称;
    private String changedeptname;

    // 会办部门;
    private String dealdept;

    // 会办部门名称;
    private String deptname;

    // 拟更改文件名称;
    private String changefilename;

    // 替代文件名称;
    private String filename;

    // 替代文件地址;
    private String fileurl;

    // 拟更改文件编号;
    private String changefileid;

    // 替代文件;
    private String replacefilename;

    // 更改理由;
    private String changereason;

    // 更改后内容;
    private String changedesc;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 审核人;
    private String audituser;

    // 审核人姓名;
    private String auditusername;

    // 审核人时间;
    private java.util.Date auditdate;

    // 审核人意见;
    private String auditdesc;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 批准人意见;
    private String allowdesc;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ChangeApply> item;

    public ChangeApply() {
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
        return ToolUtils.CompareProperty((ChangeApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "申请部门:changedept", "会办部门:dealdept", "会办部门名称:deptname",
                "申请部门名称:changedeptname", "拟更改文件名称:changefilename", "替代文件名称：filename", "替代文件地址:fileurl",
                "拟更改文件编号:changefileid", "替代文件:replacefilename", "更改理由:changereason", "更改后内容:changedesc",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "审核人:audituser",
                "审核人姓名:auditusername", "审核人时间:auditdate", "审核人意见:auditdesc", "批准人:allowuser", "批准人姓名:allowusername",
                "批准人时间:allowdate", "批准人意见:allowdesc", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.changedept = "";
        this.dealdept = "";
        this.deptname = "";
        this.changedeptname = "";
        this.changefilename = "";
        this.fileurl = "";
        this.filename = "";
        this.changefileid = "";
        this.replacefilename = "";
        this.changereason = "";
        this.changedesc = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.audituser = "";
        this.auditusername = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getChangedeptname() {
        return changedeptname;
    }

    public void setChangedeptname(String changedeptname) {
        this.changedeptname = changedeptname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getChangedept() {
        return changedept;
    }

    public void setChangedept(String changedept) {
        this.changedept = changedept;
    }

    public String getDealdept() {
        return dealdept;
    }

    public void setDealdept(String dealdept) {
        this.dealdept = dealdept;
    }

    public String getChangefilename() {
        return changefilename;
    }

    public void setChangefilename(String changefilename) {
        this.changefilename = changefilename;
    }

    public String getChangefileid() {
        return changefileid;
    }

    public void setChangefileid(String changefileid) {
        this.changefileid = changefileid;
    }

    public String getReplacefilename() {
        return replacefilename;
    }

    public void setReplacefilename(String replacefilename) {
        this.replacefilename = replacefilename;
    }

    public String getChangereason() {
        return changereason;
    }

    public void setChangereason(String changereason) {
        this.changereason = changereason;
    }

    public String getChangedesc() {
        return changedesc;
    }

    public void setChangedesc(String changedesc) {
        this.changedesc = changedesc;
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

    public String getAllowdesc() {
        return allowdesc;
    }

    public void setAllowdesc(String allowdesc) {
        this.allowdesc = allowdesc;
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

    public SelectBean<ChangeApply> getItem() {
        if (item == null)
            item = new SelectBean<ChangeApply>();

        return item;
    }

    public void setItem(SelectBean<ChangeApply> item) {
        this.item = item;
    }

}
