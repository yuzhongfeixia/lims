package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.std.*;
import com.gpersist.mapper.BaseMapper;

public interface StdMapper extends BaseMapper {

    // region StdReviewDetail Methods

    public List<StdReviewDetail> GetListStdReviewDetail(StdReviewDetail item);

    public List<StdReviewDetail> GetStdReviewDetailForChange(StdReviewDetail item);

    public void SaveStdReviewDetail(StdReviewDetail item);

    // endregion StdReviewDetail Methods

    // region StdReview Methods

    public StdReview GetStdReview(StdReview item);

    public StdReview GetBusCountry(StdReview item);

    public List<StdReview> GetListStdReview(StdReview item);

    public List<StdReview> SearchStdReview(StdReview item);

    public List<StdReview> SearchBusCountry(StdReview item);

    public List<StdReview> SearchStdReviewForChange(StdReview item);

    public void SaveStdReview(StdReview item);

    public void SaveBusCountry(StdReview item);

    // endregion StdReview Methods

    // region StdUse Methods

    public StdUse GetStdUse(StdUse item);

    public List<StdUse> GetListStdUse(StdUse item);

    public List<StdUse> GetListStdUseBySampletran(StdUse item);

    public List<StdUse> SearchStdUse(StdUse item);

    public void SaveStdUse(StdUse item);

    // endregion StdUse Methods

    // region StdNonstd Methods

    public StdNonstd GetStdNonstd(StdNonstd item);

    public List<StdNonstd> GetListStdNonstd(StdNonstd item);

    public List<StdNonstd> SearchStdNonstd(StdNonstd item);

    public void SaveStdNonstd(StdNonstd item);

    // endregion StdNonstd Methods

    // region StdMethodDevi Methods

    public StdMethodDevi GetStdMethodDevi(StdMethodDevi item);

    public List<StdMethodDevi> GetListStdMethodDevi(StdMethodDevi item);

    public List<StdMethodDevi> SearchStdMethodDevi(StdMethodDevi item);

    public void SaveStdMethodDevi(StdMethodDevi item);

    // endregion StdMethodDevi Methods

    // region StdProApply Methods

    public StdProApply GetStdProApply(StdProApply item);

    public List<StdProApply> GetListStdProApply(StdProApply item);

    public List<StdProApply> SearchStdProApply(StdProApply item);

    public void SaveStdProApply(StdProApply item);

    // endregion StdProApply Methods

    // region StdTestSure Methods

    public StdTestSure GetStdTestSure(StdTestSure item);

    public List<StdTestSure> GetListStdTestSure(StdTestSure item);

    public List<StdTestSure> SearchStdTestSure(StdTestSure item);

    public void SaveStdTestSure(StdTestSure item);

    // endregion StdTestSure Methods

    // region StdReplRecord Methods

    public StdReplRecord GetStdReplRecord(StdReplRecord item);

    public List<StdReplRecord> GetListStdReplRecord(StdReplRecord item);

    public List<StdReplRecord> SearchStdReplRecord(StdReplRecord item);

    public void SaveStdReplRecord(StdReplRecord item);

    // endregion StdReplRecord Methods

    // region StdChange Methods

    public StdChange GetStdChange(StdChange item);

    public List<StdChange> GetListStdChange(StdChange item);

    public List<StdChange> SearchStdChange(StdChange item);

    public void SaveStdChange(StdChange item);

    // endregion StdChange Methods

    // region StdSureDetail Methods

    public StdSureDetail GetStdSureDetail(StdSureDetail item);

    public List<StdSureDetail> GetListStdSureDetail(StdSureDetail item);

    public List<StdSureDetail> SearchStdSureDetail(StdSureDetail item);

    public void SaveStdSureDetail(StdSureDetail item);

    // endregion StdSureDetail Methods

}
