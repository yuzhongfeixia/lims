package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReleFile implements BaseBean {

    // 业务编号;
    private String tranid;

    // 文件编号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 文件数量;
    private int filequan;

    // 发放时间;
    private java.util.Date reledate;

    // 持有人;
    private String fileowner;

    // 持有人姓名;
    private String fileownername;

    // 备注;
    private String fileremark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 记录日期;
    private java.util.Date trandate;

    // 发放回收;
    private String reletype;

    // 发放回收类型名称;
    private String reletypename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ReleFile> item;

    public ReleFile() {
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
        return ToolUtils.CompareProperty((ReleFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "文件编号:fileid", "文件名称:filename", "文件数量:filequan", "发放时间:reledate",
                "持有人:fileowner", "持有人姓名:fileownername", "备注:fileremark", "业务员:tranuser", "业务员姓名:tranusername",
                "记录日期:trandate", "发放回收:reletype", "发放回收类型名称:reletypename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileid = "";
        this.filename = "";
        this.filequan = 0;
        // this.reledate = ToolUtils.GetMinDate();
        this.fileowner = "";
        this.fileownername = "";
        this.fileremark = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.reletype = "";
        this.reletypename = "";
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFilequan() {
        return filequan;
    }

    public void setFilequan(int filequan) {
        this.filequan = filequan;
    }

    public java.util.Date getReledate() {
        return reledate;
    }

    public void setReledate(java.util.Date reledate) {
        this.reledate = reledate;
    }

    public String getFileowner() {
        return fileowner;
    }

    public void setFileowner(String fileowner) {
        this.fileowner = fileowner;
    }

    public String getFileownername() {
        return fileownername;
    }

    public void setFileownername(String fileownername) {
        this.fileownername = fileownername;
    }

    public String getFileremark() {
        return fileremark;
    }

    public void setFileremark(String fileremark) {
        this.fileremark = fileremark;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getReletype() {
        return reletype;
    }

    public void setReletype(String reletype) {
        this.reletype = reletype;
    }

    public String getReletypename() {
        return reletypename;
    }

    public void setReletypename(String reletypename) {
        this.reletypename = reletypename;
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

    public SelectBean<ReleFile> getItem() {
        if (item == null)
            item = new SelectBean<ReleFile>();

        return item;
    }

    public void setItem(SelectBean<ReleFile> item) {
        this.item = item;
    }

}
