package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasTest implements BaseBean {

    // 检测依据编号;
    private String teststandard;

    // 检测依据代码;
    private String teststandardcode;

    // 检测依据名称;
    private String teststandardname;

    // 检测依据文件地址;
    private String teststandardurl;

    // 方法描述;
    private String teststandarddesc;

    private String standardnum;

    private String testlimit;

    private String testloq;

    private java.util.Date trandate;

    private String tranuser;

    private String tranusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasTest> item;

    public BasTest() {
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
        return ToolUtils.CompareProperty((BasTest) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "检测依据编号:teststandard", "检测依据代码:teststandardcode", "检测依据名称:teststandardname",
                "检测依据文件地址:teststandardurl", "检测方法描述:teststandarddesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.teststandard = "";
        this.teststandardcode = "";
        this.teststandardname = "";
        this.teststandardurl = "";
        this.teststandarddesc = "";
        this.tranusername = "";
        this.tranuser = "";
        this.testloq = "";
        this.standardnum = "";
        this.testlimit = "";
    }

    public String getStandardnum() {
        return standardnum;
    }

    public void setStandardnum(String standardnum) {
        this.standardnum = standardnum;
    }

    public String getTestlimit() {
        return testlimit;
    }

    public void setTestlimit(String testlimit) {
        this.testlimit = testlimit;
    }

    public String getTestloq() {
        return testloq;
    }

    public void setTestloq(String testloq) {
        this.testloq = testloq;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
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

    public String getTeststandarddesc() {
        return teststandarddesc;
    }

    public void setTeststandarddesc(String teststandarddesc) {
        this.teststandarddesc = teststandarddesc;
    }

    public String getTeststandard() {
        return teststandard;
    }

    public void setTeststandard(String teststandard) {
        this.teststandard = teststandard;
    }

    public String getTeststandardcode() {
        return teststandardcode;
    }

    public void setTeststandardcode(String teststandardcode) {
        this.teststandardcode = teststandardcode;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getTeststandardurl() {
        return teststandardurl;
    }

    public void setTeststandardurl(String teststandardurl) {
        this.teststandardurl = teststandardurl;
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

    public SelectBean<BasTest> getItem() {
        if (item == null)
            item = new SelectBean<BasTest>();

        return item;
    }

    public void setItem(SelectBean<BasTest> item) {
        this.item = item;
    }

}
