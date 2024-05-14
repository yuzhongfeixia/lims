package com.alms.action;

import java.io.FileInputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.DatDao;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusReport;
import com.alms.entity.dat.DatClassSource;
import com.alms.entity.dat.DatSampleField;
import com.alms.entity.dat.DatSampleTest;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dat.SetBusReport;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusReportFile;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.service.DatService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.FileUtils;
import com.gpersist.utils.OfficeUtils;
import com.gpersist.utils.ToolUtils;

public class DatAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region DatSampleTest Methods

    private DatSampleTest dst;

    public DatSampleTest getDst() {
        if (dst == null)
            dst = new DatSampleTest();

        return dst;
    }

    public void setDst(DatSampleTest dst) {
        this.dst = dst;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDatSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DatSampleTest rtn = new DatSampleTest();
        if (ou != null) {
            rtn = DatDao.GetDatSampleTest(this.getDst());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDatSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DatSampleTest> lists = DatDao.GetListDatSampleTest(this.getDst());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDatSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String parameterid = ToolUtils.Decode(this.getDst().getParameterid());
            String classsource = ToolUtils.Decode(this.getDst().getClasssource());

            if (!ToolUtils.CheckComboValue(parameterid)) {
                search += ToolUtils.GetAndSearch(search) + " a.parameterid = '" + parameterid + "' ";
            }

            if (!ToolUtils.CheckComboValue(classsource)) {
                search += ToolUtils.GetAndSearch(search) + " a.classsource = '" + classsource + "' ";
            }

            this.SetSearch(this.getDst().getSearch(), this.getDst().getItem(), ou, search);
            List<DatSampleTest> lists = DatDao.SearchDatSampleTest(this.getDst());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDst().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDatSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0206");
            DatService.SaveDatSampleTest(this.getDst(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String DeleteDatSampleTest() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<DatSampleTest> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), DatSampleTest.class);

            DatService.DeleteDatSampleTest(deletes, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DatSampleTest Methods

    // region BusRecord Methods

    private BusRecord br;

    public BusRecord getBr() {
        if (br == null)
            br = new BusRecord();
        return br;
    }

    public void setBr(BusRecord br) {
        this.br = br;
    }

    private BusTask btask;

    public BusTask getBtask() {
        if (btask == null)
            btask = new BusTask();
        return btask;
    }

    public void setBtask(BusTask btask) {
        this.btask = btask;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusRecordForSampleTran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String recordstatus = ToolUtils.Decode(this.getBr().getRecordstatus());
            String samplecode = ToolUtils.Decode(this.getBr().getSamplecode());
            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " d.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(recordstatus)) {
                if (!recordstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.recordstatus = '" + recordstatus + "' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.crtuser = '" + ou.getUser().getUserid() + "' ";
                }
            }

            this.SetSearch(this.getBr().getSearch(), this.getBr().getItem(), ou, search);

            List<BusRecord> lists = DatDao.SearchBusRecordForSampleTran(this.getBr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            this.SetSearch(this.getBr().getSearch(), this.getBr().getItem(), ou, search);

            List<BusRecord> lists = DatDao.SearchBusRecord(this.getBr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusRecordForSummary() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String begindate = ToolUtils.Decode(this.getBr().getStartdate());
            String enddate = ToolUtils.Decode(this.getBr().getEnddate());
            String parameterid = ToolUtils.Decode(this.getBr().getParameterid());
            String samplecode = ToolUtils.Decode(this.getBr().getSamplecode());
            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.CheckComboValue(parameterid)) {
                search += ToolUtils.GetAndSearch(search) + " e.parameterid = '" + parameterid + "' ";
            }

            if (!ToolUtils.CheckComboValue(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " f.samplecode = '" + samplecode + "' ";
            }

            if (!ToolUtils.StringIsEmpty(begindate)) {
                search += ToolUtils.GetAndSearch(search) + " a.trandate  >= '" + begindate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(enddate)) {
                search += ToolUtils.GetAndSearch(search) + " a.trandate  <= '" + enddate + "' ";
            }

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.crtuser = '" + ou.getUser().getUserid() + "' ";
                }
            }

            search += ToolUtils.GetAndSearch(search) + " a.trandate is not null ";

            this.SetSearch(this.getBr().getSearch(), this.getBr().getItem(), ou, search);

            List<BusRecord> lists = DatDao.SearchBusRecordForSummary(this.getBr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusRecordApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String recordstatus = ToolUtils.Decode(this.getBr().getRecordstatus());
            String samplecode = ToolUtils.Decode(this.getBr().getSamplecode());
            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.deptid = '" + ou.getUser().getDeptid() + "' ";
                    // search += ToolUtils.GetAndSearch(search) + " (a.approveuser = '" +
                    // ou.getUser().getUserid()+ "' or a.approveuser is null) ";
                    search += ToolUtils.GetAndSearch(search) + " a.approveuser like '%" + ou.getUser().getUserid()
                            + "%' ";
                }
            }

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(recordstatus)) {
                if (!recordstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.recordstatus = '" + recordstatus + "' ";
                }
            }

            this.SetSearch(this.getBr().getSearch(), this.getBr().getItem(), ou, search);

            List<BusRecord> lists = DatDao.SearchBusRecordForJudge(this.getBr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("样品检测复核记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusRecordAudit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String recordstatus = ToolUtils.Decode(this.getBr().getRecordstatus());
            String samplecode = ToolUtils.Decode(this.getBr().getSamplecode());
            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.deptid = '" + ou.getUser().getDeptid() + "' ";
                    search += ToolUtils.GetAndSearch(search) + "a.aduituser like '%" + ou.getUser().getUserid() + "%' ";
                    search += ToolUtils.GetAndSearch(search) + " a.approveuser <> '" + ou.getUser().getUserid()
                            + "' and a.approveuser is not null ";
                }
            }

            search += ToolUtils.GetAndSearch(search) + " a.recordstatus <> '06' ";

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(recordstatus)) {
                if (!recordstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.recordstatus = '" + recordstatus + "' ";
                }
            }

            this.SetSearch(this.getBr().getSearch(), this.getBr().getItem(), ou, search);

            List<BusRecord> lists = DatDao.SearchBusRecordForJudge(this.getBr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据检测计算", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    public String GetSetBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusRecord rtn = new SetBusRecord();
        if (ou != null) {
            rtn = DatService.GetSetBusRecord(this.getBr());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    public String GetSetBusRecordForSum() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusRecord rtn = new SetBusRecord();
        if (ou != null) {
            rtn = DatService.GetSetBusRecordForSum(this.getBr());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusRecordByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusRecord> lists = DatDao.GetBusRecordByTask(this.getBtask());

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
    public String GetBusRecordByParaTime() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusRecord> lists = DatDao.GetBusRecordByParaTime(this.getBr());

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
    public String GetBusRecordBySampleTran() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusRecord> lists = DatDao.GetBusRecordBySampleTran(this.getBr());

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
    public String GetSampleTranBySampleCode() throws Exception {
      OnlineUser ou = ToolUtils.GetOnlineUser();
      
      if (ou != null) {
        List<BusRecord> lists = DatDao.GetSampleTranBySampleCode(this.getBr());
        
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
    public String GetBusRecordForApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusRecord> lists = DatDao.GetBusRecordForApprove(this.getBr());

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

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SubmitBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0163");

            List<BusTaskAttach> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BusTaskAttach.class);

            List<BusRecordFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), BusRecordFile.class);

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            String approveusers = ServletActionContext.getRequest().getParameter("approveusers");

            DatService.SubmitBusRecord(this.getBr(), this.getRtv(), details, filedetails, signerpassword, approveusers,
                    ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String ApproveBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            String auditusers = ServletActionContext.getRequest().getParameter("auditusers");

            DatService.ApproveBusRecord(this.getBr(), this.getRtv(), signerpassword, auditusers, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String AuditBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.AuditBusRecord(this.getBr(), this.getRtv(), signerpassword, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusRecord Methods

    // region DatClassSource Methods

    private DatClassSource dcs;

    public DatClassSource getDcs() {
        if (dcs == null)
            dcs = new DatClassSource();

        return dcs;
    }

    public void setDcs(DatClassSource dcs) {
        this.dcs = dcs;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDatClassSource() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DatClassSource rtn = new DatClassSource();
        if (ou != null) {
            rtn = DatDao.GetDatClassSource(this.getDcs());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDatClassSource() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DatClassSource> lists = DatDao.GetListDatClassSource(this.getDcs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-数据类来源", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDatClassSource() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            this.SetSearch(this.getDcs().getSearch(), this.getDcs().getItem(), ou, search);

            List<DatClassSource> lists = DatDao.SearchDatClassSource(this.getDcs());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDcs().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-数据类来源", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDatClassSource() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0205");
            DatService.SaveDatClassSource(this.getDcs(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DatClassSource Methods

    // region DatSampleField Methods

    private DatSampleField dsf;

    public DatSampleField getDsf() {
        if (dsf == null)
            dsf = new DatSampleField();

        return dsf;
    }

    public void setDsf(DatSampleField dsf) {
        this.dsf = dsf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDatSampleField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DatSampleField rtn = new DatSampleField();
        if (ou != null) {
            rtn = DatDao.GetDatSampleField(this.getDsf());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDatSampleField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DatSampleField> lists = DatDao.GetListDatSampleField(this.getDsf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据类字段", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDatSampleField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String parameterid = ToolUtils.Decode(this.getDsf().getParameterids());
            String classsource = ToolUtils.Decode(this.getDsf().getClasssource());
            String datasource = ToolUtils.Decode(this.getDsf().getDatasource());

            if (!ToolUtils.CheckComboValue(parameterid)) {
                if (!parameterid.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.parameterids = '" + parameterid + "' ";
                }
            }

            if (!ToolUtils.CheckComboValue(classsource)) {
                if (!classsource.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.classsource = '" + classsource + "' ";
                }
            }

            if (!ToolUtils.CheckComboValue(datasource)) {
                if (!datasource.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.datasource = '" + datasource + "' ";
                }
            }

            this.SetSearch(this.getDsf().getSearch(), this.getDsf().getItem(), ou, search);

            List<DatSampleField> lists = DatDao.SearchDatSampleField(this.getDsf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDsf().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("数据-样品数据类字段", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDatSampleField() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0207");
            DatService.SaveDatSampleField(this.getDsf(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DatSampleField Methods

    // region BusReport Methods

    private BusTask bt;

    public BusTask getBt() {
        if (bt == null)
            bt = new BusTask();

        return bt;
    }

    public void setBt(BusTask bt) {
        this.bt = bt;
    }

    private BusReport breport;

    public BusReport getBreport() {
        if (breport == null)
            breport = new BusReport();
        return breport;
    }

    public void setBreport(BusReport breport) {
        this.breport = breport;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();
        BusReport rtn = new BusReport();

        this.getBreport().setReportid(ToolUtils.Decode(this.getBreport().getReportid()));

        if (ou != null) {
            rtn = DatDao.GetBusReport(this.getBreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportstatus = ToolUtils.Decode(this.getBreport().getReportstatus());
            String ispass = ToolUtils.Decode(this.getBreport().getIspass());
            String samplecode = ToolUtils.Decode(this.getBreport().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(ispass)) {
                search += ToolUtils.GetAndSearch(search) + " a.ispass = '" + ispass + "' ";
            }

            if (!ToolUtils.CheckComboValue(reportstatus)) {
                if (!reportstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.reportstatus = '" + reportstatus + "' ";
                }
            }
            if (!ou.getUser().getUserid().contains("99")) {
                search += ToolUtils.GetAndSearch(search) + " e.accuser = '" + ou.getUser().getUserid() + "' ";

                search += ToolUtils.GetAndSearch(search) + " (a.tranuser ='" + ou.getUser().getUserid()
                        + "' or a.tranuser = '') ";
            }

            this.SetSearch(this.getBreport().getSearch(), this.getBreport().getItem(), ou, search);

            List<BusReport> lists = DatDao.SearchBusReport(this.getBreport());

            for (BusReport busReport : lists) {
                if (busReport.getFormid().equals("0000000110")) {
                    busReport.setSamplename("环境报告（绿色产地）");
                }
                if (busReport.getFormid().equals("0000000111")) {
                    busReport.setSamplename("环境报告（非绿色产地）");
                }
            }

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("试验报告编制", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusReportApprove() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportstatus = ToolUtils.Decode(this.getBreport().getReportstatus());
            String samplecode = ToolUtils.Decode(this.getBreport().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(reportstatus)) {
                if (!reportstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.reportstatus = '" + reportstatus + "' ";
                }
            }

            search += ToolUtils.GetAndSearch(search) + " a.reportstatus > '01' ";
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " a.tranuser <> '" + ou.getUser().getUserid() + "' ";

                search += ToolUtils.GetAndSearch(search) + " (a.approveuser ='" + ou.getUser().getUserid()
                        + "' or a.approveuser = '') ";
            }
            this.SetSearch(this.getBreport().getSearch(), this.getBreport().getItem(), ou, search);

            List<BusReport> lists = DatDao.SearchBusReport(this.getBreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("试验报告复核", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusReportAduit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportstatus = ToolUtils.Decode(this.getBreport().getReportstatus());
            String samplecode = ToolUtils.Decode(this.getBreport().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(reportstatus)) {
                if (!reportstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.reportstatus = '" + reportstatus + "' ";
                }
            }
            if (!(ou.getDept().getDeptid().equals("9999"))) {
                search += ToolUtils.GetAndSearch(search) + " (a.aduituser ='" + ou.getUser().getUserid()
                        + "' or a.aduituser = '') ";
            }
            search += ToolUtils.GetAndSearch(search) + " a.reportstatus > '02' ";

            this.SetSearch(this.getBreport().getSearch(), this.getBreport().getItem(), ou, search);

            List<BusReport> lists = DatDao.SearchBusReport(this.getBreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("试验报告审核", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusReportBackUp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportstatus = ToolUtils.Decode(this.getBreport().getReportstatus());
            String samplecode = ToolUtils.Decode(this.getBreport().getSamplecode());

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.CheckComboValue(reportstatus)) {
                if (!reportstatus.equals("null")) {
                    search += ToolUtils.GetAndSearch(search) + " a.reportstatus = '" + reportstatus + "' ";
                }
            }

            search += ToolUtils.GetAndSearch(search) + " a.reportstatus > '12' ";

            this.SetSearch(this.getBreport().getSearch(), this.getBreport().getItem(), ou, search);

            List<BusReport> lists = DatDao.SearchBusReport(this.getBreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("试验报告审核", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("0172");

            List<BusReportFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), BusReportFile.class);

            DatService.SaveBusReport(this.getBreport(), filedetails, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
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

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetBusReportByTask() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BusReport> lists = DatDao.GetBusReportByTask(this.getBtsg());

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

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSetBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SetBusReport rtn = new SetBusReport();

        this.getBreport().setReportid(ToolUtils.Decode(this.getBreport().getReportid()));

        if (ou != null) {
            rtn = DatService.GetSetBusReport(this.getBreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String MakeBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            DatService.MakeBusReport(this.getBreport(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String BackBusRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String reportid = ServletActionContext.getRequest().getParameter("reportid");
            String getsource = ServletActionContext.getRequest().getParameter("getsource");
            String[] routeResultArray = getsource.split(",");
            String wincheckdesc = ServletActionContext.getRequest().getParameter("wincheckdesc");
            for (int i = 0; i < routeResultArray.length; i++) {

                DatService.BackBusRecord(reportid, routeResultArray[i], wincheckdesc, this.getRtv(), ou, log);

            }
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String SubmitBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.SubmitBusReport(this.getBreport(), signerpassword, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String ApproveBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.ApproveBusReport(this.getBreport(), signerpassword, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String BackBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            DatService.BackBusReport(this.getBreport(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String BackUpBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.BackUpBusReport(this.getBreport(), signerpassword, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String GetPdfByRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            SetBusRecord records = DatService.GetSetBusRecord(this.getBr());

            String nowfilename = records.getRecord().getSampleid() + "_" + records.getRecord().getSamplename() + "_"
                    + records.getRecord().getRecordid() + ".xls";

            filename = ToolUtils.Encode(FileUtils.ChangeFileSuffix(nowfilename, FileUtils.FILE_TYPE_PDF));

            String excelfile = DatService.GetExcelByRecord(records, nowfilename);
            String pdfpath = FileUtils.ChangeFileSuffix(excelfile, FileUtils.FILE_TYPE_PDF);
            OfficeUtils.File2Pdf(excelfile, pdfpath);

            is = new FileInputStream(pdfpath);
        }

        return Consts.DEFAULT_FILE_RETURN;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String AuditBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.AuditBusReport(this.getBreport(), signerpassword, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBusReportPrint() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String reportstatus = ToolUtils.Decode(this.getBreport().getReportstatus());
            String samplecode = ToolUtils.Decode(this.getBreport().getSamplecode()).trim();
            String testedname = ToolUtils.Decode(this.getBreport().getTestedname()).trim();
            String tranusername = ToolUtils.Decode(this.getBreport().getTranusername()).trim();
            String approveusername = ToolUtils.Decode(this.getBreport().getApproveusername()).trim();
            String aduitusername = ToolUtils.Decode(this.getBreport().getAduitusername()).trim();
            String ispass = ToolUtils.Decode(this.getBreport().getIspass());

            if (!ToolUtils.StringIsEmpty(samplecode)) {
                search += ToolUtils.GetAndSearch(search) + " a.samplecode like '%" + samplecode + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(ispass)) {
                search += ToolUtils.GetAndSearch(search) + " a.ispass = '" + ispass + "' ";
            }

            if (!ToolUtils.StringIsEmpty(testedname)) {
                search += ToolUtils.GetAndSearch(search) + " e.testedname like '%" + testedname + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(tranusername)) {
                search += ToolUtils.GetAndSearch(search) + " a.tranusername like '%" + tranusername + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(approveusername)) {
                search += ToolUtils.GetAndSearch(search) + " a.approveusername like '%" + approveusername + "%' ";
            }

            if (!ToolUtils.StringIsEmpty(aduitusername)) {
                search += ToolUtils.GetAndSearch(search) + " a.aduitusername like '%" + aduitusername + "%' ";
            }

            if (!ToolUtils.CheckComboValue(reportstatus))
                search += ToolUtils.GetAndSearch(search) + " a.reportstatus = '" + reportstatus + "' ";

            search += ToolUtils.GetAndSearch(search) + " a.reportstatus in ('08','12')";

            this.SetSearch(this.getBreport().getSearch(), this.getBreport().getItem(), ou, search);

            List<BusReport> lists = DatDao.SearchBusReport(this.getBreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("试验报告-打印", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Deal, OutType = ActionOutType.Save)
    public String PrintBusReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            String signerpassword = ServletActionContext.getRequest().getParameter("signerpassword");

            DatService.PrintBusReport(this.getBreport(), signerpassword, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BusReport Methods

}
