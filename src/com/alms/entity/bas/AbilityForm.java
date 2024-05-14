package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class AbilityForm implements BaseBean {

    // 中心能力表编号;
    private String abilityformid;

    // 中心能力表名称;
    private String abilityformname;

    // 中心能力表文件地址;
    private String abilityformurl;

    // 能力表描述;
    private String abilityformdesc;
    // 能力表版本号;
    private String abilityformnum;

    private java.util.Date trandate;

    private String tranuser;

    private String tranusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<AbilityForm> item;

    public AbilityForm() {
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
        return ToolUtils.CompareProperty((AbilityForm) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "中心能力表编号:abilityformid", "中心能力表名称:abilityformname", "中心能力表文件地址:abilityformurl",
                "能力表描述:abilityformdesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    public String getAbilityformid() {
        return abilityformid;
    }

    public void setAbilityformid(String abilityformid) {
        this.abilityformid = abilityformid;
    }

    public String getAbilityformname() {
        return abilityformname;
    }

    public void setAbilityformname(String abilityformname) {
        this.abilityformname = abilityformname;
    }

    public String getAbilityformurl() {
        return abilityformurl;
    }

    public void setAbilityformurl(String abilityformurl) {
        this.abilityformurl = abilityformurl;
    }

    public String getAbilityformdesc() {
        return abilityformdesc;
    }

    public void setAbilityformdesc(String abilityformdesc) {
        this.abilityformdesc = abilityformdesc;
    }

    public String getAbilityformnum() {
        return abilityformnum;
    }

    public void setAbilityformnum(String abilityformnum) {
        this.abilityformnum = abilityformnum;
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

    @Override
    public void OnInit() {
        this.abilityformid = "";
        this.abilityformname = "";
        this.abilityformurl = "";
        this.abilityformdesc = "";
        this.tranusername = "";
        this.tranuser = "";
        this.abilityformnum = "";
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

    public SelectBean<AbilityForm> getItem() {
        if (item == null)
            item = new SelectBean<AbilityForm>();

        return item;
    }

    public void setItem(SelectBean<AbilityForm> item) {
        this.item = item;
    }

}
