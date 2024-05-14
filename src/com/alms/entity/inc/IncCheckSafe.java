package com.alms.entity.inc;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class IncCheckSafe implements BaseBean {

    // 检查编号;
    private String tranid;

    // 检查人;
    private String checkuser;

    // 检查姓名;
    private String checkusername;

    // 检查时间;
    private java.util.Date checkdate;

    // 发现问题;
    private String dangerdesc;

    // 整改情况;
    private String dealdesc;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 中心负责人;
    private String allowuser;

    // 中心负责人姓名;
    private String allowusername;

    // 中心负责人时间;
    private java.util.Date allowdate;

    // 中心负责人意见;
    private String allowdesc;

    // 办公室负责人;
    private String officeuser;

    // 办公室负责人姓名;
    private String officeusername;

    // 办公室负责人时间;
    private java.util.Date officedate;

    // 办公室处理结果;
    private String officeresult;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IncCheckSafe> item;

    public IncCheckSafe() {
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
        return ToolUtils.CompareProperty((IncCheckSafe) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "检查编号:tranid", "检查人:checkuser", "检查姓名:checkusername", "检查时间:checkdate", "安全隐患:dangerdesc",
                "处理措施:dealdesc", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务动作:flowaction", "业务动作名称:flowactionname",
                "中心负责人:allowuser", "中心负责人姓名:allowusername", "中心负责人时间:allowdate", "中心负责人意见:allowdesc",
                "办公室负责人:officeuser", "办公室负责人姓名:officeusername", "办公室负责人时间:officedate", "办公室处理结果:officeresult",
                "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.dangerdesc = "";
        this.dealdesc = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
        this.officeuser = "";
        this.officeusername = "";
        // this.officedate = ToolUtils.GetMinDate();
        this.officeresult = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getDangerdesc() {
        return dangerdesc;
    }

    public void setDangerdesc(String dangerdesc) {
        this.dangerdesc = dangerdesc;
    }

    public String getDealdesc() {
        return dealdesc;
    }

    public void setDealdesc(String dealdesc) {
        this.dealdesc = dealdesc;
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

    public String getOfficeresult() {
        return officeresult;
    }

    public void setOfficeresult(String officeresult) {
        this.officeresult = officeresult;
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

    public SelectBean<IncCheckSafe> getItem() {
        if (item == null)
            item = new SelectBean<IncCheckSafe>();

        return item;
    }

    public void setItem(SelectBean<IncCheckSafe> item) {
        this.item = item;
    }

}
