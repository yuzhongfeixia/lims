package com.alms.entity.form;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class IntField implements BaseBean {

    // 界面编号;
    private String intid;

    // 段落;
    private int fieldtable;

    // 行数;
    private int fieldrow;

    // 本行列数;
    private int fieldrowcount;

    // 列号;
    private int fieldcolumn;

    // 所占宽度;
    private int fieldwidth;

    // 字段标签;
    private String fieldlabel;

    // 标签宽度;
    private int labelwidth;

    // 字段类型;
    private String fieldtype;

    // 字段类型名称;
    private String fieldtypename;

    // 传递类;
    private String fieldclass;

    // 字段代码;
    private String fieldcode;

    // 试样序号;
    private int fieldserial;

    // 是否可为空;
    private boolean isnull;

    // 字段长度;
    private int fieldmax;

    // 字段行数;
    private int fieldlines;

    // 关联数据源代码;
    private String storecode;

    // 校验代码;
    private String validcode;

    // 默认值;
    private String defaultvalue;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IntField> item;

    public IntField() {
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
        return ToolUtils.CompareProperty((IntField) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "界面编号:intid", "段落:fieldtable", "行数:fieldrow", "本行列数:fieldrowcount", "列号:fieldcolumn",
                "所占宽度:fieldwidth", "字段标签:fieldlabel", "标签宽度:labelwidth", "字段类型:fieldtype", "字段类型名称:fieldtypename",
                "传递类:fieldclass", "字段代码:fieldcode", "试样序号:fieldserial", "是否可为空:isnull", "字段长度:fieldmax",
                "字段行数:fieldlines", "关联数据源代码:storecode", "校验代码:validcode", "默认值:defaultvalue" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.intid = "";
        this.fieldtable = 0;
        this.fieldrow = 0;
        this.fieldrowcount = 0;
        this.fieldcolumn = 0;
        this.fieldwidth = 0;
        this.fieldlabel = "";
        this.labelwidth = 0;
        this.fieldtype = "";
        this.fieldtypename = "";
        this.fieldclass = "";
        this.fieldcode = "";
        this.fieldserial = 0;
        this.isnull = false;
        this.fieldmax = 0;
        this.fieldlines = 0;
        this.storecode = "";
        this.validcode = "";
        this.defaultvalue = "";
    }

    public String getIntid() {
        return intid;
    }

    public void setIntid(String intid) {
        this.intid = intid;
    }

    public int getFieldtable() {
        return fieldtable;
    }

    public void setFieldtable(int fieldtable) {
        this.fieldtable = fieldtable;
    }

    public int getFieldrow() {
        return fieldrow;
    }

    public void setFieldrow(int fieldrow) {
        this.fieldrow = fieldrow;
    }

    public int getFieldrowcount() {
        return fieldrowcount;
    }

    public void setFieldrowcount(int fieldrowcount) {
        this.fieldrowcount = fieldrowcount;
    }

    public int getFieldcolumn() {
        return fieldcolumn;
    }

    public void setFieldcolumn(int fieldcolumn) {
        this.fieldcolumn = fieldcolumn;
    }

    public int getFieldwidth() {
        return fieldwidth;
    }

    public void setFieldwidth(int fieldwidth) {
        this.fieldwidth = fieldwidth;
    }

    public String getFieldlabel() {
        return fieldlabel;
    }

    public void setFieldlabel(String fieldlabel) {
        this.fieldlabel = fieldlabel;
    }

    public int getLabelwidth() {
        return labelwidth;
    }

    public void setLabelwidth(int labelwidth) {
        this.labelwidth = labelwidth;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getFieldtypename() {
        return fieldtypename;
    }

    public void setFieldtypename(String fieldtypename) {
        this.fieldtypename = fieldtypename;
    }

    public String getFieldclass() {
        return fieldclass;
    }

    public void setFieldclass(String fieldclass) {
        this.fieldclass = fieldclass;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public int getFieldserial() {
        return fieldserial;
    }

    public void setFieldserial(int fieldserial) {
        this.fieldserial = fieldserial;
    }

    public boolean getIsnull() {
        return isnull;
    }

    public void setIsnull(boolean isnull) {
        this.isnull = isnull;
    }

    public int getFieldmax() {
        return fieldmax;
    }

    public void setFieldmax(int fieldmax) {
        this.fieldmax = fieldmax;
    }

    public int getFieldlines() {
        return fieldlines;
    }

    public void setFieldlines(int fieldlines) {
        this.fieldlines = fieldlines;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
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

    public SelectBean<IntField> getItem() {
        if (item == null)
            item = new SelectBean<IntField>();

        return item;
    }

    public void setItem(SelectBean<IntField> item) {
        this.item = item;
    }

}
