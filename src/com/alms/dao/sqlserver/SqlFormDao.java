package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.cxf.Bus;
import org.apache.ibatis.session.SqlSession;

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
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlFormDao {

    // region IntInterface Methods

    public static IntInterface GetIntInterface(SqlSession session, IntInterface item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIntInterface(item);
    }

    public static List<IntInterface> GetIntInterfaceByTask(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetIntInterfaceByTask(item);
    }

    public static List<IntInterface> SearchIntInterface(SqlSession session, IntInterface item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchIntInterface(item);
    }

    public static void SaveIntInterface(SqlSession session, IntInterface item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveIntInterface(item);
    }

    // endregion IntInterface Methods

    // region IntField Methods

    public static IntField GetIntField(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetIntField(item);
    }

    public static List<IntField> GetListIntField(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListIntField(item);
    }

    public static IntField GetLogIntFieldName(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetLogIntFieldName(item);
    }

    public static List<IntField> SearchIntField(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchIntField(item);
    }

    public static void SaveIntField(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveIntField(item);
    }

    public static List<IntField> GetListIntFieldBySample(SqlSession session, IntField item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListIntFieldBySample(item);
    }

    // endregion IntField Methods

    // region FrmRecord Methods

    public static FrmRecord GetFrmRecord(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFrmRecord(item);
    }

    public static List<FrmRecord> GetListFrmRecord(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListFrmRecord(item);
    }

    public static List<FrmRecord> SearchFrmRecord(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchFrmRecord(item);
    }

    public static void SaveFrmRecord(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmRecord(item);
    }

    public static List<BusRecordDetail> GetPreviewRecordDetailByID(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetPreviewRecordDetailByID(item);
    }

    // endregion FrmRecord Methods

    // region FrmGet Methods

    public static List<BusRecordDetail> GetPreviewGetDetailByID(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetPreviewGetDetailByID(item);
    }

    public static FrmGet GetFrmGet(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFrmGet(item);
    }

    public static List<FrmGet> GetListFrmGet(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListFrmGet(item);
    }

    public static List<FrmGet> SearchFrmGet(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchFrmGet(item);
    }

    public static void SaveFrmGet(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmGet(item);
    }

    public static List<BusRecordDetail> GetPreviewRecordDetailByID(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetPreviewRecordDetailByID(item);
    }

    public static void SaveFrmGetDetail(SqlSession session, FrmGetDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmGetDetail(item);
    }

    public static List<FrmGetDetail> GetFrmGetDetailByForm(SqlSession session, FrmGet item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetFrmGetDetailByForm(item);
    }

    // endregion FrmGet Methods

    // region FrmRecordDetail Methods

    public static FrmRecordDetail GetFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFrmRecordDetail(item);
    }

    public static List<FrmRecordDetail> GetListFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListFrmRecordDetail(item);
    }

    public static List<FrmRecordDetail> GetFrmRecordDetailByForm(SqlSession session, FrmRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetFrmRecordDetailByForm(item);
    }

    public static List<FrmRecordDetail> SearchFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchFrmRecordDetail(item);
    }

    public static void SaveFrmRecordDetail(SqlSession session, FrmRecordDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmRecordDetail(item);
    }

    public static void SaveFrmRecordDetailExcel(SqlSession session, FrmRecordDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmRecordDetailExcel(item);
    }

    // endregion FrmRecordDetail Methods

    // region FrmRecordParameter Methods

    public static List<FrmRecordParameter> SearchFrmRecordParameter(SqlSession session, FrmRecordParameter item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.SearchFrmRecordParameter(item);
    }

    public static void SaveFrmRecordParameter(SqlSession session, FrmRecordParameter item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        mapper.SaveFrmRecordParameter(item);
    }

    public static IntInterface GetIntInterfaceBase(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetIntInterfaceBase(item);
    }

    // endregion FrmRecordParameter Methods

    // region FrmReport Methods

    public static FrmReport GetFrmReport(SqlSession session, FrmReport item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFrmReport(item);
    }

    public static List<FrmReport> GetListFrmReport(SqlSession session, FrmReport item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListFrmReport(item);
    }

    public static List<FrmReport> SearchFrmReport(SqlSession session, FrmReport item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchFrmReport(item);
    }

    public static void SaveFrmReport(SqlSession session, FrmReport item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmReport(item);
    }

    public static List<BusReportDetail> GetPreviewReportDetailByID(SqlSession session, FrmReport item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetPreviewReportDetailByID(item);
    }

    // endregion FrmReport Methods

    // region FrmReportDetail Methods

    public static FrmReportDetail GetFrmReportDetail(SqlSession session, FrmReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFrmReportDetail(item);
    }

    public static List<FrmReportDetail> GetListFrmReportDetail(SqlSession session, FrmReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListFrmReportDetail(item);
    }

    public static List<BusReportDetail> GetListBusReportDetail(SqlSession session, BusReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetListBusReportDetail(item);
    }

    public static List<FrmReportDetail> SearchFrmReportDetail(SqlSession session, FrmReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.SearchFrmReportDetail(item);
    }

    public static void SaveFrmReportDetail(SqlSession session, FrmReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveFrmReportDetail(item);
    }

    public static void SaveBusPrintReport(SqlSession session, BusReportDetail item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        mapper.SaveBusPrintReport(item);
    }

    public static int GetBusRecordDetailMaxLength(SqlSession session, BusRecord item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);

        return mapper.GetBusRecordDetailMaxLength(item);
    }

    // endregion FrmReportDetail Methods

    // region LabSample Methods

    public static List<LabSample> GetLabSampleByMonth(SqlSession session, LabSample item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetLabSampleByMonth(item);
    }

    // endregion LabSample Methods

    // region LabParameter Methods

    public static List<LabParameter> GetLabParameterByYear(SqlSession session, LabParameter item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetLabParameterByYear(item);
    }

    // endregion LabParameter Methods

    // region TesterParameter Methods

    public static List<TesterParameter> GetTesterParameter(SqlSession session, TesterParameter item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetTesterParameter(item);
    }

    // endregion TesterParameter Methods

    // region TesterSample Methods

    public static List<TesterSample> GetTesterSample(SqlSession session, TesterSample item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetTesterSample(item);
    }

    // region SamplePass Methods

    public static List<SamplePass> GetSamplePass(SqlSession session, SamplePass item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetSamplePass(item);
    }

    // endregion SamplePass Methods

    // region PrdType Methods

    public static List<PrdType> GetPrdType(SqlSession session, PrdType item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetPrdType(item);
    }

    // endregion PrdType Methods

    // region PrdCodeCount Methods

    public static List<PrdCodeCount> GetPrdCodeCount(SqlSession session, PrdCodeCount item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetPrdCodeCount(item);
    }

    // endregion PrdCodeCount Methods

    // region PrdIn Methods

    public static List<PrdIn> GetPrdIn(SqlSession session, PrdIn item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetPrdIn(item);
    }

    // endregion PrdIn Methods

    // region PrdOut Methods

    public static List<PrdOut> GetPrdOut(SqlSession session, PrdOut item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetPrdOut(item);
    }

    // endregion PrdOut Methods

    // region DevType Methods

    public static List<DevType> GetDevType(SqlSession session, DevType item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetDevType(item);
    }

    // endregion DevType Methods

    // region DevUseCount Methods

    public static List<DevUseCount> GetDevUseCount(SqlSession session, DevUseCount item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetDevUseCount(item);
    }

    // endregion DevUseCount Methods

    // region CusOrder Methods

    public static List<CusOrder> GetCusOrder(SqlSession session, CusOrder item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetCusOrder(item);
    }

    // endregion CusOrder Methods

    // region SendSample Methods

    public static List<SendSample> GetSendSample(SqlSession session, SendSample item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetSendSample(item);
    }

    // endregion SendSample Methods

    // region CustomerCount Methods

    public static List<CustomerCount> GetCustomerCount(SqlSession session, CustomerCount item) {
        com.alms.mapper.sqlserver.FormMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FormMapper.class);
        return mapper.GetCustomerCount(item);
    }

    // endregion CustomerCount Methods

}
