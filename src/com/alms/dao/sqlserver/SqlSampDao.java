package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.samp.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlSampDao {

    // region SampleBackup Methods

    public static SampleBackup GetSampleBackup(SqlSession session, SampleBackup item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSampleBackup(item);
    }

    public static List<SampleBackup> SearchSampleBackup(SqlSession session, SampleBackup item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.SearchSampleBackup(item);
    }

    public static void SaveSampleBackup(SqlSession session, SampleBackup item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleBackup(item);
    }

    // endregion SampleBackup Methods

    // region SampleEnvDetail Methods

    public static List<SampleEnvDetail> GetListSampleEnvDetail(SqlSession session, SampleEnvDetail item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.GetListSampleEnvDetail(item);
    }

    public static void SaveSampleEnvDetail(SqlSession session, SampleEnvDetail item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleEnvDetail(item);
    }

    // endregion SampleEnvDetail Methods

    // region SampleEnv Methods

    public static SampleEnv GetSampleEnv(SqlSession session, SampleEnv item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSampleEnv(item);
    }

    public static List<SampleEnv> SearchSampleEnv(SqlSession session, SampleEnv item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.SearchSampleEnv(item);
    }

    public static void SaveSampleEnv(SqlSession session, SampleEnv item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleEnv(item);
    }

    // endregion SampleEnv Methods

    // region SampleDeal Methods

    public static SampleDeal GetSampleDeal(SqlSession session, SampleDeal item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSampleDeal(item);
    }

    public static SampleDeal GetSampleDealByTaskID(SqlSession session, SampleDeal item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.GetSampleDealByTaskID(item);
    }

    public static List<SampleDeal> SearchSampleDeal(SqlSession session, SampleDeal item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.SearchSampleDeal(item);
    }

    public static void SaveSampleDeal(SqlSession session, SampleDeal item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleDeal(item);
    }

    // endregion SampleDeal Methods

    // region SampleIceDetail Methods

    public static List<SampleIceDetail> GetListSampleIceDetail(SqlSession session, SampleIceDetail item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.GetListSampleIceDetail(item);
    }

    public static void SaveSampleIceDetail(SqlSession session, SampleIceDetail item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleIceDetail(item);
    }

    // endregion SampleIceDetail Methods

    // region SampleIce Methods

    public static SampleIce GetSampleIce(SqlSession session, SampleIce item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetSampleIce(item);
    }

    public static List<SampleIce> SearchSampleIce(SqlSession session, SampleIce item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        return mapper.SearchSampleIce(item);
    }

    public static void SaveSampleIce(SqlSession session, SampleIce item) {
        com.alms.mapper.sqlserver.SampMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.SampMapper.class);

        mapper.SaveSampleIce(item);
    }

    // endregion SampleIce Methods

}
