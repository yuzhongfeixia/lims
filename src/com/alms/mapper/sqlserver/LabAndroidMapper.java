package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.dat.*;
import com.alms.entity.lab.*;

public interface LabAndroidMapper extends BasMapper {

    // region BusTask

    // 查询当前用户的任务单
    public List<BusTask> SearchBusTaskForUser(BusTask item);

    // 查询当前用户的抽样单
    public List<BusGet> SearchBusGetForPrint(BusGet item);

    public List<BusTask> SearchBusTaskForTakeSample(BusTask item);

    public BusTaskSingle GetBusTaskBySampeltran(BusTaskSingle item);

    public BusTaskSingle GetBusTaskSingleBySampleCode(BusTaskSingle item);

    // 根据实验样品编号 查询任务单
    public BusTaskSingle SearchBusTaskBySampeltran(BusTaskSingle item);

    // endregion

    // region BusRecord

    public List<BusRecord> GetBusRecordBySampletran(String sampletran);

    // endregion BusRecord

    // region BusGetNotice

    public List<BusGetNotice> SearchBusGetNotice(BusGetNotice item);

    // endregion BusGetNotice

    // region BusGetNotice

    public List<BusGet> SearchBusGet(BusGet item);

    // region BusGetNotice

    public List<BusGet> SearchBusGetForSampling(BusGet item);

    // endregion BusGetNotice

    // region BusTaskDev

    public List<BusTaskDev> SearchBusTaskDevBySampletran(String sampletran);

    public List<BusTaskDev> SearchAllBasDevForUse(BusTaskDev item);

    public List<BusTaskSingle> GetBusTaskSingleByUserId(BusTaskSingle item);

    // endregion BusTaskDev
}
