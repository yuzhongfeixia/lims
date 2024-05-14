package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.StaffDao;
import com.alms.entity.staff.*;
import com.alms.service.FlowFmtService;
import com.alms.service.StaffService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class StaffAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region UserExamFile Methods

    private UserExamFile uxf;

    public UserExamFile getUxf() {
        if (uxf == null)
            uxf = new UserExamFile();

        return uxf;
    }

    public void setUxf(UserExamFile uxf) {
        this.uxf = uxf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamFile> lists = StaffDao.GetListUserExamFile(this.getUxf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 022 - 人员 -上岗资格考核报告附件", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserExamFile Methods

    // region BasUserFile Methods

    private BasUserFile buf;

    public BasUserFile getBuf() {
        if (buf == null)
            buf = new BasUserFile();

        return buf;
    }

    public void setBuf(BasUserFile buf) {
        this.buf = buf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasUserFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasUserFile> lists = StaffDao.GetListBasUserFile(this.getBuf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0122 - 人员- 人员档案", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion BasUserFile Methods

    // region BasUser Methods

    private BasUser bu;

    public BasUser getBu() {
        if (bu == null)
            bu = new BasUser();

        return bu;
    }

    public void setBu(BasUser bu) {
        this.bu = bu;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasUser rtn = new BasUser();
        if (ou != null) {
            rtn = StaffDao.GetBasUser(this.getBu());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasUser> lists = StaffDao.GetListBasUser(this.getBu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0121-基础-人员基础", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";

            String tranid = ToolUtils.Decode(this.getBu().getTranid());
            String username = ToolUtils.Decode(this.getBu().getUsername());
            String deptpid = ou.getDept().getDeptpid();
            String deptid = ou.getUser().getDeptid();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " a.username like '%" + username + "%' ";

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + " a.deptid = '" + deptid + "' ";
                }
            }

            this.SetSearch(this.getBu().getSearch(), this.getBu().getItem(), ou, search);
            List<BasUser> lists = StaffDao.SearchBasUser(this.getBu());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBu().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0121-基础-人员基础", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1201");
            List<BasUserFile> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), BasUserFile.class);
            StaffService.SaveBasUser(this.getBu(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasUser Methods

    // region UserExamResult Methods

    private UserExamResult examresult;

    public UserExamResult getExamresult() {
        if (examresult == null)
            examresult = new UserExamResult();

        return examresult;
    }

    public void setExamresult(UserExamResult examresult) {
        this.examresult = examresult;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserExamResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserExamResult rtn = new UserExamResult();
        if (ou != null) {
            rtn = StaffDao.GetUserExamResult(this.getExamresult());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamResult> lists = StaffDao.GetListUserExamResult(this.getExamresult());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("1240-人员-上岗资格考核结果", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserExamResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            String search = "";
            String examid = ToolUtils.Decode(this.getExamresult().getExamid()).trim();
            String username = ToolUtils.Decode(this.getExamresult().getUsername()).trim();

            if (!ToolUtils.StringIsEmpty(examid))
                search += ToolUtils.GetAndSearch(search) + " a.examid like '%" + examid + "%' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " b.username like '%" + username + "%' ";

            this.SetSearch(this.getExamresult().getSearch(), this.getExamresult().getItem(), ou, search);

            List<UserExamResult> lists = StaffDao.SearchUserExamResult(this.getExamresult());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getExamresult().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("人员-上岗资格考核结果", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserExamResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1240");
            StaffService.SaveUserExamResult(this.getExamresult(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 人员上岗考核结果html
    public String HtmlUserExamResult() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            UserExamResult item = new UserExamResult();
            item.setTranid(tranid);
            item = StaffDao.GetUserExamResult(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlUserExamResult(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserExamResultByUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserExamResult rtn = new UserExamResult();
        if (ou != null) {
            rtn = StaffDao.GetUserExamResultByUser(this.getExamresult());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    // endregion UserExamResult Methods

    // region UserExamDev Methods

    private UserExamDev examdev;

    public UserExamDev getExamdev() {
        if (examdev == null)
            examdev = new UserExamDev();

        return examdev;
    }

    public void setExamdev(UserExamDev examdev) {
        this.examdev = examdev;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamDev() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamDev> lists = StaffDao.GetListUserExamDev(this.getExamdev());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("人员-上岗资格考核使用设备", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserExamDev Methods

    // region UserExamGroup Methods

    private UserExamGroup examgroup;

    public UserExamGroup getExamgroup() {
        if (examgroup == null)
            examgroup = new UserExamGroup();

        return examgroup;
    }

    public void setExamgroup(UserExamGroup examgroup) {
        this.examgroup = examgroup;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamGroup() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamGroup> lists = StaffDao.GetListUserExamGroup(this.getExamgroup());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("人员-上岗资格考核考核小组成员", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserExamGroup Methods

    // region UserExamReport Methods

    private UserExamReport examreport;

    public UserExamReport getExamreport() {
        if (examreport == null)
            examreport = new UserExamReport();

        return examreport;
    }

    public void setexamreport(UserExamReport examreport) {
        this.examreport = examreport;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserExamReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserExamReport rtn = new UserExamReport();
        if (ou != null) {
            rtn = StaffDao.GetUserExamReport(this.getExamreport());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserExamReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String examid = ToolUtils.Decode(this.getExamreport().getExamid()).trim();
            String username = ToolUtils.Decode(this.getExamreport().getUsername()).trim();
            String deptid = ToolUtils.Decode(this.getExamreport().getDeptid());

            if (!ToolUtils.StringIsEmpty(examid))
                search += ToolUtils.GetAndSearch(search) + " a.examid like '%" + examid + "%' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " b.username like '%" + username + "%' ";

            if (!ToolUtils.CheckComboValue(deptid))
                search += ToolUtils.GetAndSearch(search) + " a.deptid like '%" + deptid + "%' ";

            this.SetSearch(this.getExamreport().getSearch(), this.getExamreport().getItem(), ou, search);

            List<UserExamReport> lists = StaffDao.SearchUserExamReport(this.getExamreport());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getExamreport().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("人员-上岗资格考核报告", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // 查询未做考核结果报告的 考核
    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserExamReportForResult() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String examid = ToolUtils.Decode(this.getExamreport().getExamid()).trim();
            String username = ToolUtils.Decode(this.getExamreport().getUsername()).trim();

            if (!ToolUtils.StringIsEmpty(examid))
                search += ToolUtils.GetAndSearch(search) + " a.examid like '%" + examid + "%' ";

            if (!ToolUtils.StringIsEmpty(username))
                search += ToolUtils.GetAndSearch(search) + " b.username like '%" + username + "%' ";

            this.SetSearch(this.getExamreport().getSearch(), this.getExamreport().getItem(), ou, search);

            List<UserExamReport> lists = StaffDao.SearchUserExamReportForResult(this.getExamreport());

            ToolUtils.OutString(this.OutLists(lists, this.getExamreport().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserExamReport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1231");
            List<UserExamGroup> groupdetails = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), UserExamGroup.class);
            List<UserExamDev> devdetails = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("devdetails"), UserExamDev.class);
            List<UserExamFile> filedetails = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("filedetails"), UserExamFile.class);
            StaffService.SaveUserExamReport(this.getExamreport(), groupdetails, devdetails, filedetails, this.getRtv(),
                    ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 人员上岗资格考核html
    public String HtmlUserExamReport() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            UserExamReport item = new UserExamReport();
            item.setExamid(tranid);
            item = StaffDao.GetUserExamReport(item);
            UserExamGroup group = new UserExamGroup();
            group.setExamid(tranid);
            List<UserExamGroup> groups = StaffDao.GetListUserExamGroup(group);
            UserExamDev dev = new UserExamDev();
            dev.setExamid(tranid);
            List<UserExamDev> devs = StaffDao.GetListUserExamDev(dev);
            UserExamFile file = new UserExamFile();
            file.setExamid(tranid);
            List<UserExamFile> files = StaffDao.GetListUserExamFile(file);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlUserExamReport(item));
                sb.append(FlowFmtService.GetHtmlUserExamGroup(groups));
                sb.append(FlowFmtService.GetHtmlUserExamDev(devs));
                sb.append(FlowFmtService.GetHtmlUserExamFile(files));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion UserExamReport Methods

    // region UserExamItem Methods

    private UserExamItem examditem;

    public UserExamItem getExamditem() {
        if (examditem == null)
            examditem = new UserExamItem();

        return examditem;
    }

    public void setExamditem(UserExamItem examditem) {
        this.examditem = examditem;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserExamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserExamItem rtn = new UserExamItem();
        if (ou != null) {
            rtn = StaffDao.GetUserExamItem(this.getExamditem());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamItem> lists = StaffDao.GetListUserExamItem();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0423-人员-考核情况项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserExamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getExamditem().getSearch().setStart(start + 1);
            this.getExamditem().getSearch().setEnd(this.GetEndCnt());

            List<UserExamItem> lists = StaffDao.SearchUserExamItem(this.getExamditem());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getExamditem().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0423-人员-考核情况项目", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserExamItem() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            StaffService.SaveUserExamItem(this.getExamditem(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion UserExamItem Methods

    // region UserExamDetail Methods

    private UserExamDetail examdetail;

    public UserExamDetail getExamdetail() {
        if (examdetail == null)
            examdetail = new UserExamDetail();

        return examdetail;
    }

    public void setExamdetail(UserExamDetail examdetail) {
        this.examdetail = examdetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamDetail> lists = StaffDao.GetListUserExamDetail(this.getExamdetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0422-人员-岗位考核记录明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserExamDetail Methods

    // region UserExamRecord Methods

    private UserExamRecord examrecord;

    public UserExamRecord getExamrecord() {
        if (examrecord == null)
            examrecord = new UserExamRecord();

        return examrecord;
    }

    public void setExamrecord(UserExamRecord examrecord) {
        this.examrecord = examrecord;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserExamRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserExamRecord rtn = new UserExamRecord();
        if (ou != null) {
            rtn = StaffDao.GetUserExamRecord(this.getExamrecord());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserExamRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserExamRecord> lists = StaffDao.GetListUserExamRecord();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0421-人员-岗位考核记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserExamRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String examid = ToolUtils.Decode(this.getExamrecord().getExamid());
            String userid = ToolUtils.Decode(this.getExamrecord().getUserid());

            if (!ToolUtils.StringIsEmpty(examid))
                search += ToolUtils.GetAndSearch(search) + " a.examid like '%" + examid + "%' ";

            if (!ToolUtils.StringIsEmpty(userid))
                search += ToolUtils.GetAndSearch(search) + " a.userid like '%" + userid + "%' ";

            this.SetSearch(this.getExamrecord().getSearch(), this.getExamrecord().getItem(), ou, search);

            List<UserExamRecord> lists = StaffDao.SearchUserExamRecord(this.getExamrecord());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getExamrecord().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("人员-岗位考核记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserExamRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1251");
            List<UserExamDetail> details = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"), UserExamDetail.class);
            StaffService.SaveUserExamRecord(this.getExamrecord(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 人员岗位考核html
    public String HtmlUserExamRecord() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            UserExamRecord item = new UserExamRecord();
            item.setExamid(tranid);
            item = StaffDao.GetUserExamRecord(item);
            UserExamDetail exam = new UserExamDetail();
            exam.setExamid(tranid);
            List<UserExamDetail> exams = StaffDao.GetListUserExamDetail(exam);

            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlUserExamRecord(item));
                sb.append(FlowFmtService.GetHtmlUserExamDetail(exams));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion UserExamRecord Methods

    // region UserTrainDetail Methods

    private UserTrainDetail traindetail;

    public UserTrainDetail getTraindetail() {
        if (traindetail == null)
            traindetail = new UserTrainDetail();

        return traindetail;
    }

    public void setTraindetail(UserTrainDetail traindetail) {
        this.traindetail = traindetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserTrainDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserTrainDetail> lists = StaffDao.GetListUserTrainDetail(this.getTraindetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("0402-人员-员工培训记录明细", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserTrainDetail Methods

    // region UserTrain Methods

    private UserTrain train;

    public UserTrain getTrain() {
        if (train == null)
            train = new UserTrain();

        return train;
    }

    public void setTrain(UserTrain train) {
        this.train = train;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserTrain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserTrain rtn = new UserTrain();
        if (ou != null) {
            rtn = StaffDao.GetUserTrain(this.getTrain());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserTrain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserTrain> lists = StaffDao.GetListUserTrain();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0401-人员-员工培训记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserTrainByUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserTrain> lists = StaffDao.GetListUserTrainByUser(this.getTrain());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0401-人员-员工培训记录", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserTrain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getTrain().getTranid()).trim();
            String trainobject = ToolUtils.Decode(this.getTrain().getTrainobject());
            String traincontent = ToolUtils.Decode(this.getTrain().getTraincontent()).trim();
            String traintype = ToolUtils.Decode(this.getTrain().getTraintype());

            this.getTrain().getSearch().setSearch(ou.getUser().getUserid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(trainobject))
                search += ToolUtils.GetAndSearch(search) + " a.trainobject like '%" + trainobject + "%' ";

            if (!ToolUtils.StringIsEmpty(traincontent))
                search += ToolUtils.GetAndSearch(search) + " a.traincontent like '%" + traincontent + "%' ";

            if (!ToolUtils.CheckComboValue(traintype))
                search += ToolUtils.GetAndSearch(search) + " a.traintype like '%" + traintype + "%' ";

            this.SetSearch(this.getTrain().getSearch(), this.getTrain().getItem(), ou, search);

            List<UserTrain> lists = StaffDao.SearchUserTrain(this.getTrain());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTrain().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0401-人员-员工培训记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserTrain() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1220");

            String details = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("details"));

            StaffService.SaveUserTrain(this.getTrain(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion UserTrain Methods

    // region RecordSummary Methods

    private RecordSummary rsummary;

    public RecordSummary getRsummary() {
        if (rsummary == null)
            train = new UserTrain();

        return rsummary;
    }

    public void setRsummary(RecordSummary rsummary) {
        this.rsummary = rsummary;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetRecordSummary() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        RecordSummary rtn = new RecordSummary();
        if (ou != null) {
            rtn = StaffDao.GetRecordSummary(this.getRsummary());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListRecordSummary() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<RecordSummary> lists = StaffDao.GetListRecordSummary();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0401-人员-员工培训总结", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchRecordSummary() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getRsummary().getTranid()).trim();
            String trainobject = ToolUtils.Decode(this.getRsummary().getTrainobject());
            String traincontent = ToolUtils.Decode(this.getRsummary().getTraincontent()).trim();

            this.getRsummary().getSearch().setSearch(ou.getUser().getUserid());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(trainobject))
                search += ToolUtils.GetAndSearch(search) + " a.trainobject like '%" + trainobject + "%' ";

            if (!ToolUtils.StringIsEmpty(traincontent))
                search += ToolUtils.GetAndSearch(search) + " a.traincontent like '%" + traincontent + "%' ";

            this.SetSearch(this.getRsummary().getSearch(), this.getRsummary().getItem(), ou, search);

            List<RecordSummary> lists = StaffDao.SearchRecordSummary(this.getRsummary());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRsummary().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0401-人员-员工培训记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveRecordSummary() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1220");

            String details = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("details"));

            StaffService.SaveRecordSummary(this.getRsummary(), details, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 年度总结 html
    public String HtmlRecordSummary() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            RecordSummary item = new RecordSummary();
            item.setTranid(tranid);
            item = StaffDao.GetRecordSummary(item);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlRecordSummary(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion RecordSummary Methods

    // region UserPlanYearDetail Methods

    private UserPlanYearDetail plandetail;

    public UserPlanYearDetail getPlandetail() {
        if (plandetail == null)
            plandetail = new UserPlanYearDetail();

        return plandetail;
    }

    public void setPlandetail(UserPlanYearDetail plandetail) {
        this.plandetail = plandetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserPlanYearDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserPlanYearDetail rtn = new UserPlanYearDetail();
        if (ou != null) {
            rtn = StaffDao.GetUserPlanYearDetail(this.getPlandetail());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListUserPlanYearDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<UserPlanYearDetail> lists = StaffDao.GetListUserPlanYearDetail(this.getPlandetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0407-人员-年度培训计划业务明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserPlanYearDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getPlandetail().getTranid()).trim();
            // String tranyear = ToolUtils.Decode(this.getPlandetail().getTranyear());
            String traincontent = ToolUtils.Decode(this.getPlandetail().getTraincontent()).trim();
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " c.tranid like '%" + tranid + "%' ";
            //
            // if (!ToolUtils.StringIsEmpty(tranyear))
            // search += ToolUtils.GetAndSearch(search) + " a.tranyear like '%" +
            // tranyear + "%' ";
            //
            if (!ToolUtils.StringIsEmpty(traincontent))
                search += ToolUtils.GetAndSearch(search) + " c.traincontent like '%" + traincontent + "%' ";
            //
            // search += ToolUtils.GetAndSearch(search) + " a.trainstatus = 01 ";

            this.getPlandetail().getSearch().setUserid(ou.getUser().getUserid());

            this.SetSearch(this.getPlandetail().getSearch(), this.getPlandetail().getItem(), ou, search);

            List<UserPlanYearDetail> lists = StaffDao.SearchUserPlanYearDetail(this.getPlandetail());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getPlandetail().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0407-人员-年度培训计划业务明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion UserPlanYearDetail Methods

    // region UserPlanYear Methods

    private UserPlanYear userplan;

    public UserPlanYear getUserplan() {
        if (userplan == null)
            userplan = new UserPlanYear();

        return userplan;
    }

    public void setUserplan(UserPlanYear userplan) {
        this.userplan = userplan;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetUserPlanYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        UserPlanYear rtn = new UserPlanYear();
        if (ou != null) {
            rtn = StaffDao.GetUserPlanYear(this.getUserplan());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserPlanYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getUserplan().getTranid());
            String tranyear = ToolUtils.Decode(this.getUserplan().getTranyear());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(tranyear))
                search += ToolUtils.GetAndSearch(search) + " a.tranyear like '%" + tranyear + "%' ";

            this.SetSearch(this.getUserplan().getSearch(), this.getUserplan().getItem(), ou, search);

            List<UserPlanYear> lists = StaffDao.SearchUserPlanYear(this.getUserplan());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getUserplan().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("人员-年度培训计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchUserPlanYearApproved() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String tranid = ToolUtils.Decode(this.getUserplan().getTranid());
            String tranyear = ToolUtils.Decode(this.getUserplan().getTranyear());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(tranyear))
                search += ToolUtils.GetAndSearch(search) + " a.tranyear like '%" + tranyear + "%' ";

            this.SetSearch(this.getUserplan().getSearch(), this.getUserplan().getItem(), ou, search);

            List<UserPlanYear> lists = StaffDao.SearchUserPlanYearApproved(this.getUserplan());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getUserplan().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("人员-年度培训计划", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveUserPlanYear() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1211");
            List<UserPlanYearDetail> details = ToolUtils.GetArrayFromJson(
                    ServletActionContext.getRequest().getParameter("details"), UserPlanYearDetail.class);
            StaffService.SaveUserPlanYear(this.getUserplan(), details, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 人员培训计划 申请html
    public String HtmlUserPlanYear() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            UserPlanYear item = new UserPlanYear();
            item.setTranid(tranid);
            item = StaffDao.GetUserPlanYear(item);
            UserPlanYearDetail detail = new UserPlanYearDetail();
            detail.setRelaid(tranid);
            List<UserPlanYearDetail> details = StaffDao.GetListUserPlanYearDetail(detail);
            StringBuilder sb = new StringBuilder();

            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlUserPlanYearDetail(details));
                sb.append(FlowFmtService.GetHtmlUserPlanYear(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion UserPlanYear Methods

}
