package com.alms.action;

import java.util.List;
import org.apache.struts2.ServletActionContext;

import com.alms.dao.*;
import com.alms.entity.glp.*;
import com.alms.enums.FileAction;
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

public class GlpAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region GlpFileDestroyRecord Methods

    private GlpFileDestroyRecord gfdr;

    public GlpFileDestroyRecord getGfdr() {
        if (gfdr == null)
            gfdr = new GlpFileDestroyRecord();

        return gfdr;
    }

    public void setGfdr(GlpFileDestroyRecord gfdr) {
        this.gfdr = gfdr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileDestroyRecord rtn = new GlpFileDestroyRecord();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileDestroyRecord(this.getGfdr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getGfdr().getFileid()).trim();
            String filename = ToolUtils.Decode(this.getGfdr().getFilename()).trim();

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfdr().getSearch(), this.getGfdr().getItem(), ou, search);

            List<GlpFileDestroyRecord> lists = GlpDao.SearchGlpFileDestroyRecord(this.getGfdr());
            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfdr().getSearch().getTotal(), true));
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
    public String SaveGlpFileDestroyRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2323");
            GlpService.SaveGlpFileDestroyRecord(this.getGfdr(), ou, this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFileDestroyRecord Methods

    // region GlpFileRegister Methods

    private GlpFileRegister gfre;

    public GlpFileRegister getGfre() {
        if (gfre == null)
            gfre = new GlpFileRegister();

        return gfre;
    }

    public void setGfre(GlpFileRegister gfre) {
        this.gfre = gfre;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileRegister rtn = new GlpFileRegister();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileRegister(this.getGfre());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String filename = ToolUtils.Decode(this.getGfre().getFilename());
            String userid = ToolUtils.Decode(this.getGfre().getUserid());
            String type = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("type"));

            if ("reading".equals(type)) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 0";
            } else if ("hasread".equals(type)) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 1";
            }

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            if (!ToolUtils.StringIsEmpty(userid))
                search += ToolUtils.GetAndSearch(search) + " a.userid = '" + userid + "' ";

            this.SetSearch(this.getGfre().getSearch(), this.getGfre().getItem(), ou, search);

            List<GlpFileRegister> lists = GlpDao.SearchGlpFileRegister(this.getGfre());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfre().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0001 - GLP文件 - 文件借阅", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpFileRegisterForExport() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String filename = ToolUtils.Decode(this.getGfre().getFilename());
            String userid = ToolUtils.Decode(this.getGfre().getUserid());
            // String type =
            // ToolUtils.Decode(ServletActionContext.getRequest().getParameter("type"));

            // if("reading".equals(type)){
            // search += ToolUtils.GetAndSearch(search) + " a.isreturn = 0";
            // }else if("hasread".equals(type)){
            // search += ToolUtils.GetAndSearch(search) + " a.isreturn = 1";
            // }
            if (this.getGfre().getIsreturn()) {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 1";
            } else {
                search += ToolUtils.GetAndSearch(search) + " a.isreturn = 0";
            }

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " b.filename like '%" + filename + "%' ";

            if (!ToolUtils.StringIsEmpty(userid))
                search += ToolUtils.GetAndSearch(search) + " a.userid = '" + userid + "' ";

            this.SetSearch(this.getGfre().getSearch(), this.getGfre().getItem(), ou, search);

            List<GlpFileRegister> lists = GlpDao.SearchGlpFileRegister(this.getGfre());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfre().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0001 - GLP文件 - 文件借阅", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveGlpFileRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2303");
            GlpService.SaveGlpFileRegister(this.getGfre(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFileRegister Methods

    // region GlpFilePassword Methods

    private GlpFilePassword gfp;

    public GlpFilePassword getGfp() {
        if (gfp == null)
            gfp = new GlpFilePassword();

        return gfp;
    }

    public void setGfp(GlpFilePassword gfp) {
        this.gfp = gfp;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFilePassword rtn = new GlpFilePassword();
        if (ou != null) {
            rtn = GlpDao.GetGlpFilePassword(this.getGfp());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFilePassword> lists = GlpDao.GetListGlpFilePassword(this.getGfp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1002 文件密码管理", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getGfp().getSearch().setStart(start + 1);
            this.getGfp().getSearch().setEnd(this.GetEndCnt());

            List<GlpFilePassword> lists = GlpDao.SearchGlpFilePassword(this.getGfp());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfp().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 1002 文件密码管理", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2381");
            GlpService.SaveGlpFilePassword(this.getGfp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ChangeGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2383");
            GlpService.ChangeGlpFilePassword(this.getGfp(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ResetGlpFilePassword() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2382");
            GlpService.ResetGlpFilePassword(this.getGfp(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFilePassword Methods

    // region GlpFileOnline Methods

    private GlpFileOnline gfo;

    public GlpFileOnline getGfo() {
        if (gfo == null)
            gfo = new GlpFileOnline();

        return gfo;
    }

    public void setGfo(GlpFileOnline gfo) {
        this.gfo = gfo;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String HasAuthToReadGlp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFile rtn = new GlpFile();
        if (ou != null) {
            rtn = GlpDao.HasAuthToReadGlp(this.getGfo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileOnline rtn = new GlpFileOnline();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileOnline(this.getGfo());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String flowstatus = ToolUtils.Decode(this.getGfo().getFlowstatus());
            String filename = ToolUtils.Decode(this.getGfo().getFilename());

            if (!ToolUtils.StringIsEmpty(flowstatus))
                search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '" + flowstatus + "' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " d.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfo().getSearch(), this.getGfo().getItem(), ou, search);

            List<GlpFileOnline> lists = GlpDao.SearchGlpFileOnline(this.getGfo());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfo().getSearch().getTotal(), true));
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
    public String SaveGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2305");
            GlpService.SaveGlpFileOnline(this.getGfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String CheckGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            GlpService.CheckGlpFileOnline(this.getGfo(), password, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String UnCheckGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            GlpService.UnCheckGlpFileOnline(this.getGfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String ApproveGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            String password = ServletActionContext.getRequest().getParameter("filepassword");
            GlpService.ApproveGlpFileOnline(this.getGfo(), password, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String DisapproveGlpFileOnline() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            GlpService.DisapproveGlpFileOnline(this.getGfo(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件在线阅读html
    public String HtmlGlpFileOnline() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            GlpFileOnline item = new GlpFileOnline();
            item.setTranid(tranid);
            item = GlpDao.GetGlpFileOnline(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlGlpFileOnline(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion GlpFileOnline Methods

    // region GlpFile Methods

    private GlpFile gf;

    public GlpFile getGf() {
        if (gf == null)
            gf = new GlpFile();

        return gf;
    }

    public void setGf(GlpFile gf) {
        this.gf = gf;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFile rtn = new GlpFile();
        if (ou != null) {
            rtn = GlpDao.GetGlpFile(this.getGf());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileForChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getGf().getDeal().setAction(FileAction.Change.getAction());
            List<GlpFile> lists = GlpDao.GetGlpFileForNeed(this.getGf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), true));
            } else {
                ExportSetting es = this.GetExportSetting("Glp-文件基础", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileForDestory() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getGf().getDeal().setAction(FileAction.Destory.getAction());
            List<GlpFile> lists = GlpDao.GetGlpFileForNeed(this.getGf());

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
    public String GetGlpFileForLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getGf().getDeal().setAction(FileAction.Loan.getAction());
            List<GlpFile> lists = GlpDao.GetGlpFileForNeed(this.getGf());

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
    public String GetListGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFile> lists = GlpDao.GetListGlpFile(this.getGf());

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
    public String SearchGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getGf().getFileid()).trim();
            String filename = ToolUtils.Decode(this.getGf().getFilename()).trim();
            String filetype = ToolUtils.Decode(this.getGf().getFiletype()).trim();

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            if (!ToolUtils.CheckComboValue(filetype))
                search += ToolUtils.GetAndSearch(search) + " a.filetype = '" + filetype + "' ";

            this.SetSearch(this.getGf().getSearch(), this.getGf().getItem(), ou, search);

            List<GlpFile> lists = GlpDao.SearchGlpFile(this.getGf());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGf().getSearch().getTotal(), true));
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
    public String SaveGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2301");
            GlpService.SaveGlpFile(this.getGf(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveAllGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2301");
            String[] filename = ServletActionContext.getRequest().getParameter("filename").split(",");
            String[] fileurl = ServletActionContext.getRequest().getParameter("fileurl").split(",");
            for (int i = 0; i < filename.length; i++) {
                filename[i] = ToolUtils.Decode(filename[i]);
                fileurl[i] = ToolUtils.Decode(fileurl[i]);
            }
            GlpService.SaveAllGlpFile(this.getGf(), filename, fileurl, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Delete, OutType = ActionOutType.Save)
    public String DeleteGlpFile() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2301");

            List<GlpFile> deletes = ToolUtils
                    .GetArrayFromJson(ServletActionContext.getRequest().getParameter("deletes"), GlpFile.class);

            GlpService.DeleteGlpFile(deletes, this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFile Methods

    // region GlpFileChange Methods

    private GlpFileChange gfc;

    public GlpFileChange getGfc() {
        if (gfc == null)
            gfc = new GlpFileChange();

        return gfc;
    }

    public void setGfc(GlpFileChange gfc) {
        this.gfc = gfc;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileChange rtn = new GlpFileChange();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileChange(this.getGfc());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileChange> lists = GlpDao.GetListGlpFileChange(this.getGfc());

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
    public String GetListGlpFileChangeForNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileChange> lists = GlpDao.GetListGlpFileChangeForNotify(this.getGfc());

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
    public String SearchGlpFileChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String changetranid = ToolUtils.Decode(this.getGfc().getTranid()).trim();
            String changefilename = ToolUtils.Decode(this.getGfc().getChangefilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(changetranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + changetranid + "%' ";

            if (!ToolUtils.StringIsEmpty(changefilename))
                search += ToolUtils.GetAndSearch(search) + " a.changefilename like '%" + changefilename + "%' ";

            this.SetSearch(this.getGfc().getSearch(), this.getGfc().getItem(), ou, search);

            List<GlpFileChange> lists = GlpDao.SearchGlpFileChange(this.getGfc());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfc().getSearch().getTotal(), true));
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
    public String SaveGlpFileChange() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2311");

            String changefileurl = ToolUtils.Decode(this.getGfc().getFileurl());
            String replacefilename = ToolUtils.Decode(this.getGfc().getReplacefilename());

            GlpService.SaveGlpFileChange(this.getGfc(), this.getRtv(), changefileurl, replacefilename, ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件更改html
    public String HtmlGlpFileChange() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            GlpFileChange item = new GlpFileChange();
            item.setTranid(tranid);
            item = GlpDao.GetGlpFileChange(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlGlpFileChange(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion GlpFileChange Methods

    // region GlpFileDestroyDetail Methods

    private GlpFileDestroyDetail gfdd;

    public GlpFileDestroyDetail getGfdd() {
        if (gfdd == null)
            gfdd = new GlpFileDestroyDetail();

        return gfdd;
    }

    public void setGfdd(GlpFileDestroyDetail gfdd) {
        this.gfdd = gfdd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileDestroyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileDestroyDetail rtn = new GlpFileDestroyDetail();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileDestroyDetail(this.getGfdd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileDestroyDetail() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileDestroyDetail> lists = GlpDao.GetListGlpFileDestroyDetail(this.getGfdd());

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

    // endregion GlpFileDestroyDetail Methods

    // region GlpFileDestroy Methods

    private GlpFileDestroy gfd;

    public GlpFileDestroy getGfd() {
        if (gfd == null)
            gfd = new GlpFileDestroy();

        return gfd;
    }

    public void setGfd(GlpFileDestroy gfd) {
        this.gfd = gfd;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileDestroy() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileDestroy rtn = new GlpFileDestroy();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileDestroy(this.getGfd());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileDestroy() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileDestroy> lists = GlpDao.GetListGlpFileDestroy(this.getGfd());

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
    public String SearchGlpFileDestroy() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getGfd().getTranid()).trim();
            String filename = ToolUtils.Decode(this.getGfd().getFilename()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " d.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfd().getSearch(), this.getGfd().getItem(), ou, search);

            List<GlpFileDestroy> lists = GlpDao.SearchGlpFileDestroy(this.getGfd());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfd().getSearch().getTotal(), true));
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
    public String SearchGlpFileDestroyForRecord() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getGfd().getFileid());
            String filename = ToolUtils.Decode(this.getGfd().getFilename()).trim();
            String tranid = ToolUtils.Decode(this.getGfd().getTranid()).trim();
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";
            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " e.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfd().getSearch(), this.getGfd().getItem(), ou, search);
            List<GlpFileDestroy> lists = GlpDao.SearchGlpFileDestroyForRecord(this.getGfd());

            ToolUtils.OutString(this.OutLists(lists, this.getGfd().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }
        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveGlpFileDestroy() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2321");
            // List<GlpFileDestroyDetail> details =
            // ToolUtils.GetArrayFromJson(ServletActionContext.getRequest().getParameter("details"),GlpFileDestroyDetail.class);
            GlpService.SaveGlpFileDestroy(this.getGfd(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件更改html
    public String HtmlGlpFileDestory() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            GlpFileDestroy item = new GlpFileDestroy();
            item.setTranid(tranid);
            item = GlpDao.GetGlpFileDestroy(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlGlpFileDestory(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }
    // endregion GlpFileDestroy Methods

    // region GlpFileLoan Methods

    private GlpFileLoan gfl;

    public GlpFileLoan getGfl() {
        if (gfl == null)
            gfl = new GlpFileLoan();

        return gfl;
    }

    public void setGfl(GlpFileLoan gfl) {
        this.gfl = gfl;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchGlpLoanForRegister() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getGfl().getFileid());
            String filename = ToolUtils.Decode(this.getGfl().getFilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfl().getSearch(), this.getGfl().getItem(), ou, search);

            List<GlpFileLoan> lists = GlpDao.SearchGlpLoanForRegister(this.getGfl());

            ToolUtils.OutString(this.OutLists(lists, this.getGfl().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileLoan rtn = new GlpFileLoan();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileLoan(this.getGfl());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileLoan> lists = GlpDao.GetListGlpFileLoan(this.getGfl());

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
    public String SearchGlpFileLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getGfl().getTranid()).trim();
            String filename = ToolUtils.Decode(this.getGfl().getFilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfl().getSearch(), this.getGfl().getItem(), ou, search);

            List<GlpFileLoan> lists = GlpDao.SearchGlpFileLoan(this.getGfl());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfl().getSearch().getTotal(), true));
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

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveGlpFileLoan() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2331");
            GlpService.SaveGlpFileLoan(this.getGfl(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件借阅复印html
    public String HtmlGlpFileLoan() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            GlpFileLoan item = new GlpFileLoan();
            item.setTranid(tranid);
            item = GlpDao.GetGlpFileLoan(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlGlpFileLoan(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion GlpFileLoan Methods

    // region GlpFileLeak Methods

    private GlpFileLeak gfk;

    public GlpFileLeak getGfk() {
        if (gfk == null)
            gfk = new GlpFileLeak();

        return gfk;
    }

    public void setBl(GlpFileLeak gfk) {
        this.gfk = gfk;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileLeak rtn = new GlpFileLeak();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileLeak(this.getGfk());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileLeak> lists = GlpDao.GetListGlpFileLeak(this.getGfk());

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
    public String SearchGlpFileLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getGfk().getTranid()).trim();
            // String filename = ToolUtils.Decode(this.getBl().getFilename());
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            // if (!ToolUtils.StringIsEmpty(filename))
            // search += ToolUtils.GetAndSearch(search) + " a.filename like '%"
            // + filename + "%' ";

            this.SetSearch(this.getGfk().getSearch(), this.getGfk().getItem(), ou, search);

            List<GlpFileLeak> lists = GlpDao.SearchGlpFileLeak(this.getGfk());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfk().getSearch().getTotal(), true));
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
    public String SaveGlpFileLeak() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2351");
            GlpService.SaveGlpFileLeak(this.getGfk(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 文件泄密处置html
    public String HtmlGlpFileLeak() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            GlpFileLeak item = new GlpFileLeak();
            item.setTranid(tranid);
            item = GlpDao.GetGlpFileLeak(item);

            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlGlpFileLeak(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion GlpFileLeak Methods

    // region GlpFileNotify Methods

    private GlpFileNotify gfn;

    public GlpFileNotify getGfn() {
        if (gfn == null)
            gfn = new GlpFileNotify();

        return gfn;
    }

    public void setGfn(GlpFileNotify gfn) {
        this.gfn = gfn;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileNotify rtn = new GlpFileNotify();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileNotify(this.getGfn());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileNotify> lists = GlpDao.GetListGlpFileNotify(this.getGfn());

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
    public String SearchGlpFileNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getGfn().getTranid()).trim();
            String filename = ToolUtils.Decode(this.getGfn().getFilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfn().getSearch(), this.getGfn().getItem(), ou, search);

            List<GlpFileNotify> lists = GlpDao.SearchGlpFileNotify(this.getGfn());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfn().getSearch().getTotal(), true));
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
    public String SaveGlpFileNotify() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2315");
            GlpService.SaveGlpFileNotify(this.getGfn(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFileNotify Methods

    // region GlpFileRele Methods

    private GlpFileRele gfr;

    public GlpFileRele getGfr() {
        if (gfr == null)
            gfr = new GlpFileRele();

        return gfr;
    }

    public void setGfr(GlpFileRele gfr) {
        this.gfr = gfr;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetGlpFileRele() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        GlpFileRele rtn = new GlpFileRele();
        if (ou != null) {
            rtn = GlpDao.GetGlpFileRele(this.getGfr());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListGlpFileRele() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<GlpFileRele> lists = GlpDao.GetListGlpFileRele(this.getGfr());

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
    public String SearchGlpFileRele() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String fileid = ToolUtils.Decode(this.getGfr().getFileid()).trim();
            String filename = ToolUtils.Decode(this.getGfr().getFilename()).trim();
            // String devstatus = ToolUtils.Decode(this.getBu().getDevstatus());

            if (!ToolUtils.StringIsEmpty(fileid))
                search += ToolUtils.GetAndSearch(search) + " a.fileid like '%" + fileid + "%' ";

            if (!ToolUtils.StringIsEmpty(filename))
                search += ToolUtils.GetAndSearch(search) + " a.filename like '%" + filename + "%' ";

            this.SetSearch(this.getGfr().getSearch(), this.getGfr().getItem(), ou, search);

            List<GlpFileRele> lists = GlpDao.SearchGlpFileRele(this.getGfr());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getGfr().getSearch().getTotal(), true));
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
    public String SaveGlpFileRele() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("2360");
            GlpService.SaveGlpFileRele(this.getGfr(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion GlpFileRele Methods

}
