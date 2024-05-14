package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlSubDao;
import com.alms.entity.sub.SubReview;
import com.alms.entity.sub.SubReviewDetail;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class SubDao {

    // region SubReview Methods

    public static SubReview GetSubReview(SubReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSubReview(session, item);
        } catch (Exception e) {
            return new SubReview();
        } finally {
            session.close();
        }
    }

    public static SubReview GetSubReview(SqlSession session, SubReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SubReview();
        default:
            return SqlSubDao.GetSubReview(session, item);
        }
    }

    public static List<SubReview> GetListSubReview(SubReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListSubReview(session, item);
        } catch (Exception e) {
            return new ArrayList<SubReview>();
        } finally {
            session.close();
        }
    }

    public static List<SubReview> GetListSubReview(SqlSession session, SubReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SubReview>();
        default:
            return SqlSubDao.GetListSubReview(session, item);
        }
    }

    public static List<SubReview> SearchSubReview(SubReview item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SubReview>();
            default:
                return SqlSubDao.SearchSubReview(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SubReview>();
        } finally {
            session.close();
        }
    }

    public static void SaveSubReview(SqlSession session, SubReview item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSubDao.SaveSubReview(session, item);
            break;
        }
    }

    // endregion SubReview Methods

    // region SubReviewDetail Methods

    public static SubReviewDetail GetSubReviewDetail(SubReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSubReviewDetail(session, item);
        } catch (Exception e) {
            return new SubReviewDetail();
        } finally {
            session.close();
        }
    }

    public static SubReviewDetail GetSubReviewDetail(SqlSession session, SubReviewDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new SubReviewDetail();
        default:
            return SqlSubDao.GetSubReviewDetail(session, item);
        }
    }

    public static List<SubReviewDetail> GetListSubReviewDetail(SubReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListSubReviewDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<SubReviewDetail>();
        } finally {
            session.close();
        }
    }

    public static List<SubReviewDetail> GetListSubReviewDetail(SqlSession session, SubReviewDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<SubReviewDetail>();
        default:
            return SqlSubDao.GetListSubReviewDetail(session, item);
        }
    }

    public static List<SubReviewDetail> SearchSubReviewDetail(SubReviewDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SubReviewDetail>();
            default:
                return SqlSubDao.SearchSubReviewDetail(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<SubReviewDetail>();
        } finally {
            session.close();
        }
    }

    public static void SaveSubReviewDetail(SqlSession session, SubReviewDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlSubDao.SaveSubReviewDetail(session, item);
            break;
        }
    }

    // endregion SubReviewDetail Methods

}