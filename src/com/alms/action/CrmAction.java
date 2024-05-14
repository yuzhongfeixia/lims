package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.CrmDao;
import com.alms.entity.crm.*;
import com.alms.service.CrmService;
import com.alms.service.FlowFmtService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class CrmAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region CrmAccidentDeal Methods

    private CrmAccidentDeal accident;

    public CrmAccidentDeal getAccident() {
        if (accident == null)
            accident = new CrmAccidentDeal();

        return accident;
    }

    public void setAccident(CrmAccidentDeal accident) {
        this.accident = accident;
    }

    // @AuthMethod(Menus="1631", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetCrmAccidentDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CrmAccidentDeal rtn = new CrmAccidentDeal();
        if (ou != null) {
            rtn = CrmDao.GetCrmAccidentDeal(this.getAccident());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1631", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchCrmAccidentDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getAccident().getTranid());
            String samplename = ToolUtils.Decode(this.getAccident().getSamplename()).trim();
            String testedname = ToolUtils.Decode(this.getAccident().getTestedname()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " d.testedname like '%" + testedname + "%' ";

            this.SetSearch(this.getAccident().getSearch(), this.getAccident().getItem(), ou, search);

            List<CrmAccidentDeal> lists = CrmDao.SearchCrmAccidentDeal(this.getAccident());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAccident().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0906-投诉-检验事故处理登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SearchCrmAccidentDealApproved() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getAccident().getTranid()).trim();
            String samplename = ToolUtils.Decode(this.getAccident().getSamplename()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";

            this.SetSearch(this.getAccident().getSearch(), this.getAccident().getItem(), ou, search);

            List<CrmAccidentDeal> lists = CrmDao.SearchCrmAccidentDealApproved(this.getAccident());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAccident().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0906-投诉-检验事故处理登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1631", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCrmAccidentDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1631");
            CrmService.SaveCrmAccidentDeal(this.getAccident(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 检验事故处理登记html
    public String HtmlAccidentDeal() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            CrmAccidentDeal item = new CrmAccidentDeal();
            item.setTranid(tranid);
            item = CrmDao.GetCrmAccidentDeal(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlAccidentDeal(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion CrmAccidentDeal Methods

    // region CrmSurveyItem Methods

    private CrmSurveyItem surveyitem;

    public CrmSurveyItem getSurveyitem() {
        if (surveyitem == null)
            surveyitem = new CrmSurveyItem();

        return surveyitem;
    }

    public void setSurveyitem(CrmSurveyItem surveyitem) {
        this.surveyitem = surveyitem;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetCrmSurveyItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CrmSurveyItem rtn = new CrmSurveyItem();
        if (ou != null) {
            rtn = CrmDao.GetCrmSurveyItem(this.getSurveyitem());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchCrmSurveyItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getSurveyitem().getSearch().setStart(start + 1);
            this.getSurveyitem().getSearch().setEnd(this.GetEndCnt());

            List<CrmSurveyItem> lists = CrmDao.SearchCrmSurveyItem(this.getSurveyitem());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSurveyitem().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0905-投诉-客户调查内容", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCrmSurveyItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            CrmService.SaveCrmSurveyItem(this.getSurveyitem(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion CrmSurveyItem Methods

    // region CrmSurveyDetail Methods

    private CrmSurveyDetail surveydetail;

    public CrmSurveyDetail getSurveydetail() {
        if (surveydetail == null)
            surveydetail = new CrmSurveyDetail();

        return surveydetail;
    }

    public void setSurveydetail(CrmSurveyDetail surveydetail) {
        this.surveydetail = surveydetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListCrmSurveyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<CrmSurveyDetail> lists = CrmDao.GetListCrmSurveyDetail(this.getSurveydetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0904-投诉-客户满意度调查明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion CrmSurveyDetail Methods

    // region CrmSurvey Methods

    private CrmSurvey crmsurvey;

    public CrmSurvey getCrmsurvey() {
        if (crmsurvey == null)
            crmsurvey = new CrmSurvey();

        return crmsurvey;
    }

    public void setCrmsurvey(CrmSurvey crmsurvey) {
        this.crmsurvey = crmsurvey;
    }

    // @AuthMethod(Menus="1621", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetCrmSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CrmSurvey rtn = new CrmSurvey();
        if (ou != null) {
            rtn = CrmDao.GetCrmSurvey(this.getCrmsurvey());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1621", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchCrmSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCrmsurvey().getTranid()).trim();
            String custname = ToolUtils.Decode(this.getCrmsurvey().getCustname()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(custname))
                search += ToolUtils.GetAndSearch(search) + " a.custname like '%" + custname + "%' ";

            this.SetSearch(this.getCrmsurvey().getSearch(), this.getCrmsurvey().getItem(), ou, search);

            List<CrmSurvey> lists = CrmDao.SearchCrmSurvey(this.getCrmsurvey());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCrmsurvey().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0903-投诉-客户满意度调查", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1621", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCrmSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1621");
            List<CrmSurveyDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), CrmSurveyDetail.class);
            CrmService.SaveCrmSurvey(this.getCrmsurvey(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion CrmSurvey Methods

    // region CrmReceptDeal Methods

    private CrmReceptDeal receptdeal;

    public CrmReceptDeal getReceptdeal() {
        if (receptdeal == null)
            receptdeal = new CrmReceptDeal();

        return receptdeal;
    }

    public void setReceptdeal(CrmReceptDeal receptdeal) {
        this.receptdeal = receptdeal;
    }

    // @AuthMethod(Menus="1611", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetCrmReceptDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CrmReceptDeal rtn = new CrmReceptDeal();
        if (ou != null) {
            rtn = CrmDao.GetCrmReceptDeal(this.getReceptdeal());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchCrmReceptDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getReceptdeal().getTranid()).trim();
            String receptid = ToolUtils.Decode(this.getReceptdeal().getReceptid()).trim();
            if (!ToolUtils.StringIsEmpty(receptid))
                search += ToolUtils.GetAndSearch(search) + " a.receptid like '%" + receptid + "%' ";
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getReceptdeal().getSearch(), this.getReceptdeal().getItem(), ou, search);

            List<CrmReceptDeal> lists = CrmDao.SearchCrmReceptDeal(this.getReceptdeal());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getReceptdeal().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0902-投诉-投诉处理结论", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchCrmReceptDealForComplaint() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getReceptdeal().getTranid()).trim();
            String receptid = ToolUtils.Decode(this.getReceptdeal().getReceptid()).trim();
            if (!ToolUtils.StringIsEmpty(receptid))
                search += ToolUtils.GetAndSearch(search) + " a.receptid like '%" + receptid + "%' ";
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getReceptdeal().getSearch(), this.getReceptdeal().getItem(), ou, search);

            List<CrmReceptDeal> lists = CrmDao.SearchCrmReceptDealForComplaint(this.getReceptdeal());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getReceptdeal().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0902-投诉-投诉处理结论", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1611", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCrmReceptDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1611");
            CrmService.SaveCrmReceptDeal(this.getReceptdeal(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 检验事故处理登记html
    public String HtmlReceptDeal() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            CrmReceptDeal item = new CrmReceptDeal();
            item.setTranid(tranid);
            item = CrmDao.GetCrmReceptDeal(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReceptDeal(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion CrmReceptDeal Methods

    // region CrmRecept Methods

    private CrmRecept crmrecept;

    public CrmRecept getCrmrecept() {
        if (crmrecept == null)
            crmrecept = new CrmRecept();

        return crmrecept;
    }

    public void setCrmrecept(CrmRecept crmrecept) {
        this.crmrecept = crmrecept;
    }

    // @AuthMethod(Menus="1601", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetCrmRecept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        CrmRecept rtn = new CrmRecept();
        if (ou != null) {
            rtn = CrmDao.GetCrmRecept(this.getCrmrecept());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchCrmRecept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCrmrecept().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getCrmrecept().getSearch(), this.getCrmrecept().getItem(), ou, search);

            List<CrmRecept> lists = CrmDao.SearchCrmRecept(this.getCrmrecept());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCrmrecept().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0901-投诉-值班接待记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchCrmReceptForDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCrmrecept().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getCrmrecept().getSearch(), this.getCrmrecept().getItem(), ou, search);

            List<CrmRecept> lists = CrmDao.SearchCrmReceptForDeal(this.getCrmrecept());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCrmrecept().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0901-投诉-值班接待记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1601", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveCrmRecept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1601");
            CrmService.SaveCrmRecept(this.getCrmrecept(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion CrmRecept Methods

}
