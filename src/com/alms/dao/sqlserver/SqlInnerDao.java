package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.inner.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlInnerDao {

    // region InnerFoodReview Methods

    public static InnerFoodReview GetInnerFoodReview(SqlSession session, InnerFoodReview item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerFoodReview(item);
    }

    public static List<InnerFoodReview> SearchInnerFoodReview(SqlSession session, InnerFoodReview item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerFoodReview(item);
    }

    public static void SaveInnerFoodReview(SqlSession session, InnerFoodReview item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerFoodReview(item);
    }

    // endregion InnerFoodReview Methods

    // region InnerMeetPart Methods

    public static List<InnerMeetPart> GetListInnerMeetPart(SqlSession session, InnerMeetPart item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.GetListInnerMeetPart(item);
    }

    public static void SaveInnerMeetPart(SqlSession session, InnerMeetPart item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerMeetPart(item);
    }

    // endregion InnerMeetPart Methods

    // region InnerMeetSign Methods

    public static InnerMeetSign GetInnerMeetSign(SqlSession session, InnerMeetSign item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerMeetSign(item);
    }

    public static List<InnerMeetSign> SearchInnerMeetSign(SqlSession session, InnerMeetSign item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerMeetSign(item);
    }

    public static void SaveInnerMeetSign(SqlSession session, InnerMeetSign item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerMeetSign(item);
    }

    // endregion InnerMeetSign Methods

    // region InnerReport Methods

    public static InnerReport GetInnerReport(SqlSession session, InnerReport item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerReport(item);
    }

    public static List<InnerReport> SearchInnerReport(SqlSession session, InnerReport item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerReport(item);
    }

    public static void SaveInnerReport(SqlSession session, InnerReport item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerReport(item);
    }

    // endregion InnerReport Methods

    // region InnerInvalid Methods

    public static InnerInvalid GetInnerInvalid(SqlSession session, InnerInvalid item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerInvalid(item);
    }

    public static List<InnerInvalid> SearchInnerInvalid(SqlSession session, InnerInvalid item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerInvalid(item);
    }

    public static void SaveInnerInvalid(SqlSession session, InnerInvalid item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerInvalid(item);
    }

    // endregion InnerInvalid Methods

    // region InnerSceneWork Methods

    public static InnerSceneWork GetInnerSceneWork(SqlSession session, InnerSceneWork item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerSceneWork(item);
    }

    public static List<InnerSceneWork> GetListInnerSceneWork(SqlSession session, InnerSceneWork item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.GetListInnerSceneWork(item);
    }

    public static List<InnerSceneWork> SearchInnerSceneWork(SqlSession session, InnerSceneWork item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerSceneWork(item);
    }

    public static void SaveInnerSceneWork(SqlSession session, InnerSceneWork item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerSceneWork(item);
    }

    // endregion InnerSceneWork Methods

    // region InnerScene Methods

    public static InnerScene GetInnerScene(SqlSession session, InnerScene item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerScene(item);
    }

    public static List<InnerScene> SearchInnerScene(SqlSession session, InnerScene item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerScene(item);
    }

    public static void SaveInnerScene(SqlSession session, InnerScene item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerScene(item);
    }

    // endregion InnerScene Methods

    // region InnerGroupMember Methods

    public static List<InnerGroupMember> GetListInnerGroupMember(SqlSession session, InnerGroupMember item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.GetListInnerGroupMember(item);
    }

    public static void SaveInnerGroupMember(SqlSession session, InnerGroupMember item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerGroupMember(item);
    }

    // endregion InnerGroupMember Methods

    // region InnerGroup Methods

    public static InnerGroup GetInnerGroup(SqlSession session, InnerGroup item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerGroup(item);
    }

    public static List<InnerGroup> GetListInnerGroup(SqlSession session, InnerGroup item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.GetListInnerGroup(item);
    }

    public static List<InnerGroup> SearchInnerGroup(SqlSession session, InnerGroup item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerGroup(item);
    }

    public static void SaveInnerGroup(SqlSession session, InnerGroup item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerGroup(item);
    }

    // endregion InnerGroup Methods

    // region InnerYearDetail Methods

    public static List<InnerYearDetail> GetListInnerYearDetail(SqlSession session, InnerYearDetail item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.GetListInnerYearDetail(item);
    }

    public static void SaveInnerYearDetail(SqlSession session, InnerYearDetail item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerYearDetail(item);
    }

    // endregion InnerYearDetail Methods

    // region InnerYear Methods

    public static InnerYear GetInnerYear(SqlSession session, InnerYear item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetInnerYear(item);
    }

    public static List<InnerYear> SearchInnerYear(SqlSession session, InnerYear item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        return mapper.SearchInnerYear(item);
    }

    public static void SaveInnerYear(SqlSession session, InnerYear item) {
        com.alms.mapper.sqlserver.InnerMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.InnerMapper.class);

        mapper.SaveInnerYear(item);
    }

    // endregion InnerYear Methods

}
