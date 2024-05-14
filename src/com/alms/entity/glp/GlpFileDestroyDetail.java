package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFileDestroyDetail implements BaseBean {

    // 销毁编号;
    private String tranid;

    // 资料编号;
    private String fileid;

    // 操作类型;
    private String filetran;

    // 文件操作类型名称;
    private String filetranname;

    // 操作人;
    private String tranuser;

    // 操作日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFileDestroyDetail> item;

    public GlpFileDestroyDetail() {
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
        return ToolUtils.CompareProperty((GlpFileDestroyDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "销毁编号:tranid", "资料编号:fileid", "操作类型:filetran", "文件操作类型名称:filetranname", "操作人:tranuser",
                "操作日期:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileid = "";
        this.filetran = "";
        this.filetranname = "";
        this.tranuser = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFiletran() {
        return filetran;
    }

    public void setFiletran(String filetran) {
        this.filetran = filetran;
    }

    public String getFiletranname() {
        return filetranname;
    }

    public void setFiletranname(String filetranname) {
        this.filetranname = filetranname;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
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

    public SelectBean<GlpFileDestroyDetail> getItem() {
        if (item == null)
            item = new SelectBean<GlpFileDestroyDetail>();

        return item;
    }

    public void setItem(SelectBean<GlpFileDestroyDetail> item) {
        this.item = item;
    }

}
