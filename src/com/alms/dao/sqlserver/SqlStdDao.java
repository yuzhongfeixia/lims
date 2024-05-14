package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.std.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlStdDao {

    // region StdReviewDetail Methods

    public static List<StdReviewDetail> GetListStdReviewDetail(SqlSession session, StdReviewDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdReviewDetail(item);
    }

    public static List<StdReviewDetail> GetStdReviewDetailForChange(SqlSession session, StdReviewDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetStdReviewDetailForChange(item);
    }

    public static void SaveStdReviewDetail(SqlSession session, StdReviewDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdReviewDetail(item);
    }

    // endregion StdReviewDetail Methods

    // region StdReview Methods

    public static StdReview GetStdReview(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdReview(item);
    }

    public static StdReview GetBusCountry(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusCountry(item);
    }

    public static List<StdReview> GetListStdReview(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdReview(item);
    }

    public static List<StdReview> SearchStdReview(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdReview(item);
    }

    public static List<StdReview> SearchBusCountry(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchBusCountry(item);
    }

    public static List<StdReview> SearchStdReviewForChange(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdReviewForChange(item);
    }

    public static void SaveStdReview(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdReview(item);
    }

    public static void SaveBusCountry(SqlSession session, StdReview item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveBusCountry(item);
    }

    // endregion StdReview Methods

    // region StdUse Methods

    public static StdUse GetStdUse(SqlSession session, StdUse item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdUse(item);
    }

    public static List<StdUse> GetListStdUse(SqlSession session, StdUse item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdUse(item);
    }

    public static List<StdUse> GetListStdUseBySampletran(SqlSession session, StdUse item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdUseBySampletran(item);
    }

    public static List<StdUse> SearchStdUse(SqlSession session, StdUse item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdUse(item);
    }

    public static void SaveStdUse(SqlSession session, StdUse item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdUse(item);
    }

    // endregion StdUse Methods

    // region StdNonstd Methods

    public static StdNonstd GetStdNonstd(SqlSession session, StdNonstd item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdNonstd(item);
    }

    public static List<StdNonstd> GetListStdNonstd(SqlSession session, StdNonstd item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdNonstd(item);
    }

    public static List<StdNonstd> SearchStdNonstd(SqlSession session, StdNonstd item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdNonstd(item);
    }

    public static void SaveStdNonstd(SqlSession session, StdNonstd item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdNonstd(item);
    }

    // endregion StdNonstd Methods

    // region StdMethodDevi Methods

    public static StdMethodDevi GetStdMethodDevi(SqlSession session, StdMethodDevi item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdMethodDevi(item);
    }

    public static List<StdMethodDevi> GetListStdMethodDevi(SqlSession session, StdMethodDevi item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdMethodDevi(item);
    }

    public static List<StdMethodDevi> SearchStdMethodDevi(SqlSession session, StdMethodDevi item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdMethodDevi(item);
    }

    public static void SaveStdMethodDevi(SqlSession session, StdMethodDevi item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdMethodDevi(item);
    }

    // endregion StdMethodDevi Methods

    // region StdProApply Methods

    public static StdProApply GetStdProApply(SqlSession session, StdProApply item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdProApply(item);
    }

    public static List<StdProApply> GetListStdProApply(SqlSession session, StdProApply item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdProApply(item);
    }

    public static List<StdProApply> SearchStdProApply(SqlSession session, StdProApply item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdProApply(item);
    }

    public static void SaveStdProApply(SqlSession session, StdProApply item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdProApply(item);
    }

    // endregion StdProApply Methods

    // region StdTestSure Methods

    public static StdTestSure GetStdTestSure(SqlSession session, StdTestSure item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdTestSure(item);
    }

    public static List<StdTestSure> GetListStdTestSure(SqlSession session, StdTestSure item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdTestSure(item);
    }

    public static List<StdTestSure> SearchStdTestSure(SqlSession session, StdTestSure item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdTestSure(item);
    }

    public static void SaveStdTestSure(SqlSession session, StdTestSure item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdTestSure(item);
    }

    // endregion StdTestSure Methods

    // region StdReplRecord Methods

    public static StdReplRecord GetStdReplRecord(SqlSession session, StdReplRecord item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdReplRecord(item);
    }

    public static List<StdReplRecord> GetListStdReplRecord(SqlSession session, StdReplRecord item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdReplRecord(item);
    }

    public static List<StdReplRecord> SearchStdReplRecord(SqlSession session, StdReplRecord item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdReplRecord(item);
    }

    public static void SaveStdReplRecord(SqlSession session, StdReplRecord item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdReplRecord(item);
    }

    // endregion StdReplRecord Methods

    // region StdChange Methods

    public static StdChange GetStdChange(SqlSession session, StdChange item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdChange(item);
    }

    public static List<StdChange> GetListStdChange(SqlSession session, StdChange item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdChange(item);
    }

    public static List<StdChange> SearchStdChange(SqlSession session, StdChange item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdChange(item);
    }

    public static void SaveStdChange(SqlSession session, StdChange item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdChange(item);
    }

    // endregion StdChange Methods

    // region StdSureDetail Methods

    public static StdSureDetail GetStdSureDetail(SqlSession session, StdSureDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetStdSureDetail(item);
    }

    public static List<StdSureDetail> GetListStdSureDetail(SqlSession session, StdSureDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.GetListStdSureDetail(item);
    }

    public static List<StdSureDetail> SearchStdSureDetail(SqlSession session, StdSureDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        return mapper.SearchStdSureDetail(item);
    }

    public static void SaveStdSureDetail(SqlSession session, StdSureDetail item) {
        com.alms.mapper.sqlserver.StdMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StdMapper.class);

        mapper.SaveStdSureDetail(item);
    }

    // endregion StdSureDetail Methods

}
