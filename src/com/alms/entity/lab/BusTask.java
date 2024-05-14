package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTask implements BaseBean {

    private String tranid;

    // 任务单号;
    private String taskid;

    // 样品编号
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 盲样编号;
    private String samplecode;

    // 样品状态;
    private String samplestatus;

    // 检验性质;
    private String testprop;

    // 收样单号
    private String accsampleid;

    // 检验性质;
    private String testpropname;

    // 规格批号;
    private String samplestand;

    private String checkdesc;

    // 下达人;
    private String senduser;

    // 下达人姓名;
    private String sendusername;

    // 下达日期;
    private java.util.Date senddate;

    // 要求完成日期;
    private java.util.Date reqdate;

    // 承检室;
    private String labid;

    private String labname;

    // 检测室负责人;
    private String labuser;

    // 检测室负责人姓名;
    private String labusername;

    // 领样人;
    private String getuser;

    // 领样人姓名;
    private String getusername;

    // 领样数量;
    private String getcount;

    // 领样日期;
    private java.util.Date getdate;

    // 备注;
    private String taskremark;

    // 退样量;
    private double backcount;

    private String backuser;

    private String backusername;

    // 委托单位
    private String testedname;

    // 委托人
    private String testeduser;

    private java.util.Date backdate;

    // 原始记录上交日期;
    private java.util.Date handdate;

    // 签收人;
    private String acceptuser;

    // 签收人姓名;
    private String acceptusername;

    // 签收人时间;
    private java.util.Date acceptdate;

    // 开始检测日期;
    private java.util.Date begintestdate;

    // 结束检测日期;
    private java.util.Date endtestdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 温度;
    private String testtemp;

    // 湿度;
    private String testhum;

    // 环境情况;
    private String testenv;

    // 设备编号;
    private String devids;

    // 设备名称;
    private String devnames;

    // 复核人;
    private String checkuser;

    // 复核人姓名;
    private String checkusername;

    // 复核日期;
    private java.util.Date checkdate;

    private String recordstatus;

    private String modifydesc;

    // 审批人;
    private String aduituser;

    // 审批人姓名;
    private String aduitusername;

    // 审批日期;
    private java.util.Date aduitdate;

    // 归档人;
    private String processuser;

    // 归档人姓名;
    private String processusername;

    // 归档日期;
    private java.util.Date processdate;

    private boolean isjudge;

    private String judgestatus;

    private String judgestatusname;

    private String teststandardname;

    private String sampletran;

    private int samplecount;

    private String gettype;

    private String sampletype;

    private String samplecatalog;

    // 领样人
    private String takeusername;

    // 领样数量
    private String takenumber;

    // 领样时间
    private java.util.Date takedate;

    // 发样人
    private String sendsampleuser;

    // 取样人签名
    private String takesign;

    // 发样人签名
    private String sendsign;

    private String takepath;

    private String sendpath;

    private String samplestate;

    private String startdate;

    private String enddate;

    private String entertele;

    private java.util.Date accdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTask> item;

    public BusTask() {
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
        return ToolUtils.CompareProperty((BusTask) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "联系电话:entertele", "审批意见:checkdesc", "开始日期:startdate", "结束日期:enddate",
                "领样人:takeusername", "领样数量:takenumber", "领样人签名:takesign", "发样人签名:sendsign", "领样时间:takedate",
                "发样人:sendsampleuser", "样品编号:sampleid", "样品类型:sampletype", "收样编号:accsampleid", "样品名称:samplename",
                "样品状态:samplestatus", "检验性质:testprop", "检验性质:testpropname", "规格批号:samplestand", "下达人:senduser",
                "下达人姓名:sendusername", "下达日期:senddate", "要求完成日期:reqdate", "承检室:labid", "检测室负责人:labuser",
                "检测室负责人姓名:labusername", "领样人:getuser", "领样人姓名:getusername", "领样数量:getcount", "领样日期:getdate",
                "备注:taskremark", "退样量:backcount", "原始记录上交日期:handdate", "签收人:acceptuser", "签收人姓名:acceptusername",
                "签收人时间:acceptdate", "开始检测日期:begintestdate", "结束检测日期:endtestdate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "温度:testtemp", "湿度:testhum",
                "环境情况:testenv", "设备编号:devids", "设备名称:devnames", "复核人:checkuser", "复核人姓名:checkusername",
                "复核日期:checkdate", "审批人:aduituser", "审批人姓名:aduitusername", "审批日期:aduitdate", "归档人:processuser",
                "归档人姓名:processusername", "归档日期:processdate", "样品状态:samplestate", "路径:takepath", "样品状态:sendpath",
                "原始记录状态:recordstatus", "审批驳回:modifydesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.entertele = "";
        this.takepath = "";
        this.sendpath = "";
        this.sendsampleuser = "";
        this.samplestate = "";
        this.taskid = "";
        this.takeusername = "";
        this.takenumber = "";
        this.samplecode = "";
        this.sampleid = "";
        this.checkdesc = "";
        this.sampletype = "";
        this.accsampleid = "";
        this.samplename = "";
        this.samplestatus = "";
        this.testprop = "";
        this.testpropname = "";
        this.samplestand = "";
        this.senduser = "";
        this.sendusername = "";
        this.senddate = ToolUtils.GetMinDate();
        this.reqdate = ToolUtils.GetMinDate();
        this.labid = "";
        this.labname = "";
        this.labuser = "";
        this.labusername = "";
        this.getuser = "";
        this.getusername = "";
        this.getcount = "";
        this.getdate = null;
        this.taskremark = "";
        this.backcount = 0;
        this.backuser = "";
        this.backusername = "";
        this.backdate = null;
        this.handdate = null;
        this.acceptuser = "";
        this.acceptusername = "";
        this.acceptdate = null;
        this.begintestdate = null;
        this.endtestdate = null;
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.testtemp = "";
        this.testhum = "";
        this.testenv = "";
        this.devids = "";
        this.devnames = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = null;
        this.aduituser = "";
        this.aduitusername = "";
        this.aduitdate = null;
        this.processuser = "";
        this.processusername = "";
        this.processdate = null;
        this.isjudge = false;
        this.judgestatus = "";
        this.judgestatusname = "";
        this.teststandardname = "";
        this.sampletran = "";
        this.gettype = "";
        this.samplecatalog = "";
        // this.takedate = ToolUtils.GetNowDate();
        this.takesign = "";
        this.sendsign = "";
        this.startdate = "";
        this.enddate = "";
        this.testedname = "";
        this.testeduser = "";
        this.recordstatus = "";
        this.modifydesc = "";
    }

    public String getEntertele() {
        return entertele;
    }

    public void setEntertele(String entertele) {
        this.entertele = entertele;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
    }

    public String getTesteduser() {
        return testeduser;
    }

    public void setTesteduser(String testeduser) {
        this.testeduser = testeduser;
    }

    public java.util.Date getAccdate() {
        return accdate;
    }

    public void setAccdate(java.util.Date accdate) {
        this.accdate = accdate;
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

    public String getTakepath() {
        return takepath;
    }

    public void setTakepath(String takepath) {
        this.takepath = takepath;
    }

    public String getSendpath() {
        return sendpath;
    }

    public void setSendpath(String sendpath) {
        this.sendpath = sendpath;
    }

    public String getSamplestate() {
        return samplestate;
    }

    public void setSamplestate(String samplestate) {
        this.samplestate = samplestate;
    }

    public String getTakesign() {
        return takesign;
    }

    public void setTakesign(String takesign) {
        this.takesign = takesign;
    }

    public String getSendsign() {
        return sendsign;
    }

    public void setSendsign(String sendsign) {
        this.sendsign = sendsign;
    }

    public String getSendsampleuser() {
        return sendsampleuser;
    }

    public void setSendsampleuser(String sendsampleuser) {
        this.sendsampleuser = sendsampleuser;
    }

    public java.util.Date getTakedate() {
        return takedate;
    }

    public void setTakedate(java.util.Date takedate) {
        this.takedate = takedate;
    }

    public String getTakeusername() {
        return takeusername;
    }

    public void setTakeusername(String takeusername) {
        this.takeusername = takeusername;
    }

    public String getTakenumber() {
        return takenumber;
    }

    public void setTakenumber(String takenumber) {
        this.takenumber = takenumber;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getSampletype() {
        return sampletype;
    }

    public void setSampletype(String sampletype) {
        this.sampletype = sampletype;
    }

    public String getAccsampleid() {
        return accsampleid;
    }

    public void setAccsampleid(String accsampleid) {
        this.accsampleid = accsampleid;
    }

    public String getTestpropname() {
        return testpropname;
    }

    public void setTestpropname(String testpropname) {
        this.testpropname = testpropname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
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

    public String getSamplestand() {
        return samplestand;
    }

    public void setSamplestand(String samplestand) {
        this.samplestand = samplestand;
    }

    public String getSenduser() {
        return senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser;
    }

    public String getSendusername() {
        return sendusername;
    }

    public void setSendusername(String sendusername) {
        this.sendusername = sendusername;
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

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getLabuser() {
        return labuser;
    }

    public void setLabuser(String labuser) {
        this.labuser = labuser;
    }

    public String getLabusername() {
        return labusername;
    }

    public void setLabusername(String labusername) {
        this.labusername = labusername;
    }

    public String getGetuser() {
        return getuser;
    }

    public void setGetuser(String getuser) {
        this.getuser = getuser;
    }

    public String getGetusername() {
        return getusername;
    }

    public void setGetusername(String getusername) {
        this.getusername = getusername;
    }

    public String getGetcount() {
        return getcount;
    }

    public void setGetcount(String getcount) {
        this.getcount = getcount;
    }

    public java.util.Date getGetdate() {
        return getdate;
    }

    public void setGetdate(java.util.Date getdate) {
        this.getdate = getdate;
    }

    public String getTaskremark() {
        return taskremark;
    }

    public void setTaskremark(String taskremark) {
        this.taskremark = taskremark;
    }

    public double getBackcount() {
        return backcount;
    }

    public void setBackcount(double backcount) {
        this.backcount = backcount;
    }

    public String getBackuser() {
        return backuser;
    }

    public void setBackuser(String backuser) {
        this.backuser = backuser;
    }

    public String getBackusername() {
        return backusername;
    }

    public void setBackusername(String backusername) {
        this.backusername = backusername;
    }

    public java.util.Date getBackdate() {
        return backdate;
    }

    public void setBackdate(java.util.Date backdate) {
        this.backdate = backdate;
    }

    public java.util.Date getHanddate() {
        return handdate;
    }

    public void setHanddate(java.util.Date handdate) {
        this.handdate = handdate;
    }

    public String getAcceptuser() {
        return acceptuser;
    }

    public void setAcceptuser(String acceptuser) {
        this.acceptuser = acceptuser;
    }

    public String getAcceptusername() {
        return acceptusername;
    }

    public void setAcceptusername(String acceptusername) {
        this.acceptusername = acceptusername;
    }

    public java.util.Date getAcceptdate() {
        return acceptdate;
    }

    public void setAcceptdate(java.util.Date acceptdate) {
        this.acceptdate = acceptdate;
    }

    public java.util.Date getBegintestdate() {
        return begintestdate;
    }

    public void setBegintestdate(java.util.Date begintestdate) {
        this.begintestdate = begintestdate;
    }

    public java.util.Date getEndtestdate() {
        return endtestdate;
    }

    public void setEndtestdate(java.util.Date endtestdate) {
        this.endtestdate = endtestdate;
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

    public String getTesttemp() {
        return testtemp;
    }

    public void setTesttemp(String testtemp) {
        this.testtemp = testtemp;
    }

    public String getTesthum() {
        return testhum;
    }

    public void setTesthum(String testhum) {
        this.testhum = testhum;
    }

    public String getTestenv() {
        return testenv;
    }

    public void setTestenv(String testenv) {
        this.testenv = testenv;
    }

    public String getDevids() {
        return devids;
    }

    public void setDevids(String devids) {
        this.devids = devids;
    }

    public String getDevnames() {
        return devnames;
    }

    public void setDevnames(String devnames) {
        this.devnames = devnames;
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

    public String getProcessuser() {
        return processuser;
    }

    public void setProcessuser(String processuser) {
        this.processuser = processuser;
    }

    public String getProcessusername() {
        return processusername;
    }

    public void setProcessusername(String processusername) {
        this.processusername = processusername;
    }

    public java.util.Date getProcessdate() {
        return processdate;
    }

    public void setProcessdate(java.util.Date processdate) {
        this.processdate = processdate;
    }

    public boolean isIsjudge() {
        return isjudge;
    }

    public void setIsjudge(boolean isjudge) {
        this.isjudge = isjudge;
    }

    public String getJudgestatus() {
        return judgestatus;
    }

    public void setJudgestatus(String judgestatus) {
        this.judgestatus = judgestatus;
    }

    public String getJudgestatusname() {
        return judgestatusname;
    }

    public void setJudgestatusname(String judgestatusname) {
        this.judgestatusname = judgestatusname;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public int getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(int samplecount) {
        this.samplecount = samplecount;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getSamplecatalog() {
        return samplecatalog;
    }

    public void setSamplecatalog(String samplecatalog) {
        this.samplecatalog = samplecatalog;
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

    public SelectBean<BusTask> getItem() {
        if (item == null)
            item = new SelectBean<BusTask>();

        return item;
    }

    public void setItem(SelectBean<BusTask> item) {
        this.item = item;
    }

    public String getRecordstatus() {
        return recordstatus;
    }

    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    public String getModifydesc() {
        return modifydesc;
    }

    public void setModifydesc(String modifydesc) {
        this.modifydesc = modifydesc;
    }

}
