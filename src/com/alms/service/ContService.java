package com.alms.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.ContDao;
import com.alms.dao.FlowDao;
import com.alms.entity.cont.*;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.flow.BusTodoNow;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class ContService {

    // region ContractReview Methods

    public static void SaveContractReview(ContractReview item, List<ContractReviewDetail> details,
            List<ContractReviewSample> sample, List<ContractReviewParam> params, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
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
            ContDao.SaveContractReview(session, item);

            // 添加评论明细
            ContractReviewDetail reviewDetail = new ContractReviewDetail();
            reviewDetail.setTranid(item.getTranid());
            reviewDetail.getDeal().setAction(DataAction.Delete.getAction());
            ContDao.SaveContractReviewDetail(session, reviewDetail);

            for (ContractReviewDetail review : details) {
                review.setTranid(item.getTranid());
                review.getDeal().setAction(DataAction.Create.getAction());
                ContDao.SaveContractReviewDetail(session, review);
            }

            // 删除样品明细
            ContractReviewSample reviewSample = new ContractReviewSample();
            reviewSample.setTranid(item.getTranid());
            reviewSample.getDeal().setAction(DataAction.Delete.getAction());
            ContDao.SaveContractReviewSample(session, reviewSample);
            ContractReviewParam reviewParam = new ContractReviewParam();
            reviewParam.setTranid(item.getTranid());
            reviewParam.getDeal().setAction(DataAction.Delete.getAction());
            ContDao.SaveContractReviewParam(session, reviewParam);

            // 保存样品明细
            for (ContractReviewSample samp : sample) {
                samp.setTranid(item.getTranid());
                samp.getDeal().setAction(DataAction.Create.getAction());
                ContDao.SaveContractReviewSample(session, samp);
                // 保存检测项目
                for (ContractReviewParam param : params) {
                    if (samp.getSampleid().equals(param.getSampleid())) {
                        param.setTranid(item.getTranid());
                        param.getDeal().setAction(DataAction.Create.getAction());
                        ContDao.SaveContractReviewParam(session, param);
                    }
                }
            }

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("ContractReview");

            BusTodoNow todonow = new BusTodoNow();
            todonow.setTranid(item.getTranid());
            List<BusTodoNow> todonows = FlowDao.GetBusTodoNowByTran(todonow);
            if (todonows.size() == 0) {
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

    // endregion ContractReview Methods

}
