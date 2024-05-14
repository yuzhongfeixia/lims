package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.QuanDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.quan.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class QuanService {

    // region QuanMonitSamItem Methods

    public static void SaveQuanMonitSamItem(QuanMonitSamItem item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            QuanDao.SaveQuanMonitSamItem(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
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

    // endregion QuanMonitSamItem Methods

    // region QuanMonitBigItem Methods

    public static void SaveQuanMonitBigItem(QuanMonitBigItem item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            QuanDao.SaveQuanMonitBigItem(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
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

    // endregion QuanMonitBigItem Methods

    // region QuanMonitWork Methods

    public static void SaveQuanMonitWork(QuanMonitWork item, List<QuanMonitWorkDetail> details, ReturnValue rtv,
            OnlineUser ou, TranLog log) {
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

            QuanDao.SaveQuanMonitWork(session, item);

            // 增加记录明细
            QuanMonitWorkDetail workDetail = new QuanMonitWorkDetail();
            workDetail.setTranid(item.getTranid());
            workDetail.getDeal().setAction(DataAction.Delete.getAction());
            QuanDao.SaveQuanMonitWorkDetail(session, workDetail);

            for (QuanMonitWorkDetail work : details) {
                work.setTranid(item.getTranid());
                work.getDeal().setAction(DataAction.Create.getAction());
                QuanDao.SaveQuanMonitWorkDetail(session, work);
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

    // endregion QuanMonitWork Methods

    // region QuanMonitOption Methods

    public static void SaveQuanMonitOption(QuanMonitOption item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            QuanDao.SaveQuanMonitOption(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getOptionid());
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

    // endregion QuanMonitOption Methods

    // region QuanControlPlan Methods

    public static void SaveQuanControlPlan(QuanControlPlan item, List<QuanControlPlanDetail> details, ReturnValue rtv,
            OnlineUser ou, TranLog log) {
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
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
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
                todo.setBusflow("QuanControlPlan");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            QuanDao.SaveQuanControlPlan(session, item);

            // 保存计划明细
            QuanControlPlanDetail planDetail = new QuanControlPlanDetail();
            planDetail.setTranid(item.getTranid());
            planDetail.getDeal().setAction(DataAction.Delete.getAction());
            QuanDao.SaveQuanControlPlanDetail(session, planDetail);

            for (QuanControlPlanDetail plan : details) {
                plan.setTranid(item.getTranid());
                plan.getDeal().setAction(DataAction.Create.getAction());
                QuanDao.SaveQuanControlPlanDetail(session, plan);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"," + "\"flowaction\":\"" + item.getFlowaction() + "\","
                    + "\"flowstatus\":\"" + item.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion QuanControlPlan Methods

    // region CheckGroup Methods

    public static void SaveCheckGroup(CheckGroup item, List<CheckGroupMember> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            QuanDao.SaveCheckGroup(session, item);

            // 保存小组成员
            CheckGroupMember groupMember = new CheckGroupMember();
            groupMember.setGroupid(item.getGroupid());
            groupMember.getDeal().setAction(DataAction.Delete.getAction());
            QuanDao.SaveCheckGroupMember(session, groupMember);

            for (CheckGroupMember member : details) {
                member.setGroupid(item.getGroupid());
                member.getDeal().setAction(DataAction.Create.getAction());
                QuanDao.SaveCheckGroupMember(session, member);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getGroupid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"groupid\":\"" + item.getGroupid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion CheckGroup Methods

    // region QuanMonitPlan Methods

    public static void SaveQuanMonitPlan(QuanMonitPlan item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());

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
                todo.setBusflow("QuanMonitPlan");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            QuanDao.SaveQuanMonitPlan(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"," + "\"flowaction\":\"" + item.getFlowaction() + "\","
                    + "\"flowstatus\":\"" + item.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion QuanMonitPlan Methods

    // region QuanControlEval Methods

    public static void SaveQuanControlEval(QuanControlEval item, List<QuanControlUser> users,
            List<QuanControlSamp> samples, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
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
                todo.setBusflow("QuanControlEval");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            QuanDao.SaveQuanControlEval(session, item);

            // 添加参加人员
            QuanControlUser controlUser = new QuanControlUser();
            controlUser.setTranid(item.getTranid());
            controlUser.getDeal().setAction(DataAction.Delete.getAction());
            QuanDao.SaveQuanControlUser(session, controlUser);

            for (QuanControlUser user : users) {
                user.setTranid(item.getTranid());
                user.getDeal().setAction(DataAction.Create.getAction());
                QuanDao.SaveQuanControlUser(session, user);
            }

            // 添加样品明细
            QuanControlSamp controlSamp = new QuanControlSamp();
            controlSamp.setTranid(item.getTranid());
            controlSamp.getDeal().setAction(DataAction.Delete.getAction());
            QuanDao.SaveQuanControlSamp(session, controlSamp);

            for (QuanControlSamp samp : samples) {
                samp.setTranid(item.getTranid());
                samp.getDeal().setAction(DataAction.Create.getAction());
                QuanDao.SaveQuanControlSamp(session, samp);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"," + "\"flowaction\":\"" + item.getFlowaction() + "\","
                    + "\"flowstatus\":\"" + item.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion QuanControlEval Methods

    // region QuanCheckRecord Methods

    public static void SaveQuanCheckRecord(QuanCheckRecord item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("02");
                item.setFlowstatus("02");

                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("QuanCheckRecord");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            QuanDao.SaveQuanCheckRecord(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"," + "\"flowaction\":\"" + item.getFlowaction() + "\","
                    + "\"flowstatus\":\"" + item.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion QuanCheckRecord Methods

}
