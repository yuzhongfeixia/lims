package com.gpersist.entity.log;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class TranLog implements BaseBean {

    // 序号;
    private int transerial;

    // 交易流水;
    private String tranid;

    // 操作人员;
    private String tranuser;

    // 操作员姓名;
    private String username;

    // 机构;
    private String trandept;

    // 机构名称;
    private String deptname;

    // 交易代码;
    private String trancode;

    // 交易动作;
    private String tranaction;

    // 交易动作名称;
    private String tranactionname;

    // 说明;
    private String trandesc;
    // 操作详细TranDescDetail
    private String trandescdetail;

    // 交易时间;
    private java.util.Date trandate;

    private int action;

    private String mname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<TranLog> item;

    public TranLog() {
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
        return ToolUtils.CompareProperty((TranLog) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "序号:transerial", "交易流水:tranid", "操作人员:tranuser", "操作员姓名:username", "机构:trandept",
                "机构名称:deptname", "交易代码:trancode", "菜单名称:mname", "交易动作:tranaction", "交易动作名称:tranactionname",
                "说明:trandesc", "操作详细:trandescdetail", "交易时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.transerial = 0;
        this.tranid = "";
        this.tranuser = "";
        this.username = "";
        this.trandept = "";
        this.deptname = "";
        this.trancode = "";
        this.tranaction = "";
        this.tranactionname = "";
        this.trandesc = "";
        this.setTrandescdetail("");
        this.trandate = ToolUtils.GetMinDate();
        this.action = 0;
        this.mname = "";
    }

    public int getTranserial() {
        return transerial;
    }

    public void setTranserial(int transerial) {
        this.transerial = transerial;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTrandept() {
        return trandept;
    }

    public void setTrandept(String trandept) {
        this.trandept = trandept;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public String getTranaction() {
        return tranaction;
    }

    public void setTranaction(String tranaction) {
        this.tranaction = tranaction;
    }

    public String getTranactionname() {
        return tranactionname;
    }

    public void setTranactionname(String tranactionname) {
        this.tranactionname = tranactionname;
    }

    public String getTrandesc() {
        return trandesc;
    }

    public void setTrandesc(String trandesc) {
        this.trandesc = trandesc;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
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

    public SelectBean<TranLog> getItem() {
        if (item == null)
            item = new SelectBean<TranLog>();

        return item;
    }

    public void setItem(SelectBean<TranLog> item) {
        this.item = item;
    }

    public void ActionToTran(int value) {
        switch (value) {
        case 0:
            this.tranaction = TranAction.None.getTranaction();
            break;

        case 1:
            this.tranaction = TranAction.Search.getTranaction();
            break;

        case 2:
            this.tranaction = TranAction.New.getTranaction();
            break;

        case 3:
            this.tranaction = TranAction.Edit.getTranaction();
            break;

        case 4:
            this.tranaction = TranAction.Delete.getTranaction();
            break;

        case 7:
            this.tranaction = TranAction.Deal.getTranaction();
            break;

        case 16:
            this.tranaction = TranAction.Submit.getTranaction();
            break;

        default:
            this.tranaction = TranAction.None.getTranaction();
            break;
        }
    }

    public String getTrandescdetail() {
        return trandescdetail;
    }

    public void setTrandescdetail(String trandescdetail) {
        this.trandescdetail = trandescdetail;
    }
}
