package com.alms.entity.dev;

import java.util.Date;

import com.alms.entity.crm.CrmAccidentDeal;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class DevUseAllot implements BaseBean {

    private String devid;
    private String devname;
    private String allotusername;
    private String allotuser;
    private Date allotdate;
    private String devstatusname;
    private String devstatus;
    private String startproject;
    private String startlabname;
    private String startlabid;
    private String endproject;
    private String endlabname;
    private String endlabid;
    private String tranid;
    private String flowstatus;
    private String flowstatusname;
    private String allotoutuser;
    private String allotoutusername;
    private String allotinuser;
    private String allotinusername;
    private SearchParams search;

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

    public String getAllotusername() {
        return allotusername;
    }

    public void setAllotusername(String allotusername) {
        this.allotusername = allotusername;
    }

    public String getAllotuser() {
        return allotuser;
    }

    public void setAllotuser(String allotuser) {
        this.allotuser = allotuser;
    }

    public Date getAllotdate() {
        return allotdate;
    }

    public void setAllotdate(Date allotdate) {
        this.allotdate = allotdate;
    }

    public String getDevstatusname() {
        return devstatusname;
    }

    public void setDevstatusname(String devstatusname) {
        this.devstatusname = devstatusname;
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getStartproject() {
        return startproject;
    }

    public void setStartproject(String startproject) {
        this.startproject = startproject;
    }

    public String getStartlabname() {
        return startlabname;
    }

    public void setStartlabname(String startlabname) {
        this.startlabname = startlabname;
    }

    public String getStartlabid() {
        return startlabid;
    }

    public void setStartlabid(String startlabid) {
        this.startlabid = startlabid;
    }

    public String getEndproject() {
        return endproject;
    }

    public void setEndproject(String endproject) {
        this.endproject = endproject;
    }

    public String getEndlabname() {
        return endlabname;
    }

    public void setEndlabname(String endlabname) {
        this.endlabname = endlabname;
    }

    public String getEndlabid() {
        return endlabid;
    }

    public void setEndlabid(String endlabid) {
        this.endlabid = endlabid;
    }

    public String getTranid() {
        return tranid;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getAllotoutuser() {
        return allotoutuser;
    }

    public void setAllotoutuser(String allotoutuser) {
        this.allotoutuser = allotoutuser;
    }

    public String getAllotoutusername() {
        return allotoutusername;
    }

    public void setAllotoutusername(String allotoutusername) {
        this.allotoutusername = allotoutusername;
    }

    public String getAllotinuser() {
        return allotinuser;
    }

    public void setAllotinuser(String allotinuser) {
        this.allotinuser = allotinuser;
    }

    public String getAllotinusername() {
        return allotinusername;
    }

    public void setAllotinusername(String allotinusername) {
        this.allotinusername = allotinusername;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    private DataDeal deal;

    private SelectBean<CrmAccidentDeal> item;

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

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {

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
