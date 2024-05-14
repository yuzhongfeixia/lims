package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlFormDao;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.form.CusOrder;
import com.alms.entity.form.CustomerCount;
import com.alms.entity.form.DevType;
import com.alms.entity.form.DevUseCount;
import com.alms.entity.form.FrmGet;
import com.alms.entity.form.FrmGetDetail;
import com.alms.entity.form.FrmRecord;
import com.alms.entity.form.FrmRecordDetail;
import com.alms.entity.form.FrmRecordParameter;
import com.alms.entity.form.FrmReport;
import com.alms.entity.form.FrmReportDetail;
import com.alms.entity.form.IntField;
import com.alms.entity.form.IntInterface;
import com.alms.entity.form.LabParameter;
import com.alms.entity.form.LabSample;
import com.alms.entity.form.PrdCodeCount;
import com.alms.entity.form.PrdIn;
import com.alms.entity.form.PrdOut;
import com.alms.entity.form.PrdType;
import com.alms.entity.form.SamplePass;
import com.alms.entity.form.SendSample;
import com.alms.entity.form.TesterParameter;
import com.alms.entity.form.TesterSample;
import com.alms.entity.lab.BusTaskSingle;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class FormDao {

    // region IntInterface Methods

    public static IntInterface GetIntInterface(IntInterface item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIntInterface(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new IntInterface();
        } finally {
            session.close();
        }
    }

    public static IntInterface GetIntInterface(SqlSession session, IntInterface item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IntInterface();
        default:
            return SqlFormDao.GetIntInterface(session, item);
        }
    }

    public static List<IntInterface> GetIntInterfaceByTask(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIntInterfaceByTask(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<IntInterface>();
        } finally {
            session.close();
        }
    }

    public static List<IntInterface> GetIntInterfaceByTask(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<IntInterface>();
        default:
            return SqlFormDao.GetIntInterfaceByTask(session, item);
        }
    }

    public static List<IntInterface> SearchIntInterface(IntInterface item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IntInterface>();
            default:
                return SqlFormDao.SearchIntInterface(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IntInterface>();
        } finally {
            session.close();
        }
    }

    public static void SaveIntInterface(SqlSession session, IntInterface item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveIntInterface(session, item);
            break;
        }
    }

    // endregion IntInterface Methods

    // region IntField Methods

    public static IntField GetIntField(IntField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIntField(session, item);
        } catch (Exception e) {
            return new IntField();
        } finally {
            session.close();
        }
    }

    public static IntField GetIntField(SqlSession session, IntField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IntField();
        default:
            return SqlFormDao.GetIntField(session, item);
        }
    }

    public static List<IntField> GetListIntField(IntField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListIntField(session, item);
        } catch (Exception e) {
            return new ArrayList<IntField>();
        } finally {
            session.close();
        }
    }

    public static List<IntField> GetListIntField(SqlSession session, IntField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<IntField>();
        default:
            return SqlFormDao.GetListIntField(session, item);
        }
    }

    public static IntField GetLogIntFieldName(IntField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetLogIntFieldName(session, item);
        } catch (Exception e) {
            return new IntField();
        } finally {
            session.close();
        }
    }

    public static IntField GetLogIntFieldName(SqlSession session, IntField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IntField();
        default:
            return SqlFormDao.GetLogIntFieldName(session, item);
        }
    }

    public static List<IntField> SearchIntField(IntField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<IntField>();
            default:
                return SqlFormDao.SearchIntField(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<IntField>();
        } finally {
            session.close();
        }
    }

    public static void SaveIntField(SqlSession session, IntField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveIntField(session, item);
            break;
        }
    }

    public static List<IntField> GetListIntFieldBySample(IntField item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListIntFieldBySample(session, item);
        } catch (Exception e) {
            return new ArrayList<IntField>();
        } finally {
            session.close();
        }
    }

    private static List<IntField> GetListIntFieldBySample(SqlSession session, IntField item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<IntField>();
        default:
            return SqlFormDao.GetListIntFieldBySample(session, item);
        }
    }

    // endregion IntField Methods

    // region FrmRecord Methods

    public static FrmRecord GetFrmRecord(FrmRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmRecord(session, item);
        } catch (Exception e) {
            return new FrmRecord();
        } finally {
            session.close();
        }
    }

    public static FrmRecord GetFrmRecord(SqlSession session, FrmRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FrmRecord();
        default:
            return SqlFormDao.GetFrmRecord(session, item);
        }
    }

    public static List<FrmRecord> GetListFrmRecord(FrmRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFrmRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmRecord>();
        } finally {
            session.close();
        }
    }

    public static List<FrmRecord> GetListFrmRecord(SqlSession session, FrmRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmRecord>();
        default:
            return SqlFormDao.GetListFrmRecord(session, item);
        }
    }

    public static List<FrmRecord> SearchFrmRecord(FrmRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmRecord>();
            default:
                return SqlFormDao.SearchFrmRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FrmRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmRecord(SqlSession session, FrmRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmRecord(session, item);
            break;
        }
    }

    public static List<BusRecordDetail> GetPreviewRecordDetailByID(FrmRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPreviewRecordDetailByID(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecordDetail>();
        } finally {
            session.close();
        }
    }

    public static List<BusRecordDetail> GetPreviewRecordDetailByID(SqlSession session, FrmRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordDetail>();
        default:
            return SqlFormDao.GetPreviewRecordDetailByID(session, item);
        }
    }

    // endregion FrmRecord Methods

    // region FrmGet Methods

    public static List<BusRecordDetail> GetPreviewGetDetailByID(FrmGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPreviewGetDetailByID(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecordDetail>();
        } finally {
            session.close();
        }
    }

    public static List<BusRecordDetail> GetPreviewGetDetailByID(SqlSession session, FrmGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordDetail>();
        default:
            return SqlFormDao.GetPreviewGetDetailByID(session, item);
        }
    }

    public static FrmGet GetFrmGet(FrmGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmGet(session, item);
        } catch (Exception e) {
            return new FrmGet();
        } finally {
            session.close();
        }
    }

    public static FrmGet GetFrmGet(SqlSession session, FrmGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FrmGet();
        default:
            return SqlFormDao.GetFrmGet(session, item);
        }
    }

    public static List<FrmGet> GetListFrmGet(FrmGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFrmGet(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmGet>();
        } finally {
            session.close();
        }
    }

    public static List<FrmGet> GetListFrmGet(SqlSession session, FrmGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmGet>();
        default:
            return SqlFormDao.GetListFrmGet(session, item);
        }
    }

    public static List<FrmGet> SearchFrmGet(FrmGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmGet>();
            default:
                return SqlFormDao.SearchFrmGet(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FrmGet>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmGet(SqlSession session, FrmGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmGet(session, item);
            break;
        }
    }

    public static void SaveFrmGetDetail(SqlSession session, FrmGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmGetDetail(session, item);
            break;
        }
    }

    public static List<FrmGetDetail> GetFrmGetDetailByForm(FrmGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmGetDetailByForm(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmGetDetail>();
        } finally {
            session.close();
        }
    }

    public static List<FrmGetDetail> GetFrmGetDetailByForm(SqlSession session, FrmGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmGetDetail>();
        default:
            return SqlFormDao.GetFrmGetDetailByForm(session, item);
        }
    }

    // endregion FrmGet Methods

    // region FrmRecordParameter Methods

    // public static FrmRecordParameter GetFrmRecordParameter(FrmRecordParameter
    // item) {
    // SqlSession session = DBUtils.getFactory();
    //
    // try {
    // return GetFrmRecordParameter(session, item);
    // } catch (Exception e) {
    // return new FrmRecordParameter();
    // }
    // finally {
    // session.close();
    // }
    // }
    //
    // public static FrmRecordParameter GetFrmRecordParameter(SqlSession session,
    // FrmRecordParameter item) {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new FrmRecordParameter();
    // default:
    // return SqlFormDao.GetFrmRecordParameter(session, item);
    // }
    // }
    //
    // public static List<FrmRecordParameter>
    // GetListFrmRecordParameter(FrmRecordParameter item) {
    // SqlSession session = DBUtils.getFactory();
    //
    // try {
    // return GetListFrmRecordParameter(session, item);
    // } catch (Exception e) {
    // return new ArrayList<FrmRecordParameter>();
    // }
    // finally {
    // session.close();
    // }
    // }
    //
    // public static List<FrmRecordParameter> GetListFrmRecordParameter(SqlSession
    // session, FrmRecordParameter item) {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new ArrayList<FrmRecordParameter>();
    // default:
    // return SqlFormDao.GetListFrmRecordParameter(session, item);
    // }
    // }
    //
    // public static List<FrmRecordParameter>
    // SearchFrmRecordParameter(FrmRecordParameter item) {
    // SqlSession session = DBUtils.getFactory();
    //
    // try {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new ArrayList<FrmRecordParameter>();
    // default:
    // return SqlFormDao.SearchFrmRecordParameter(session, item);
    //
    // }
    // } catch (Exception e) {
    // return new ArrayList<FrmRecordParameter>();
    // }
    // finally {
    // session.close();
    // }
    // }
    //
    // public static void SaveFrmRecordParameter(SqlSession session,
    // FrmRecordParameter item) {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // break;
    //
    // default:
    // SqlFormDao.SaveFrmRecordParameter(session, item);
    // break;
    // }
    // }

    // endregion FrmRecordParameter Methods

    // region FrmRecordDetail Methods

    public static FrmRecordDetail GetFrmRecordDetail(FrmRecordDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmRecordDetail(session, item);
        } catch (Exception e) {
            return new FrmRecordDetail();
        } finally {
            session.close();
        }
    }

    public static FrmRecordDetail GetFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FrmRecordDetail();
        default:
            return SqlFormDao.GetFrmRecordDetail(session, item);
        }
    }

    public static List<FrmRecordDetail> GetListFrmRecordDetail(FrmRecordDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFrmRecordDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmRecordDetail>();
        } finally {
            session.close();
        }
    }

    public static List<FrmRecordDetail> GetListFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmRecordDetail>();
        default:
            return SqlFormDao.GetListFrmRecordDetail(session, item);
        }
    }

    public static List<FrmRecordDetail> GetFrmRecordDetailByForm(FrmRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmRecordDetailByForm(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmRecordDetail>();
        } finally {
            session.close();
        }
    }

    public static List<FrmRecordDetail> GetFrmRecordDetailByForm(SqlSession session, FrmRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmRecordDetail>();
        default:
            return SqlFormDao.GetFrmRecordDetailByForm(session, item);
        }
    }

    public static List<FrmRecordDetail> SearchFrmRecordDetail(FrmRecordDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmRecordDetail>();
            default:
                return SqlFormDao.SearchFrmRecordDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FrmRecordDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmRecordDetail(session, item);
            break;
        }
    }

    public static void SaveFrmRecordDetailExcel(SqlSession session, FrmRecordDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmRecordDetailExcel(session, item);
            break;
        }
    }

    // endregion FrmRecordDetail Methods

    // region FrmRecordParameter Methods

    public static List<FrmRecordParameter> SearchFrmRecordParameter(FrmRecordParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmRecordParameter>();
            default:
                return SqlFormDao.SearchFrmRecordParameter(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FrmRecordParameter>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmRecordParameter(SqlSession session, FrmRecordParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmRecordParameter(session, item);
            break;
        }
    }

    public static IntInterface GetIntInterfaceBase(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetIntInterfaceBase(session, item);
        } catch (Exception e) {
            return new IntInterface();
        } finally {
            session.close();
        }
    }

    private static IntInterface GetIntInterfaceBase(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new IntInterface();

        default:
            return SqlFormDao.GetIntInterfaceBase(session, item);
        }
    }

    // endregion FrmRecordParameter Methods

    // region FrmReport Methods

    public static FrmReport GetFrmReport(FrmReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmReport(session, item);
        } catch (Exception e) {
            return new FrmReport();
        } finally {
            session.close();
        }
    }

    public static FrmReport GetFrmReport(SqlSession session, FrmReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FrmReport();
        default:
            return SqlFormDao.GetFrmReport(session, item);
        }
    }

    public static List<FrmReport> GetListFrmReport(FrmReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFrmReport(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmReport>();
        } finally {
            session.close();
        }
    }

    public static List<FrmReport> GetListFrmReport(SqlSession session, FrmReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmReport>();
        default:
            return SqlFormDao.GetListFrmReport(session, item);
        }
    }

    public static List<FrmReport> SearchFrmReport(FrmReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmReport>();
            default:
                return SqlFormDao.SearchFrmReport(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FrmReport>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmReport(SqlSession session, FrmReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmReport(session, item);
            break;
        }
    }

    public static List<BusReportDetail> GetPreviewReportDetailByID(SqlSession session, FrmReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReportDetail>();
        default:
            return SqlFormDao.GetPreviewReportDetailByID(session, item);
        }
    }

    // endregion FrmReport Methods

    // region FrmReportDetail Methods

    public static FrmReportDetail GetFrmReportDetail(FrmReportDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFrmReportDetail(session, item);
        } catch (Exception e) {
            return new FrmReportDetail();
        } finally {
            session.close();
        }
    }

    public static FrmReportDetail GetFrmReportDetail(SqlSession session, FrmReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FrmReportDetail();
        default:
            return SqlFormDao.GetFrmReportDetail(session, item);
        }
    }

    public static List<FrmReportDetail> GetListFrmReportDetail(FrmReportDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFrmReportDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<FrmReportDetail>();
        } finally {
            session.close();
        }
    }

    public static List<FrmReportDetail> GetListFrmReportDetail(SqlSession session, FrmReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FrmReportDetail>();
        default:
            return SqlFormDao.GetListFrmReportDetail(session, item);
        }
    }

    public static List<BusReportDetail> GetListBusReportDetail(BusReportDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusReportDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<BusReportDetail>();
        } finally {
            session.close();
        }
    }

    public static List<BusReportDetail> GetListBusReportDetail(SqlSession session, BusReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReportDetail>();
        default:
            return SqlFormDao.GetListBusReportDetail(session, item);
        }
    }

    public static List<FrmReportDetail> SearchFrmReportDetail(FrmReportDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FrmReportDetail>();
            default:
                return SqlFormDao.SearchFrmReportDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FrmReportDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveFrmReportDetail(SqlSession session, FrmReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveFrmReportDetail(session, item);
            break;
        }
    }

    public static void SaveBusPrintReport(SqlSession session, BusReportDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFormDao.SaveBusPrintReport(session, item);
            break;
        }
    }

    public static int GetBusRecordDetailMaxLength(BusRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusRecordDetailMaxLength(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    private static int GetBusRecordDetailMaxLength(SqlSession session, BusRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return 0;
        default:
            return SqlFormDao.GetBusRecordDetailMaxLength(session, item);
        }
    }

    // endregion FrmReportDetail Methods

    // region LabSample Methods

    public static List<LabSample> GetLabSampleByMonth(LabSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetLabSampleByMonth(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<LabSample>();
        } finally {
            session.close();
        }
    }

    public static List<LabSample> GetLabSampleByMonth(SqlSession session, LabSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<LabSample>();
        default:
            return SqlFormDao.GetLabSampleByMonth(session, item);
        }
    }

    // endregion LabSample Methods

    // region LabParameter Methods

    public static List<LabParameter> GetLabParameterByYear(LabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetLabParameterByYear(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<LabParameter>();
        } finally {
            session.close();
        }
    }

    public static List<LabParameter> GetLabParameterByYear(SqlSession session, LabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<LabParameter>();
        default:
            return SqlFormDao.GetLabParameterByYear(session, item);
        }
    }

    // endregion LabParameter Methods

    // region TesterParameter Methods

    public static List<TesterParameter> GetTesterParameter(TesterParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetTesterParameter(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TesterParameter>();
        } finally {
            session.close();
        }
    }

    public static List<TesterParameter> GetTesterParameter(SqlSession session, TesterParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<TesterParameter>();
        default:
            return SqlFormDao.GetTesterParameter(session, item);
        }
    }

    // endregion TesterParameter Methods

    // region TesterSample Methods

    public static List<TesterSample> GetTesterSample(TesterSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetTesterSample(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TesterSample>();
        } finally {
            session.close();
        }
    }

    public static List<TesterSample> GetTesterSample(SqlSession session, TesterSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<TesterSample>();
        default:
            return SqlFormDao.GetTesterSample(session, item);
        }
    }

    // endregion TesterSample Methods

    // region SamplePass Methods

    public static List<SamplePass> GetSamplePass(SamplePass item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSamplePass(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<SamplePass>();
        } finally {
            session.close();
        }
    }

    public static List<SamplePass> GetSamplePass(SqlSession session, SamplePass item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SamplePass>();
        default:
            return SqlFormDao.GetSamplePass(session, item);
        }
    }

    // endregion SamplePass Methods

    // region PrdType Methods

    public static List<PrdType> GetPrdType(PrdType item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdType(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<PrdType>();
        } finally {
            session.close();
        }
    }

    public static List<PrdType> GetPrdType(SqlSession session, PrdType item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdType>();
        default:
            return SqlFormDao.GetPrdType(session, item);
        }
    }

    // endregion PrdType Methods

    // region PrdCodeCount Methods

    public static List<PrdCodeCount> GetPrdCodeCount(PrdCodeCount item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdCodeCount(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<PrdCodeCount>();
        } finally {
            session.close();
        }
    }

    public static List<PrdCodeCount> GetPrdCodeCount(SqlSession session, PrdCodeCount item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdCodeCount>();
        default:
            return SqlFormDao.GetPrdCodeCount(session, item);
        }
    }

    // endregion PrdCodeCount Methods

    // region PrdIn Methods

    public static List<PrdIn> GetPrdIn(PrdIn item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdIn(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<PrdIn>();
        } finally {
            session.close();
        }
    }

    public static List<PrdIn> GetPrdIn(SqlSession session, PrdIn item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdIn>();
        default:
            return SqlFormDao.GetPrdIn(session, item);
        }
    }

    // endregion PrdIn Methods

    // region PrdOut Methods

    public static List<PrdOut> GetPrdOut(PrdOut item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetPrdOut(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<PrdOut>();
        } finally {
            session.close();
        }
    }

    public static List<PrdOut> GetPrdOut(SqlSession session, PrdOut item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<PrdOut>();
        default:
            return SqlFormDao.GetPrdOut(session, item);
        }
    }

    // endregion PrdOut Methods

    // region DevType Methods

    public static List<DevType> GetDevType(DevType item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevType(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DevType>();
        } finally {
            session.close();
        }
    }

    public static List<DevType> GetDevType(SqlSession session, DevType item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevType>();
        default:
            return SqlFormDao.GetDevType(session, item);
        }
    }

    // endregion DevType Methods

    // region DevUseCount Methods

    public static List<DevUseCount> GetDevUseCount(DevUseCount item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevUseCount(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DevUseCount>();
        } finally {
            session.close();
        }
    }

    public static List<DevUseCount> GetDevUseCount(SqlSession session, DevUseCount item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevUseCount>();
        default:
            return SqlFormDao.GetDevUseCount(session, item);
        }
    }

    // endregion DevUseCount Methods

    // region CusOrder Methods

    public static List<CusOrder> GetCusOrder(CusOrder item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCusOrder(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<CusOrder>();
        } finally {
            session.close();
        }
    }

    public static List<CusOrder> GetCusOrder(SqlSession session, CusOrder item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<CusOrder>();
        default:
            return SqlFormDao.GetCusOrder(session, item);
        }
    }

    // endregion CusOrder Methods

    // region SendSample Methods

    public static List<SendSample> GetSendSample(SendSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSendSample(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<SendSample>();
        } finally {
            session.close();
        }
    }

    public static List<SendSample> GetSendSample(SqlSession session, SendSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SendSample>();
        default:
            return SqlFormDao.GetSendSample(session, item);
        }
    }

    // endregion SendSample Methods

    // region CustomerCount Methods

    public static List<CustomerCount> GetCustomerCount(CustomerCount item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCustomerCount(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<CustomerCount>();
        } finally {
            session.close();
        }
    }

    public static List<CustomerCount> GetCustomerCount(SqlSession session, CustomerCount item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<CustomerCount>();
        default:
            return SqlFormDao.GetCustomerCount(session, item);
        }
    }

    // endregion CustomerCount Methods

}
