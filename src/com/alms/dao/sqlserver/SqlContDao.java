package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.cont.*;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlContDao {

    // region ContractReviewParam Methods

    public static List<ContractReviewParam> GetListContractReviewParam(SqlSession session, ContractReviewParam item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        return mapper.GetListContractReviewParam(item);
    }

    public static void SaveContractReviewParam(SqlSession session, ContractReviewParam item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        mapper.SaveContractReviewParam(item);
    }

    // endregion ContractReviewParam Methods

    // region ContractReviewSample Methods

    public static List<ContractReviewSample> GetListContractReviewSample(SqlSession session,
            ContractReviewSample item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        return mapper.GetListContractReviewSample(item);
    }

    public static void SaveContractReviewSample(SqlSession session, ContractReviewSample item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        mapper.SaveContractReviewSample(item);
    }

    // endregion ContractReviewSample Methods

    // region ContractReviewDetail Methods

    public static List<ContractReviewDetail> GetListContractReviewDetail(SqlSession session,
            ContractReviewDetail item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        return mapper.GetListContractReviewDetail(item);
    }

    public static void SaveContractReviewDetail(SqlSession session, ContractReviewDetail item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        mapper.SaveContractReviewDetail(item);
    }

    // endregion ContractReviewDetail Methods

    // region ContractReview Methods

    public static ContractReview GetContractReview(SqlSession session, ContractReview item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetContractReview(item);
    }

    public static List<ContractReview> SearchContractReview(SqlSession session, ContractReview item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        return mapper.SearchContractReview(item);
    }

    public static void SaveContractReview(SqlSession session, ContractReview item) {
        com.alms.mapper.sqlserver.ContMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.ContMapper.class);

        mapper.SaveContractReview(item);
    }

    // endregion ContractReview Methods

}
