package com.gpersist.action;

import java.util.List;

import com.gpersist.dao.*;
import com.gpersist.webservice.entity.*;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.user.*;
import com.gpersist.entity.log.*;
import com.gpersist.enums.*;
import com.gpersist.service.*;
import com.gpersist.utils.*;

public class WsPubAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    // region WsApp Methods

    private WsApp wa;

    public WsApp getWa() {
        if (wa == null)
            wa = new WsApp();

        return wa;
    }

    public void setWa(WsApp wa) {
        this.wa = wa;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetWsApp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        WsApp rtn = new WsApp();
        if (ou != null) {
            rtn = WsPubDao.GetWsApp(this.getWa());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchWsApp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {

            this.SetSearch(this.getWa().getSearch(), this.getWa().getItem(), ou, "");

            List<WsApp> lists = WsPubDao.SearchWsApp(this.getWa());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getWa().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("接口服务商", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveWsApp() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            WsPubService.SaveWsApp(this.getWa(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion WsApp Methods

    // region WsVisit Methods

    private WsVisit wv;

    public WsVisit getWv() {
        if (wv == null)
            wv = new WsVisit();

        return wv;
    }

    public void setWv(WsVisit wv) {
        this.wv = wv;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetWsVisit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        WsVisit rtn = new WsVisit();
        if (ou != null) {
            rtn = WsPubDao.GetWsVisit(this.getWv());
        }
        ToolUtils.OutString(this.OutBean(rtn, true));

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchWsVisit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String search = "";
            String begindate = this.getWv().getSearch().getBegindate();
            String enddate = this.getWv().getSearch().getEnddate();

            if (!ToolUtils.StringIsEmpty(enddate))
                search += ToolUtils.GetAndSearch(search) + " a.visitdate <= " + ToolUtils.GetEndDate(enddate) + " ";

            if (!ToolUtils.StringIsEmpty(begindate))
                search += ToolUtils.GetAndSearch(search) + " a.visitdate >= " + ToolUtils.GetBeginDate(begindate) + " ";

            this.SetSearch(this.getWv().getSearch(), this.getWv().getItem(), ou, search);

            List<WsVisit> lists = WsPubDao.SearchWsVisit(this.getWv());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getWv().getSearch().getTotal(), true));
            } else {
                ExportSetting es = this.GetExportSetting("接口访问日志", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = "", Auth = MenuAuth.Modify, OutType = ActionOutType.Bean)
    public String SaveWsVisit() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog("");
            WsPubService.SaveWsVisit(this.getWv(), this.getRtv(), log);
            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion WsVisit Methods

}
