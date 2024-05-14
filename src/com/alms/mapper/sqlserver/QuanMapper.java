package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.quan.*;
import com.gpersist.mapper.BaseMapper;

public interface QuanMapper extends BaseMapper {

    // region QuanMonitSamItem Methods

    public QuanMonitSamItem GetQuanMonitSamItem(QuanMonitSamItem item);

    public List<QuanMonitSamItem> GetListQuanMonitSamItem(QuanMonitSamItem item);

    public List<QuanMonitSamItem> SearchQuanMonitSamItem(QuanMonitSamItem item);

    public void SaveQuanMonitSamItem(QuanMonitSamItem item);

    // endregion QuanMonitSamItem Methods

    // region QuanMonitBigItem Methods

    public QuanMonitBigItem GetQuanMonitBigItem(QuanMonitBigItem item);

    public List<QuanMonitBigItem> GetListQuanMonitBigItem(QuanMonitBigItem item);

    public List<QuanMonitBigItem> SearchQuanMonitBigItem(QuanMonitBigItem item);

    public void SaveQuanMonitBigItem(QuanMonitBigItem item);

    // endregion QuanMonitBigItem Methods

    // region QuanMonitWorkDetail Methods

    public List<QuanMonitWorkDetail> GetListQuanMonitWorkDetail(QuanMonitWorkDetail item);

    public void SaveQuanMonitWorkDetail(QuanMonitWorkDetail item);

    // endregion QuanMonitWorkDetail Methods

    // region QuanMonitWork Methods

    public QuanMonitWork GetQuanMonitWork(QuanMonitWork item);

    public List<QuanMonitWork> SearchQuanMonitWork(QuanMonitWork item);

    public void SaveQuanMonitWork(QuanMonitWork item);

    // endregion QuanMonitWork Methods

    // region QuanMonitOption Methods

    public QuanMonitOption GetQuanMonitOption(QuanMonitOption item);

    public List<QuanMonitOption> GetListQuanMonitOption(QuanMonitOption item);

    public List<QuanMonitOption> SearchQuanMonitOption(QuanMonitOption item);

    public void SaveQuanMonitOption(QuanMonitOption item);

    // endregion QuanMonitOption Methods

    // region QuanControlPlanDetail Methods

    public List<QuanControlPlanDetail> GetListQuanControlPlanDetail(QuanControlPlanDetail item);

    public void SaveQuanControlPlanDetail(QuanControlPlanDetail item);

    // endregion QuanControlPlanDetail Methods

    // region QuanControlPlan Methods

    public QuanControlPlan GetQuanControlPlan(QuanControlPlan item);

    public List<QuanControlPlan> SearchQuanControlPlan(QuanControlPlan item);

    public void SaveQuanControlPlan(QuanControlPlan item);

    // endregion QuanControlPlan Methods

    // region CheckGroupMember Methods

    public List<CheckGroupMember> GetListCheckGroupMember(CheckGroupMember item);

    public void SaveCheckGroupMember(CheckGroupMember item);

    // endregion CheckGroupMember Methods

    // region CheckGroup Methods

    public CheckGroup GetCheckGroup(CheckGroup item);

    public List<CheckGroup> SearchCheckGroup(CheckGroup item);

    public void SaveCheckGroup(CheckGroup item);

    // endregion CheckGroup Methods

    // region QuanMonitPlan Methods

    public QuanMonitPlan GetQuanMonitPlan(QuanMonitPlan item);

    public List<QuanMonitPlan> SearchQuanMonitPlan(QuanMonitPlan item);

    public List<QuanMonitPlan> SearchQuanMonitPlanApproved(QuanMonitPlan item);

    public void SaveQuanMonitPlan(QuanMonitPlan item);

    // endregion QuanMonitPlan Methods

    // region QuanControlSamp Methods

    public List<QuanControlSamp> GetListQuanControlSamp(QuanControlSamp item);

    public void SaveQuanControlSamp(QuanControlSamp item);

    // endregion QuanControlSamp Methods

    // region QuanControlUser Methods

    public List<QuanControlUser> GetListQuanControlUser(QuanControlUser item);

    public void SaveQuanControlUser(QuanControlUser item);

    // endregion QuanControlUser Methods

    // region QuanControlEval Methods

    public QuanControlEval GetQuanControlEval(QuanControlEval item);

    public List<QuanControlEval> SearchQuanControlEval(QuanControlEval item);

    public void SaveQuanControlEval(QuanControlEval item);

    // endregion QuanControlEval Methods

    // region QuanCheckRecord Methods

    public QuanCheckRecord GetQuanCheckRecord(QuanCheckRecord item);

    public List<QuanCheckRecord> SearchQuanCheckRecord(QuanCheckRecord item);

    public void SaveQuanCheckRecord(QuanCheckRecord item);

    // endregion QuanCheckRecord Methods

}
