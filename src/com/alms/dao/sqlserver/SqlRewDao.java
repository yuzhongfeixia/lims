package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.entity.review.MeetSign;
import com.alms.entity.review.ReviewImprove;
import com.alms.entity.review.ReviewMeetUser;
import com.alms.entity.review.ReviewNotice;
import com.alms.entity.review.ReviewPlan;
import com.alms.entity.review.ReviewRecord;
import com.alms.entity.review.ReviewReport;
import com.alms.entity.review.ReviewYear;
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlRewDao {

    // region ReviewYear Methods

    public static ReviewYear GetReviewYear(SqlSession session, ReviewYear item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewYear(item);
    }

    public static List<ReviewYear> GetListReviewYear(SqlSession session, ReviewYear item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewYear(item);
    }

    public static List<ReviewYear> SearchReviewYear(SqlSession session, ReviewYear item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewYear(item);
    }

    public static void SaveReviewYear(SqlSession session, ReviewYear item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewYear(item);
    }

    // endregion ReviewYear Methods

    // region ReviewPlan Methods

    public static ReviewPlan GetReviewPlan(SqlSession session, ReviewPlan item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewPlan(item);
    }

    public static List<ReviewPlan> GetListReviewPlan(SqlSession session, ReviewPlan item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewPlan(item);
    }

    public static List<ReviewPlan> SearchReviewPlan(SqlSession session, ReviewPlan item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewPlan(item);
    }

    public static List<ReviewPlan> SearchReviewPlanForNotice(SqlSession session, ReviewPlan item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewPlanForNotice(item);
    }

    public static void SaveReviewPlan(SqlSession session, ReviewPlan item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewPlan(item);
    }

    // endregion ReviewPlan Methods

    // region ReviewRecord Methods

    public static ReviewRecord GetReviewRecord(SqlSession session, ReviewRecord item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewRecord(item);
    }

    public static List<ReviewRecord> GetListReviewRecord(SqlSession session, ReviewRecord item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewRecord(item);
    }

    public static List<ReviewRecord> SearchReviewRecord(SqlSession session, ReviewRecord item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewRecord(item);
    }

    public static void SaveReviewRecord(SqlSession session, ReviewRecord item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewRecord(item);
    }

    // endregion ReviewRecord Methods

    // region ReviewReport Methods

    public static ReviewReport GetReviewReport(SqlSession session, ReviewReport item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewReport(item);
    }

    public static List<ReviewReport> GetListReviewReport(SqlSession session, ReviewReport item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewReport(item);
    }

    public static List<ReviewReport> SearchReviewReport(SqlSession session, ReviewReport item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewReport(item);
    }

    public static List<ReviewReport> SearchReviewReportForImprove(SqlSession session, ReviewReport item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewReportForImprove(item);
    }

    public static void SaveReviewReport(SqlSession session, ReviewReport item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewReport(item);
    }

    // endregion ReviewReport Methods

    // region ReviewImprove Methods

    public static ReviewImprove GetReviewImprove(SqlSession session, ReviewImprove item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewImprove(item);
    }

    public static List<ReviewImprove> GetListReviewImprove(SqlSession session, ReviewImprove item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewImprove(item);
    }

    public static List<ReviewImprove> SearchReviewImprove(SqlSession session, ReviewImprove item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewImprove(item);
    }

    public static void SaveReviewImprove(SqlSession session, ReviewImprove item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewImprove(item);
    }

    // endregion ReviewImprove Methods

    // region ReviewNotice Methods

    public static ReviewNotice GetReviewNotice(SqlSession session, ReviewNotice item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewNotice(item);
    }

    public static List<ReviewNotice> GetListReviewNotice(SqlSession session, ReviewNotice item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewNotice(item);
    }

    public static List<ReviewNotice> SearchReviewNotice(SqlSession session, ReviewNotice item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewNotice(item);
    }

    public static List<ReviewNotice> SearchReviewNoticeForMeet(SqlSession session, ReviewNotice item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewNoticeForMeet(item);
    }

    public static void SaveReviewNotice(SqlSession session, ReviewNotice item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewNotice(item);
    }

    // endregion ReviewNotice Methods

    // region MeetSign Methods

    public static MeetSign GetMeetSign(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetMeetSign(item);
    }

    public static List<MeetSign> GetListMeetSign(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListMeetSign(item);
    }

    public static List<MeetSign> SearchMeetSign(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchMeetSign(item);
    }

    public static List<MeetSign> SearchMeetSignForRecord(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchMeetSignForRecord(item);
    }

    public static List<MeetSign> SearchMeetSignForReport(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchMeetSignForReport(item);
    }

    public static void SaveMeetSign(SqlSession session, MeetSign item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveMeetSign(item);
    }

    // endregion MeetSign Methods

    // region ReviewMeetUser Methods

    public static ReviewMeetUser GetReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetReviewMeetUser(item);
    }

    public static List<ReviewMeetUser> GetListReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.GetListReviewMeetUser(item);
    }

    public static List<ReviewMeetUser> SearchReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        return mapper.SearchReviewMeetUser(item);
    }

    public static void SaveReviewMeetUser(SqlSession session, ReviewMeetUser item) {
        com.alms.mapper.sqlserver.RewMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.RewMapper.class);

        mapper.SaveReviewMeetUser(item);
    }

    // endregion ReviewMeetUser Methods

}