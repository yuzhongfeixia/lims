package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlStaffDao;
import com.alms.entity.staff.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class StaffDao {

    // region UserExamFile Methods

    public static List<UserExamFile> GetListUserExamFile(UserExamFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamFile>();
            default:
                return SqlStaffDao.GetListUserExamFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamFile>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamFile(SqlSession session, UserExamFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamFile(session, item);
            break;
        }
    }

    // endregion UserExamFile Methods

    // region BasUserFile Methods

    public static List<BasUserFile> GetListBasUserFile(BasUserFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasUserFile>();
            default:
                return SqlStaffDao.GetListBasUserFile(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasUserFile>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasUserFile(SqlSession session, BasUserFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveBasUserFile(session, item);
            break;
        }
    }

    // endregion BasUserFile Methods

    // region BasUser Methods

    public static BasUser GetBasUser(BasUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasUser(session, item);
        } catch (Exception e) {
            return new BasUser();
        } finally {
            session.close();
        }
    }

    public static BasUser GetBasUser(SqlSession session, BasUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasUser();
        default:
            return SqlStaffDao.GetBasUser(session, item);
        }
    }

    public static List<BasUser> GetListBasUser(BasUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasUser(session, item);
        } catch (Exception e) {
            return new ArrayList<BasUser>();
        } finally {
            session.close();
        }
    }

    public static List<BasUser> GetListBasUser(SqlSession session, BasUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasUser>();
        default:
            return SqlStaffDao.GetListBasUser(session, item);
        }
    }

    public static List<BasUser> SearchBasUser(BasUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasUser>();
            default:
                return SqlStaffDao.SearchBasUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasUser>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasUser(SqlSession session, BasUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveBasUser(session, item);
            break;
        }
    }

    // endregion BasUser Methods

    // region UserExamResult Methods

    public static UserExamResult GetUserExamResult(UserExamResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserExamResult(session, item);
        } catch (Exception e) {
            return new UserExamResult();
        } finally {
            session.close();
        }
    }

    public static List<UserExamResult> GetListUserExamResult(UserExamResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamResult>();
            default:
                return SqlStaffDao.GetListUserExamResult(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamResult>();
        } finally {
            session.close();
        }
    }

    public static UserExamResult GetUserExamResult(SqlSession session, UserExamResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserExamResult();
        default:
            return SqlStaffDao.GetUserExamResult(session, item);
        }
    }

    public static UserExamResult GetUserExamResultByUser(UserExamResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserExamResultByUser(session, item);
        } catch (Exception e) {
            return new UserExamResult();
        } finally {
            session.close();
        }
    }

    public static UserExamResult GetUserExamResultByUser(SqlSession session, UserExamResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserExamResult();
        default:
            return SqlStaffDao.GetUserExamResultByUser(session, item);
        }
    }

    public static List<UserExamResult> SearchUserExamResult(UserExamResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamResult>();
            default:
                return SqlStaffDao.SearchUserExamResult(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamResult>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamResult(SqlSession session, UserExamResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamResult(session, item);
            break;
        }
    }

    // endregion UserExamResult Methods

    // region UserExamDev Methods

    public static List<UserExamDev> GetListUserExamDev(UserExamDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamDev>();
            default:
                return SqlStaffDao.GetListUserExamDev(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamDev>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamDev(SqlSession session, UserExamDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamDev(session, item);
            break;
        }
    }

    // endregion UserExamDev Methods

    // region UserExamGroup Methods

    public static List<UserExamGroup> GetListUserExamGroup(UserExamGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamGroup>();
            default:
                return SqlStaffDao.GetListUserExamGroup(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamGroup>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamGroup(SqlSession session, UserExamGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamGroup(session, item);
            break;
        }
    }

    // endregion UserExamGroup Methods

    // region UserExamReport Methods

    public static UserExamReport GetUserExamReport(UserExamReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserExamReport(session, item);
        } catch (Exception e) {
            return new UserExamReport();
        } finally {
            session.close();
        }
    }

    public static UserExamReport GetUserExamReport(SqlSession session, UserExamReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserExamReport();
        default:
            return SqlStaffDao.GetUserExamReport(session, item);
        }
    }

    public static List<UserExamReport> SearchUserExamReport(UserExamReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamReport>();
            default:
                return SqlStaffDao.SearchUserExamReport(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamReport>();
        } finally {
            session.close();
        }
    }

    // 查询未做考核结果的
    public static List<UserExamReport> SearchUserExamReportForResult(UserExamReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamReport>();
            default:
                return SqlStaffDao.SearchUserExamReportForResult(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamReport>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamReport(SqlSession session, UserExamReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamReport(session, item);
            break;
        }
    }

    // endregion UserExamReport Methods

    // region UserExamItem Methods

    public static UserExamItem GetUserExamItem(UserExamItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserExamItem(session, item);
        } catch (Exception e) {
            return new UserExamItem();
        } finally {
            session.close();
        }
    }

    public static UserExamItem GetUserExamItem(SqlSession session, UserExamItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserExamItem();
        default:
            return SqlStaffDao.GetUserExamItem(session, item);
        }
    }

    public static List<UserExamItem> GetListUserExamItem() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserExamItem(session);
        } catch (Exception e) {
            return new ArrayList<UserExamItem>();
        } finally {
            session.close();
        }
    }

    public static List<UserExamItem> GetListUserExamItem(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<UserExamItem>();
        default:
            return SqlStaffDao.GetListUserExamItem(session);
        }
    }

    public static List<UserExamItem> SearchUserExamItem(UserExamItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamItem>();
            default:
                return SqlStaffDao.SearchUserExamItem(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamItem>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamItem(SqlSession session, UserExamItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamItem(session, item);
            break;
        }
    }

    // endregion UserExamItem Methods

    // region UserExamDetail Methods

    public static List<UserExamDetail> GetListUserExamDetail(UserExamDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamDetail>();
            default:
                return SqlStaffDao.GetListUserExamDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamDetail(SqlSession session, UserExamDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamDetail(session, item);
            break;
        }
    }

    // endregion UserExamDetail Methods

    // region UserExamRecord Methods

    public static UserExamRecord GetUserExamRecord(UserExamRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserExamRecord(session, item);
        } catch (Exception e) {
            return new UserExamRecord();
        } finally {
            session.close();
        }
    }

    public static UserExamRecord GetUserExamRecord(SqlSession session, UserExamRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserExamRecord();
        default:
            return SqlStaffDao.GetUserExamRecord(session, item);
        }
    }

    public static List<UserExamRecord> GetListUserExamRecord() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserExamRecord(session);
        } catch (Exception e) {
            return new ArrayList<UserExamRecord>();
        } finally {
            session.close();
        }
    }

    public static List<UserExamRecord> GetListUserExamRecord(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<UserExamRecord>();
        default:
            return SqlStaffDao.GetListUserExamRecord(session);
        }
    }

    public static List<UserExamRecord> SearchUserExamRecord(UserExamRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserExamRecord>();
            default:
                return SqlStaffDao.SearchUserExamRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserExamRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserExamRecord(SqlSession session, UserExamRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserExamRecord(session, item);
            break;
        }
    }

    // endregion UserExamRecord Methods

    // region UserTrainDetail Methods

    public static List<UserTrainDetail> GetListUserTrainDetail(UserTrainDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserTrainDetail>();
            default:
                return SqlStaffDao.GetListUserTrainDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserTrainDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserTrainDetail(SqlSession session, UserTrainDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserTrainDetail(session, item);
            break;
        }
    }

    // endregion UserTrainDetail Methods

    // region UserTrain Methods

    public static UserTrain GetUserTrain(UserTrain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserTrain(session, item);
        } catch (Exception e) {
            return new UserTrain();
        } finally {
            session.close();
        }
    }

    public static UserTrain GetUserTrain(SqlSession session, UserTrain item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserTrain();
        default:
            return SqlStaffDao.GetUserTrain(session, item);
        }
    }

    public static List<UserTrain> GetListUserTrain() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserTrain(session);
        } catch (Exception e) {
            return new ArrayList<UserTrain>();
        } finally {
            session.close();
        }
    }

    public static List<UserTrain> GetListUserTrain(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<UserTrain>();
        default:
            return SqlStaffDao.GetListUserTrain(session);
        }
    }

    public static List<UserTrain> GetListUserTrainByUser(UserTrain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserTrainByUser(item, session);
        } catch (Exception e) {
            return new ArrayList<UserTrain>();
        } finally {
            session.close();
        }
    }

    public static List<UserTrain> GetListUserTrainByUser(UserTrain item, SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<UserTrain>();
        default:
            return SqlStaffDao.GetListUserTrainByUser(item, session);
        }
    }

    public static List<UserTrain> SearchUserTrain(UserTrain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserTrain>();
            default:
                return SqlStaffDao.SearchUserTrain(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserTrain>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserTrain(SqlSession session, UserTrain item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserTrain(session, item);
            break;
        }
    }

    // endregion UserTrain Methods

    // region RecordSummary Methods

    public static RecordSummary GetRecordSummary(RecordSummary item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetRecordSummary(session, item);
        } catch (Exception e) {
            return new RecordSummary();
        } finally {
            session.close();
        }
    }

    public static RecordSummary GetRecordSummary(SqlSession session, RecordSummary item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new RecordSummary();
        default:
            return SqlStaffDao.GetRecordSummary(session, item);
        }
    }

    public static List<RecordSummary> GetListRecordSummary() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListRecordSummary(session);
        } catch (Exception e) {
            return new ArrayList<RecordSummary>();
        } finally {
            session.close();
        }
    }

    public static List<RecordSummary> GetListRecordSummary(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<RecordSummary>();
        default:
            return SqlStaffDao.GetListRecordSummary(session);
        }
    }

    public static List<RecordSummary> SearchRecordSummary(RecordSummary item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<RecordSummary>();
            default:
                return SqlStaffDao.SearchRecordSummary(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<RecordSummary>();
        } finally {
            session.close();
        }
    }

    public static void SaveRecordSummary(SqlSession session, RecordSummary item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveRecordSummary(session, item);
            break;
        }
    }

    // endregion RecordSummary Methods

    // region UserPlanYearDetail Methods

    public static UserPlanYearDetail GetUserPlanYearDetail(UserPlanYearDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserPlanYearDetail(session, item);
        } catch (Exception e) {
            return new UserPlanYearDetail();
        } finally {
            session.close();
        }
    }

    public static UserPlanYearDetail GetUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserPlanYearDetail();
        default:
            return SqlStaffDao.GetUserPlanYearDetail(session, item);
        }
    }

    public static List<UserPlanYearDetail> GetListUserPlanYearDetail(UserPlanYearDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListUserPlanYearDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<UserPlanYearDetail>();
        } finally {
            session.close();
        }
    }

    public static List<UserPlanYearDetail> GetListUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<UserPlanYearDetail>();
        default:
            return SqlStaffDao.GetListUserPlanYearDetail(session, item);
        }
    }

    public static List<UserPlanYearDetail> SearchUserPlanYearDetail(UserPlanYearDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserPlanYearDetail>();
            default:
                return SqlStaffDao.SearchUserPlanYearDetail(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<UserPlanYearDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserPlanYearDetail(session, item);
            break;
        }
    }

    // endregion UserPlanYearDetail Methods

    // region UserPlanYear Methods

    public static UserPlanYear GetUserPlanYear(UserPlanYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetUserPlanYear(session, item);
        } catch (Exception e) {
            return new UserPlanYear();
        } finally {
            session.close();
        }
    }

    public static UserPlanYear GetUserPlanYear(SqlSession session, UserPlanYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new UserPlanYear();
        default:
            return SqlStaffDao.GetUserPlanYear(session, item);
        }
    }

    public static List<UserPlanYear> SearchUserPlanYear(UserPlanYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserPlanYear>();
            default:
                return SqlStaffDao.SearchUserPlanYear(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserPlanYear>();
        } finally {
            session.close();
        }
    }

    public static List<UserPlanYear> SearchUserPlanYearApproved(UserPlanYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<UserPlanYear>();
            default:
                return SqlStaffDao.SearchUserPlanYearApproved(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<UserPlanYear>();
        } finally {
            session.close();
        }
    }

    public static void SaveUserPlanYear(SqlSession session, UserPlanYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlStaffDao.SaveUserPlanYear(session, item);
            break;
        }
    }

    // endregion UserPlanYear Methods

}
