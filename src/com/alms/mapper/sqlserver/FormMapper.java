package com.alms.mapper.sqlserver;

import java.util.List;

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

public interface FormMapper extends BasMapper {

    // region IntInterface Methods

    public IntInterface GetIntInterface(IntInterface item);

    public List<IntInterface> GetIntInterfaceByTask(BusTaskSingle item);

    public List<IntInterface> SearchIntInterface(IntInterface item);

    public void SaveIntInterface(IntInterface item);

    // endregion IntInterface Methods

    // region IntField Methods

    public IntField GetIntField(IntField item);

    public List<IntField> GetListIntField(IntField item);

    public IntField GetLogIntFieldName(IntField item);

    public List<IntField> SearchIntField(IntField item);

    public void SaveIntField(IntField item);

    public List<IntField> GetListIntFieldBySample(IntField item);

    // endregion IntField Methods

    // region FrmRecord Methods

    public FrmRecord GetFrmRecord(FrmRecord item);

    public List<FrmRecord> GetListFrmRecord(FrmRecord item);

    public List<FrmRecord> SearchFrmRecord(FrmRecord item);

    public void SaveFrmRecord(FrmRecord item);

    public List<BusRecordDetail> GetPreviewRecordDetailByID(FrmRecord item);

    // endregion FrmRecord Methods

    // region FrmGet Methods

    public List<BusRecordDetail> GetPreviewGetDetailByID(FrmGet item);

    public FrmGet GetFrmGet(FrmGet item);

    public List<FrmGet> GetListFrmGet(FrmGet item);

    public List<FrmGet> SearchFrmGet(FrmGet item);

    public void SaveFrmGet(FrmGet item);

    public List<BusRecordDetail> GetPreviewRecordDetailByID(FrmGet item);

    public void SaveFrmGetDetail(FrmGetDetail item);

    public List<FrmGetDetail> GetFrmGetDetailByForm(FrmGet item);

    // endregion FrmGet Methods

    // region FrmRecordParameter Methods

    // public FrmRecordParameter GetFrmRecordParameter(FrmRecordParameter item);
    //
    // public List<FrmRecordParameter>
    // GetListFrmRecordParameter(FrmRecordParameter item);
    //
    // public List<FrmRecordParameter> SearchFrmRecordParameter(FrmRecordParameter
    // item);
    //
    // public void SaveFrmRecordParameter(FrmRecordParameter item);

    // endregion FrmRecordParameter Methods

    // region FrmRecordDetail Methods

    public FrmRecordDetail GetFrmRecordDetail(FrmRecordDetail item);

    public List<FrmRecordDetail> GetListFrmRecordDetail(FrmRecordDetail item);

    public List<FrmRecordDetail> GetFrmRecordDetailByForm(FrmRecord item);

    public List<FrmRecordDetail> SearchFrmRecordDetail(FrmRecordDetail item);

    public void SaveFrmRecordDetail(FrmRecordDetail item);

    public void SaveFrmRecordDetailExcel(FrmRecordDetail item);

    // endregion FrmRecordDetail Methods

    // region FrmRecordParameter Methods

    public List<FrmRecordParameter> SearchFrmRecordParameter(FrmRecordParameter item);

    public void SaveFrmRecordParameter(FrmRecordParameter item);

    public IntInterface GetIntInterfaceBase(BusTaskSingle item);

    // endregion FrmRecordParameter Methods

    // region FrmReport Methods

    public FrmReport GetFrmReport(FrmReport item);

    public List<FrmReport> GetListFrmReport(FrmReport item);

    public List<FrmReport> SearchFrmReport(FrmReport item);

    public void SaveFrmReport(FrmReport item);

    public List<BusReportDetail> GetPreviewReportDetailByID(FrmReport item);

    // endregion FrmReport Methods

    // region FrmReportDetail Methods

    public FrmReportDetail GetFrmReportDetail(FrmReportDetail item);

    public List<FrmReportDetail> GetListFrmReportDetail(FrmReportDetail item);

    public List<BusReportDetail> GetListBusReportDetail(BusReportDetail item);

    public List<FrmReportDetail> SearchFrmReportDetail(FrmReportDetail item);

    public void SaveFrmReportDetail(FrmReportDetail item);

    public void SaveBusPrintReport(BusReportDetail item);

    // endregion FrmReportDetail Methods

    // region BusReportDetail Methods

    public int GetBusRecordDetailMaxLength(BusRecord item);

    // endregion BusReportDetail Methods

    // region LabSample Methods

    public List<LabSample> GetLabSampleByMonth(LabSample item);

    // endregion LabSample Methods

    // region LabParameter Methods

    public List<LabParameter> GetLabParameterByYear(LabParameter item);

    // endregion LabParameter Methods

    // region TesterParameter Methods

    public List<TesterParameter> GetTesterParameter(TesterParameter item);

    // endregion TesterParameter Methods

    // region TesterSample Methods

    public List<TesterSample> GetTesterSample(TesterSample item);

    // endregion TesterSample Methods

    // region SamplePass Methods

    public List<SamplePass> GetSamplePass(SamplePass item);

    // endregion SamplePass Methods

    // region PrdType Methods

    public List<PrdType> GetPrdType(PrdType item);

    // endregion PrdType Methods

    // region PrdCodeCount Methods

    public List<PrdCodeCount> GetPrdCodeCount(PrdCodeCount item);

    // endregion PrdCodeCount Methods

    // region PrdIn Methods

    public List<PrdIn> GetPrdIn(PrdIn item);

    // endregion PrdIn Methods

    // region PrdOut Methods

    public List<PrdOut> GetPrdOut(PrdOut item);

    // endregion PrdOut Methods

    // region DevType Methods

    public List<DevType> GetDevType(DevType item);

    // endregion DevType Methods

    // region DevUseCount Methods

    public List<DevUseCount> GetDevUseCount(DevUseCount item);

    // endregion DevUseCount Methods

    // region CusOrder Methods

    public List<CusOrder> GetCusOrder(CusOrder item);

    // endregion CusOrder Methods

    // region SendSample Methods

    public List<SendSample> GetSendSample(SendSample item);

    // endregion SendSample Methods

    // region CustomerCount Methods

    public List<CustomerCount> GetCustomerCount(CustomerCount item);

    // endregion CustomerCount Methods

}
