package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.FlowDao;
import com.alms.dao.StaffDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.flow.BusTodoNow;
import com.alms.entity.staff.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class StaffService {

    // region BasUser Methods

    public static void SaveBasUser(BasUser item, List<BasUserFile> details, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
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

            StaffDao.SaveBasUser(session, item);

            BasUserFile file = new BasUserFile();
            file.setTranid(item.getTranid());
            file.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveBasUserFile(session, file);

            String logs = "";
            logs = item.getUserid();

            // 添加培训计划详细列表
            for (BasUserFile detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveBasUserFile(session, detail);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion BasUser Methods

    // region UserExamResult Methods

    public static void SaveUserExamResult(UserExamResult item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setTranuser(ou.getUser().getUserid());
            item.setTranuser(ou.getUser().getUsername());

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("UserExamResult");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StaffDao.SaveUserExamResult(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getExamid());
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

    // endregion UserExamResult Methods

    // region UserExamReport Methods

    public static void SaveUserExamReport(UserExamReport item, List<UserExamGroup> groupdetails,
            List<UserExamDev> devdetails, List<UserExamFile> filedetails, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setTranuser(ou.getUser().getUsername());

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getExamid());
                todo.setBusflow("UserExamReport");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StaffDao.SaveUserExamReport(session, item);

            // 添加小组明细
            UserExamGroup examgroup = new UserExamGroup();
            examgroup.setExamid(item.getExamid());
            examgroup.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserExamGroup(session, examgroup);

            for (UserExamGroup exam : groupdetails) {
                exam.setExamid(item.getExamid());
                exam.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserExamGroup(session, exam);
            }

            // 添加设备明细
            UserExamDev examDev = new UserExamDev();
            examDev.setExamid(item.getExamid());
            examDev.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserExamDev(session, examDev);

            for (UserExamDev exam : devdetails) {
                exam.setExamid(item.getExamid());
                exam.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserExamDev(session, exam);
            }

            // 添加附件明细
            UserExamFile examFile = new UserExamFile();
            examFile.setExamid(item.getExamid());
            examFile.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserExamFile(session, examFile);

            for (UserExamFile file : filedetails) {
                file.setExamid(item.getExamid());
                file.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserExamFile(session, file);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getExamid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"examid\":\"" + item.getExamid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion UserExamReport Methods

    // region UserExamItem Methods

    public static void SaveUserExamItem(UserExamItem item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            StaffDao.SaveUserExamItem(session, item);

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

    // endregion UserExamItem Methods

    // region UserExamRecord Methods

    public static void SaveUserExamRecord(UserExamRecord item, List<UserExamDetail> details, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowstatus("01");
            }

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowstatus("02");
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getExamid());
                todo.setBusflow("UserExamRecord");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StaffDao.SaveUserExamRecord(session, item);

            UserExamDetail examDetail = new UserExamDetail();
            examDetail.setExamid(item.getExamid());
            examDetail.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserExamDetail(session, examDetail);

            for (UserExamDetail exam : details) {
                exam.setExamid(item.getExamid());
                exam.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserExamDetail(session, exam);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getExamid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"examid\":\"" + item.getExamid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion UserExamRecord Methods

    // region UserTrain Methods

    public static void SaveUserTrain(UserTrain item, String details, ReturnValue rtv, TranLog log) {
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
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
            }

            StaffDao.SaveUserTrain(session, item);

            // 培训业务计划明细 状态修改 01：未进行 02：进行中 03 已结束
            UserPlanYearDetail yearDetail = new UserPlanYearDetail();
            yearDetail.setTranid(item.getRelaid());
            yearDetail = StaffDao.GetUserPlanYearDetail(yearDetail);
            if (yearDetail != null) {
                yearDetail.setTrainstatus("02");
                yearDetail.getDeal().setAction(DataAction.Modify.getAction());
                StaffDao.SaveUserPlanYearDetail(session, yearDetail);
            }

            UserTrainDetail train = new UserTrainDetail();
            train.setTranid(item.getTranid());
            train.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserTrainDetail(session, train);

            // 添加培训人员
            String[] trains = details.split(",");

            for (String userid : trains) {
                train.setTranid(item.getTranid());
                train.setUserid(userid);
                train.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserTrainDetail(session, train);
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

    // endregion UserTrain Methods

    // region RecordSummary Methods

    public static void SaveRecordSummary(RecordSummary item, String details, ReturnValue rtv, TranLog log) {
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
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("RecordSummary");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StaffDao.SaveRecordSummary(session, item);

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

    // endregion RecordSummary Methods

    // region UserPlanYear Methods

    public static void SaveUserPlanYear(UserPlanYear item, List<UserPlanYearDetail> details, ReturnValue rtv,
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
            item.setDeptid(ou.getUser().getDeptid());
            item.setTrandate(new Date());
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");
                item.setModifyserial(item.getModifyserial() + 1);
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setBusflow("UserPlanYear");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StaffDao.SaveUserPlanYear(session, item);

            UserPlanYearDetail plan = new UserPlanYearDetail();
            plan.setRelaid(item.getTranid());
            plan.getDeal().setAction(DataAction.Delete.getAction());
            StaffDao.SaveUserPlanYearDetail(session, plan);

            String logs = "";
            logs = item.getTranid();

            // 添加培训计划详细列表
            for (UserPlanYearDetail detail : details) {
                detail.setRelaid(item.getTranid());
                detail.setTranyear(item.getTranyear());
                detail.getDeal().setAction(DataAction.Create.getAction());
                StaffDao.SaveUserPlanYearDetail(session, detail);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion UserPlanYear Methods

}
