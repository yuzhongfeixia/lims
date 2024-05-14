package com.alms.entity.dev;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class TradeSurvey implements BaseBean {

    // 业务编号
    private String tranid;

    // 供应商编号
    private String tradeid;

    // 供应商名称
    private String tradename;

    // 联系人
    private String linkman;

    // 联系电话
    private String linktele;

    // 单位地址
    private String linkaddress;

    // 邮政编码
    private String linkpost;

    // 产品名称
    private String prdname;

    // 业务动作
    private String flowaction;

    // 业务动作名称
    private String flowactionname;

    // 业务状态
    private String flowstatus;

    // 业务状态名称
    private String flowstatusname;

    // 调查人
    private String tranuser;

    // 调查人姓名
    private String tranusername;

    // 调查人时间
    private java.util.Date trandate;

    // 企业规模
    private String enterscale;

    // 企业规模名称
    private String enterscalename;

    // 企业知名度
    private String enterpopular;

    // 企业知名度名称
    private String enterpopularname;

    // 资格证书
    private String entercret;

    // 资格证书名称
    private String entercretname;

    // 产品质量
    private String prdquality;

    // 产品质量名称
    private String prdqualityname;

    // 试用情况
    private String prdtest;

    // 试用情况名称
    private String prdtestname;

    // 管理
    private String prdmanage;

    // 管理名称
    private String prdmanagename;

    // 生产检查设备
    private String prdcheck;

    // 生产检查设备名称
    private String prdcheckname;

    // 服务质量
    private String prdservice;

    // 服务质量名称
    private String prdservicename;

    // 价格
    private String prdprice;

    // 价格名称
    private String prdpricename;

    // 评价结论
    private String evaldesc;

    // 评价员
    private String evaluser;

    // 评价员姓名
    private String evalusername;

    // 评价日期
    private java.util.Date evaldate;

    // 评价负责人
    private String checkuser;

    // 评价员负责人姓名
    private String checkusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<TradeSurvey> item;

    public TradeSurvey() {
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
    public void OnInit() {
        this.tranid = "";
        this.tradeid = "";
        this.tradename = "";
        this.linkman = "";
        this.linktele = "";
        this.linkaddress = "";
        this.linkpost = "";
        this.prdname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.enterscale = "";
        this.enterscalename = "";
        this.enterpopular = "";
        this.enterpopularname = "";
        this.entercret = "";
        this.entercretname = "";
        this.prdquality = "";
        this.prdqualityname = "";
        this.prdtest = "";
        this.prdtestname = "";
        this.prdmanage = "";
        this.prdmanagename = "";
        this.prdcheck = "";
        this.prdcheckname = "";
        this.prdservice = "";
        this.prdservicename = "";
        this.prdprice = "";
        this.prdpricename = "";
        this.evaldesc = "";
        this.evaluser = "";
        this.evalusername = "";
        this.evaldate = ToolUtils.GetMinDate();
        this.checkuser = "";
        this.checkusername = "";

    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((TradeSurvey) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "供应商编号:tradeid", "供应商名称:tradename", "联系人:linkman", "单位地址:linkaddress",
                "邮政编码:linkpost", "联系电话:linktele", "产品名称:prdname", "业务动作:flowaction", "业务动作名称:flowactionname",
                "业务状态:flowstatus", "业务状态名称:flowstatusname", "调查人:tranuser", "调查人姓名:tranusername", "调查人时间:trandate",
                "企业规模:enterscale", "企业规模名称:enterscalename", "企业知名度:enterpopular", "企业知名度名称:enterpopularname",
                "资格证书:entercret", "资格证书名称:entercretname", "产品质量:prdquality", "产品质量名称:prdqualityname", "试用情况:prdtest",
                "试用情况名称:prdtestname", "管理:prdmanage", "管理名称:prdmanagename", "生产检查设备:prdcheck", "生产检查设备名称:prdcheckname",
                "服务质量:prdservice", "服务质量名称:prdservicename", "价格:prdprice", "价格名称:prdpricename", "评价结论:evaldesc",
                "评价员:evaluser", "评价员姓名:evalusername", "评价日期:evaldate", "评价负责人姓名:checkusername", "评价负责人:checkuser" };
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getTradename() {
        return tradename;
    }

    public void setTradename(String tradename) {
        this.tradename = tradename;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktele() {
        return linktele;
    }

    public void setLinktele(String linktele) {
        this.linktele = linktele;
    }

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress;
    }

    public String getLinkpost() {
        return linkpost;
    }

    public void setLinkpost(String linkpost) {
        this.linkpost = linkpost;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
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
        if (search == null) {
            search = new SearchParams();
        }
        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    public DataDeal getDeal() {
        if (deal == null) {
            deal = new DataDeal();
        }
        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<TradeSurvey> getItem() {
        if (item == null) {
            item = new SelectBean<TradeSurvey>();
        }
        return item;
    }

    public void setItem(SelectBean<TradeSurvey> item) {
        this.item = item;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getEnterscale() {
        return enterscale;
    }

    public void setEnterscale(String enterscale) {
        this.enterscale = enterscale;
    }

    public String getEnterscalename() {
        return enterscalename;
    }

    public void setEnterscalename(String enterscalename) {
        this.enterscalename = enterscalename;
    }

    public String getEnterpopular() {
        return enterpopular;
    }

    public void setEnterpopular(String enterpopular) {
        this.enterpopular = enterpopular;
    }

    public String getEnterpopularname() {
        return enterpopularname;
    }

    public void setEnterpopularname(String enterpopularname) {
        this.enterpopularname = enterpopularname;
    }

    public String getEntercret() {
        return entercret;
    }

    public void setEntercret(String entercret) {
        this.entercret = entercret;
    }

    public String getEntercretname() {
        return entercretname;
    }

    public void setEntercretname(String entercretname) {
        this.entercretname = entercretname;
    }

    public String getPrdquality() {
        return prdquality;
    }

    public void setPrdquality(String prdquality) {
        this.prdquality = prdquality;
    }

    public String getPrdqualityname() {
        return prdqualityname;
    }

    public void setPrdqualityname(String prdqualityname) {
        this.prdqualityname = prdqualityname;
    }

    public String getPrdtest() {
        return prdtest;
    }

    public void setPrdtest(String prdtest) {
        this.prdtest = prdtest;
    }

    public String getPrdtestname() {
        return prdtestname;
    }

    public void setPrdtestname(String prdtestname) {
        this.prdtestname = prdtestname;
    }

    public String getPrdmanage() {
        return prdmanage;
    }

    public void setPrdmanage(String prdmanage) {
        this.prdmanage = prdmanage;
    }

    public String getPrdmanagename() {
        return prdmanagename;
    }

    public void setPrdmanagename(String prdmanagename) {
        this.prdmanagename = prdmanagename;
    }

    public String getPrdcheck() {
        return prdcheck;
    }

    public void setPrdcheck(String prdcheck) {
        this.prdcheck = prdcheck;
    }

    public String getPrdcheckname() {
        return prdcheckname;
    }

    public void setPrdcheckname(String prdcheckname) {
        this.prdcheckname = prdcheckname;
    }

    public String getPrdservice() {
        return prdservice;
    }

    public void setPrdservice(String prdservice) {
        this.prdservice = prdservice;
    }

    public String getPrdservicename() {
        return prdservicename;
    }

    public void setPrdservicename(String prdservicename) {
        this.prdservicename = prdservicename;
    }

    public String getPrdprice() {
        return prdprice;
    }

    public void setPrdprice(String prdprice) {
        this.prdprice = prdprice;
    }

    public String getPrdpricename() {
        return prdpricename;
    }

    public void setPrdpricename(String prdpricename) {
        this.prdpricename = prdpricename;
    }

    public String getEvaldesc() {
        return evaldesc;
    }

    public void setEvaldesc(String evaldesc) {
        this.evaldesc = evaldesc;
    }

    public String getEvaluser() {
        return evaluser;
    }

    public void setEvaluser(String evaluser) {
        this.evaluser = evaluser;
    }

    public String getEvalusername() {
        return evalusername;
    }

    public void setEvalusername(String evalusername) {
        this.evalusername = evalusername;
    }

    public java.util.Date getEvaldate() {
        return evaldate;
    }

    public void setEvaldate(java.util.Date evaldate) {
        this.evaldate = evaldate;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

}
