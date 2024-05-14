package com.gpersist.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.sqlserver.*;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.publics.*;
import com.gpersist.entity.log.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;
import com.gpersist.mapper.*;

public class PublicDao {

    public static <T, X extends BaseMapper> T Get(Class<X> mapper, String id) {
        return Get(mapper, "", id);
    }

    public static <T, X extends BaseMapper> T Get(SqlSession session, Class<X> mapper, String id) {
        return Get(session, mapper, "", id);
    }

    public static <T, X extends BaseMapper> T Get(Class<X> mapper, String procedure, String id) {
        SqlSession session = DBUtils.getFactory();
        T rtn = null;

        try {
            rtn = Get(session, mapper, "", id);
        } finally {
            session.close();
        }

        return rtn;
    }

    public static <T, X extends BaseMapper> T Get(SqlSession session, Class<X> mapper, String procedure, String id) {
        SelectByString params = new SelectByString();

        params.setSelectid(id);
        params.setGetaction(ActionGetType.row.toString());
        params.setProcedurename(procedure);

        X getMapper = DBUtils.getMapper(session, mapper);

        T rtn = getMapper.Get(params);

        return rtn;
    }

    public static <T, X extends BaseMapper> T Get(Class<X> mapper, int id) {
        return Get(mapper, "", id);
    }

    public static <T, X extends BaseMapper> T Get(SqlSession session, Class<X> mapper, int id) {
        return Get(session, mapper, "", id);
    }

    public static <T, X extends BaseMapper> T Get(Class<X> mapper, String procedure, int id) {
        SqlSession session = DBUtils.getFactory();
        T rtn = null;

        try {
            rtn = Get(session, mapper, "", id);
        } finally {
            session.close();
        }

        return rtn;
    }

    public static <T, X extends BaseMapper> T Get(SqlSession session, Class<X> mapper, String procedure, int id) {
        SelectByInt params = new SelectByInt();

        params.setSelectid(id);
        params.setGetaction(ActionGetType.row.toString());
        params.setProcedurename(procedure);

        X getMapper = DBUtils.getMapper(session, mapper);

        T rtn = getMapper.Get(params);

        return rtn;
    }

    public static <T, X extends BaseMapper> List<T> GetList(Class<X> mapper, String procedure, String id) {
        SqlSession session = DBUtils.getFactory();
        List<T> rtn = new ArrayList<T>();

        try {
            rtn = GetList(session, mapper, "", id);
        } finally {
            session.close();
        }

        return rtn;
    }

    public static <T, X extends BaseMapper> List<T> GetList(SqlSession session, Class<X> mapper, String procedure,
            String id) {
        SelectByString params = new SelectByString();

        params.setSelectid(id);
        params.setGetaction(ActionGetType.list.toString());
        params.setProcedurename(procedure);

        X getMapper = DBUtils.getMapper(session, mapper);

        List<T> rtn = getMapper.GetList(params);

        return rtn;
    }

    public static void ExecSql(SqlSession session, String execsql) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSystemDao.ExecSql(session, execsql);
            break;
        }
    }

    public static void ExecSql(String sql, ReturnValue rtv) {
        SqlSession session = DBUtils.getFactory();

        rtv.setSuccess(false);

        try {

            ExecSql(session, sql);

            rtv.setSuccess(true);
            session.commit();
        } catch (Exception e) {
            // TODO: handle exception
            session.rollback();
            rtv.setMsg(e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void ExecSql(SqlSession session, List<String> sqls) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSystemDao.ExecSql(session, sqls);
            break;
        }
    }

    public static void ExecSql(List<String> sqls, ReturnValue rtv) {
        SqlSession session = DBUtils.getFactory();

        rtv.setSuccess(false);

        try {

            ExecSql(session, sqls);

            rtv.setSuccess(true);
            session.commit();
        } catch (Exception e) {
            // TODO: handle exception
            session.rollback();
            rtv.setMsg(e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void SaveTranLog(SqlSession session, TranLog log) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSystemDao.SaveTranLog(session, log);
            break;
        }
    }

}
