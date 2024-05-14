package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlStdDao;
import com.alms.entity.std.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class StdDao {

    // region StdReviewDetail Methods

    public static List<StdReviewDetail> GetListStdReviewDetail(StdReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReviewDetail>();
            default:
                return SqlStdDao.GetListStdReviewDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReviewDetail>();
        } finally {
            session.close();
        }
    }

    public static List<StdReviewDetail> GetStdReviewDetailForChange(StdReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReviewDetail>();
            default:
                return SqlStdDao.GetStdReviewDetailForChange(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReviewDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdReviewDetail(SqlSession session, StdReviewDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdReviewDetail(session, item);
            break;
        }
    }

    // endregion StdReviewDetail Methods

    // region StdReview Methods

    public static StdReview GetStdReview(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdReview(session, item);
        } catch (Exception e) {
            return new StdReview();
        } finally {
            session.close();
        }
    }

    public static StdReview GetBusCountry(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusCountry(session, item);
        } catch (Exception e) {
            return new StdReview();
        } finally {
            session.close();
        }
    }

    public static StdReview GetStdReview(SqlSession session, StdReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdReview();
        default:
            return SqlStdDao.GetStdReview(session, item);
        }
    }

    public static StdReview GetBusCountry(SqlSession session, StdReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdReview();
        default:
            return SqlStdDao.GetBusCountry(session, item);
        }
    }

    public static List<StdReview> GetListStdReview(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdReview(session, item);
        } catch (Exception e) {
            return new ArrayList<StdReview>();
        } finally {
            session.close();
        }
    }

    public static List<StdReview> GetListStdReview(SqlSession session, StdReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdReview>();
        default:
            return SqlStdDao.GetListStdReview(session, item);
        }
    }

    public static List<StdReview> SearchStdReview(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReview>();
            default:
                return SqlStdDao.SearchStdReview(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReview>();
        } finally {
            session.close();
        }
    }

    public static List<StdReview> SearchBusCountry(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReview>();
            default:
                return SqlStdDao.SearchBusCountry(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReview>();
        } finally {
            session.close();
        }
    }

    public static List<StdReview> SearchStdReviewForChange(StdReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReview>();
            default:
                return SqlStdDao.SearchStdReviewForChange(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReview>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdReview(SqlSession session, StdReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdReview(session, item);
            break;
        }
    }

    public static void SaveBusCountry(SqlSession session, StdReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveBusCountry(session, item);
            break;
        }
    }

    // endregion StdReview Methods

    // region StdUse Methods

    public static StdUse GetStdUse(StdUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdUse(session, item);
        } catch (Exception e) {
            return new StdUse();
        } finally {
            session.close();
        }
    }

    public static StdUse GetStdUse(SqlSession session, StdUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdUse();
        default:
            return SqlStdDao.GetStdUse(session, item);
        }
    }

    public static List<StdUse> GetListStdUse(StdUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdUse(session, item);
        } catch (Exception e) {
            return new ArrayList<StdUse>();
        } finally {
            session.close();
        }
    }

    public static List<StdUse> GetListStdUse(SqlSession session, StdUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdUse>();
        default:
            return SqlStdDao.GetListStdUse(session, item);
        }
    }

    public static List<StdUse> GetListStdUseBySampletran(StdUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdUseBySampletran(session, item);
        } catch (Exception e) {
            return new ArrayList<StdUse>();
        } finally {
            session.close();
        }
    }

    public static List<StdUse> GetListStdUseBySampletran(SqlSession session, StdUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdUse>();
        default:
            return SqlStdDao.GetListStdUseBySampletran(session, item);
        }
    }

    public static List<StdUse> SearchStdUse(StdUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdUse>();
            default:
                return SqlStdDao.SearchStdUse(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdUse>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdUse(SqlSession session, StdUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdUse(session, item);
            break;
        }
    }

    // endregion StdUse Methods

    // region StdNonstd Methods

    public static StdNonstd GetStdNonstd(StdNonstd item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdNonstd(session, item);
        } catch (Exception e) {
            return new StdNonstd();
        } finally {
            session.close();
        }
    }

    public static StdNonstd GetStdNonstd(SqlSession session, StdNonstd item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdNonstd();
        default:
            return SqlStdDao.GetStdNonstd(session, item);
        }
    }

    public static List<StdNonstd> GetListStdNonstd(StdNonstd item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdNonstd(session, item);
        } catch (Exception e) {
            return new ArrayList<StdNonstd>();
        } finally {
            session.close();
        }
    }

    public static List<StdNonstd> GetListStdNonstd(SqlSession session, StdNonstd item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdNonstd>();
        default:
            return SqlStdDao.GetListStdNonstd(session, item);
        }
    }

    public static List<StdNonstd> SearchStdNonstd(StdNonstd item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdNonstd>();
            default:
                return SqlStdDao.SearchStdNonstd(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdNonstd>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdNonstd(SqlSession session, StdNonstd item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdNonstd(session, item);
            break;
        }
    }

    // endregion StdNonstd Methods

    // region StdMethodDevi Methods

    public static StdMethodDevi GetStdMethodDevi(StdMethodDevi item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdMethodDevi(session, item);
        } catch (Exception e) {
            return new StdMethodDevi();
        } finally {
            session.close();
        }
    }

    public static StdMethodDevi GetStdMethodDevi(SqlSession session, StdMethodDevi item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdMethodDevi();
        default:
            return SqlStdDao.GetStdMethodDevi(session, item);
        }
    }

    public static List<StdMethodDevi> GetListStdMethodDevi(StdMethodDevi item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdMethodDevi(session, item);
        } catch (Exception e) {
            return new ArrayList<StdMethodDevi>();
        } finally {
            session.close();
        }
    }

    public static List<StdMethodDevi> GetListStdMethodDevi(SqlSession session, StdMethodDevi item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdMethodDevi>();
        default:
            return SqlStdDao.GetListStdMethodDevi(session, item);
        }
    }

    public static List<StdMethodDevi> SearchStdMethodDevi(StdMethodDevi item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdMethodDevi>();
            default:
                return SqlStdDao.SearchStdMethodDevi(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdMethodDevi>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdMethodDevi(SqlSession session, StdMethodDevi item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdMethodDevi(session, item);
            break;
        }
    }

    // endregion StdMethodDevi Methods

    // region StdProApply Methods

    public static StdProApply GetStdProApply(StdProApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdProApply(session, item);
        } catch (Exception e) {
            return new StdProApply();
        } finally {
            session.close();
        }
    }

    public static StdProApply GetStdProApply(SqlSession session, StdProApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdProApply();
        default:
            return SqlStdDao.GetStdProApply(session, item);
        }
    }

    public static List<StdProApply> GetListStdProApply(StdProApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdProApply(session, item);
        } catch (Exception e) {
            return new ArrayList<StdProApply>();
        } finally {
            session.close();
        }
    }

    public static List<StdProApply> GetListStdProApply(SqlSession session, StdProApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdProApply>();
        default:
            return SqlStdDao.GetListStdProApply(session, item);
        }
    }

    public static List<StdProApply> SearchStdProApply(StdProApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdProApply>();
            default:
                return SqlStdDao.SearchStdProApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdProApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdProApply(SqlSession session, StdProApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdProApply(session, item);
            break;
        }
    }

    // endregion StdProApply Methods

    // region StdTestSure Methods

    public static StdTestSure GetStdTestSure(StdTestSure item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdTestSure(session, item);
        } catch (Exception e) {
            return new StdTestSure();
        } finally {
            session.close();
        }
    }

    public static StdTestSure GetStdTestSure(SqlSession session, StdTestSure item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdTestSure();
        default:
            return SqlStdDao.GetStdTestSure(session, item);
        }
    }

    public static List<StdTestSure> GetListStdTestSure(StdTestSure item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdTestSure(session, item);
        } catch (Exception e) {
            return new ArrayList<StdTestSure>();
        } finally {
            session.close();
        }
    }

    public static List<StdTestSure> GetListStdTestSure(SqlSession session, StdTestSure item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdTestSure>();
        default:
            return SqlStdDao.GetListStdTestSure(session, item);
        }
    }

    public static List<StdTestSure> SearchStdTestSure(StdTestSure item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdTestSure>();
            default:
                return SqlStdDao.SearchStdTestSure(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdTestSure>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdTestSure(SqlSession session, StdTestSure item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdTestSure(session, item);
            break;
        }
    }

    // endregion StdTestSure Methods

    // region StdReplRecord Methods

    public static StdReplRecord GetStdReplRecord(StdReplRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdReplRecord(session, item);
        } catch (Exception e) {
            return new StdReplRecord();
        } finally {
            session.close();
        }
    }

    public static StdReplRecord GetStdReplRecord(SqlSession session, StdReplRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdReplRecord();
        default:
            return SqlStdDao.GetStdReplRecord(session, item);
        }
    }

    public static List<StdReplRecord> GetListStdReplRecord(StdReplRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdReplRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<StdReplRecord>();
        } finally {
            session.close();
        }
    }

    public static List<StdReplRecord> GetListStdReplRecord(SqlSession session, StdReplRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdReplRecord>();
        default:
            return SqlStdDao.GetListStdReplRecord(session, item);
        }
    }

    public static List<StdReplRecord> SearchStdReplRecord(StdReplRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdReplRecord>();
            default:
                return SqlStdDao.SearchStdReplRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdReplRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdReplRecord(SqlSession session, StdReplRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdReplRecord(session, item);
            break;
        }
    }

    // endregion StdReplRecord Methods

    // region StdChange Methods

    public static StdChange GetStdChange(StdChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdChange(session, item);
        } catch (Exception e) {
            return new StdChange();
        } finally {
            session.close();
        }
    }

    public static StdChange GetStdChange(SqlSession session, StdChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdChange();
        default:
            return SqlStdDao.GetStdChange(session, item);
        }
    }

    public static List<StdChange> GetListStdChange(StdChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdChange(session, item);
        } catch (Exception e) {
            return new ArrayList<StdChange>();
        } finally {
            session.close();
        }
    }

    public static List<StdChange> GetListStdChange(SqlSession session, StdChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdChange>();
        default:
            return SqlStdDao.GetListStdChange(session, item);
        }
    }

    public static List<StdChange> SearchStdChange(StdChange item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdChange>();
            default:
                return SqlStdDao.SearchStdChange(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdChange>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdChange(SqlSession session, StdChange item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdChange(session, item);
            break;
        }
    }

    // endregion StdChange Methods

    // region StdSureDetail Methods

    public static StdSureDetail GetStdSureDetail(StdSureDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetStdSureDetail(session, item);
        } catch (Exception e) {
            return new StdSureDetail();
        } finally {
            session.close();
        }
    }

    public static StdSureDetail GetStdSureDetail(SqlSession session, StdSureDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new StdSureDetail();
        default:
            return SqlStdDao.GetStdSureDetail(session, item);
        }
    }

    public static List<StdSureDetail> GetListStdSureDetail(StdSureDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListStdSureDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<StdSureDetail>();
        } finally {
            session.close();
        }
    }

    public static List<StdSureDetail> GetListStdSureDetail(SqlSession session, StdSureDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<StdSureDetail>();
        default:
            return SqlStdDao.GetListStdSureDetail(session, item);
        }
    }

    public static List<StdSureDetail> SearchStdSureDetail(StdSureDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<StdSureDetail>();
            default:
                return SqlStdDao.SearchStdSureDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<StdSureDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveStdSureDetail(SqlSession session, StdSureDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStdDao.SaveStdSureDetail(session, item);
            break;
        }
    }

    // endregion StdSureDetail Methods

}
