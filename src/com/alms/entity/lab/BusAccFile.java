package com.alms.entity.lab;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BusAccFile implements BaseBean {

    private String tranid;

    private String sampletran;

    private String filename;

    private String testuserfile;

    private String testuserfilename;

    private String fileurl;

    private String parameterid;

    private String parametername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusAccFile> item;

    public BusAccFile() {
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
        return ToolUtils.CompareProperty((BusAccFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.sampletran = "";
        this.filename = "";
        this.testuserfile = "";
        this.testuserfilename = "";
        this.fileurl = "";
        this.parameterid = "";
        this.parametername = "";
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTestuserfile() {
        return testuserfile;
    }

    public void setTestuserfile(String testuserfile) {
        this.testuserfile = testuserfile;
    }

    public String getTestuserfilename() {
        return testuserfilename;
    }

    public void setTestuserfilename(String testuserfilename) {
        this.testuserfilename = testuserfilename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
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

    public SelectBean<BusAccFile> getItem() {
        if (item == null)
            item = new SelectBean<BusAccFile>();

        return item;
    }

    public void setItem(SelectBean<BusAccFile> item) {
        this.item = item;
    }

}
