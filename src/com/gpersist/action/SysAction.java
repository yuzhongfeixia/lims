package com.gpersist.action;

import java.io.ByteArrayInputStream;
import java.util.List;
import org.apache.struts2.ServletActionContext;

import com.gpersist.dao.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.publics.*;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.enums.*;
import com.gpersist.service.*;
import com.gpersist.utils.*;

public class SysAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    // region Pmt Methods

    public String ListSelects() throws Exception {

        List<String> pmts = ToolUtils.GetArrayFromJson(this.selects, String.class);

        ToolUtils.JsonOutString(ToolUtils.GetJsonFromArray(SystemService.GetPmtSelects(pmts)));

        return null;
    }

    public String ListPmt() throws Exception {

        String pmttype = ServletActionContext.getRequest().getParameter("pmttype");

        List<SysPmt> lists = SystemDao.GetListPmt(pmttype);

        ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
        return null;
    }

    public String ListPmtTable() throws Exception {
        String pmtid = ServletActionContext.getRequest().getParameter("pmtid");
        String pmtcode = ServletActionContext.getRequest().getParameter("pmtcode");

        switch (pmtcode.toUpperCase()) {
        case "P_GET_IPMT":
            List<IntPmt> ipmts = SystemDao.IntPmtByTable(Short.parseShort(pmtid));
            ToolUtils.OutString(this.OutLists(ipmts, ipmts.size(), false));
            break;

        case "P_GET_SPMT":
            List<ShortPmt> spmts = SystemDao.ShortPmtByTable(Short.parseShort(pmtid));
            ToolUtils.OutString(this.OutLists(spmts, spmts.size(), false));
            break;

        case "P_GET_TPMT":
            List<StringPmt> tpmts = SystemDao.StringPmtByTable(Short.parseShort(pmtid));
            ToolUtils.OutString(this.OutLists(tpmts, tpmts.size(), false));
            break;

        default:
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
            break;
        }

        return null;
    }

    public String ExcelPmtTable() throws Exception {
        try {

            String pmtid = ServletActionContext.getRequest().getParameter("pmtid");
            SysPmt pmt = SystemDao.GetPmt(Short.parseShort(pmtid));
            filename = ToolUtils.Encode(pmt.getPmtname() + ".xls");

            switch (pmt.getPmtcode().toUpperCase()) {
            case "P_GET_IPMT":
                List<IntPmt> ipmts = SystemDao.IntPmtByTable(Short.parseShort(pmtid));
                is = ExcelUtils.GetPmtExeclStream(pmt, ipmts);
                break;

            case "P_GET_SPMT":
                List<ShortPmt> spmts = SystemDao.ShortPmtByTable(Short.parseShort(pmtid));
                is = ExcelUtils.GetPmtExeclStream(pmt, spmts);
                break;

            case "P_GET_TPMT":
                List<StringPmt> tpmts = SystemDao.StringPmtByTable(Short.parseShort(pmtid));
                is = ExcelUtils.GetPmtExeclStream(pmt, tpmts);
                break;

            default:
                ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
                break;
            }

            return Consts.DEFAULT_EXCEL_RETURN;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public String SqlPmtTable() throws Exception {
        try {

            String pmtid = ServletActionContext.getRequest().getParameter("pmtid");
            SysPmt pmt = SystemDao.GetPmt(Short.parseShort(pmtid));
            filename = ToolUtils.Encode(pmt.getPmttable().toLowerCase() + ".sql");

            String sql = "delete from " + pmt.getPmttable().toLowerCase() + ";" + ToolUtils.WriteEnter();

            switch (pmt.getPmtcode().toUpperCase()) {
            case "P_GET_IPMT":
                List<IntPmt> ipmts = SystemDao.IntPmtByTable(Short.parseShort(pmtid));
                for (IntPmt intPmt : ipmts) {
                    sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ("
                            + String.valueOf(intPmt.getId()) + ", '" + intPmt.getName() + "');"
                            + ToolUtils.WriteEnter();
                }
                break;

            case "P_GET_SPMT":
                List<ShortPmt> spmts = SystemDao.ShortPmtByTable(Short.parseShort(pmtid));
                for (ShortPmt shortPmt : spmts) {
                    sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ("
                            + String.valueOf(shortPmt.getId()) + ", '" + shortPmt.getName() + "');"
                            + ToolUtils.WriteEnter();
                }
                break;

            case "P_GET_TPMT":
                List<StringPmt> tpmts = SystemDao.StringPmtByTable(Short.parseShort(pmtid));
                for (StringPmt stringPmt : tpmts) {
                    sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ('" + stringPmt.getId() + "', '"
                            + stringPmt.getName() + "');" + ToolUtils.WriteEnter();
                }
                break;

            default:
                break;
            }

            is = new ByteArrayInputStream(sql.getBytes("GBK"));
            return Consts.DEFAULT_SQL_RETURN;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public String SqlAllPmtTable() throws Exception {
        try {
            String pmttype = ServletActionContext.getRequest().getParameter("pmttype");

            List<SysPmt> lists = SystemDao.GetListPmt(pmttype);

            filename = ToolUtils.Encode(pmttype.toLowerCase() + "_pmt.sql");
            String sql = "";

            for (SysPmt pmt : lists) {
                sql += "delete from " + pmt.getPmttable().toLowerCase() + ";" + ToolUtils.WriteEnter();

                switch (pmt.getPmtcode().toUpperCase()) {
                case "P_GET_IPMT":
                    List<IntPmt> ipmts = SystemDao.IntPmtByTable(pmt.getPmtid());
                    for (IntPmt intPmt : ipmts) {
                        sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ("
                                + String.valueOf(intPmt.getId()) + ", '" + intPmt.getName() + "');"
                                + ToolUtils.WriteEnter();
                    }
                    break;

                case "P_GET_SPMT":
                    List<ShortPmt> spmts = SystemDao.ShortPmtByTable(pmt.getPmtid());
                    for (ShortPmt shortPmt : spmts) {
                        sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ("
                                + String.valueOf(shortPmt.getId()) + ", '" + shortPmt.getName() + "');"
                                + ToolUtils.WriteEnter();
                    }
                    break;

                case "P_GET_TPMT":
                    List<StringPmt> tpmts = SystemDao.StringPmtByTable(pmt.getPmtid());
                    for (StringPmt stringPmt : tpmts) {
                        sql += "insert into " + pmt.getPmttable().toLowerCase() + " values ('" + stringPmt.getId()
                                + "', '" + stringPmt.getName() + "');" + ToolUtils.WriteEnter();
                    }
                    break;

                default:
                    break;
                }

                sql += ToolUtils.WriteEnter();
            }

            is = new ByteArrayInputStream(sql.getBytes("GBK"));
            return Consts.DEFAULT_SQL_RETURN;
        } catch (Exception e) {
        }

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_SYS_PMT, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SavePmt() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String pmttable = ServletActionContext.getRequest().getParameter("pmttable");
            String pmtcode = ServletActionContext.getRequest().getParameter("pmtcode");

            SystemService.SavePmt(pmttable, pmtcode, this.getListdata(), this.getRtv());

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion Pmt Methods

    // region Column Methods

    public String SqlColumn() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        String sqlid = ServletActionContext.getRequest().getParameter("sqlid");

        if (ou != null) {
            List<JsonSqlColumn> lists = SystemDao.SqlColumn(sqlid);

            ToolUtils.OutString(this.OutLists(lists, false));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion Column Methods

    // region Log Methods

    private TranLog tranlog;

    public TranLog getTranlog() {
        if (tranlog == null)
            tranlog = new TranLog();

        return tranlog;
    }

    public void setTranlog(TranLog tranlog) {
        this.tranlog = tranlog;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_LOG_TRAN, Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchTranLog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getTranlog().setSearch(GetSearchParams(this.getReq().GetDateBetween("a.trandate")));
            List<TranLog> lists = SystemDao.SearchTranLog(this.getTranlog());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getTranlog().getSearch().getTotal()));
            } else {
                ExportSetting es = this.GetExportSetting("操作日志", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    private LoginLog loginlog;

    public LoginLog getLoginlog() {
        if (loginlog == null)
            loginlog = new LoginLog();

        return loginlog;
    }

    public void setLoginlog(LoginLog loginlog) {
        this.loginlog = loginlog;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_LOG_LOGIN, Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String SearchLoginLog() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            this.getLoginlog().setSearch(GetSearchParams(this.getReq().GetDateBetween("a.logindate")));
            this.getLoginlog().getSearch().setSearch(this.getReq().GetDateBetween("a.logindate"));
            List<LoginLog> lists = SystemDao.SearchLoginLog(this.getLoginlog());

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, this.getLoginlog().getSearch().getTotal()));
            } else {
                ExportSetting es = this.GetExportSetting("登录日志", true);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    // endregion Log Methods
}
