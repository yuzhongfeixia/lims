package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.SampDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.samp.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class SampService {

    // region SampleBackup Methods

    public static void SaveSampleBackup(SampleBackup item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
                todo.setBusflow("SampBackup");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            SampDao.SaveSampleBackup(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid() + '-' + item.getSamplename());
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

    // endregion SampleBackup Methods

    // region SampleEnv Methods

    public static void SaveSampleEnv(SampleEnv item, List<SampleEnvDetail> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            SampDao.SaveSampleEnv(session, item);

            // 采样明细
            SampleEnvDetail envDetail = new SampleEnvDetail();
            envDetail.setTranid(item.getTranid());
            envDetail.getDeal().setAction(DataAction.Delete.getAction());
            SampDao.SaveSampleEnvDetail(session, envDetail);

            for (SampleEnvDetail env : details) {
                env.setTranid(item.getTranid());
                env.getDeal().setAction(DataAction.Create.getAction());
                SampDao.SaveSampleEnvDetail(session, env);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid() + '-' + item.getEntername());
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

    // endregion SampleEnv Methods

    // region SampleDeal Methods

    public static void SaveSampleDeal(SampleDeal item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setDealuser(ou.getUser().getUserid());
            item.setDealusername(ou.getUser().getUsername());
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
                todo.setBusflow("SampleDeal");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            SampDao.SaveSampleDeal(session, item);

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

    // endregion SampleDeal Methods

    // region SampleIce Methods

    public static void SaveSampleIce(SampleIce item, List<SampleIceDetail> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            SampDao.SaveSampleIce(session, item);

            // 增加温度明细
            SampleIceDetail iceDetail = new SampleIceDetail();
            iceDetail.setIceid(item.getIceid());
            iceDetail.getDeal().setAction(DataAction.Delete.getAction());
            SampDao.SaveSampleIceDetail(session, iceDetail);

            for (SampleIceDetail ice : details) {
                ice.setIceid(item.getIceid());
                ice.getDeal().setAction(DataAction.Create.getAction());
                SampDao.SaveSampleIceDetail(session, ice);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getIceid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            // rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
            rtv.setMsg(Consts.STR_SAVE_F + "冰柜编号已存在");
        } finally {
            session.close();
        }
    }

    // endregion SampleIce Methods

}
