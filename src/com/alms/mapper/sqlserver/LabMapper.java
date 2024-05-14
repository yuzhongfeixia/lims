package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.bas.BasMainParameter;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.lab.BusAccFile;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusReportFile;
import com.alms.entity.lab.BusTaskFile;
import com.alms.entity.lab.BusTaskLab;
import com.alms.entity.lab.BusTaskResult;
import com.alms.entity.lab.SignerPassword;
import com.alms.entity.lab.BusAccSample;
import com.alms.entity.lab.BusCatalogParam;
import com.alms.entity.lab.BusConsign;
import com.alms.entity.lab.BusConsignParam;
import com.alms.entity.lab.BusConsignSample;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusGetNoticeDetail;
import com.alms.entity.lab.BusProc;
import com.alms.entity.lab.BusRecordData;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.lab.BusSelect;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskData;
import com.alms.entity.lab.BusTaskDev;
import com.alms.entity.lab.BusTaskJudge;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.BusTaskTest;
import com.alms.entity.lab.BusTaskTester;
import com.gpersist.entity.user.SysUser;

public interface LabMapper extends BasMapper {

    public void SaveBusProc(BusProc item);

    public List<BusGetNotice> SearchBusGetNotice(BusGetNotice item);

    public List<BusGetNoticeDetail> GetBusGetNoticeDetail(BusGetNoticeDetail item);

    public List<BusTestedUnit> SearchBusTestedUnit(BusTestedUnit item);

    public void SaveBusGetNoticeDetail(BusGetNoticeDetail item);

    public void SaveBusGetNotice(BusGetNotice item);

    public BusTestedUnit GetBusTestedUnit(BusTestedUnit item);

    public BusGetNotice GetBusGetNotice(BusGetNotice item);

    public List<BusGetNotice> SearchBusGetNoticeForGet(BusGetNotice item);

    // region BusGet Methods

    public BusGet GetBusGet(BusGet item);

    public List<BusGet> GetListBusGet(BusGet item);

    public List<BusGet> SearchBusGet(BusGet item);

    public List<BusGet> SearchBusGetForView(BusGet item);

    public void SaveBusGet(BusGet item);

    public BusGet GetBusGetBySampleCode(String item);
    
    public BusGet GetBusSamplingGetByNoticeid(String item);

    // endregion BusGet Methods

    // region BusGetDetail Methods

    public BusGetDetail GetBusGetDetail(BusGetDetail item);

    public BusGetDetail GetBusGetDetailForStand(BusGetDetail item);

    public List<BusGetDetail> GetListBusGetDetail(BusGetDetail item);

    public List<BusGetDetail> GetListBusGetDetailByTask(BusGetDetail item);

    public List<BusGetDetail> SearchBusGetDetail(BusGetDetail item);

    public void SaveBusGetDetail(BusGetDetail item);

    public List<BusGet> SearchBusGetForConsign(BusGet item);

    public BusGetDetail GetBusGetDetailBySampleCode(BusGetDetail item);

    // endregion BusGetDetail Methods

    // region BusConsign Methods

    public BusConsign GetBusConsign(BusConsign item);

    public List<BusConsign> GetListBusConsign(BusConsign item);

    public List<BusConsign> SearchBusConsign(BusConsign item);

    public void SaveBusConsign(BusConsign item);

    public List<BusConsign> SearchBusConsignForTask(BusConsign item);

    // endregion BusConsign Methods

    // region BusConsignSample Methods

    public BusConsignSample GetBusConsignSample(BusConsignSample item);

    public List<BusConsignSample> GetListBusConsignSample(BusConsignSample item);

    public List<BusConsignSample> SearchBusConsignSample(BusConsignSample item);

    public void SaveBusConsignSample(BusConsignSample item);

    // endregion BusConsignSample Methods

    // region BusConsignParam Methods

    public BusConsignParam GetBusConsignParam(BusConsignParam item);

    public List<BusConsignParam> GetListBusConsignParam(BusConsignParam item);

    public List<BusConsignParam> SearchBusConsignParam(BusConsignParam item);

    public void SaveBusConsignParam(BusConsignParam item);

    // endregion BusConsignParam Methods

    // region BusTask Methods

    public BusTask GetBusTask(BusTask item);

    public BusTask GetBusTaskForSampleCode(BusTask item);

    public List<BusTask> GetListBusTask(BusTask item);

    public List<BusTask> GetListBusTaskForCount(BusTask item);

    public List<BusTask> SearchBusTask(BusTask item);

    public List<BusTask> SearchBusTaskForSamplecode(BusTask item);

    public List<BusTask> SearchBusTaskForDeal(BusTask item);

    public List<BusTask> SearchBusTaskAllot(BusTask item);

    public void SaveBusTask(BusTask item);

    public void SaveBusTaskSingle(BusTask item);

    // endregion BusTask Methods

    // region BusTaskTest Methods

    public BusTaskTest GetBusTaskTest(BusTaskTest item);

    public List<BusTaskTest> GetListBusTaskTest(BusTaskTest item);

    public List<BusTaskTest> GetListBusTaskTestForMore(BusTaskTest item);

