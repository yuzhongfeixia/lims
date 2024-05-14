package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.office.*;
import com.gpersist.mapper.BaseMapper;

public interface OffMapper extends BaseMapper {

    // region OfficeApplyDetail Methods

    public List<OfficeApplyDetail> GetListOfficeApplyDetail(OfficeApplyDetail item);

    public void SaveOfficeApplyDetail(OfficeApplyDetail item);

    // endregion OfficeApplyDetail Methods

    // region OfficeApply Methods

    public OfficeApply GetOfficeApply(OfficeApply item);

    public List<OfficeApply> SearchOfficeApply(OfficeApply item);

    public void SaveOfficeApply(OfficeApply item);

    // endregion OfficeApply Methods

}
