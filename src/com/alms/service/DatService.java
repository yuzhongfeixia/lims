package com.alms.service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.dao.DatDao;
import com.alms.dao.FlowDao;
import com.alms.dao.FormDao;
import com.alms.dao.LabDao;
import com.alms.entity.bas.BasSample;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.dat.DatClassSource;
import com.alms.entity.dat.DatSampleField;
import com.alms.entity.dat.DatSampleParameter;
import com.alms.entity.dat.DatSampleTest;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dat.SetBusRecordDetail;
import com.alms.entity.dat.SetBusReport;
import com.alms.entity.dat.SetBusReportDetail;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.form.FrmRecord;
import com.alms.entity.form.FrmReport;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusReportFile;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskResult;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.SignerPassword;
import com.gpersist.dao.PublicDao;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysUser;
import com.gpersist.enums.AlignType;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.ValueSource;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.FileUtils;
import com.gpersist.utils.OfficeUtils;
import com.gpersist.utils.ToolUtils;

public class DatService {

    // region DatSampleTest Methods

    public static void SaveDatSampleTest(DatSampleTest item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DatDao.SaveDatSampleTest(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getParameterid() + item.getParametername());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void DeleteDatSampleTest(List<DatSampleTest> items, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            for (DatSampleTest item : items) {
                item.getDeal().setAction(DataAction.Delete.getAction());

                if (item.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            String ids = "";

            for (DatSampleTest item : items) {
                if (!ToolUtils.StringIsEmpty(ids))
                    ids += ",";

                ids += item.getParameterid() + "-" + item.getClasssource() + "-" + item.getClassfield() + "-"
                        + item.getTeststandard();

                DatDao.SaveDatSampleTest(session, item);
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DatSampleTest Methods

    // region BusRecord Methods

    public static SetBusRecord GetSetBusRecord(BusRecord item) {
        SetBusRecord rtn = new SetBusRecord();

        SqlSession session = DBUtils.getFactory();

        try {

            rtn.setRecord(DatDao.GetBusRecord(session, item));

            if (!ToolUtils.StringIsEmpty(rtn.getRecord().getRecordid())) {
                FrmRecord frm = new FrmRecord();
                frm.setFormid(rtn.getRecord().getFormid());
                FrmRecord fr = FormDao.GetFrmRecord(session, frm);
                int formlength = FormDao.GetBusRecordDetailMaxLength(item);
                fr.setFormlength(formlength);
                rtn.setForm(fr);

                BusRecordDetail detail = new BusRecordDetail();
                detail.setRecordid(rtn.getRecord().getRecordid());

                for (int i = 0; i < rtn.getRecord().getFormcount(); i++) {
                    detail.setFormserial(i + 1);

                    SetBusRecordDetail sd = new SetBusRecordDetail();
                    sd.setFormserial(i + 1);
                    sd.setDatas(DatDao.GetBusRecordDetailByFormSerial(session, detail));

                    for (BusRecordDetail ditem : sd.getDatas()) {
                        if (ditem.getFieldcode().equals("{recordid}")) {
                            ditem.setCelltext(ditem.getRecordid());

                            break;
                        }
                    }
                    rtn.getDetails().add(sd);
                }
            }

            return rtn;
        } catch (Exception e) {
            e.printStackTrace();
            return new SetBusRecord();
        } finally {
            session.close();
        }
    }

    public static SetBusRecord GetSetBusRecordForSum(BusRecord item) {
        SetBusRecord rtn = new SetBusRecord();

        SqlSession session = DBUtils.getFactory();

        try {

            List<BusRecord> bList = DatDao.GetBusRecordByParaTime(item);

            if (bList.size() > 0) {
                rtn.setRecord(DatDao.GetBusRecord(session, bList.get(0)));

                if (!ToolUtils.StringIsEmpty(rtn.getRecord().getRecordid())) {
                    FrmRecord frm = new FrmRecord();
                    frm.setFormid(rtn.getRecord().getFormid());
                    FrmRecord fr = FormDao.GetFrmRecord(session, frm);

                    item.setRecordid(bList.get(0).getRecordid());
                    int formlength = FormDao.GetBusRecordDetailMaxLength(item);
                    fr.setFormlength(formlength);
                    rtn.setForm(fr);

                    BusRecordDetail detail = new BusRecordDetail();
                    detail.setRecordid(rtn.getRecord().getRecordid());
                    detail.setParameterid(item.getParameterid());
                    detail.setStartdate(item.getStartdate());
                    detail.setEnddate(item.getEnddate());
                    detail.setTranuser(item.getTranuser());

                    for (int i = 0; i < 100; i++) {
                        detail.setFormserial(i + 1);

                        SetBusRecordDetail sd = new SetBusRecordDetail();
                        sd.setFormserial(i + 1);

                        List<BusRecordDetail> bDetails = DatDao.GetBusRecordDetailForSumByFormSerial(session, detail);
                        if (bDetails.size() == 0) {
                            break;
                        }

                        sd.setDatas(bDetails);

                        for (BusRecordDetail ditem : sd.getDatas()) {
                            if (ditem.getFieldcode().equals("{recordid}")) {
                                ditem.setCelltext(ditem.getRecordid());
                                break;
                            }
                        }
                        rtn.getDetails().add(sd);
                    }
                }
            }

            return rtn;
        } catch (Exception e) {
            e.printStackTrace();
            return new SetBusRecord();
        } finally {
            session.close();
        }
    }

    public static void SubmitBusRecord(BusRecord item, ReturnValue rtv, List<BusTaskAttach> details,
            List<BusRecordFile> filedetails, String signerpassword, String approveusers, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusRecord bRecord = new BusRecord();
            bRecord.setTranuser(ou.getUser().getUserid());
            bRecord.setTranusername(ou.getUser().getUsername());
            bRecord.setTrandate(new Date());
            bRecord.setRecordstatus(item.getRecordstatus());
            bRecord.setTaskid(item.getTaskid());
            bRecord.setRecordid(item.getRecordid());
            bRecord.setSamplecode(item.getSamplecode());
            bRecord.setSampletran(item.getSampletran());
            bRecord.setApproveuser(approveusers);
            DatDao.SubmitBusRecord(session, bRecord);

            String tranid = item.getSampletran();
            String taskid = item.getTaskid();

            BusTaskAttach dbta = new BusTaskAttach();
            dbta.setTranid(tranid);
            dbta.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskAttach(session, dbta);

            for (BusTaskAttach bustaskattach : details) {
                bustaskattach.setTranid(tranid);
                bustaskattach.setTaskid(taskid);
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
                bRecordFile.setTaskid(taskid);
                bRecordFile.setAttachtype(bRecordFile.getAttachtype());
                bRecordFile.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusRecordFile(session, bRecordFile);
            }

            // 改变t_bus_task_sample的状态
            BusTaskSample bts = new BusTaskSample();
            bts.setSampletran(item.getSampletran());
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setAuditstatus("92");
            bts.setCheckdesc("");
            bts.getDeal().setAction(DataAction.Submit.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            // 删除驳回提醒
            BusTodo bt = new BusTodo();
            bt.setTranid(item.getSampletran());
            bt.setTranuser(ou.getUser().getUserid());
            bt.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, bt);

            String[] sysUsers = approveusers.split(",");

            boolean isuser = false;

            for (String sysUser : sysUsers) {
                if (!sysUser.equals(ou.getUser().getUserid())) {
                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(item.getSampletran());
                    busTodo.setBusflow("RecordApprove");
                    busTodo.setFlownode("create");
                    busTodo.setSenddate(new Date());
                    busTodo.setTranuser(sysUser);
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(15));
                    busTodo.setTodotype("15");
                    // 判定是否同一个提醒
                    BusTodo bTodo = new BusTodo();
                    bTodo = FlowDao.GetBusTodo(session, busTodo);

                    if (bTodo == null) {
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    }

                    isuser = true;
                }
            }

            if (!isuser) {
                throw new Exception("指定复核人有误，请重新选择！");
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getRecordstatus()) {
            case "02":
                rtv.setMsg("试验报告生成成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void ApproveBusRecord(BusRecord item, ReturnValue rtv, String signerpassword, String auditusers,
            OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusRecord bRecord = new BusRecord();
            bRecord.setApproveuser(ou.getUser().getUserid());
            bRecord.setApproveusername(ou.getUser().getUsername());
            bRecord.setApprovedate(new Date());
            bRecord.setRecordstatus(item.getRecordstatus());
            bRecord.setTaskid(item.getTaskid());
            bRecord.setRecordid(item.getRecordid());
            bRecord.setModifydesc(item.getModifydesc());
            bRecord.setSampletran(item.getSampletran());
            bRecord.setAduituser(auditusers);
            DatDao.SubmitBusRecord(session, bRecord);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {

                // 改变t_bus_task_sample的状态
                BusTaskSample bts = new BusTaskSample();
                bts.setSampletran(item.getSampletran());
                bts.setAuditstatus("94");
                bts.setCheckdesc("");
                bts.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask bTask = new BusTask();
                bTask.setTaskid(item.getTaskid());
                bTask = LabDao.GetBusTask(bTask);
                bTask.setFlowstatus("94");
                bTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, bTask);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getSampletran());
                bTodo.setBusflow("RecordApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                boolean isuser = false;

                if (!auditusers.equals("") && auditusers != null) {
                    String[] sysUsers = auditusers.split(",");

                    for (String sysUser : sysUsers) {

                        if (!sysUser.equals(ou.getUser().getUserid()) && !sysUser.equals(item.getTranuser())) {
                            BusTodo busTodo = new BusTodo();
                            busTodo.setTranid(item.getSampletran());
                            busTodo.setBusflow("RecordAudit");
                            busTodo.setFlownode("create");
                            busTodo.setSenddate(new Date());
                            busTodo.setTranuser(sysUser);
                            busTodo.setIsnowflow(true);
                            busTodo.setSampleid(String.valueOf(1));
                            busTodo.setTodotype("16");
                            // 判定是否同一个提醒
                            BusTodo btd = new BusTodo();
                            btd = FlowDao.GetBusTodo(session, busTodo);

                            if (btd == null || btd.getTranid().equals("")) {
                                busTodo.getDeal().setAction(DataAction.Create.getAction());
                                FlowDao.SaveBusTodo(session, busTodo);
                            }
                            isuser = true;
                        }
                    }
                }

                if (!isuser) {
                    throw new Exception("指定批准人有误，请重新选择！");
                }

            }

            // 审批不通过提醒检测人员
            if (item.getDeal().getAction() == DataAction.InValid.getAction()) {

                // 改变t_bus_task_sample的状态
                BusTaskSample bts = new BusTaskSample();
                bts.setSampletran(item.getSampletran());
                bts.setAuditstatus("96");
                bts.setCheckdesc(item.getModifydesc());
                bts.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask busTask = new BusTask();
                busTask.setTaskid(item.getTaskid());
                busTask = LabDao.GetBusTask(busTask);
                busTask.setFlowstatus("96");
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, busTask);

                // 分别提醒检测人员
                BusRecord busRecord = new BusRecord();
                busRecord.setSampletran(item.getSampletran());
                List<BusRecord> busRecords = DatDao.GetBusRecordBySampleTran(busRecord);

                for (BusRecord br : busRecords) {
                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(br.getSampletran());
                    busTodo.setBusflow("RecordNoPass");
                    busTodo.setFlownode("create");
                    busTodo.setSenddate(new Date());
                    busTodo.setTranuser(br.getTranuser());
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(1));
                    busTodo.setTodotype("14");
                    // 判定是否同一个提醒
                    BusTodo btd = new BusTodo();
                    btd = FlowDao.GetBusTodo(session, busTodo);

                    if (btd == null || btd.getTranid().equals("")) {
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    }
                }
            }

            // 删除原始记录审批提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(item.getSampletran());
            bTodo.setBusflow("RecordApprove");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getRecordstatus()) {
            case "04":
                rtv.setMsg("原始记录表复核通过成功！");
                break;
            case "06":
                rtv.setMsg("原始记录表驳回成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void AuditBusRecord(BusRecord item, ReturnValue rtv, String signerpassword, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusRecord bRecord = new BusRecord();
            bRecord.setAduituser(ou.getUser().getUserid());
            bRecord.setAduitusername(ou.getUser().getUsername());
            bRecord.setAduitdate(new Date());
            bRecord.setRecordstatus(item.getRecordstatus());
            bRecord.setTaskid(item.getTaskid());
            bRecord.setRecordid(item.getRecordid());
            bRecord.setModifydesc(item.getModifydesc());
            // bRecord.setSamplecode(item.getSamplecode());
            bRecord.setSampletran(item.getSampletran());
            DatDao.SubmitBusRecord(session, bRecord);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {

                // 改变t_bus_task_sample的状态
                BusTaskSample bts = new BusTaskSample();
                bts.setSampletran(item.getSampletran());
                bts.setAuditstatus("98");
                bts.setCheckdesc("");
                bts.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask busTask = new BusTask();
                busTask.setTaskid(item.getTaskid());
                busTask = LabDao.GetBusTask(busTask);
                busTask.setFlowstatus("98");
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, busTask);

                // 删除截止日期提醒
                BusTaskSingle bSingle = new BusTaskSingle();
                bSingle.setTaskid(item.getTaskid());
                bSingle.setSamplecode(item.getSamplecode());
                bSingle = LabDao.GetBusTaskSingleByTaskid(bSingle);
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getTaskid() + bSingle.getLabuser());
                busTodo.setBusflow("BusTaskDeadline");
                busTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, busTodo);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getSampletran());
                bTodo.setBusflow("RecordAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);
            }

            // 审批不通过提醒检测人员
            if (item.getDeal().getAction() == DataAction.InValid.getAction()) {

                // 改变t_bus_task_sample的状态
                BusTaskSample bts = new BusTaskSample();
                bts.setSampletran(item.getSampletran());
                bts.setAuditstatus("99");
                bts.setCheckdesc(item.getModifydesc());
                bts.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask busTask = new BusTask();
                busTask.setTaskid(item.getTaskid());
                busTask = LabDao.GetBusTask(busTask);
                busTask.setFlowstatus("99");
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, busTask);

                // 分别提醒检测人员
                BusRecord busRecord = new BusRecord();
                busRecord.setSampletran(item.getSampletran());
                List<BusRecord> busRecords = DatDao.GetBusRecordBySampleTran(busRecord);

                for (BusRecord br : busRecords) {
                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(br.getSampletran());
                    busTodo.setBusflow("RecordNoPass");
                    busTodo.setFlownode("create");
                    busTodo.setSenddate(new Date());
                    busTodo.setTranuser(br.getTranuser());
                    // busTodo.setTrandept(ou.getDept().getDeptid());
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(1));
                    busTodo.setTodotype("14");
                    // 判定是否同一个提醒
                    BusTodo btd = new BusTodo();
                    btd = FlowDao.GetBusTodo(session, busTodo);

                    if (btd == null || btd.getTranid().equals("")) {
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    }
                }
            }

