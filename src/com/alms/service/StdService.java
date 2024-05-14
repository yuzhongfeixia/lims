package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.LabDao;
import com.alms.dao.StdDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.std.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class StdService {

    // region StdReview Methods

    public static void SaveStdReview(StdReview item, List<BusTaskAttach> details, ReturnValue rtv, OnlineUser ou,
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
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("StdReview");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            StdDao.SaveStdReview(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                String tranid = item.getTranid();
                BusTaskAttach dbta = new BusTaskAttach();
                dbta.setTranid(tranid);
                dbta.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusTaskAttach(session, dbta);
                for (BusTaskAttach bustaskattach : details) {
                    bustaskattach.setTranid(tranid);
                    bustaskattach.setAttachtype(bustaskattach.getAttachtype());
                    bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskAttach(session, bustaskattach);
                }

            }

            // //保存申请明细
            // StdReviewDetail review = new StdReviewDetail();
            // review.setTranid(item.getTranid());
            // review.getDeal().setAction(DataAction.Delete.getAction());
            // StdDao.SaveStdReviewDetail(session, review);
            //
            // for(StdReviewDetail detail:details){
            // detail.setTranid(item.getTranid());
            // detail.getDeal().setAction(DataAction.Create.getAction());
            // StdDao.SaveStdReviewDetail(session, detail);
            // }

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

    public static void SaveBusCountry(StdReview item, List<BusTaskAttach> details, ReturnValue rtv, OnlineUser ou,
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
                // 业务流
                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("BusCountry");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            StdDao.SaveBusCountry(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                String tranid = item.getTranid();
                BusTaskAttach dbta = new BusTaskAttach();
                dbta.setTranid(tranid);
                dbta.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusTaskAttach(session, dbta);
                for (BusTaskAttach bustaskattach : details) {
                    bustaskattach.setTranid(tranid);
                    bustaskattach.setAttachtype(bustaskattach.getAttachtype());
                    bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskAttach(session, bustaskattach);
                }

            }

            // //保存申请明细
            // StdReviewDetail review = new StdReviewDetail();
            // review.setTranid(item.getTranid());
            // review.getDeal().setAction(DataAction.Delete.getAction());
            // StdDao.SaveStdReviewDetail(session, review);
            //
            // for(StdReviewDetail detail:details){
            // detail.setTranid(item.getTranid());
            // detail.getDeal().setAction(DataAction.Create.getAction());
            // StdDao.SaveStdReviewDetail(session, detail);
            // }

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

    // endregion StdReview Methods

    // region StdUse Methods

    public static void SaveStdUse(StdUse item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            StdDao.SaveStdUse(session, item);

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

    // endregion StdUse Methods

    // region StdNonstd Methods

    public static void SaveStdNonstd(StdNonstd item, List<BusTaskAttach> details, List<BusRecordFile> filedetails,
            ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("NonStd");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            StdDao.SaveStdNonstd(session, item);
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                String tranid = item.getTranid();
                BusTaskAttach dbta = new BusTaskAttach();
                dbta.setTranid(tranid);
                dbta.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusTaskAttach(session, dbta);
                for (BusTaskAttach bustaskattach : details) {
                    bustaskattach.setTranid(tranid);
                    bustaskattach.setAttachtype(bustaskattach.getAttachtype());
                    bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskAttach(session, bustaskattach);
                }
                BusRecordFile bFile = new BusRecordFile();
                bFile.setTranid(tranid);
                bFile.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusRecordFile(session, bFile);
                for (BusRecordFile bRecordFile : filedetails) {
                    bRecordFile.setTranid(tranid);
                    bRecordFile.setAttachtype(bRecordFile.getAttachtype());
                    bRecordFile.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusRecordFile(session, bRecordFile);
                }

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

    // endregion StdNonstd Methods

    // region StdMethodDevi Methods

    public static void SaveStdMethodDevi(StdMethodDevi item, List<BusTaskAttach> details,
            List<BusRecordFile> filedetails, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("MethodDevi");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StdDao.SaveStdMethodDevi(session, item);
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                String tranid = item.getTranid();
                BusTaskAttach dbta = new BusTaskAttach();
                dbta.setTranid(tranid);
                dbta.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusTaskAttach(session, dbta);
                for (BusTaskAttach bustaskattach : details) {
                    bustaskattach.setTranid(tranid);
                    bustaskattach.setAttachtype(bustaskattach.getAttachtype());
                    bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskAttach(session, bustaskattach);
                }
                BusRecordFile bFile = new BusRecordFile();
                bFile.setTranid(tranid);
                bFile.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusRecordFile(session, bFile);
                for (BusRecordFile bRecordFile : filedetails) {
                    bRecordFile.setTranid(tranid);
                    bRecordFile.setAttachtype(bRecordFile.getAttachtype());
                    bRecordFile.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusRecordFile(session, bRecordFile);
                }

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

    // endregion StdMethodDevi Methods

    // region StdProApply Methods

    public static void SaveStdProApply(StdProApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                todo.setBusflow("ProApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StdDao.SaveStdProApply(session, item);

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

    // endregion StdProApply Methods

    // region StdTestSure Methods

    public static void SaveStdTestSure(StdTestSure item, ReturnValue rtv, List<StdSureDetail> stdSureDetails,
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
                todo.setLabid(ou.getUser().getDeptid());
                todo.setBusflow("TestSure");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            StdDao.SaveStdTestSure(session, item);

            // 添加随机文件
            StdSureDetail suredatail = new StdSureDetail();
            suredatail.setTranid(item.getTranid());
            suredatail.getDeal().setAction(DataAction.Delete.getAction());
            StdDao.SaveStdSureDetail(session, suredatail);

            for (StdSureDetail stdsuredetail : stdSureDetails) {
                stdsuredetail.setTranid(item.getTranid());
                stdsuredetail.getDeal().setAction(DataAction.Create.getAction());
                StdDao.SaveStdSureDetail(session, stdsuredetail);
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

    // endregion StdTestSure Methods

    // region StdReplRecord Methods

    public static void SaveStdReplRecord(StdReplRecord item, List<BusTaskAttach> details, ReturnValue rtv,
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
                // 业务流 隐藏
                // BusTodo todo = new BusTodo();
                // todo.setTranid(item.getTranid());
                // todo.setLabid(ou.getUser().getDeptid());
                // todo.setBusflow("StdReplRecord");
                // FlowService.FlowCreate(session, todo, rtv, log);
            }
            StdDao.SaveStdReplRecord(session, item);

            // 保存附件
            if ((item.getDeal().getAction() == DataAction.Create.getAction())
                    || (item.getDeal().getAction() == DataAction.Modify.getAction())) {
                String tranid = item.getTranid();
                BusTaskAttach dbta = new BusTaskAttach();
                dbta.setTranid(tranid);
                dbta.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusTaskAttach(session, dbta);
                for (BusTaskAttach bustaskattach : details) {
                    bustaskattach.setTranid(tranid);
                    bustaskattach.setAttachtype(bustaskattach.getAttachtype());
                    bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskAttach(session, bustaskattach);
                }

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

    // endregion StdReplRecord Methods

    // region StdChange Methods

    public static void SaveStdChange(StdChange item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                // //业务流
                // BusTodo todo = new BusTodo();
                // todo.setTranid(item.getTranid());
                // todo.setLabid(ou.getUser().getDeptid());
                // todo.setBusflow("StdChange");
                // FlowService.FlowCreate(session, todo, rtv, log);
            }

            StdDao.SaveStdChange(session, item);

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

    // endregion StdChange Methods

}
