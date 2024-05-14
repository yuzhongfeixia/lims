package com.gpersist.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.sqlserver.*;

import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class OrgDao {

    // region Dept Methods

    public static SysDept GetDept(String deptid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDept(session, deptid);
        } catch (Exception e) {
            return new SysDept();
        } finally {
            session.close();
        }
    }

    public static SysDept GetDept(SqlSession session, String deptid) {

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SysDept();

        default:
            return SqlOrgDao.GetDept(session, deptid);
        }
    }

    public static List<SysDept> GetListDept() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDept(session);
        } catch (Exception e) {
            return new ArrayList<SysDept>();
        } finally {
            session.close();
        }
    }

    public static List<SysDept> GetListDept(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysDept>();

        default:
            return SqlOrgDao.GetListDept(session);
        }
    }

    public static List<SysDept> GetListDeptByCo(String coid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDeptByCo(session, coid);
        } catch (Exception e) {
            return new ArrayList<SysDept>();
        } finally {
            session.close();
        }
    }

    public static List<SysDept> GetListDeptByCo(SqlSession session, String coid) {
        SysDept dept = new SysDept();
        dept.setCoid(coid);

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysDept>();

        default:
            return SqlOrgDao.GetListDeptByCo(session, dept);
        }
    }

    public static List<SysDept> SearchDept(SysDept item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchDept(session, item);
        } catch (Exception e) {
            return new ArrayList<SysDept>();
        } finally {
            session.close();
        }
    }

    public static List<SysDept> SearchDept(SqlSession session, SysDept item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysDept>();

        default:
            return SqlOrgDao.SearchDept(session, item);
        }
    }

    public static void SaveDept(SqlSession session, SysDept item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlOrgDao.SaveDept(session, item);
            break;
        }
    }

    public static List<PmtBean> GetDeptByUser(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDeptByUser(session, userid);
        } catch (Exception e) {
            return new ArrayList<PmtBean>();
        } finally {
            session.close();
        }
    }

    public static List<PmtBean> GetDeptByUser(SqlSession session, String userid) {

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PmtBean>();

        default:
            return SqlOrgDao.GetDeptByUser(session, userid);
        }
    }

    // endregion Dept Methods

    // region Company Methods

    public static SysCompany GetCompany(String coid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCompany(session, coid);
        } catch (Exception e) {
            return new SysCompany();
        } finally {
            session.close();
        }
    }

    public static SysCompany GetCompany(SqlSession session, String coid) {

        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SysCompany();

        default:
            return SqlOrgDao.GetCompany(session, coid);
        }
    }

    public static List<SysCompany> GetListCompany() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListCompany(session);
        } catch (Exception e) {
            return new ArrayList<SysCompany>();
        } finally {
            session.close();
        }
    }

    public static List<SysCompany> GetListCompany(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysCompany>();

        default:
            return SqlOrgDao.GetListCompany(session);
        }
    }

    public static void SaveCompany(SqlSession session, SysCompany item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlOrgDao.SaveCompany(session, item);
            break;
        }
    }

    // endregion Company Methods
}
