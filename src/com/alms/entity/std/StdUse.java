package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdUse implements BaseBean {

    // 业务编号;
    private String tranid;

    // 物质名称;
    private String stdname;

    // 规格;
    private String stdsize;

    // 数量;
    private String stdquanity;

    // 入库日期;
    private java.util.Date indate;

    // 有效期;
    private int validmonth;

    // 使用情况;
    private String usesituation;

    // 备注;
    private String remark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    private String parameterid;

    private String parametername;

    private String sampleid;

    private String samplename;

    private String sampletran;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdUse> item;

    public StdUse() {
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
        return ToolUtils.CompareProperty((StdUse) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "物质名称:stdname", "规格:stdsize", "数量:stdquanity", "入库日期:indate",
                "有效期:validdate", "使用情况:usesituation", "备注:remark", "业务员:tranuser", "业务员姓名:tranusername",
                "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.stdname = "";
        this.stdsize = "";
        this.stdquanity = "";
        this.indate = ToolUtils.GetMinDate();
        this.usesituation = "";
        this.remark = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.parameterid = "";
        this.parametername = "";
        this.sampleid = "";
        this.samplename = "";
        this.sampletran = "";
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getStdsize() {
        return stdsize;
    }

    public void setStdsize(String stdsize) {
        this.stdsize = stdsize;
    }

    public String getStdquanity() {
        return stdquanity;
    }

    public void setStdquanity(String stdquanity) {
        this.stdquanity = stdquanity;
    }

    public java.util.Date getIndate() {
        return indate;
    }

    public void setIndate(java.util.Date indate) {
        this.indate = indate;
    }

    public int getValidmonth() {
        return validmonth;
    }

    public void setValidmonth(int validmonth) {
        this.validmonth = validmonth;
    }

    public String getUsesituation() {
        return usesituation;
    }

    public void setUsesituation(String usesituation) {
        this.usesituation = usesituation;
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

    public SelectBean<StdUse> getItem() {
        if (item == null)
            item = new SelectBean<StdUse>();

        return item;
    }

    public void setItem(SelectBean<StdUse> item) {
        this.item = item;
    }

}
