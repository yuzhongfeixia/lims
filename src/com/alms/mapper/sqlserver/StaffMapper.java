package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.staff.*;
import com.gpersist.mapper.BaseMapper;

public interface StaffMapper extends BaseMapper {

    // region UserExamFile Methods

    public List<UserExamFile> GetListUserExamFile(UserExamFile item);

    public void SaveUserExamFile(UserExamFile item);

    // endregion UserExamFile Methods

    // region BasUserFile Methods

    public List<BasUserFile> GetListBasUserFile(BasUserFile item);

    public void SaveBasUserFile(BasUserFile item);

    // endregion BasUserFile Methods

    // region BasUser Methods

    public BasUser GetBasUser(BasUser item);

    public List<BasUser> GetListBasUser(BasUser item);

    public List<BasUser> SearchBasUser(BasUser item);

    public void SaveBasUser(BasUser item);

    // endregion BasUser Methods

    // region UserExamResult Methods

    public UserExamResult GetUserExamResult(UserExamResult item);

    public List<UserExamResult> GetListUserExamResult(UserExamResult item);

    public UserExamResult GetUserExamResultByUser(UserExamResult item);

    public List<UserExamResult> SearchUserExamResult(UserExamResult item);

    public void SaveUserExamResult(UserExamResult item);

    // endregion UserExamResult Methods

    // region UserExamDev Methods

    public List<UserExamDev> GetListUserExamDev(UserExamDev item);

    public void SaveUserExamDev(UserExamDev item);

    // endregion UserExamDev Methods

    // region UserExamGroup Methods

    public List<UserExamGroup> GetListUserExamGroup(UserExamGroup item);

    public void SaveUserExamGroup(UserExamGroup item);

    // endregion UserExamGroup Methods

    // region UserExamReport Methods

    public UserExamReport GetUserExamReport(UserExamReport item);

    public List<UserExamReport> SearchUserExamReport(UserExamReport item);

    public List<UserExamReport> SearchUserExamReportForResult(UserExamReport item);

    public void SaveUserExamReport(UserExamReport item);

    // endregion UserExamReport Methods

    // region UserExamItem Methods

    public UserExamItem GetUserExamItem(UserExamItem item);

    public List<UserExamItem> GetListUserExamItem();

    public List<UserExamItem> SearchUserExamItem(UserExamItem item);

    public void SaveUserExamItem(UserExamItem item);

    // endregion UserExamItem Methods

    // region UserExamDetail Methods

    public List<UserExamDetail> GetListUserExamDetail(UserExamDetail item);

    public void SaveUserExamDetail(UserExamDetail item);

    // endregion UserExamDetail Methods

    // region UserExamRecord Methods

    public UserExamRecord GetUserExamRecord(UserExamRecord item);

    public List<UserExamRecord> GetListUserExamRecord();

    public List<UserExamRecord> SearchUserExamRecord(UserExamRecord item);

    public void SaveUserExamRecord(UserExamRecord item);

    // endregion UserExamRecord Methods

    // region UserTrainDetail Methods

    public List<UserTrainDetail> GetListUserTrainDetail(UserTrainDetail item);

    public void SaveUserTrainDetail(UserTrainDetail item);

    // endregion UserTrainDetail Methods

    // region UserTrain Methods

    public UserTrain GetUserTrain(UserTrain item);

    public List<UserTrain> GetListUserTrain();

    public List<UserTrain> GetListUserTrainByUser(UserTrain item);

    public List<UserTrain> SearchUserTrain(UserTrain item);

    public void SaveUserTrain(UserTrain item);

    // endregion UserTrain Methods

    // region RecordSummary Methods

    public RecordSummary GetRecordSummary(RecordSummary item);

    public List<RecordSummary> GetListRecordSummary();

    public List<RecordSummary> SearchRecordSummary(RecordSummary item);

    public void SaveRecordSummary(RecordSummary item);

    // endregion RecordSummary Methods

    // region UserPlanYearDetail Methods

    public UserPlanYearDetail GetUserPlanYearDetail(UserPlanYearDetail item);

    public List<UserPlanYearDetail> GetListUserPlanYearDetail(UserPlanYearDetail item);

    public List<UserPlanYearDetail> SearchUserPlanYearDetail(UserPlanYearDetail item);

    public void SaveUserPlanYearDetail(UserPlanYearDetail item);

    // endregion UserPlanYearDetail Methods

    // region UserPlanYear Methods

    public UserPlanYear GetUserPlanYear(UserPlanYear item);

    public List<UserPlanYear> SearchUserPlanYear(UserPlanYear item);

    public List<UserPlanYear> SearchUserPlanYearApproved(UserPlanYear item);

    public void SaveUserPlanYear(UserPlanYear item);

    // endregion UserPlanYear Methods

}
