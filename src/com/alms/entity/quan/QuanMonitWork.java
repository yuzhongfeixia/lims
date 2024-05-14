package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanMonitWork implements BaseBean {

    // 业务编号;
    private String tranid;

    // 质量监督员;
    private String monituser;

    // 质量监督员姓名;
    private String monitusername;

    // 监督开始时间;
    private java.util.Date monitstart;

    // 监督结束时间;
    private java.util.Date monitend;

    // 试验员;
    private String trialuser;

    // 实验员姓名;
    private String trialusername;

    // 样品号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 工作类型;
    private String worktype;

    // 工作类型名称
    private String worktypename;

    // 抽取记录报告份数;
    private int reportnum;

    // 时效起始;
    private java.util.Date validstart;

    // 时效结束;
    private java.util.Date validend;

    // 报告类别;
    private String reportcata;

    // 备注;
    private String remark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanMonitWork> item;

    public QuanMonitWork() {
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
        return ToolUtils.CompareProperty((QuanMonitWork) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "质量监督员:monituser", "质量监督员姓名:monitusername", "监督开始时间:monitstart",
                "监督结束时间:monitend", "试验员:trialuser", "实验员姓名:trialusername", "样品号:sampleid", "样品名称:samplename",
                "工作类型:worktype", "抽取记录报告份数:reportnum", "时效起始:validstart", "时效结束:validend", "报告类别:reportcata",
                "备注:remark", "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.monituser = "";
        this.monitusername = "";
        this.monitstart = ToolUtils.GetMinDate();
        this.monitend = ToolUtils.GetMinDate();
        this.trialuser = "";
        this.trialusername = "";
        this.sampleid = "";
        this.samplename = "";
        this.worktype = "";
        this.reportnum = 0;
        this.validstart = ToolUtils.GetMinDate();
        this.validend = ToolUtils.GetMinDate();
        this.reportcata = "";
        this.remark = "";
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

    public String getMonituser() {
        return monituser;
    }

    public void setMonituser(String monituser) {
        this.monituser = monituser;
    }

    public String getMonitusername() {
        return monitusername;
    }

    public void setMonitusername(String monitusername) {
        this.monitusername = monitusername;
    }

    public java.util.Date getMonitstart() {
        return monitstart;
    }

    public void setMonitstart(java.util.Date monitstart) {
        this.monitstart = monitstart;
    }

    public java.util.Date getMonitend() {
        return monitend;
    }

    public void setMonitend(java.util.Date monitend) {
        this.monitend = monitend;
    }

    public String getTrialuser() {
        return trialuser;
    }

    public void setTrialuser(String trialuser) {
        this.trialuser = trialuser;
    }

    public String getTrialusername() {
        return trialusername;
    }

    public void setTrialusername(String trialusername) {
        this.trialusername = trialusername;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getWorktypename() {
        return worktypename;
    }

    public void setWorktypename(String worktypename) {
        this.worktypename = worktypename;
    }

    public int getReportnum() {
        return reportnum;
    }

    public void setReportnum(int reportnum) {
        this.reportnum = reportnum;
    }

    public java.util.Date getValidstart() {
        return validstart;
    }

    public void setValidstart(java.util.Date validstart) {
        this.validstart = validstart;
    }

    public java.util.Date getValidend() {
        return validend;
    }

    public void setValidend(java.util.Date validend) {
        this.validend = validend;
    }

    public String getReportcata() {
        return reportcata;
    }

    public void setReportcata(String reportcata) {
        this.reportcata = reportcata;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<QuanMonitWork> getItem() {
        if (item == null)
            item = new SelectBean<QuanMonitWork>();

        return item;
    }

    public void setItem(SelectBean<QuanMonitWork> item) {
        this.item = item;
    }

}
