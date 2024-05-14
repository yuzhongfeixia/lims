package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class BuyApply implements BaseBean {

    // 业务编号;
    private String tranid;

    // 设备名称;
    private String devname;

    // 生产厂商;
    private String factoryname;

    // 设备类型;
    private String devtype;

    // 设备类型名称;
    private String devtypename;

    // 设备型号;
    private String devstandard;

    // 技术指标;
    private String devrange;

    // 设备数量;
    private String devcount;

    // 数量单位;
    private String devunit;

    // 数量单位名称;
    private String devunitname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 申请理由;
    private String applyreason;

    // 申请部门(检测室);
    private String applydept;

    // 申请部门(检测室);
    private String applydeptname;

    // 申请人;
    private String applyuser;

    // 申请人姓名;
    private String applyusername;

    // 申请日期;
    private java.util.Date applydate;

    // 评审意见;
    private String judgedesc;

    // 评审人(办公室主任);
    private String judgeuser;

    // 评审人姓名;
    private String judgeusername;

    // 评审日期;
    private java.util.Date judgedate;

    // 批准意见;
    private String approvedesc;

    // 批准人(主任或副主任);
    private String approveuser;

    // 批准人姓名;
    private String approveusername;

    // 批准日期;
    private java.util.Date approvedate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BuyApply> item;

    public BuyApply() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.CheckComboValue(this.getDevtype())) {
            msg.setErrmsg("请选择设备类型！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BuyApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "设备名称:devname", "生产厂商:factoryname", "设备类型:devtype", "设备类型名称:devtypename",
                "设备型号:devstandard", "技术指标:devrange", "设备数量:devcount", "数量单位:devunit", "数量单位名称:devunitname",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "申请理由:applyreason", "申请部门(检测室):applydept", "申请部门(检测室):applydeptname", "申请人:applyuser",
                "申请人姓名:applyusername", "申请日期:applydate", "评审意见:judgedesc", "评审人(办公室主任):judgeuser",
                "评审人姓名:judgeusername", "评审日期:judgedate", "批准意见:approvedesc", "批准人(主任或副主任):approveuser",
                "批准人姓名:approveusername", "批准日期:approvedate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.devname = "";
        this.factoryname = "";
        this.devstandard = "";
        this.devrange = "";
        this.devtype = "";
        this.devtypename = "";
        this.devcount = "";
        this.devunit = "";
        this.devunitname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.applyreason = "";
        this.applydept = "";
        this.applydeptname = "";
        this.applyuser = "";
        this.applyusername = "";
        this.applydate = ToolUtils.GetMinDate();
        this.judgedesc = "";
        this.judgeuser = "";
        this.judgeusername = "";
        this.judgedate = ToolUtils.GetMinDate();
        this.approvedesc = "";
        this.approveuser = "";
        this.approveusername = "";
        this.approvedate = ToolUtils.GetMinDate();
    }

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype;
    }

    public String getDevtypename() {
        return devtypename;
    }

    public void setDevtypename(String devtypename) {
        this.devtypename = devtypename;
    }

    public String getApplydeptname() {
        return applydeptname;
    }

    public void setApplydeptname(String applydeptname) {
        this.applydeptname = applydeptname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
    }

    public String getDevcount() {
        return devcount;
    }

    public void setDevcount(String devcount) {
        this.devcount = devcount;
    }

    public String getDevunit() {
        return devunit;
    }

    public void setDevunit(String devunit) {
        this.devunit = devunit;
    }

    public String getDevunitname() {
        return devunitname;
    }

    public void setDevunitname(String devunitname) {
        this.devunitname = devunitname;
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

    public String getApplyreason() {
        return applyreason;
    }

    public void setApplyreason(String applyreason) {
        this.applyreason = applyreason;
    }

    public String getApplydept() {
        return applydept;
    }

    public void setApplydept(String applydept) {
        this.applydept = applydept;
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

    public java.util.Date getApplydate() {
        return applydate;
    }

    public void setApplydate(java.util.Date applydate) {
        this.applydate = applydate;
    }

    public String getJudgedesc() {
        return judgedesc;
    }

    public void setJudgedesc(String judgedesc) {
        this.judgedesc = judgedesc;
    }

    public String getJudgeuser() {
        return judgeuser;
    }

    public void setJudgeuser(String judgeuser) {
        this.judgeuser = judgeuser;
    }

    public String getJudgeusername() {
        return judgeusername;
    }

    public void setJudgeusername(String judgeusername) {
        this.judgeusername = judgeusername;
    }

    public java.util.Date getJudgedate() {
        return judgedate;
    }

    public void setJudgedate(java.util.Date judgedate) {
        this.judgedate = judgedate;
    }

    public String getApprovedesc() {
        return approvedesc;
    }

    public void setApprovedesc(String approvedesc) {
        this.approvedesc = approvedesc;
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

    public SelectBean<BuyApply> getItem() {
        if (item == null)
            item = new SelectBean<BuyApply>();

        return item;
    }

    public void setItem(SelectBean<BuyApply> item) {
        this.item = item;
    }

}
