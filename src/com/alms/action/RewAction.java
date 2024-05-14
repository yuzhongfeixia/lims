package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import com.alms.dao.RewDao;
import com.alms.entity.review.MeetSign;
import com.alms.entity.review.ReviewImprove;
import com.alms.entity.review.ReviewMeetUser;
import com.alms.entity.review.ReviewNotice;
import com.alms.entity.review.ReviewPlan;
import com.alms.entity.review.ReviewRecord;
import com.alms.entity.review.ReviewReport;
import com.alms.entity.review.ReviewYear;
import com.alms.service.FlowFmtService;
import com.alms.service.RewService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class RewAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // region ReviewYear Methods

    private ReviewYear rr;

    public ReviewYear getRr() {
        if (rr == null)
            rr = new ReviewYear();

        return rr;
    }

    public void setRr(ReviewYear rr) {
        this.rr = rr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewYear rtn = new ReviewYear();
        if (ou != null) {
            rtn = RewDao.GetReviewYear(this.getRr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewYear> lists = RewDao.GetListReviewYear(this.getRr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1201-评审-管理评审年度工作计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getRr().getTranid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))

                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getRr().getSearch(), this.getRr().getItem(), ou, search);

            List<ReviewYear> lists = RewDao.SearchReviewYear(this.getRr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1201-评审-管理评审年度工作计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1911");
            RewService.SaveReviewYear(this.getRr(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 管理评审年度计划 html
    public String HtmlReviewYear() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ReviewYear item = new ReviewYear();
            item.setTranid(tranid);
            item = RewDao.GetReviewYear(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReviewYear(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion ReviewYear Methods

    // region ReviewPlan Methods

    private ReviewPlan rp;

    public ReviewPlan getRp() {
        if (rp == null)
            rp = new ReviewPlan();

        return rp;
    }

    public void setRp(ReviewPlan rp) {
        this.rp = rp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewPlan rtn = new ReviewPlan();
        if (ou != null) {
            rtn = RewDao.GetReviewPlan(this.getRp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewPlan> lists = RewDao.GetListReviewPlan(this.getRp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1202-评审-管理评审工作计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getRp().getTranid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getRp().getSearch(), this.getRp().getItem(), ou, search);

            List<ReviewPlan> lists = RewDao.SearchReviewPlan(this.getRp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1202-评审-管理评审工作计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewPlanForNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String tranid = ToolUtils.Decode(this.getRp().getTranid());
            // // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            //
            // if (!ToolUtils.StringIsEmpty(tranid))
            // search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid
            // + "%' ";

            this.SetSearch(this.getRp().getSearch(), this.getRp().getItem(), ou, search);

            List<ReviewPlan> lists = RewDao.SearchReviewPlanForNotice(this.getRp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1202-评审-管理评审工作计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1921");
            RewService.SaveReviewPlan(this.getRp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 管理评审工作计划 html
    public String HtmlReviewPlan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ReviewPlan item = new ReviewPlan();
            item.setTranid(tranid);
            item = RewDao.GetReviewPlan(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReviewPlan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion ReviewYear Methods

    // endregion ReviewPlan Methods

    // region ReviewRecord Methods

    private ReviewRecord rerd;

    public ReviewRecord getRerd() {
        if (rerd == null)
            rerd = new ReviewRecord();

        return rerd;
    }

    public void setRerd(ReviewRecord rerd) {
        this.rerd = rerd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewRecord rtn = new ReviewRecord();
        if (ou != null) {
            rtn = RewDao.GetReviewRecord(this.getRerd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewRecord> lists = RewDao.GetListReviewRecord(this.getRerd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1203-评审-管理评审记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getRerd().getSearch().setStart(start + 1);
            this.getRerd().getSearch().setEnd(this.GetEndCnt());
            String tranid = ToolUtils.Decode(this.getRerd().getTranid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            String search = "";
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";
            this.SetSearch(this.getRerd().getSearch(), this.getRerd().getItem(), ou, search);
            List<ReviewRecord> lists = RewDao.SearchReviewRecord(this.getRerd());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRerd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1203-评审-管理评审记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1951");
            RewService.SaveReviewRecord(this.getRerd(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 管理评审工作计划 html
    public String HtmlReviewRecord() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ReviewRecord item = new ReviewRecord();
            item.setTranid(tranid);
            item = RewDao.GetReviewRecord(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReviewRecord(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion ReviewRecord Methods

    // region ReviewReport Methods

    private ReviewReport rert;

    public ReviewReport getRert() {
        if (rert == null)
            rert = new ReviewReport();

        return rert;
    }

    public void setRert(ReviewReport rert) {
        this.rert = rert;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewReport rtn = new ReviewReport();
        if (ou != null) {
            rtn = RewDao.GetReviewReport(this.getRert());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewReport> lists = RewDao.GetListReviewReport(this.getRert());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1204-评审-评审报告", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getRerd().getTranid()).trim();
            if (!ToolUtils.CheckComboValue(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";
            this.SetSearch(this.getRert().getSearch(), this.getRert().getItem(), ou, search);

            List<ReviewReport> lists = RewDao.SearchReviewReport(this.getRert());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRert().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1204-评审-评审报告", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewReportForImprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            this.SetSearch(this.getRert().getSearch(), this.getRert().getItem(), ou, search);

            List<ReviewReport> lists = RewDao.SearchReviewReportForImprove(this.getRert());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRert().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1204-评审-评审报告", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1961");
            String content = this.getRert().getReportcontent();
            content = content.replaceAll("\"", "&quot;");
            content = content.replaceAll("<", "&lt;");
            content = content.replaceAll(">", "&gt;");
            this.getRert().setReportcontent(content);

            RewService.SaveReviewReport(this.getRert(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 管理评审报告 html
    public String HtmlReviewReport() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ReviewReport item = new ReviewReport();
            item.setTranid(tranid);
            item = RewDao.GetReviewReport(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReviewReport(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion ReviewReport Methods

    // region ReviewImprove Methods

    private ReviewImprove ri;

    public ReviewImprove getRi() {
        if (ri == null)
            ri = new ReviewImprove();

        return ri;
    }

    public void setRi(ReviewImprove ri) {
        this.ri = ri;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewImprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewImprove rtn = new ReviewImprove();
        if (ou != null) {
            rtn = RewDao.GetReviewImprove(this.getRi());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewImprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewImprove> lists = RewDao.GetListReviewImprove(this.getRi());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1205-评审-改进措施", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewImprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getRi().getTranid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getRi().getSearch(), this.getRi().getItem(), ou, search);

            List<ReviewImprove> lists = RewDao.SearchReviewImprove(this.getRi());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRi().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1205-评审-改进措施", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewImprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1971");
            RewService.SaveReviewImprove(this.getRi(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 管理改进措施 html
    public String HtmlReviewImprove() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ReviewImprove item = new ReviewImprove();
            item.setTranid(tranid);
            item = RewDao.GetReviewImprove(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReviewImprove(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion ReviewImprove Methods

    // region ReviewNotice Methods

    private ReviewNotice rn;

    public ReviewNotice getRn() {
        if (rn == null)
            rn = new ReviewNotice();

        return rn;
    }

    public void setRn(ReviewNotice rn) {
        this.rn = rn;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewNotice rtn = new ReviewNotice();
        if (ou != null) {
            rtn = RewDao.GetReviewNotice(this.getRn());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewNotice> lists = RewDao.GetListReviewNotice(this.getRn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1206-评审-管理评审实施通知", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getRn().getTranid());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getRn().getSearch(), this.getRn().getItem(), ou, search);
            List<ReviewNotice> lists = RewDao.SearchReviewNotice(this.getRn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1206-评审-管理评审实施通知", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewNoticeForMeet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String tranid = ToolUtils.Decode(this.getRn().getTranid());
            // // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            //
            // if (!ToolUtils.StringIsEmpty(tranid))
            // search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid
            // + "%' ";

            this.SetSearch(this.getRn().getSearch(), this.getRn().getItem(), ou, search);
            List<ReviewNotice> lists = RewDao.SearchReviewNoticeForMeet(this.getRn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1206-评审-管理评审实施通知", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1930");
            String content = this.getRn().getNoticetext();
            content = content.replaceAll("\"", "&quot;");
            content = content.replaceAll("<", "&lt;");
            content = content.replaceAll(">", "&gt;");
            this.getRn().setNoticetext(content);
            RewService.SaveReviewNotice(this.getRn(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion ReviewNotice Methods

    // region MeetSign Methods

    private MeetSign ms;

    public MeetSign getMs() {
        if (ms == null)
            ms = new MeetSign();

        return ms;
    }

    public void setMs(MeetSign ms) {
        this.ms = ms;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        MeetSign rtn = new MeetSign();
        if (ou != null) {
            rtn = RewDao.GetMeetSign(this.getMs());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<MeetSign> lists = RewDao.GetListMeetSign(this.getMs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1108-内审-内审首/末次会议记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String meetid = ToolUtils.Decode(this.getMs().getMeetid());
            String meettopic = ToolUtils.Decode(this.getMs().getMeettopic());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(meetid))
                search += ToolUtils.GetAndSearch(search) + " a.meetid like '%" + meetid + "%' ";

            if (!ToolUtils.StringIsEmpty(meettopic))
                search += ToolUtils.GetAndSearch(search) + " a.meettopic like '%" + meettopic + "%' ";

            this.SetSearch(this.getMs().getSearch(), this.getMs().getItem(), ou, search);

            List<MeetSign> lists = RewDao.SearchMeetSign(this.getMs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getMs().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1108-内审-内审首/末次会议记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchMeetSignForRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String meetid = ToolUtils.Decode(this.getMs().getMeetid());
            // String meettopic = ToolUtils.Decode(this.getMs().getMeettopic());
            // // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            //
            // if (!ToolUtils.StringIsEmpty(meetid))
            // search += ToolUtils.GetAndSearch(search) + " a.meetid like '%" + meetid
            // + "%' ";
            //
            // if (!ToolUtils.StringIsEmpty(meettopic))
            // search += ToolUtils.GetAndSearch(search) + " a.meettopic like '%" +
            // meettopic
            // + "%' ";

            this.SetSearch(this.getMs().getSearch(), this.getMs().getItem(), ou, search);

            List<MeetSign> lists = RewDao.SearchMeetSignForRecord(this.getMs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getMs().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1108-内审-内审首/末次会议记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchMeetSignForReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String meetid = ToolUtils.Decode(this.getMs().getMeetid());
            // String meettopic = ToolUtils.Decode(this.getMs().getMeettopic());
            // // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            //
            // if (!ToolUtils.StringIsEmpty(meetid))
            // search += ToolUtils.GetAndSearch(search) + " a.meetid like '%" + meetid
            // + "%' ";
            //
            // if (!ToolUtils.StringIsEmpty(meettopic))
            // search += ToolUtils.GetAndSearch(search) + " a.meettopic like '%" +
            // meettopic
            // + "%' ";

            this.SetSearch(this.getMs().getSearch(), this.getMs().getItem(), ou, search);

            List<MeetSign> lists = RewDao.SearchMeetSignForReport(this.getMs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getMs().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1108-内审-内审首/末次会议记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveMeetSign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1944");

            List<ReviewMeetUser> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), ReviewMeetUser.class);

            RewService.SaveMeetSign(this.getMs(), this.getRtv(), details, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion MeetSign Methods

    // region ReviewMeetUser Methods

    private ReviewMeetUser mu;

    public ReviewMeetUser getMu() {
        if (mu == null)
            mu = new ReviewMeetUser();

        return mu;
    }

    public void setMu(ReviewMeetUser mu) {
        this.mu = mu;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReviewMeetUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReviewMeetUser rtn = new ReviewMeetUser();
        if (ou != null) {
            rtn = RewDao.GetReviewMeetUser(this.getMu());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReviewMeetUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReviewMeetUser> lists = RewDao.GetListReviewMeetUser(this.getMu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("05-1208-评审-管理评审会议签到人员", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReviewMeetUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getMu().getSearch().setStart(start + 1);
            this.getMu().getSearch().setEnd(this.GetEndCnt());

            List<ReviewMeetUser> lists = RewDao.SearchReviewMeetUser(this.getMu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getMu().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("05-1208-评审-管理评审会议签到人员", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReviewMeetUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            RewService.SaveReviewMeetUser(this.getMu(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion ReviewMeetUser Methods

}