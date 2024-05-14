package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.cont.*;
import com.gpersist.mapper.BaseMapper;

public interface ContMapper extends BaseMapper {

    // region ContractReviewParam Methods

    public List<ContractReviewParam> GetListContractReviewParam(ContractReviewParam item);

    public void SaveContractReviewParam(ContractReviewParam item);

    // endregion ContractReviewParam Methods

    // region ContractReviewSample Methods

    public List<ContractReviewSample> GetListContractReviewSample(ContractReviewSample item);

    public void SaveContractReviewSample(ContractReviewSample item);

    // endregion ContractReviewSample Methods

    // region ContractReviewDetail Methods

    public List<ContractReviewDetail> GetListContractReviewDetail(ContractReviewDetail item);

    public void SaveContractReviewDetail(ContractReviewDetail item);

    // endregion ContractReviewDetail Methods

    // region ContractReview Methods

    public ContractReview GetContractReview(ContractReview item);

    public List<ContractReview> SearchContractReview(ContractReview item);

    public void SaveContractReview(ContractReview item);

    // endregion ContractReview Methods
}
