package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlLabAndroidDao;
import com.alms.entity.dat.*;
import com.alms.entity.lab.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class LabAndroidDao {

    // region BusTask

    public static List<BusTask> SearchBusTaskForUser(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabAndroidDao.SearchBusTaskForUser(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }

    }
    // region BusGet

    public static List<BusGet> SearchBusGetForPrint(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabAndroidDao.SearchBusGetForPrint(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }

    }

    public static List<BusTask> SearchBusTaskForTakeSample(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabAndroidDao.SearchBusTaskForTakeSample(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }

    }

    public static BusTaskSingle SearchBusTaskBySampeltran(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new BusTaskSingle();
            default:
                return SqlLabAndroidDao.SearchBusTaskBySampeltran(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BusTaskSingle();
        } finally {
            session.close();
        }

    }

    public static BusTaskSingle GetBusTaskBySampeltran(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();
        try {
            return GetBusTaskBySampeltran(session, item);
        } catch (Exception e) {
            return new BusTaskSingle();
        } finally {
            session.close();
        }
    }

    private static BusTaskSingle GetBusTaskBySampeltran(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSingle();
        default:
            return SqlLabAndroidDao.GetBusTaskBySampeltran(session, item);
        }

    }

    public static BusTaskSingle GetBusTaskSingleBySampleCode(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();
        try {
            return GetBusTaskSingleBySampleCode(session, item);
        } catch (Exception e) {
            return new BusTaskSingle();
        } finally {
            session.close();
        }
    }

    private static BusTaskSingle GetBusTaskSingleBySampleCode(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSingle();
        default:
            return SqlLabAndroidDao.GetBusTaskSingleBySampleCode(session, item);
        }

    }
    // endregion

    // region BusRecord
    public static List<BusRecord> GetBusRecordBySampletran(String sampletran) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusRecord>();
            default:
                return SqlLabAndroidDao.GetBusRecordBySampletran(session, sampletran);
            }
        } catch (Exception e) {
            return new ArrayList<BusRecord>();
        } finally {
            session.close();
        }

    }
    // endregion BusRecord

    // region BusGetNotice
    public static List<BusGetNotice> SearchBusGetNotice(BusGetNotice item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGetNotice>();
            default:
                return SqlLabAndroidDao.SearchBusGetNotice(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusGetNotice>();
        } finally {
            session.close();
        }
    }
    // endregion BusGetNotice

    // region BusGetNotice
    public static List<BusGet> SearchBusGet(BusGet item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabAndroidDao.SearchBusGet(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    // region BusGetNotice
    public static List<BusGet> SearchBusGetForSampling(BusGet item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabAndroidDao.SearchBusGetForSampling(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }
    // endregion BusGetNotice

    // region BusTaskDev
    public static List<BusTaskDev> SearchBusTaskDevBySampletran(String sampletran) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskDev>();
            default:
                return SqlLabAndroidDao.SearchBusTaskDevBySampletran(session, sampletran);
            }
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> SearchAllBasDevForUse(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskDev>();
            default:
                return SqlLabAndroidDao.SearchAllBasDevForUse(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }
    // endregion BusTaskDev

    public static List<BusTaskSingle> GetBusTaskSingleByUserId(BusTaskSingle bustasksingle) {
        SqlSession session = DBUtils.getFactory();
        try {
            return GetBusTaskSingleByUserId(session, bustasksingle);
        } catch (Exception e) {
            return new ArrayList<BusTaskSingle>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskSingle> GetBusTaskSingleByUserId(SqlSession session, BusTaskSingle bustasksingle) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSingle>();
        default:
            return SqlLabAndroidDao.GetBusTaskSingleByUserId(session, bustasksingle);
        }

    }

}
