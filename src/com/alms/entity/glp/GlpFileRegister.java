package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFileRegister implements BaseBean {

    // 登记编号;
    private String tranid;

    // 申请编号;
    private String applyid;

    // 文件号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 借阅人;
    private String userid;

    // 借阅人姓名;
    private String username;

    // 借阅时间;
    private java.util.Date borrowdate;

    // 借阅类型;
    private String borrowtype;

    // 借阅类型名称;
    private String borrowtypename;

    // 是否借阅;
    private boolean isborrow;

    // 是否归还;
    private boolean isreturn;

    // 归还时间;
    private java.util.Date returndate;

    // 备注;
    private String remark;

    // 登记人;
    private String tranuser;

    // 登记人姓名;
    private String tranusername;

    // 登记时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFileRegister> item;

    public GlpFileRegister() {
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
        return ToolUtils.CompareProperty((GlpFileRegister) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "登记编号:tranid", "申请编号:applyid", "文件号:fileid", "文件名称:filename", "借阅人:userid",
                "借阅人姓名:username", "借阅时间:borrowdate", "借阅类型:borrowtype", "借阅类型名称:borrowtypename", "是否借阅:isborrow",
                "是否归还:isreturn", "归还时间:returndate", "备注:remark", "登记人:tranuser", "登记人姓名:tranusername",
                "登记时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.applyid = "";
        this.fileid = "";
        this.filename = "";
        this.userid = "";
        this.username = "";
        this.borrowdate = ToolUtils.GetMinDate();
        this.borrowtype = "";
        this.borrowtypename = "";
        this.isborrow = false;
        this.isreturn = false;
        // this.returndate = ToolUtils.GetMinDate();
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

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public java.util.Date getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(java.util.Date borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getBorrowtype() {
        return borrowtype;
    }

    public void setBorrowtype(String borrowtype) {
        this.borrowtype = borrowtype;
    }

    public String getBorrowtypename() {
        return borrowtypename;
    }

    public void setBorrowtypename(String borrowtypename) {
        this.borrowtypename = borrowtypename;
    }

    public boolean getIsborrow() {
        return isborrow;
    }

    public void setIsborrow(boolean isborrow) {
        this.isborrow = isborrow;
    }

    public boolean getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(boolean isreturn) {
        this.isreturn = isreturn;
    }

    public java.util.Date getReturndate() {
        return returndate;
    }

    public void setReturndate(java.util.Date returndate) {
        this.returndate = returndate;
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

    public SelectBean<GlpFileRegister> getItem() {
        if (item == null)
            item = new SelectBean<GlpFileRegister>();

        return item;
    }

    public void setItem(SelectBean<GlpFileRegister> item) {
        this.item = item;
    }

}
