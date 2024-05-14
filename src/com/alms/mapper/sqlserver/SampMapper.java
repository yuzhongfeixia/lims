package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.samp.*;
import com.gpersist.mapper.BaseMapper;

public interface SampMapper extends BaseMapper {

    // region SampleBackup Methods

    public SampleBackup GetSampleBackup(SampleBackup item);

    public List<SampleBackup> SearchSampleBackup(SampleBackup item);

    public void SaveSampleBackup(SampleBackup item);

    // endregion SampleBackup Methods

    // region SampleEnvDetail Methods

    public List<SampleEnvDetail> GetListSampleEnvDetail(SampleEnvDetail item);

    public void SaveSampleEnvDetail(SampleEnvDetail item);

    // endregion SampleEnvDetail Methods

    // region SampleEnv Methods

    public SampleEnv GetSampleEnv(SampleEnv item);

    public List<SampleEnv> SearchSampleEnv(SampleEnv item);

    public void SaveSampleEnv(SampleEnv item);

    // endregion SampleEnv Methods

    // region SampleDeal Methods

    public SampleDeal GetSampleDeal(SampleDeal item);

    public SampleDeal GetSampleDealByTaskID(SampleDeal item);

    public List<SampleDeal> SearchSampleDeal(SampleDeal item);

    public void SaveSampleDeal(SampleDeal item);

    // endregion SampleDeal Methods

    // region SampleIceDetail Methods

    public List<SampleIceDetail> GetListSampleIceDetail(SampleIceDetail item);

    public void SaveSampleIceDetail(SampleIceDetail item);

    // endregion SampleIceDetail Methods

    // region SampleIce Methods

    public SampleIce GetSampleIce(SampleIce item);

    public List<SampleIce> SearchSampleIce(SampleIce item);

    public void SaveSampleIce(SampleIce item);

    // endregion SampleIce Methods
}
