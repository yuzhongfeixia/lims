package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.InnerDao;
import com.alms.entity.inner.InnerFoodReview;
import com.alms.entity.inner.InnerGroup;
import com.alms.entity.inner.InnerGroupMember;
import com.alms.entity.inner.InnerInvalid;
import com.alms.entity.inner.InnerMeetPart;
import com.alms.entity.inner.InnerMeetSign;
import com.alms.entity.inner.InnerReport;
import com.alms.entity.inner.InnerScene;
import com.alms.entity.inner.InnerSceneWork;
import com.alms.entity.inner.InnerYear;
import com.alms.entity.inner.InnerYearDetail;
import com.alms.service.FlowFmtService;
import com.alms.service.InnerService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class InnerAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region InnerFoodReview Methods

    private InnerFoodReview ifr;

    public InnerFoodReview getIfr() {
        if (ifr == null)
            ifr = new InnerFoodReview();

        return ifr;
    }

    public void setIfr(InnerFoodReview ifr) {
        this.ifr = ifr;
    }

    @AuthMethod(Menus = "1861", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetInnerFoodReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerFoodReview rtn = new InnerFoodReview();
        if (ou != null) {
            rtn = InnerDao.GetInnerFoodReview(this.getIfr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "1861", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchInnerFoodReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getIfr().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getIfr().getSearch(), this.getIfr().getItem(), ou, search);

            List<InnerFoodReview> lists = InnerDao.SearchInnerFoodReview(this.getIfr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1121 - 内审 - 食品检验机构资质认定评审准则内审", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1861", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerFoodReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1861");
            InnerService.SaveInnerFoodReview(this.getIfr(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerFoodReview Methods

    // region InnerMeetPart Methods

    private InnerMeetPart meetpart;

    public InnerMeetPart getMeetpart() {
        if (meetpart == null)
            meetpart = new InnerMeetPart();

        return meetpart;
    }

    public void setMeetpart(InnerMeetPart meetpart) {
        this.meetpart = meetpart;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListInnerMeetPart() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<InnerMeetPart> lists = InnerDao.GetListInnerMeetPart(this.getMeetpart());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1110-内审-内审会议参与人员", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion InnerMeetPart Methods

    // region InnerMeetSign Methods

    private InnerMeetSign meetsign;

    public InnerMeetSign getMeetsign() {
        if (meetsign == null)
            meetsign = new InnerMeetSign();

        return meetsign;
    }

    public void setMeetsign(InnerMeetSign meetsign) {
        this.meetsign = meetsign;
    }

    // @AuthMethod(Menus="1851", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetInnerMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerMeetSign rtn = new InnerMeetSign();
        if (ou != null) {
            rtn = InnerDao.GetInnerMeetSign(this.getMeetsign());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1851", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String meetid = ToolUtils.Decode(this.getMeetsign().getMeetid()).trim();
            String meettopic = ToolUtils.Decode(this.getMeetsign().getMeettopic()).trim();

            if (!ToolUtils.StringIsEmpty(meetid))
                search += ToolUtils.GetAndSearch(search) + " a.meetid like '%" + meetid + "%' ";

            if (!ToolUtils.StringIsEmpty(meettopic))
                search += ToolUtils.GetAndSearch(search) + " a.meettopic like '%" + meettopic + "%' ";

            this.SetSearch(this.getMeetsign().getSearch(), this.getMeetsign().getItem(), ou, search);

            List<InnerMeetSign> lists = InnerDao.SearchInnerMeetSign(this.getMeetsign());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getMeetsign().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1108-内审-内审首/末次会议记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1851", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1851");
            List<InnerMeetPart> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), InnerMeetPart.class);
            InnerService.SaveInnerMeetSign(this.getMeetsign(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerMeetSign Methods

    // region InnerReport Methods

    private InnerReport innerreport;

    public InnerReport getInnerreport() {
        if (innerreport == null)
            innerreport = new InnerReport();

        return innerreport;
    }

    public void setInnerreport(InnerReport innerreport) {
        this.innerreport = innerreport;
    }

    // @AuthMethod(Menus="1841", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetInnerReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerReport rtn = new InnerReport();
        if (ou != null) {
            rtn = InnerDao.GetInnerReport(this.getInnerreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1841", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportid = ToolUtils.Decode(this.getInnerreport().getReportid());
            String auditeddept = ToolUtils.Decode(this.getInnerreport().getAuditeddept());

            if (!ToolUtils.StringIsEmpty(reportid))
                search += ToolUtils.GetAndSearch(search) + " a.reportid like '%" + reportid + "%' ";

            if (!ToolUtils.CheckComboValue(auditeddept))
                search += ToolUtils.GetAndSearch(search) + " a.auditeddept like '%" + auditeddept + "%' ";

            this.SetSearch(this.getInnerreport().getSearch(), this.getInnerreport().getItem(), ou, search);

            List<InnerReport> lists = InnerDao.SearchInnerReport(this.getInnerreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getInnerreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("内审-审核报告", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1841", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1841");
            String auditby = ServletActionContext.getRequest().getParameter("auditby");
            this.getInnerreport().setAuditby(auditby);
            InnerService.SaveInnerReport(this.getInnerreport(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerReport Methods

    // region InnerInvalid Methods

    private InnerInvalid innerinvalid;

    public InnerInvalid getInnerinvalid() {
        if (innerinvalid == null)
            innerinvalid = new InnerInvalid();

        return innerinvalid;
    }

    public void setInnerinvalid(InnerInvalid innerinvalid) {
        this.innerinvalid = innerinvalid;
    }

    // @AuthMethod(Menus="1831", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetInnerInvalid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerInvalid rtn = new InnerInvalid();
        if (ou != null) {
            rtn = InnerDao.GetInnerInvalid(this.getInnerinvalid());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1831", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerInvalid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getInnerinvalid().getTranid());
            String occurdept = ToolUtils.Decode(this.getInnerinvalid().getOccurdept());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.CheckComboValue(occurdept))
                search += ToolUtils.GetAndSearch(search) + " a.occurdept like '%" + occurdept + "%' ";

            this.SetSearch(this.getInnerinvalid().getSearch(), this.getInnerinvalid().getItem(), ou, search);

            List<InnerInvalid> lists = InnerDao.SearchInnerInvalid(this.getInnerinvalid());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getInnerinvalid().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1107-内审-不符合纠正措施报告", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1831", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerInvalid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1831");
            InnerService.SaveInnerInvalid(this.getInnerinvalid(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 不符合纠正措施html
    public String HtmlInnerInvalid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            InnerInvalid item = new InnerInvalid();
            item.setTranid(tranid);
            item = InnerDao.GetInnerInvalid(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlInnerInvalid(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion InnerInvalid Methods

    // region InnerSceneWork Methods

    private InnerSceneWork scenework;

    public InnerSceneWork getScenework() {
        if (scenework == null)
            scenework = new InnerSceneWork();

        return scenework;
    }

    public void setScenework(InnerSceneWork scenework) {
        this.scenework = scenework;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetInnerSceneWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerSceneWork rtn = new InnerSceneWork();
        if (ou != null) {
            rtn = InnerDao.GetInnerSceneWork(this.getScenework());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListInnerSceneWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<InnerSceneWork> lists = InnerDao.GetListInnerSceneWork(this.getScenework());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1106-内审-现场审核工作计划工作安排", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchInnerSceneWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getScenework().getSearch().setStart(start + 1);
            this.getScenework().getSearch().setEnd(this.GetEndCnt());

            List<InnerSceneWork> lists = InnerDao.SearchInnerSceneWork(this.getScenework());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getScenework().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1106-内审-现场审核工作计划工作安排", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerSceneWork() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            InnerService.SaveInnerSceneWork(this.getScenework(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerSceneWork Methods

    // region InnerScene Methods

    private InnerScene innerscene;

    public InnerScene getInnerscene() {
        if (innerscene == null)
            innerscene = new InnerScene();

        return innerscene;
    }

    public void setInnerscene(InnerScene innerscene) {
        this.innerscene = innerscene;
    }

    // @AuthMethod(Menus="1821", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetInnerScene() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerScene rtn = new InnerScene();
        if (ou != null) {
            rtn = InnerDao.GetInnerScene(this.getInnerscene());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1821", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerScene() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getInnerscene().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getInnerscene().getSearch(), this.getInnerscene().getItem(), ou, search);

            List<InnerScene> lists = InnerDao.SearchInnerScene(this.getInnerscene());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getInnerscene().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1105-内审-现场审核工作计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1821", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerScene() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1821");
            String auditby = ServletActionContext.getRequest().getParameter("auditby");
            String auditmethod = ServletActionContext.getRequest().getParameter("auditmethod");
            this.getInnerscene().setAuditby(auditby);
            this.getInnerscene().setAuditmethod(auditmethod);
            List<InnerSceneWork> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), InnerSceneWork.class);
            InnerService.SaveInnerScene(this.getInnerscene(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerScene Methods

    // region InnerGroupMember Methods

    private InnerGroupMember groupmember;

    public InnerGroupMember getGroupmember() {
        if (groupmember == null)
            groupmember = new InnerGroupMember();

        return groupmember;
    }

    public void setGroupmember(InnerGroupMember groupmember) {
        this.groupmember = groupmember;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListInnerGroupMember() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<InnerGroupMember> lists = InnerDao.GetListInnerGroupMember(this.getGroupmember());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1104-内审-内审小组成员组", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion InnerGroupMember Methods

    // region InnerGroup Methods

    private InnerGroup innergroup;

    public InnerGroup getInnergroup() {
        if (innergroup == null)
            innergroup = new InnerGroup();

        return innergroup;
    }

    public void setInnergroup(InnerGroup innergroup) {
        this.innergroup = innergroup;
    }

    @AuthMethod(Menus = "1811", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetInnerGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerGroup rtn = new InnerGroup();
        if (ou != null) {
            rtn = InnerDao.GetInnerGroup(this.getInnergroup());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "1811", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListInnerGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<InnerGroup> lists = InnerDao.GetListInnerGroup(this.getInnergroup());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1103-内审-内审小组", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // @AuthMethod(Menus="1811", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String groupid = ToolUtils.Decode(this.getInnergroup().getGroupid()).trim();
            String groupname = ToolUtils.Decode(this.getInnergroup().getGroupname()).trim();
            if (!ToolUtils.StringIsEmpty(groupname))
                search += " a.groupname like '%" + groupname + "%' ";
            if (!ToolUtils.StringIsEmpty(groupid))
                search += " a.groupid like '%" + groupid + "%' ";

            this.SetSearch(this.getInnergroup().getSearch(), this.getInnergroup().getItem(), ou, search);
            List<InnerGroup> lists = InnerDao.SearchInnerGroup(this.getInnergroup());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getInnergroup().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1103-内审-内审小组", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1811", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1811");
            String details = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("details"));
            InnerService.SaveInnerGroup(this.getInnergroup(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion InnerGroup Methods

    // region InnerYear Methods

    private InnerYear inneryear;

    public InnerYear getInneryear() {
        if (inneryear == null)
            inneryear = new InnerYear();

        return inneryear;
    }

    public void setInneryear(InnerYear inneryear) {
        this.inneryear = inneryear;
    }

    // @AuthMethod(Menus="1801", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetInnerYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        InnerYear rtn = new InnerYear();
        if (ou != null) {
            rtn = InnerDao.GetInnerYear(this.getInneryear());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1801", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchInnerYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getInneryear().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getInneryear().getSearch(), this.getInneryear().getItem(), ou, search);

            List<InnerYear> lists = InnerDao.SearchInnerYear(this.getInneryear());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getInneryear().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1101-内审-内部审核年度计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1801", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveInnerYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1801");
            String auditby = ServletActionContext.getRequest().getParameter("auditby");
            this.getInneryear().setAuditby(auditby);
            List<InnerYearDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), InnerYearDetail.class);
            InnerService.SaveInnerYear(this.getInneryear(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 内审年度计划html
    public String HtmlInnerYear() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            InnerYear item = new InnerYear();
            item.setTranid(tranid);
            item = InnerDao.GetInnerYear(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlInnerYear(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion InnerYear Methods

    // region InnerYearDetail Methods

    private InnerYearDetail innerdetail;

    public InnerYearDetail getInnerdetail() {
        if (innerdetail == null)
            innerdetail = new InnerYearDetail();

        return innerdetail;
    }

    public void setInnerdetail(InnerYearDetail innerdetail) {
        this.innerdetail = innerdetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListInnerYearDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<InnerYearDetail> lists = InnerDao.GetListInnerYearDetail(this.getInnerdetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1102-内审-内部审核年度计划审核日程", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion InnerYearDetail Methods

}
