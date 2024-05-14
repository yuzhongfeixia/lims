package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.SampDao;
import com.alms.entity.samp.*;
import com.alms.service.FlowFmtService;
import com.alms.service.SampService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.*;

public class SampAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region SampleBackup Methods

    private SampleBackup backup;

    public SampleBackup getBackup() {
        if (backup == null)
            backup = new SampleBackup();

        return backup;
    }

    public void setBackup(SampleBackup backup) {
        this.backup = backup;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSampleBackup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SampleBackup rtn = new SampleBackup();
        if (ou != null) {
            rtn = SampDao.GetSampleBackup(this.getBackup());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchSampleBackup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplename = ToolUtils.Decode(this.getBackup().getSamplename());

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename.trim() + "%' ";

            this.SetSearch(this.getBackup().getSearch(), this.getBackup().getItem(), ou, search);

            List<SampleBackup> lists = SampDao.SearchSampleBackup(this.getBackup());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBackup().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-备份样品申领、拆、封样现场记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSampleBackup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0631");
            SampService.SaveSampleBackup(this.getBackup(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 质量控制评价html
    public String HtmlSampleBackup() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            SampleBackup item = new SampleBackup();
            item.setTranid(tranid);
            item = SampDao.GetSampleBackup(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlSampleBackup(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion SampleBackup Methods

    // region SampleEnvDetail Methods

    private SampleEnvDetail sed;

    public SampleEnvDetail getSed() {
        if (sed == null)
            sed = new SampleEnvDetail();

        return sed;
    }

    public void setSed(SampleEnvDetail sed) {
        this.sed = sed;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSampleEnvDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SampleEnvDetail> lists = SampDao.GetListSampleEnvDetail(this.getSed());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-环境样品采样单明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion SampleEnvDetail Methods

    // region SampleEnv Methods

    private SampleEnv sampenv;

    public SampleEnv getSampenv() {
        if (sampenv == null)
            sampenv = new SampleEnv();

        return sampenv;
    }

    public void setSampenv(SampleEnv sampenv) {
        this.sampenv = sampenv;
    }

    // @AuthMethod(Menus="0611", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetSampleEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SampleEnv rtn = new SampleEnv();
        if (ou != null) {
            rtn = SampDao.GetSampleEnv(this.getSampenv());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0611", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchSampleEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String entername = ToolUtils.Decode(this.getSampenv().getEntername());
            String enterbase = ToolUtils.Decode(this.getSampenv().getEnterbase());

            if (!ToolUtils.StringIsEmpty(entername))
                search += ToolUtils.GetAndSearch(search) + " a.entername like '%" + entername + "%' ";

            if (!ToolUtils.StringIsEmpty(enterbase))
                search += ToolUtils.GetAndSearch(search) + " a.enterbase like '%" + enterbase + "%' ";

            this.SetSearch(this.getSampenv().getSearch(), this.getSampenv().getItem(), ou, search);

            List<SampleEnv> lists = SampDao.SearchSampleEnv(this.getSampenv());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSampenv().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-环境样品采样单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0611", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSampleEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0611");
            List<SampleEnvDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), SampleEnvDetail.class);
            SampService.SaveSampleEnv(this.getSampenv(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion SampleEnv Methods

    // region SampleDeal Methods

    private SampleDeal sampdeal;

    public SampleDeal getSampdeal() {
        if (sampdeal == null)
            sampdeal = new SampleDeal();

        return sampdeal;
    }

    public void setSampdeal(SampleDeal sampdeal) {
        this.sampdeal = sampdeal;
    }

    // @AuthMethod(Menus="0621", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetSampleDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SampleDeal rtn = new SampleDeal();
        if (ou != null) {
            rtn = SampDao.GetSampleDeal(this.getSampdeal());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    public String GetSampleDealByTaskID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SampleDeal rtn = new SampleDeal();
        if (ou != null) {
            rtn = SampDao.GetSampleDealByTaskID(this.getSampdeal());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0621", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchSampleDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplename = ToolUtils.Decode(this.getSampdeal().getSamplename());
            String samplecode = ToolUtils.Decode(this.getSampdeal().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";

            if (!ToolUtils.StringIsEmpty(samplecode))
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";

            this.SetSearch(this.getSampdeal().getSearch(), this.getSampdeal().getItem(), ou, search);

            List<SampleDeal> lists = SampDao.SearchSampleDeal(this.getSampdeal());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSampdeal().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-样品处置单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0621", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSampleDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0621");
            SampService.SaveSampleDeal(this.getSampdeal(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 样品处置html
    public String HtmlSampleDeal() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            SampleDeal item = new SampleDeal();
            item.setTranid(tranid);
            item = SampDao.GetSampleDeal(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlSampleDeal(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion SampleDeal Methods

    // region SampleIceDetail Methods

    private SampleIceDetail sid;

    public SampleIceDetail getSid() {
        if (sid == null)
            sid = new SampleIceDetail();

        return sid;
    }

    public void setSid(SampleIceDetail sid) {
        this.sid = sid;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSampleIceDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            this.getSid().setIceid(ToolUtils.Decode(this.getSid().getIceid()));

            List<SampleIceDetail> lists = SampDao.GetListSampleIceDetail(this.getSid());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-样品贮存冰柜温度记录明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion SampleIceDetail Methods

    // region SampleIce Methods

    private SampleIce sampice;

    public SampleIce getSampice() {
        if (sampice == null)
            sampice = new SampleIce();

        return sampice;
    }

    public void setSampice(SampleIce sampice) {
        this.sampice = sampice;
    }

    // @AuthMethod(Menus="0601", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetSampleIce() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SampleIce rtn = new SampleIce();
        if (ou != null) {
            rtn = SampDao.GetSampleIce(this.getSampice());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="0601", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchSampleIce() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String iceid = ToolUtils.Decode(this.getSampice().getIceid());
            String icename = ToolUtils.Decode(this.getSampice().getIcename());

            if (!ToolUtils.StringIsEmpty(iceid))
                search += ToolUtils.GetAndSearch(search) + " a.iceid like '%" + iceid + "%' ";

            if (!ToolUtils.StringIsEmpty(icename))
                search += ToolUtils.GetAndSearch(search) + " a.icename like '%" + icename + "%' ";

            this.SetSearch(this.getSampice().getSearch(), this.getSampice().getItem(), ou, search);

            List<SampleIce> lists = SampDao.SearchSampleIce(this.getSampice());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSampice().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品-样品贮存冰柜温度记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0601", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSampleIce() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0601");
            List<SampleIceDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), SampleIceDetail.class);
            SampService.SaveSampleIce(this.getSampice(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion SampleIce Methods

}
