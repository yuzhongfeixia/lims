package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysUserWorkGroup implements BaseBean {

    // 操作员编号;
    private String userid;

    private String workgroup;

    private DataDeal deal;

    private SelectBean<SysUserWorkGroup> item;

    public SysUserWorkGroup() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysUserWorkGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "用户编号:userid", "工作组编号:workgroup" };
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.userid = "";
        this.workgroup = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal" };
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(String workgroup) {
        this.workgroup = workgroup;
    }

    public SelectBean<SysUserWorkGroup> getItem() {
        if (item == null)
            item = new SelectBean<SysUserWorkGroup>();

        return item;
    }

    public void setItem(SelectBean<SysUserWorkGroup> item) {
        this.item = item;
    }

    public DataDeal getDeal() {
        if (deal == null)
            this.deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}