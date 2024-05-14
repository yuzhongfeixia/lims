package com.gpersist.entity.system;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysMenuGroup implements BaseBean {

    // 菜单组编号;
    private int mgid;

    // 菜单组名称;
    private String mgname;

    // 菜单组描述;
    private String mgtip;

    // 正常状态显示图标;
    private String mgnormalicon;

    // 激活状态显示图标;
    private String mghoticon;

    // 无效状态显示图标;
    private String mgdisableicon;

    // 菜单组功能函数接口;
    private String mgfunction;

    // 菜单组状态;
    private String mgstatus;

    // 排序;
    private int mgorder;

    private DataDeal deal;

    private SelectBean<SysMenuGroup> item;

    public SysMenuGroup() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysMenuGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.mgid = 0;
        this.mgname = "";
        this.mgtip = "";
        this.mgnormalicon = "";
        this.mghoticon = "";
        this.mgdisableicon = "";
        this.mgfunction = "";
        this.mgstatus = "";
        this.mgorder = 0;
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

    public int getMgid() {
        return mgid;
    }

    public void setMgid(int mgid) {
        this.mgid = mgid;
    }

    public String getMgname() {
        return mgname;
    }

    public void setMgname(String mgname) {
        this.mgname = mgname;
    }

    public String getMgtip() {
        return mgtip;
    }

    public void setMgtip(String mgtip) {
        this.mgtip = mgtip;
    }

    public String getMgnormalicon() {
        return mgnormalicon;
    }

    public void setMgnormalicon(String mgnormalicon) {
        this.mgnormalicon = mgnormalicon;
    }

    public String getMghoticon() {
        return mghoticon;
    }

    public void setMghoticon(String mghoticon) {
        this.mghoticon = mghoticon;
    }

    public String getMgdisableicon() {
        return mgdisableicon;
    }

    public void setMgdisableicon(String mgdisableicon) {
        this.mgdisableicon = mgdisableicon;
    }

    public String getMgfunction() {
        return mgfunction;
    }

    public void setMgfunction(String mgfunction) {
        this.mgfunction = mgfunction;
    }

    public String getMgstatus() {
        return mgstatus;
    }

    public void setMgstatus(String mgstatus) {
        this.mgstatus = mgstatus;
    }

    public int getMgorder() {
        return mgorder;
    }

    public void setMgorder(int mgorder) {
        this.mgorder = mgorder;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SelectBean<SysMenuGroup> getItem() {
        if (item == null)
            item = new SelectBean<SysMenuGroup>();

        return item;
    }

    public void setItem(SelectBean<SysMenuGroup> item) {
        this.item = item;
    }

}