            // 删除原始记录审批提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(item.getSampletran());
            bTodo.setBusflow("RecordAudit");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            // region 生成报告
            BusTask btask = new BusTask();
            btask.setTaskid(item.getTaskid());
            BusTask btsapm = LabDao.GetBusTask(btask);
            BasSample bsample = new BasSample();
            bsample.setSampleid(btsapm.getSampleid());
            BasSample rtnsample = BasDao.GetBasSample(bsample);
            if (rtnsample.getSamplemain() == "000766" || rtnsample.getSamplemain().equals("000766")) {
                // 判断原始记录表是否全部已提交
                BusTask bCount = new BusTask();
                bCount.setSamplecode(item.getSamplecode());
                bCount.getItem().setGetaction("000766");
                List<BusTask> busTasks = LabDao.GetListBusTaskForCount(session, bCount);
                if (busTasks.size() == 0) {

                    BusTaskSingle bSingle = new BusTaskSingle();
                    bSingle.setSamplecode(item.getSamplecode());
                    bSingle.setTaskid(item.getTaskid());
                    bSingle = LabDao.GetBusTaskSingleByTaskid(bSingle);

                    boolean isnew = false;

                    List<BusReport> reports = DatDao.GetBusReportByTask(session, bSingle);

                    if (reports.size() == 0) {
                        reports = DatDao.GetNewBusReport(session, bSingle);
                        isnew = true;
                    }

                    // int i = 0;
                    for (BusReport report : reports) {
                        if (isnew) {

                            // 判断该样品是否合格
                            BusTaskSample bSample = new BusTaskSample();
                            bSample.setSamplecode(item.getSamplecode());
                            bSample.getItem().setGetaction("000766");
                            List<BusTaskSample> lists = LabDao.GetBusTaskSampleForJudge(bSample);
                            if (lists.size() > 0) {
                                report.setIspass("不合格");
                            } else {
                                report.setIspass("合格");
                            }

                            report.getDeal().setAction(DataAction.Create.getAction());
                        }

                        report.setTranid(bSingle.getTranid());
                        report.setTaskid(bSingle.getTaskid());
                        report.setModifydesc("");
                        DatDao.SaveBusReport(session, report);
                    }
                }
            } else {
                // 判断原始记录表是否全部已提交
                BusTask bCount = new BusTask();
                bCount.setSamplecode(item.getSamplecode());
                List<BusTask> busTasks = LabDao.GetListBusTaskForCount(session, bCount);
                if (busTasks.size() == 0) {

                    BusTaskSingle bSingle = new BusTaskSingle();
                    bSingle.setSamplecode(item.getSamplecode());
                    bSingle.setTaskid(item.getTaskid());
                    bSingle = LabDao.GetBusTaskSingleByTaskid(bSingle);

                    boolean isnew = false;

                    List<BusReport> reports = DatDao.GetBusReportByTask(session, bSingle);

                    if (reports.size() == 0) {
                        reports = DatDao.GetNewBusReport(session, bSingle);
                        isnew = true;
                    }

                    // int i = 0;
                    for (BusReport report : reports) {
                        if (isnew) {

                            // 判断转基因内标准基因是否为阳性（合格）
                            BusTaskResult bResult = new BusTaskResult();
                            bResult.setSamplecode(bSingle.getSamplecode());
                            List<BusTaskResult> bResults = LabDao.GetListBusTaskResultForIn(bResult);
                            // 内标准基因检测参数（样品身份证，必须为阳性）为阴性不合格
                            if (bResults.size() > 0) {
                                report.setIspass("不合格");
                            } else {
                                // 判断该样品是否合格
                                BusTaskSample bSample = new BusTaskSample();
                                bSample.setSamplecode(bSingle.getSamplecode());
                                List<BusTaskSample> lists = LabDao.GetBusTaskSampleForJudge(bSample);
                                if (lists.size() > 0) {
                                    report.setIspass("不合格");
                                } else {
                                    report.setIspass("合格");
                                }
                            }

                            report.getDeal().setAction(DataAction.Create.getAction());
                        }

                        report.setTranid(bSingle.getTranid());
                        report.setTaskid(bSingle.getTaskid());
                        report.setModifydesc("");
                        DatDao.SaveBusReport(session, report);
                    }
                }
            }

