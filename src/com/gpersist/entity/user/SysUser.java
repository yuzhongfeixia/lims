package com.gpersist.entity.user;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class SysUser implements BaseBean {

    // 操作员编号;
    private String userid;

    // 操作员姓名;
    private String username;

    // 所属机构;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 所属公司;
    private String coid;

    // 公司名称;
    private String coname;

    // 创建日期;
    private java.util.Date crtdate;

    // 操作员岗位;
    private String userpost;

    // 操作员岗位名称;
    private String userpostname;

    // 操作员级别;
    private String userlevel;

    // 操作员级别名称;
    private String userlevelname;

    // 操作员职务;
    private String usertitle;

    // 操作员职务名称;
    private String usertitlename;

    // 操作员状态;
    private String userstatus;

    // 操作员状态名称;
    private String userstatusname;

    // 登陆密码;
    private String userpassword;

    // 业务流水号;
    private int userserial;

    // 当前工作日期;
    private String workdate;

    // 上一有效工作日;
    private String preworkdate;

    // 数据权限范围;
    private String authbound;

    // 是否系统管理员;
    private boolean isadmin;

    // 是否锁定;
    private boolean islock;

    // 是否首次登录;
    private boolean isfirst;

    // 输错密码次数;
    private int errorpassword;

    // 操作员电话;
    private String usertele;

    // 操作员电子邮件;
    private String useremail;

    // 密码最后修改日期;
    private java.util.Date modifypassword;

    // 备用;
    private String remark;

    // 角色编号
    private String roleid;

    private String usersex;

    private String usersexname;

    private java.util.Date birthdate;

    private String useredu;

    private String usereduname;

    private String worktitle;

    private String worktitlename;

    private String workyear;

    private String postyear;

    private String usermajor;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SysUser> item;

    public SysUser() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getUserid())) {
            msg.setErrmsg("操作员编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.username)) {
            msg.setErrmsg("操作员姓名不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getDeptid())) {
            msg.setErrmsg("请选择所属机构！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getUserlevel())) {
            msg.setErrmsg("请选择操作员级别！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getUsertitle())) {
            msg.setErrmsg("请选择操作员职务！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getUserstatus())) {
            msg.setErrmsg("请选择操作员状态！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.MustComboValue(this.getUserpost())) {
            msg.setErrmsg("请选择操作员岗位！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysUser) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "操作员编号:userid", "操作员姓名:username", "所属机构:deptid", "机构名称:deptname", "所属公司:coid",
                "公司名称:coname", "创建日期:crtdate", "操作员岗位:userpost", "操作员岗位名称:userpostname", "操作员级别:userlevel",
                "操作员级别名称:userlevelname", "操作员职务:usertitle", "操作员职务名称:usertitlename", "操作员状态:userstatus",
                "操作员状态名称:userstatusname", "登陆密码:userpassword", "业务流水号:userserial", "当前工作日期:workdate",
                "上一有效工作日:preworkdate", "数据权限范围:authbound", "是否系统管理员:isadmin", "是否锁定:islock", "是否首次登录:isfirst",
                "输错密码次数:errorpassword", "操作员电话:usertele", "操作员电子邮件:useremail", "密码最后修改日期:modifypassword", "备用:remark",
                "角色编号:roleid", "性别:usersex", "性别:usersexname", "文化程度:useredu", "文化程度:usereduname", "职称:worktitle",
                "职称:worktitlename", "从事年限:workyear", "本岗年限:postyear", "所学专业:usermajor", "出生日期:birthdate" };
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.userid = "";
        this.roleid = "";
        this.username = "";
        this.deptid = "";
        this.deptname = "";
        this.coid = "";
        this.coname = "";
        this.crtdate = ToolUtils.GetMinDate();
        this.userpost = "";
        this.userpostname = "";
        this.userlevel = "";
        this.userlevelname = "";
        this.usertitle = "";
        this.usertitlename = "";
        this.userstatus = "";
        this.userstatusname = "";
        this.userpassword = "";
        this.userserial = 0;
        this.workdate = "";
        this.preworkdate = "";
        this.authbound = "";
        this.isadmin = false;
        this.islock = false;
        this.isfirst = false;
        this.errorpassword = 0;
        this.usertele = "";
        this.useremail = "";
        this.modifypassword = ToolUtils.GetMinDate();
        this.birthdate = ToolUtils.GetMinDate();
        this.usersex = "";
        this.usersexname = "";
        this.worktitle = "";
        this.worktitlename = "";
        this.useredu = "";
        this.usereduname = "";
        this.workyear = "";
        this.postyear = "";
        this.usermajor = "";
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

    public java.util.Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(java.util.Date birthdate) {
        this.birthdate = birthdate;
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

    public String getWorktitle() {
        return worktitle;
    }

    public void setWorktitle(String worktitle) {
        this.worktitle = worktitle;
    }

    public String getWorktitlename() {
        return worktitlename;
    }

    public void setWorktitlename(String worktitlename) {
        this.worktitlename = worktitlename;
    }

    public String getWorkyear() {
        return workyear;
    }

    public void setWorkyear(String workyear) {
        this.workyear = workyear;
    }

    public String getPostyear() {
        return postyear;
    }

    public void setPostyear(String postyear) {
        this.postyear = postyear;
    }

    public String getUsermajor() {
        return usermajor;
    }

    public void setUsermajor(String usermajor) {
        this.usermajor = usermajor;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
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

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getConame() {
        return coname;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
    }

    public String getUserpost() {
        return userpost;
    }

    public void setUserpost(String userpost) {
        this.userpost = userpost;
    }

    public String getUserpostname() {
        return userpostname;
    }

    public void setUserpostname(String userpostname) {
        this.userpostname = userpostname;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public String getUserlevelname() {
        return userlevelname;
    }

    public void setUserlevelname(String userlevelname) {
        this.userlevelname = userlevelname;
    }

    public String getUsertitle() {
        return usertitle;
    }

    public void setUsertitle(String usertitle) {
        this.usertitle = usertitle;
    }

    public String getUsertitlename() {
        return usertitlename;
    }

    public void setUsertitlename(String usertitlename) {
        this.usertitlename = usertitlename;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getUserstatusname() {
        return userstatusname;
    }

    public void setUserstatusname(String userstatusname) {
        this.userstatusname = userstatusname;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public int getUserserial() {
        return userserial;
    }

    public void setUserserial(int userserial) {
        this.userserial = userserial;
    }

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public String getPreworkdate() {
        return preworkdate;
    }

    public void setPreworkdate(String preworkdate) {
        this.preworkdate = preworkdate;
    }

    public String getAuthbound() {
        return authbound;
    }

    public void setAuthbound(String authbound) {
        this.authbound = authbound;
    }

    public boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public boolean getIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public boolean getIsfirst() {
        return isfirst;
    }

    public void setIsfirst(boolean isfirst) {
        this.isfirst = isfirst;
    }

    public int getErrorpassword() {
        return errorpassword;
    }

    public void setErrorpassword(int errorpassword) {
        this.errorpassword = errorpassword;
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

    public java.util.Date getModifypassword() {
        return modifypassword;
    }

    public void setModifypassword(java.util.Date modifypassword) {
        this.modifypassword = modifypassword;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<SysUser> getItem() {
        if (item == null)
            item = new SelectBean<SysUser>();

        return item;
    }

    public void setItem(SelectBean<SysUser> item) {
        this.item = item;
    }

}