    public List<BusTaskTest> SearchBusTaskTest(BusTaskTest item);

    public void SaveBusTaskTest(BusTaskTest item);

    public List<BusTaskTest> GetListBusTaskParam(BusTaskTest item);

    public List<BusTaskTest> GetJudgeTasks(BusTaskTest item);

    // endregion BusTaskTest Methods

    // region BusTaskDev Methods

    public BusTaskDev GetBusTaskDev(BusTaskDev item);

    public List<BusTaskDev> GetListBusTaskDev(BusTaskDev item);

    public List<BusTaskDev> GetListBusTaskDevByDevID(BusTaskDev item);

    public List<BusTaskDev> GetListBusTaskDevBySamplecode(BusTaskDev item);

    public List<BusTaskDev> SearchBusTaskDev(BusTaskDev item);

    public void SaveBusTaskDev(BusTaskDev item);

    // endregion BusTaskDev Methods

    // region BusTaskData Methods

    public BusTaskData GetBusTaskData(BusTaskData item);

    public List<BusTaskData> GetListBusTaskData(BusTaskData item);

    public List<BusTaskData> SearchBusTaskData(BusTaskData item);

    public void SaveBusTaskData(BusTaskData item);

    // endregion BusTaskData Methods

    // region BusTaskSample Methods

    public BusTaskSample GetBusTaskSample(BusTaskSample item);

    public List<BusTaskSample> GetListBusTaskSample(BusTaskSample item);

    public List<BusTaskSample> GetListBusTaskForAcc(BusTaskSample item);

    public List<BusTaskSample> SearchBusTaskSample(BusTaskSample item);

    public void SaveBusTaskSample(BusTaskSample item);

    public void SaveBusRecordDataBySampletran(BusRecordData item);

    // public List<BusTaskSingle> SearchBusTaskBegin(BusTaskSingle item);

    public List<BasMainParameter> GetBusSampleParamByLab(BasMainParameter item);

    public List<BasMainParameter> GetBasSampleReplaceByLab(BasMainParameter item);

    // endregion BusTaskSample Methods

    public void ComputeBusTask(BusTaskSingle item);

    public BusConsignSample GetBusConsignSampleByTask(BusTask item);

    // public List<BusTaskSingle> SearchBusTaskEnd(BusTaskSingle item);

    public List<BusTaskSample> GetBusTaskSampleByTask(BusTaskSample item);

    public List<BusTaskSample> GetBusTaskSampleForJudge(BusTaskSample item);

    public List<BusTaskSample> GetBusTaskSampleForFinsh(BusTaskSample item);

    public List<BusRecordData> GetDataByTask(BusTask item);

    public SysUser GetLabLeader(SysUser item);

    public List<BusConsignParam> GetBusConsignParamByLab(BusConsignParam item);

    public void UpdateParamInfo(BusTaskTester item);

    public void UpdateParamForZjy(BusTaskTester item);

    public List<BusTaskSample> GetConsignCode(BusConsign item);

    // region BusSampleParam Methods

    public BusSampleParam GetBusSampleParam(BusSampleParam item);

    public BusSampleParam GetBusSampleParamByParameter(BusSampleParam item);

    public BusSampleParam GetBusSampleParamByStand(BusSampleParam item);

    public List<BusSampleParam> GetListBusSampleParam(BusSampleParam item);

    public List<BusSampleParam> GetListBusSampleParamForTask(BusSampleParam item);

    public List<BusSampleParam> GetListBusSampleParamByTask(BusSampleParam item);

    public List<BusSampleParam> GetListBusSampleParamByAcc(BusSampleParam item);

    public List<BusSampleParam> SearchBusSampleParam(BusSampleParam item);

    public void SaveBusSampleParam(BusSampleParam item);

    public List<BusSampleParam> GetBusSampleParamBySampleCode(BusSampleParam item);

    public BusSampleParam GetBusSampleParamForJudge(BusSampleParam item);

    public BusSampleParam GetBusSampleParamForBatchJudge(BusSampleParam item);

    // endregion BusSampleParam Methods

    // region BusCatalogParam Methods

    public BusCatalogParam GetBusCatalogParam(BusCatalogParam item);

    public List<BusCatalogParam> GetListBusCatalogParam(BusCatalogParam item);

    public List<BusCatalogParam> SearchBusCatalogParam(BusCatalogParam item);

    public void SaveBusCatalogParam(BusCatalogParam item);

    // public List<BusCatalogParam> GetListCatalogParamBySample(String item);

    // endregion BusCatalogParam Methods

    // region BusAccSample Methods

    public BusAccSample GetBusAccSample(BusAccSample item);

    public BusAccSample GetBusAccSampleByGetID(BusAccSample item);

    public BusAccSample GetBusAccSampleByTranID(BusAccSample item);

    public List<BusAccSample> GetListBusAccSample(BusAccSample item);

    public List<BusAccSample> SearchBusAccSample(BusAccSample item);

    public List<BusAccSample> SearchBusAccSampleForBatch(BusAccSample item);

