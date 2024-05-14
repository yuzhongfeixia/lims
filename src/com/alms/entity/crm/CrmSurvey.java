package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmSurvey implements BaseBean {

    // 调查编号;
    private String tranid;

    // 客户名称;
    private String custname;

    // 检测项目;
    private String testitem;

    // 地址;
    private String custaddr;

    // 联系电话;
    private String linktele;

    // 调查日期;
    private java.util.Date custdate;

    // 备注;
    private String remark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmSurvey> item;

    public CrmSurvey() {
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
        return ToolUtils.CompareProperty((CrmSurvey) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "调查编号:tranid", "客户名称:custname", "检测项目:testitem", "地址:custaddr", "联系电话:linktele",
                "调查日期:custdate", "备注:remark", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.custname = "";
        this.testitem = "";
        this.custaddr = "";
        this.linktele = "";
        // this.custdate = ToolUtils.GetMinDate();
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

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getTestitem() {
        return testitem;
    }

    public void setTestitem(String testitem) {
        this.testitem = testitem;
    }

    public String getCustaddr() {
        return custaddr;
    }

    public void setCustaddr(String custaddr) {
        this.custaddr = custaddr;
    }

    public String getLinktele() {
        return linktele;
    }

    public void setLinktele(String linktele) {
        this.linktele = linktele;
    }

    public java.util.Date getCustdate() {
        return custdate;
    }

    public void setCustdate(java.util.Date custdate) {
        this.custdate = custdate;
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

    public SelectBean<CrmSurvey> getItem() {
        if (item == null)
            item = new SelectBean<CrmSurvey>();

        return item;
    }

    public void setItem(SelectBean<CrmSurvey> item) {
        this.item = item;
    }

}
