package com.gpersist.action;

import java.util.List;
import java.io.InputStream;

import net.sf.json.JsonConfig;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ReturnList;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.publics.ExportColumn;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.publics.FlowSubmit;
import com.gpersist.entity.publics.RequestParams;
import com.gpersist.entity.publics.RptSearch;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.entity.publics.UploadFile;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.utils.*;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // region Private Attribute

    public int start;

    public int limit;

    public int expcnt;

    public boolean hasexport;

    public String filename;

    public InputStream is;

    public String columns;

    public ReturnValue rtv;

    public String selects;

    public String listdata;

    public RequestParams req;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getExpcnt() {
        return expcnt;
    }

    public void setExpcnt(int expcnt) {
        this.expcnt = expcnt;
    }

    public boolean isHasexport() {
        return hasexport;
    }

    public void setHasexport(boolean hasexport) {
        this.hasexport = hasexport;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public ReturnValue getRtv() {
        if (rtv == null)
            rtv = new ReturnValue();

        return rtv;
    }

    public void setRtv(ReturnValue rtv) {
        this.rtv = rtv;
    }

    public String getSelects() {
        return selects;
    }

    public void setSelects(String selects) {
        this.selects = selects;
    }

    public String getListdata() {
        return listdata;
    }

    public void setListdata(String listdata) {
        this.listdata = listdata;
    }

    public RequestParams getReq() {
        if (req == null)
            req = new RequestParams();

        return req;
    }

    public void setReq(RequestParams req) {
        this.req = req;
    }

    private UploadFile uf;

    public UploadFile getUf() {
        if (uf == null)
            uf = new UploadFile();

        return uf;
    }

    public void setUf(UploadFile uf) {
        this.uf = uf;
    }

    private RptSearch rs;

    public RptSearch getRs() {
        return rs;
    }

    public void setRs(RptSearch rs) {
        this.rs = rs;
    }

    public FlowSubmit pubflow;

    public FlowSubmit getPubflow() {
        return pubflow;
    }

    public void setPubflow(FlowSubmit pubflow) {
        this.pubflow = pubflow;
    }

    // endregion Private Attribute

    public SearchParams GetSearchParams(String search) {
        return GetSearchParams(search, "");
    }

    public int GetEndCnt() {
        int endcnt = hasexport ? ((expcnt == 0) ? Consts.DEFAULT_EXPORT_ROWS : expcnt) : limit;

        return start + endcnt;
    }

    public SearchParams GetSearchParams(String search, String procedurename) {
        SearchParams rtn = new SearchParams();

        OnlineUser ou = ToolUtils.GetOnlineUser();

        rtn.setSearch(search);
        rtn.setProcedurename(procedurename);
        rtn.setStart(start + 1);

        if (ou != null) {
            rtn.setUserid(ou.getUser().getUserid());
            rtn.setDeptid(ou.getUser().getDeptid());
        }

        int endcnt = hasexport ? ((expcnt == 0) ? Consts.DEFAULT_EXPORT_ROWS : expcnt) : limit;
        rtn.setEnd(start + endcnt);

        return rtn;
    }

    public ExportSetting GetExportSetting(String file, boolean hasdaterange) {
        return GetExportSetting(file, file, file, hasdaterange);
    }

    public ExportSetting GetExportSetting(String file, String sheetname, String title, boolean hasdaterange) {
        ExportSetting es = new ExportSetting();

        filename = ToolUtils.Encode(file + ".xls");
        es.setSheetname(sheetname);
        es.setTitle(title);

        if (hasdaterange)
            es.setDaterange(this.getReq().GetExportDateBetween());

        return es;
    }

    public String NotLoginRtv() {
        this.getRtv().setSuccess(false);
        this.getRtv().setMsg("没有登录系统!");

        return this.getRtv().toString();
    }

    public ReturnValue NotLoginBean() {
        this.getRtv().setSuccess(false);
        this.getRtv().setMsg("没有登录系统!");

        return this.getRtv();
    }

    public <T> String OutLists(List<T> lists, int total) {
        return OutLists(lists, total, true);
    }

    public <T> String OutLists(List<T> lists, int total, JsonConfig config) {
        ReturnList rtn = new ReturnList();
        rtn.setTotal(total);
        rtn.setData(ToolUtils.GetJsonFromArray(lists, config));

        return rtn.toString();
    }

    public <T> String OutLists(List<T> lists, int total, boolean hasdate) {
        ReturnList rtn = new ReturnList();

        JsonConfig config = new JsonConfig();
        if (hasdate)
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

        if (lists.size() > 0) {
            T item = lists.get(0);
            config.registerPropertyExclusions(item.getClass(), ((BaseBean) item).OnExclusions());
        }

        rtn.setTotal(total);
        rtn.setData(ToolUtils.GetJsonFromArray(lists, config));

        return rtn.toString();
    }

    public <T> String OutLists(List<T> lists, boolean hasdate) {
        JsonConfig config = new JsonConfig();

        if (hasdate)
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

        if (lists.size() > 0) {
            T item = lists.get(0);
            config.registerPropertyExclusions(item.getClass(), ((BaseBean) item).OnExclusions());
        }

        return ToolUtils.GetJsonFromArray(lists, config);
    }

    public <T> String OutBean(T item) {
        return OutBean(item, true);
    }

    public <T> String OutBean(T item, boolean hasdate) {
        if (item == null)
            return Consts.DEFAULT_NULL_BEAN;

        JsonConfig config = new JsonConfig();
        if (hasdate)
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

        config.registerPropertyExclusions(item.getClass(), ((BaseBean) item).OnExclusions());

        return ToolUtils.GetJsonFromBean(item, config);
    }

    public <T> String OutBean(T item, JsonConfig config) {
        if (item == null)
            return Consts.DEFAULT_NULL_BEAN;

        return ToolUtils.GetJsonFromBean(item, config);
    }

    public <T> void OutExport(List<T> lists, ExportSetting es) {
        List<ExportColumn> cols = ToolUtils.GetArrayFromJson(columns, ExportColumn.class);
        is = ExcelUtils.GetExeclStream(es, cols, lists);
    }

    public <T> void OutMutilExport(List<T> lists, ExportSetting es) {
        List<ExportColumn> cols = ToolUtils.GetArrayFromJson(columns, ExportColumn.class);
        is = ExcelUtils.GetExeclStream(es, cols, lists);
    }

    public <T> void SetSearch(SearchParams sp, SelectBean<T> item, OnlineUser ou, String search) {
        sp.setSearch(search);
        sp.setStart(this.getStart() + 1);
        sp.setEnd(this.GetEndCnt());
        sp.setUserid(ou.getUser().getUserid());
        item.setUserid(ou.getUser().getUserid());
        item.setIsadmin(ou.getUser().getIsadmin());
    }

    public <T> void SetUser(SelectBean<T> item, OnlineUser ou) {
        item.setUserid(ou.getUser().getUserid());
        item.setIsadmin(ou.getUser().getIsadmin());
    }
}
