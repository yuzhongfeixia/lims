package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.inc.*;
import com.gpersist.mapper.BaseMapper;

public interface IncMapper extends BaseMapper {

    // region IncTestEnv Methods

    public IncTestEnv GetIncTestEnv(IncTestEnv item);

    public List<IncTestEnv> SearchIncTestEnv(IncTestEnv item);

    public void SaveIncTestEnv(IncTestEnv item);

    // endregion IncTestEnv Methods

    // region IncCheckSafe Methods

    public IncCheckSafe GetIncCheckSafe(IncCheckSafe item);

    public List<IncCheckSafe> SearchIncCheckSafe(IncCheckSafe item);

    public void SaveIncCheckSafe(IncCheckSafe item);

    // endregion IncCheckSafe Methods
}
