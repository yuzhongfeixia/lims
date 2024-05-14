package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class AcceptFileDetail implements BaseBean {

    // 验收编号;
    private String tranid;

    // 文件序号;
    private int fileserial;

    // 文件名称;
    private String filename;

    // 文件数量;
    private double filecount;

    // 文件地址;
    private String fileurl;

    // 文件类别;
    private String filecate;

    // 文件类别名称;
    private String filecatename;

    // 备注;
    private String fileremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<AcceptFileDetail> item;

    public AcceptFileDetail() {
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
        return ToolUtils.CompareProperty((AcceptFileDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "验收编号:tranid", "文件序号:fileserial", "文件地址:fileurl", "文件类别:filecate", "文件类别名称:filecatename",
                "文件名称:filename", "文件数量:filecount", "备注:fileremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileserial = 0;
        this.filename = "";
        this.filecount = 0;
        this.fileremark = "";
        this.fileurl = "";
        this.filecate = "";
        this.filecatename = "";
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
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

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getFileserial() {
        return fileserial;
    }

    public void setFileserial(int fileserial) {
        this.fileserial = fileserial;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getFilecount() {
        return filecount;
    }

    public void setFilecount(double filecount) {
        this.filecount = filecount;
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

    public SelectBean<AcceptFileDetail> getItem() {
        if (item == null)
            item = new SelectBean<AcceptFileDetail>();

        return item;
    }

    public void setItem(SelectBean<AcceptFileDetail> item) {
        this.item = item;
    }

}
