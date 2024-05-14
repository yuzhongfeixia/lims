package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlDatDao;
import com.alms.dao.sqlserver.SqlLabDao;
import com.alms.entity.bas.BasMainParameter;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.lab.BusRecordFile;
import com.alms.entity.lab.BusRecordSamples;
import com.alms.entity.lab.BusReportFile;
import com.alms.entity.lab.BusTaskFile;
import com.alms.entity.lab.BusTaskLab;
import com.alms.entity.lab.BusTaskResult;
import com.alms.entity.lab.SignerPassword;
import com.alms.entity.lab.BusAccSample;
import com.alms.entity.lab.BusAccFile;
import com.alms.entity.lab.BusCatalogParam;
import com.alms.entity.lab.BusConsign;
import com.alms.entity.lab.BusConsignParam;
import com.alms.entity.lab.BusConsignSample;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusGetNoticeDetail;
import com.alms.entity.lab.BusProc;
import com.alms.entity.lab.BusRecordData;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.lab.BusSelect;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskAttach;
import com.alms.entity.lab.BusTaskData;
import com.alms.entity.lab.BusTaskDev;
import com.alms.entity.lab.BusTaskJudge;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.BusTaskTest;
import com.alms.entity.lab.BusTaskTester;
import com.gpersist.entity.user.SysUser;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class LabDao {

    public static void SaveBusProc(SqlSession session, BusProc item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusProc(session, item);
            break;
        }
    }

    public static List<BusGetNotice> SearchBusGetNotice(BusGetNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGetNotice>();
            default:
                return SqlLabDao.SearchBusGetNotice(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusGetNotice>();
        } finally {
            session.close();
        }

    }

    public static List<BusGetNoticeDetail> GetBusGetNoticeDetail(BusGetNoticeDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGetNoticeDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<BusGetNoticeDetail>();
        } finally {
            session.close();
        }

    }

    private static List<BusGetNoticeDetail> GetBusGetNoticeDetail(SqlSession session, BusGetNoticeDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusGetNoticeDetail>();
        default:
            return SqlLabDao.GetBusGetNoticeDetail(session, item);
        }
    }

    public static List<BusTestedUnit> SearchBusTestedUnit(BusTestedUnit item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTestedUnit>();
            default:
                return SqlLabDao.SearchBusTestedUnit(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusTestedUnit>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusGetNoticeDetail(SqlSession session, BusGetNoticeDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusGetNoticeDetail(session, item);
            break;
        }
    }

    public static void SaveBusGetNotice(SqlSession session, BusGetNotice item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusGetNotice(session, item);
            break;
        }
    }

    public static BusTestedUnit GetBusTestedUnit(BusTestedUnit item) {
        SqlSession session = DBUtils.getFactory();
        try {
            return GetBusTestedUnit(session, item);
        } catch (Exception e) {
            return new BusTestedUnit();
        } finally {
            session.close();
        }
    }

    private static BusTestedUnit GetBusTestedUnit(SqlSession session, BusTestedUnit item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTestedUnit();
        default:
            return SqlLabDao.GetBusTestedUnit(session, item);
        }

    }

    public static BusGetNotice GetBusGetNotice(BusGetNotice item) {
        SqlSession session = DBUtils.getFactory();
        try {
            return GetBusGetNotice(session, item);
        } catch (Exception e) {
            return new BusGetNotice();
        } finally {
            session.close();
        }
    }

    private static BusGetNotice GetBusGetNotice(SqlSession session, BusGetNotice item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetNotice();
        default:
            return SqlLabDao.GetBusGetNotice(session, item);
        }
    }

    public static List<BusGetNotice> SearchBusGetNoticeForGet(BusGetNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGetNotice>();
            default:
                return SqlLabDao.SearchBusGetNoticeForGet(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusGetNotice>();
        } finally {
            session.close();
        }
    }

    // region BusGet Methods

    public static BusGet GetBusGet(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGet(session, item);
        } catch (Exception e) {
            return new BusGet();
        } finally {
            session.close();
        }
    }

    public static BusGet GetBusGet(SqlSession session, BusGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGet();
        default:
            return SqlLabDao.GetBusGet(session, item);
        }
    }

    public static List<BusGet> GetListBusGet(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusGet(session, item);
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    public static List<BusGet> GetListBusGet(SqlSession session, BusGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusGet>();
        default:
            return SqlLabDao.GetListBusGet(session, item);
        }
    }

    public static List<BusGet> SearchBusGet(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabDao.SearchBusGet(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    public static List<BusGet> SearchBusGetForView(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabDao.SearchBusGetForView(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusGet(SqlSession session, BusGet item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusGet(session, item);
            break;
        }
    }

    public static List<BusGet> SearchBusGetForConsign(BusGet item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:

                return new ArrayList<BusGet>();

            default:
                return SqlLabDao.SearchBusGetForConsign(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    public static BusGet GetBusGetBySampleCode(String item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGetBySampleCode(session, item);
        } catch (Exception e) {
            return new BusGet();
        } finally {
            session.close();
        }
    }

    public static BusGet GetBusGetBySampleCode(SqlSession session, String item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGet();

        default:
            return SqlLabDao.GetBusGetBySampleCode(session, item);
        }
    }
    public static BusGet GetBusSamplingGetByNoticeid(String item) {
      SqlSession session = DBUtils.getFactory();
      
      try {
        return GetBusSamplingGetByNoticeid(session, item);
      } catch (Exception e) {
        return new BusGet();
      } finally {
        session.close();
      }
    }
    
    public static BusGet GetBusSamplingGetByNoticeid(SqlSession session, String item) {
      switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
          return new BusGet();
          
        default:
          return SqlLabDao.GetBusSamplingGetByNoticeid(session, item);
      }
    }

    // endregion BusGet Methods

    // region BusGetDetail Methods

    public static BusGetDetail GetBusGetDetail(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGetDetail(session, item);
        } catch (Exception e) {
            return new BusGetDetail();
        } finally {
            session.close();
        }
    }

    public static BusGetDetail GetBusGetDetail(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetDetail();
        default:
            return SqlLabDao.GetBusGetDetail(session, item);
        }
    }

    public static BusGetDetail GetBusGetDetailForStand(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGetDetailForStand(session, item);
        } catch (Exception e) {
            return new BusGetDetail();
        } finally {
            session.close();
        }
    }

    public static BusGetDetail GetBusGetDetailForStand(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetDetail();
        default:
            return SqlLabDao.GetBusGetDetailForStand(session, item);
        }
    }

    public static List<BusGetDetail> GetListBusGetDetail(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusGetDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<BusGetDetail>();
        } finally {
            session.close();
        }
    }

    public static List<BusGetDetail> GetListBusGetDetail(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusGetDetail>();
        default:
            return SqlLabDao.GetListBusGetDetail(session, item);
        }
    }

    public static List<BusGetDetail> GetListBusGetDetailByTask(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusGetDetailByTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusGetDetail>();
        } finally {
            session.close();
        }
    }

    public static List<BusGetDetail> GetListBusGetDetailByTask(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusGetDetail>();
        default:
            return SqlLabDao.GetListBusGetDetailByTask(session, item);
        }
    }

    public static List<BusGetDetail> SearchBusGetDetail(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGetDetail>();
            default:
                return SqlLabDao.SearchBusGetDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusGetDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusGetDetail(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusGetDetail(session, item);
            break;
        }
    }

    public static BusGetDetail GetBusGetDetailBySampleCode(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusGetDetailBySampleCode(session, item);
        } catch (Exception e) {
            return new BusGetDetail();
        } finally {
            session.close();
        }
    }

    private static BusGetDetail GetBusGetDetailBySampleCode(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetDetail();
        default:
            return SqlLabDao.GetBusGetDetailBySampleCode(session, item);
        }
    }

    // endregion BusGetDetail Methods

    // region BusConsign Methods

    public static BusConsign GetBusConsign(BusConsign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusConsign(session, item);
        } catch (Exception e) {
            return new BusConsign();
        } finally {
            session.close();
        }
    }

    public static BusConsign GetBusConsign(SqlSession session, BusConsign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusConsign();
        default:
            return SqlLabDao.GetBusConsign(session, item);
        }
    }

    public static List<BusConsign> GetListBusConsign(BusConsign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusConsign(session, item);
        } catch (Exception e) {
            return new ArrayList<BusConsign>();
        } finally {
            session.close();
        }
    }

    public static List<BusConsign> GetListBusConsign(SqlSession session, BusConsign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusConsign>();
        default:
            return SqlLabDao.GetListBusConsign(session, item);
        }
    }

    public static List<BusConsign> SearchBusConsign(BusConsign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusConsign>();
            default:
                return SqlLabDao.SearchBusConsign(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusConsign>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusConsign(SqlSession session, BusConsign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusConsign(session, item);
            break;
        }
    }

    public static List<BusConsign> SearchBusConsignForTask(BusConsign item) {
        SqlSession session = DBUtils.getFactory();
        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusConsign>();
            default:
                return SqlLabDao.SearchBusConsignForTask(session, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusConsign>();
        } finally {
            session.close();
        }
    }

    // endregion BusConsign Methods

    // region BusConsignSample Methods

    public static BusConsignSample GetBusConsignSample(BusConsignSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusConsignSample(session, item);
        } catch (Exception e) {
            return new BusConsignSample();
        } finally {
            session.close();
        }
    }

    public static BusConsignSample GetBusConsignSample(SqlSession session, BusConsignSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusConsignSample();
        default:
            return SqlLabDao.GetBusConsignSample(session, item);
        }
    }

    public static List<BusConsignSample> GetListBusConsignSample(BusConsignSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusConsignSample(session, item);
        } catch (Exception e) {
            return new ArrayList<BusConsignSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusConsignSample> GetListBusConsignSample(SqlSession session, BusConsignSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusConsignSample>();
        default:
            return SqlLabDao.GetListBusConsignSample(session, item);
        }
    }

    public static List<BusConsignSample> SearchBusConsignSample(BusConsignSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusConsignSample>();
            default:
                return SqlLabDao.SearchBusConsignSample(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusConsignSample>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusConsignSample(SqlSession session, BusConsignSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusConsignSample(session, item);
            break;
        }
    }

    // endregion BusConsignSample Methods

    // region BusConsignParam Methods

    public static BusConsignParam GetBusConsignParam(BusConsignParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusConsignParam(session, item);
        } catch (Exception e) {
            return new BusConsignParam();
        } finally {
            session.close();
        }
    }

    public static BusConsignParam GetBusConsignParam(SqlSession session, BusConsignParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusConsignParam();
        default:
            return SqlLabDao.GetBusConsignParam(session, item);
        }
    }

    public static List<BusConsignParam> GetListBusConsignParam(BusConsignParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusConsignParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BusConsignParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusConsignParam> GetListBusConsignParam(SqlSession session, BusConsignParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusConsignParam>();
        default:
            return SqlLabDao.GetListBusConsignParam(session, item);
        }
    }

    public static List<BusConsignParam> SearchBusConsignParam(BusConsignParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusConsignParam>();
            default:
                return SqlLabDao.SearchBusConsignParam(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusConsignParam>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusConsignParam(SqlSession session, BusConsignParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusConsignParam(session, item);
            break;
        }
    }

    // endregion BusConsignParam Methods

    // region BusTask Methods

    public static BusTask GetBusTask(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTask(session, item);
        } catch (Exception e) {
            return new BusTask();
        } finally {
            session.close();
        }
    }

    public static BusTask GetBusTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTask();
        default:
            return SqlLabDao.GetBusTask(session, item);
        }
    }

    public static BusTask GetBusTaskForSampleCode(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskForSampleCode(session, item);
        } catch (Exception e) {
            return new BusTask();
        } finally {
            session.close();
        }
    }

    public static BusTask GetBusTaskForSampleCode(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTask();
        default:
            return SqlLabDao.GetBusTaskForSampleCode(session, item);
        }
    }

    public static List<BusTask> GetListBusTask(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static List<BusTask> GetListBusTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTask>();
        default:
            return SqlLabDao.GetListBusTask(session, item);
        }
    }

    public static List<BusTask> GetListBusTaskForCount(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskForCount(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static List<BusTask> GetListBusTaskForCount(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTask>();
        default:
            return SqlLabDao.GetListBusTaskForCount(session, item);
        }
    }

    public static List<BusTask> SearchBusTask(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabDao.SearchBusTask(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static List<BusTask> SearchBusTaskForSamplecode(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabDao.SearchBusTaskForSamplecode(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static List<BusTask> SearchBusTaskForDeal(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabDao.SearchBusTaskForDeal(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static List<BusTask> SearchBusTaskAllot(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTask>();
            default:
                return SqlLabDao.SearchBusTaskAllot(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTask(session, item);
            break;
        }
    }

    public static void SaveBusTaskSingle(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskSingle(session, item);
            break;
        }
    }

    // endregion BusTask Methods

    // region BusTaskFile Methods

    public static List<BusTaskFile> GetListBusTaskFile(BusTaskFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskFile(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskFile>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskFile> GetListBusTaskFile(SqlSession session, BusTaskFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskFile>();
        default:
            return SqlLabDao.GetListBusTaskFile(session, item);
        }
    }

    public static void SaveBusTaskFile(SqlSession session, BusTaskFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskFile(session, item);
            break;
        }
    }

    // endregion BusTaskFile Methods

    // region BusAccFile Methods

    public static List<BusAccFile> GetListBusAccFile(BusAccFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusAccFile(session, item);
        } catch (Exception e) {
            return new ArrayList<BusAccFile>();
        } finally {
            session.close();
        }
    }

    private static List<BusAccFile> GetListBusAccFile(SqlSession session, BusAccFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusAccFile>();
        default:
            return SqlLabDao.GetListBusAccFile(session, item);
        }
    }

    public static void SaveBusAccFile(SqlSession session, BusAccFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusAccFile(session, item);
            break;
        }
    }

    // endregion BusAccFile Methods

    // region BusTaskTest Methods

    public static BusTaskTest GetBusTaskTest(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskTest(session, item);
        } catch (Exception e) {
            return new BusTaskTest();
        } finally {
            session.close();
        }
    }

    public static BusTaskTest GetBusTaskTest(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskTest();
        default:
            return SqlLabDao.GetBusTaskTest(session, item);
        }
    }

    public static List<BusTaskTest> GetListBusTaskTest(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskTest(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTest>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskTest> GetListBusTaskTest(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTest>();
        default:
            return SqlLabDao.GetListBusTaskTest(session, item);
        }
    }

    public static List<BusTaskTest> GetListBusTaskTestForMore(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskTestForMore(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTest>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskTest> GetListBusTaskTestForMore(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTest>();
        default:
            return SqlLabDao.GetListBusTaskTestForMore(session, item);
        }
    }

    public static List<BusTaskTest> SearchBusTaskTest(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskTest>();
            default:
                return SqlLabDao.SearchBusTaskTest(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskTest>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskTest(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskTest(session, item);
            break;
        }
    }

    public static List<BusTaskTest> GetListBusTaskParam(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTest>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskTest> GetListBusTaskParam(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTest>();
        default:
            return SqlLabDao.GetListBusTaskParam(session, item);
        }
    }

    public static List<BusTaskTest> GetJudgeTasks(BusTaskTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetJudgeTasks(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTest>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskTest> GetJudgeTasks(SqlSession session, BusTaskTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTest>();
        default:
            return SqlLabDao.GetJudgeTasks(session, item);
        }
    }

    // endregion BusTaskTest Methods

    // region BusTaskDev Methods

    public static BusTaskDev GetBusTaskDev(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskDev(session, item);
        } catch (Exception e) {
            return new BusTaskDev();
        } finally {
            session.close();
        }
    }

    public static BusTaskDev GetBusTaskDev(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskDev();
        default:
            return SqlLabDao.GetBusTaskDev(session, item);
        }
    }

    public static List<BusTaskDev> GetListBusTaskDev(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskDev(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> GetListBusTaskDev(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskDev>();
        default:
            return SqlLabDao.GetListBusTaskDev(session, item);
        }
    }

    public static List<BusTaskDev> GetListBusTaskDevByDevID(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskDevByDevID(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> GetListBusTaskDevByDevID(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskDev>();
        default:
            return SqlLabDao.GetListBusTaskDevByDevID(session, item);
        }
    }

    public static List<BusTaskDev> GetListBusTaskDevBySamplecode(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskDevBySamplecode(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskDev> GetListBusTaskDevBySamplecode(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskDev>();
        default:
            return SqlLabDao.GetListBusTaskDevBySamplecode(session, item);
        }
    }

    public static List<BusTaskDev> SearchBusTaskDev(BusTaskDev item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskDev>();
            default:
                return SqlLabDao.SearchBusTaskDev(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskDev>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskDev(SqlSession session, BusTaskDev item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskDev(session, item);
            break;
        }
    }

    // endregion BusTaskDev Methods

    // region BusTaskData Methods

    public static BusTaskData GetBusTaskData(BusTaskData item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskData(session, item);
        } catch (Exception e) {
            return new BusTaskData();
        } finally {
            session.close();
        }
    }

    public static BusTaskData GetBusTaskData(SqlSession session, BusTaskData item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskData();
        default:
            return SqlLabDao.GetBusTaskData(session, item);
        }
    }

    public static List<BusTaskData> GetListBusTaskData(BusTaskData item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskData(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskData>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskData> GetListBusTaskData(SqlSession session, BusTaskData item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskData>();
        default:
            return SqlLabDao.GetListBusTaskData(session, item);
        }
    }

    public static List<BusTaskData> SearchBusTaskData(BusTaskData item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskData>();
            default:
                return SqlLabDao.SearchBusTaskData(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskData>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskData(SqlSession session, BusTaskData item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskData(session, item);
            break;
        }
    }

    // endregion BusTaskData Methods

    // region BusTaskSample Methods

    public static BusTaskSample GetBusTaskSample(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSample(session, item);
        } catch (Exception e) {
            return new BusTaskSample();
        } finally {
            session.close();
        }
    }

    public static BusTaskSample GetBusTaskSample(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSample();
        default:
            return SqlLabDao.GetBusTaskSample(session, item);
        }
    }

    public static List<BusTaskSample> GetListBusTaskSample(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskSample(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetListBusTaskSample(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetListBusTaskSample(session, item);
        }
    }

    public static List<BusTaskSample> GetListBusTaskForAcc(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskForAcc(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetListBusTaskForAcc(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetListBusTaskForAcc(session, item);
        }
    }

    public static List<BusTaskSample> SearchBusTaskSample(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskSample>();
            default:
                return SqlLabDao.SearchBusTaskSample(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskSample(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskSample(session, item);
            break;
        }
    }

    public static void SaveBusRecordDataBySampletran(SqlSession session, BusRecordData item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusRecordDataBySampletran(session, item);
            break;
        }
    }

    // public static List<BusTaskSingle> SearchBusTaskBegin(BusTaskSingle item) {
    // SqlSession session = DBUtils.getFactory();
    //
    // try {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new ArrayList<BusTaskSingle>();
    // default:
    // return SqlLabDao.SearchBusTaskBegin(session, item);
    //
    // }
    // } catch (Exception e) {
    // return new ArrayList<BusTaskSingle>();
    // }
    // finally {
    // session.close();
    // }
    // }

    // endregion BusTaskSample Methods

    public static void ComputeBusTask(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.ComputeBusTask(session, item);
            break;
        }
    }

    public static BusConsignSample GetBusConsignSampleByTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusConsignSample();
        default:
            return SqlLabDao.GetBusConsignSampleByTask(session, item);
        }
    }

    // public static List<BusTaskSingle> SearchBusTaskEnd(BusTaskSingle item) {
    // SqlSession session = DBUtils.getFactory();
    //
    // try {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new ArrayList<BusTaskSingle>();
    // default:
    // return SqlLabDao.SearchBusTaskEnd(session, item);
    //
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new ArrayList<BusTaskSingle>();
    // }
    // finally {
    // session.close();
    // }
    // }

    public static List<BusTaskSample> GetBusTaskSampleByTask(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSampleByTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetBusTaskSampleByTask(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetBusTaskSampleByTask(session, item);
        }
    }

    public static List<BusTaskSample> GetBusTaskSampleForJudge(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSampleForJudge(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetBusTaskSampleForJudge(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetBusTaskSampleForJudge(session, item);
        }
    }

    public static List<BusTaskSample> GetBusTaskSampleForFinsh(BusTaskSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSampleForFinsh(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetBusTaskSampleForFinsh(SqlSession session, BusTaskSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetBusTaskSampleForFinsh(session, item);
        }
    }

    public static List<BusRecordData> GetDataByTask(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordData>();
        default:
            return SqlLabDao.GetDataByTask(session, item);
        }
    }

    public static SysUser GetLabLeader(SysUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetLabLeader(session, item);
        } catch (Exception e) {
            return new SysUser();
        } finally {
            session.close();
        }
    }

    private static SysUser GetLabLeader(SqlSession session, SysUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SysUser();
        default:
            return SqlLabDao.GetLabLeader(session, item);
        }
    }

    public static List<BusConsignParam> GetBusConsignParamByLab(BusConsignParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusConsignParamByLab(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusConsignParam>();
        } finally {
            session.close();
        }
    }

    private static List<BusConsignParam> GetBusConsignParamByLab(SqlSession session, BusConsignParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusConsignParam>();
        default:
            return SqlLabDao.GetBusConsignParamByLab(session, item);
        }
    }

    public static void UpdateParamInfo(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.UpdateParamInfo(session, item);
            break;
        }
    }

    public static void UpdateParamForZjy(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.UpdateParamForZjy(session, item);
            break;
        }
    }

    // region GetConsignCode Methods

    public static List<BusTaskSample> GetConsignCode(BusConsign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetConsignCode(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSample> GetConsignCode(SqlSession session, BusConsign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSample>();
        default:
            return SqlLabDao.GetConsignCode(session, item);
        }
    }

    // endregion GetConsignCode Methods

    // region BusSampleParam Methods

    public static BusSampleParam GetBusSampleParam(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParam(session, item);
        } catch (Exception e) {
            return new BusSampleParam();
        } finally {
            session.close();
        }
    }

    public static BusSampleParam GetBusSampleParam(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusSampleParam();
        default:
            return SqlLabDao.GetBusSampleParam(session, item);
        }
    }

    public static BusSampleParam GetBusSampleParamByParameter(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamByParameter(session, item);
        } catch (Exception e) {
            return new BusSampleParam();
        } finally {
            session.close();
        }
    }

    public static BusSampleParam GetBusSampleParamByParameter(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusSampleParam();
        default:
            return SqlLabDao.GetBusSampleParamByParameter(session, item);
        }
    }

    public static BusSampleParam GetBusSampleParamByStand(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamByStand(session, item);
        } catch (Exception e) {
            return new BusSampleParam();
        } finally {
            session.close();
        }
    }

    public static BusSampleParam GetBusSampleParamByStand(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusSampleParam();
        default:
            return SqlLabDao.GetBusSampleParamByStand(session, item);
        }
    }

    public static List<BusSampleParam> GetListBusSampleParam(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusSampleParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusSampleParam> GetListBusSampleParam(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlLabDao.GetListBusSampleParam(session, item);
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamForTask(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusSampleParamForTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamForTask(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlLabDao.GetListBusSampleParamForTask(session, item);
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamByTask(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusSampleParamByTask(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamByTask(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlLabDao.GetListBusSampleParamByTask(session, item);
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamByAcc(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusSampleParamByAcc(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusSampleParam> GetListBusSampleParamByAcc(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlLabDao.GetListBusSampleParamByAcc(session, item);
        }
    }

    public static List<BusSampleParam> SearchBusSampleParam(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusSampleParam>();
            default:
                return SqlLabDao.SearchBusSampleParam(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusSampleParam(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusSampleParam(session, item);
            break;
        }
    }

    public static List<BusSampleParam> GetBusSampleParamBySampleCode(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamBySampleCode(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    private static List<BusSampleParam> GetBusSampleParamBySampleCode(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlLabDao.GetBusSampleParamBySampleCode(session, item);
        }
    }

    public static List<BasMainParameter> GetBusSampleParamByLab(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamByLab(session, item);
        } catch (Exception e) {
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasMainParameter> GetBusSampleParamByLab(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlLabDao.GetBusSampleParamByLab(session, item);
        }
    }

    public static List<BasMainParameter> GetBasSampleReplaceByLab(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleReplaceByLab(session, item);
        } catch (Exception e) {
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasMainParameter> GetBasSampleReplaceByLab(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlLabDao.GetBasSampleReplaceByLab(session, item);
        }
    }

    public static BusSampleParam GetBusSampleParamForJudge(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamForJudge(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new BusSampleParam();
        } finally {
            session.close();
        }
    }

    private static BusSampleParam GetBusSampleParamForJudge(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusSampleParam();
        default:
            return SqlLabDao.GetBusSampleParamForJudge(session, item);
        }
    }

    public static BusSampleParam GetBusSampleParamForBatchJudge(BusSampleParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamForBatchJudge(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new BusSampleParam();
        } finally {
            session.close();
        }
    }

    private static BusSampleParam GetBusSampleParamForBatchJudge(SqlSession session, BusSampleParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusSampleParam();
        default:
            return SqlLabDao.GetBusSampleParamForBatchJudge(session, item);
        }
    }

    // endregion BusSampleParam Methods

    // region BusCatelogParam Methods

    public static BusCatalogParam GetBusCatalogParam(BusCatalogParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusCatalogParam(session, item);
        } catch (Exception e) {
            return new BusCatalogParam();
        } finally {
            session.close();
        }
    }

    public static BusCatalogParam GetBusCatalogParam(SqlSession session, BusCatalogParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusCatalogParam();
        default:
            return SqlLabDao.GetBusCatalogParam(session, item);
        }
    }

    public static List<BusCatalogParam> GetListBusCatalogParam(BusCatalogParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusCatalogParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BusCatalogParam>();
        } finally {
            session.close();
        }
    }

    public static List<BusCatalogParam> GetListBusCatalogParam(SqlSession session, BusCatalogParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusCatalogParam>();
        default:
            return SqlLabDao.GetListBusCatalogParam(session, item);
        }
    }

    public static List<BusCatalogParam> SearchBusCatalogParam(BusCatalogParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusCatalogParam>();
            default:
                return SqlLabDao.SearchBusCatalogParam(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusCatalogParam>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusCatalogParam(SqlSession session, BusCatalogParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusCatalogParam(session, item);
            break;
        }
    }

    // public static List<BusCatalogParam> GetListCatalogParamBySample(SqlSession
    // session, String item) {
    // switch(ToolUtils.GetDataBaseType()) {
    // case Oracle10:
    // return new ArrayList<BusCatalogParam>();
    // default:
    // return SqlLabDao.GetListCatalogParamBySample(session, item);
    // }
    // }

    // endregion BusCatelogParam Methods

    // region BusAccSample Methods

    public static BusAccSample GetBusAccSample(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusAccSample(session, item);
        } catch (Exception e) {
            return new BusAccSample();
        } finally {
            session.close();
        }
    }

    public static BusAccSample GetBusAccSample(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusAccSample();
        default:
            return SqlLabDao.GetBusAccSample(session, item);
        }
    }

    public static BusAccSample GetBusAccSampleByGetID(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusAccSampleByGetID(session, item);
        } catch (Exception e) {
            return new BusAccSample();
        } finally {
            session.close();
        }
    }

    public static BusAccSample GetBusAccSampleByGetID(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusAccSample();
        default:
            return SqlLabDao.GetBusAccSampleByGetID(session, item);
        }
    }

    public static BusAccSample GetBusAccSampleByTranID(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusAccSampleByTranID(session, item);
        } catch (Exception e) {
            return new BusAccSample();
        } finally {
            session.close();
        }
    }

    public static BusAccSample GetBusAccSampleByTranID(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusAccSample();
        default:
            return SqlLabDao.GetBusAccSampleByTranID(session, item);
        }
    }

    public static List<BusAccSample> GetListBusAccSample(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusAccSample(session, item);
        } catch (Exception e) {
            return new ArrayList<BusAccSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusAccSample> GetListBusAccSample(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusAccSample>();
        default:
            return SqlLabDao.GetListBusAccSample(session, item);
        }
    }

    public static List<BusAccSample> SearchBusAccSample(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusAccSample>();
            default:
                return SqlLabDao.SearchBusAccSample(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusAccSample>();
        } finally {
            session.close();
        }
    }

    public static List<BusAccSample> SearchBusAccSampleForBatch(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusAccSample>();
            default:
                return SqlLabDao.SearchBusAccSampleForBatch(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusAccSample>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusAccSample(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusAccSample(session, item);
            break;
        }
    }

    public static List<BusGet> SearchBusGetForAcc(BusGet item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusGet>();
            default:
                return SqlLabDao.SearchBusGetForAcc(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusGet>();
        } finally {
            session.close();
        }
    }

    public static List<BusAccSample> GetBusAccSampleBySampleCode(BusAccSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusAccSampleBySampleCode(session, item);
        } catch (Exception e) {
            return new ArrayList<BusAccSample>();
        } finally {
            session.close();
        }
    }

    private static List<BusAccSample> GetBusAccSampleBySampleCode(SqlSession session, BusAccSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusAccSample>();

        default:
            return SqlLabDao.GetBusAccSampleBySampleCode(session, item);
        }
    }

    // endregion BusAccSample Methods

    // region BusTaskTester Methods

    public static BusTaskTester GetBusTaskTester(BusTaskTester item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskTester(session, item);
        } catch (Exception e) {
            return new BusTaskTester();
        } finally {
            session.close();
        }
    }

    public static BusTaskTester GetBusTaskTester(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskTester();
        default:
            return SqlLabDao.GetBusTaskTester(session, item);
        }
    }

    public static List<BusTaskTester> GetListBusTaskTester(BusTaskTester item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskTester(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTester>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskTester> GetListBusTaskTester(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTester>();
        default:
            return SqlLabDao.GetListBusTaskTester(session, item);
        }
    }

    public static List<BusTaskTester> GetListBusTaskTesterByUser(BusTaskTester item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskTesterByUser(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskTester>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskTester> GetListBusTaskTesterByUser(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTester>();
        default:
            return SqlLabDao.GetListBusTaskTesterByUser(session, item);
        }
    }

    public static List<BusTaskTester> SearchBusTaskTester(BusTaskTester item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskTester>();
            default:
                return SqlLabDao.SearchBusTaskTester(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskTester>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTaskTester(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskTester(session, item);
            break;
        }
    }

    public static List<BusTaskTester> GetListBusTaskTesterBySingle(BusTaskTester item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskTesterBySingle(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusTaskTester>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskTester> GetListBusTaskTesterBySingle(SqlSession session, BusTaskTester item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskTester>();
        default:
            return SqlLabDao.GetListBusTaskTesterBySingle(session, item);
        }
    }

    // endregion BusTaskTester Methods

    // region BusTaskSingle Methods

    public static BusTaskSingle GetBusTaskSingle(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSingle(session, item);
        } catch (Exception e) {
            return new BusTaskSingle();
        } finally {
            session.close();
        }
    }

    public static BusTaskSingle GetBusTaskSingle(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSingle();
        default:
            return SqlLabDao.GetBusTaskSingle(session, item);
        }
    }

    public static BusTaskSingle GetBusTaskSingleByTaskid(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSingleByTaskid(session, item);
        } catch (Exception e) {
            return new BusTaskSingle();
        } finally {
            session.close();
        }
    }

    public static BusTaskSingle GetBusTaskSingleByTaskid(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSingle();
        default:
            return SqlLabDao.GetBusTaskSingleByTaskid(session, item);
        }
    }

    public static BusTaskSingle GetBusTaskSingleBySampleTran(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSingleBySampleTran(session, item);
        } catch (Exception e) {
            return new BusTaskSingle();
        } finally {
            session.close();
        }
    }

    public static BusTaskSingle GetBusTaskSingleBySampleTran(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskSingle();
        default:
            return SqlLabDao.GetBusTaskSingleBySampleTran(session, item);
        }
    }

    public static List<BusTaskSingle> GetBusTaskSingleByTranID(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskSingleByTranID(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskSingle>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSingle> GetBusTaskSingleByTranID(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskSingle>();
        default:
            return SqlLabDao.GetBusTaskSingleByTranID(session, item);
        }
    }

    public static void SaveBusTaskSingleLab(SqlSession session, BusTaskSingle item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskSingleLab(session, item);
            break;
        }
    }

    public static List<BusTaskSingle> SearchBusTaskSingle(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskSingle>();
            default:
                return SqlLabDao.SearchBusTaskSingle(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskSingle>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSingle> SearchBusTaskRecord(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskSingle>();
            default:
                return SqlLabDao.SearchBusTaskRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskSingle>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskSingle> SearchBusTaskJudge(BusTaskSingle item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTaskSingle>();
            default:
                return SqlLabDao.SearchBusTaskJudge(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTaskSingle>();
        } finally {
            session.close();
        }
    }

    // endregion BusTaskSingle Methods

    // region BusMainParameter Methods

    public static List<BasMainParameter> GetBusSampleParamBySample(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamBySample(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasMainParameter> GetBusSampleParamBySample(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlLabDao.GetBusSampleParamBySample(session, item);
        }
    }

    public static List<BasMainParameter> GetBusSampleParamByConsign(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSampleParamByConsign(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasMainParameter> GetBusSampleParamByConsign(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlLabDao.GetBusSampleParamByConsign(session, item);
        }
    }

    public static List<BasMainParameter> GetBasSampleParamForCombo(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleParamForCombo(session, item);
        } catch (Exception e) {
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasMainParameter> GetBasSampleParamForCombo(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlLabDao.GetBasSampleParamForCombo(session, item);
        }
    }

    // endregion BusMainParameter Methods

    // region BusTaskAttach Methods

    public static List<BusTaskAttach> GetListBusTaskAttach(BusTaskAttach item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskAttach(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskAttach>();
        } finally {
            session.close();
        }
    }

    private static List<BusTaskAttach> GetListBusTaskAttach(SqlSession session, BusTaskAttach item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskAttach>();
        default:
            return SqlLabDao.GetListBusTaskAttach(session, item);
        }
    }

    public static List<BusTask> GetListBusTaskManyAllot(BusTask item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskManyAllot(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTask>();
        } finally {
            session.close();
        }
    }

    private static List<BusTask> GetListBusTaskManyAllot(SqlSession session, BusTask item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTask>();
        default:
            return SqlLabDao.GetListBusTaskManyAllot(session, item);
        }
    }

    public static void SaveBusTaskAttach(SqlSession session, BusTaskAttach item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskAttach(session, item);
            break;
        }
    }

    // endregion BusTaskAttach Methods

    // region BusRecordFile Methods

    public static List<BusRecordFile> GetListBusRecordFile(BusRecordFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusRecordFile(session, item);
        } catch (Exception e) {
            return new ArrayList<BusRecordFile>();
        } finally {
            session.close();
        }
    }

    private static List<BusRecordFile> GetListBusRecordFile(SqlSession session, BusRecordFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusRecordFile>();
        default:
            return SqlLabDao.GetListBusRecordFile(session, item);
        }
    }

    public static void SaveBusRecordFile(SqlSession session, BusRecordFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusRecordFile(session, item);
            break;
        }
    }

    // endregion BusRecordFile Methods

    // region BusReportFile Methods

    public static List<BusReportFile> GetListBusReportFile(BusReportFile item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusReportFile(session, item);
        } catch (Exception e) {
            return new ArrayList<BusReportFile>();
        } finally {
            session.close();
        }
    }

    private static List<BusReportFile> GetListBusReportFile(SqlSession session, BusReportFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusReportFile>();
        default:
            return SqlLabDao.GetListBusReportFile(session, item);
        }
    }

    public static void SaveBusReportFile(SqlSession session, BusReportFile item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusReportFile(session, item);
            break;
        }
    }

    // endregion BusReportFile Methods

    // region BusSelect Methods

    public static List<BusSelect> GetBusSelectByCode(BusSelect item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusSelectByCode(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BusSelect>();
        } finally {
            session.close();
        }
    }

    private static List<BusSelect> GetBusSelectByCode(SqlSession session, BusSelect item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSelect>();

        default:
            return SqlLabDao.GetBusSelectByCode(session, item);
        }
    }

    // endregion BusSelect Methods

    // region BusTaskJudge Methods

    public static BusTaskJudge GetBusTaskJudge(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskJudge(session, item);
        } catch (Exception e) {
            return new BusTaskJudge();
        } finally {
            session.close();
        }
    }

    public static BusTaskJudge GetBusTaskJudge(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskJudge();
        default:
            return SqlLabDao.GetBusTaskJudge(session, item);
        }
    }

    public static BusTaskJudge GetBusTaskJudgeForInfo(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTaskJudgeForInfo(session, item);
        } catch (Exception e) {
            return new BusTaskJudge();
        } finally {
            session.close();
        }
    }

    public static BusTaskJudge GetBusTaskJudgeForInfo(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTaskJudge();
        default:
            return SqlLabDao.GetBusTaskJudgeForInfo(session, item);
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudge(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskJudge(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudge(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskJudge>();
        default:
            return SqlLabDao.GetListBusTaskJudge(session, item);
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForNo(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskJudgeForNo(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForNo(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskJudge>();
        default:
            return SqlLabDao.GetListBusTaskJudgeForNo(session, item);
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForYes(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskJudgeForYes(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeForYes(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskJudge>();
        default:
            return SqlLabDao.GetListBusTaskJudgeForYes(session, item);
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeBySampleCode(BusTaskJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskJudgeBySampleCode(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskJudge> GetListBusTaskJudgeBySampleCode(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskJudge>();
        default:
            return SqlLabDao.GetListBusTaskJudgeBySampleCode(session, item);
        }
    }

    public static void SaveBusTaskJudge(SqlSession session, BusTaskJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskJudge(session, item);
            break;
        }
    }

    // endregion BusTaskJudge Methods

    // region SignerPassword Methods

    public static SignerPassword GetSignerPassword(SignerPassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSignerPassword(session, item);
        } catch (Exception e) {
            return new SignerPassword();
        } finally {
            session.close();
        }
    }

    public static SignerPassword GetSignerPassword(SqlSession session, SignerPassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SignerPassword();
        default:
            return SqlLabDao.GetSignerPassword(session, item);
        }
    }

    public static List<SignerPassword> GetListSignerPassword(SignerPassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListSignerPassword(session, item);
        } catch (Exception e) {
            return new ArrayList<SignerPassword>();
        } finally {
            session.close();
        }
    }

    public static List<SignerPassword> GetListSignerPassword(SqlSession session, SignerPassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SignerPassword>();
        default:
            return SqlLabDao.GetListSignerPassword(session, item);
        }
    }

    public static List<SignerPassword> SearchSignerPassword(SignerPassword item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SignerPassword>();
            default:
                return SqlLabDao.SearchSignerPassword(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SignerPassword>();
        } finally {
            session.close();
        }
    }

    public static void SaveSignerPassword(SqlSession session, SignerPassword item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveSignerPassword(session, item);
            break;
        }
    }

    // endregion SignerFilePassword Methods

    // region BusTaskResult Methods

    public static List<BusTaskResult> GetListBusTaskResult(BusTaskResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskResult(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskResult>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskResult> GetListBusTaskResult(SqlSession session, BusTaskResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskResult>();
        default:
            return SqlLabDao.GetListBusTaskResult(session, item);
        }
    }

    public static List<BusTaskResult> GetListBusTaskResultForIn(BusTaskResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskResultForIn(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskResult>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskResult> GetListBusTaskResultForIn(SqlSession session, BusTaskResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskResult>();
        default:
            return SqlLabDao.GetListBusTaskResultForIn(session, item);
        }
    }

    public static List<BusTaskResult> GetListBusTaskResultForReport(BusTaskResult item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskResultForReport(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskResult>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskResult> GetListBusTaskResultForReport(SqlSession session, BusTaskResult item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskResult>();
        default:
            return SqlLabDao.GetListBusTaskResultForReport(session, item);
        }
    }

    // endregion BusTaskResult Methods

    // region BusTaskLab Methods
    public static List<BusTaskLab> GetListBusTaskLab(BusTaskLab item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTaskLab(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTaskLab>();
        } finally {
            session.close();
        }
    }

    public static List<BusTaskLab> GetListBusTaskLab(SqlSession session, BusTaskLab item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTaskLab>();
        default:
            return SqlLabDao.GetListBusTaskLab(session, item);
        }
    }

    public static void SaveBusTaskLab(SqlSession session, BusTaskLab item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlLabDao.SaveBusTaskLab(session, item);
            break;
        }
    }
    // endregion BusTaskLab Methods

    public static void SubmitBusRecordSamples(SqlSession session, BusRecordSamples brsample) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;
        default:
            SqlDatDao.SubmitBusRecordSamples(session, brsample);
            break;
        }
    }

    public static BusRecordSamples SearchBusRecordSamples(SqlSession session, String taskid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusRecordSamples();
        default:
            return SqlDatDao.SearchBusRecordSamples(session, taskid);
        }

    }

    public static BusGetDetail GetGetSourceName(BusGetDetail bgds) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetGetSourceName(session, bgds);
        } catch (Exception e) {
            return new BusGetDetail();
        } finally {
            session.close();
        }

    }

    public static BusGetDetail GetGetSourceName(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetDetail();
        default:
            return SqlLabDao.GetGetSourceName(session, item);
        }
    }

    public static BusGetDetail GetKeepWarmName(BusGetDetail bgds) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetKeepWarmName(session, bgds);
        } catch (Exception e) {
            return new BusGetDetail();
        } finally {
            session.close();
        }

    }

    public static BusGetDetail GetKeepWarmName(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusGetDetail();
        default:
            return SqlLabDao.GetKeepWarmName(session, item);
        }
    }

}
