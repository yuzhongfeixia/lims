package com.gpersist.entity.system;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class SysSet implements BaseBean {

    // 参数编码;
    private String setcode;

    // 参数名称;
    private String setname;

    // 参数值;
    private String setvalue;

    // 是否可修改;
    private boolean ismodify;

    // 参数类型;
    private String coltype;

    // 是否可为个人参数;
    private boolean isuser;

    private DataDeal deal;

    private SelectBean<SysSet> item;

    public SysSet() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysSet) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.setcode = "";
        this.setname = "";
        this.setvalue = "";
        this.ismodify = false;
        this.coltype = "";
        this.isuser = false;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    public static String[] GetExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getSetcode() {
        return setcode;
    }

    public void setSetcode(String setcode) {
        this.setcode = setcode;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public String getSetvalue() {
        return setvalue;
    }

    public void setSetvalue(String setvalue) {
        this.setvalue = setvalue;
    }

    public boolean isIsmodify() {
        return ismodify;
    }

    public void setIsmodify(boolean ismodify) {
        this.ismodify = ismodify;
    }

    public String getColtype() {
        return coltype;
    }

    public void setColtype(String coltype) {
        this.coltype = coltype;
    }

    public boolean isIsuser() {
        return isuser;
    }

    public void setIsuser(boolean isuser) {
        this.isuser = isuser;
    }

    public DataDeal getDeal() {
        if (deal == null)
            this.deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SysSet> getItem() {
        if (item == null)
            item = new SelectBean<SysSet>();

        return item;
    }

    public void setItem(SelectBean<SysSet> item) {
        this.item = item;
    }

}
