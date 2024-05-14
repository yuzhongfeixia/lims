package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlRewDao;
import com.alms.entity.review.MeetSign;
import com.alms.entity.review.ReviewImprove;
import com.alms.entity.review.ReviewMeetUser;
import com.alms.entity.review.ReviewNotice;
import com.alms.entity.review.ReviewPlan;
import com.alms.entity.review.ReviewRecord;
import com.alms.entity.review.ReviewReport;
import com.alms.entity.review.ReviewYear;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class RewDao {

    // region ReviewYear Methods

    public static ReviewYear GetReviewYear(ReviewYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewYear(session, item);
        } catch (Exception e) {
            return new ReviewYear();
        } finally {
            session.close();
        }
    }

    public static ReviewYear GetReviewYear(SqlSession session, ReviewYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewYear();
        default:
            return SqlRewDao.GetReviewYear(session, item);
        }
    }

    public static List<ReviewYear> GetListReviewYear(ReviewYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewYear(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewYear>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewYear> GetListReviewYear(SqlSession session, ReviewYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewYear>();
        default:
            return SqlRewDao.GetListReviewYear(session, item);
        }
    }

    public static List<ReviewYear> SearchReviewYear(ReviewYear item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewYear>();
            default:
                return SqlRewDao.SearchReviewYear(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewYear>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewYear(SqlSession session, ReviewYear item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewYear(session, item);
            break;
        }
    }

    // endregion ReviewYear Methods

    // region ReviewPlan Methods

    public static ReviewPlan GetReviewPlan(ReviewPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewPlan(session, item);
        } catch (Exception e) {
            return new ReviewPlan();
        } finally {
            session.close();
        }
    }

    public static ReviewPlan GetReviewPlan(SqlSession session, ReviewPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewPlan();
        default:
            return SqlRewDao.GetReviewPlan(session, item);
        }
    }

    public static List<ReviewPlan> GetListReviewPlan(ReviewPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewPlan(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewPlan>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewPlan> GetListReviewPlan(SqlSession session, ReviewPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewPlan>();
        default:
            return SqlRewDao.GetListReviewPlan(session, item);
        }
    }

    public static List<ReviewPlan> SearchReviewPlan(ReviewPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewPlan>();
            default:
                return SqlRewDao.SearchReviewPlan(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewPlan>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewPlan> SearchReviewPlanForNotice(ReviewPlan item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewPlan>();
            default:
                return SqlRewDao.SearchReviewPlanForNotice(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewPlan>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewPlan(SqlSession session, ReviewPlan item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewPlan(session, item);
            break;
        }
    }

    // endregion ReviewPlan Methods

    // region ReviewRecord Methods

    public static ReviewRecord GetReviewRecord(ReviewRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewRecord(session, item);
        } catch (Exception e) {
            return new ReviewRecord();
        } finally {
            session.close();
        }
    }

    public static ReviewRecord GetReviewRecord(SqlSession session, ReviewRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewRecord();
        default:
            return SqlRewDao.GetReviewRecord(session, item);
        }
    }

    public static List<ReviewRecord> GetListReviewRecord(ReviewRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewRecord(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewRecord>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewRecord> GetListReviewRecord(SqlSession session, ReviewRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewRecord>();
        default:
            return SqlRewDao.GetListReviewRecord(session, item);
        }
    }

    public static List<ReviewRecord> SearchReviewRecord(ReviewRecord item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewRecord>();
            default:
                return SqlRewDao.SearchReviewRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewRecord>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewRecord(SqlSession session, ReviewRecord item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewRecord(session, item);
            break;
        }
    }

    // endregion ReviewRecord Methods

    // region ReviewReport Methods

    public static ReviewReport GetReviewReport(ReviewReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewReport(session, item);
        } catch (Exception e) {
            return new ReviewReport();
        } finally {
            session.close();
        }
    }

    public static ReviewReport GetReviewReport(SqlSession session, ReviewReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewReport();
        default:
            return SqlRewDao.GetReviewReport(session, item);
        }
    }

    public static List<ReviewReport> GetListReviewReport(ReviewReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewReport(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewReport>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewReport> GetListReviewReport(SqlSession session, ReviewReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewReport>();
        default:
            return SqlRewDao.GetListReviewReport(session, item);
        }
    }

    public static List<ReviewReport> SearchReviewReport(ReviewReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewReport>();
            default:
                return SqlRewDao.SearchReviewReport(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewReport>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewReport> SearchReviewReportForImprove(ReviewReport item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewReport>();
            default:
                return SqlRewDao.SearchReviewReportForImprove(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewReport>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewReport(SqlSession session, ReviewReport item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewReport(session, item);
            break;
        }
    }

    // endregion ReviewReport Methods

    // region ReviewImprove Methods

    public static ReviewImprove GetReviewImprove(ReviewImprove item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewImprove(session, item);
        } catch (Exception e) {
            return new ReviewImprove();
        } finally {
            session.close();
        }
    }

    public static ReviewImprove GetReviewImprove(SqlSession session, ReviewImprove item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewImprove();
        default:
            return SqlRewDao.GetReviewImprove(session, item);
        }
    }

    public static List<ReviewImprove> GetListReviewImprove(ReviewImprove item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewImprove(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewImprove>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewImprove> GetListReviewImprove(SqlSession session, ReviewImprove item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewImprove>();
        default:
            return SqlRewDao.GetListReviewImprove(session, item);
        }
    }

    public static List<ReviewImprove> SearchReviewImprove(ReviewImprove item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewImprove>();
            default:
                return SqlRewDao.SearchReviewImprove(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewImprove>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewImprove(SqlSession session, ReviewImprove item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewImprove(session, item);
            break;
        }
    }

    // endregion ReviewImprove Methods

    // region ReviewNotice Methods

    public static ReviewNotice GetReviewNotice(ReviewNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewNotice(session, item);
        } catch (Exception e) {
            return new ReviewNotice();
        } finally {
            session.close();
        }
    }

    public static ReviewNotice GetReviewNotice(SqlSession session, ReviewNotice item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewNotice();
        default:
            return SqlRewDao.GetReviewNotice(session, item);
        }
    }

    public static List<ReviewNotice> GetListReviewNotice(ReviewNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewNotice(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewNotice>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewNotice> GetListReviewNotice(SqlSession session, ReviewNotice item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewNotice>();
        default:
            return SqlRewDao.GetListReviewNotice(session, item);
        }
    }

    public static List<ReviewNotice> SearchReviewNotice(ReviewNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewNotice>();
            default:
                return SqlRewDao.SearchReviewNotice(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewNotice>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewNotice> SearchReviewNoticeForMeet(ReviewNotice item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewNotice>();
            default:
                return SqlRewDao.SearchReviewNoticeForMeet(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewNotice>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewNotice(SqlSession session, ReviewNotice item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewNotice(session, item);
            break;
        }
    }

    // endregion ReviewNotice Methods

    // region MeetSign Methods

    public static MeetSign GetMeetSign(MeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetMeetSign(session, item);
        } catch (Exception e) {
            return new MeetSign();
        } finally {
            session.close();
        }
    }

    public static MeetSign GetMeetSign(SqlSession session, MeetSign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new MeetSign();
        default:
            return SqlRewDao.GetMeetSign(session, item);
        }
    }

    public static List<MeetSign> GetListMeetSign(MeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListMeetSign(session, item);
        } catch (Exception e) {
            return new ArrayList<MeetSign>();
        } finally {
            session.close();
        }
    }

    public static List<MeetSign> GetListMeetSign(SqlSession session, MeetSign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<MeetSign>();
        default:
            return SqlRewDao.GetListMeetSign(session, item);
        }
    }

    public static List<MeetSign> SearchMeetSign(MeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<MeetSign>();
            default:
                return SqlRewDao.SearchMeetSign(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<MeetSign>();
        } finally {
            session.close();
        }
    }

    public static List<MeetSign> SearchMeetSignForRecord(MeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<MeetSign>();
            default:
                return SqlRewDao.SearchMeetSignForRecord(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<MeetSign>();
        } finally {
            session.close();
        }
    }

    public static List<MeetSign> SearchMeetSignForReport(MeetSign item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<MeetSign>();
            default:
                return SqlRewDao.SearchMeetSignForReport(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<MeetSign>();
        } finally {
            session.close();
        }
    }

    public static void SaveMeetSign(SqlSession session, MeetSign item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveMeetSign(session, item);
            break;
        }
    }

    // endregion MeetSign Methods

    // region ReviewMeetUser Methods

    public static ReviewMeetUser GetReviewMeetUser(ReviewMeetUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetReviewMeetUser(session, item);
        } catch (Exception e) {
            return new ReviewMeetUser();
        } finally {
            session.close();
        }
    }

    public static ReviewMeetUser GetReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ReviewMeetUser();
        default:
            return SqlRewDao.GetReviewMeetUser(session, item);
        }
    }

    public static List<ReviewMeetUser> GetListReviewMeetUser(ReviewMeetUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListReviewMeetUser(session, item);
        } catch (Exception e) {
            return new ArrayList<ReviewMeetUser>();
        } finally {
            session.close();
        }
    }

    public static List<ReviewMeetUser> GetListReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<ReviewMeetUser>();
        default:
            return SqlRewDao.GetListReviewMeetUser(session, item);
        }
    }

    public static List<ReviewMeetUser> SearchReviewMeetUser(ReviewMeetUser item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<ReviewMeetUser>();
            default:
                return SqlRewDao.SearchReviewMeetUser(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<ReviewMeetUser>();
        } finally {
            session.close();
        }
    }

    public static void SaveReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlRewDao.SaveReviewMeetUser(session, item);
            break;
        }
    }

    // endregion ReviewMeetUser Methods

}