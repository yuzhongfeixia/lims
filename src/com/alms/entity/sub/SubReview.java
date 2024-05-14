package com.alms.entity.sub;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SubReview implements BaseBean {

    // 评审编号;
    private String tranid;

    // 分包方名称;
    private String subname;

    // 地址;
    private String subaddr;

    // 联系电话;
    private String linktele;

    // 分包项目;
    private String subproject;

    // 检测人员数量、素质;
    private String testerdesc;

    // 检测设备;
    private String testdev;

    // 检测设备量值溯源情况;
    private String testsource;

    // 环境情况;
    private String envdesc;

    // 质量体系和运行情况;
    private String qualsys;

    // 服务质量;
    private String servicequal;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 评审结论;
    private String auditresult;

    // 评审员;
    private String audituser;

    // 评审员姓名;
    private String auditusername;

    // 评审员时间;
    private java.util.Date auditdate;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SubReview> item;

    public SubReview() {
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
        return ToolUtils.CompareProperty((SubReview) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "评审编号:tranid", "分包方名称:subname", "地址:subaddr", "联系电话:linktele", "分包项目:subproject",
                "检测人员数量、素质:testerdesc", "检测设备:testdev", "检测设备量值溯源情况:testsource", "环境情况:envdesc", "质量体系和运行情况:qualsys",
                "服务质量:servicequal", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "评审结论:auditresult", "评审员:audituser", "评审员姓名:auditusername", "评审员时间:auditdate",
                "批准人:allowuser", "批准人姓名:allowusername", "批准人时间:allowdate", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.subname = "";
        this.subaddr = "";
        this.linktele = "";
        this.subproject = "";
        this.testerdesc = "";
        this.testdev = "";
        this.testsource = "";
        this.envdesc = "";
        this.qualsys = "";
        this.servicequal = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.auditresult = "";
        this.audituser = "";
        this.auditusername = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
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

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSubaddr() {
        return subaddr;
    }

    public void setSubaddr(String subaddr) {
        this.subaddr = subaddr;
    }

    public String getLinktele() {
        return linktele;
    }

    public void setLinktele(String linktele) {
        this.linktele = linktele;
    }

    public String getSubproject() {
        return subproject;
    }

    public void setSubproject(String subproject) {
        this.subproject = subproject;
    }

    public String getTesterdesc() {
        return testerdesc;
    }

    public void setTesterdesc(String testerdesc) {
        this.testerdesc = testerdesc;
    }

    public String getTestdev() {
        return testdev;
    }

    public void setTestdev(String testdev) {
        this.testdev = testdev;
    }

    public String getTestsource() {
        return testsource;
    }

    public void setTestsource(String testsource) {
        this.testsource = testsource;
    }

    public String getEnvdesc() {
        return envdesc;
    }

    public void setEnvdesc(String envdesc) {
        this.envdesc = envdesc;
    }

    public String getQualsys() {
        return qualsys;
    }

    public void setQualsys(String qualsys) {
        this.qualsys = qualsys;
    }

    public String getServicequal() {
        return servicequal;
    }

    public void setServicequal(String servicequal) {
        this.servicequal = servicequal;
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

    public String getAuditresult() {
        return auditresult;
    }

    public void setAuditresult(String auditresult) {
        this.auditresult = auditresult;
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

    public SelectBean<SubReview> getItem() {
        if (item == null)
            item = new SelectBean<SubReview>();

        return item;
    }

    public void setItem(SelectBean<SubReview> item) {
        this.item = item;
    }

}
