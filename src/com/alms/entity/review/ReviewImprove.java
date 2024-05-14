package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReviewImprove implements BaseBean {

    // 业务编号;
    private String tranid;

    // 评审报告编号;
    private String reportid;

    // 责任部门;
    private String respdept;

    // 责任部门名称;
    private String respdeptname;

    // 质量改进内容;
    private String improvecontent;

    // 期限要求;
    private String timerequire;

    // 签发人;
    private String tranuser;

    // 签发人姓名;
    private String tranusername;

    // 签发人时间;
    private java.util.Date trandate;

    // 改进情况;
    private String improvedesc;

    // 责任部门负责人;
    private String respuser;

    // 责任部门负责人姓名;
    private String respusername;

    // 改进验证;
    private String improvevalid;

    // 验证人;
    private String validuser;

    // 验证人姓名;
    private String validusername;

    // 验证人时间;
    private java.util.Date validdate;

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

    private SelectBean<ReviewImprove> item;

    public ReviewImprove() {
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
        return ToolUtils.CompareProperty((ReviewImprove) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "评审报告编号:reportid", "责任部门:respdept", "责任部门名称:respdeptname",
                "质量改进内容:improvecontent", "期限要求:timerequire", "签发人:tranuser", "签发人姓名:tranusername", "签发人时间:trandate",
                "改进情况:improvedesc", "责任部门负责人:respuser", "责任部门负责人姓名:respusername", "改进验证:improvevalid", "验证人:validuser",
                "验证人姓名:validusername", "验证人时间:validdate", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.reportid = "";
        this.respdept = "";
        this.respdeptname = "";
        this.improvecontent = "";
        this.timerequire = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.improvedesc = "";
        this.respuser = "";
        this.respusername = "";
        this.improvevalid = "";
        this.validuser = "";
        this.validusername = "";
        this.validdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getRespdept() {
        return respdept;
    }

    public void setRespdept(String respdept) {
        this.respdept = respdept;
    }

    public String getRespdeptname() {
        return respdeptname;
    }

    public void setRespdeptname(String respdeptname) {
        this.respdeptname = respdeptname;
    }

    public String getImprovecontent() {
        return improvecontent;
    }

    public void setImprovecontent(String improvecontent) {
        this.improvecontent = improvecontent;
    }

    public String getTimerequire() {
        return timerequire;
    }

    public void setTimerequire(String timerequire) {
        this.timerequire = timerequire;
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

    public String getImprovedesc() {
        return improvedesc;
    }

    public void setImprovedesc(String improvedesc) {
        this.improvedesc = improvedesc;
    }

    public String getRespuser() {
        return respuser;
    }

    public void setRespuser(String respuser) {
        this.respuser = respuser;
    }

    public String getRespusername() {
        return respusername;
    }

    public void setRespusername(String respusername) {
        this.respusername = respusername;
    }

    public String getImprovevalid() {
        return improvevalid;
    }

    public void setImprovevalid(String improvevalid) {
        this.improvevalid = improvevalid;
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

    public SelectBean<ReviewImprove> getItem() {
        if (item == null)
            item = new SelectBean<ReviewImprove>();

        return item;
    }

    public void setItem(SelectBean<ReviewImprove> item) {
        this.item = item;
    }

}
