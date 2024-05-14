package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanControlPlanDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 时间;
    private String plantime;

    // 内容;
    private String plancontent;

    // 组织机构(人员);
    private String orgdept;

    // 备注;
    private String planremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanControlPlanDetail> item;

    public QuanControlPlanDetail() {
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
        return ToolUtils.CompareProperty((QuanControlPlanDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "时间:plantime", "内容:plancontent", "组织机构(人员):orgdept", "备注:planremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.plantime = "";
        this.plancontent = "";
        this.orgdept = "";
        this.planremark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getPlantime() {
        return plantime;
    }

    public void setPlantime(String plantime) {
        this.plantime = plantime;
    }

    public String getPlancontent() {
        return plancontent;
    }

    public void setPlancontent(String plancontent) {
        this.plancontent = plancontent;
    }

    public String getOrgdept() {
        return orgdept;
    }

    public void setOrgdept(String orgdept) {
        this.orgdept = orgdept;
    }

    public String getPlanremark() {
        return planremark;
    }

    public void setPlanremark(String planremark) {
        this.planremark = planremark;
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

    public SelectBean<QuanControlPlanDetail> getItem() {
        if (item == null)
            item = new SelectBean<QuanControlPlanDetail>();

        return item;
    }

    public void setItem(SelectBean<QuanControlPlanDetail> item) {
        this.item = item;
    }

}
