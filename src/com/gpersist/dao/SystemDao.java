package com.gpersist.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.sqlserver.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SystemDao {

    // region Set Methods

    public static List<SysSet> GetSetList(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysSet>();

        default:
            return SqlSystemDao.GetListSet(session);
        }
    }

    // endregion Set Methods

    // region Pmt Methods

    public static List<PmtBean> GetPmtSelect(String pmtname) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPmtSelect(session, pmtname);
        } finally {
            session.close();
        }
    }

    public static List<PmtBean> GetPmtSelect(SqlSession session, String pmtname) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PmtBean>();

        default:
            return SqlSystemDao.GetPmtSelect(session, pmtname);
        }
    }

    public static SysPmt GetPmt(short pmtid) {
        SqlSession session = DBUtils.getFactory();

        try {
            SysPmt item = new SysPmt();
            item.setPmtid(pmtid);

            return GetPmt(session, item);
        } finally {
            session.close();
        }
    }

    public static SysPmt GetPmt(SqlSession session, SysPmt item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SysPmt();

        default:
            return SqlSystemDao.GetPmt(session, item);
        }
    }

    public static List<SysPmt> GetListPmt(String pmttype) {
        SqlSession session = DBUtils.getFactory();

        try {
            SysPmt item = new SysPmt();
            item.setPmttype(pmttype);

            return GetListPmt(session, item);
        } finally {
            session.close();
        }
    }

    public static List<SysPmt> GetListPmt(SqlSession session, SysPmt item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SysPmt>();

        default:
            return SqlSystemDao.GetListPmt(session, item);
        }
    }

    public static List<ShortPmt> ShortPmtByTable(short pmtid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ShortPmt>();

        default:
            return SqlSystemDao.ShortPmtByTable(pmtid);
        }
    }

    public static List<IntPmt> IntPmtByTable(short pmtid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<IntPmt>();

        default:
            return SqlSystemDao.IntPmtByTable(pmtid);
        }
    }

    public static List<StringPmt> StringPmtByTable(short pmtid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StringPmt>();

        default:
            return SqlSystemDao.StringPmtByTable(pmtid);
        }
    }

    // endregion Pmt Methods

    // region Log Methods

    public static List<TranLog> SearchTranLog(TranLog item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<TranLog>();

            default:
                return SqlSystemDao.SearchTranLog(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<TranLog>();
        } finally {
            session.close();
        }
    }

    public static List<LoginLog> SearchLoginLog(LoginLog item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<LoginLog>();

            default:
                return SqlSystemDao.SearchLoginLog(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<LoginLog>();
        } finally {
            session.close();
        }
    }

    // endregion Log Methods

    // region Column Methods

    public static List<JsonSqlColumn> SqlColumn(String sqlid) {
        SqlSession session = DBUtils.getFactory();

        try {
            JsonSqlColumn item = new JsonSqlColumn();
            item.setSqlid(sqlid);

            return SqlColumn(session, item);

        } catch (Exception e) {
            return new ArrayList<JsonSqlColumn>();
        } finally {
            session.close();
        }
    }

    public static List<JsonSqlColumn> SqlColumn(SqlSession session, JsonSqlColumn item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<JsonSqlColumn>();

        default:
            return SqlSystemDao.SqlColumn(session, item);
        }
    }

    // endregion Column Methods

    // region GetValue Methods

    public static String GetStringValue(String sql) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return "";

            default:
                String rtn = SqlSystemDao.GetStringValue(session, sql);

                if (!ToolUtils.StringIsEmpty(rtn))
                    return rtn;
            }
        } catch (Exception e) {

        } finally {
            session.close();
        }

        return "";
    }

    public static String GetStringValue(SqlSession session, String sql) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return "";

        default:
            String rtn = SqlSystemDao.GetStringValue(session, sql);

            if (!ToolUtils.StringIsEmpty(rtn))
                return rtn;
        }

        return "";
    }

    public static int GetIntValue(String sql) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return 0;

            default:
                return SqlSystemDao.GetIntValue(session, sql);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public static int GetIntValue(SqlSession session, String sql) {
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return 0;

            default:
                return SqlSystemDao.GetIntValue(session, sql);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static Short GetShortValue(String sql) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return 0;

            default:
                return SqlSystemDao.GetShortValue(session, sql);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public static Short GetShortValue(SqlSession session, String sql) {
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return 0;

            default:
                return SqlSystemDao.GetShortValue(session, sql);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    // endregion GetValue Methods

}
