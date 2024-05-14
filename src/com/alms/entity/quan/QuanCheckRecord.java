package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanCheckRecord implements BaseBean {

    // 业务编号;
    private String tranid;

    // 项目名称;
    private String projname;

    // 检查对象;
    private String checkobject;

    // 检查方法;
    private String checkmethod;

    // 检查人;
    private String checkuser;

    // 检查人姓名;
    private String checkusername;

    // 检查时间;
    private java.util.Date checkdate;

    // 检查内容;
    private String checkcontent;

    // 检查结论;
    private String checkdesc;

    // 问题及处理措施;
    private String dealmeasure;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 备注;
    private String checkremark;

    // 中心主管意见;
    private String directoradvice;

    // 中心主管;
    private String directoruser;

    // 中心主管姓名;
    private String directorusername;

    // 中心主管时间;
    private java.util.Date directordate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanCheckRecord> item;

    public QuanCheckRecord() {
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
        return ToolUtils.CompareProperty((QuanCheckRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "项目名称:projname", "检查对象:checkobject", "检查方法:checkmethod", "检查人:checkuser",
                "检查人姓名:checkusername", "检查时间:checkdate", "检查内容:checkcontent", "检查结论:checkdesc", "问题及处理措施:dealmeasure",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "备注:checkremark", "中心主管意见:directoradvice", "中心主管:directoruser", "中心主管姓名:directorusername",
                "中心主管时间:directordate", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projname = "";
        this.checkobject = "";
        this.checkmethod = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.checkcontent = "";
        this.checkdesc = "";
        this.dealmeasure = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.checkremark = "";
        this.directoradvice = "";
        this.directoruser = "";
        this.directorusername = "";
        // this.directordate = ToolUtils.GetMinDate();
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

    public String getCheckobject() {
        return checkobject;
    }

    public void setCheckobject(String checkobject) {
        this.checkobject = checkobject;
    }

    public String getCheckmethod() {
        return checkmethod;
    }

    public void setCheckmethod(String checkmethod) {
        this.checkmethod = checkmethod;
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

    public String getCheckcontent() {
        return checkcontent;
    }

    public void setCheckcontent(String checkcontent) {
        this.checkcontent = checkcontent;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getDealmeasure() {
        return dealmeasure;
    }

    public void setDealmeasure(String dealmeasure) {
        this.dealmeasure = dealmeasure;
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

    public String getCheckremark() {
        return checkremark;
    }

    public void setCheckremark(String checkremark) {
        this.checkremark = checkremark;
    }

    public String getDirectoradvice() {
        return directoradvice;
    }

    public void setDirectoradvice(String directoradvice) {
        this.directoradvice = directoradvice;
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

    public SelectBean<QuanCheckRecord> getItem() {
        if (item == null)
            item = new SelectBean<QuanCheckRecord>();

        return item;
    }

    public void setItem(SelectBean<QuanCheckRecord> item) {
        this.item = item;
    }

}
