package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.IncFileDao;
import com.alms.entity.file.*;
import com.alms.enums.FileAction;
import com.alms.service.FlowFmtService;
import com.alms.service.IncFileService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class IncFileAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region IncFileDestroyRecord Methods

    private IncFileDestroyRecord ifdr;

    public IncFileDestroyRecord getIfdr() {
        if (ifdr == null)
            ifdr = new IncFileDestroyRecord();

        return ifdr;
    }

    public void setIfdr(IncFileDestroyRecord ifdr) {
        this.ifdr = ifdr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncFileDestroyRecord rtn = new IncFileDestroyRecord();
        if (ou != null) {
            rtn = IncFileDao.GetIncFileDestroyRecord(this.getIfdr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getIfdr().getFileid()).trim();
            String filename = ToolUtils.Decode(this.getIfdr().getFilename()).trim();

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            this.SetSearch(this.getIfdr().getSearch(), this.getIfdr().getItem(), ou, search);

            List<IncFileDestroyRecord> lists = IncFileDao.SearchIncFileDestroyRecord(this.getIfdr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfdr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 4001 -GLp 文件 文件销毁记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1723");
            IncFileService.SaveIncFileDestroyRecord(this.getIfdr(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion IncFileDestroyRecord Methods

    // region IncFilePassword Methods

    private IncFilePassword ifp;

    public IncFilePassword getIfp() {
        if (ifp == null)
            ifp = new IncFilePassword();

        return ifp;
    }

    public void setIfp(IncFilePassword ifp) {
        this.ifp = ifp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncFilePassword rtn = new IncFilePassword();
        if (ou != null) {
            rtn = IncFileDao.GetIncFilePassword(this.getIfp());
        }
        ToolUtils.OutString(this.OutBean(rtn, false));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<IncFilePassword> lists = IncFileDao.GetListIncFilePassword(this.getIfp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1002 文件密码管理", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getIfp().getSearch().setStart(start + 1);
            this.getIfp().getSearch().setEnd(this.GetEndCnt());

            List<IncFilePassword> lists = IncFileDao.SearchIncFilePassword(this.getIfp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfp().getSearch().getTotal(), false));
            } else {
                ExportSetting es = this.GetExportSetting(" 1002 文件密码管理", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1781");
            IncFileService.SaveIncFilePassword(this.getIfp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ChangeIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1783");
            IncFileService.ChangeIncFilePassword(this.getIfp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ResetIncFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1782");
            IncFileService.ResetIncFilePassword(this.getIfp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion IncFilePassword Methods

    // region IncFileOnline Methods

    private IncFileOnline ifo;

    public IncFileOnline getIfo() {
        if (ifo == null)
            ifo = new IncFileOnline();

        return ifo;
    }

    public void setIfo(IncFileOnline ifo) {
        this.ifo = ifo;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncFileOnline rtn = new IncFileOnline();
        if (ou != null) {
            rtn = IncFileDao.GetIncFileOnline(this.getIfo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String flowstatus = ToolUtils.Decode(this.getIfo().getFlowstatus());
            String filename = ToolUtils.Decode(this.getIfo().getFilename());

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " d.filename like '%" + filename + "%' ";

            this.SetSearch(this.getIfo().getSearch(), this.getIfo().getItem(), ou, search);

            List<IncFileOnline> lists = IncFileDao.SearchIncFileOnline(this.getIfo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfo().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1001 - 文件在线阅读", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1705");
            IncFileService.SaveIncFileOnline(this.getIfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String CheckIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            IncFileService.CheckIncFileOnline(this.getIfo(), password, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String UnCheckIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            IncFileService.UnCheckIncFileOnline(this.getIfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ApproveIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            IncFileService.ApproveIncFileOnline(this.getIfo(), password, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String DisapproveIncFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            IncFileService.DisapproveIncFileOnline(this.getIfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件在线阅读html
    public String HtmlIncFileOnline() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            IncFileOnline item = new IncFileOnline();
            item.setTranid(tranid);
            item = IncFileDao.GetIncFileOnline(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlIncFileOnline(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion IncFileOnline Methods

    // region IncFileRegister Methods

    private IncFileRegister ifr;

    public IncFileRegister getIfr() {
        if (ifr == null)
            ifr = new IncFileRegister();

        return ifr;
    }

    public void setIfr(IncFileRegister ifr) {
        this.ifr = ifr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncFileRegister rtn = new IncFileRegister();
        if (ou != null) {
            rtn = IncFileDao.GetIncFileRegister(this.getIfr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String filename = ToolUtils.Decode(this.getIfr().getFilename()).trim();
            String tranid = ToolUtils.Decode(this.getIfr().getTranid()).trim();
            String type = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("type"));
            String userid = ou.getUser().getUserid();
            if (!ToolUtils.StringIsEmpty(userid)) {
                if (!(ou.getDept().getDeptid().equals("9999"))) {
                    search += ToolUtils.GetAndSearch(search) + " a.userid = " + userid;
                }
            }

            if ("reading".equals(type)) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 0";
            } else if ("hasread".equals(type)) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 1";
            }

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getIfr().getSearch(), this.getIfr().getItem(), ou, search);

            List<IncFileRegister> lists = IncFileDao.SearchIncFileRegister(this.getIfr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0001 - 文件 - 文件借阅", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFileRegisterForExp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String filename = ToolUtils.Decode(this.getIfr().getFilename()).trim();
            String tranid = ToolUtils.Decode(this.getIfr().getTranid()).trim();
            String userid = ou.getUser().getUserid();
            if (!ToolUtils.StringIsEmpty(userid)) {
                if (!(ou.getDept().getDeptid().equals("9999"))) {
                    search += ToolUtils.GetAndSearch(search) + " a.userid = " + userid;
                }
            }
            if (this.getIfr().getIsreturn()) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 1";
            } else {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 0";
            }

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getIfr().getSearch(), this.getIfr().getItem(), ou, search);

            List<IncFileRegister> lists = IncFileDao.SearchIncFileRegister(this.getIfr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIfr().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0001 - 文件 - 文件借阅", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1703");
            IncFileService.SaveIncFileRegister(this.getIfr(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion IncFileRegister Methods

    // region BasFile Methods

    private BasFile bf;

    public BasFile getBf() {
        if (bf == null)
            bf = new BasFile();

        return bf;
    }

    public void setBf(BasFile bf) {
        this.bf = bf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String HasAuthToRead() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasFile rtn = new BasFile();
        if (ou != null) {
            rtn = IncFileDao.HasAuthToRead(this.getIfo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasFile rtn = new BasFile();
        if (ou != null) {
            rtn = IncFileDao.GetBasFile(this.getBf());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasFileForChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBf().getDeal().setAction(FileAction.Change.getAction());
            List<BasFile> lists = IncFileDao.GetBasFileForNeed(this.getBf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-基础-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasFileForDestory() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBf().getDeal().setAction(FileAction.Destory.getAction());
            List<BasFile> lists = IncFileDao.GetBasFileForNeed(this.getBf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-基础-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasFileForLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getBf().getDeal().setAction(FileAction.Loan.getAction());
            List<BasFile> lists = IncFileDao.GetBasFileForNeed(this.getBf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-基础-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasFile> lists = IncFileDao.GetListBasFile(this.getBf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-基础-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getBf().getFileid()).trim();
            String filename = ToolUtils.Decode(this.getBf().getFilename()).trim();
            String filetype = ToolUtils.Decode(this.getBf().getFiletype()).trim();

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            if (!ToolUtils.CheckComboValue(filetype))
                search += ToolUtils.GetAndSearch(search) + " a.filetype = '" + filetype + "' ";

            this.SetSearch(this.getBf().getSearch(), this.getBf().getItem(), ou, search);

            List<BasFile> lists = IncFileDao.SearchBasFile(this.getBf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBf().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1001-基础-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1701");
            IncFileService.SaveBasFile(this.getBf(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAllBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String[] filename = ServletActionContext.getRequest().getParameter("filename").split(",");
            String[] fileurl = ServletActionContext.getRequest().getParameter("fileurl").split(",");
            for (int i = 0; i < filename.length; i++) {
                filename[i] = ToolUtils.Decode(filename[i]);
                fileurl[i] = ToolUtils.Decode(fileurl[i]);
            }
            IncFileService.SaveAllBasFile(this.getBf(), filename, fileurl, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String DeleteBasFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<BasFile> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), BasFile.class);

            IncFileService.DeleteBasFile(deletes, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion BasFile Methods

    // region ChangeApply Methods

    private ChangeApply ca;

    public ChangeApply getCa() {
        if (ca == null)
            ca = new ChangeApply();

        return ca;
    }

    public void setCa(ChangeApply ca) {
        this.ca = ca;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetChangeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ChangeApply rtn = new ChangeApply();
        if (ou != null) {
            rtn = IncFileDao.GetChangeApply(this.getCa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListChangeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ChangeApply> lists = IncFileDao.GetListChangeApply(this.getCa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1002-文件-文件更改申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListChangeApplyForNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ChangeApply> lists = IncFileDao.GetListChangeApplyForNotify(this.getCa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1002-文件-文件更改申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchChangeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCa().getTranid()).trim();
            String changefilename = ToolUtils.Decode(this.getCa().getChangefilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(changefilename))
                search += ToolUtils.GetAndSearch(search) + " a.changefilename like '%" + changefilename + "%' ";

            this.SetSearch(this.getCa().getSearch(), this.getCa().getItem(), ou, search);

            List<ChangeApply> lists = IncFileDao.SearchChangeApply(this.getCa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1002-文件-文件更改申请", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveChangeApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1711");

            String changefileurl = ToolUtils.Decode(this.getCa().getFileurl());
            String replacefilename = ToolUtils.Decode(this.getCa().getReplacefilename());

            IncFileService.SaveChangeApply(this.getCa(), this.getRtv(), changefileurl, replacefilename, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件更改html
    public String HtmlFileChange() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            ChangeApply item = new ChangeApply();
            item.setTranid(tranid);
            item = IncFileDao.GetChangeApply(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlFileChange(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion ChangeApply Methods

    // region DestoryApplyDetail Methods

    private DestoryApplyDetail dadetail;

    public DestoryApplyDetail getDadetail() {
        if (dadetail == null)
            dadetail = new DestoryApplyDetail();

        return dadetail;
    }

    public void setDadetail(DestoryApplyDetail dadetail) {
        this.dadetail = dadetail;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDestoryApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DestoryApplyDetail rtn = new DestoryApplyDetail();
        if (ou != null) {
            rtn = IncFileDao.GetDestoryApplyDetail(this.getDadetail());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDestoryApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DestoryApplyDetail> lists = IncFileDao.GetListDestoryApplyDetail(this.getDadetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1007-文件-文件操作明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDestoryApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getDadetail().getSearch().setStart(start + 1);
            this.getDadetail().getSearch().setEnd(this.GetEndCnt());

            List<DestoryApplyDetail> lists = IncFileDao.SearchDestoryApplyDetail(this.getDadetail());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDadetail().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1007-文件-文件操作明细", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDestoryApplyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1721");
            IncFileService.SaveDestoryApplyDetail(this.getDadetail(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion DestoryApplyDetail Methods

    // region destoryapply Methods

    private DestoryApply da;

    public DestoryApply getDa() {
        if (da == null)
            da = new DestoryApply();

        return da;
    }

    public void setDa(DestoryApply da) {
        this.da = da;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetDestoryApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        DestoryApply rtn = new DestoryApply();
        if (ou != null) {
            rtn = IncFileDao.GetDestoryApply(this.getDa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDestoryApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<DestoryApply> lists = IncFileDao.GetListDestoryApply(this.getDa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1003-文件-文件销毁记录登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchDestoryApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getDa().getTranid()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getDa().getSearch(), this.getDa().getItem(), ou, search);

            List<DestoryApply> lists = IncFileDao.SearchDestoryApply(this.getDa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getDa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1003-文件-文件销毁记录登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncFileDestroyForRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getDa().getFileid());
            String filename = ToolUtils.Decode(this.getDa().getFilename());

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " e.filename like '%" + filename + "%' ";

            this.SetSearch(this.getDa().getSearch(), this.getDa().getItem(), ou, search);

            List<DestoryApply> lists = IncFileDao.SearchIncFileDestroyForRecord(this.getDa());

            ToolUtils.OutString(this.OutLists(lists, this.getDa().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveDestoryApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1721");
            IncFileService.SaveDestoryApply(this.getDa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件更改html
    public String HtmlFileDestory() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            DestoryApply item = new DestoryApply();
            item.setTranid(tranid);
            item = IncFileDao.GetDestoryApply(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlFileDestory(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion destoryapply Methods

    // region LoanApply Methods

    private LoanApply la;

    public LoanApply getLa() {
        if (la == null)
            la = new LoanApply();

        return la;
    }

    public void setLa(LoanApply la) {
        this.la = la;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetLoanApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        LoanApply rtn = new LoanApply();
        if (ou != null) {
            rtn = IncFileDao.GetLoanApply(this.getLa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListLoanApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<LoanApply> lists = IncFileDao.GetListLoanApply(this.getLa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1004-文件-文件借阅、复印登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchLoanApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getLa().getTranid()).trim();
            String filename = ToolUtils.Decode(this.getLa().getFilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getLa().getSearch(), this.getLa().getItem(), ou, search);

            List<LoanApply> lists = IncFileDao.SearchLoanApply(this.getLa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getLa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1004-文件-文件借阅、复印登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchLoanApplyForRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getLa().getFileid());
            String filename = ToolUtils.Decode(this.getLa().getFilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getLa().getSearch(), this.getLa().getItem(), ou, search);

            List<LoanApply> lists = IncFileDao.SearchLoanApplyForRegister(this.getLa());

            ToolUtils.OutString(this.OutLists(lists, this.getLa().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveLoanApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1731");
            IncFileService.SaveLoanApply(this.getLa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件借阅复印html
    public String HtmlFileLoan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            LoanApply item = new LoanApply();
            item.setTranid(tranid);
            item = IncFileDao.GetLoanApply(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlFileLoan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion LoanApply Methods

    // region SecretApply Methods

    private SecretApply sa;

    public SecretApply getSa() {
        if (sa == null)
            sa = new SecretApply();

        return sa;
    }

    public void setSa(SecretApply sa) {
        this.sa = sa;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetSecretApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SecretApply rtn = new SecretApply();
        if (ou != null) {
            rtn = IncFileDao.GetSecretApply(this.getSa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListSecretApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SecretApply> lists = IncFileDao.GetListSecretApply(this.getSa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1005-文件-机密信息查阅登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchSecretApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getSa().getTranid());
            // String changefilename =
            // ToolUtils.Decode(this.getCa().getChangefilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getSa().getSearch(), this.getSa().getItem(), ou, search);

            List<SecretApply> lists = IncFileDao.SearchSecretApply(this.getSa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getSa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1005-文件-机密信息查阅登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveSecretApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            IncFileService.SaveSecretApply(this.getSa(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String CommitSecretApply() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");

            List<SecretApply> commits = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("commits"), SecretApply.class);

            IncFileService.CommitSecretApply(commits, this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 机密信息查阅html
    public String HtmlFileSecret() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            SecretApply item = new SecretApply();
            item.setTranid(tranid);
            item = IncFileDao.GetSecretApply(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlFileSecret(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion SecretApply Methods

    // region BasLeak Methods

    private BasLeak bl;

    public BasLeak getBl() {
        if (bl == null)
            bl = new BasLeak();

        return bl;
    }

    public void setBl(BasLeak bl) {
        this.bl = bl;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetBasLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        BasLeak rtn = new BasLeak();
        if (ou != null) {
            rtn = IncFileDao.GetBasLeak(this.getBl());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListBasLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<BasLeak> lists = IncFileDao.GetListBasLeak(this.getBl());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1006-文件-泄密情况处置", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchBasLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getBl().getTranid()).trim();
            // String filename = ToolUtils.Decode(this.getBl().getFilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            // if (!ToolUtils.StringIsEmpty(filename))
            // search += ToolUtils.GetAndSearch(search) + " a.filename like '%"
            // + filename + "%' ";

            this.SetSearch(this.getBl().getSearch(), this.getBl().getItem(), ou, search);

            List<BasLeak> lists = IncFileDao.SearchBasLeak(this.getBl());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getBl().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1006-文件-泄密情况处置", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveBasLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1751");
            IncFileService.SaveBasLeak(this.getBl(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件泄密处置html
    public String HtmlFileLeak() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            BasLeak item = new BasLeak();
            item.setTranid(tranid);
            item = IncFileDao.GetBasLeak(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlFileLeak(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion BasLeak Methods

    // region ChangeNotify Methods

    private ChangeNotify cn;

    public ChangeNotify getCn() {
        if (cn == null)
            cn = new ChangeNotify();

        return cn;
    }

    public void setCn(ChangeNotify cn) {
        this.cn = cn;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetChangeNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ChangeNotify rtn = new ChangeNotify();
        if (ou != null) {
            rtn = IncFileDao.GetChangeNotify(this.getCn());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListChangeNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ChangeNotify> lists = IncFileDao.GetListChangeNotify(this.getCn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1023-文件-文件更改通知登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchChangeNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getCn().getTranid()).trim();
            String filename = ToolUtils.Decode(this.getCn().getFilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getCn().getSearch(), this.getCn().getItem(), ou, search);

            List<ChangeNotify> lists = IncFileDao.SearchChangeNotify(this.getCn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getCn().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1023-文件-文件更改通知登记", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveChangeNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1714");
            IncFileService.SaveChangeNotify(this.getCn(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion ChangeNotify Methods

    // region ReleFile Methods

    private ReleFile rf;

    public ReleFile getRf() {
        if (rf == null)
            rf = new ReleFile();

        return rf;
    }

    public void setRf(ReleFile rf) {
        this.rf = rf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetReleFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        ReleFile rtn = new ReleFile();
        if (ou != null) {
            rtn = IncFileDao.GetReleFile(this.getRf());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListReleFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<ReleFile> lists = IncFileDao.GetListReleFile(this.getRf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("1021-文件-受控文件(发放回收)一览", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchReleFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getRf().getFileid());
            String filename = ToolUtils.Decode(this.getRf().getFilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getRf().getSearch(), this.getRf().getItem(), ou, search);

            List<ReleFile> lists = IncFileDao.SearchReleFile(this.getRf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getRf().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("文件-受控文件(发放回收)一览", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveReleFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1760");
            IncFileService.SaveReleFile(this.getRf(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion ReleFile Methods

}
