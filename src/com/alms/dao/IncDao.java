package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlIncDao;
import com.alms.entity.inc.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class IncDao {

    // region IncTestEnv Methods

    public static IncTestEnv GetIncTestEnv(IncTestEnv item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncTestEnv(session, item);
        } catch (Exception e) {
            return new IncTestEnv();
        } finally {
            session.close();
        }
    }

    public static IncTestEnv GetIncTestEnv(SqlSession session, IncTestEnv item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncTestEnv();
        default:
            return SqlIncDao.GetIncTestEnv(session, item);
        }
    }

    public static List<IncTestEnv> SearchIncTestEnv(IncTestEnv item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncTestEnv>();
            default:
                return SqlIncDao.SearchIncTestEnv(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncTestEnv>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncTestEnv(SqlSession session, IncTestEnv item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncDao.SaveIncTestEnv(session, item);
            break;
        }
    }

    // endregion IncTestEnv Methods

    // region IncCheckSafe Methods

    public static IncCheckSafe GetIncCheckSafe(IncCheckSafe item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIncCheckSafe(session, item);
        } catch (Exception e) {
            return new IncCheckSafe();
        } finally {
            session.close();
        }
    }

    public static IncCheckSafe GetIncCheckSafe(SqlSession session, IncCheckSafe item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IncCheckSafe();
        default:
            return SqlIncDao.GetIncCheckSafe(session, item);
        }
    }

    public static List<IncCheckSafe> SearchIncCheckSafe(IncCheckSafe item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IncCheckSafe>();
            default:
                return SqlIncDao.SearchIncCheckSafe(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IncCheckSafe>();
        } finally {
            session.close();
        }
    }

    public static void SaveIncCheckSafe(SqlSession session, IncCheckSafe item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlIncDao.SaveIncCheckSafe(session, item);
            break;
        }
    }

    // endregion IncCheckSafe Methods

}
