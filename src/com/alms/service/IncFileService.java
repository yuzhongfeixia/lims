package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.FlowDao;
import com.alms.dao.IncFileDao;
import com.alms.entity.file.*;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.flow.BusTodoNow;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.TranAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class IncFileService {

    // region IncFileDestroyRecord Methods

    public static void SaveIncFileDestroyRecord(IncFileDestroyRecord item, OnlineUser ou, ReturnValue rtv,
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
            item.setTrandate(new Date());
            IncFileDao.SaveIncFileDestroyRecord(session, item);

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

    // endregion IncFileDestroyRecord Methods

    // region IncFilePassword Methods

    public static void SaveIncFilePassword(IncFilePassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            if (ToolUtils.StringIsEmpty(item.getFilepassword())) {
                rtv.setMsg("密码不能为空！");
                return;
            }

            if (!item.getFilepassword().equals(item.getRepassword())) {
                rtv.setMsg("密码两次输入不同，请确认后再进行保存！");
                return;
            }

            item.setUserid(ou.getUser().getUserid());

            IncFilePassword pwd = IncFileDao.GetIncFilePassword(item);
            if (pwd != null) {
                rtv.setMsg("密码已存在，不能再设置！<br/> 亲可重置密码或修改密码！");
                return;
            }

            item.setFilepassword(ToolUtils.GetMD5(item.getFilepassword()));
            item.getDeal().setAction(DataAction.Create.getAction());
            IncFileDao.SaveIncFilePassword(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("设置密码！");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("密码设置成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "密码设置失败！"));
        } finally {
            session.close();
        }
    }

    public static void ChangeIncFilePassword(IncFilePassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            if (ToolUtils.StringIsEmpty(item.getOldpassword())) {
                rtv.setMsg("原密码不能为空！");
                return;
            }

            if (ToolUtils.StringIsEmpty(item.getFilepassword())) {
                rtv.setMsg("新密码不能为空！");
                return;
            }

            if (item.getOldpassword().equals(item.getFilepassword())) {
                rtv.setMsg("新密码不能和旧密码不能一样！");
                return;
            }

            /*
             * if ((user.getUserpassword().length() < 8) ||
             * !user.getUserpassword().matches(".*?[a-z]+.*?") ||
             * !user.getUserpassword().matches(".*?[0-9]+.*?") ||
             * !user.getUserpassword().matches(".*?[A-Z]+.*?")) {
             * rtv.setMsg("新密码最少8位、要求有数字、字母、大小写！"); return; }
             */

            if (!item.getFilepassword().equals(item.getRepassword())) {
                rtv.setMsg("新密码两次输入不同，请确认后再进行保存！");
                return;
            }

            item.setUserid(ou.getUser().getUserid());
            IncFilePassword pwd = IncFileDao.GetIncFilePassword(item);

            if (ToolUtils.StringIsEmpty(pwd.getFilepassword())
                    || !pwd.getFilepassword().equals(ToolUtils.GetMD5(item.getOldpassword()))) {
                rtv.setMsg("原密码不正确！");
                return;
            }

            item.setFilepassword(ToolUtils.GetMD5(item.getFilepassword()));

            item.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFilePassword(session, item);

            log.setTranaction(TranAction.Deal.getTranaction());
            log.setTrandesc(item.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("密码修改成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "密码修改失败:"));
        } finally {
            session.close();
        }
    }

    public static void ResetIncFilePassword(IncFilePassword item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            item.setFilepassword(ToolUtils.GetMD5(item.getUserid()));

            item.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFilePassword(session, item);

            log.setTranaction(TranAction.Deal.getTranaction());
            log.setTrandesc(item.getUserid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("密码重置成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "密码重置失败:"));
        } finally {
            session.close();
        }
    }

    // endregion IncFilePassword Methods

    // region IncFileOnline Methods

    public static void SaveIncFileOnline(IncFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setFlowaction("01");
            item.setFlowstatus("02");
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());

            IncFileDao.SaveIncFileOnline(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("IncFileOnline");
            BusTodoNow todonow = new BusTodoNow();
            todonow.setTranid(item.getTranid());
            List<BusTodoNow> todonows = FlowDao.GetBusTodoNowByTran(todonow);
            if (todonows.size() == 0) {
                FlowService.FlowCreateFile(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
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

    // 审核通过
    public static void CheckIncFileOnline(IncFileOnline item, String password, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setFlowaction("20"); // 审核
            item.setFlowstatus("30"); // 审核通过

            IncFilePassword pwd = new IncFilePassword();
            pwd.setUserid(ou.getUser().getUserid());
            pwd = IncFileDao.GetIncFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            item.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFileOnline(session, item);

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

    public static void UnCheckIncFileOnline(IncFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setFlowaction("20"); // 审核
            item.setFlowstatus("31"); // 审核未通过

            item.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFileOnline(session, item);

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

    // 申请通过
    public static void ApproveIncFileOnline(IncFileOnline item, String password, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setFlowaction("21"); // 审批
            item.setFlowstatus("88"); // 申请通过

            IncFilePassword pwd = new IncFilePassword();
            pwd.setUserid(ou.getUser().getUserid());
            pwd = IncFileDao.GetIncFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            item.getDeal().setAction(DataAction.Deal.getAction());
            IncFileDao.SaveIncFileOnline(session, item);

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

    public static void DisapproveIncFileOnline(IncFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setFlowaction("21"); // 审批
            item.setFlowstatus("33"); // 审批未通过

            item.getDeal().setAction(DataAction.Deal.getAction());
            IncFileDao.SaveIncFileOnline(session, item);

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

    // endregion IncFileOnline Methods

    // region IncFileRegister Methods

    public static void SaveIncFileRegister(IncFileRegister item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setIsborrow(true);

            IncFileDao.SaveIncFileRegister(session, item);

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

    // endregion IncFileRegister Methods

    // region BasFile Methods

    public static void SaveBasFile(BasFile item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            item.setFiledate(new Date());

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BasFile file = new BasFile();
                file = IncFileDao.GetBasFile(item);
                item.setModifytimes(file.getModifytimes() + 1);
            }

            IncFileDao.SaveBasFile(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getFileid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"fileid\":\"" + item.getFileid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void DeleteBasFile(List<BasFile> details, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (BasFile file : details) {
                file.getDeal().setAction(DataAction.Deal.getAction());
                IncFileDao.SaveBasFile(session, file);
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("销毁成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "销毁失败"));
        } finally {
            session.close();
        }
    }

    public static void SaveAllBasFile(BasFile item, String[] filename, String[] fileurl, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            for (int i = 0; i < filename.length; i++) {

                //
                // File file = new File("E:\\alms\\" + fileurl[i]);
                // long time = file.lastModified();//返回文件最后修改时间，是以个long型毫秒数
                // String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new
                // Date(time));

                item.setFilename(filename[i]);
                item.setFileurl(fileurl[i]);
                item.setCrtuser(ou.getUser().getUserid());
                item.setCrtusername(ou.getUser().getUsername());
                item.setUploaddate(new Date());
                item.setFiledate(new Date());
                IncFileDao.SaveBasFile(session, item);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"fileid\":\"" + item.getFileid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BasFile Methods

    // region ChangeApply Methods

    public static void SaveChangeApply(ChangeApply item, ReturnValue rtv, String changefileurl, String replacefilename,
            OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setChangedept(ou.getDept().getDeptid());
            item.setChangedeptname(ou.getDept().getDeptname());
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            item.setFlowaction("01");
            item.setFlowstatus("01");

            //// if(item.getDeal().getAction()==DataAction.Modify.getAction()){
            //// BusProc busproc = new BusProc();
            //// busproc.setProcid(item.getProcid());
            //// busproc = ProDao.GetBusProc(busproc);
            // BasFile bf = new BasFile();
            // bf.setFileid(item.getChangefileid());
            // bf = IncFileDao.GetBasFile(bf);
            // if(!ToolUtils.StringIsEmpty(changefileurl)){
            // bf.setFilename(replacefilename);
            // bf.setFileurl(changefileurl);
            // bf.setUploaddate(new Date());
            // }
            // bf.getDeal().setAction(DataAction.Modify.getAction());
            // IncFileDao.SaveBasFile(session, bf);
            //// }
            IncFileDao.SaveChangeApply(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("IncFileChange");
            BusTodoNow todonow = new BusTodoNow();
            todonow.setTranid(item.getTranid());
            List<BusTodoNow> bustodonow = FlowDao.GetBusTodoNowByTran(todonow);
            if (bustodonow.size() == 0) {
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

    // endregion ChangeApply Methods

    // region DestoryApplyDetail Methods

    public static void SaveDestoryApplyDetail(DestoryApplyDetail item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            IncFileDao.SaveDestoryApplyDetail(session, item);

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

    // endregion DestoryApplyDetail Methods

    // region Destoryapply Methods

    public static void SaveDestoryApply(DestoryApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("01");
            } else if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                item.setFlowaction("01");
                item.setFlowstatus("02");

                BusTodo todo = new BusTodo();
                todo.setTranid(item.getTranid());
                // todo.setLabid(user.getDeptid());
                todo.setBusflow("IncFileDestory");
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            IncFileDao.SaveDestoryApply(session, item);

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

    // endregion destoryapply Methods

    // region LoanApply Methods

    public static void SaveLoanApply(LoanApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            item.setFlowaction("01");
            item.setFlowstatus("01");

            IncFileDao.SaveLoanApply(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("IncFileLoan");
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

    // endregion LoanApply Methods

    // region SecretApply Methods

    public static void SaveSecretApply(SecretApply item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setReaduser(ou.getUser().getUserid());
            item.setReadusername(ou.getUser().getUsername());
            item.setReaddate(new Date());

            item.setFlowaction("01");
            item.setFlowstatus("01");

            IncFileDao.SaveSecretApply(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("IncFileSecret");
            BusTodoNow todonow = new BusTodoNow();
            todonow.setTranid(item.getTranid());
            List<BusTodoNow> todonows = FlowDao.GetBusTodoNowByTran(todonow);
            if (todonows.size() == 0) {
                FlowService.FlowCreate(session, todo, rtv, log);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
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

    public static void CommitSecretApply(List<SecretApply> commits, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (SecretApply commit : commits) {
                commit.getDeal().setAction(DataAction.Submit.getAction());
                commit.setFlowstatus("02");
                IncFileDao.SaveSecretApply(session, commit);
            }

            log.ActionToTran(DataAction.Submit.getAction());
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

    // endregion SecretApply Methods

    // region BasLeak Methods

    public static void SaveBasLeak(BasLeak item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            item.setFlowaction("01");
            item.setFlowstatus("01");

            IncFileDao.SaveBasLeak(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("IncFileLeak");
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

    // endregion BasLeak Methods

    // region ChangeNotify Methods

    public static void SaveChangeNotify(ChangeNotify item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            IncFileDao.SaveChangeNotify(session, item);

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

    // endregion ChangeNotify Methods

    // region ReleFile Methods

    public static void SaveReleFile(ReleFile item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            IncFileDao.SaveReleFile(session, item);

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

    // endregion ReleFile Methods

}