package com.alms.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.LabDao;
import com.alms.entity.bas.BasMainParameter;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.lab.BusAccFile;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusRecordSamples;
import com.alms.entity.lab.BusReportFile;
import com.alms.entity.lab.BusTaskFile;
import com.alms.entity.lab.BusTaskLab;
import com.alms.entity.lab.BusTaskResult;
import com.alms.entity.lab.SignerPassword;
import com.alms.entity.lab.BusAccSample;
import com.alms.entity.lab.BusConsign;
import com.alms.entity.lab.BusConsignParam;
import com.alms.entity.lab.BusConsignSample;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusGetNoticeDetail;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.lab.BusSelect;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskData;
import com.alms.entity.lab.BusTaskDev;
import com.alms.entity.lab.BusTaskJudge;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.BusTaskTest;
import com.alms.entity.lab.BusTaskTester;
import com.alms.service.FlowFmtService;
import com.alms.service.LabService;
import com.gpersist.action.BaseAction;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysRole;
import com.gpersist.entity.user.SysUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;
import com.gpersist.utils.ToolUtils2;

public class LabAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region BusGetNotice Methods

    private BusGetNotice bgn;

    public BusGetNotice getBgn() {
        if (bgn == null)
            bgn = new BusGetNotice();

        return bgn;
    }

    public void setBgn(BusGetNotice bgn) {
        this.bgn = bgn;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String testedname = ToolUtils.Decode(this.getBgn().getTestedname()).trim();
            String tranid = ToolUtils.Decode(this.getBgn().getTranid());
            String tranuser = ToolUtils.Decode(this.getBgn().getTranuser());

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(tranuser))
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + tranuser + "' ";

            this.SetSearch(this.getBgn().getSearch(), this.getBgn().getItem(), ou, search);

            List<BusGetNotice> lists = LabDao.SearchBusGetNotice(this.getBgn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBgn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-现场抽样通知单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetNoticeForRecv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String testedname = ToolUtils.Decode(this.getBgn().getTestedname()).trim();
            String flowstatus = ToolUtils.Decode(this.getBgn().getFlowstatus());
            String tranid = ToolUtils.Decode(this.getBgn().getTranid());
            String getuser = ToolUtils.Decode(this.getBgn().getGetuser());

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(getuser))
                search += ToolUtils.GetAndSearch(search) + " a.getuser like '%" + getuser + "%' ";

            this.SetSearch(this.getBgn().getSearch(), this.getBgn().getItem(), ou, search);

            List<BusGetNotice> lists = LabDao.SearchBusGetNotice(this.getBgn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBgn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-现场抽样通知单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusGetNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusGetNotice rtn = new BusGetNotice();
        if (ou != null) {
            rtn = LabDao.GetBusGetNotice(this.getBgn());
            if (rtn == null) {
                rtn = new BusGetNotice();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetNotice() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0103");
            List<BusGetNoticeDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), BusGetNoticeDetail.class);
            LabService.SaveBusGetNotice(this.getBgn(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetNoticeRecv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0105");
            LabService.SaveBusGetNoticeRecv(this.getBgn(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetNoticeForGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getBgn().getTranid());
            String gettype = ToolUtils.Decode(this.getBgn().getGettype());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(gettype))
                search += ToolUtils.GetAndSearch(search) + " a.gettype = '" + gettype + "' ";

            search += ToolUtils.GetAndSearch(search) + " a.getuser like '%" + ou.getUser().getUserid() + "%' ";
            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '03' ";
            search += ToolUtils.GetAndSearch(search) + " b.tranid is null ";

            this.SetSearch(this.getBgn().getSearch(), this.getBgn().getItem(), ou, search);

            List<BusGetNotice> lists = LabDao.SearchBusGetNoticeForGet(this.getBgn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBgn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-现场抽样通知单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusGetNotice Methods

    // region BusGetNoticeDetail Methods

    private BusGetNoticeDetail bgnd;

    public BusGetNoticeDetail getBgnd() {
        if (bgnd == null)
            bgnd = new BusGetNoticeDetail();

        return bgnd;
    }

    public void setBgnd(BusGetNoticeDetail bgnd) {
        this.bgnd = bgnd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusGetNoticeDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusGetNoticeDetail> lists = LabDao.GetBusGetNoticeDetail(this.getBgnd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-现场抽样通知单明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusGetNoticeDetail Methods

    // region BusTestedUnit Methods

    private BusTestedUnit btu;

    public BusTestedUnit getBtu() {
        if (btu == null)
            btu = new BusTestedUnit();

        return btu;
    }

    public void setBtu(BusTestedUnit btu) {
        this.btu = btu;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String testedid = ToolUtils.Decode(this.getBtu().getTestedid());
            String testedname = ToolUtils.Decode(this.getBtu().getTestedname());

            if (!ToolUtils.StringIsEmpty(testedid))
                search += ToolUtils.GetAndSearch(search) + " a.testedid like '%" + testedid + "%' ";

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            this.SetSearch(this.getBtu().getSearch(), this.getBtu().getItem(), ou, search);

            List<BusTestedUnit> lists = LabDao.SearchBusTestedUnit(this.getBtu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-受检单位", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTestedUnit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTestedUnit rtn = new BusTestedUnit();
        if (ou != null) {
            rtn = LabDao.GetBusTestedUnit(this.getBtu());
            if (rtn == null) {
                rtn = new BusTestedUnit();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    // endregion BusTestedUnit Methods

    // region BusGet Methods

    private BusGet bg;

    public BusGet getBg() {
        if (bg == null)
            bg = new BusGet();

        return bg;
    }

    public void setBg(BusGet bg) {
        this.bg = bg;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusGet rtn = new BusGet();
        if (ou != null) {
            rtn = LabDao.GetBusGet(this.getBg());
            if (rtn == null) {
                rtn = new BusGet();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusGetBySampleCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusGet rtn = new BusGet();
        if (ou != null) {
            rtn = LabDao.GetBusGetBySampleCode(this.getBg().getSamplecode());
            if (rtn == null) {
                rtn = new BusGet();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }
    
    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusSamplingGetByNoticeid() throws Exception {
      OnlineUser ou = ToolUtils.GetOnlineUser();
      
      BusGet rtn = new BusGet();
      if (ou != null) {
        rtn = LabDao.GetBusSamplingGetByNoticeid(this.getBg().getNoticeid());
        if (rtn == null) {
          rtn = new BusGet();
        }
      }
      ToolUtils.OutString(this.OutBean(rtn, true));
      
      return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String flowstatus = this.getBg().getFlowstatus();
            String gettype = ToolUtils.Decode(this.getBg().getGettype());
            String testedname = ToolUtils.Decode(this.getBg().getTestedname());
            String tranid = ToolUtils.Decode(this.getBg().getTranid());
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" + ou.getUser().getUserid() + "' ";
            }
            if (!ToolUtils.CheckComboValue(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid = '" + tranid + "' ";

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(gettype))
                search += ToolUtils.GetAndSearch(search) + " a.gettype = '" + gettype + "' ";

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            this.SetSearch(this.getBg().getSearch(), this.getBg().getItem(), ou, search);

            List<BusGet> lists = LabDao.SearchBusGet(this.getBg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-抽样单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetForView() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String testedname = ToolUtils.Decode(this.getBg().getTestedname());
            String entertele = ToolUtils.Decode(this.getBg().getEntertele());
            String productcate = ToolUtils.Decode(this.getBg().getProductcate());
            String samplecode = ToolUtils.Decode(this.getBg().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBg().getSamplename());

            if (!ToolUtils.CheckComboValue(productcate))
                search += ToolUtils.GetAndSearch(search) + " a.productcate = '" + productcate + "' ";

            if (!ToolUtils.StringIsEmpty(entertele))
                search += ToolUtils.GetAndSearch(search) + " a.entertele like '%" + entertele + "%' ";

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            if (!ToolUtils.StringIsEmpty(samplecode))
                search += ToolUtils.GetAndSearch(search) + " k.samplecode like '%" + samplecode + "%' ";

            if (!ToolUtils.StringIsEmpty(samplename))
                search += ToolUtils.GetAndSearch(search) + " k.samplename like '%" + samplename + "%' ";

            this.SetSearch(this.getBg().getSearch(), this.getBg().getItem(), ou, search);

            List<BusGet> lists = LabDao.SearchBusGetForView(this.getBg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-客户信息", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetForAcc() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02' ";
            search += ToolUtils.GetAndSearch(search) + " i.tranid is null ";
            // search += ToolUtils.GetAndSearch(search) + " a.tranuser = '" +
            // ou.getUser().getUserid() + "' ";

            String testedname = ToolUtils.Decode(this.getBg().getTestedname());

            if (!ToolUtils.StringIsEmpty(testedname))
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";

            this.SetSearch(this.getBg().getSearch(), this.getBg().getItem(), ou, search);

            List<BusGet> lists = LabDao.SearchBusGetForAcc(this.getBg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-抽样单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetQuan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0113");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetCon() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0115");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetPest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0117");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0119");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveManBusEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0119");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveManBusEnv(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveManBusGreen() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0119");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);
            LabService.SaveManBusGreen(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetEnvConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0119");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetPoll() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0121");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetCountry() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0123");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetGeo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0125");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetSup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0127");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetProv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0129");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);

            LabService.SaveBusGet(this.getBg(), details, this.getRtv(), ou, log);

            ToolUtils.OutString(this.getRtv().toString());

        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusGetAudit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.SaveBusGetAudit(this.getBg(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusGetForConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = this.getBg().getTranid();

            if (!ToolUtils.CheckComboValue(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '30' ";
            search += ToolUtils.GetAndSearch(search) + " h.getid is null ";

            this.SetSearch(this.getBg().getSearch(), this.getBg().getItem(), ou, search);

            List<BusGet> lists = LabDao.SearchBusGetForConsign(this.getBg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBg().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-抽样单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveConsignGet() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0133");

            List<BusGetDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusGetDetail.class);
            LabService.SaveConsignGet(this.getBg(), details, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusGet Methods

    // region BusGetDetail Methods

    private BusGetDetail bgd;

    public BusGetDetail getBgd() {
        if (bgd == null)
            bgd = new BusGetDetail();

        return bgd;
    }

    public void setBgd(BusGetDetail bgd) {
        this.bgd = bgd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusGetDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusGetDetail> lists = LabDao.GetListBusGetDetail(this.getBgd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-抽样单明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusGetDetail() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusGetDetail rtn = new BusGetDetail();
        if (ou != null) {
            rtn = LabDao.GetBusGetDetail(this.getBgd());
            if (rtn == null) {
                rtn = new BusGetDetail();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;

    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusGetDetailBySampleCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusGetDetail rtn = new BusGetDetail();
        if (ou != null) {
            rtn = LabDao.GetBusGetDetailBySampleCode(this.getBgd());
            if (rtn == null) {
                rtn = new BusGetDetail();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;

    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusGetDetailByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusGetDetail> lists = LabDao.GetListBusGetDetailByTask(this.getBgd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusGetDetail Methods

    // region BusConsign Methods

    private BusConsign bc;

    public BusConsign getBc() {
        if (bc == null)
            bc = new BusConsign();

        return bc;
    }

    public void setBc(BusConsign bc) {
        this.bc = bc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusConsign rtn = new BusConsign();
        if (ou != null) {
            rtn = LabDao.GetBusConsign(this.getBc());
            if (rtn == null) {
                rtn = new BusConsign();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusConsign() throws Exception {
        // OnlineUser ou = ToolUtils.GetOnlineUser();
        //
        // if (ou != null) {
        // List<BusConsign> lists = LabDao.GetListBusConsign(this.getBc());
        //
        // if (!hasexport) {
        // ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        // }
        // else {
        // ExportSetting es = this.GetExportSetting("检测-委托书", false);
        // this.OutExport(lists, es);
        // return Consts.DEFAULT_EXCEL_RETURN;
        // }
        // }
        // else {
        // ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        // }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            this.SetSearch(this.getBc().getSearch(), this.getBc().getItem(), ou, search);

            List<BusConsign> lists = LabDao.SearchBusConsign(this.getBc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBc().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BusConsignSample> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), BusConsignSample.class);

            LabService.SaveBusConsign(this.getBc(), details, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusConsignForTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02' ";
            search += ToolUtils.GetAndSearch(search) + " g.taskid is null ";

            this.SetSearch(this.getBc().getSearch(), this.getBc().getItem(), ou, search);

            List<BusConsign> lists = LabDao.SearchBusConsignForTask(this.getBc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBc().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusConsign Methods

    // region BusConsignSample Methods

    private BusConsignSample bcs;

    public BusConsignSample getBcs() {
        if (bcs == null)
            bcs = new BusConsignSample();

        return bcs;
    }

    public void setBcs(BusConsignSample bcs) {
        this.bcs = bcs;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusConsignSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusConsignSample rtn = new BusConsignSample();
        if (ou != null) {
            rtn = LabDao.GetBusConsignSample(this.getBcs());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusConsignSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusConsignSample> lists = LabDao.GetListBusConsignSample(this.getBcs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书样品明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusConsignSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBcs().getSearch().setStart(start + 1);
            this.getBcs().getSearch().setEnd(this.GetEndCnt());

            List<BusConsignSample> lists = LabDao.SearchBusConsignSample(this.getBcs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBcs().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书样品明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusConsignSample() throws Exception {
        // OnlineUser ou = ToolUtils.GetOnlineUser();
        //
        // if (ou != null) {
        // TranLog log = ToolUtils.InitTranLog("");
        // LabService.SaveBusConsignSample(this.getBcs(), this.getRtv(), log);
        // ToolUtils.OutString(this.getRtv().toString());
        // }
        // else
        // ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusConsignSample Methods

    // region BusConsignParam Methods

    private BusConsignParam bcp;

    public BusConsignParam getBcp() {
        if (bcp == null)
            bcp = new BusConsignParam();

        return bcp;
    }

    public void setBcp(BusConsignParam bcp) {
        this.bcp = bcp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusConsignParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusConsignParam rtn = new BusConsignParam();
        if (ou != null) {
            rtn = LabDao.GetBusConsignParam(this.getBcp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusConsignParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusConsignParam> lists = LabDao.GetListBusConsignParam(this.getBcp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusConsignParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBcp().getSearch().setStart(start + 1);
            this.getBcp().getSearch().setEnd(this.GetEndCnt());

            List<BusConsignParam> lists = LabDao.SearchBusConsignParam(this.getBcp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBcp().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusConsignParam() throws Exception {
        // OnlineUser ou = ToolUtils.GetOnlineUser();
        //
        // if (ou != null) {
        // TranLog log = ToolUtils.InitTranLog("");
        // LabService.SaveBusConsignParam(this.getBcp(), this.getRtv(), log);
        // ToolUtils.OutString(this.getRtv().toString());
        // }
        // else
        // ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusConsignParam Methods

    // region BusTask Methods

    private BusTask bt;

    public BusTask getBt() {
        if (bt == null)
            bt = new BusTask();

        return bt;
    }

    public void setBt(BusTask bt) {
        this.bt = bt;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTask rtn = new BusTask();
        if (ou != null) {
            rtn = LabDao.GetBusTask(this.getBt());
        }
        if (rtn.getSamplecode().contains("多样品")) {
            BusRecordSamples brs = LabService.SearchBusRecordSamples(rtn.getTaskid());
            if (brs != null) {
                rtn.setRecordstatus(brs.getRecordstatus());
            }
        }

        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SaveApproveForSamples() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            String auditusers = ServletActionContext.getRequest().getParameter("auditusers");

            LabService.SaveApproveForSamples(this.getBt(), this.getRtv(), signerpassword, auditusers, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRecordSamples() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        BusRecordSamples brs = new BusRecordSamples();
        if (ou != null) {
            brs = LabService.SearchBusRecordSamples(this.getBt().getTaskid());
        }
        if (!hasexport) {
            ToolUtils.OutString(this.OutBean(brs, true));
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskForSampleCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTask rtn = new BusTask();
        if (ou != null) {
            rtn = LabDao.GetBusTaskForSampleCode(this.getBt());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTask> lists = LabDao.GetListBusTask(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());
            String begindate = ToolUtils.Decode(this.getBt().getStartdate());
            String enddate = ToolUtils.Decode(this.getBt().getEnddate());
            String labid = ToolUtils.Decode(this.getBt().getLabid());
            String taskid = ToolUtils.Decode(this.getBt().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.CheckComboValue(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskForMore() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());
            String begindate = ToolUtils.Decode(this.getBt().getStartdate());
            String enddate = ToolUtils.Decode(this.getBt().getEnddate());
            String labid = ToolUtils.Decode(this.getBt().getLabid());
            String taskid = ToolUtils.Decode(this.getBt().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.CheckComboValue(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                } else {
                    search += ToolUtils.GetAndSearch(search) + " a.flowstatus  = '92' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.samplecode  like '多样品样品编号%' ";

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTaskForMoreApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());
            String begindate = ToolUtils.Decode(this.getBt().getStartdate());
            String enddate = ToolUtils.Decode(this.getBt().getEnddate());
            String labid = ToolUtils.Decode(this.getBt().getLabid());
            String taskid = ToolUtils.Decode(this.getBt().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.CheckComboValue(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                } else {
                    search += ToolUtils.GetAndSearch(search) + " a.flowstatus  = '92' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.samplecode  like '多样品样品编号%' ";

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }

        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTaskForMoreAudit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());
            String begindate = ToolUtils.Decode(this.getBt().getStartdate());
            String enddate = ToolUtils.Decode(this.getBt().getEnddate());
            String labid = ToolUtils.Decode(this.getBt().getLabid());
            String taskid = ToolUtils.Decode(this.getBt().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.CheckComboValue(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                } else {
                    search += ToolUtils.GetAndSearch(search) + " a.flowstatus  = '92' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.samplecode  like '多样品样品编号%' ";

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }

        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskForQuery() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());
            String begindate = ToolUtils.Decode(this.getBt().getStartdate());
            String enddate = ToolUtils.Decode(this.getBt().getEnddate());
            String labid = ToolUtils.Decode(this.getBt().getLabid());
            String testedname = ToolUtils.Decode(this.getBt().getTestedname());
            // 按角色查询试验信息
            SysUser suser = new SysUser();
            suser.setUserid(ou.getUser().getUserid());
            suser.getItem().setGetaction("set");
            List<SysRole> userRole = UserDao.GetSetRole(suser);
            for (SysRole sysRole : userRole) {
                if (sysRole.getRoleid() == 7) {
                    deptpid = "";
                }
            }
            if (!ToolUtils.CheckComboValue(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.reqdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(testedname)) {
                search += ToolUtils.GetAndSearch(search) + " e.testedname like '%" + testedname + "%' ";
            }

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskForSamplecode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTaskForSamplecode(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskForDeal() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String samplename = ToolUtils.Decode(this.getBt().getSamplename());

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTaskForDeal(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskAllot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();
            String flowstatus = this.getBt().getFlowstatus();
            String samplecode = ToolUtils.Decode(this.getBt().getSamplecode());
            String taskid = ToolUtils.Decode(this.getBt().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + deptid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTaskAllot(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            LabService.SaveBusTask(this.getBt(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskForSamples() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0168");

            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);
            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            String approveusers = ServletActionContext.getRequest().getParameter("approveusers");

            LabService.SaveBusTaskForSamples(this.getBt(), details, ou, this.getRtv(), signerpassword, approveusers,
                    log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SaveAuditsForSamples() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            LabService.SaveAuditsForSamples(this.getBt(), this.getRtv(), signerpassword, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskSingle() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0143");
            List<BusSampleParam> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusSampleParam.class);
            LabService.SaveBusTaskSingle(this.getBt(), details, ou, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 撤销任务单
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String CancelBusTaskSingle() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0143");
            // String modifydesc= new
            // String(this.getBt().getModifydesc().getBytes("ISO-8859-1"), "UTF-8");
            String modifydesc = ToolUtils.Encode(this.getBt().getModifydesc());

            this.getBt().setModifydesc(modifydesc);
            LabService.CancelBusTaskSingle(this.getBt(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 批量委托
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String GetManyConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0143");

            String tranid = this.getBt().getTranid();
            String modifydesc = this.getBt().getModifydesc();
            int consigncount = Integer.parseInt(modifydesc);

            this.getBg().setTranid(tranid);
            BusGet rtn = LabDao.GetBusGet(this.getBg());
            rtn.setTranid("");
            // 借用这个字段来标注是同一批委托产品（时间具有唯一性）
            rtn.setMandatorysign(ToolUtils.GetDebugDate(ToolUtils.GetNowDate()));
            this.getBgd().setTranid(tranid);
            List<BusGetDetail> lists = LabDao.GetListBusGetDetail(this.getBgd());
            for (BusGetDetail busGetDetail : lists) {
                busGetDetail.setTranid("");
            }
            for (int i = 0; i < consigncount; i++) {
                rtn.getDeal().setAction(10);
                LabService.SaveManyConsign(rtn, lists, ou, this.getRtv(), log);

            }

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 同批次收样
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ManAccsample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0137");
            BusGet rtn = LabDao.GetBusGet(this.getBg());
            String mandatorysign = rtn.getMandatorysign();
            if (!ToolUtils.StringIsEmpty(mandatorysign)) {

                String search = "a.mandatorysign = '" + mandatorysign + "'";
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02' ";
                search += ToolUtils.GetAndSearch(search) + " i.tranid is null ";

                if (!ToolUtils.StringIsEmpty(rtn.getTestedname()))
                    search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + rtn.getTestedname() + "%' ";

                search += ToolUtils.GetAndSearch(search) + " a.tranid <> '" + this.getBg().getTranid() + "' ";

                this.SetSearch(this.getBg().getSearch(), this.getBg().getItem(), ou, search);
                this.getBg().setTranid("");
                this.getBg().getSearch().setEnd(1000);
                this.getBg().getItem().setGetaction("row");
                List<BusGet> lists = LabDao.SearchBusGetForAcc(this.getBg());

                for (BusGet busGet : lists) {
                    BusAccSample bas = new BusAccSample();
                    bas.setAccdate(ToolUtils2.GetNowDate());
                    bas.setTesteduser(busGet.getTesteduser());
                    bas.setTestfee("/");
                    bas.setTestedname(busGet.getTestedname());
                    bas.setAcctele("025—86229784");
                    bas.setFeestatus("/");
                    bas.setGetid(busGet.getTranid());
                    bas.setGettype(busGet.getGettype());
                    bas.setTestedid(busGet.getTestedid());
                    bas.setEntertele(busGet.getEntertele());
                    bas.getDeal().setAction(2);
                    bas.setSampleplace("收样室");
                    LabService.SaveBusAccSample(bas, ou, this.getRtv(), log);
                }

                ToolUtils.OutString(this.getRtv().toString());
            } else {
                this.getRtv().setMsg("请选择批次样品");
                ToolUtils.OutString(this.getRtv().toString());
            }
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String DeleteTaskAlert() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0143");
            LabService.DeleteTaskAlert(this.getBt(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskSingleList() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0143");

            List<BusSampleParam> BusSampleParam = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("bspsss"), BusSampleParam.class);
            List<BusAccSample> BusAccSample = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("basss"), BusAccSample.class);

            for (int i = 0; i < BusSampleParam.size(); i++) {
                BusAccSample rtn1 = new BusAccSample();
                rtn1.setSampletype(BusAccSample.get(i).getSampletype());
                rtn1.setTranid(BusAccSample.get(i).getTranid());
                BusAccSample rtn = LabDao.GetBusAccSampleByTranID(rtn1);
                rtn.setTestprop("02");

                BusGetDetail bgd = new BusGetDetail();
                bgd.setSamplecode(BusSampleParam.get(i).getSamplecode());
                BusGetDetail bgd2 = LabDao.GetBusGetDetailBySampleCode(bgd);

                BasMainParameter bmp = new BasMainParameter();
                bmp.setSamplecode(BusSampleParam.get(i).getSamplecode());
                List<BasMainParameter> bmplist = LabDao.GetBusSampleParamByConsign(bmp);
                BusTask bt = new BusTask();
                bt.setSamplename(bgd2.getSamplename());
                bt.setSamplestand(bgd2.getSamplestand());
                bt.setTestprop("02");
                bt.setReqdate(BusAccSample.get(i).getReqdate());
                bt.setTeststandardname(bgd2.getMainstandname());
                bt.setSenddate(BusAccSample.get(i).getSenddate());
                bt.setSamplestatus(bgd2.getSamplestatus());
                bt.setSenduser(BusAccSample.get(i).getAccuser());
                bt.setTranid(BusAccSample.get(i).getTranid());
                bt.setAccsampleid(BusAccSample.get(i).getTranid());
                bt.setSampleid(BusSampleParam.get(i).getSampleid());
                bt.setSamplecode(BusSampleParam.get(i).getSamplecode());
                bt.setSampletype(BusAccSample.get(i).getSampletype());
                DataDeal deal = new DataDeal();
                deal.setAction(3);
                bt.setDeal(deal);

                BusSampleParam.get(i).setSamplecode(bgd2.getSamplecode());
                BusSampleParam.get(i).setTranid(BusAccSample.get(i).getGetid());

                List<BusSampleParam> bsplist = LabDao.GetListBusSampleParamByAcc(BusSampleParam.get(i));
                for (int j = 0; j < bmplist.size(); j++) {
                    for (int j2 = 0; j2 < bsplist.size(); j2++) {
                        if (bmplist.get(j).getParameterid().equals(bsplist.get(j2).getParameterid())) {
                            bsplist.get(j2).setParamunit(bmplist.get(j).getParamunit());
                        }
                    }

                }

                LabService.SaveBusTaskSingle(bt, bsplist, ou, this.getRtv(), log);
            }

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskBatch() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0144");

            List<BusTaskFile> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskFile.class);

            List<BusSampleParam> partsdetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("partsdetails"), BusSampleParam.class);

            LabService.SaveBusTaskBatch(this.getBt(), details, partsdetails, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 同批次分配
    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ManAllot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0137");
            BusAccSample bas = new BusAccSample();
            bas.getItem().setGetaction("row");
            bas.setTranid(this.getBt().getAccsampleid());
            BusAccSample busac = LabDao.GetBusAccSample(bas);
            BusGet bgt = new BusGet();
            bgt.setTranid(busac.getGetid());
            BusGet rtn = LabDao.GetBusGet(bgt);
            String mandatorysign = rtn.getMandatorysign();
            if (!ToolUtils.StringIsEmpty(mandatorysign)) {
                BusTaskTester btter = new BusTaskTester();
                btter.setTaskid(this.getBt().getTaskid());

                List<BusTaskTester> testerdetails = LabDao.GetListBusTaskTester(btter);
                List<BusAccFile> accFiles = new ArrayList<BusAccFile>();
                BusTask bts = new BusTask();
                bts.setLabid(ou.getUser().getDeptid());
                bts.setModifydesc(mandatorysign);

                List<BusTask> manytask = LabDao.GetListBusTaskManyAllot(bts);
                for (BusTask busTask : manytask) {
                    BusTask btsallot = new BusTask();
                    btsallot.setFlowstatus(busTask.getFlowstatus());
                    btsallot.setTaskid(busTask.getTaskid());
                    btsallot.setSamplecode(busTask.getSamplecode());
                    btsallot.setSamplename(busTask.getSamplename());
                    LabService.SaveBusTaskAllot(btsallot, testerdetails, accFiles, ou, this.getRtv(), log);
                }

                ToolUtils.OutString(this.getRtv().toString());
            } else {
                this.getRtv().setMsg("请选择批次样品");
                ToolUtils.OutString(this.getRtv().toString());
            }
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskAllot() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0147");

            List<BusTaskTester> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskTester.class);

            List<BusAccFile> accFiles = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("filedetails"), BusAccFile.class);

            LabService.SaveBusTaskAllot(this.getBt(), details, accFiles, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskAcc() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0145");

            LabService.SaveBusTaskAcc(this.getBt(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveListBusTaskAcc() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0145");
            List<BusTask> BusTasklist = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("bstlist"), BusTask.class);
            for (int i = 0; i < BusTasklist.size(); i++) {
                LabService.SaveBusTaskAcc(BusTasklist.get(i), ou, this.getRtv(), log);
            }
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // dev 申请html
    public String HtmlReceiveSamples() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            BusTask item = new BusTask();
            item.setTaskid(tranid);
            item = LabDao.GetBusTask(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlReceiveSamples(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion BuyApply Methods

    // region BusTaskFile Methods

    private BusTaskFile btf;

    public BusTaskFile getBtf() {
        if (btf == null) {
            btf = new BusTaskFile();
        }
        return btf;
    }

    public void setBtf(BusTaskFile btf) {
        this.btf = btf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusTaskFile> lists = LabDao.GetListBusTaskFile(this.getBtf());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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

    // endregion BusTaskFile Methods

    // region BusAccFile Methods

    private BusAccFile baf;

    public BusAccFile getBaf() {
        if (baf == null) {
            baf = new BusAccFile();
        }
        return baf;
    }

    public void setBtf(BusAccFile baf) {
        this.baf = baf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusAccFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusAccFile> lists = LabDao.GetListBusAccFile(this.getBaf());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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

    // endregion BusAccFile Methods

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
    public String GetListBusTaskDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskDev> lists = LabDao.GetListBusTaskDev(this.getBtd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单设备", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskDevBySamplecode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskDev> lists = LabDao.GetListBusTaskDevBySamplecode(this.getBtd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单设备", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskDevByDevID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String devid = ToolUtils.Decode(this.getBtd().getDevid());
            this.getBtd().setDevid(devid);

            List<BusTaskDev> lists = LabDao.GetListBusTaskDevByDevID(this.getBtd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单设备", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskEnd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0155");

            List<BusTaskData> datas = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("datas"), BusTaskData.class);

            LabService.SaveBusTaskEnd(this.getBtsg(), datas, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskBack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            LabService.SaveBusTaskBack(this.getBt(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusPower() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BusTask> taskids = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("taskids"), BusTask.class);
            LabService.SaveBusPower(this.getBt(), taskids, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskEnd() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplename = ToolUtils.Decode(this.getBtsg().getSamplename());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());
            String deptpid = ou.getDept().getDeptpid();
            String taskid = ToolUtils.Decode(this.getBtsg().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " b.begintestdate is not null ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " b.testuser = '" + ou.getUser().getUserid() + "' ";
                }
            }

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskSingle(this.getBtsg());
            // List<BusTaskSingle> lists = LabDao.SearchBusTaskEnd(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskForUse() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String samplename = ToolUtils.Decode(this.getBtsg().getSamplename());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }
            //
            search += ToolUtils.GetAndSearch(search) + " b.testuser = " + ou.getUser().getUserid();

            search += ToolUtils.GetAndSearch(search) + " b.endtestdate is not null ";

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskSingle(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskBack() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '76' ";
            search += ToolUtils.GetAndSearch(search) + " a.labid = '" + ou.getUser().getDeptid() + "' ";

            this.SetSearch(this.getBt().getSearch(), this.getBt().getItem(), ou, search);

            List<BusTask> lists = LabDao.SearchBusTask(this.getBt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBt().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskBegin() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String labid = this.getBtsg().getLabid();
            String testuser = ou.getUser().getUserid();
            String deptpid = ou.getDept().getDeptpid();
            String samplename = ToolUtils.Decode(this.getBtsg().getSamplename());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());
            String taskid = ToolUtils.Decode(this.getBtsg().getTaskid());

            if (!ToolUtils.StringIsEmpty(taskid)) {
                search += ToolUtils.GetAndSearch(search) + " a.taskid  like '%" + taskid + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(labid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(testuser)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " b.testuser = '" + testuser + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.acceptuser <> '' ";
            search += ToolUtils.GetAndSearch(search) + " a.acceptdate is not null ";

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskSingle(this.getBtsg());

            // List<BusTaskSingle> lists = LabDao.SearchBusTaskBegin(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String labid = this.getBtsg().getLabid();
            String testuser = this.getBtsg().getLabuser();
            String flowstatus = ToolUtils.Decode(this.getBtsg().getFlowstatus());
            String aduituser = ToolUtils.Decode(this.getBtsg().getAduituser());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());

            if (!ToolUtils.StringIsEmpty(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            search += ToolUtils.GetAndSearch(search) + " a.acceptuser <> '' ";
            search += ToolUtils.GetAndSearch(search) + " a.acceptdate is not null ";

            if (testuser.equals("")) {
                search += ToolUtils.GetAndSearch(search) + " a.aduituser = '" + aduituser + "' ";
            } else {
                search += ToolUtils.GetAndSearch(search) + " b.testuser = '" + testuser + "' ";
            }

            search += ToolUtils.GetAndSearch(search) + " b.judgestatus <> '1' ";

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskRecord(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String labid = this.getBtsg().getLabid();
            String flowstatus = ToolUtils.Decode(this.getBtsg().getFlowstatus());
            String aduituser = ToolUtils.Decode(this.getBtsg().getAduituser());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());

            if (!ToolUtils.StringIsEmpty(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";
            else {
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus in ('92','94','98','99','96') ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.acceptuser <> '' ";
            search += ToolUtils.GetAndSearch(search) + " a.acceptdate is not null ";

            if (!ToolUtils.StringIsEmpty(aduituser)) {
                search += ToolUtils.GetAndSearch(search) + " a.aduituser = '" + aduituser + "' ";
            }

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskJudge(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String labid = this.getBtsg().getLabid();
            String flowstatus = ToolUtils.Decode(this.getBtsg().getFlowstatus());
            String aduituser = ToolUtils.Decode(this.getBtsg().getAduituser());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());

            if (!ToolUtils.StringIsEmpty(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";
            else {
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus in ('92','94','98','99','96') ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.acceptuser <> '' ";
            search += ToolUtils.GetAndSearch(search) + " a.acceptdate is not null ";

            // 填写人不可以进行审批
            search += ToolUtils.GetAndSearch(search) + " a.getuser <> '" + ou.getUser().getUserid() + "'";

            if (!ToolUtils.StringIsEmpty(aduituser)) {
                search += ToolUtils.GetAndSearch(search) + " a.aduituser = '" + aduituser + "' ";
            }

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskJudge(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskAduit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String labid = this.getBtsg().getLabid();
            String flowstatus = ToolUtils.Decode(this.getBtsg().getFlowstatus());
            String aduituser = ToolUtils.Decode(this.getBtsg().getAduituser());
            String samplecode = ToolUtils.Decode(this.getBtsg().getSamplecode());

            if (!ToolUtils.StringIsEmpty(labid)) {
                search += ToolUtils.GetAndSearch(search) + " a.labid = '" + labid + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";
            else {
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus in ('94','98','99') ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.acceptuser <> '' ";
            search += ToolUtils.GetAndSearch(search) + " a.acceptdate is not null ";

            // 填写人、审批人不可以进行审核
            search += ToolUtils.GetAndSearch(search) + " a.checkuser <> '" + ou.getUser().getUserid() + "'";
            search += ToolUtils.GetAndSearch(search) + " a.getuser <> '" + ou.getUser().getUserid() + "'";

            if (!ToolUtils.StringIsEmpty(aduituser)) {
                search += ToolUtils.GetAndSearch(search) + " a.aduituser = '" + aduituser + "' ";
            }

            this.SetSearch(this.getBtsg().getSearch(), this.getBtsg().getItem(), ou, search);

            List<BusTaskSingle> lists = LabDao.SearchBusTaskJudge(this.getBtsg());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtsg().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SubmitBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.SubmitBusTask(this.getBtsg(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // @AuthMethod(Menus="", Auth=MenuAuth.Modify, OutType=ActionOutType.Bean)
    // public String JudgeBusTask() throws Exception {
    // OnlineUser ou = ToolUtils.GetOnlineUser();
    //
    // if (ou != null) {
    // TranLog log = ToolUtils.InitTranLog("");
    // LabService.JudgeBusTask(this.getBtsg(), ou, this.getRtv(), log);
    // ToolUtils.OutString(this.getRtv().toString());
    // }
    // else
    // ToolUtils.OutString(this.NotLoginRtv());
    //
    // return null;
    // }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ComputeBusTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.ComputeBusTask(this.getBtsg(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    private SysUser su;

    public SysUser getSu() {
        if (su == null) {
            su = new SysUser();
        }
        return su;
    }

    public void setSu(SysUser su) {
        this.su = su;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetLabLeader() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SysUser rtn = new SysUser();
        if (ou != null) {
            rtn = LabDao.GetLabLeader(this.getSu());
            if (rtn == null) {
                rtn = new SysUser();
            }
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    private BasMainParameter bmp;

    public BasMainParameter getBmp() {
        if (bmp == null)
            bmp = new BasMainParameter();

        return bmp;
    }

    public void setBmp(BasMainParameter bmp) {
        this.bmp = bmp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusSampleParamBySample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BasMainParameter> lists = LabDao.GetBusSampleParamBySample(this.getBmp());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
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
    public String GetBusSampleParamByConsign() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BasMainParameter> lists = LabDao.GetBusSampleParamByConsign(this.getBmp());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
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
    public String GetBasSampleParamForCombo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasMainParameter> lists = LabDao.GetBasSampleParamForCombo(this.getBmp());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
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

    // endregion BusTask Methods

    // region BusTaskTest Methods

    private BusTaskTest btt;

    public BusTaskTest getBtt() {
        if (btt == null)
            btt = new BusTaskTest();

        return btt;
    }

    public void setBtt(BusTaskTest btt) {
        this.btt = btt;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskTest() throws Exception {
        // OnlineUser ou = ToolUtils.GetOnlineUser();
        //
        // BusTaskTest rtn = new BusTaskTest();
        // if (ou != null) {
        // rtn = LabDao.GetBusTaskTest(this.getBtt());
        // }
        // ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTest> lists = LabDao.GetListBusTaskTest(this.getBtt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测项目明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskTestForMore() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTest> lists = LabDao.GetListBusTaskTestForMore(this.getBtt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测项目明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTest> lists = LabDao.GetListBusTaskParam(this.getBtt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-委托书检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusTaskTest Methods

    // region BusTaskData Methods

    private BusTaskData btdt;

    public BusTaskData getBtdt() {
        if (btdt == null)
            btdt = new BusTaskData();

        return btdt;
    }

    public void setBtdt(BusTaskData btdt) {
        this.btdt = btdt;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskData() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskData rtn = new BusTaskData();
        if (ou != null) {
            rtn = LabDao.GetBusTaskData(this.getBtdt());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskData() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskData> lists = LabDao.GetListBusTaskData(this.getBtdt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测样品数据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskData() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBtdt().getSearch().setStart(start + 1);
            this.getBtdt().getSearch().setEnd(this.GetEndCnt());

            List<BusTaskData> lists = LabDao.SearchBusTaskData(this.getBtdt());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBtdt().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测样品数据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusTaskData Methods

    // region BusTaskSample

    private BusTaskSample bts;

    public BusTaskSample getBts() {
        if (bts == null)
            bts = new BusTaskSample();

        return bts;
    }

    public void setBts(BusTaskSample bts) {
        this.bts = bts;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String GetListBusTaskSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSample> lists = LabDao.GetListBusTaskSample(this.getBts());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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
    public String GetBusTaskSampleByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSample> lists = LabDao.GetBusTaskSampleByTask(this.getBts());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测样品数据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusTaskSampleForJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSample> lists = LabDao.GetBusTaskSampleForJudge(this.getBts());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测样品数据", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String GetListBusTaskForAcc() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSample> lists = LabDao.GetListBusTaskForAcc(this.getBts());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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

    // endregion BusTaskSample

    // region GetConsignCode Methods

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetConsignCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSample> lists = LabDao.GetConsignCode(this.getBc());

            ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion GetConsignCode Methods

    // region BusSampleParam Methods

    private BusSampleParam bsp;

    public BusSampleParam getBsp() {
        if (bsp == null)
            bsp = new BusSampleParam();

        return bsp;
    }

    public void setBsp(BusSampleParam bsp) {
        this.bsp = bsp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusSampleParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusSampleParam rtn = new BusSampleParam();
        if (ou != null) {
            rtn = LabDao.GetBusSampleParam(this.getBsp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusSampleParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusSampleParam> lists = LabDao.GetListBusSampleParam(this.getBsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-样品检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusSampleParamForTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusSampleParam> lists = LabDao.GetListBusSampleParamForTask(this.getBsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-样品检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusSampleParamByAcc() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusSampleParam> lists = LabDao.GetListBusSampleParamByAcc(this.getBsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-样品检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusSampleParamByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusSampleParam> lists = LabDao.GetListBusSampleParamByTask(this.getBsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-样品检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusSampleParam() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBsp().getSearch().setStart(start + 1);
            this.getBsp().getSearch().setEnd(this.GetEndCnt());

            List<BusSampleParam> lists = LabDao.SearchBusSampleParam(this.getBsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBsp().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-样品检测", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusSampleParam() throws Exception {
        // OnlineUser ou = ToolUtils.GetOnlineUser();
        //
        // if (ou != null) {
        // TranLog log = ToolUtils.InitTranLog("");
        // LabService.SaveBusSampleParam(this.getBsp(), this.getRtv(), log);
        // ToolUtils.OutString(this.getRtv().toString());
        // }
        // else
        // ToolUtils.OutString(this.NotLoginRtv());
        //
        return null;
    }

    // endregion BusSampleParam Methods

    // region BusAccSample Methods

    private BusAccSample bas;

    public BusAccSample getBas() {
        if (bas == null)
            bas = new BusAccSample();

        return bas;
    }

    public void setBas(BusAccSample bas) {
        this.bas = bas;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusAccSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusAccSample rtn = new BusAccSample();
        if (ou != null) {
            rtn = LabDao.GetBusAccSample(this.getBas());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusAccSampleByGetID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusAccSample rtn = new BusAccSample();
        if (ou != null) {
            rtn = LabDao.GetBusAccSampleByGetID(this.getBas());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusAccSampleByTranID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusAccSample rtn = new BusAccSample();
        if (ou != null) {
            rtn = LabDao.GetBusAccSampleByTranID(this.getBas());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusAccSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusAccSample> lists = LabDao.GetListBusAccSample(this.getBas());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-收样", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusAccSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String samplename = ToolUtils.Decode(this.getBas().getSamplename());
            String samplecode = ToolUtils.Decode(this.getBas().getSamplecode());
            String begindate = ToolUtils.Decode(this.getBas().getStartdate());
            String enddate = ToolUtils.Decode(this.getBas().getEnddate());
            String gettype = ToolUtils.Decode(this.getBas().getGettype());
            String issend = ToolUtils.Decode(this.getBas().getIssendstatus());
            String sampletype = ToolUtils.Decode(this.getBas().getSampletype());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(gettype)) {
                search += ToolUtils.GetAndSearch(search) + " a.gettype = '" + gettype + "' ";
            }

            if (!ToolUtils.CheckComboValue(sampletype)) {
                search += ToolUtils.GetAndSearch(search) + " a.sampletype = '" + sampletype + "' ";
            }

            if (!ToolUtils.CheckComboValue(issend)) {
                search += ToolUtils.GetAndSearch(search) + " a.issend = '" + issend + "' ";
            }
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.accuser like '%" + ou.getUser().getUserid() + "%' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.samplecode not like '多样品样品编号%' ";

            this.SetSearch(this.getBas().getSearch(), this.getBas().getItem(), ou, search);

            List<BusAccSample> lists = LabDao.SearchBusAccSample(this.getBas());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBas().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-收样", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusAccSampleBatch() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String sampletype = this.getBas().getSampletype();
            String samplename = ToolUtils.Decode(this.getBas().getSamplename());
            String testedname = ToolUtils.Decode(this.getBas().getTestedname());
            String begindate = ToolUtils.Decode(this.getBas().getStartdate());
            String enddate = ToolUtils.Decode(this.getBas().getEnddate());
            String gettype = ToolUtils.Decode(this.getBas().getGettype());
            String issend = ToolUtils.Decode(this.getBas().getIssendstatus());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(testedname)) {
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";
            }

            if (!ToolUtils.CheckComboValue(gettype)) {
                search += ToolUtils.GetAndSearch(search) + " a.gettype = '" + gettype + "' ";
            }

            if (!ToolUtils.StringIsEmpty(sampletype)) {
                search += ToolUtils.GetAndSearch(search) + " a.sampletype = '" + sampletype + "' ";
            }

            if (!ToolUtils.CheckComboValue(issend)) {
                search += ToolUtils.GetAndSearch(search) + " a.issend = '" + issend + "' ";
            }

            search += ToolUtils.GetAndSearch(search) + " a.samplecode like '多样品样品编号%' ";

            search += ToolUtils.GetAndSearch(search) + " a.accuser = '" + ou.getUser().getUserid() + "' ";

            this.SetSearch(this.getBas().getSearch(), this.getBas().getItem(), ou, search);

            List<BusAccSample> lists = LabDao.SearchBusAccSampleForBatch(this.getBas());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBas().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-收样", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusAccSampleAll() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String samplename = ToolUtils.Decode(this.getBas().getSamplename());
            String testedname = ToolUtils.Decode(this.getBas().getTestedname());
            String begindate = ToolUtils.Decode(this.getBas().getStartdate());
            String enddate = ToolUtils.Decode(this.getBas().getEnddate());
            String gettype = ToolUtils.Decode(this.getBas().getGettype());

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.accdate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(samplename)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplename like '%" + samplename + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(testedname)) {
                search += ToolUtils.GetAndSearch(search) + " a.testedname like '%" + testedname + "%' ";
            }

            if (!ToolUtils.CheckComboValue(gettype)) {
                search += ToolUtils.GetAndSearch(search) + " a.gettype = '" + gettype + "' ";
            }
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.accuser like '%" + ou.getUser().getUserid() + "%' ";
            }
            this.SetSearch(this.getBas().getSearch(), this.getBas().getItem(), ou, search);

            List<BusAccSample> lists = LabDao.SearchBusAccSampleForBatch(this.getBas());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBas().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-收样", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusAccSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0137");
            LabService.SaveBusAccSample(this.getBas(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String CommitBusAccSample() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            this.getBas().getDeal().setAction(DataAction.Submit.getAction());
            LabService.SaveBusAccSample(this.getBas(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusAccSample Methods

    // region BusTaskTester Methods

    private BusTaskTester bttr;

    public BusTaskTester getBttr() {
        if (bttr == null)
            bttr = new BusTaskTester();

        return bttr;
    }

    public void setBttr(BusTaskTester bttr) {
        this.bttr = bttr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskTester() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskTester rtn = new BusTaskTester();
        if (ou != null) {
            rtn = LabDao.GetBusTaskTester(this.getBttr());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskTester() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTester> lists = LabDao.GetListBusTaskTester(this.getBttr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测员检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskTesterByUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTester> lists = LabDao.GetListBusTaskTesterByUser(this.getBttr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测员检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskTesterBySingle() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskTester> lists = LabDao.GetListBusTaskTesterBySingle(this.getBttr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测员检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusTaskTester() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBttr().getSearch().setStart(start + 1);
            this.getBttr().getSearch().setEnd(this.GetEndCnt());

            List<BusTaskTester> lists = LabDao.SearchBusTaskTester(this.getBttr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBttr().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测-任务单检测员检测项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusTaskTester Methods

    // region BusTaskSingle Methods

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
    public String GetBusTaskSingle() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskSingle rtn = new BusTaskSingle();
        if (ou != null) {
            rtn = LabDao.GetBusTaskSingle(this.getBtsg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskSingleByTaskid() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskSingle rtn = new BusTaskSingle();
        if (ou != null) {
            rtn = LabDao.GetBusTaskSingleByTaskid(this.getBtsg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskSingleBySampleTran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskSingle rtn = new BusTaskSingle();
        if (ou != null) {
            rtn = LabDao.GetBusTaskSingleBySampleTran(this.getBtsg());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskSingleByTranID() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskSingle> lists = LabDao.GetBusTaskSingleByTranID(this.getBtsg());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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
    public String SaveBusTaskBegin() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0153");
            List<BusTaskTester> busTaskTesters = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskTester.class);
            LabService.SaveBusTaskBegin(this.getBtsg(), ou, busTaskTesters, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusTaskSingle Methods

    // region BusTaskAttach Methods

    private BusTaskAttach bta;

    public BusTaskAttach getBta() {
        if (bta == null) {
            bta = new BusTaskAttach();
        }
        return bta;
    }

    public void setBta(BusTaskAttach bta) {
        this.bta = bta;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskAttach() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusTaskAttach> lists = LabDao.GetListBusTaskAttach(this.getBta());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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
    public String SaveBusTaskRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            List<BusRecordFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), BusRecordFile.class);

            LabService.SaveBusTaskRecord(this.getBtsg(), details, filedetails, ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ApproveBusTaskRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            LabService.ApproveBusTaskRecord(this.getBtsg(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String AuditBusTaskRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            LabService.AuditBusTaskRecord(this.getBtsg(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusTaskAttach Methods

    // region BusRecordFile Methods

    private BusRecordFile brf;

    public BusRecordFile getBrf() {
        if (brf == null) {
            brf = new BusRecordFile();
        }
        return brf;
    }

    public void setBrf(BusRecordFile brf) {
        this.brf = brf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusRecordFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusRecordFile> lists = LabDao.GetListBusRecordFile(this.getBrf());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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

    // endregion BusRecordFile Methods

    // region BusReportFile Methods

    private BusReportFile bref;

    public BusReportFile getBref() {
        if (bref == null) {
            bref = new BusReportFile();
        }
        return bref;
    }

    public void setBref(BusReportFile bref) {
        this.bref = bref;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusReportFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusReportFile> lists = LabDao.GetListBusReportFile(this.getBref());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
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

    // endregion BusReportFile Methods

    // region BusSelect Methods

    private BusSelect bselect;

    public BusSelect getBselect() {
        if (bselect == null) {
            bselect = new BusSelect();
        }
        return bselect;
    }

    public void setBselect(BusSelect bselect) {
        this.bselect = bselect;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusSelectByCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        if (ou != null) {
            List<BusSelect> lists = LabDao.GetBusSelectByCode(this.getBselect());
            ToolUtils.OutString(this.OutLists(lists, true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    // endregion BusSelect Methods

    // region BusTaskJudge Methods

    private BusTaskJudge btj;

    public BusTaskJudge getBtj() {
        if (btj == null)
            btj = new BusTaskJudge();

        return btj;
    }

    public void setBtj(BusTaskJudge btj) {
        this.btj = btj;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusTaskJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BusTaskJudge rtn = new BusTaskJudge();
        if (ou != null) {
            rtn = LabDao.GetBusTaskJudge(this.getBtj());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskJudge> lists = LabDao.GetListBusTaskJudge(this.getBtj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskJudgeForNo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskJudge> lists = LabDao.GetListBusTaskJudgeForNo(this.getBtj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskJudgeForYes() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskJudge> lists = LabDao.GetListBusTaskJudgeForYes(this.getBtj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskJudgeBySampleCode() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskJudge> lists = LabDao.GetListBusTaskJudgeBySampleCode(this.getBtj());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusTaskJudge() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.SaveBusTaskJudge(this.getBtj(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusTaskJudge Methods

    // region SignerPassword Methods

    private SignerPassword lsp;

    public SignerPassword getLsp() {
        if (lsp == null)
            lsp = new SignerPassword();

        return lsp;
    }

    public void setLsp(SignerPassword lsp) {
        this.lsp = lsp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SignerPassword rtn = new SignerPassword();
        if (ou != null) {
            rtn = LabDao.GetSignerPassword(this.getLsp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SignerPassword> lists = LabDao.GetListSignerPassword(this.getLsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getLsp().getSearch().setStart(start + 1);
            this.getLsp().getSearch().setEnd(this.GetEndCnt());

            List<SignerPassword> lists = LabDao.SearchSignerPassword(this.getLsp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getLsp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.SaveSignerPassword(this.getLsp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ChangeSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.ChangeSignerPassword(this.getLsp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ResetSignerPassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            LabService.ResetSignerPassword(this.getLsp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion SignerPassword Methods

    // region BusTaskResult Methods

    private BusTaskResult btr;

    public BusTaskResult getBtr() {
        if (btr == null)
            btr = new BusTaskResult();

        return btr;
    }

    public void setBtr(BusTaskResult btr) {
        this.btr = btr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskResult> lists = LabDao.GetListBusTaskResult(this.getBtr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskResultForIn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskResult> lists = LabDao.GetListBusTaskResultForIn(this.getBtr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskResultForReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskResult> lists = LabDao.GetListBusTaskResultForReport(this.getBtr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BusTaskResult Methods

    // region BusTaskLab Methods
    private BusTaskLab btl;

    public BusTaskLab getBtl() {
        if (btl == null)
            btl = new BusTaskLab();

        return btl;
    }

    public void setBtr(BusTaskLab btl) {
        this.btl = btl;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBusTaskLab() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusTaskLab> lists = LabDao.GetListBusTaskLab(this.getBtl());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("检测结果判定", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }
}
