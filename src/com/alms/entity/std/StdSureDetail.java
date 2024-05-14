package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdSureDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 文件名称;
    private String attachfilename;

    // 文件地址;
    private String attachfileurl;

    // 文件类型;
    private String filecate;

    // 文件类别名称;
    private String filecatename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdSureDetail> item;

    public StdSureDetail() {
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
        return ToolUtils.CompareProperty((StdSureDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "文件名称:attachfilename", "文件地址:attachfileurl", "文件类型:filecate",
                "文件类别名称:filecatename" };
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
        this.filecate = "";
        this.filecatename = "";
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

    public String getFilecate() {
        return filecate;
    }

    public void setFilecate(String filecate) {
        this.filecate = filecate;
    }

    public String getFilecatename() {
        return filecatename;
    }

    public void setFilecatename(String filecatename) {
        this.filecatename = filecatename;
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

    public SelectBean<StdSureDetail> getItem() {
        if (item == null)
            item = new SelectBean<StdSureDetail>();

        return item;
    }

    public void setItem(SelectBean<StdSureDetail> item) {
        this.item = item;
    }

}
