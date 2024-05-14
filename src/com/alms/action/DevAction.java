package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.DevDao;
import com.alms.entity.dev.AcceptFileDetail;
import com.alms.entity.dev.AcceptManage;
import com.alms.entity.dev.AcceptPartsDetail;
import com.alms.entity.dev.BasDev;
import com.alms.entity.dev.BusTrade;
import com.alms.entity.dev.BuyApply;
import com.alms.entity.dev.DevBasic;
import com.alms.entity.dev.DevCalib;
import com.alms.entity.dev.DevCalibPlan;
import com.alms.entity.dev.DevCheck;
import com.alms.entity.dev.DevCheckPlan;
import com.alms.entity.dev.DevCommon;
import com.alms.entity.dev.DevPlan;
import com.alms.entity.dev.DevScrap;
import com.alms.entity.dev.DevTest;
import com.alms.entity.dev.DevUse;
import com.alms.entity.dev.DevUseAllot;
import com.alms.entity.dev.DevUseApply;
import com.alms.entity.dev.DevUseBack;
import com.alms.entity.dev.RepairApply;
import com.alms.entity.dev.RepairRecord;
import com.alms.entity.dev.TradeSurvey;
import com.alms.entity.lab.BusTaskDev;
import com.alms.service.DevService;
import com.alms.service.FlowFmtService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class DevAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region BusTrade Methods

    private BusTrade bustrade;

    public BusTrade getBustrade() {
        if (bustrade == null) {
            bustrade = new BusTrade();
        }
        return bustrade;
    }

    public void setBustrade(BusTrade bustrade) {
        this.bustrade = bustrade;
    }

    // @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType =
    // ActionOutType.Bean)
    public String GetBusTrade() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTrade busTrade = new BusTrade();
        if (ou != null) {
            busTrade = DevDao.GetBusTrade(this.getBustrade());
        }
        ToolUtils.OutString(this.OutBean(busTrade, true));

        return null;
    }

    // @AuthMethod(Menus = "1002", Auth = MenuAuth.BrowseExport, OutType =
    // ActionOutType.Array)
    public String SearchBusTrade() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tradename = ToolUtils.Decode(this.getBustrade().getTradename()).trim();

            if (!ToolUtils.StringIsEmpty(tradename))
                search += ToolUtils.GetAndSearch(search) + " a.tradename like '%" + tradename + "%' ";

            this.SetSearch(this.getBustrade().getSearch(), this.getBustrade().getItem(), ou, search);

            List<BusTrade> lists = DevDao.SearchBusTrade(this.getBustrade());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBustrade().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0501-业务-供应商表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    // @AuthMethod(Menus = "1001", Auth = MenuAuth.Modify, OutType =
    // ActionOutType.Bean)
    public String SaveBusTrade() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1001");
            DevService.SaveBusTrade(this.getBustrade(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchBusTradeForSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            String search = "";
            String tradename = ToolUtils.Decode(this.getBustrade().getTradename());

            if (!ToolUtils.StringIsEmpty(tradename))
                search += ToolUtils.GetAndSearch(search) + " a.tradename like '%" + tradename + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.tradestatus = '02' ";

            this.SetSearch(this.getBustrade().getSearch(), this.getBustrade().getItem(), ou, search);

            List<BusTrade> lists = DevDao.SearchBusTradeForSurvey(this.getBustrade());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBustrade().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0501-业务-供应商表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    // endregion BusTrade Methods

    // region TradeSurvey Methods

    private TradeSurvey trsu;

    public TradeSurvey getTrsu() {
        if (trsu == null) {
            trsu = new TradeSurvey();
        }
        return trsu;
    }

    public void setTrsu(TradeSurvey trsu) {
        this.trsu = trsu;
    }

    @AuthMethod(Menus = "1005", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTradeSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {

            String search = "";

            String tradeid = ToolUtils.Decode(this.getTrsu().getTradeid()).trim();
            String tradename = ToolUtils.Decode(this.getTrsu().getTradename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tradeid))
                search += ToolUtils.GetAndSearch(search) + " a.tradeid like '%" + tradeid + "%' ";

            if (!ToolUtils.StringIsEmpty(tradename))
                search += ToolUtils.GetAndSearch(search) + " a.tradename like '%" + tradename + "%' ";

            this.SetSearch(this.getTrsu().getSearch(), this.getTrsu().getItem(), ou, search);

            List<TradeSurvey> lists = DevDao.SearchTradeSurvey(this.getTrsu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTrsu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0501-业务-供应商调查表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveTradeSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1005");
            DevService.SaveTradeSurvey(this.getTrsu(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 供应商调查html
    public String HtmlTradeSurvey() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            TradeSurvey item = new TradeSurvey();
            item.setTranid(tranid);
            item = DevDao.GetTradeSurvey(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlTradeSurvey(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetTradeSurvey() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        TradeSurvey trsu = new TradeSurvey();
        if (ou != null) {
            trsu = DevDao.GetTradeSurvey(this.getTrsu());
        }
        ToolUtils.OutString(this.OutBean(trsu, true));

        return null;
    }

    @AuthMethod(Menus = "1007", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTradeCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        String search = "";

        if (ou != null) {
            search += ToolUtils.GetAndSearch(search) + " a.flowstatus in(02,04,05) ";
            this.SetSearch(this.getTrsu().getSearch(), this.getTrsu().getItem(), ou, search);

            List<TradeSurvey> lists = DevDao.SearchTradeCheck(this.getTrsu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTrsu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0502-业务-供应商调查评价", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1007", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTradeForCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        String search = "";

        if (ou != null) {
            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02' ";
            this.SetSearch(this.getTrsu().getSearch(), this.getTrsu().getItem(), ou, search);
            List<TradeSurvey> lists = DevDao.SearchBusTradeForCheck(this.getTrsu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTrsu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0502-业务-供应商调查评价", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveTradeCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1007");
            DevService.SaveTradeCheck(this.getTrsu(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTradeApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        String search = "";

        if (ou != null) {
            search += ToolUtils.GetAndSearch(search) + " a.flowstatus in(05,06) ";
            this.SetSearch(this.getTrsu().getSearch(), this.getTrsu().getItem(), ou, search);

            List<TradeSurvey> lists = DevDao.SearchTradeApprove(this.getTrsu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTrsu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0502-业务-供应商调查评价", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveTradeApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.SaveTradeApprove(this.getTrsu(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion TradeSurvey Methods

    // region BasDev Methods

    private BasDev bd;

    public BasDev getBd() {
        if (bd == null)
            bd = new BasDev();

        return bd;
    }

    public void setBd(BasDev bd) {
        this.bd = bd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        this.getBd().setDevid(ToolUtils.Decode(this.getBd().getDevid()));
        BasDev rtn = new BasDev();
        if (ou != null) {
            rtn = DevDao.GetBasDev(this.getBd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String devid = ToolUtils.Decode(this.getBd().getDevid());
            String devname = ToolUtils.Decode(this.getBd().getDevname());
            String devstatus = ToolUtils.Decode(this.getBd().getDevstatus());

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " d.deptid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.CheckComboValue(devstatus)) {
                search += ToolUtils.GetAndSearch(search) + " a.devstatus = '" + devstatus + "' ";
            }

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDev4Use() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "a.borrowstatu in(01,03) ";
            String devname = ToolUtils.Decode(this.getBd().getDevname()).trim();

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDevForTotal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String devid = ToolUtils.Decode(this.getBd().getDevid());
            String devname = ToolUtils.Decode(this.getBd().getDevname());
            String devstatus = ToolUtils.Decode(this.getBd().getDevstatus());

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " d.deptid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.CheckComboValue(devstatus)) {
                search += ToolUtils.GetAndSearch(search) + " a.devstatus = '" + devstatus + "' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.devstatus <> '04' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDevForCalibplan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getBd().getDevid());
            String devname = ToolUtils.Decode(this.getBd().getDevname());
            String begindate = ToolUtils.Decode(this.getBd().getStartdate());
            String enddate = ToolUtils.Decode(this.getBd().getEnddate());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nexttime  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nexttime  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDevForCalibplan(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    private BusTaskDev btd;

    public BusTaskDev getBtd() {
        if (btd == null)
            btd = new BusTaskDev();

        return btd;
    }

    public void setBt(BusTaskDev btd) {
        this.btd = btd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDevForUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getBtd().getDevid());
            String devname = ToolUtils.Decode(this.getBtd().getDevname());
            // String devstatus = ToolUtils.Decode(this.getBd().getDevstatus());

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " g.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " g.devname like '%" + devname + "%' ";

            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " g.labid =  '" + ou.getDept().getDeptid() + "' ";

                search += ToolUtils.GetAndSearch(search) + " g.testuser =  '" + ou.getUser().getUserid() + "' ";
            }

            // if (!ToolUtils.CheckComboValue(devstatus))
            // search += ToolUtils.GetAndSearch(search) + " a.devstatus = '"
            // + devstatus + "' ";

            this.SetSearch(this.getBtd().getSearch(), this.getBtd().getItem(), ou, search);

            List<BusTaskDev> lists = DevDao.SearchBasDevForUse(this.getBtd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDevForCheckPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getBd().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDevForCheckplan(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasDevForBuy() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getBd().getDevid());
            String devname = ToolUtils.Decode(this.getBd().getDevname());

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDevForBuy(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备一览表", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1009");
            DevService.SaveBasDev(this.getBd(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String OperateBasDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1009");
            List<BasDev> devs = ToolUtils.GetArrayFromJson(ServletActionContext.getRequest().getParameter("devs"),
                    BasDev.class);

            String operatereasons = ServletActionContext.getRequest().getParameter("operatereasons");

            String devstatus = ServletActionContext.getRequest().getParameter("devstatus");

            DevService.OperateBasDev(this.getBd(), devs, operatereasons, devstatus, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 停用申请html
    public String HtmlOperateDev() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("tranid"));
            BasDev item = new BasDev();
            item.setDevid(tranid);
            item = DevDao.GetBasDev(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlBasDev(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasDevByLab() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasDev> lists = DevDao.GetListBasDevByLab(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0131-基础-设备", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasDev Methods

    // region DevScrap Methods

    private DevScrap ds;

    public DevScrap getDs() {
        if (ds == null)
            ds = new DevScrap();

        return ds;
    }

    public void setDs(DevScrap ds) {
        this.ds = ds;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevScrap() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevScrap rtn = new DevScrap();
        if (ou != null) {
            rtn = DevDao.GetDevScrap(this.getDs());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevScrap() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String devid = ToolUtils.Decode(this.getDs().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDs().getDevname()).trim();
            String deptpid = ou.getDept().getDeptpid();
            if (!ToolUtils.StringIsEmpty(deptpid) && deptpid.equals("8000"))
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " b.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDs().getSearch(), this.getDs().getItem(), ou, search);

            List<DevScrap> lists = DevDao.SearchDevScrap(this.getDs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDs().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0220-设备-设备降级/报废申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevScrap() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1069");
            DevService.SaveDevScrap(this.getDs(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchBasDevForScrap() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getBd().getDevname());
            String devid = ToolUtils.Decode(this.getBd().getDevid());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            // search += ToolUtils.GetAndSearch(search) + " a.labid like '%"
            // + ou.getUser().getDeptid() + "%' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevScrapCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.SaveDevScrapCheck(this.getDs(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevScrapAudit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.SaveDevScrapAudit(this.getDs(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevScrapAllow() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.SaveDevScrapAllow(this.getDs(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 降级/报废申请html
    public String HtmlDevScrap() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevScrap item = new DevScrap();
            item.setTranid(tranid);
            item = DevDao.GetDevScrap(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevScrap(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion DevScrap Methods

    // region DevUse Methods

    private DevUse du;

    public DevUse getDu() {
        if (du == null)
            du = new DevUse();

        return du;
    }

    public void setDu(DevUse du) {
        this.du = du;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevUse rtn = new DevUse();
        if (ou != null) {
            rtn = DevDao.GetDevUse(this.getDu());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevUseBySampletran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevUse> lists = DevDao.GetListDevUseBySampletran(this.getDu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-使用", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevUseByDevID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String devid = ToolUtils.Decode(this.getDu().getDevid());
            this.getDu().setDevid(devid);

            List<DevUse> lists = DevDao.GetListDevUseByDevID(this.getDu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-使用", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevUseByUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            List<DevUse> lists = DevDao.GetListDevUseByUser(this.getDu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-使用", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getDu().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDu().getDevname()).trim();
            String deptpid = ou.getDept().getDeptpid();
            String userid = ou.getUser().getUserid();

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + userid + "' ";
                }
            }

            this.SetSearch(this.getDu().getSearch(), this.getDu().getItem(), ou, search);

            List<DevUse> lists = DevDao.SearchDevUse(this.getDu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0212-设备-设备使用记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1033");
            DevService.SaveDevUse(this.getDu(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DevUse Methods

    // region DevPlan Methods

    private DevPlan devplan;

    public DevPlan getDevplan() {
        if (devplan == null)
            devplan = new DevPlan();

        return devplan;
    }

    public void setDevplan(DevPlan devplan) {
        this.devplan = devplan;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevPlan rtn = new DevPlan();
        if (ou != null) {
            rtn = DevDao.GetDevPlan(this.getDevplan());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevPlan> lists = DevDao.GetListDevPlan(this.getDevplan());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("设备-检定、核查、维护总体计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";

            // String userid = ToolUtils.Decode(this.getDevplan().getUserid());
            String devname = ToolUtils.Decode(this.getDevplan().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDevplan().getSearch(), this.getDevplan().getItem(), ou, search);

            List<DevPlan> lists = DevDao.SearchDevPlan(this.getDevplan());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDevplan().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-检定、核查、维护总体计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevPlanForCalib() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";

            // String userid = ToolUtils.Decode(this.getDevplan().getUserid());
            String devname = ToolUtils.Decode(this.getDevplan().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDevplan().getSearch(), this.getDevplan().getItem(), ou, search);

            List<DevPlan> lists = DevDao.SearchDevPlanForCalib(this.getDevplan());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDevplan().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-检定、核查、维护总体计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1037");
            DevService.SaveDevPlan(this.getDevplan(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DevPlan Methods

    // region DevCommon Methods

    private DevCommon dc;

    public DevCommon getDc() {
        if (dc == null)
            dc = new DevCommon();

        return dc;
    }

    public void setDc(DevCommon dc) {
        this.dc = dc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevCommon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevCommon rtn = new DevCommon();
        if (ou != null) {
            rtn = DevDao.GetDevCommon(this.getDc());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevCommon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevCommon> lists = DevDao.GetListDevCommon(this.getDc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("设备-常用设备功能检查", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCommon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String devid = ToolUtils.Decode(this.getDc().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDc().getDevname()).trim();

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " d.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDc().getSearch(), this.getDc().getItem(), ou, search);

            List<DevCommon> lists = DevDao.SearchDevCommon(this.getDc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDc().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-常用设备功能检查", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevCommon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1045");
            DevService.SaveDevCommon(this.getDc(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 常用设备检查html
    public String HtmlDevCommon() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");

            DevCommon rtn = new DevCommon();
            rtn.setTranid(tranid);

            rtn = DevDao.GetDevCommonByTranID(rtn);

            StringBuilder sb = new StringBuilder();
            if (rtn != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevCommon(rtn));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    public String SearchBasDevForCommon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getBd().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            // search += ToolUtils.GetAndSearch(search) + " a.labid = '"
            // + ou.getUser().getDeptid() + "' ";

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion DevCommon Methods

    // region BuyApply Methods

    private BuyApply ba;

    public BuyApply getBa() {
        if (ba == null)
            ba = new BuyApply();

        return ba;
    }

    public void setBa(BuyApply ba) {
        this.ba = ba;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BuyApply rtn = new BuyApply();
        if (ou != null) {
            rtn = DevDao.GetBuyApply(this.getBa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BuyApply> lists = DevDao.GetListBuyApply(this.getBa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0202-设备-设备申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String devname = ToolUtils.Decode(this.getBa().getDevname()).trim();
            String userid = ou.getUser().getUserid();
            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.applydept = '" + deptid + "' ";
                    search += ToolUtils.GetAndSearch(search) + " a.applyuser = " + userid + " ";
                }
            }

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getBa().getSearch(), this.getBa().getItem(), ou, search);

            List<BuyApply> lists = DevDao.SearchBuyApply(this.getBa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0202-设备-设备申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBuyApplyForAccept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String devname = ToolUtils.Decode(this.getBa().getDevname());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            // search += ToolUtils.GetAndSearch(search) + " a.applydept like '%"
            // + ou.getUser().getDeptid() + "%' ";

            this.SetSearch(this.getBa().getSearch(), this.getBa().getItem(), ou, search);

            List<BuyApply> lists = DevDao.SearchBuyApplyForAccept(this.getBa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0202-设备-设备申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBuyApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1013");
            DevService.SaveBuyApply(this.getBa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 申请html
    public String HtmlDevApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            BuyApply item = new BuyApply();
            item.setTranid(tranid);
            item = DevDao.GetBuyApply(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion BuyApply Methods

    // region AcceptManage Methods

    private AcceptManage am;

    public AcceptManage getAm() {
        if (am == null)
            am = new AcceptManage();

        return am;
    }

    public void setAm(AcceptManage am) {
        this.am = am;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetAcceptManage() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        AcceptManage rtn = new AcceptManage();
        if (ou != null) {
            rtn = DevDao.GetAcceptManage(this.getAm());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListAcceptManage() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<AcceptManage> lists = DevDao.GetListAcceptManage(this.getAm());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备验收", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchAcceptManage() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getAm().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getAm().getDevname()).trim();

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            this.SetSearch(this.getAm().getSearch(), this.getAm().getItem(), ou, search);

            List<AcceptManage> lists = DevDao.SearchAcceptManage(this.getAm());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAm().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0203-设备-设备验收记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchAcceptForUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getAm().getDevname());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getAm().getSearch(), this.getAm().getItem(), ou, search);

            List<AcceptManage> lists = DevDao.SearchAcceptForUser(this.getAm());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAm().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0203-设备-设备验收记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAcceptManage() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1023");

            List<AcceptFileDetail> acceptfiledetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), AcceptFileDetail.class);
            List<AcceptPartsDetail> acceptpartsdetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("partsdetails"), AcceptPartsDetail.class);
            DevService.SaveAcceptManage(this.getAm(), acceptfiledetails, acceptpartsdetails, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 设备验收html
    public String HtmlAcceptManage() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            AcceptManage item = new AcceptManage();
            item.setTranid(tranid);
            item = DevDao.GetAcceptManageByTranID(item);

            AcceptFileDetail file = new AcceptFileDetail();
            file.setTranid(tranid);
            List<AcceptFileDetail> files = DevDao.GetListAcceptFileDetail(file);

            AcceptPartsDetail part = new AcceptPartsDetail();
            part.setAcceptid(tranid);
            List<AcceptPartsDetail> parts = DevDao.GetListAcceptPartsDetail(part);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlAcceptManage(item));
                sb.append(FlowFmtService.GetHtmlAcceptPartsDetail(parts));
                sb.append(FlowFmtService.GetHtmlAcceptFileDetail(files));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion AcceptManage Methods

    // region AcceptFileDetail Methods

    private AcceptFileDetail afd;

    public AcceptFileDetail getAfd() {
        if (afd == null)
            afd = new AcceptFileDetail();

        return afd;
    }

    public void setAfd(AcceptFileDetail afd) {
        this.afd = afd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetAcceptFileDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        AcceptFileDetail rtn = new AcceptFileDetail();
        if (ou != null) {
            rtn = DevDao.GetAcceptFileDetail(this.getAfd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListAcceptFileDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<AcceptFileDetail> lists = DevDao.GetListAcceptFileDetail(this.getAfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0204-设备-设备随机文件明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchAcceptFileDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getAfd().getSearch().setStart(start + 1);
            this.getAfd().getSearch().setEnd(this.GetEndCnt());

            List<AcceptFileDetail> lists = DevDao.SearchAcceptFileDetail(this.getAfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getAfd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0204-设备-设备随机文件明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAcceptFileDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1023");
            DevService.SaveAcceptFileDetail(this.getAfd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion AcceptFileDetail Methods

    // region DevCalib Methods

    private DevCalib decb;

    public DevCalib getDecb() {
        if (decb == null)
            decb = new DevCalib();

        return decb;
    }

    public void setDecb(DevCalib decb) {
        this.decb = decb;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevCalib() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevCalib rtn = new DevCalib();
        if (ou != null) {
            rtn = DevDao.GetDevCalib(this.getDecb());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCalib() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getDecb().getDevid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            this.SetSearch(this.getDecb().getSearch(), this.getDecb().getItem(), ou, search);

            List<DevCalib> lists = DevDao.SearchDevCalib(this.getDecb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDecb().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备检定/校准记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevCalib() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        String calibplanid = ServletActionContext.getRequest().getParameter("calibplanid");
        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1043");
            DevService.SaveDevCalib(this.getDecb(), this.getRtv(), calibplanid, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 设备校准html
    public String HtmlDevCalib() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevCalib item = new DevCalib();
            item.setTranid(tranid);
            item = DevDao.GetDevCalibByTranID(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevCalib(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion DevCalib Methods

    // region DevCalibPlan Methods

    private DevCalibPlan decp;

    public DevCalibPlan getDecp() {
        if (decp == null)
            decp = new DevCalibPlan();

        return decp;
    }

    public void setDecp(DevCalibPlan decp) {
        this.decp = decp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevCalibPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevCalibPlan rtn = new DevCalibPlan();
        if (ou != null) {
            rtn = DevDao.GetDevCalibPlan(this.getDecp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevCalibPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevCalibPlan> lists = DevDao.GetListDevCalibPlan(this.getDecp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("仪器年度检定校准计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCalibPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getDecp().getDevname()).trim();
            String begindate = ToolUtils.Decode(this.getDecp().getStartdate());
            String enddate = ToolUtils.Decode(this.getDecp().getEnddate());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            // 添加过滤 设备管理员显示所有，其他检测室人员只能显示本室的
            if ((ou.getUser().getUserid().equals("412")) || (ou.getDept().getDeptid().equals("9999"))) {

            } else {
                search += ToolUtils.GetAndSearch(search) + " f.labid =   '" + ou.getUser().getDeptid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDecp().getSearch(), this.getDecp().getItem(), ou, search);

            List<DevCalibPlan> lists = DevDao.SearchDevCalibPlan(this.getDecp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDecp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("仪器年度检定校准计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCalibPlanForCalib() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getDecp().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02'";

            this.SetSearch(this.getDecp().getSearch(), this.getDecp().getItem(), ou, search);

            List<DevCalibPlan> lists = DevDao.SearchDevCalibPlanForCalib(this.getDecp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDecp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("仪器年度检定校准计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevCalibPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1040");
            DevService.SaveDevCalibPlan(this.getDecp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 检定校准html
    public String HtmlDevCalibPlan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevCalibPlan item = new DevCalibPlan();
            item.setTranid(tranid);
            item = DevDao.GetDevCalibPlan(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevCalibPlan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion DevCalibPlan Methods

    // region DevCheckPlan Methods

    private DevCheckPlan dcp;

    public DevCheckPlan getDcp() {
        if (dcp == null)
            dcp = new DevCheckPlan();

        return dcp;
    }

    public void setDcp(DevCheckPlan dcp) {
        this.dcp = dcp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevCheckPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevCheckPlan rtn = new DevCheckPlan();
        if (ou != null) {
            rtn = DevDao.GetDevCheckPlan(this.getDcp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevCheckPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevCheckPlan> lists = DevDao.GetListDevCheckPlan();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("年仪器设备期间核查计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCheckPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String devid = ToolUtils.Decode(this.getDeck().getDevid());
            String devname = ToolUtils.Decode(this.getDcp().getDevname()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            // if (!ToolUtils.StringIsEmpty(devid))
            // search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid
            // + "%' ";

            String begindate = ToolUtils.Decode(this.getDcp().getStartdate());
            String enddate = ToolUtils.Decode(this.getDcp().getEnddate());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDcp().getSearch(), this.getDcp().getItem(), ou, search);

            List<DevCheckPlan> lists = DevDao.SearchDevCheckPlan(this.getDcp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDcp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("年仪器设备期间核查计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCheckPlanForCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devname = ToolUtils.Decode(this.getDcp().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02'";

            this.SetSearch(this.getDcp().getSearch(), this.getDcp().getItem(), ou, search);

            List<DevCheckPlan> lists = DevDao.SearchDevCheckPlanForCheck(this.getDcp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDcp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("年仪器设备期间核查计划", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevCheckPlan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1047");
            DevService.SaveDevCheckPlan(this.getDcp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 检定校准html
    public String HtmlDevCheckPlan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevCheckPlan item = new DevCheckPlan();
            item.setTranid(tranid);
            item = DevDao.GetDevCheckPlan(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevCheckPlan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion DevCheckPlan Methods

    // region DevCheck Methods

    private DevCheck deck;

    public DevCheck getDeck() {
        if (deck == null)
            deck = new DevCheck();

        return deck;
    }

    public void setDeck(DevCheck deck) {
        this.deck = deck;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevCheck rtn = new DevCheck();
        if (ou != null) {
            rtn = DevDao.GetDevCheck(this.getDeck());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevCheck> lists = DevDao.GetListDevCheck(this.getDeck());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("设备-期间核查记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getDeck().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDeck().getDevname()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            String begindate = ToolUtils.Decode(this.getDeck().getStartdate());
            String enddate = ToolUtils.Decode(this.getDeck().getEnddate());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextcheckdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.nextcheckdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDeck().getSearch(), this.getDeck().getItem(), ou, search);

            List<DevCheck> lists = DevDao.SearchDevCheck(this.getDeck());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDeck().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-期间核查记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1050");
            DevService.SaveDevCheck(this.getDeck(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 期间核查html
    public String HtmlDevCheck() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevCheck item = new DevCheck();
            item.setCheckid(tranid);
            item = DevDao.GetDevCheck(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlDevCheck(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion DevCheck Methods

    // region RepairApply Methods

    private RepairApply ra;

    public RepairApply getRa() {
        if (ra == null)
            ra = new RepairApply();

        return ra;
    }

    public void setRa(RepairApply ra) {
        this.ra = ra;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetRepairApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        RepairApply rtn = new RepairApply();
        if (ou != null) {
            rtn = DevDao.GetRepairApply(this.getRa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListRepairApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<RepairApply> lists = DevDao.GetListRepairApply(this.getRa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备报修单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRepairApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String devid = ToolUtils.Decode(this.getRa().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getRa().getDevname()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            String deptpid = ou.getDept().getDeptpid();
            if (!ToolUtils.StringIsEmpty(deptpid) && deptpid.equals("8000"))
                search += ToolUtils.GetAndSearch(search) + " a.manageruser = '" + ou.getUser().getUserid() + "' ";

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getRa().getSearch(), this.getRa().getItem(), ou, search);

            List<RepairApply> lists = DevDao.SearchRepairApply(this.getRa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备报修单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveRepairApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1055");
            DevService.SaveRepairApply(this.getRa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchBasDevForRepair() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String deptpid = ou.getDept().getDeptpid();
            String devname = ToolUtils.Decode(this.getBd().getDevname());

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (deptpid.equals("8000")) {
                search += ToolUtils.GetAndSearch(search) + " a.labid like '%" + ou.getUser().getDeptid() + "%' ";
            }

            this.SetSearch(this.getBd().getSearch(), this.getBd().getItem(), ou, search);

            List<BasDev> lists = DevDao.SearchBasDev(this.getBd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // dev 报修申请html
    public String HtmlRepairApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            RepairApply item = new RepairApply();
            item.setTranid(tranid);
            item = DevDao.GetRepairApply(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlRepairApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRepairApplyForRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            // String devid = ToolUtils.Decode(this.getRa().getDevid());
            String devname = ToolUtils.Decode(this.getRa().getDevname());
            // // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());
            //
            // if (!ToolUtils.StringIsEmpty(devid))
            // search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid
            // + "%' ";
            //
            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getRa().getSearch(), this.getRa().getItem(), ou, search);

            List<RepairApply> lists = DevDao.SearchRepairApplyForRecord(this.getRa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("设备-设备报修单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion RepairApply Methods

    // region RepairRecord Methods

    private RepairRecord rere;

    public RepairRecord getRere() {
        if (rere == null)
            rere = new RepairRecord();

        return rere;
    }

    public void setRere(RepairRecord rere) {
        this.rere = rere;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetRepairRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        RepairRecord rtn = new RepairRecord();
        if (ou != null) {
            rtn = DevDao.GetRepairRecord(this.getRere());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListRepairRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<RepairRecord> lists = DevDao.GetListRepairRecord(this.getRere());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 0231-仪器设备-仪器设备维修记录卡", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRepairRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String deptpid = ou.getDept().getDeptpid();
            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
                }
            }
            String devid = ToolUtils.Decode(this.getRere().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getRere().getDevname()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getRere().getSearch(), this.getRere().getItem(), ou, search);

            List<RepairRecord> lists = DevDao.SearchRepairRecord(this.getRere());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRere().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0231-仪器设备-仪器设备维修记录卡", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveRepairRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1063");
            DevService.SaveRepairRecord(this.getRere(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 维修记录html
    public String HtmlRepairRecord() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            RepairRecord item = new RepairRecord();
            item.setTranid(tranid);
            item = DevDao.GetRepairRecord(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlRepairRecord(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion RepairRecord Methods

    // region AcceptPartsDetail Methods

    private AcceptPartsDetail apd;

    public AcceptPartsDetail getApd() {
        if (apd == null)
            apd = new AcceptPartsDetail();

        return apd;
    }

    public void setApd(AcceptPartsDetail apd) {
        this.apd = apd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetAcceptPartsDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        AcceptPartsDetail rtn = new AcceptPartsDetail();
        if (ou != null) {
            rtn = DevDao.GetAcceptPartsDetail(this.getApd());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListAcceptPartsDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<AcceptPartsDetail> lists = DevDao.GetListAcceptPartsDetail(this.getApd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0205-设备-设备验收配件", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchAcceptPartsDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getApd().getSearch().setStart(start + 1);
            this.getApd().getSearch().setEnd(this.GetEndCnt());

            List<AcceptPartsDetail> lists = DevDao.SearchAcceptPartsDetail(this.getApd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getApd().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0205-设备-设备验收配件", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAcceptPartsDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1023");
            DevService.SaveAcceptPartsDetail(this.getApd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion AcceptPartsDetail Methods

    // region DevBasic Methods

    private DevBasic db;

    public DevBasic getDb() {
        if (db == null)
            db = new DevBasic();

        return db;
    }

    public void setDb(DevBasic db) {
        this.db = db;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevBasic() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevBasic rtn = new DevBasic();
        if (ou != null) {
            rtn = DevDao.GetDevBasic(this.getDb());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevBasic() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevBasic> lists = DevDao.GetListDevBasic(this.getDb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 0221-仪器设备-仪器设备基本情况简", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevBasic() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getDb().getSearch().setStart(start + 1);
            this.getDb().getSearch().setEnd(this.GetEndCnt());

            List<DevBasic> lists = DevDao.SearchDevBasic(this.getDb());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDb().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0221-仪器设备-仪器设备基本情况简", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevBasic() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DevService.SaveDevBasic(this.getDb(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DevBasic Methods

    // region DevTest Methods

    private DevTest devtest;

    public DevTest getDevtest() {
        if (devtest == null)
            devtest = new DevTest();

        return devtest;
    }

    public void setDevtest(DevTest devtest) {
        this.devtest = devtest;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDevTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DevTest rtn = new DevTest();
        if (ou != null) {
            rtn = DevDao.GetDevTest(this.getDevtest());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDevTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DevTest> lists = DevDao.GetListDevTest(this.getDevtest());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0141-基础-设备可检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDevTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getDevtest().getSearch().setStart(start + 1);
            this.getDevtest().getSearch().setEnd(this.GetEndCnt());

            List<DevTest> lists = DevDao.SearchDevTest(this.getDevtest());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDevtest().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0141-基础-设备可检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            List<DevTest> devtestdetails = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), DevTest.class);
            DevService.SaveDevTest(this.getDevtest(), this.getRtv(), devtestdetails, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DevTest Methods

    // region DevAcceptUse Methods

    private DevUseApply dua;

    public DevUseApply getDua() {
        if (dua == null)
            dua = new DevUseApply();

        return dua;
    }

    public void setUseApply(DevUseApply dua) {
        this.dua = dua;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUseApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1027");
            DevService.SaveDevUseApply(this.getDua(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUseApply4Allot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1028");
            this.getDua().setBorrowstatu("03");
            this.getDua().getDeal().setAction(DataAction.Modify.getAction());
            DevService.SaveDevUseApply(this.getDua(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUseApply4Back() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1029");
            this.getDua().setBorrowstatu("01");
            this.getDua().getDeal().setAction(DataAction.Modify.getAction());
            DevService.SaveDevUseApply(this.getDua(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchDevUseApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String devid = ToolUtils.Decode(this.getDua().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDua().getDevname()).trim();

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            if (!ToolUtils.StringIsEmpty(this.getDua().getBorrowstatu())) {
                search += ToolUtils.GetAndSearch(search) + " a.borrowstatu = '" + this.getDua().getBorrowstatu() + "' ";
            }
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.applyuser = '" + ou.getUser().getUserid() + "' ";
            }
            this.SetSearch(this.getDua().getSearch(), this.getDua().getItem(), ou, search);

            List<DevUseApply> lists = DevDao.SearchDevUseApply(this.getDua());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDua().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-设备-使用申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    public String SearchDevUseApply4Back() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String devid = ToolUtils.Decode(this.getDua().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDua().getDevname()).trim();
            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.borrowstatu = '03' ";

            this.SetSearch(this.getDua().getSearch(), this.getDua().getItem(), ou, search);

            List<DevUseApply> lists = DevDao.SearchDevUseApply(this.getDua());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDua().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-设备-使用申请", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    // 设备申请流程html
    public String HtmlUseApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DevUseApply item = new DevUseApply();
            item.setTranid(tranid);
            item.getItem().setGetaction("row");
            item = DevDao.GetDevUseApply(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlUseApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }

            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // end region DevAccept Methods

    // region DevUseAllot Methods
    private DevUseAllot dual;

    public DevUseAllot getDual() {
        if (dual == null)
            dual = new DevUseAllot();

        return dual;
    }

    public void setDual(DevUseAllot dual) {
        this.dual = dual;
    }

    public String SearchDevUseAllot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String devid = ToolUtils.Decode(this.getDual().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDual().getDevname()).trim();

            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";

            this.SetSearch(this.getDual().getSearch(), this.getDual().getItem(), ou, search);

            List<DevUseAllot> lists = DevDao.SearchDevUseAllot(this.getDual());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDual().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-设备-使用调拨", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUseAllot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1028");

            DevService.SaveDevUseAllot(this.getDual(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }
    // end region DevUseAllot Methods

    // region DevUseBack methods
    private DevUseBack dub;

    public DevUseBack getDub() {
        if (dub == null)
            dub = new DevUseBack();

        return dub;
    }

    public void setDub(DevUseBack dub) {
        this.dub = dub;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDevUseBack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1029");

            DevService.SaveDevUseBack(this.getDub(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchDevUseBack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String devid = ToolUtils.Decode(this.getDua().getDevid()).trim();
            String devname = ToolUtils.Decode(this.getDua().getDevname()).trim();
            if (!ToolUtils.StringIsEmpty(devid))
                search += ToolUtils.GetAndSearch(search) + " a.devid like '%" + devid + "%' ";

            if (!ToolUtils.StringIsEmpty(devname))
                search += ToolUtils.GetAndSearch(search) + " a.devname like '%" + devname + "%' ";
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.acceptuser = '" + ou.getUser().getUserid() + "' ";
            }
            this.SetSearch(this.getDub().getSearch(), this.getDub().getItem(), ou, search);

            List<DevUseBack> lists = DevDao.SearchDevUseBack(this.getDub());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDub().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-设备-使用调拨", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;

    }

    // end region DevUseBack methods
}
