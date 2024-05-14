package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.office.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlOffDao {

    // region OfficeApplyDetail Methods

    public static List<OfficeApplyDetail> GetListOfficeApplyDetail(SqlSession session, OfficeApplyDetail item) {
        com.alms.mapper.sqlserver.OffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.OffMapper.class);

        return mapper.GetListOfficeApplyDetail(item);
    }

    public static void SaveOfficeApplyDetail(SqlSession session, OfficeApplyDetail item) {
        com.alms.mapper.sqlserver.OffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.OffMapper.class);

        mapper.SaveOfficeApplyDetail(item);
    }

    // endregion OfficeApplyDetail Methods

    // region OfficeApply Methods

    public static OfficeApply GetOfficeApply(SqlSession session, OfficeApply item) {
        com.alms.mapper.sqlserver.OffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.OffMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetOfficeApply(item);
    }

    public static List<OfficeApply> SearchOfficeApply(SqlSession session, OfficeApply item) {
        com.alms.mapper.sqlserver.OffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.OffMapper.class);

        return mapper.SearchOfficeApply(item);
    }

    public static void SaveOfficeApply(SqlSession session, OfficeApply item) {
        com.alms.mapper.sqlserver.OffMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.OffMapper.class);

        mapper.SaveOfficeApply(item);
    }

    // endregion OfficeApply Methods

}
