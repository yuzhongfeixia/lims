package com.alms.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alms.dao.IncDao;
import com.alms.entity.inc.IncCheckSafe;
import com.alms.entity.inc.IncTestEnv;
import com.alms.service.FlowFmtService;
import com.alms.service.IncService;
import com.gpersist.action.BaseAction;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ActionOutType;
import com.gpersist.enums.MenuAuth;
import com.gpersist.utils.AuthMethod;
import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class IncAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region IncTestEnv Methods

    private IncTestEnv ite;

    public IncTestEnv getIte() {
        if (ite == null)
            ite = new IncTestEnv();

        return ite;
    }

    public void setIte(IncTestEnv ite) {
        this.ite = ite;
    }

    @AuthMethod(Menus = "1411", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncTestEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncTestEnv rtn = new IncTestEnv();
        if (ou != null) {
            rtn = IncDao.GetIncTestEnv(this.getIte());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "1411", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncTestEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getIte().getTranid());
            String parametername = ToolUtils.Decode(this.getIte().getParametername()).trim();

            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            if (!ToolUtils.StringIsEmpty(parametername))
                search += ToolUtils.GetAndSearch(search) + " a.parametername like '%" + parametername + "%' ";

            this.SetSearch(this.getIte().getSearch(), this.getIte().getItem(), ou, search);

            List<IncTestEnv> lists = IncDao.SearchIncTestEnv(this.getIte());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getIte().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting(" 0712 - 检查 - 特定检测环境一览", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1411", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncTestEnv() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1411");
            IncService.SaveIncTestEnv(this.getIte(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion IncTestEnv Methods

    // region IncCheckSafe Methods

    private IncCheckSafe checksafe;

    public IncCheckSafe getChecksafe() {
        if (checksafe == null)
            checksafe = new IncCheckSafe();

        return checksafe;
    }

    public void setChecksafe(IncCheckSafe checksafe) {
        this.checksafe = checksafe;
    }

    @AuthMethod(Menus = "1401", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetIncCheckSafe() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        IncCheckSafe rtn = new IncCheckSafe();
        if (ou != null) {
            rtn = IncDao.GetIncCheckSafe(this.getChecksafe());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "1401", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchIncCheckSafe() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";

            String tranid = ToolUtils.Decode(this.getChecksafe().getTranid());
            if (!ToolUtils.StringIsEmpty(tranid))
                search += ToolUtils.GetAndSearch(search) + " a.tranid like '%" + tranid + "%' ";

            this.SetSearch(this.getChecksafe().getSearch(), this.getChecksafe().getItem(), ou, search);

            List<IncCheckSafe> lists = IncDao.SearchIncCheckSafe(this.getChecksafe());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getChecksafe().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("0701-检查-安全检查记录", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "1401", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveIncCheckSafe() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("1401");
            IncService.SaveIncCheckSafe(this.getChecksafe(), this.getRtv(), ou, log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // 安全检查html
    public String HtmlCheckSafe() throws Exception {

        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String tranid = ServletActionContext.getRequest().getParameter("tranid");
            IncCheckSafe item = new IncCheckSafe();
            item.setTranid(tranid);
            item = IncDao.GetIncCheckSafe(item);
            StringBuilder sb = new StringBuilder();
            if (item != null) {
                sb.append(FlowFmtService.GetInfoHtmlHeader());
                sb.append(FlowFmtService.GetHtmlCheckSafe(item));
                sb.append(FlowFmtService.GetInfoHtmlBottom());
            }
            ToolUtils.OutString(sb.toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());
        return null;
    }

    // endregion IncCheckSafe Methods

}
