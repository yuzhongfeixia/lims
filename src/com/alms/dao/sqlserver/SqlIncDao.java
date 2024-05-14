package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.inc.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlIncDao {

    // region IncTestEnv Methods

    public static IncTestEnv GetIncTestEnv(SqlSession session, IncTestEnv item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncTestEnv(item);
    }

    public static List<IncTestEnv> SearchIncTestEnv(SqlSession session, IncTestEnv item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);

        return mapper.SearchIncTestEnv(item);
    }

    public static void SaveIncTestEnv(SqlSession session, IncTestEnv item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);

        mapper.SaveIncTestEnv(item);
    }

    // endregion IncTestEnv Methods

    // region IncCheckSafe Methods

    public static IncCheckSafe GetIncCheckSafe(SqlSession session, IncCheckSafe item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIncCheckSafe(item);
    }

    public static List<IncCheckSafe> SearchIncCheckSafe(SqlSession session, IncCheckSafe item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);

        return mapper.SearchIncCheckSafe(item);
    }

    public static void SaveIncCheckSafe(SqlSession session, IncCheckSafe item) {
        com.alms.mapper.sqlserver.IncMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.IncMapper.class);

        mapper.SaveIncCheckSafe(item);
    }

    // endregion IncCheckSafe Methods

}
