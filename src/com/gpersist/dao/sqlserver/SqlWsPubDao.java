package com.gpersist.dao.sqlserver;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.gpersist.webservice.entity.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;

public class SqlWsPubDao {

    // region WsApp Methods

    public static WsApp GetWsApp(SqlSession session, WsApp item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetWsApp(item);
    }

    public static List<WsApp> GetListWsApp(SqlSession session) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);

        return mapper.GetListWsApp();
    }

    public static List<WsApp> SearchWsApp(SqlSession session, WsApp item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);

        return mapper.SearchWsApp(item);
    }

    public static void SaveWsApp(SqlSession session, WsApp item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);

        mapper.SaveWsApp(item);
    }

    // endregion WsApp Methods

    // region WsVisit Methods

    public static WsVisit GetWsVisit(SqlSession session, WsVisit item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetWsVisit(item);
    }

    public static List<WsVisit> SearchWsVisit(SqlSession session, WsVisit item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);

        return mapper.SearchWsVisit(item);
    }

    public static void SaveWsVisit(SqlSession session, WsVisit item) {
        com.gpersist.mapper.sqlserver.WsPubMapper mapper = DBUtils.getMapper(session,
                com.gpersist.mapper.sqlserver.WsPubMapper.class);

        mapper.SaveWsVisit(item);
    }

    // endregion WsVisit Methods

}
