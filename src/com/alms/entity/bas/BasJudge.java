package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasJudge implements BaseBean {

    // 判定依据编号;
    private String judgestandard;

    // 判定依据代码;
    private String judgestandardcode;

    // 判定依据名称;
    private String judgestandardname;

    // 判定依据限制范围;
    private String judgestandardrange;

    // 判定依据说明;
    private String judgestandardexplain;

    // 判定依据文件地址;
    private String judgestandardurl;

    private String standardnum;

    private java.util.Date registerdate;

    private java.util.Date trandate;

    private String tranuser;

    private String tranusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasJudge> item;

    public BasJudge() {
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
        return ToolUtils.CompareProperty((BasJudge) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "判定依据编号:judgestandard", "判定依据代码:judgestandardcode", "判定依据名称:judgestandardname",
                "判定依据说明:judgestandardexplain", "判定依据范围:judgestandardrange", "判定依据文件地址:judgestandardurl" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.judgestandard = "";
        this.judgestandardcode = "";
        this.judgestandardname = "";
        this.judgestandardurl = "";
        this.standardnum = "";
        this.tranuser = "";
        this.tranusername = "";
        this.judgestandardexplain = "";
        this.judgestandardrange = "";
    }

    public String getJudgestandardrange() {
        return judgestandardrange;
    }

    public void setJudgestandardrange(String judgestandardrange) {
        this.judgestandardrange = judgestandardrange;
    }

    public String getJudgestandardexplain() {
        return judgestandardexplain;
    }

    public void setJudgestandardexplain(String judgestandardexplain) {
        this.judgestandardexplain = judgestandardexplain;
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

    public String getStandardnum() {
        return standardnum;
    }

    public void setStandardnum(String standardnum) {
        this.standardnum = standardnum;
    }

    public java.util.Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(java.util.Date registerdate) {
        this.registerdate = registerdate;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getJudgestandard() {
        return judgestandard;
    }

    public void setJudgestandard(String judgestandard) {
        this.judgestandard = judgestandard;
    }

    public String getJudgestandardcode() {
        return judgestandardcode;
    }

    public void setJudgestandardcode(String judgestandardcode) {
        this.judgestandardcode = judgestandardcode;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
    }

    public String getJudgestandardurl() {
        return judgestandardurl;
    }

    public void setJudgestandardurl(String judgestandardurl) {
        this.judgestandardurl = judgestandardurl;
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

    public SelectBean<BasJudge> getItem() {
        if (item == null)
            item = new SelectBean<BasJudge>();

        return item;
    }

    public void setItem(SelectBean<BasJudge> item) {
        this.item = item;
    }

}
