package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmAccidentDeal implements BaseBean {

    // 处理编号;
    private String tranid;

    // 送检单位;
    private String testedunit;

    // 送检单位名称
    private String testedname;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 事故来源;
    private String oldreport;

    // 事故日期;
    private java.util.Date eventdate;

    // 事故责任人;
    private String eventuser;

    // 事故责任人姓名;
    private String eventusername;

    // 事故原因;
    private String eventreason;

    // 处理措施;
    private String dealdesc;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 办公室负责人;
    private String officeuser;

    // 办公室负责人姓名;
    private String officeusername;

    // 办公室负责人时间;
    private java.util.Date officedate;

    // 办公室负责人意见;
    private String officedesc;

    // 中心负责人;
    private String allowuser;

    // 中心负责人姓名;
    private String allowusername;

    // 中心负责人时间;
    private java.util.Date allowdate;

    // 中心负责人意见;
    private String allowdesc;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmAccidentDeal> item;

    public CrmAccidentDeal() {
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
        return ToolUtils.CompareProperty((CrmAccidentDeal) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "处理编号:tranid", "送检单位:testedunit", "样品编号:sampleid", "样品名称:samplename", "原检测报告编号:oldreport",
                "事故日期:eventdate", "事故责任人:eventuser", "事故责任人姓名:eventusername", "事故原因:eventreason", "处理措施:dealdesc",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "办公室负责人:officeuser", "办公室负责人姓名:officeusername", "办公室负责人时间:officedate", "办公室负责人意见:officedesc",
                "中心负责人:allowuser", "中心负责人姓名:allowusername", "中心负责人时间:allowdate", "中心负责人意见:allowdesc", "业务员:tranuser",
                "业务员姓名:tranusername", "业务员时间:trandate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.testedunit = "";
        this.sampleid = "";
        this.samplename = "";
        this.oldreport = "";
        // this.eventdate = ToolUtils.GetMinDate();
        this.eventuser = "";
        this.eventusername = "";
        this.eventreason = "";
        this.dealdesc = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.officeuser = "";
        this.officeusername = "";
        // this.officedate = ToolUtils.GetMinDate();
        this.officedesc = "";
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTestedunit() {
        return testedunit;
    }

    public void setTestedunit(String testedunit) {
        this.testedunit = testedunit;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
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

    public String getOldreport() {
        return oldreport;
    }

    public void setOldreport(String oldreport) {
        this.oldreport = oldreport;
    }

    public java.util.Date getEventdate() {
        return eventdate;
    }

    public void setEventdate(java.util.Date eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventuser() {
        return eventuser;
    }

    public void setEventuser(String eventuser) {
        this.eventuser = eventuser;
    }

    public String getEventusername() {
        return eventusername;
    }

    public void setEventusername(String eventusername) {
        this.eventusername = eventusername;
    }

    public String getEventreason() {
        return eventreason;
    }

    public void setEventreason(String eventreason) {
        this.eventreason = eventreason;
    }

    public String getDealdesc() {
        return dealdesc;
    }

    public void setDealdesc(String dealdesc) {
        this.dealdesc = dealdesc;
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

    public String getOfficeuser() {
        return officeuser;
    }

    public void setOfficeuser(String officeuser) {
        this.officeuser = officeuser;
    }

    public String getOfficeusername() {
        return officeusername;
    }

    public void setOfficeusername(String officeusername) {
        this.officeusername = officeusername;
    }

    public java.util.Date getOfficedate() {
        return officedate;
    }

    public void setOfficedate(java.util.Date officedate) {
        this.officedate = officedate;
    }

    public String getOfficedesc() {
        return officedesc;
    }

    public void setOfficedesc(String officedesc) {
        this.officedesc = officedesc;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<CrmAccidentDeal> getItem() {
        if (item == null)
            item = new SelectBean<CrmAccidentDeal>();

        return item;
    }

    public void setItem(SelectBean<CrmAccidentDeal> item) {
        this.item = item;
    }

}
