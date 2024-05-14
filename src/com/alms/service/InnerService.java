package com.alms.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.InnerDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.inner.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class InnerService {

    // region InnerFoodReview Methods

    public static void SaveInnerFoodReview(InnerFoodReview item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            InnerDao.SaveInnerFoodReview(session, item);

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

    // endregion InnerFoodReview Methods

    // region InnerMeetSign Methods

    public static void SaveInnerMeetSign(InnerMeetSign item, List<InnerMeetPart> details, ReturnValue rtv,
            TranLog log) {
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
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }
            InnerDao.SaveInnerMeetSign(session, item);

            // 添加参会人员
            InnerMeetPart meetpart = new InnerMeetPart();
            meetpart.setMeetid(item.getMeetid());
            meetpart.getDeal().setAction(DataAction.Delete.getAction());
            InnerDao.SaveInnerMeetPart(session, meetpart);

            for (InnerMeetPart part : details) {
                part.setMeetid(item.getMeetid());
                part.getDeal().setAction(DataAction.Create.getAction());
                InnerDao.SaveInnerMeetPart(session, part);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getMeetid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"meetid\":\"" + item.getMeetid() + "\"," + "\"flowaction\":\"" + item.getFlowaction() + "\","
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

    // endregion InnerMeetSign Methods

    // region InnerReport Methods

    public static void SaveInnerReport(InnerReport item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            InnerDao.SaveInnerReport(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"reportid\":\"" + item.getReportid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion InnerReport Methods

    // region InnerInvalid Methods

    public static void SaveInnerInvalid(InnerInvalid item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("InnerInvalid");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            InnerDao.SaveInnerInvalid(session, item);

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

    // endregion InnerInvalid Methods

    // region InnerSceneWork Methods

    public static void SaveInnerSceneWork(InnerSceneWork item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            InnerDao.SaveInnerSceneWork(session, item);

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

    // endregion InnerSceneWork Methods

    // region InnerScene Methods

    public static void SaveInnerScene(InnerScene item, List<InnerSceneWork> details, ReturnValue rtv, TranLog log) {
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
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }
            InnerDao.SaveInnerScene(session, item);

            InnerSceneWork scenework = new InnerSceneWork();
            scenework.setTranid(item.getTranid());
            scenework.getDeal().setAction(DataAction.Delete.getAction());
            InnerDao.SaveInnerSceneWork(session, scenework);

            for (InnerSceneWork inner : details) {
                inner.setTranid(item.getTranid());
                inner.getDeal().setAction(DataAction.Create.getAction());
                InnerDao.SaveInnerSceneWork(session, inner);
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

    // endregion InnerScene Methods

    // region InnerGroup Methods

    public static void SaveInnerGroup(InnerGroup item, String details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            InnerDao.SaveInnerGroup(session, item);

            InnerGroupMember member = new InnerGroupMember();
            member.setGroupid(item.getGroupid());
            member.getDeal().setAction(DataAction.Delete.getAction());
            InnerDao.SaveInnerGroupMember(session, member);

            // 添加小组人员
            String[] members = details.split(",");

            for (String userid : members) {
                member.setGroupid(item.getGroupid());
                member.setMemberuser(userid);
                member.getDeal().setAction(DataAction.Create.getAction());
                InnerDao.SaveInnerGroupMember(session, member);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getGroupid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getGroupid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion InnerGroup Methods

    // region InnerYear Methods

    public static void SaveInnerYear(InnerYear item, List<InnerYearDetail> details, ReturnValue rtv, TranLog log) {
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
                todo.setBusflow("InnerYear");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            InnerDao.SaveInnerYear(session, item);

            InnerYearDetail yearDetail = new InnerYearDetail();
            yearDetail.setTranid(item.getTranid());
            yearDetail.getDeal().setAction(DataAction.Delete.getAction());
            InnerDao.SaveInnerYearDetail(session, yearDetail);

            for (InnerYearDetail year : details) {
                year.setTranid(item.getTranid());
                year.getDeal().setAction(DataAction.Create.getAction());
                InnerDao.SaveInnerYearDetail(session, year);
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

    // endregion InnerYear Methods

}
