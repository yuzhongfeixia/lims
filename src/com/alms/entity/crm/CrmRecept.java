package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmRecept implements BaseBean {

    // 接待编号;
    private String tranid;

    // 接待时间;
    private java.util.Date trandate;

    // 接待地点;
    private String receptaddr;

    // 接待对象;
    private String receptobject;

    // 事由;
    private String receptreason;

    // 处理描述;
    private String receptdesc;

    // 处理类型;
    private String recepttype;

    // 处理类型名称;
    private String recepttypename;

    // 接待人;
    private String tranuser;

    // 接待人姓名;
    private String tranusername;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmRecept> item;

    public CrmRecept() {
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
        return ToolUtils.CompareProperty((CrmRecept) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "接待编号:tranid", "接待时间:trandate", "接待地点:receptaddr", "接待对象:receptobject", "事由:receptreason",
                "处理描述:receptdesc", "处理类型:recepttype", "处理类型名称:recepttypename", "接待人:tranuser", "接待人姓名:tranusername",
                "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.trandate = ToolUtils.GetMinDate();
        this.receptaddr = "";
        this.receptobject = "";
        this.receptreason = "";
        this.receptdesc = "";
        this.recepttype = "";
        this.recepttypename = "";
        this.tranuser = "";
        this.tranusername = "";
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getReceptaddr() {
        return receptaddr;
    }

    public void setReceptaddr(String receptaddr) {
        this.receptaddr = receptaddr;
    }

    public String getReceptobject() {
        return receptobject;
    }

    public void setReceptobject(String receptobject) {
        this.receptobject = receptobject;
    }

    public String getReceptreason() {
        return receptreason;
    }

    public void setReceptreason(String receptreason) {
        this.receptreason = receptreason;
    }

    public String getReceptdesc() {
        return receptdesc;
    }

    public void setReceptdesc(String receptdesc) {
        this.receptdesc = receptdesc;
    }

    public String getRecepttype() {
        return recepttype;
    }

    public void setRecepttype(String recepttype) {
        this.recepttype = recepttype;
    }

    public String getRecepttypename() {
        return recepttypename;
    }

    public void setRecepttypename(String recepttypename) {
        this.recepttypename = recepttypename;
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

    public SelectBean<CrmRecept> getItem() {
        if (item == null)
            item = new SelectBean<CrmRecept>();

        return item;
    }

    public void setItem(SelectBean<CrmRecept> item) {
        this.item = item;
    }

}
