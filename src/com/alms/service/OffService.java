package com.alms.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.OffDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.office.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class OffService {

    // region OfficeApply Methods

    public static void SaveOfficeApply(OfficeApply item, List<OfficeApplyDetail> details, ReturnValue rtv,
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
                todo.setBusflow("OfficeApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            OffDao.SaveOfficeApply(session, item);

            // 添加办公用品明细
            OfficeApplyDetail applyDetail = new OfficeApplyDetail();
            applyDetail.setTranid(item.getTranid());
            applyDetail.getDeal().setAction(DataAction.Delete.getAction());
            OffDao.SaveOfficeApplyDetail(session, applyDetail);

            for (OfficeApplyDetail apply : details) {
                apply.setTranid(item.getTranid());
                apply.getDeal().setAction(DataAction.Create.getAction());
                OffDao.SaveOfficeApplyDetail(session, apply);
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

    // endregion OfficeApply Methods

}
