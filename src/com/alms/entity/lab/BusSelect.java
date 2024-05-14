package com.alms.entity.lab;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BusSelect implements BaseBean {

    private String selectcode;

    private String selectid;

    private String selectname;

    private String id;

    private String name;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusSelect> item;

    public BusSelect() {
        this.OnInit();
    }

    public String getSelectcode() {
        return selectcode;
    }

    public void setSelectcode(String selectcode) {
        this.selectcode = selectcode;
    }

    public String getSelectid() {
        return selectid;
    }

    public void setSelectid(String selectid) {
        this.selectid = selectid;
    }

    public String getSelectname() {
        return selectname;
    }

    public void setSelectname(String selectname) {
        this.selectname = selectname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SelectBean<BusSelect> getItem() {
        if (item == null)
            item = new SelectBean<BusSelect>();

        return item;
    }

    public void setItem(SelectBean<BusSelect> item) {
        this.item = item;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public void OnInit() {
        this.selectcode = "";
        this.selectid = "";
        this.selectname = "";
        this.id = "";
        this.name = "";
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        return rtn;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return null;
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "下拉框编码:selectcode", "下拉框编号:selectid", "下拉框名称:selectname" };
    }

}
