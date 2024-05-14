package com.alms.entity.file;

import java.util.Date;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasFile implements BaseBean {

    // 文件编号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 文件类别;
    private String filecate;

    // 文件类别名称;
    private String filecatename;

    // 文件类型;
    private String filetype;

    // 文件类型名称;
    private String filetypename;

    // 文件状态;
    private String filestatus;

    // 文件状态名称;
    private String filestatusname;

    // 文件时间;
    private java.util.Date filedate;

    // 创建人;
    private String crtuser;

    // 创建人姓名;
    private String crtusername;

    // 文件地址;
    private String fileurl;

    // 文件修改次数
    private int modifytimes;

    // 文件修改时间
    private Date modifydate;

    // 是否销毁
    private boolean isdestroy;

    // 销毁时间
    private Date destroydate;

    // 上传时间;
    private java.util.Date uploaddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasFile> item;

    public BasFile() {
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
        return ToolUtils.CompareProperty((BasFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "文件编号:fileid", "文件名称:filename", "文件类别:filecate", "文件类别名称:filecatename", "文件类型:filetype",
                "文件类型名称:filetypename", "文件状态:filestatus", "文件状态名称:filestatusname", "文件时间:filedate", "创建人:crtuser",
                "创建人姓名:crtusername", "文件地址:fileurl", "上传时间:uploaddate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.fileid = "";
        this.filename = "";
        this.filecate = "";
        this.filecatename = "";
        this.filetype = "";
        this.filetypename = "";
        this.filestatus = "";
        this.filestatusname = "";
        this.filedate = ToolUtils.GetMinDate();
        this.crtuser = "";
        this.crtusername = "";
        this.fileurl = "";
        this.uploaddate = ToolUtils.GetMinDate();
        this.isdestroy = false;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFiletypename() {
        return filetypename;
    }

    public void setFiletypename(String filetypename) {
        this.filetypename = filetypename;
    }

    public String getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(String filestatus) {
        this.filestatus = filestatus;
    }

    public String getFilestatusname() {
        return filestatusname;
    }

    public void setFilestatusname(String filestatusname) {
        this.filestatusname = filestatusname;
    }

    public java.util.Date getFiledate() {
        return filedate;
    }

    public void setFiledate(java.util.Date filedate) {
        this.filedate = filedate;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public String getCrtusername() {
        return crtusername;
    }

    public void setCrtusername(String crtusername) {
        this.crtusername = crtusername;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public java.util.Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(java.util.Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public int getModifytimes() {
        return modifytimes;
    }

    public void setModifytimes(int modifytimes) {
        this.modifytimes = modifytimes;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public boolean isIsdestroy() {
        return isdestroy;
    }

    public void setIsdestroy(boolean isdestroy) {
        this.isdestroy = isdestroy;
    }

    public Date getDestroydate() {
        return destroydate;
    }

    public void setDestroydate(Date destroydate) {
        this.destroydate = destroydate;
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

    public SelectBean<BasFile> getItem() {
        if (item == null)
            item = new SelectBean<BasFile>();

        return item;
    }

    public void setItem(SelectBean<BasFile> item) {
        this.item = item;
    }

}
