package com.gpersist.dao.sqlserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.lab.BusConsign;
import com.gpersist.entity.org.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class SqlUserDao {

    // region User Methods

    public static SysUser GetUser(SqlSession session, SysUser item) {
        item.getItem().setGetaction(ActionGetType.row.toString());

        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetUser(item);
    }

    public static void UserLock(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);
        item.getDeal().setProcedurename("P_User_Lock");
        mapper.UserPublic(item);
    }

    public static void UserError(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);
        item.getDeal().setProcedurename("P_User_Error");
        mapper.UserPublic(item);
    }

    public static void UserLoginLog(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.UserLoginLog(item);
    }

    public static void UserLoginOutLog(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);
        item.getDeal().setProcedurename("P_User_LoginOutLog");
        mapper.UserPublic(item);
    }

    public static void ChangePwd(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.ChangePwd(item);
    }

    public static String GetUserName(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetUserName(item);
    }

    public static Integer GetEduCounts(SqlSession session, String useredu) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetEduCounts(useredu);
    }

    public static Integer GetAgeCounts(SqlSession session, String begin, String end) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetAgeCounts(begin, end);
    }

    public static Integer GetDutyCounts(SqlSession session, String userduty) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetDutyCounts(userduty);
    }

    public static void SetUnLock(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SetUnLock(item);
    }

    public static void SetPasswd(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SetPasswd(item);
    }

    public static List<SysUser> SearchUser(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.SearchUser(item);
    }

    public static void SaveUser(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SaveUser(item);
    }

    public static List<SelectUser> SelectAllUser(SqlSession session) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.SelectAllUser();
    }

    public static List<SysUser> GetListUserByDept(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);
        return mapper.GetListUserByDept(item);
    }

    // endregion User Methods

    // region Role Methods

    public static List<SysRole> GetListRole(SqlSession session) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetListRole();
    }

    public static List<SysRole> GetSetRole(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetSetRole(item);
    }

    public static List<SysRoleDetail> GetRoleDetail(SqlSession session, SysRole item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetRoleDetail(item);
    }

    public static void SaveRole(SqlSession session, SysRole role, List<SysRoleDetail> roledetail) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SaveRole(role);

        SysRoleAuth auths = new SysRoleAuth();
        auths.setRoleid(role.getRoleid());
        auths.getDeal().setAction(4);
        mapper.SaveRoleDetail(auths);

        // 保存角色授权明细
        if (roledetail.size() > 0) {
            for (SysRoleDetail item : roledetail) {
                auths.setRoleid(role.getRoleid());
                auths.setMid(item.getMid());
                auths.setRoleauth(item.GetAuth());
                auths.getDeal().setAction(DataAction.Create.getAction());
                mapper.SaveRoleDetail(auths);
            }
        }
    }

    public static void SaveSetRole(SqlSession session, SysUser user, List<SysRole> setroles) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        // 删除角色分配明细
        SysUserRole userrole = new SysUserRole();
        userrole.setUserid(user.getUserid());
        userrole.getDeal().setAction(DataAction.Delete.getAction());
        mapper.SaveSetRole(userrole);

        // 保存角色分配明细
        if (setroles.size() > 0) {
            for (SysRole item : setroles) {
                userrole.setUserid(user.getUserid());
                userrole.setRoleid(item.getRoleid());
                userrole.getDeal().setAction(DataAction.Create.getAction());
                mapper.SaveSetRole(userrole);
            }
        }
    }

    public static void EndSetRole(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.EndSetRole(item);
    }

    public static List<SysUserRole> GetListSysRoleByRole(SqlSession session, SysUserRole item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetListSysRoleByRole(item);
    }

    // endregion Role Methods

    // region Menu Mehtods

    public static List<SysMenuGroup> GetMenuGroupByUser(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetMenuGroupByUser(item);
    }

    public static List<SysMenu> GetMenuByUser(SqlSession session, SysUser user) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        List<SysMenu> menus = mapper.GetMenuByUser(user);

        List<SysMenu> rtn = new ArrayList<SysMenu>();

        boolean isExist = false;

        for (SysMenu menu : menus) {
            isExist = false;

            for (SysMenu item : rtn) {
                if (item.getMid() == menu.getMid()) {
                    item.setMauth(item.getMauth() | menu.getMauth());
                    isExist = true;
                    break;
                }
            }

            if (!isExist)
                rtn.add(menu);
        }

        return rtn;
    }

    public static List<SysMenu> GetListMenu(SqlSession session) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetListMenu();
    }

    public static void SaveMenuDetial(SqlSession session, SysMenuDetail item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SaveMenuDetial(item);
    }

    public static void LastCreateMenuDetail(SqlSession session) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.LastCreateMenuDetail();
    }

    // endregion Menu Methods

    // region User Dept Methods

    public static List<SysDept> GetUserDept(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetUserDept(item);
    }

    public static void SaveUserDept(SqlSession session, SysUser item, List<SysDept> detps) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        SysUser user = new SysUser();
        user.setUserid(item.getUserid());
        user.getDeal().setAction(DataAction.Delete.getAction());
        mapper.SaveUserDept(user);

        if (detps.size() > 0) {
            for (SysDept dept : detps) {
                user.setDeptid(dept.getDeptid());
                user.getDeal().setAction(DataAction.Create.getAction());
                mapper.SaveUserDept(user);
            }
        }
    }

    // endregion User Dept Methods

    // region WorkGroup Methods

    public static List<SysWorkGroup> SearchWorkGroup(SqlSession session, SysWorkGroup item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.SearchWorkGroup(item);
    }

    public static void SaveWorkGroup(SqlSession session, SysWorkGroup item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        mapper.SaveWorkGroup(item);
    }

    public static List<SysWorkGroup> GetSetWorkGroup(SqlSession session, SysUser item) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        return mapper.GetSetWorkGroup(item);
    }

    public static void SaveSetWorkGroup(SqlSession session, SysUser user, List<SysWorkGroup> sets) {
        com.gpersist.mapper.sqlserver.UserMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.UserMapper.class);

        SysUserWorkGroup wg = new SysUserWorkGroup();
        wg.setUserid(user.getUserid());
        wg.getDeal().setAction(DataAction.Delete.getAction());
        mapper.SaveSetWorkGroup(wg);

        if (sets.size() > 0) {
            for (SysWorkGroup item : sets) {
                wg.setUserid(user.getUserid());
                wg.setWorkgroup(item.getWorkgroup());
                wg.getDeal().setAction(DataAction.Create.getAction());
                mapper.SaveSetWorkGroup(wg);
            }
        }
    }

    // endregion WorkGroup Methods

}
