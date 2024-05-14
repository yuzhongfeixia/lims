package com.alms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.*;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.prd.*;
import com.alms.service.*;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class PrdAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region PrdPoisonFile Methods

    private PrdPoisonFile ppf;

    public PrdPoisonFile getPpf() {
        if (ppf == null)
            ppf = new PrdPoisonFile();

        return ppf;
    }

    public void setPpf(PrdPoisonFile ppf) {
        this.ppf = ppf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdPoisonFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdPoisonFile> lists = PrdDao.GetListPrdPoisonFile(this.getPpf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("031 - 剧毒易制毒申请 - 附件", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdPoisonFile Methods

    // region PrdPoisonDetail Methods

    private PrdPoisonDetail ppd;

    public PrdPoisonDetail getPpd() {
        if (ppd == null)
            ppd = new PrdPoisonDetail();

        return ppd;
    }

    public void setPpd(PrdPoisonDetail ppd) {
        this.ppd = ppd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdPoisonDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdPoisonDetail> lists = PrdDao.GetListPrdPoisonDetail(this.getPpd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1802 - 耗材 -易制毒化学品使用申请明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdPoisonDetail Methods

    // region PrdPoison Methods

    private PrdPoison pp;

    public PrdPoison getPp() {
        if (pp == null)
            pp = new PrdPoison();

        return pp;
    }

    public void setPp(PrdPoison pp) {
        this.pp = pp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetPrdPoison() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdPoison rtn = new PrdPoison();
        if (ou != null) {
            rtn = PrdDao.GetPrdPoison(this.getPp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdPoison() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdPoison> lists = PrdDao.GetListPrdPoison(this.getPp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1801 - 耗材 -易制毒化学品使用申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchPrdPoison() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getPp().getTranid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getPp().getSearch(), this.getPp().getItem(), ou, search);

            List<PrdPoison> lists = PrdDao.SearchPrdPoison(this.getPp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1801 - 耗材 -易制毒化学品使用申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdPoison() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1141");
            List<PrdPoisonDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), PrdPoisonDetail.class);
            List<PrdPoisonFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), PrdPoisonFile.class);
            PrdService.SavePrdPoison(this.getPp(), details, filedetails, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 易制毒用品使用申请html
    public String HtmlPrdPoison() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            PrdPoison item = new PrdPoison();
            item.setTranid(tranid);
            item = PrdDao.GetPrdPoison(item);
            PrdPoisonDetail detail = new PrdPoisonDetail();
            detail.setTranid(tranid);
            List<PrdPoisonDetail> details = PrdDao.GetListPrdPoisonDetail(detail);
            PrdPoisonFile file = new PrdPoisonFile();
            file.setTranid(tranid);
            List<PrdPoisonFile> files = PrdDao.GetListPrdPoisonFile(file);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlPrdPoisonDetail(details));
                sb.append(FlowFmtService.GetHtmlPrdPoison(item));
                sb.append(FlowFmtService.GetHtmlPrdPoisonFile(files));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion PrdPoison Methods

    // region PrdCodeDetail Methods

    private PrdCodeDetail pcd;

    public PrdCodeDetail getPcd() {
        if (pcd == null)
            pcd = new PrdCodeDetail();

        return pcd;
    }

    public void setPcd(PrdCodeDetail pcd) {
        this.pcd = pcd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetPrdCodeDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdCodeDetail rtn = new PrdCodeDetail();
        if (ou != null) {
            rtn = PrdDao.GetPrdCodeDetail(this.getPcd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdCodeDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdCodeDetail> lists = PrdDao.GetListPrdCodeDetail(this.getPcd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0330-库存-耗材试剂条码明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchPrdCodeDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getPcd().getSearch().setStart(start + 1);
            this.getPcd().getSearch().setEnd(this.GetEndCnt());

            List<PrdCodeDetail> lists = PrdDao.SearchPrdCodeDetail(this.getPcd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPcd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0330-库存-耗材试剂条码明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdCodeDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            PrdService.SavePrdCodeDetail(this.getPcd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion PrdCodeDetail Methods

    // region PrdCode Methods

    private PrdCode pc;

    public PrdCode getPc() {
        if (pc == null)
            pc = new PrdCode();

        return pc;
    }

    public void setPc(PrdCode pc) {
        this.pc = pc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetPrdCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdCode rtn = new PrdCode();
        if (ou != null) {
            rtn = PrdDao.GetPrdCode(this.getPc());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdCode> lists = PrdDao.GetListPrdCode(this.getPc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0329-库存-试剂耗材条码", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchPrdCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String prdname = ToolUtils.Decode(this.getPc().getPrdname());
            String storeid = this.getPc().getStoreid();

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " b.prdname  like '%" + prdname + "%' ";

            if (!ToolUtils.StringIsEmpty(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid  = '" + storeid + "' ";

            this.SetSearch(this.getPc().getSearch(), this.getPc().getItem(), ou, search);

            List<PrdCode> lists = PrdDao.SearchPrdCode(this.getPc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPc().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("库存-试剂耗材", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchPrdCodeForLack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String begindate = this.getPc().getSearch().getBegindate();
            String enddate = this.getPc().getSearch().getEnddate();
            String prdname = ToolUtils.Decode(this.getPc().getPrdname());
            String storeid = this.getPc().getStoreid();

            if (!ToolUtils.StringIsEmpty(enddate))
                search += ToolUtils.GetAndSearch(search) + " a.buydate <= " + ToolUtils.GetEndDate(enddate) + " ";

            if (!ToolUtils.StringIsEmpty(begindate))
                search += ToolUtils.GetAndSearch(search) + " a.buydate >= " + ToolUtils.GetBeginDate(begindate) + " ";

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " b.prdname  like '%" + prdname + "%' ";

            if (!ToolUtils.StringIsEmpty(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid  = '" + storeid + "' ";

            this.SetSearch(this.getPc().getSearch(), this.getPc().getItem(), ou, search);

            List<PrdCode> lists = PrdDao.SearchPrdCodeForLack(this.getPc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPc().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("库存-试剂耗材", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            PrdService.SavePrdCode(this.getPc(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion PrdCode Methods

    // region StkCheckDetail Methods

    private StkCheckDetail chdetail;

    public StkCheckDetail getChdetail() {
        if (chdetail == null)
            chdetail = new StkCheckDetail();

        return chdetail;
    }

    public void setChdetail(StkCheckDetail chdetail) {
        this.chdetail = chdetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStkCheckDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkCheckDetail> lists = PrdDao.GetListStkCheckDetail(this.getChdetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0327-库存-仓库盘点明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion StkCheckDetail Methods

    // region StkCheck Methods

    private StkCheck stkcheck;

    public StkCheck getStkcheck() {
        if (stkcheck == null)
            stkcheck = new StkCheck();

        return stkcheck;
    }

    public void setStkcheck(StkCheck stkcheck) {
        this.stkcheck = stkcheck;
    }

    // @AuthMethod(Menus="1171", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetStkCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StkCheck rtn = new StkCheck();
        if (ou != null) {
            rtn = PrdDao.GetStkCheck(this.getStkcheck());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1171", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchStkCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String flowaction = ToolUtils.Decode(this.getStkcheck().getFlowaction());
            String flowstatus = ToolUtils.Decode(this.getStkcheck().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getStkcheck().getTranid());
            String storeid = ToolUtils.Decode(this.getStkcheck().getStoreid());

            if (!ToolUtils.StringIsEmpty(flowaction))
                search += ToolUtils.GetAndSearch(search) + " c.flowaction like '%" + flowaction + "%' ";

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " d.flowstatus like '%" + flowstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.CheckComboValue(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid like '%" + storeid + "%' ";

            this.SetSearch(this.getStkcheck().getSearch(), this.getStkcheck().getItem(), ou, search);

            List<StkCheck> lists = PrdDao.SearchStkCheck(this.getStkcheck());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStkcheck().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0326-库存-仓库盘点单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1171", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStkCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1171");
            List<StkCheckDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), StkCheckDetail.class);
            this.getStkout().setRecodate(new Date());
            PrdService.SaveStkCheck(this.getStkcheck(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核
    public String SaveStkCheckAll() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1173");
            PrdService.SaveStkCheckAll(this.getStkcheck(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion StkCheck Methods

    // region StkOutDetail Methods

    private StkOutDetail outdetail;

    public StkOutDetail getOutdetail() {
        if (outdetail == null)
            outdetail = new StkOutDetail();

        return outdetail;
    }

    public void setOutdetail(StkOutDetail outdetail) {
        this.outdetail = outdetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStkOutDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkOutDetail> lists = PrdDao.GetListStkOutDetail(this.getOutdetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0325-库存-仓库出库明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStkOutDetailByPrdID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkOutDetail> lists = PrdDao.GetListStkOutDetailByPrdID(this.getOutdetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0325-库存-仓库出库明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion StkOutDetail Methods

    // region StkOut Methods

    private StkOut stkout;

    public StkOut getStkout() {
        if (stkout == null)
            stkout = new StkOut();

        return stkout;
    }

    public void setStkout(StkOut stkout) {
        this.stkout = stkout;
    }

    // @AuthMethod(Menus="1161", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetStkOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StkOut rtn = new StkOut();
        if (ou != null) {
            rtn = PrdDao.GetStkOut(this.getStkout());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1161", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String GetListStkOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkOut> lists = PrdDao.GetListStkOut();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0324-库存-仓库出库（领用）单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // @AuthMethod(Menus="1161", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchStkOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String flowaction = ToolUtils.Decode(this.getStkout().getFlowaction());
            String flowstatus = ToolUtils.Decode(this.getStkout().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getStkout().getTranid());
            String storeid = ToolUtils.Decode(this.getStkout().getStoreid());
            String outfact = ToolUtils.Decode(this.getStkout().getOutfact());
            String deptid = ToolUtils.Decode(this.getStkout().getDeptid());

            if (!ToolUtils.StringIsEmpty(flowaction))
                search += ToolUtils.GetAndSearch(search) + " c.flowaction like '%" + flowaction + "%' ";

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " d.flowstatus like '%" + flowstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(outfact))
                search += ToolUtils.GetAndSearch(search) + " a.outfact like '%" + outfact + "%' ";

            if (!ToolUtils.CheckComboValue(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid like '%" + storeid + "%' ";

            if (!ToolUtils.CheckComboValue(deptid))
                search += ToolUtils.GetAndSearch(search) + " f.deptid = '" + deptid + "' ";

            this.SetSearch(this.getStkout().getSearch(), this.getStkout().getItem(), ou, search);

            List<StkOut> lists = PrdDao.SearchStkOut(this.getStkout());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStkout().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0324-库存-仓库出库（领用）单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1161", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStkOut() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1161");
            List<StkOutDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), StkOutDetail.class);
            this.getStkout().setRecodate(new Date());
            PrdService.SaveStkOut(this.getStkout(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核
    public String SaveStkOutCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1163");
            PrdService.SaveStkOutCheck(this.getStkout(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SearchStkOutCount() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String prdname = ToolUtils.Decode(this.getStkout().getPrdname());
            String deptid = ToolUtils.Decode(this.getStkout().getDeptid());
            String begindate = ToolUtils.Decode(this.getStkout().getBegindate());
            String enddate = ToolUtils.Decode(this.getStkout().getEnddate());

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " h.prdname like '%" + prdname + "%' ";

            if (!ToolUtils.CheckComboValue(deptid))
                search += ToolUtils.GetAndSearch(search) + " f.deptid = '" + deptid + "' ";

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.outdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.outdate  <= '" + enddate + "' ";
            }

            this.SetSearch(this.getStkout().getSearch(), this.getStkout().getItem(), ou, search);

            List<StkOut> lists = PrdDao.SearchStkOutCount(this.getStkout());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStkout().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0324-库存-仓库出库（领用）单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion StkOut Methods

    // region StkInDetail Methods

    private StkInDetail indetail;

    public StkInDetail getIndetail() {
        if (indetail == null)
            indetail = new StkInDetail();

        return indetail;
    }

    public void setIndetail(StkInDetail indetail) {
        this.indetail = indetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStkInDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkInDetail> lists = PrdDao.GetListStkInDetail(this.getIndetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0323-库存-仓库入库明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListStkInDetailByPrdID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<StkInDetail> lists = PrdDao.GetListStkInDetailByPrdID(this.getIndetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0323-库存-仓库入库明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion StkInDetail Methods

    // region StkIn Methods

    private StkIn stkin;

    public StkIn getStkin() {
        if (stkin == null)
            stkin = new StkIn();

        return stkin;
    }

    public void setStkin(StkIn stkin) {
        this.stkin = stkin;
    }

    @AuthMethod(Menus = "1153", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetStkIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StkIn rtn = new StkIn();
        if (ou != null) {
            rtn = PrdDao.GetStkIn(this.getStkin());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "1153", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchStkIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String flowaction = ToolUtils.Decode(this.getStkin().getFlowaction());
            String flowstatus = ToolUtils.Decode(this.getStkin().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getStkin().getTranid());
            String infact = ToolUtils.Decode(this.getStkin().getInfact());
            String storeid = ToolUtils.Decode(this.getStkin().getStoreid());

            if (!ToolUtils.StringIsEmpty(flowaction))
                search += ToolUtils.GetAndSearch(search) + " c.flowaction like '%" + flowaction + "%' ";

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " d.flowstatus like '%" + flowstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(infact))
                search += ToolUtils.GetAndSearch(search) + " a.infact like '%" + infact + "%' ";

            if (!ToolUtils.CheckComboValue(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid like '%" + storeid + "%' ";

            this.SetSearch(this.getStkin().getSearch(), this.getStkin().getItem(), ou, search);

            List<StkIn> lists = PrdDao.SearchStkIn(this.getStkin());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStkin().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0322-库存-仓库入库单", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1153", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStkIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1153");
            List<StkInDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), StkInDetail.class);
            this.getStkin().setRecodate(new Date());
            PrdService.SaveStkIn(this.getStkin(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 审核
    public String SaveStkInCheck() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1153");
            PrdService.SaveStkInCheck(this.getStkin(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion StkIn Methods

    // region StkStore Methods

    private StkStore stkstore;

    public StkStore getStkstore() {
        if (stkstore == null)
            stkstore = new StkStore();

        return stkstore;
    }

    public void setStkstore(StkStore stkstore) {
        this.stkstore = stkstore;
    }

    // @AuthMethod(Menus="1103", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetStkStore() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        StkStore rtn = new StkStore();
        if (ou != null) {
            rtn = PrdDao.GetStkStore(this.getStkstore());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1103", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchStkStore() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String storeid = ToolUtils.Decode(this.getStkstore().getStoreid());
            String storename = ToolUtils.Decode(this.getStkstore().getStorename());

            if (!ToolUtils.StringIsEmpty(storeid))
                search += ToolUtils.GetAndSearch(search) + " a.storeid like '%" + storeid + "%' ";

            if (!ToolUtils.StringIsEmpty(storename))
                search += ToolUtils.GetAndSearch(search) + " a.storename like '%" + storename + "%' ";

            this.SetSearch(this.getStkstore().getSearch(), this.getStkstore().getItem(), ou, search);

            List<StkStore> lists = PrdDao.SearchStkStore(this.getStkstore());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getStkstore().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0321-库存-仓库", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1103", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveStkStore() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1103");
            PrdService.SaveStkStore(this.getStkstore(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion StkStore Methods

    // region PrdWasteDetail Methods

    private PrdWasteDetail wastedetail;

    public PrdWasteDetail getWastedetail() {
        if (wastedetail == null)
            wastedetail = new PrdWasteDetail();

        return wastedetail;
    }

    public void setWastedetail(PrdWasteDetail wastedetail) {
        this.wastedetail = wastedetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdWasteDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdWasteDetail> lists = PrdDao.GetListPrdWasteDetail(this.getWastedetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0305-耗材-废弃物处理申请明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdWasteDetail Methods

    // region PrdWaste Methods

    private PrdWaste prdwaste;

    public PrdWaste getPrdwaste() {
        if (prdwaste == null)
            prdwaste = new PrdWaste();

        return prdwaste;
    }

    public void setPrdwaste(PrdWaste prdwaste) {
        this.prdwaste = prdwaste;
    }

    // @AuthMethod(Menus="1131", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetPrdWaste() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdWaste rtn = new PrdWaste();
        if (ou != null) {
            rtn = PrdDao.GetPrdWaste(this.getPrdwaste());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1115", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchPrdWaste() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String followaction = ToolUtils.Decode(this.getPrdwaste().getFlowaction());
            String followstatus = ToolUtils.Decode(this.getPrdwaste().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getPrdwaste().getTranid());
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.comfirmuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(followaction))
                search += ToolUtils.GetAndSearch(search) + " b.flowaction like '%" + followaction + "%' ";

            if (!ToolUtils.StringIsEmpty(followstatus))
                search += ToolUtils.GetAndSearch(search) + " c.flowstatus like '%" + followstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getPrdwaste().getSearch(), this.getPrdwaste().getItem(), ou, search);

            List<PrdWaste> lists = PrdDao.SearchPrdWaste(this.getPrdwaste());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPrdwaste().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0304-耗材-废弃物处理申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "0641", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdWaste() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("641");
            List<PrdWasteDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), PrdWasteDetail.class);
            this.getPrdwaste().setComfirmuser(ou.getUser().getUserid());
            this.getPrdwaste().setComfirmusername(ou.getUser().getUsername());
            PrdService.SavePrdWaste(this.getPrdwaste(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 办公室审核
    public String SavePrdWasteOffice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1133");
            PrdService.SavePrdWasteOffice(this.getPrdwaste(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 分管主任审核
    public String SavePrdWasteCharge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1135");
            PrdService.SavePrdWasteCharge(this.getPrdwaste(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 主任审核
    public String SavePrdWasteDirector() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1137");
            PrdService.SavePrdWasteDirector(this.getPrdwaste(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 废弃物申请html
    public String HtmlWasteApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            PrdWasteDetail item = new PrdWasteDetail();
            List<PrdWasteDetail> items = new ArrayList<PrdWasteDetail>();
            item.setTranid(tranid);
            items = PrdDao.GetListPrdWasteDetail(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlWasteApply(items));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion PrdWaste Methods

    // region PrdVerify Methods

    private PrdVerify prdverify;

    public PrdVerify getPrdverify() {
        if (prdverify == null)
            prdverify = new PrdVerify();

        return prdverify;
    }

    public void setPrdverify(PrdVerify prdverify) {
        this.prdverify = prdverify;
    }

    // @AuthMethod(Menus="1121", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetPrdVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdVerify rtn = new PrdVerify();
        if (ou != null) {
            rtn = PrdDao.GetPrdVerify(this.getPrdverify());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1123", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchPrdVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String followaction = ToolUtils.Decode(this.getPrdverify().getFlowaction());
            String followstatus = ToolUtils.Decode(this.getPrdverify().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getPrdverify().getTranid());
            String prdname = ToolUtils.Decode(this.getPrdverify().getPrdname());
            String verifyid = ToolUtils.Decode(this.getPrdverify().getVerifyid());
            String tradename = ToolUtils.Decode(this.getPrdverify().getTradename());
            String factoryname = ToolUtils.Decode(this.getPrdverify().getFactoryname());
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.StringIsEmpty(factoryname))
                search += ToolUtils.GetAndSearch(search) + " a.factoryname like '%" + factoryname + "%' ";

            if (!ToolUtils.StringIsEmpty(followaction))
                search += ToolUtils.GetAndSearch(search) + " b.flowaction like '%" + followaction + "%' ";

            if (!ToolUtils.StringIsEmpty(followstatus))
                search += ToolUtils.GetAndSearch(search) + " c.flowstatus like '%" + followstatus + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " d.prdname like '%" + prdname + "%' ";

            if (!ToolUtils.StringIsEmpty(verifyid))
                search += ToolUtils.GetAndSearch(search) + " a.verifyid like '%" + verifyid + "%' ";

            if (!ToolUtils.StringIsEmpty(tradename))
                search += ToolUtils.GetAndSearch(search) + " a.tradename like '%" + tradename + "%' ";

            this.SetSearch(this.getPrdverify().getSearch(), this.getPrdverify().getItem(), ou, search);

            List<PrdVerify> lists = PrdDao.SearchPrdVerify(this.getPrdverify());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPrdverify().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0303-耗材-试剂耗材验证记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SearchPrdVerifyForIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String prdname = ToolUtils.Decode(this.getPrdverify().getPrdname()).trim();
            String prdid = ToolUtils.Decode(this.getPrdverify().getPrdid()).trim();
            if (!ToolUtils.StringIsEmpty(prdid))
                search += ToolUtils.GetAndSearch(search) + " a.prdid like '%" + prdid + "%' ";
            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " a.prdname like '%" + prdname + "%' ";

            this.SetSearch(this.getPrdverify().getSearch(), this.getPrdverify().getItem(), ou, search);

            List<PrdVerify> lists = PrdDao.SearchPrdVerifyForIn(this.getPrdverify());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPrdverify().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0303-耗材-试剂耗材验证记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1121", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1121");
            this.getPrdverify().setTranuser(ou.getUser().getUserid());
            this.getPrdverify().setTranusername(ou.getUser().getUsername());
            this.getPrdverify().setTrandate(new Date());
            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);
            PrdService.SavePrdVerify(this.getPrdverify(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String SavePrdVerifyOffice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1123");
            PrdService.SavePrdVerifyOffice(this.getPrdverify(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 验收审核
    public String SavePrdVerifyCharge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1125");
            PrdService.SavePrdVerifyCharge(this.getPrdverify(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 主任审核
    public String SavePrdVerifyDirector() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1127");
            PrdService.SavePrdVerifyDirector(this.getPrdverify(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 耗材 验证html
    public String HtmlPrdVerify() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            PrdVerify item = new PrdVerify();
            item.setVerifyid(tranid);
            item = PrdDao.GetPrdVerify(item);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlPrdVerify(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion PrdVerify Methods

    // region PrdApplyDetail Methods

    private PrdApplyDetail prddetail;

    public PrdApplyDetail getPrddetail() {
        if (prddetail == null)
            prddetail = new PrdApplyDetail();

        return prddetail;
    }

    public void setPrddetail(PrdApplyDetail prddetail) {
        this.prddetail = prddetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListPrdApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdApplyDetail> lists = PrdDao.GetListPrdApplyDetail(this.getPrddetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0302-耗材-试剂耗材采购申请明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // 申请验证的耗材查询
    public String GetListPrdApplyDetailForVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<PrdApplyDetail> lists = PrdDao.GetListPrdApplyDetailForVerify(this.getPrddetail());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SearchPrdApplyDetailForVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String prdname = ToolUtils.Decode(this.getPrddetail().getPrdname());

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " a.prdname like '%" + prdname + "%' ";

            this.SetSearch(this.getPrddetail().getSearch(), this.getPrddetail().getItem(), ou, search);

            List<PrdApplyDetail> lists = PrdDao.SearchPrdApplyDetailForVerify(this.getPrddetail());

            ToolUtils.OutString(this.OutLists(lists, this.getPrddetail().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdApplyDetail Methods

    // region BasPrd Methods

    private BasPrd basprd;

    public BasPrd getBasprd() {
        if (basprd == null)
            basprd = new BasPrd();

        return basprd;
    }

    public void setBasprd(BasPrd basprd) {
        this.basprd = basprd;
    }

    // @AuthMethod(Menus="1101", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetBasPrd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasPrd rtn = new BasPrd();
        if (ou != null) {
            rtn = PrdDao.GetBasPrd(this.getBasprd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1101", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchBasPrd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String prdid = ToolUtils.Decode(this.getBasprd().getPrdid());
            String prdname = ToolUtils.Decode(this.getBasprd().getPrdname());
            String prdtype = this.getBasprd().getPrdtype();

            if (!ToolUtils.StringIsEmpty(prdtype))
                search += ToolUtils.GetAndSearch(search) + " a.prdtype = '" + prdtype + "' ";
            if (!ToolUtils.StringIsEmpty(prdid))
                search += ToolUtils.GetAndSearch(search) + " a.prdid like '%" + prdid + "%' ";

            if (!ToolUtils.StringIsEmpty(prdname))
                search += ToolUtils.GetAndSearch(search) + " a.prdname like '%" + prdname + "%' ";

            this.SetSearch(this.getBasprd().getSearch(), this.getBasprd().getItem(), ou, search);
            List<BasPrd> lists = PrdDao.SearchBasPrd(this.getBasprd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBasprd().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0111-基础-耗材基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1101", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasPrd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1101");
            PrdService.SaveBasPrd(this.getBasprd(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasPrd Methods

    // region PrdApply Methods

    private PrdApply prdapply;

    public PrdApply getPrdapply() {
        if (prdapply == null)
            prdapply = new PrdApply();

        return prdapply;
    }

    public void setPrdapply(PrdApply prdapply) {
        this.prdapply = prdapply;
    }

    // @AuthMethod(Menus="1111", Auth=MenuAuth.Browse, OutType=ActionOutType.Bean)
    public String GetPrdApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        PrdApply rtn = new PrdApply();
        if (ou != null) {
            rtn = PrdDao.GetPrdApply(this.getPrdapply());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // @AuthMethod(Menus="1111", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchPrdApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String followaction = ToolUtils.Decode(this.getPrdapply().getFlowaction());
            String followstatus = ToolUtils.Decode(this.getPrdapply().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getPrdapply().getTranid());
            String prdtype = ToolUtils.Decode(this.getPrdapply().getPrdtype());
            String projectid = ToolUtils.Decode(this.getPrdapply().getProjectid());

            // search += ToolUtils.GetAndSearch(search) + " a.confirmuser = '" +
            // ou.getUser().getUserid() + "' ";

            if (!ToolUtils.StringIsEmpty(followaction))
                search += ToolUtils.GetAndSearch(search) + " c.flowaction like '%" + followaction + "%' ";

            if (!ToolUtils.StringIsEmpty(followstatus))
                search += ToolUtils.GetAndSearch(search) + " d.flowstatus like '%" + followstatus + "%' ";

            if (!ToolUtils.CheckComboValue(prdtype))
                search += ToolUtils.GetAndSearch(search) + " a.prdtype like '%" + prdtype + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(projectid))
                search += ToolUtils.GetAndSearch(search) + " a.projectid like '%" + projectid + "%' ";

            this.SetSearch(this.getPrdapply().getSearch(), this.getPrdapply().getItem(), ou, search);

            List<PrdApply> lists = PrdDao.SearchPrdApply(this.getPrdapply());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPrdapply().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("耗材-试剂耗材采购申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String SearchPrdApplyByPrdSource() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String flowaction = ToolUtils.Decode(this.getPrdapply().getFlowaction());
            String flowstatus = ToolUtils.Decode(this.getPrdapply().getFlowstatus());

            if (!ToolUtils.StringIsEmpty(flowaction) && !ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + "(( c.flowaction like '%" + flowaction
                        + "%' and d.flowstatus like '%" + flowstatus + "%')";

            search += " or (d.flowstatus like '%05%' and a.prdsource like '%04%')) ";

            this.SetSearch(this.getPrdapply().getSearch(), this.getPrdapply().getItem(), ou, search);

            List<PrdApply> lists = PrdDao.SearchPrdApply(this.getPrdapply());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPrdapply().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("耗材-试剂耗材采购申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1111", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SavePrdApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1111");
            List<PrdApplyDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), PrdApplyDetail.class);
            // this.getPrdapply().setConfirmdate(new Date());
            // this.getPrdapply().setConfirmuser(ou.getUser().getUserid());
            // this.getPrdapply().setConfirmusername(ou.getUser().getUsername());
            PrdService.SavePrdApply(this.getPrdapply(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 耗材 申请html
    public String HtmlPrdApply() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            PrdApply item = new PrdApply();
            item.setTranid(tranid);
            item = PrdDao.GetPrdApply(item);
            PrdApplyDetail detail = new PrdApplyDetail();
            detail.setTranid(tranid);
            List<PrdApplyDetail> details = PrdDao.GetListPrdApplyDetail(detail);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlPrdApplyDetail(details));
                sb.append(FlowFmtService.GetHtmlPrdApply(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // 办公室审核
    public String SavePrdApplyOffice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1113");
            PrdService.SavePrdApplyOffice(this.getPrdapply(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 分管主任审核
    public String SavePrdApplyCharge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1115");
            PrdService.SavePrdApplyCharge(this.getPrdapply(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 主任审核
    public String SavePrdApplyDirector() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1117");
            PrdService.SavePrdApplyDirector(this.getPrdapply(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 耗材验证prdapply 查询
    // @AuthMethod(Menus="1111", Auth=MenuAuth.BrowseExport,
    // OutType=ActionOutType.Array)
    public String SearchPrdApplyForVerify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getPrdverify().getTranid()).trim();
            // String prdtype = ToolUtils.Decode(this.getPrdapply().getPrdtype());
            //
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";
            //
            // if (!ToolUtils.StringIsEmpty(projectid))
            // search += ToolUtils.GetAndSearch(search) + " a.projectid like '%" +
            // projectid + "%' ";
            this.SetSearch(this.getPrdapply().getSearch(), this.getPrdapply().getItem(), ou, search);

            List<PrdApply> lists = PrdDao.SearchPrdApplyForVerify(this.getPrdapply());

            ToolUtils.OutString(this.OutLists(lists, this.getPrdapply().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion PrdApply Methods

}
