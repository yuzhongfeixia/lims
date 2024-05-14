package com.alms.webservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.WebServiceContext;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.dao.CrmDao;
import com.alms.dao.DevDao;
import com.alms.dao.PrdDao;
import com.alms.entity.bas.BasSample;
import com.alms.entity.bas.BasSampleCatalog;
import com.alms.entity.bas.BasSampleJudge;
import com.alms.entity.bas.BasSampleMain;
import com.alms.entity.bas.BasSampleParameter;
import com.alms.entity.bas.BasSampleStand;
import com.alms.entity.bas.BasSampleTest;
import com.alms.entity.bas.BasTestCollect;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.crm.CrmSurvey;
import com.alms.entity.crm.CrmSurveyDetail;
import com.alms.entity.dev.AcceptManage;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.prd.BasPrd;
import com.alms.service.LabAndroidService;
import com.alms.webservice.inter.IServiceBas;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.JsonDateValueProcessor;
import com.gpersist.utils.ToolUtils;

public class ServiceBas implements IServiceBas {

    @Resource
    private WebServiceContext context = new org.apache.cxf.jaxws.context.WebServiceContextImpl();

    // region 查询所有样品
    @WebMethod
    public @WebResult String GetAllBasSample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetAllBasSample");
            BasSample item = new BasSample();
            // 查询所有样品
            List<BasSample> lists = BasDao.GetListBasSample();
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String sampleJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", sampleJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询所有受检单位
    @WebMethod
    public @WebResult String GetAllTestedUnit(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetAllTestedUnit");
            BusTestedUnit item = new BusTestedUnit();
            // 查询所有受检单位
            List<BusTestedUnit> lists = BasDao.GetListBusTestedUnit(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String unitJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", unitJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 根据编号查询受检单位
    @WebMethod
    public @WebResult String SearchTestedUnitById(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。SearchTestedUnit");
            BusTestedUnit item = new BusTestedUnit();
            item.setTestedid(selparam);
            // 查询所有受检单位
            item = BasDao.GetBusTestedUnit(item);

            if (item == null) {
                item = new BusTestedUnit();
            }

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String unit = ToolUtils.GetJsonFromBean(item, config);

            resultMap.put("success", true);
            resultMap.put("data", unit);
            resultMap.put("message", "成功");
        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 根据样品编号查询 检测参数
    @WebMethod
    public @WebResult String GetParameterBySample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            BusSampleParam item = new BusSampleParam();

            BusGetDetail busgetdetail = (BusGetDetail) JSONObject.toBean(JSONObject.fromObject(selparam),
                    BusGetDetail.class);

            List<BusSampleParam> lists = BasDao.GetParameterByGetDetail(busgetdetail);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String paramJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", paramJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 根据检测参数和样品 分别查询 检测依据和判定依据
    @WebMethod
    public @WebResult String GetJudgeTestByParam(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetJudgeTestByParam");

            String[] selparams = selparam.split(",");

            // 根据检测参数查询判定依据
            BasSampleTest test = new BasSampleTest();
            test.setSampleid(selparams[0]);
            test.setParameterid(selparams[1]);
            List<BasSampleTest> testLists = BasDao.GetListBasSampleTest(test);

            // 根据检测参数查询检测依据
            BasSampleJudge judge = new BasSampleJudge();
            judge.setSampleid(selparams[0]);
            judge.setParameterid(selparams[1]);
            List<BasSampleJudge> judgeLists = BasDao.GetListBasSampleJudge(judge);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String testJson = ToolUtils.GetJsonFromArray(testLists, config);
            String judgeJson = ToolUtils.GetJsonFromArray(judgeLists, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, testJson); // 检测依据
            jsonArray.add(1, judgeJson); // 判定依据

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 根据检测参数和样品编号 一次性查询检测依据和判定依据
    @WebMethod
    public @WebResult String GetAllJudgeTestByParam(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetAllJudgeTestByParam");

            String[] selparams = selparam.split(",");

            // 根据检测参数查询判定依据和检测依据
            BasTestCollect test = new BasTestCollect();
            test.setSampleid(selparams[0]);
            test.setParameterid(selparams[1]);
            List<BasTestCollect> testLists = BasDao.GetListBasTestCollect(test);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String testJson = ToolUtils.GetJsonFromArray(testLists, config);

            resultMap.put("success", true);
            resultMap.put("data", testJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 耗材html
    @WebMethod
    public @WebResult String GetBasPrdHtml(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBasPrdHtml" + selparam);
            BasPrd bPrd = new BasPrd();
            bPrd.setPrdid(selparam);
            // 当前客户的任务单
            bPrd = PrdDao.GetBasPrd(bPrd);

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            sb.append(LabAndroidService.GetBasPrdHtml(bPrd));
            sb.append(LabAndroidService.GetInfoHtmlBottom());

            resultMap.put("success", true);
            resultMap.put("data", sb.toString());

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // endregion

    // region 添加满意度调查
    @WebMethod
    public @WebResult String AddCrmSurvey(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        OnlineUser ou = new OnlineUser();
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。AddCrmSurvey");

            JSONArray jsonObject = JSONArray.fromObject(selparam);

            System.out.print(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            CrmSurvey survey = new CrmSurvey();
            survey = (CrmSurvey) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), CrmSurvey.class);

            List<CrmSurveyDetail> getDetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(1),
                    CrmSurveyDetail.class);

            survey.setTranuser(ou.getUser().getUserid());
            survey.setTranusername(ou.getUser().getUsername());
            survey.setTrandate(new Date());
            survey.getDeal().setAction(DataAction.Create.getAction());
            CrmDao.SaveCrmSurvey(session, survey);

            // 满意度单明细
            for (CrmSurveyDetail detail : getDetails) {
                detail.setTranid(survey.getTranid());
                detail.getDeal().setAction(DataAction.Create.getAction());
                CrmDao.SaveCrmSurveyDetail(session, detail);
            }

            session.commit();
            resultMap.put("success", true);
            resultMap.put("data", "满意度调查保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("message", ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // 根据样品编号查询规格总数

    // region
    @WebMethod
    public @WebResult String GetStandCount(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            BasSampleStand item = new BasSampleStand();
            item.setSampleid(selparam);

            item = BasDao.GetSampleStandCount(item);
            if (item == null) {
                item = new BasSampleStand();
            }

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String unit = ToolUtils.GetJsonFromBean(item, config);

            resultMap.put("success", true);
            resultMap.put("data", unit);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String GetSampleStand(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            System.out.println(selparam);
            // List<BasSampleStand> lbss = BasDao.GetSampleStandByLevel(item);

            // JsonConfig config = new JsonConfig();
            // config.registerPropertyExclusions(item.getClass(),
            // item.OnExclusions());
            //
            // String unit = ToolUtils.GetJsonFromBean(item, config);
            //
            // resultMap.put("success", true);
            // resultMap.put("data", unit);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String GetBasSampleCate(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            BasSampleCatalog item = new BasSampleCatalog();
            List<BasSampleCatalog> lists = BasDao.GetBasSampleCate();

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String samplecatalogjson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", samplecatalogjson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String GetSampleMainBySampleCate(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            BasSampleMain item = new BasSampleMain();
            item.setSamplecate(selparam);
            List<BasSampleMain> lists = BasDao.GetSampleMainBySampleCate(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String samplemainjson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", samplemainjson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String GetSampleBySampleMain(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            BasSample item = new BasSample();
            item.setSamplemain(selparam);
            List<BasSample> lists = BasDao.GetSampleBySampleMain(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String samplejson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", samplejson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String GetSampleStandInfo(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        try {
            BasSampleStand bss = new BasSampleStand();
            bss.setSampleid(selparam);
            BasSampleStand bssget = BasDao.GetSampleStandCount(bss);
            if (bssget == null) {
                bssget = new BasSampleStand();
            }

            List<BasSampleStand> bsss = BasDao.GetListBasSampleStand(bss);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            config.registerPropertyExclusions(bssget.getClass(), ((BaseBean) bssget).OnExclusions());

            String bssjson = ToolUtils.GetJsonFromBean(bssget, config);
            String listbssjson = ToolUtils.GetJsonFromArray(bsss, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, bssjson);
            jsonArray.add(1, listbssjson);

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 获取设备验收记录
    @WebMethod
    public @WebResult String GetALLAcceptManage(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {
            System.out.println("调试。。。。。。。GetALLAcceptManage");
            AcceptManage item = new AcceptManage();

            // 查询所有待安装调试人验收的记录
            List<AcceptManage> lists = DevDao.GetListAcceptManageForSign(item);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String sampleJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", sampleJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 设备安装人验收确认
    @WebMethod
    public @WebResult String DevAcceptConfirm(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        try {
            System.out.println("调试。。。。。。。DevAcceptConfirm");

            JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            AcceptManage am = (AcceptManage) JSONObject.toBean(jsonObject, AcceptManage.class);

            AcceptManage aManage = DevDao.GetAcceptManage(am);
            aManage.setInstallpath(am.getInstallpath());
            aManage.setInstallsign(am.getInstallsign());
            aManage.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveAcceptManage(session, aManage);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "设备安装人验收确认成功！");
            resultMap.put("message", "设备安装人验收完成");

        } catch (Exception e) {
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "设备安装人验收确认失败！");
        } finally {
            session.close();
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询该样品对应的检测参数
    @WebMethod
    public @WebResult String GetAllBasSampleParameter(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {

            System.out.println("调试。。。。。。。GetAllBasSampleParameter");
            BasSampleParameter item = new BasSampleParameter();

            // 查询
            List<BasSampleParameter> lists = BasDao.GetListBasSampleParameterDetail(selparam);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String sampleJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", sampleJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

}
