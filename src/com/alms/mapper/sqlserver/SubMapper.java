package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.sub.SubReview;
import com.alms.entity.sub.SubReviewDetail;
import com.gpersist.mapper.BaseMapper;

public interface SubMapper extends BaseMapper {

    // region SubReview Methods

    public SubReview GetSubReview(SubReview item);

    public List<SubReview> GetListSubReview(SubReview item);

    public List<SubReview> SearchSubReview(SubReview item);

    public void SaveSubReview(SubReview item);

    // endregion SubReview Methods

    // region SubReviewDetail Methods

    public SubReviewDetail GetSubReviewDetail(SubReviewDetail item);

    public List<SubReviewDetail> GetListSubReviewDetail(SubReviewDetail item);

    public List<SubReviewDetail> SearchSubReviewDetail(SubReviewDetail item);

    public void SaveSubReviewDetail(SubReviewDetail item);

    // endregion SubReviewDetail Methods

}