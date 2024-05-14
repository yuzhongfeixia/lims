package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkCheck implements BaseBean {

    // 盘点单号;
    private String tranid;

    // 检测室
    private String deptid;

    // 检测室
    private String deptname;

    // 仓库编号;
    private String storeid;

    // 仓库名称;
    private String storename;

    // 盘点日期;
    private java.util.Date checkdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 盘点人;
    private String tranuser;

    // 盘点人姓名;
    private String tranusername;

    // 审核人;
    private String checkuser;

    // 审核人姓名;
    private String checkusername;

    // 记帐员;
    private String recouser;

    // 记帐员姓名;
    private String recousername;

    // 备注;
    private String remark;

    // 记录日期;
    private java.util.Date recodate;

    // 是否修改;
    private boolean ismodify;

    // 修改序号;
    private int transerial;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkCheck> item;

    public StkCheck() {
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
        return ToolUtils.CompareProperty((StkCheck) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "盘点单号:tranid", "仓库编号:storeid", "仓库名称:storename", "盘点日期:checkdate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "盘点人:tranuser",
                "盘点人姓名:tranusername", "审核人:checkuser", "审核人姓名:checkusername", "记帐员:recouser", "记帐员姓名:recousername",
                "备注:remark", "记录日期:recodate", "是否修改:ismodify", "修改序号:transerial" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.storeid = "";
        this.storename = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.checkuser = "";
        this.checkusername = "";
        this.recouser = "";
        this.recousername = "";
        this.remark = "";
        this.recodate = ToolUtils.GetMinDate();
        this.ismodify = false;
        this.transerial = 0;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public java.util.Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(java.util.Date checkdate) {
        this.checkdate = checkdate;
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

    public String getRecouser() {
        return recouser;
    }

    public void setRecouser(String recouser) {
        this.recouser = recouser;
    }

    public String getRecousername() {
        return recousername;
    }

    public void setRecousername(String recousername) {
        this.recousername = recousername;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public java.util.Date getRecodate() {
        return recodate;
    }

    public void setRecodate(java.util.Date recodate) {
        this.recodate = recodate;
    }

    public boolean getIsmodify() {
        return ismodify;
    }

    public void setIsmodify(boolean ismodify) {
        this.ismodify = ismodify;
    }

    public int getTranserial() {
        return transerial;
    }

    public void setTranserial(int transerial) {
        this.transerial = transerial;
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

    public SelectBean<StkCheck> getItem() {
        if (item == null)
            item = new SelectBean<StkCheck>();

        return item;
    }

    public void setItem(SelectBean<StkCheck> item) {
        this.item = item;
    }

}
