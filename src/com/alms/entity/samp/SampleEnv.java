package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleEnv implements BaseBean {

    // 业务编号;
    private String tranid;

    // 受检企业;
    private String entername;

    // 企业法人;
    private String enterlegal;

    // 企业性质;
    private String entertype;

    // 企业性质名称
    private String entertypename;

    // 邮编;
    private String enterpost;

    // 联系电话;
    private String entertele;

    // 企业地址;
    private String enteraddress;

    // 检验类别;
    private String testtype;

    // 检验类别名称;
    private String testtypename;

    // 基地名称;
    private String enterbase;

    // 采样时间;
    private java.util.Date sampledate;

    // 采样深度;
    private String sampledeep;

    // 采样深度名称;
    private String sampledeepname;

    // 种植品种;
    private String sampleplant;

    // 基地面积;
    private String basearea;

    // 备注;
    private String remark;

    // 采样员;
    private String sampleuser;

    // 采样员姓名;
    private String sampleusername;

    // 采样员时间;
    private java.util.Date sampleuserdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleEnv> item;

    public SampleEnv() {
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
        return ToolUtils.CompareProperty((SampleEnv) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "受检企业:entername", "企业法人:enterlegal", "企业性质:entertype", "邮编:enterpost",
                "联系电话:entertele", "企业地址:enteraddress", "检验类别:testtype", "检验类别名称:testtypename", "基地名称:enterbase",
                "采样时间:sampledate", "采样深度:sampledeep", "采样深度名称:sampledeepname", "种植品种:sampleplant", "基地面积:basearea",
                "备注:remark", "采样员:sampleuser", "采样员姓名:sampleusername", "采样员时间:sampleuserdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.entername = "";
        this.enterlegal = "";
        this.entertype = "";
        this.enterpost = "";
        this.entertele = "";
        this.enteraddress = "";
        this.testtype = "";
        this.testtypename = "";
        this.enterbase = "";
        this.sampledate = ToolUtils.GetMinDate();
        this.sampledeep = "";
        this.sampledeepname = "";
        this.sampleplant = "";
        this.basearea = "";
        this.remark = "";
        this.sampleuser = "";
        this.sampleusername = "";
        // this.sampleuserdate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getEnterlegal() {
        return enterlegal;
    }

    public void setEnterlegal(String enterlegal) {
        this.enterlegal = enterlegal;
    }

    public String getEntertype() {
        return entertype;
    }

    public void setEntertype(String entertype) {
        this.entertype = entertype;
    }

    public String getEntertypename() {
        return entertypename;
    }

    public void setEntertypename(String entertypename) {
        this.entertypename = entertypename;
    }

    public String getEnterpost() {
        return enterpost;
    }

    public void setEnterpost(String enterpost) {
        this.enterpost = enterpost;
    }

    public String getEntertele() {
        return entertele;
    }

    public void setEntertele(String entertele) {
        this.entertele = entertele;
    }

    public String getEnteraddress() {
        return enteraddress;
    }

    public void setEnteraddress(String enteraddress) {
        this.enteraddress = enteraddress;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTesttypename() {
        return testtypename;
    }

    public void setTesttypename(String testtypename) {
        this.testtypename = testtypename;
    }

    public String getEnterbase() {
        return enterbase;
    }

    public void setEnterbase(String enterbase) {
        this.enterbase = enterbase;
    }

    public java.util.Date getSampledate() {
        return sampledate;
    }

    public void setSampledate(java.util.Date sampledate) {
        this.sampledate = sampledate;
    }

    public String getSampledeep() {
        return sampledeep;
    }

    public void setSampledeep(String sampledeep) {
        this.sampledeep = sampledeep;
    }

    public String getSampledeepname() {
        return sampledeepname;
    }

    public void setSampledeepname(String sampledeepname) {
        this.sampledeepname = sampledeepname;
    }

    public String getSampleplant() {
        return sampleplant;
    }

    public void setSampleplant(String sampleplant) {
        this.sampleplant = sampleplant;
    }

    public String getBasearea() {
        return basearea;
    }

    public void setBasearea(String basearea) {
        this.basearea = basearea;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleuser() {
        return sampleuser;
    }

    public void setSampleuser(String sampleuser) {
        this.sampleuser = sampleuser;
    }

    public String getSampleusername() {
        return sampleusername;
    }

    public void setSampleusername(String sampleusername) {
        this.sampleusername = sampleusername;
    }

    public java.util.Date getSampleuserdate() {
        return sampleuserdate;
    }

    public void setSampleuserdate(java.util.Date sampleuserdate) {
        this.sampleuserdate = sampleuserdate;
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

    public SelectBean<SampleEnv> getItem() {
        if (item == null)
            item = new SelectBean<SampleEnv>();

        return item;
    }

    public void setItem(SelectBean<SampleEnv> item) {
        this.item = item;
    }

}
