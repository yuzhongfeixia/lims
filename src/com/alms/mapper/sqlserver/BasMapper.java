package com.alms.mapper.sqlserver;

import java.util.List;

import com.alms.entity.bas.AbilityForm;
import com.alms.entity.bas.BasArea;
import com.alms.entity.bas.BasCatalogParameter;
import com.alms.entity.bas.BasCity;
import com.alms.entity.bas.BasCountry;
import com.alms.entity.bas.BasJudge;
import com.alms.entity.bas.BasLabParameter;
import com.alms.entity.bas.BasLocation;
import com.alms.entity.bas.BasMainParameter;
import com.alms.entity.bas.BasParameter;
import com.alms.entity.bas.BasProvince;
import com.alms.entity.bas.BasSample;
import com.alms.entity.bas.BasSampleCatalog;
import com.alms.entity.bas.BasSampleJudge;
import com.alms.entity.bas.BasSampleMain;
import com.alms.entity.bas.BasSampleParameter;
import com.alms.entity.bas.BasSampleReplace;
import com.alms.entity.bas.BasSampleStand;
import com.alms.entity.bas.BasSampleTest;
import com.alms.entity.bas.BasTest;
import com.alms.entity.bas.BasTestCollect;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusSampleParam;
import com.gpersist.entity.user.SysUser;
import com.gpersist.mapper.BaseMapper;

public interface BasMapper extends BaseMapper {

    // region BasSampleTest Methods

    public List<BasSampleTest> GetListBasSampleTest(BasSampleTest item);

    public List<BasSampleTest> GetListBasSampleTestBySample(BasSampleTest item);

    public void SaveBasSampleTest(BasSampleTest item);

    public List<BasSampleTest> GetListBasSampleTestByParam(BasSampleTest item);

    public List<BasTestCollect> GetListBasTestCollect(BasTestCollect item);

    // endregion BasSampleTest Methods

    // region BasSampleJudge Methods

    public List<BasSampleJudge> GetListBasSampleJudge(BasSampleJudge item);

    public void SaveBasSampleJudge(BasSampleJudge item);

    public List<BasSampleJudge> GetListBasSampleJudgeByParam(BasSampleJudge item);

    // endregion BasSampleJudge Methods

    // region BasSampleParameter Methods

    public List<BasSampleParameter> GetListBasSampleParameter(BasSampleParameter item);

    public List<BasSampleParameter> GetListBasSampleParameterSure(BasSampleParameter item);

    public List<BasSampleParameter> GetListBasSampleDetail(BasSampleParameter item);

    public List<BasSampleParameter> GetListBasSampleParameterDetail(String sampleid);

    public void SaveBasSampleParameter(BasSampleParameter item);

    // endregion BasSampleParameter Methods

    // region BasMainParameter Methods

    public List<BasMainParameter> GetListBasMainParameter(BasMainParameter item);

    public void SaveBasMainParameter(BasMainParameter item);

    public BasMainParameter GetBasMainParameter(BasMainParameter item);

    // endregion BasMainParameter Methods

    // region BasCatalogParameter Methods

    public List<BasCatalogParameter> GetListBasCatalogParameter(BasCatalogParameter item);

    public void SaveBasCatalogParameter(BasCatalogParameter item);

    // endregion BasCatalogParameter Methods

    // region BasTest Methods

    public BasTest GetBasTest(BasTest item);

    public List<BasTest> GetListBasTest();

    public List<BasTest> SearchBasTest(BasTest item);

    public void SaveBasTest(BasTest item);

    // endregion BasTest Methods

    // region AbilityForm Methods

    public AbilityForm GetAbilityForm(AbilityForm item);

    public List<AbilityForm> GetListAbilityForm();

    public List<AbilityForm> SearchAbilityForm(AbilityForm item);

    public void SaveAbilityForm(AbilityForm item);

    // endregion AbilityForm Methods

    // region BasJudge Methods

    public BasJudge GetBasJudge(BasJudge item);

    public List<BasJudge> GetListBasJudge();

    public List<BasJudge> SearchBasJudge(BasJudge item);

    public void SaveBasJudge(BasJudge item);

    // endregion BasJudge Methods

    // region BasParameter Methods

    public BasParameter GetBasParameter(BasParameter item);

    public List<BasParameter> GetListBasParameter();

    public List<BasParameter> GetListBasParameterByLab(BasParameter item);

    public List<BasParameter> SearchBasParameter(BasParameter item);

    public void SaveBasParameter(BasParameter item);

    // endregion BasParameter Methods

    // region BasSample Methods

    public BasSample GetBasSample(BasSample item);

