package com.alms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.sqlserver.SqlBasDao;
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
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;

public class BasDao {

    // region BasSampleTest Methods

    public static List<BasSampleTest> GetListBasSampleTest(BasSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleTest(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleTest>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleTest> GetListBasSampleTest(SqlSession session, BasSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleTest>();
        default:
            return SqlBasDao.GetListBasSampleTest(session, item);
        }
    }

    public static List<BasSampleTest> GetListBasSampleTestBySample(BasSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleTestBySample(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleTest>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleTest> GetListBasSampleTestBySample(SqlSession session, BasSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleTest>();
        default:
            return SqlBasDao.GetListBasSampleTestBySample(session, item);
        }
    }

    public static void SaveBasSampleTest(SqlSession session, BasSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleTest(session, item);
            break;
        }
    }

    public static List<BasSampleTest> GetListBasSampleTestByParam(BasSampleTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleTestByParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleTest>();
        } finally {
            session.close();
        }
    }

    private static List<BasSampleTest> GetListBasSampleTestByParam(SqlSession session, BasSampleTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleTest>();
        default:
            return SqlBasDao.GetListBasSampleTestByParam(session, item);
        }
    }

    public static List<BasTestCollect> GetListBasTestCollect(BasTestCollect item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasTestCollect(session, item);
        } catch (Exception e) {
            return new ArrayList<BasTestCollect>();
        } finally {
            session.close();
        }
    }

    private static List<BasTestCollect> GetListBasTestCollect(SqlSession session, BasTestCollect item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasTestCollect>();
        default:
            return SqlBasDao.GetListBasTestCollect(session, item);
        }
    }

    // endregion BasSampleTest Methods

    // region BasSampleJudge Methods

    public static List<BasSampleJudge> GetListBasSampleJudge(BasSampleJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleJudge(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleJudge> GetListBasSampleJudge(SqlSession session, BasSampleJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleJudge>();
        default:
            return SqlBasDao.GetListBasSampleJudge(session, item);
        }
    }

    public static void SaveBasSampleJudge(SqlSession session, BasSampleJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleJudge(session, item);
            break;
        }
    }

    public static List<BasSampleJudge> GetListBasSampleJudgeByParam(BasSampleJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleJudgeByParam(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleJudge>();
        } finally {
            session.close();
        }
    }

    private static List<BasSampleJudge> GetListBasSampleJudgeByParam(SqlSession session, BasSampleJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleJudge>();
        default:
            return SqlBasDao.GetListBasSampleJudgeByParam(session, item);
        }
    }

    // endregion BasSampleJudge Methods

    // region BasSampleParameter Methods

    public static List<BasSampleParameter> GetListBasSampleParameter(BasSampleParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleParameter(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleParameter> GetListBasSampleParameter(SqlSession session, BasSampleParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleParameter>();
        default:
            return SqlBasDao.GetListBasSampleParameter(session, item);
        }
    }

    public static List<BasSampleParameter> GetListBasSampleParameterSure(BasSampleParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleParameterSure(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleParameter> GetListBasSampleParameterSure(SqlSession session, BasSampleParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleParameter>();
        default:
            return SqlBasDao.GetListBasSampleParameterSure(session, item);
        }
    }

    public static List<BasSampleParameter> GetListBasSampleDetail(BasSampleParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleParameter> GetListBasSampleDetail(SqlSession session, BasSampleParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleParameter>();
        default:
            return SqlBasDao.GetListBasSampleDetail(session, item);
        }
    }

    public static List<BasSampleParameter> GetListBasSampleParameterDetail(String sampleid) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleParameterDetail(session, sampleid);
        } catch (Exception e) {
            return new ArrayList<BasSampleParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleParameter> GetListBasSampleParameterDetail(SqlSession session, String sampleid) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleParameter>();
        default:
            return SqlBasDao.GetListBasSampleParameterDetail(session, sampleid);
        }
    }

    public static void SaveBasSampleParameter(SqlSession session, BasSampleParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleParameter(session, item);
            break;
        }
    }

    // endregion BasSampleParameter Methods

    // region BasMainParameter Methods

    public static List<BasMainParameter> GetListBasMainParameter(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasMainParameter(session, item);
        } catch (Exception e) {
            return new ArrayList<BasMainParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasMainParameter> GetListBasMainParameter(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasMainParameter>();
        default:
            return SqlBasDao.GetListBasMainParameter(session, item);
        }
    }

    public static BasMainParameter GetBasMainParameter(BasMainParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasMainParameter(session, item);
        } catch (Exception e) {
            return new BasMainParameter();
        } finally {
            session.close();
        }
    }

    public static BasMainParameter GetBasMainParameter(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasMainParameter();
        default:
            return SqlBasDao.GetBasMainParameter(session, item);
        }
    }

    public static void SaveBasMainParameter(SqlSession session, BasMainParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasMainParameter(session, item);
            break;
        }
    }

    // endregion BasMainParameter Methods

    // region BasCatalogParameter Methods

    public static List<BasCatalogParameter> GetListBasCatalogParameter(BasCatalogParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasCatalogParameter(session, item);
        } catch (Exception e) {
            return new ArrayList<BasCatalogParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasCatalogParameter> GetListBasCatalogParameter(SqlSession session, BasCatalogParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasCatalogParameter>();
        default:
            return SqlBasDao.GetListBasCatalogParameter(session, item);
        }
    }

    public static void SaveBasCatalogParameter(SqlSession session, BasCatalogParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasCatalogParameter(session, item);
            break;
        }
    }

    // endregion BasCatalogParameter Methods

    // region BasTest Methods

    public static BasTest GetBasTest(BasTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasTest(session, item);
        } catch (Exception e) {
            return new BasTest();
        } finally {
            session.close();
        }
    }

    public static BasTest GetBasTest(SqlSession session, BasTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasTest();
        default:
            return SqlBasDao.GetBasTest(session, item);
        }
    }

    public static List<BasTest> GetListBasTest() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasTest(session);
        } catch (Exception e) {
            return new ArrayList<BasTest>();
        } finally {
            session.close();
        }
    }

    public static List<BasTest> GetListBasTest(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasTest>();
        default:
            return SqlBasDao.GetListBasTest(session);
        }
    }

    public static List<BasTest> SearchBasTest(BasTest item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasTest>();
            default:
                return SqlBasDao.SearchBasTest(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasTest>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasTest(SqlSession session, BasTest item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasTest(session, item);
            break;
        }
    }

    // endregion BasTest Methods

    // region AbilityForm Methods

    public static AbilityForm GetAbilityForm(AbilityForm item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetAbilityForm(session, item);
        } catch (Exception e) {
            return new AbilityForm();
        } finally {
            session.close();
        }
    }

    public static AbilityForm GetAbilityForm(SqlSession session, AbilityForm item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new AbilityForm();
        default:
            return SqlBasDao.GetAbilityForm(session, item);
        }
    }

    public static List<AbilityForm> GetListAbilityForm() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListAbilityForm(session);
        } catch (Exception e) {
            return new ArrayList<AbilityForm>();
        } finally {
            session.close();
        }
    }

    public static List<AbilityForm> GetListAbilityForm(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<AbilityForm>();
        default:
            return SqlBasDao.GetListAbilityForm(session);
        }
    }

    public static List<AbilityForm> SearchAbilityForm(AbilityForm item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<AbilityForm>();
            default:
                return SqlBasDao.SearchAbilityForm(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<AbilityForm>();
        } finally {
            session.close();
        }
    }

    public static void SaveAbilityForm(SqlSession session, AbilityForm item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveAbilityForm(session, item);
            break;
        }
    }

    // endregion AbilityForm Methods

    // region BasJudge Methods

    public static BasJudge GetBasJudge(BasJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasJudge(session, item);
        } catch (Exception e) {
            return new BasJudge();
        } finally {
            session.close();
        }
    }

    public static BasJudge GetBasJudge(SqlSession session, BasJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasJudge();
        default:
            return SqlBasDao.GetBasJudge(session, item);
        }
    }

    public static List<BasJudge> GetListBasJudge() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasJudge(session);
        } catch (Exception e) {
            return new ArrayList<BasJudge>();
        } finally {
            session.close();
        }
    }

    public static List<BasJudge> GetListBasJudge(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasJudge>();
        default:
            return SqlBasDao.GetListBasJudge(session);
        }
    }

    public static List<BasJudge> SearchBasJudge(BasJudge item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasJudge>();
            default:
                return SqlBasDao.SearchBasJudge(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasJudge>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasJudge(SqlSession session, BasJudge item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasJudge(session, item);
            break;
        }
    }

    // endregion BasJudge Methods

    // region BasParameter Methods

    public static BasParameter GetBasParameter(BasParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasParameter(session, item);
        } catch (Exception e) {
            return new BasParameter();
        } finally {
            session.close();
        }
    }

    public static BasParameter GetBasParameter(SqlSession session, BasParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasParameter();
        default:
            return SqlBasDao.GetBasParameter(session, item);
        }
    }

    public static List<BasParameter> GetListBasParameter() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasParameter(session);
        } catch (Exception e) {
            return new ArrayList<BasParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasParameter> GetListBasParameter(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasParameter>();
        default:
            return SqlBasDao.GetListBasParameter(session);
        }
    }

    public static List<BasParameter> GetListBasParameterByLab(BasParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasParameterByLab(session, item);
        } catch (Exception e) {
            return new ArrayList<BasParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasParameter> GetListBasParameterByLab(SqlSession session, BasParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasParameter>();
        default:
            return SqlBasDao.GetListBasParameterByLab(session, item);
        }
    }

    public static List<BasParameter> SearchBasParameter(BasParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasParameter>();
            default:
                return SqlBasDao.SearchBasParameter(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasParameter>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasParameter(SqlSession session, BasParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasParameter(session, item);
            break;
        }
    }

    // endregion BasParameter Methods

    // region BasSample Methods

    public static BasSample GetBasSample(BasSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSample(session, item);
        } catch (Exception e) {
            return new BasSample();
        } finally {
            session.close();
        }
    }

    public static BasSample GetBasSample(SqlSession session, BasSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasSample();
        default:
            return SqlBasDao.GetBasSample(session, item);
        }
    }

    public static List<BasSample> GetListBasSample() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSample(session);
        } catch (Exception e) {
            return new ArrayList<BasSample>();
        } finally {
            session.close();
        }
    }

    public static List<BasSample> GetListBasSample(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSample>();
        default:
            return SqlBasDao.GetListBasSample(session);
        }
    }

    public static List<BasSample> SearchBasSample(BasSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasSample>();
            default:
                return SqlBasDao.SearchBasSample(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasSample>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasSample(SqlSession session, BasSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSample(session, item);
            break;
        }
    }

    public static List<BasSample> GetSampleBySampleMain(BasSample item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleBySampleMain(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSample>();
        } finally {
            session.close();
        }
    }

    public static List<BasSample> GetSampleBySampleMain(SqlSession session, BasSample item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSample>();
        default:
            return SqlBasDao.GetSampleBySampleMain(session, item);
        }
    }

    // endregion BasSample Methods

    // region BasSampleMain Methods

    public static BasSampleMain GetBasSampleMain(BasSampleMain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleMain(session, item);
        } catch (Exception e) {
            return new BasSampleMain();
        } finally {
            session.close();
        }
    }

    public static BasSampleMain GetBasSampleMain(SqlSession session, BasSampleMain item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasSampleMain();
        default:
            return SqlBasDao.GetBasSampleMain(session, item);
        }
    }

    public static List<BasSampleMain> GetListBasSampleMain() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleMain(session);
        } catch (Exception e) {
            return new ArrayList<BasSampleMain>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleMain> GetListBasSampleMain(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleMain>();
        default:
            return SqlBasDao.GetListBasSampleMain(session);
        }
    }

    public static List<BasSampleMain> SearchBasSampleMain(BasSampleMain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasSampleMain>();
            default:
                return SqlBasDao.SearchBasSampleMain(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasSampleMain>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasSampleMain(SqlSession session, BasSampleMain item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleMain(session, item);
            break;
        }
    }

    public static List<BasSampleMain> GetSampleMainBySampleCate(BasSampleMain item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetSampleMainBySampleCate(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleMain>();
        } finally {
            session.close();
        }
    }

    private static List<BasSampleMain> GetSampleMainBySampleCate(SqlSession session, BasSampleMain item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleMain>();
        default:
            return SqlBasDao.GetSampleMainBySampleCate(session, item);
        }
    }

    // endregion BasSampleMain Methods

    // region BasSampleCatalog Methods

    public static BasSampleCatalog GetBasSampleCatalog(BasSampleCatalog item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleCatalog(session, item);
        } catch (Exception e) {
            return new BasSampleCatalog();
        } finally {
            session.close();
        }
    }

    public static BasSampleCatalog GetBasSampleCatalog(SqlSession session, BasSampleCatalog item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasSampleCatalog();
        default:
            return SqlBasDao.GetBasSampleCatalog(session, item);
        }
    }

    public static BasSampleCatalog GetBasSampleCatalogBySampleID(BasSampleCatalog item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleCatalogBySampleID(session, item);
        } catch (Exception e) {
            return new BasSampleCatalog();
        } finally {
            session.close();
        }
    }

    public static BasSampleCatalog GetBasSampleCatalogBySampleID(SqlSession session, BasSampleCatalog item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasSampleCatalog();
        default:
            return SqlBasDao.GetBasSampleCatalogBySampleID(session, item);
        }
    }

    public static List<BasSampleCatalog> GetListBasSampleCatalog() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleCatalog(session);
        } catch (Exception e) {
            return new ArrayList<BasSampleCatalog>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleCatalog> GetListBasSampleCatalog(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleCatalog>();
        default:
            return SqlBasDao.GetListBasSampleCatalog(session);
        }
    }

    public static List<BasSampleCatalog> GetBasSampleCate() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleCate(session);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasSampleCatalog>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleCatalog> GetBasSampleCate(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleCatalog>();
        default:
            return SqlBasDao.GetBasSampleCate(session);
        }
    }

    public static List<BasSampleCatalog> SearchBasSampleCatalog(BasSampleCatalog item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasSampleCatalog>();
            default:
                return SqlBasDao.SearchBasSampleCatalog(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasSampleCatalog>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasSampleCatalog(SqlSession session, BasSampleCatalog item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleCatalog(session, item);
            break;
        }
    }

    // endregion BasSampleCatalog Methods

    // region BusTestedUnit Methods

    public static BusTestedUnit GetBusTestedUnit(BusTestedUnit item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBusTestedUnit(session, item);
        } catch (Exception e) {
            return new BusTestedUnit();
        } finally {
            session.close();
        }
    }

    public static BusTestedUnit GetBusTestedUnit(SqlSession session, BusTestedUnit item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BusTestedUnit();
        default:
            return SqlBasDao.GetBusTestedUnit(session, item);
        }
    }

    public static List<BusTestedUnit> GetListBusTestedUnit(BusTestedUnit item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBusTestedUnit(session, item);
        } catch (Exception e) {
            return new ArrayList<BusTestedUnit>();
        } finally {
            session.close();
        }
    }

    public static List<BusTestedUnit> GetListBusTestedUnit(SqlSession session, BusTestedUnit item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusTestedUnit>();
        default:
            return SqlBasDao.GetListBusTestedUnit(session, item);
        }
    }

    public static List<BusTestedUnit> SearchBusTestedUnit(BusTestedUnit item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BusTestedUnit>();
            default:
                return SqlBasDao.SearchBusTestedUnit(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BusTestedUnit>();
        } finally {
            session.close();
        }
    }

    public static void SaveBusTestedUnit(SqlSession session, BusTestedUnit item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBusTestedUnit(session, item);
            break;
        }
    }

    // endregion BusTestedUnit Methods

    // region BasLabParameter Methods

    public static BasLabParameter GetBasLabParameter(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasLabParameter(session, item);
        } catch (Exception e) {
            return new BasLabParameter();
        } finally {
            session.close();
        }
    }

    public static BasLabParameter GetBasLabParameter(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasLabParameter();
        default:
            return SqlBasDao.GetBasLabParameter(session, item);
        }
    }

    public static BasLabParameter GetBasLabParameterForInfo(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasLabParameterForInfo(session, item);
        } catch (Exception e) {
            return new BasLabParameter();
        } finally {
            session.close();
        }
    }

    public static BasLabParameter GetBasLabParameterForInfo(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasLabParameter();
        default:
            return SqlBasDao.GetBasLabParameterForInfo(session, item);
        }
    }

    public static List<BasLabParameter> GetListBasLabParameter(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasLabParameter(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasLabParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasLabParameter> GetListBasLabParameter(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasLabParameter>();
        default:
            return SqlBasDao.GetListBasLabParameter(session, item);
        }
    }

    public static List<BasLabParameter> GetListBasLabParameterForDept(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasLabParameterForDept(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasLabParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasLabParameter> GetListBasLabParameterForDept(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasLabParameter>();
        default:
            return SqlBasDao.GetListBasLabParameterForDept(session, item);
        }
    }

    public static List<BasLabParameter> SearchBasLabParameter(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasLabParameter>();
            default:
                return SqlBasDao.SearchBasLabParameter(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasLabParameter>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasLabParameter(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasLabParameter(session, item);
            break;
        }
    }

    public static List<BasLabParameter> GetBasLabParameterByLab(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasLabParameter>();
            default:
                return SqlBasDao.GetBasLabParameterByLab(session, item);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasLabParameter>();
        } finally {
            session.close();
        }
    }

    public static List<BasLabParameter> GetListBasLabBySample(BasLabParameter item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasLabBySample(session, item);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<BasLabParameter>();
        } finally {
            session.close();
        }
    }

    private static List<BasLabParameter> GetListBasLabBySample(SqlSession session, BasLabParameter item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasLabParameter>();
        default:
            return SqlBasDao.GetListBasLabBySample(session, item);
        }
    }

    // endregion BasLabParameter Methods

    public static List<BusSampleParam> GetParameterByGetDetail(BusGetDetail item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetParameterByGetDetail(session, item);
        } catch (Exception e) {
            return new ArrayList<BusSampleParam>();
        } finally {
            session.close();
        }
    }

    private static List<BusSampleParam> GetParameterByGetDetail(SqlSession session, BusGetDetail item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BusSampleParam>();
        default:
            return SqlBasDao.GetParameterByGetDetail(session, item);
        }
    }

    public static List<SysUser> GetUserByDept(String deptid) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<SysUser>();
            default:
                return SqlBasDao.GetUserByDept(session, deptid);

            }
        } catch (Exception e) {
            return new ArrayList<SysUser>();
        } finally {
            session.close();
        }
    }

    // region BasArea Methods

    public static BasArea GetBasArea(BasArea item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasArea(session, item);
        } catch (Exception e) {
            return new BasArea();
        } finally {
            session.close();
        }
    }

    public static BasArea GetBasArea(SqlSession session, BasArea item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasArea();
        default:
            return SqlBasDao.GetBasArea(session, item);
        }
    }

    public static List<BasArea> SearchBasArea(BasArea item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasArea>();
            default:
                return SqlBasDao.SearchBasArea(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasArea>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasArea(SqlSession session, BasArea item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasArea(session, item);
            break;
        }
    }

    public static List<BasArea> GetBasAreaByCity(BasArea item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasArea>();
            default:
                return SqlBasDao.GetBasAreaByCity(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasArea>();
        } finally {
            session.close();
        }
    }

    // endregion BasArea Methods

    // region BasCity Methods

    public static BasCity GetBasCity(BasCity item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasCity(session, item);
        } catch (Exception e) {
            return new BasCity();
        } finally {
            session.close();
        }
    }

    public static BasCity GetBasCity(SqlSession session, BasCity item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasCity();
        default:
            return SqlBasDao.GetBasCity(session, item);
        }
    }

    public static List<BasCity> SearchBasCity(BasCity item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasCity>();
            default:
                return SqlBasDao.SearchBasCity(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasCity>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasCity(SqlSession session, BasCity item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasCity(session, item);
            break;
        }
    }

    public static List<BasCity> GetBasCityByProv(BasCity item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasCity>();
            default:
                return SqlBasDao.GetBasCityByProv(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasCity>();
        } finally {
            session.close();
        }
    }

    // endregion BasCity Methods

    // region BasProvince Methods

    public static BasProvince GetBasProvince(BasProvince item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasProvince(session, item);
        } catch (Exception e) {
            return new BasProvince();
        } finally {
            session.close();
        }
    }

    public static BasProvince GetBasProvince(SqlSession session, BasProvince item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasProvince();
        default:
            return SqlBasDao.GetBasProvince(session, item);
        }
    }

    public static List<BasProvince> SearchBasProvince(BasProvince item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasProvince>();
            default:
                return SqlBasDao.SearchBasProvince(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasProvince>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasProvince(SqlSession session, BasProvince item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasProvince(session, item);
            break;
        }
    }

    // endregion BasProvince Methods

    // region BasCountry Methods

    public static BasCountry GetBasCountry(BasCountry item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasCountry(session, item);
        } catch (Exception e) {
            return new BasCountry();
        } finally {
            session.close();
        }
    }

    public static BasCountry GetBasCountry(SqlSession session, BasCountry item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasCountry();
        default:
            return SqlBasDao.GetBasCountry(session, item);
        }
    }

    public static List<BasCountry> SearchBasCountry(BasCountry item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasCountry>();
            default:
                return SqlBasDao.SearchBasCountry(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasCountry>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasCountry(SqlSession session, BasCountry item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasCountry(session, item);
            break;
        }
    }

    // endregion BasCountry Methods

    // region BasLocation Methods

    public static List<BasLocation> GetBasLocation(BasLocation item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasLocation>();
            default:
                return SqlBasDao.GetBasLocation(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasLocation>();
        } finally {
            session.close();
        }
    }

    // endregion BasLocation Methods

    // region BasSampleStand Methods

    public static BasSampleStand GetSampleStandCount(BasSampleStand item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new BasSampleStand();
            default:
                return SqlBasDao.GetSampleStandCount(session, item);
            }
        } catch (Exception e) {
            return new BasSampleStand();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleStand> GetSampleStandByLevel(BasSampleStand item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasSampleStand>();
            default:
                return SqlBasDao.GetSampleStandByLevel(session, item);
            }
        } catch (Exception e) {
            return new ArrayList<BasSampleStand>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleStand> GetListBasSampleStand(BasSampleStand item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleStand(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleStand>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleStand> GetListBasSampleStand(SqlSession session, BasSampleStand item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleStand>();
        default:
            return SqlBasDao.GetListBasSampleStand(session, item);
        }
    }

    public static void SaveBasSampleStand(SqlSession session, BasSampleStand item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleStand(session, item);
            break;
        }
    }

    // endregion BasSampleStand Methods

    // region BasSampleReplace Methods

    public static BasSampleReplace GetBasSampleReplace(BasSampleReplace item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetBasSampleReplace(session, item);
        } catch (Exception e) {
            return new BasSampleReplace();
        } finally {
            session.close();
        }
    }

    public static BasSampleReplace GetBasSampleReplace(SqlSession session, BasSampleReplace item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new BasSampleReplace();
        default:
            return SqlBasDao.GetBasSampleReplace(session, item);
        }
    }

    public static List<BasSampleReplace> GetListBasSampleReplace() {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleReplace(session);
        } catch (Exception e) {
            return new ArrayList<BasSampleReplace>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleReplace> GetListBasSampleReplace(SqlSession session) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleReplace>();
        default:
            return SqlBasDao.GetListBasSampleReplace(session);
        }
    }

    public static List<BasSampleReplace> GetListBasSampleReplaceBySampleID(BasSampleReplace item) {
        SqlSession session = DBUtils.getFactory();

        try {
            return GetListBasSampleReplaceBySampleID(session, item);
        } catch (Exception e) {
            return new ArrayList<BasSampleReplace>();
        } finally {
            session.close();
        }
    }

    public static List<BasSampleReplace> GetListBasSampleReplaceBySampleID(SqlSession session, BasSampleReplace item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return new ArrayList<BasSampleReplace>();
        default:
            return SqlBasDao.GetListBasSampleReplaceBySampleID(session, item);
        }
    }

    public static List<BasSampleReplace> SearchBasSampleReplace(BasSampleReplace item) {
        SqlSession session = DBUtils.getFactory();

        try {
            switch (ToolUtils.GetDataBaseType()) {
            case Oracle10:
                return new ArrayList<BasSampleReplace>();
            default:
                return SqlBasDao.SearchBasSampleReplace(session, item);

            }
        } catch (Exception e) {
            return new ArrayList<BasSampleReplace>();
        } finally {
            session.close();
        }
    }

    public static void SaveBasSampleReplace(SqlSession session, BasSampleReplace item) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            break;

        default:
            SqlBasDao.SaveBasSampleReplace(session, item);
            break;
        }
    }

    // endregion BasSampleMain Methods

}
