package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.staff.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlStaffDao {

    // region UserExamFile Methods

    public static List<UserExamFile> GetListUserExamFile(SqlSession session, UserExamFile item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamFile(item);
    }

    public static void SaveUserExamFile(SqlSession session, UserExamFile item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamFile(item);
    }

    // endregion UserExamFile Methods

    // region BasUserFile Methods

    public static List<BasUserFile> GetListBasUserFile(SqlSession session, BasUserFile item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListBasUserFile(item);
    }

    public static void SaveBasUserFile(SqlSession session, BasUserFile item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveBasUserFile(item);
    }

    // endregion BasUserFile Methods

    // region BasUser Methods

    public static BasUser GetBasUser(SqlSession session, BasUser item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasUser(item);
    }

    public static List<BasUser> GetListBasUser(SqlSession session, BasUser item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListBasUser(item);
    }

    public static List<BasUser> SearchBasUser(SqlSession session, BasUser item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchBasUser(item);
    }

    public static void SaveBasUser(SqlSession session, BasUser item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveBasUser(item);
    }

    // endregion BasUser Methods

    // region UserExamResult Methods

    public static UserExamResult GetUserExamResult(SqlSession session, UserExamResult item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserExamResult(item);
    }

    public static List<UserExamResult> GetListUserExamResult(SqlSession session, UserExamResult item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamResult(item);
    }

    public static UserExamResult GetUserExamResultByUser(SqlSession session, UserExamResult item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetUserExamResultByUser(item);
    }

    public static List<UserExamResult> SearchUserExamResult(SqlSession session, UserExamResult item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserExamResult(item);
    }

    public static void SaveUserExamResult(SqlSession session, UserExamResult item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamResult(item);
    }

    // endregion UserExamResult Methods

    // region UserExamDev Methods

    public static List<UserExamDev> GetListUserExamDev(SqlSession session, UserExamDev item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamDev(item);
    }

    public static void SaveUserExamDev(SqlSession session, UserExamDev item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamDev(item);
    }

    // endregion UserExamDev Methods

    // region UserExamGroup Methods

    public static List<UserExamGroup> GetListUserExamGroup(SqlSession session, UserExamGroup item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamGroup(item);
    }

    public static void SaveUserExamGroup(SqlSession session, UserExamGroup item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamGroup(item);
    }

    // endregion UserExamGroup Methods

    // region UserExamReport Methods

    public static UserExamReport GetUserExamReport(SqlSession session, UserExamReport item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserExamReport(item);
    }

    public static List<UserExamReport> SearchUserExamReport(SqlSession session, UserExamReport item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserExamReport(item);
    }

    public static List<UserExamReport> SearchUserExamReportForResult(SqlSession session, UserExamReport item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserExamReportForResult(item);
    }

    public static void SaveUserExamReport(SqlSession session, UserExamReport item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamReport(item);
    }

    // endregion UserExamReport Methods

    // region UserExamItem Methods

    public static UserExamItem GetUserExamItem(SqlSession session, UserExamItem item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserExamItem(item);
    }

    public static List<UserExamItem> GetListUserExamItem(SqlSession session) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamItem();
    }

    public static List<UserExamItem> SearchUserExamItem(SqlSession session, UserExamItem item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserExamItem(item);
    }

    public static void SaveUserExamItem(SqlSession session, UserExamItem item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamItem(item);
    }

    // endregion UserExamItem Methods

    // region UserExamDetail Methods

    public static List<UserExamDetail> GetListUserExamDetail(SqlSession session, UserExamDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamDetail(item);
    }

    public static void SaveUserExamDetail(SqlSession session, UserExamDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamDetail(item);
    }

    // endregion UserExamDetail Methods

    // region UserExamRecord Methods

    public static UserExamRecord GetUserExamRecord(SqlSession session, UserExamRecord item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserExamRecord(item);
    }

    public static List<UserExamRecord> GetListUserExamRecord(SqlSession session) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserExamRecord();
    }

    public static List<UserExamRecord> SearchUserExamRecord(SqlSession session, UserExamRecord item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserExamRecord(item);
    }

    public static void SaveUserExamRecord(SqlSession session, UserExamRecord item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserExamRecord(item);
    }

    // endregion UserExamRecord Methods

    // region UserTrainDetail Methods

    public static List<UserTrainDetail> GetListUserTrainDetail(SqlSession session, UserTrainDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserTrainDetail(item);
    }

    public static void SaveUserTrainDetail(SqlSession session, UserTrainDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserTrainDetail(item);
    }

    // endregion UserTrainDetail Methods

    // region UserTrain Methods

    public static UserTrain GetUserTrain(SqlSession session, UserTrain item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserTrain(item);
    }

    public static List<UserTrain> GetListUserTrain(SqlSession session) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserTrain();
    }

    public static List<UserTrain> GetListUserTrainByUser(UserTrain item, SqlSession session) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserTrainByUser(item);
    }

    public static List<UserTrain> SearchUserTrain(SqlSession session, UserTrain item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserTrain(item);
    }

    public static void SaveUserTrain(SqlSession session, UserTrain item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserTrain(item);
    }

    // endregion UserTrain Methods

    // region RecordSummary Methods

    public static RecordSummary GetRecordSummary(SqlSession session, RecordSummary item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetRecordSummary(item);
    }

    public static List<RecordSummary> GetListRecordSummary(SqlSession session) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListRecordSummary();
    }

    public static List<RecordSummary> SearchRecordSummary(SqlSession session, RecordSummary item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchRecordSummary(item);
    }

    public static void SaveRecordSummary(SqlSession session, RecordSummary item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveRecordSummary(item);
    }

    // endregion RecordSummary Methods

    // region UserPlanYearDetail Methods

    public static UserPlanYearDetail GetUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserPlanYearDetail(item);
    }

    public static List<UserPlanYearDetail> GetListUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.GetListUserPlanYearDetail(item);
    }

    public static List<UserPlanYearDetail> SearchUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserPlanYearDetail(item);
    }

    public static void SaveUserPlanYearDetail(SqlSession session, UserPlanYearDetail item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserPlanYearDetail(item);
    }

    // endregion UserPlanYearDetail Methods

    // region UserPlanYear Methods

    public static UserPlanYear GetUserPlanYear(SqlSession session, UserPlanYear item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetUserPlanYear(item);
    }

    public static List<UserPlanYear> SearchUserPlanYear(SqlSession session, UserPlanYear item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserPlanYear(item);
    }

    public static List<UserPlanYear> SearchUserPlanYearApproved(SqlSession session, UserPlanYear item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        return mapper.SearchUserPlanYearApproved(item);
    }

    public static void SaveUserPlanYear(SqlSession session, UserPlanYear item) {
        com.alms.mapper.sqlserver.StaffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.StaffMapper.class);

        mapper.SaveUserPlanYear(item);
    }

    // endregion UserPlanYear Methods

}
