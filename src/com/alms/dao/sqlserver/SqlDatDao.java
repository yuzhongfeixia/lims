package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.dat.DatClassSource;
import com.alms.entity.dat.DatComputeData;
import com.alms.entity.dat.DatSampleField;
import com.alms.entity.dat.DatSampleParameter;
import com.alms.entity.dat.DatSampleTest;
import com.alms.entity.lab.BusRecordSamples;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskSingle;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlDatDao {

    // region DatSampleTest Methods

    public static DatSampleTest GetDatSampleTest(SqlSession session, DatSampleTest item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDatSampleTest(item);
    }

    public static List<DatSampleTest> GetListDatSampleTest(SqlSession session, DatSampleTest item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.GetListDatSampleTest(item);
    }

    public static List<DatSampleTest> SearchDatSampleTest(SqlSession session, DatSampleTest item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.SearchDatSampleTest(item);
    }

    public static void SaveDatSampleTest(SqlSession session, DatSampleTest item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        mapper.SaveDatSampleTest(item);
    }

    // endregion DatSampleTest Methods

    // region BusRecord Methods

    public static List<BusRecord> GetListBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetListBusRecord(item);
    }

    public static List<BusRecord> GetNewBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetNewBusRecord(item);
    }

    public static void SaveBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SaveBusRecord(item);
    }

    public static List<BusRecord> SearchBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.SearchBusRecord(item);
    }

    public static List<BusRecord> SearchBusRecordForSummary(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.SearchBusRecordForSummary(item);
    }

    public static List<BusRecord> SearchBusRecordForJudge(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.SearchBusRecordForJudge(item);
    }

    public static List<BusRecord> SearchBusRecordForSampleTran(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.SearchBusRecordForSampleTran(item);
    }

    public static BusRecord GetBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecord(item);
    }

    public static List<BusRecord> GetBusRecordBySampleTran(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordBySampleTran(item);
    }
    public static List<BusRecord> GetSampleTranBySampleCode(SqlSession session, BusRecord item) {
      com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
          com.alms.mapper.sqlserver.DatMapper.class);
      return mapper.GetSampleTranBySampleCode(item);
    }

    public static List<BusRecord> GetBusRecordForApprove(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordForApprove(item);
    }

    public static void SubmitBusRecord(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SubmitBusRecord(item);
    }

    // endregion BusRecord Methods

    // region BusRecordDetail Methods

    public static List<BusRecordDetail> GetBusRecordDetailByFormSerial(SqlSession session, BusRecordDetail item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordDetailByFormSerial(item);
    }

    public static List<BusRecordDetail> GetBusRecordDetailForSumByFormSerial(SqlSession session, BusRecordDetail item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordDetailForSumByFormSerial(item);
    }

    public static List<BusRecord> GetBusRecordByTask(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordByTask(item);
    }

    public static List<BusRecord> GetBusRecordByParaTime(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusRecordByParaTime(item);
    }

    public static void SyncBusRecordDetail(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SyncBusRecordDetail(item);
    }

    public static List<BusRecordDetail> GetMaxSpecSerial(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetMaxSpecSerial(item);
    }

    // endregion BusRecordDetail Methods

    // region DatClassSource Methods

    public static DatClassSource GetDatClassSource(SqlSession session, DatClassSource item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDatClassSource(item);
    }

    public static List<DatClassSource> GetListDatClassSource(SqlSession session, DatClassSource item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.GetListDatClassSource(item);
    }

    public static List<DatClassSource> SearchDatClassSource(SqlSession session, DatClassSource item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.SearchDatClassSource(item);
    }

    public static void SaveDatClassSource(SqlSession session, DatClassSource item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        mapper.SaveDatClassSource(item);
    }

    // endregion DatClassSource Methods

    // region DatSampleField Methods

    public static DatSampleField GetDatSampleField(SqlSession session, DatSampleField item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDatSampleField(item);
    }

    public static List<DatSampleField> GetListDatSampleField(SqlSession session, DatSampleField item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.GetListDatSampleField(item);
    }

    public static List<DatSampleField> SearchDatSampleField(SqlSession session, DatSampleField item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.SearchDatSampleField(item);
    }

    public static void SaveDatSampleField(SqlSession session, DatSampleField item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        mapper.SaveDatSampleField(item);
    }

    // endregion DatSampleField Methods

    // region DatSampleParameter Methods

    public static void SaveDatSampleParameter(SqlSession session, DatSampleParameter item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        mapper.SaveDatSampleParameter(item);
    }

    // endregion DatSampleParameter Methods

    // region DatComputeData Methods

    public static List<DatComputeData> GetComputeDataBySet(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetComputeDataBySet(item);
    }

    public static List<DatComputeData> GetComputeDataForBatch(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetComputeDataForBatch(item);
    }

    // endregion DatComputeData Methods

    // region BusReport Methods

    public static BusReport GetBasDev(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusReport(item);
    }

    public static List<BusReport> GetBusReportByTask(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusReportByTask(item);
    }

    public static List<BusReport> GetNewBusReport(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetNewBusReport(item);
    }

    public static void SaveBusReport(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SaveBusReport(item);
    }

    public static void SaveBusRecordData(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SaveBusRecordData(item);
    }

    public static List<BusReport> SearchBusReport(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.SearchBusReport(item);
    }

    public static BusReport GetBusReport(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        return mapper.GetBusReport(item);
    }

    public static void SyncBusReport(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SyncBusReport(item);
    }

    public static void BackBusRecord(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.BackBusRecord(item);
    }

    public static void SubmitBusReport(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SubmitBusReport(item);
    }

    public static void BackBusReport(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.BackBusReport(item);
    }

    public static void UpdateBusReportInfo(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.UpdateBusReportInfo(item);
    }

    public static void UpdateEnvReportInfo(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.UpdateEnvReportInfo(item);
    }

    public static void UpdateNgEnvReportInfo(SqlSession session, BusReport item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.UpdateNgEnvReportInfo(item);
    }

    // endregion BusReport Methods

    // region BusReportDetail Methods

    public static List<BusReportDetail> GetBusReportDetailBySerial(SqlSession session, BusReportDetail item) {
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.GetBusReportDetailBySerial(item);
    }

    public static void SubmitBusRecordSamples(SqlSession session, BusRecordSamples brsample) {

        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);
        mapper.SubmitBusRecordSamples(brsample);
    }

    public static BusRecordSamples SearchBusRecordSamples(SqlSession session, String taskid) {
        // TODO Auto-generated method stub
        com.alms.mapper.sqlserver.DatMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DatMapper.class);

        return mapper.SearchBusRecordSamples(taskid);
    }

    // endregion BusReportDetail Methods

}
