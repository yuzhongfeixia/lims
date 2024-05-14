package com.alms.action;

import java.util.List;

import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.FlowDao;
import com.alms.entity.flow.*;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.service.DevService;
import com.alms.service.FlowFmtService;
import com.alms.service.FlowService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.JsonDateValueProcessor;
import com.gpersist.utils.ToolUtils;

public class FlowAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region FlowNodeRole

    private FlowNodeRole fnr;

    public FlowNodeRole getFnr() {
        if (fnr == null)
            fnr = new FlowNodeRole();

        return fnr;
    }

    public void setFnr(FlowNodeRole fnr) {
        this.fnr = fnr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRoleAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String noderole = this.getFnr().getNoderole();
            this.getFnr().getSearch().setUserid(ou.getUser().getUserid());
            if (!ToolUtils.CheckComboValue(noderole))
                search += ToolUtils.GetAndSearch(search) + " a.noderole = '" + noderole + "' ";

            this.SetSearch(this.getFnr().getSearch(), this.getFnr().getItem(), ou, search);

            List<FlowNodeRole> lists = FlowDao.SearchRoleAuth(this.getFnr());

            ToolUtils.OutString(this.OutLists(lists, this.getFnr().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion FlowNodeRole

    // region FlowAuth Methods

    private FlowAuth fa;

    public FlowAuth getFa() {
        if (fa == null)
            fa = new FlowAuth();

        return fa;
    }

    public void setFa(FlowAuth fa) {
        this.fa = fa;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowAuth rtn = new FlowAuth();
        if (ou != null) {
            rtn = FlowDao.GetFlowAuth(this.getFa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFlowAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FlowAuth> lists = FlowDao.GetListFlowAuth(this.getFa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 01 - 授权 - 授权业务", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFlowAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getFa().getSearch().setStart(start + 1);
            this.getFa().getSearch().setEnd(this.GetEndCnt());
            this.getFa().getSearch().setUserid(ou.getUser().getUserid());
            String search = "";
            String busflowname = ToolUtils.Decode(this.getFnr().getBusflowname()).trim();
            String beauthname = ToolUtils.Decode(this.getFa().getBeauthname()).trim();
            if (!ToolUtils.StringIsEmpty(beauthname))
                search += ToolUtils.GetAndSearch(search) + " a.beauthname like '%" + beauthname + "%' ";
            if (!ToolUtils.StringIsEmpty(busflowname))
                search += ToolUtils.GetAndSearch(search) + " d.busflowname like '%" + busflowname + "%' ";
            this.SetSearch(this.getFa().getSearch(), this.getFa().getItem(), ou, search);
            List<FlowAuth> lists = FlowDao.SearchFlowAuth(this.getFa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 01 - 授权 - 授权业务", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFlowAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.SaveFlowAuth(this.getFa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String CancelFlowAuth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<FlowAuth> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), FlowAuth.class);

            FlowService.CancelFlowAuth(deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FlowAuth Methods

    // region FlowRole Methods

    private FlowRole fr;

    public FlowRole getFr() {
        if (fr == null)
            fr = new FlowRole();

        return fr;
    }

    public void setFr(FlowRole fr) {
        this.fr = fr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowRole rtn = new FlowRole();
        if (ou != null) {
            rtn = FlowDao.GetFlowRole(this.getFr());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFlowRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FlowRole> lists = FlowDao.GetListFlowRole(this.getFr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3062-工作流-节点执行角色", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFlowRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String noderole = this.getFr().getNoderole();
            String noderolename = ToolUtils.Decode(this.getFr().getNoderolename()).trim();
            if (!ToolUtils.CheckComboValue(noderolename))
                search += ToolUtils.GetAndSearch(search) + " a.noderolename like '%" + noderolename + "%' ";
            if (!ToolUtils.CheckComboValue(noderole))
                search += ToolUtils.GetAndSearch(search) + " a.noderole = '" + noderole + "' ";
            this.SetSearch(this.getFr().getSearch(), this.getFr().getItem(), ou, search);
            List<FlowRole> lists = FlowDao.SearchFlowRole(this.getFr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFr().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3062-工作流-节点执行角色", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFlowRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0913");
            FlowService.SaveFlowRole(this.getFr(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String DeleteFlowRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0913");

            List<FlowRole> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), FlowRole.class);
            FlowService.DeleteFlowRole(this.getFr(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }
    // endregion FlowRole Methods

    // region FlowNode Methods

    private FlowNode fn;

    public FlowNode getFn() {
        if (fn == null)
            fn = new FlowNode();

        return fn;
    }

    public void setFn(FlowNode fn) {
        this.fn = fn;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowNode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowNode rtn = new FlowNode();
        if (ou != null) {
            rtn = FlowDao.GetFlowNode(this.getFn());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFlowNode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FlowNode> lists = FlowService.GetListFlowNode(this.getFn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("3052-工作流-业务节点策略", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion FlowNode Methods

    // region FlowButton Methods

    private FlowButton fb;

    public FlowButton getFb() {
        if (fb == null)
            fb = new FlowButton();

        return fb;
    }

    public void setFb(FlowButton fb) {
        this.fb = fb;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowButton rtn = new FlowButton();
        if (ou != null) {
            rtn = FlowDao.GetFlowButton(this.getFb());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFlowButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getFb().getSearch().setStart(start + 1);
            this.getFb().getSearch().setEnd(this.GetEndCnt());
            String search = "";
            String flowbtn = ToolUtils.Decode(this.getFb().getFlowbtn()).trim();
            String flowbtnname = ToolUtils.Decode(this.getFb().getFlowbtnname()).trim();

            if (!ToolUtils.StringIsEmpty(flowbtn))
                search += ToolUtils.GetAndSearch(search) + " a.flowbtn like '%" + flowbtn + "%' ";

            if (!ToolUtils.CheckComboValue(flowbtnname))
                search += ToolUtils.GetAndSearch(search) + " a.flowbtnname like '%" + flowbtnname + "%' ";

            this.SetSearch(this.getFb().getSearch(), this.getFb().getItem(), ou, search);

            List<FlowButton> lists = FlowDao.SearchFlowButton(this.getFb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFb().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3061-工作流-按钮类型", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFlowButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0917");
            FlowService.SaveFlowButton(this.getFb(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FlowButton Methods

    // region BusFlow Methods

    private BusFlow flow;

    public BusFlow getFlow() {
        if (flow == null)
            flow = new BusFlow();

        return flow;
    }

    public void setFlow(BusFlow flow) {
        this.flow = flow;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusFlow rtn = new BusFlow();
        if (ou != null) {
            rtn = FlowDao.GetBusFlow(this.getFlow());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String busflowname = ToolUtils.Decode(this.getFlow().getBusflowname());

            if (!ToolUtils.StringIsEmpty(busflowname))
                search += ToolUtils.GetAndSearch(search) + " a.busflowname like '%" + busflowname + "%' ";

            this.SetSearch(this.getFlow().getSearch(), this.getFlow().getItem(), ou, search);

            List<BusFlow> lists = FlowDao.SearchBusFlow(this.getFlow());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFlow().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("流程定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0911");
            List<FlowNode> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), FlowNode.class);
            List<FlowNodeButton> buttons = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("dbuttons"), FlowNodeButton.class);
            FlowService.SaveBusFlow(this.getFlow(), details, buttons, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String DeleteBusFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0911");

            List<BusFlow> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BusFlow.class);

            FlowService.DeleteBusFlow(deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusFlow Methods

    // region BusTodo Methods

    private BusTodo todo;

    public BusTodo getTodo() {
        if (todo == null)
            todo = new BusTodo();

        return todo;
    }

    public void setTodo(BusTodo todo) {
        this.todo = todo;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTodo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTodo rtn = new BusTodo();
        if (ou != null) {
            rtn = FlowDao.GetBusTodo(this.getTodo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTodo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTodo> lists = FlowDao.GetListBusTodo(this.getTodo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("流程", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String GetBusTodoByTran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTodo> lists = FlowDao.GetBusTodoByTran(this.getTodo());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String GetTodoHtmlByTran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getTodo().setTranid(ToolUtils.Decode(this.getTodo().getTranid()));
            List<BusTodo> lists = FlowDao.GetBusTodoByTran(this.getTodo());

            String[] fmts = { "序号:listserial:5", "节点:flownodename:8", "执行人:username:12", "下达日期:senddate:10",
                    "执行日期:trandate:10", "结果:todostatusdesc:10", "说明:tododesc:45" };

            ToolUtils.OutString(FlowFmtService.GetListHtml(lists, "业务流程明细", fmts, true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_HTML);
        }

        return null;
    }

    public String GetTodoHtmlByTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTodo> lists = FlowDao.GetBusTodoByTest(this.getTodo());

            String[] fmts = { "序号:listserial:5", "节点:flownodename:8", "执行人:username:16", "下达日期:senddate:10",
                    "执行日期:trandate:10", "结果:todostatusdesc:10", "说明:tododesc:41" };

            ToolUtils.OutString(FlowFmtService.GetListHtml(lists, "业务流程明细", fmts, true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_HTML);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTodo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = " a.tranuser = '" + ou.getUser().getUserid() + "' and a.isnowflow = 1 ";
            String busflow = this.getTodo().getBusflow();
            String flownode = this.getTodo().getFlownode();
            String tranid = this.getTodo().getTranid();
            String begindate = this.getTodo().getSearch().getBegindate();
            String enddate = this.getTodo().getSearch().getEnddate();
            String labid = this.getTodo().getLabid();
            String todotype = this.getTodo().getTodotype();

            if (!ToolUtils.StringIsEmpty(enddate))
                search += ToolUtils.GetAndSearch(search) + " a.senddate <= " + ToolUtils.GetEndDate(enddate) + " ";

            if (!ToolUtils.StringIsEmpty(begindate))
                search += ToolUtils.GetAndSearch(search) + " a.senddate >= " + ToolUtils.GetBeginDate(begindate) + " ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid.trim() + "%' ";

            if (!ToolUtils.StringIsEmpty(todotype))
                search += ToolUtils.GetAndSearch(search) + " a.todotype = '" + todotype + "' ";

            if (!ToolUtils.StringIsEmpty(busflow))
                search += ToolUtils.GetAndSearch(search) + " a.busflow = '" + busflow + "' ";

            if (!ToolUtils.StringIsEmpty(flownode))
                search += ToolUtils.GetAndSearch(search) + " a.flownode = '" + flownode + "' ";

            if (!ToolUtils.AllComboValue(labid))
                search += ToolUtils.GetAndSearch(search) + " a.labid  = '" + labid + "' ";

            this.SetSearch(this.getTodo().getSearch(), this.getTodo().getItem(), ou, search);

            List<BusTodo> lists = FlowDao.SearchBusTodo(this.getTodo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTodo().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("待办事宜", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    private HBusTodo htodo;

    public HBusTodo getHtodo() {
        if (htodo == null)
            htodo = new HBusTodo();

        return htodo;
    }

    public void setHtodo(HBusTodo htodo) {
        this.htodo = htodo;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetHBusTodo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        HBusTodo rtn = new HBusTodo();
        if (ou != null) {
            rtn = FlowDao.GetHBusTodo(this.getHtodo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTodo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.SaveBusTodo(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetMyTodoCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getTodo().setTranuser(ou.getUser().getUserid());
            FlowDao.GetMyTodoCount(this.getTodo());
        }

        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
        config.registerPropertyExclusions(this.getTodo().getClass(), this.getTodo().OnCountExclusions());

        ToolUtils.OutString(ToolUtils.GetJsonFromBean(this.getTodo(), config));

        return null;
    }

    public String GetMyTodoAlert() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTodo> lists = FlowDao.GetMyTodoAlert(ou.getUser().getUserid());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // 审核流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowDeal(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowBack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowBack(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowApprove(this.getTodo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowDisapprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowDisapprove(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 耗材招标内
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String PrdTenderIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.PrdTenderIn(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 耗材招标外
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String PrdTenderOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.PrdTenderOut(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchMyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String busflow = this.getTodo().getBusflow();
            String flownode = this.getTodo().getFlownode();
            String tranid = this.getTodo().getTranid().trim();
            String begindate = this.getTodo().getSearch().getBegindate();
            String enddate = this.getTodo().getSearch().getEnddate();
            String labid = this.getTodo().getLabid();

            this.getTodo().getSearch().setUserid(ou.getUser().getUserid());

            if (!ToolUtils.StringIsEmpty(enddate))
                search += ToolUtils.GetAndSearch(search) + " d.senddate <= " + ToolUtils.GetEndDate(enddate) + " ";

            if (!ToolUtils.StringIsEmpty(begindate))
                search += ToolUtils.GetAndSearch(search) + " d.senddate >= " + ToolUtils.GetBeginDate(begindate) + " ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " d.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(busflow))
                search += ToolUtils.GetAndSearch(search) + " d.busflow = '" + busflow + "' ";

            if (!ToolUtils.StringIsEmpty(flownode))
                search += ToolUtils.GetAndSearch(search) + " d.flownode = '" + flownode + "' ";

            if (!ToolUtils.AllComboValue(labid))
                search += ToolUtils.GetAndSearch(search) + " d.labid  = '" + labid + "' ";

            this.SetSearch(this.getTodo().getSearch(), this.getTodo().getItem(), ou, search);

            List<BusTodo> lists = FlowDao.SearchMyApply(this.getTodo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTodo().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("我的申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchYueZhi() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String busflow = this.getTodo().getBusflow();
            String flownode = this.getTodo().getFlownode();
            String tranid = this.getTodo().getTranid();
            String begindate = this.getTodo().getSearch().getBegindate();
            String enddate = this.getTodo().getSearch().getEnddate();
            String labid = this.getTodo().getLabid();

            this.getTodo().getSearch().setUserid(ou.getUser().getUserid());

            if (!ToolUtils.StringIsEmpty(enddate))
                search += ToolUtils.GetAndSearch(search) + " d.senddate <= " + ToolUtils.GetEndDate(enddate) + " ";

            if (!ToolUtils.StringIsEmpty(begindate))
                search += ToolUtils.GetAndSearch(search) + " d.senddate >= " + ToolUtils.GetBeginDate(begindate) + " ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " d.tranid like '%" + tranid.trim() + "%' ";

            if (!ToolUtils.StringIsEmpty(busflow))
                search += ToolUtils.GetAndSearch(search) + " d.busflow = '" + busflow + "' ";

            if (!ToolUtils.StringIsEmpty(flownode))
                search += ToolUtils.GetAndSearch(search) + " d.flownode = '" + flownode + "' ";

            if (!ToolUtils.AllComboValue(labid))
                search += ToolUtils.GetAndSearch(search) + " d.labid  = '" + labid + "' ";

            this.SetSearch(this.getTodo().getSearch(), this.getTodo().getItem(), ou, search);

            List<BusTodo> lists = FlowDao.SearchYueZhi(this.getTodo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTodo().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("阅知事项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusTodo Methods

    // region 文件授权
    // 审核流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowAuthFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            FlowService.FlowAuthFile(this.getTodo(), password, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowBackFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowBackFile(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowApproveFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            FlowService.FlowApproveFile(this.getTodo(), password, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowDisapproveFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowDisapproveFile(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion 文件授权

    // region Glp文件授权
    // 审核流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowAuthGlp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            FlowService.FlowAuthGlp(this.getTodo(), password, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowBackGlp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowBackGlp(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程通过
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowApproveGlp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            FlowService.FlowApproveGlp(this.getTodo(), password, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审批流程驳回
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String FlowDisapproveGlp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.FlowDisapproveGlp(this.getTodo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion Glp文件授权

    // region FlowSubmit Methods

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Bean)
    public String SaveFlowSubmit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.SaveFlowSubmit(this.getPubflow(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FlowSubmit Methods

    // region FlowRoleUser Methods

    private FlowRoleUser fruser;

    public FlowRoleUser getFruser() {
        if (fruser == null)
            fruser = new FlowRoleUser();

        return fruser;
    }

    public void setFruser(FlowRoleUser fruser) {
        this.fruser = fruser;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowRoleUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowRoleUser rtn = new FlowRoleUser();
        if (ou != null) {
            rtn = FlowDao.GetFlowRoleUser(this.getFruser());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetFlowRoleUserByRole() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FlowRoleUser> lists = FlowDao.GetFlowRoleUserByRole(this.getFruser());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFlowRoleUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String noderole = this.getFruser().getNoderole();
            String deptid = this.getFruser().getDeptid();

            if (!ToolUtils.StringIsEmpty(deptid))
                search += ToolUtils.GetAndSearch(search) + " c.deptid like '%" + deptid + "%' ";

            if (!ToolUtils.CheckComboValue(noderole))
                search += ToolUtils.GetAndSearch(search) + " a.noderole = '" + noderole + "' ";

            this.SetSearch(this.getFruser().getSearch(), this.getFruser().getItem(), ou, search);

            List<FlowRoleUser> lists = FlowDao.SearchFlowRoleUser(this.getFruser());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFruser().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("角色类型人员定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveFlowRoleUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0915");
            FlowService.SaveFlowRoleUser(this.getFruser(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String DeleteFlowRoleUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0915");

            List<FlowRoleUser> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), FlowRoleUser.class);

            FlowService.DeleteFlowRoleUser(this.getFruser(), deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FlowRoleUser Methods

    // region FlowNodeButton Methods

    private FlowNodeButton nodebutton;

    public FlowNodeButton getNodebutton() {
        if (nodebutton == null)
            nodebutton = new FlowNodeButton();

        return nodebutton;
    }

    public void setNodebutton(FlowNodeButton nodebutton) {
        this.nodebutton = nodebutton;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFlowNodeButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FlowNodeButton rtn = new FlowNodeButton();
        if (ou != null) {
            rtn = FlowDao.GetFlowNodeButton(this.getNodebutton());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFlowNodeButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FlowNodeButton> lists = FlowDao.GetListFlowNodeButton(this.getNodebutton());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3053-工作流-业务节点按钮策略", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFlowNodeButtonByFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            List<FlowNodeButton> lists = FlowDao.GetListFlowNodeButtonByFlow(this.getNodebutton());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getNodebutton().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3053-工作流-业务节点按钮策略", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFlowNodeButton() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.SaveFlowNodeButton(this.getNodebutton(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FlowNodeButton Methods

    // region BusTodoNow Methods

    private BusTodoNow todonow;

    public BusTodoNow getTodonow() {
        if (todonow == null)
            todonow = new BusTodoNow();

        return todonow;
    }

    public void setTodonow(BusTodoNow todonow) {
        this.todonow = todonow;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTodoNow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTodoNow rtn = new BusTodoNow();
        if (ou != null) {
            rtn = FlowDao.GetBusTodoNow(this.getTodonow());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTodoNow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTodoNow> lists = FlowDao.GetListBusTodoNow(this.getTodonow());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3043-工作流-当前流程明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTodoNow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getTodonow().getSearch().setStart(start + 1);
            this.getTodonow().getSearch().setEnd(this.GetEndCnt());

            List<BusTodoNow> lists = FlowDao.SearchBusTodoNow(this.getTodonow());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTodonow().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("3043-工作流-当前流程明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTodoNow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FlowService.SaveBusTodoNow(this.getTodonow(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusTodoNow Methods

    // region buyapply
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String JudgeBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.JudgeBuyApply(this.getTodo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String UnJudgeBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.UnJudgeBuyApply(this.getTodo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }
    // endregion buyapply

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFileFlow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1387");

            String attachname = ToolUtils.Encode(ServletActionContext.getRequest().getParameter("attachname"));
            String attachurl = ServletActionContext.getRequest().getParameter("attachurl");
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            BusTaskAttach dbta = new BusTaskAttach();
            dbta.setTranid(tranid);
            dbta.setAttachname(attachname);
            dbta.setAttachtype("05");
            dbta.setAttachurl(attachurl);
            dbta.setTaskid(ou.getUser().getUsername());
            FlowService.SaveFileFlow(dbta, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

}
