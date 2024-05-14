package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdReplRecord implements BaseBean {

    // 业务编号;
    private String tranid;

    // 标准号;
    private String stdid;

    // 标准名称;
    private String stdname;

    // 代替标准号;
    private String replstdid;

    // 作废日期;
    private java.util.Date canceldate;

    // 实施日期;
    private java.util.Date impldate;

    // 备注;
    private String remark;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 批准人;
    private String approuser;

    // 批准人姓名;
    private String approusername;

    // 批准日期;
    private java.util.Date approdate;

    // 校核人;
    private String checkuser;

    // 校核人姓名;
    private String checkusername;

    // 校核人日期;
    private java.util.Date checkdate;

    // 查新人;
    private String searchuser;

    // 查新人姓名;
    private String searchusername;

    // 查新人日期;
    private java.util.Date searchdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdReplRecord> item;

    public StdReplRecord() {
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
        return ToolUtils.CompareProperty((StdReplRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "标准号:stdid", "标准名称:stdname", "代替标准号:replstdid", "作废日期:canceldate",
                "实施日期:impldate", "备注:remark", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务动作:flowaction",
                "业务动作名称:flowactionname", "批准人:approuser", "批准人姓名:approusername", "批准日期:approdate", "校核人:checkuser",
                "校核人姓名:checkusername", "校核人日期:checkdate", "查新人:searchuser", "查新人姓名:searchusername", "查新人日期:searchdate",
                "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.stdid = "";
        this.stdname = "";
        this.replstdid = "";
        this.canceldate = ToolUtils.GetMinDate();
        this.impldate = ToolUtils.GetMinDate();
        this.remark = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.approuser = "";
        this.approusername = "";
        this.approdate = ToolUtils.GetMinDate();
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.searchuser = "";
        this.searchusername = "";
        this.searchdate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getStdid() {
        return stdid;
    }

    public void setStdid(String stdid) {
        this.stdid = stdid;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getReplstdid() {
        return replstdid;
    }

    public void setReplstdid(String replstdid) {
        this.replstdid = replstdid;
    }

    public java.util.Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(java.util.Date canceldate) {
        this.canceldate = canceldate;
    }

    public java.util.Date getImpldate() {
        return impldate;
    }

    public void setImpldate(java.util.Date impldate) {
        this.impldate = impldate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getApprouser() {
        return approuser;
    }

    public void setApprouser(String approuser) {
        this.approuser = approuser;
    }

    public String getApprousername() {
        return approusername;
    }

    public void setApprousername(String approusername) {
        this.approusername = approusername;
    }

    public java.util.Date getApprodate() {
        return approdate;
    }

    public void setApprodate(java.util.Date approdate) {
        this.approdate = approdate;
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

    public String getSearchuser() {
        return searchuser;
    }

    public void setSearchuser(String searchuser) {
        this.searchuser = searchuser;
    }

    public String getSearchusername() {
        return searchusername;
    }

    public void setSearchusername(String searchusername) {
        this.searchusername = searchusername;
    }

    public java.util.Date getSearchdate() {
        return searchdate;
    }

    public void setSearchdate(java.util.Date searchdate) {
        this.searchdate = searchdate;
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

    public SelectBean<StdReplRecord> getItem() {
        if (item == null)
            item = new SelectBean<StdReplRecord>();

        return item;
    }

    public void setItem(SelectBean<StdReplRecord> item) {
        this.item = item;
    }

}
