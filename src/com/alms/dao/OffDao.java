package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlOffDao;
import com.alms.entity.office.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class OffDao {

    // region OfficeApplyDetail Methods

    public static List<OfficeApplyDetail> GetListOfficeApplyDetail(OfficeApplyDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<OfficeApplyDetail>();
            default:
                return SqlOffDao.GetListOfficeApplyDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<OfficeApplyDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveOfficeApplyDetail(SqlSession session, OfficeApplyDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlOffDao.SaveOfficeApplyDetail(session, item);
            break;
        }
    }

    // endregion OfficeApplyDetail Methods

    // region OfficeApply Methods

    public static OfficeApply GetOfficeApply(OfficeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetOfficeApply(session, item);
        } catch (Exception e) {
            return new OfficeApply();
        } finally {
            session.close();
        }
    }

    public static OfficeApply GetOfficeApply(SqlSession session, OfficeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new OfficeApply();
        default:
            return SqlOffDao.GetOfficeApply(session, item);
        }
    }

    public static List<OfficeApply> SearchOfficeApply(OfficeApply item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<OfficeApply>();
            default:
                return SqlOffDao.SearchOfficeApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<OfficeApply>();
        } finally {
            session.close();
        }
    }

    public static void SaveOfficeApply(SqlSession session, OfficeApply item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlOffDao.SaveOfficeApply(session, item);
            break;
        }
    }

    // endregion OfficeApply Methods

}
