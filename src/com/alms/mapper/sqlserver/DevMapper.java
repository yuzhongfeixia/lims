package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.dev.AcceptFileDetail;
import com.alms.entity.dev.AcceptManage;
import com.alms.entity.dev.AcceptPartsDetail;
import com.alms.entity.dev.BasDev;
import com.alms.entity.dev.BusTrade;
import com.alms.entity.dev.BuyApply;
import com.alms.entity.dev.DevBasic;
import com.alms.entity.dev.DevCalib;
import com.alms.entity.dev.DevCalibPlan;
import com.alms.entity.dev.DevCheck;
import com.alms.entity.dev.DevCheckPlan;
import com.alms.entity.dev.DevCommon;
import com.alms.entity.dev.DevPlan;
import com.alms.entity.dev.DevScrap;
import com.alms.entity.dev.DevTest;
import com.alms.entity.dev.DevUse;
import com.alms.entity.dev.DevUseAllot;
import com.alms.entity.dev.DevUseApply;
import com.alms.entity.dev.DevUseBack;
import com.alms.entity.dev.RepairApply;
import com.alms.entity.dev.RepairRecord;
import com.alms.entity.dev.TradeSurvey;
import com.alms.entity.lab.BusTaskDev;
import com.gpersist.mapper.BaseMapper;

public interface DevMapper extends BaseMapper {

    // region BusTrade Methods

    public List<BusTrade> SearchBusTrade(BusTrade item);

    public void SaveBusTrade(BusTrade item);

    public List<BusTrade> SearchBusTradeForSurvey(BusTrade item);

    public BusTrade GetBusTrade(BusTrade item);

    // endregion BusTrade Methods

    // region TradeSurvey Methods

    public List<TradeSurvey> SearchTradeCheck(TradeSurvey item);

    public List<TradeSurvey> SearchBusTradeForCheck(TradeSurvey item);

    public void SaveTradeCheck(TradeSurvey item);

    public List<TradeSurvey> SearchTradeSurvey(TradeSurvey item);

    public void SaveTradeSurvey(TradeSurvey item);

    public TradeSurvey GetTradeSurvey(TradeSurvey item);

    public List<TradeSurvey> SearchTradeApprove(TradeSurvey item);

    // endregion TradeSurvey Methods

    // region BasDev Methods

    public void SaveBasDev(BasDev item);

    public void SaveBasDevByAccept(BasDev item);

    public List<BasDev> SearchBasDev(BasDev item);

    public List<BasDev> SearchBasDevForCalibplan(BasDev item);

    public List<BasDev> SearchBasDevForCheckplan(BasDev item);

    public List<BusTaskDev> SearchBasDevForUse(BusTaskDev item);

    public List<BasDev> SearchBasDevForBuy(BasDev item);

    public BasDev GetBasDev(BasDev item);

    public List<BasDev> GetListBasDevByLab(BasDev item);

    // endregion BasDev Methods

    // region DevScrap Methods

    public void SaveDevScrap(DevScrap item);

    public List<DevScrap> SearchDevScrap(DevScrap item);

    public DevScrap GetDevScrap(DevScrap item);

    // endregion DevScrap Methods

    // region DevUse Methods

    public void SaveDevUse(DevUse item);

    public List<DevUse> SearchDevUse(DevUse item);

    public DevUse GetDevUse(DevUse item);

    public List<DevUse> GetListDevUseBySampletran(DevUse item);

    public List<BusTaskDev> GetListBasDevForUse(BusTaskDev item);

    public List<DevUse> GetListDevUseByUser(DevUse item);

    public List<DevUse> GetListDevUseByDevID(DevUse item);

    public DevUse GetDevUseBySampletran(DevUse item);

    // endregion DevUse Methods

    // region DevPlan Methods

    public DevPlan GetDevPlan(DevPlan item);

    public List<DevPlan> GetListDevPlan(DevPlan item);

    public List<DevPlan> SearchDevPlan(DevPlan item);

    public List<DevPlan> SearchDevPlanForCalib(DevPlan item);

    public void SaveDevPlan(DevPlan item);

    // endregion DevPlan Methods

    // region DevCommon Methods

    public DevCommon GetDevCommon(DevCommon item);

    public List<DevCommon> GetListDevCommon(DevCommon item);

    public List<DevCommon> SearchDevCommon(DevCommon item);

    public void SaveDevCommon(DevCommon item);

    // endregion DevCommon Methods

    // region BuyApply Methods

    public BuyApply GetBuyApply(BuyApply item);

    public List<BuyApply> GetListBuyApply(BuyApply item);

    public List<BuyApply> SearchBuyApply(BuyApply item);

    public List<BuyApply> SearchBuyApplyForAccept(BuyApply item);

    public void SaveBuyApply(BuyApply item);

    // endregion BuyApply Methods

    // region AcceptManage Methods

    public AcceptManage GetAcceptManage(AcceptManage item);

    public AcceptManage GetAcceptManageByTranID(AcceptManage item);

