package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasUser implements BaseBean {

    // 业务编号;
    private String tranid;

    // 人员编号;
    private String userid;

    // 人员姓名;
    private String username;

    // 所属部门;
    private String deptid;

    // 所属部门名称;
    private String deptname;

    // 所学专业;
    private String usermajor;

    // 联系电话;
    private String usertele;

    // 电子邮箱;
    private String useremail;

    // 实验员类型;
    private String opertype;

    // 实验员类型名称;
    private String opertypename;

    // 性别;
    private String usersex;

    // 性别名称;
    private String usersexname;

    // 民族;
    private String usernative;

    // 曾用名;
    private String altername;

    // 出生日期;
    private java.util.Date borndate;

    // 出生地;
    private String bornaddress;

    // 身份证号;
    private String useridentity;

    // 最高学历;
    private String useredu;

    // 学历名称;
    private String usereduname;

    // 最高学位;
    private String userdegree;

    // 学位名称;
    private String userdegreename;

    // 政治面貌;
    private String userpolity;

    // 政治面貌名称;
    private String userpolityname;

    // 家庭住址;
    private String homeaddress;

    // 家庭电话;
    private String hometele;

    // 备注;
    private String userremark;

    // 人员状态;
    private String userstatus;

    // 人员状态名称;
    private String userstatusname;

    // 人员职称;
    private String usertitle;

    // 人员职称名称;
    private String usertitlename;

    // 技术职务;
    private String userduty;

    // 技术职务名称;
    private String userdutyname;

    // 开始从事检测时间;
    private java.util.Date begintest;

    // 建立时间;
    private java.util.Date crtdate;

    // 修改时间;
    private java.util.Date modifydate;

    // 建立人;
    private String crtuser;

    // 建立人姓名;
    private String crtusername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasUser> item;

    public BasUser() {
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
        return ToolUtils.CompareProperty((BasUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "人员编号:userid", "人员姓名:username", "所属部门:deptid", "所属部门名称:deptname", "所学专业:usermajor",
                "联系电话:usertele", "电子邮箱:useremail", "实验员类型:opertype", "实验员类型名称:opertypename", "性别:usersex",
                "性别名称:usersexname", "民族:usernative", "曾用名:altername", "出生日期:borndate", "出生地:bornaddress",
                "身份证号:useridentity", "最高学历:useredu", "学历名称:usereduname", "最高学位:userdegree", "学位名称:userdegreename",
                "政治面貌:userpolity", "政治面貌名称:userpolityname", "家庭住址:homeaddress", "家庭电话:hometele", "备注:userremark",
                "人员状态:userstatus", "人员状态名称:userstatusname", "人员职称:usertitle", "人员职称名称:usertitlename", "技术职务:userduty",
                "技术职务名称:userdutyname", "开始从事检测时间:begintest", "建立时间:crtdate", "修改时间:modifydate", "建立人:crtuser",
                "建立人姓名:crtusername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.userid = "";
        this.username = "";
        this.deptid = "";
        this.deptname = "";
        this.usermajor = "";
        this.usertele = "";
        this.useremail = "";
        this.opertype = "";
        this.opertypename = "";
        this.usersex = "";
        this.usersexname = "";
        this.usernative = "";
        this.altername = "";
        this.borndate = ToolUtils.GetMinDate();
        this.bornaddress = "";
        this.useridentity = "";
        this.useredu = "";
        this.usereduname = "";
        this.userdegree = "";
        this.userdegreename = "";
        this.userpolity = "";
        this.userpolityname = "";
        this.homeaddress = "";
        this.hometele = "";
        this.userremark = "";
        this.userstatus = "";
        this.userstatusname = "";
        this.usertitle = "";
        this.usertitlename = "";
        this.userduty = "";
        this.userdutyname = "";
        this.begintest = ToolUtils.GetMinDate();
        this.crtdate = ToolUtils.GetMinDate();
        this.modifydate = ToolUtils.GetMinDate();
        this.crtuser = "";
        this.crtusername = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getUserstatusname() {
        return userstatusname;
    }

    public void setUserstatusname(String userstatusname) {
        this.userstatusname = userstatusname;
    }

    public String getUsertitlename() {
        return usertitlename;
    }

    public void setUsertitlename(String usertitlename) {
        this.usertitlename = usertitlename;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUsermajor() {
        return usermajor;
    }

    public void setUsermajor(String usermajor) {
        this.usermajor = usermajor;
    }

    public String getUsertele() {
        return usertele;
    }

    public void setUsertele(String usertele) {
        this.usertele = usertele;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getOpertype() {
        return opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype;
    }

    public String getOpertypename() {
        return opertypename;
    }

    public void setOpertypename(String opertypename) {
        this.opertypename = opertypename;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUsersexname() {
        return usersexname;
    }

    public void setUsersexname(String usersexname) {
        this.usersexname = usersexname;
    }

    public String getUsernative() {
        return usernative;
    }

    public void setUsernative(String usernative) {
        this.usernative = usernative;
    }

    public String getAltername() {
        return altername;
    }

    public void setAltername(String altername) {
        this.altername = altername;
    }

    public java.util.Date getBorndate() {
        return borndate;
    }

    public void setBorndate(java.util.Date borndate) {
        this.borndate = borndate;
    }

    public String getBornaddress() {
        return bornaddress;
    }

    public void setBornaddress(String bornaddress) {
        this.bornaddress = bornaddress;
    }

    public String getUseridentity() {
        return useridentity;
    }

    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity;
    }

    public String getUseredu() {
        return useredu;
    }

    public void setUseredu(String useredu) {
        this.useredu = useredu;
    }

    public String getUsereduname() {
        return usereduname;
    }

    public void setUsereduname(String usereduname) {
        this.usereduname = usereduname;
    }

    public String getUserdegree() {
        return userdegree;
    }

    public void setUserdegree(String userdegree) {
        this.userdegree = userdegree;
    }

    public String getUserdegreename() {
        return userdegreename;
    }

    public void setUserdegreename(String userdegreename) {
        this.userdegreename = userdegreename;
    }

    public String getUserpolity() {
        return userpolity;
    }

    public void setUserpolity(String userpolity) {
        this.userpolity = userpolity;
    }

    public String getUserpolityname() {
        return userpolityname;
    }

    public void setUserpolityname(String userpolityname) {
        this.userpolityname = userpolityname;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getHometele() {
        return hometele;
    }

    public void setHometele(String hometele) {
        this.hometele = hometele;
    }

    public String getUserremark() {
        return userremark;
    }

    public void setUserremark(String userremark) {
        this.userremark = userremark;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getUsertitle() {
        return usertitle;
    }

    public void setUsertitle(String usertitle) {
        this.usertitle = usertitle;
    }

    public String getUserduty() {
        return userduty;
    }

    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

    public String getUserdutyname() {
        return userdutyname;
    }

    public void setUserdutyname(String userdutyname) {
        this.userdutyname = userdutyname;
    }

    public java.util.Date getBegintest() {
        return begintest;
    }

    public void setBegintest(java.util.Date begintest) {
        this.begintest = begintest;
    }

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
    }

    public java.util.Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(java.util.Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public String getCrtusername() {
        return crtusername;
    }

    public void setCrtusername(String crtusername) {
        this.crtusername = crtusername;
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

    public SelectBean<BasUser> getItem() {
        if (item == null)
            item = new SelectBean<BasUser>();

        return item;
    }

    public void setItem(SelectBean<BasUser> item) {
        this.item = item;
    }

}
