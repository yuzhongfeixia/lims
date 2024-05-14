package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.crm.*;
import com.gpersist.mapper.BaseMapper;

public interface CrmMapper extends BaseMapper {

    // region CrmAccidentDeal Methods

    public CrmAccidentDeal GetCrmAccidentDeal(CrmAccidentDeal item);

    public List<CrmAccidentDeal> SearchCrmAccidentDeal(CrmAccidentDeal item);

    public List<CrmAccidentDeal> SearchCrmAccidentDealApproved(CrmAccidentDeal item);

    public void SaveCrmAccidentDeal(CrmAccidentDeal item);

    // endregion CrmAccidentDeal Methods

    // region CrmSurveyItem Methods

    public CrmSurveyItem GetCrmSurveyItem(CrmSurveyItem item);

    public List<CrmSurveyItem> SearchCrmSurveyItem(CrmSurveyItem item);

    public void SaveCrmSurveyItem(CrmSurveyItem item);

    // endregion CrmSurveyItem Methods

    // region CrmSurveyDetail Methods

    public List<CrmSurveyDetail> GetListCrmSurveyDetail(CrmSurveyDetail item);

    public void SaveCrmSurveyDetail(CrmSurveyDetail item);

    // endregion CrmSurveyDetail Methods

    // region CrmSurvey Methods

    public CrmSurvey GetCrmSurvey(CrmSurvey item);

    public List<CrmSurvey> SearchCrmSurvey(CrmSurvey item);

    public void SaveCrmSurvey(CrmSurvey item);

    // endregion CrmSurvey Methods

    // region CrmReceptDeal Methods

    public CrmReceptDeal GetCrmReceptDeal(CrmReceptDeal item);

    public List<CrmReceptDeal> SearchCrmReceptDeal(CrmReceptDeal item);

    public List<CrmReceptDeal> SearchCrmReceptDealForComplaint(CrmReceptDeal item);

    public void SaveCrmReceptDeal(CrmReceptDeal item);

    // endregion CrmReceptDeal Methods

    // region CrmRecept Methods

    public CrmRecept GetCrmRecept(CrmRecept item);

    public List<CrmRecept> SearchCrmRecept(CrmRecept item);

    public List<CrmRecept> SearchCrmReceptForDeal(CrmRecept item);

    public void SaveCrmRecept(CrmRecept item);

    // endregion CrmRecept Methods
}
