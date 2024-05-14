package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasUserFile implements BaseBean {

    // 人员编号;
    private String tranid;

    // 档案序号;
    private int fileno;

    // 档案名称;
    private String filename;

    // 专业
    private String major;

    // 证书编号
    private String filenumber;

    // 证书有效时间
    private java.util.Date validtime;

    // 证书类型
    private String filetypename;

    // 证书获取时间
    private java.util.Date gettime;

    // 档案url;
    private String fileurl;

    // 备注;
    private String fileremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasUserFile> item;

    public BasUserFile() {
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
        return ToolUtils.CompareProperty((BasUserFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "人员编号:tranid", "档案序号:fileno", "档案名称:filename", "专业:major", "证书编号:filenumber",
                "证书获取时间:gettime", "证书有效期:validtime", "文件类型:filetypename", "档案Url:fileurl", "备注:fileremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileno = 0;
        this.filename = "";
        this.major = "";
        this.filenumber = "";
        this.filetypename = "";
        this.fileurl = "";
        this.fileremark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getFileno() {
        return fileno;
    }

    public void setFileno(int fileno) {
        this.fileno = fileno;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFilenumber() {
        return filenumber;
    }

    public void setFilenumber(String filenumber) {
        this.filenumber = filenumber;
    }

    public java.util.Date getValidtime() {
        return validtime;
    }

    public void setValidtime(java.util.Date validtime) {
        this.validtime = validtime;
    }

    public String getFiletypename() {
        return filetypename;
    }

    public void setFiletypename(String filetypename) {
        this.filetypename = filetypename;
    }

    public java.util.Date getGettime() {
        return gettime;
    }

    public void setGettime(java.util.Date gettime) {
        this.gettime = gettime;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFileremark() {
        return fileremark;
    }

    public void setFileremark(String fileremark) {
        this.fileremark = fileremark;
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

    public SelectBean<BasUserFile> getItem() {
        if (item == null)
            item = new SelectBean<BasUserFile>();

        return item;
    }

    public void setItem(SelectBean<BasUserFile> item) {
        this.item = item;
    }

}
