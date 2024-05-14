package com.alms.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.*;
import com.alms.entity.glp.*;
import com.alms.entity.flow.*;
import com.gpersist.dao.*;
import com.gpersist.entity.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.TranAction;
import com.gpersist.utils.*;

public class GlpService {

    // region GlpFileDestroyRecord Methods

    public static void SaveGlpFileDestroyRecord(GlpFileDestroyRecord item, OnlineUser ou, ReturnValue rtv,
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
            GlpDao.SaveGlpFileDestroyRecord(session, item);

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

    // endregion GlpFileDestroyRecord Methods

    // region GlpFileRegister Methods

    public static void SaveGlpFileRegister(GlpFileRegister item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileRegister(session, item);

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

    // endregion GlpFileRegister Methods

    // region GlpFilePassword Methods

    public static void SaveGlpFilePassword(GlpFilePassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpFilePassword pwd = GlpDao.GetGlpFilePassword(item);
            if (pwd != null) {
                rtv.setMsg("密码已存在，不能再设置！<br/> 亲可重置密码或修改密码！");
                return;
            }

            item.setFilepassword(ToolUtils.GetMD5(item.getFilepassword()));
            item.getDeal().setAction(DataAction.Create.getAction());
            GlpDao.SaveGlpFilePassword(session, item);

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

    public static void ChangeGlpFilePassword(GlpFilePassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            GlpFilePassword pwd = GlpDao.GetGlpFilePassword(item);

            if (ToolUtils.StringIsEmpty(pwd.getFilepassword())
                    || !pwd.getFilepassword().equals(ToolUtils.GetMD5(item.getOldpassword()))) {
                rtv.setMsg("原密码不正确！");
                return;
            }

            item.setFilepassword(ToolUtils.GetMD5(item.getFilepassword()));

            item.getDeal().setAction(DataAction.Modify.getAction());
            GlpDao.SaveGlpFilePassword(session, item);

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

    public static void ResetGlpFilePassword(GlpFilePassword item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            item.setFilepassword(ToolUtils.GetMD5(item.getUserid()));

            item.getDeal().setAction(DataAction.Modify.getAction());
            GlpDao.SaveGlpFilePassword(session, item);

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

    // endregion GlpFilePassword Methods

    // region GlpFileOnline Methods

    public static void SaveGlpFileOnline(GlpFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileOnline(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("GlpFileOnline");
            BusTodoNow todonow = new BusTodoNow();
            todonow.setTranid(item.getTranid());
            List<BusTodoNow> todonows = FlowDao.GetBusTodoNowByTran(todonow);
            if (todonows.size() == 0) {
                FlowService.FlowCreateGlp(session, todo, rtv, log);
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
    public static void CheckGlpFileOnline(GlpFileOnline item, String password, ReturnValue rtv, OnlineUser ou,
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

            GlpFilePassword pwd = new GlpFilePassword();
            pwd.setUserid(ou.getUser().getUserid());
            pwd = GlpDao.GetGlpFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            item.getDeal().setAction(DataAction.Modify.getAction());
            GlpDao.SaveGlpFileOnline(session, item);

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

    public static void UnCheckGlpFileOnline(GlpFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            GlpDao.SaveGlpFileOnline(session, item);

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

    // 审批通过
    public static void ApproveGlpFileOnline(GlpFileOnline item, String password, ReturnValue rtv, OnlineUser ou,
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

            GlpFilePassword pwd = new GlpFilePassword();
            pwd.setUserid(ou.getUser().getUserid());
            pwd = GlpDao.GetGlpFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            item.getDeal().setAction(DataAction.Deal.getAction());
            GlpDao.SaveGlpFileOnline(session, item);

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

    public static void DisapproveGlpFileOnline(GlpFileOnline item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            GlpDao.SaveGlpFileOnline(session, item);

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

    // endregion GlpFileOnline Methods

    // region GlpFile Methods

    public static void SaveGlpFile(GlpFile item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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
            // item.setUploaddate(new Date());
            item.setFiledate(new Date());

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                GlpFile file = new GlpFile();
                file = GlpDao.GetGlpFile(item);
                item.setModifytimes(file.getModifytimes() + 1);
            }

            GlpDao.SaveGlpFile(session, item);

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

    public static void SaveAllGlpFile(GlpFile item, String[] filename, String[] fileurl, ReturnValue rtv, OnlineUser ou,
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

                item.setFilename(filename[i]);
                item.setFileurl(fileurl[i]);
                item.setCrtuser(ou.getUser().getUserid());
                item.setCrtusername(ou.getUser().getUsername());
                item.setUploaddate(new Date());
                item.setFiledate(new Date());
                GlpDao.SaveGlpFile(session, item);
            }

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

    public static void DeleteGlpFile(List<GlpFile> details, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (GlpFile file : details) {
                file.getDeal().setAction(DataAction.Deal.getAction());
                GlpDao.SaveGlpFile(session, file);
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("删除");
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

    // endregion GlpFile Methods

    // region GlpFileChange Methods

    public static void SaveGlpFileChange(GlpFileChange item, ReturnValue rtv, String changefileurl,
            String replacefilename, OnlineUser ou, TranLog log) {
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
            // GlpFile bf = new GlpFile();
            // bf.setFileid(item.getChangefileid());
            // bf = GlpDao.GetGlpFile(bf);
            // if(!ToolUtils.StringIsEmpty(changefileurl)){
            // bf.setFilename(replacefilename);
            // bf.setFileurl(changefileurl);
            // bf.setUploaddate(new Date());
            // }
            // bf.getDeal().setAction(DataAction.Modify.getAction());
            // GlpDao.SaveGlpFile(session, bf);
            //// }
            GlpDao.SaveGlpFileChange(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("GlpFileChange");
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

    // endregion GlpFileChange Methods

    // region destoryapply Methods

    public static void SaveGlpFileDestroy(GlpFileDestroy item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileDestroy(session, item);

            // // 保存明细
            // GlpFileDestroyDetail dad = new GlpFileDestroyDetail();
            // dad.setTranid(item.getTranid());
            // dad.getDeal().setAction(DataAction.Delete.getAction());
            // GlpDao.SaveGlpFileDestroyDetail(session, dad);
            //
            // for(GlpFileDestroyDetail detail:details){
            // detail.setTranid(item.getTranid());
            // detail.setFileid(item.getFileid());
            // detail.getDeal().setAction(DataAction.Create.getAction());
            // GlpDao.SaveGlpFileDestroyDetail(session, detail);
            // }

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("GlpFileDestory");
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

    // endregion destoryapply Methods

    // region GlpFileLoan Methods

    public static void SaveGlpFileLoan(GlpFileLoan item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileLoan(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("GlpFileLoan");
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

    // endregion GlpFileLoan Methods

    // region GlpFileLeak Methods

    public static void SaveGlpFileLeak(GlpFileLeak item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileLeak(session, item);

            // 业务流
            BusTodo todo = new BusTodo();
            todo.setTranid(item.getTranid());
            todo.setBusflow("GlpFileLeak");
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

    // endregion GlpFileLeak Methods

    // region GlpFileNotify Methods

    public static void SaveGlpFileNotify(GlpFileNotify item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileNotify(session, item);

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

    // endregion GlpFileNotify Methods

    // region GlpFileRele Methods

    public static void SaveGlpFileRele(GlpFileRele item, ReturnValue rtv, OnlineUser ou, TranLog log) {
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

            GlpDao.SaveGlpFileRele(session, item);

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

    // endregion GlpFileRele Methods

}