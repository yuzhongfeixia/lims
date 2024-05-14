package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.review.MeetSign;
import com.alms.entity.review.ReviewImprove;
import com.alms.entity.review.ReviewMeetUser;
import com.alms.entity.review.ReviewNotice;
import com.alms.entity.review.ReviewPlan;
import com.alms.entity.review.ReviewRecord;
import com.alms.entity.review.ReviewReport;
import com.alms.entity.review.ReviewYear;
import com.gpersist.mapper.BaseMapper;

public interface RewMapper extends BaseMapper {

    // region ReviewYear Methods

    public ReviewYear GetReviewYear(ReviewYear item);

    public List<ReviewYear> GetListReviewYear(ReviewYear item);

    public List<ReviewYear> SearchReviewYear(ReviewYear item);

    public void SaveReviewYear(ReviewYear item);

    // endregion ReviewYear Methods

    // region ReviewPlan Methods

    public ReviewPlan GetReviewPlan(ReviewPlan item);

    public List<ReviewPlan> GetListReviewPlan(ReviewPlan item);

    public List<ReviewPlan> SearchReviewPlan(ReviewPlan item);

    public List<ReviewPlan> SearchReviewPlanForNotice(ReviewPlan item);

    public void SaveReviewPlan(ReviewPlan item);

    // endregion ReviewPlan Methods

    // region ReviewRecord Methods

    public ReviewRecord GetReviewRecord(ReviewRecord item);

    public List<ReviewRecord> GetListReviewRecord(ReviewRecord item);

    public List<ReviewRecord> SearchReviewRecord(ReviewRecord item);

    public void SaveReviewRecord(ReviewRecord item);

    // endregion ReviewRecord Methods

    // region ReviewReport Methods

    public ReviewReport GetReviewReport(ReviewReport item);

    public List<ReviewReport> GetListReviewReport(ReviewReport item);

    public List<ReviewReport> SearchReviewReport(ReviewReport item);

    public List<ReviewReport> SearchReviewReportForImprove(ReviewReport item);

    public void SaveReviewReport(ReviewReport item);

    // endregion ReviewReport Methods

    // region ReviewImprove Methods

    public ReviewImprove GetReviewImprove(ReviewImprove item);

    public List<ReviewImprove> GetListReviewImprove(ReviewImprove item);

    public List<ReviewImprove> SearchReviewImprove(ReviewImprove item);

    public void SaveReviewImprove(ReviewImprove item);

    // endregion ReviewImprove Methods

    // region ReviewNotice Methods

    public ReviewNotice GetReviewNotice(ReviewNotice item);

    public List<ReviewNotice> GetListReviewNotice(ReviewNotice item);

    public List<ReviewNotice> SearchReviewNotice(ReviewNotice item);

    public List<ReviewNotice> SearchReviewNoticeForMeet(ReviewNotice item);

    public void SaveReviewNotice(ReviewNotice item);

    // endregion ReviewNotice Methods

    // region MeetSign Methods

    public MeetSign GetMeetSign(MeetSign item);

    public List<MeetSign> GetListMeetSign(MeetSign item);

    public List<MeetSign> SearchMeetSign(MeetSign item);

    public List<MeetSign> SearchMeetSignForRecord(MeetSign item);

    public List<MeetSign> SearchMeetSignForReport(MeetSign item);

    public void SaveMeetSign(MeetSign item);

    // endregion MeetSign Methods

    // region ReviewMeetUser Methods

    public ReviewMeetUser GetReviewMeetUser(ReviewMeetUser item);

    public List<ReviewMeetUser> GetListReviewMeetUser(ReviewMeetUser item);

    public List<ReviewMeetUser> SearchReviewMeetUser(ReviewMeetUser item);

    public void SaveReviewMeetUser(ReviewMeetUser item);

    // endregion ReviewMeetUser Methods

}