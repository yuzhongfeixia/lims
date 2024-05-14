package com.gpersist.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.*;
import com.gpersist.entity.*;
import com.gpersist.entity.log.*;
import com.gpersist.entity.org.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.*;

import com.gpersist.utils.*;

public class OrgService {

    // region Dept Methods

    public static void SaveDept(SysDept dept, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (dept.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            if (dept.getDeptpid().equals(Consts.SELECT_NULL_VALUE))
                dept.setDeptpid(dept.getDeptid());

            OrgDao.SaveDept(session, dept);
            log.setTranaction(TranAction.parse(dept.getDeal().getAction()).getTranaction());
            log.setTrandesc(dept.getDeptid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("保存成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            rtv.setMsg(ToolUtils.GetErrorMessage(e, "保存出错:"));
        } finally {
            session.close();
        }
    }

    public static void DeptTree(ReturnValue rtv) {
        List<SysDept> depts = OrgDao.GetListDept();

        List<TreeItem> lists = new ArrayList<TreeItem>();

        TreeItem root = new TreeItem();
        root.setText("所有机构");
        root.setId("0000");
        root.setLeaf(false);
        root.setExpanded(true);
        lists.add(root);

        if (depts.size() > 0) {
            for (SysDept dept : depts) {
                TreeItem ti = new TreeItem();
                ti.setText(dept.getDeptname());
                ti.setId(dept.getDeptid());

                if (dept.getDeptid().equals(dept.getDeptpid())) {
                    lists.add(ti);
                } else
                    GetSubDept(lists, dept);
            }
        }

        for (TreeItem item : lists) {
            item.setExpanded(true);
        }

        rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(lists), true);
    }

    public static boolean GetSubDept(List<TreeItem> items, SysDept dept) {
        for (TreeItem item : items) {
            if (item.getId().equals(dept.getDeptpid())) {
                TreeItem ti = new TreeItem();
                ti.setText(dept.getDeptname());
                ti.setId(dept.getDeptid());
                item.getChildren().add(ti);
                item.setLeaf(false);
                return true;
            }

            if (item.getChildren().size() > 0)
                GetSubDept(item.getChildren(), dept);
        }

        return false;
    }

    public static void DeptTreeByCo(ReturnValue rtv, String coid) {
        List<SysDept> depts = OrgDao.GetListDeptByCo(coid);

        List<TreeItem> lists = new ArrayList<TreeItem>();

        TreeItem root = new TreeItem();
        root.setText("所有机构");
        root.setId("0000");
        root.setLeaf(false);
        root.setExpanded(true);
        lists.add(root);

        if (depts.size() > 0) {
            for (SysDept dept : depts) {
                TreeItem ti = new TreeItem();
                ti.setText(dept.getDeptname());
                ti.setId(dept.getDeptid());

                if (dept.getDeptid().equals(dept.getDeptpid())) {
                    root.getChildren().add(ti);
                } else
                    GetSubDept(root.getChildren(), dept);
            }
        }

        for (TreeItem item : lists) {
            item.setExpanded(true);
        }

        rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(lists), true);
    }

    public static void DeptTreeByCoDept(ReturnValue rtv, String coid) {
        List<SysDept> depts = OrgDao.GetListDeptByCo(coid);

        List<TreeItem> lists = new ArrayList<TreeItem>();

        if (depts.size() > 0) {
            for (SysDept dept : depts) {
                TreeItem ti = new TreeItem();
                ti.setText(dept.getDeptname());
                ti.setId(dept.getDeptid());

                if (dept.getDeptid().equals(dept.getDeptpid())) {
                    lists.add(ti);
                } else
                    GetSubDept(lists, dept);
            }
        }

        for (TreeItem item : lists) {
            item.setExpanded(true);
        }

        rtv.SetValues(true, "", ToolUtils.GetJsonFromArray(lists), true);
    }

    public static List<PmtBean> GetSelectDeptByCo(String coid) {
        List<SysDept> depts = OrgDao.GetListDeptByCo(coid);

        List<PmtBean> rtn = new ArrayList<PmtBean>();

        for (SysDept dept : depts) {
            PmtBean item = new PmtBean();

            item.setId(dept.getDeptid());
            item.setName(dept.getDeptname());

            rtn.add(item);
        }

        return rtn;
    }

    // endregion Dept Methods

    // region Company Methods

    public static void SaveCompany(SysCompany company, ReturnValue rtv, TranLog log) {
        rtv.setSuccess(false);

        SqlSession session = DBUtils.getFactory();

        try {
            ErrorMsg errmsg = new ErrorMsg();

            if (company.OnBeforeSave(errmsg)) {
                rtv.setMsg(errmsg.getErrmsg());
                return;
            }

            OrgDao.SaveCompany(session, company);
            log.setTranaction(TranAction.parse(company.getDeal().getAction()).getTranaction());
            log.setTrandesc(company.getCoid());
            PublicDao.SaveTranLog(session, log);

            rtv.setSuccess(true);
            rtv.setMsg("保存成功!");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            // rtv.setMsg(ToolUtils.GetErrorMessage(e, "保存出错:"));
            rtv.setMsg("保存出错:该公司编号已被使用");
        } finally {
            session.close();
        }
    }

    // endregion Company Methods
}
