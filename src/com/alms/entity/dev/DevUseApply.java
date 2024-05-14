package com.alms.entity.dev;

import java.util.Date;

import com.alms.entity.crm.CrmAccidentDeal;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class DevUseApply implements BaseBean {

    // 处理编号;
    private String tranid;

    // 设备编号
    private String devid;

    // 设备名称
    private String devname;

    // 申请人
    private String applyuser;

    // 申请人姓名
    private String applyusername;

    // 使用项目
    private String useproject;

    // 申请日期
    private Date applydate;
    // 设备状态
    private String devstatus;

    // 设备状态名称
    private String devstatusname;

    // 设备接收人
    private String acceptuser;

    // 设备接收人姓名、
    private String acceptusername;
    // 实验室
    private String labid;

    // 实验室名称
    private String labname;

    // 设备借出状态
    private String borrowstatu;

    // 设备借出状态名称
    private String borrowstatuname;

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

    public DevUseApply() {
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
        return new String[] { "处理编号:tranid", "设备编号:devid", "设备名称:devname", "申请人:applyuser", "申请人姓名:applyusername",
                "申请日期:applydate", "设备状态:devstatus", "设备状态名称:devsatusname", "设备接收人:acceptuser", "设备接收人姓名:acceptusername",
                "实验室:labid", "实验室名称:labname", "设备借出状态:borrowstatu", "设备借出状态名称:borrowstatusname", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "办公室负责人:officeuser",
                "办公室负责人姓名:officeusername", "办公室负责人时间:officedate", "办公室负责人意见:officedesc", "中心负责人:allowuser",
                "中心负责人姓名:allowusername", "中心负责人时间:allowdate", "中心负责人意见:allowdesc", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate", "备注:remark" };
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getApplyuser() {
        return applyuser;
    }

    public void setApplyuser(String applyuser) {
        this.applyuser = applyuser;
    }

    public String getApplyusername() {
        return applyusername;
    }

    public void setApplyusername(String applyusername) {
        this.applyusername = applyusername;
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getDevstatusname() {
        return devstatusname;
    }

    public void setDevstatusname(String devstatusname) {
        this.devstatusname = devstatusname;
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

    public String getBorrowstatu() {
        return borrowstatu;
    }

    public void setBorrowstatu(String borrowstatu) {
        this.borrowstatu = borrowstatu;
    }

    public String getBorrowstatuname() {
        return borrowstatuname;
    }

    public void setBorrowstatuname(String borrowstatuname) {
        this.borrowstatuname = borrowstatuname;
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.devid = "";
        this.devname = "";
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

    public String getUseproject() {
        return useproject;
    }

    public void setUseproject(String useproject) {
        this.useproject = useproject;
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
