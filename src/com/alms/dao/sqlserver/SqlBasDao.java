package com.alms.dao.sqlserver;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

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
import com.gpersist.enums.ActionGetType;
import com.gpersist.utils.DBUtils;

public class SqlBasDao {

    // region BasSampleTest Methods

    public static List<BasSampleTest> GetListBasSampleTest(SqlSession session, BasSampleTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleTest(item);
    }

    public static List<BasSampleTest> GetListBasSampleTestBySample(SqlSession session, BasSampleTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetListBasSampleTestBySample(item);
    }

    public static void SaveBasSampleTest(SqlSession session, BasSampleTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleTest(item);
    }

    public static List<BasSampleTest> GetListBasSampleTestByParam(SqlSession session, BasSampleTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetListBasSampleTestByParam(item);
    }

    public static List<BasTestCollect> GetListBasTestCollect(SqlSession session, BasTestCollect item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasTestCollect(item);
    }

    // endregion BasSampleTest Methods

    // region BasSampleJudge Methods

    public static List<BasSampleJudge> GetListBasSampleJudge(SqlSession session, BasSampleJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleJudge(item);
    }

    public static void SaveBasSampleJudge(SqlSession session, BasSampleJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleJudge(item);
    }

    public static List<BasSampleJudge> GetListBasSampleJudgeByParam(SqlSession session, BasSampleJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetListBasSampleJudgeByParam(item);
    }

    // endregion BasSampleJudge Methods

    // region BasSampleParameter Methods

    public static List<BasSampleParameter> GetListBasSampleParameter(SqlSession session, BasSampleParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleParameter(item);
    }

    public static List<BasSampleParameter> GetListBasSampleParameterSure(SqlSession session, BasSampleParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleParameterSure(item);
    }

    public static List<BasSampleParameter> GetListBasSampleDetail(SqlSession session, BasSampleParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleDetail(item);
    }

