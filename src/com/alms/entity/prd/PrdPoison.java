package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdPoison implements BaseBean {

    // 申请编号;
    private String tranid;

    // 使用项目;
    private String projectid;

    // 耗材类别;
    private String prdtype;

    // 耗材分类名称;
    private String prdtypename;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 申请人;
    private String tranuser;

    // 申请人姓名;
    private String tranusername;

    // 申请日期;
    private java.util.Date trandate;

    // 室主任;
    private String confirmuser;

    // 室主任姓名;
    private String confirmusername;

    // 室主任确认日期;
    private java.util.Date confirmdate;

    // 室主任确认意见;
    private String confirmdesc;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdPoison> item;

    public PrdPoison() {
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
        return ToolUtils.CompareProperty((PrdPoison) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "使用项目:projectid", "耗材类别:prdtype", "耗材分类名称:prdtypename", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "申请人:tranuser",
                "申请人姓名:tranusername", "申请日期:trandate", "室主任:confirmuser", "室主任姓名:confirmusername",
                "室主任确认日期:confirmdate", "室主任确认意见:confirmdesc", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projectid = "";
        this.prdtype = "";
        this.prdtypename = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.confirmuser = "";
        this.confirmusername = "";
        this.confirmdate = ToolUtils.GetMinDate();
        this.confirmdesc = "";
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getPrdtype() {
        return prdtype;
    }

    public void setPrdtype(String prdtype) {
        this.prdtype = prdtype;
    }

    public String getPrdtypename() {
        return prdtypename;
    }

    public void setPrdtypename(String prdtypename) {
        this.prdtypename = prdtypename;
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

    public String getConfirmuser() {
        return confirmuser;
    }

    public void setConfirmuser(String confirmuser) {
        this.confirmuser = confirmuser;
    }

    public String getConfirmusername() {
        return confirmusername;
    }

    public void setConfirmusername(String confirmusername) {
        this.confirmusername = confirmusername;
    }

    public java.util.Date getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(java.util.Date confirmdate) {
        this.confirmdate = confirmdate;
    }

    public String getConfirmdesc() {
        return confirmdesc;
    }

    public void setConfirmdesc(String confirmdesc) {
        this.confirmdesc = confirmdesc;
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

    public SelectBean<PrdPoison> getItem() {
        if (item == null)
            item = new SelectBean<PrdPoison>();

        return item;
    }

    public void setItem(SelectBean<PrdPoison> item) {
        this.item = item;
    }

}
