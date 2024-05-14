package com.gpersist.service;

import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.*;
import com.gpersist.entity.*;
import com.gpersist.entity.log.*;
import com.gpersist.webservice.entity.*;

import com.gpersist.utils.*;

public class WsPubService {

    // region WsApp Methods

    public static void SaveWsApp(WsApp item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setCrtuser(log.getTranuser());
            WsPubDao.SaveWsApp(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getAppid() + "-" + item.getAppname());
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

    public static WsApp GetWsApp(String appid) {

        SqlSession session = DBUtils.getFactory();

        try {
            WsApp item = new WsApp();
            item.setAppid(appid);

            return WsPubDao.GetWsApp(session, item);
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }

        return new WsApp();
    }

    // endregion WsApp Methods

    // region WsVisit Methods

    public static void SaveWsVisit(WsVisit item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            WsPubDao.SaveWsVisit(session, item);

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

    public static void SaveWsVisit(WsVisit item) {

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                throw new Exception(errmsg.getErrmsg());
            }

            WsPubDao.SaveWsVisit(session, item);
            session.commit();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
    }

    // endregion WsVisit Methods
}
