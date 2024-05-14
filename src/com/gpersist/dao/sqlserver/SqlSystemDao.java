package com.gpersist.dao.sqlserver;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.gpersist.entity.publics.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.log.*;
import com.gpersist.enums.*;
import com.gpersist.utils.DBUtils;

public class SqlSystemDao {

    // region Set Methods

    public static List<SysSet> GetListSet(SqlSession session) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.GetListSet();
    }

    // endregion Set Methods

    // region Log Methods

    public static void SaveTranLog(SqlSession session, TranLog log) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        mapper.SaveTranLog(log);
    }

    public static List<TranLog> SearchTranLog(SqlSession session, TranLog item) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.SearchTranLog(item);
    }

    public static List<LoginLog> SearchLoginLog(SqlSession session, LoginLog item) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.SearchLoginLog(item);
    }

    // endregion TranLog Methods

    // region Pmt Methods

    public static List<PmtBean> GetPmtSelect(SqlSession session, String pmtname) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        PmtBean item = new PmtBean();
        item.setName(pmtname);

        return mapper.GetPmtSelect(item);
    }

    public static SysPmt GetPmt(SqlSession session, SysPmt item) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetPmt(item);
    }

    public static List<SysPmt> GetListPmt(SqlSession session, SysPmt item) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        item.getItem().setGetaction(ActionGetType.listrows.toString());

        return mapper.GetListPmt(item);
    }

    public static List<ShortPmt> ShortPmtByTable(short pmtid) {
        SqlSession session = DBUtils.getFactory();

        try {
            com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                    com.gpersist.mapper.sqlserver.SystemMapper.class);

            return mapper.ShortPmtByTable(pmtid);
        } finally {
            session.close();
        }
    }

    public static List<IntPmt> IntPmtByTable(short pmtid) {
        SqlSession session = DBUtils.getFactory();

        try {
            com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                    com.gpersist.mapper.sqlserver.SystemMapper.class);

            return mapper.IntPmtByTable(pmtid);
        } finally {
            session.close();
        }
    }

    public static List<StringPmt> StringPmtByTable(short pmtid) {
        SqlSession session = DBUtils.getFactory();

        try {
            com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                    com.gpersist.mapper.sqlserver.SystemMapper.class);

            return mapper.StringPmtByTable(pmtid);
        } finally {
            session.close();
        }
    }

    public static void ExecSql(SqlSession session, List<String> sqls) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        for (String execsql : sqls) {
            mapper.ExecSQL(execsql);
        }
    }

    public static void ExecSql(SqlSession session, String execsql) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        mapper.ExecSQL(execsql);
    }

    // endregion Pmt Methods

    // region Column Methods

    public static List<JsonSqlColumn> SqlColumn(SqlSession session, JsonSqlColumn item) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.SqlColumn(item);
    }

    // endregion Column Methods

    // region GetValue Methods

    public static String GetStringValue(SqlSession session, String sql) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.GetStringValue(sql);
    }

    public static int GetIntValue(SqlSession session, String sql) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.GetIntValue(sql);
    }

    public static short GetShortValue(SqlSession session, String sql) {
        com.gpersist.mapper.sqlserver.SystemMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.SystemMapper.class);

        return mapper.GetShortValue(sql);
    }

    // endregion GetValue Methods
}
