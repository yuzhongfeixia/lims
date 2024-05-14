package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlInnerDao;
import com.alms.entity.inner.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class InnerDao {

    // region InnerFoodReview Methods

    public static InnerFoodReview GetInnerFoodReview(InnerFoodReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerFoodReview(session, item);
        } catch (Exception e) {
            return new InnerFoodReview();
        } finally {
            session.close();
        }
    }

    public static InnerFoodReview GetInnerFoodReview(SqlSession session, InnerFoodReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerFoodReview();
        default:
            return SqlInnerDao.GetInnerFoodReview(session, item);
        }
    }

    public static List<InnerFoodReview> SearchInnerFoodReview(InnerFoodReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerFoodReview>();
            default:
                return SqlInnerDao.SearchInnerFoodReview(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerFoodReview>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerFoodReview(SqlSession session, InnerFoodReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerFoodReview(session, item);
            break;
        }
    }

    // endregion InnerFoodReview Methods

    // region InnerMeetPart Methods

    public static List<InnerMeetPart> GetListInnerMeetPart(InnerMeetPart item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerMeetPart>();
            default:
                return SqlInnerDao.GetListInnerMeetPart(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerMeetPart>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerMeetPart(SqlSession session, InnerMeetPart item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerMeetPart(session, item);
            break;
        }
    }

    // endregion InnerMeetPart Methods

    // region InnerMeetSign Methods

    public static InnerMeetSign GetInnerMeetSign(InnerMeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerMeetSign(session, item);
        } catch (Exception e) {
            return new InnerMeetSign();
        } finally {
            session.close();
        }
    }

    public static InnerMeetSign GetInnerMeetSign(SqlSession session, InnerMeetSign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerMeetSign();
        default:
            return SqlInnerDao.GetInnerMeetSign(session, item);
        }
    }

    public static List<InnerMeetSign> SearchInnerMeetSign(InnerMeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerMeetSign>();
            default:
                return SqlInnerDao.SearchInnerMeetSign(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerMeetSign>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerMeetSign(SqlSession session, InnerMeetSign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerMeetSign(session, item);
            break;
        }
    }

    // endregion InnerMeetSign Methods

    // region InnerReport Methods

    public static InnerReport GetInnerReport(InnerReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerReport(session, item);
        } catch (Exception e) {
            return new InnerReport();
        } finally {
            session.close();
        }
    }

    public static InnerReport GetInnerReport(SqlSession session, InnerReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerReport();
        default:
            return SqlInnerDao.GetInnerReport(session, item);
        }
    }

    public static List<InnerReport> SearchInnerReport(InnerReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerReport>();
            default:
                return SqlInnerDao.SearchInnerReport(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerReport>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerReport(SqlSession session, InnerReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerReport(session, item);
            break;
        }
    }

    // endregion InnerReport Methods

    // region InnerInvalid Methods

    public static InnerInvalid GetInnerInvalid(InnerInvalid item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerInvalid(session, item);
        } catch (Exception e) {
            return new InnerInvalid();
        } finally {
            session.close();
        }
    }

    public static InnerInvalid GetInnerInvalid(SqlSession session, InnerInvalid item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerInvalid();
        default:
            return SqlInnerDao.GetInnerInvalid(session, item);
        }
    }

    public static List<InnerInvalid> SearchInnerInvalid(InnerInvalid item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerInvalid>();
            default:
                return SqlInnerDao.SearchInnerInvalid(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerInvalid>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerInvalid(SqlSession session, InnerInvalid item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerInvalid(session, item);
            break;
        }
    }

    // endregion InnerInvalid Methods

    // region InnerSceneWork Methods

    public static InnerSceneWork GetInnerSceneWork(InnerSceneWork item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerSceneWork(session, item);
        } catch (Exception e) {
            return new InnerSceneWork();
        } finally {
            session.close();
        }
    }

    public static InnerSceneWork GetInnerSceneWork(SqlSession session, InnerSceneWork item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerSceneWork();
        default:
            return SqlInnerDao.GetInnerSceneWork(session, item);
        }
    }

    public static List<InnerSceneWork> GetListInnerSceneWork(InnerSceneWork item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListInnerSceneWork(session, item);
        } catch (Exception e) {
            return new ArrayList<InnerSceneWork>();
        } finally {
            session.close();
        }
    }

    public static List<InnerSceneWork> GetListInnerSceneWork(SqlSession session, InnerSceneWork item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<InnerSceneWork>();
        default:
            return SqlInnerDao.GetListInnerSceneWork(session, item);
        }
    }

    public static List<InnerSceneWork> SearchInnerSceneWork(InnerSceneWork item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerSceneWork>();
            default:
                return SqlInnerDao.SearchInnerSceneWork(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerSceneWork>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerSceneWork(SqlSession session, InnerSceneWork item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerSceneWork(session, item);
            break;
        }
    }

    // endregion InnerSceneWork Methods

    // region InnerScene Methods

    public static InnerScene GetInnerScene(InnerScene item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerScene(session, item);
        } catch (Exception e) {
            return new InnerScene();
        } finally {
            session.close();
        }
    }

    public static InnerScene GetInnerScene(SqlSession session, InnerScene item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerScene();
        default:
            return SqlInnerDao.GetInnerScene(session, item);
        }
    }

    public static List<InnerScene> SearchInnerScene(InnerScene item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerScene>();
            default:
                return SqlInnerDao.SearchInnerScene(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerScene>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerScene(SqlSession session, InnerScene item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerScene(session, item);
            break;
        }
    }

    // endregion InnerScene Methods

    // region InnerGroupMember Methods

    public static List<InnerGroupMember> GetListInnerGroupMember(InnerGroupMember item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerGroupMember>();
            default:
                return SqlInnerDao.GetListInnerGroupMember(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerGroupMember>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerGroupMember(SqlSession session, InnerGroupMember item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerGroupMember(session, item);
            break;
        }
    }

    // endregion InnerGroupMember Methods

    // region InnerGroup Methods

    public static InnerGroup GetInnerGroup(InnerGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerGroup(session, item);
        } catch (Exception e) {
            return new InnerGroup();
        } finally {
            session.close();
        }
    }

    public static InnerGroup GetInnerGroup(SqlSession session, InnerGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerGroup();
        default:
            return SqlInnerDao.GetInnerGroup(session, item);
        }
    }

    public static List<InnerGroup> GetListInnerGroup(InnerGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListInnerGroup(session, item);
        } catch (Exception e) {
            return new ArrayList<InnerGroup>();
        } finally {
            session.close();
        }
    }

    public static List<InnerGroup> GetListInnerGroup(SqlSession session, InnerGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<InnerGroup>();
        default:
            return SqlInnerDao.GetListInnerGroup(session, item);
        }
    }

    public static List<InnerGroup> SearchInnerGroup(InnerGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerGroup>();
            default:
                return SqlInnerDao.SearchInnerGroup(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerGroup>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerGroup(SqlSession session, InnerGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerGroup(session, item);
            break;
        }
    }

    // endregion InnerGroup Methods

    // region InnerYearDetail Methods

    public static List<InnerYearDetail> GetListInnerYearDetail(InnerYearDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListInnerYearDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<InnerYearDetail>();
        } finally {
            session.close();
        }
    }

    public static List<InnerYearDetail> GetListInnerYearDetail(SqlSession session, InnerYearDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<InnerYearDetail>();
        default:
            return SqlInnerDao.GetListInnerYearDetail(session, item);
        }
    }

    public static void SaveInnerYearDetail(SqlSession session, InnerYearDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerYearDetail(session, item);
            break;
        }
    }

    // endregion InnerYearDetail Methods

    // region InnerYear Methods

    public static InnerYear GetInnerYear(InnerYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetInnerYear(session, item);
        } catch (Exception e) {
            return new InnerYear();
        } finally {
            session.close();
        }
    }

    public static InnerYear GetInnerYear(SqlSession session, InnerYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new InnerYear();
        default:
            return SqlInnerDao.GetInnerYear(session, item);
        }
    }

    public static List<InnerYear> SearchInnerYear(InnerYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<InnerYear>();
            default:
                return SqlInnerDao.SearchInnerYear(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<InnerYear>();
        } finally {
            session.close();
        }
    }

    public static void SaveInnerYear(SqlSession session, InnerYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlInnerDao.SaveInnerYear(session, item);
            break;
        }
    }

    // endregion InnerYear Methods

}
