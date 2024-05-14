package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.*;
import com.alms.entity.crm.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class CrmDao {

    // region CrmAccidentDeal Methods

    public static CrmAccidentDeal GetCrmAccidentDeal(CrmAccidentDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCrmAccidentDeal(session, item);
        } catch (Exception e) {
            return new CrmAccidentDeal();
        } finally {
            session.close();
        }
    }

    public static CrmAccidentDeal GetCrmAccidentDeal(SqlSession session, CrmAccidentDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CrmAccidentDeal();
        default:
            return SqlCrmDao.GetCrmAccidentDeal(session, item);
        }
    }

    public static List<CrmAccidentDeal> SearchCrmAccidentDeal(CrmAccidentDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmAccidentDeal>();
            default:
                return SqlCrmDao.SearchCrmAccidentDeal(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmAccidentDeal>();
        } finally {
            session.close();
        }
    }

    public static List<CrmAccidentDeal> SearchCrmAccidentDealApproved(CrmAccidentDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmAccidentDeal>();
            default:
                return SqlCrmDao.SearchCrmAccidentDealApproved(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmAccidentDeal>();
        } finally {
            session.close();
        }
    }

    public static void SaveCrmAccidentDeal(SqlSession session, CrmAccidentDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmAccidentDeal(session, item);
            break;
        }
    }

    // endregion CrmAccidentDeal Methods

    // region CrmSurveyItem Methods

    public static CrmSurveyItem GetCrmSurveyItem(CrmSurveyItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCrmSurveyItem(session, item);
        } catch (Exception e) {
            return new CrmSurveyItem();
        } finally {
            session.close();
        }
    }

    public static CrmSurveyItem GetCrmSurveyItem(SqlSession session, CrmSurveyItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CrmSurveyItem();
        default:
            return SqlCrmDao.GetCrmSurveyItem(session, item);
        }
    }

    public static List<CrmSurveyItem> SearchCrmSurveyItem(CrmSurveyItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmSurveyItem>();
            default:
                return SqlCrmDao.SearchCrmSurveyItem(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmSurveyItem>();
        } finally {
            session.close();
        }
    }

    public static void SaveCrmSurveyItem(SqlSession session, CrmSurveyItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmSurveyItem(session, item);
            break;
        }
    }

    // endregion CrmSurveyItem Methods

    // region CrmSurveyDetail Methods

    public static List<CrmSurveyDetail> GetListCrmSurveyDetail(CrmSurveyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListCrmSurveyDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<CrmSurveyDetail>();
        } finally {
            session.close();
        }
    }

    public static List<CrmSurveyDetail> GetListCrmSurveyDetail(SqlSession session, CrmSurveyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<CrmSurveyDetail>();
        default:
            return SqlCrmDao.GetListCrmSurveyDetail(session, item);
        }
    }

    public static void SaveCrmSurveyDetail(SqlSession session, CrmSurveyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmSurveyDetail(session, item);
            break;
        }
    }

    // endregion CrmSurveyDetail Methods

    // region CrmSurvey Methods

    public static CrmSurvey GetCrmSurvey(CrmSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCrmSurvey(session, item);
        } catch (Exception e) {
            return new CrmSurvey();
        } finally {
            session.close();
        }
    }

    public static CrmSurvey GetCrmSurvey(SqlSession session, CrmSurvey item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CrmSurvey();
        default:
            return SqlCrmDao.GetCrmSurvey(session, item);
        }
    }

    public static List<CrmSurvey> SearchCrmSurvey(CrmSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmSurvey>();
            default:
                return SqlCrmDao.SearchCrmSurvey(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmSurvey>();
        } finally {
            session.close();
        }
    }

    public static void SaveCrmSurvey(SqlSession session, CrmSurvey item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmSurvey(session, item);
            break;
        }
    }

    // endregion CrmSurvey Methods

    // region CrmReceptDeal Methods

    public static CrmReceptDeal GetCrmReceptDeal(CrmReceptDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCrmReceptDeal(session, item);
        } catch (Exception e) {
            return new CrmReceptDeal();
        } finally {
            session.close();
        }
    }

    public static CrmReceptDeal GetCrmReceptDeal(SqlSession session, CrmReceptDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CrmReceptDeal();
        default:
            return SqlCrmDao.GetCrmReceptDeal(session, item);
        }
    }

    public static List<CrmReceptDeal> SearchCrmReceptDeal(CrmReceptDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmReceptDeal>();
            default:
                return SqlCrmDao.SearchCrmReceptDeal(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmReceptDeal>();
        } finally {
            session.close();
        }
    }

    public static List<CrmReceptDeal> SearchCrmReceptDealForComplaint(CrmReceptDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmReceptDeal>();
            default:
                return SqlCrmDao.SearchCrmReceptDealForComplaint(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmReceptDeal>();
        } finally {
            session.close();
        }
    }

    public static void SaveCrmReceptDeal(SqlSession session, CrmReceptDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmReceptDeal(session, item);
            break;
        }
    }

    // endregion CrmReceptDeal Methods

    // region CrmRecept Methods

    public static CrmRecept GetCrmRecept(CrmRecept item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCrmRecept(session, item);
        } catch (Exception e) {
            return new CrmRecept();
        } finally {
            session.close();
        }
    }

    public static CrmRecept GetCrmRecept(SqlSession session, CrmRecept item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CrmRecept();
        default:
            return SqlCrmDao.GetCrmRecept(session, item);
        }
    }

    public static List<CrmRecept> SearchCrmRecept(CrmRecept item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmRecept>();
            default:
                return SqlCrmDao.SearchCrmRecept(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmRecept>();
        } finally {
            session.close();
        }
    }

    public static List<CrmRecept> SearchCrmReceptForDeal(CrmRecept item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CrmRecept>();
            default:
                return SqlCrmDao.SearchCrmReceptForDeal(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CrmRecept>();
        } finally {
            session.close();
        }
    }

    public static void SaveCrmRecept(SqlSession session, CrmRecept item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlCrmDao.SaveCrmRecept(session, item);
            break;
        }
    }

    // endregion CrmRecept Methods

}
