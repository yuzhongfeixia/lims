package com.alms.entity.dev;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BusTrade implements BaseBean {

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

    // 供应商状态
    private String tradestatus;

    // 供应商状态名称
    private String tradestatusname;

    // 备注
    private String remark;

    // 内容和评价
    private String tradecontent;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTrade> item;

    public BusTrade() {
        // TODO Auto-generated constructor stub
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public void OnInit() {
        this.tradeid = "";
        this.tradename = "";
        this.linkman = "";
        this.linktele = "";
        this.linkaddress = "";
        this.linkpost = "";
        this.tradestatus = "";
        this.tradestatusname = "";
        this.remark = "";
        this.tradecontent = "";
    }

    public String getTradecontent() {
        return tradecontent;
    }

    public void setTradecontent(String tradecontent) {
        this.tradecontent = tradecontent;
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        return rtn;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BusTrade) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "供应商编号:tradeid", "供应商名称:tradename", "联系人:linkman", "备注:remark", "内容和评价:tradecontent",
                "联系电话:linktele", "单位地址:linkaddress", "邮政编码:linkpost", "供应商状态:tradestatus", "供应商状态名称:tradestatusname" };
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradestatusname() {
        return tradestatusname;
    }

    public void setTradestatusname(String tradestatusname) {
        this.tradestatusname = tradestatusname;
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

    public String getTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(String tradestatus) {
        this.tradestatus = tradestatus;
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

    public SelectBean<BusTrade> getItem() {
        if (item == null) {
            item = new SelectBean<BusTrade>();
        }
        return item;
    }

    public void setItem(SelectBean<BusTrade> item) {
        this.item = item;
    }

}
