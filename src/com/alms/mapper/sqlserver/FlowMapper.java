package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.flow.*;
import com.gpersist.entity.publics.FlowSubmit;

public interface FlowMapper extends BasMapper {

    // region FlowAuth Methods

    public FlowAuth GetFlowAuth(FlowAuth item);

    public List<FlowAuth> GetListFlowAuth(FlowAuth item);

    public List<FlowAuth> SearchFlowAuth(FlowAuth item);

    public void SaveFlowAuth(FlowAuth item);

    // endregion FlowAuth Methods

    // region BusTodoLog Methods

    public void SaveBusTodoLog(BusTodoLog item);

    // endregion BusTodoLog Methods

    // region FlowRole Methods

    public FlowRole GetFlowRole(FlowRole item);

    public List<FlowRole> GetListFlowRole(FlowRole item);

    public List<FlowRole> SearchFlowRole(FlowRole item);

    public void SaveFlowRole(FlowRole item);

    // endregion FlowRole Methods

    // region FlowNodeUser Methods

    public List<FlowNodeUser> GetListFlowNodeUser(FlowNodeUser item);

    public void SaveFlowNodeUser(FlowNodeUser item);

    // endregion FlowNodeUser Methods

    // region FlowNodeRole Methods

    public List<FlowNodeRole> GetListFlowNodeRole(FlowNodeRole item);

    public List<FlowNodeRole> SearchRoleAuth(FlowNodeRole item);

    public void SaveFlowNodeRole(FlowNodeRole item);

    // endregion FlowNodeRole Methods

    // region FlowNode Methods

    public FlowNode GetFlowNodeByCreate(String busflow);

    public FlowNode GetFlowNodeByTodo(int todoserial);

    public List<FlowNode> GetFlowNodeBySerial(FlowNode item);

    public FlowNode GetFlowNode(FlowNode item);

    public List<FlowNode> GetListFlowNode(FlowNode item);

    public List<FlowNode> SearchFlowNode(FlowNode item);

    public void SaveFlowNode(FlowNode item);

    // endregion FlowNode Methods

    // region FlowButton Methods

    public FlowButton GetFlowButton(FlowButton item);

    public List<FlowButton> SearchFlowButton(FlowButton item);

    public void SaveFlowButton(FlowButton item);

    // endregion FlowButton Methods

    // region BusFlow Methods

    public BusFlow GetBusFlow(BusFlow item);

    public List<BusFlow> SearchBusFlow(BusFlow item);

    public void SaveBusFlow(BusFlow item);

    // endregion BusFlow Methods

    // region BusTodo Methods

    public BusTodo GetBusTodo(BusTodo item);

    public HBusTodo GetHBusTodo(HBusTodo item);

    public List<BusTodo> GetListBusTodo(BusTodo item);

    public List<BusTodo> GetBusTodoByTran(BusTodo item);

    public List<BusTodo> GetBusTodoByTest(BusTodo item);

    public List<BusTodo> SearchBusTodo(BusTodo item);

    public void SaveBusTodo(BusTodo item);

    public void BeginBusTodo(BusTodo item);

    public void InsertBusTodo(BusTodo item);

    public void DealBusTodo(BusTodo item);

    public void DealBusTodoByUser(BusTodo item);

    public void LastBusTodo(BusTodo item);

    public void GetMyTodoCount(BusTodo item);

    public List<BusTodo> GetMyTodoAlert(String userid);

    // 查询 我的申请
    public List<BusTodo> SearchMyApply(BusTodo item);

    // 查询 阅知事项
    public List<BusTodo> SearchYueZhi(BusTodo item);

    // endregion BusTodo Methods

    // region BusFlowProcess Methods

    public BusFlowProcess GetBusFlowProcess(BusFlowProcess item);

    public List<BusFlowProcess> GetListBusFlowProcess(BusFlowProcess item);

    public List<BusFlowProcess> SearchBusFlowProcess(BusFlowProcess item);

    public void SaveBusFlowProcess(BusFlowProcess item);

    // endregion BusFlowProcess Methods

    // region FlowSubmit Methods

    public void SaveFlowSubmit(FlowSubmit item);

    // endregion FlowSubmit Methods

    // region FlowRoleUser Methods

    public FlowRoleUser GetFlowRoleUser(FlowRoleUser item);

    public List<FlowRoleUser> GetFlowRoleUserByRole(FlowRoleUser item);

    public List<FlowRoleUser> SearchFlowRoleUser(FlowRoleUser item);

    public void SaveFlowRoleUser(FlowRoleUser item);

    // endregion FlowRoleUser Methods

    // region FlowNodeButton Methods

    public FlowNodeButton GetFlowNodeButton(FlowNodeButton item);

    public List<FlowNodeButton> GetListFlowNodeButton(FlowNodeButton item);

    public List<FlowNodeButton> GetListFlowNodeButtonByFlow(FlowNodeButton item);

    public void SaveFlowNodeButton(FlowNodeButton item);

    // endregion FlowNodeButton Methods

    // region BusTodoNow Methods

    public BusTodoNow GetBusTodoNow(BusTodoNow item);

    public List<BusTodoNow> GetListBusTodoNow(BusTodoNow item);

    public List<BusTodoNow> SearchBusTodoNow(BusTodoNow item);

    public void SaveBusTodoNow(BusTodoNow item);

    public List<BusTodoNow> GetBusTodoNowByTran(BusTodoNow item);

    // endregion BusTodoNow Methods

}
