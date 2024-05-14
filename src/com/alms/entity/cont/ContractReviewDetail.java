package com.alms.entity.cont;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ContractReviewDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 评审人员;
    private String reviewuser;

    // 评审人员姓名;
    private String reviewusername;

    // 评审意见措施;
    private String reviewadvice;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ContractReviewDetail> item;

    public ContractReviewDetail() {
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
        return ToolUtils.CompareProperty((ContractReviewDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "评审人员:reviewuser", "评审人员姓名:reviewusername", "评审意见措施:reviewadvice" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.reviewuser = "";
        this.reviewusername = "";
        this.reviewadvice = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getReviewuser() {
        return reviewuser;
    }

    public void setReviewuser(String reviewuser) {
        this.reviewuser = reviewuser;
    }

    public String getReviewusername() {
        return reviewusername;
    }

    public void setReviewusername(String reviewusername) {
        this.reviewusername = reviewusername;
    }

    public String getReviewadvice() {
        return reviewadvice;
    }

    public void setReviewadvice(String reviewadvice) {
        this.reviewadvice = reviewadvice;
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

    public SelectBean<ContractReviewDetail> getItem() {
        if (item == null)
            item = new SelectBean<ContractReviewDetail>();

        return item;
    }

    public void setItem(SelectBean<ContractReviewDetail> item) {
        this.item = item;
    }

}
