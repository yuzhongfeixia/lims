package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusGet implements BaseBean {

    // 业务编号;
    private String tranid;
    // 采样任务编号;
    private String tranidcn;

    // 受检单位编号;
    private String testedid;

    // 受检单位;
    private String testedname;

    // 企业法人;
    private String enterlegal;

    // 企业地址;
    private String enteraddr;

    // 企业邮编;
    private String enterpost;

    // 企业传真;
    private String enterfax;

    // 企业联系人;
    private String enterlink;

    // 企业联系电话;
    private String entertele;

    // 企业性质;
    private String entertype;

    // 受检单位性质名称;
    private String entertypename;

    // 企业规模;
    private String enterscale;

    // 规模名称;
    private String enterscalename;

    // 主管单位;
    private String entermanager;

    // 受检者姓名;
    private String testeduser;

    // 受检者电话;
    private String testedtele;

    // 受检者传真;
    private String testedfax;

    // 受检者日期;
    private java.util.Date testeddate;

    // 检验机构;
    private String testdept;

    // 检验机构地址;
    private String testaddr;

    // 检验机构联系人;
    private String testlink;

    // 检验机构邮编;
    private String testpost;

    // 检验机构联系电话;
    private String testtele;

    // 检验机构传真;
    private String testfax;

    // 检验机构邮箱;
    private String testemail;

    // 收样员;
    private String sampleuser;

    // 收样员姓名;
    private String sampleusername;

    // 收样员时间;
    private java.util.Date userdate;

    // 受委托机构;
    private String trustdept;

    // 负责人;
    private String samplerespon;

    // 负责人姓名;
    private String sampleresponname;

    // 负责人时间;
    private java.util.Date respondate;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 关联通知单号;
    private String noticeid;

    // 抽样员;
    private String tranuser;

    // 抽样员姓名;
    private String tranusername;

    // 抽样员时间;
    private java.util.Date trandate;

    // 抽样单类型;
    private String gettype;

    // 抽样单类型名称;
    private String gettypename;

    // 检验类别;
    private String testtype;

    // 检测依据数据类型名称;
    private String testtypename;

    // 抽样时间;
    private java.util.Date getdate;

    // 是否送样;
    private String sendtype;

    private String sendtypename;

    // 送样时间;
    private java.util.Date senddate;

    // 送样地点;
    private String sendaddr;

    // 采样深度;
    private String sampledeep;

    // 基地名称;
    private String basename;

    // 基地面积;
    private String basearea;

    // 种植品种;
    private String planvar;

    // 任务来源;
    private String tasksource;

    // 产品执行标准;
    private String prdstand;

    // 监督抽查文件编号;
    private String fileid;

    // 任务检测依据;
    private String teststand;

    // 备注;
    private String tranremark;

    private String reportget;

    private String restdeal;

    private String reportgetname;

    private String restdealname;

    private String timereq;

    private String timereqname;

    private String samplecode;

    private String clientsign;

    private String mandatorysign;

    private String sampleid;

    private String productcate;

    private String productcatename;

    private String samplename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusGet> item;

    public BusGet() {
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
        return ToolUtils.CompareProperty((BusGet) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "业务编号cn:tranidcn", "受检单位编号:testedid", "受检单位:testedname", "企业法人:enterlegal",
                "企业地址:enteraddr", "企业邮编:enterpost", "企业传真:enterfax", "企业联系人:enterlink", "企业联系电话:entertele",
                "企业性质:entertype", "受检单位性质名称:entertypename", "企业规模:enterscale", "规模名称:enterscalename",
                "主管单位:entermanager", "受检者姓名:testeduser", "受检者电话:testedtele", "受检者传真:testedfax", "受检者日期:testeddate",
                "检验机构:testdept", "检验机构地址:testaddr", "检验机构联系人:testlink", "检验机构邮编:testpost", "检验机构联系电话:testtele",
                "检验机构传真:testfax", "检验机构邮箱:testemail", "收样员:sampleuser", "收样员姓名:sampleusername", "收样员时间:userdate",
                "受委托机构:trustdept", "负责人:samplerespon", "负责人姓名:sampleresponname", "负责人时间:respondate", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "业务动作:flowaction", "业务动作名称:flowactionname", "关联通知单号:noticeid", "抽样员:tranuser",
                "抽样员姓名:tranusername", "抽样员时间:trandate", "抽样单类型:gettype", "抽样单类型名称:gettypename", "检验类别:testtype",
                "检测依据数据类型名称:testtypename", "抽样时间:getdate", "是否送样:issend", "送样时间:senddate", "送样地点:sendaddr",
                "采样深度:sampledeep", "基地名称:basename", "基地面积:basearea", "种植品种:planvar", "任务来源:tasksource",
                "产品执行标准:prdstand", "监督抽查文件编号:fileid", "任务检测依据:teststand", "备注:tranremark", "样品编号:samplecode",
                "委托人签名:clientsign", "受委托人签名:mandatorysign", "是否送样:sendtypename", "样品编号:sampleid", "样品类别:productcate",
                "样品类别:productcatename", "样品名称:samplename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.tranidcn = "";
        this.testedid = "";
        this.sendtypename = "";
        this.clientsign = "";
        this.testedid = "";
        this.mandatorysign = "";
        this.enterlegal = "";
        this.enteraddr = "";
        this.enterpost = "";
        this.enterfax = "";
        this.enterlink = "";
        this.entertele = "";
        this.entertype = "";
        this.entertypename = "";
        this.enterscale = "";
        this.enterscalename = "";
        this.entermanager = "";
        this.testeduser = "";
        this.testedtele = "";
        this.testedfax = "";
        this.testeddate = ToolUtils.GetMinDate();
        this.testdept = "";
        this.testaddr = "";
        this.testlink = "";
        this.testpost = "";
        this.testtele = "";
        this.testfax = "";
        this.testemail = "";
        this.sampleuser = "";
        this.sampleusername = "";
        this.userdate = ToolUtils.GetMinDate();
        this.trustdept = "";
        this.samplerespon = "";
        this.sampleresponname = "";
        this.respondate = ToolUtils.GetMinDate();
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.noticeid = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetNowDate();
        this.gettype = "";
        this.gettypename = "";
        this.testtype = "";
        this.testtypename = "";
        this.getdate = ToolUtils.GetNowDate();
        this.sendtype = "";
        this.senddate = null;
        this.sendaddr = "";
        this.sampledeep = "";
        this.basename = "";
        this.basearea = "";
        this.planvar = "";
        this.tasksource = "";
        this.prdstand = "";
        this.fileid = "";
        this.teststand = "";
        this.tranremark = "";
        this.reportget = "";
        this.restdeal = "";
        this.reportgetname = "";
        this.restdealname = "";
        this.timereq = "";
        this.timereqname = "";
        this.samplecode = "";
        this.sampleid = "";
        this.productcate = "";
        this.productcatename = "";
        this.samplename = "";
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getProductcate() {
        return productcate;
    }

    public void setProductcate(String productcate) {
        this.productcate = productcate;
    }

    public String getProductcatename() {
        return productcatename;
    }

    public void setProductcatename(String productcatename) {
        this.productcatename = productcatename;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSendtypename() {
        return sendtypename;
    }

    public void setSendtypename(String sendtypename) {
        this.sendtypename = sendtypename;
    }

    public String getClientsign() {
        return clientsign;
    }

    public void setClientsign(String clientsign) {
        this.clientsign = clientsign;
    }

    public String getMandatorysign() {
        return mandatorysign;
    }

    public void setMandatorysign(String mandatorysign) {
        this.mandatorysign = mandatorysign;
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

    public String getEnterlegal() {
        return enterlegal;
    }

    public void setEnterlegal(String enterlegal) {
        this.enterlegal = enterlegal;
    }

    public String getEnteraddr() {
        return enteraddr;
    }

    public void setEnteraddr(String enteraddr) {
        this.enteraddr = enteraddr;
    }

    public String getEnterpost() {
        return enterpost;
    }

    public void setEnterpost(String enterpost) {
        this.enterpost = enterpost;
    }

    public String getEnterfax() {
        return enterfax;
    }

    public void setEnterfax(String enterfax) {
        this.enterfax = enterfax;
    }

    public String getEnterlink() {
        return enterlink;
    }

    public void setEnterlink(String enterlink) {
        this.enterlink = enterlink;
    }

    public String getEntertele() {
        return entertele;
    }

    public void setEntertele(String entertele) {
        this.entertele = entertele;
    }

    public String getEntertype() {
        return entertype;
    }

    public void setEntertype(String entertype) {
        this.entertype = entertype;
    }

    public String getEntertypename() {
        return entertypename;
    }

    public void setEntertypename(String entertypename) {
        this.entertypename = entertypename;
    }

    public String getEnterscale() {
        return enterscale;
    }

    public void setEnterscale(String enterscale) {
        this.enterscale = enterscale;
    }

    public String getEnterscalename() {
        return enterscalename;
    }

    public void setEnterscalename(String enterscalename) {
        this.enterscalename = enterscalename;
    }

    public String getEntermanager() {
        return entermanager;
    }

    public void setEntermanager(String entermanager) {
        this.entermanager = entermanager;
    }

    public String getTesteduser() {
        return testeduser;
    }

    public void setTesteduser(String testeduser) {
        this.testeduser = testeduser;
    }

    public String getTestedtele() {
        return testedtele;
    }

    public void setTestedtele(String testedtele) {
        this.testedtele = testedtele;
    }

    public String getTestedfax() {
        return testedfax;
    }

    public void setTestedfax(String testedfax) {
        this.testedfax = testedfax;
    }

    public java.util.Date getTesteddate() {
        return testeddate;
    }

    public void setTesteddate(java.util.Date testeddate) {
        this.testeddate = testeddate;
    }

    public String getTestdept() {
        return testdept;
    }

    public void setTestdept(String testdept) {
        this.testdept = testdept;
    }

    public String getTestaddr() {
        return testaddr;
    }

    public void setTestaddr(String testaddr) {
        this.testaddr = testaddr;
    }

    public String getTestlink() {
        return testlink;
    }

    public void setTestlink(String testlink) {
        this.testlink = testlink;
    }

    public String getTestpost() {
        return testpost;
    }

    public void setTestpost(String testpost) {
        this.testpost = testpost;
    }

    public String getTesttele() {
        return testtele;
    }

    public void setTesttele(String testtele) {
        this.testtele = testtele;
    }

    public String getTestfax() {
        return testfax;
    }

    public void setTestfax(String testfax) {
        this.testfax = testfax;
    }

    public String getTestemail() {
        return testemail;
    }

    public void setTestemail(String testemail) {
        this.testemail = testemail;
    }

    public String getSampleuser() {
        return sampleuser;
    }

    public void setSampleuser(String sampleuser) {
        this.sampleuser = sampleuser;
    }

    public String getSampleusername() {
        return sampleusername;
    }

    public void setSampleusername(String sampleusername) {
        this.sampleusername = sampleusername;
    }

    public java.util.Date getUserdate() {
        return userdate;
    }

    public void setUserdate(java.util.Date userdate) {
        this.userdate = userdate;
    }

    public String getTrustdept() {
        return trustdept;
    }

    public void setTrustdept(String trustdept) {
        this.trustdept = trustdept;
    }

    public String getSamplerespon() {
        return samplerespon;
    }

    public void setSamplerespon(String samplerespon) {
        this.samplerespon = samplerespon;
    }

    public String getSampleresponname() {
        return sampleresponname;
    }

    public void setSampleresponname(String sampleresponname) {
        this.sampleresponname = sampleresponname;
    }

    public java.util.Date getRespondate() {
        return respondate;
    }

    public void setRespondate(java.util.Date respondate) {
        this.respondate = respondate;
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

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
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

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTesttypename() {
        return testtypename;
    }

    public void setTesttypename(String testtypename) {
        this.testtypename = testtypename;
    }

    public java.util.Date getGetdate() {
        return getdate;
    }

    public void setGetdate(java.util.Date getdate) {
        this.getdate = getdate;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public java.util.Date getSenddate() {
        return senddate;
    }

    public void setSenddate(java.util.Date senddate) {
        this.senddate = senddate;
    }

    public String getSendaddr() {
        return sendaddr;
    }

    public void setSendaddr(String sendaddr) {
        this.sendaddr = sendaddr;
    }

    public String getSampledeep() {
        return sampledeep;
    }

    public void setSampledeep(String sampledeep) {
        this.sampledeep = sampledeep;
    }

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getBasearea() {
        return basearea;
    }

    public void setBasearea(String basearea) {
        this.basearea = basearea;
    }

    public String getPlanvar() {
        return planvar;
    }

    public void setPlanvar(String planvar) {
        this.planvar = planvar;
    }

    public String getTasksource() {
        return tasksource;
    }

    public void setTasksource(String tasksource) {
        this.tasksource = tasksource;
    }

    public String getPrdstand() {
        return prdstand;
    }

    public void setPrdstand(String prdstand) {
        this.prdstand = prdstand;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getTeststand() {
        return teststand;
    }

    public void setTeststand(String teststand) {
        this.teststand = teststand;
    }

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
    }

    public String getReportget() {
        return reportget;
    }

    public void setReportget(String reportget) {
        this.reportget = reportget;
    }

    public String getRestdeal() {
        return restdeal;
    }

    public void setRestdeal(String restdeal) {
        this.restdeal = restdeal;
    }

    public String getTimereq() {
        return timereq;
    }

    public void setTimereq(String timereq) {
        this.timereq = timereq;
    }

    public String getTimereqname() {
        return timereqname;
    }

    public void setTimereqname(String timereqname) {
        this.timereqname = timereqname;
    }

    public String getReportgetname() {
        return reportgetname;
    }

    public void setReportgetname(String reportgetname) {
        this.reportgetname = reportgetname;
    }

    public String getRestdealname() {
        return restdealname;
    }

    public void setRestdealname(String restdealname) {
        this.restdealname = restdealname;
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

    public SelectBean<BusGet> getItem() {
        if (item == null)
            item = new SelectBean<BusGet>();

        return item;
    }

    public void setItem(SelectBean<BusGet> item) {
        this.item = item;
    }

    public String getTranidcn() {
        return tranidcn;
    }

    public void setTranidcn(String tranidcn) {
        this.tranidcn = tranidcn;
    }

}
