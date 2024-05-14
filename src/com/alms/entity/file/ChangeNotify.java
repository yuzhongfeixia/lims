package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ChangeNotify implements BaseBean {

    // 业务编号;
    private String tranid;

    // 文件编号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 更改情况;
    private String filechange;

    // 生效日期;
    private java.util.Date effectdate;

    // 更改通知书编号;
    private String notifyid;

    // 替代文件;
    private String replacefile;

    // 填表人;
    private String writeuser;

    // 填表人姓名;
    private String writeusername;

    // 填表日期;
    private java.util.Date writedate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 记录日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ChangeNotify> item;

    public ChangeNotify() {
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
        return ToolUtils.CompareProperty((ChangeNotify) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "文件编号:fileid", "文件名称:filename", "更改情况:filechange", "生效日期:effectdate",
                "更改通知书编号:notifyid", "替代文件:replacefile", "填表人:writeuser", "填表人姓名:writeusername", "填表日期:writedate",
                "业务员:tranuser", "业务员姓名:tranusername", "记录日期:trandate" };
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
        this.filechange = "";
        // this.effectdate = ToolUtils.GetMinDate();
        this.notifyid = "";
        this.replacefile = "";
        this.writeuser = "";
        this.writeusername = "";
        // this.writedate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilechange() {
        return filechange;
    }

    public void setFilechange(String filechange) {
        this.filechange = filechange;
    }

    public java.util.Date getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(java.util.Date effectdate) {
        this.effectdate = effectdate;
    }

    public String getNotifyid() {
        return notifyid;
    }

    public void setNotifyid(String notifyid) {
        this.notifyid = notifyid;
    }

    public String getReplacefile() {
        return replacefile;
    }

    public void setReplacefile(String replacefile) {
        this.replacefile = replacefile;
    }

    public String getWriteuser() {
        return writeuser;
    }

    public void setWriteuser(String writeuser) {
        this.writeuser = writeuser;
    }

    public String getWriteusername() {
        return writeusername;
    }

    public void setWriteusername(String writeusername) {
        this.writeusername = writeusername;
    }

    public java.util.Date getWritedate() {
        return writedate;
    }

    public void setWritedate(java.util.Date writedate) {
        this.writedate = writedate;
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

    public SelectBean<ChangeNotify> getItem() {
        if (item == null)
            item = new SelectBean<ChangeNotify>();

        return item;
    }

    public void setItem(SelectBean<ChangeNotify> item) {
        this.item = item;
    }

}
