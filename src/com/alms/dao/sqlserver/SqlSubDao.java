package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.sub.SubReview;
import com.alms.entity.sub.SubReviewDetail;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlSubDao {

    // region SubReview Methods

    public static SubReview GetSubReview(SqlSession session, SubReview item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSubReview(item);
    }

    public static List<SubReview> GetListSubReview(SqlSession session, SubReview item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        return mapper.GetListSubReview(item);
    }

    public static List<SubReview> SearchSubReview(SqlSession session, SubReview item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        return mapper.SearchSubReview(item);
    }

    public static void SaveSubReview(SqlSession session, SubReview item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        mapper.SaveSubReview(item);
    }

    // endregion SubReview Methods

    // region SubReviewDetail Methods

    public static SubReviewDetail GetSubReviewDetail(SqlSession session, SubReviewDetail item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSubReviewDetail(item);
    }

    public static List<SubReviewDetail> GetListSubReviewDetail(SqlSession session, SubReviewDetail item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        return mapper.GetListSubReviewDetail(item);
    }

    public static List<SubReviewDetail> SearchSubReviewDetail(SqlSession session, SubReviewDetail item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        return mapper.SearchSubReviewDetail(item);
    }

    public static void SaveSubReviewDetail(SqlSession session, SubReviewDetail item) {
        com.alms.mapper.sqlserver.SubMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SubMapper.class);

        mapper.SaveSubReviewDetail(item);
    }

    // endregion SubReviewDetail Methods

}