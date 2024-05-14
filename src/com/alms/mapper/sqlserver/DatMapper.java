package com.alms.mapper.sqlserver;

import java.util.List;

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
import com.gpersist.mapper.BaseMapper;

public interface DatMapper extends BaseMapper {

    // region DatSampleTest Methods

    public DatSampleTest GetDatSampleTest(DatSampleTest item);

    public List<DatSampleTest> GetListDatSampleTest(DatSampleTest item);

    public List<DatSampleTest> SearchDatSampleTest(DatSampleTest item);

    public void SaveDatSampleTest(DatSampleTest item);

    // endregion DatSampleTest Methods

    // region BusRecord Methods

    public List<BusRecord> GetListBusRecord(BusRecord item);

    public List<BusRecord> GetNewBusRecord(BusRecord item);

    public void SaveBusRecord(BusRecord item);

    public List<BusRecord> SearchBusRecord(BusRecord item);

    public List<BusRecord> SearchBusRecordForSummary(BusRecord item);

    public List<BusRecord> SearchBusRecordForJudge(BusRecord item);

    public List<BusRecord> SearchBusRecordForSampleTran(BusRecord item);

    public BusRecord GetBusRecord(BusRecord item);

    public List<BusRecordDetail> GetBusRecordDetailByFormSerial(BusRecordDetail item);

    public List<BusRecordDetail> GetBusRecordDetailForSumByFormSerial(BusRecordDetail item);

    public List<BusRecord> GetBusRecordByTask(BusTask item);

    public List<BusRecord> GetBusRecordByParaTime(BusRecord item);

    public List<BusRecord> GetBusRecordBySampleTran(BusRecord item);
    
    public List<BusRecord> GetSampleTranBySampleCode(BusRecord item);

    public List<BusRecord> GetBusRecordForApprove(BusRecord item);

    // endregion BusRecord Methods

    // region BusRecordDetail Methods

    public void SyncBusRecordDetail(BusTaskSingle item);

    public List<BusRecordDetail> GetMaxSpecSerial(BusTaskSingle item);

    // endregion BusRecordDetail Methods

    // region DatClassSource Methods

    public DatClassSource GetDatClassSource(DatClassSource item);

    public List<DatClassSource> GetListDatClassSource(DatClassSource item);

    public List<DatClassSource> SearchDatClassSource(DatClassSource item);

    public void SaveDatClassSource(DatClassSource item);

    // endregion DatClassSource Methods

    // region DatSampleField Methods

    public DatSampleField GetDatSampleField(DatSampleField item);

    public List<DatSampleField> GetListDatSampleField(DatSampleField item);

    public List<DatSampleField> SearchDatSampleField(DatSampleField item);

    public void SaveDatSampleField(DatSampleField item);

    // endregion DatSampleField Methods

    // region DatSampleParameter Methods

    public void SaveDatSampleParameter(DatSampleParameter item);

    // endregion DatSampleParameter Methods

    // region DatComputeData Methods

    public List<DatComputeData> GetComputeDataBySet(BusTaskSingle item);

    public List<DatComputeData> GetComputeDataForBatch(BusTaskSingle item);

    // endregion DatComputeData Methods

    // region BusReport Methods

    public List<BusReport> GetBusReportByTask(BusTaskSingle item);

    public List<BusReport> GetNewBusReport(BusTaskSingle item);

    public void SaveBusReport(BusReport item);

    public void SaveBusRecordData(BusReport item);

    public List<BusReport> SearchBusReport(BusReport item);

    public BusReport GetBusReport(BusReport item);

    public void SyncBusReport(BusTaskSingle item);

    public void BackBusRecord(BusReport item);

    public void SubmitBusReport(BusReport item);

    public void SubmitBusRecord(BusRecord item);

    public void BackBusReport(BusReport item);

    public void UpdateBusReportInfo(BusReport item);

    public void UpdateEnvReportInfo(BusReport item);

    public void UpdateNgEnvReportInfo(BusReport item);

    // endregion BusReport Methods

    // region BusReportDetail Methods

    public List<BusReportDetail> GetBusReportDetailBySerial(BusReportDetail item);

    public void SubmitBusRecordSamples(BusRecordSamples brsample);

    public BusRecordSamples SearchBusRecordSamples(String taskid);

    // endregion BusReportDetail Methods

}
