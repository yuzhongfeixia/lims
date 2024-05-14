package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlDatDao;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.dat.DatClassSource;
import com.alms.entity.dat.DatComputeData;
import com.alms.entity.dat.DatSampleField;
import com.alms.entity.dat.DatSampleParameter;
import com.alms.entity.dat.DatSampleTest;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskSingle;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class DatDao {

    // region DatSampleTest Methods

    public static DatSampleTest GetDatSampleTest(DatSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDatSampleTest(session, item);
        } catch (Exception e) {
            return new DatSampleTest();
        } finally {
            session.close();
        }
    }

    public static DatSampleTest GetDatSampleTest(SqlSession session, DatSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DatSampleTest();
        default:
            return SqlDatDao.GetDatSampleTest(session, item);
        }
    }

    public static List<DatSampleTest> GetListDatSampleTest(DatSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDatSampleTest(session, item);
        } catch (Exception e) {
            return new ArrayList<DatSampleTest>();
        } finally {
            session.close();
        }
    }

    public static List<DatSampleTest> GetListDatSampleTest(SqlSession session, DatSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DatSampleTest>();
        default:
            return SqlDatDao.GetListDatSampleTest(session, item);
        }
    }

    public static List<DatSampleTest> SearchDatSampleTest(DatSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DatSampleTest>();
            default:
                return SqlDatDao.SearchDatSampleTest(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DatSampleTest>();
        } finally {
            session.close();
        }
    }

    public static void SaveDatSampleTest(SqlSession session, DatSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveDatSampleTest(session, item);
            break;
        }
    }

    // endregion DatSampleTest Methods

    // region BusRecord Methods

    public static List<BusRecord> GetListBusRecord(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> GetListBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetListBusRecord(session, item);
        }
    }

    public static List<BusRecord> GetNewBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetNewBusRecord(session, item);
        }
    }

    public static void SaveBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveBusRecord(session, item);
            break;
        }
    }

    public static List<BusRecord> SearchBusRecord(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchBusRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> SearchBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();

        default:
            return SqlDatDao.SearchBusRecord(session, item);
        }
    }

    public static List<BusRecord> SearchBusRecordForSummary(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchBusRecordForSummary(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> SearchBusRecordForSummary(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();

        default:
            return SqlDatDao.SearchBusRecordForSummary(session, item);
        }
    }

    public static List<BusRecord> SearchBusRecordForJudge(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchBusRecordForJudge(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> SearchBusRecordForJudge(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();

        default:
            return SqlDatDao.SearchBusRecordForJudge(session, item);
        }
    }

    public static List<BusRecord> SearchBusRecordForSampleTran(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchBusRecordForSampleTran(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> SearchBusRecordForSampleTran(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();

        default:
            return SqlDatDao.SearchBusRecordForSampleTran(session, item);
        }
    }

    public static BusRecord GetBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusRecord();
        default:
            return SqlDatDao.GetBusRecord(session, item);
        }
    }

    public static List<BusRecordDetail> GetBusRecordDetailByFormSerial(SqlSession session, BusRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordDetail>();
        default:
            return SqlDatDao.GetBusRecordDetailByFormSerial(session, item);
        }
    }

    public static List<BusRecordDetail> GetBusRecordDetailForSumByFormSerial(SqlSession session, BusRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordDetail>();
        default:
            return SqlDatDao.GetBusRecordDetailForSumByFormSerial(session, item);
        }
    }

    public static List<BusRecord> GetBusRecordByTask(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusRecordByTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> GetBusRecordByTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetBusRecordByTask(session, item);
        }
    }

    public static List<BusRecord> GetBusRecordByParaTime(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusRecordByParaTime(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecord> GetBusRecordByParaTime(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetBusRecordByParaTime(session, item);
        }
    }

    public static void SyncBusRecordDetail(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.SyncBusRecordDetail(session, item);
            break;
        }
    }

    public static List<BusRecord> GetBusRecordBySampleTran(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusRecordBySampleTran(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    public static List<BusRecord> GetBusRecordBySampleTran(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetBusRecordBySampleTran(session, item);
        }
    }
    public static List<BusRecord> GetSampleTranBySampleCode(BusRecord item) {
      SqlSession session = DBUtils.getFactory();
      
      try {
        return GetSampleTranBySampleCode(session, item);
      } catch (Exception e) {
        return new ArrayList<BusRecord>();
      } finally {
        session.close();
      }
    }
    
    public static List<BusRecord> GetSampleTranBySampleCode(SqlSession session, BusRecord item) {
      switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
          return new ArrayList<BusRecord>();
        default:
          return SqlDatDao.GetSampleTranBySampleCode(session, item);
      }
    }

    public static List<BusRecord> GetBusRecordForApprove(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusRecordForApprove(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }
    }

    public static List<BusRecord> GetBusRecordForApprove(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecord>();
        default:
            return SqlDatDao.GetBusRecordForApprove(session, item);
        }
    }

    public static void SubmitBusRecord(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.SubmitBusRecord(session, item);
            break;
        }
    }

    // endregion BusRecord Methods

    // region DatClassSource Methods

    public static DatClassSource GetDatClassSource(DatClassSource item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDatClassSource(session, item);
        } catch (Exception e) {
            return new DatClassSource();
        } finally {
            session.close();
        }
    }

    public static DatClassSource GetDatClassSource(SqlSession session, DatClassSource item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DatClassSource();
        default:
            return SqlDatDao.GetDatClassSource(session, item);
        }
    }

    public static List<DatClassSource> GetListDatClassSource(DatClassSource item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDatClassSource(session, item);
        } catch (Exception e) {
            return new ArrayList<DatClassSource>();
        } finally {
            session.close();
        }
    }

    public static List<DatClassSource> GetListDatClassSource(SqlSession session, DatClassSource item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DatClassSource>();
        default:
            return SqlDatDao.GetListDatClassSource(session, item);
        }
    }

    public static List<DatClassSource> SearchDatClassSource(DatClassSource item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DatClassSource>();
            default:
                return SqlDatDao.SearchDatClassSource(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DatClassSource>();
        } finally {
            session.close();
        }
    }

    public static void SaveDatClassSource(SqlSession session, DatClassSource item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveDatClassSource(session, item);
            break;
        }
    }

    // endregion DatClassSource Methods

    // region DatSampleField Methods

    public static DatSampleField GetDatSampleField(DatSampleField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDatSampleField(session, item);
        } catch (Exception e) {
            return new DatSampleField();
        } finally {
            session.close();
        }
    }

    public static DatSampleField GetDatSampleField(SqlSession session, DatSampleField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DatSampleField();
        default:
            return SqlDatDao.GetDatSampleField(session, item);
        }
    }

    public static List<DatSampleField> GetListDatSampleField(DatSampleField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDatSampleField(session, item);
        } catch (Exception e) {
            return new ArrayList<DatSampleField>();
        } finally {
            session.close();
        }
    }

    public static List<DatSampleField> GetListDatSampleField(SqlSession session, DatSampleField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DatSampleField>();
        default:
            return SqlDatDao.GetListDatSampleField(session, item);
        }
    }

    public static List<DatSampleField> SearchDatSampleField(DatSampleField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DatSampleField>();
            default:
                return SqlDatDao.SearchDatSampleField(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DatSampleField>();
        } finally {
            session.close();
        }
    }

    public static void SaveDatSampleField(SqlSession session, DatSampleField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveDatSampleField(session, item);
            break;
        }
    }

    // endregion DatSampleField Methods

    // region DatSampleParameter Methods

    public static void SaveDatSampleParameter(SqlSession session, DatSampleParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveDatSampleParameter(session, item);
            break;
        }
    }

    // endregion DatSampleParameter Methods

    // region DatComputeData Methods

    public static List<DatComputeData> GetComputeDataBySet(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DatComputeData>();
        default:
            return SqlDatDao.GetComputeDataBySet(session, item);
        }
    }

    public static List<DatComputeData> GetComputeDataForBatch(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DatComputeData>();
        default:
            return SqlDatDao.GetComputeDataForBatch(session, item);
        }
    }

    // endregion DatComputeData Methods

    // region BusRecordDetail Methods

    public static List<BusRecordDetail> GetMaxSpecSerial(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordDetail>();
        default:
            return SqlDatDao.GetMaxSpecSerial(session, item);
        }
    }

    // endregion BusRecordDetail Methods

    // region BusReport Methods

    public static BusReport GetBusReport(BusReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusReport(session, item);
        } catch (Exception e) {
            return new BusReport();
        } finally {
            session.close();
        }
    }

    public static BusReport GetBasDev(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusReport();
        default:
            return SqlDatDao.GetBusReport(session, item);
        }
    }

    public static List<BusReport> GetBusReportByTask(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusReportByTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusReport>();
        } finally {
            session.close();
        }
    }

    public static List<BusReport> GetBusReportByTask(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReport>();
        default:
            return SqlDatDao.GetBusReportByTask(session, item);
        }
    }

    public static List<BusReport> GetNewBusReport(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReport>();
        default:
            return SqlDatDao.GetNewBusReport(session, item);
        }
    }

    public static void SaveBusReport(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveBusReport(session, item);
            break;
        }
    }

    public static void SaveBusRecordData(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDatDao.SaveBusRecordData(session, item);
            break;
        }
    }

    public static List<BusReport> SearchBusReport(BusReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return SearchBusReport(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusReport>();
        } finally {
            session.close();
        }
    }

    private static List<BusReport> SearchBusReport(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReport>();
        default:
            return SqlDatDao.SearchBusReport(session, item);
        }
    }

    public static BusReport GetBusReport(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusReport();
        default:
            return SqlDatDao.GetBusReport(session, item);
        }
    }

    public static void SyncBusReport(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.SyncBusReport(session, item);
            break;
        }
    }

    public static void BackBusRecord(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.BackBusRecord(session, item);
            break;
        }
    }

    public static void SubmitBusReport(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.SubmitBusReport(session, item);
            break;
        }
    }

    public static void BackBusReport(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.BackBusReport(session, item);
            break;
        }
    }

    public static void UpdateBusReportInfo(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.UpdateBusReportInfo(session, item);
            break;
        }
    }

    public static void UpdateEnvReportInfo(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.UpdateEnvReportInfo(session, item);
            break;
        }
    }

    public static void UpdateNgEnvReportInfo(SqlSession session, BusReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.UpdateNgEnvReportInfo(session, item);
            break;
        }
    }

    // endregion BusReport Methods

    // region BusReportDetail Methods

    public static List<BusReportDetail> GetBusReportDetailBySerial(SqlSession session, BusReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReportDetail>();
        default:
            return SqlDatDao.GetBusReportDetailBySerial(session, item);
        }
    }

    // endregion BusReportDetail Methods

}
