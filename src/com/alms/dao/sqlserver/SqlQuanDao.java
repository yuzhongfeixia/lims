package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.quan.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlQuanDao {

    // region QuanMonitSamItem Methods

    public static QuanMonitSamItem GetQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanMonitSamItem(item);
    }

    public static List<QuanMonitSamItem> GetListQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanMonitSamItem(item);
    }

    public static List<QuanMonitSamItem> SearchQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitSamItem(item);
    }

    public static void SaveQuanMonitSamItem(SqlSession session, QuanMonitSamItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitSamItem(item);
    }

    // endregion QuanMonitSamItem Methods

    // region QuanMonitBigItem Methods

    public static QuanMonitBigItem GetQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanMonitBigItem(item);
    }

    public static List<QuanMonitBigItem> GetListQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanMonitBigItem(item);
    }

    public static List<QuanMonitBigItem> SearchQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitBigItem(item);
    }

    public static void SaveQuanMonitBigItem(SqlSession session, QuanMonitBigItem item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitBigItem(item);
    }

    // endregion QuanMonitBigItem Methods

    // region QuanMonitWorkDetail Methods

    public static List<QuanMonitWorkDetail> GetListQuanMonitWorkDetail(SqlSession session, QuanMonitWorkDetail item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanMonitWorkDetail(item);
    }

    public static void SaveQuanMonitWorkDetail(SqlSession session, QuanMonitWorkDetail item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitWorkDetail(item);
    }

    // endregion QuanMonitWorkDetail Methods

    // region QuanMonitWork Methods

    public static QuanMonitWork GetQuanMonitWork(SqlSession session, QuanMonitWork item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanMonitWork(item);
    }

    public static List<QuanMonitWork> SearchQuanMonitWork(SqlSession session, QuanMonitWork item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitWork(item);
    }

    public static void SaveQuanMonitWork(SqlSession session, QuanMonitWork item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitWork(item);
    }

    // endregion QuanMonitWork Methods

    // region QuanMonitOption Methods

    public static QuanMonitOption GetQuanMonitOption(SqlSession session, QuanMonitOption item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanMonitOption(item);
    }

    public static List<QuanMonitOption> GetListQuanMonitOption(SqlSession session, QuanMonitOption item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanMonitOption(item);
    }

    public static List<QuanMonitOption> SearchQuanMonitOption(SqlSession session, QuanMonitOption item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitOption(item);
    }

    public static void SaveQuanMonitOption(SqlSession session, QuanMonitOption item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitOption(item);
    }

    // endregion QuanMonitOption Methods

    // region QuanControlPlanDetail Methods

    public static List<QuanControlPlanDetail> GetListQuanControlPlanDetail(SqlSession session,
            QuanControlPlanDetail item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanControlPlanDetail(item);
    }

    public static void SaveQuanControlPlanDetail(SqlSession session, QuanControlPlanDetail item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanControlPlanDetail(item);
    }

    // endregion QuanControlPlanDetail Methods

    // region QuanControlPlan Methods

    public static QuanControlPlan GetQuanControlPlan(SqlSession session, QuanControlPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanControlPlan(item);
    }

    public static List<QuanControlPlan> SearchQuanControlPlan(SqlSession session, QuanControlPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanControlPlan(item);
    }

    public static void SaveQuanControlPlan(SqlSession session, QuanControlPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanControlPlan(item);
    }

    // endregion QuanControlPlan Methods

    // region CheckGroupMember Methods

    public static List<CheckGroupMember> GetListCheckGroupMember(SqlSession session, CheckGroupMember item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListCheckGroupMember(item);
    }

    public static void SaveCheckGroupMember(SqlSession session, CheckGroupMember item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveCheckGroupMember(item);
    }

    // endregion CheckGroupMember Methods

    // region CheckGroup Methods

    public static CheckGroup GetCheckGroup(SqlSession session, CheckGroup item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetCheckGroup(item);
    }

    public static List<CheckGroup> SearchCheckGroup(SqlSession session, CheckGroup item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchCheckGroup(item);
    }

    public static void SaveCheckGroup(SqlSession session, CheckGroup item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveCheckGroup(item);
    }

    // endregion CheckGroup Methods

    // region QuanMonitPlan Methods

    public static QuanMonitPlan GetQuanMonitPlan(SqlSession session, QuanMonitPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanMonitPlan(item);
    }

    public static List<QuanMonitPlan> SearchQuanMonitPlan(SqlSession session, QuanMonitPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitPlan(item);
    }

    public static List<QuanMonitPlan> SearchQuanMonitPlanApproved(SqlSession session, QuanMonitPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanMonitPlanApproved(item);
    }

    public static void SaveQuanMonitPlan(SqlSession session, QuanMonitPlan item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanMonitPlan(item);
    }

    // endregion QuanMonitPlan Methods

    // region QuanControlSamp Methods

    public static List<QuanControlSamp> GetListQuanControlSamp(SqlSession session, QuanControlSamp item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanControlSamp(item);
    }

    public static void SaveQuanControlSamp(SqlSession session, QuanControlSamp item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanControlSamp(item);
    }

    // endregion QuanControlSamp Methods

    // region QuanControlUser Methods

    public static List<QuanControlUser> GetListQuanControlUser(SqlSession session, QuanControlUser item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.GetListQuanControlUser(item);
    }

    public static void SaveQuanControlUser(SqlSession session, QuanControlUser item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanControlUser(item);
    }

    // endregion QuanControlUser Methods

    // region QuanControlEval Methods

    public static QuanControlEval GetQuanControlEval(SqlSession session, QuanControlEval item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanControlEval(item);
    }

    public static List<QuanControlEval> SearchQuanControlEval(SqlSession session, QuanControlEval item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanControlEval(item);
    }

    public static void SaveQuanControlEval(SqlSession session, QuanControlEval item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanControlEval(item);
    }

    // endregion QuanControlEval Methods

    // region QuanCheckRecord Methods

    public static QuanCheckRecord GetQuanCheckRecord(SqlSession session, QuanCheckRecord item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetQuanCheckRecord(item);
    }

    public static List<QuanCheckRecord> SearchQuanCheckRecord(SqlSession session, QuanCheckRecord item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        return mapper.SearchQuanCheckRecord(item);
    }

    public static void SaveQuanCheckRecord(SqlSession session, QuanCheckRecord item) {
        com.alms.mapper.sqlserver.QuanMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.QuanMapper.class);

        mapper.SaveQuanCheckRecord(item);
    }

    // endregion QuanCheckRecord Methods

}
