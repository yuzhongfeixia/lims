package com.alms.entity.sub;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SubReviewDetail implements BaseBean {

    // 评审编号;
    private String tranid;

    // 文件名称;
    private String attachfilename;

    // 文件地址;
    private String attachfileurl;

    // 资格证书;
    private String entercret;

    // 资格证书名称;
    private String entercretname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SubReviewDetail> item;

    public SubReviewDetail() {
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
        return ToolUtils.CompareProperty((SubReviewDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "评审编号:tranid", "文件名称:attachfilename", "文件地址:attachfileurl", "资格证书:entercret",
                "资格证书名称:entercretname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.attachfilename = "";
        this.attachfileurl = "";
        this.entercret = "";
        this.entercretname = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getAttachfilename() {
        return attachfilename;
    }

    public void setAttachfilename(String attachfilename) {
        this.attachfilename = attachfilename;
    }

    public String getAttachfileurl() {
        return attachfileurl;
    }

    public void setAttachfileurl(String attachfileurl) {
        this.attachfileurl = attachfileurl;
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

    public SelectBean<SubReviewDetail> getItem() {
        if (item == null)
            item = new SelectBean<SubReviewDetail>();

        return item;
    }

    public void setItem(SelectBean<SubReviewDetail> item) {
        this.item = item;
    }

}
