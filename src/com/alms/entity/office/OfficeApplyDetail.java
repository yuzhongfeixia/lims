package com.alms.entity.office;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class OfficeApplyDetail implements BaseBean {

    // 申请编号;
    private String tranid;

    // 明细序号;
    private int officeserial;

    // 办公用品名称;
    private String officename;

    // 数量;
    private int officequan;

    // 数量单位;
    private String quanunit;

    // 数量单位名称;
    private String quanunitname;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<OfficeApplyDetail> item;

    public OfficeApplyDetail() {
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
        return ToolUtils.CompareProperty((OfficeApplyDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "明细序号:officeserial", "办公用品名称:officename", "数量:officequan", "数量单位:quanunit",
                "数量单位名称:quanunitname", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.officeserial = 0;
        this.officename = "";
        this.officequan = 0;
        this.quanunit = "";
        this.quanunitname = "";
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getOfficeserial() {
        return officeserial;
    }

    public void setOfficeserial(int officeserial) {
        this.officeserial = officeserial;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public int getOfficequan() {
        return officequan;
    }

    public void setOfficequan(int officequan) {
        this.officequan = officequan;
    }

    public String getQuanunit() {
        return quanunit;
    }

    public void setQuanunit(String quanunit) {
        this.quanunit = quanunit;
    }

    public String getQuanunitname() {
        return quanunitname;
    }

    public void setQuanunitname(String quanunitname) {
        this.quanunitname = quanunitname;
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

    public SelectBean<OfficeApplyDetail> getItem() {
        if (item == null)
            item = new SelectBean<OfficeApplyDetail>();

        return item;
    }

    public void setItem(SelectBean<OfficeApplyDetail> item) {
        this.item = item;
    }

}
