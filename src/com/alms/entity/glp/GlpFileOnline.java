package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFileOnline implements BaseBean {

    // 申请编号;
    private String tranid;

    // 申请文件;
    private String fileid;

    // 申请文件
    private String filename;

    // 申请人;
    private String tranuser;

    // 申请人姓名;
    private String tranusername;

    // 申请时间;
    private java.util.Date trandate;

    // 申请状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 申请动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 申请理由;
    private String applyreason;

    // 关闭时间
    private java.util.Date closetime;

    // 是否取消阅读;
    private boolean iscancel;

    // 取消时间;
    private java.util.Date canceldate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFileOnline> item;

    public GlpFileOnline() {
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
        return ToolUtils.CompareProperty((GlpFileOnline) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "申请文件:fileid", "申请人:tranuser", "申请人姓名:tranusername", "申请时间:trandate",
                "申请状态:flowstatus", "业务状态名称:flowstatusname", "申请动作:flowaction", "业务动作名称:flowactionname",
                "申请理由:applyreason", "是否取消阅读:iscancel", "取消时间:canceldate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileid = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.applyreason = "";
        this.iscancel = false;
        // this.canceldate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getApplyreason() {
        return applyreason;
    }

    public void setApplyreason(String applyreason) {
        this.applyreason = applyreason;
    }

    public java.util.Date getClosetime() {
        return closetime;
    }

    public void setClosetime(java.util.Date closetime) {
        this.closetime = closetime;
    }

    public boolean getIscancel() {
        return iscancel;
    }

    public void setIscancel(boolean iscancel) {
        this.iscancel = iscancel;
    }

    public java.util.Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(java.util.Date canceldate) {
        this.canceldate = canceldate;
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

    public SelectBean<GlpFileOnline> getItem() {
        if (item == null)
            item = new SelectBean<GlpFileOnline>();

        return item;
    }

    public void setItem(SelectBean<GlpFileOnline> item) {
        this.item = item;
    }

}
