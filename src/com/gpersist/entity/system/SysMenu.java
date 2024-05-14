package com.gpersist.entity.system;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SysMenu implements BaseBean {

    // 菜单编号;
    private int mid;

    // 菜单名称;
    private String mname;

    // 父菜单编号;
    private int mpid;

    // 菜单组编号;
    private int mgid;

    // 菜单组名称;
    private String mgname;

    // 菜单层次;
    private int mdepth;

    // 显示顺序;
    private int mdisp;

    // 菜单权限定义;
    private int mauth;

    // 菜单功能描述;
    private String mtip;

    // 菜单功能函数接口;
    private String mfunction;

    // 菜单下显示分割线;
    private boolean isline;

    // 是否存在子菜单;
    private boolean issub;

    // 第一个子菜单编号;
    private int msid;

    // 正常状态显示图标;
    private String mnormalicon;

    // 激活状态显示图标(暂定为菜单排序);
    private String mhoticon;

    // 无效状态显示图标;
    private String mdisableicon;

    // 基础菜单标志;
    private boolean isbase;

    // 菜单功能代码;
    private String mcode;

    // 菜单状态;
    private String mstatus;

    // 菜单快捷方式;
    private String mshortcut;

    // 是否直接窗口打开;
    private boolean istab;

    // 是否存在单机多机功能
    private boolean ismutil;

    private DataDeal deal;

    private SelectBean<SysMenu> item;

    public SysMenu() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysMenu) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.mid = 0;
        this.mname = "";
        this.mpid = 0;
        this.mgname = "";
        this.mgid = 0;
        this.mdepth = 0;
        this.mdisp = 0;
        this.mauth = 0;
        this.mtip = "";
        this.mfunction = "";
        this.isline = false;
        this.issub = false;
        this.msid = 0;
        this.mnormalicon = "";
        this.mhoticon = "";
        this.mdisableicon = "";
        this.isbase = false;
        this.mcode = "";
        this.mstatus = "";
        this.mshortcut = "";
        this.istab = true;
        this.ismutil = false;
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

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getMpid() {
        return mpid;
    }

    public void setMpid(int mpid) {
        this.mpid = mpid;
    }

    public String getMgname() {
        return mgname;
    }

    public void setMgname(String mgname) {
        this.mgname = mgname;
    }

    public int getMgid() {
        return mgid;
    }

    public void setMgid(int mgid) {
        this.mgid = mgid;
    }

    public int getMdepth() {
        return mdepth;
    }

    public void setMdepth(int mdepth) {
        this.mdepth = mdepth;
    }

    public int getMdisp() {
        return mdisp;
    }

    public void setMdisp(int mdisp) {
        this.mdisp = mdisp;
    }

    public int getMauth() {
        return mauth;
    }

    public void setMauth(int mauth) {
        this.mauth = mauth;
    }

    public String getMtip() {
        return mtip;
    }

    public void setMtip(String mtip) {
        this.mtip = mtip;
    }

    public String getMfunction() {
        return mfunction;
    }

    public void setMfunction(String mfunction) {
        this.mfunction = mfunction;
    }

    public boolean isIsline() {
        return isline;
    }

    public void setIsline(boolean isline) {
        this.isline = isline;
    }

    public boolean isIssub() {
        return issub;
    }

    public void setIssub(boolean issub) {
        this.issub = issub;
    }

    public int getMsid() {
        return msid;
    }

    public void setMsid(int msid) {
        this.msid = msid;
    }

    public String getMnormalicon() {
        return mnormalicon;
    }

    public void setMnormalicon(String mnormalicon) {
        this.mnormalicon = mnormalicon;
    }

    public String getMhoticon() {
        return mhoticon;
    }

    public void setMhoticon(String mhoticon) {
        this.mhoticon = mhoticon;
    }

    public String getMdisableicon() {
        return mdisableicon;
    }

    public void setMdisableicon(String mdisableicon) {
        this.mdisableicon = mdisableicon;
    }

    public boolean isIsbase() {
        return isbase;
    }

    public void setIsbase(boolean isbase) {
        this.isbase = isbase;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getMshortcut() {
        return mshortcut;
    }

    public void setMshortcut(String mshortcut) {
        this.mshortcut = mshortcut;
    }

    public boolean isIstab() {
        return istab;
    }

    public void setIstab(boolean istab) {
        this.istab = istab;
    }

    public boolean isIsmutil() {
        return ismutil;
    }

    public void setIsmutil(boolean ismutil) {
        this.ismutil = ismutil;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SelectBean<SysMenu> getItem() {
        if (item == null)
            item = new SelectBean<SysMenu>();

        return item;
    }

    public void setItem(SelectBean<SysMenu> item) {
        this.item = item;
    }

}
