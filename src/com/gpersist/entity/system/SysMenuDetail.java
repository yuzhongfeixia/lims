package com.gpersist.entity.system;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysMenuDetail implements BaseBean {

    private int mid;

    private int authid;

    private DataDeal deal;

    private SelectBean<SysMenuDetail> item;

    public SysMenuDetail() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysMenuDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.mid = 0;
        this.authid = 0;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    public DataDeal getDeal() {
        if (deal == null)
            this.deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getAuthid() {
        return authid;
    }

    public void setAuthid(int authid) {
        this.authid = authid;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SelectBean<SysMenuDetail> getItem() {
        if (item == null)
            item = new SelectBean<SysMenuDetail>();

        return item;
    }

    public void setItem(SelectBean<SysMenuDetail> item) {
        this.item = item;
    }

}