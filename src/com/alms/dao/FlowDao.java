package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlFlowDao;
import com.alms.entity.flow.*;
import com.gpersist.entity.publics.FlowSubmit;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class FlowDao {

    // region FlowAuth Methods

    public static FlowAuth GetFlowAuth(FlowAuth item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowAuth(session, item);
        } catch (Exception e) {
            return new FlowAuth();
        } finally {
            session.close();
        }
    }

    public static FlowAuth GetFlowAuth(SqlSession session, FlowAuth item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowAuth();
        default:
            return SqlFlowDao.GetFlowAuth(session, item);
        }
    }

    public static List<FlowAuth> GetListFlowAuth(FlowAuth item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFlowAuth(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowAuth>();
        } finally {
            session.close();
        }
    }

    public static List<FlowAuth> GetListFlowAuth(SqlSession session, FlowAuth item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowAuth>();
        default:
            return SqlFlowDao.GetListFlowAuth(session, item);
        }
    }

    public static List<FlowAuth> SearchFlowAuth(FlowAuth item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowAuth>();
            default:
                return SqlFlowDao.SearchFlowAuth(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowAuth>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowAuth(SqlSession session, FlowAuth item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowAuth(session, item);
            break;
        }
    }

    // endregion FlowAuth Methods

    // region BusTodoLog Methods

    public static void SaveBusTodoLog(SqlSession session, BusTodoLog item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveBusTodoLog(session, item);
            break;
        }
    }

    // endregion BusTodoLog Methods

    // region FlowRole Methods

    public static FlowRole GetFlowRole(FlowRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowRole(session, item);
        } catch (Exception e) {
            return new FlowRole();
        } finally {
            session.close();
        }
    }

    public static FlowRole GetFlowRole(SqlSession session, FlowRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowRole();
        default:
            return SqlFlowDao.GetFlowRole(session, item);
        }
    }

    public static List<FlowRole> GetListFlowRole(FlowRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFlowRole(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowRole>();
        } finally {
            session.close();
        }
    }

    public static List<FlowRole> GetListFlowRole(SqlSession session, FlowRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowRole>();
        default:
            return SqlFlowDao.GetListFlowRole(session, item);
        }
    }

    public static List<FlowRole> SearchFlowRole(FlowRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowRole>();
            default:
                return SqlFlowDao.SearchFlowRole(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowRole>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowRole(SqlSession session, FlowRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowRole(session, item);
            break;
        }
    }

    // endregion FlowRole Methods

    // region FlowNodeUser Methods

    public static List<FlowNodeUser> GetListFlowNodeUser(FlowNodeUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowNodeUser>();
            default:
                return SqlFlowDao.GetListFlowNodeUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowNodeUser>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowNodeUser(SqlSession session, FlowNodeUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowNodeUser(session, item);
            break;
        }
    }

    // endregion FlowNodeUser Methods

    // region FlowNodeRole Methods

    public static List<FlowNodeRole> GetListFlowNodeRole(FlowNodeRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowNodeRole>();
            default:
                return SqlFlowDao.GetListFlowNodeRole(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowNodeRole>();
        } finally {
            session.close();
        }
    }

    public static List<FlowNodeRole> SearchRoleAuth(FlowNodeRole item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowNodeRole>();
            default:
                return SqlFlowDao.SearchRoleAuth(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowNodeRole>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowNodeRole(SqlSession session, FlowNodeRole item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowNodeRole(session, item);
            break;
        }
    }

    // endregion FlowNodeRole Methods

    // region FlowNode Methods

    public static FlowNode GetFlowNodeByCreate(String busflow) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowNodeByCreate(session, busflow);
        } catch (Exception e) {
            return new FlowNode();
        } finally {
            session.close();
        }
    }

    public static FlowNode GetFlowNodeByCreate(SqlSession session, String busflow) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowNode();
        default:
            return SqlFlowDao.GetFlowNodeByCreate(session, busflow);
        }
    }

    public static List<FlowNode> GetFlowNodeBySerial(FlowNode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowNodeBySerial(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowNode>();
        } finally {
            session.close();
        }
    }

    public static List<FlowNode> GetFlowNodeBySerial(SqlSession session, FlowNode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowNode>();
        default:
            return SqlFlowDao.GetFlowNodeBySerial(session, item);
        }
    }

    public static FlowNode GetFlowNodeByTodo(int todoserial) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowNodeByTodo(session, todoserial);
        } catch (Exception e) {
            return new FlowNode();
        } finally {
            session.close();
        }
    }

    public static FlowNode GetFlowNodeByTodo(SqlSession session, int todoserial) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowNode();
        default:
            return SqlFlowDao.GetFlowNodeByTodo(session, todoserial);
        }
    }

    public static FlowNode GetFlowNode(FlowNode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowNode(session, item);
        } catch (Exception e) {
            return new FlowNode();
        } finally {
            session.close();
        }
    }

    public static FlowNode GetFlowNode(SqlSession session, FlowNode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowNode();
        default:
            return SqlFlowDao.GetFlowNode(session, item);
        }
    }

    public static List<FlowNode> GetListFlowNode(FlowNode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFlowNode(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowNode>();
        } finally {
            session.close();
        }
    }

    public static List<FlowNode> GetListFlowNode(SqlSession session, FlowNode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowNode>();
        default:
            return SqlFlowDao.GetListFlowNode(session, item);
        }
    }

    public static List<FlowNode> SearchFlowNode(FlowNode item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowNode>();
            default:
                return SqlFlowDao.SearchFlowNode(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowNode>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowNode(SqlSession session, FlowNode item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowNode(session, item);
            break;
        }
    }

    // endregion FlowNode Methods

    // region FlowButton Methods

    public static FlowButton GetFlowButton(FlowButton item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowButton(session, item);
        } catch (Exception e) {
            return new FlowButton();
        } finally {
            session.close();
        }
    }

    public static FlowButton GetFlowButton(SqlSession session, FlowButton item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowButton();
        default:
            return SqlFlowDao.GetFlowButton(session, item);
        }
    }

    public static List<FlowButton> SearchFlowButton(FlowButton item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowButton>();
            default:
                return SqlFlowDao.SearchFlowButton(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowButton>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowButton(SqlSession session, FlowButton item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowButton(session, item);
            break;
        }
    }

    // endregion FlowButton Methods

    // region BusFlow Methods

    public static BusFlow GetBusFlow(BusFlow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusFlow(session, item);
        } catch (Exception e) {
            return new BusFlow();
        } finally {
            session.close();
        }
    }

    public static BusFlow GetBusFlow(SqlSession session, BusFlow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusFlow();
        default:
            return SqlFlowDao.GetBusFlow(session, item);
        }
    }

    public static List<BusFlow> SearchBusFlow(BusFlow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusFlow>();
            default:
                return SqlFlowDao.SearchBusFlow(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusFlow>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusFlow(SqlSession session, BusFlow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveBusFlow(session, item);
            break;
        }
    }

    // endregion BusFlow Methods

    // region BusTodo Methods

    public static BusTodo GetBusTodo(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTodo(session, item);
        } catch (Exception e) {
            return new BusTodo();
        } finally {
            session.close();
        }
    }

    public static HBusTodo GetHBusTodo(HBusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetHBusTodo(session, item);
        } catch (Exception e) {
            return new HBusTodo();
        } finally {
            session.close();
        }
    }

    public static HBusTodo GetHBusTodo(SqlSession session, HBusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new HBusTodo();
        default:
            return SqlFlowDao.GetHBusTodo(session, item);
        }
    }

    public static BusTodo GetBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTodo();
        default:
            return SqlFlowDao.GetBusTodo(session, item);
        }
    }

    public static List<BusTodo> GetListBusTodo(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTodo(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodo> GetListBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodo>();
        default:
            return SqlFlowDao.GetListBusTodo(session, item);
        }
    }

    public static List<BusTodo> GetBusTodoByTran(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTodoByTran(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodo> GetBusTodoByTran(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodo>();
        default:
            return SqlFlowDao.GetBusTodoByTran(session, item);
        }
    }

    public static List<BusTodo> GetBusTodoByTest(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTodoByTest(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodo> GetBusTodoByTest(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodo>();
        default:
            return SqlFlowDao.GetBusTodoByTest(session, item);
        }
    }

    public static List<BusTodo> SearchBusTodo(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTodo>();
            default:
                return SqlFlowDao.SearchBusTodo(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveBusTodo(session, item);
            break;
        }
    }

    public static void BeginBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.BeginBusTodo(session, item);
            break;
        }
    }

    public static void InsertBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.InsertBusTodo(session, item);
            break;
        }
    }

    public static void DealBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.DealBusTodo(session, item);
            break;
        }
    }

    public static void DealBusTodoByUser(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.DealBusTodoByUser(session, item);
            break;
        }
    }

    public static void LastBusTodo(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.LastBusTodo(session, item);
            break;
        }
    }

    public static void GetMyTodoCount(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            GetMyTodoCount(session, item);
        } catch (Exception e) {
            return;
        } finally {
            session.close();
        }
    }

    public static void GetMyTodoCount(SqlSession session, BusTodo item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return;
        default:
            SqlFlowDao.GetMyTodoCount(session, item);
        }
    }

    public static List<BusTodo> GetMyTodoAlert(String userid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetMyTodoAlert(session, userid);
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodo> GetMyTodoAlert(SqlSession session, String userid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodo>();
        default:
            return SqlFlowDao.GetMyTodoAlert(session, userid);
        }
    }

    public static List<BusTodo> SearchMyApply(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTodo>();
            default:
                return SqlFlowDao.SearchMyApply(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodo> SearchYueZhi(BusTodo item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTodo>();
            default:
                return SqlFlowDao.SearchYueZhi(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTodo>();
        } finally {
            session.close();
        }
    }

    // endregion BusTodo Methods

    // region BusFlowProcess Methods

    public static BusFlowProcess GetBusFlowProcess(BusFlowProcess item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusFlowProcess(session, item);
        } catch (Exception e) {
            return new BusFlowProcess();
        } finally {
            session.close();
        }
    }

    public static BusFlowProcess GetBusFlowProcess(SqlSession session, BusFlowProcess item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusFlowProcess();
        default:
            return SqlFlowDao.GetBusFlowProcess(session, item);
        }
    }

    public static List<BusFlowProcess> GetListBusFlowProcess(BusFlowProcess item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusFlowProcess(session, item);
        } catch (Exception e) {
            return new ArrayList<BusFlowProcess>();
        } finally {
            session.close();
        }
    }

    public static List<BusFlowProcess> GetListBusFlowProcess(SqlSession session, BusFlowProcess item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusFlowProcess>();
        default:
            return SqlFlowDao.GetListBusFlowProcess(session, item);
        }
    }

    public static List<BusFlowProcess> SearchBusFlowProcess(BusFlowProcess item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusFlowProcess>();
            default:
                return SqlFlowDao.SearchBusFlowProcess(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusFlowProcess>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusFlowProcess(SqlSession session, BusFlowProcess item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveBusFlowProcess(session, item);
            break;
        }
    }

    // endregion BusFlowProcess Methods

    // region FlowSubmit Methods

    public static void SaveFlowSubmit(SqlSession session, FlowSubmit item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowSubmit(session, item);
            break;
        }
    }

    // endregion FlowSubmit Methods

    // region FlowRoleUser Methods

    public static FlowRoleUser GetFlowRoleUser(FlowRoleUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowRoleUser(session, item);
        } catch (Exception e) {
            return new FlowRoleUser();
        } finally {
            session.close();
        }
    }

    public static FlowRoleUser GetFlowRoleUser(SqlSession session, FlowRoleUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowRoleUser();
        default:
            return SqlFlowDao.GetFlowRoleUser(session, item);
        }
    }

    public static List<FlowRoleUser> GetFlowRoleUserByRole(FlowRoleUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowRoleUserByRole(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowRoleUser>();
        } finally {
            session.close();
        }
    }

    public static List<FlowRoleUser> GetFlowRoleUserByRole(SqlSession session, FlowRoleUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowRoleUser>();
        default:
            return SqlFlowDao.GetFlowRoleUserByRole(session, item);
        }
    }

    public static List<FlowRoleUser> SearchFlowRoleUser(FlowRoleUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowRoleUser>();
            default:
                return SqlFlowDao.SearchFlowRoleUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowRoleUser>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowRoleUser(SqlSession session, FlowRoleUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowRoleUser(session, item);
            break;
        }
    }

    // endregion FlowRoleUser Methods

    // region FlowNodeButton Methods

    public static FlowNodeButton GetFlowNodeButton(FlowNodeButton item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetFlowNodeButton(session, item);
        } catch (Exception e) {
            return new FlowNodeButton();
        } finally {
            session.close();
        }
    }

    public static FlowNodeButton GetFlowNodeButton(SqlSession session, FlowNodeButton item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new FlowNodeButton();
        default:
            return SqlFlowDao.GetFlowNodeButton(session, item);
        }
    }

    public static List<FlowNodeButton> GetListFlowNodeButton(FlowNodeButton item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListFlowNodeButton(session, item);
        } catch (Exception e) {
            return new ArrayList<FlowNodeButton>();
        } finally {
            session.close();
        }
    }

    public static List<FlowNodeButton> GetListFlowNodeButton(SqlSession session, FlowNodeButton item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<FlowNodeButton>();
        default:
            return SqlFlowDao.GetListFlowNodeButton(session, item);
        }
    }

    public static List<FlowNodeButton> GetListFlowNodeButtonByFlow(FlowNodeButton item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<FlowNodeButton>();
            default:
                return SqlFlowDao.GetListFlowNodeButtonByFlow(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<FlowNodeButton>();
        } finally {
            session.close();
        }
    }

    public static void SaveFlowNodeButton(SqlSession session, FlowNodeButton item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveFlowNodeButton(session, item);
            break;
        }
    }

    // endregion FlowNodeButton Methods

    // region BusTodoNow Methods

    public static BusTodoNow GetBusTodoNow(BusTodoNow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTodoNow(session, item);
        } catch (Exception e) {
            return new BusTodoNow();
        } finally {
            session.close();
        }
    }

    public static BusTodoNow GetBusTodoNow(SqlSession session, BusTodoNow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTodoNow();
        default:
            return SqlFlowDao.GetBusTodoNow(session, item);
        }
    }

    public static List<BusTodoNow> GetListBusTodoNow(BusTodoNow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTodoNow(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTodoNow>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodoNow> GetListBusTodoNow(SqlSession session, BusTodoNow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodoNow>();
        default:
            return SqlFlowDao.GetListBusTodoNow(session, item);
        }
    }

    public static List<BusTodoNow> SearchBusTodoNow(BusTodoNow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTodoNow>();
            default:
                return SqlFlowDao.SearchBusTodoNow(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTodoNow>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTodoNow(SqlSession session, BusTodoNow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlFlowDao.SaveBusTodoNow(session, item);
            break;
        }
    }

    public static List<BusTodoNow> GetBusTodoNowByTran(BusTodoNow item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTodoNowByTran(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTodoNow>();
        } finally {
            session.close();
        }
    }

    public static List<BusTodoNow> GetBusTodoNowByTran(SqlSession session, BusTodoNow item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTodoNow>();
        default:
            return SqlFlowDao.GetBusTodoNowByTran(session, item);
        }
    }

    // endregion BusTodoNow Methods

}
