package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.crm.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlCrmDao {

    // region CrmAccidentDeal Methods

    public static CrmAccidentDeal GetCrmAccidentDeal(SqlSession session, CrmAccidentDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCrmAccidentDeal(item);
    }

    public static List<CrmAccidentDeal> SearchCrmAccidentDeal(SqlSession session, CrmAccidentDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmAccidentDeal(item);
    }

    public static List<CrmAccidentDeal> SearchCrmAccidentDealApproved(SqlSession session, CrmAccidentDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmAccidentDealApproved(item);
    }

    public static void SaveCrmAccidentDeal(SqlSession session, CrmAccidentDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmAccidentDeal(item);
    }

    // endregion CrmAccidentDeal Methods

    // region CrmSurveyItem Methods

    public static CrmSurveyItem GetCrmSurveyItem(SqlSession session, CrmSurveyItem item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCrmSurveyItem(item);
    }

    public static List<CrmSurveyItem> SearchCrmSurveyItem(SqlSession session, CrmSurveyItem item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmSurveyItem(item);
    }

    public static void SaveCrmSurveyItem(SqlSession session, CrmSurveyItem item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmSurveyItem(item);
    }

    // endregion CrmSurveyItem Methods

    // region CrmSurveyDetail Methods

    public static List<CrmSurveyDetail> GetListCrmSurveyDetail(SqlSession session, CrmSurveyDetail item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.GetListCrmSurveyDetail(item);
    }

    public static void SaveCrmSurveyDetail(SqlSession session, CrmSurveyDetail item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmSurveyDetail(item);
    }

    // endregion CrmSurveyDetail Methods

    // region CrmSurvey Methods

    public static CrmSurvey GetCrmSurvey(SqlSession session, CrmSurvey item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCrmSurvey(item);
    }

    public static List<CrmSurvey> SearchCrmSurvey(SqlSession session, CrmSurvey item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmSurvey(item);
    }

    public static void SaveCrmSurvey(SqlSession session, CrmSurvey item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmSurvey(item);
    }

    // endregion CrmSurvey Methods

    // region CrmReceptDeal Methods

    public static CrmReceptDeal GetCrmReceptDeal(SqlSession session, CrmReceptDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCrmReceptDeal(item);
    }

    public static List<CrmReceptDeal> SearchCrmReceptDeal(SqlSession session, CrmReceptDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmReceptDeal(item);
    }

    public static List<CrmReceptDeal> SearchCrmReceptDealForComplaint(SqlSession session, CrmReceptDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmReceptDealForComplaint(item);
    }

    public static void SaveCrmReceptDeal(SqlSession session, CrmReceptDeal item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmReceptDeal(item);
    }

    // endregion CrmReceptDeal Methods

    // region CrmRecept Methods

    public static CrmRecept GetCrmRecept(SqlSession session, CrmRecept item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCrmRecept(item);
    }

    public static List<CrmRecept> SearchCrmRecept(SqlSession session, CrmRecept item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmRecept(item);
    }

    public static void SaveCrmRecept(SqlSession session, CrmRecept item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        mapper.SaveCrmRecept(item);
    }

    public static List<CrmRecept> SearchCrmReceptForDeal(SqlSession session, CrmRecept item) {
        com.alms.mapper.sqlserver.CrmMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.CrmMapper.class);

        return mapper.SearchCrmReceptForDeal(item);
    }

    // endregion CrmRecept Methods

}
