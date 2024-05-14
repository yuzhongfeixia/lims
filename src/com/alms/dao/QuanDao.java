package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlQuanDao;
import com.alms.entity.quan.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class QuanDao {

    // region QuanMonitSamItem Methods

    public static QuanMonitSamItem GetQuanMonitSamItem(QuanMonitSamItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanMonitSamItem(session, item);
        } catch (Exception e) {
            return new QuanMonitSamItem();
        } finally {
            session.close();
        }
    }

    public static QuanMonitSamItem GetQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanMonitSamItem();
        default:
            return SqlQuanDao.GetQuanMonitSamItem(session, item);
        }
    }

    public static List<QuanMonitSamItem> GetListQuanMonitSamItem(QuanMonitSamItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListQuanMonitSamItem(session, item);
        } catch (Exception e) {
            return new ArrayList<QuanMonitSamItem>();
        } finally {
            session.close();
        }
    }

    public static List<QuanMonitSamItem> GetListQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<QuanMonitSamItem>();
        default:
            return SqlQuanDao.GetListQuanMonitSamItem(session, item);
        }
    }

    public static List<QuanMonitSamItem> SearchQuanMonitSamItem(QuanMonitSamItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitSamItem>();
            default:
                return SqlQuanDao.SearchQuanMonitSamItem(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitSamItem>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitSamItem(session, item);
            break;
        }
    }

    // endregion QuanMonitSamItem Methods

    // region QuanMonitBigItem Methods

    public static QuanMonitBigItem GetQuanMonitBigItem(QuanMonitBigItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanMonitBigItem(session, item);
        } catch (Exception e) {
            return new QuanMonitBigItem();
        } finally {
            session.close();
        }
    }

    public static QuanMonitBigItem GetQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanMonitBigItem();
        default:
            return SqlQuanDao.GetQuanMonitBigItem(session, item);
        }
    }

    public static List<QuanMonitBigItem> GetListQuanMonitBigItem(QuanMonitBigItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListQuanMonitBigItem(session, item);
        } catch (Exception e) {
            return new ArrayList<QuanMonitBigItem>();
        } finally {
            session.close();
        }
    }

    public static List<QuanMonitBigItem> GetListQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<QuanMonitBigItem>();
        default:
            return SqlQuanDao.GetListQuanMonitBigItem(session, item);
        }
    }

    public static List<QuanMonitBigItem> SearchQuanMonitBigItem(QuanMonitBigItem item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitBigItem>();
            default:
                return SqlQuanDao.SearchQuanMonitBigItem(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitBigItem>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitBigItem(session, item);
            break;
        }
    }

    // endregion QuanMonitBigItem Methods

    // region QuanMonitWorkDetail Methods

    public static List<QuanMonitWorkDetail> GetListQuanMonitWorkDetail(QuanMonitWorkDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitWorkDetail>();
            default:
                return SqlQuanDao.GetListQuanMonitWorkDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitWorkDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitWorkDetail(SqlSession session, QuanMonitWorkDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitWorkDetail(session, item);
            break;
        }
    }

    // endregion QuanMonitWorkDetail Methods

    // region QuanMonitWork Methods

    public static QuanMonitWork GetQuanMonitWork(QuanMonitWork item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanMonitWork(session, item);
        } catch (Exception e) {
            return new QuanMonitWork();
        } finally {
            session.close();
        }
    }

    public static QuanMonitWork GetQuanMonitWork(SqlSession session, QuanMonitWork item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanMonitWork();
        default:
            return SqlQuanDao.GetQuanMonitWork(session, item);
        }
    }

    public static List<QuanMonitWork> SearchQuanMonitWork(QuanMonitWork item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitWork>();
            default:
                return SqlQuanDao.SearchQuanMonitWork(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitWork>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitWork(SqlSession session, QuanMonitWork item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitWork(session, item);
            break;
        }
    }

    // endregion QuanMonitWork Methods

    // region QuanMonitOption Methods

    public static QuanMonitOption GetQuanMonitOption(QuanMonitOption item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanMonitOption(session, item);
        } catch (Exception e) {
            return new QuanMonitOption();
        } finally {
            session.close();
        }
    }

    public static QuanMonitOption GetQuanMonitOption(SqlSession session, QuanMonitOption item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanMonitOption();
        default:
            return SqlQuanDao.GetQuanMonitOption(session, item);
        }
    }

    public static List<QuanMonitOption> GetListQuanMonitOption(QuanMonitOption item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListQuanMonitOption(session, item);
        } catch (Exception e) {
            return new ArrayList<QuanMonitOption>();
        } finally {
            session.close();
        }
    }

    public static List<QuanMonitOption> GetListQuanMonitOption(SqlSession session, QuanMonitOption item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<QuanMonitOption>();
        default:
            return SqlQuanDao.GetListQuanMonitOption(session, item);
        }
    }

    public static List<QuanMonitOption> SearchQuanMonitOption(QuanMonitOption item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitOption>();
            default:
                return SqlQuanDao.SearchQuanMonitOption(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitOption>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitOption(SqlSession session, QuanMonitOption item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitOption(session, item);
            break;
        }
    }

    // endregion QuanMonitOption Methods

    // region QuanControlPlanDetail Methods

    public static List<QuanControlPlanDetail> GetListQuanControlPlanDetail(QuanControlPlanDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanControlPlanDetail>();
            default:
                return SqlQuanDao.GetListQuanControlPlanDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanControlPlanDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanControlPlanDetail(SqlSession session, QuanControlPlanDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanControlPlanDetail(session, item);
            break;
        }
    }

    // endregion QuanControlPlanDetail Methods

    // region QuanControlPlan Methods

    public static QuanControlPlan GetQuanControlPlan(QuanControlPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanControlPlan(session, item);
        } catch (Exception e) {
            return new QuanControlPlan();
        } finally {
            session.close();
        }
    }

    public static QuanControlPlan GetQuanControlPlan(SqlSession session, QuanControlPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanControlPlan();
        default:
            return SqlQuanDao.GetQuanControlPlan(session, item);
        }
    }

    public static List<QuanControlPlan> SearchQuanControlPlan(QuanControlPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanControlPlan>();
            default:
                return SqlQuanDao.SearchQuanControlPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanControlPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanControlPlan(SqlSession session, QuanControlPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanControlPlan(session, item);
            break;
        }
    }

    // endregion QuanControlPlan Methods

    // region CheckGroupMember Methods

    public static List<CheckGroupMember> GetListCheckGroupMember(CheckGroupMember item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CheckGroupMember>();
            default:
                return SqlQuanDao.GetListCheckGroupMember(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CheckGroupMember>();
        } finally {
            session.close();
        }
    }

    public static void SaveCheckGroupMember(SqlSession session, CheckGroupMember item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveCheckGroupMember(session, item);
            break;
        }
    }

    // endregion CheckGroupMember Methods

    // region CheckGroup Methods

    public static CheckGroup GetCheckGroup(CheckGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetCheckGroup(session, item);
        } catch (Exception e) {
            return new CheckGroup();
        } finally {
            session.close();
        }
    }

    public static CheckGroup GetCheckGroup(SqlSession session, CheckGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new CheckGroup();
        default:
            return SqlQuanDao.GetCheckGroup(session, item);
        }
    }

    public static List<CheckGroup> SearchCheckGroup(CheckGroup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<CheckGroup>();
            default:
                return SqlQuanDao.SearchCheckGroup(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<CheckGroup>();
        } finally {
            session.close();
        }
    }

    public static void SaveCheckGroup(SqlSession session, CheckGroup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveCheckGroup(session, item);
            break;
        }
    }

    // endregion CheckGroup Methods

    // region QuanMonitPlan Methods

    public static QuanMonitPlan GetQuanMonitPlan(QuanMonitPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanMonitPlan(session, item);
        } catch (Exception e) {
            return new QuanMonitPlan();
        } finally {
            session.close();
        }
    }

    public static QuanMonitPlan GetQuanMonitPlan(SqlSession session, QuanMonitPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanMonitPlan();
        default:
            return SqlQuanDao.GetQuanMonitPlan(session, item);
        }
    }

    public static List<QuanMonitPlan> SearchQuanMonitPlan(QuanMonitPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitPlan>();
            default:
                return SqlQuanDao.SearchQuanMonitPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitPlan>();
        } finally {
            session.close();
        }
    }

    public static List<QuanMonitPlan> SearchQuanMonitPlanApproved(QuanMonitPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanMonitPlan>();
            default:
                return SqlQuanDao.SearchQuanMonitPlanApproved(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanMonitPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanMonitPlan(SqlSession session, QuanMonitPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanMonitPlan(session, item);
            break;
        }
    }

    // endregion QuanMonitPlan Methods

    // region QuanControlSamp Methods

    public static List<QuanControlSamp> GetListQuanControlSamp(QuanControlSamp item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanControlSamp>();
            default:
                return SqlQuanDao.GetListQuanControlSamp(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanControlSamp>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanControlSamp(SqlSession session, QuanControlSamp item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanControlSamp(session, item);
            break;
        }
    }

    // endregion QuanControlSamp Methods

    // region QuanControlUser Methods

    public static List<QuanControlUser> GetListQuanControlUser(QuanControlUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanControlUser>();
            default:
                return SqlQuanDao.GetListQuanControlUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanControlUser>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanControlUser(SqlSession session, QuanControlUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanControlUser(session, item);
            break;
        }
    }

    // endregion QuanControlUser Methods

    // region QuanControlEval Methods

    public static QuanControlEval GetQuanControlEval(QuanControlEval item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanControlEval(session, item);
        } catch (Exception e) {
            return new QuanControlEval();
        } finally {
            session.close();
        }
    }

    public static QuanControlEval GetQuanControlEval(SqlSession session, QuanControlEval item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanControlEval();
        default:
            return SqlQuanDao.GetQuanControlEval(session, item);
        }
    }

    public static List<QuanControlEval> SearchQuanControlEval(QuanControlEval item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanControlEval>();
            default:
                return SqlQuanDao.SearchQuanControlEval(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanControlEval>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanControlEval(SqlSession session, QuanControlEval item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanControlEval(session, item);
            break;
        }
    }

    // endregion QuanControlEval Methods

    // region QuanCheckRecord Methods

    public static QuanCheckRecord GetQuanCheckRecord(QuanCheckRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetQuanCheckRecord(session, item);
        } catch (Exception e) {
            return new QuanCheckRecord();
        } finally {
            session.close();
        }
    }

    public static QuanCheckRecord GetQuanCheckRecord(SqlSession session, QuanCheckRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new QuanCheckRecord();
        default:
            return SqlQuanDao.GetQuanCheckRecord(session, item);
        }
    }

    public static List<QuanCheckRecord> SearchQuanCheckRecord(QuanCheckRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<QuanCheckRecord>();
            default:
                return SqlQuanDao.SearchQuanCheckRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<QuanCheckRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveQuanCheckRecord(SqlSession session, QuanCheckRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlQuanDao.SaveQuanCheckRecord(session, item);
            break;
        }
    }

    // endregion QuanCheckRecord Methods

}
