package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

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
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlDevDao {

    // region BusTrade Methods

    public static List<BusTrade> SearchBusTrade(SqlSession session, BusTrade item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchBusTrade(item);
    }

    public static void SaveBusTrade(SqlSession session, BusTrade item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveBusTrade(item);
    }

    public static List<BusTrade> SearchBusTradeForSurvey(SqlSession session, BusTrade item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBusTradeForSurvey(item);
    }

    public static BusTrade GetBusTrade(SqlSession session, BusTrade item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTrade(item);
    }

    // endregion BusTrade Methods

    // region TradeSurvey Methods

    public static List<TradeSurvey> SearchTradeSurvey(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchTradeSurvey(item);
    }

    public static void SaveTradeSurvey(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        mapper.SaveTradeSurvey(item);
    }

    public static TradeSurvey GetTradeSurvey(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetTradeSurvey(item);
    }

    public static List<TradeSurvey> SearchTradeCheck(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchTradeCheck(item);
    }

    public static List<TradeSurvey> SearchBusTradeForCheck(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchBusTradeForCheck(item);
    }

    public static void SaveTradeCheck(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveTradeCheck(item);
    }

    public static List<TradeSurvey> SearchTradeApprove(SqlSession session, TradeSurvey item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchTradeApprove(item);
    }

    // endregion TradeSurvey Methods

    // region BasDev Methods

    public static void SaveBasDev(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        mapper.SaveBasDev(item);
    }

    public static void SaveBasDevByAccept(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        mapper.SaveBasDevByAccept(item);
    }

    public static List<BasDev> SearchBasDev(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBasDev(item);
    }

    public static List<BasDev> SearchBasDevForCalibplan(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBasDevForCalibplan(item);
    }

    public static List<BasDev> SearchBasDevForCheckplan(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBasDevForCheckplan(item);
    }

    public static List<BusTaskDev> SearchBasDevForUse(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBasDevForUse(item);
    }

    public static List<BasDev> SearchBasDevForBuy(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchBasDevForBuy(item);
    }

    public static BasDev GetBasDev(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBasDev(item);
    }

    public static List<BasDev> GetListBasDevByLab(SqlSession session, BasDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.GetListBasDevByLab(item);
    }

    // endregion BasDev Methods

    // region DevScrap Methods

    public static void SaveDevScrap(SqlSession session, DevScrap item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        mapper.SaveDevScrap(item);
    }

    public static List<DevScrap> SearchDevScrap(SqlSession session, DevScrap item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchDevScrap(item);
    }

    public static DevScrap GetDevScrap(SqlSession session, DevScrap item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetDevScrap(item);
    }

    // endregion DevScrap Methods

    // region DevUse Methods

    public static void SaveDevUse(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        mapper.SaveDevUse(item);
    }

    public static List<DevUse> SearchDevUse(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        return mapper.SearchDevUse(item);
    }

    public static DevUse GetDevUse(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevUse(item);
    }

    public static List<DevUse> GetListDevUseBySampletran(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevUseBySampletran(item);
    }

    public static List<DevUse> GetListDevUseByUser(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevUseByUser(item);
    }

    public static List<DevUse> GetListDevUseByDevID(SqlSession session, DevUse item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevUseByDevID(item);
    }

    public static List<BusTaskDev> GetListBasDevForUse(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListBasDevForUse(item);
    }

    // endregion DevUse Methods

    // region DevPlan Methods

    public static DevPlan GetDevPlan(SqlSession session, DevPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevPlan(item);
    }

    public static List<DevPlan> GetListDevPlan(SqlSession session, DevPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevPlan(item);
    }

    public static List<DevPlan> SearchDevPlan(SqlSession session, DevPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevPlan(item);
    }

    public static List<DevPlan> SearchDevPlanForCalib(SqlSession session, DevPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevPlanForCalib(item);
    }

    public static void SaveDevPlan(SqlSession session, DevPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevPlan(item);
    }

    // endregion DevPlan Methods

    // region DevCommon Methods

    public static DevCommon GetDevCommon(SqlSession session, DevCommon item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevCommon(item);
    }

    public static List<DevCommon> GetListDevCommon(SqlSession session, DevCommon item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevCommon(item);
    }

    public static List<DevCommon> SearchDevCommon(SqlSession session, DevCommon item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCommon(item);
    }

    public static void SaveDevCommon(SqlSession session, DevCommon item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevCommon(item);
    }

    // endregion DevCommon Methods

    // region BuyApply Methods

    public static BuyApply GetBuyApply(SqlSession session, BuyApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBuyApply(item);
    }

    public static List<BuyApply> GetListBuyApply(SqlSession session, BuyApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListBuyApply(item);
    }

    public static List<BuyApply> SearchBuyApply(SqlSession session, BuyApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchBuyApply(item);
    }

    public static List<BuyApply> SearchBuyApplyForAccept(SqlSession session, BuyApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchBuyApplyForAccept(item);
    }

    public static void SaveBuyApply(SqlSession session, BuyApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveBuyApply(item);
    }

    // endregion BuyApply Methods

    // region AcceptManage Methods

    public static AcceptManage GetAcceptManage(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetAcceptManage(item);
    }

    public static List<AcceptManage> GetListAcceptManage(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.full.toString());
        return mapper.GetListAcceptManage(item);
    }

    public static List<AcceptManage> GetListAcceptManageForSign(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.full.toString());
        return mapper.GetListAcceptManageForSign(item);
    }

    public static AcceptManage GetAcceptManageByTranID(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetAcceptManageByTranID(item);
    }

    public static List<AcceptManage> SearchAcceptManage(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchAcceptManage(item);
    }

    public static List<AcceptManage> SearchAcceptForUser(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchAcceptForUser(item);
    }

    public static void SaveAcceptManage(SqlSession session, AcceptManage item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveAcceptManage(item);
    }

    // endregion AcceptManage Methods

    // region AcceptFileDetail Methods

    public static AcceptFileDetail GetAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetAcceptFileDetail(item);
    }

    public static List<AcceptFileDetail> GetListAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListAcceptFileDetail(item);
    }

    public static List<AcceptFileDetail> SearchAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchAcceptFileDetail(item);
    }

    public static void SaveAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveAcceptFileDetail(item);
    }

    // endregion AcceptFileDetail Methods

    // region DevCalib Methods

    public static DevCalib GetDevCalib(SqlSession session, DevCalib item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevCalib(item);
    }

    public static DevCalib GetDevCalibByTranID(SqlSession session, DevCalib item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetDevCalibByTranID(item);
    }

    public static List<DevCalib> SearchDevCalib(SqlSession session, DevCalib item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCalib(item);
    }

    public static void SaveDevCalib(SqlSession session, DevCalib item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevCalib(item);
    }

    // endregion DevCalib Methods

    // region DevCalibPlan Methods

    public static DevCalibPlan GetDevCalibPlan(SqlSession session, DevCalibPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevCalibPlan(item);
    }

    public static List<DevCalibPlan> GetListDevCalibPlan(SqlSession session, DevCalibPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevCalibPlan(item);
    }

    public static List<DevCalibPlan> SearchDevCalibPlan(SqlSession session, DevCalibPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCalibPlan(item);
    }

    public static List<DevCalibPlan> SearchDevCalibPlanForCalib(SqlSession session, DevCalibPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCalibPlanForCalib(item);
    }

    public static void SaveDevCalibPlan(SqlSession session, DevCalibPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevCalibPlan(item);
    }

    // endregion DevCalibPlan Methods

    // region DevCheckPlan Methods

    public static DevCheckPlan GetDevCheckPlan(SqlSession session, DevCheckPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevCheckPlan(item);
    }

    public static List<DevCheckPlan> GetListDevCheckPlan(SqlSession session) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevCheckPlan();
    }

    public static List<DevCheckPlan> SearchDevCheckPlan(SqlSession session, DevCheckPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCheckPlan(item);
    }

    public static List<DevCheckPlan> SearchDevCheckPlanForCheck(SqlSession session, DevCheckPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCheckPlanForCheck(item);
    }

    public static void SaveDevCheckPlan(SqlSession session, DevCheckPlan item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevCheckPlan(item);
    }

    // endregion DevCheckPlan Methods

    // region DevCheck Methods

    public static DevCheck GetDevCheck(SqlSession session, DevCheck item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevCheck(item);
    }

    public static List<DevCheck> GetListDevCheck(SqlSession session, DevCheck item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevCheck(item);
    }

    public static List<DevCheck> SearchDevCheck(SqlSession session, DevCheck item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevCheck(item);
    }

    public static void SaveDevCheck(SqlSession session, DevCheck item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevCheck(item);
    }

    // endregion DevCheck Methods

    // region RepairApply Methods

    public static RepairApply GetRepairApply(SqlSession session, RepairApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetRepairApply(item);
    }

    public static List<RepairApply> GetListRepairApply(SqlSession session, RepairApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListRepairApply(item);
    }

    public static void SaveRepairApply(SqlSession session, RepairApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveRepairApply(item);
    }

    public static List<RepairApply> SearchRepairApply(SqlSession session, RepairApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchRepairApply(item);
    }

    public static List<RepairApply> SearchRepairApplyForRecord(SqlSession session, RepairApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchRepairApplyForRecord(item);
    }

    // endregion RepairApply Methods

    // region RepairRecord Methods

    public static RepairRecord GetRepairRecord(SqlSession session, RepairRecord item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetRepairRecord(item);
    }

    public static List<RepairRecord> GetListRepairRecord(SqlSession session, RepairRecord item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListRepairRecord(item);
    }

    public static List<RepairRecord> SearchRepairRecord(SqlSession session, RepairRecord item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchRepairRecord(item);
    }

    public static void SaveRepairRecord(SqlSession session, RepairRecord item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveRepairRecord(item);
    }

    // endregion RepairRecord Methods

    // region AcceptPartsDetail Methods

    public static AcceptPartsDetail GetAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetAcceptPartsDetail(item);
    }

    public static List<AcceptPartsDetail> GetListAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListAcceptPartsDetail(item);
    }

    public static List<AcceptPartsDetail> SearchAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchAcceptPartsDetail(item);
    }

    public static void SaveAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveAcceptPartsDetail(item);
    }

    // endregion AcceptPartsDetail Methods

    // region DevBasic Methods

    public static DevBasic GetDevBasic(SqlSession session, DevBasic item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevBasic(item);
    }

    public static List<DevBasic> GetListDevBasic(SqlSession session, DevBasic item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevBasic(item);
    }

    public static List<DevBasic> SearchDevBasic(SqlSession session, DevBasic item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevBasic(item);
    }

    public static void SaveDevBasic(SqlSession session, DevBasic item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevBasic(item);
    }

    // endregion DevBasic Methods

    // SqlServer Dao Functions

    // region DevTest Methods

    public static DevTest GetDevTest(SqlSession session, DevTest item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetDevTest(item);
    }

    public static List<DevTest> GetListDevTest(SqlSession session, DevTest item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetListDevTest(item);
    }

    public static List<DevTest> SearchDevTest(SqlSession session, DevTest item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevTest(item);
    }

    public static void SaveDevTest(SqlSession session, DevTest item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevTest(item);
    }

    // endregion DevTest Methods
    // region DevUseApply Methods

    public static List<DevUseApply> SearchDevUseApply(SqlSession session, DevUseApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevUseApply(item);
    }

    public static void SaveDevUseApply(SqlSession session, DevUseApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevUseApply(item);
    }

    public static DevUseApply GetDevUseApply(SqlSession session, DevUseApply item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetDevUseApply(item);
    }
    // end region DevUseApply Mehtods

    // region DevUseAllot Methods
    public static List<DevUseAllot> SearchDevUseAllot(SqlSession session, DevUseAllot item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevUseAllot(item);
    }

    public static void SaveDevUseAllot(SqlSession session, DevUseAllot item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevUseAllot(item);
    }
    // end region DevUseAllot Methods

    // region devuseback methods
    public static void SaveDevUseBack(SqlSession session, DevUseBack item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        mapper.SaveDevUseBack(item);
    }

    public static List<DevUseBack> SearchDevUseBack(SqlSession session, DevUseBack item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.SearchDevUseBack(item);
    }

    public static DevCommon GetDevCommonByTranID(SqlSession session, DevCommon item) {
        com.alms.mapper.sqlserver.DevMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.DevMapper.class);

        return mapper.GetDevCommonByTranID(item);
    }
    // end region devuseback methods
}
