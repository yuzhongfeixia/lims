package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;
import com.gpersist.entity.publics.*;

public class SysRoleDetail implements BaseBean {

    private int mid;

    private String mname;

    private boolean isselect;

    private boolean abrowse;

    private boolean anew;

    private boolean aedit;

    private boolean adelete;

    private boolean aprint;

    private boolean aexport;

    private boolean adeal;

    private boolean avalid;

    private boolean ainvalid;

    private boolean acheck;

    private boolean auncheck;

    private boolean aupload;

    private boolean aspecial01;

    private boolean aspecial02;

    private boolean aspecial03;

    private boolean asubmit;

    private DataDeal deal;

    private SelectBean<SysRoleDetail> item;

    public SysRoleDetail() {
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
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.mid = 0;
        this.mname = "";
        this.isselect = false;
        this.abrowse = false;
        this.adeal = false;
        this.adelete = false;
        this.aedit = false;
        this.aexport = false;
        this.anew = false;
        this.aprint = false;
        this.avalid = false;
        this.ainvalid = false;
        this.acheck = false;
        this.auncheck = false;
        this.aupload = false;
        this.aspecial01 = false;
        this.aspecial02 = false;
        this.aspecial03 = false;
        this.asubmit = false;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal" };
    }

    public int GetAuth() {
        int auth = 0;

        if (this.abrowse)
            auth += 1;

        if (this.anew)
            auth += 2;

        if (this.aedit)
            auth += 4;

        if (this.adelete)
            auth += 8;

        if (this.aprint)
            auth += 16;

        if (this.aexport)
            auth += 32;

        if (this.adeal)
            auth += 64;

        if (this.avalid)
            auth += 128;

        if (this.ainvalid)
            auth += 256;

        if (this.acheck)
            auth += 512;

        if (this.auncheck)
            auth += 1024;

        if (this.aupload)
            auth += 2048;

        if (this.aspecial01)
            auth += 4096;

        if (this.aspecial02)
            auth += 8192;

        if (this.aspecial03)
            auth += 16384;

        if (this.asubmit)
            auth += 32768;

        return auth;
    }

    public String GetAuthString() {
        int auth = 0;

        if (this.abrowse)
            auth += 1;

        if (this.anew)
            auth += 2;

        if (this.aedit)
            auth += 4;

        if (this.adelete)
            auth += 8;

        if (this.aprint)
            auth += 16;

        if (this.aexport)
            auth += 32;

        if (this.adeal)
            auth += 64;

        if (this.avalid)
            auth += 128;

        if (this.ainvalid)
            auth += 256;

        if (this.acheck)
            auth += 512;

        if (this.auncheck)
            auth += 1024;

        if (this.aupload)
            auth += 2048;

        if (this.aspecial01)
            auth += 4096;

        if (this.aspecial02)
            auth += 8192;

        if (this.aspecial03)
            auth += 16384;

        if (this.asubmit)
            auth += 32768;

        String sauth = Integer.toBinaryString(auth);

        return ToolUtils.LeftFill(sauth, 7);
    }

    public boolean isAsubmit() {
        return asubmit;
    }

    public void setAsubmit(boolean asubmit) {
        this.asubmit = asubmit;
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

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public boolean isAbrowse() {
        return abrowse;
    }

    public void setAbrowse(boolean abrowse) {
        this.abrowse = abrowse;
    }

    public boolean isAnew() {
        return anew;
    }

    public void setAnew(boolean anew) {
        this.anew = anew;
    }

    public boolean isAedit() {
        return aedit;
    }

    public void setAedit(boolean aedit) {
        this.aedit = aedit;
    }

    public boolean isAdelete() {
        return adelete;
    }

    public void setAdelete(boolean adelete) {
        this.adelete = adelete;
    }

    public boolean isAprint() {
        return aprint;
    }

    public void setAprint(boolean aprint) {
        this.aprint = aprint;
    }

    public boolean isAexport() {
        return aexport;
    }

    public void setAexport(boolean aexport) {
        this.aexport = aexport;
    }

    public boolean isAdeal() {
        return adeal;
    }

    public void setAdeal(boolean adeal) {
        this.adeal = adeal;
    }

    public boolean isAvalid() {
        return avalid;
    }

    public void setAvalid(boolean avalid) {
        this.avalid = avalid;
    }

    public boolean isAcheck() {
        return acheck;
    }

    public void setAcheck(boolean acheck) {
        this.acheck = acheck;
    }

    public DataDeal getDeal() {
        if (deal == null)
            this.deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SysRoleDetail> getItem() {
        if (item == null)
            item = new SelectBean<SysRoleDetail>();

        return item;
    }

    public void setItem(SelectBean<SysRoleDetail> item) {
        this.item = item;
    }

    public boolean isAinvalid() {
        return ainvalid;
    }

    public void setAinvalid(boolean ainvalid) {
        this.ainvalid = ainvalid;
    }

    public boolean isAuncheck() {
        return auncheck;
    }

    public void setAuncheck(boolean auncheck) {
        this.auncheck = auncheck;
    }

    public boolean isAupload() {
        return aupload;
    }

    public void setAupload(boolean aupload) {
        this.aupload = aupload;
    }

    public boolean isAspecial01() {
        return aspecial01;
    }

    public void setAspecial01(boolean aspecial01) {
        this.aspecial01 = aspecial01;
    }

    public boolean isAspecial02() {
        return aspecial02;
    }

    public void setAspecial02(boolean aspecial02) {
        this.aspecial02 = aspecial02;
    }

    public boolean isAspecial03() {
        return aspecial03;
    }

    public void setAspecial03(boolean aspecial03) {
        this.aspecial03 = aspecial03;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}