package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.*;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.std.*;
import com.alms.service.FlowFmtService;
import com.alms.service.StdService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class StdAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // region StdReviewDetail Methods

    private StdReviewDetail srd;

    public StdReviewDetail getSrd() {
        if (srd == null)
            srd = new StdReviewDetail();

        return srd;
    }

    public void setSrd(StdReviewDetail srd) {
        this.srd = srd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdReviewDetail> lists = StdDao.GetListStdReviewDetail(this.getSrd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1620- 标准 - 标准变更评审明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetStdReviewDetailForChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdReviewDetail> lists = StdDao.GetStdReviewDetailForChange(this.getSrd());
            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion StdReviewDetail Methods

    // region StdReview Methods

    private StdReview sr;

    public StdReview getSr() {
        if (sr == null)
            sr = new StdReview();

        return sr;
    }

    public void setSr(StdReview sr) {
        this.sr = sr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdReview rtn = new StdReview();
        if (ou != null) {
            rtn = StdDao.GetStdReview(this.getSr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdReview> lists = StdDao.GetListStdReview(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1619 - 标准 - 标准变更评审", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getSr().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getSr().getSearch(), this.getSr().getItem(), ou, search);

            List<StdReview> lists = StdDao.SearchStdReview(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1619 - 标准 - 标准变更评审", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getSr().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getSr().getSearch(), this.getSr().getItem(), ou, search);

            List<StdReview> lists = StdDao.SearchBusCountry(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("例行检测抽样", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdReviewForChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getSr().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getSr().getSearch(), this.getSr().getItem(), ou, search);

            List<StdReview> lists = StdDao.SearchStdReviewForChange(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1617 - 标准 - 标准变更内容识别记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1387");
            // List<StdReviewDetail> details =
            // ToolUtils.GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"),StdReviewDetail.class);
            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            StdService.SaveStdReview(this.getSr(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1387");
            // List<StdReviewDetail> details =
            // ToolUtils.GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"),StdReviewDetail.class);
            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            StdService.SaveBusCountry(this.getSr(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 标准变更技术html
    public String HtmlStdReview() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdReview item = new StdReview();
            item.setTranid(tranid);
            item = StdDao.GetStdReview(item);
            StdReviewDetail detail = new StdReviewDetail();
            detail.setTranid(tranid);
            List<StdReviewDetail> details = StdDao.GetListStdReviewDetail(detail);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlStdReviewDetail(details));
                sb.append(FlowFmtService.GetHtmlStdReview(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // 例行监测抽样单下达html
    public String HtmlBusCountry() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdReview item = new StdReview();
            item.setTranid(tranid);
            item = StdDao.GetBusCountry(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlBusCountry(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdReview Methods

    // 标准变更内容识别记录 region StdUse Methods
    public String HtmlStdChange() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdChange item = new StdChange();
            item.setTranid(tranid);
            item = StdDao.GetStdChange(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlStdChange(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdChange Methods

    // region StdUse Methods

    private StdUse su;

    public StdUse getSu() {
        if (su == null)
            su = new StdUse();

        return su;
    }

    public void setSu(StdUse su) {
        this.su = su;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdUse rtn = new StdUse();
        if (ou != null) {
            rtn = StdDao.GetStdUse(this.getSu());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdUse> lists = StdDao.GetListStdUse(this.getSu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1601 - 标准 -标准物质使用情况", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdUseBySampletran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdUse> lists = StdDao.GetListStdUseBySampletran(this.getSu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1601 - 标准 -标准物质使用情况", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getSu().getTranid());
            String stdname = ToolUtils.Decode(this.getSu().getStdname());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(stdname))
                search += ToolUtils.GetAndSearch(search) + " a.stdname like '%" + stdname + "%' ";

            this.SetSearch(this.getSu().getSearch(), this.getSu().getItem(), ou, search);

            List<StdUse> lists = StdDao.SearchStdUse(this.getSu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1601 - 标准 -标准物质使用情况", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1007");
            StdService.SaveStdUse(this.getSu(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion StdUse Methods

    // region StdNonstd Methods

    private StdNonstd stdnonstd;

    public StdNonstd getStdnonstd() {
        if (stdnonstd == null)
            stdnonstd = new StdNonstd();

        return stdnonstd;
    }

    public void setStdnonstd(StdNonstd stdnonstd) {
        this.stdnonstd = stdnonstd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdNonstd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdNonstd rtn = new StdNonstd();
        if (ou != null) {
            rtn = StdDao.GetStdNonstd(this.getStdnonstd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdNonstd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdNonstd> lists = StdDao.GetListStdNonstd(this.getStdnonstd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1602 - 标准 - 非标检测方法确认", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdNonstd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getStdnonstd().getTranid()).trim();
            String testmethname = ToolUtils.Decode(this.getStdnonstd().getTestmethname()).trim();
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(testmethname))
                search += ToolUtils.GetAndSearch(search) + " a.testmethname like '%" + testmethname + "%' ";

            this.SetSearch(this.getStdnonstd().getSearch(), this.getStdnonstd().getItem(), ou, search);

            List<StdNonstd> lists = StdDao.SearchStdNonstd(this.getStdnonstd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStdnonstd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1602 - 标准 - 非标检测方法确认", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdNonstd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1341");
            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            List<BusRecordFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), BusRecordFile.class);
            StdService.SaveStdNonstd(this.getStdnonstd(), details, filedetails, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 非标方法html
    public String HtmlNonStd() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdNonstd item = new StdNonstd();
            item.setTranid(tranid);
            item = StdDao.GetStdNonstd(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlNonStd(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdNonstd Methods

    // region StdMethodDevi Methods

    private StdMethodDevi stddevi;

    public StdMethodDevi getStddevi() {
        if (stddevi == null)
            stddevi = new StdMethodDevi();

        return stddevi;
    }

    public void setStddevi(StdMethodDevi stddevi) {
        this.stddevi = stddevi;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdMethodDevi() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdMethodDevi rtn = new StdMethodDevi();
        if (ou != null) {
            rtn = StdDao.GetStdMethodDevi(this.getStddevi());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdMethodDevi() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdMethodDevi> lists = StdDao.GetListStdMethodDevi(this.getStddevi());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1605 - 标准 - 检测方法偏离确认", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdMethodDevi() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getStddevi().getTranid()).trim();
            String methodname = ToolUtils.Decode(this.getStddevi().getMethodname()).trim();
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(methodname))
                search += ToolUtils.GetAndSearch(search) + " a.methodname like '%" + methodname + "%' ";

            this.SetSearch(this.getStddevi().getSearch(), this.getStddevi().getItem(), ou, search);

            List<StdMethodDevi> lists = StdDao.SearchStdMethodDevi(this.getStddevi());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStddevi().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1605 - 标准 - 检测方法偏离确认", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdMethodDevi() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1351");
            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            List<BusRecordFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), BusRecordFile.class);
            StdService.SaveStdMethodDevi(this.getStddevi(), details, filedetails, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 检测方法偏离html
    public String HtmlMethodDevi() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdMethodDevi item = new StdMethodDevi();
            item.setTranid(tranid);
            item = StdDao.GetStdMethodDevi(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlMethodDevi(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdMethodDevi Methods

    // region StdProApply Methods

    private StdProApply proapply;

    public StdProApply getProapply() {
        if (proapply == null)
            proapply = new StdProApply();

        return proapply;
    }

    public void setProapply(StdProApply proapply) {
        this.proapply = proapply;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdProApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdProApply rtn = new StdProApply();
        if (ou != null) {
            rtn = StdDao.GetStdProApply(this.getProapply());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdProApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdProApply> lists = StdDao.GetListStdProApply(this.getProapply());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1611 - 标准 - 新增检验项目方案申请书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdProApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getProapply().getTranid()).trim();
            String projname = ToolUtils.Decode(this.getProapply().getProjname()).trim();
            String flowstatus = ToolUtils.Decode(this.getProapply().getFlowstatus()).trim();
            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(projname))
                search += ToolUtils.GetAndSearch(search) + " a.projname like '%" + projname + "%' ";

            this.SetSearch(this.getProapply().getSearch(), this.getProapply().getItem(), ou, search);

            List<StdProApply> lists = StdDao.SearchStdProApply(this.getProapply());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getProapply().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1611 - 标准 - 新增检验项目方案申请书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdProApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1361");
            StdService.SaveStdProApply(this.getProapply(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 新增试验项目html
    public String HtmlProApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdProApply item = new StdProApply();
            item.setTranid(tranid);
            item = StdDao.GetStdProApply(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlProApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdProApply Methods

    // region StdTestSure Methods

    private StdTestSure testsure;

    public StdTestSure getTestsure() {
        if (testsure == null)
            testsure = new StdTestSure();

        return testsure;
    }

    public void setTestsure(StdTestSure testsure) {
        this.testsure = testsure;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdTestSure() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdTestSure rtn = new StdTestSure();
        if (ou != null) {
            rtn = StdDao.GetStdTestSure(this.getTestsure());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdTestSure() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdTestSure> lists = StdDao.GetListStdTestSure(this.getTestsure());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1613 - 标准 - 新增检验能力确认书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdTestSure() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getTestsure().getTranid()).trim();
            String projname = ToolUtils.Decode(this.getTestsure().getProjname()).trim();
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(projname))
                search += ToolUtils.GetAndSearch(search) + " a.projname like '%" + projname + "%' ";

            this.SetSearch(this.getTestsure().getSearch(), this.getTestsure().getItem(), ou, search);

            List<StdTestSure> lists = StdDao.SearchStdTestSure(this.getTestsure());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTestsure().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1613 - 标准 - 新增检验能力确认书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdTestSure() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1371");
            List<StdSureDetail> stdSureDetails = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), StdSureDetail.class);
            StdService.SaveStdTestSure(this.getTestsure(), this.getRtv(), stdSureDetails, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 新增检验项目能力确认html
    public String HtmlTestSure() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdTestSure item = new StdTestSure();
            item.setTranid(tranid);
            item = StdDao.GetStdTestSure(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlTestSure(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion StdTestSure Methods

    // region StdReplRecord Methods

    private StdReplRecord replrecord;

    public StdReplRecord getReplrecord() {
        if (replrecord == null)
            replrecord = new StdReplRecord();

        return replrecord;
    }

    public void setReplrecord(StdReplRecord replrecord) {
        this.replrecord = replrecord;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdReplRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdReplRecord rtn = new StdReplRecord();
        if (ou != null) {
            rtn = StdDao.GetStdReplRecord(this.getReplrecord());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdReplRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdReplRecord> lists = StdDao.GetListStdReplRecord(this.getReplrecord());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1615 - 标准 - 标准查新记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdReplRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getReplrecord().getTranid()).trim();
            String stdname = ToolUtils.Decode(this.getReplrecord().getStdname()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(stdname))
                search += ToolUtils.GetAndSearch(search) + " a.stdname like '%" + stdname + "%' ";

            this.SetSearch(this.getReplrecord().getSearch(), this.getReplrecord().getItem(), ou, search);

            List<StdReplRecord> lists = StdDao.SearchStdReplRecord(this.getReplrecord());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getReplrecord().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1615 - 标准 - 标准查新记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdReplRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1381");

            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            StdService.SaveStdReplRecord(this.getReplrecord(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String HtmlStdReplRecord() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            StdReplRecord item = new StdReplRecord();
            item.setTranid(tranid);
            item = StdDao.GetStdReplRecord(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlStdReplRecord(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion StdReplRecord Methods

    // region StdChange Methods

    private StdChange stdchange;

    public StdChange getStdchange() {
        if (stdchange == null)
            stdchange = new StdChange();

        return stdchange;
    }

    public void setStdchange(StdChange stdchange) {
        this.stdchange = stdchange;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdChange rtn = new StdChange();
        if (ou != null) {
            rtn = StdDao.GetStdChange(this.getStdchange());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdChange> lists = StdDao.GetListStdChange(this.getStdchange());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1617 - 标准 - 标准变更内容识别记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getStdchange().getSearch().setStart(start + 1);
            this.getStdchange().getSearch().setEnd(this.GetEndCnt());
            String search = "";
            String tranid = ToolUtils.Decode(this.getStdchange().getTranid()).trim();
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";
            this.SetSearch(this.getStdchange().getSearch(), this.getStdchange().getItem(), ou, search);
            List<StdChange> lists = StdDao.SearchStdChange(this.getStdchange());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStdchange().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1617 - 标准 - 标准变更内容识别记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStdChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1391");
            StdService.SaveStdChange(this.getStdchange(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion StdChange Methods

    // region StdSureDetail Methods

    private StdSureDetail ssd;

    public StdSureDetail getSsd() {
        if (ssd == null)
            ssd = new StdSureDetail();

        return ssd;
    }

    public void setSsd(StdSureDetail ssd) {
        this.ssd = ssd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStdSureDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StdSureDetail rtn = new StdSureDetail();
        if (ou != null) {
            rtn = StdDao.GetStdSureDetail(this.getSsd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStdSureDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StdSureDetail> lists = StdDao.GetListStdSureDetail(this.getSsd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1614 - 标准 - 新增检验能力确认明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStdSureDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getSsd().getSearch().setStart(start + 1);
            this.getSsd().getSearch().setEnd(this.GetEndCnt());

            List<StdSureDetail> lists = StdDao.SearchStdSureDetail(this.getSsd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSsd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1614 - 标准 - 新增检验能力确认明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // @AuthMethod(Menus="", Auth=MenuAuth.Modify, OutType=ActionOutType.Bean)
    // public String SaveStdSureDetail() throws Exception {
    // OnlineUser ou = ToolUtils.GetOnlineUser();
    //
    // if (ou != null) {
    // TranLog log = ToolUtils.InitTranLog("");
    // StdService.SaveStdSureDetail(this.getSsd(), this.getRtv(), log);
    // ToolUtils.OutString(this.getRtv().toString());
    // }
    // else
    // ToolUtils.OutString(this.NotLoginRtv());
    //
    // return null;
    // }

    // endregion StdSureDetail Methods

}
