package com.gpersist.service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.gpersist.dao.*;
import com.gpersist.entity.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;
import com.gpersist.listener.*;
import com.gpersist.utils.*;
import com.opensymphony.xwork2.ActionContext;

public class UserService {

    // region User Mthods

    public static void Login(SysUser user, ReturnValue rtv, String rand) {
        rtv.setSuccess(false);

        String arandom = (String) (ServletActionContext.getContext().getSession().get("random"));

        if (!ToolUtils.StringIsEmpty(arandom) && !arandom.equals(rand)) {
            // rtv.setMsg("验证码输入错误，请重新输入");
            // return;
        }

        if (ToolUtils.StringIsEmpty(user.getUserid()) || ToolUtils.StringIsEmpty(user.getUserpassword())) {
            rtv.setMsg("无效的用户名和密码!");
            return;
        }

        SqlSession session = DBUtils.getFactory();

        try {
            OnlineUser ou = UserDao.GetOnlineUser(session, user.getUserid());

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            config.registerPropertyExclusions(com.gpersist.entity.org.SysDept.class, ou.getDept().OnExclusions());
            config.registerPropertyExclusions(com.gpersist.entity.user.SysUser.class, ou.getUser().OnExclusions());
            config.registerPropertyExclusions(com.gpersist.entity.system.SysSet.class, SysSet.GetExclusions());
            config.registerPropertyExclusions(com.gpersist.entity.user.OnlineUser.class, ou.OnExclusions());

            if (ToolUtils.StringIsEmpty(ou.getUser().getUserid())
                    || ToolUtils.StringIsEmpty(ou.getDept().getDeptid())) {
                rtv.setMsg("该用户不存在!");
                return;
            }

            if (!ou.getUser().getUserstatus().equals(Consts.DEFAULT_VALID_STATUS)) {
                rtv.setMsg("无效的部门状态!");
                return;
            }

            if (!ou.getDept().getDeptstatus().equals(Consts.DEFAULT_VALID_STATUS)) {
                rtv.setMsg("无效的用户状态!");
                return;
            }

            if (ou.getUser().getIslock()) {
                rtv.setMsg("该用户被锁定，不能登录系统，请联系系统管理员!");
                return;
            }

            ou.getUser().getDeal().setIp(ServletActionContext.getRequest().getRemoteAddr());

            if (!ou.getUser().getUserpassword().equals(ToolUtils.GetMD5(user.getUserpassword()))) {
                switch (ou.getUser().getErrorpassword()) {
                case 2:
                    rtv.setMsg("您已经连续输错3次密码，还有2次机会!");
                    break;

                case 3:
                    rtv.setMsg("您已经连续输错4次密码，还有1次机会!");
                    break;

                default:
                    rtv.setMsg("密码不正确!");
                    break;
                }

                if (ou.getUser().getErrorpassword() >= 4) {
                    rtv.setMsg("您的帐号因为多次尝试错误密码而被锁定，请与系统管理员联系！");
                    UserDao.UserLock(session, ou.getUser());
                } else {
                    UserDao.UserError(session, ou.getUser());
                }

                session.commit();
                return;
            }

            if (ou.getUser().getIsfirst()) {
                rtv.setMsg("您的帐号是第一次登录系统，请先修改初始化密码！");
                rtv.setData("ChangePassword");
                return;
            }

            ou.getSets().clear();
            ou.getSets().addAll(SystemDao.GetSetList(session));

            boolean ispasswdvalid = false;

            try {
                ispasswdvalid = Boolean.parseBoolean(ToolUtils.GetSetValue(ou, Consts.SYS_SET_PASSWDVALID));
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (ispasswdvalid) {
                int passwdvalid = 90;

                try {
                    passwdvalid = Integer.parseInt(ToolUtils.GetSetValue(ou, Consts.SYS_SET_PASSWDVALIDDAYS));
                } catch (Exception e) {
                    // TODO: handle exception
                }

                int days = ToolUtils.GetDaysBetween(ou.getUser().getModifypassword(), ToolUtils.GetNowDate());

                if (days >= passwdvalid) {
                    rtv.setMsg("密码过期，请修改密码！");
                    rtv.setData("ChangePassword");
                    return;
                }

                if (days == (passwdvalid - 10)) {
                    ou.getUser().setRemark("TenDays");
                }
            }

            UserDao.UserLoginLog(session, ou.getUser());

            // List<SysDept> userdepts = SystemDao.GetUserDept(session,
            // ou.getUser().getUserid());
            /*
             * for (OrgDept dept : userdepts) { ou.getUserdept().add(dept.getDeptid()); }
             */

            ou.getMenugroups().clear();
            ou.getMenugroups().addAll(UserDao.GetMenuGroupByUser(session, ou.getUser()));
            ou.getMenus().clear();
            ou.getMenus().addAll(UserDao.GetMenuByUser(session, ou.getUser()));

            ou.getAuths().clear();
            for (SysMenu menu : ou.getMenus()) {
                ListMenu lm = new ListMenu();
                lm.setMid(menu.getMid());
                lm.setMcode(menu.getMcode());
                lm.setMauth(menu.getMauth());
                ou.getAuths().add(lm);
            }

            ou.setSessionid(ServletActionContext.getRequest().getSession().getId());
            ActionContext.getContext().getSession().put(Consts.DEFAULT_USER, ou);
            ServletActionContext.getRequest().getSession().setMaxInactiveInterval(-1);
            UserSessionListener.isLogined(ServletActionContext.getRequest().getSession(), ou.getUser().getUserid());

            rtv.setData(JSONObject.fromObject(ou, config).toString());
            rtv.setSuccess(true);
            rtv.setBean(true);
            rtv.setMsg("登录成功!");
            session.commit();
        } catch (Exception e) {
            // TODO: handle exception
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "登录失败:"));
        } finally {
            session.close();
        }
    }

    public static void LogOut(OnlineUser ou, ReturnValue rtv) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            // Deal Success Login
            UserDao.UserLoginOutLog(session, ou.getUser());

            ActionContext.getContext().getSession().put("User", null);
            rtv.setSuccess(true);
            rtv.setBean(true);
            rtv.setMsg("");
            session.commit();
        } catch (Exception e) {
            // TODO: handle exception
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e));
        } finally {
            session.close();
        }
    }

    public static void GetMenu(ReturnValue rtv) {
        List<TreePanel> tps = new ArrayList<TreePanel>();

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou == null) {
            rtv.SetValues(false, "未登录", ToolUtils.GetJsonFromArray(tps), true);
        } else {

            if ((ou.getMenugroups().size() > 0) && (ou.getMenus().size() > 0)) {
                for (SysMenuGroup mg : ou.getMenugroups()) {
                    TreePanel tp = new TreePanel();

                    tp.setId(Integer.toString(mg.getMgid()));
                    tp.setTitle(mg.getMgname());

                    for (SysMenu menu : ou.getMenus()) {
                        if (menu.getMgid() != mg.getMgid())
                            continue;

                        TreeMenu tm = new TreeMenu();
                        tm.setMid(Integer.toString(menu.getMid()));
                        tm.setText(menu.getMname());
                        tm.setUrl(menu.getMfunction());
                        tm.setTooltip(menu.getMtip());
                        tm.setMcode(menu.getMcode());
                        tm.setIstab(menu.isIstab());
                        tm.setIsmutil(menu.isIsmutil());

                        if (!ToolUtils.StringIsEmpty(menu.getMnormalicon())) {
                            tm.setMnormalicon(menu.getMnormalicon());
                            tm.setIconCls(menu.getMnormalicon());
                        }

                        if (menu.isIssub())
                            tm.setLeaf(false);
                        else
                            tm.setLeaf(true);

                        if (menu.getMid() == menu.getMpid())
                            tp.getRoot().add(tm);
                        else
                            GetSubMenu(tp.getRoot(), menu);
                    }

                    if (tp.getRoot().size() > 0)
                        tps.add(tp);
                }
            }

            rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(tps), true);
        }
    }

    public static void GetSubMenu(List<TreeMenu> items, SysMenu menu) {
        for (TreeMenu tm : items) {
            if (tm.getMid().equals(Integer.toString(menu.getMpid()))) {
                TreeMenu stm = new TreeMenu();
                stm.setMid(Integer.toString(menu.getMid()));
                stm.setText(menu.getMname());
                stm.setUrl(menu.getMfunction());
                stm.setTooltip(menu.getMtip());
                stm.setMcode(menu.getMcode());
                stm.setIstab(menu.isIstab());
                tm.setIsmutil(menu.isIsmutil());

                stm.setMnormalicon(menu.getMnormalicon());
                if (!ToolUtils.StringIsEmpty(menu.getMnormalicon())) {
                    stm.setMnormalicon(menu.getMnormalicon());
                    stm.setIconCls(menu.getMnormalicon());
                }

                if (menu.isIssub())
                    stm.setLeaf(false);
                else
                    stm.setLeaf(true);

                tm.getChildren().add(stm);
                break;
            }

            if (tm.getChildren().size() > 0)
                GetSubMenu(tm.getChildren(), menu);
        }
    }

    public static void ChangePwd(SysUser user, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            if (ToolUtils.StringIsEmpty(user.getUsername())) {
                rtv.setMsg("原密码不能为空！");
                return;
            }

            if (ToolUtils.StringIsEmpty(user.getUserpassword())) {
                rtv.setMsg("新密码不能为空！");
                return;
            }

            if (user.getUsername().equals(user.getUserpassword())) {
                rtv.setMsg("新密码不能和旧密码一样！");
                return;
            }

            /*
             * if ((user.getUserpassword().length() < 8) ||
             * !user.getUserpassword().matches(".*?[a-z]+.*?") ||
             * !user.getUserpassword().matches(".*?[0-9]+.*?") ||
             * !user.getUserpassword().matches(".*?[A-Z]+.*?")) {
             * rtv.setMsg("新密码最少8位、要求有数字、字母、大小写！"); return; }
             */

            if (!user.getUserpassword().equals(user.getRemark())) {
                rtv.setMsg("新密码两次输入不同，请确认后再进行保存！");
                return;
            }

            SysUser ou = UserDao.GetUser(session, user.getUserid());

            if (ToolUtils.StringIsEmpty(ou.getUserpassword())
                    || !ou.getUserpassword().equals(ToolUtils.GetMD5(user.getUsername()))) {
                rtv.setMsg("原密码不正确！");
                return;
            }

            user.setUserpassword(ToolUtils.GetMD5(user.getUserpassword()));

            UserDao.ChangePwd(session, user);

            log.setTranaction(TranAction.Deal.getTranaction());
            log.setTrandesc(user.getUserid());
            log.setTrandept(ou.getDeptid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("密码修改成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "密码修改失败:"));
        } finally {
            session.close();
        }
    }

    public static void SetUnLock(SysUser user, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            UserDao.SetUnLock(session, user);

            log.setTranaction(TranAction.Deal.getTranaction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("操作员解锁成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "操作员解锁失败:"));
        } finally {
            session.close();
        }
    }

    public static void SetPasswd(SysUser user, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            user.setUserpassword(ToolUtils.GetMD5(user.getUserid()));

            UserDao.SetPasswd(session, user);

            log.setTranaction(TranAction.Deal.getTranaction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("密码设置成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "密码设置失败:"));
        } finally {
            session.close();
        }
    }

    public static void SaveUser(SysUser user, ReturnValue rtv, TranLog log, OnlineUser ou) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            if (user.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            user.setUserpassword(ToolUtils.GetMD5(user.getUserid()));

            UserDao.SaveUser(session, user);

            log.ActionToTran(user.getDeal().getAction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("保存成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "保存出错:"));
        } finally {
            session.close();
        }
    }

    // endregion User Methods

    // region Role Methods

    public static void SaveRole(SysRole role, List<SysRoleDetail> roledetail, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            if (role.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            UserDao.SaveRole(session, role, roledetail);

            log.ActionToTran(role.getDeal().getAction());
            log.setTrandesc(String.valueOf(role.getRoleid()));
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("保存成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "保存出错:"));
        } finally {
            session.close();
        }
    }

    public static void SaveSetRole(SysUser user, List<SysRole> setroles, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            user.getDeal().setAction(DataAction.Deal.getAction());

            if (user.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            UserDao.SaveSetRole(session, user, setroles);

            // UserDao.EndSetRole(session, user);

            log.ActionToTran(user.getDeal().getAction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("保存成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "保存出错:"));
        } finally {
            session.close();
        }
    }

    // endregion Role Methods

    // region User Dept Methods

    public static void SaveUserDept(SysUser user, List<SysDept> detps, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            user.getDeal().setAction(DataAction.Deal.getAction());

            if (user.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            UserDao.SaveUserDept(session, user, detps);

            log.ActionToTran(user.getDeal().getAction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("分配成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "分配出错:"));
        } finally {
            session.close();
        }
    }

    // endregion User Dept Methods

    // region Create Menu Detail Methods

    public static void CreateMenuDetail(ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            List<SysMenu> menus = UserDao.GetListMenu(session);

            // SysMenuDetail item = new SysMenuDetail();
            String auth = "";
            int i = 0;

            String deletesql = "delete from t_sys_menu_detail where mid = ?";

            PreparedStatement ps = session.getConnection().prepareStatement(deletesql);

            for (SysMenu menu : menus) {
                ps.setInt(1, menu.getMid());
                ps.addBatch();
            }

            ps.executeBatch();

            String insertsql = "insert into t_sys_menu_detail(mid, authid) values (?, ?)";

            ps = session.getConnection().prepareStatement(insertsql);

            for (SysMenu menu : menus) {
                auth = Integer.toBinaryString(menu.getMauth());

                for (i = 0; i < auth.length(); i++) {
                    if (auth.substring(i, i + 1).equals("1")) {
                        ps.setInt(1, menu.getMid());
                        ps.setInt(2, auth.length() - i);
                        ps.addBatch();
                    }
                }
            }

            ps.executeBatch();

            /*
             * for (SysMenu menu : menus) { item.setMid(menu.getMid());
             * item.getDeal().setAction(DataAction.Delete.getAction());
             * 
             * UserDao.SaveMenuDetial(session, item);
             * 
             * auth = Integer.toBinaryString(menu.getMauth());
             * 
             * item.getDeal().setAction(DataAction.Create.getAction());
             * 
             * for (i = 0; i < auth.length(); i++) { if (auth.substring(i, i +
             * 1).equals("1")) { item.setAuthid(auth.length() - i);
             * UserDao.SaveMenuDetial(session, item); } } }
             */

            UserDao.LastCreateMenuDetail(session);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SET_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SET_F));
        } finally {
            session.close();
        }
    }

    // endregion Create Menu Detail Methods

    // region WorkGroup Methods

    public static void SaveWorkGroup(SysWorkGroup item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            UserDao.SaveWorkGroup(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getWorkgroup() + "-" + item.getWorkgroupname());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveSetWorkGroup(SysUser user, List<SysWorkGroup> sets, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            user.getDeal().setAction(DataAction.Deal.getAction());

            UserDao.SaveSetWorkGroup(session, user, sets);

            log.ActionToTran(user.getDeal().getAction());
            log.setTrandesc(user.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SET_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SET_F));
        } finally {
            session.close();
        }
    }

    // endregion WorkGroup Methods

}
