package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusAccSample implements BaseBean {

    // 业务编号;
    private String tranid;

    // 收样日期;
    private java.util.Date accdate;

    private String samplecode;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    private String testedid;

    private String issendstatus;

    // 受检单位;
    private String testedname;

    // 联系电话;
    private String entertele;

    // 联系人;
    private String testeduser;

    // 样品实验室;
    private String samplelab;

    // 要求分析项目;
    private String parameterids;

    // 出报告时间;
    private java.util.Date reportdate;

    // 咨询电话;
    private String acctele;

    // 优惠后收费;
    private String feestatus;

    // 检验收费;
    private String testfee;

    // 取报告人;
    private String reportgeter;

    // 备注;
    private String remark;

    // 取样单号;
    private String getid;

    // 收样人;
    private String accuser;

    // 收样人姓名;
    private String accusername;

    private String sampletype;

    private String sampletypename;

    private String gettype;

    private String gettypename;

    private boolean issend;

    private String samplecatalog;

    private String samplemain;

    private String samplemainname;

    private String samplestand;

    private String startdate;

    private String enddate;

    private String samplestatus;

    private String testprop;

    private String mainstandname;

    private String teststandardname;

    private String flowstatus;

    private String flowstatusname;

    private String modifydesc;

    // 存放地点
    private String sampleplace;

    private java.util.Date senddate;

    private java.util.Date reqdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusAccSample> item;

    public BusAccSample() {
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
        return ToolUtils.CompareProperty((BusAccSample) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "下达时间:senddate", "要求完成时间:reqdate", "收样日期:accdate", "检测依据:teststandardname",
                "任务单状态:issendstatus", "收样开始日期:startdate", "收样结束日期:enddate", "样品编号:sampleid", "样品名称:samplename",
                "受检单位:testedname", "联系电话:entertele", "联系人:testeduser", "样品实验室:samplelab", "要求分析项目:paramreq",
                "出报告时间:reportdate", "收费情况:feestatus", "取报告人:reportgeter", "备注:remark", "取样单号:getid", "收样人:accuser",
                "人物撤销理由：modifydesc", "收样人姓名:accusername", "状态:flowstatus", "状态:flowstatusname", "咨询电话:acctele",
                "检验收费:testfee", "存放地点:sampleplace" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.accdate = ToolUtils.GetMinDate();
        this.samplecode = "";
        this.sampleid = "";
        this.issendstatus = "";
        this.teststandardname = "";
        this.samplename = "";
        this.testedid = "";
        this.testedname = "";
        this.entertele = "";
        this.startdate = "";
        this.enddate = "";
        this.testeduser = "";
        this.samplelab = "";
        this.parameterids = "";
        this.reportdate = ToolUtils.GetMinDate();
        // this.senddate = ToolUtils.GetMinDate();
        // this.reqdate = ToolUtils.GetMinDate();
        this.feestatus = "";
        this.reportgeter = "";
        this.remark = "";
        this.getid = "";
        this.accuser = "";
        this.accusername = "";
        this.sampletype = "";
        this.sampletypename = "";
        this.gettype = "";
        this.gettypename = "";
        this.issend = false;
        this.samplecatalog = "";
        this.samplemain = "";
        this.samplemainname = "";
        this.samplestand = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.acctele = "";
        this.testfee = "";
        this.sampleplace = "";
        this.modifydesc = "";
    }

    public String getModifydesc() {
        return modifydesc;
    }

    public void setModifydesc(String modifydesc) {
        this.modifydesc = modifydesc;
    }

    public String getSampleplace() {
        return sampleplace;
    }

    public void setSampleplace(String sampleplace) {
        this.sampleplace = sampleplace;
    }

    public String getTestfee() {
        return testfee;
    }

    public void setTestfee(String testfee) {
        this.testfee = testfee;
    }

    public String getAcctele() {
        return acctele;
    }

    public void setAcctele(String acctele) {
        this.acctele = acctele;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public java.util.Date getSenddate() {
        return senddate;
    }

    public void setSenddate(java.util.Date senddate) {
        this.senddate = senddate;
    }

    public java.util.Date getReqdate() {
        return reqdate;
    }

    public void setReqdate(java.util.Date reqdate) {
        this.reqdate = reqdate;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getIssendstatus() {
        return issendstatus;
    }

    public void setIssendstatus(String issendstatus) {
        this.issendstatus = issendstatus;
    }

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    public String getTestprop() {
        return testprop;
    }

    public void setTestprop(String testprop) {
        this.testprop = testprop;
    }

    public String getMainstandname() {
        return mainstandname;
    }

    public void setMainstandname(String mainstandname) {
        this.mainstandname = mainstandname;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public java.util.Date getAccdate() {
        return accdate;
    }

    public void setAccdate(java.util.Date accdate) {
        this.accdate = accdate;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
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

    public String getTestedid() {
        return testedid;
    }

    public void setTestedid(String testedid) {
        this.testedid = testedid;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
    }

    public String getEntertele() {
        return entertele;
    }

    public void setEntertele(String entertele) {
        this.entertele = entertele;
    }

    public String getTesteduser() {
        return testeduser;
    }

    public void setTesteduser(String testeduser) {
        this.testeduser = testeduser;
    }

    public String getSamplelab() {
        return samplelab;
    }

    public void setSamplelab(String samplelab) {
        this.samplelab = samplelab;
    }

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }

    public java.util.Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(java.util.Date reportdate) {
        this.reportdate = reportdate;
    }

    public String getFeestatus() {
        return feestatus;
    }

    public void setFeestatus(String feestatus) {
        this.feestatus = feestatus;
    }

    public String getReportgeter() {
        return reportgeter;
    }

    public void setReportgeter(String reportgeter) {
        this.reportgeter = reportgeter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGetid() {
        return getid;
    }

    public void setGetid(String getid) {
        this.getid = getid;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getAccusername() {
        return accusername;
    }

    public void setAccusername(String accusername) {
        this.accusername = accusername;
    }

    public String getSampletype() {
        return sampletype;
    }

    public void setSampletype(String sampletype) {
        this.sampletype = sampletype;
    }

    public String getSampletypename() {
        return sampletypename;
    }

    public void setSampletypename(String sampletypename) {
        this.sampletypename = sampletypename;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getGettypename() {
        return gettypename;
    }

    public void setGettypename(String gettypename) {
        this.gettypename = gettypename;
    }

    public boolean isIssend() {
        return issend;
    }

    public void setIssend(boolean issend) {
        this.issend = issend;
    }

    public String getSamplecatalog() {
        return samplecatalog;
    }

    public void setSamplecatalog(String samplecatalog) {
        this.samplecatalog = samplecatalog;
    }

    public String getSamplemain() {
        return samplemain;
    }

    public void setSamplemain(String samplemain) {
        this.samplemain = samplemain;
    }

    public String getSamplemainname() {
        return samplemainname;
    }

    public void setSamplemainname(String samplemainname) {
        this.samplemainname = samplemainname;
    }

    public String getSamplestand() {
        return samplestand;
    }

    public void setSamplestand(String samplestand) {
        this.samplestand = samplestand;
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

    public SelectBean<BusAccSample> getItem() {
        if (item == null)
            item = new SelectBean<BusAccSample>();

        return item;
    }

    public void setItem(SelectBean<BusAccSample> item) {
        this.item = item;
    }

}
