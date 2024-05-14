package com.gpersist.action;

import java.util.List;

import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.gpersist.dao.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.org.SysDept;
import com.gpersist.entity.publics.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.enums.*;
import com.gpersist.service.*;
import com.gpersist.utils.*;

public class UserAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region User Methods

    private String rand;

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    private SysUser user;

    public SysUser getUser() {
        if (user == null)
            user = new SysUser();

        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        SysUser rtn = new SysUser();
        String userid = ServletActionContext.getRequest().getParameter("userid");

        if (ou != null) {
            rtn = UserDao.GetUser(userid);
            if (rtn == null) {
                rtn = new SysUser();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserEduCounts() throws Exception {
        UserEdu ue = new UserEdu();
        for (int i = 0; i < 8; i++) {
            int counts = UserDao.GetEduCounts("0" + (i + 1));
            switch (i) {
            case 0:
                ue.setXx(counts + "");
                break;
            case 1:
                ue.setCz(counts + "");
                break;
            case 2:
                ue.setGz(counts + "");
                break;
            case 3:
                ue.setDz(counts + "");
                break;
            case 4:
                ue.setBk(counts + "");
                break;
            case 5:
                ue.setBk(counts + "");
            case 6:
                ue.setSs(counts + "");
                break;
            case 7:
                ue.setBs(counts + "");
                break;
            default:
                break;
            }
        }
        String eduJson = JSON.toJSONString(ue);
        ToolUtils.OutString(eduJson);
        return null;
    }

    public String GetUserAgeCounts() throws Exception {

        String begindate = ToolUtils.Encode(ServletActionContext.getRequest().getParameter("begindate"));
        String enddate = ToolUtils.Encode(ServletActionContext.getRequest().getParameter("enddate"));
        UserAge ua = new UserAge();

        if (!ToolUtils.StringIsEmpty(begindate) && !ToolUtils.StringIsEmpty(enddate)) {
            ua.setCustom(GetUserCountsByAge(enddate + "-01-01 00:00:00", begindate + "-01-01 00:00:00") + "");
        } else {
            ua.setFifties(GetUserCountsByAge("1950-01-01 00:00:00", "1960-01-01 00:00:00") + "");
            ua.setSixties(GetUserCountsByAge("1960-01-01 00:00:00", "1970-01-01 00:00:00") + "");
            ua.setSeventies(GetUserCountsByAge("1970-01-01 00:00:00", "1980-01-01 00:00:00") + "");
            ua.setEighties(GetUserCountsByAge("1980-01-01 00:00:00", "1990-01-01 00:00:00") + "");
            ua.setNineties(GetUserCountsByAge("1990-01-01 00:00:00", "2000-01-01 00:00:00") + "");
        }

        String ageJson = JSON.toJSONString(ua);
        ToolUtils.OutString(ageJson);
        return null;
    }

    private int GetUserCountsByAge(String begin, String end) {
        String search = "";
        search += ToolUtils.GetAndSearch(search) + " a.birthdate >= '" + begin + "' ";
        search += ToolUtils.GetAndSearch(search) + " a.birthdate < '" + end + "' ";

        this.getUser().getSearch().setSearch(search);
        this.getUser().getSearch().setStart(start + 1);
        this.getUser().getSearch().setEnd(1000);

        List<SysUser> lists = UserDao.SearchUser(this.getUser());
        return lists.size();
    }

    public String GetUserDutyCounts() throws Exception {
        UserDuty ud = new UserDuty();
        ud.setTgyjy(UserDao.GetDutyCounts("02") + "");
        ud.setGjnys(UserDao.GetDutyCounts("04") + "");
        ud.setJs(UserDao.GetDutyCounts("06") + "");
        ud.setZlnys(UserDao.GetDutyCounts("08") + "");
        ud.setNys(UserDao.GetDutyCounts("10") + "");
        String ageJson = JSON.toJSONString(ud);
        ToolUtils.OutString(ageJson);
        return null;
    }

    public String Login() throws Exception {
        try {
            UserService.Login(this.getUser(), this.getRtv(), this.getRand());
        } catch (Exception e) {
            this.getRtv().SetValues(true, "系统错误！" + e.getMessage(), "", false);
        }

        ToolUtils.OutString(this.getRtv().toString());
        return null;
    }

    public String LoginOut() throws Exception {
        try {
            OnlineUser ou = ToolUtils.GetOnlineUser();

            if (ou != null)
                UserService.LogOut(ou, this.getRtv());

        } catch (Exception e) {
            this.getRtv().SetValues(true, "系统错误！" + e.getMessage(), "", false);
        }

        ToolUtils.OutString(this.getRtv().toString());
        return null;
    }

    public String OnlineInfo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            if (ou.getSessionid().equals(ServletActionContext.getRequest().getSession().getId())) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
                config.registerPropertyExclusions(com.gpersist.entity.org.SysDept.class, ou.getDept().OnExclusions());
                config.registerPropertyExclusions(com.gpersist.entity.user.SysUser.class, ou.getUser().OnExclusions());
                config.registerPropertyExclusions(com.gpersist.entity.system.SysSet.class, SysSet.GetExclusions());
                config.registerPropertyExclusions(com.gpersist.entity.user.OnlineUser.class, ou.OnExclusions());

                this.getRtv().SetValues(true, "", ToolUtils.GetJsonFromBean(ou, config), true);
            } else
                this.getRtv().SetValues(false, "", "", false);

            ToolUtils.OutString(this.getRtv().toString());
        } else {
            ToolUtils.OutString(this.NotLoginRtv());
        }

        return null;
    }

    public String GetMenu() throws Exception {
        try {
            UserService.GetMenu(this.getRtv());

        } catch (Exception e) {
            this.getRtv().SetValues(false, e.toString(), "", false);
        }

        ToolUtils.OutString(this.getRtv().toString());
        return null;
    }

    public String ChangePwd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("9999");

            this.getUser().setUserid(ou.getUser().getUserid());
            this.getUser().getDeal().setUserid(ou.getUser().getUserid());

            UserService.ChangePwd(this.getUser(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String MustChangePwd() throws Exception {
        TranLog log = new TranLog();
        log.setTranuser(this.getUser().getUserid());
        log.setTrandept(this.getUser().getUserid());
        log.setTrancode("9999");

        this.getUser().getDeal().setUserid(this.getUser().getUserid());

        UserService.ChangePwd(this.getUser(), this.getRtv(), log);

        ToolUtils.OutString(this.getRtv().toString());

        return null;
    }

    public String GetUserName() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");

            this.getUser().setUserid(userid);
            this.getUser().getSearch().setUserid(ou.getUser().getUserid());

            String name = UserDao.GetUserName(this.getUser());

            ToolUtils.OutString("{\"name\":\"" + name + "\"}");
        } else {
            ToolUtils.OutString("{\"name\":\"\"}");
        }

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_SET_LOCK, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SetUnLock() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_SET_LOCK);

            this.getUser().getDeal().setUserid(ou.getUser().getUserid());
            UserService.SetUnLock(this.getUser(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_SET_PASSWD, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SetPasswd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_SET_PASSWD);

            this.getUser().getDeal().setUserid(ou.getUser().getUserid());
            UserService.SetPasswd(this.getUser(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER + SysMenuCode.CODE_SPLIT + SysMenuCode.CODE_USER_DEPT
            + SysMenuCode.CODE_SPLIT
            + SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String deptid = this.getUser().getDeptid();
            String username = ToolUtils.Decode(this.getUser().getUsername());
            String search = "";

            if (!ToolUtils.StringIsEmpty(deptid) && !deptid.equals(Consts.DEFAULT_ALL_DEPT))
                search += ToolUtils.GetAndSearch(search) + " a.deptid = '" + deptid + "' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " a.username like '%" + username + "%' ";

            this.getUser().getSearch().setSearch(search);
            this.getUser().getSearch().setStart(start + 1);
            this.getUser().getSearch().setEnd(this.GetEndCnt());
            this.getUser().getSearch().setUserid(ou.getUser().getUserid());

            List<SysUser> lists = UserDao.SearchUser(this.getUser());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getUser().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("操作员信息", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_USER);

            this.getUser().getDeal().setUserid(ou.getUser().getUserid());

            UserService.SaveUser(this.getUser(), this.getRtv(), log, ou);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SelectAllUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SelectUser> lists = UserDao.SelectAllUser();

            ToolUtils.OutString(this.OutLists(lists, lists.size()));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserByDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SysUser> lists = UserDao.GetListUserByDept(this.getUser());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("操作员信息", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion User Methods

    // region Role Methods

    private SysRole role;

    public SysRole getRole() {
        if (role == null)
            role = new SysRole();

        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_ROLE + SysMenuCode.CODE_SPLIT
            + SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetRole() throws Exception {
        /*
         * OnlineUser ou = ToolUtils.GetOnlineUser();
         * 
         * if (ou != null) { List<SysRole> lists = UserDao.GetListRole();
         * 
         * if (!hasexport) { ToolUtils.OutString(this.OutLists(lists, lists.size(),
         * false)); } else { ExportSetting es = this.GetExportSetting("角色", false);
         * this.OutExport(lists, es); return Consts.DEFAULT_EXCEL_RETURN; } } else
         * ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
         */

        List<SysRole> lists = UserDao.GetListRole();
        ToolUtils.OutString(this.OutLists(lists, lists.size(), false));

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_ROLE + SysMenuCode.CODE_SPLIT
            + SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetRoleDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            int roleid = Integer.parseInt(ServletActionContext.getRequest().getParameter("roleid"));
            this.getRole().setRoleid(roleid);

            List<SysRoleDetail> lists = UserDao.GetRoleDetail(this.getRole());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_ROLE, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_ROLE);

            List<SysRoleDetail> roledetail = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("auths"), SysRoleDetail.class);
            UserService.SaveRole(role, roledetail, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetUnSetRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("unset");

            List<SysRole> lists = UserDao.GetSetRole(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetSetRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("set");

            List<SysRole> lists = UserDao.GetSetRole(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_ROLE, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SaveSetRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_USER_ROLE);

            List<SysRole> setroles = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("setroles"), SysRole.class);
            UserService.SaveSetRole(this.getUser(), setroles, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    private SysUserRole userRole;

    public SysUserRole getUserRole() {
        if (userRole == null)
            userRole = new SysUserRole();

        return userRole;
    }

    public void setUserRole(SysUserRole userRole) {
        this.userRole = userRole;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSysRoleByRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SysUserRole> lists = UserDao.GetListSysRoleByRole(this.getUserRole());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion Role Methods

    // region User Dept Methods

    @AuthMethod(Menus = SysMenuCode.CODE_USER_DEPT, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetSetUserDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("set");

            List<SysDept> lists = UserDao.GetUserDept(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_DEPT, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetUnSetUserDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("unset");

            List<SysDept> lists = UserDao.GetUserDept(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_DEPT, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SaveUserDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_USER_DEPT);

            List<SysDept> setdepts = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("setdepts"), SysDept.class);
            UserService.SaveUserDept(this.getUser(), setdepts, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion User Dept Methods

    // region Create Menu Detail Methods

    @AuthMethod(Menus = SysMenuCode.CODE_CREATE_MENU_DETAIL, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String CreateMenuDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_CREATE_MENU_DETAIL);

            UserService.CreateMenuDetail(this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion Create Menu Detail Methods

    // region WorkGroup Methods

    private SysWorkGroup wgroup;

    public SysWorkGroup getWgroup() {
        if (wgroup == null)
            wgroup = new SysWorkGroup();

        return wgroup;
    }

    public void setWgroup(SysWorkGroup wgroup) {
        this.wgroup = wgroup;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_WORKGROUP, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String SearchWorkGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getWgroup().getSearch().setStart(start + 1);
            this.getWgroup().getSearch().setEnd(this.GetEndCnt());
            this.getWgroup().getSearch().setUserid(ou.getUser().getUserid());

            List<SysWorkGroup> lists = UserDao.SearchWorkGroup(this.getWgroup());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getWgroup().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("工作组", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_WORKGROUP, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveWorkGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_WORKGROUP);

            UserService.SaveWorkGroup(this.getWgroup(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_WORKGROUP, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetUnSetWorkGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("unset");

            List<SysWorkGroup> lists = UserDao.GetSetWorkGroup(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_WORKGROUP, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetSetWorkGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String userid = ServletActionContext.getRequest().getParameter("userid");
            this.getUser().setUserid(userid);
            this.getUser().getItem().setGetaction("set");

            List<SysWorkGroup> lists = UserDao.GetSetWorkGroup(this.getUser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_USER_WORKGROUP, Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SaveSetWorkGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_USER_WORKGROUP);

            List<SysWorkGroup> sets = ToolUtils.GetArrayFromJson(ServletActionContext.getRequest().getParameter("sets"),
                    SysWorkGroup.class);
            UserService.SaveSetWorkGroup(this.getUser(), sets, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion WorkGroup Methods

}