            // DatDao.SyncBusReport(session, bSingle);

            // endregion 生成报告

            rtv.setSuccess(true);
            switch (item.getRecordstatus()) {
            case "08":
                rtv.setMsg("原始记录表审核通过成功！");
                break;
            case "10":
                rtv.setMsg("原始记录表驳回成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    // endregion BusRecord Methods

    // region DatClassSource Methods

    public static void SaveDatClassSource(DatClassSource item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DatDao.SaveDatClassSource(session, item);

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

    // endregion DatClassSource Methods

    // region DatSampleField Methods

    public static void SaveDatSampleField(DatSampleField item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            DatDao.SaveDatSampleField(session, item);

            DatSampleParameter dter = new DatSampleParameter();
            // dter.setSampleid(item.getSampleid());
            dter.setParameterid(item.getParameterids());
            dter.setClasssource(item.getClasssource());
            dter.setClassfield(item.getClassfield());
            dter.getDeal().setAction(DataAction.Deal.getAction());
            DatDao.SaveDatSampleParameter(session, dter);

            String[] parameterids = item.getParameterids().split(",");
            dter.getDeal().setAction(DataAction.Create.getAction());
            for (String parameterid : parameterids) {
                dter.setParameterid(parameterid);
                DatDao.SaveDatSampleParameter(session, dter);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getParameterids() + item.getParameternames());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion DatSampleField Methods

    // region SetBusReport Methods

    public static SetBusReport GetSetBusReport(BusReport item) {
        SetBusReport rtn = new SetBusReport();

        SqlSession session = DBUtils.getFactory();

        try {

            rtn.setRecord(DatDao.GetBusReport(session, item));

            if (!ToolUtils.StringIsEmpty(rtn.getRecord().getReportid())) {
                FrmReport frm = new FrmReport();
                frm.setFormid(rtn.getRecord().getFormid());
                rtn.setForm(FormDao.GetFrmReport(session, frm));

                // rtn.getForm().setFormlength(rtn.getRecord().getFormcount()*38+77);

                BusReportDetail detail = new BusReportDetail();
                detail.setReportid(rtn.getRecord().getReportid());

                SetBusReportDetail brd = new SetBusReportDetail();
                brd.setFormserial(0);
                brd.setDatas(DatDao.GetBusReportDetailBySerial(session, detail));
                rtn.getDetails().add(brd);

                for (int i = 0; i < rtn.getRecord().getFormcount(); i++) {
                    detail.setFormserial(i + 1);

                    SetBusReportDetail sd = new SetBusReportDetail();
                    sd.setFormserial(i + 1);
                    sd.setDatas(DatDao.GetBusReportDetailBySerial(session, detail));

                    for (BusReportDetail ditem : sd.getDatas()) {
                        if (ditem.getFieldcode().equals("{reportid}")) {
                            ditem.setCelltext(ditem.getReportid());

                            continue;
                        }

                        if (ditem.getFieldcode().equals("{actreportid}")) {
                            ditem.setCelltext(ditem.getActreportid());

                            continue;
                        }

                        if (ditem.getFieldcode().equals("{nowpage}")) {
                            ditem.setCelltext(String.valueOf(i + 1));

                            continue;
                        }

                        if (ditem.getFieldcode().equals("{allpage}")) {
                            ditem.setCelltext(String.valueOf(rtn.getRecord().getFormcount()));

                            continue;
                        }
                    }

                    rtn.getDetails().add(sd);
                }
            }

            return rtn;
        } catch (Exception e) {
            return new SetBusReport();
        } finally {
            session.close();
        }
    }

    // endregion SetBusReport Methods

    // region BusReport Methods

    public static void SaveBusReport(BusReport item, List<BusReportFile> filedetails, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            String logstr = "";
            BusReport busReport = new BusReport();
            busReport.setReportid(item.getReportid());
            busReport = DatDao.GetBusReport(session, busReport);
            if (!busReport.getReportresult().equals(item.getReportresult())) {
                logstr = logstr + "  字段名称:检验结论,  旧值:" + busReport.getReportresult() + ",  新值:" + item.getReportresult()
                        + ";";
            }
            if (!busReport.getReportrequest().equals(item.getReportrequest())) {
                logstr = logstr + "  字段名称:备注,  旧值:" + busReport.getReportrequest() + ",  新值:" + item.getReportrequest()
                        + ";";
            }

            busReport.setReportresult(item.getReportresult());
            // busReport.setReportdeal(item.getReportdeal());
            busReport.setReportstatus("01");
            busReport.setReportrequest(item.getReportrequest());
            busReport.setModifydesc("");
            busReport.getDeal().setAction(DataAction.Deal.getAction());
            DatDao.SaveBusReport(session, busReport);

            BusReportFile bFile = new BusReportFile();
            bFile.setTranid(item.getReportid());
            bFile.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusReportFile(session, bFile);

            for (BusReportFile busReportFile : filedetails) {
                busReportFile.setTranid(item.getReportid());
                busReportFile.setSamplecode(item.getSamplecode());
                busReportFile.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusReportFile(session, busReportFile);
            }

            BusTaskSingle busTaskSingle = new BusTaskSingle();
            busTaskSingle.setTaskid(item.getTaskid());
            busTaskSingle.setSamplecode(item.getSamplecode());
            busTaskSingle = LabDao.GetBusTaskSingleByTaskid(session, busTaskSingle);
            if (!busTaskSingle.getTestenv().equals(item.getTestenv())) {
                logstr = logstr + "  字段名称:试验环境条件,  旧值:" + busTaskSingle.getTestenv() + ",  新值:" + item.getTestenv()
                        + ";";
            }
            if (!busTaskSingle.getDevnames().equals(item.getDevnames())) {
                logstr = logstr + "  字段名称:使用设备,  旧值:" + busTaskSingle.getDevnames() + ",  新值:" + item.getDevnames()
                        + ";";
            }
            if (!busTaskSingle.getSamplestatus().equals(item.getSamplestatus())) {
                logstr = logstr + "  字段名称:样品状态,  旧值:" + busTaskSingle.getSamplestatus() + ",  新值:"
                        + item.getSamplestatus() + ";";
            }

            busTaskSingle.setTestenv(item.getTestenv());
            busTaskSingle.setDevnames(item.getDevnames());
            busTaskSingle.setDevids(item.getDevids());
            busTaskSingle.setSamplestatus(item.getSamplestatus());
            busTaskSingle.setTeststandardname(item.getTeststandardname());
            busTaskSingle.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusTaskSingleLab(session, busTaskSingle);

            if (!(busReport.getFormid().equals("0000000110") || busReport.getFormid().equals("0000000111"))) {
                BusGet busGet = new BusGet();
                busGet = LabDao.GetBusGetBySampleCode(session, item.getSamplecode());
                if (!busGet.getTesttypename().equals(item.getTesttypename())) {
                    logstr = logstr + "  字段名称:检验类别,  旧值:" + busGet.getTesttypename() + ",  新值:" + item.getTesttypename()
                            + ";";
                }
                if (!busGet.getTestedname().equals(item.getTestedname())) {
                    logstr = logstr + "  字段名称:受检单位,  旧值:" + busGet.getTestedname() + ",  新值:" + item.getTestedname()
                            + ";";
                }
                busGet.setTesttypename(item.getTesttypename());
                busGet.setTestedname(item.getTestedname());
                busGet.getDeal().setAction(DataAction.Modify.getAction());
                LabDao.SaveBusGet(session, busGet);

                BusGetDetail busGetDetail = new BusGetDetail();
                busGetDetail.setSamplecode(item.getSamplecode());
                ;
                busGetDetail = LabDao.GetBusGetDetailBySampleCode(busGetDetail);

                if (!busGetDetail.getSamplename().equals(item.getSamplename())) {
                    logstr = logstr + "  字段名称:样品名称,  旧值:" + busGetDetail.getSamplename() + ",  新值:"
                            + item.getSamplename() + ";";
                }
                if (!busGetDetail.getPrdcode().equals(item.getPrdcode())) {
                    logstr = logstr + "  字段名称:生产批号,  旧值:" + busGetDetail.getSamplename() + ",  新值:" + item.getPrdcode()
                            + ";";
                }
                if (!busGetDetail.getSamplebase().equals(item.getSamplebase())) {
                    logstr = logstr + "  字段名称:抽样基数,  旧值:" + busGetDetail.getSamplebase() + ",  新值:"
                            + item.getSamplebase() + ";";
                }
                if (!busGetDetail.getGetaddr().equals(item.getGetaddr())) {
                    logstr = logstr + "  字段名称:抽样地点,  旧值:" + busGetDetail.getGetaddr() + ",  新值:" + item.getGetaddr()
                            + ";";
                }
                if (!busGetDetail.getMainstandname().equals(item.getTeststandardname())) {
                    logstr = logstr + "  字段名称:检测依据,  旧值:" + busGetDetail.getMainstandname() + ",  新值:"
                            + item.getTeststandardname() + ";";
                }
                if (!busGetDetail.getTrademark().equals(item.getTrademark())) {
                    logstr = logstr + "  字段名称:商标,  旧值:" + busGetDetail.getTrademark() + ",  新值:" + item.getTrademark()
                            + ";";
                }
                if (!busGetDetail.getSamplestand().equals(item.getSamplestand())) {
                    logstr = logstr + "  字段名称:型号规格,  旧值:" + busGetDetail.getSamplestand() + ",  新值:"
                            + item.getSamplestand() + ";";
                }
                if (!busGetDetail.getSamplecount().equals(item.getSamplecount())) {
                    logstr = logstr + "  字段名称:样品数量,  旧值:" + busGetDetail.getSamplecount() + ",  新值:"
                            + item.getSamplecount() + ";";
                }
                if (!busGetDetail.getFactname().equals(item.getFactname())) {
                    logstr = logstr + "  字段名称:生产单位,  旧值:" + busGetDetail.getFactname() + ",  新值:" + item.getFactname()
                            + ";";
                }
                if (!busGetDetail.getTestitems().equals(item.getTestitems())) {
                    logstr = logstr + "  字段名称:检测项目,  旧值:" + busGetDetail.getTestitems() + ",  新值:" + item.getTestitems()
                            + ";";
                }
                busGetDetail.setSamplename(item.getSamplename());
                busGetDetail.setPrdcode(item.getPrdcode());
                busGetDetail.setSamplebase(item.getSamplebase());
                busGetDetail.setGetaddr(item.getGetaddr());
                busGetDetail.setMainstandname(item.getTeststandardname());
                busGetDetail.setTrademark(item.getTrademark());
                busGetDetail.setSamplestand(item.getSamplestand());
                busGetDetail.setSamplecount(item.getSamplecount());
                busGetDetail.setFactname(item.getFactname());
                busGetDetail.setTestitems(item.getTestitems());
                busGetDetail.getDeal().setAction(DataAction.Deal.getAction());
                LabDao.SaveBusGetDetail(session, busGetDetail);

            }
            // 根据表单的内容修改t_bus_record_data,更新t_bus_report_detail中的信息
            DatDao.SaveBusRecordData(session, item);

            if (item.getReportstatus().equals("06") || item.getReportstatus().equals("10")) {
                // 删除试验报告审批或者审核驳回提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportBack");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            log.setTrandescdetail(logstr);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"reportid\":\"" + item.getReportid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SubmitBusReport(BusReport item, String signerpassword, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusReport bReport = DatDao.GetBusReport(session, item);
            bReport.setTranuser(ou.getUser().getUserid());
            bReport.setTranusername(ou.getUser().getUsername());
            bReport.setTrandate(new Date());
            bReport.setReportstatus(item.getReportstatus());
            bReport.setModifydesc(item.getModifydesc());
            bReport.setSamplecode(item.getSamplecode());
            DatDao.SubmitBusReport(session, bReport);

            SysUser sysUser = new SysUser();
            // sysUser.setDeptid(log.getTrandept());
            List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
            for (SysUser sUser : sysUsers) {
                if (sUser.getRoleid().equals("5")) { // 5是办公室主任
                    if (!sUser.getUserid().equals(ou.getUser().getUserid())) {
                        BusTodo busTodo = new BusTodo();
                        busTodo.setTranid(item.getReportid());
                        busTodo.setBusflow("ReportApprove");
                        busTodo.setFlownode("create");
                        busTodo.setSenddate(new Date());
                        busTodo.setTranuser(sUser.getUserid());
                        busTodo.setIsnowflow(true);
                        busTodo.setSampleid("17");
                        busTodo.setTodotype("17");
                        // 判定是否同一个提醒
                        BusTodo btd = new BusTodo();
                        btd = FlowDao.GetBusTodo(session, busTodo);

                        if (btd == null || btd.getTranid().equals("")) {
                            busTodo.getDeal().setAction(DataAction.Create.getAction());
                            FlowDao.SaveBusTodo(session, busTodo);
                        }
                    }
                }
            }

            // 生成试验报告
            // DatDao.UpdateBusReportInfo(session, item);
            //
            // BusTaskSingle busTask = new BusTaskSingle();
            // busTask.setSamplecode(item.getSamplecode());
            // DatDao.SyncBusReport(session, busTask);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getReportstatus()) {
            case "02":
                rtv.setMsg("报告签名、提交成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void ApproveBusReport(BusReport item, String signerpassword, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusReport bReport = DatDao.GetBusReport(session, item);
            bReport.setApproveuser(ou.getUser().getUserid());
            bReport.setApproveusername(ou.getUser().getUsername());
            bReport.setApprovedate(new Date());
            bReport.setAduituser("");
            bReport.setAduitusername("");
            bReport.setReportstatus(item.getReportstatus());
            bReport.setModifydesc(item.getModifydesc());
            bReport.setSamplecode(item.getSamplecode());
            DatDao.SubmitBusReport(session, bReport);

            // 试验报告审批通过提交给中心主任
            if (item.getDeal().getAction() == DataAction.Check.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                SysUser sysUser = new SysUser();
                // sysUser.setDeptid(log.getTrandept());
                List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                for (SysUser sUser : sysUsers) {
                    if (sUser.getRoleid().equals("4") || sUser.getRoleid().equals("20")) { // 4是中心主任，20是中心副主任
                        if (!sUser.getUserid().equals(ou.getUser().getUserid())) {
                            BusTodo busTodo = new BusTodo();
                            busTodo.setTranid(item.getReportid());
                            busTodo.setBusflow("ReportAudit");
                            busTodo.setFlownode("create");
                            busTodo.setSenddate(new Date());
                            busTodo.setTranuser(sUser.getUserid());
                            // busTodo.setTrandept(ou.getDept().getDeptid());
                            busTodo.setIsnowflow(true);
                            busTodo.setSampleid("18");
                            busTodo.setTodotype("18");
                            // 判定是否同一个提醒
                            BusTodo btd = new BusTodo();
                            btd = FlowDao.GetBusTodo(session, busTodo);

                            if (btd == null || btd.getTranid().equals("")) {
                                busTodo.getDeal().setAction(DataAction.Create.getAction());
                                FlowDao.SaveBusTodo(session, busTodo);
                            }
                        }
                    }
                }
            }

            // 试验报告审批驳回后返回到办公室重新编制
            if (item.getDeal().getAction() == DataAction.UnCheck.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                BusReport rtn = DatDao.GetBusReport(item);
                // SysUser sysUser = new SysUser();
                //// sysUser.setDeptid("7000");
                // List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                // for(SysUser sUser: sysUsers){
                // if(sUser.getRoleid().equals("17")){
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getReportid());
                busTodo.setBusflow("ReportBack");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                // busTodo.setTranuser(sUser.getUserid());
                busTodo.setTranuser(rtn.getTranuser());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid("20");
                busTodo.setTodotype("20");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
                // }
                // }
            }

            // 试验报告不合格，需申请备份样品，提交给技术负责人进行审批
            if (item.getDeal().getAction() == DataAction.Special01.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                SysUser sysUser = new SysUser();
                // sysUser.setDeptid("7000");
                List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                for (SysUser sUser : sysUsers) {
                    if (sUser.getRoleid().equals("8")) {
                        BusTodo busTodo = new BusTodo();
                        busTodo.setTranid(item.getReportid());
                        busTodo.setBusflow("ReportBackUp");
                        busTodo.setFlownode("create");
                        busTodo.setSenddate(new Date());
                        busTodo.setTranuser(sUser.getUserid());
                        busTodo.setIsnowflow(true);
                        busTodo.setSampleid("21");
                        busTodo.setTodotype("21");
                        // 判定是否同一个提醒
                        BusTodo btd = new BusTodo();
                        btd = FlowDao.GetBusTodo(session, busTodo);

                        if (btd == null || btd.getTranid().equals("")) {
                            busTodo.getDeal().setAction(DataAction.Create.getAction());
                            FlowDao.SaveBusTodo(session, busTodo);
                        }
                    }
                }
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getReportstatus()) {
            case "04":
                rtv.setMsg("审批通过提交成功！");
                break;
            case "06":
                rtv.setMsg("审批驳回提交成功！");
                break;
            case "14":
                rtv.setMsg("申领备份样提交成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void BackBusReport(BusReport item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            DatDao.BackBusReport(session, item);

            BusTaskSingle busTaskSingle = new BusTaskSingle();
            busTaskSingle.setTaskid(item.getTaskid());
            busTaskSingle.setSamplecode(item.getSamplecode());
            busTaskSingle = LabDao.GetBusTaskSingleByTaskid(busTaskSingle);
            List<BusTaskSingle> busTaskSingles = LabDao.GetBusTaskSingleByTranID(busTaskSingle);
            for (BusTaskSingle bTaskSingle : busTaskSingles) {

                BusTaskSample busTaskSample = new BusTaskSample();
                busTaskSample.setCheckdesc(item.getModifydesc());
                busTaskSample.setAuditstatus("92");
                busTaskSample.setSampletran(bTaskSingle.getSampletran());
                busTaskSample.getDeal().setAction(DataAction.Check.getAction());
                LabDao.SaveBusTaskSample(session, busTaskSample);

                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(busTaskSingle.getTranid());
                busTodo.setBusflow("ReportNoPass");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                busTodo.setTranuser(bTaskSingle.getTestuser());
                // busTodo.setTrandept(ou.getDept().getDeptid());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype("14");
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("提交成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void AuditBusReport(BusReport item, String signerpassword, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusReport bReport = DatDao.GetBusReport(session, item);
            bReport.setAduituser(ou.getUser().getUserid());
            bReport.setAduitusername(ou.getUser().getUsername());
            bReport.setAduitdate(new Date());
            bReport.setReportstatus(item.getReportstatus());
            bReport.setModifydesc(item.getModifydesc());
            bReport.setSamplecode(item.getSamplecode());
            DatDao.SubmitBusReport(session, bReport);

            // 试验报告审核通过后通知办公室打印
            if (item.getDeal().getAction() == DataAction.Check.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审核提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                BusReport rtn = DatDao.GetBusReport(item);
                // SysUser sysUser = new SysUser();
                //// sysUser.setDeptid("7000");
                // List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                // for(SysUser sUser: sysUsers){
                // if(sUser.getRoleid().equals("17")){
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getReportid());
                busTodo.setBusflow("ReportPrint");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                // busTodo.setTranuser(sUser.getUserid());
                busTodo.setTranuser(rtn.getTranuser());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid("19");
                busTodo.setTodotype("19");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
                // }
                // }
            }

            // 试验报告审批驳回后返回到办公室重新编制
            if (item.getDeal().getAction() == DataAction.UnCheck.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审核提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                BusReport rtn = DatDao.GetBusReport(item);
                // SysUser sysUser = new SysUser();
                //// sysUser.setDeptid("7000");
                // List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                // for(SysUser sUser: sysUsers){
                // if(sUser.getRoleid().equals("17")){
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getReportid());
                busTodo.setBusflow("ReportBack");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                // busTodo.setTranuser(sUser.getUserid());
                busTodo.setTranuser(rtn.getTranuser());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid("20");
                busTodo.setTodotype("20");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
                // }
                // }
            }

            // 试验报告不合格，需申请备份样品，提交给技术负责人进行审批
            if (item.getDeal().getAction() == DataAction.Special01.getAction()) {

                // 删除无需备份样提醒
                BusTodo btdo = new BusTodo();
                btdo.setTranid(item.getReportid());
                btdo.setBusflow("BackUpNo");
                btdo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, btdo);

                // 删除试验报告审核提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                SysUser sysUser = new SysUser();
                // sysUser.setDeptid("7000");
                List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);
                for (SysUser sUser : sysUsers) {
                    if (sUser.getRoleid().equals("8")) {
                        BusTodo busTodo = new BusTodo();
                        busTodo.setTranid(item.getReportid());
                        busTodo.setBusflow("ReportBackUp");
                        busTodo.setFlownode("create");
                        busTodo.setSenddate(new Date());
                        busTodo.setTranuser(sUser.getUserid());
                        busTodo.setIsnowflow(true);
                        busTodo.setSampleid("21");
                        busTodo.setTodotype("21");
                        // 判定是否同一个提醒
                        BusTodo btd = new BusTodo();
                        btd = FlowDao.GetBusTodo(session, busTodo);

                        if (btd == null || btd.getTranid().equals("")) {
                            busTodo.getDeal().setAction(DataAction.Create.getAction());
                            FlowDao.SaveBusTodo(session, busTodo);
                        }
                    }
                }
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getReportstatus()) {
            case "08":
                rtv.setMsg("审核通过提交成功！");
                break;
            case "10":
                rtv.setMsg("审核驳回提交成功！");
                break;
            case "14":
                rtv.setMsg("申领备份样提交成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void BackUpBusReport(BusReport item, String signerpassword, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusReport bReport = DatDao.GetBusReport(session, item);
            bReport.setReportstatus(item.getReportstatus());
            bReport.setModifydesc(item.getModifydesc());
            bReport.setSamplecode(item.getSamplecode());
            DatDao.SubmitBusReport(session, bReport);

            if (item.getDeal().getAction() == DataAction.Check.getAction()) {

                // 删除申领备份样提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportBackUp");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                // 提醒办公室下达备份样
                BusTodo busTodo = new BusTodo();
                String samplecode = item.getSamplecode();
                busTodo.setTranid(samplecode.substring(0, samplecode.length() - 2));
                busTodo.setBusflow("BackUpPass");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                busTodo.setTranuser("300");
                busTodo.setIsnowflow(true);
                busTodo.setSampleid("22");
                busTodo.setTodotype("22");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            if (item.getDeal().getAction() == DataAction.UnCheck.getAction()) {

                // 删除申领备份样提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(item.getReportid());
                bTodo.setBusflow("ReportBackUp");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                // 提醒审批人或者是审核人无需申领备份样
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getReportid());
                busTodo.setBusflow("BackUpNo");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());

                if (bReport.getAduituser().equals("")) {
                    busTodo.setTranuser(bReport.getApproveuser());
                    busTodo.setSampleid("23");
                    busTodo.setTodotype("23");
                } else {
                    busTodo.setTranuser(bReport.getAduituser());
                    busTodo.setSampleid("24");
                    busTodo.setTodotype("24");
                }

                busTodo.setIsnowflow(true);

                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getReportstatus()) {
            case "16":
                rtv.setMsg("申领备份样通过！");
                break;
            case "18":
                rtv.setMsg("申领备份样被驳回成功！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void PrintBusReport(BusReport item, String signerpassword, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {

            // 权限密码判断
            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            SignerPassword pwd = new SignerPassword();
            pwd.setUserid(log.getTranuser());
            pwd = LabDao.GetSignerPassword(pwd);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }

            } else {

                if (ToolUtils.StringIsEmpty(signerpassword)
                        || !su.getUserpassword().equals(ToolUtils.GetMD5(signerpassword))) {
                    rtv.setMsg("密码不正确！");
                    return;
                }
            }

            BusReport bReport = DatDao.GetBusReport(session, item);
            bReport.setReportstatus(item.getReportstatus());
            DatDao.SubmitBusReport(session, bReport);

            BusTodo btdo = new BusTodo();
            btdo.setTranid(item.getReportid());
            btdo.setBusflow("ReportPrint");
            btdo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, btdo);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (item.getReportstatus()) {
            case "12":
                rtv.setMsg("试验报告打印完成！");
                break;
            default:
                break;
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static String GetExcelByRecord(SetBusRecord records, String filename) {

        URL ut = Thread.currentThread().getContextClassLoader().getResource("");
        String path = ut.toString().replace("file:/", "").replace("WEB-INF/classes/", "") + "model/record.xls";
        String newfile = FileUtils.GetFileBasePath() + "record/"
                + ToolUtils.GetFmtDate(ToolUtils.GetNowDate(), Consts.STR_DATE_FMT) + "/"
                + FileUtils.GetUUIDFileName("xls");

        Workbook wb;
        WritableWorkbook workbook;

        try {
            FileUtils.CopyFile(path, newfile);

            wb = Workbook.getWorkbook(new File(newfile));

            workbook = Workbook.createWorkbook(new File(newfile), wb);

            WritableSheet sheet = workbook.getSheet(0); // .createSheet(filename, 0);
            sheet.setName(filename);

            sheet.getSettings().setTopMargin(0);
            sheet.getSettings().setLeftMargin(0);
            sheet.getSettings().setBottomMargin(0);
            sheet.getSettings().setRightMargin(0);

            if (records.getForm().getFormdirect().equals("1"))
                sheet.getSettings().setOrientation(PageOrientation.LANDSCAPE);
            else
                sheet.getSettings().setOrientation(PageOrientation.PORTRAIT);

            int i = 0, j = 0, pagerow = 0;
            String value = "";

            for (i = 0; i < records.getDetails().size(); i++) {
                pagerow = i * records.getForm().getFormlength();

                for (j = 0; j < records.getForm().getFormlength(); j++) {
                    sheet.setRowView(j + pagerow, 400);
                }

                for (BusRecordDetail detail : records.getDetails().get(i).getDatas()) {
                    sheet.mergeCells(detail.getBegincolumn() - 1, detail.getBeginrow() - 1 + pagerow,
                            detail.getEndcolumn() - 1, detail.getEndrow() - 1 + pagerow);

                    switch (ValueSource.parse(detail.getValuesource())) {
                    case Text:
                        value = detail.getCellname();
                        break;

                    case Data:
                        value = detail.getCelltext();

                        if (ToolUtils.StringIsEmpty(value))
                            value = JudgeService.DEF_VALUE;
                        break;

                    default:
                        value = "";
                        break;
                    }

                    WritableCellFormat nowformat = new WritableCellFormat();
                    if (value.length() > (detail.getEndcolumn() - detail.getBegincolumn() + 1) * 2)
                        nowformat.setWrap(true);

                    WritableFont font = new WritableFont(WritableFont.createFont(OfficeUtils.GetFontName()),
                            detail.getFontsize(), WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

                    nowformat.setBorder(Border.NONE, BorderLineStyle.THIN);

                    if (detail.getIsborder() > 0)
                        nowformat.setBorder(Border.ALL, BorderLineStyle.THIN);

                    if (detail.getIsline())
                        nowformat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);

                    nowformat.setVerticalAlignment(VerticalAlignment.CENTRE);

                    switch (AlignType.parse(detail.getAligntype())) {
                    case Left:
                        nowformat.setAlignment(jxl.format.Alignment.LEFT);
                        break;

                    case Right:
                        nowformat.setAlignment(jxl.format.Alignment.RIGHT);
                        break;

                    default:
                        nowformat.setAlignment(jxl.format.Alignment.CENTRE);
                        break;
                    }

                    font.setPointSize(detail.getFontsize());
                    font.setBoldStyle(detail.getIsbold() ? WritableFont.BOLD : WritableFont.NO_BOLD);
                    nowformat.setFont(font);

                    // WritableImage wimage = new WritableImage(x, y, width, height,
                    // imageData);

                    sheet.addCell(new jxl.write.Label(detail.getBegincolumn() - 1, detail.getBeginrow() - 1 + pagerow,
                            value, nowformat));
                }
            }

            // WritableSheet sheet1 = workbook.createSheet("test", 1);

            // for (j = 1; j < records.getForm().getFormwidth(); j++) {
            // sheet1.setColumnView(j, cv);
            // }

            workbook.write();
            workbook.close();
        } catch (Exception e) {
        }

        return newfile;
    }

    public static void CheckBusReport(BusReport item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            DatDao.SubmitBusReport(session, item);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("提交成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static void MakeBusReport(BusReport item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        SqlSession session = DBUtils.getFactory();

        try {
            // 生成试验报告
            BusReport bReport = DatDao.GetBusReport(session, item);

            if (bReport.getFormid().equals("0000000110")) {
                DatDao.UpdateEnvReportInfo(session, item);
            } else if (bReport.getFormid().equals("0000000111")) {
                DatDao.UpdateNgEnvReportInfo(session, item);
            } else {

                DatDao.UpdateBusReportInfo(session, item);
            }
            BusTaskSingle busTask = new BusTaskSingle();
            busTask.setSamplecode(item.getSamplecode());
            DatDao.SyncBusReport(session, busTask);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("试验报告生成成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "生成失败！"));
        } finally {
            session.close();
        }

    }

    public static void BackBusRecord(String reportid, String deptuser, String wincheckdesc, ReturnValue rtv,
            OnlineUser ou, TranLog log) {

        SqlSession session = DBUtils.getFactory();

        try {
            // 删除试验报告审批或者审核驳回提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(reportid);
            bTodo.setBusflow("ReportBack");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            BusReport backMessage = new BusReport();
            backMessage.setReportid(reportid);
            backMessage.setModifydesc(wincheckdesc);
            backMessage.setTranuser(deptuser);

            DatDao.BackBusRecord(session, backMessage);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("驳回报告编号:" + reportid);
            PublicDao.SaveTranLog(session, log);
            rtv.setSuccess(true);
            rtv.setMsg("驳回报告" + reportid + "成功");
            ;
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }

    }

    // endregion BusReport Methods

}
