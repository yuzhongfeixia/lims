package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

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
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlLabDao {

    public static void SaveBusProc(SqlSession session, BusProc item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusProc(item);
    }

    public static List<BusGetNotice> SearchBusGetNotice(SqlSession session, BusGetNotice item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetNotice(item);
    }

    public static List<BusGetNoticeDetail> GetBusGetNoticeDetail(SqlSession session, BusGetNoticeDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusGetNoticeDetail(item);
    }

    public static List<BusTestedUnit> SearchBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusTestedUnit(item);
    }

    public static void SaveBusGetNoticeDetail(SqlSession session, BusGetNoticeDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusGetNoticeDetail(item);

    }

    public static void SaveBusGetNotice(SqlSession session, BusGetNotice item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusGetNotice(item);
    }

    public static BusTestedUnit GetBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusTestedUnit(item);

    }

    public static BusGetNotice GetBusGetNotice(SqlSession session, BusGetNotice item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusGetNotice(item);
    }

    public static List<BusGetNotice> SearchBusGetNoticeForGet(SqlSession session, BusGetNotice item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetNoticeForGet(item);
    }

    // region BusGet Methods

    public static BusGet GetBusGet(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusGet(item);
    }

    public static List<BusGet> GetListBusGet(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusGet(item);
    }

    public static List<BusGet> SearchBusGet(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGet(item);
    }

    public static List<BusGet> SearchBusGetForView(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetForView(item);
    }

    public static void SaveBusGet(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusGet(item);
    }

    public static BusGet GetBusGetBySampleCode(SqlSession session, String item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusGetBySampleCode(item);
    }
    public static BusGet GetBusSamplingGetByNoticeid(SqlSession session, String item) {
      com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
          com.alms.mapper.sqlserver.LabMapper.class);
      return mapper.GetBusSamplingGetByNoticeid(item);
    }

    // endregion BusGet Methods

    // region BusGetDetail Methods

    public static BusGetDetail GetBusGetDetail(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusGetDetail(item);
    }

    public static BusGetDetail GetBusGetDetailForStand(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusGetDetailForStand(item);
    }

    public static List<BusGetDetail> GetListBusGetDetail(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusGetDetail(item);
    }

    public static List<BusGetDetail> GetListBusGetDetailByTask(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusGetDetailByTask(item);
    }

    public static List<BusGetDetail> SearchBusGetDetail(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetDetail(item);
    }

    public static void SaveBusGetDetail(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusGetDetail(item);
    }

    public static List<BusGet> SearchBusGetForConsign(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetForConsign(item);
    }

    public static BusGetDetail GetBusGetDetailBySampleCode(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusGetDetailBySampleCode(item);
    }

    // endregion BusGetDetail Methods

    // region BusConsign Methods

    public static BusConsign GetBusConsign(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusConsign(item);
    }

    public static List<BusConsign> GetListBusConsign(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusConsign(item);
    }

    public static List<BusConsign> SearchBusConsign(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusConsign(item);
    }

    public static void SaveBusConsign(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusConsign(item);
    }

    public static List<BusConsign> SearchBusConsignForTask(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusConsignForTask(item);
    }

    // endregion BusConsign Methods

    // region BusConsignSample Methods

    public static BusConsignSample GetBusConsignSample(SqlSession session, BusConsignSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusConsignSample(item);
    }

    public static List<BusConsignSample> GetListBusConsignSample(SqlSession session, BusConsignSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusConsignSample(item);
    }

    public static List<BusConsignSample> SearchBusConsignSample(SqlSession session, BusConsignSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusConsignSample(item);
    }

    public static void SaveBusConsignSample(SqlSession session, BusConsignSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusConsignSample(item);
    }

    // endregion BusConsignSample Methods

    // region BusConsignParam Methods

    public static BusConsignParam GetBusConsignParam(SqlSession session, BusConsignParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusConsignParam(item);
    }

    public static List<BusConsignParam> GetListBusConsignParam(SqlSession session, BusConsignParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusConsignParam(item);
    }

    public static List<BusConsignParam> SearchBusConsignParam(SqlSession session, BusConsignParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusConsignParam(item);
    }

    public static void SaveBusConsignParam(SqlSession session, BusConsignParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusConsignParam(item);
    }

    // endregion BusConsignParam Methods

    // region BusTask Methods

    public static BusTask GetBusTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTask(item);
    }

    public static BusTask GetBusTaskForSampleCode(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskForSampleCode(item);
    }

    public static List<BusTask> GetListBusTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTask(item);
    }

    public static List<BusTask> GetListBusTaskForCount(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskForCount(item);
    }

    public static List<BusTask> SearchBusTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTask(item);
    }

    public static List<BusTask> SearchBusTaskForSamplecode(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskForSamplecode(item);
    }

    public static List<BusTask> SearchBusTaskForDeal(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskForDeal(item);
    }

    public static List<BusTask> SearchBusTaskAllot(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskAllot(item);
    }

    public static void SaveBusTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTask(item);
    }

    public static void SaveBusTaskSingle(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTaskSingle(item);
    }

    // endregion BusTask Methods

    // region BusTaskFile Methods

    public static List<BusTaskFile> GetListBusTaskFile(SqlSession session, BusTaskFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusTaskFile(item);
    }

    public static void SaveBusTaskFile(SqlSession session, BusTaskFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTaskFile(item);
    }

    // region BusAccFile Methods

    public static List<BusAccFile> GetListBusAccFile(SqlSession session, BusAccFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusAccFile(item);
    }

    public static void SaveBusAccFile(SqlSession session, BusAccFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusAccFile(item);
    }

    // endregion BusAccFile Methods

    // region BusTaskTest Methods

    public static BusTaskTest GetBusTaskTest(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskTest(item);
    }

    public static List<BusTaskTest> GetListBusTaskTest(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskTest(item);
    }

    public static List<BusTaskTest> GetListBusTaskTestForMore(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskTestForMore(item);
    }

    public static List<BusTaskTest> SearchBusTaskTest(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskTest(item);
    }

    public static void SaveBusTaskTest(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskTest(item);
    }

    public static List<BusTaskTest> GetListBusTaskParam(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskParam(item);
    }

    public static List<BusTaskTest> GetJudgeTasks(SqlSession session, BusTaskTest item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetJudgeTasks(item);
    }

    // endregion BusTaskTest Methods

    // region BusTaskDev Methods

    public static BusTaskDev GetBusTaskDev(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskDev(item);
    }

    public static List<BusTaskDev> GetListBusTaskDev(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskDev(item);
    }

    public static List<BusTaskDev> GetListBusTaskDevByDevID(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskDevByDevID(item);
    }

    public static List<BusTaskDev> GetListBusTaskDevBySamplecode(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskDevBySamplecode(item);
    }

    public static List<BusTaskDev> SearchBusTaskDev(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskDev(item);
    }

    public static void SaveBusTaskDev(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskDev(item);
    }

    // endregion BusTaskDev Methods

    // region BusTaskData Methods

    public static BusTaskData GetBusTaskData(SqlSession session, BusTaskData item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskData(item);
    }

    public static List<BusTaskData> GetListBusTaskData(SqlSession session, BusTaskData item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskData(item);
    }

    public static List<BusTaskData> SearchBusTaskData(SqlSession session, BusTaskData item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskData(item);
    }

    public static void SaveBusTaskData(SqlSession session, BusTaskData item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskData(item);
    }

    // endregion BusTaskData Methods

    // region BusTaskSample Methods

    public static BusTaskSample GetBusTaskSample(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskSample(item);
    }

    public static List<BusTaskSample> GetListBusTaskSample(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskSample(item);
    }

    public static List<BusTaskSample> GetListBusTaskForAcc(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskForAcc(item);
    }

    public static List<BusTaskSample> SearchBusTaskSample(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskSample(item);
    }

    public static void SaveBusTaskSample(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskSample(item);
    }

    public static void SaveBusRecordDataBySampletran(SqlSession session, BusRecordData item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusRecordDataBySampletran(item);
    }

    // public static List<BusTaskSingle> SearchBusTaskBegin(SqlSession session,
    // BusTaskSingle item) {
    // com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
    // com.alms.mapper.sqlserver.LabMapper.class);
    // return mapper.SearchBusTaskBegin(item);
    // }

    // endregion BusTaskSample Methods

    public static void ComputeBusTask(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.ComputeBusTask(item);
    }

    public static BusConsignSample GetBusConsignSampleByTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusConsignSampleByTask(item);
    }

    // public static List<BusTaskSingle> SearchBusTaskEnd(SqlSession session,
    // BusTaskSingle item) {
    // com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
    // com.alms.mapper.sqlserver.LabMapper.class);
    // return mapper.SearchBusTaskEnd(item);
    // }

    public static List<BusTaskSample> GetBusTaskSampleByTask(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusTaskSampleByTask(item);
    }

    public static List<BusTaskSample> GetBusTaskSampleForJudge(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusTaskSampleForJudge(item);
    }

    public static List<BusTaskSample> GetBusTaskSampleForFinsh(SqlSession session, BusTaskSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusTaskSampleForFinsh(item);
    }

    public static List<BusRecordData> GetDataByTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetDataByTask(item);
    }

    public static SysUser GetLabLeader(SqlSession session, SysUser item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetLabLeader(item);
    }

    public static List<BusConsignParam> GetBusConsignParamByLab(SqlSession session, BusConsignParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusConsignParamByLab(item);
    }

    public static void UpdateParamInfo(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.UpdateParamInfo(item);
    }

    public static void UpdateParamForZjy(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.UpdateParamForZjy(item);
    }

    public static List<BusTaskSample> GetConsignCode(SqlSession session, BusConsign item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetConsignCode(item);
    }

    // region BusSampleParam Methods

    public static BusSampleParam GetBusSampleParam(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusSampleParam(item);
    }

    public static List<BusSampleParam> GetListBusSampleParam(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.full.toString());
        return mapper.GetListBusSampleParam(item);
    }

    public static BusSampleParam GetBusSampleParamByParameter(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusSampleParamByParameter(item);
    }

    public static BusSampleParam GetBusSampleParamByStand(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamByStand(item);
    }

    public static List<BusSampleParam> GetListBusSampleParamForTask(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusSampleParamForTask(item);
    }

    public static List<BusSampleParam> GetListBusSampleParamByTask(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusSampleParamByTask(item);
    }

    public static List<BusSampleParam> GetListBusSampleParamByAcc(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusSampleParamByAcc(item);
    }

    public static List<BusSampleParam> SearchBusSampleParam(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusSampleParam(item);
    }

    public static void SaveBusSampleParam(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusSampleParam(item);
    }

    public static List<BusSampleParam> GetBusSampleParamBySampleCode(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamBySampleCode(item);
    }

    public static List<BasMainParameter> GetBusSampleParamByLab(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamByLab(item);
    }

    public static List<BasMainParameter> GetBasSampleReplaceByLab(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBasSampleReplaceByLab(item);
    }

    public static BusSampleParam GetBusSampleParamForJudge(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamForJudge(item);
    }

    public static BusSampleParam GetBusSampleParamForBatchJudge(SqlSession session, BusSampleParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamForBatchJudge(item);
    }

    // endregion BusSampleParam Methods

    // region BusCatalogParam Methods

    public static BusCatalogParam GetBusCatalogParam(SqlSession session, BusCatalogParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusCatalogParam(item);
    }

    public static List<BusCatalogParam> GetListBusCatalogParam(SqlSession session, BusCatalogParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusCatalogParam(item);
    }

    public static List<BusCatalogParam> SearchBusCatalogParam(SqlSession session, BusCatalogParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusCatalogParam(item);
    }

    public static void SaveBusCatalogParam(SqlSession session, BusCatalogParam item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusCatalogParam(item);
    }

    // public static List<BusCatalogParam> GetListCatalogParamBySample(SqlSession
    // session, String item) {
    // com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
    // com.alms.mapper.sqlserver.LabMapper.class);
    // return mapper.GetListCatalogParamBySample(item);
    // }

    // endregion BusCatalogParam Methods

    // region BusAccSample Methods

    public static BusAccSample GetBusAccSample(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusAccSample(item);
    }

    public static BusAccSample GetBusAccSampleByGetID(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusAccSampleByGetID(item);
    }

    public static BusAccSample GetBusAccSampleByTranID(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusAccSampleByTranID(item);
    }

    public static List<BusAccSample> GetListBusAccSample(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusAccSample(item);
    }

    public static List<BusAccSample> SearchBusAccSample(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusAccSample(item);
    }

    public static List<BusAccSample> SearchBusAccSampleForBatch(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusAccSampleForBatch(item);
    }

    public static void SaveBusAccSample(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusAccSample(item);
    }

    public static List<BusGet> SearchBusGetForAcc(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusGetForAcc(item);
    }

    public static List<BusAccSample> GetBusAccSampleBySampleCode(SqlSession session, BusAccSample item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusAccSampleBySampleCode(item);
    }

    // endregion BusAccSample Methods

    // region BusTaskTester Methods

    public static BusTaskTester GetBusTaskTester(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskTester(item);
    }

    public static List<BusTaskTester> GetListBusTaskTester(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskTester(item);
    }

    public static List<BusTaskTester> GetListBusTaskTesterByUser(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskTesterByUser(item);
    }

    public static List<BusTaskTester> SearchBusTaskTester(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchBusTaskTester(item);
    }

    public static void SaveBusTaskTester(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskTester(item);
    }

    public static List<BusTaskTester> GetListBusTaskTesterBySingle(SqlSession session, BusTaskTester item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusTaskTesterBySingle(item);
    }

    // endregion BusTaskTester Methods

    // region BusTaskSingle Methods

    public static BusTaskSingle GetBusTaskSingle(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusTaskSingle(item);
    }

    public static BusTaskSingle GetBusTaskSingleByTaskid(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusTaskSingleByTaskid(item);
    }

    public static BusTaskSingle GetBusTaskSingleBySampleTran(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusTaskSingleBySampleTran(item);
    }

    public static List<BusTaskSingle> GetBusTaskSingleByTranID(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusTaskSingleByTranID(item);
    }

    public static void SaveBusTaskSingleLab(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTaskSingleLab(item);
    }

    public static List<BusTaskSingle> SearchBusTaskSingle(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusTaskSingle(item);
    }

    public static List<BusTaskSingle> SearchBusTaskRecord(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusTaskRecord(item);
    }

    public static List<BusTaskSingle> SearchBusTaskJudge(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.SearchBusTaskJudge(item);
    }

    // endregion BusTaskSingle Methods

    // region BusMainParameter Methods

    public static List<BasMainParameter> GetBusSampleParamBySample(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamBySample(item);
    }

    public static List<BasMainParameter> GetBusSampleParamByConsign(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSampleParamByConsign(item);
    }

    public static List<BasMainParameter> GetBasSampleParamForCombo(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBasSampleParamForCombo(item);
    }

    // endregion BusMainParameter Methods

    // region BusTaskAttach Methods

    public static List<BusTaskAttach> GetListBusTaskAttach(SqlSession session, BusTaskAttach item) {
        if (item.getAttachtype() == "05" || item.getAttachtype().equals("05")) {
            com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                    com.alms.mapper.sqlserver.LabMapper.class);
            return mapper.GetListFlowAttach(item);
        }
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusTaskAttach(item);
    }

    public static List<BusTask> GetListBusTaskManyAllot(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusTaskManyAllot(item);
    }

    public static void SaveBusTaskAttach(SqlSession session, BusTaskAttach item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTaskAttach(item);
    }

    // endregion BusTaskAttach Methods

    // region BusRecordFile Methods

    public static List<BusRecordFile> GetListBusRecordFile(SqlSession session, BusRecordFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusRecordFile(item);
    }

    public static void SaveBusRecordFile(SqlSession session, BusRecordFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusRecordFile(item);
    }

    // endregion BusRecordFile Methods

    // region BusReportFile Methods

    public static List<BusReportFile> GetListBusReportFile(SqlSession session, BusReportFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetListBusReportFile(item);
    }

    public static void SaveBusReportFile(SqlSession session, BusReportFile item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusReportFile(item);
    }

    // endregion BusReportFile Methods

    // region BusSelect Methods

    public static List<BusSelect> GetBusSelectByCode(SqlSession session, BusSelect item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetBusSelectByCode(item);
    }

    // endregion BusSelect Methods

    // region BusTaskJudge Methods

    public static BusTaskJudge GetBusTaskJudge(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTaskJudge(item);
    }

    public static BusTaskJudge GetBusTaskJudgeForInfo(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetBusTaskJudgeForInfo(item);
    }

    public static List<BusTaskJudge> GetListBusTaskJudge(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskJudge(item);
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForNo(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskJudgeForNo(item);
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForYes(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskJudgeForYes(item);
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeBySampleCode(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskJudgeBySampleCode(item);
    }

    public static void SaveBusTaskJudge(SqlSession session, BusTaskJudge item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveBusTaskJudge(item);
    }

    // endregion BusTaskJudge Methods

    // region SignerPassword Methods

    public static SignerPassword GetSignerPassword(SqlSession session, SignerPassword item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSignerPassword(item);
    }

    public static List<SignerPassword> GetListSignerPassword(SqlSession session, SignerPassword item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListSignerPassword(item);
    }

    public static List<SignerPassword> SearchSignerPassword(SqlSession session, SignerPassword item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.SearchSignerPassword(item);
    }

    public static void SaveSignerPassword(SqlSession session, SignerPassword item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        mapper.SaveSignerPassword(item);
    }

    // region BusTaskResult Methods

    public static List<BusTaskResult> GetListBusTaskResult(SqlSession session, BusTaskResult item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskResult(item);
    }

    public static List<BusTaskResult> GetListBusTaskResultForIn(SqlSession session, BusTaskResult item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskResultForIn(item);
    }

    public static List<BusTaskResult> GetListBusTaskResultForReport(SqlSession session, BusTaskResult item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskResultForReport(item);
    }

    // endregion BusTaskResult Methods

    // region BusTaskLab Methods

    public static List<BusTaskLab> GetListBusTaskLab(SqlSession session, BusTaskLab item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);

        return mapper.GetListBusTaskLab(item);
    }

    public static void SaveBusTaskLab(SqlSession session, BusTaskLab item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        mapper.SaveBusTaskLab(item);
    }

    public static BusGetDetail GetGetSourceName(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetGetSourceName(item);
    }

    public static BusGetDetail GetKeepWarmName(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.LabMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabMapper.class);
        return mapper.GetKeepWarmName(item);
    }
    // endregion BusTaskLab Methods
}
