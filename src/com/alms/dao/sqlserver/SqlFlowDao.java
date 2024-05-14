package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.flow.*;
import com.gpersist.entity.publics.FlowSubmit;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlFlowDao {

    // region FlowAuth Methods

    public static FlowAuth GetFlowAuth(SqlSession session, FlowAuth item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowAuth(item);
    }

    public static List<FlowAuth> GetListFlowAuth(SqlSession session, FlowAuth item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowAuth(item);
    }

    public static List<FlowAuth> SearchFlowAuth(SqlSession session, FlowAuth item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchFlowAuth(item);
    }

    public static void SaveFlowAuth(SqlSession session, FlowAuth item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowAuth(item);
    }

    // endregion FlowAuth Methods

    // region BusTodoLog Methods

    public static void SaveBusTodoLog(SqlSession session, BusTodoLog item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveBusTodoLog(item);
    }

    // endregion BusTodoLog Methods

    // region FlowRole Methods

    public static FlowRole GetFlowRole(SqlSession session, FlowRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowRole(item);
    }

    public static List<FlowRole> GetListFlowRole(SqlSession session, FlowRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowRole(item);
    }

    public static List<FlowRole> SearchFlowRole(SqlSession session, FlowRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchFlowRole(item);
    }

    public static void SaveFlowRole(SqlSession session, FlowRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowRole(item);
    }

    // endregion FlowRole Methods

    // region FlowNodeUser Methods

    public static List<FlowNodeUser> GetListFlowNodeUser(SqlSession session, FlowNodeUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowNodeUser(item);
    }

    public static void SaveFlowNodeUser(SqlSession session, FlowNodeUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowNodeUser(item);
    }

    // endregion FlowNodeUser Methods

    // region FlowNodeRole Methods

    public static List<FlowNodeRole> GetListFlowNodeRole(SqlSession session, FlowNodeRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowNodeRole(item);
    }

    public static List<FlowNodeRole> SearchRoleAuth(SqlSession session, FlowNodeRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchRoleAuth(item);
    }

    public static void SaveFlowNodeRole(SqlSession session, FlowNodeRole item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowNodeRole(item);
    }

    // endregion FlowNodeRole Methods

    // region FlowNode Methods

    public static FlowNode GetFlowNodeByCreate(SqlSession session, String busflow) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetFlowNodeByCreate(busflow);
    }

    public static List<FlowNode> GetFlowNodeBySerial(SqlSession session, FlowNode item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetFlowNodeBySerial(item);
    }

    public static FlowNode GetFlowNodeByTodo(SqlSession session, int todoserial) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetFlowNodeByTodo(todoserial);
    }

    public static FlowNode GetFlowNode(SqlSession session, FlowNode item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowNode(item);
    }

    public static List<FlowNode> GetListFlowNode(SqlSession session, FlowNode item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowNode(item);
    }

    public static List<FlowNode> SearchFlowNode(SqlSession session, FlowNode item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchFlowNode(item);
    }

    public static void SaveFlowNode(SqlSession session, FlowNode item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowNode(item);
    }

    // endregion FlowNode Methods

    // region FlowButton Methods

    public static FlowButton GetFlowButton(SqlSession session, FlowButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowButton(item);
    }

    public static List<FlowButton> SearchFlowButton(SqlSession session, FlowButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchFlowButton(item);
    }

    public static void SaveFlowButton(SqlSession session, FlowButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowButton(item);
    }

    // endregion FlowButton Methods

    // region BusFlow Methods

    public static BusFlow GetBusFlow(SqlSession session, BusFlow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusFlow(item);
    }

    public static List<BusFlow> SearchBusFlow(SqlSession session, BusFlow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchBusFlow(item);
    }

    public static void SaveBusFlow(SqlSession session, BusFlow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveBusFlow(item);
    }

    // endregion BusFlow Methods

    // region BusTodo Methods

    public static BusTodo GetBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetBusTodo(item);
    }

    public static HBusTodo GetHBusTodo(SqlSession session, HBusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());
        return mapper.GetHBusTodo(item);
    }

    public static List<BusTodo> GetListBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.full.toString());
        return mapper.GetListBusTodo(item);
    }

    public static List<BusTodo> GetBusTodoByTran(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetBusTodoByTran(item);
    }

    public static List<BusTodo> GetBusTodoByTest(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetBusTodoByTest(item);
    }

    public static List<BusTodo> SearchBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchBusTodo(item);
    }

    public static void SaveBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveBusTodo(item);
    }

    public static void BeginBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.BeginBusTodo(item);
    }

    public static void InsertBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.InsertBusTodo(item);
    }

    public static void DealBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.DealBusTodo(item);
    }

    public static void DealBusTodoByUser(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.DealBusTodoByUser(item);
    }

    public static void LastBusTodo(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.LastBusTodo(item);
    }

    public static void GetMyTodoCount(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.GetMyTodoCount(item);
    }

    public static List<BusTodo> GetMyTodoAlert(SqlSession session, String userid) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetMyTodoAlert(userid);
    }

    public static List<BusTodo> SearchMyApply(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchMyApply(item);
    }

    public static List<BusTodo> SearchYueZhi(SqlSession session, BusTodo item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchYueZhi(item);
    }

    // endregion BusTodo Methods

    // region BusFlowProcess Methods

    public static BusFlowProcess GetBusFlowProcess(SqlSession session, BusFlowProcess item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusFlowProcess(item);
    }

    public static List<BusFlowProcess> GetListBusFlowProcess(SqlSession session, BusFlowProcess item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListBusFlowProcess(item);
    }

    public static List<BusFlowProcess> SearchBusFlowProcess(SqlSession session, BusFlowProcess item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchBusFlowProcess(item);
    }

    public static void SaveBusFlowProcess(SqlSession session, BusFlowProcess item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveBusFlowProcess(item);
    }

    // endregion BusFlowProcess Methods

    // region FlowSubmit Methods

    public static void SaveFlowSubmit(SqlSession session, FlowSubmit item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowSubmit(item);
    }

    // endregion FlowSubmit Methods

    // region FlowRoleUser Methods

    public static FlowRoleUser GetFlowRoleUser(SqlSession session, FlowRoleUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowRoleUser(item);
    }

    public static List<FlowRoleUser> GetFlowRoleUserByRole(SqlSession session, FlowRoleUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetFlowRoleUserByRole(item);
    }

    public static List<FlowRoleUser> SearchFlowRoleUser(SqlSession session, FlowRoleUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchFlowRoleUser(item);
    }

    public static void SaveFlowRoleUser(SqlSession session, FlowRoleUser item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowRoleUser(item);
    }

    // endregion FlowRoleUser Methods

    // region FlowNodeButton Methods

    public static FlowNodeButton GetFlowNodeButton(SqlSession session, FlowNodeButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetFlowNodeButton(item);
    }

    public static List<FlowNodeButton> GetListFlowNodeButton(SqlSession session, FlowNodeButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowNodeButton(item);
    }

    public static List<FlowNodeButton> GetListFlowNodeButtonByFlow(SqlSession session, FlowNodeButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListFlowNodeButtonByFlow(item);
    }

    public static void SaveFlowNodeButton(SqlSession session, FlowNodeButton item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveFlowNodeButton(item);
    }

    // endregion FlowNodeButton Methods

    // region BusTodoNow Methods

    public static BusTodoNow GetBusTodoNow(SqlSession session, BusTodoNow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTodoNow(item);
    }

    public static List<BusTodoNow> GetListBusTodoNow(SqlSession session, BusTodoNow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetListBusTodoNow(item);
    }

    public static List<BusTodoNow> SearchBusTodoNow(SqlSession session, BusTodoNow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.SearchBusTodoNow(item);
    }

    public static void SaveBusTodoNow(SqlSession session, BusTodoNow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        mapper.SaveBusTodoNow(item);
    }

    public static List<BusTodoNow> GetBusTodoNowByTran(SqlSession session, BusTodoNow item) {
        com.alms.mapper.sqlserver.FlowMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.FlowMapper.class);

        return mapper.GetBusTodoNowByTran(item);
    }

    // endregion BusTodoNow Methods
}
