package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlSampDao;
import com.alms.entity.samp.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class SampDao {

    // region SampleBackup Methods

    public static SampleBackup GetSampleBackup(SampleBackup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleBackup(session, item);
        } catch (Exception e) {
            return new SampleBackup();
        } finally {
            session.close();
        }
    }

    public static SampleBackup GetSampleBackup(SqlSession session, SampleBackup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SampleBackup();
        default:
            return SqlSampDao.GetSampleBackup(session, item);
        }
    }

    public static List<SampleBackup> SearchSampleBackup(SampleBackup item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleBackup>();
            default:
                return SqlSampDao.SearchSampleBackup(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleBackup>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleBackup(SqlSession session, SampleBackup item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleBackup(session, item);
            break;
        }
    }

    // endregion SampleBackup Methods

    // region SampleEnvDetail Methods

    public static List<SampleEnvDetail> GetListSampleEnvDetail(SampleEnvDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleEnvDetail>();
            default:
                return SqlSampDao.GetListSampleEnvDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleEnvDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleEnvDetail(SqlSession session, SampleEnvDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleEnvDetail(session, item);
            break;
        }
    }

    // endregion SampleEnvDetail Methods

    // region SampleEnv Methods

    public static SampleEnv GetSampleEnv(SampleEnv item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleEnv(session, item);
        } catch (Exception e) {
            return new SampleEnv();
        } finally {
            session.close();
        }
    }

    public static SampleEnv GetSampleEnv(SqlSession session, SampleEnv item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SampleEnv();
        default:
            return SqlSampDao.GetSampleEnv(session, item);
        }
    }

    public static List<SampleEnv> SearchSampleEnv(SampleEnv item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleEnv>();
            default:
                return SqlSampDao.SearchSampleEnv(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleEnv>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleEnv(SqlSession session, SampleEnv item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleEnv(session, item);
            break;
        }
    }

    // endregion SampleEnv Methods

    // region SampleDeal Methods

    public static SampleDeal GetSampleDeal(SampleDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleDeal(session, item);
        } catch (Exception e) {
            return new SampleDeal();
        } finally {
            session.close();
        }
    }

    public static SampleDeal GetSampleDeal(SqlSession session, SampleDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SampleDeal();
        default:
            return SqlSampDao.GetSampleDeal(session, item);
        }
    }

    public static SampleDeal GetSampleDealByTaskID(SampleDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleDealByTaskID(session, item);
        } catch (Exception e) {
            return new SampleDeal();
        } finally {
            session.close();
        }
    }

    public static SampleDeal GetSampleDealByTaskID(SqlSession session, SampleDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SampleDeal();
        default:
            return SqlSampDao.GetSampleDealByTaskID(session, item);
        }
    }

    public static List<SampleDeal> SearchSampleDeal(SampleDeal item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleDeal>();
            default:
                return SqlSampDao.SearchSampleDeal(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleDeal>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleDeal(SqlSession session, SampleDeal item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleDeal(session, item);
            break;
        }
    }

    // endregion SampleDeal Methods

    // region SampleIceDetail Methods

    public static List<SampleIceDetail> GetListSampleIceDetail(SampleIceDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleIceDetail>();
            default:
                return SqlSampDao.GetListSampleIceDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleIceDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleIceDetail(SqlSession session, SampleIceDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleIceDetail(session, item);
            break;
        }
    }

    // endregion SampleIceDetail Methods

    // region SampleIce Methods

    public static SampleIce GetSampleIce(SampleIce item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleIce(session, item);
        } catch (Exception e) {
            return new SampleIce();
        } finally {
            session.close();
        }
    }

    public static SampleIce GetSampleIce(SqlSession session, SampleIce item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SampleIce();
        default:
            return SqlSampDao.GetSampleIce(session, item);
        }
    }

    public static List<SampleIce> SearchSampleIce(SampleIce item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SampleIce>();
            default:
                return SqlSampDao.SearchSampleIce(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SampleIce>();
        } finally {
            session.close();
        }
    }

    public static void SaveSampleIce(SqlSession session, SampleIce item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSampDao.SaveSampleIce(session, item);
            break;
        }
    }

    // endregion SampleIce Methods

}
