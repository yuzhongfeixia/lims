package com.gpersist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlLabDao;
import com.alms.entity.lab.BusConsign;
import com.gpersist.dao.sqlserver.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.entity.org.*;
import com.gpersist.utils.*;

public class UserDao {

    // region User Method

    public static SysUser GetUser(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUser(session, userid);
        } catch (Exception e) {
            return new SysUser();
        } finally {
            session.close();
        }
    }

    public static SysUser GetUser(SqlSession session, String userid) {
        SysUser item = new SysUser();
        item.setUserid(userid);

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SysUser();

        default:
            return SqlUserDao.GetUser(session, item);
        }
    }

    public static OnlineUser GetOnlineUser(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetOnlineUser(session, userid);
        } catch (Exception e) {
            return new OnlineUser();
        } finally {
            session.close();
        }
    }

    public static OnlineUser GetOnlineUser(SqlSession session, String userid) {
        OnlineUser ou = new OnlineUser();

        ou.setUser(GetUser(session, userid));

        if (!ToolUtils.StringIsEmpty(ou.getUser().getDeptid()))
            ou.setDept(OrgDao.GetDept(session, ou.getUser().getDeptid()));

        return ou;
    }

    public static void UserLock(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.UserLock(session, item);
            break;
        }
    }

    public static void UserError(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.UserError(session, item);
            break;
        }
    }

    public static void UserLoginLog(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.UserLoginLog(session, item);
            break;
        }
    }

    public static void UserLoginOutLog(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.UserLoginOutLog(session, item);
            break;
        }
    }

    public static void ChangePwd(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.ChangePwd(session, item);
            break;
        }
    }

    public static String GetUserName(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserName(session, item);
        } catch (Exception e) {
            return "";
        } finally {
            session.close();
        }
    }

    public static String GetUserName(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return "";

        default:
            return SqlUserDao.GetUserName(session, item);
        }
    }

    public static Integer GetEduCounts(String useredu) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetEduCounts(session, useredu);
        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public static int GetEduCounts(SqlSession session, String useredu) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return 0;

        default:
            return SqlUserDao.GetEduCounts(session, useredu);
        }
    }

    public static Integer GetAgeCounts(String begin, String end) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAgeCounts(session, begin, end);
        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public static Integer GetAgeCounts(SqlSession session, String begin, String end) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return 0;

        default:
            return SqlUserDao.GetAgeCounts(session, begin, end);
        }
    }

    public static Integer GetDutyCounts(String userduty) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDutyCounts(session, userduty);
        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public static int GetDutyCounts(SqlSession session, String userduty) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return 0;

        default:
            return SqlUserDao.GetDutyCounts(session, userduty);
        }
    }

    public static void SetUnLock(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SetUnLock(session, item);
            break;
        }
    }

    public static void SetPasswd(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SetPasswd(session, item);
            break;
        }
    }

    public static List<SysUser> SearchUser(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchUser(session, item);
        } catch (Exception e) {
            return new ArrayList<SysUser>();
        } finally {
            session.close();
        }
    }

    public static List<SysUser> SearchUser(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysUser>();

        default:
            return SqlUserDao.SearchUser(session, item);
        }
    }

    public static void SaveUser(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveUser(session, item);
            break;
        }
    }

    public static List<SelectUser> SelectAllUser() {
        SqlSession session = DBUtils.getFactory();

        try {
            return SelectAllUser(session);
        } catch (Exception e) {
            return new ArrayList<SelectUser>();
        } finally {
            session.close();
        }
    }

    public static List<SelectUser> SelectAllUser(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SelectUser>();

        default:
            return SqlUserDao.SelectAllUser(session);
        }
    }

    public static List<SysUser> GetListUserByDept(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserByDept(session, item);
        } catch (Exception e) {
            return new ArrayList<SysUser>();
        } finally {
            session.close();
        }
    }

    public static List<SysUser> GetListUserByDept(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysUser>();
        default:
            return SqlUserDao.GetListUserByDept(session, item);
        }
    }

    // endregion User Methods

    // region Role Methods

    public static List<SysRole> GetListRole() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListRole(session);
        } catch (Exception e) {
            return new ArrayList<SysRole>();
        } finally {
            session.close();
        }
    }

    public static List<SysRole> GetListRole(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysRole>();

        default:
            return SqlUserDao.GetListRole(session);
        }
    }

    public static List<SysRole> GetSetRole(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSetRole(session, item);
        } catch (Exception e) {
            return new ArrayList<SysRole>();
        } finally {
            session.close();
        }
    }

    public static List<SysRole> GetSetRole(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysRole>();

        default:
            return SqlUserDao.GetSetRole(session, item);
        }
    }

    public static List<SysRoleDetail> GetRoleDetail(SysRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetRoleDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<SysRoleDetail>();
        } finally {
            session.close();
        }
    }

    public static List<SysRoleDetail> GetRoleDetail(SqlSession session, SysRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysRoleDetail>();

        default:
            return SqlUserDao.GetRoleDetail(session, item);
        }
    }

    public static void SaveRole(SqlSession session, SysRole role, List<SysRoleDetail> roledetail) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveRole(session, role, roledetail);
            break;
        }
    }

    public static void SaveSetRole(SqlSession session, SysUser user, List<SysRole> setroles) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveSetRole(session, user, setroles);
            break;
        }
    }

    public static void EndSetRole(SqlSession session, SysUser user) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.EndSetRole(session, user);
            break;
        }
    }

    public static List<SysUserRole> GetListSysRoleByRole(SysUserRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListSysRoleByRole(session, item);
        } catch (Exception e) {
            return new ArrayList<SysUserRole>();
        } finally {
            session.close();
        }
    }

    public static List<SysUserRole> GetListSysRoleByRole(SqlSession session, SysUserRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysUserRole>();
        default:
            return SqlUserDao.GetListSysRoleByRole(session, item);
        }
    }

    // endregion Role Methods

    // region User Menu Methods

    public static List<SysMenuGroup> GetMenuGroupByUser(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            SysUser item = new SysUser();
            item.setUserid(userid);

            return GetMenuGroupByUser(session, item);
        } catch (Exception e) {
            return new ArrayList<SysMenuGroup>();
        } finally {
            session.close();
        }
    }

    public static List<SysMenuGroup> GetMenuGroupByUser(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysMenuGroup>();

        default:
            return SqlUserDao.GetMenuGroupByUser(session, item);
        }
    }

    public static List<SysMenu> GetMenuByUser(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            SysUser item = new SysUser();
            item.setUserid(userid);

            return GetMenuByUser(session, item);
        } catch (Exception e) {
            return new ArrayList<SysMenu>();
        } finally {
            session.close();
        }
    }

    public static List<SysMenu> GetMenuByUser(SqlSession session, SysUser item) {

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysMenu>();

        default:
            return SqlUserDao.GetMenuByUser(session, item);
        }
    }

    public static List<SysMenu> GetListMenu(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysMenu>();

        default:
            return SqlUserDao.GetListMenu(session);
        }
    }

    public static void SaveMenuDetial(SqlSession session, SysMenuDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveMenuDetial(session, item);
            break;
        }
    }

    public static void LastCreateMenuDetail(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.LastCreateMenuDetail(session);
            break;
        }
    }

    // endregion User Menu Mehtods

    // region User Dept Methods

    public static List<SysDept> GetUserDept(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserDept(session, item);
        } catch (Exception e) {
            return new ArrayList<SysDept>();
        } finally {
            session.close();
        }
    }

    public static List<SysDept> GetUserDept(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysDept>();

        default:
            return SqlUserDao.GetUserDept(session, item);
        }
    }

    public static void SaveUserDept(SqlSession session, SysUser item, List<SysDept> detps) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveUserDept(session, item, detps);
            break;
        }
    }

    // endregion User Dept Methods

    // region WorkGroup Methods

    public static List<SysWorkGroup> SearchWorkGroup(SysWorkGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchWorkGroup(session, item);
        } catch (Exception e) {
            return new ArrayList<SysWorkGroup>();
        } finally {
            session.close();
        }
    }

    public static List<SysWorkGroup> SearchWorkGroup(SqlSession session, SysWorkGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysWorkGroup>();

        default:
            return SqlUserDao.SearchWorkGroup(session, item);
        }
    }

    public static List<SysWorkGroup> GetSetWorkGroup(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSetWorkGroup(session, item);
        } catch (Exception e) {
            return new ArrayList<SysWorkGroup>();
        } finally {
            session.close();
        }
    }

    public static List<SysWorkGroup> GetSetWorkGroup(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysWorkGroup>();

        default:
            return SqlUserDao.GetSetWorkGroup(session, item);
        }
    }

    public static void SaveWorkGroup(SqlSession session, SysWorkGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveWorkGroup(session, item);
            break;
        }
    }

    public static void SaveSetWorkGroup(SqlSession session, SysUser user, List<SysWorkGroup> sets) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlUserDao.SaveSetWorkGroup(session, user, sets);
            break;
        }
    }

    // endregion WorkGroup Methods

}
