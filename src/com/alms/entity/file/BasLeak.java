package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasLeak implements BaseBean {

    // 处置编号;
    private String tranid;

    // 泄密人编号;
    private String leakuser;

    // 泄密人姓名;
    private String leakusername;

    // 泄密时间;
    private java.util.Date leakdate;

    // 泄密原因;
    private String leakreason;

    // 造成后果;
    private String leakresult;

    // 泄密报告时间;
    private java.util.Date reportdate;

    // 泄密简述;
    private String leakdesc;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 补救措施;
    private String remedydesc;

    // 实施人;
    private String remedyuser;

    // 实施人姓名;
    private String remedyusername;

    // 实施人时间;
    private java.util.Date remedydate;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 技术负责人时间;
    private java.util.Date techdate;

    // 补救效果;
    private String techdesc;

    // 质量负责人;
    private String qualuser;

    // 质量负责人姓名;
    private String qualusername;

    // 质量负责人时间;
    private java.util.Date qualdate;

    // 质量负责人意见;
    private String qualdesc;

    // 主任;
    private String allowuser;

    // 主任姓名;
    private String allowusername;

    // 主任时间;
    private java.util.Date allowdate;

    // 主任意见;
    private String allowdesc;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasLeak> item;

    public BasLeak() {
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
        return ToolUtils.CompareProperty((BasLeak) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "处置编号:tranid", "泄密人编号:leakuser", "泄密人姓名:leakusername", "泄密时间:leakdate", "泄密原因:leakreason",
                "造成后果:leakresult", "泄密报告时间:reportdate", "泄密简述:leakdesc", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务动作:flowaction",
                "业务动作名称:flowactionname", "补救措施:remedydesc", "实施人:remedyuser", "实施人姓名:remedyusername",
                "实施人时间:remedydate", "技术负责人:techuser", "技术负责人姓名:techusername", "技术负责人时间:techdate", "补救效果:techdesc",
                "质量负责人:qualuser", "质量负责人姓名:qualusername", "质量负责人时间:qualdate", "质量负责人意见:qualdesc", "主任:allowuser",
                "主任姓名:allowusername", "主任时间:allowdate", "主任意见:allowdesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.leakuser = "";
        this.leakusername = "";
        this.leakdate = ToolUtils.GetMinDate();
        this.leakreason = "";
        this.leakresult = "";
        this.reportdate = ToolUtils.GetMinDate();
        this.leakdesc = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.remedydesc = "";
        this.remedyuser = "";
        this.remedyusername = "";
        this.remedydate = ToolUtils.GetMinDate();
        this.techuser = "";
        this.techusername = "";
        this.techdate = ToolUtils.GetMinDate();
        this.techdesc = "";
        this.qualuser = "";
        this.qualusername = "";
        this.qualdate = ToolUtils.GetMinDate();
        this.qualdesc = "";
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getLeakuser() {
        return leakuser;
    }

    public void setLeakuser(String leakuser) {
        this.leakuser = leakuser;
    }

    public String getLeakusername() {
        return leakusername;
    }

    public void setLeakusername(String leakusername) {
        this.leakusername = leakusername;
    }

    public java.util.Date getLeakdate() {
        return leakdate;
    }

    public void setLeakdate(java.util.Date leakdate) {
        this.leakdate = leakdate;
    }

    public String getLeakreason() {
        return leakreason;
    }

    public void setLeakreason(String leakreason) {
        this.leakreason = leakreason;
    }

    public String getLeakresult() {
        return leakresult;
    }

    public void setLeakresult(String leakresult) {
        this.leakresult = leakresult;
    }

    public java.util.Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(java.util.Date reportdate) {
        this.reportdate = reportdate;
    }

    public String getLeakdesc() {
        return leakdesc;
    }

    public void setLeakdesc(String leakdesc) {
        this.leakdesc = leakdesc;
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

    public String getRemedydesc() {
        return remedydesc;
    }

    public void setRemedydesc(String remedydesc) {
        this.remedydesc = remedydesc;
    }

    public String getRemedyuser() {
        return remedyuser;
    }

    public void setRemedyuser(String remedyuser) {
        this.remedyuser = remedyuser;
    }

    public String getRemedyusername() {
        return remedyusername;
    }

    public void setRemedyusername(String remedyusername) {
        this.remedyusername = remedyusername;
    }

    public java.util.Date getRemedydate() {
        return remedydate;
    }

    public void setRemedydate(java.util.Date remedydate) {
        this.remedydate = remedydate;
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

    public String getTechdesc() {
        return techdesc;
    }

    public void setTechdesc(String techdesc) {
        this.techdesc = techdesc;
    }

    public String getQualuser() {
        return qualuser;
    }

    public void setQualuser(String qualuser) {
        this.qualuser = qualuser;
    }

    public String getQualusername() {
        return qualusername;
    }

    public void setQualusername(String qualusername) {
        this.qualusername = qualusername;
    }

    public java.util.Date getQualdate() {
        return qualdate;
    }

    public void setQualdate(java.util.Date qualdate) {
        this.qualdate = qualdate;
    }

    public String getQualdesc() {
        return qualdesc;
    }

    public void setQualdesc(String qualdesc) {
        this.qualdesc = qualdesc;
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

    public SelectBean<BasLeak> getItem() {
        if (item == null)
            item = new SelectBean<BasLeak>();

        return item;
    }

    public void setItem(SelectBean<BasLeak> item) {
        this.item = item;
    }

}
