package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamFile implements BaseBean {

    // 考核编号;
    private String examid;

    // 报告序号;
    private int fileno;

    // 报告名称;
    private String filename;

    // 报告类别;
    private String filecate;

    // 文件类别名称;
    private String filecatename;

    // 报告类型;
    private String filetype;

    // 文件类型名称;
    private String filetypename;

    // 报告url;
    private String fileurl;

    // 备注;
    private String fileremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamFile> item;

    public UserExamFile() {
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
        return ToolUtils.CompareProperty((UserExamFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核编号:examid", "报告序号:fileno", "报告名称:filename", "报告类别:filecate", "文件类别名称:filecatename",
                "报告类型:filetype", "文件类型名称:filetypename", "报告Url:fileurl", "备注:fileremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.examid = "";
        this.fileno = 0;
        this.filename = "";
        this.filecate = "";
        this.filecatename = "";
        this.filetype = "";
        this.filetypename = "";
        this.fileurl = "";
        this.fileremark = "";
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
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

    public SelectBean<UserExamFile> getItem() {
        if (item == null)
            item = new SelectBean<UserExamFile>();

        return item;
    }

    public void setItem(SelectBean<UserExamFile> item) {
        this.item = item;
    }

}
