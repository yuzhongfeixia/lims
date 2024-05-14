package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysRoleAuth implements BaseBean {

    // 角色编号;
    private int roleid;

    private int mid;

    private int roleauth;

    private DataDeal deal;

    public SysRoleAuth() {
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
        return new String[] { "菜单编号:mid", "角色编号:roleid", "权限:roleauth" };
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.roleid = 0;
        this.mid = 0;
        this.roleauth = 0;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal" };
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getRoleauth() {
        return roleauth;
    }

    public void setRoleauth(int roleauth) {
        this.roleauth = roleauth;
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