package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusReport implements BaseBean {

    // 报告编号;
    private String reportid;

    // 任务单号;
    private String taskid;

    // 表单编号;
    private String formid;

    // 表单名称;
    private String formname;

    // 所属样品;
    private String sampleid;

    // 样品名称;
    private String samplename;

    private int formcount;

    private int groupcount;

    private int speccount;

    private int pagegroup;

    private int pagespec;

    private int onespacing;

    private int twospacing;

    private String tranuser;

    private String tranusername;

    private java.util.Date trandate;

    private String aduituser;

    private String aduitusername;

    private java.util.Date aduitdate;

    private String approveuser;

    private String approveusername;

    private java.util.Date approvedate;

    private String gettype;

    private String actreportid;

    // 检验结论
    private String reportresult;

    // 备注
    private String reportrequest;

    private String reportdeal;

    // 检测项目
    private String testitems;

    private String tranid;

    private String samplecode;

    private String invaliddesc;

    private String modifydesc;

    private String reportstatus;

    private String reportstatusname;

    private String teststandardname;

    private String factname;

    private String samplecount;

    private String samplestand;

    private String samplestatus;

    private String testerproject;

    private String trademark;

    private String getaddr;

    private String samplebase;

    private String devnames;

    private String devids;

    private String testedname;

    private String prdcode;

    private String testtypename;

    private String testenv;

    // 采样任务编号;
    private String tranidcn;

    // 检测室负责人
    private String labuser;

    // 是否合格
    private String ispass;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusReport> item;

    public BusReport() {
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
        return ToolUtils.CompareProperty((BusReport) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "报告编号:reportid", "任务单号:taskid", "表单编号:formid", "所属样品:sampleid", "检测项目:testitems",
                "产品名称:samplename", "生产单位:factname", "样品数量:samplecount", "检测依据:teststandardname", "任务单编号:taskid",
                "型号规格:samplestand", "样品状态:samplestatus", "抽(送)样者:tranusername", "检测项目:testerproject", "样品编号:samplecode",
                "商标:trademark", "抽样地点:getaddr", "抽样基数:samplebase", "所用主要仪器:devnames", "报告生成编号:tranidcn",
                "试验环境条件:testenv", "受检单位:testedname", "原编号或生产日期:prdcode", "检验类别:testtypename", "检验结论:reportresult",
                "备注:reportrequest", "设备:devids", "检测室负责人:labuser", "是否合格:ispass" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.reportid = "";
        this.taskid = "";
        this.formid = "";
        this.formname = "";
        this.sampleid = "";
        this.samplename = "";
        this.tranuser = "";
        this.tranusername = "";
        // this.trandate = ToolUtils.GetNowDate();
        this.gettype = "";
        this.formcount = 0;
        this.groupcount = 0;
        this.speccount = 0;
        this.pagegroup = 0;
        // this.aduitdate = ToolUtils.GetNowDate();
        this.aduituser = "";
        this.aduitusername = "";
        // this.approvedate = ToolUtils.GetNowDate();
        this.approveuser = "";
        this.approveusername = "";
        this.actreportid = "";
        this.reportrequest = "";
        this.reportresult = "";
        this.reportdeal = "";
        this.tranid = "";
        this.tranidcn = "";
        this.samplecode = "";
        this.invaliddesc = "";
        this.modifydesc = "";
        this.pagespec = 0;
        this.onespacing = 0;
        this.twospacing = 0;
        this.reportstatus = "";
        this.reportstatusname = "";
        this.samplename = "";
        this.factname = "";
        this.samplecount = "";
        this.teststandardname = "";
        this.samplestand = "";
        this.samplestatus = "";
        this.tranusername = "";
        this.testerproject = "";
        this.trademark = "";
        this.getaddr = "";
        this.samplebase = "";
        this.devnames = "";
        this.testedname = "";
        this.prdcode = "";
        this.testtypename = "";
        this.samplecode = "";
        this.testenv = "";
        this.devids = "";
        this.testitems = "";
        this.labuser = "";
        this.ispass = "";
    }

    public String getIspass() {
        return ispass;
    }

    public void setIspass(String ispass) {
        this.ispass = ispass;
    }

    public String getLabuser() {
        return labuser;
    }

    public void setLabuser(String labuser) {
        this.labuser = labuser;
    }

    public String getTestitems() {
        return testitems;
    }

    public int getOnespacing() {
        return onespacing;
    }

    public void setOnespacing(int onespacing) {
        this.onespacing = onespacing;
    }

    public int getTwospacing() {
        return twospacing;
    }

    public void setTwospacing(int twospacing) {
        this.twospacing = twospacing;
    }

    public void setTestitems(String testitems) {
        this.testitems = testitems;
    }

    public String getDevids() {
        return devids;
    }

    public void setDevids(String devids) {
        this.devids = devids;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getFactname() {
        return factname;
    }

    public void setFactname(String factname) {
        this.factname = factname;
    }

    public String getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(String samplecount) {
        this.samplecount = samplecount;
    }

    public String getSamplestand() {
        return samplestand;
    }

    public void setSamplestand(String samplestand) {
        this.samplestand = samplestand;
    }

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    public String getTesterproject() {
        return testerproject;
    }

    public void setTesterproject(String testerproject) {
        this.testerproject = testerproject;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getGetaddr() {
        return getaddr;
    }

    public void setGetaddr(String getaddr) {
        this.getaddr = getaddr;
    }

    public String getSamplebase() {
        return samplebase;
    }

    public void setSamplebase(String samplebase) {
        this.samplebase = samplebase;
    }

    public String getDevnames() {
        return devnames;
    }

    public void setDevnames(String devnames) {
        this.devnames = devnames;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
    }

    public String getPrdcode() {
        return prdcode;
    }

    public void setPrdcode(String prdcode) {
        this.prdcode = prdcode;
    }

    public String getTesttypename() {
        return testtypename;
    }

    public void setTesttypename(String testtypename) {
        this.testtypename = testtypename;
    }

    public String getTestenv() {
        return testenv;
    }

    public void setTestenv(String testenv) {
        this.testenv = testenv;
    }

    public int getPagespec() {
        return pagespec;
    }

    public void setPagespec(int pagespec) {
        this.pagespec = pagespec;
    }

    public String getInvaliddesc() {
        return invaliddesc;
    }

    public void setInvaliddesc(String invaliddesc) {
        this.invaliddesc = invaliddesc;
    }

    public String getModifydesc() {
        return modifydesc;
    }

    public void setModifydesc(String modifydesc) {
        this.modifydesc = modifydesc;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getActreportid() {
        return actreportid;
    }

    public void setActreportid(String actreportid) {
        this.actreportid = actreportid;
    }

    public String getReportresult() {
        return reportresult;
    }

    public void setReportresult(String reportresult) {
        this.reportresult = reportresult;
    }

    public String getReportrequest() {
        return reportrequest;
    }

    public void setReportrequest(String reportrequest) {
        this.reportrequest = reportrequest;
    }

    public String getReportdeal() {
        return reportdeal;
    }

    public void setReportdeal(String reportdeal) {
        this.reportdeal = reportdeal;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
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

    public String getAduituser() {
        return aduituser;
    }

    public void setAduituser(String aduituser) {
        this.aduituser = aduituser;
    }

    public String getAduitusername() {
        return aduitusername;
    }

    public void setAduitusername(String aduitusername) {
        this.aduitusername = aduitusername;
    }

    public java.util.Date getAduitdate() {
        return aduitdate;
    }

    public void setAduitdate(java.util.Date aduitdate) {
        this.aduitdate = aduitdate;
    }

    public String getApproveuser() {
        return approveuser;
    }

    public void setApproveuser(String approveuser) {
        this.approveuser = approveuser;
    }

    public String getApproveusername() {
        return approveusername;
    }

    public void setApproveusername(String approveusername) {
        this.approveusername = approveusername;
    }

    public java.util.Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(java.util.Date approvedate) {
        this.approvedate = approvedate;
    }

    public int getPagegroup() {
        return pagegroup;
    }

    public void setPagegroup(int pagegroup) {
        this.pagegroup = pagegroup;
    }

    public int getFormcount() {
        return formcount;
    }

    public void setFormcount(int formcount) {
        this.formcount = formcount;
    }

    public int getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(int groupcount) {
        this.groupcount = groupcount;
    }

    public int getSpeccount() {
        return speccount;
    }

    public void setSpeccount(int speccount) {
        this.speccount = speccount;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
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

    public String getReportstatus() {
        return reportstatus;
    }

    public void setReportstatus(String reportstatus) {
        this.reportstatus = reportstatus;
    }

    public String getReportstatusname() {
        return reportstatusname;
    }

    public void setReportstatusname(String reportstatusname) {
        this.reportstatusname = reportstatusname;
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

    public SelectBean<BusReport> getItem() {
        if (item == null)
            item = new SelectBean<BusReport>();

        return item;
    }

    public void setItem(SelectBean<BusReport> item) {
        this.item = item;
    }

    public String getTranidcn() {
        return tranidcn;
    }

    public void setTranidcn(String tranidcn) {
        this.tranidcn = tranidcn;
    }

}
