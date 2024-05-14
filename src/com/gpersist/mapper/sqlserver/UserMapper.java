package com.gpersist.mapper.sqlserver;

import java.util.Date;
import java.util.List;

import com.gpersist.entity.org.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.mapper.BaseMapper;

public interface UserMapper extends BaseMapper {

    // region User Methods

    public SysUser GetUser(SysUser item);

    public void UserPublic(SysUser item);

    public void UserLoginLog(SysUser item);

    public List<SysUser> SearchUser(SysUser item);

    public void SaveUser(SysUser item);

    public String GetUserName(SysUser item);

    public Integer GetEduCounts(String useredu);

    public Integer GetAgeCounts(String begin, String end);

    public Integer GetDutyCounts(String userduty);

    public void SetPasswd(SysUser item);

    public void SetUnLock(SysUser item);

    public void ChangePwd(SysUser item);

    public List<SelectUser> SelectAllUser();

    public List<SysUser> GetListUserByDept(SysUser item);

    // endregion User Methods

    // region Role Methods

    public List<SysRole> GetListRole();

    public List<SysRole> GetSetRole(SysUser item);

    public List<SysRoleDetail> GetRoleDetail(SysRole item);

    public void SaveRole(SysRole item);

    public void SaveRoleDetail(SysRoleAuth item);

    public void SaveSetRole(SysUserRole item);

    public void EndSetRole(SysUser item);

    public List<SysUserRole> GetListSysRoleByRole(SysUserRole item);

    // endregion Role Methods

    // region User Menu Methods

    public List<SysMenuGroup> GetMenuGroupByUser(SysUser item);

    public List<SysMenu> GetMenuByUser(SysUser item);

    public List<SysMenu> GetListMenu();

    public void SaveMenuDetial(SysMenuDetail item);

    public void LastCreateMenuDetail();

    // endregion User Menu Methods

    // region User Dept Methods

    public List<SysDept> GetDeptByUser(SysUser item);

    public List<SysDept> GetUserDept(SysUser item);

    public void SaveUserDept(SysUser item);

    // endregion User Dept Methods

    // region WorkGroup Methods

    public List<SysWorkGroup> SearchWorkGroup(SysWorkGroup item);

    public void SaveWorkGroup(SysWorkGroup item);

    public List<SysWorkGroup> GetSetWorkGroup(SysUser item);

    public void SaveSetWorkGroup(SysUserWorkGroup item);

    // endregion WorkGroup Methods

}
