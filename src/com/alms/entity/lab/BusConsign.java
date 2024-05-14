package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusConsign implements BaseBean {

    // 业务编号(委托编号);
    private String tranid;

    // 委托单位名称;
    private String consignname;

    // 委托人;
    private String consigncontact;

    // 联系电话;
    private String consigntele;

    // 通讯地址;
    private String consignaddr;

    // 邮政编号;
    private String consignpost;

    // 委托日期;
    private java.util.Date consigndate;

    // 时间要求;
    private String timereq;

    // 时间要求名称;
    private String timereqname;

    // 报告提取方式;
    private String reportget;

    // 余样处置方式;
    private String restdeal;

    // 收样人;
    private String sampleuser;

    // 收样人姓名;
    private String sampleusername;

    // 收样日期;
    private java.util.Date sampledate;

    // 检验费用;
    private double testfee;

    // 咨询电话;
    private String refertele;

    // 关联抽样单编号;
    private String getid;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 备注;
    private String tranremark;

    // 来样方式;
    private String gettype;

    // 抽样单类型名称;
    private String gettypename;

    // 是否下单;
    private boolean istask;

    // 是否合格;
    private boolean isok;

    // 业务员时间;
    private java.util.Date trandate;

    // 检测类别;
    private String testtype;

    // 检测依据数据类型名称;
    private String testtypename;

    // 基地名称;
    private String basename;

    // 基地面积;
    private String basearea;

    // 抽样日期;
    private java.util.Date getdate;

    // 采样深度;
    private String sampledeep;

    // 抽/送样者;
    private String getusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusConsign> item;

    public BusConsign() {
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
        return ToolUtils.CompareProperty((BusConsign) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号(委托编号):tranid", "委托单位名称:consignname", "委托人:consigncontact", "联系电话:consigntele",
                "通讯地址:consignaddr", "邮政编号:consignpost", "委托日期:consigndate", "时间要求:timereq", "时间要求名称:timereqname",
                "报告提取方式:reportget", "余样处置方式:restdeal", "收样人:sampleuser", "收样人姓名:sampleusername", "收样日期:sampledate",
                "检验费用:testfee", "咨询电话:refertele", "关联抽样单编号:getid", "业务动作:flowaction", "业务动作名称:flowactionname",
                "业务状态:flowstatus", "业务状态名称:flowstatusname", "备注:tranremark", "来样方式:gettype", "抽样单类型名称:gettypename",
                "是否下单:istask", "是否合格:isok", "业务员时间:trandate", "检测类别:testtype", "检测依据数据类型名称:testtypename",
                "基地名称:basename", "基地面积:basearea", "抽样日期:getdate", "采样深度:sampledeep", "抽/送样者:getusername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.consignname = "";
        this.consigncontact = "";
        this.consigntele = "";
        this.consignaddr = "";
        this.consignpost = "";
        this.consigndate = ToolUtils.GetMinDate();
        this.timereq = "";
        this.timereqname = "";
        this.reportget = "";
        this.restdeal = "";
        this.sampleuser = "";
        this.sampleusername = "";
        this.sampledate = ToolUtils.GetMinDate();
        this.testfee = 0;
        this.refertele = "";
        this.getid = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranremark = "";
        this.gettype = "";
        this.gettypename = "";
        this.istask = false;
        this.isok = false;
        this.trandate = ToolUtils.GetMinDate();
        this.testtype = "";
        this.testtypename = "";
        this.basename = "";
        this.basearea = "";
        this.getdate = ToolUtils.GetMinDate();
        this.sampledeep = "";
        this.getusername = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getConsignname() {
        return consignname;
    }

    public void setConsignname(String consignname) {
        this.consignname = consignname;
    }

    public String getConsigncontact() {
        return consigncontact;
    }

    public void setConsigncontact(String consigncontact) {
        this.consigncontact = consigncontact;
    }

    public String getConsigntele() {
        return consigntele;
    }

    public void setConsigntele(String consigntele) {
        this.consigntele = consigntele;
    }

    public String getConsignaddr() {
        return consignaddr;
    }

    public void setConsignaddr(String consignaddr) {
        this.consignaddr = consignaddr;
    }

    public String getConsignpost() {
        return consignpost;
    }

    public void setConsignpost(String consignpost) {
        this.consignpost = consignpost;
    }

    public java.util.Date getConsigndate() {
        return consigndate;
    }

    public void setConsigndate(java.util.Date consigndate) {
        this.consigndate = consigndate;
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

    public java.util.Date getSampledate() {
        return sampledate;
    }

    public void setSampledate(java.util.Date sampledate) {
        this.sampledate = sampledate;
    }

    public double getTestfee() {
        return testfee;
    }

    public void setTestfee(double testfee) {
        this.testfee = testfee;
    }

    public String getRefertele() {
        return refertele;
    }

    public void setRefertele(String refertele) {
        this.refertele = refertele;
    }

    public String getGetid() {
        return getid;
    }

    public void setGetid(String getid) {
        this.getid = getid;
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

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
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

    public boolean getIstask() {
        return istask;
    }

    public void setIstask(boolean istask) {
        this.istask = istask;
    }

    public boolean getIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
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

    public java.util.Date getGetdate() {
        return getdate;
    }

    public void setGetdate(java.util.Date getdate) {
        this.getdate = getdate;
    }

    public String getSampledeep() {
        return sampledeep;
    }

    public void setSampledeep(String sampledeep) {
        this.sampledeep = sampledeep;
    }

    public String getGetusername() {
        return getusername;
    }

    public void setGetusername(String getusername) {
        this.getusername = getusername;
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

    public SelectBean<BusConsign> getItem() {
        if (item == null)
            item = new SelectBean<BusConsign>();

        return item;
    }

    public void setItem(SelectBean<BusConsign> item) {
        this.item = item;
    }

}
