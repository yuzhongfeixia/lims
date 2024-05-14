package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.inner.*;
import com.gpersist.mapper.BaseMapper;

public interface InnerMapper extends BaseMapper {

    // region InnerFoodReview Methods

    public InnerFoodReview GetInnerFoodReview(InnerFoodReview item);

    public List<InnerFoodReview> SearchInnerFoodReview(InnerFoodReview item);

    public void SaveInnerFoodReview(InnerFoodReview item);

    // endregion InnerFoodReview Methods

    // region InnerMeetPart Methods

    public List<InnerMeetPart> GetListInnerMeetPart(InnerMeetPart item);

    public void SaveInnerMeetPart(InnerMeetPart item);

    // endregion InnerMeetPart Methods

    // region InnerMeetSign Methods

    public InnerMeetSign GetInnerMeetSign(InnerMeetSign item);

    public List<InnerMeetSign> SearchInnerMeetSign(InnerMeetSign item);

    public void SaveInnerMeetSign(InnerMeetSign item);

    // endregion InnerMeetSign Methods

    // region InnerReport Methods

    public InnerReport GetInnerReport(InnerReport item);

    public List<InnerReport> SearchInnerReport(InnerReport item);

    public void SaveInnerReport(InnerReport item);

    // endregion InnerReport Methods

    // region InnerInvalid Methods

    public InnerInvalid GetInnerInvalid(InnerInvalid item);

    public List<InnerInvalid> GetListInnerInvalid(InnerInvalid item);

    public List<InnerInvalid> SearchInnerInvalid(InnerInvalid item);

    public void SaveInnerInvalid(InnerInvalid item);

    // endregion InnerInvalid Methods

    // region InnerSceneWork Methods

    public InnerSceneWork GetInnerSceneWork(InnerSceneWork item);

    public List<InnerSceneWork> GetListInnerSceneWork(InnerSceneWork item);

    public List<InnerSceneWork> SearchInnerSceneWork(InnerSceneWork item);

    public void SaveInnerSceneWork(InnerSceneWork item);

    // endregion InnerSceneWork Methods

    // region InnerScene Methods

    public InnerScene GetInnerScene(InnerScene item);

    public List<InnerScene> SearchInnerScene(InnerScene item);

    public void SaveInnerScene(InnerScene item);

    // endregion InnerScene Methods

    // region InnerGroupMember Methods

    public List<InnerGroupMember> GetListInnerGroupMember(InnerGroupMember item);

    public void SaveInnerGroupMember(InnerGroupMember item);

    // endregion InnerGroupMember Methods

    // region InnerGroup Methods

    public InnerGroup GetInnerGroup(InnerGroup item);

    public List<InnerGroup> GetListInnerGroup(InnerGroup item);

    public List<InnerGroup> SearchInnerGroup(InnerGroup item);

    public void SaveInnerGroup(InnerGroup item);

    // endregion InnerGroup Methods

    // region InnerYearDetail Methods

    public List<InnerYearDetail> GetListInnerYearDetail(InnerYearDetail item);

    public void SaveInnerYearDetail(InnerYearDetail item);

    // endregion InnerYearDetail Methods

    // region InnerYear Methods

    public InnerYear GetInnerYear(InnerYear item);

    public List<InnerYear> SearchInnerYear(InnerYear item);

    public void SaveInnerYear(InnerYear item);

    // endregion InnerYear Methods
}
