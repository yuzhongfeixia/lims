package com.alms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.*;
import com.alms.entity.dev.*;
import com.alms.entity.file.*;
import com.alms.entity.flow.*;
import com.alms.entity.glp.*;
import com.alms.entity.inner.InnerYear;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.prd.*;
import com.alms.entity.std.StdReview;
import com.alms.enums.FlowNodeType;
import com.alms.enums.TodoType;
import com.gpersist.dao.PublicDao;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.FlowSubmit;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class FlowService {

    // region FlowAuth Methods

    public static void SaveFlowAuth(FlowAuth item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            item.setAuthuser(ou.getUser().getUserid());
            item.setAuthusername(ou.getUser().getUsername());
            FlowDao.SaveFlowAuth(session, item);

            // 添加审批人员
            FlowNodeUser node = new FlowNodeUser();
            node.setBusflow(item.getBusflow());
            node.setFlownode(item.getFlownode());
            node.setUserid(item.getBeauth());
            node.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveFlowNodeUser(session, node);

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

    // 取消授权
    public static void CancelFlowAuth(List<FlowAuth> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (FlowAuth detail : details) {
                detail.getDeal().setAction(DataAction.InValid.getAction());
                detail.setCanceldate(new Date());
                detail.setIscancel(true);
                FlowDao.SaveFlowAuth(session, detail);

                FlowAuth auth = new FlowAuth();
                auth.setTranid(detail.getTranid());
                auth = FlowDao.GetFlowAuth(auth);
                // 添加审批人员
                if (auth != null) {
                    FlowNodeUser node = new FlowNodeUser();
                    node.setBusflow(auth.getBusflow());
                    node.setFlownode(auth.getFlownode());
                    node.setUserid(auth.getBeauth());
                    node.getDeal().setAction(DataAction.InValid.getAction());
                    FlowDao.SaveFlowNodeUser(session, node);
                }
            }

            log.ActionToTran(DataAction.InValid.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("取消成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "取消失败!"));
        } finally {
            session.close();
        }
    }

    // endregion FlowAuth Methods

    // region FlowRole Methods

    public static void SaveFlowRole(FlowRole item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveFlowRole(session, item);

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

    // endregion FlowRole Methods

    // region GetListFlowNode
    public static List<FlowNode> GetListFlowNode(FlowNode node) {
        SqlSession session = DBUtils.getFactory();
        List<FlowNode> lists = new ArrayList<FlowNode>();
        FlowNodeRole fnr = new FlowNodeRole();

        char separator = ',';

        try {
            List<FlowNode> flowNodes = FlowDao.GetListFlowNode(node);
            for (FlowNode flowNode : flowNodes) {
                StringBuilder noderole = new StringBuilder();
                StringBuilder noderolename = new StringBuilder();

                fnr.setFlownode(flowNode.getFlownode());
                fnr.setBusflow(node.getBusflow());
                List<FlowNodeRole> roles = FlowDao.GetListFlowNodeRole(fnr);

                for (int i = 0; i < roles.size(); i++) {
                    noderole.append(roles.get(i).getNoderole());
                    noderolename.append(roles.get(i).getNoderolename());
                    if (i < roles.size() - 1) {
                        noderole.append(separator);
                        noderolename.append(separator);
                    }
                }
                flowNode.setNoderole(noderole.toString());
                flowNode.setNoderolenames(noderolename.toString());

                lists.add(flowNode);
            }
            return lists;

        } catch (Exception e) {
            return new ArrayList<FlowNode>();
        } finally {
            session.close();
        }
    }

    // endregion

    // region FlowButton Methods

    public static void SaveFlowButton(FlowButton item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveFlowButton(session, item);

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

    // endregion FlowButton Methods

    // region BusFlow Methods

    public static void SaveBusFlow(BusFlow item, List<FlowNode> details, List<FlowNodeButton> buttons, ReturnValue rtv,
            TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            for (FlowNode detail : details) {
                detail.setBusflow(item.getBusflow());
                detail.getDeal().setAction(DataAction.Create.getAction());

                if (detail.OnBeforeSave(errmsg)) {
                    rtv.setMsg(errmsg.getErrmsg());
                    return;
                }
            }

            FlowDao.SaveBusFlow(session, item);

            FlowNode delete = new FlowNode();
            delete.setBusflow(item.getBusflow());
            delete.getDeal().setAction(DataAction.Delete.getAction());
            FlowDao.SaveFlowNode(session, delete);

            // 删除执行角色
            FlowNodeRole role = new FlowNodeRole();
            role.setBusflow(item.getBusflow());
            role.getDeal().setAction(DataAction.Delete.getAction());
            FlowDao.SaveFlowNodeRole(session, role);

            // 删除按钮
            FlowNodeButton button = new FlowNodeButton();
            button.setBusflow(item.getBusflow());
            button.getDeal().setAction(DataAction.Delete.getAction());
            FlowDao.SaveFlowNodeButton(session, button);

            // 删除人员
            FlowNodeUser user = new FlowNodeUser();
            user.setBusflow(item.getBusflow());
            user.getDeal().setAction(DataAction.Delete.getAction());
            FlowDao.SaveFlowNodeUser(session, user);

            FlowRoleUser fUser = new FlowRoleUser();
            FlowNodeUser nUser = new FlowNodeUser();

            for (FlowNode detail : details) {
                detail.setBusflow(item.getBusflow());
                detail.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveFlowNode(session, detail);
                // 保存执行角色
                String[] noderoles = detail.getNoderole().split(",");

                for (String noderole : noderoles) {
                    role.setBusflow(item.getBusflow());
                    role.setFlownode(detail.getFlownode());
                    role.setNoderole(noderole);
                    role.getDeal().setAction(DataAction.Create.getAction());
                    FlowDao.SaveFlowNodeRole(session, role);
                    fUser.setNoderole(noderole);
                    List<FlowRoleUser> roleUsers = FlowDao.GetFlowRoleUserByRole(fUser);
                    for (FlowRoleUser rUser : roleUsers) {
                        nUser.setUserid(rUser.getUserid());
                        nUser.setBusflow(item.getBusflow());
                        nUser.setFlownode(detail.getFlownode());
                        nUser.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveFlowNodeUser(session, nUser);
                    }
                }
                // 保存样品明细
                for (FlowNodeButton btn : buttons) {
                    if (detail.getFlownode().equals(btn.getFlownode())) {
                        btn.setBusflow(item.getBusflow());
                        btn.getDeal().setAction(DataAction.Create.getAction());
                        FlowDao.SaveFlowNodeButton(session, btn);
                    }
                }
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getBusflow() + "-" + item.getBusflowname());
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

    public static void DeleteBusFlow(List<BusFlow> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (BusFlow detail : details) {
                detail.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveBusFlow(session, detail);
                // 删除执行角色
                FlowNodeRole role = new FlowNodeRole();
                role.setBusflow(detail.getBusflow());
                role.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveFlowNodeRole(session, role);

                // 删除按钮
                FlowNodeButton button = new FlowNodeButton();
                button.setBusflow(detail.getBusflow());
                button.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveFlowNodeButton(session, button);

                // 删除人员
                FlowNodeUser user = new FlowNodeUser();
                user.setBusflow(detail.getBusflow());
                user.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveFlowNodeUser(session, user);

                // 删除节点
                FlowNode node = new FlowNode();
                node.setBusflow(detail.getBusflow());
                node.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveFlowNode(session, node);

            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion BusFlow Methods

    // region BusTodo Methods

    public static void SaveBusTodo(BusTodo item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveBusTodo(session, item);

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

    // endregion BusTodo Methods

    // region BusFlowProcess Methods

    public static void SaveBusFlowProcess(BusFlowProcess item, List<FlowNode> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveBusFlowProcess(session, item);

            FlowNode delete = new FlowNode();
            delete.setBusflow(item.getBusflow());
            delete.getDeal().setAction(DataAction.Delete.getAction());
            FlowDao.SaveFlowNode(session, delete);

            for (FlowNode detail : details) {
                delete.setBusflow(item.getBusflow());
                detail.getDeal().setAction(DataAction.Create.getAction());

                FlowDao.SaveFlowNode(session, detail);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getBusflow() + "-" + item.getBusflowname());
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

    // endregion BusFlowProcess Methods

    // region Bus Flow Auto Methods

    public static void FlowCreate(SqlSession session, String busflow, ReturnValue rtv, TranLog log) throws Exception {
        BusTodo item = new BusTodo();
        item.setBusflow(busflow);

        FlowCreate(session, item, rtv, log);
    }

    public static void FlowCreate(SqlSession session, BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        ErrorMsg errmsg = new ErrorMsg();

        // 新建业务开始流程，并转入日志表，并转入后续流程，建立待办事宜

        if (item.OnBeforeSave(errmsg)) {
            throw new Exception(errmsg.getErrmsg());
        }

        // 根据业务流程代码读取该业务流的开始流程
        FlowNode cnode = FlowDao.GetFlowNodeByCreate(session, item.getBusflow());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("找不到业务流程的开始流程！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Bus.getTodotype());

        // 根据业务流程代码读取该业务流下一步流程（存在多个并行的流00程）
        cnode.setNodeserial(cnode.getNodeserial() + 1);
        List<FlowNode> cnexts = FlowDao.GetFlowNodeBySerial(session, cnode);

        if (cnexts.size() <= 0)
            throw new Exception("找不到业务流程的下一步流程！");

        FlowDao.BeginBusTodo(session, item);

        boolean islast = true;

        for (FlowNode next : cnexts) {
            if (next.getFlownode().equals(FlowNodeType.Finish.getFlownodetype()))
                continue;

            islast = false;
            item.setBusflow(next.getBusflow());
            item.setFlownode(next.getFlownode());

            FlowDao.InsertBusTodo(session, item);
        }

        if (islast) {

        }
    }

    public static void FlowDeal(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Bus.getTodotype());
        item.getDeal().setAction(DataAction.Check.getAction());
        FlowDao.DealBusTodoByUser(session, item);

    }

    public static void FlowBack(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Bus.getTodotype());
        item.getDeal().setAction(DataAction.UnCheck.getAction());
        FlowDao.DealBusTodoByUser(session, item);

    }

    // 审核下一步
    public static void FlowDeal(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowDeal(session, item, log);
            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("02");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审核驳回
    public static void FlowBack(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowBack(session, item, log);

            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("03");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批流程
    public static void FlowApprove(BusTodo item, ReturnValue rtv, OnlineUser ou, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowDeal(session, item, log);

            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("04");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            if (item.getBusflow().equals("IncFileChange")) {
                ChangeApply ca = new ChangeApply();
                ca.setTranid(item.getTranid());
                ca = IncFileDao.GetChangeApply(ca);
                BasFile basFile = new BasFile();
                basFile.setFileid(ca.getChangefileid());
                basFile.setFilename(ca.getReplacefilename());
                basFile.setFileurl(ca.getFileurl());
                // 替换只更新文件名和地址
                basFile.getDeal().setAction(5);
                IncFileDao.SaveBasFile(session, basFile);
            }

            if ((item.getTranid()).substring(0, 2).equals("AM") && item.getBusflow().equals("DevAccept")) {
                AcceptManage acceptManage = new AcceptManage();
                acceptManage.setTranid(item.getTranid());
                acceptManage = DevDao.GetAcceptManage(acceptManage);
                BuyApply buyapply = new BuyApply();
                buyapply.setTranid(acceptManage.getApplyid());
                buyapply = DevDao.GetBuyApply(buyapply);
                BasDev basdev = new BasDev();
                // basdev.setDevid(acceptManage.getDevid());
                basdev.setDevname(acceptManage.getDevname());
                basdev.setFactoryname(acceptManage.getFactoryname());
                basdev.setFactorycode(acceptManage.getFactorycode());
                basdev.setDevstandard(acceptManage.getDevstandard());
                basdev.setDevprice(acceptManage.getDevprice());
                basdev.setBuydate(acceptManage.getEnterdate());
                basdev.setDevtype(buyapply.getDevtype());
                basdev.setLabid(buyapply.getApplydept());
                basdev.setDevrange(buyapply.getDevrange());
                basdev.setDevmanager(acceptManage.getDevmanager());
                basdev.setDevmanagername(acceptManage.getDevmanagername());
                basdev.setCrtuser(ou.getUser().getUserid());
                basdev.setCrtusername(ou.getUser().getUsername());
                basdev.setCrtdate(new Date());
                basdev.setOperateuser(ou.getUser().getUserid());
                basdev.setOperateusername(ou.getUser().getUsername());
                basdev.setOperatedate(new Date());
                basdev.setDevstatus("00");
                // 添加设备操作人员 对应存储过程添加这两个字段
                basdev.setDevoperators(acceptManage.getDevoperators());
                basdev.setDevoperatorsname(acceptManage.getDevoperatorsname());
                basdev.getDeal().setAction(DataAction.Create.getAction());
                basdev.setAccepttranid(acceptManage.getTranid());
                DevDao.SaveBasDevByAccept(session, basdev);
            }

            if (item.getBusflow().equals("OperateDev")) {
                BasDev basDev = new BasDev();
                basDev.setDevid(item.getTranid());
                basDev = DevDao.GetBasDev(basDev);
                basDev.setDevstatus("04");
                basDev.getDeal().setAction(DataAction.Modify.getAction());
                DevDao.SaveBasDev(session, basDev);
            }

            if (item.getBusflow().equals("DevScrap")) {
                DevScrap devScrap = new DevScrap();
                devScrap.setTranid(item.getTranid());
                devScrap = DevDao.GetDevScrap(devScrap);
                BasDev basDev = new BasDev();
                basDev.setDevid(devScrap.getDevid());
                basDev = DevDao.GetBasDev(basDev);
                basDev.setDevstatus("03");
                basDev.getDeal().setAction(DataAction.Modify.getAction());
                DevDao.SaveBasDev(session, basDev);
            }

            // 内审年度计划最后通过时获取批准人姓名和批准时间
            if (item.getBusflow().equals("InnerYear")) {
                InnerYear innerYear = new InnerYear();
                innerYear.setTranid(item.getTranid());
                innerYear.setAllowusername(ou.getUser().getUsername());
                innerYear.setAllowdate(new Date());
                innerYear.getDeal().setAction(17);
                InnerDao.SaveInnerYear(session, innerYear);
            }

            // 试剂耗材验证在审批通过
            if (item.getBusflow().equals("PrdAccept")) {
                PrdVerify pVerify = new PrdVerify();
                pVerify.setVerifyid(item.getTranid());
                pVerify = PrdDao.GetPrdVerify(pVerify);

                PrdApplyDetail pDetail = new PrdApplyDetail();
                pDetail.setTranid(pVerify.getTranid());
                pDetail.setPrdserial(pVerify.getPrdserial());
                pDetail.getDeal().setAction(DataAction.Deal.getAction());
                PrdDao.SavePrdApplyDetail(session, pDetail);
            }
            // 仪器校准确认检测室更新校准结果
            if (item.getBusflow().equals("DevCalib") && ou.getUser().getDeptname().contains("检验")) {
                DevCalib dCalib = new DevCalib();
                dCalib.setTranid(item.getTranid());

                DevCalib dCalibitem = DevDao.GetDevCalibByTranID(dCalib);
                dCalibitem.setCalibresult(item.getTododesc());
                dCalibitem.getDeal().setAction(3);
                DevDao.SaveDevCalib(session, dCalibitem);
            }
            // 耗材申请检测室主任更新主任意见
            if (item.getBusflow().equals("PrdApply") && ou.getUser().getUserpostname().contains("室主任")) {
                PrdApply pApply = new PrdApply();
                pApply.setTranid(item.getTranid());
                pApply = PrdDao.GetPrdApply(pApply);
                pApply.setConfirmuser(item.getTranuser());
                pApply.setConfirmusername(item.getUsername());
                pApply.setConfirmdesc(item.getTododesc());
                pApply.setConfirmdate(item.getTrandate());
                pApply.getDeal().setAction(3);
                PrdDao.SavePrdApply(session, pApply);
            }
            //
            if (item.getBusflow().equals("GlpFileChange")) {
                GlpFileChange gfc = new GlpFileChange();
                gfc.setTranid(item.getTranid());
                gfc = GlpDao.GetGlpFileChange(gfc);

                GlpFile glpFile = new GlpFile();
                glpFile.setFileid(gfc.getChangefileid());
                glpFile.setFilename(gfc.getReplacefilename());
                glpFile.setFileurl(gfc.getFileurl());
                // 替换只更新文件名和地址
                glpFile.getDeal().setAction(5);
                GlpDao.SaveGlpFile(session, glpFile);
            }

            // 文件销毁登记确认后销毁相应文件
            if (item.getBusflow().equals("IncFileDestory")) {
                DestoryApply destoryApply = new DestoryApply();
                destoryApply.setTranid(item.getTranid());
                destoryApply = IncFileDao.GetDestoryApply(destoryApply);
                String deleteFileid = destoryApply.getFileid();
                BasFile deleteFile = new BasFile();
                deleteFile.setFileid(deleteFileid);
                deleteFile.getDeal().setAction(DataAction.Delete.getAction());
                IncFileDao.SaveBasFile(session, deleteFile);
            }

            // glp文件销毁登记确认后销毁相应文件
            if (item.getBusflow().equals("GlpFileDestory")) {
                GlpFileDestroy gfd = new GlpFileDestroy();
                gfd.setTranid(item.getTranid());
                gfd = GlpDao.GetGlpFileDestroy(gfd);
                String deleteGlpfileid = gfd.getFileid();
                GlpFile deleteGlpFile = new GlpFile();
                deleteGlpFile.setFileid(deleteGlpfileid);
                deleteGlpFile.getDeal().setAction(DataAction.Delete.getAction());
                GlpDao.SaveGlpFile(session, deleteGlpFile);
            }

            if (item.getBusflow().equals("DevUseApply")) {
                // 如果不为已借出则改为 已借出状态 如果是已借出
                DevUseApply stub = new DevUseApply();
                stub.setTranid(item.getTranid());
                stub.getItem().setGetaction("row");
                DevUseApply dua = DevDao.GetDevUseApply(stub);
                if (dua.getBorrowstatu().equals("05")) {
                    // 已借出- -需调拨
                    dua.setBorrowstatu("04");
                    // 插入设备调拨表
                    dua.getDeal().setAction(DataAction.Special01.getAction());
                } else {
                    // 未借出-借出申请-已借出
                    dua.setBorrowstatu("03");
                    dua.getDeal().setAction(DataAction.Modify.getAction());
                }
                // 魔法数不推荐
                // dua.getDeal().setAction(DataAction.Modify.getAction());
                DevDao.SaveDevUseApply(session, dua);
            }

            // 设备建档（验收）最后一步 审批通过 将 设备操作员写入设备仪器表
            if (item.getBusflow().equals("DevAccept")) {
                AcceptManage aManage = new AcceptManage();
                aManage.setTranid(item.getTranid());
                AcceptManage am = DevDao.GetAcceptManageByTranID(aManage);
                BasDev basDev = new BasDev();

            }
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批未通过流程
    public static void FlowDisapprove(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowBack(session, item, log);

            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("05");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 耗材招标外
    public static void PrdTenderIn(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowDeal(session, item, log);
            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("02");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            PrdApply prd = new PrdApply();
            prd.setTranid(item.getTranid());
            prd = PrdDao.GetPrdApply(prd);
            if (prd != null) {
                prd.setPrdsource("04");// prd来源：02 招标内 ， 04 招标外
                prd.getDeal().setAction(DataAction.Modify.getAction());
                PrdDao.SavePrdApply(session, prd);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 耗材招标内
    public static void PrdTenderOut(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

            if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
                throw new Exception("找不到业务流程的开始流程！");

            item.setTranuser(log.getTranuser());
            item.setTrandept(log.getTrandept());
            item.setBusflow(cnode.getBusflow());
            item.setFlownode(cnode.getFlownode());
            item.setTodotype(TodoType.Bus.getTodotype());

            FlowDao.DealBusTodo(session, item);

            cnode.setNodeserial(10); // 招标内节点
            List<FlowNode> cnexts = FlowDao.GetFlowNodeBySerial(session, cnode);

            if (cnexts.size() > 0) {

                for (FlowNode next : cnexts) {
                    if (next.getFlownode().equals(FlowNodeType.Finish.getFlownodetype()))
                        continue;

                    item.setBusflow(next.getBusflow());
                    item.setFlownode(next.getFlownode());

                    FlowDao.InsertBusTodo(session, item);
                }
            }

            BusTodoNow btNow = new BusTodoNow();
            btNow.setTranid(item.getTranid());
            btNow.setBusflow(item.getBusflow());
            btNow.setActusername(log.getTranuser());
            btNow.setTododesc(item.getTododesc());
            btNow.setTodostatusdesc(item.getTodostatusdesc());
            btNow.setTodostatus("02");// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                      // 06：申请通过
            btNow.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodoNow(session, btNow);

            PrdApply prd = new PrdApply();
            prd.setTranid(item.getTranid());
            prd = PrdDao.GetPrdApply(prd);
            if (prd != null) {
                prd.setPrdsource("02");// prd来源：02 招标内 ， 04 招标外
                prd.getDeal().setAction(DataAction.Modify.getAction());
                PrdDao.SavePrdApply(session, prd);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // endregion Bus Flow Auto Methods

    // region 文件授权查看

    public static void FlowCreateFile(SqlSession session, String busflow, ReturnValue rtv, TranLog log)
            throws Exception {
        BusTodo item = new BusTodo();
        item.setBusflow(busflow);

        FlowCreate(session, item, rtv, log);
    }

    public static void FlowCreateFile(SqlSession session, BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        ErrorMsg errmsg = new ErrorMsg();

        // 新建业务开始流程，并转入日志表，并转入后续流程，建立待办事宜

        if (item.OnBeforeSave(errmsg)) {
            throw new Exception(errmsg.getErrmsg());
        }

        // 根据业务流程代码读取该业务流的开始流程
        FlowNode cnode = FlowDao.GetFlowNodeByCreate(session, item.getBusflow());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("找不到业务流程的开始流程！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.File.getTodotype());
        // 根据业务流程代码读取该业务流下一步流程（存在多个并行的流00程）
        cnode.setNodeserial(cnode.getNodeserial() + 1);
        List<FlowNode> cnexts = FlowDao.GetFlowNodeBySerial(session, cnode);

        if (cnexts.size() <= 0)
            throw new Exception("找不到业务流程的下一步流程！");

        FlowDao.BeginBusTodo(session, item);

        boolean islast = true;

        for (FlowNode next : cnexts) {
            if (next.getFlownode().equals(FlowNodeType.Finish.getFlownodetype()))
                continue;

            islast = false;
            item.setBusflow(next.getBusflow());
            item.setFlownode(next.getFlownode());

            FlowDao.InsertBusTodo(session, item);
        }

        if (islast) {

        }
    }

    public static void FlowAuthFile(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.File.getTodotype());
        item.getDeal().setAction(DataAction.Check.getAction());
        FlowDao.DealBusTodoByUser(session, item);

    }

    // 保存todonow 表
    public static void FLowBusTodoNow(SqlSession session, BusTodo item, String todostatus, TranLog log)
            throws Exception {

        BusTodoNow btNow = new BusTodoNow();
        btNow.setTranid(item.getTranid());
        btNow.setBusflow(item.getBusflow());
        btNow.setActusername(log.getTranuser());
        btNow.setTododesc(item.getTododesc());
        btNow.setTodostatusdesc(item.getTodostatusdesc());
        btNow.setTodostatus(todostatus);// 01:提交 02：审核通过 03：审核未通过 04：审批通过 05：审批未通过
                                        // 06：申请通过
        btNow.getDeal().setAction(DataAction.Create.getAction());
        FlowDao.SaveBusTodoNow(session, btNow);
    }

    public static void FlowBackFile(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.File.getTodotype());

        FlowDao.DealBusTodoByUser(session, item);

    }

    // 审核通过
    public static void FlowAuthFile(BusTodo item, String password, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            IncFileOnline online = new IncFileOnline();
            online.setFlowaction("20"); // 审核
            online.setFlowstatus("30"); // 审核通过
            online.setTranid(item.getTranid());

            IncFilePassword pwd = new IncFilePassword();
            pwd.setUserid(log.getTranuser());
            pwd = IncFileDao.GetIncFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            online.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFileOnline(session, online);

            FlowAuthFile(session, item, log);
            FLowBusTodoNow(session, item, "02", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审核未通过
    public static void FlowBackFile(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            IncFileOnline online = new IncFileOnline();
            online.setFlowaction("20"); // 审核
            online.setFlowstatus("31"); // 审核未通过
            online.setTranid(item.getTranid());

            online.getDeal().setAction(DataAction.Modify.getAction());
            IncFileDao.SaveIncFileOnline(session, online);

            FlowBackFile(session, item, log);
            FLowBusTodoNow(session, item, "03", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批通过
    public static void FlowApproveFile(BusTodo item, String password, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            IncFileOnline online = new IncFileOnline();
            online.setFlowaction("21"); // 审核
            online.setFlowstatus("88"); // 审核通过
            online.setTranid(item.getTranid());

            IncFilePassword pwd = new IncFilePassword();
            pwd.setUserid(log.getTranuser());
            pwd = IncFileDao.GetIncFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            online.getDeal().setAction(DataAction.Deal.getAction());
            IncFileDao.SaveIncFileOnline(session, online);

            FlowAuthFile(session, item, log);
            FLowBusTodoNow(session, item, "04", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批未通过
    public static void FlowDisapproveFile(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            IncFileOnline online = new IncFileOnline();
            online.setFlowaction("21"); // 审核
            online.setFlowstatus("33"); // 审核未通过
            online.setTranid(item.getTranid());
            online.getDeal().setAction(DataAction.Deal.getAction());
            IncFileDao.SaveIncFileOnline(session, online);

            FlowBackFile(session, item, log);
            FLowBusTodoNow(session, item, "05", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // endregion 文件授权查看

    // region Glp文件授权查看

    public static void FlowCreateGlp(SqlSession session, String busflow, ReturnValue rtv, TranLog log)
            throws Exception {
        BusTodo item = new BusTodo();
        item.setBusflow(busflow);

        FlowCreate(session, item, rtv, log);
    }

    public static void FlowCreateGlp(SqlSession session, BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        ErrorMsg errmsg = new ErrorMsg();

        // 新建业务开始流程，并转入日志表，并转入后续流程，建立待办事宜

        if (item.OnBeforeSave(errmsg)) {
            throw new Exception(errmsg.getErrmsg());
        }

        // 根据业务流程代码读取该业务流的开始流程
        FlowNode cnode = FlowDao.GetFlowNodeByCreate(session, item.getBusflow());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("找不到业务流程的开始流程！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Glp.getTodotype());
        // 根据业务流程代码读取该业务流下一步流程（存在多个并行的流00程）
        cnode.setNodeserial(cnode.getNodeserial() + 1);
        List<FlowNode> cnexts = FlowDao.GetFlowNodeBySerial(session, cnode);

        if (cnexts.size() <= 0)
            throw new Exception("找不到业务流程的下一步流程！");

        FlowDao.BeginBusTodo(session, item);

        boolean islast = true;

        for (FlowNode next : cnexts) {
            if (next.getFlownode().equals(FlowNodeType.Finish.getFlownodetype()))
                continue;

            islast = false;
            item.setBusflow(next.getBusflow());
            item.setFlownode(next.getFlownode());

            FlowDao.InsertBusTodo(session, item);
        }

        if (islast) {

        }
    }

    public static void FlowAuthGlp(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Glp.getTodotype());
        item.getDeal().setAction(DataAction.Check.getAction());
        FlowDao.DealBusTodoByUser(session, item);

    }

    public static void FlowBackGlp(SqlSession session, BusTodo item, TranLog log) throws Exception {

        FlowNode cnode = FlowDao.GetFlowNodeByTodo(session, item.getTodoserial());

        if ((cnode == null) || (ToolUtils.StringIsEmpty(cnode.getBusflow())))
            throw new Exception("该流程您已批过！");

        item.setTranuser(log.getTranuser());
        item.setTrandept(log.getTrandept());
        item.setBusflow(cnode.getBusflow());
        item.setFlownode(cnode.getFlownode());
        item.setTodotype(TodoType.Glp.getTodotype());

        FlowDao.DealBusTodoByUser(session, item);

    }

    // 审核通过
    public static void FlowAuthGlp(BusTodo item, String password, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            GlpFileOnline online = new GlpFileOnline();
            online.setFlowaction("20"); // 审核
            online.setFlowstatus("30"); // 审核通过
            online.setTranid(item.getTranid());

            GlpFilePassword pwd = new GlpFilePassword();
            pwd.setUserid(log.getTranuser());
            pwd = GlpDao.GetGlpFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            online.getDeal().setAction(DataAction.Modify.getAction());
            GlpDao.SaveGlpFileOnline(session, online);

            FlowAuthGlp(session, item, log);
            FLowBusTodoNow(session, item, "02", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审核未通过
    public static void FlowBackGlp(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            GlpFileOnline online = new GlpFileOnline();
            online.setFlowaction("20"); // 审核
            online.setFlowstatus("31"); // 审核未通过
            online.setTranid(item.getTranid());

            online.getDeal().setAction(DataAction.Modify.getAction());
            GlpDao.SaveGlpFileOnline(session, online);

            FlowBackGlp(session, item, log);
            FLowBusTodoNow(session, item, "03", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批通过
    public static void FlowApproveGlp(BusTodo item, String password, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            GlpFileOnline online = new GlpFileOnline();
            online.setFlowaction("21"); // 审核
            online.setFlowstatus("88"); // 审核通过
            online.setTranid(item.getTranid());

            GlpFilePassword pwd = new GlpFilePassword();
            pwd.setUserid(log.getTranuser());
            pwd = GlpDao.GetGlpFilePassword(pwd);

            if (pwd == null) {
                rtv.setMsg("您还未设置密码！");
                return;
            }

            if (ToolUtils.StringIsEmpty(password) || !pwd.getFilepassword().equals(ToolUtils.GetMD5(password))) {
                rtv.setMsg("密码不正确！");
                return;
            }

            online.getDeal().setAction(DataAction.Deal.getAction());
            GlpDao.SaveGlpFileOnline(session, online);

            FlowAuthGlp(session, item, log);
            FLowBusTodoNow(session, item, "04", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // 审批未通过
    public static void FlowDisapproveGlp(BusTodo item, ReturnValue rtv, TranLog log) throws Exception {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            GlpFileOnline online = new GlpFileOnline();
            online.setFlowaction("21"); // 审核
            online.setFlowstatus("33"); // 审核未通过
            online.setTranid(item.getTranid());
            online.getDeal().setAction(DataAction.Deal.getAction());
            GlpDao.SaveGlpFileOnline(session, online);

            FlowBackGlp(session, item, log);
            FLowBusTodoNow(session, item, "05", log);
            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    // endregion Glp文件授权查看

    // region FlowSubmit Methods

    public static void SaveFlowSubmit(FlowSubmit item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            BusTodo todo = new BusTodo();
            item.getDeal().setUserid(log.getTranuser());
            item.getDeal().setUsername(log.getUsername());
            todo.setTranuser(log.getTranuser());
            todo.setBusflow(item.getBusflow());
            todo.setFlownode(item.getFlownode());
            todo.setTododesc(item.getFlowdesc());
            todo.getDeal().setAction(item.getDeal().getAction());
            todo.setTodotype(TodoType.Bus.getTodotype());

            for (String tranid : item.getTranids()) {
                item.setSubmittranid(tranid);

                FlowDao.SaveFlowSubmit(session, item);

                todo.setTranid(tranid);

                FlowDao.DealBusTodoByUser(session, todo);
            }

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTranid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("处理成功！");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_CHECK_F));
        } finally {
            session.close();
        }
    }

    public static void SaveFlowSubmit(SqlSession session, FlowSubmit item, ReturnValue rtv, TranLog log) {

        BusTodo todo = new BusTodo();
        item.getDeal().setUserid(log.getTranuser());
        item.getDeal().setUsername(log.getUsername());
        todo.setTranuser(log.getTranuser());
        todo.setBusflow(item.getBusflow());
        todo.setFlownode(item.getFlownode());
        todo.setTododesc(item.getFlowdesc());
        todo.getDeal().setAction(item.getDeal().getAction());
        todo.setTodotype(TodoType.Bus.getTodotype());

        for (String tranid : item.getTranids()) {
            todo.setTranid(tranid);

            FlowDao.DealBusTodoByUser(session, todo);
        }
    }

    // endregion FlowSubmit Methods
    // region FlowRole Methods
    public static void DeleteFlowRole(FlowRole item, List<FlowRole> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (FlowRole detail : details) {
                detail.getDeal().setAction(DataAction.Delete.getAction());
                FlowDao.SaveFlowRole(session, detail);
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }
    // end region FlowRole Methods
    // region FlowRoleUser Methods

    public static void SaveFlowRoleUser(FlowRoleUser item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveFlowRoleUser(session, item);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getNoderole() + "-" + item.getUserid());
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

    public static void DeleteFlowRoleUser(FlowRoleUser item, List<FlowRoleUser> details, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {

            for (FlowRoleUser detail : details) {
                detail.getDeal().setAction(DataAction.Delete.getAction());

                FlowDao.SaveFlowRoleUser(session, detail);
            }

            log.ActionToTran(DataAction.Delete.getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg(Consts.STR_DELETE_S);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, Consts.STR_DELETE_F));
        } finally {
            session.close();
        }
    }

    // endregion FlowRoleUser Methods

    // region FlowNodeButton Methods

    public static void SaveFlowNodeButton(FlowNodeButton item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveFlowNodeButton(session, item);

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

    // endregion FlowNodeButton Methods

    // region BusTodoNow Methods

    public static void SaveBusTodoNow(BusTodoNow item, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            FlowDao.SaveBusTodoNow(session, item);

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

    // endregion BusTodoNow Methods
    public static void SaveFileFlow(BusTaskAttach item, ReturnValue rtv, OnlineUser ou, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (item.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            // item.getDeal().setAction(DataAction.Delete.getAction());
            // LabDao.SaveBusTaskAttach(session, item);

            item.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusTaskAttach(session, item);

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
}
