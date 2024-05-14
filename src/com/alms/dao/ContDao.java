package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlContDao;
import com.alms.entity.cont.*;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class ContDao {

    // region ContractReviewParam Methods

    public static List<ContractReviewParam> GetListContractReviewParam(ContractReviewParam item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ContractReviewParam>();
            default:
                return SqlContDao.GetListContractReviewParam(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ContractReviewParam>();
        } finally {
            session.close();
        }
    }

    public static void SaveContractReviewParam(SqlSession session, ContractReviewParam item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlContDao.SaveContractReviewParam(session, item);
            break;
        }
    }

    // endregion ContractReviewParam Methods

    // region ContractReviewSample Methods

    public static List<ContractReviewSample> GetListContractReviewSample(ContractReviewSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ContractReviewSample>();
            default:
                return SqlContDao.GetListContractReviewSample(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ContractReviewSample>();
        } finally {
            session.close();
        }
    }

    public static void SaveContractReviewSample(SqlSession session, ContractReviewSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlContDao.SaveContractReviewSample(session, item);
            break;
        }
    }

    // endregion ContractReviewSample Methods

    // region ContractReviewDetail Methods

    public static List<ContractReviewDetail> GetListContractReviewDetail(ContractReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ContractReviewDetail>();
            default:
                return SqlContDao.GetListContractReviewDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ContractReviewDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveContractReviewDetail(SqlSession session, ContractReviewDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlContDao.SaveContractReviewDetail(session, item);
            break;
        }
    }

    // endregion ContractReviewDetail Methods

    // region ContractReview Methods

    public static ContractReview GetContractReview(ContractReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetContractReview(session, item);
        } catch (Exception e) {
            return new ContractReview();
        } finally {
            session.close();
        }
    }

    public static ContractReview GetContractReview(SqlSession session, ContractReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ContractReview();
        default:
            return SqlContDao.GetContractReview(session, item);
        }
    }

    public static List<ContractReview> SearchContractReview(ContractReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ContractReview>();
            default:
                return SqlContDao.SearchContractReview(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ContractReview>();
        } finally {
            session.close();
        }
    }

    public static void SaveContractReview(SqlSession session, ContractReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlContDao.SaveContractReview(session, item);
            break;
        }
    }

    // endregion ContractReview Methods

}
