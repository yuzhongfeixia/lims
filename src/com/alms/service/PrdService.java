package com.alms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.LabDao;
import com.alms.dao.PrdDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.prd.*;
import com.gpersist.dao.PublicDao;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class PrdService {

    // region PrdPoison Methods

    public static void SavePrdPoison(PrdPoison item, List<PrdPoisonDetail> details, List<PrdPoisonFile> filedetails,
            ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setConfirmuser(ou.getUser().getUserid());
            item.setConfirmusername(ou.getUser().getUsername());
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
                todo.setBusflow("PrdPoison");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            PrdDao.SavePrdPoison(session, item);
            // 保存申请明细
            PrdPoisonDetail prd = new PrdPoisonDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SavePrdPoisonDetail(session, prd);

            String logs = "";

            for (PrdPoisonDetail detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SavePrdPoisonDetail(session, detail);
                logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
            }

            // 保存附件明细
            PrdPoisonFile file = new PrdPoisonFile();
            file.setTranid(item.getTranid());
            file.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SavePrdPoisonFile(session, file);

            for (PrdPoisonFile detail : filedetails) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SavePrdPoisonFile(session, detail);
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

    // endregion PrdPoison Methods

    // region PrdCodeDetail Methods

    public static void SavePrdCodeDetail(PrdCodeDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            PrdDao.SavePrdCodeDetail(session, item);

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

    // endregion PrdCodeDetail Methods

    // region PrdCode Methods

    public static void SavePrdCode(PrdCode item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            PrdDao.SavePrdCode(session, item);

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

    // endregion PrdCode Methods

    // region StkCheck Methods

    public static void SaveStkCheck(StkCheck item, List<StkCheckDetail> details, ReturnValue rtv, OnlineUser ou,
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
            item.setRecouser(ou.getUser().getUserid());
            item.setRecousername(ou.getUser().getUsername());
            item.setDeptid(ou.getUser().getDeptid());
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
                item.setTranserial(0);
                item.setIsmodify(false);
            } else if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                // item.setFlowaction("01");
                // item.setFlowstatus("02");
                item.setTranserial(item.getTranserial() + 1);
                item.setIsmodify(true);
            }
            PrdDao.SaveStkCheck(session, item);

            // 保存申请明细
            StkCheckDetail prd = new StkCheckDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SaveStkCheckDetail(session, prd);

            String logs = "";

            for (StkCheckDetail detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SaveStkCheckDetail(session, detail);
                logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
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

    // 审核
    public static void SaveStkCheckAll(StkCheck item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            StkCheck prd = PrdDao.GetStkCheck(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckuser(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SaveStkCheck(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion StkCheck Methods

    // region StkOut Methods

    public static void SaveStkOut(StkOut item, List<StkOutDetail> details, ReturnValue rtv, OnlineUser ou,
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
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                PrdCode prdCode = new PrdCode();
                PrdCodeDetail pcd = new PrdCodeDetail();
                // 往试剂耗材条码表添加数据
                for (StkOutDetail detail : details) {

                    prdCode.setPrdid(detail.getPrdid());
                    prdCode.setStoreid(item.getStoreid());
                    prdCode = PrdDao.GetPrdCodeByPrd(prdCode);

                    double prdnumber = 0;
                    double factnumber = 0;

                    if (prdCode != null) {
                        prdnumber = prdCode.getPrdnumber();
                        prdnumber -= detail.getPrdnumber();
                        factnumber = prdCode.getFactnumber();
                        factnumber -= detail.getFactnumber();

                        if (prdnumber < 0) {
                            rtv.setMsg(detail.getPrdname() + "数量不够！");
                            return;
                        }

                        prdCode.setPrdnumber(prdnumber);
                        prdCode.setFactnumber(factnumber);
                        prdCode.setLastnumber(factnumber);
                        prdCode.getDeal().setAction(DataAction.Modify.getAction());
                        PrdDao.SavePrdCode(session, prdCode);
                    } else {
                        rtv.setMsg(item.getStorename() + "没有" + detail.getPrdname());
                        return;
                    }

                    pcd.setPrdcode(detail.getPrdcode());
                    pcd.setPrdid(detail.getPrdid());
                    pcd.setPrdstandard(detail.getPrdstandard());
                    pcd.setPrdnumber(detail.getPrdnumber());
                    pcd.setFactnumber(detail.getFactnumber());
                    pcd.setStktype("04");// 04出库
                    pcd.setTrandate(new Date());
                    pcd.setTranuser(item.getRecouser());
                    pcd.setLabid(ou.getUser().getDeptid());
                    pcd.getDeal().setAction(DataAction.Create.getAction());
                    PrdDao.SavePrdCodeDetail(session, pcd);
                }
            }
            PrdDao.SaveStkOut(session, item);
            // 保存申请明细
            StkOutDetail prd = new StkOutDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SaveStkOutDetail(session, prd);

            String logs = "";

            for (StkOutDetail detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SaveStkOutDetail(session, detail);
                logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
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

    // 审核
    public static void SaveStkOutCheck(StkOut item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            StkOut prd = PrdDao.GetStkOut(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckdesc(item.getCheckdesc());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckuser(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SaveStkOut(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion StkOut Methods

    // region StkIn Methods

    public static void SaveStkIn(StkIn item, List<StkInDetail> details, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            List<StkInDetail> stkindetails = new ArrayList<StkInDetail>();

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                PrdCodeDetail pcd = new PrdCodeDetail();
                // 往试剂耗材条码表添加数据
                for (StkInDetail detail : details) {

                    if (detail.getPrdid().equals("")) {
                        BasPrd basPrd = new BasPrd();
                        // 是否需要验收，00是验收，02是不验收
                        if (detail.getVerifyid().equals("")) {
                            basPrd.setIsverify("02");
                        } else {
                            basPrd.setIsverify("00");
                        }

                        basPrd.setPrdprice(detail.getPrdprice());
                        basPrd.setPrdname(detail.getPrdname());
                        basPrd.setPrdunit(detail.getPrdunit());
                        basPrd.setValidmonth(detail.getValiddate());
                        basPrd.setPrdstandard(detail.getPrdstandard());
                        basPrd.setRemark(detail.getRemark());
                        basPrd.setPrdtype(detail.getPrdtype());
                        basPrd.setStoreid(item.getStoreid());
                        basPrd.setPrduser(item.getInuser());
                        basPrd.setPrdusername(item.getInusername());
                        basPrd.setTradename(detail.getTradename());
                        basPrd.setFactoryname(detail.getFactoryname());
                        basPrd.setFactorydate(detail.getFactorydate());
                        basPrd.setBuydate(detail.getBuydate());
                        basPrd.setBuyuser(detail.getBuyuser());
                        basPrd.setBuyusername(detail.getBuyusername());
                        basPrd.getDeal().setAction(DataAction.Create.getAction());
                        PrdDao.SaveBasPrd(session, basPrd);
                        detail.setPrdid(basPrd.getPrdid());
                    }
                    if (detail.getPrdcodeprefix().equals("")) {
                        Calendar c1 = Calendar.getInstance();
                        String Prdcodeprefix = "Prefix" + c1.get(Calendar.YEAR) + c1.get(Calendar.MONTH) + 1
                                + c1.get(Calendar.DAY_OF_MONTH) + c1.get(Calendar.HOUR_OF_DAY) + c1.get(Calendar.MINUTE)
                                + c1.get(Calendar.SECOND);
                        detail.setPrdcodeprefix(Prdcodeprefix);
                    }

                    stkindetails.add(detail);

                    PrdCode prdCode = new PrdCode();
                    prdCode.setPrdid(detail.getPrdid());
                    prdCode.setStoreid(item.getStoreid());
                    prdCode = PrdDao.GetPrdCodeByPrd(prdCode);

                    double prdnumber = 0;
                    double factnumber = 0;

                    if (prdCode != null) {
                        prdnumber = prdCode.getPrdnumber();
                        prdnumber += detail.getPrdnumber();
                        factnumber = prdCode.getFactnumber();
                        factnumber += detail.getFactnumber();

                        prdCode.setPrdnumber(prdnumber);
                        prdCode.setFactnumber(factnumber);
                        prdCode.setLastnumber(factnumber);
                        prdCode.getDeal().setAction(DataAction.Modify.getAction());
                        PrdDao.SavePrdCode(session, prdCode);
                    } else {
                        PrdCode prd = new PrdCode();
                        prd.setPrdcode(detail.getPrdcodeprefix());
                        prd.setPrdid(detail.getPrdid());
                        prd.setPrdstandard(detail.getPrdstandard());
                        prd.setPrdunit(detail.getPrdunit());
                        prd.setPrdnumber(detail.getPrdnumber());
                        prd.setUnitnumber(detail.getUnitnumber());
                        prd.setFactnumber(detail.getFactnumber());
                        prd.setLastnumber(detail.getFactnumber());
                        prd.setValidmonth(detail.getValiddate());
                        prd.setStoreid(item.getStoreid());
                        prd.setBuydate(detail.getBuydate());
                        prd.getDeal().setAction(DataAction.Create.getAction());
                        PrdDao.SavePrdCode(session, prd);
                    }

                    pcd.setPrdcode(detail.getPrdcodeprefix());
                    pcd.setPrdid(detail.getPrdid());
                    pcd.setPrdstandard(detail.getPrdstandard());
                    pcd.setPrdnumber(detail.getPrdnumber());
                    pcd.setFactnumber(detail.getFactnumber());
                    pcd.setStktype("02");// 02 入库
                    pcd.setTrandate(new Date());
                    pcd.setTranuser(item.getRecouser());
                    pcd.setLabid(ou.getUser().getDeptid());
                    pcd.getDeal().setAction(DataAction.Create.getAction());
                    PrdDao.SavePrdCodeDetail(session, pcd);
                }
            }

            PrdDao.SaveStkIn(session, item);

            // 保存申请明细
            StkInDetail prd = new StkInDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SaveStkInDetail(session, prd);

            String logs = "";

            if (stkindetails.size() > 0) {
                for (StkInDetail detail : stkindetails) {
                    detail.setTranid(item.getTranid());
                    detail.getDeal().setAction(DataAction.Create.getAction());
                    PrdDao.SaveStkInDetail(session, detail);
                    logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
                }
            } else {
                for (StkInDetail detail : details) {
                    detail.setTranid(item.getTranid());
                    detail.getDeal().setAction(DataAction.Create.getAction());
                    PrdDao.SaveStkInDetail(session, detail);
                    logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
                }
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

    // 审核
    public static void SaveStkInCheck(StkIn item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            StkIn prd = PrdDao.GetStkIn(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckdesc(item.getCheckdesc());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckuser(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SaveStkIn(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion StkIn Methods

    // region StkStore Methods

    public static void SaveStkStore(StkStore item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            PrdDao.SaveStkStore(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("storeid");
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

    // endregion StkStore Methods

    // region PrdWaste Methods

    public static void SavePrdWaste(PrdWaste item, List<PrdWasteDetail> details, ReturnValue rtv, TranLog log) {
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
                todo.setBusflow("WasteApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            PrdDao.SavePrdWaste(session, item);

            // 保存明细
            PrdWasteDetail prd = new PrdWasteDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SavePrdWasteDetail(session, prd);

            String logs = "";

            logs = item.getTranid();

            for (PrdWasteDetail detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SavePrdWasteDetail(session, detail);
                logs += "[" + detail.getPrdid() + "-" + detail.getPrdname() + "]";
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

    // 办公室
    public static void SavePrdWasteOffice(PrdWaste item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdWaste prd = PrdDao.GetPrdWaste(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setAuditdesc(item.getAuditdesc());
                prd.setAuditdate(new Date());
                prd.setAudituser(ou.getUser().getUserid());
                prd.setAuditusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdWaste(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 分管主任
    public static void SavePrdWasteCharge(PrdWaste item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdWaste prd = PrdDao.GetPrdWaste(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckdesc(item.getCheckdesc());
                prd.setCheckdate(new Date());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdWaste(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 主任
    public static void SavePrdWasteDirector(PrdWaste item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdWaste prd = PrdDao.GetPrdWaste(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setAgreedate(new Date());
                prd.setAgreeuser(ou.getUser().getUserid());
                prd.setAgreeusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdWaste(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion PrdWaste Methods

    // region PrdVerify Methods

    public static void SavePrdVerify(PrdVerify item, List<BusTaskAttach> details, ReturnValue rtv, TranLog log) {
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

                SysUser user = UserDao.GetUser(item.getComfirmuser());
                // 业务流
                BusTodo todo = new BusTodo();
                if (user != null) {
                    todo.setLabid(user.getDeptid());
                }
                todo.setTranid(item.getVerifyid());
                todo.setBusflow("PrdAccept");
                FlowService.FlowCreate(session, todo, rtv, log);

            }

            PrdDao.SavePrdVerify(session, item);
            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                String tranid = item.getVerifyid();
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
            log.setTrandesc(item.getVerifyid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"verifyid\":\"" + item.getVerifyid() + "\"," + "\"flowaction\":\"" + item.getFlowaction()
                    + "\"," + "\"flowstatus\":\"" + item.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 办公室
    public static void SavePrdVerifyOffice(PrdVerify item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdVerify prd = PrdDao.GetPrdVerify(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setAuditdesc(item.getAuditdesc());
                prd.setAuditdate(new Date());
                prd.setAudituser(ou.getUser().getUserid());
                prd.setAuditusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdVerify(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 检测室验收
    public static void SavePrdVerifyCharge(PrdVerify item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdVerify prd = PrdDao.GetPrdVerify(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setComfirmdate(new Date());
                prd.setComfirmdesc(item.getComfirmdesc());
                prd.setComfirmuser(ou.getUser().getUserid());
                prd.setComfirmusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdVerify(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 主任
    public static void SavePrdVerifyDirector(PrdVerify item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdVerify prd = PrdDao.GetPrdVerify(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckdesc(item.getCheckdesc());
                prd.setCheckdate(new Date());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdVerify(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // endregion PrdVerify Methods

    // region BasPrd Methods

    public static void SaveBasPrd(BasPrd item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            PrdDao.SaveBasPrd(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getPrdid() + "" + item.getPrdname());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"prdid\":\"" + item.getPrdid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasPrd Methods

    // region PrdApply Methods

    public static void SavePrdApply(PrdApply item, List<PrdApplyDetail> details, ReturnValue rtv, TranLog log) {
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
                todo.setBusflow("PrdApply");
                FlowService.FlowCreate(session, todo, rtv, log);
            }
            PrdDao.SavePrdApply(session, item);

            // 保存申请明细
            PrdApplyDetail prd = new PrdApplyDetail();
            prd.setTranid(item.getTranid());
            prd.getDeal().setAction(DataAction.Delete.getAction());
            PrdDao.SavePrdApplyDetail(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getProjectid();

            for (PrdApplyDetail detail : details) {

                if (detail.getIsverify().equals("-2") || detail.getLevel().equals("-2")) {
                    rtv.setMsg(Consts.STR_SAVE_F + "-- 耗材明细级别或者是否验收没有填写");

                    return;
                }
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                PrdDao.SavePrdApplyDetail(session, detail);
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

    // 办公室
    public static void SavePrdApplyOffice(PrdApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdApply prd = PrdDao.GetPrdApply(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setPrdsource(item.getPrdsource());
                prd.setAuditdesc(item.getAuditdesc());
                prd.setAuditdate(new Date());
                prd.setAudituser(ou.getUser().getUserid());
                prd.setAuditusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdApply(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 分管主任
    public static void SavePrdApplyCharge(PrdApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdApply prd = PrdDao.GetPrdApply(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setCheckdesc(item.getCheckdesc());
                prd.setCheckdate(new Date());
                prd.setCheckuser(ou.getUser().getUserid());
                prd.setCheckusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdApply(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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

    // 主任
    public static void SavePrdApplyDirector(PrdApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            PrdApply prd = PrdDao.GetPrdApply(item);
            if (prd == null) {
                rtv.setMsg("该申请不存在!");
                return;
            } else {
                prd.setFlowaction(item.getFlowaction());
                prd.setFlowstatus(item.getFlowstatus());
                prd.setAgreedate(new Date());
                prd.setAgreeuser(ou.getUser().getUserid());
                prd.setAgreeusername(ou.getUser().getUsername());
                prd.getDeal().setAction(DataAction.Modify.getAction());
            }

            PrdDao.SavePrdApply(session, prd);

            String logs = "";

            logs = item.getTranid() + "-" + item.getFlowstatus();

            log.ActionToTran(prd.getDeal().getAction());
            log.setTrandesc(logs);
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
    // endregion PrdApply Methods

}