    public List<AcceptManage> GetListAcceptManage(AcceptManage item);

    public List<AcceptManage> GetListAcceptManageForSign(AcceptManage item);

    public List<AcceptManage> SearchAcceptManage(AcceptManage item);

    public List<AcceptManage> SearchAcceptForUser(AcceptManage item);

    public void SaveAcceptManage(AcceptManage item);

    // endregion AcceptManage Methods

    // region AcceptFileDetail Methods

    public AcceptFileDetail GetAcceptFileDetail(AcceptFileDetail item);

    public List<AcceptFileDetail> GetListAcceptFileDetail(AcceptFileDetail item);

    public List<AcceptFileDetail> SearchAcceptFileDetail(AcceptFileDetail item);

    public void SaveAcceptFileDetail(AcceptFileDetail item);

    // endregion AcceptFileDetail Methods

    // region DevCalib Methods

    public DevCalib GetDevCalib(DevCalib item);

    public DevCalib GetDevCalibByTranID(DevCalib item);

    public List<DevCalib> SearchDevCalib(DevCalib item);

    public void SaveDevCalib(DevCalib item);

    // endregion DevCalib Methods

    // region DevCalibPlan Methods

    public DevCalibPlan GetDevCalibPlan(DevCalibPlan item);

    public List<DevCalibPlan> GetListDevCalibPlan(DevCalibPlan item);

    public List<DevCalibPlan> SearchDevCalibPlan(DevCalibPlan item);

    public List<DevCalibPlan> SearchDevCalibPlanForCalib(DevCalibPlan item);

    public void SaveDevCalibPlan(DevCalibPlan item);

    // endregion DevCalibPlan Methods

    // region DevCheckPlan Methods

    public DevCheckPlan GetDevCheckPlan(DevCheckPlan item);

    public List<DevCheckPlan> GetListDevCheckPlan();

    public List<DevCheckPlan> SearchDevCheckPlan(DevCheckPlan item);

    public List<DevCheckPlan> SearchDevCheckPlanForCheck(DevCheckPlan item);

    public void SaveDevCheckPlan(DevCheckPlan item);

    // endregion DevCheckPlan Methods

    // region DevCheck Methods

    public DevCheck GetDevCheck(DevCheck item);

    public List<DevCheck> GetListDevCheck(DevCheck item);

    public List<DevCheck> SearchDevCheck(DevCheck item);

    public void SaveDevCheck(DevCheck item);

    // endregion DevCheck Methods

    // region RepairApply Methods

    public RepairApply GetRepairApply(RepairApply item);

    public List<RepairApply> GetListRepairApply(RepairApply item);

    public void SaveRepairApply(RepairApply item);

    public List<RepairApply> SearchRepairApply(RepairApply item);

    public List<RepairApply> SearchRepairApplyForRecord(RepairApply item);

    // endregion RepairApply Methods

    // region RepairRecord Methods

    public RepairRecord GetRepairRecord(RepairRecord item);

    public List<RepairRecord> GetListRepairRecord(RepairRecord item);

    public List<RepairRecord> SearchRepairRecord(RepairRecord item);

    public void SaveRepairRecord(RepairRecord item);

    // endregion RepairRecord Methods

    // region AcceptPartsDetail Methods

    public AcceptPartsDetail GetAcceptPartsDetail(AcceptPartsDetail item);

    public List<AcceptPartsDetail> GetListAcceptPartsDetail(AcceptPartsDetail item);

    public List<AcceptPartsDetail> SearchAcceptPartsDetail(AcceptPartsDetail item);

    public void SaveAcceptPartsDetail(AcceptPartsDetail item);

    // endregion AcceptPartsDetail Methods

    // region DevBasic Methods

    public DevBasic GetDevBasic(DevBasic item);

    public List<DevBasic> GetListDevBasic(DevBasic item);

    public List<DevBasic> SearchDevBasic(DevBasic item);

    public void SaveDevBasic(DevBasic item);

    // endregion DevBasic Methods

    // region DevTest Methods

    public DevTest GetDevTest(DevTest item);

    public List<DevTest> GetListDevTest(DevTest item);

    public List<DevTest> SearchDevTest(DevTest item);

    public void SaveDevTest(DevTest item);

    // endregion DevTest Methods

    // region DevUseApply Methods
    public List<DevUseApply> SearchDevUseApply(DevUseApply item);

    public void SaveDevUseApply(DevUseApply item);

    public DevUseApply GetDevUseApply(DevUseApply item);
    // end region DevUseApply Methods

    // region DevUseAllot Methods
    public List<DevUseAllot> SearchDevUseAllot(DevUseAllot item);

    public void SaveDevUseAllot(DevUseAllot item);

    // end region DevUseAllot Methods
    // region DevUseBack Methods
    public void SaveDevUseBack(DevUseBack item);

    public List<DevUseBack> SearchDevUseBack(DevUseBack item);
    // endregion DevUseBack Methods

    public DevCommon GetDevCommonByTranID(DevCommon item);
}
