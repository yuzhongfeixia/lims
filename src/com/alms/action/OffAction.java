package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.*;
import com.alms.entity.office.*;
import com.alms.service.FlowFmtService;
import com.alms.service.OffService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class OffAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region OfficeApplyDetail Methods

    private OfficeApplyDetail od;

    public OfficeApplyDetail getOd() {
        if (od == null)
            od = new OfficeApplyDetail();

        return od;
    }

    public void setOd(OfficeApplyDetail od) {
        this.od = od;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListOfficeApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<OfficeApplyDetail> lists = OffDao.GetListOfficeApplyDetail(this.getOd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1302-办公用品-办公用品明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion OfficeApplyDetail Methods

    // region OfficeApply Methods

    private OfficeApply off;

    public OfficeApply getOff() {
        if (off == null)
            off = new OfficeApply();

        return off;
    }

    public void setOff(OfficeApply off) {
        this.off = off;
    }

    // @AuthMethod(Menus="2101", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetOfficeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        OfficeApply rtn = new OfficeApply();
        if (ou != null) {
            rtn = OffDao.GetOfficeApply(this.getOff());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="2101", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchOfficeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getOff().getTranid());
            String projid = ToolUtils.Decode(this.getOff().getProjid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(projid))
                search += ToolUtils.GetAndSearch(search) + " a.projid like '%" + projid + "%' ";

            this.SetSearch(this.getOff().getSearch(), this.getOff().getItem(), ou, search);

            List<OfficeApply> lists = OffDao.SearchOfficeApply(this.getOff());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getOff().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1301-办公用品-办公用品采购申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "2101", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveOfficeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2101");
            List<OfficeApplyDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), OfficeApplyDetail.class);
            OffService.SaveOfficeApply(this.getOff(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 办公用品 申请html
    public String HtmlPrdApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            OfficeApply item = new OfficeApply();
            item.setTranid(tranid);
            item = OffDao.GetOfficeApply(item);
            OfficeApplyDetail detail = new OfficeApplyDetail();
            detail.setTranid(tranid);
            List<OfficeApplyDetail> details = OffDao.GetListOfficeApplyDetail(detail);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlOfficeApplyDetail(details));
                sb.append(FlowFmtService.GetHtmlOfficeApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion OfficeApply Methods

}
