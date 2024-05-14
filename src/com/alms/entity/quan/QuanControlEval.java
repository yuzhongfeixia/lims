package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanControlEval implements BaseBean {

    // 业务编号;
    private String tranid;

    // 项目名称;
    private String projname;

    // 项目类别;
    private String projtype;

    // 完成时间;
    private java.util.Date finishdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 评价;
    private String evalation;

    // 评价人员;
    private String evaluser;

    // 评价人员姓名;
    private String evalusername;

    // 评价日期;
    private java.util.Date evaldate;

    // 主任意见;
    private String directoradv;

    // 主任;
    private String directoruser;

    // 主任姓名;
    private String directorusername;

    // 主任时间;
    private java.util.Date directordate;

    // 备注;
    private String remark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanControlEval> item;

    public QuanControlEval() {
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
        return ToolUtils.CompareProperty((QuanControlEval) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "项目名称:projname", "项目类别:projtype", "完成时间:finishdate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "评价:evalation", "评价人员:evaluser",
                "评价人员姓名:evalusername", "评价日期:evaldate", "主任意见:directoradv", "主任:directoruser", "主任姓名:directorusername",
                "主任时间:directordate", "备注:remark", "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projname = "";
        this.projtype = "";
        this.finishdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.evalation = "";
        this.evaluser = "";
        this.evalusername = "";
        // this.evaldate = ToolUtils.GetMinDate();
        this.directoradv = "";
        this.directoruser = "";
        this.directorusername = "";
        // this.directordate = ToolUtils.GetMinDate();
        this.remark = "";
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

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getProjtype() {
        return projtype;
    }

    public void setProjtype(String projtype) {
        this.projtype = projtype;
    }

    public java.util.Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(java.util.Date finishdate) {
        this.finishdate = finishdate;
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

    public String getEvalation() {
        return evalation;
    }

    public void setEvalation(String evalation) {
        this.evalation = evalation;
    }

    public String getEvaluser() {
        return evaluser;
    }

    public void setEvaluser(String evaluser) {
        this.evaluser = evaluser;
    }

    public String getEvalusername() {
        return evalusername;
    }

    public void setEvalusername(String evalusername) {
        this.evalusername = evalusername;
    }

    public java.util.Date getEvaldate() {
        return evaldate;
    }

    public void setEvaldate(java.util.Date evaldate) {
        this.evaldate = evaldate;
    }

    public String getDirectoradv() {
        return directoradv;
    }

    public void setDirectoradv(String directoradv) {
        this.directoradv = directoradv;
    }

    public String getDirectoruser() {
        return directoruser;
    }

    public void setDirectoruser(String directoruser) {
        this.directoruser = directoruser;
    }

    public String getDirectorusername() {
        return directorusername;
    }

    public void setDirectorusername(String directorusername) {
        this.directorusername = directorusername;
    }

    public java.util.Date getDirectordate() {
        return directordate;
    }

    public void setDirectordate(java.util.Date directordate) {
        this.directordate = directordate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<QuanControlEval> getItem() {
        if (item == null)
            item = new SelectBean<QuanControlEval>();

        return item;
    }

    public void setItem(SelectBean<QuanControlEval> item) {
        this.item = item;
    }

}
