package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.SubDao;
import com.alms.entity.sub.SubReview;
import com.alms.entity.sub.SubReviewDetail;
import com.alms.service.FlowFmtService;
import com.alms.service.SubService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class SubAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region SubReview Methods

    private SubReview sr;

    public SubReview getSr() {
        if (sr == null)
            sr = new SubReview();

        return sr;
    }

    public void setSr(SubReview sr) {
        this.sr = sr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSubReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SubReview rtn = new SubReview();
        if (ou != null) {
            rtn = SubDao.GetSubReview(this.getSr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSubReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SubReview> lists = SubDao.GetListSubReview(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0801-分包-分包评审", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchSubReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getSr().getTranid()).trim();
            String subname = ToolUtils.Decode(this.getSr().getSubname()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(subname))
                search += ToolUtils.GetAndSearch(search) + " a.subname like '%" + subname + "%' ";

            this.SetSearch(this.getSr().getSearch(), this.getSr().getItem(), ou, search);

            List<SubReview> lists = SubDao.SearchSubReview(this.getSr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0801-分包-分包评审", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSubReview() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1501");
            List<SubReviewDetail> subReviewDetails = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), SubReviewDetail.class);
            SubService.SaveSubReview(this.getSr(), this.getRtv(), subReviewDetails, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 分包评审html
    public String HtmlSubReview() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            SubReview item = new SubReview();
            item.setTranid(tranid);
            item = SubDao.GetSubReview(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlSubReview(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion SubReview Methods

    // region SubReviewDetail Methods

    private SubReviewDetail sfd;

    public SubReviewDetail getSfd() {
        if (sfd == null)
            sfd = new SubReviewDetail();

        return sfd;
    }

    public void setSfd(SubReviewDetail sfd) {
        this.sfd = sfd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSubReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SubReviewDetail rtn = new SubReviewDetail();
        if (ou != null) {
            rtn = SubDao.GetSubReviewDetail(this.getSfd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSubReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SubReviewDetail> lists = SubDao.GetListSubReviewDetail(this.getSfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0802-分包-分包评审文件明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchSubReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getSfd().getSearch().setStart(start + 1);
            this.getSfd().getSearch().setEnd(this.GetEndCnt());

            List<SubReviewDetail> lists = SubDao.SearchSubReviewDetail(this.getSfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSfd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0802-分包-分包评审文件明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSubReviewDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            SubService.SaveSubReviewDetail(this.getSfd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion SubReviewDetail Methods

}
