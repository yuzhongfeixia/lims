package com.gpersist.dao.sqlserver;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class SqlOrgDao {

    // region Dept Methods

    public static SysDept GetDept(SqlSession session, String deptid) {
        SysDept item = new SysDept();

        item.setDeptid(deptid);
        item.getItem().setGetaction(ActionGetType.row.toString());

        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetDept(item);
    }

    public static List<SysDept> GetListDept(SqlSession session) {
        SysDept item = new SysDept();
        item.getItem().setGetaction(ActionGetType.full.toString());

        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetListDept(item);
    }

    public static List<SysDept> GetListDeptByCo(SqlSession session, SysDept item) {
        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetListDeptByCo(item);
    }

    public static List<SysDept> SearchDept(SqlSession session, SysDept item) {
        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.SearchDept(item);
    }

    public static void SaveDept(SqlSession session, SysDept dept) {
        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        mapper.SaveDept(dept);
    }

    public static List<PmtBean> GetDeptByUser(SqlSession session, String userid) {
        SysDept item = new SysDept();

        item.setDeptid(userid);

        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetDeptByUser(item);
    }

    // endregion Dept Methods

    // region Company Methods

    public static SysCompany GetCompany(SqlSession session, String coid) {
        SysCompany item = new SysCompany();

        item.setCoid(coid);
        item.getItem().setGetaction(ActionGetType.row.toString());

        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetCompany(item);
    }

    public static List<SysCompany> GetListCompany(SqlSession session) {
        SysCompany item = new SysCompany();
        item.getItem().setGetaction(ActionGetType.full.toString());

        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        return mapper.GetListCompany(item);
    }

    public static void SaveCompany(SqlSession session, SysCompany co) {
        com.gpersist.mapper.sqlserver.OrgMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.OrgMapper.class);

        mapper.SaveCompany(co);
    }

    // endregion Company Methods
}
