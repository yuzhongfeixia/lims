package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.alms.dao.SubDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.sub.SubReview;
import com.alms.entity.sub.SubReviewDetail;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class SubService {

    // region SubReview Methods

    public static void SaveSubReview(SubReview item, ReturnValue rtv, List<SubReviewDetail> subReviewDetails,
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
                todo.setBusflow("SubReview");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            SubDao.SaveSubReview(session, item);

            // 添加随机文件
            SubReviewDetail reviewdatail = new SubReviewDetail();
            reviewdatail.setTranid(item.getTranid());
            reviewdatail.getDeal().setAction(DataAction.Delete.getAction());
            SubDao.SaveSubReviewDetail(session, reviewdatail);

            for (SubReviewDetail subreviewdatail : subReviewDetails) {
                subreviewdatail.setTranid(item.getTranid());
                subreviewdatail.getDeal().setAction(DataAction.Create.getAction());
                SubDao.SaveSubReviewDetail(session, subreviewdatail);
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

    // endregion SubReview Methods

    // region SubReviewDetail Methods

    public static void SaveSubReviewDetail(SubReviewDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            SubDao.SaveSubReviewDetail(session, item);

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

    // endregion SubReviewDetail Methods

}