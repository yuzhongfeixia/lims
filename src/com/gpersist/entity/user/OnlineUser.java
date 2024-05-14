package com.gpersist.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.gpersist.entity.*;
import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.ListMenu;
import com.gpersist.entity.system.*;
import com.gpersist.utils.ToolUtils;

public class OnlineUser implements BaseBean {

    private SysUser user;

    private SysDept dept;

    private List<SysMenuGroup> menugroups;

    private List<SysMenu> menus;

    private List<String> userdept;

    private List<ListMenu> auths;

    private List<SysSet> sets;

    private String sessionid;

    private ServiceReturn sr;

    public OnlineUser() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((OnlineUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.sessionid = "";
    }

    public SysUser getUser() {
        if (this.user == null)
            this.user = new SysUser();

        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysDept getDept() {
        if (this.dept == null)
            this.dept = new SysDept();

        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "menugroups", "userdept", "menus", "sr" };
    }

    public List<SysMenuGroup> getMenugroups() {
        if (menugroups == null)
            menugroups = new ArrayList<SysMenuGroup>();

        return menugroups;
    }

    public List<SysMenu> getMenus() {
        if (menus == null)
            menus = new ArrayList<SysMenu>();
        return menus;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public List<String> getUserdept() {
        if (userdept == null)
            userdept = new ArrayList<String>();

        return userdept;
    }

    public void setUserdept(List<String> userdept) {
        this.userdept = userdept;
    }

    public List<ListMenu> getAuths() {
        if (auths == null)
            this.auths = new ArrayList<ListMenu>();

        return auths;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public List<SysSet> getSets() {
        if (sets == null)
            this.sets = new ArrayList<SysSet>();

        return sets;
    }

    public ServiceReturn getSr() {
        if (sr == null)
            sr = new ServiceReturn();

        return sr;
    }

    public void setSr(ServiceReturn sr) {
        this.sr = sr;
    }

}
