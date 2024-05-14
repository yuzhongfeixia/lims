package com.gpersist.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;

import com.gpersist.dao.*;
import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.ExportSetting;
import com.gpersist.entity.publics.PmtBean;
import com.gpersist.entity.system.SysMenuCode;
import com.gpersist.entity.user.*;
import com.gpersist.entity.log.*;
import com.gpersist.enums.*;
import com.gpersist.service.*;
import com.gpersist.utils.*;

public class OrgAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    // region Dept Methods

    private SysDept dept;

    public SysDept getDept() {
        if (dept == null)
            dept = new SysDept();

        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public String GetDept() throws Exception {

        SysDept item = new SysDept();

        try {
            String deptid = ServletActionContext.getRequest().getParameter("deptid");

            item = OrgDao.GetDept(deptid);
        } catch (Exception e) {

        }

        ToolUtils.OutString(this.OutBean(item));

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_DEPT, Auth = MenuAuth.Browse, OutType = ActionOutType.Array)
    public String SearchDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            String coid = ServletActionContext.getRequest().getParameter("coid");

            String search = "";

            if (!ToolUtils.StringIsEmpty(coid))
                search += ToolUtils.GetAndSearch(search) + " a.coid = '" + coid + "' ";

            this.getDept().getSearch().setSearch(search);
            this.getDept().getSearch().setStart(start + 1);
            this.getDept().getSearch().setEnd(this.GetEndCnt());
            this.getDept().getSearch().setUserid(ou.getUser().getUserid());

            List<SysDept> lists = OrgDao.SearchDept(this.getDept());

            ToolUtils.OutString(this.OutLists(lists, this.getDept().getSearch().getTotal(), true));
        } else {
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        }

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_DEPT, Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SysDept> lists = OrgDao.GetListDept();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("机构", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_DEPT, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_DEPT);

            OrgService.SaveDept(this.getDept(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    public String DeptTreeByCo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            try {
                String coid = ServletActionContext.getRequest().getParameter("coid");

                OrgService.DeptTreeByCo(this.getRtv(), coid);

                ToolUtils.JsonOutString(this.getRtv().getData().toString());
                return null;
            } catch (Exception e) {
            }
        }

        ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        return null;
    }

    public String DeptTreeByCoDept() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            try {
                String coid = ServletActionContext.getRequest().getParameter("coid");

                OrgService.DeptTreeByCoDept(this.getRtv(), coid);

                ToolUtils.JsonOutString(this.getRtv().getData().toString());
                return null;
            } catch (Exception e) {
            }
        }

        ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        return null;
    }

    public String DeptTree() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            try {
                OrgService.DeptTree(this.getRtv());

                ToolUtils.JsonOutString(this.getRtv().getData().toString());
                return null;
            } catch (Exception e) {
            }
        }

        ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);
        return null;
    }

    public String GetDeptByUser() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        List<PmtBean> rtn = new ArrayList<PmtBean>();
        if (ou != null) {
            rtn = OrgDao.GetDeptByUser(ou.getUser().getUserid());
        }

        ToolUtils.OutString(this.OutLists(rtn, false));

        return null;
    }

    public String GetSelectDeptByCo() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        List<PmtBean> rtn = new ArrayList<PmtBean>();
        if (ou != null) {
            String coid = ServletActionContext.getRequest().getParameter("coid");

            rtn = OrgService.GetSelectDeptByCo(coid);
        }

        ToolUtils.OutString(this.OutLists(rtn, false));

        return null;
    }

    // endregion Dept Methods

    // region Company Methods

    private SysCompany co;

    public SysCompany getCo() {
        if (co == null)
            co = new SysCompany();

        return co;
    }

    public void setCo(SysCompany co) {
        this.co = co;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_COMPANY, Auth = MenuAuth.Browse, OutType = ActionOutType.Bean)
    public String GetCompany() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        SysCompany item = new SysCompany();

        if (ou != null) {
            String coid = ServletActionContext.getRequest().getParameter("coid");

            item = OrgDao.GetCompany(coid);
        }

        ToolUtils.OutString(this.OutBean(item));
        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_COMPANY, Auth = MenuAuth.BrowseExport, OutType = ActionOutType.Array)
    public String GetListCompany() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            List<SysCompany> lists = OrgDao.GetListCompany();

            if (!hasexport) {
                ToolUtils.OutString(this.OutLists(lists, lists.size(), false));
            } else {
                ExportSetting es = this.GetExportSetting("公司", false);
                this.OutExport(lists, es);
                return Consts.DEFAULT_EXCEL_RETURN;
            }
        } else
            ToolUtils.OutString(Consts.DEFAULT_NULL_ARRAY);

        return null;
    }

    @AuthMethod(Menus = SysMenuCode.CODE_COMPANY, Auth = MenuAuth.Modify, OutType = ActionOutType.Save)
    public String SaveCompany() throws Exception {
        OnlineUser ou = ToolUtils.GetOnlineUser();

        if (ou != null) {
            TranLog log = ToolUtils.InitTranLog(SysMenuCode.CODE_COMPANY);

            OrgService.SaveCompany(this.getCo(), this.getRtv(), log);

            ToolUtils.OutString(this.getRtv().toString());
        } else
            ToolUtils.OutString(this.NotLoginRtv());

        return null;
    }

    // endregion Company Methods
}
