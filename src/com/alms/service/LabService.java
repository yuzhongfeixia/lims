package com.alms.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.dao.DatDao;
import com.alms.dao.FlowDao;
import com.alms.dao.LabDao;
import com.alms.entity.bas.BasLabParameter;
import com.alms.entity.bas.BasMainParameter;
import com.alms.entity.bas.BasSample;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.DatComputeData;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.lab.BusAccFile;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusRecordSamples;
import com.alms.entity.lab.BusTaskFile;
import com.alms.entity.lab.SignerPassword;
import com.alms.entity.lab.BusAccSample;
import com.alms.entity.lab.BusCatalogParam;
import com.alms.entity.lab.BusConsign;
import com.alms.entity.lab.BusConsignParam;
import com.alms.entity.lab.BusConsignSample;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusGetNoticeDetail;
import com.alms.entity.lab.BusProc;
import com.alms.entity.lab.BusRecordData;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskData;
import com.alms.entity.lab.BusTaskDev;
import com.alms.entity.lab.BusTaskJudge;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.BusTaskTest;
import com.alms.entity.lab.BusTaskTester;
import com.alms.enums.DataSource;
import com.alms.enums.TodoType;
import com.gpersist.dao.PublicDao;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysUser;
import com.gpersist.entity.user.SysUserRole;
import com.gpersist.enums.ClassSource;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.FieldType;
import com.gpersist.enums.JudgeStatus;
import com.gpersist.enums.TranAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.LogDetailsUtils;
import com.gpersist.utils.ToolUtils;

public class LabService {

    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    // region BusGetNotice Methods

    public static void SaveBusGetNotice(BusGetNotice item, List<BusGetNoticeDetail> details, ReturnValue rtv,
            OnlineUser ou, TranLog log) {

        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            String str = "";
            // 编辑委托单位
            if (!item.getTestedname().equals("") && item.getTestedname() != null) {
                BusTestedUnit bUnit = new BusTestedUnit();

                if (item.getTestedid().equals("")) {
                    bUnit.setTestedname(item.getTestedname());
                    bUnit.setComtype(item.getTesttype());
                    bUnit.getDeal().setAction(DataAction.Create.getAction());
                } else {

                    bUnit.setTestedid(item.getTestedid());
                    bUnit = BasDao.GetBusTestedUnit(bUnit);
                    str = LogDetailsUtils.BusGetNoticeTestedUnitDiff(bUnit, item);
                    bUnit.setTestedname(item.getTestedname());
                    bUnit.getDeal().setAction(DataAction.Modify.getAction());

                    // bUnit.setTestedid(item.getTestedid());
                    // bUnit = BasDao.GetBusTestedUnit(bUnit);
                    // bUnit.setTestedname(item.getTestedname());
                    // bUnit.getDeal().setAction(DataAction.Modify.getAction());
                }

                BasDao.SaveBusTestedUnit(session, bUnit);
                item.setTestedid(bUnit.getTestedid());
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setTrandate(new Date());
                item.setTrandatecn(ToolUtils.GetFmtDate(new Date(), "yyyyMMdd"));
                item.setTranuser(ou.getUser().getUserid());
                item.setTranusername(ou.getUser().getUsername());
                item.setFlowaction("50");
                item.setFlowstatus("01");
            }

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGetNotice busgetnotice = LabDao.GetBusGetNotice(item);
                item.setTrandate(busgetnotice.getTrandate());
                item.setTrandatecn(busgetnotice.getTrandatecn());
                item.setTranuser(busgetnotice.getTranuser());
                item.setTranusername(busgetnotice.getTranusername());
                item.setFlowaction(busgetnotice.getFlowaction());
                item.setFlowstatus(busgetnotice.getFlowstatus());
                item.setModifydate(new Date());
                item.setModifyserial(item.getModifyserial() + 1);
            }

            LabDao.SaveBusGetNotice(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                BusProc busproc = new BusProc();
                busproc.getDeal().setAction(DataAction.Create.getAction());
                busproc.setProcid(item.getProcid());
                LabDao.SaveBusProc(session, busproc);
            }

            // 明细操作
            BusGetNoticeDetail del = new BusGetNoticeDetail();
            del.setTranid(item.getTranid());
            del.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetNoticeDetail(session, del);