    public void SaveBusAccSample(BusAccSample item);

    public List<BusGet> SearchBusGetForAcc(BusGet item);

    public List<BusAccSample> GetBusAccSampleBySampleCode(BusAccSample item);

    // endregion BusAccSample Methods

    // region BusTaskTester Methods

    public BusTaskTester GetBusTaskTester(BusTaskTester item);

    public List<BusTaskTester> GetListBusTaskTester(BusTaskTester item);

    public List<BusTaskTester> GetListBusTaskTesterByUser(BusTaskTester item);

    public List<BusTaskTester> SearchBusTaskTester(BusTaskTester item);

    public void SaveBusTaskTester(BusTaskTester item);

    public List<BusTaskTester> GetListBusTaskTesterBySingle(BusTaskTester item);

    // endregion BusTaskTester Methods

    // region BusTaskSingle Methods

    public BusTaskSingle GetBusTaskSingle(BusTaskSingle item);

    public BusTaskSingle GetBusTaskSingleByTaskid(BusTaskSingle item);

    public BusTaskSingle GetBusTaskSingleBySampleTran(BusTaskSingle item);

    public List<BusTaskSingle> GetBusTaskSingleByTranID(BusTaskSingle item);

    public void SaveBusTaskSingleLab(BusTaskSingle item);

    public List<BusTaskSingle> SearchBusTaskSingle(BusTaskSingle item);

    public List<BusTaskSingle> SearchBusTaskRecord(BusTaskSingle item);

    public List<BusTaskSingle> SearchBusTaskJudge(BusTaskSingle item);

    // endregion BusTaskSingle Methods

    // region BusMainParameter Methods

    public List<BasMainParameter> GetBusSampleParamBySample(BasMainParameter item);

    public List<BasMainParameter> GetBusSampleParamByConsign(BasMainParameter item);

    public List<BasMainParameter> GetBasSampleParamForCombo(BasMainParameter item);

    // endregion BusMainParameter Methods

    // region BusTaskFile Methods

    public List<BusTaskFile> GetListBusTaskFile(BusTaskFile item);

    public void SaveBusTaskFile(BusTaskFile item);

    // endregion BusTaskFile Methods

    // region BusAccFile Methods

    public List<BusAccFile> GetListBusAccFile(BusAccFile item);

    public void SaveBusAccFile(BusAccFile item);

    // endregion BusTaskFile Methods

    // region BusTaskAttach Methods

    public List<BusTaskAttach> GetListBusTaskAttach(BusTaskAttach item);

    public List<BusTask> GetListBusTaskManyAllot(BusTask item);

    public List<BusTaskAttach> GetListFlowAttach(BusTaskAttach item);

    public void SaveBusTaskAttach(BusTaskAttach item);

    // endregion BusTaskAttach Methods

    // region BusRecordFile Methods

    public List<BusRecordFile> GetListBusRecordFile(BusRecordFile item);

    public void SaveBusRecordFile(BusRecordFile item);

    // endregion BusRecordFile Methods

    // region BusReportFile Methods

    public List<BusReportFile> GetListBusReportFile(BusReportFile item);

    public void SaveBusReportFile(BusReportFile item);

    // endregion BusReportFile Methods

    // region BusSelect Methods

    public List<BusSelect> GetBusSelectByCode(BusSelect item);

    // endregion BusSelect Methods

    // region BusTaskJudge Methods

    public BusTaskJudge GetBusTaskJudge(BusTaskJudge item);

    public BusTaskJudge GetBusTaskJudgeForInfo(BusTaskJudge item);

    public List<BusTaskJudge> GetListBusTaskJudge(BusTaskJudge item);

    public List<BusTaskJudge> GetListBusTaskJudgeForNo(BusTaskJudge item);

    public List<BusTaskJudge> GetListBusTaskJudgeForYes(BusTaskJudge item);

    public List<BusTaskJudge> GetListBusTaskJudgeBySampleCode(BusTaskJudge item);

    public void SaveBusTaskJudge(BusTaskJudge item);

    // endregion BusTaskJudge Methods

    // region SignerPassword Methods

    public SignerPassword GetSignerPassword(SignerPassword item);

    public List<SignerPassword> GetListSignerPassword(SignerPassword item);

    public List<SignerPassword> SearchSignerPassword(SignerPassword item);

    public void SaveSignerPassword(SignerPassword item);

    // endregion SignerPassword Methods

    // region BusTaskResult Methods

    public List<BusTaskResult> GetListBusTaskResult(BusTaskResult item);

    public List<BusTaskResult> GetListBusTaskResultForIn(BusTaskResult item);

    public List<BusTaskResult> GetListBusTaskResultForReport(BusTaskResult item);

    // endregion BusTaskResult Methods

    // region BusTaskLab Methods

    public List<BusTaskLab> GetListBusTaskLab(BusTaskLab item);

    public void SaveBusTaskLab(BusTaskLab item);

    public BusGetDetail GetGetSourceName(BusGetDetail item);

    public BusGetDetail GetKeepWarmName(BusGetDetail item);

    // endregion BusTaskLab Methods
}
