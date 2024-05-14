package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.QuanDao;
import com.alms.entity.quan.*;
import com.alms.service.FlowFmtService;
import com.alms.service.QuanService;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class QuanAction extends BasAction {

    private static final long serialVersionUID = 1L;

    // region QuanMonitSamItem Methods

    private QuanMonitSamItem qms;

    public QuanMonitSamItem getQms() {
        if (qms == null)
            qms = new QuanMonitSamItem();

        return qms;
    }

    public void setQms(QuanMonitSamItem qms) {
        this.qms = qms;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetQuanMonitSamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanMonitSamItem rtn = new QuanMonitSamItem();
        if (ou != null) {
            rtn = QuanDao.GetQuanMonitSamItem(this.getQms());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanMonitSamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanMonitSamItem> lists = QuanDao.GetListQuanMonitSamItem(this.getQms());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("2 监督 - 小项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchQuanMonitSamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getQms().getSearch().setStart(start + 1);
            this.getQms().getSearch().setEnd(this.GetEndCnt());

            List<QuanMonitSamItem> lists = QuanDao.SearchQuanMonitSamItem(this.getQms());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQms().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("2 监督 - 小项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanMonitSamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            QuanService.SaveQuanMonitSamItem(this.getQms(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion QuanMonitSamItem Methods

    // region QuanMonitBigItem Methods

    private QuanMonitBigItem qmb;

    public QuanMonitBigItem getQmb() {
        if (qmb == null)
            qmb = new QuanMonitBigItem();

        return qmb;
    }

    public void setQmb(QuanMonitBigItem qmb) {
        this.qmb = qmb;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetQuanMonitBigItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanMonitBigItem rtn = new QuanMonitBigItem();
        if (ou != null) {
            rtn = QuanDao.GetQuanMonitBigItem(this.getQmb());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanMonitBigItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanMonitBigItem> lists = QuanDao.GetListQuanMonitBigItem(this.getQmb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1-监督 - 大项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchQuanMonitBigItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getQmb().getSearch().setStart(start + 1);
            this.getQmb().getSearch().setEnd(this.GetEndCnt());

            List<QuanMonitBigItem> lists = QuanDao.SearchQuanMonitBigItem(this.getQmb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQmb().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1-监督 - 大项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanMonitBigItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            QuanService.SaveQuanMonitBigItem(this.getQmb(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion QuanMonitBigItem Methods

    // region QuanMonitWorkDetail Methods

    private QuanMonitWorkDetail qmwk;

    public QuanMonitWorkDetail getQmwk() {
        if (qmwk == null)
            qmwk = new QuanMonitWorkDetail();

        return qmwk;
    }

    public void setQmwk(QuanMonitWorkDetail qmwk) {
        this.qmwk = qmwk;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanMonitWorkDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanMonitWorkDetail> lists = QuanDao.GetListQuanMonitWorkDetail(this.getQmwk());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 6 监督 - 监督工作记录详细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion QuanMonitWorkDetail Methods

    // region QuanMonitWork Methods

    private QuanMonitWork qmw;

    public QuanMonitWork getQmw() {
        if (qmw == null)
            qmw = new QuanMonitWork();

        return qmw;
    }

    public void setQmw(QuanMonitWork qmw) {
        this.qmw = qmw;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetQuanMonitWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanMonitWork rtn = new QuanMonitWork();
        if (ou != null) {
            rtn = QuanDao.GetQuanMonitWork(this.getQmw());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchQuanMonitWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQmw().getTranid()).trim();
            String monitusername = ToolUtils.Decode(this.getQmw().getMonitusername()).trim();

            if (!ToolUtils.StringIsEmpty(monitusername))
                search += ToolUtils.GetAndSearch(search) + " a.monitusername like '%" + monitusername + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getQmw().getSearch(), this.getQmw().getItem(), ou, search);

            List<QuanMonitWork> lists = QuanDao.SearchQuanMonitWork(this.getQmw());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQmw().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 4 监督 - 监督工作记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanMonitWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0741");
            List<QuanMonitWorkDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), QuanMonitWorkDetail.class);
            QuanService.SaveQuanMonitWork(this.getQmw(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion QuanMonitWork Methods

    // region QuanMonitOption Methods

    private QuanMonitOption qmo;

    public QuanMonitOption getQmo() {
        if (qmo == null)
            qmo = new QuanMonitOption();

        return qmo;
    }

    public void setQmo(QuanMonitOption qmo) {
        this.qmo = qmo;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetQuanMonitOption() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanMonitOption rtn = new QuanMonitOption();
        if (ou != null) {
            rtn = QuanDao.GetQuanMonitOption(this.getQmo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanMonitOption() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanMonitOption> lists = QuanDao.GetListQuanMonitOption(this.getQmo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("3 监督 - 选项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchQuanMonitOption() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getQmo().getSearch().setStart(start + 1);
            this.getQmo().getSearch().setEnd(this.GetEndCnt());

            List<QuanMonitOption> lists = QuanDao.SearchQuanMonitOption(this.getQmo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQmo().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("3 监督 - 选项", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanMonitOption() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            QuanService.SaveQuanMonitOption(this.getQmo(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion QuanMonitOption Methods

    // region QuanControlPlanDetail Methods

    private QuanControlPlanDetail qcpd;

    public QuanControlPlanDetail getQcpd() {
        if (qcpd == null)
            qcpd = new QuanControlPlanDetail();

        return qcpd;
    }

    public void setQcpd(QuanControlPlanDetail qcpd) {
        this.qcpd = qcpd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanControlPlanDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanControlPlanDetail> lists = QuanDao.GetListQuanControlPlanDetail(this.getQcpd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1522 - 质量 - 计划内容明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion QuanControlPlanDetail Methods

    // region QuanControlPlan Methods

    private QuanControlPlan qcp;

    public QuanControlPlan getQcp() {
        if (qcp == null)
            qcp = new QuanControlPlan();

        return qcp;
    }

    public void setQcp(QuanControlPlan qcp) {
        this.qcp = qcp;
    }

    // @AuthMethod(Menus="0731", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetQuanControlPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanControlPlan rtn = new QuanControlPlan();
        if (ou != null) {
            rtn = QuanDao.GetQuanControlPlan(this.getQcp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0731", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchQuanControlPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQcp().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getQcp().getSearch(), this.getQcp().getItem(), ou, search);

            List<QuanControlPlan> lists = QuanDao.SearchQuanControlPlan(this.getQcp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQcp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1521 - 质量 - 能力验证(质量控制)计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0731", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanControlPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0731");
            List<QuanControlPlanDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), QuanControlPlanDetail.class);
            QuanService.SaveQuanControlPlan(this.getQcp(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 能力验证 申请html
    public String HtmlControlPlan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            QuanControlPlan item = new QuanControlPlan();
            item.setTranid(tranid);
            item = QuanDao.GetQuanControlPlan(item);
            QuanControlPlanDetail detail = new QuanControlPlanDetail();
            detail.setTranid(tranid);
            List<QuanControlPlanDetail> details = QuanDao.GetListQuanControlPlanDetail(detail);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlControlPlan(item));
                sb.append(FlowFmtService.GetHtmlControlPlanDetail(details));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion QuanControlPlan Methods

    // region CheckGroupMember Methods

    private CheckGroupMember cgm;

    public CheckGroupMember getCgm() {
        if (cgm == null)
            cgm = new CheckGroupMember();

        return cgm;
    }

    public void setCgm(CheckGroupMember cgm) {
        this.cgm = cgm;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListCheckGroupMember() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<CheckGroupMember> lists = QuanDao.GetListCheckGroupMember(this.getCgm());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1513 - 质量 - 小组成员", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion CheckGroupMember Methods

    // region CheckGroup Methods

    private CheckGroup cg;

    public CheckGroup getCg() {
        if (cg == null)
            cg = new CheckGroup();

        return cg;
    }

    public void setCg(CheckGroup cg) {
        this.cg = cg;
    }

    // @AuthMethod(Menus="0725", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetCheckGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CheckGroup rtn = new CheckGroup();
        if (ou != null) {
            rtn = QuanDao.GetCheckGroup(this.getCg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchCheckGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String groupid = ToolUtils.Decode(this.getCg().getGroupid());
            String groupname = ToolUtils.Decode(this.getCg().getGroupname());
            boolean isuse = this.getCg().getIsuse();

            if (!ToolUtils.StringIsEmpty(groupid))
                search += ToolUtils.GetAndSearch(search) + " a.groupid like '%" + groupid + "%' ";

            if (!ToolUtils.StringIsEmpty(groupname))
                search += ToolUtils.GetAndSearch(search) + " a.groupname like '%" + groupname + "%' ";

            if (isuse)
                search += ToolUtils.GetAndSearch(search) + " a.isuse = '" + isuse + "' ";

            this.SetSearch(this.getCg().getSearch(), this.getCg().getItem(), ou, search);

            List<CheckGroup> lists = QuanDao.SearchCheckGroup(this.getCg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1512 - 质量 - 考核小组", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0725", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCheckGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0725");
            List<CheckGroupMember> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), CheckGroupMember.class);
            QuanService.SaveCheckGroup(this.getCg(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion CheckGroup Methods

    // region QuanMonitPlan Methods

    private QuanMonitPlan qmp;

    public QuanMonitPlan getQmp() {
        if (qmp == null)
            qmp = new QuanMonitPlan();

        return qmp;
    }

    public void setQmp(QuanMonitPlan qmp) {
        this.qmp = qmp;
    }

    // @AuthMethod(Menus="0721", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetQuanMonitPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanMonitPlan rtn = new QuanMonitPlan();
        if (ou != null) {
            rtn = QuanDao.GetQuanMonitPlan(this.getQmp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0721", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchQuanMonitPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQmp().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getQmp().getSearch(), this.getQmp().getItem(), ou, search);

            List<QuanMonitPlan> lists = QuanDao.SearchQuanMonitPlan(this.getQmp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQmp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1511 - 质量 - 质量监督计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SearchQuanMonitPlanApproved() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQmp().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getQmp().getSearch(), this.getQmp().getItem(), ou, search);

            List<QuanMonitPlan> lists = QuanDao.SearchQuanMonitPlanApproved(this.getQmp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQmp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1511 - 质量 - 质量监督计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0721", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanMonitPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0721");
            QuanService.SaveQuanMonitPlan(this.getQmp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 质量监督计划html
    public String HtmlQuanMonitPlan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            QuanMonitPlan item = new QuanMonitPlan();
            item.setTranid(tranid);
            item = QuanDao.GetQuanMonitPlan(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlQuanMonitPlan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion QuanMonitPlan Methods

    // region QuanControlSamp Methods

    private QuanControlSamp qcs;

    public QuanControlSamp getQcs() {
        if (qcs == null)
            qcs = new QuanControlSamp();

        return qcs;
    }

    public void setQcs(QuanControlSamp qcs) {
        this.qcs = qcs;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanControlSamp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanControlSamp> lists = QuanDao.GetListQuanControlSamp(this.getQcs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1505 - 质量 - 控制评价样品", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion QuanControlSamp Methods

    // region QuanControlUser Methods

    private QuanControlUser qcu;

    public QuanControlUser getQcu() {
        if (qcu == null)
            qcu = new QuanControlUser();

        return qcu;
    }

    public void setQcu(QuanControlUser qcu) {
        this.qcu = qcu;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListQuanControlUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<QuanControlUser> lists = QuanDao.GetListQuanControlUser(this.getQcu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1504 - 质量 - 评价参加人员", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion QuanControlUser Methods

    // region QuanControlEval Methods

    private QuanControlEval qce;

    public QuanControlEval getQce() {
        if (qce == null)
            qce = new QuanControlEval();

        return qce;
    }

    public void setQce(QuanControlEval qce) {
        this.qce = qce;
    }

    // @AuthMethod(Menus="0711", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetQuanControlEval() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanControlEval rtn = new QuanControlEval();
        if (ou != null) {
            rtn = QuanDao.GetQuanControlEval(this.getQce());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0711", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchQuanControlEval() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQce().getTranid());
            String projname = ToolUtils.Decode(this.getQce().getProjname()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(projname))
                search += ToolUtils.GetAndSearch(search) + " a.projname like '%" + projname + "%' ";

            this.SetSearch(this.getQce().getSearch(), this.getQce().getItem(), ou, search);

            List<QuanControlEval> lists = QuanDao.SearchQuanControlEval(this.getQce());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQce().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1503 - 质量- 质量控制评价", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0711", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanControlEval() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0711");
            List<QuanControlSamp> samples = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), QuanControlSamp.class);
            List<QuanControlUser> users = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("userdetail"), QuanControlUser.class);
            QuanService.SaveQuanControlEval(this.getQce(), users, samples, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 质量控制评价html
    public String HtmlQuanControlEval() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            QuanControlEval item = new QuanControlEval();
            item.setTranid(tranid);
            item = QuanDao.GetQuanControlEval(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlQuanControlEval(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion QuanControlEval Methods

    // region QuanCheckRecord Methods

    private QuanCheckRecord qcr;

    public QuanCheckRecord getQcr() {
        if (qcr == null)
            qcr = new QuanCheckRecord();

        return qcr;
    }

    public void setQcr(QuanCheckRecord qcr) {
        this.qcr = qcr;
    }

    // @AuthMethod(Menus="0701", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetQuanCheckRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        QuanCheckRecord rtn = new QuanCheckRecord();
        if (ou != null) {
            rtn = QuanDao.GetQuanCheckRecord(this.getQcr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0701", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchQuanCheckRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getQcr().getTranid());
            String projname = ToolUtils.Decode(this.getQcr().getProjname());
            String tranuser = ou.getUser().getUserid();
            if (!ToolUtils.StringIsEmpty(tranuser))
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + tranuser + "' ";
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid.trim() + "%' ";

            if (!ToolUtils.StringIsEmpty(projname))
                search += ToolUtils.GetAndSearch(search) + " a.projname like '%" + projname.trim() + "%' ";

            this.SetSearch(this.getQcr().getSearch(), this.getQcr().getItem(), ou, search);

            List<QuanCheckRecord> lists = QuanDao.SearchQuanCheckRecord(this.getQcr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getQcr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1501-质量-质量保证（QA）检查记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0701", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveQuanCheckRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0701");
            QuanService.SaveQuanCheckRecord(this.getQcr(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 质量保证（QA）检查记录表 申请html
    public String HtmlQuanCheckRecord() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            QuanCheckRecord item = new QuanCheckRecord();
            item.setTranid(tranid);
            item = QuanDao.GetQuanCheckRecord(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlQuanCheckRecord(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion QuanCheckRecord Methods

}