            for (BusGetNoticeDetail detail : details) {
                detail.setTranid(item.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusGetNoticeDetail(session, detail);
            }

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                String[] getusers = item.getGetuser().split(",");
                for (String getuser : getusers) {
                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(item.getTranid());
                    busTodo.setBusflow("GetNotice");
                    busTodo.setFlownode("create");
                    busTodo.setSenddate(new Date());
                    busTodo.setTranuser(getuser);
                    busTodo.setTrandept(item.getGetdept());
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(1));
                    busTodo.setTodotype(TodoType.Notice.getTodotype());
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            log.setTrandescdetail(str);
            PublicDao.SaveTranLog(session, log);

            // log.ActionToTran(item.getDeal().getAction());
            // log.setTrandesc("" + item.getTranid());
            // PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\",\"testedid\":\"" + item.getTestedid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusGetNoticeRecv(BusGetNotice item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String flowstatus = item.getFlowstatus();
            String backdesc = item.getBackdesc();
            item = LabDao.GetBusGetNotice(item);
            item.setFlowstatus(flowstatus);
            item.setBackdesc(backdesc);
            item.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGetNotice(session, item);

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getTranid());
            busTodo.setBusflow("GetNotice");
            busTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            PublicDao.SaveTranLog(session, log);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("" + item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusGetNotice Methods

    // region BusGet Methods

    public static void SaveBusGet(BusGet item, List<BusGetDetail> details, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // 更新受检企业信息
            BusTestedUnit bUnit = new BusTestedUnit();
            if (item.getDeal().getAction() != 16) {
                if (item.getTestedid().equals("") || item.getTestedid() == null) {
                    bUnit.setTestedname(item.getTestedname());
                    bUnit.setEnterlegal(item.getEnterlegal());
                    bUnit.setEntertele(item.getEntertele());
                    bUnit.setEnterpost(item.getEnterpost());
                    bUnit.setEntertype(item.getEntertype());
                    bUnit.setEnterscale(item.getEnterscale());
                    bUnit.setEnteraddr(item.getEnteraddr());
                    bUnit.setEnterfax(item.getEnterfax());
                    bUnit.getDeal().setAction(DataAction.Create.getAction());
                } else {
                    bUnit.setTestedid(item.getTestedid());
                    bUnit = BasDao.GetBusTestedUnit(bUnit);
                    bUnit.setTestedname(item.getTestedname());
                    bUnit.setEnterlegal(item.getEnterlegal());
                    bUnit.setEntertele(item.getEntertele());
                    bUnit.setEnterpost(item.getEnterpost());
                    bUnit.setEntertype(item.getEntertype());
                    bUnit.setEnterscale(item.getEnterscale());
                    bUnit.setEnteraddr(item.getEnteraddr());
                    bUnit.setEnterfax(item.getEnterfax());
                    bUnit.getDeal().setAction(DataAction.Modify.getAction());
                }
                BasDao.SaveBusTestedUnit(session, bUnit);
            }
            item.setTestedid(bUnit.getTestedid());

            // 更新通知单
            if (!item.getNoticeid().equals("") && item.getNoticeid() != null) {
                BusGetNotice bNotice = new BusGetNotice();
                bNotice.setTranid(item.getNoticeid());
                bNotice = LabDao.GetBusGetNotice(bNotice);
                bNotice.setTestedid(bUnit.getTestedid());
                bNotice.setTestedname(bUnit.getTestedname());
                bNotice.getDeal().setAction(DataAction.Modify.getAction());
                LabDao.SaveBusGetNotice(session, bNotice);
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setTranuser(ou.getUser().getUserid());
                item.setTranusername(ou.getUser().getUsername());
                item.setTrandate(new Date());
                item.setFlowaction("51");
                item.setFlowstatus("01");
                item.setSampleid(details.get(0).getSampleid());
            }

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGet busget = LabDao.GetBusGet(item);
                item.setTrandate(busget.getTrandate());
                item.setTranuser(busget.getTranuser());
                item.setTranusername(busget.getTranusername());
                item.setFlowaction(busget.getFlowaction());
                item.setFlowstatus(busget.getFlowstatus());
            }

            LabDao.SaveBusGet(session, item);

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getTranid());
                busTodo.setBusflow("GetAcc");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());

                // 将收样提醒提交给业务室收样人，不提交给农药科
                SysUserRole sRole = new SysUserRole();
                sRole.setRoleid(17);
                List<SysUserRole> sysUserRoles = UserDao.GetListSysRoleByRole(sRole);
                for (SysUserRole sysUserRole : sysUserRoles) {
                    busTodo.setTranuser(sysUserRole.getUserid());
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(1));
                    busTodo.setTodotype(TodoType.GetAcc.getTodotype());
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGetDetail delgetdetail = new BusGetDetail();
                delgetdetail.setTranid(item.getTranid());
                delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusGetDetail(session, delgetdetail);

                // 多样品流程
                if (item.getGettype().equals("09") || item.getGettype().equals("08")) {

                    List<String> sampleidss = new ArrayList<String>();
                    int samcount = 0;

                    for (BusGetDetail bDetail : details) {
                        if (sampleidss.toString().indexOf(bDetail.getSampleid()) == -1) {
                            if (samcount > 0) {
                                throw new Exception("‘" + bDetail.getSamplename() + "’检测项目与其他检测项目不是同类，请修改填写的检测项目信息！");
                            }

                            sampleidss.add(bDetail.getSampleid());
                            samcount++;
                        }
                    }
                }

                if (details.size() == 0) {
                    BusSampleParam delsampleparam = new BusSampleParam();
                    delsampleparam.setTranid(item.getTranid());
                    delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusSampleParam(session, delsampleparam);

                    BusCatalogParam delcatalogparam = new BusCatalogParam();
                    delcatalogparam.setTranid(item.getTranid());
                    delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusCatalogParam(session, delcatalogparam);
                } else {
                    for (BusGetDetail busgetdetail : details) {
                        String paramids = busgetdetail.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusSampleParam delparam = new BusSampleParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Delete.getAction());
                            LabDao.SaveBusSampleParam(session, delparam);
                        }
                    }

                    for (BusGetDetail busgetdetail : details) {

                        // 检测依据赋值
                        BasSample bSample = new BasSample();
                        bSample.setSampleid(busgetdetail.getSampleid());
                        bSample = BasDao.GetBasSample(session, bSample);
                        busgetdetail.setMainstandname(bSample.getMainstandname());
                        // 检测项目赋值
                        busgetdetail.setTestitems(busgetdetail.getParameternames());

                        busgetdetail.setTranid(item.getTranid());
                        busgetdetail.setTesttype(item.getTesttype());
                        busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusGetDetail(session, busgetdetail);

                        String paramids = busgetdetail.getParameterids();
                        String sampleid = busgetdetail.getSampleid();

                        if (!paramids.equals("") && paramids != null) {
                            String[] paramnames = paramids.split(",");
                            for (String params : paramnames) {
                                String[] param = params.split(":");
                                BusSampleParam bussampleparam = new BusSampleParam();
                                bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                                bussampleparam.setTranid(item.getTranid());
                                bussampleparam.setSampleid(sampleid);
                                bussampleparam.setParameterid(param[0]);
                                bussampleparam.setParametername(param[1]);
                                bussampleparam.setTeststandard(param[2]);
                                bussampleparam.setTeststandardcode(param[3]);
                                bussampleparam.setJudgestandard(param[4]);
                                bussampleparam.setJudgestandardcode(param[5]);
                                bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                                LabDao.SaveBusSampleParam(session, bussampleparam);
                            }
                        }
                    }
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
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusGetAudit(BusGet item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String flowstatus = item.getFlowstatus();
            item = LabDao.GetBusGet(item);
            item.setFlowstatus(flowstatus);
            item.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGet(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveConsignGet(BusGet item, List<BusGetDetail> details, OnlineUser ou, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            String str = "";
            // 编辑委托单位
            BusTestedUnit bUnit = new BusTestedUnit();
            if (item.getTestedid().equals("")) {
                bUnit.setTestedname(item.getTestedname());
                bUnit.setEnterlegal(item.getTesteduser());
                bUnit.setEntertele(item.getEntertele());
                bUnit.setEnterpost(item.getEnterpost());
                bUnit.setComtype(item.getTesttype());
                bUnit.getDeal().setAction(DataAction.Create.getAction());
            } else {
                bUnit.setTestedid(item.getTestedid());
                bUnit = BasDao.GetBusTestedUnit(bUnit);
                str = LogDetailsUtils.BusTestedUnitDiff(bUnit, item);
                bUnit.setTestedname(item.getTestedname());
                bUnit.setEnterlegal(item.getTesteduser());
                bUnit.setEntertele(item.getEntertele());
                bUnit.setEnterpost(item.getEnterpost());
                bUnit.getDeal().setAction(DataAction.Modify.getAction());
            }

            BasDao.SaveBusTestedUnit(session, bUnit);
            item.setTestedid(bUnit.getTestedid());

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setSampleid(details.get(0).getSampleid());
                item.setTranuser(ou.getUser().getUserid());
                item.setTranusername(ou.getUser().getUsername());
                item.setTrandate(new Date());
                item.setFlowaction("52");
                item.setFlowstatus("01");
                item.setTesttype("01");
                item.setGettype("10");
                item.setTrandate(new Date());
            }
            BusGet oldbusget = null;
            BusGet nbusget = item;
            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                oldbusget = LabDao.GetBusGet(item);
                item.setFlowaction(oldbusget.getFlowaction());
                item.setFlowstatus(oldbusget.getFlowstatus());
                item.setTesttype(oldbusget.getTesttype());
                item.setGettype(oldbusget.getGettype());
                item.setTranuser(oldbusget.getTranuser());
                item.setTranusername(oldbusget.getTranusername());
                item.setTrandate(oldbusget.getTrandate());
            }

            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getTranid());
                busTodo.setBusflow("GetAcc");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                busTodo.setTranuser(ou.getUser().getUserid());
                busTodo.setTrandept(ou.getUser().getDeptid());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype(TodoType.GetAcc.getTodotype());
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
            }

            LabDao.SaveBusGet(session, item);
            session.commit();
            if (oldbusget != null) {
                BusGet newbusget = LabDao.GetBusGet(nbusget);
                // str=LogDetailsUtils.ContrastObj(oldbusget,newbusget);
                str = LogDetailsUtils.BusGetDiff(oldbusget, newbusget);
            }

            List<BusGetDetail> olddetaillist = null;
            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGetDetail oldgetdetail = new BusGetDetail();
                oldgetdetail.setTranid(item.getTranid());
                olddetaillist = LabDao.GetListBusGetDetail(oldgetdetail);
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGetDetail delgetdetail = new BusGetDetail();
                delgetdetail.setTranid(item.getTranid());
                delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusGetDetail(session, delgetdetail);

                if (details.size() == 0) {
                    BusSampleParam delsampleparam = new BusSampleParam();
                    delsampleparam.setTranid(item.getTranid());
                    delsampleparam.getDeal().setAction(DataAction.Deal.getAction());
                    LabDao.SaveBusSampleParam(session, delsampleparam);

                    BusCatalogParam delcatalogparam = new BusCatalogParam();
                    delcatalogparam.setTranid(item.getTranid());
                    delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusCatalogParam(session, delcatalogparam);
                } else {
                    for (BusGetDetail busgetdetail : details) {
                        String paramids = busgetdetail.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusSampleParam delparam = new BusSampleParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Deal.getAction());
                            LabDao.SaveBusSampleParam(session, delparam);
                        } else {
                            throw new Exception("请在样品明细中选择当前样品对应的检测参数！！");
                        }
                    }
                    for (BusGetDetail busgetdetail : details) {

                        // 检测依据赋值
                        BasSample bSample = new BasSample();
                        bSample.setSampleid(busgetdetail.getSampleid());
                        bSample = BasDao.GetBasSample(session, bSample);
                        busgetdetail.setMainstandname(bSample.getMainstandname());
                        // 检测项目赋值
                        busgetdetail.setTestitems(busgetdetail.getParameternames());
                        busgetdetail.setParameterids(busgetdetail.getParameterids());

                        busgetdetail.setTranid(item.getTranid());
                        busgetdetail.setTesttype(item.getTesttype());
                        busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusGetDetail(session, busgetdetail);

                        session.commit();
                        BusGetDetail newgetdetail = new BusGetDetail();
                        newgetdetail.setTranid(busgetdetail.getTranid());
                        List<BusGetDetail> newdetaillist = LabDao.GetListBusGetDetail(newgetdetail);
                        String detailstr = LogDetailsUtils.compareBusGetDetail(olddetaillist, newdetaillist);
                        // String
                        // detailstr=LogDetailsUtils.compareMapBusGetDetail(olddetaillist,newdetaillist);
                        if (!detailstr.equals("") && detailstr != null) {
                            str = str + "详细页面:" + detailstr;
                        }

                        String paramids = busgetdetail.getParameterids();
                        String sampleid = busgetdetail.getSampleid();

                        if (!paramids.equals("") && paramids != null) {
                            String[] paramnames = paramids.split(";");
                            for (String params : paramnames) {
                                String[] param = params.split(":");

                                BusSampleParam bussampleparam = new BusSampleParam();

                                bussampleparam.setSampleid(sampleid);
                                bussampleparam.setParameterid(param[0]);
                                bussampleparam.setTranid(item.getTranid());
                                bussampleparam.setSamplecode(busgetdetail.getSamplecode());

                                if (param.length == 8) {
                                    bussampleparam.setParametername(param[1]);
                                    bussampleparam.setTeststandard(param[2]);
                                    bussampleparam.setTeststandardcode(param[3]);
                                    bussampleparam.setJudgestandard(param[4]);
                                    bussampleparam.setJudgestandardcode(param[5]);
                                    bussampleparam.setTestjudge(param[6]);
                                    bussampleparam.setStandvalue(param[7]);
                                }

                                bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                                LabDao.SaveBusSampleParam(session, bussampleparam);
                            }
                        }
                    }
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            log.setTrandescdetail(str);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\",\"testedid\":\"" + item.getTestedid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 批次委托
    public static void SaveManyConsign(BusGet item, List<BusGetDetail> details, OnlineUser ou, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setSampleid(details.get(0).getSampleid());
            item.setTranuser(ou.getUser().getUserid());
            item.setTranusername(ou.getUser().getUsername());
            item.setTrandate(new Date());
            item.setFlowaction("52");
            item.setFlowstatus("01");
            item.setTesttype("01");
            item.setGettype("10");
            item.setTrandate(new Date());

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getTranid());
            busTodo.setBusflow("GetAcc");
            busTodo.setFlownode("create");
            busTodo.setSenddate(new Date());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.setTrandept(ou.getUser().getDeptid());
            busTodo.setIsnowflow(true);
            busTodo.setSampleid(String.valueOf(1));
            busTodo.setTodotype(TodoType.GetAcc.getTodotype());
            busTodo.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            LabDao.SaveBusGet(session, item);

            if (item.getDeal().getAction() == DataAction.Check.getAction()) {
                BusGetDetail delgetdetail = new BusGetDetail();
                delgetdetail.setTranid(item.getTranid());
                delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusGetDetail(session, delgetdetail);

                if (details.size() == 0) {
                    BusSampleParam delsampleparam = new BusSampleParam();
                    delsampleparam.setTranid(item.getTranid());
                    delsampleparam.getDeal().setAction(DataAction.Deal.getAction());
                    LabDao.SaveBusSampleParam(session, delsampleparam);

                    BusCatalogParam delcatalogparam = new BusCatalogParam();
                    delcatalogparam.setTranid(item.getTranid());
                    delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusCatalogParam(session, delcatalogparam);
                } else {
                    for (BusGetDetail busgetdetail : details) {
                        String paramids = busgetdetail.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusSampleParam delparam = new BusSampleParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Deal.getAction());
                            LabDao.SaveBusSampleParam(session, delparam);
                        } else {
                            throw new Exception("请在样品明细中选择当前样品对应的检测参数！！");
                        }
                    }
                    for (BusGetDetail busgetdetail : details) {

                        // 检测依据赋值
                        BasSample bSample = new BasSample();
                        bSample.setSampleid(busgetdetail.getSampleid());
                        bSample = BasDao.GetBasSample(session, bSample);
                        busgetdetail.setMainstandname(bSample.getMainstandname());
                        // 检测项目赋值
                        busgetdetail.setTestitems(busgetdetail.getParameternames());
                        busgetdetail.setParameterids(busgetdetail.getParameterids());

                        busgetdetail.setTranid(item.getTranid());
                        busgetdetail.setTesttype(item.getTesttype());
                        busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusGetDetail(session, busgetdetail);

                        session.commit();

                        String paramids = busgetdetail.getParameterids();
                        String sampleid = busgetdetail.getSampleid();

                        if (!paramids.equals("") && paramids != null) {
                            String[] paramnames = paramids.split(";");
                            for (String params : paramnames) {
                                String[] param = params.split(":");

                                BusSampleParam bussampleparam = new BusSampleParam();

                                bussampleparam.setSampleid(sampleid);
                                bussampleparam.setParameterid(param[0]);
                                bussampleparam.setTranid(item.getTranid());
                                bussampleparam.setSamplecode(busgetdetail.getSamplecode());

                                if (param.length == 8) {
                                    bussampleparam.setParametername(param[1]);
                                    bussampleparam.setTeststandard(param[2]);
                                    bussampleparam.setTeststandardcode(param[3]);
                                    bussampleparam.setJudgestandard(param[4]);
                                    bussampleparam.setJudgestandardcode(param[5]);
                                    bussampleparam.setTestjudge(param[6]);
                                    bussampleparam.setStandvalue(param[7]);
                                }

                                bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                                LabDao.SaveBusSampleParam(session, bussampleparam);
                            }
                        }
                    }
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\",\"testedid\":\"" + item.getTestedid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusGet Methods

    // region BusConsign Methods

    public static void SaveBusConsign(BusConsign item, List<BusConsignSample> details, OnlineUser ou, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.setFlowaction("52");
                item.setFlowstatus("01");
                item.setTrandate(new Date());
            }

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusConsign busconsign = LabDao.GetBusConsign(item);
                item.setFlowaction(busconsign.getFlowaction());
                item.setFlowstatus(busconsign.getFlowstatus());
            }

            LabDao.SaveBusConsign(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusConsignSample delsample = new BusConsignSample();
                delsample.setTranid(item.getTranid());
                delsample.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusConsignSample(session, delsample);

                if (details.size() == 0) {
                    BusConsignParam delparam = new BusConsignParam();
                    delparam.setTranid(item.getTranid());
                    delparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusConsignParam(session, delparam);
                } else {
                    for (BusConsignSample busconsignsample : details) {
                        String paramids = busconsignsample.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusConsignParam delparam = new BusConsignParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Delete.getAction());
                            LabDao.SaveBusConsignParam(session, delparam);
                        }
                    }
                    for (BusConsignSample busconsignsample : details) {
                        busconsignsample.setTranid(item.getTranid());
                        busconsignsample.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusConsignSample(session, busconsignsample);
                        String paramids = busconsignsample.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            String[] paramnames = paramids.split(",");
                            for (String params : paramnames) {
                                String[] param = params.split(":");
                                BusConsignParam busconsignparam = new BusConsignParam();
                                busconsignparam.setTranid(item.getTranid());
                                busconsignparam.setSampleid(busconsignsample.getSampleid());
                                busconsignparam.setParameterid(param[0]);
                                busconsignparam.setParametername(param[1]);
                                busconsignparam.setTeststandard(param[2]);
                                busconsignparam.setTeststandardname(param[3]);
                                busconsignparam.setJudgestandard(param[4]);
                                busconsignparam.setJudgestandardname(param[5]);
                                busconsignparam.getDeal().setAction(DataAction.Create.getAction());
                                LabDao.SaveBusConsignParam(session, busconsignparam);
                            }
                        }
                    }
                }
            }

            // 委托单提交时，将委托单样品的数据放入任务单
            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {
                // 委托样品信息
                for (BusConsignSample busconsignsample : details) {
                    String sampleid = busconsignsample.getSampleid();

                    BusConsignParam busconsignparam = new BusConsignParam();
                    busconsignparam.setTranid(item.getTranid());
                    busconsignparam.setSampleid(busconsignsample.getSampleid());
                    List<BusConsignParam> busconsignparams = LabDao.GetListBusConsignParam(session, busconsignparam);
                    String parameterids = "";
                    for (BusConsignParam bcp : busconsignparams) {
                        if (parameterids.equals("")) {
                            parameterids = bcp.getParameterid();
                        } else {
                            parameterids = parameterids + "," + bcp.getParameterid();
                        }
                    }
                    BasLabParameter blp = new BasLabParameter();
                    blp.setSampleid(sampleid);
                    blp.setParameterids(parameterids);

                    // 查询能检测委托样品，且能检测相关参数的检测室
                    List<BasLabParameter> blps = BasDao.GetListBasLabParameter(blp);
                    for (BasLabParameter baslabparameter : blps) {
                        SysUser sysuser = new SysUser();
                        sysuser.setDeptid(baslabparameter.getLabid());
                        // 根据检测室获取检测室负责人
                        sysuser = LabDao.GetLabLeader(sysuser);
                        if (sysuser == null) {
                            sysuser = new SysUser();
                        }
                        // 将委托单的信息及样品信息放入任务单
                        BusTask bustask = new BusTask();
                        bustask.getDeal().setAction(DataAction.Create.getAction());
                        bustask.setSamplecode(busconsignsample.getSamplecode());
                        bustask.setSampleid(busconsignsample.getSampleid());
                        bustask.setSamplename(busconsignsample.getSamplename());
                        bustask.setSamplestand(busconsignsample.getSamplestand());
                        bustask.setSamplestatus(busconsignsample.getSamplestatus());
                        bustask.setSenduser(ou.getUser().getUserid());
                        bustask.setSendusername(ou.getUser().getUsername());
                        bustask.setSenddate(new Date());
                        bustask.setLabid(sysuser.getDeptid());
                        bustask.setLabuser(sysuser.getUserid());
                        bustask.setLabusername(sysuser.getUsername());
                        bustask.setFlowaction("53");
                        bustask.setFlowstatus("01");
                        LabDao.SaveBusTask(session, bustask);

                        BusConsignParam bcp = new BusConsignParam();
                        bcp.setLabid(baslabparameter.getLabid());
                        bcp.setTranid(item.getTranid());
                        bcp.setSampleid(busconsignsample.getSampleid());

                        List<BusConsignParam> bcps = LabDao.GetBusConsignParamByLab(bcp);

                        for (BusConsignParam consignparam : bcps) {
                            BusTaskTest bustasktest = new BusTaskTest();
                            bustasktest.getDeal().setAction(DataAction.Create.getAction());
                            bustasktest.setSampleid(consignparam.getSampleid());
                            bustasktest.setTaskid(bustask.getTaskid());
                            bustasktest.setParameterid(consignparam.getParameterid());
                            bustasktest.setIsok(false);
                            bustasktest.setTeststandard(consignparam.getTeststandard());
                            bustasktest.setJudgestandard(consignparam.getJudgestandard());
                            LabDao.SaveBusTaskTest(session, bustasktest);
                        }
                    }
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"tranid\":\"" + item.getTranid() + "\"}");
            rtv.setBean(true);
            // session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusConsign Methods

    // region BusTask Methods

    public static void SaveBusTask(BusTask item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            LabDao.SaveBusTask(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskForSamples(BusTask item, List<BusTaskAttach> details, OnlineUser ou, ReturnValue rtv,
            String signerpassword, String approveusers, TranLog log) {
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
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            BusRecordSamples brsample = new BusRecordSamples();
            brsample.setTranuser(ou.getUser().getUserid());
            brsample.setTranusername(ou.getUser().getUsername());
            brsample.setTrandate(new Date());
            brsample.setRecordstatus(item.getRecordstatus());
            brsample.setTaskid(item.getTaskid());
            brsample.setApproveuser(approveusers);
            LabDao.SubmitBusRecordSamples(session, brsample);

            BusTaskAttach dbta = new BusTaskAttach();
            dbta.setTranid(item.getTaskid());
            dbta.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskAttach(session, dbta);

            for (BusTaskAttach bustaskattach : details) {
                bustaskattach.setTranid(item.getTaskid());
                bustaskattach.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusTaskAttach(session, bustaskattach);
            }
            // 修改任务单状态
            item.setFlowstatus("92");
            item.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusTask(session, item);
            // 删除原始记录审批提醒
            BusTodo bt = new BusTodo();
            bt.setTranid(item.getTaskid());
            bt.setTranuser(ou.getUser().getUserid());
            bt.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, bt);

            String[] sysUsers = approveusers.split(",");

            boolean isuser = false;
            for (String sysUser : sysUsers) {
                if (!sysUser.equals(ou.getUser().getUserid())) {
                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(item.getTaskid());
                    busTodo.setBusflow("RecordApprove");
                    busTodo.setFlownode("create");
                    busTodo.setSenddate(new Date());
                    busTodo.setTranuser(sysUser);
                    busTodo.setIsnowflow(true);
                    busTodo.setSampleid(String.valueOf(15));
                    busTodo.setTodotype("27");
                    // 判定是否同一个提醒
                    BusTodo bTodo = new BusTodo();
                    bTodo = FlowDao.GetBusTodo(session, busTodo);

                    if (bTodo == null) {
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    } else {
                        busTodo.getDeal().setAction(DataAction.Check.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    }

                    isuser = true;
                }
            }

            if (!isuser) {
                throw new Exception("指定复核人有误，请重新选择！");
            }
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskSingle(BusTask item, List<BusSampleParam> details, OnlineUser ou, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String sampleid = item.getSampleid();
            String samplecode = item.getSamplecode();
            String samplename = item.getSamplename();
            Date nowdate = new Date();

            BusAccSample bas = new BusAccSample();
            bas.setSamplecode(samplecode);
            bas.setSampletype(item.getSampletype());
            List<BusAccSample> bass = LabDao.GetBusAccSampleBySampleCode(bas);
            for (BusAccSample busaccsample : bass) {
                if (busaccsample.isIssend()) {
                    throw new Exception("当前样品已下达任务单");
                }
                busaccsample.setIssend(true);
                busaccsample.setFlowstatus("84");
                busaccsample.getDeal().setAction(DataAction.Modify.getAction());
                LabDao.SaveBusAccSample(session, busaccsample);
            }

            // 删除申请备份样品提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(samplecode.substring(0, samplecode.length() - 2));
            bTodo.setBusflow("BackUpPass");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            // 获取检测项目所有编号
            String parameterids = "";
            String parameternames = "";
            for (BusSampleParam bsp : details) {
                if (parameterids.equals("")) {
                    parameterids = bsp.getParameterid();
                    parameternames = bsp.getParametername();
                } else {
                    parameterids = parameterids + "," + bsp.getParameterid();
                    parameternames = parameternames + "，" + bsp.getParametername();
                }
            }
            // 获取检测项目所有检测参数填入到委托书中
            String busgetparameter = "";
            int flag = 0;
            for (BusSampleParam bsps : details) {
                flag = flag + 1;
                if (flag == details.size()) {
                    busgetparameter = busgetparameter + bsps.getParameterid() + ":" + bsps.getParametername() + ":"
                            + bsps.getTeststandard() + ":" + bsps.getTeststandardcode() + ":" + bsps.getJudgestandard()
                            + ":" + bsps.getJudgestandardcode() + ":" + bsps.getTestjudge() + ":"
                            + bsps.getStandvalue();
                } else {
                    busgetparameter = busgetparameter + bsps.getParameterid() + ":" + bsps.getParametername() + ":"
                            + bsps.getTeststandard() + ":" + bsps.getTeststandardcode() + ":" + bsps.getJudgestandard()
                            + ":" + bsps.getJudgestandardcode() + ":" + bsps.getTestjudge() + ":" + bsps.getStandvalue()
                            + ";";
                }

            }

            BusGetDetail bDetail = new BusGetDetail();
            bDetail.setSamplecode(item.getSamplecode());
            bDetail = LabDao.GetBusGetDetailBySampleCode(bDetail);
            bDetail.getDeal().setAction(DataAction.Deal.getAction());
            bDetail.setMainstandname(item.getTeststandardname());
            bDetail.setSamplestand(item.getSamplestand());
            bDetail.setTestitems(parameternames);
            bDetail.setParameterids(busgetparameter);
            LabDao.SaveBusGetDetail(session, bDetail);

            BasLabParameter blp = new BasLabParameter();
            blp.setParameterids(parameterids);
            String[] paramnames = parameternames.split("，");

            // 判定检测参数是否全部分配到检测室
            List<BasLabParameter> labParameters = BasDao.GetListBasLabParameter(blp);

            for (String parametername : paramnames) {
                boolean isparam = false;
                for (BasLabParameter baslabparameter : labParameters) {
                    if (parametername.equals(baslabparameter.getParametername())) {
                        isparam = true;
                        continue;
                    }
                }

                if (!isparam) {
                    throw new Exception("'" + parametername + "'没有分配检测室");
                }
            }

            // 查询能检测委托样品，且能检测相关参数的检测室
            List<BasLabParameter> blps = BasDao.GetListBasLabParameterForDept(blp);
            for (BasLabParameter baslabparameter : blps) {
                SysUser sysuser = new SysUser();
                sysuser.setDeptid(baslabparameter.getLabid());
                // 根据检测室获取检测室负责人
                sysuser = LabDao.GetLabLeader(sysuser);
                if (sysuser == null) {
                    sysuser = new SysUser();
                }
                // 将委托单的信息及样品信息放入任务单
                BusTask bustask = new BusTask();
                bustask.getDeal().setAction(DataAction.Create.getAction());
                bustask.setSamplecode(samplecode);
                bustask.setSampleid(sampleid);
                bustask.setAccsampleid(item.getAccsampleid());
                bustask.setSamplename(samplename);
                bustask.setSamplestand(item.getSamplestand());
                bustask.setSamplestatus(item.getSamplestatus());
                bustask.setTestprop(item.getTestprop());
                bustask.setReqdate(item.getReqdate());
                bustask.setTeststandardname(item.getTeststandardname());
                bustask.setSenduser(ou.getUser().getUserid());
                bustask.setSendusername(ou.getUser().getUsername());
                bustask.setSenddate(item.getSenddate());
                bustask.setLabid(sysuser.getDeptid());
                bustask.setLabuser(sysuser.getUserid());
                bustask.setLabusername(sysuser.getUsername());
                bustask.setFlowaction("53");
                bustask.setFlowstatus("84");
                LabDao.SaveBusTask(session, bustask);

                BusSampleParam dbsp = new BusSampleParam();
                dbsp.getDeal().setAction(DataAction.Delete.getAction());
                dbsp.setSamplecode(item.getSamplecode());
                LabDao.SaveBusSampleParam(session, dbsp);

                for (BusSampleParam bsp : details) {
                    bsp.getDeal().setAction(DataAction.Create.getAction());
                    bsp.setTranid(item.getTranid());
                    bsp.setSamplecode(samplecode);
                    bsp.setSampleid(sampleid);
                    LabDao.SaveBusSampleParam(session, bsp);
                }
                session.commit();

                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(bustask.getTaskid());
                busTodo.setBusflow("BusTaskAcc");
                busTodo.setFlownode("create");
                busTodo.setSenddate(nowdate);
                busTodo.setTranuser(bustask.getLabuser());
                busTodo.setTrandept(bustask.getLabid());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype(TodoType.TaskAcc.getTodotype());
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);

                BusTodo bt = new BusTodo();
                bt.setTranid(bustask.getTaskid() + bustask.getLabuser());
                bt.setBusflow("BusTaskDeadline");
                bt.setFlownode("create");
                bt.setSenddate(bustask.getReqdate());
                bt.setTranuser(bustask.getLabuser());
                bt.setTrandept(bustask.getLabid());
                bt.setIsnowflow(true);
                // bt.setSampleid(String.valueOf(1));
                bt.setTodotype("13");
                bt.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, bt);

                BasMainParameter bmp = new BasMainParameter();
                bmp.setLabid(baslabparameter.getLabid());
                bmp.setSamplecode(samplecode);

                List<BasMainParameter> bmps = LabDao.GetBusSampleParamByLab(bmp);

                for (BasMainParameter mainparam : bmps) {
                    BusTaskTest bustasktest = new BusTaskTest();
                    bustasktest.getDeal().setAction(DataAction.Create.getAction());
                    bustasktest.setSampleid(sampleid);
                    bustasktest.setTaskid(bustask.getTaskid());
                    bustasktest.setParameterid(mainparam.getParameterid());
                    bustasktest.setIsok(false);
                    bustasktest.setTestjudge(mainparam.getTestjudge());
                    bustasktest.setStandvalue(mainparam.getStandvalue());
                    bustasktest.setTeststandard(mainparam.getTeststandard());
                    bustasktest.setJudgestandard(mainparam.getJudgestandard());
                    bustasktest.setParamunit(mainparam.getParamunit());
                    bustasktest.setDeteclimit(mainparam.getDeteclimit());
                    bustasktest.setParameterorder(mainparam.getParameterorder());
                    LabDao.SaveBusTaskTest(session, bustasktest);
                }
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc(item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("下达成功！");
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\",\"issend\":\"true\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "不能下达："));
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskBatch(BusTask item, List<BusTaskFile> details, List<BusSampleParam> partsdetails,
            OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            Date nowdate = new Date();

            // 获取检测项目所有编号
            String parameterids = "";
            String parameternames = "";
            for (BusSampleParam bsp : partsdetails) {
                if (parameterids.equals("")) {
                    parameterids = bsp.getParameterid();
                    parameternames = bsp.getParametername();
                } else {
                    parameterids = parameterids + "," + bsp.getParameterid();
                    parameternames = parameternames + "," + bsp.getParametername();
                }
            }

            BasLabParameter blp = new BasLabParameter();
            blp.setParameterids(parameterids);
            String[] paramnames = parameternames.split(",");

            // 判定检测参数是否全部分配到检测室
            List<BasLabParameter> labParameters = BasDao.GetListBasLabParameter(blp);

            for (String parametername : paramnames) {
                boolean isparam = false;
                for (BasLabParameter baslabparameter : labParameters) {
                    if (parametername.equals(baslabparameter.getParametername())) {
                        isparam = true;
                        continue;
                    }
                }

                if (!isparam) {
                    throw new Exception("'" + parametername + "'没有分配检测室");
                }
            }

            // 查询能检测委托样品，且能检测相关参数的检测室
            List<BasLabParameter> blps = BasDao.GetListBasLabParameterForDept(blp);

            // SysUser sysuser = new SysUser();

            List<SysUser> sUsers = new ArrayList<SysUser>();

            if (details.size() > 0) {
                for (BusTaskFile bFile : details) {
                    SysUser sysuser = new SysUser();

                    sysuser.setDeptid(bFile.getLabid());
                    // 根据检测室获取检测室负责人
                    sysuser = LabDao.GetLabLeader(sysuser);
                    if (sysuser == null) {
                        sysuser = new SysUser();
                    }

                    sUsers.add(sysuser);
                }
            } else {
                for (BasLabParameter baslabparameter : blps) {
                    SysUser sysuser = new SysUser();

                    sysuser.setDeptid(baslabparameter.getLabid());
                    // 根据检测室获取检测室负责人
                    sysuser = LabDao.GetLabLeader(sysuser);
                    if (sysuser == null) {
                        sysuser = new SysUser();
                    }

                    sUsers.add(sysuser);
                }
            }

            for (SysUser sUser : sUsers) {
                // 将多个样品的信息及样品信息放入任务单
                BusTask bustask = new BusTask();
                bustask.getDeal().setAction(DataAction.Create.getAction());
                bustask.setSamplecode(item.getSamplecode());
                bustask.setSampleid(item.getSampleid());
                bustask.setAccsampleid(item.getAccsampleid());
                bustask.setSamplename(item.getSamplename());
                bustask.setSamplestand(item.getSamplestand());
                bustask.setSamplestatus(item.getSamplestatus());
                bustask.setTestprop(item.getTestprop());
                bustask.setReqdate(item.getReqdate());
                bustask.setTeststandardname(item.getTeststandardname());
                bustask.setSenduser(ou.getUser().getUserid());
                bustask.setSendusername(ou.getUser().getUsername());
                bustask.setSenddate(item.getSenddate());
                bustask.setLabid(sUser.getDeptid());
                bustask.setLabuser(sUser.getUserid());
                bustask.setLabusername(sUser.getUsername());
                bustask.setFlowaction("53");
                bustask.setFlowstatus("84");
                LabDao.SaveBusTask(session, bustask);

                BusSampleParam dbsp = new BusSampleParam();
                dbsp.getDeal().setAction(DataAction.Delete.getAction());
                dbsp.setSamplecode(item.getSampleid());
                LabDao.SaveBusSampleParam(session, dbsp);

                for (BusSampleParam bsp : partsdetails) {
                    bsp.getDeal().setAction(DataAction.Create.getAction());
                    bsp.setTranid(item.getTranid());
                    bsp.setSamplecode(item.getSampleid());
                    bsp.setSampleid(item.getSampleid());
                    LabDao.SaveBusSampleParam(session, bsp);
                }
                session.commit();

                BasMainParameter bmp = new BasMainParameter();
                bmp.setLabid(sUser.getDeptid());
                bmp.setSamplecode(item.getSampleid());
                bmp.setSampleid(item.getSampleid());

                List<BasMainParameter> bmps = LabDao.GetBasSampleReplaceByLab(bmp);

                for (BasMainParameter mainparam : bmps) {
                    BusTaskTest bustasktest = new BusTaskTest();
                    bustasktest.getDeal().setAction(DataAction.Create.getAction());
                    bustasktest.setSampleid(item.getSampleid());
                    bustasktest.setTaskid(bustask.getTaskid());
                    bustasktest.setParameterid(mainparam.getParameterid());
                    bustasktest.setIsok(false);
                    bustasktest.setTestjudge(mainparam.getTestjudge());
                    bustasktest.setStandvalue(mainparam.getStandvalue());
                    bustasktest.setTeststandard(mainparam.getTeststandard());
                    bustasktest.setJudgestandard(mainparam.getJudgestandard());
                    bustasktest.setParamunit(mainparam.getParamunit());
                    LabDao.SaveBusTaskTest(session, bustasktest);
                }

                if (details.size() > 0) {
                    for (BusTaskFile bFile : details) {
                        if (bFile.getLabid().equals(sUser.getDeptid())) {
                            String taskid = bustask.getTaskid();
                            String tranid = bustask.getAccsampleid();
                            bFile.setTranid(tranid);
                            bFile.setTaskid(taskid);
                            bFile.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusTaskFile(session, bFile);
                        }
                    }
                }

                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(bustask.getTaskid());
                busTodo.setBusflow("BusTaskAcc");
                busTodo.setFlownode("create");
                busTodo.setSenddate(nowdate);
                busTodo.setTranuser(bustask.getLabuser());
                busTodo.setTrandept(bustask.getLabid());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype(TodoType.TaskAcc.getTodotype());
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
            }

            BusAccSample bas = new BusAccSample();
            bas.setSamplecode(item.getSamplecode());
            bas.setSampletype(item.getSampletype());
            bas.setTranid(item.getTranid());

            List<BusAccSample> bass = LabDao.GetBusAccSampleBySampleCode(bas);
            for (BusAccSample busaccsample : bass) {
                if (busaccsample.isIssend()) {
                    throw new Exception("当前样品的任务单已下达...");
                }
                busaccsample.setIssend(true);
                busaccsample.setFlowstatus("84");
                busaccsample.getDeal().setAction(DataAction.Modify.getAction());
                LabDao.SaveBusAccSample(session, busaccsample);
            }

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc(item.getSamplename());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("样品任务单下达成功！");
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 二次分配
    public static void SaveBusTaskAllot(BusTask item, List<BusTaskTester> details, List<BusAccFile> accFiles,
            OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);
        SqlSession session = DBUtils.getFactory();
        try {
            ErrorMsg errmsg = new ErrorMsg();
            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            // 对任务单状态调整
            BusTask bustask = LabDao.GetBusTask(item);
            bustask.getDeal().setAction(DataAction.Deal.getAction());
            bustask.setFlowstatus("81");
            LabDao.SaveBusTask(session, bustask);

            // 删除与任务单挂钩的相关检测员检测参数的数据
            BusTaskTester dbtt = new BusTaskTester();
            dbtt.setTaskid(item.getTaskid());
            dbtt.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskTester(session, dbtt);// 如果分配任务需要修改，在该存储过程中删除之前存储的数据

            BusGetDetail bDetail = new BusGetDetail();
            bDetail.setTaskid(bustask.getTaskid());
            List<BusGetDetail> bDetails = LabDao.GetListBusGetDetailByTask(session, bDetail);

            // 多样品处理
            if (item.getSamplecode().equals("多样品样品编号-A") || item.getSamplecode().equals("多样品样品编号-B")) {
                if (bDetails.size() > 0) {
                    for (BusGetDetail bGetDetail : bDetails) {
                        bustask.setSampleid(bGetDetail.getSampleid());
                        String samplecode = item.getSamplecode();
                        if (samplecode.contains("A")) {
                            bustask.setSamplecode(bGetDetail.getSamplecode() + "-A");
                        } else {
                            bustask.setSamplecode(bGetDetail.getSamplecode() + "-B");
                        }

                        bustask.setSamplename(bGetDetail.getSamplename());

                        if (details.size() > 0) {
                            bustask.setSamplecount(details.size());
                        } else {
                            bustask.setSamplecount(accFiles.size());
                        }

                        // bustask.setSamplestatus(bustask.getSamplestate());

                        bustask.setSamplestatus(bustask.getSamplestatus());
                        bustask.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusTaskSingle(session, bustask);
                    }
                }
            } else {
                bustask.setSampleid(bustask.getSampleid());
                bustask.setSamplecode(bustask.getSamplecode());
                bustask.setSamplename(bustask.getSamplename());

                if (details.size() > 0) {
                    bustask.setSamplecount(details.size());
                } else {
                    bustask.setSamplecount(accFiles.size());
                }

                // bustask.setSamplestatus(bustask.getSamplestate());

                bustask.setSamplestatus(bustask.getSamplestatus());
                bustask.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusTaskSingle(session, bustask);
            }

            // 查询当前任务单的试验样品
            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(item.getTaskid());
            bts.setTranid(bustask.getTranid());
            // bts.setSamplecode(bustask.getSamplecode());
            String tranuser = "";

            // 多样品流程处理
            if (item.getSamplecode().equals("多样品样品编号-A") || item.getSamplecode().equals("多样品样品编号-B")) {
                for (BusGetDetail bDetail2 : bDetails) {

                    if (item.getSamplecode().contains("A")) {
                        bts.setSamplecode(bDetail2.getSamplecode() + "-A");
                    } else {
                        bts.setSamplecode(bDetail2.getSamplecode() + "-B");
                    }

                    // 获取当前任务单的样品信息,t_bus_task_sample在SaveBusTaskSingle中新增的
                    List<BusTaskSample> samples = LabDao.GetListBusTaskSample(session, bts);
                    for (int j = 0; j < samples.size(); j++) {
                        BusTaskSample bustasksample = samples.get(j);
                        String sampletran = bustasksample.getSampletran();

                        // 新增多样品明细信息
                        BusAccFile bFile = accFiles.get(j);
                        String params = bFile.getParameterid();
                        String[] parameterids = params.split(",");
                        for (String parameterid : parameterids) {
                            BusTaskTester btt = new BusTaskTester();
                            btt.setTranid(bustask.getTranid());
                            btt.setTaskid(item.getTaskid());
                            btt.setTestuser(bFile.getTestuserfile());
                            btt.setTestusername(bFile.getTestuserfilename());
                            btt.setParameterid(parameterid);
                            btt.setSampletran(sampletran);
                            btt.setIschoice(true);
                            btt.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusTaskTester(session, btt);
                            tranuser = bFile.getTestuserfile();
                        }

                        tranuser = bFile.getTestuserfile();
                        bFile.setSampletran(sampletran);
                        bFile.setTranid(bustask.getTaskid());
                        // bFile.setParameterid(bFile.getParameterid());
                        bFile.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusAccFile(session, bFile);

                        // 做试验提醒
                        BusTodo busTodo = new BusTodo();
                        busTodo.setTranid(sampletran);
                        busTodo.setBusflow("LabReady");
                        busTodo.setFlownode("create");
                        busTodo.setSenddate(new Date());
                        busTodo.setTranuser(tranuser);
                        busTodo.setTrandept(ou.getDept().getDeptid());
                        busTodo.setIsnowflow(true);
                        busTodo.setSampleid(String.valueOf(1));
                        busTodo.setTodotype(TodoType.LabReady.getTodotype());
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);
                    }
                }

            } else {
                // 单样品流程
                // 获取当前任务单的样品信息,t_bus_task_sample在SaveBusTaskSingle中新增的
                List<BusTaskSample> samples = LabDao.GetListBusTaskSample(session, bts);
                for (int j = 0; j < samples.size(); j++) {
                    BusTaskSample bustasksample = samples.get(j);
                    String sampletran = bustasksample.getSampletran();

                    if (details.size() > 0) {
                        BusTaskTester bustasktester = details.get(j);
                        String params = bustasktester.getParameterids();
                        String[] parameterids = params.split(",");
                        for (String parameterid : parameterids) {
                            BusTaskTester btt = new BusTaskTester();
                            btt.getDeal().setAction(DataAction.Create.getAction());
                            btt.setTranid(bustask.getTranid());
                            btt.setTaskid(item.getTaskid());
                            btt.setTestuser(bustasktester.getTestuser());
                            btt.setTestusername(bustasktester.getTestusername());
                            btt.setParameterid(parameterid);
                            btt.setSampletran(sampletran);
                            btt.setIschoice(true);
                            LabDao.SaveBusTaskTester(session, btt);
                            tranuser = bustasktester.getTestuser();
                        }

                        // 做试验提醒
                        BusTodo busTodo = new BusTodo();
                        busTodo.setTranid(sampletran);
                        busTodo.setBusflow("LabReady");
                        busTodo.setFlownode("create");
                        busTodo.setSenddate(new Date());
                        busTodo.setTranuser(tranuser);
                        busTodo.setTrandept(ou.getDept().getDeptid());
                        busTodo.setIsnowflow(true);
                        busTodo.setSampleid(String.valueOf(1));
                        busTodo.setTodotype(TodoType.LabReady.getTodotype());
                        busTodo.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveBusTodo(session, busTodo);

                    }

                    BusRecord br = new BusRecord();
                    br.setTaskid(item.getTaskid());
                    BusRecord brecord = new BusRecord();

                    brecord.setTaskid(item.getTaskid());
                    brecord.setSampletran(sampletran);
                    brecord.setSamplecode(bustask.getSamplecode());
                    List<BusRecord> newrecords = DatDao.GetNewBusRecord(session, brecord);
                    for (BusRecord record : newrecords) {
                        record.getDeal().setAction(DataAction.Create.getAction());
                        record.setCrtuser(tranuser);
                        record.setTranid(bustask.getTranid());
                        record.setSampletran(bustasksample.getSampletran());
                        record.setSampleid(bustask.getSampleid());
                        record.setRecordstatus("01");
                        DatDao.SaveBusRecord(session, record);

                        BusTaskTester bttr = new BusTaskTester();
                        bttr.setTaskid(item.getTaskid());
                        bttr.setTranid(bustask.getTranid());
                        bttr.setTestuser(tranuser);
                        bttr.setRecordid(record.getRecordid());
                        bttr.setSampleid(bustask.getSampleid());
                        bttr.setSampletran(bustasksample.getSampletran());
                        if (record.getFormname().indexOf("转基因") != -1) {
                            // 生成转基因的原始记录表
                            LabDao.UpdateParamForZjy(session, bttr);
                        } else {
                            LabDao.UpdateParamInfo(session, bttr);
                        }
                    }
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("任务单分配完成！");
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\",\"flowstatus\":\"" + bustask.getFlowstatus() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskAcc(BusTask item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String flowstatus = item.getFlowstatus();
            item = LabDao.GetBusTask(item);
            item.setFlowstatus(flowstatus);
            item.setAcceptuser(ou.getUser().getUserid());
            item.setAcceptusername(ou.getUser().getUsername());
            item.setAcceptdate(new Date());
            item.getDeal().setAction(DataAction.Deal.getAction());
            // 修改任务单状态
            LabDao.SaveBusTask(session, item);

            // 接收后删除接收提醒
            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getTaskid());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // 保存实验准备
    public static void SaveBusTaskBegin(BusTaskSingle item, OnlineUser ou, List<BusTaskTester> busTaskTesters,
            ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            Date begintestdate = item.getBegintestdate();
            String testtemp = item.getTesttemp();
            String testhum = item.getTesthum();
            String testenv = item.getTestenv();
            String teststandardname = item.getTeststandardname();
            String devids = item.getDevids();
            String devnames = item.getDevnames();
            String getuser = item.getGetuser();
            String getusername = item.getGetusername();
            String sampletran = item.getSampletran();
            String taskremark = item.getTaskremark();
            Date getdate = item.getGetdate();
            String getcount = item.getGetcount();

            item = LabDao.GetBusTaskSingle(item);
            item.setFlowstatus("80");
            item.setBegintestdate(begintestdate);
            item.setTesttemp(testtemp);
            item.setTesthum(testhum);
            item.setTestenv(testenv);
            item.setTeststandardname(teststandardname);
            item.setDevids(devids);
            item.setDevnames(devnames);
            item.setGetuser(getuser);
            item.setGetusername(getusername);
            item.setGetdate(getdate);
            item.setGetcount(getcount);
            item.setSampletran(sampletran);
            item.setTaskremark(taskremark);
            item.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTaskSingleLab(session, item);

            // 更新t_bus_task表的状态
            BusTask bTask = new BusTask();
            bTask.setTaskid(item.getTaskid());
            bTask = LabDao.GetBusTask(bTask);
            bTask.setFlowstatus(item.getFlowstatus());
            bTask.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusTask(session, bTask);

            // 提醒修改为试验判定
            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getSampletran());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.setBusflow("LabReady");
            // 多样品处理
            // if(item.getSamplecode().equals("多样品样品编号-A")||item.getSamplecode().equals("多样品样品编号-B")){
            //
            // busTodo.getDeal().setAction(DataAction.Special01.getAction());
            // FlowDao.SaveBusTodo(session, busTodo);
            // }else{
            // 单样品处理
            List<BusTodo> busTodos = FlowDao.GetListBusTodo(busTodo);
            busTodo.setSampleid(String.valueOf(busTodos.size()));
            busTodo = FlowDao.GetBusTodo(busTodo);
            if (busTodo != null) {
                busTodo.setSenddate(new Date());
                busTodo.setBusflow("StartLab");
                busTodo.setTodotype(TodoType.StartLab.getTodotype());
                busTodo.setTranuser(ou.getUser().getUserid());
                busTodo.getDeal().setAction(DataAction.Modify.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
                // }
            }

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(item.getTaskid());
            bts.setSampletran(sampletran);
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setBegintestdate(begintestdate);
            bts.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            // BusTaskDev deldev = new BusTaskDev();
            // deldev.setSampletran(sampletran);
            // deldev.getDeal().setAction(DataAction.Delete.getAction());
            // LabDao.SaveBusTaskDev(session,deldev);
            //
            // String [] devidsarray = devids.split(",");
            // for (String devid : devidsarray) {
            // BusTaskDev btd = new BusTaskDev();
            // btd.setTaskid(item.getTaskid());
            // btd.setSampleid(item.getSampleid());
            // btd.setDevid(devid);
            // btd.setDevuse(new Date());
            // // btd.setDevconfirm(null);
            // btd.setSampletran(sampletran);
            // btd.getDeal().setAction(DataAction.Create.getAction());
            // LabDao.SaveBusTaskDev(session, btd);
            // }

            for (BusTaskTester bTester : busTaskTesters) {
                bTester.setSampletran(sampletran);
                bTester.getDeal().setAction(DataAction.Deal.getAction());
                LabDao.SaveBusTaskTester(session, bTester);

            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskEnd(BusTaskSingle item, List<BusTaskData> datas, OnlineUser ou, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            List<BusTaskData> oldtaskdatas = null;
            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusTaskData oldtaskdata = new BusTaskData();
                oldtaskdata.setTaskid(item.getTaskid());
                oldtaskdata.setSampletran(item.getSampletran());
                oldtaskdatas = LabDao.GetListBusTaskData(oldtaskdata);
            }

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(item.getTaskid());
            bts.setSampletran(item.getSampletran());
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setEndtestdate(item.getEndtestdate());
            bts.setAuditstatus("78");
            bts.getDeal().setAction(DataAction.Submit.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            BusTaskDev deldev = new BusTaskDev();
            deldev.setSampletran(item.getSampletran());
            deldev.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskDev(session, deldev);

            BusTaskData deldata = new BusTaskData();
            deldata.setSampletran(item.getSampletran());
            deldata.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskData(session, deldata);

            for (BusTaskData bustaskdata : datas) {
                bustaskdata.setSampletran(item.getSampletran());
                bustaskdata.setTaskid(item.getTaskid());
                bustaskdata.getDeal().setAction(DataAction.Create.getAction());

                LabDao.SaveBusTaskData(session, bustaskdata);

                if (bustaskdata.getFieldtype().equals("07")) {

                    String[] devids = bustaskdata.getSubmitvalue().split(",");
                    for (int i = 0; i < devids.length; i++) {
                        BusTaskDev btd = new BusTaskDev();
                        btd.setTaskid(item.getTaskid());
                        btd.setSampleid(item.getSampleid());
                        btd.setDevid(devids[i]);
                        btd.setDevuse(bts.getBegintestdate());
                        btd.setDevconfirm(bts.getEndtestdate());
                        btd.setSampletran(item.getSampletran());
                        btd.setFieldcode(bustaskdata.getFieldcode());
                        btd.setIntid(bustaskdata.getIntid());
                        btd.getDeal().setAction(DataAction.Create.getAction());
                        LabDao.SaveBusTaskDev(session, btd);
                    }
                }
            }
            session.commit();

            BusTaskData newtaskdata = new BusTaskData();
            newtaskdata.setTaskid(item.getTaskid());
            newtaskdata.setSampletran(item.getSampletran());
            List<BusTaskData> newtaskdatas = LabDao.GetListBusTaskData(newtaskdata);

            String strlog = LogDetailsUtils.getDiffrentdata(oldtaskdatas, newtaskdatas);

            int btscount = 0;
            BusTaskSample btsample = new BusTaskSample();
            btsample.setTaskid(item.getTaskid());
            btsample.setSamplecode(item.getSamplecode());
            List<BusTaskSample> btsamples = LabDao.GetListBusTaskSample(btsample);
            for (BusTaskSample bustasksample : btsamples) {
                if (bustasksample.getEndtestdate() == null) {
                    btscount = btscount + 1;
                }
            }

            // Date endtestdate = item.getEndtestdate();
            String testtemp = item.getTesttemp();
            String testhum = item.getTesthum();
            String testenv = item.getTestenv();
            String devids = item.getDevids();
            String devnames = item.getDevnames();

            item = LabDao.GetBusTaskSingle(item);
            if (btscount == 0) {
                // item.setFlowstatus("78");
            }
            // item.setEndtestdate(endtestdate);
            item.setEndtestdate(new Date());
            item.setTesttemp(testtemp);
            item.setTesthum(testhum);
            item.setTestenv(testenv);
            item.setDevids(devids);
            item.setDevnames(devnames);
            item.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTaskSingleLab(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("样品编号:" + item.getSamplecode());
            log.setTrandescdetail(strlog);
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void ComputeBusTask(BusTaskSingle item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ComputeBusTask(session, item, rtv, log);

            String sampletran = item.getSampletran();
            item = LabDao.GetBusTaskSingle(session, item);

            item.setFlowstatus("76");
            item.getDeal().setAction(DataAction.Deal.getAction());
            item.setSampletran(sampletran);
            LabDao.SaveBusTaskSingleLab(session, item);

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(item.getTaskid());
            List<BusTaskSample> bSamples = LabDao.GetBusTaskSampleByTask(session, bts);
            int isjudge = 0;
            if (bSamples.size() > 0) {
                for (BusTaskSample bSample : bSamples) {
                    if (!bSample.getIsjudge()) {
                        isjudge++;
                    }
                }
            }

            if (isjudge == 0) {
                // 更新t_bus_task表的状态
                BusTask bTask = new BusTask();
                bTask.setTaskid(item.getTaskid());
                bTask = LabDao.GetBusTask(bTask);
                bTask.setFlowstatus("78");
                bTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, bTask);
            }

            BusRecord bRecord = new BusRecord();
            bRecord.setSampletran(item.getSampletran());
            List<BusRecord> bRecords = DatDao.GetBusRecordBySampleTran(bRecord);
            for (BusRecord busRecord : bRecords) {
                busRecord.setRecordstatus("01");
                busRecord.getDeal().setAction(DataAction.Modify.getAction());
                DatDao.SaveBusRecord(session, busRecord);
            }

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getSampletran());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("判定成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            System.out.println(e);
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("rawtypes")
    public static void ComputeBusTask(SqlSession session, BusTaskSingle item, ReturnValue rtv, TranLog log)
            throws Exception {
        item.getDeal().setUserid(log.getTranuser());
        LabDao.ComputeBusTask(session, item);

        int i = 0;

        BusTaskSample bts = new BusTaskSample();
        bts.setSampletran(item.getSampletran());
        bts.setTaskid(item.getTaskid());
        BusTaskSample tsample = LabDao.GetBusTaskSample(session, bts);

        if (ToolUtils.StringIsEmpty(tsample.getTaskid())) {
            throw new Exception("读取任务单信息出错！");
        }

        if (tsample.getBegintestdate() == null) {
            throw new Exception("请先进行开始试验操作！");
        }

        if (tsample.getEndtestdate() == null) {
            throw new Exception("请先进行结束试验操作！");
        }

        BusTaskData btd = new BusTaskData();
        btd.setTaskid(item.getTaskid());
        btd.setSampletran(item.getSampletran());
        List<BusTaskData> datas = LabDao.GetListBusTaskData(session, btd);

        for (BusTaskData data : datas) {
            if (data.getFieldtype().equals("01") && !ToolUtils.StringIsEmpty(data.getSubmitvalue())) {
                try {
                    Double.parseDouble(data.getSubmitvalue());
                } catch (Exception e) {
                    throw new Exception(data.getFieldcode() + "存在非数值的数据！");
                }
            }

            if (data.getFieldtype().equals("02")) {
                if (ToolUtils.MustComboValue(data.getSubmitvalue()))
                    throw new Exception(data.getFieldcode() + "存在未选择的数据！");
            }
        }

        String samplecode = item.getSamplecode();
        BusGetDetail busGetDetail = new BusGetDetail();
        busGetDetail.setSamplecode(item.getSamplecode());
        // busGetDetail.setTaskid(item.getTaskid());
        busGetDetail = LabDao.GetBusGetDetailBySampleCode(busGetDetail);
        if ((busGetDetail == null) || ToolUtils.StringIsEmpty(busGetDetail.getTranid())) {
            throw new Exception("读取取样明细出错！");
        }

        // 获取取样单信息
        BusGet get = LabDao.GetBusGetBySampleCode(samplecode);
        if ((get == null) || ToolUtils.StringIsEmpty(get.getTranid())) {
            throw new Exception("读取取样信息出错！");
        }

        int groupserial = 1;
        int maxspecserial = 0;

        List<BusRecordData> gets = SetGet(get);
        List<BusRecordData> getdetails = SetGetDetail(busGetDetail, groupserial);
        List<BusRecordData> nowdatas = SetTask(item);

        List<BusRecordDetail> rdetail = DatDao.GetMaxSpecSerial(session, item);

        nowdatas.addAll(getdetails);
        nowdatas.addAll(gets);

        for (BusTaskData data : datas) {
            if (data.getSampletran().equals(item.getSampletran())) {
                AddRecordData(nowdatas, ClassSource.Data.getClasssource(), data.getFieldcode(), data.getFieldtype(),
                        data.getSubmitvalue(), data.getDisplayvalue(), groupserial, data.getSpecserial());

                if (data.getSpecserial() > maxspecserial) {
                    maxspecserial = data.getSpecserial();
                }
            }
        }

        List<DatComputeData> sets = DatDao.GetComputeDataBySet(session, item);

        Pattern pa = Pattern.compile("\\{[^{}]*\\}");
        Matcher ma;
        String matchfield = "";
        String actformula = "";
        String actvalue = "";

        List<String> avelists = new ArrayList<String>();
        String aveact = "";

        String[] funcargs;
        int argindex = 0;
        int validx = 0;
        String lastresult = "";
        boolean hasjudge = false;
        int arglen = 0;
        String phjudge = "";
        // String getsubmitvalue = "";
        // boolean isget = false;

        for (DatComputeData comcd : sets) {

            // region计算取值
            comcd.setActformula(comcd.getActformula().trim());
            if (comcd.getDatasource().equals(DataSource.FuncData.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getActformula())) {
                    continue;
                }

                avelists.clear();
                for (BusTaskData datahl : datas) {
                    if (datahl.getFieldcode().equals(comcd.getClassfield()) && !datahl.getSubmitvalue().equals("")) {
                        avelists.add(datahl.getSubmitvalue());
                    }
                }

                if (avelists.size() == 0) {
                    int nowmaxspecserial = 0;

                    // if (comcd.isIsserial()) {
                    nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());
                    if (nowmaxspecserial == 0) {
                        nowmaxspecserial = 1;
                    }
                    nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                    for (i = 1; i <= nowmaxspecserial; i++) {
                        actformula = comcd.getActformula();
                        ma = pa.matcher(comcd.getActformula());

                        while (ma.find()) {
                            matchfield = ma.group().replace("{", "").replace("}", "");

                            actformula = actformula.replace(ma.group(),
                                    GetSubmitValue(nowdatas, sets, matchfield, groupserial, i));
                        }

                        try {
                            actvalue = String.valueOf(jse.eval(actformula)).replace("-", "");

                            BusTaskTest bTest = new BusTaskTest();
                            bTest.setTaskid(item.getTaskid());
                            bTest.setSampleid(comcd.getSampleid());
                            bTest.setParameterid(comcd.getParameterids());
                            bTest = LabDao.GetBusTaskTest(bTest);
                            // 获取标准值的小数位数
                            String judgevalue = bTest.getStandvalue();
                            if (judgevalue.contains("-")) {
                                String[] juvalues = judgevalue.split("-");
                                judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                            }
                            if (judgevalue.contains("~")) {
                                String[] juvalues = judgevalue.split("~");
                                judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                            }
                            int dcimalDigits = 5;// 判定值为空时，默然保留位数
                            int indexOf = judgevalue.indexOf(".");
                            if (indexOf > 0) {
                                if (item.getLabid().equals("8003")) {
                                    // dcimalDigits为标准值小数位数一致（三室要求规定）
                                    dcimalDigits = judgevalue.length() - indexOf - 1;
                                } else {
                                    // dcimalDigits为标准值小数位数多一位（检测标准规定）
                                    dcimalDigits = judgevalue.length() - indexOf;
                                }
                            }

                            actvalue = actvalue.substring(0, actvalue.indexOf(".") + dcimalDigits + 1);

                            // //计算结果保留两位小数
                            // if (actvalue.indexOf(".") >= 0) {
                            // if ((actvalue.length() - actvalue.indexOf(".") -
                            // 1) > 2)
                            // actvalue = actvalue.substring(0,
                            // actvalue.indexOf(".") + 3);
                            // }

                        } catch (Exception e) {
                            actvalue = "ND";
                        }

                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(),
                                actvalue, actvalue, groupserial, i);

                    }
                    // }
                }
            }

            // endregion计算取值

            // region 平均值

            // AveData代表的是该平均值用于判定是否合格，AveNotJudge代表平均值用于计算中，不用于判定中
            if (comcd.getDatasource().equals(DataSource.AveData.getDatasource())
                    || comcd.getDatasource().equals(DataSource.AveNotJudge.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))// avg
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))// {data.awjshl}
                    continue;

                avelists.clear();
                aveact = comcd.getActformula().replace("{", "").replace("}", "");
                int nowmaxspecserial = 0;
                // 查找试验组数
                nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());

                nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                for (i = 1; i <= nowmaxspecserial; i++) {
                    for (BusRecordData data : nowdatas) {
                        if (aveact.equals(data.getClasssource() + "." + data.getFieldcode())
                                && (data.getSpecserial() == i)) {
                            if (!data.getSubmitvalue().equals("")) {
                                avelists.add(data.getSubmitvalue());
                            }

                        }
                    }
                }

                if (avelists.size() <= 0) {
                    actvalue = "";
                } else {
                    switch (comcd.getFuncformula().toLowerCase()) {
                    case "avg":
                        BusTaskTest bTest = new BusTaskTest();
                        bTest.setTaskid(item.getTaskid());
                        bTest.setSampleid(comcd.getSampleid());
                        bTest.setParameterid(comcd.getParameterids());
                        bTest = LabDao.GetBusTaskTest(bTest);
                        // 获取标准值的小数位数
                        String judgevalue = bTest.getStandvalue();
                        if (judgevalue.contains("-")) {
                            String[] juvalues = judgevalue.split("-");
                            judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                        }
                        if (judgevalue.contains("~")) {
                            String[] juvalues = judgevalue.split("~");
                            judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                        }
                        int dcimalDigits = 0;
                        int indexOf = judgevalue.indexOf(".");
                        if (indexOf > 0) {
                            if (item.getLabid().equals("8003")) {
                                // dcimalDigits为标准值小数位数一致（三室要求规定）
                                dcimalDigits = judgevalue.length() - indexOf - 1;
                            } else {
                                // dcimalDigits为标准值小数位数多一位（检测标准规定）
                                dcimalDigits = judgevalue.length() - indexOf;
                            }

                            actvalue = MathService.Ave(avelists, comcd.getDefvalue(), dcimalDigits);
                        } else {
                            actvalue = MathService.Ave(avelists, comcd.getDefvalue());
                        }
                        break;

                    case "statmax":
                        actvalue = MathService.statmax(avelists, comcd.getDefvalue());
                        break;

                    case "statmin":
                        actvalue = MathService.statmin(avelists, comcd.getDefvalue());
                        break;

                    case "statcount":
                        actvalue = MathService.statcount(avelists, comcd.getDefvalue());
                        break;

                    case "avemean3":
                        actvalue = MathService.AveMean3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "avemiddle3":
                        actvalue = MathService.AveMiddle3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averetain3":
                        actvalue = MathService.AveRetain3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averefre3":
                        actvalue = MathService.AveRefre3(avelists, comcd.getAvefactor(), comcd.getComparefactor(),
                                comcd.getDefvalue());
                        break;

                    case "avemean6":
                        actvalue = MathService.AveMean6(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averetain6":
                        actvalue = MathService.AveRetain6(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "statsum":
                        actvalue = MathService.statsum(avelists, comcd.getDefvalue());
                        break;

                    case "statmiddle":
                        actvalue = MathService.statmiddle(avelists, comcd.getDefvalue());
                        break;
                    case "choice":
                        actvalue = MathService.choice(avelists, comcd.getDefvalue());
                        break;

                    default:
                        actvalue = comcd.getDefvalue();
                        break;
                    }
                }

                AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(), actvalue,
                        actvalue, groupserial, 0);
            }

            // endregion 平均值

            // region 相对相差

            if (comcd.getDatasource().equals(DataSource.FormulaData.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))
                    continue;

                avelists.clear();
                aveact = comcd.getActformula().replace("{", "").replace("}", "");
                int nowmaxspecserial = 0;
                nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());

                nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                for (i = 1; i <= nowmaxspecserial; i++) {
                    for (BusRecordData data : nowdatas) {
                        if (aveact.equals(data.getClasssource() + "." + data.getFieldcode())
                                && (data.getSpecserial() == i)) {
                            if (!data.getSubmitvalue().equals("")) {
                                avelists.add(data.getSubmitvalue());
                            }
                        }
                    }
                }

                if (avelists.size() <= 0)
                    actvalue = "";
                else {
                    switch (comcd.getFuncformula().toLowerCase()) {
                    case "differ":
                        // actvalue = MathService.Differ(avelists,
                        // comcd.getDefvalue());
                        actvalue = MathService.RelativeDiffer(avelists, comcd.getDefvalue());
                        break;
                    default:
                        actvalue = comcd.getDefvalue();
                        break;
                    }
                }

                AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(), actvalue,
                        actvalue, groupserial, 0);
            }

        }

        // TwoJudgment 二次取值计算
        for (DatComputeData comcd : sets) {

            // region计算取值
            comcd.setActformula(comcd.getActformula().trim());
            if (comcd.getDatasource().equals(DataSource.TwoJudgment.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))
                    continue;

                avelists.clear();
                for (BusRecordData datahl : nowdatas) {
                    if (datahl.getFieldcode().equals(comcd.getClassfield()) && !datahl.getSubmitvalue().equals("")) {
                        avelists.add(datahl.getSubmitvalue());
                    }
                }

                if (avelists.size() == 0) {
                    int nowmaxspecserial = 0;

                    nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());
                    if (nowmaxspecserial == 0) {
                        nowmaxspecserial = 1;
                    }
                    nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                    for (i = 1; i <= nowmaxspecserial; i++) {
                        actformula = comcd.getActformula();
                        ma = pa.matcher(comcd.getActformula());

                        while (ma.find()) {
                            matchfield = ma.group().replace("{", "").replace("}", "");

                            actformula = actformula.replace(ma.group(),
                                    GetSubmitValue(nowdatas, sets, matchfield, groupserial, i));
                        }

                        try {
                            actvalue = String.valueOf(jse.eval(actformula)).replace("-", "");

                            if (actvalue.indexOf(".") >= 0) {
                                if ((actvalue.length() - actvalue.indexOf(".") - 1) > 9)
                                    actvalue = actvalue.substring(0, actvalue.indexOf(".") + 6);
                            }
                        } catch (Exception e) {
                            actvalue = "ND";
                        }

                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(),
                                actvalue, actvalue, groupserial, i);

                    }

                }

            }

        }
        // FLJudgment 肥料总养分取值计算
        // for (DatComputeData comcd : sets) {
        // // region计算取值
        // comcd.setActformula(comcd.getActformula().trim());
        // if (comcd.getDatasource().equals(
        // DataSource.FLJudgment.getDatasource())) {
        // if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
        // continue;
        //
        // if (ToolUtils.StringIsEmpty(comcd.getActformula()))
        // continue;
        //
        // avelists.clear();
        // for (BusRecordData datahl : nowdatas) {
        // if (datahl.getFieldcode().equals(comcd.getClassfield())
        // && !datahl.getSubmitvalue().equals("")) {
        // avelists.add(datahl.getSubmitvalue());
        // }
        // }
        //
        // if (avelists.size() == 0) {
        // int nowmaxspecserial = 1;
        // for (i = 1; i <= nowmaxspecserial; i++) {
        // actformula = comcd.getActformula();
        // ma = pa.matcher(comcd.getActformula());
        //
        // while (ma.find()) {
        // matchfield = ma.group().replace("{", "")
        // .replace("}", "");
        // actformula = actformula.replace(
        // ma.group(),
        // GetAVSubmitValue(nowdatas, sets,
        // matchfield, 1, 0));
        //
        // }
        //
        // try {
        // actvalue = String.valueOf(jse.eval(actformula))
        // .replace("-", "");
        //
        // if (actvalue.indexOf(".") >= 0) {
        // if ((actvalue.length() - actvalue.indexOf(".") - 1) > 9)
        // actvalue = actvalue.substring(0,
        // actvalue.indexOf(".") + 6);
        // }
        // } catch (Exception e) {
        // actvalue = "ND";
        // }
        //
        // AddRecordData(nowdatas, comcd.getClasssource(),
        // comcd.getClassfield(), comcd.getFieldtype(),
        // actvalue, actvalue, 1, 1);
        // DeleteRecordData(nowdatas, comcd.getClasssource(),
        // comcd.getClassfield() + "av",
        // comcd.getFieldtype(), actvalue, actvalue, 1, 0);
        // AddRecordData(nowdatas, comcd.getClasssource(),
        // comcd.getClassfield() + "av",
        // comcd.getFieldtype(), actvalue, actvalue, 1, 0);
        //
        // }
        //
        // }
        //
        // }
        //
        // }

        // region判定
        for (DatComputeData judgecd : sets) {
            if (judgecd.getDatasource().equals(DataSource.JudgeData.getDatasource())) {
                if (!ToolUtils.StringIsEmpty(judgecd.getFuncformula())) {
                    hasjudge = true;

                    try {
                        funcargs = judgecd.getActformula().split(",");

                        if (ToolUtils.StringIsEmpty(judgecd.getActformula()))
                            arglen = 0;
                        else {
                            arglen = funcargs.length;
                        }

                        Class judgeclass = Class.forName("com.alms.service.JudgeService");
                        Method[] methods = judgeclass.getMethods();

                        for (Method method : methods) {
                            if (method.getName().equals("testjudge")) {
                                Object[] margs = new Object[arglen];
                                argindex = 0;

                                for (String funcarg : funcargs) {
                                    if (ToolUtils.StringIsEmpty(funcarg))
                                        continue;

                                    if (funcarg.indexOf("[") >= 0) {
                                        funcarg = funcarg.replace("[", "").replace("]", "");

                                        avelists.clear();

                                        for (BusRecordData data : nowdatas) {
                                            if (funcarg.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                                avelists.add(data.getSubmitvalue());
                                            }
                                        }

                                        String[] vals = new String[avelists.size()];
                                        validx = 0;

                                        for (String valobject : avelists) {
                                            vals[validx++] = valobject;
                                        }

                                        margs[argindex] = vals;
                                    } else {
                                        funcarg = funcarg.replace("{", "").replace("}", "");

                                        for (BusRecordData data : nowdatas) {
                                            if (funcarg.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                                margs[argindex] = data.getSubmitvalue();
                                                break;
                                            }
                                        }
                                    }

                                    argindex++;
                                }

                                // 获取检测参数的标准判定值

                                BusTaskTest bTest = new BusTaskTest();
                                bTest.setTaskid(item.getTaskid());
                                bTest.setSampleid(judgecd.getSampleid());
                                bTest.setParameterid(judgecd.getParameterids());

                                if (judgecd.getSampleid().equals("001283")) {
                                    if (judgecd.getParameterids().equals("000864")) {
                                        double dv = Double.parseDouble(margs[0].toString());
                                        if (dv < 6.5) {
                                            phjudge = "0209";
                                            actvalue = "pH<6.5" + JudgeService.CN_LEVEL_1;
                                            break;
                                        } else if (dv > 7.5) {
                                            phjudge = "0212";
                                            actvalue = "pH>7.5" + JudgeService.CN_LEVEL_3;
                                            break;

                                        } else {
                                            phjudge = "0213";
                                            actvalue = "6.5≤pH≤7.5" + JudgeService.CN_LEVEL_2;
                                            break;
                                        }

                                    }
                                    bTest.setPhlevel(phjudge);
                                    bTest = LabDao.GetBusTaskTest(bTest);
                                    String judgevalue = bTest.getStandvalue();
                                    String testjudge = bTest.getTestjudge();
                                    String deteclimit = bTest.getDeteclimit();
                                    actvalue = (String) method.invoke(null, margs, judgevalue, testjudge, deteclimit);

                                } else {
                                    bTest = LabDao.GetBusTaskTest(bTest);
                                    String judgevalue = bTest.getStandvalue();
                                    String testjudge = bTest.getTestjudge();
                                    String deteclimit = bTest.getDeteclimit();
                                    actvalue = (String) method.invoke(null, margs, judgevalue, testjudge, deteclimit);
                                }
                                break;

                            }
                        }
                    } catch (Exception e) {
                        actvalue = JudgeService.JUDGE_INVALID;
                    }

                    AddRecordData(nowdatas, judgecd.getClasssource(), judgecd.getClassfield(), judgecd.getFieldtype(),
                            actvalue, actvalue, groupserial, 0);

                    switch (actvalue) {
                    case JudgeService.JUDGE_NOTOK:
                        lastresult = JudgeStatus.NOTOK.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_REVIEW:
                        lastresult = JudgeStatus.REVIEW.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_INVALID:
                        lastresult = JudgeStatus.INVALID.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_NO:
                        lastresult = JudgeStatus.NO.getJudgestatus();
                        break;

                    default:
                        break;
                    }
                }
            }
        }

        if (hasjudge) {
            if (ToolUtils.StringIsEmpty(lastresult)) {
                lastresult = JudgeStatus.OK.getJudgestatus();
            }

            item.setJudgestatus(lastresult);
            BusTaskSample ubts = new BusTaskSample();
            ubts.setSampletran(item.getSampletran());
            ubts = LabDao.GetBusTaskSample(ubts);
            ubts.setJudgestatus(lastresult);
            ubts.setIsjudge(true);
            ubts.setAuditstatus("76");
            ubts.getDeal().setAction(DataAction.Modify.getAction());
            LabDao.SaveBusTaskSample(session, ubts);
        }

        String deletesql = "delete from t_bus_record_data where sampletran = ?";

        PreparedStatement ds = session.getConnection().prepareStatement(deletesql);

        ds.setString(1, item.getSampletran());
        ds.addBatch();
        ds.executeBatch();

        // 将相关数据保存到T_Bus_Record_Data中
        String insertsql = "insert into t_bus_record_data(tranid, samplecode, taskid, sampletran, sampleid, groupserial, "
                + "specserial, classsource, fieldcode, fieldtype, displayvalue, submitvalue, validcode) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = session.getConnection().prepareStatement(insertsql);
        for (BusRecordData data : nowdatas) {
            data.setTaskid(item.getTaskid());
            data.setTranid(item.getTranid());
            data.setSamplecode(item.getSamplecode());
            data.setSampleid(item.getSampleid());
            data.setSampletran(item.getSampletran());

            ps.setString(1, data.getTranid());
            ps.setString(2, data.getSamplecode());
            ps.setString(3, data.getTaskid());
            ps.setString(4, data.getSampletran());
            ps.setString(5, data.getSampleid());
            ps.setInt(6, data.getGroupserial());
            ps.setInt(7, data.getSpecserial());
            ps.setString(8, data.getClasssource());
            ps.setString(9, data.getFieldcode());
            ps.setString(10, data.getFieldtype());

            if (data.getDisplayvalue().equals("") || data.getDisplayvalue().equals("请选择")) {
                ps.setString(11, "/");
            } else {
                ps.setString(11, data.getDisplayvalue());
            }

            if (data.getSubmitvalue().equals("") || data.getSubmitvalue().equals("-2")) {
                ps.setString(12, "/");
            } else {
                ps.setString(12, data.getSubmitvalue());
            }

            ps.setString(13, data.getValidcode());
            ps.addBatch();
            data.getDeal().setAction(DataAction.Create.getAction());
        }
        ps.executeBatch();
        DatDao.SyncBusRecordDetail(session, item);
    }

    public static void SubmitBusTask(BusTaskSingle item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            item = LabDao.GetBusTaskSingle(item);

            BusTaskSample sample = new BusTaskSample();
            sample.setSampletran(item.getSampletran());
            sample = LabDao.GetBusTaskSample(sample);

            if ((sample == null) || ToolUtils.StringIsEmpty(sample.getTranid())) {
                throw new Exception("读取样品信息出错！");
            }

            item.setGetuser(log.getTranuser());
            item.setGetusername(log.getUsername());
            item.getDeal().setUserid(log.getTranuser());
            // LabDao.SubmitBusTask(session, item);
            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc(item.getTaskid());
            PublicDao.SaveTranLog(session, log);

            // 报告结论
            item.setSampleid(sample.getSampleid());
            item.setSamplecode(sample.getSamplecode());

            // region 生成报告

            boolean isnew = false;

            List<BusReport> reports = DatDao.GetBusReportByTask(session, item);

            if (reports.size() == 0) {
                reports = DatDao.GetNewBusReport(session, item);
                isnew = true;
            }

            for (BusReport report : reports) {
                if (isnew)
                    report.getDeal().setAction(DataAction.Create.getAction());
                else {
                    report.getDeal().setAction(DataAction.Modify.getAction());
                }

                report.setTranuser(log.getTranuser());
                report.setTranusername(log.getUsername());
                report.setTranid(sample.getTranid());

                DatDao.SaveBusReport(session, report);
                DatDao.UpdateBusReportInfo(session, report);
            }

            DatDao.SyncBusReport(session, item);

            // endregion 生成报告

            rtv.setSuccess(true);
            rtv.setMsg("提交成功！");
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "提交失败！"));
        } finally {
            session.close();
        }
    }

    public static String GetSubmitValue(List<BusRecordData> datas, List<DatComputeData> sets, String fieldcode,
            int groupserial, int specserial) {

        boolean isfind = true;

        for (DatComputeData set : sets) {
            if (fieldcode.equals(set.getClasssource() + "." + set.getClassfield())) {

                if (!set.isIsgroup())
                    groupserial = 0;

                if (!set.isIsserial())
                    specserial = 0;

                for (BusRecordData data : datas) {
                    if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                            && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode())) {

                        isfind = false;
                        return data.getSubmitvalue();
                    }
                }

                // 判断计算公式里面的成分是不是通过计算获取的，04是计算,计算结果是单组试验的情况
                if (isfind) {
                    if (set.getDatasource().equals("04")) {
                        for (BusRecordData data : datas) {
                            int specserial2 = 1;

                            if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial2)
                                    && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                return data.getSubmitvalue();
                            }
                        }
                    }
                }
            }
        }

        for (BusRecordData data : datas) {
            if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                    && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode()))
                return data.getSubmitvalue();
        }
        return "/";
    }

    // 获取肥料结果计算值
    public static String GetAVSubmitValue(List<BusRecordData> datas, List<DatComputeData> sets, String fieldcode,
            int groupserial, int specserial) {
        boolean isfind = true;
        for (DatComputeData set : sets) {
            if (fieldcode.equals(set.getClasssource() + "." + set.getClassfield())) {
                for (BusRecordData data : datas) {
                    if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                            && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode()) && isfind) {
                        isfind = false;
                        return data.getSubmitvalue();
                    }
                }

            }
        }
        return "/";
    }

    public static int GetMaxSpecSerial(List<BusRecordDetail> datas, String classsource, String fieldcode) {
        int rtn = 0;

        for (BusRecordDetail data : datas) {
            if (data.getClasssource().equals(classsource) && data.getFieldcode().equals(fieldcode)) {
                if (data.getSpecserial() > rtn)
                    rtn = data.getSpecserial();
            }
        }
        return rtn;
    }

    public static List<BusRecordData> SetGetDetail(BusGetDetail item, int groupserial) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();

        if (item != null) {
            String[] classfields = { "samplecode", "tranid", "sampleid", "samplename", "factname", "trademark",
                    "samplestand", "samplelevel", "samplecount", "prdcode", "getaddr", "samplebase", "testitems",
                    "mainstandname", "tranidcn" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();
                    BusRecordData groupdata = new BusRecordData();

                    data.setClasssource(ClassSource.Sample.getClasssource());
                    data.setFieldcode(classfield);
                    groupdata.setClasssource(ClassSource.Sample.getClasssource());
                    groupdata.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }

                    groupdata.setSubmitvalue(data.getSubmitvalue());

                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);

                    groupdata.setDisplayvalue(groupdata.getSubmitvalue());
                    groupdata.setGroupserial(groupserial);
                    groupdata.setSpecserial(0);
                    groupdata.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(groupdata);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    public static List<BusRecordData> SetGet(BusGet item) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();

        if (item != null) {
            String[] classfields = { "testedname", "tranusername", "getdate", "testtypename", "testeduser" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();

                    data.setClasssource(ClassSource.Consign.getClasssource());
                    data.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }

                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return datas;
    }

    public static List<BusRecordData> SetTask(BusTaskSingle item) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();
        item = LabDao.GetBusTaskSingle(item);
        if (item != null) {
            String[] classfields = { "taskid", "samplecode", "samplename", "begintestdate", "labid", "labname",
                    "testtemp", "testhum", "testenv", "devids", "devnames", "mainstandname", "samplestatus",
                    "teststandardname", "taskremark" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();

                    data.setClasssource(ClassSource.Task.getClasssource());
                    data.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }
                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    public static void AddRecordData(List<BusRecordData> datas, String classsource, String fieldcode, String fieldtype,
            String submitvalue, String displayvalue, int groupserial, int specserial) {
        BusRecordData item = new BusRecordData();

        item.setClasssource(classsource);
        item.setFieldcode(fieldcode);
        item.setFieldtype(fieldtype);
        item.setSubmitvalue(submitvalue);
        item.setDisplayvalue(displayvalue);
        item.setGroupserial(groupserial);
        item.setSpecserial(specserial);

        datas.add(item);
    }

    public static void DeleteRecordData(List<BusRecordData> datas, String classsource, String fieldcode,
            String fieldtype, String submitvalue, String displayvalue, int groupserial, int specserial) {

        Iterator<BusRecordData> iterator = datas.iterator();
        while (iterator.hasNext()) {
            BusRecordData obj = iterator.next();
            if (obj.getClasssource().equals(classsource) && obj.getFieldcode().equals(fieldcode)
                    && obj.getFieldtype().equals(fieldtype) && obj.getGroupserial() == groupserial
                    && obj.getSpecserial() == specserial) {
                iterator.remove();
            }
        }
    }

    public static void SaveBusTaskBack(BusTask item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            double backcount = item.getBackcount();
            item = LabDao.GetBusTask(item);
            item.setFlowstatus("74");
            item.setBackcount(backcount);
            item.setBackuser(ou.getUser().getUserid());
            item.setBackusername(ou.getUser().getUsername());
            item.setBackdate(new Date());
            item.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTask(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusTask Methods

    // region BusAccSample Methods

    public static void SaveBusAccSample(BusAccSample item, OnlineUser ou, ReturnValue rtv, TranLog log) {
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

                item.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusAccSample(session, item);

                BusGetDetail bgd = new BusGetDetail();
                BusAccSample bas = new BusAccSample();

                bgd.setTranid(item.getGetid());
                List<BusGetDetail> bgds = LabDao.GetListBusGetDetail(bgd);

                // 多样品流程
                if (item.getGettype().equals("09") || item.getGettype().equals("08")) {

                    List<String> sampleidss = new ArrayList<String>();
                    int samcount = 0;

                    for (BusGetDetail bDetail : bgds) {
                        if (sampleidss.toString().indexOf(bDetail.getSampleid()) == -1) {
                            if (samcount > 0) {
                                throw new Exception(bDetail.getSamplename() + "检测参数与其他检测项目不是同类，请修改抽样单信息！");
                            }

                            sampleidss.add(bDetail.getSampleid());
                            samcount++;
                        }
                    }

                    for (String samid : sampleidss) {
                        // 多样品任务
                        for (int i = 0; i < 2; i++) {
                            // String sampleids = "";
                            String samplenames = "";
                            String servercodes = "";

                            BasSample bSample = new BasSample();
                            bSample.setSampleid(samid);

                            bSample = BasDao.GetBasSample(bSample);

                            samplenames = bSample.getSamplename();

                            // samplenames = "多样品检测";
                            //
                            switch (i) {
                            case 0:
                                servercodes = "多样品样品编号-A";
                                break;
                            case 1:
                                servercodes = "多样品样品编号-B";
                                break;
                            }

                            switch (i) {
                            case 0:
                                bas.setSampletype("01");
                                break;
                            case 1:
                                bas.setSampletype("03");
                                break;
                            }

                            bas.setSamplecode(servercodes);
                            bas.setSampleid(samid);
                            bas.setSamplename(samplenames);
                            bas.setTestedname(item.getTestedname());
                            bas.setTestedid(item.getTestedid());
                            bas.setEntertele(item.getEntertele());
                            bas.setTesteduser(item.getTesteduser());
                            bas.setGetid(item.getGetid());
                            bas.setAccuser(log.getTranuser());
                            bas.setAccusername(log.getUsername());
                            bas.setGettype(item.getGettype());
                            bas.setAccdate(item.getAccdate());
                            bas.setFeestatus(item.getFeestatus());
                            bas.setTestfee(item.getTestfee());
                            bas.setRemark(item.getRemark());
                            bas.setAcctele(item.getAcctele());
                            bas.setSampleplace(item.getSampleplace());
                            bas.setFlowstatus("85");
                            bas.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusAccSample(session, bas);
                        }
                    }
                }
                // 单样品流程
                else {
                    for (BusGetDetail busgetdetail : bgds) {
                        for (int i = 0; i < 2; i++) {
                            String sampleids = "";
                            String samplenames = "";
                            String servercodes = "";

                            sampleids = busgetdetail.getSampleid();

                            samplenames = busgetdetail.getSamplename();

                            switch (i) {
                            case 0:
                                servercodes = busgetdetail.getSamplecode() + "-A";
                                break;
                            case 1:
                                servercodes = busgetdetail.getSamplecode() + "-B";
                                break;
                            }

                            switch (i) {
                            case 0:
                                bas.setSampletype("01");
                                break;
                            case 1:
                                bas.setSampletype("03");
                                break;
                            }

                            bas.setSamplecode(servercodes);
                            bas.setSampleid(sampleids);
                            bas.setSamplename(samplenames);
                            bas.setTestedname(item.getTestedname());
                            bas.setTestedid(item.getTestedid());
                            bas.setEntertele(item.getEntertele());
                            bas.setTesteduser(item.getTesteduser());
                            bas.setGetid(item.getGetid());
                            bas.setAccuser(log.getTranuser());
                            bas.setAccusername(log.getUsername());
                            bas.setGettype(item.getGettype());
                            bas.setAccdate(item.getAccdate());
                            bas.setFeestatus(item.getFeestatus());
                            bas.setRemark(item.getRemark());
                            bas.setAcctele(item.getAcctele());
                            bas.setTestfee(item.getTestfee());
                            bas.setSampleplace(item.getSampleplace());
                            bas.setFlowstatus("85");
                            bas.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusAccSample(session, bas);
                        }
                    }
                }

                // 删除收样提醒
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(item.getGetid());
                busTodo.setBusflow("GetAcc");
                busTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, busTodo);

            }

            log.ActionToTran(DataAction.Create.getAction());
            log.setTrandesc("收样时间:" + new SimpleDateFormat("yyyy-MM-dd").format(item.getAccdate()));
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

    // endregion BusAccSample Methods

    // region BusTaskRecord Methods

    public static void SaveBusTaskRecord(BusTaskSingle item, List<BusTaskAttach> details,
            List<BusRecordFile> filedetails, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

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

            BusTaskSingle bSingle = new BusTaskSingle();
            bSingle = LabDao.GetBusTaskSingle(item);
            bSingle.setFlowstatus(item.getFlowstatus());
            bSingle.setCheckdesc(item.getCheckdesc());
            // bSingle.setAduituser(item.getLabuser());
            // bSingle.setAduitusername(item.getLabusername());
            bSingle.getDeal().setAction(DataAction.Check.getAction());
            LabDao.SaveBusTaskSingleLab(session, bSingle);

            BusTaskSample bts = new BusTaskSample();
            bts.setSampletran(item.getSampletran());
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setAuditstatus("92");
            bts.setCheckdesc("");
            bts.getDeal().setAction(DataAction.Submit.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            // 删除驳回提醒
            BusTodo bt = new BusTodo();
            bt.setTranid(item.getTranid());
            bt.setTranuser(ou.getUser().getUserid());
            bt.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, bt);

            // 提醒该检测室人员审批原始记录表
            SysUser sysUser = new SysUser();
            sysUser.setDeptid(ou.getDept().getDeptid());
            List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);

            // 获取样品分配的角色
            BusTaskTester bTester = new BusTaskTester();
            bTester.setTaskid(bSingle.getTaskid());
            List<BusTaskTester> acceptusers = LabDao.GetListBusTaskTester(bTester);

            for (SysUser sUser : sysUsers) {
                for (BusTaskTester bTaskTester : acceptusers) {
                    if (!sUser.getUserid().equals(bTaskTester.getTestuser())) {
                        if (!sUser.getUserid().equals(ou.getUser().getUserid())) {
                            BusTodo busTodo = new BusTodo();
                            busTodo.setTranid(item.getTaskid());
                            busTodo.setBusflow("RecordApprove");
                            busTodo.setFlownode("create");
                            busTodo.setSenddate(new Date());
                            busTodo.setTranuser(sUser.getUserid());
                            // busTodo.setTrandept(ou.getDept().getDeptid());
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
                        }
                    }
                }
            }

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

    public static void ApproveBusTaskRecord(BusTaskSingle item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BusTaskSingle bSingle = new BusTaskSingle();
            bSingle = LabDao.GetBusTaskSingleByTaskid(item);
            bSingle.setFlowstatus(item.getFlowstatus());
            bSingle.setCheckdesc(item.getCheckdesc());
            bSingle.setCheckuser(ou.getUser().getUserid());
            bSingle.setCheckusername(ou.getUser().getUsername());
            bSingle.setCheckdate(new Date());
            bSingle.getDeal().setAction(DataAction.Check.getAction());
            LabDao.SaveBusTaskSingleLab(session, bSingle);

            // 审批不通过提醒检测人员
            if (item.getDeal().getAction() == DataAction.InValid.getAction()) {

                List<BusTaskSingle> busTaskSingles = LabDao.GetBusTaskSingleByTranID(bSingle);
                for (BusTaskSingle bTaskSingle : busTaskSingles) {

                    // 把反馈意见返回给检测员，并作提醒功能
                    BusTaskSample busTaskSample = new BusTaskSample();
                    busTaskSample.setCheckdesc(item.getCheckdesc());
                    busTaskSample.setAuditstatus("96");
                    busTaskSample.setSampletran(bTaskSingle.getSampletran());
                    busTaskSample.getDeal().setAction(DataAction.Check.getAction());
                    LabDao.SaveBusTaskSample(session, busTaskSample);

                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(bSingle.getTranid());
                    busTodo.setBusflow("RecordNoPass");
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

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bSingle.getTaskid());
                bTodo.setBusflow("RecordApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);
            }

            // 审批通过
            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {

                BusTaskSample busTaskSample = new BusTaskSample();
                busTaskSample.setCheckdesc("");
                busTaskSample.setAuditstatus("94");
                busTaskSample.setTaskid(item.getTaskid());
                busTaskSample.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSample(session, busTaskSample);

                // 删除样品试验截止完成时间提醒
                BusTodo bt = new BusTodo();
                bt.setTranid(bSingle.getTaskid() + bSingle.getLabuser());
                bt.setTranuser(bSingle.getLabuser());
                bt.getDeal().setAction(DataAction.Deal.getAction());
                FlowDao.SaveBusTodo(session, bt);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bSingle.getTaskid());
                bTodo.setBusflow("RecordApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                // 提醒资质审核人审核原始记录表
                SysUser sysUser = new SysUser();
                sysUser.setDeptid(ou.getDept().getDeptid());
                List<SysUser> sysUsers = UserDao.GetListUserByDept(sysUser);

                // 获取样品分配的角色
                BusTaskTester bTester = new BusTaskTester();
                bTester.setTaskid(bSingle.getTaskid());
                List<BusTaskTester> acceptusers = LabDao.GetListBusTaskTester(bTester);

                for (SysUser sUser : sysUsers) {
                    for (BusTaskTester bTaskTester : acceptusers) {
                        // if(sUser.getRoleid().equals("9")){

                        if (!sUser.getUserid().equals(bTaskTester.getTestuser())) {
                            if (!sUser.getUserid().equals(ou.getUser().getUserid())) {
                                BusTodo busTodo = new BusTodo();
                                busTodo.setTranid(bSingle.getTaskid());
                                busTodo.setBusflow("RecordAudit");
                                busTodo.setFlownode("create");
                                busTodo.setSenddate(new Date());
                                busTodo.setTranuser(sUser.getUserid());
                                // busTodo.setTrandept(ou.getDept().getDeptid());
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
                            }
                        }
                        // }
                    }
                }
            }

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

    public static void AuditBusTaskRecord(BusTaskSingle item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            BusTaskSingle bSingle = new BusTaskSingle();
            bSingle = LabDao.GetBusTaskSingleByTaskid(item);
            bSingle.setFlowstatus(item.getFlowstatus());
            bSingle.setCheckdesc(item.getCheckdesc());
            bSingle.setAduituser(ou.getUser().getUserid());
            bSingle.setAduitusername(ou.getUser().getUsername());
            bSingle.setAduitdate(new Date());
            bSingle.getDeal().setAction(DataAction.Check.getAction());
            LabDao.SaveBusTaskSingleLab(session, bSingle);

            // 审批不通过提醒检测人员
            if (item.getDeal().getAction() == DataAction.InValid.getAction()) {

                List<BusTaskSingle> busTaskSingles = LabDao.GetBusTaskSingleByTranID(bSingle);
                for (BusTaskSingle bTaskSingle : busTaskSingles) {

                    // 把反馈意见返回给检测员，并作提醒功能
                    BusTaskSample busTaskSample = new BusTaskSample();
                    busTaskSample.setCheckdesc(item.getCheckdesc());
                    busTaskSample.setAuditstatus("99");
                    busTaskSample.setSampletran(bTaskSingle.getSampletran());
                    busTaskSample.getDeal().setAction(DataAction.Check.getAction());
                    LabDao.SaveBusTaskSample(session, busTaskSample);

                    BusTodo busTodo = new BusTodo();
                    busTodo.setTranid(bSingle.getTranid());
                    busTodo.setBusflow("RecordNoPass");
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

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bSingle.getTaskid());
                bTodo.setBusflow("RecordAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);
            }

            // 审批通过生成试验报告
            if (item.getDeal().getAction() == DataAction.Submit.getAction()) {

                BusTaskSample busTaskSample = new BusTaskSample();
                busTaskSample.setCheckdesc("");
                busTaskSample.setAuditstatus("98");
                busTaskSample.setTranid(item.getTranid());
                busTaskSample.getDeal().setAction(DataAction.Check.getAction());
                LabDao.SaveBusTaskSample(session, busTaskSample);

                // 删除样品试验截止完成时间提醒
                BusTodo bt = new BusTodo();
                bt.setTranid(bSingle.getTaskid() + bSingle.getLabuser());
                bt.setTranuser(bSingle.getLabuser());
                bt.getDeal().setAction(DataAction.Deal.getAction());
                FlowDao.SaveBusTodo(session, bt);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bSingle.getTaskid());
                bTodo.setBusflow("RecordAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                // BusTaskSample sample = new BusTaskSample();
                // sample.setSampletran(bSingle.getSampletran());
                // sample = LabDao.GetBusTaskSample(sample);
                //
                // if ((sample == null) ||
                // ToolUtils.StringIsEmpty(sample.getTranid())) {
                // throw new Exception("读取样品信息出错！");
                // }

                bSingle.setGetuser(log.getTranuser());
                bSingle.setGetusername(log.getUsername());
                bSingle.getDeal().setUserid(log.getTranuser());
                // LabDao.SubmitBusTask(session, bSingle);
                log.ActionToTran(DataAction.Deal.getAction());
                log.setTrandesc(bSingle.getTaskid());
                PublicDao.SaveTranLog(session, log);

                // 报告结论
                // bSingle.setSampleid(sample.getSampleid());
                // bSingle.setSamplecode(sample.getSamplecode());

                // region 生成报告

                boolean isnew = false;

                List<BusReport> reports = DatDao.GetBusReportByTask(session, bSingle);

                if (reports.size() == 0) {
                    reports = DatDao.GetNewBusReport(session, bSingle);
                    isnew = true;
                }

                for (BusReport report : reports) {
                    if (isnew)
                        report.getDeal().setAction(DataAction.Create.getAction());
                    else {
                        report.getDeal().setAction(DataAction.Modify.getAction());
                        report.setReportstatus("01");
                    }

                    // report.setTranuser(log.getTranuser());
                    // report.setTranusername(log.getUsername());
                    report.setTranid(bSingle.getTranid());
                    report.setTaskid(bSingle.getTaskid());
                    report.setModifydesc("");
                    DatDao.SaveBusReport(session, report);
                    // DatDao.UpdateBusReportInfo(session, report);
                }

                // DatDao.SyncBusReport(session, bSingle);

                // endregion 生成报告
            }

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

    // endregion BusTaskRecord Methods

    // region BusTaskJudge Methods

    public static void SaveBusTaskJudge(BusTaskJudge item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            LabDao.SaveBusTaskJudge(session, item);

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

    // endregion BusTaskJudge Methods

    // region SignerPassword Methods

    public static void SaveSignerPassword(SignerPassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            if (ToolUtils.StringIsEmpty(item.getSignerpassword())) {
                rtv.setMsg("密码不能为空！");
                return;
            }

            if (!item.getSignerpassword().equals(item.getRepassword())) {
                rtv.setMsg("密码两次输入不同，请确认后再进行保存！");
                return;
            }

            item.setUserid(ou.getUser().getUserid());

            SignerPassword pwd = LabDao.GetSignerPassword(item);
            if (pwd != null) {
                rtv.setMsg("密码已存在，不能再设置！<br/> 亲可重置密码或修改密码！");
                return;
            }

            item.setSignerpassword(ToolUtils.GetMD5(item.getSignerpassword()));
            item.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveSignerPassword(session, item);

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

    public static void ChangeSignerPassword(SignerPassword item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            if (ToolUtils.StringIsEmpty(item.getOldpassword())) {
                rtv.setMsg("原密码不能为空！");
                return;
            }

            if (ToolUtils.StringIsEmpty(item.getSignerpassword())) {
                rtv.setMsg("新密码不能为空！");
                return;
            }

            if (item.getOldpassword().equals(item.getSignerpassword())) {
                rtv.setMsg("新密码不能和旧密码不能一样！");
                return;
            }

            if (!item.getSignerpassword().equals(item.getRepassword())) {
                rtv.setMsg("新密码两次输入不同，请确认后再进行保存！");
                return;
            }

            item.setUserid(ou.getUser().getUserid());
            SignerPassword pwd = LabDao.GetSignerPassword(item);

            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            if (pwd != null) {

                if (ToolUtils.StringIsEmpty(pwd.getSignerpassword())
                        || !pwd.getSignerpassword().equals(ToolUtils.GetMD5(item.getOldpassword()))) {
                    rtv.setMsg("原密码不正确！");
                    return;
                }

                item.setSignerpassword(ToolUtils.GetMD5(item.getSignerpassword()));

                item.getDeal().setAction(DataAction.Modify.getAction());

            } else {

                if (!su.getUserpassword().equals(ToolUtils.GetMD5(item.getOldpassword()))) {
                    rtv.setMsg("原密码不正确！");
                    return;
                }

                item.setSignerpassword(ToolUtils.GetMD5(item.getSignerpassword()));

                item.getDeal().setAction(DataAction.Create.getAction());

            }

            LabDao.SaveSignerPassword(session, item);

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

    public static void ResetSignerPassword(SignerPassword item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            String userid = log.getTranuser();
            SysUser su = new SysUser();
            su = UserDao.GetUser(userid);

            item.setSignerpassword(su.getUserpassword());

            item.getDeal().setAction(DataAction.Modify.getAction());
            LabDao.SaveSignerPassword(session, item);

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

    // endregion SignerPassword Methods

    public static void SaveBusPower(BusTask item, List<BusTask> taskids, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            for (BusTask busTask : taskids) {
                // 删除原始记录审批提醒
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(busTask.getTaskid());
                busTodo.setTranuser(log.getTranuser());
                busTodo.getDeal().setAction(DataAction.Deal.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
                // 提醒被授权者审批原始记录
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(busTask.getTaskid());
                bTodo.setBusflow("BusRecord");
                bTodo.setFlownode("create");
                bTodo.setSenddate(new Date());
                bTodo.setTranuser(item.getAcceptuser());
                bTodo.setIsnowflow(true);
                bTodo.setTodotype("15");
                bTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                // 将新授权的审批人更新到任务单t_bus_task_single 上
                busTask.setAduituser(item.getAcceptuser());
                busTask.setAduitusername(item.getAcceptusername());
                busTask.setFlowstatus("96");
                busTask.setProcessuser(item.getProcessuser());
                busTask.setProcessusername(item.getProcessusername());
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTaskSingle(session, busTask);
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

    public static BusRecordSamples SearchBusRecordSamples(String taskid) {

        SqlSession session = DBUtils.getFactory();

        return LabDao.SearchBusRecordSamples(session, taskid);
    }

    // 多样品复核
    public static void SaveApproveForSamples(BusTask bt, ReturnValue rtv, String signerpassword, String auditusers,
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
            BusRecordSamples bRecordSamples = new BusRecordSamples();
            bRecordSamples.setApproveuser(ou.getUser().getUserid());
            bRecordSamples.setApproveusername(ou.getUser().getUsername());
            bRecordSamples.setApprovedate(new Date());
            bRecordSamples.setRecordstatus(bt.getRecordstatus());
            bRecordSamples.setTaskid(bt.getTaskid());
            bRecordSamples.setModifydesc(bt.getModifydesc());
            bRecordSamples.setAduituser(auditusers);
            LabDao.SubmitBusRecordSamples(session, bRecordSamples);

            if (bt.getDeal().getAction() == DataAction.Submit.getAction()) {

                // //改变t_bus_task_sample的状态
                // BusTaskSample bts = new BusTaskSample();
                // bts.setTaskid(bt.getTaskid());
                // bts.setAuditstatus("94");
                // bts.setCheckdesc("");
                // bts.getDeal().setAction(DataAction.Special01.getAction());
                // LabDao.SaveBusTaskSample(session, bts);

                // 修改任务单状态
                bt.setFlowstatus("94");
                bt.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, bt);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bt.getTaskid());
                bTodo.setBusflow("RecordApprove");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);

                boolean isuser = false;
                BusRecordSamples brSamples = new BusRecordSamples();
                if (ou != null) {
                    brSamples = LabService.SearchBusRecordSamples(bt.getTaskid());
                }
                if (!auditusers.equals("") && auditusers != null) {
                    String[] sysUsers = auditusers.split(",");

                    for (String sysUser : sysUsers) {

                        if (!sysUser.equals(ou.getUser().getUserid()) && !sysUser.equals(brSamples.getTranuser())) {
                            BusTodo busTodo = new BusTodo();
                            busTodo.setTranid(bt.getTaskid());
                            busTodo.setBusflow("RecordAudit");
                            busTodo.setFlownode("create");
                            busTodo.setSenddate(new Date());
                            busTodo.setTranuser(sysUser);
                            busTodo.setIsnowflow(true);
                            busTodo.setSampleid(String.valueOf(1));
                            busTodo.setTodotype("28");
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
            if (bt.getDeal().getAction() == DataAction.InValid.getAction()) {

                // //改变t_bus_task_sample的状态
                // BusTaskSample bts = new BusTaskSample();
                // bts.setSampletran(bt.getTaskid());
                // bts.setAuditstatus("96");
                // bts.setCheckdesc(bt.getModifydesc());
                // bts.getDeal().setAction(DataAction.Special01.getAction());
                // LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask busTask = new BusTask();
                busTask.setTaskid(bt.getTaskid());
                busTask = LabDao.GetBusTask(busTask);
                busTask.setFlowstatus("96");
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, busTask);

                // 分别提醒检测人员
                BusRecordSamples brSamples = new BusRecordSamples();
                if (ou != null) {
                    brSamples = LabService.SearchBusRecordSamples(bt.getTaskid());
                }

                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(brSamples.getTaskid());
                busTodo.setBusflow("RecordNoPass");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                busTodo.setTranuser(brSamples.getTranuser());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype("29");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            // 删除原始记录审批提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(bt.getTaskid());
            bTodo.setBusflow("RecordApprove");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + bt.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (bt.getRecordstatus()) {
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

    // 多样品审核
    public static void SaveAuditsForSamples(BusTask bt, ReturnValue rtv, String signerpassword, OnlineUser ou,
            TranLog log) {
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
            BusRecordSamples bRecordSamples = new BusRecordSamples();
            bRecordSamples.setAduituser(ou.getUser().getUserid());
            bRecordSamples.setAduitusername(ou.getUser().getUsername());
            bRecordSamples.setAduitdate(new Date());
            bRecordSamples.setRecordstatus(bt.getRecordstatus());
            bRecordSamples.setTaskid(bt.getTaskid());
            bRecordSamples.setModifydesc(bt.getModifydesc());
            LabDao.SubmitBusRecordSamples(session, bRecordSamples);

            if (bt.getDeal().getAction() == DataAction.Submit.getAction()) {

                // //改变t_bus_task_sample的状态
                // BusTaskSample bts = new BusTaskSample();
                // bts.setTaskid(bt.getTaskid());
                // bts.setAuditstatus("94");
                // bts.setCheckdesc("");
                // bts.getDeal().setAction(DataAction.Special01.getAction());
                // LabDao.SaveBusTaskSample(session, bts);

                // 修改任务单状态
                bt.setFlowstatus("98");
                bt.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, bt);

                // 删除原始记录审批提醒
                BusTodo bTodo = new BusTodo();
                bTodo.setTranid(bt.getTaskid());
                bTodo.setBusflow("RecordAudit");
                bTodo.getDeal().setAction(DataAction.Special01.getAction());
                FlowDao.SaveBusTodo(session, bTodo);
            }
            // 审批不通过提醒检测人员
            if (bt.getDeal().getAction() == DataAction.InValid.getAction()) {

                // //改变t_bus_task_sample的状态
                // BusTaskSample bts = new BusTaskSample();
                // bts.setSampletran(bt.getTaskid());
                // bts.setAuditstatus("96");
                // bts.setCheckdesc(bt.getModifydesc());
                // bts.getDeal().setAction(DataAction.Special01.getAction());
                // LabDao.SaveBusTaskSample(session, bts);

                // 更新t_bus_task表的状态
                BusTask busTask = new BusTask();
                busTask.setTaskid(bt.getTaskid());
                busTask = LabDao.GetBusTask(busTask);
                busTask.setFlowstatus("99");
                busTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, busTask);

                // 分别提醒检测人员
                BusRecordSamples brSamples = new BusRecordSamples();
                if (ou != null) {
                    brSamples = LabService.SearchBusRecordSamples(bt.getTaskid());
                }

                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(brSamples.getTaskid());
                busTodo.setBusflow("RecordNoPass");
                busTodo.setFlownode("create");
                busTodo.setSenddate(new Date());
                busTodo.setTranuser(brSamples.getTranuser());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype("29");
                // 判定是否同一个提醒
                BusTodo btd = new BusTodo();
                btd = FlowDao.GetBusTodo(session, busTodo);

                if (btd == null || btd.getTranid().equals("")) {
                    busTodo.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveBusTodo(session, busTodo);
                }
            }

            // 删除原始记录审批提醒
            BusTodo bTodo = new BusTodo();
            bTodo.setTranid(bt.getTaskid());
            bTodo.setBusflow("RecordAudit");
            bTodo.getDeal().setAction(DataAction.Special01.getAction());
            FlowDao.SaveBusTodo(session, bTodo);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc("样品编号:" + bt.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            switch (bt.getRecordstatus()) {
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

    // 保存绿色采样单
    public static void SaveManBusEnv(BusGet item, List<BusGetDetail> details, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // 更新受检企业信息
            BusTestedUnit bUnit = new BusTestedUnit();

            if (item.getTestedid().equals("") || item.getTestedid() == null) {
                bUnit.setTestedname(item.getTestedname());
                bUnit.setEnterlegal(item.getEnterlegal());
                bUnit.setEntertele(item.getEntertele());
                bUnit.setEnterpost(item.getEnterpost());
                bUnit.setEntertype(item.getEntertype());
                bUnit.setEnterscale(item.getEnterscale());
                bUnit.setEnteraddr(item.getEnteraddr());
                bUnit.setEnterfax(item.getEnterfax());
                bUnit.getDeal().setAction(DataAction.Create.getAction());
            } else {
                bUnit.setTestedid(item.getTestedid());
                bUnit = BasDao.GetBusTestedUnit(bUnit);
                bUnit.setTestedname(item.getTestedname());
                bUnit.setEnterlegal(item.getEnterlegal());
                bUnit.setEntertele(item.getEntertele());
                bUnit.setEnterpost(item.getEnterpost());
                bUnit.setEntertype(item.getEntertype());
                bUnit.setEnterscale(item.getEnterscale());
                bUnit.setEnteraddr(item.getEnteraddr());
                bUnit.setEnterfax(item.getEnterfax());
                bUnit.getDeal().setAction(DataAction.Modify.getAction());
            }
            BasDao.SaveBusTestedUnit(session, bUnit);
            item.setTestedid(bUnit.getTestedid());

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.getDeal().setAction(DataAction.Special01.getAction());
                item.setClientsign(item.getTranidcn());
                item.setSampleuser(ou.getUser().getUserid());
                item.setSampleusername(ou.getUser().getUsername());
                item.setTranuser(ou.getUser().getUserid());
                item.setTranusername(ou.getUser().getUsername());
                item.setTrandate(new Date());
                item.setFlowaction("51");
                item.setFlowstatus("00");
                item.setSampleid(details.get(0).getSampleid());
            }

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGet busget = LabDao.GetBusGet(item);
                item.setClientsign(item.getClientsign());
                item.setSampleuser(ou.getUser().getUserid());
                item.setSampleusername(ou.getUser().getUsername());
                item.setTrandate(busget.getTrandate());
                item.setTranuser(busget.getTranuser());
                item.setTranusername(busget.getTranusername());
                item.setFlowaction(busget.getFlowaction());
                item.setFlowstatus(busget.getFlowstatus());
            }

            LabDao.SaveBusGet(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()
                    || item.getDeal().getAction() == DataAction.Special01.getAction()) {
                BusGetDetail delgetdetail = new BusGetDetail();
                delgetdetail.setTranid(item.getTranid());
                delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusGetDetail(session, delgetdetail);
                // 多样品流程
                if (item.getGettype().equals("09") || item.getGettype().equals("08")) {

                    List<String> sampleidss = new ArrayList<String>();
                    int samcount = 0;

                    for (BusGetDetail bDetail : details) {
                        if (sampleidss.toString().indexOf(bDetail.getSampleid()) == -1) {
                            if (samcount > 0) {
                                throw new Exception("‘" + bDetail.getSamplename() + "’检测项目与其他检测项目不是同类，请修改填写的检测项目信息！");
                            }

                            sampleidss.add(bDetail.getSampleid());
                            samcount++;
                        }
                    }
                }

                if (details.size() == 0) {
                    BusSampleParam delsampleparam = new BusSampleParam();
                    delsampleparam.setTranid(item.getTranid());
                    delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusSampleParam(session, delsampleparam);

                    BusCatalogParam delcatalogparam = new BusCatalogParam();
                    delcatalogparam.setTranid(item.getTranid());
                    delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusCatalogParam(session, delcatalogparam);
                } else {
                    for (BusGetDetail busgetdetail : details) {
                        String paramids = busgetdetail.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusSampleParam delparam = new BusSampleParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Delete.getAction());
                            LabDao.SaveBusSampleParam(session, delparam);
                        }
                    }

                    for (BusGetDetail busgetdetail : details) {

                        // 检测依据赋值
                        BasSample bSample = new BasSample();
                        bSample.setSampleid(busgetdetail.getSampleid());
                        bSample = BasDao.GetBasSample(session, bSample);
                        busgetdetail.setMainstandname(bSample.getMainstandname());
                        // 检测项目赋值
                        busgetdetail.setTranidcn(item.getTranidcn());
                        busgetdetail.setTestitems(busgetdetail.getParameternames());
                        busgetdetail.setTranid(item.getTranid());
                        busgetdetail.setTesttype(item.getTesttype());
                        busgetdetail.getDeal().setAction(DataAction.Special01.getAction());
                        LabDao.SaveBusGetDetail(session, busgetdetail);

                        String paramids = busgetdetail.getParameterids();
                        String sampleid = busgetdetail.getSampleid();

                        if (!paramids.equals("") && paramids != null) {
                            String[] paramnames = paramids.split(",");
                            for (String params : paramnames) {
                                String[] param = params.split(":");
                                BusSampleParam bussampleparam = new BusSampleParam();
                                bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                                bussampleparam.setTranid(item.getTranid());
                                bussampleparam.setSampleid(sampleid);
                                bussampleparam.setParameterid(param[0]);
                                bussampleparam.setParametername(param[1]);
                                bussampleparam.setTeststandard(param[2]);
                                bussampleparam.setTeststandardcode(param[3]);
                                bussampleparam.setJudgestandard(param[4]);
                                bussampleparam.setJudgestandardcode(param[5]);
                                bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                                LabDao.SaveBusSampleParam(session, bussampleparam);
                            }
                        }
                    }
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
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
    }

    public static void SaveManBusGreen(BusGet item, List<BusGetDetail> details, ReturnValue rtv, OnlineUser ou,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            if (item.getDeal().getAction() == DataAction.Create.getAction()) {
                item.getDeal().setAction(DataAction.Special02.getAction());
                item.setSampleuser(ou.getUser().getUserid());
                item.setSampleusername(ou.getUser().getUsername());
                item.setClientsign(item.getTranidcn());
                item.setTranuser(ou.getUser().getUserid());
                item.setTranusername(ou.getUser().getUsername());
                item.setTrandate(new Date());
                item.setFlowaction("51");
                item.setFlowstatus("00");
                item.setSampleid(details.get(0).getSampleid());
            }

            if (item.getDeal().getAction() == DataAction.Modify.getAction()) {
                BusGet busget = LabDao.GetBusGet(item);
                item.setSampleuser(ou.getUser().getUserid());
                item.setSampleusername(ou.getUser().getUsername());
                item.setClientsign(item.getTranidcn());
                item.setTrandate(busget.getTrandate());
                item.setTranuser(busget.getTranuser());
                item.setTranusername(busget.getTranusername());
                item.setFlowaction(busget.getFlowaction());
                item.setFlowstatus(busget.getFlowstatus());
            }

            LabDao.SaveBusGet(session, item);

            if (item.getDeal().getAction() == DataAction.Create.getAction()
                    || item.getDeal().getAction() == DataAction.Modify.getAction()
                    || item.getDeal().getAction() == DataAction.Special02.getAction()) {
                BusGetDetail delgetdetail = new BusGetDetail();
                delgetdetail.setTranid(item.getTranid());
                delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusGetDetail(session, delgetdetail);
                // 多样品流程
                if (item.getGettype().equals("09") || item.getGettype().equals("08")) {

                    List<String> sampleidss = new ArrayList<String>();
                    int samcount = 0;

                    for (BusGetDetail bDetail : details) {
                        if (sampleidss.toString().indexOf(bDetail.getSampleid()) == -1) {
                            if (samcount > 0) {
                                throw new Exception("‘" + bDetail.getSamplename() + "’检测项目与其他检测项目不是同类，请修改填写的检测项目信息！");
                            }

                            sampleidss.add(bDetail.getSampleid());
                            samcount++;
                        }
                    }
                }

                if (details.size() == 0) {
                    BusSampleParam delsampleparam = new BusSampleParam();
                    delsampleparam.setTranid(item.getTranid());
                    delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusSampleParam(session, delsampleparam);

                    BusCatalogParam delcatalogparam = new BusCatalogParam();
                    delcatalogparam.setTranid(item.getTranid());
                    delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                    LabDao.SaveBusCatalogParam(session, delcatalogparam);
                } else {
                    for (BusGetDetail busgetdetail : details) {
                        String paramids = busgetdetail.getParameterids();
                        if (!paramids.equals("") && paramids != null) {
                            BusSampleParam delparam = new BusSampleParam();
                            delparam.setTranid(item.getTranid());
                            delparam.getDeal().setAction(DataAction.Delete.getAction());
                            LabDao.SaveBusSampleParam(session, delparam);
                        }
                    }

                    for (BusGetDetail busgetdetail : details) {
                        busgetdetail.setTranidcn(item.getTranidcn());
                        // 检测依据赋值
                        BasSample bSample = new BasSample();
                        bSample.setSampleid(busgetdetail.getSampleid());
                        bSample = BasDao.GetBasSample(session, bSample);
                        busgetdetail.setMainstandname(bSample.getMainstandname());
                        // 检测项目赋值
                        busgetdetail.setTestitems(busgetdetail.getParameternames());

                        busgetdetail.setTranid(item.getTranid());
                        busgetdetail.setTesttype(item.getTesttype());
                        busgetdetail.getDeal().setAction(DataAction.Special01.getAction());
                        LabDao.SaveBusGetDetail(session, busgetdetail);

                    }
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
            e.printStackTrace();
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }

    }

    // 单样品任务单撤销
    public static void CancelBusTaskSingle(BusTask item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            String tranid = item.getTranid();
            String modifydesc = item.getModifydesc();
            Date nowdate = new Date();

            BusAccSample bas = new BusAccSample();
            bas.setTranid(tranid);
            ;
            BusAccSample bass = LabDao.GetBusAccSample(bas);
            bass.setFlowstatus("89");
            bass.getDeal().setAction(DataAction.Modify.getAction());
            LabDao.SaveBusAccSample(session, bass);

            // 将任务单状态修改为撤销状态
            BusTask bustask = new BusTask();
            SearchParams search = new SearchParams();
            search.setSearch(" a.Accsampleid  like '%" + tranid + "%' ");
            search.setStart(0);
            search.setEnd(50);
            bustask.setSearch(search);
            List<BusTask> basss = LabDao.SearchBusTask(bustask);
            for (BusTask baskt : basss) {
                baskt.setFlowstatus("89");
                baskt.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, baskt);

                // 设置提醒
                BusTodo busTodo = new BusTodo();
                busTodo.setTranid(baskt.getTaskid());
                busTodo.setBusflow("BusTaskCancel");
                busTodo.setFlownode("create");
                busTodo.setSenddate(nowdate);
                busTodo.setTranuser(baskt.getLabuser());
                busTodo.setTrandept(baskt.getLabid());
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype("30");
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);

                // 删除试验参数标准值
                BusTaskTest bustasktest = new BusTaskTest();
                bustasktest.getDeal().setAction(DataAction.Delete.getAction());
                bustasktest.setTaskid(baskt.getTaskid());
                LabDao.SaveBusTaskTest(session, bustasktest);

            }
            // 保存撤销原因
            BusRecordSamples bRecordSamples = new BusRecordSamples();
            bRecordSamples.setRecordstatus("89");
            bRecordSamples.setTaskid(tranid);
            bRecordSamples.setModifydesc(modifydesc);
            LabDao.SubmitBusRecordSamples(session, bRecordSamples);

            log.ActionToTran(DataAction.Deal.getAction());
            log.setTrandesc(bass.getSamplecode());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("撤销成功！");
            rtv.setData("{\"taskid\":\"" + bass.getSamplecode() + "\",\"issend\":\"true\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "撤销失败："));
            session.rollback();
        } finally {
            session.close();
        }

    }

    // 删除撤销提醒
    public static void DeleteTaskAlert(BusTask item, OnlineUser ou, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }
            // 防止数据库发生不可重复读 重新修改任务单状态
            BusTask bustask = new BusTask();
            bustask.setFlowstatus("89");
            bustask.setTaskid(item.getTaskid());
            bustask.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusTask(session, bustask);
            // 删除原始记录审批提醒
            BusTodo bt = new BusTodo();
            bt.setTranid(item.getTaskid());
            bt.setTranuser(ou.getUser().getUserid());
            bt.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, bt);

            // 保存撤销原因
            BusRecordSamples bRecordSamples = new BusRecordSamples();
            bRecordSamples.setRecordstatus("90");
            bRecordSamples.setTaskid(item.getTaskid());
            LabDao.SubmitBusRecordSamples(session, bRecordSamples);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_SAVE_S);
            rtv.setData("{\"taskid\":\"" + item.getTaskid() + "\"}");
            rtv.setBean(true);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }

    }

}
