package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.ContDao;
import com.alms.entity.cont.ContractReview;
import com.alms.entity.cont.ContractReviewDetail;
import com.alms.entity.cont.ContractReviewParam;
import com.alms.entity.cont.ContractReviewSample;
import com.alms.service.ContService;
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

public class ContAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region ContractReviewParam Methods

    private ContractReviewParam crp;

    public ContractReviewParam getCrp() {
        if (crp == null)
            crp = new ContractReviewParam();

        return crp;
    }

    public void setCrp(ContractReviewParam crp) {
        this.crp = crp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListContractReviewParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ContractReviewParam> lists = ContDao.GetListContractReviewParam(this.getCrp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1404-合同-合同评审检测项目明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion ContractReviewParam Methods

    // region ContractReviewSample Methods

    private ContractReviewSample crs;

    public ContractReviewSample getCrs() {
        if (crs == null)
            crs = new ContractReviewSample();

        return crs;
    }

    public void setCrs(ContractReviewSample crs) {
        this.crs = crs;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListContractReviewSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ContractReviewSample> lists = ContDao.GetListContractReviewSample(this.getCrs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1403-合同-合同评审样品明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion ContractReviewSample Methods

    // region ContractReviewDetail Methods

    private ContractReviewDetail crd;

    public ContractReviewDetail getCrd() {
        if (crd == null)
            crd = new ContractReviewDetail();

        return crd;
    }

    public void setCrd(ContractReviewDetail crd) {
        this.crd = crd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListContractReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ContractReviewDetail> lists = ContDao.GetListContractReviewDetail(this.getCrd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1402-合同-合同评审明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion ContractReviewDetail Methods

    // region ContractReview Methods

    private ContractReview cr;

    public ContractReview getCr() {
        if (cr == null)
            cr = new ContractReview();

        return cr;
    }

    public void setCr(ContractReview cr) {
        this.cr = cr;
    }

    @AuthMethod(Menus = "2001", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetContractReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ContractReview rtn = new ContractReview();
        if (ou != null) {
            rtn = ContDao.GetContractReview(this.getCr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "2001", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchContractReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCr().getTranid()).trim();
            String consignname = ToolUtils.Decode(this.getCr().getConsignname()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(consignname))
                search += ToolUtils.GetAndSearch(search) + " a.consignname like '%" + consignname + "%' ";
            this.SetSearch(this.getCr().getSearch(), this.getCr().getItem(), ou, search);
            List<ContractReview> lists = ContDao.SearchContractReview(this.getCr());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1401-合同-合同评审", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "2001", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveContractReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2001");
            List<ContractReviewDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), ContractReviewDetail.class);
            List<ContractReviewParam> params = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("params"), ContractReviewParam.class);
            List<ContractReviewSample> sample = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("sample"), ContractReviewSample.class);
            ContService.SaveContractReview(this.getCr(), details, sample, params, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 合同评审html
    public String HtmlContractReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ContractReview item = new ContractReview();
            item.setTranid(tranid);
            item = ContDao.GetContractReview(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlContractReview(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion ContractReview Methods

}
