package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFileLoan implements BaseBean {

    // 业务编号;
    private String tranid;

    // 文件编号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 申请理由
    private String loanreason;

    // 备注;
    private String remark;

    // 借阅时间;
    private java.util.Date loandate;

    // 归还时间;
    private java.util.Date returndate;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 借阅人;
    private String tranuser;

    // 借阅人姓名;
    private String tranusername;

    // 借阅人时间;
    private java.util.Date trandate;

    // 办公室负责人;
    private String officeuser;

    // 办公室负责人姓名;
    private String officeusername;

    // 办公室时间;
    private java.util.Date officedate;

    // 办公室意见;
    private String officedesc;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 批准人意见;
    private String allowdesc;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFileLoan> item;

    public GlpFileLoan() {
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
        return ToolUtils.CompareProperty((GlpFileLoan) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "文件编号:fileid", "文件名称:filename", "备注:remark", "借阅时间:loandate",
                "归还时间:returndate", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务动作:flowaction",
                "业务动作名称:flowactionname", "借阅人:tranuser", "借阅人姓名:tranusername", "借阅人时间:trandate", "办公室负责人:officeuser",
                "办公室负责人姓名:officeusername", "办公室时间:officedate", "办公室意见:officedesc", "批准人:allowuser",
                "批准人姓名:allowusername", "批准人时间:allowdate", "批准人意见:allowdesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileid = "";
        this.filename = "";
        this.loanreason = "";
        this.remark = "";
        this.loandate = ToolUtils.GetMinDate();
        // this.returndate = ToolUtils.GetMinDate();
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.officeuser = "";
        this.officeusername = "";
        this.officedate = ToolUtils.GetMinDate();
        this.officedesc = "";
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getLoanreason() {
        return loanreason;
    }

    public void setLoanreason(String loanreason) {
        this.loanreason = loanreason;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public java.util.Date getLoandate() {
        return loandate;
    }

    public void setLoandate(java.util.Date loandate) {
        this.loandate = loandate;
    }

    public java.util.Date getReturndate() {
        return returndate;
    }

    public void setReturndate(java.util.Date returndate) {
        this.returndate = returndate;
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

    public String getOfficeuser() {
        return officeuser;
    }

    public void setOfficeuser(String officeuser) {
        this.officeuser = officeuser;
    }

    public String getOfficeusername() {
        return officeusername;
    }

    public void setOfficeusername(String officeusername) {
        this.officeusername = officeusername;
    }

    public java.util.Date getOfficedate() {
        return officedate;
    }

    public void setOfficedate(java.util.Date officedate) {
        this.officedate = officedate;
    }

    public String getOfficedesc() {
        return officedesc;
    }

    public void setOfficedesc(String officedesc) {
        this.officedesc = officedesc;
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

    public SelectBean<GlpFileLoan> getItem() {
        if (item == null)
            item = new SelectBean<GlpFileLoan>();

        return item;
    }

    public void setItem(SelectBean<GlpFileLoan> item) {
        this.item = item;
    }

}
