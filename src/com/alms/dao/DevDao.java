package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlDevDao;
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
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class DevDao {

    // region BusTrade Methods

    public static BusTrade GetBusTrade(BusTrade item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTrade(session, item);
        } catch (Exception e) {
            return new BusTrade();
        } finally {
            session.close();
        }
    }

    public static BusTrade GetBusTrade(SqlSession session, BusTrade item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTrade();
        default:
            return SqlDevDao.GetBusTrade(session, item);
        }
    }

    public static List<BusTrade> SearchBusTrade(BusTrade item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTrade>();
            default:
                return SqlDevDao.SearchBusTrade(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<BusTrade>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTrade(SqlSession session, BusTrade item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveBusTrade(session, item);
            break;
        }
    }

    public static TradeSurvey GetTradeSurvey(TradeSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetTradeSurvey(session, item);
        } catch (Exception e) {
            return new TradeSurvey();
        } finally {
            session.close();
        }
    }

    public static TradeSurvey GetTradeSurvey(SqlSession session, TradeSurvey item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new TradeSurvey();
        default:
            return SqlDevDao.GetTradeSurvey(session, item);
        }
    }

    public static List<BusTrade> SearchBusTradeForSurvey(BusTrade item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTrade>();
            default:
                return SqlDevDao.SearchBusTradeForSurvey(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<BusTrade>();
        } finally {
            session.close();
        }
    }

    // endregion BusTrade Methods

    // region TradeSurvey Methods

    public static List<TradeSurvey> SearchTradeSurvey(TradeSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<TradeSurvey>();
            default:
                return SqlDevDao.SearchTradeSurvey(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<TradeSurvey>();
        } finally {
            session.close();
        }
    }

    public static void SaveTradeSurvey(SqlSession session, TradeSurvey item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveTradeSurvey(session, item);
            break;
        }
    }

    public static List<TradeSurvey> SearchTradeCheck(TradeSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<TradeSurvey>();
            default:
                return SqlDevDao.SearchTradeCheck(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<TradeSurvey>();
        } finally {
            session.close();
        }
    }

    public static List<TradeSurvey> SearchBusTradeForCheck(TradeSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<TradeSurvey>();
            default:
                return SqlDevDao.SearchBusTradeForCheck(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<TradeSurvey>();
        } finally {
            session.close();
        }
    }

    public static void SaveTradeCheck(SqlSession session, TradeSurvey item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveTradeCheck(session, item);
            break;
        }
    }

    public static List<TradeSurvey> SearchTradeApprove(TradeSurvey item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<TradeSurvey>();
            default:
                return SqlDevDao.SearchTradeApprove(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<TradeSurvey>();
        } finally {
            session.close();
        }
    }

    // endregion TradeSurvey Methods

    // region BasDev Methods

    public static void SaveBasDev(SqlSession session, BasDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveBasDev(session, item);
            break;
        }
    }

    public static void SaveBasDevByAccept(SqlSession session, BasDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveBasDevByAccept(session, item);
            break;
        }
    }

    public static List<BasDev> SearchBasDev(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasDev>();
            default:
                return SqlDevDao.SearchBasDev(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasDev>();
        } finally {
            session.close();
        }
    }

    public static List<BasDev> SearchBasDevForCheckplan(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasDev>();
            default:
                return SqlDevDao.SearchBasDevForCheckplan(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasDev>();
        } finally {
            session.close();
        }
    }

    public static List<BasDev> SearchBasDevForCalibplan(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasDev>();
            default:
                return SqlDevDao.SearchBasDevForCalibplan(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> SearchBasDevForUse(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskDev>();
            default:
                return SqlDevDao.SearchBasDevForUse(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BasDev> SearchBasDevForBuy(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasDev>();
            default:
                return SqlDevDao.SearchBasDevForBuy(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasDev>();
        } finally {
            session.close();
        }
    }

    public static BasDev GetBasDev(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasDev(session, item);
        } catch (Exception e) {
            return new BasDev();
        } finally {
            session.close();
        }
    }

    public static BasDev GetBasDev(SqlSession session, BasDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasDev();
        default:
            return SqlDevDao.GetBasDev(session, item);
        }
    }

    public static List<BasDev> GetListBasDevByLab(BasDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasDevByLab(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasDev>();
        } finally {
            session.close();
        }
    }

    private static List<BasDev> GetListBasDevByLab(SqlSession session, BasDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasDev>();
        default:
            return SqlDevDao.GetListBasDevByLab(session, item);
        }
    }

    // endregion BasDev Methods

    // region DevScrap Methods

    public static void SaveDevScrap(SqlSession session, DevScrap item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevScrap(session, item);
            break;
        }
    }

    public static List<DevScrap> SearchDevScrap(DevScrap item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevScrap>();
            default:
                return SqlDevDao.SearchDevScrap(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DevScrap>();
        } finally {
            session.close();
        }
    }

    public static DevScrap GetDevScrap(DevScrap item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevScrap(session, item);
        } catch (Exception e) {
            return new DevScrap();
        } finally {
            session.close();
        }
    }

    private static DevScrap GetDevScrap(SqlSession session, DevScrap item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevScrap();
        default:
            return SqlDevDao.GetDevScrap(session, item);
        }
    }

    // endregion DevScrap Methods

    // region DevUse Methods

    public static DevUse GetDevUse(DevUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevUse(session, item);
        } catch (Exception e) {
            return new DevUse();
        } finally {
            session.close();
        }
    }

    public static DevUse GetDevUse(SqlSession session, DevUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevUse();
        default:
            return SqlDevDao.GetDevUse(session, item);
        }
    }

    public static List<DevUse> GetListDevUseBySampletran(DevUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevUseBySampletran(session, item);
        } catch (Exception e) {
            return new ArrayList<DevUse>();
        } finally {
            session.close();
        }
    }

    public static List<DevUse> GetListDevUseBySampletran(SqlSession session, DevUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevUse>();
        default:
            return SqlDevDao.GetListDevUseBySampletran(session, item);
        }
    }

    public static List<DevUse> GetListDevUseByUser(DevUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevUseByUser(session, item);
        } catch (Exception e) {
            return new ArrayList<DevUse>();
        } finally {
            session.close();
        }
    }

    public static List<DevUse> GetListDevUseByUser(SqlSession session, DevUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevUse>();
        default:
            return SqlDevDao.GetListDevUseByUser(session, item);
        }
    }

    public static List<DevUse> GetListDevUseByDevID(DevUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevUseByDevID(session, item);
        } catch (Exception e) {
            return new ArrayList<DevUse>();
        } finally {
            session.close();
        }
    }

    public static List<DevUse> GetListDevUseByDevID(SqlSession session, DevUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevUse>();
        default:
            return SqlDevDao.GetListDevUseByDevID(session, item);
        }
    }

    public static void SaveDevUse(SqlSession session, DevUse item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevUse(session, item);
            break;
        }
    }

    public static List<DevUse> SearchDevUse(DevUse item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevUse>();
            default:
                return SqlDevDao.SearchDevUse(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DevUse>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> GetListBasDevForUse(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasDevForUse(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> GetListBasDevForUse(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskDev>();
        default:
            return SqlDevDao.GetListBasDevForUse(session, item);
        }
    }

    // endregion DevUse Methods

    // region DevPlan Methods

    public static DevPlan GetDevPlan(DevPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevPlan(session, item);
        } catch (Exception e) {
            return new DevPlan();
        } finally {
            session.close();
        }
    }

    public static DevPlan GetDevPlan(SqlSession session, DevPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevPlan();
        default:
            return SqlDevDao.GetDevPlan(session, item);
        }
    }

    public static List<DevPlan> GetListDevPlan(DevPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevPlan(session, item);
        } catch (Exception e) {
            return new ArrayList<DevPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevPlan> GetListDevPlan(SqlSession session, DevPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevPlan>();
        default:
            return SqlDevDao.GetListDevPlan(session, item);
        }
    }

    public static List<DevPlan> SearchDevPlan(DevPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevPlan>();
            default:
                return SqlDevDao.SearchDevPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevPlan> SearchDevPlanForCalib(DevPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevPlan>();
            default:
                return SqlDevDao.SearchDevPlanForCalib(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevPlan(SqlSession session, DevPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevPlan(session, item);
            break;
        }
    }

    // endregion DevPlan Methods

    // region DevCommon Methods

    public static DevCommon GetDevCommon(DevCommon item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCommon(session, item);
        } catch (Exception e) {
            return new DevCommon();
        } finally {
            session.close();
        }
    }

    public static DevCommon GetDevCommon(SqlSession session, DevCommon item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCommon();
        default:
            return SqlDevDao.GetDevCommon(session, item);
        }
    }

    public static List<DevCommon> GetListDevCommon(DevCommon item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevCommon(session, item);
        } catch (Exception e) {
            return new ArrayList<DevCommon>();
        } finally {
            session.close();
        }
    }

    public static List<DevCommon> GetListDevCommon(SqlSession session, DevCommon item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevCommon>();
        default:
            return SqlDevDao.GetListDevCommon(session, item);
        }
    }

    public static List<DevCommon> SearchDevCommon(DevCommon item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCommon>();
            default:
                return SqlDevDao.SearchDevCommon(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCommon>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevCommon(SqlSession session, DevCommon item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevCommon(session, item);
            break;
        }
    }

    // endregion DevCommon Methods

    // region BuyApply Methods

    public static BuyApply GetBuyApply(BuyApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBuyApply(session, item);
        } catch (Exception e) {
            return new BuyApply();
        } finally {
            session.close();
        }
    }

    public static BuyApply GetBuyApply(SqlSession session, BuyApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BuyApply();
        default:
            return SqlDevDao.GetBuyApply(session, item);
        }
    }

    public static List<BuyApply> GetListBuyApply(BuyApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBuyApply(session, item);
        } catch (Exception e) {
            return new ArrayList<BuyApply>();
        } finally {
            session.close();
        }
    }

    public static List<BuyApply> GetListBuyApply(SqlSession session, BuyApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BuyApply>();
        default:
            return SqlDevDao.GetListBuyApply(session, item);
        }
    }

    public static List<BuyApply> SearchBuyApply(BuyApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BuyApply>();
            default:
                return SqlDevDao.SearchBuyApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BuyApply>();
        } finally {
            session.close();
        }
    }

    public static List<BuyApply> SearchBuyApplyForAccept(BuyApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BuyApply>();
            default:
                return SqlDevDao.SearchBuyApplyForAccept(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BuyApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveBuyApply(SqlSession session, BuyApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveBuyApply(session, item);
            break;
        }
    }

    // endregion BuyApply Methods

    // region AcceptManage Methods

    public static AcceptManage GetAcceptManage(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAcceptManage(session, item);
        } catch (Exception e) {
            return new AcceptManage();
        } finally {
            session.close();
        }
    }

    public static AcceptManage GetAcceptManage(SqlSession session, AcceptManage item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new AcceptManage();
        default:
            return SqlDevDao.GetAcceptManage(session, item);
        }
    }

    public static AcceptManage GetAcceptManageByTranID(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAcceptManageByTranID(session, item);
        } catch (Exception e) {
            return new AcceptManage();
        } finally {
            session.close();
        }
    }

    public static AcceptManage GetAcceptManageByTranID(SqlSession session, AcceptManage item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new AcceptManage();
        default:
            return SqlDevDao.GetAcceptManageByTranID(session, item);
        }
    }

    public static List<AcceptManage> GetListAcceptManage(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListAcceptManage(session, item);
        } catch (Exception e) {
            return new ArrayList<AcceptManage>();
        } finally {
            session.close();
        }
    }

    public static List<AcceptManage> GetListAcceptManage(SqlSession session, AcceptManage item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<AcceptManage>();
        default:
            return SqlDevDao.GetListAcceptManage(session, item);
        }
    }

    public static List<AcceptManage> GetListAcceptManageForSign(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListAcceptManageForSign(session, item);
        } catch (Exception e) {
            return new ArrayList<AcceptManage>();
        } finally {
            session.close();
        }
    }

    public static List<AcceptManage> GetListAcceptManageForSign(SqlSession session, AcceptManage item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<AcceptManage>();
        default:
            return SqlDevDao.GetListAcceptManageForSign(session, item);
        }
    }

    public static List<AcceptManage> SearchAcceptManage(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<AcceptManage>();
            default:
                return SqlDevDao.SearchAcceptManage(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<AcceptManage>();
        } finally {
            session.close();
        }
    }

    public static List<AcceptManage> SearchAcceptForUser(AcceptManage item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<AcceptManage>();
            default:
                return SqlDevDao.SearchAcceptForUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<AcceptManage>();
        } finally {
            session.close();
        }
    }

    public static void SaveAcceptManage(SqlSession session, AcceptManage item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveAcceptManage(session, item);
            break;
        }
    }

    // endregion AcceptManage Methods

    // region AcceptFileDetail Methods

    public static AcceptFileDetail GetAcceptFileDetail(AcceptFileDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAcceptFileDetail(session, item);
        } catch (Exception e) {
            return new AcceptFileDetail();
        } finally {
            session.close();
        }
    }

    public static AcceptFileDetail GetAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new AcceptFileDetail();
        default:
            return SqlDevDao.GetAcceptFileDetail(session, item);
        }
    }

    public static List<AcceptFileDetail> GetListAcceptFileDetail(AcceptFileDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListAcceptFileDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<AcceptFileDetail>();
        } finally {
            session.close();
        }
    }

    public static List<AcceptFileDetail> GetListAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<AcceptFileDetail>();
        default:
            return SqlDevDao.GetListAcceptFileDetail(session, item);
        }
    }

    public static List<AcceptFileDetail> SearchAcceptFileDetail(AcceptFileDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<AcceptFileDetail>();
            default:
                return SqlDevDao.SearchAcceptFileDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<AcceptFileDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveAcceptFileDetail(SqlSession session, AcceptFileDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveAcceptFileDetail(session, item);
            break;
        }
    }

    // endregion AcceptFileDetail Methods

    // region DevCalib Methods

    public static DevCalib GetDevCalib(DevCalib item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCalib(session, item);
        } catch (Exception e) {
            return new DevCalib();
        } finally {
            session.close();
        }
    }

    public static DevCalib GetDevCalib(SqlSession session, DevCalib item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCalib();
        default:
            return SqlDevDao.GetDevCalib(session, item);
        }
    }

    public static DevCalib GetDevCalibByTranID(DevCalib item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCalibByTranID(session, item);
        } catch (Exception e) {
            return new DevCalib();
        } finally {
            session.close();
        }
    }

    public static DevCalib GetDevCalibByTranID(SqlSession session, DevCalib item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCalib();
        default:
            return SqlDevDao.GetDevCalibByTranID(session, item);
        }
    }

    public static List<DevCalib> SearchDevCalib(DevCalib item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCalib>();
            default:
                return SqlDevDao.SearchDevCalib(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCalib>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevCalib(SqlSession session, DevCalib item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevCalib(session, item);
            break;
        }
    }

    // endregion DevCalib Methods

    // region DevCalibPlan Methods

    public static DevCalibPlan GetDevCalibPlan(DevCalibPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCalibPlan(session, item);
        } catch (Exception e) {
            return new DevCalibPlan();
        } finally {
            session.close();
        }
    }

    public static DevCalibPlan GetDevCalibPlan(SqlSession session, DevCalibPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCalibPlan();
        default:
            return SqlDevDao.GetDevCalibPlan(session, item);
        }
    }

    public static List<DevCalibPlan> GetListDevCalibPlan(DevCalibPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevCalibPlan(session, item);
        } catch (Exception e) {
            return new ArrayList<DevCalibPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevCalibPlan> GetListDevCalibPlan(SqlSession session, DevCalibPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevCalibPlan>();
        default:
            return SqlDevDao.GetListDevCalibPlan(session, item);
        }
    }

    public static List<DevCalibPlan> SearchDevCalibPlan(DevCalibPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCalibPlan>();
            default:
                return SqlDevDao.SearchDevCalibPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCalibPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevCalibPlan> SearchDevCalibPlanForCalib(DevCalibPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCalibPlan>();
            default:
                return SqlDevDao.SearchDevCalibPlanForCalib(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCalibPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevCalibPlan(SqlSession session, DevCalibPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevCalibPlan(session, item);
            break;
        }
    }

    // endregion DevCalibPlan Methods

    // region DevCheckPlan Methods

    public static DevCheckPlan GetDevCheckPlan(DevCheckPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCheckPlan(session, item);
        } catch (Exception e) {
            return new DevCheckPlan();
        } finally {
            session.close();
        }
    }

    public static DevCheckPlan GetDevCheckPlan(SqlSession session, DevCheckPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCheckPlan();
        default:
            return SqlDevDao.GetDevCheckPlan(session, item);
        }
    }

    public static List<DevCheckPlan> GetListDevCheckPlan() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevCheckPlan(session);
        } catch (Exception e) {
            return new ArrayList<DevCheckPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevCheckPlan> GetListDevCheckPlan(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevCheckPlan>();
        default:
            return SqlDevDao.GetListDevCheckPlan(session);
        }
    }

    public static List<DevCheckPlan> SearchDevCheckPlan(DevCheckPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCheckPlan>();
            default:
                return SqlDevDao.SearchDevCheckPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCheckPlan>();
        } finally {
            session.close();
        }
    }

    public static List<DevCheckPlan> SearchDevCheckPlanForCheck(DevCheckPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCheckPlan>();
            default:
                return SqlDevDao.SearchDevCheckPlanForCheck(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCheckPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevCheckPlan(SqlSession session, DevCheckPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevCheckPlan(session, item);
            break;
        }
    }

    // endregion DevCheckPlan Methods

    // region DevCheck Methods

    public static DevCheck GetDevCheck(DevCheck item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCheck(session, item);
        } catch (Exception e) {
            return new DevCheck();
        } finally {
            session.close();
        }
    }

    public static DevCheck GetDevCheck(SqlSession session, DevCheck item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCheck();
        default:
            return SqlDevDao.GetDevCheck(session, item);
        }
    }

    public static List<DevCheck> GetListDevCheck(DevCheck item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevCheck(session, item);
        } catch (Exception e) {
            return new ArrayList<DevCheck>();
        } finally {
            session.close();
        }
    }

    public static List<DevCheck> GetListDevCheck(SqlSession session, DevCheck item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevCheck>();
        default:
            return SqlDevDao.GetListDevCheck(session, item);
        }
    }

    public static List<DevCheck> SearchDevCheck(DevCheck item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevCheck>();
            default:
                return SqlDevDao.SearchDevCheck(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevCheck>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevCheck(SqlSession session, DevCheck item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevCheck(session, item);
            break;
        }
    }

    // endregion DevCheck Methods

    // region RepairApply Methods

    public static RepairApply GetRepairApply(RepairApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetRepairApply(session, item);
        } catch (Exception e) {
            return new RepairApply();
        } finally {
            session.close();
        }
    }

    public static RepairApply GetRepairApply(SqlSession session, RepairApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new RepairApply();
        default:
            return SqlDevDao.GetRepairApply(session, item);
        }
    }

    public static List<RepairApply> GetListRepairApply(RepairApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListRepairApply(session, item);
        } catch (Exception e) {
            return new ArrayList<RepairApply>();
        } finally {
            session.close();
        }
    }

    public static List<RepairApply> GetListRepairApply(SqlSession session, RepairApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<RepairApply>();
        default:
            return SqlDevDao.GetListRepairApply(session, item);
        }
    }

    public static void SaveRepairApply(SqlSession session, RepairApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveRepairApply(session, item);
            break;
        }
    }

    public static List<RepairApply> SearchRepairApply(RepairApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<RepairApply>();
            default:
                return SqlDevDao.SearchRepairApply(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<RepairApply>();
        } finally {
            session.close();
        }
    }

    public static List<RepairApply> SearchRepairApplyForRecord(RepairApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<RepairApply>();
            default:
                return SqlDevDao.SearchRepairApplyForRecord(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<RepairApply>();
        } finally {
            session.close();
        }
    }

    // endregion RepairApply Methods

    // region RepairRecord Methods

    public static RepairRecord GetRepairRecord(RepairRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetRepairRecord(session, item);
        } catch (Exception e) {
            return new RepairRecord();
        } finally {
            session.close();
        }
    }

    public static RepairRecord GetRepairRecord(SqlSession session, RepairRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new RepairRecord();
        default:
            return SqlDevDao.GetRepairRecord(session, item);
        }
    }

    public static List<RepairRecord> GetListRepairRecord(RepairRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListRepairRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<RepairRecord>();
        } finally {
            session.close();
        }
    }

    public static List<RepairRecord> GetListRepairRecord(SqlSession session, RepairRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<RepairRecord>();
        default:
            return SqlDevDao.GetListRepairRecord(session, item);
        }
    }

    public static List<RepairRecord> SearchRepairRecord(RepairRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<RepairRecord>();
            default:
                return SqlDevDao.SearchRepairRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<RepairRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveRepairRecord(SqlSession session, RepairRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveRepairRecord(session, item);
            break;
        }
    }

    // endregion RepairRecord Methods

    // region AcceptPartsDetail Methods

    public static AcceptPartsDetail GetAcceptPartsDetail(AcceptPartsDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAcceptPartsDetail(session, item);
        } catch (Exception e) {
            return new AcceptPartsDetail();
        } finally {
            session.close();
        }
    }

    public static AcceptPartsDetail GetAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new AcceptPartsDetail();
        default:
            return SqlDevDao.GetAcceptPartsDetail(session, item);
        }
    }

    public static List<AcceptPartsDetail> GetListAcceptPartsDetail(AcceptPartsDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListAcceptPartsDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<AcceptPartsDetail>();
        } finally {
            session.close();
        }
    }

    public static List<AcceptPartsDetail> GetListAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<AcceptPartsDetail>();
        default:
            return SqlDevDao.GetListAcceptPartsDetail(session, item);
        }
    }

    public static List<AcceptPartsDetail> SearchAcceptPartsDetail(AcceptPartsDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<AcceptPartsDetail>();
            default:
                return SqlDevDao.SearchAcceptPartsDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<AcceptPartsDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveAcceptPartsDetail(SqlSession session, AcceptPartsDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveAcceptPartsDetail(session, item);
            break;
        }
    }

    // endregion AcceptPartsDetail Methods

    // region DevBasic Methods

    public static DevBasic GetDevBasic(DevBasic item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevBasic(session, item);
        } catch (Exception e) {
            return new DevBasic();
        } finally {
            session.close();
        }
    }

    public static DevBasic GetDevBasic(SqlSession session, DevBasic item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevBasic();
        default:
            return SqlDevDao.GetDevBasic(session, item);
        }
    }

    public static List<DevBasic> GetListDevBasic(DevBasic item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevBasic(session, item);
        } catch (Exception e) {
            return new ArrayList<DevBasic>();
        } finally {
            session.close();
        }
    }

    public static List<DevBasic> GetListDevBasic(SqlSession session, DevBasic item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevBasic>();
        default:
            return SqlDevDao.GetListDevBasic(session, item);
        }
    }

    public static List<DevBasic> SearchDevBasic(DevBasic item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevBasic>();
            default:
                return SqlDevDao.SearchDevBasic(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevBasic>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevBasic(SqlSession session, DevBasic item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevBasic(session, item);
            break;
        }
    }

    // endregion DevBasic Methods

    // region DevTest Methods

    public static DevTest GetDevTest(DevTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevTest(session, item);
        } catch (Exception e) {
            return new DevTest();
        } finally {
            session.close();
        }
    }

    public static DevTest GetDevTest(SqlSession session, DevTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevTest();
        default:
            return SqlDevDao.GetDevTest(session, item);
        }
    }

    public static List<DevTest> GetListDevTest(DevTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListDevTest(session, item);
        } catch (Exception e) {
            return new ArrayList<DevTest>();
        } finally {
            session.close();
        }
    }

    public static List<DevTest> GetListDevTest(SqlSession session, DevTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<DevTest>();
        default:
            return SqlDevDao.GetListDevTest(session, item);
        }
    }

    public static List<DevTest> SearchDevTest(DevTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevTest>();
            default:
                return SqlDevDao.SearchDevTest(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<DevTest>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevTest(SqlSession session, DevTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevTest(session, item);
            break;
        }
    }

    // endregion DevTest Methods

    // region DevUseApply Methods
    public static List<DevUseApply> SearchDevUseApply(DevUseApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevUseApply>();
            default:
                return SqlDevDao.SearchDevUseApply(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<DevUseApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevUseApply(SqlSession session, DevUseApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevUseApply(session, item);
            break;
        }
    }

    public static DevUseApply GetDevUseApply(DevUseApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevUseApply(session, item);
        } catch (Exception e) {
            return new DevUseApply();
        } finally {
            session.close();
        }
    }

    public static DevUseApply GetDevUseApply(SqlSession session, DevUseApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevUseApply();
        default:
            return SqlDevDao.GetDevUseApply(session, item);
        }
    }
    // end region DevUseApply Methods

    // region DevUseAllot Methods
    public static List<DevUseAllot> SearchDevUseAllot(DevUseAllot item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevUseAllot>();
            default:
                return SqlDevDao.SearchDevUseAllot(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<DevUseAllot>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevUseAllot(SqlSession session, DevUseAllot item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevUseAllot(session, item);
            break;
        }
    }
    // end region DevUseAllot Methods

    // region devuseback methods
    public static List<DevUseBack> SearchDevUseBack(DevUseBack item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<DevUseBack>();
            default:
                return SqlDevDao.SearchDevUseBack(session, item);
            }

        } catch (Exception e) {
            return new ArrayList<DevUseBack>();
        } finally {
            session.close();
        }
    }

    public static void SaveDevUseBack(SqlSession session, DevUseBack item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlDevDao.SaveDevUseBack(session, item);
            break;
        }
    }

    public static DevCommon GetDevCommonByTranID(DevCommon rtn) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetDevCommonByTranID(session, rtn);
        } catch (Exception e) {
            return new DevCommon();
        } finally {
            session.close();
        }
    }

    public static DevCommon GetDevCommonByTranID(SqlSession session, DevCommon item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new DevCommon();
        default:
            return SqlDevDao.GetDevCommonByTranID(session, item);
        }
    }

    // end region devuseback mehtods
}
