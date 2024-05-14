package com.alms.entity.cont;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ContractReview implements BaseBean {

    // 业务编号;
    private String tranid;

    // 合同/委托书编号;
    private String contractid;

    // 客户名称;
    private String consignname;

    // 联系人;
    private String consigncontact;

    // 联系电话;
    private String consigntele;

    // 检测特殊要求;
    private String testrequest;

    // 评审结论;
    private String reviewresult;

    // 评审主持人;
    private String reviewhost;

    // 评审主持人姓名;
    private String reviewhostname;

    // 评审主持人时间;
    private java.util.Date reviewhostdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 记录日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ContractReview> item;

    public ContractReview() {
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
        return ToolUtils.CompareProperty((ContractReview) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "合同/委托书编号:contractid", "客户名称:consignname", "联系人:consigncontact",
                "联系电话:consigntele", "检测特殊要求:testrequest", "评审结论:reviewresult", "评审主持人:reviewhost",
                "评审主持人姓名:reviewhostname", "评审主持人时间:reviewhostdate", "业务员:tranuser", "业务员姓名:tranusername",
                "记录日期:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.contractid = "";
        this.consignname = "";
        this.consigncontact = "";
        this.consigntele = "";
        this.testrequest = "";
        this.reviewresult = "";
        this.reviewhost = "";
        this.reviewhostname = "";
        this.reviewhostdate = ToolUtils.GetMinDate();
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

    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid;
    }

    public String getConsignname() {
        return consignname;
    }

    public void setConsignname(String consignname) {
        this.consignname = consignname;
    }

    public String getConsigncontact() {
        return consigncontact;
    }

    public void setConsigncontact(String consigncontact) {
        this.consigncontact = consigncontact;
    }

    public String getConsigntele() {
        return consigntele;
    }

    public void setConsigntele(String consigntele) {
        this.consigntele = consigntele;
    }

    public String getTestrequest() {
        return testrequest;
    }

    public void setTestrequest(String testrequest) {
        this.testrequest = testrequest;
    }

    public String getReviewresult() {
        return reviewresult;
    }

    public void setReviewresult(String reviewresult) {
        this.reviewresult = reviewresult;
    }

    public String getReviewhost() {
        return reviewhost;
    }

    public void setReviewhost(String reviewhost) {
        this.reviewhost = reviewhost;
    }

    public String getReviewhostname() {
        return reviewhostname;
    }

    public void setReviewhostname(String reviewhostname) {
        this.reviewhostname = reviewhostname;
    }

    public java.util.Date getReviewhostdate() {
        return reviewhostdate;
    }

    public void setReviewhostdate(java.util.Date reviewhostdate) {
        this.reviewhostdate = reviewhostdate;
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

    public SelectBean<ContractReview> getItem() {
        if (item == null)
            item = new SelectBean<ContractReview>();

        return item;
    }

    public void setItem(SelectBean<ContractReview> item) {
        this.item = item;
    }

}