    public static List<BasSampleParameter> GetListBasSampleParameterDetail(SqlSession session, String sampleid) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleParameterDetail(sampleid);
    }

    public static void SaveBasSampleParameter(SqlSession session, BasSampleParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleParameter(item);
    }

    // endregion BasSampleParameter Methods

    // region BasMainParameter Methods

    public static BasMainParameter GetBasMainParameter(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasMainParameter(item);
    }

    public static List<BasMainParameter> GetListBasMainParameter(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasMainParameter(item);
    }

    public static void SaveBasMainParameter(SqlSession session, BasMainParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasMainParameter(item);
    }

    // endregion BasMainParameter Methods

    // region BasCatalogParameter Methods

    public static List<BasCatalogParameter> GetListBasCatalogParameter(SqlSession session, BasCatalogParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasCatalogParameter(item);
    }

    public static void SaveBasCatalogParameter(SqlSession session, BasCatalogParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasCatalogParameter(item);
    }

    // endregion BasCatalogParameter Methods

    // region BasTest Methods

    public static BasTest GetBasTest(SqlSession session, BasTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasTest(item);
    }

    public static List<BasTest> GetListBasTest(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasTest();
    }

    public static List<BasTest> SearchBasTest(SqlSession session, BasTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasTest(item);
    }

    public static void SaveBasTest(SqlSession session, BasTest item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasTest(item);
    }

    // endregion BasTest Methods

    // region AbilityForm Methods

    public static AbilityForm GetAbilityForm(SqlSession session, AbilityForm item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetAbilityForm(item);
    }

    public static List<AbilityForm> GetListAbilityForm(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListAbilityForm();
    }

    public static List<AbilityForm> SearchAbilityForm(SqlSession session, AbilityForm item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchAbilityForm(item);
    }

    public static void SaveAbilityForm(SqlSession session, AbilityForm item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveAbilityForm(item);
    }

    // endregion AbilityForm Methods

    // region BasJudge Methods

    public static BasJudge GetBasJudge(SqlSession session, BasJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasJudge(item);
    }

    public static List<BasJudge> GetListBasJudge(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasJudge();
    }

    public static List<BasJudge> SearchBasJudge(SqlSession session, BasJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasJudge(item);
    }

    public static void SaveBasJudge(SqlSession session, BasJudge item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasJudge(item);
    }

    // endregion BasJudge Methods

    // region BasParameter Methods

    public static BasParameter GetBasParameter(SqlSession session, BasParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasParameter(item);
    }

    public static List<BasParameter> GetListBasParameter(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasParameter();
    }

    public static List<BasParameter> GetListBasParameterByLab(SqlSession session, BasParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasParameterByLab(item);
    }

    public static List<BasParameter> SearchBasParameter(SqlSession session, BasParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasParameter(item);
    }

    public static void SaveBasParameter(SqlSession session, BasParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasParameter(item);
    }

    // endregion BasParameter Methods

    // region BasSample Methods

    public static BasSample GetBasSample(SqlSession session, BasSample item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasSample(item);
    }

    public static List<BasSample> GetListBasSample(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSample();
    }

    public static List<BasSample> SearchBasSample(SqlSession session, BasSample item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasSample(item);
    }

    public static void SaveBasSample(SqlSession session, BasSample item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSample(item);
    }

    public static List<BasSample> GetSampleBySampleMain(SqlSession session, BasSample item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetSampleBySampleMain(item);
    }

    // endregion BasSample Methods

    // region BasSampleMain Methods

    public static BasSampleMain GetBasSampleMain(SqlSession session, BasSampleMain item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasSampleMain(item);
    }

    public static List<BasSampleMain> GetListBasSampleMain(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleMain();
    }

    public static List<BasSampleMain> SearchBasSampleMain(SqlSession session, BasSampleMain item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasSampleMain(item);
    }

    public static void SaveBasSampleMain(SqlSession session, BasSampleMain item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleMain(item);
    }

    public static List<BasSampleMain> GetSampleMainBySampleCate(SqlSession session, BasSampleMain item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetSampleMainBySampleCate(item);
    }

    // endregion BasSampleMain Methods

    // region BasSampleCatalog Methods

    public static BasSampleCatalog GetBasSampleCatalog(SqlSession session, BasSampleCatalog item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasSampleCatalog(item);
    }

    public static BasSampleCatalog GetBasSampleCatalogBySampleID(SqlSession session, BasSampleCatalog item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasSampleCatalogBySampleID(item);
    }

    public static List<BasSampleCatalog> GetListBasSampleCatalog(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleCatalog();
    }

    public static List<BasSampleCatalog> SearchBasSampleCatalog(SqlSession session, BasSampleCatalog item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasSampleCatalog(item);
    }

    public static void SaveBasSampleCatalog(SqlSession session, BasSampleCatalog item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleCatalog(item);
    }

    public static List<BasSampleCatalog> GetBasSampleCate(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetBasSampleCate();
    }

    // endregion BasSampleCatalog Methods

    // region BusTestedUnit Methods

    public static BusTestedUnit GetBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBusTestedUnit(item);
    }

    public static List<BusTestedUnit> GetListBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.full.toString());
        return mapper.GetListBusTestedUnit(item);
    }

    public static List<BusTestedUnit> SearchBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBusTestedUnit(item);
    }

    public static void SaveBusTestedUnit(SqlSession session, BusTestedUnit item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBusTestedUnit(item);
    }

    // endregion BusTestedUnit Methods

    // region BasLabParameter Methods

    public static BasLabParameter GetBasLabParameter(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasLabParameter(item);
    }

    public static BasLabParameter GetBasLabParameterForInfo(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetBasLabParameterForInfo(item);
    }

    public static List<BasLabParameter> GetListBasLabParameter(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasLabParameter(item);
    }

    public static List<BasLabParameter> GetListBasLabParameterForDept(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasLabParameterForDept(item);
    }

    public static List<BasLabParameter> SearchBasLabParameter(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasLabParameter(item);
    }

    public static void SaveBasLabParameter(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasLabParameter(item);
    }

    public static List<BasLabParameter> GetBasLabParameterByLab(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetBasLabParameterByLab(item);
    }

    public static List<BasLabParameter> GetListBasLabBySample(SqlSession session, BasLabParameter item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetListBasLabBySample(item);
    }

    // endregion BasLabParameter Methods

    public static List<BusSampleParam> GetParameterByGetDetail(SqlSession session, BusGetDetail item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetParameterByGetDetail(item);
    }

    public static List<SysUser> GetUserByDept(SqlSession session, String deptid) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetUserByDept(deptid);
    }

    // region BasArea Methods

    public static BasArea GetBasArea(SqlSession session, BasArea item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasArea(item);
    }

    public static List<BasArea> SearchBasArea(SqlSession session, BasArea item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasArea(item);
    }

    public static void SaveBasArea(SqlSession session, BasArea item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasArea(item);
    }

    public static List<BasArea> GetBasAreaByCity(SqlSession session, BasArea item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetBasAreaByCity(item);
    }

    // endregion BasArea Methods

    // region BasCity Methods

    public static BasCity GetBasCity(SqlSession session, BasCity item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasCity(item);
    }

    public static List<BasCity> SearchBasCity(SqlSession session, BasCity item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasCity(item);
    }

    public static void SaveBasCity(SqlSession session, BasCity item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasCity(item);
    }

    public static List<BasCity> GetBasCityByProv(SqlSession session, BasCity item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetBasCityByProv(item);
    }

    // endregion BasCity Methods

    // region BasProvince Methods

    public static BasProvince GetBasProvince(SqlSession session, BasProvince item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasProvince(item);
    }

    public static List<BasProvince> SearchBasProvince(SqlSession session, BasProvince item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasProvince(item);
    }

    public static void SaveBasProvince(SqlSession session, BasProvince item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasProvince(item);
    }

    // endregion BasProvince Methods

    // region BasCountry Methods

    public static BasCountry GetBasCountry(SqlSession session, BasCountry item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasCountry(item);
    }

    public static List<BasCountry> SearchBasCountry(SqlSession session, BasCountry item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasCountry(item);
    }

    public static void SaveBasCountry(SqlSession session, BasCountry item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasCountry(item);
    }

    // endregion BasCountry Methods

    // region BasLocation Methods

    public static List<BasLocation> GetBasLocation(SqlSession session, BasLocation item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetBasLocation(item);
    }

    // endregion BasLocation Methods

    // region BasSampleStand Methods

    public static BasSampleStand GetSampleStandCount(SqlSession session, BasSampleStand item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetSampleStandCount(item);
    }

    public static List<BasSampleStand> GetSampleStandByLevel(SqlSession session, BasSampleStand item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetSampleStandByLevel(item);
    }

    public static List<BasSampleStand> GetListBasSampleStand(SqlSession session, BasSampleStand item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        return mapper.GetListBasSampleStand(item);
    }

    public static void SaveBasSampleStand(SqlSession session, BasSampleStand item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleStand(item);
    }

    // endregion BasSampleStand Methods

    // region BasSampleReplace Methods

    public static BasSampleReplace GetBasSampleReplace(SqlSession session, BasSampleReplace item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);
        item.getItem().setGetaction(ActionGetType.row.toString());

        return mapper.GetBasSampleReplace(item);
    }

    public static List<BasSampleReplace> GetListBasSampleReplace(SqlSession session) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleReplace();
    }

    public static List<BasSampleReplace> GetListBasSampleReplaceBySampleID(SqlSession session, BasSampleReplace item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.GetListBasSampleReplaceBySampleID(item);
    }

    public static List<BasSampleReplace> SearchBasSampleReplace(SqlSession session, BasSampleReplace item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        return mapper.SearchBasSampleReplace(item);
    }

    public static void SaveBasSampleReplace(SqlSession session, BasSampleReplace item) {
        com.alms.mapper.sqlserver.BasMapper mapper = DBUtils.getMapper(session,
                com.alms.mapper.sqlserver.BasMapper.class);

        mapper.SaveBasSampleReplace(item);
    }

    // endregion BasSampleMain Methods
}
