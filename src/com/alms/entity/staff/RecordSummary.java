package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class RecordSummary implements BaseBean {

    // 业务编号;
    private String tranid;

    // 培训目标;
    private String traintarget;

    // 培训对象;
    private String trainobject;

    // 培训项目;
    private String traincontent;

    // 培训总结;
    private String trainresult;

    // 业务人员;
    private String tranuser;

    // 业务人名称;
    private String tranusername;

    // 业务日期;
    private java.util.Date trandate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 审核人;
    private String aduituser;

    // 审核人姓名;
    private String aduitusername;

    // 审核日期;
    private java.util.Date aduitdate;

    // 审核说明;
    private String aduitremark;

    // 审批人;
    private String checkuser;

    // 审批人姓名;
    private String checkusername;

    private String userid;

    // 审批日期;
    private java.util.Date checkdate;

    // 审批说明;
    private String checkremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<RecordSummary> item;

    public RecordSummary() {
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
        return ToolUtils.CompareProperty((RecordSummary) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "培训目标:traintarget", "培训对象:trainobject", "培训项目:traincontent",
                "培训总结:trainresult", "业务人员:tranuser", "业务人名称:tranusername", "业务日期:trandate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "审核人:aduituser",
                "审核人姓名:aduitusername", "审核日期:aduitdate", "审核说明:aduitremark", "审批人:checkuser", "审批人姓名:checkusername",
                "审批日期:checkdate", "审批说明:checkremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.traintarget = "";
        this.trainobject = "";
        this.traincontent = "";
        this.trainresult = "";
        this.tranuser = "";
        this.tranusername = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.aduituser = "";
        this.aduitusername = "";
        this.aduitremark = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkremark = "";
        this.userid = "";
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTraintarget() {
        return traintarget;
    }

    public void setTraintarget(String traintarget) {
        this.traintarget = traintarget;
    }

    public String getTrainobject() {
        return trainobject;
    }

    public void setTrainobject(String trainobject) {
        this.trainobject = trainobject;
    }

    public String getTraincontent() {
        return traincontent;
    }

    public void setTraincontent(String traincontent) {
        this.traincontent = traincontent;
    }

    public String getTrainresult() {
        return trainresult;
    }

    public void setTrainresult(String trainresult) {
        this.trainresult = trainresult;
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

    public String getAduitremark() {
        return aduitremark;
    }

    public void setAduitremark(String aduitremark) {
        this.aduitremark = aduitremark;
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

    public String getCheckremark() {
        return checkremark;
    }

    public void setCheckremark(String checkremark) {
        this.checkremark = checkremark;
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

    public SelectBean<RecordSummary> getItem() {
        if (item == null)
            item = new SelectBean<RecordSummary>();

        return item;
    }

    public void setItem(SelectBean<RecordSummary> item) {
        this.item = item;
    }

}