package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.FormDao;
import com.alms.entity.dat.BusReportDetail;
import com.alms.entity.dat.SetBusGet;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dat.SetBusReport;
import com.alms.entity.form.CusOrder;
import com.alms.entity.form.CustomerCount;
import com.alms.entity.form.DevType;
import com.alms.entity.form.DevUseCount;
import com.alms.entity.form.FrmGet;
import com.alms.entity.form.FrmGetDetail;
import com.alms.entity.form.FrmRecord;
import com.alms.entity.form.FrmRecordDetail;
import com.alms.entity.form.FrmRecordParameter;
import com.alms.entity.form.FrmReport;
import com.alms.entity.form.FrmReportDetail;
import com.alms.entity.form.IntField;
import com.alms.entity.form.IntInterface;
import com.alms.entity.form.LabParameter;
import com.alms.entity.form.LabSample;
import com.alms.entity.form.PrdCodeCount;
import com.alms.entity.form.PrdIn;
import com.alms.entity.form.PrdOut;
import com.alms.entity.form.PrdType;
import com.alms.entity.form.SamplePass;
import com.alms.entity.form.SendSample;
import com.alms.entity.form.TesterParameter;
import com.alms.entity.form.TesterSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.service.FormService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class FormAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region IntInterface Methods

    private IntInterface iif;

    public IntInterface getIif() {
        if (iif == null)
            iif = new IntInterface();

        return iif;
    }

    public void setIi(IntInterface iif) {
        this.iif = iif;
    }

    private BusTaskSingle btsg;

    public BusTaskSingle getBtsg() {
        if (btsg == null)
            btsg = new BusTaskSingle();
        return btsg;
    }

    public void setBtsg(BusTaskSingle btsg) {
        this.btsg = btsg;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIntInterface() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IntInterface rtn = new IntInterface();
        if (ou != null) {
            rtn = FormDao.GetIntInterface(this.getIif());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetIntInterfaceByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            List<IntInterface> lists = FormService.GetIntInterfaceByTask(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("界面-界面定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIntInterface() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String intname = ToolUtils.Decode(this.getIif().getIntname());

            if (!ToolUtils.StringIsEmpty(intname)) {
                search += ToolUtils.GetAndSearch(search) + " a.intname like '%" + intname + "%' ";
            }

            this.SetSearch(this.getIif().getSearch(), this.getIif().getItem(), ou, search);

            List<IntInterface> lists = FormDao.SearchIntInterface(this.getIif());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIif().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("界面-界面定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIntInterface() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0201");

            List<IntField> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), IntField.class);

            FormService.SaveIntInterface(this.getIif(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion IntInterface Methods

    // region IntField Methods

    private IntField ifd;

    public IntField getIfd() {
        if (ifd == null)
            ifd = new IntField();

        return ifd;
    }

    public void setIfd(IntField ifd) {
        this.ifd = ifd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIntField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IntField rtn = new IntField();
        if (ou != null) {
            rtn = FormDao.GetIntField(this.getIfd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListIntField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<IntField> lists = FormDao.GetListIntField(this.getIfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("界面-界面字段定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListIntFieldBySample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<IntField> lists = FormDao.GetListIntFieldBySample(this.getIfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("界面-界面字段定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIntField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getIfd().getSearch().setStart(start + 1);
            this.getIfd().getSearch().setEnd(this.GetEndCnt());

            List<IntField> lists = FormDao.SearchIntField(this.getIfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("界面-界面字段定义", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion IntField Methods

    // region FrmRecord Methods

    private FrmRecord fr;

    public FrmRecord getFr() {
        if (fr == null)
            fr = new FrmRecord();

        return fr;
    }

    public void setFr(FrmRecord fr) {
        this.fr = fr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFrmRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FrmRecord rtn = new FrmRecord();
        if (ou != null) {
            rtn = FormDao.GetFrmRecord(this.getFr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFrmRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmRecord> lists = FormDao.GetListFrmRecord(this.getFr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5001-单-原始记录样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetFrmRecordDetailByForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmRecordDetail> lists = FormDao.GetFrmRecordDetailByForm(this.getFr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("原始记录样式明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFrmRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String formname = ToolUtils.Decode(this.getFr().getFormname());

            if (!ToolUtils.StringIsEmpty(formname))
                search += ToolUtils.GetAndSearch(search) + " a.formname like '%" + formname + "%' ";

            this.SetSearch(this.getFr().getSearch(), this.getFr().getItem(), ou, search);

            List<FrmRecord> lists = FormDao.SearchFrmRecord(this.getFr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("5001-单-原始记录样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFrmRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0203");

            List<FrmRecordDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), FrmRecordDetail.class);
            FormService.SaveFrmRecord(this.getFr(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetSetFrmRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusRecord rtn = new SetBusRecord();
        if (ou != null) {
            rtn = FormService.GetSetFrmRecord(this.getFr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // endregion FrmRecord Methods

    // region FrmRecord Methods

    private FrmGet fg;

    public FrmGet getFg() {
        if (fg == null)
            fg = new FrmGet();

        return fg;
    }

    public void setFr(FrmGet fg) {
        this.fg = fg;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFrmGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FrmGet rtn = new FrmGet();
        if (ou != null) {
            rtn = FormDao.GetFrmGet(this.getFg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFrmGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmGet> lists = FormDao.GetListFrmGet(this.getFg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5001-单-原始记录样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetFrmGetDetailByForm() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmGetDetail> lists = FormDao.GetFrmGetDetailByForm(this.getFg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("原始记录样式明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFrmGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String formname = ToolUtils.Decode(this.getFg().getFormname());

            if (!ToolUtils.StringIsEmpty(formname))
                search += ToolUtils.GetAndSearch(search) + " a.formname like '%" + formname + "%' ";

            this.SetSearch(this.getFg().getSearch(), this.getFg().getItem(), ou, search);

            List<FrmGet> lists = FormDao.SearchFrmGet(this.getFg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("5001-单-原始记录样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFrmGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0212");

            List<FrmGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), FrmGetDetail.class);
            FormService.SaveFrmGet(this.getFg(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetSetFrmGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusGet rtn = new SetBusGet();
        if (ou != null) {
            rtn = FormService.GetSetFrmGet(this.getFg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // endregion FrmGet Methods

    // region FrmRecordParameter

    private FrmRecordParameter frp;

    public FrmRecordParameter getFrp() {
        if (frp == null) {
            frp = new FrmRecordParameter();
        }
        return frp;
    }

    public void setFrp(FrmRecordParameter frp) {
        this.frp = frp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFormRecordParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String formname = ToolUtils.Decode(this.frp().getFormname());

            // if (!ToolUtils.StringIsEmpty(formname))
            // search += ToolUtils.GetAndSearch(search) + " a.formname like '%" +
            // formname + "%' ";

            this.SetSearch(this.getFrp().getSearch(), this.getFrp().getItem(), ou, search);

            List<FrmRecordParameter> lists = FormDao.SearchFrmRecordParameter(this.getFrp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("原始记录-检测参数表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFormRecordParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            FormService.SaveFrmRecordParameter(this.getFrp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FrmRecordParameter

    // region FrmReport Methods

    private FrmReport freport;

    public FrmReport getFreport() {
        if (freport == null)
            freport = new FrmReport();

        return freport;
    }

    public void setFreport(FrmReport freport) {
        this.freport = freport;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFrmReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FrmReport rtn = new FrmReport();
        if (ou != null) {
            rtn = FormDao.GetFrmReport(this.getFreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFrmReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmReport> lists = FormDao.GetListFrmReport(this.getFreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5021-单-试验报告样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFrmReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getFreport().getSearch().setStart(start + 1);
            this.getFreport().getSearch().setEnd(this.GetEndCnt());
            String formname = ToolUtils.Decode(this.freport.getFormname().trim());
            String search = "";

            if (!ToolUtils.StringIsEmpty(formname))
                search += ToolUtils.GetAndSearch(search) + " a.formname like '%" + formname + "%' ";
            this.SetSearch(this.getFreport().getSearch(), this.getFreport().getItem(), ou, search);
            List<FrmReport> lists = FormDao.SearchFrmReport(this.getFreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFreport().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5021-单-试验报告样式", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFrmReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<FrmReportDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), FrmReportDetail.class);
            FormService.SaveFrmReport(this.getFreport(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusPrintReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            List<BusReportDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusReportDetail.class);
            FormService.SaveBusPrintReport(this.getBreport(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetSetFrmReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusReport rtn = new SetBusReport();
        if (ou != null) {
            rtn = FormService.GetSetFrmReport(this.getFreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // endregion FrmReport Methods

    // region FrmReportDetail Methods

    private FrmReportDetail freportd;

    public FrmReportDetail getFreportd() {
        if (freportd == null)
            freportd = new FrmReportDetail();

        return freportd;
    }

    public void setFreportd(FrmReportDetail freportd) {
        this.freportd = freportd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetFrmReportDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        FrmReportDetail rtn = new FrmReportDetail();
        if (ou != null) {
            rtn = FormDao.GetFrmReportDetail(this.getFreportd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListFrmReportDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<FrmReportDetail> lists = FormDao.GetListFrmReportDetail(this.getFreportd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5022-单-试验报告样式明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchFrmReportDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getFreportd().getSearch().setStart(start + 1);
            this.getFreportd().getSearch().setEnd(this.GetEndCnt());

            List<FrmReportDetail> lists = FormDao.SearchFrmReportDetail(this.getFreportd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getFreportd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5022-单-试验报告样式明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveFrmReportDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            FormService.SaveFrmReportDetail(this.getFreportd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion FrmReportDetail Methods

    // region BusReportDetail Methods

    private BusReportDetail breport;

    public BusReportDetail getBreport() {
        if (breport == null)
            breport = new BusReportDetail();

        return breport;
    }

    public void setBreport(BusReportDetail breport) {
        this.breport = breport;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusReportDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusReportDetail> lists = FormDao.GetListBusReportDetail(this.getBreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("5022-单-试验报告样式明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }
    // endregion BusReportDetail Methods

    // region LabSample Methods

    private LabSample ls;

    public LabSample getLs() {
        if (ls == null)
            ls = new LabSample();
        return ls;
    }

    public void setLs(LabSample ls) {
        this.ls = ls;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetLabSampleByMonth() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<LabSample> lists = FormDao.GetLabSampleByMonth(this.getLs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }
    // endregion LabSample Methods

    // region LabParameter Methods

    private LabParameter lp;

    public LabParameter getLp() {
        if (lp == null)
            lp = new LabParameter();
        return lp;
    }

    public void setLp(LabParameter lp) {
        this.lp = lp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetLabParameterByYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<LabParameter> lists = FormDao.GetLabParameterByYear(this.getLp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion LabParameter Methods

    // region TesterParameter Methods

    private TesterParameter tp;

    public TesterParameter getTp() {
        if (tp == null)
            tp = new TesterParameter();
        return tp;
    }

    public void setTp(TesterParameter tp) {
        this.tp = tp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetTesterParameter() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<TesterParameter> lists = FormDao.GetTesterParameter(this.getTp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-检测员检测参数统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion TesterParameter Methods

    // region TesterSample Methods

    private TesterSample ts;

    public TesterSample getTs() {
        if (ts == null)
            ts = new TesterSample();
        return ts;
    }

    public void setTs(TesterSample ts) {
        this.ts = ts;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetTesterSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<TesterSample> lists = FormDao.GetTesterSample(this.getTs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-检测员检测参数统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion TesterSample Methods

    // region SamplePass Methods

    private SamplePass sp;

    public SamplePass getSp() {
        if (sp == null)
            sp = new SamplePass();
        return sp;
    }

    public void setSp(SamplePass sp) {
        this.sp = sp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetSamplePass() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SamplePass> lists = FormDao.GetSamplePass(this.getSp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-样品合格率统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion SamplePass Methods

    // region PrdType Methods

    private PrdType pt;

    public PrdType getPt() {
        if (pt == null)
            pt = new PrdType();
        return pt;
    }

    public void setPt(PrdType pt) {
        this.pt = pt;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetPrdType() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdType> lists = FormDao.GetPrdType(this.getPt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdType Methods

    // region PrdCodeCount Methods

    private PrdCodeCount pcc;

    public PrdCodeCount getPcc() {
        if (pcc == null)
            pcc = new PrdCodeCount();
        return pcc;
    }

    public void setPcc(PrdCodeCount pcc) {
        this.pcc = pcc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetPrdCodeCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdCodeCount> lists = FormDao.GetPrdCodeCount(this.getPcc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdCodeCount Methods

    // region PrdIn Methods

    private PrdIn pi;

    public PrdIn getPi() {
        if (pi == null)
            pi = new PrdIn();
        return pi;
    }

    public void setPi(PrdIn pi) {
        this.pi = pi;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetPrdIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdIn> lists = FormDao.GetPrdIn(this.getPi());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdIn Methods

    // region PrdOut Methods

    private PrdOut po;

    public PrdOut getPo() {
        if (po == null)
            po = new PrdOut();
        return po;
    }

    public void setPo(PrdOut po) {
        this.po = po;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetPrdOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdOut> lists = FormDao.GetPrdOut(this.getPo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdOut Methods

    // region DevType Methods

    private DevType dt;

    public DevType getDt() {
        if (dt == null)
            dt = new DevType();
        return dt;
    }

    public void setDt(DevType dt) {
        this.dt = dt;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetDevType() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevType> lists = FormDao.GetDevType(this.getDt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion DevType Methods

    // region DevUseCount Methods

    private DevUseCount duc;

    public DevUseCount getDuc() {
        if (duc == null)
            duc = new DevUseCount();
        return duc;
    }

    public void setDuc(DevUseCount duc) {
        this.duc = duc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetDevUseCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevUseCount> lists = FormDao.GetDevUseCount(this.getDuc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion DevUseCount Methods

    // region CusOrder Methods

    private CusOrder co;

    public CusOrder getCo() {
        if (co == null)
            co = new CusOrder();
        return co;
    }

    public void setDuc(CusOrder co) {
        this.co = co;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetCusOrder() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<CusOrder> lists = FormDao.GetCusOrder(this.getCo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion CusOrder Methods

    // region SendSample Methods

    private SendSample ss;

    public SendSample getSs() {
        if (ss == null)
            ss = new SendSample();
        return ss;
    }

    public void setSs(SendSample ss) {
        this.ss = ss;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetSendSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SendSample> lists = FormDao.GetSendSample(this.getSs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion SendSample Methods

    // region CustomerCount Methods

    private CustomerCount cc;

    public CustomerCount getCc() {
        if (cc == null)
            cc = new CustomerCount();
        return cc;
    }

    public void setCc(CustomerCount cc) {
        this.cc = cc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetCustomerCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<CustomerCount> lists = FormDao.GetCustomerCount(this.getCc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("业务-总增长量统计", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion CustomerCount Methods

}