    public List<BasSample> GetListBasSample();

    public List<BasSample> SearchBasSample(BasSample item);

    public void SaveBasSample(BasSample item);

    public List<BasSample> GetSampleBySampleMain(BasSample item);

    // endregion BasSample Methods

    // region BasSampleMain Methods

    public BasSampleMain GetBasSampleMain(BasSampleMain item);

    public List<BasSampleMain> GetListBasSampleMain();

    public List<BasSampleMain> SearchBasSampleMain(BasSampleMain item);

    public void SaveBasSampleMain(BasSampleMain item);

    public List<BasSampleMain> GetSampleMainBySampleCate(BasSampleMain item);

    // endregion BasSampleMain Methods

    // region BasSampleCatalog Methods

    public BasSampleCatalog GetBasSampleCatalog(BasSampleCatalog item);

    public BasSampleCatalog GetBasSampleCatalogBySampleID(BasSampleCatalog item);

    public List<BasSampleCatalog> GetListBasSampleCatalog();

    public List<BasSampleCatalog> SearchBasSampleCatalog(BasSampleCatalog item);

    public void SaveBasSampleCatalog(BasSampleCatalog item);

    public List<BasSampleCatalog> GetBasSampleCate();

    // endregion BasSampleCatalog Methods

    // region BusTestedUnit Methods

    public BusTestedUnit GetBusTestedUnit(BusTestedUnit item);

    public List<BusTestedUnit> GetListBusTestedUnit(BusTestedUnit item);

    public List<BusTestedUnit> SearchBusTestedUnit(BusTestedUnit item);

    public void SaveBusTestedUnit(BusTestedUnit item);

    // endregion BusTestedUnit Methods

    // region BasLabParameter Methods

    public BasLabParameter GetBasLabParameter(BasLabParameter item);

    public BasLabParameter GetBasLabParameterForInfo(BasLabParameter item);

    public List<BasLabParameter> GetListBasLabParameter(BasLabParameter item);

    public List<BasLabParameter> GetListBasLabParameterForDept(BasLabParameter item);

    public List<BasLabParameter> SearchBasLabParameter(BasLabParameter item);

    public void SaveBasLabParameter(BasLabParameter item);

    public List<BasLabParameter> GetListBasLabBySample(BasLabParameter item);

    // 根据实验室和检测参数查询
    public List<BasLabParameter> GetBasLabParameterByLab(BasLabParameter item);

    // endregion BasLabParameter Methods

    public List<BusSampleParam> GetParameterByGetDetail(BusGetDetail item);

    public List<SysUser> GetUserByDept(String deptid);

    // region BasArea Methods

    public BasArea GetBasArea(BasArea item);

    public List<BasArea> SearchBasArea(BasArea item);

    public void SaveBasArea(BasArea item);

    public List<BasArea> GetBasAreaByCity(BasArea item);

    // endregion BusArea Methods

    // region BasCity Methods

    public BasCity GetBasCity(BasCity item);

    public List<BasCity> SearchBasCity(BasCity item);

    public void SaveBasCity(BasCity item);

    public List<BasCity> GetBasCityByProv(BasCity item);

    // endregion BasCity Methods

    // region BasProvince Methods

    public BasProvince GetBasProvince(BasProvince item);

    public List<BasProvince> SearchBasProvince(BasProvince item);

    public void SaveBasProvince(BasProvince item);

    // endregion BasProvince Methods

    // region BasCountry Methods

    public BasCountry GetBasCountry(BasCountry item);

    public List<BasCountry> SearchBasCountry(BasCountry item);

    public void SaveBasCountry(BasCountry item);

    // endregion BasCountry Methods

    // region BasLocation Methods

    public List<BasLocation> GetBasLocation(BasLocation item);

    // endregion BasLocation Methods

    // region BasSampleStand Methods

    public BasSampleStand GetSampleStandCount(BasSampleStand item);

    public List<BasSampleStand> GetSampleStandByLevel(BasSampleStand item);

    public List<BasSampleStand> GetListBasSampleStand(BasSampleStand item);

    public void SaveBasSampleStand(BasSampleStand item);

    // endregion BasSampleStand Methods

    // region BasSampleReplace Methods

    public BasSampleReplace GetBasSampleReplace(BasSampleReplace item);

    public List<BasSampleReplace> GetListBasSampleReplace();

    public List<BasSampleReplace> GetListBasSampleReplaceBySampleID(BasSampleReplace item);

    public List<BasSampleReplace> SearchBasSampleReplace(BasSampleReplace item);

    public void SaveBasSampleReplace(BasSampleReplace item);

    // endregion BasSampleMain Methods
}
