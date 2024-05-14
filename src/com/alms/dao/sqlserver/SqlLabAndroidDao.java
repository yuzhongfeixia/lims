package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.dat.*;
import com.alms.entity.lab.*;
import com.gpersist.utils.DBUtils;

public class SqlLabAndroidDao {

    // region BusTask

    public static List<BusTask> SearchBusTaskForUser(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusTaskForUser(item);
    }
    // region BusGet

    public static List<BusGet> SearchBusGetForPrint(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusGetForPrint(item);
    }

    public static List<BusTask> SearchBusTaskForTakeSample(SqlSession session, BusTask item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusTaskForTakeSample(item);
    }

    public static BusTaskSingle SearchBusTaskBySampeltran(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusTaskBySampeltran(item);
    }

    public static BusTaskSingle GetBusTaskBySampeltran(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);

        return mapper.GetBusTaskBySampeltran(item);

    }

    public static BusTaskSingle GetBusTaskSingleBySampleCode(SqlSession session, BusTaskSingle item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);

        return mapper.GetBusTaskSingleBySampleCode(item);

    }

    // endregion

    // region BusRecord
    public static List<BusRecord> GetBusRecordBySampletran(SqlSession session, String sampletran) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.GetBusRecordBySampletran(sampletran);
    }
    // endregion BusRecord

    // region BusGetNotice
    public static List<BusGetNotice> SearchBusGetNotice(SqlSession session, BusGetNotice item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusGetNotice(item);
    }
    // endregion BusGetNotice

    // region BusGetNotice
    public static List<BusGet> SearchBusGet(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusGet(item);
    }

    // region BusGetNotice
    public static List<BusGet> SearchBusGetForSampling(SqlSession session, BusGet item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusGetForSampling(item);
    }
    // endregion BusGetNotice

    // region BasDevTest

    public static List<BusTaskDev> SearchBusTaskDevBySampletran(SqlSession session, String sampletran) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchBusTaskDevBySampletran(sampletran);
    }

    public static List<BusTaskDev> SearchAllBasDevForUse(SqlSession session, BusTaskDev item) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);
        return mapper.SearchAllBasDevForUse(item);
    }

    public static List<BusTaskSingle> GetBusTaskSingleByUserId(SqlSession session, BusTaskSingle bustasksingle) {
        com.alms.mapper.sqlserver.LabAndroidMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.LabAndroidMapper.class);

        return mapper.GetBusTaskSingleByUserId(bustasksingle);
    }

    // endregion BusDevTest

}
