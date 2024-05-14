package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.dao.DevDao;
import com.alms.entity.bas.BusTestedUnit;
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
import com.alms.entity.flow.BusTodo;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class DevService {

    // region BusTrade Methods

    public static void SaveBusTrade(BusTrade item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DevDao.SaveBusTrade(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTradeid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tradeid\":\"" + item.getTradeid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusTrade Methods

    // region TradeSurvey Methods

    public static void SaveTradeSurvey(TradeSurvey item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // 编辑供应商
            BusTrade bTrade = new BusTrade();
            if (item.getTradeid().equals("")) {
                bTrade.setTradename(item.getTradename());
                bTrade.setLinkaddress(item.getLinkaddress());
                bTrade.setLinkman(item.getLinkman());
                bTrade.setLinkpost(item.getLinkpost());
                bTrade.setLinktele(item.getLinktele());
                bTrade.setTradestatus("02");
                bTrade.getDeal().setAction(DataAction.Create.getAction());
            } else {
                bTrade.setTradeid(item.getTradeid());
                bTrade = DevDao.GetBusTrade(bTrade);
                bTrade.setTradename(item.getTradename());
                bTrade.setLinkaddress(item.getLinkaddress());
                bTrade.setLinkman(item.getLinkman());
                bTrade.setLinkpost(item.getLinkpost());
                bTrade.setLinktele(item.getLinktele());
                bTrade.getDeal().setAction(DataAction.Modify.getAction());
            }

            DevDao.SaveBusTrade(session, bTrade);
            item.setTradeid(bTrade.getTradeid());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("TradeSurvey");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            DevDao.SaveTradeSurvey(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\",\"tradeid\":\"" + item.getTradeid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveTradeCheck(TradeSurvey item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            TradeSurvey trsu = DevDao.GetTradeSurvey(item);
            if (trsu == null) {
                rtv.setMsg("该供应商不存在");
                return;
            } else {
                trsu.setFlowaction(item.getFlowaction());
                trsu.setFlowstatus(item.getFlowstatus());
                trsu.setEvaluser(ou.getUser().getUserid());
                trsu.setEvalusername(ou.getUser().getUsername());
                trsu.setEvaldesc(item.getEvaldesc());
                trsu.setEvaldate(new Date());
                trsu.getDeal().setAction(DataAction.Modify.getAction());
            }
            DevDao.SaveTradeCheck(session, trsu);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveTradeApprove(TradeSurvey item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            TradeSurvey trsu = DevDao.GetTradeSurvey(item);
            if (trsu == null) {
                rtv.setMsg("该供应商不存在");
                return;
            } else {
                trsu.setFlowaction(item.getFlowaction());
                trsu.setFlowstatus(item.getFlowstatus());
                trsu.setCheckuser(ou.getUser().getUserid());
                trsu.setCheckusername(ou.getUser().getUsername());
                trsu.getDeal().setAction(DataAction.Modify.getAction());
            }
            DevDao.SaveTradeCheck(session, trsu);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }
    // endregion TradeSurvey Methods

    // region BasDev Methods

    public static void SaveBasDev(BasDev item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setCrtuser(ou.getUser().getUserid());
            item.setCrtusername(ou.getUser().getUsername());
            item.setCrtdate(new Date());
            item.setOperateuser(ou.getUser().getUserid());
            item.setOperateusername(ou.getUser().getUsername());
            item.setOperatedate(new Date());
            item.setDevstatus("00");

            DevDao.SaveBasDev(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"devid\":\"" + item.getDevid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void OperateBasDev(BasDev item, List<BasDev> devs, String operatereasons, String devstatus,
            OnlineUser ou, ReturnValue rtv, TranLog log) {

        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        String devstatusname = "";
        try {

            for (BasDev basDev : devs) {
                basDev = DevDao.GetBasDev(basDev);
                basDev.setOperateuser(ou.getUser().getUserid());
                basDev.setOperateusername(ou.getUser().getUsername());
                basDev.setOperatedate(new Date());
                basDev.setDevstatus(devstatus);
                basDev.setOperatereason(operatereasons);
                basDev.getDeal().setAction(DataAction.Deal.getAction());
                DevDao.SaveBasDev(session, basDev);

                if (devstatus.equals("05")) { // 05为停用申请状态
                    // 业务流
                    BusTodo todo = new BusTodo();
                    todo.setTranid(basDev.getDevid());
                    todo.setLabid("devid");
                    todo.setBusflow("OperateDev");
                    FlowService.FlowCreate(session, todo, rtv, log);
                }

            }

            if (devstatus.equals("00")) {
                devstatusname = "设备开启成功!";
            } else if (devstatus.equals("05")) {
                devstatusname = "设备停用申请已发出!";
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(devstatusname);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(devstatusname);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasDev Methods

    // region DevScrap Methods

    public static void SaveDevScrap(DevScrap item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            item.setTrandept(ou.getDept().getDeptid());
            item.setTrandeptname(ou.getDept().getDeptname());
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            // item.setFlowaction("01");
            // item.setFlowstatus("01");

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            DevDao.SaveDevScrap(session, item);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("DevScrap");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveDevScrapCheck(DevScrap item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            String flowaction = item.getFlowaction();
            String flowstatus = item.getFlowstatus();
            String checkdesc = item.getCheckdesc();

            DevScrap devscrap = DevDao.GetDevScrap(item);
            devscrap.setFlowaction(flowaction);
            devscrap.setFlowstatus(flowstatus);
            devscrap.setCheckdesc(checkdesc);
            devscrap.setCheckdate(new Date());
            devscrap.setCheckuser(ou.getUser().getUserid());
            devscrap.setCheckusername(ou.getUser().getUsername());
            devscrap.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveDevScrap(session, devscrap);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveDevScrapAudit(DevScrap item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            String flowaction = item.getFlowaction();
            String flowstatus = item.getFlowstatus();
            String auditdesc = item.getAuditdesc();

            DevScrap devscrap = DevDao.GetDevScrap(item);
            devscrap.setFlowaction(flowaction);
            devscrap.setFlowstatus(flowstatus);
            devscrap.setAuditdesc(auditdesc);
            devscrap.setAuditdate(new Date());
            devscrap.setAudituser(ou.getUser().getUserid());
            devscrap.setAuditusername(ou.getUser().getUsername());
            devscrap.setAuditdept(ou.getDept().getDeptid());
            devscrap.setAuditdeptname(ou.getDept().getDeptname());
            devscrap.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveDevScrap(session, devscrap);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveDevScrapAllow(DevScrap item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            String flowaction = item.getFlowaction();
            String flowstatus = item.getFlowstatus();
            String approvedesc = item.getApprovedesc();
            String devstatus = item.getDevstatus();

            DevScrap devscrap = DevDao.GetDevScrap(item);
            devscrap.setFlowaction(flowaction);
            devscrap.setFlowstatus(flowstatus);
            devscrap.setApprovedesc(approvedesc);
            devscrap.setDevstatus(devstatus);
            devscrap.setApprovedate(new Date());
            devscrap.setApproveuser(ou.getUser().getUserid());
            devscrap.setApproveusername(ou.getUser().getUsername());
            devscrap.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveDevScrap(session, devscrap);

            BasDev basdev = new BasDev();
            basdev.setDevid(item.getDevid());
            basdev = DevDao.GetBasDev(basdev);
            basdev.setDevstatus(devstatus);
            basdev.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveBasDev(session, basdev);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevScrap Methods

    // region DevUse Methods

    public static void SaveDevUse(DevUse item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            item.setFlowaction("01");
            item.setFlowstatus("01");
            DevDao.SaveDevUse(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevUse Methods

    // region DevPlan Methods

    public static void SaveDevPlan(DevPlan item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setDevmanager(ou.getUser().getUserid());
            item.setDevmanagername(ou.getUser().getUsername());
            item.setDevmanagerdate(new Date());

            DevDao.SaveDevPlan(session, item);

            BasDev basDev = new BasDev();
            basDev.setDevid(item.getDevid());
            basDev = DevDao.GetBasDev(basDev);
            basDev.setUsehumid(item.getUsagehumid());
            basDev.setUsetemp(item.getUsagetemp());
            basDev.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveBasDev(session, basDev);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getPlanid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"planid\":\"" + item.getPlanid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevPlan Methods

    // region DevCommon Methods

    public static void SaveDevCommon(DevCommon item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // item.setDevmanager(ou.getUser().getUserid());
            // item.setDevmanagername(ou.getUser().getUsername());
            // item.setManagerdate(new Date());

            DevDao.SaveDevCommon(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            // todo.setLabid(item.getCalibunit());
            todo.setBusflow("DevCommon");
            FlowService.FlowCreate(session, todo, rtv, log);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevCommon Methods

    // region BuyApply Methods

    public static void SaveBuyApply(BuyApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            item.setApplydept(ou.getDept().getDeptid());
            item.setApplydeptname(ou.getDept().getDeptname());
            item.setApplyuser(ou.getUser().getUserid());
            item.setApplyusername(ou.getUser().getUsername());
            item.setApplydate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            DevDao.SaveBuyApply(session, item);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setLabid("tranid");
                todo.setBusflow("DevApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 设备申请办公室审核
    public static void JudgeBuyApply(BusTodo item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            BuyApply dev = new BuyApply();
            dev.setTranid(item.getTranid());
            dev = DevDao.GetBuyApply(dev);
            if (dev != null) {
                dev.setJudgeuser(ou.getUser().getUserid());
                dev.setJudgeusername(ou.getUser().getUsername());
                dev.setJudgedate(new Date());
                dev.setJudgedesc(item.getTododesc());
                dev.setFlowaction("02");
                dev.setFlowstatus("05");
                dev.getDeal().setAction(DataAction.Modify.getAction());
                DevDao.SaveBuyApply(session, dev);
                // 业务流
                FlowService.FlowDeal(session, item, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 办公室审核驳回
    public static void UnJudgeBuyApply(BusTodo item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            BuyApply dev = new BuyApply();
            dev.setTranid(item.getTranid());
            dev = DevDao.GetBuyApply(dev);
            if (dev != null) {
                dev.setJudgeuser(ou.getUser().getUserid());
                dev.setJudgeusername(ou.getUser().getUsername());
                dev.setJudgedate(new Date());
                dev.setJudgedesc(item.getTododesc());
                dev.setFlowaction("02");
                dev.setFlowstatus("04");
                dev.getDeal().setAction(DataAction.Modify.getAction());
                DevDao.SaveBuyApply(session, dev);
                // 业务流
                FlowService.FlowBack(session, item, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BuyApply Methods

    // region AcceptManage Methods

    public static void SaveAcceptManage(AcceptManage item, List<AcceptFileDetail> acceptfiledetails,
            List<AcceptPartsDetail> acceptpartsdetails, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // item.setDevmanager(ou.getUser().getUserid());
            // item.setDevmanagername(ou.getUser().getUsername());
            item.setManagerdate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            DevDao.SaveAcceptManage(session, item);

            // 添加随机文件
            AcceptFileDetail acceptfile = new AcceptFileDetail();
            acceptfile.setTranid(item.getTranid());
            acceptfile.getDeal().setAction(DataAction.Delete.getAction());
            DevDao.SaveAcceptFileDetail(session, acceptfile);

            for (AcceptFileDetail accept : acceptfiledetails) {
                accept.setTranid(item.getTranid());
                accept.getDeal().setAction(DataAction.Create.getAction());
                DevDao.SaveAcceptFileDetail(session, accept);
            }

            // 添加验收配件
            AcceptPartsDetail acceptparts = new AcceptPartsDetail();
            acceptparts.setAcceptid(item.getTranid());
            acceptparts.getDeal().setAction(DataAction.Delete.getAction());
            DevDao.SaveAcceptPartsDetail(session, acceptparts);

            for (AcceptPartsDetail acceptpart : acceptpartsdetails) {
                acceptpart.setAcceptid(item.getTranid());
                acceptpart.getDeal().setAction(DataAction.Create.getAction());
                DevDao.SaveAcceptPartsDetail(session, acceptpart);
            }

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 业务流
                // SysUser user = UserDao.GetUser(item.getDeptuser());
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                // todo.setLabid(user.getDeptid());
                todo.setBusflow("DevAccept");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\",\"devid\":\"" + item.getDevid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion AcceptManage Methods

    // region AcceptFileDetail Methods

    public static void SaveAcceptFileDetail(AcceptFileDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DevDao.SaveAcceptFileDetail(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion AcceptFileDetail Methods

    // region DevCalib Methods

    public static void SaveDevCalib(DevCalib item, ReturnValue rtv, String calibplanid, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getDept().getDeptid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                // todo.setLabid(item.getCalibunit());
                todo.setBusflow("DevCalib");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            DevDao.SaveDevCalib(session, item);

            // DevCalibPlan devCalibPlan = new DevCalibPlan();
            // devCalibPlan.setTranid(calibplanid);
            // devCalibPlan = DevDao.GetDevCalibPlan(devCalibPlan);
            // devCalibPlan.setFlowstatus("90");
            // devCalibPlan.getDeal().setAction(DataAction.Modify.getAction());
            // DevDao.SaveDevCalibPlan(session, devCalibPlan);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevCalib Methods

    // region DevCalibPlan Methods

    public static void SaveDevCalibPlan(DevCalibPlan item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setLabid(item.getDevmanager());
                todo.setBusflow("DevCalibPlan");
                FlowService.FlowCreate(session, todo, rtv, log);

                // 接收后删除接收提醒
                // BusTodo bt = new BusTodo();
                // bt.setTranid(item.getDevid());
                // bt.setTranuser(ou.getUser().getUserid());
                // bt.getDeal().setAction(DataAction.Deal.getAction());
                // FlowDao.SaveBusTodo(session, bt);

                // BusTodo busTodo = new BusTodo();
                // busTodo.setTranid(item.getDevid());
                // busTodo.setBusflow("DevCalibPlan");
                // busTodo.setFlownode("create");
                // busTodo.setSenddate(new Date());
                // busTodo.setTranuser(ou.getUser().getUserid());
                // busTodo.setTrandept(ou.getDept().getDeptid());
                // busTodo.setTrandate(item.getNextdate());
                // busTodo.setIsnowflow(true);
                // busTodo.setSampleid(String.valueOf(1));
                // busTodo.setTodotype("11");
                // busTodo.getDeal().setAction(DataAction.Create.getAction());
                // FlowDao.SaveBusTodo(session, busTodo);

            }

            DevDao.SaveDevCalibPlan(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevCalibPlan Methods

    // region DevCheck Methods

    public static void SaveDevCheck(DevCheck item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setDevmanager(ou.getUser().getUserid());
            item.setDevmanagername(ou.getUser().getUsername());
            item.setManagerdate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                BusTodo todo = new BusTodo();
                todo.setTranid(item.getCheckid());
                // todo.setLabid(item.getCalibunit());
                todo.setBusflow("DevCheck");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            DevDao.SaveDevCheck(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getCheckid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"checkid\":\"" + item.getCheckid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevCheck Methods

    // region DevCheckPlan Methods

    public static void SaveDevCheckPlan(DevCheckPlan item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getDept().getDeptid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                // BusTodo bt = new BusTodo();
                // bt.setTranid(item.getDevid());
                // bt.setTranuser(ou.getUser().getUserid());
                // bt.getDeal().setAction(DataAction.Deal.getAction());
                // FlowDao.SaveBusTodo(session, bt);

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setLabid(item.getDevmanager());
                todo.setBusflow("DevCheckPlan");
                FlowService.FlowCreate(session, todo, rtv, log);

                // BusTodo busTodo = new BusTodo();
                // busTodo.setTranid(item.getDevid());
                // busTodo.setBusflow("DevCheckPlan");
                // busTodo.setFlownode("create");
                // busTodo.setSenddate(new Date());
                // busTodo.setTranuser(ou.getUser().getUserid());
                // busTodo.setTrandept(ou.getDept().getDeptid());
                // busTodo.setTrandate(item.getNextdate());
                // busTodo.setIsnowflow(true);
                // busTodo.setSampleid(String.valueOf(1));
                // busTodo.setTodotype("12");
                // busTodo.getDeal().setAction(DataAction.Create.getAction());
                // FlowDao.SaveBusTodo(session, busTodo);
            }

            DevDao.SaveDevCheckPlan(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevCheckPlan Methods

    // region RepairApply Methods

    public static void SaveRepairApply(RepairApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setManageruser(ou.getUser().getUserid());
            item.setManagerusername(ou.getUser().getUsername());
            item.setDevmanagerdate(new Date());

            // item.setFlowaction("01");
            // item.setFlowstatus("01");
            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            DevDao.SaveRepairApply(session, item);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("DevRepair");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion RepairApply Methods

    // region RepairRecord Methods

    public static void SaveRepairRecord(RepairRecord item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            // item.setFlowaction("01");
            // item.setFlowstatus("01");

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            DevDao.SaveRepairRecord(session, item);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("RepairRecord");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion RepairRecord Methods

    // region AcceptPartsDetail Methods

    public static void SaveAcceptPartsDetail(AcceptPartsDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DevDao.SaveAcceptPartsDetail(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getAcceptid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion AcceptPartsDetail Methods

    // region DevBasic Methods

    public static void SaveDevBasic(DevBasic item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            DevDao.SaveDevBasic(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"devid\":\"" + item.getDevid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevBasic Methods

    // region DevTest Methods

    public static void SaveDevTest(DevTest item, ReturnValue rtv, List<DevTest> devtestdetails, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.getDeal().setAction(DataAction.Delete.getAction());
            DevDao.SaveDevTest(session, item);

            for (DevTest devtest : devtestdetails) {
                devtest.setDevid(item.getDevid());
                devtest.getDeal().setAction(DataAction.Create.getAction());
                DevDao.SaveDevTest(session, devtest);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevTest Methods

    // region DevUseTest Methods
    public static void SaveDevUseApply(DevUseApply item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */
            item.setTranuser(ToolUtils.GetOnlineUser().getUser().getUserid());
            item.setTranusername(ToolUtils.GetOnlineUser().getUser().getUsername());
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
                // 设备借出状态变更为 借出申请
                if (item.getBorrowstatu().equals("05")) {

                } else {
                    item.setBorrowstatu("02");
                }

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("DevUseApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            DevDao.SaveDevUseApply(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevUseTest Methods

    // region DevUseAllot Methods
    public static void SaveDevUseAllot(DevUseAllot item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */

            DevDao.SaveDevUseAllot(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DevUseAllot

    // region devuseback methods
    public static void SaveDevUseBack(DevUseBack item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            /*
             * 更改业务状态 flowaction: 01 申请， 20 审核， 02办公室审核 ; flowstatus:01预存,02提交
             */

            DevDao.SaveDevUseBack(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getDevid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }
    // endregion devuseback methods
}
