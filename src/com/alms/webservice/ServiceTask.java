package com.alms.webservice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.xml.ws.WebServiceContext;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.BasDao;
import com.alms.dao.DatDao;
import com.alms.dao.DevDao;
import com.alms.dao.FlowDao;
import com.alms.dao.FormDao;
import com.alms.dao.LabAndroidDao;
import com.alms.dao.LabDao;
import com.alms.entity.bas.BasLabParameter;
import com.alms.entity.bas.BasSample;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.dat.BusRecord;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.DatComputeData;
import com.alms.entity.dat.SetBusGet;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dev.BasDev;
import com.alms.entity.dev.DevUse;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.form.FrmGet;
import com.alms.entity.form.IntField;
import com.alms.entity.form.IntInterface;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusRecordData;
import com.alms.entity.lab.BusSampleParam;
import com.alms.entity.lab.BusSelect;
import com.alms.entity.lab.BusTask;
import com.alms.entity.lab.BusTaskData;
import com.alms.entity.lab.BusTaskDev;
import com.alms.entity.lab.BusTaskSample;
import com.alms.entity.lab.BusTaskSingle;
import com.alms.entity.lab.BusTaskTest;
import com.alms.entity.lab.BusTaskTester;
import com.alms.enums.DataSource;
import com.alms.enums.TodoType;
import com.alms.service.DatService;
import com.alms.service.FormService;
import com.alms.service.JudgeService;
import com.alms.service.LabAndroidService;
import com.alms.service.MathService;
import com.alms.webservice.inter.IServiceTask;
import com.gpersist.dao.PublicDao;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.ClassSource;
import com.gpersist.enums.DataAction;
import com.gpersist.enums.FieldType;
import com.gpersist.enums.JudgeStatus;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.JsonDateValueProcessor;
import com.gpersist.utils.JsonUtils;
import com.gpersist.utils.ToolUtils;
import com.gpersist.webservice.WsUtils;

@WebService
public class ServiceTask implements IServiceTask {

    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    @Resource
    private WebServiceContext context = new org.apache.cxf.jaxws.context.WebServiceContextImpl();

    // region 委托书提交
    @WebMethod
    public @WebResult String CommitBusConsign(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        SqlSession session = DBUtils.getFactory();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        OnlineUser ou = new OnlineUser();

        try {

            System.out.println("调试。。。。。。。CommitBusConsign");

            ou = UserDao.GetOnlineUser(session, userid);

            JSONArray jsonObject = JSONArray.fromObject(selparam);
            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusGet busget = (BusGet) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), BusGet.class);
            List<BusGetDetail> getdetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(2), BusGetDetail.class);
            List<BusSampleParam> sampleparams = ToolUtils.GetArrayFromJson((String) jsonObject.get(1),
                    BusSampleParam.class);

            // 编辑委托单位
            BusTestedUnit bUnit = new BusTestedUnit();
            if (busget.getTestedid().equals("")) {
                bUnit.setTestedname(busget.getTestedname());
                bUnit.setEnterlegal(busget.getTesteduser());
                bUnit.setEntertele(busget.getEntertele());
                bUnit.setEnterpost(busget.getEnterpost());
                bUnit.getDeal().setAction(DataAction.Create.getAction());
            } else {
                bUnit.setTestedid(busget.getTestedid());
                bUnit = BasDao.GetBusTestedUnit(bUnit);
                bUnit.setTestedname(busget.getTestedname());
                bUnit.setEnterlegal(busget.getTesteduser());
                bUnit.setEntertele(busget.getEntertele());
                bUnit.setEnterpost(busget.getEnterpost());
                bUnit.getDeal().setAction(DataAction.Modify.getAction());
            }

            BasDao.SaveBusTestedUnit(session, bUnit);
            busget.setTestedid(bUnit.getTestedid());

            // 添加委托书主表
            busget.setSampleid(getdetails.get(0).getSampleid());
            busget.setFlowaction("52");
            busget.setFlowstatus("02");
            busget.setTrandate(new Date());
            busget.setTesttype("01");
            busget.setTranuser(ou.getUser().getUserid());
            busget.setTranusername(ou.getUser().getUsername());
            busget.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusGet(session, busget);

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(busget.getTranid());
            busTodo.setBusflow("GetAcc");
            busTodo.setFlownode("create");
            busTodo.setSenddate(new Date());
            busTodo.setTranuser(userid);
            busTodo.setIsnowflow(true);
            busTodo.setSampleid(String.valueOf(1));
            busTodo.setTodotype(TodoType.GetAcc.getTodotype());
            busTodo.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            // 获取检测项目
            String parameternames = "";
            if (sampleparams != null) {
                for (int i = 0; i < sampleparams.size(); i++) {
                    if (i == 0) {
                        parameternames = sampleparams.get(i).getParametername();
                    } else {
                        parameternames = parameternames + "," + sampleparams.get(i).getParametername();
                    }
                }
            }

            // 委托样品处理
            BusGetDetail delbusgetdetail = new BusGetDetail();
            delbusgetdetail.setTranid(busget.getTranid());
            delbusgetdetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetDetail(session, delbusgetdetail);

            // 删除委托样品检测参数
            BusSampleParam delbussampleparam = new BusSampleParam();
            delbussampleparam.setTranid(busget.getTranid());
            delbussampleparam.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusSampleParam(session, delbussampleparam);

            for (BusGetDetail busgetdetail : getdetails) {
                busgetdetail.setTranid(busget.getTranid());
                busgetdetail.setTestitems(parameternames);
                busgetdetail.setTesttype(busget.getTesttype());
                // 检测依据赋值
                BasSample bSample = new BasSample();
                bSample.setSampleid(busgetdetail.getSampleid());
                bSample = BasDao.GetBasSample(session, bSample);
                busgetdetail.setMainstandname(bSample.getMainstandname());
                busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusGetDetail(session, busgetdetail);

                for (BusSampleParam bussampleparam : sampleparams) {
                    bussampleparam.setTranid(busget.getTranid());
                    bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                    bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusSampleParam(session, bussampleparam);
                }
            }

            resultMap.put("success", true);
            resultMap.put("data", "委托书提交成功");
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 抽样单保存
    @WebMethod
    public @WebResult String AddBusConsign(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {

        SqlSession session = DBUtils.getFactory();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            WsUtils.LogWsVisit(appid, "CommitBusConsign", userid, "委托书提交", context);
            JSONArray jsonObject = JSONArray.fromObject(selparam);
            String[] dateFormats = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusGet busget = (BusGet) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), BusGet.class);
            List<BusGetDetail> getdetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(2), BusGetDetail.class);
            List<BusSampleParam> sampleparams = ToolUtils.GetArrayFromJson((String) jsonObject.get(1),
                    BusSampleParam.class);

            // 更新受检企业信息
            BusTestedUnit bUnit = new BusTestedUnit();

            if (busget.getTestedid().equals("") || busget.getTestedid() == null) {
                bUnit.setTestedname(busget.getTestedname());
                bUnit.setEnterlegal(busget.getEnterlegal());
                bUnit.setEntertele(busget.getEntertele());
                bUnit.setEnterpost(busget.getEnterpost());
                bUnit.setEntertype(busget.getEntertype());
                bUnit.setEnterscale(busget.getEnterscale());
                bUnit.setEnteraddr(busget.getEnteraddr());
                bUnit.setEnterfax(busget.getEnterfax());
                bUnit.getDeal().setAction(DataAction.Create.getAction());
            } else {
                bUnit.setTestedid(busget.getTestedid());
                bUnit = BasDao.GetBusTestedUnit(bUnit);
                bUnit.setTestedname(busget.getTestedname());
                bUnit.setEnterlegal(busget.getEnterlegal());
                bUnit.setEntertele(busget.getEntertele());
                bUnit.setEnterpost(busget.getEnterpost());
                bUnit.setEntertype(busget.getEntertype());
                bUnit.setEnterscale(busget.getEnterscale());
                bUnit.setEnteraddr(busget.getEnteraddr());
                bUnit.setEnterfax(busget.getEnterfax());
                bUnit.getDeal().setAction(DataAction.Modify.getAction());
            }
            BasDao.SaveBusTestedUnit(session, bUnit);
            busget.setTestedid(bUnit.getTestedid());

            // 更新通知单
            if (!busget.getNoticeid().equals("") && busget.getNoticeid() != null) {
                BusGetNotice bNotice = new BusGetNotice();
                bNotice.setTranid(busget.getNoticeid());
                bNotice = LabDao.GetBusGetNotice(bNotice);
                bNotice.setTestedid(bUnit.getTestedid());
                bNotice.setTestedname(bUnit.getTestedname());
                bNotice.getDeal().setAction(DataAction.Modify.getAction());
                LabDao.SaveBusGetNotice(session, bNotice);
            }

            // 添加委托书主表
            busget.setSampleid(getdetails.get(0).getSampleid());
            busget.setFlowaction("52");
            busget.setFlowstatus("01");
            busget.setTrandate(new Date());
            busget.setTesttype("01");
            busget.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusGet(session, busget);

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(busget.getTranid());
            busTodo.setBusflow("GetAcc");
            busTodo.setFlownode("create");
            busTodo.setSenddate(new Date());
            busTodo.setTranuser("300");
            busTodo.setTrandept("7000");
            busTodo.setIsnowflow(true);
            busTodo.setSampleid(String.valueOf(1));
            busTodo.setTodotype(TodoType.GetAcc.getTodotype());
            busTodo.getDeal().setAction(DataAction.Create.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            // 获取检测项目
            String parameternames = "";
            if (sampleparams != null) {
                for (int i = 0; i < sampleparams.size(); i++) {
                    if (i == 0) {
                        parameternames = sampleparams.get(i).getParametername();
                    } else {
                        parameternames = parameternames + "," + sampleparams.get(i).getParametername();
                    }
                }
            }

            // 委托样品处理
            BusGetDetail delbusgetdetail = new BusGetDetail();
            delbusgetdetail.setTranid(busget.getTranid());
            delbusgetdetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetDetail(session, delbusgetdetail);

            // 删除委托样品检测参数
            BusSampleParam delbussampleparam = new BusSampleParam();
            delbussampleparam.setTranid(busget.getTranid());
            delbussampleparam.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusSampleParam(session, delbussampleparam);

            for (BusGetDetail busgetdetail : getdetails) {
                busgetdetail.setTranid(busget.getTranid());
                busgetdetail.setTestitems(parameternames);
                busgetdetail.setTesttype(busget.getTesttype());
                busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusGetDetail(session, busgetdetail);

                for (BusSampleParam bussampleparam : sampleparams) {
                    bussampleparam.setTranid(busget.getTranid());
                    bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                    bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusSampleParam(session, bussampleparam);
                }
            }

            for (BusGetDetail getdetail : getdetails) {
                String sampleid = getdetail.getSampleid();

                BusSampleParam bussampleparam = new BusSampleParam();
                // bussampleparam.setTranid(busget.getTranid());
                bussampleparam.setSampleid(sampleid);

                List<BusSampleParam> bussampleparams = LabDao.GetListBusSampleParam(session, bussampleparam);
                String parameterids = "";
                for (BusSampleParam param : bussampleparams) {
                    if (getdetail.getSampleid().equals(param.getSampleid())) {
                        if (parameterids.equals("")) {
                            parameterids = param.getParameterid();
                        } else {
                            parameterids = parameterids + "," + param.getParameterid();
                        }
                    }
                }

                // session.commit();

                BasLabParameter blp = new BasLabParameter();
                blp.setSampleid(getdetail.getSampleid());
                blp.setParameterids(parameterids);
            }

            resultMap.put("success", true);
            resultMap.put("data", "委托书提交成功");
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询当前用户的任务单明细
    @WebMethod
    public @WebResult String SearchBusTaskDetail(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {

            System.out.println("调试。。。。。。。SearchBusTaskDetail");
            // 当前客户的任务单
            BusTask item = new BusTask();

            BusTaskTest btt = new BusTaskTest();
            btt.setTaskid(selparam);

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(selparam);

            String search = "";

            search += ToolUtils.GetAndSearch(search) + "  a.taskid = '" + selparam + "'";
            item.getSearch().setSearch(search);
            List<BusTask> lists = LabAndroidDao.SearchBusTaskForUser(item);

            String samplecode = lists.get(0).getSamplecode();

            List<BusTaskTest> btts = new ArrayList<BusTaskTest>();

            if (samplecode.indexOf("多样品样品编号") > 0) {
                btts = LabDao.GetListBusTaskTestForMore(btt);
            } else {
                btts = LabDao.GetListBusTaskTest(btt);
            }

            List<BusTaskSample> btss = LabDao.GetListBusTaskForAcc(bts);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            StringBuilder sb = new StringBuilder();
            item = lists.get(0);

            item = LabDao.GetBusTask(item);

            sb.append(LabAndroidService.GetInfoHtmlHeader());
            sb.append(LabAndroidService.GetBusTaskHtml(item));
            sb.append(LabAndroidService.GetBusTaskTestHtml(btts));
            sb.append(LabAndroidService.GetBusTaskSampleHtml(btss));
            sb.append(LabAndroidService.GetBusTaskResultHtml(btss));
            sb.append(LabAndroidService.GetInfoHtmlBottom());

            resultMap.put("success", true);
            resultMap.put("data", sb.toString());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // region 查询当前用户的任务单明细
    @WebMethod
    public @WebResult String SearchBusGetPrintDetail(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        String formid = "";

        try {

            System.out.println("调试。。。。。。。SearchBusGetPrintDetail");
            String[] d = selparam.split(",");

            if ((!ToolUtils.StringIsEmpty(d[0])) && (!ToolUtils.StringIsEmpty(d[1]))) {

                if (d[1].equals("01")) {
                    formid = "000008";
                } else if (d[1].equals("02")) {
                    formid = "000007";
                } else if (d[1].equals("03")) {
                    formid = "000009";
                } else if (d[1].equals("04")) {
                    formid = "000010";
                } else if (d[1].equals("05")) {
                    formid = "000001";
                } else if (d[1].equals("07")) {
                    formid = "000003";
                } else if (d[1].equals("08")) {
                    formid = "000004";
                } else if (d[1].equals("11")) {
                    formid = "000011";
                } else if (d[1].equals("12")) {
                    formid = "000012";
                } else {
                    formid = "000004";
                }
                FrmGet frg = new FrmGet();
                frg.setFormid(formid);
                frg.setGettype(d[1]);
                frg.setTranid(d[0]);

                StringBuilder sb = new StringBuilder();
                sb.append("<html><head></head><body>");
                sb.append(LabAndroidService.GetPrintBusGetHtml(frg));
                sb.append("</body></html>");
                System.out.println(sb);
                resultMap.put("success", true);
                resultMap.put("data", sb.toString());
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询当前用户的任务单列表
    @WebMethod
    public @WebResult String SearchBusTaskForUser(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();
        SqlSession session = DBUtils.getFactory();

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。SearchBusTaskForUser=" + userid);
            ou = UserDao.GetOnlineUser(session, userid);
            BusTask item = new BusTask();
            String search = "";
            String[] d = selparam.split(",");
            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + "  a.labid = '" + ou.getDept().getDeptid() + "'";
                }
            }

            if (!ToolUtils.StringIsEmpty(d[0])) {
                search += ToolUtils.GetAndSearch(search) + "  a.senddate >= " + ToolUtils.GetBeginDate(d[0]); // 开始时间
            }
            if (!ToolUtils.StringIsEmpty(d[1])) {
                search += ToolUtils.GetAndSearch(search) + "  a.senddate <= " + ToolUtils.GetEndDate(d[1]); // 结束时间
            }
            item.getSearch().setSearch(search);
            // 当前客户的任务单
            List<BusTask> lists = LabAndroidDao.SearchBusTaskForUser(item);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String taskJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", taskJson);

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // region 查询当前用户的抽样单列表
    @WebMethod
    public @WebResult String SearchBusGetForPrint(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();
        SqlSession session = DBUtils.getFactory();

        try {

            System.out.println("调试。。。。。。。SearchBusGetForPrint=" + userid);
            ou = UserDao.GetOnlineUser(session, userid);
            BusGet item = new BusGet();
            String search = "";
            String[] d = selparam.split(",");

            if (!ToolUtils.StringIsEmpty(d[0])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate >= " + ToolUtils.GetBeginDate(d[0]); // 开始时间
            }
            if (!ToolUtils.StringIsEmpty(d[1])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate <= " + ToolUtils.GetEndDate(d[1]); // 结束时间
            }
            if (!ToolUtils.StringIsEmpty(d[2])) {
                search += ToolUtils.GetAndSearch(search) + "  a.gettype = '" + d[2] + "' "; // 抽样单类型
            }
            if (!ToolUtils.StringIsEmpty(userid)) {
                search += ToolUtils.GetAndSearch(search) + "  a.sampleuser = '" + userid + "' "; // 抽样单类型
            }
            item.getSearch().setSearch(search);
            // 当前客户的任务单
            List<BusGet> lists = LabAndroidDao.SearchBusGetForPrint(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String printJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", printJson);

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    @WebMethod
    public @WebResult String SearchBusTaskBySamplecode(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();
        SqlSession session = DBUtils.getFactory();

        try {

            System.out.println("调试。。。。。。。SearchBusTaskBySamplecode");
            ou = UserDao.GetOnlineUser(session, userid);
            BusTask item = new BusTask();
            String search = "";

            String deptpid = ou.getDept().getDeptpid();

            if (!ToolUtils.StringIsEmpty(deptpid)) {
                if (deptpid.equals("8000")) {
                    search += ToolUtils.GetAndSearch(search) + "  a.labid = '" + ou.getDept().getDeptid() + "'";
                }
            }

            search += ToolUtils.GetAndSearch(search) + "  a.samplecode = '" + selparam + "'";

            item.getSearch().setSearch(search);
            // 当前客户的任务单
            List<BusTask> lists = LabAndroidDao.SearchBusTaskForUser(item);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String taskJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", taskJson);

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询当前用户待接任务单
    @WebMethod
    public @WebResult String SearchToBeReceivedTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();
        SqlSession session = DBUtils.getFactory();

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。SearchToBeReceivedTask=" + userid);
            BusTask item = new BusTask();
            ou = UserDao.GetOnlineUser(session, userid);

            String search = "";
            String[] d = selparam.split(",");

            search += ToolUtils.GetAndSearch(search) + "  a.labid = '" + ou.getDept().getDeptid() + "'";
            search += ToolUtils.GetAndSearch(search) + "  a.flowstatus = '84'  ";
            if (!ToolUtils.StringIsEmpty(d[0])) {
                search += ToolUtils.GetAndSearch(search) + "  a.senddate >= " + ToolUtils.GetBeginDate(d[0]); // 开始时间
            }
            if (!ToolUtils.StringIsEmpty(d[1])) {
                search += ToolUtils.GetAndSearch(search) + "  a.senddate <= " + ToolUtils.GetEndDate(d[1]); // 结束时间
            }

            item.getSearch().setSearch(search);
            // 前用户待接任务单
            List<BusTask> lists = LabAndroidDao.SearchBusTaskForUser(item);
            System.out.println(lists);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String taskJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", taskJson);

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 任务单接单
    @WebMethod
    public @WebResult String ReceiveBusTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        SqlSession session = DBUtils.getFactory();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();

        try {
            ou = UserDao.GetOnlineUser(session, userid);

            // 保存日志
            TranLog log = new TranLog();
            log.setTranuser(ou.getUser().getUserid());
            log.setTrandept(ou.getUser().getDeptid());
            log.setUsername(ou.getUser().getUsername());
            log.setTrancode("");

            System.out.println("调试。。。。。。。ReceiveBusTask");

            BusTask item = new BusTask();
            item.setTaskid(selparam);
            item = LabDao.GetBusTask(item);
            item.setAcceptuser(ou.getUser().getUserid());
            item.setAcceptusername(ou.getUser().getUsername());
            item.setAcceptdate(new Date());
            item.getDeal().setAction(DataAction.Deal.getAction());
            // 修改任务单状态
            LabDao.SaveBusTask(session, item);

            // 接收后删除接收提醒
            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(item.getTaskid());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            log.ActionToTran(item.getDeal().getAction());
            log.setTrandesc(item.getTaskid());
            PublicDao.SaveTranLog(session, log);

            session.commit();
            resultMap.put("success", true);
            resultMap.put("data", "接单成功！");

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "接单失败！");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;

    }
    // endregion

    // 接受前台图片字节流保存到服务器返回图片地址的String
    @WebMethod
    public @WebResult String SaveTakeSignJpgOnServer(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {
            System.out.println("调试......SaveTakeSignJpgOnServer");
            // 将前台传过来的图片字符串保存到服务器并获取保存地址
            String fileName = JsonUtils.getPhotoByBuffer(selparam);

            resultMap.put("success", true);
            resultMap.put("data", fileName);
            resultMap.put("message", "图片上传成功");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "上传图片失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;

    }

    // 样品管理员签名图片保存
    @WebMethod
    public @WebResult String SaveSendSignJpgOnServer(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {
            System.out.println("调试......SaveSendSignJpgOnServer");
            // 将前台传过来的图片字符串保存到服务器并获取保存地址
            String fileName = JsonUtils.getPhotoByBuffer(selparam);

            resultMap.put("success", true);
            resultMap.put("data", fileName);
            resultMap.put("message", "图片上传成功");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "上传图片失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;

    }

    // region 取样任务单
    @WebMethod
    public @WebResult String SearchBusTaskForTakeSample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        OnlineUser ou = new OnlineUser();

        try {
            System.out.println("调试。。。。。。。SearchBusTaskForTaskSample");
            ou = UserDao.GetOnlineUser(session, userid);

            BusTask item = new BusTask();
            String search = "";
            search += ToolUtils.GetAndSearch(search) + "  a.flowstatus = '82'  "; // 82
                                                                                  // 已接单
            search += ToolUtils.GetAndSearch(search) + "  a.labid = '" + ou.getUser().getDeptid() + "'";
            item.getSearch().setSearch(search);
            // 前用户待接任务单
            List<BusTask> lists = LabAndroidDao.SearchBusTaskForTakeSample(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String notice = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", notice);
            resultMap.put("msg", "成功");
        } catch (Exception e) {
            resultMap.put("msg", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 样品取样完成
    @WebMethod
    public @WebResult String TakeSampleBusTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        try {
            System.out.println("调试。。。。。。。TakeSampleBusTask");

            // JSONObject jsonObject = JSONObject.fromObject(selparam);
            JSONArray jsonObject = JSONArray.fromObject(selparam);
            System.out.print(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            // busTask = (BusTask)JSONObject.toBean(jsonObject,BusTask.class);
            List<BusTask> busTasks = ToolUtils.GetArrayFromJson(jsonObject, BusTask.class);

            for (BusTask busTask : busTasks) {
                BusTask bTask = new BusTask();
                bTask = LabDao.GetBusTask(busTask);
                bTask.setTakenumber(busTask.getTakenumber());
                bTask.setTakeusername(busTask.getTakeusername());
                bTask.setSamplestate(busTask.getSamplestatus());
                bTask.setSendsampleuser(busTask.getSendsampleuser());
                bTask.setTakesign(busTask.getTakesign());
                bTask.setSendsign(busTask.getSendsign());
                bTask.setTakepath(busTask.getTakepath());
                bTask.setSendpath(busTask.getSendpath());
                bTask.setTakedate(new Date());
                bTask.setFlowstatus("83");
                bTask.getDeal().setAction(DataAction.Deal.getAction());
                // 修改任务单状态
                LabDao.SaveBusTask(session, bTask);
            }

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "成功");
            resultMap.put("message", "保存成功");

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

    // 查询试验样品编号做实验准备
    @WebMethod
    public @WebResult String SearchLabBegin(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        try {
            System.out.println("调试。。。。。。。SearchLabBegin");
            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setTestuser(userid);
            bustasksingle.setSampleid("1");
            List<BusTaskSingle> bustasksinglelist = LabAndroidDao.GetBusTaskSingleByUserId(bustasksingle);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String task = ToolUtils.GetJsonFromArray(bustasksinglelist, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, task); // 任务单

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());
            resultMap.put("message", "成功");

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "当前任务单状态非已接单状态");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // 查询试验样品编号查看记录表
    @WebMethod
    public @WebResult String SearchLabRecord(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        try {
            System.out.println("调试。。。。。。。SearchLabRecord");
            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setTestuser(userid);
            bustasksingle.setSampleid("3");
            List<BusTaskSingle> bustasksinglelist = LabAndroidDao.GetBusTaskSingleByUserId(bustasksingle);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String task = ToolUtils.GetJsonFromArray(bustasksinglelist, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, task); // 任务单

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());
            resultMap.put("message", "成功");

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "当前任务单状态非已接单状态");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // 查询试验样品编号做实验
    @WebMethod
    public @WebResult String SearchLabEnd(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        try {
            System.out.println("调试。。。。。。。SearchLabend");
            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setTestuser(userid);
            bustasksingle.setSampleid("2");
            List<BusTaskSingle> bustasksinglelist = LabAndroidDao.GetBusTaskSingleByUserId(bustasksingle);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String task = ToolUtils.GetJsonFromArray(bustasksinglelist, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, task); // 任务单

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());
            resultMap.put("message", "成功");

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "当前任务单状态非已接单状态");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // region 查询试验准备前基础信息

    @WebMethod
    public @WebResult String SearchInfoBeforeLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        try {
            System.out.println("调试。。。。。。。SearchInfoBeforeLab");

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setSamplecode(selparam);
            bustasksingle.setTestuser(userid);
            bustasksingle = LabAndroidDao.GetBusTaskSingleBySampleCode(bustasksingle);

            BusTaskTester bttr = new BusTaskTester();
            bttr.setSampletran(bustasksingle.getSampletran());
            List<BusTaskTester> bttrs = LabDao.GetListBusTaskTesterBySingle(bttr);

            // 查询设备
            BusTaskDev btd = new BusTaskDev();
            btd.setTaskid(bustasksingle.getTaskid());
            List<BusTaskDev> basDevs = LabDao.GetListBusTaskDev(btd);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String task = ToolUtils.GetJsonFromBean(bustasksingle, config);
            String devs = ToolUtils.GetJsonFromArray(basDevs, config);
            String tests = ToolUtils.GetJsonFromArray(bttrs, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, task); // 任务单
            jsonArray.add(1, tests);// 检测参数
            jsonArray.add(2, devs); // 检测设备

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());
            resultMap.put("message", "成功");

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "当前任务单状态非已接单状态");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // endregion

    // region 根据部门查询检测设备
    @WebMethod
    public @WebResult String SearchBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            ou = UserDao.GetOnlineUser(session, userid);
            System.out.println("调试。。。。。。。SearchBasDevForLab" + ou.getUser().getDeptid());

            // 查询设备
            BasDev bd = new BasDev();
            bd.setLabid(ou.getUser().getDeptid());
            List<BasDev> basDevs = DevDao.GetListBasDevByLab(bd);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String devs = ToolUtils.GetJsonFromArray(basDevs, config);

            resultMap.put("success", true);
            resultMap.put("data", devs);

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 添加试验检测设备
    @WebMethod
    public @WebResult String AddBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。AddBasDevForLab");
            JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
            BusTaskDev bd = new BusTaskDev();
            bd = (BusTaskDev) JSONObject.toBean(jsonObject, BusTaskDev.class);

            bd.setDevuse(new Date());
            bd.setDevconfirm(null);
            bd.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusTaskDev(session, bd);
            session.commit();
            // 新增之后重新查询设备列表
            List<BusTaskDev> basDevs = LabDao.GetListBusTaskDev(bd);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            String dev = ToolUtils.GetJsonFromArray(basDevs, config);

            resultMap.put("success", true);
            resultMap.put("data", dev);

        } catch (Exception e) {
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "添加失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 删除试验检测设备
    @WebMethod
    public @WebResult String DeleteBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。DeleteBasDevForLab");
            JSONObject jsonObject = JSONObject.fromObject(selparam);

            BusTaskDev bd = new BusTaskDev();
            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            bd = (BusTaskDev) JSONObject.toBean(jsonObject, BusTaskDev.class);
            bd.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTaskDev(session, bd);
            session.commit();
            // 删除之后重新查询设备列表
            List<BusTaskDev> basDevs = LabDao.GetListBusTaskDev(bd);

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            String dev = ToolUtils.GetJsonFromArray(basDevs, config);

            resultMap.put("success", true);
            resultMap.put("data", dev);

        } catch (Exception e) {
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "删除失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询设备明细
    @WebMethod
    public @WebResult String GetBasDevInfo(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBasDevInfo");
            // 查询设备
            BasDev item = new BasDev();
            item.setDevid(selparam);
            item = DevDao.GetBasDev(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            sb.append(LabAndroidService.GetBasDevHtml(item));
            sb.append(LabAndroidService.GetInfoHtmlBottom());

            resultMap.put("success", true);
            resultMap.put("data", sb.toString());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 保存试验准备信息
    @WebMethod
    public @WebResult String SavePrepareLabInfo(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SavePrepareLabInfo");
            JSONObject jsonObject = JSONObject.fromObject(selparam);
            System.out.println(selparam);
            System.out.println(jsonObject);

            // 保存试验准备数据
            BusTaskSingle btsg = new BusTaskSingle();
            String[] dateFormats = new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
            btsg = (BusTaskSingle) JSONObject.toBean(jsonObject, BusTaskSingle.class);

            Date begintestdate = btsg.getBegintestdate();
            String testtemp = btsg.getTesttemp();
            String testhum = btsg.getTesthum();
            String testenv = btsg.getTestenv();
            String teststandardname = btsg.getTeststandardname();
            String devids = btsg.getDevids();
            String devnames = btsg.getDevnames();
            String getuser = btsg.getGetuser();
            String getusername = btsg.getGetusername();
            String sampletran = btsg.getSampletran();
            String taskremark = btsg.getTaskremark();
            Date getdate = btsg.getGetdate();
            String getcount = btsg.getGetcount();

            btsg = LabDao.GetBusTaskSingle(btsg);
            btsg.setFlowstatus("80");
            btsg.setBegintestdate(begintestdate);
            btsg.setTesttemp(testtemp);
            btsg.setTesthum(testhum);
            btsg.setTestenv(testenv);
            btsg.setTeststandardname(teststandardname);

            String devinfos = "";
            if (!devids.equals("")) {
                String devidlist[] = devids.split(",");
                String devnamelist[] = devnames.split(",");

                for (int i = 0; i < devidlist.length; i++) {
                    if (i == 0) {
                        devinfos = devnamelist[i] + "," + devidlist[i];
                    } else {
                        devinfos = devinfos + "," + devnamelist[i] + devidlist[i];
                    }
                }
            }

            btsg.setDevids(devids);
            btsg.setDevnames(devinfos);
            btsg.setGetuser(getuser);
            btsg.setGetusername(getusername);
            btsg.setGetdate(getdate);
            btsg.setGetcount(getcount);
            btsg.setSampletran(sampletran);
            btsg.setTaskremark(taskremark);
            btsg.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTaskSingleLab(session, btsg);

            // 更新t_bus_task表的状态
            BusTask bTask = new BusTask();
            bTask.setTaskid(btsg.getTaskid());
            bTask = LabDao.GetBusTask(bTask);
            bTask.setFlowstatus(btsg.getFlowstatus());
            bTask.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusTask(session, bTask);

            // 提醒修改为试验判定
            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(btsg.getSampletran());
            busTodo.setTranuser(userid);
            busTodo.setBusflow("LabReady");
            List<BusTodo> busTodos = FlowDao.GetListBusTodo(busTodo);
            busTodo.setSampleid(String.valueOf(busTodos.size()));
            busTodo = FlowDao.GetBusTodo(busTodo);
            if (busTodo != null) {
                busTodo.setSenddate(new Date());
                busTodo.setBusflow("StartLab");
                busTodo.setTodotype(TodoType.StartLab.getTodotype());
                busTodo.setTranuser(userid);
                busTodo.getDeal().setAction(DataAction.Modify.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
            }

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(btsg.getTaskid());
            bts.setSampletran(sampletran);
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setBegintestdate(begintestdate);
            bts.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            BusTaskDev deldev = new BusTaskDev();
            deldev.setSampletran(sampletran);
            deldev.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskDev(session, deldev);

            String[] devidsarray = devids.split(",");
            for (String devid : devidsarray) {
                BusTaskDev btd = new BusTaskDev();
                btd.setTaskid(btsg.getTaskid());
                btd.setSampleid(btsg.getSampleid());
                btd.setDevid(devid);
                btd.setDevuse(new Date());
                btd.setDevconfirm(null);
                btd.setSampletran(sampletran);
                btd.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusTaskDev(session, btd);
            }

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "保存成功");
            resultMap.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "保存失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询试验及试验检测参数
    @WebMethod
    public @WebResult String SearchParamForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SearchParamForLab");

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setSamplecode(selparam);
            bustasksingle.setTestuser(userid);
            bustasksingle = LabAndroidDao.GetBusTaskSingleBySampleCode(bustasksingle);

            BusTaskTester bttr = new BusTaskTester();
            bttr.setSampletran(bustasksingle.getSampletran());
            List<BusTaskTester> bttrs = LabDao.GetListBusTaskTesterBySingle(bttr);

            List<IntInterface> interfaces = FormService.GetIntInterfaceByTask(bustasksingle);
            Map<String, Object> fields = new HashMap<String, Object>();

            IntField iField = new IntField();
            for (int i = 0; i < interfaces.size(); i++) {
                iField.setIntid(interfaces.get(i).getIntid());
                List<IntField> intfields = FormDao.GetListIntField(iField);
                fields.put(interfaces.get(i).getIntid(), intfields);
            }

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            config.registerPropertyExclusions(iField.getClass(), ((BaseBean) iField).OnExclusions());

            String task = ToolUtils.GetJsonFromBean(bustasksingle, config);
            String inter = ToolUtils.GetJsonFromArray(interfaces, config);
            String field = ToolUtils.GetJsonFromArray(fields, config);
            String tests = ToolUtils.GetJsonFromArray(bttrs, config);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(0, task); // 任务单
            jsonArray.add(1, inter); // 需检测的参数
            jsonArray.add(2, field); // 具体检测值
            jsonArray.add(3, tests); // 检测参数

            resultMap.put("success", true);
            resultMap.put("data", jsonArray.toString());
            resultMap.put("message", "获取信息成功");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "获取信息失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 保存试验数据，结束试验

    @WebMethod
    public @WebResult String FinishTest(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // OnlineUser ou = new OnlineUser();
        try {
            // ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。FinishTest");

            JSONArray jsonObject = JSONArray.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle = (BusTaskSingle) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)),
                    BusTaskSingle.class);

            List<BusTaskData> taskDatas = ToolUtils.GetArrayFromJson((String) jsonObject.get(1), BusTaskData.class);

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(bustasksingle.getTaskid());
            bts.setSampletran(bustasksingle.getSampletran());
            bts = LabDao.GetBusTaskSample(session, bts);
            bts.setEndtestdate(bustasksingle.getEndtestdate());
            bts.setAuditstatus("78");
            bts.getDeal().setAction(DataAction.Submit.getAction());
            LabDao.SaveBusTaskSample(session, bts);

            BusTaskDev deldev = new BusTaskDev();
            deldev.setSampletran(bustasksingle.getSampletran());
            deldev.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskDev(session, deldev);

            BusTaskData deldata = new BusTaskData();
            deldata.setSampletran(bustasksingle.getSampletran());
            deldata.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusTaskData(session, deldata);

            // 保存试验数据
            for (BusTaskData bustaskdata : taskDatas) {
                bustaskdata.setSampletran(bustasksingle.getSampletran());
                bustaskdata.setTaskid(bustasksingle.getTaskid());
                bustaskdata.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusTaskData(session, bustaskdata);

                if (bustaskdata.getFieldtype().equals("07")) {
                    BusTaskDev btd = new BusTaskDev();
                    btd.setTaskid(bustasksingle.getTaskid());
                    btd.setSampleid(bustasksingle.getSampleid());
                    btd.setDevid(bustaskdata.getSubmitvalue());
                    btd.setDevuse(bts.getBegintestdate());
                    btd.setDevconfirm(bts.getEndtestdate());
                    btd.setSampletran(bustasksingle.getSampletran());
                    btd.setFieldcode(bustaskdata.getFieldcode());
                    btd.setIntid(bustaskdata.getIntid());
                    btd.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusTaskDev(session, btd);
                }
            }

            int btscount = 0;
            BusTaskSample btsample = new BusTaskSample();
            btsample.setTaskid(bustasksingle.getTaskid());
            List<BusTaskSample> btsamples = LabDao.GetListBusTaskSample(btsample);
            for (BusTaskSample bustasksample : btsamples) {
                if (bustasksample.getEndtestdate() == null) {
                    btscount = btscount + 1;
                }
            }

            Date endtestdate = bustasksingle.getEndtestdate();
            String testtemp = bustasksingle.getTesttemp();
            String testhum = bustasksingle.getTesthum();
            String testenv = bustasksingle.getTestenv();
            String devids = bustasksingle.getDevids();
            String devnames = bustasksingle.getDevnames();

            bustasksingle = LabDao.GetBusTaskSingle(bustasksingle);
            if (btscount == 0) {
                // item.setFlowstatus("78");
            }
            bustasksingle.setEndtestdate(endtestdate);
            bustasksingle.setTesttemp(testtemp);
            bustasksingle.setTesthum(testhum);
            bustasksingle.setTestenv(testenv);
            bustasksingle.setDevids(devids);
            bustasksingle.setDevnames(devnames);
            bustasksingle.getDeal().setAction(DataAction.Deal.getAction());

            LabDao.SaveBusTaskSingleLab(session, bustasksingle);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 计算试验数据
    @WebMethod
    public @WebResult String ComputeLabData(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        TranLog log = new TranLog();
        OnlineUser ou = new OnlineUser();
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。ComputeLabData");
            JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusTaskSingle bustasksingle = (BusTaskSingle) JSONObject.toBean(jsonObject, BusTaskSingle.class);

            JudgeBusTask(session, bustasksingle, log);

            String sampletran = bustasksingle.getSampletran();
            bustasksingle = LabDao.GetBusTaskSingle(session, bustasksingle);

            bustasksingle.setFlowstatus("76");
            bustasksingle.getDeal().setAction(DataAction.Deal.getAction());
            bustasksingle.setSampletran(sampletran);
            LabDao.SaveBusTaskSingleLab(session, bustasksingle);

            BusTaskSample bts = new BusTaskSample();
            bts.setTaskid(bustasksingle.getTaskid());
            List<BusTaskSample> bSamples = LabDao.GetBusTaskSampleByTask(session, bts);
            int isjudge = 0;
            if (bSamples.size() > 0) {
                for (BusTaskSample bSample : bSamples) {
                    if (!bSample.getIsjudge()) {
                        isjudge++;
                    }
                }
            }

            if (isjudge == 0) {
                // 更新t_bus_task表的状态
                BusTask bTask = new BusTask();
                bTask.setTaskid(bustasksingle.getTaskid());
                bTask = LabDao.GetBusTask(bTask);
                bTask.setFlowstatus("78");
                bTask.getDeal().setAction(DataAction.Special01.getAction());
                LabDao.SaveBusTask(session, bTask);
            }

            BusRecord bRecord = new BusRecord();
            bRecord.setSampletran(bustasksingle.getSampletran());
            List<BusRecord> bRecords = DatDao.GetBusRecordBySampleTran(bRecord);
            for (BusRecord busRecord : bRecords) {
                busRecord.setRecordstatus("01");
                busRecord.getDeal().setAction(DataAction.Modify.getAction());
                DatDao.SaveBusRecord(session, busRecord);
            }

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(bustasksingle.getSampletran());
            busTodo.setTranuser(ou.getUser().getUserid());
            busTodo.getDeal().setAction(DataAction.Deal.getAction());
            FlowDao.SaveBusTodo(session, busTodo);

            log.ActionToTran(bustasksingle.getDeal().getAction());
            log.setTrandesc("");
            PublicDao.SaveTranLog(session, log);

            session.commit();
            resultMap.put("success", true);
            resultMap.put("data", "完成计算");

        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", ToolUtils.GetErrorMessage(e, Consts.STR_SAVE_F));
        } finally {
            session.close();
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    public static void JudgeBusTask(SqlSession session, BusTaskSingle item, TranLog log) throws Exception {
        item.getDeal().setUserid(log.getTranuser());
        LabDao.ComputeBusTask(session, item);

        int i = 0;

        BusTaskSample bts = new BusTaskSample();
        bts.setSampletran(item.getSampletran());
        bts.setTaskid(item.getTaskid());
        BusTaskSample tsample = LabDao.GetBusTaskSample(session, bts);

        if (ToolUtils.StringIsEmpty(tsample.getTaskid())) {
            throw new Exception("读取任务单信息出错！");
        }

        if (tsample.getBegintestdate() == null) {
            throw new Exception("请先进行开始试验操作！");
        }

        if (tsample.getEndtestdate() == null) {
            throw new Exception("请先进行结束试验操作！");
        }

        BusTaskData btd = new BusTaskData();
        btd.setTaskid(item.getTaskid());
        btd.setSampletran(item.getSampletran());
        List<BusTaskData> datas = LabDao.GetListBusTaskData(session, btd);

        for (BusTaskData data : datas) {
            if (data.getFieldtype().equals("01") && !ToolUtils.StringIsEmpty(data.getSubmitvalue())) {
                try {
                    Double.parseDouble(data.getSubmitvalue());
                } catch (Exception e) {
                    throw new Exception(data.getFieldcode() + "存在非数值的数据！");
                }
            }

            if (data.getFieldtype().equals("02")) {
                if (ToolUtils.MustComboValue(data.getSubmitvalue()))
                    throw new Exception(data.getFieldcode() + "存在未选择的数据！");
            }
        }

        String samplecode = item.getSamplecode();
        BusGetDetail busGetDetail = new BusGetDetail();
        busGetDetail.setSamplecode(item.getSamplecode());
        // busGetDetail.setTaskid(item.getTaskid());
        busGetDetail = LabDao.GetBusGetDetailBySampleCode(busGetDetail);
        if ((busGetDetail == null) || ToolUtils.StringIsEmpty(busGetDetail.getTranid())) {
            throw new Exception("读取取样明细出错！");
        }

        // 获取取样单信息
        BusGet get = LabDao.GetBusGetBySampleCode(samplecode);
        if ((get == null) || ToolUtils.StringIsEmpty(get.getTranid())) {
            throw new Exception("读取取样信息出错！");
        }

        int groupserial = 1;
        int maxspecserial = 0;

        List<BusRecordData> gets = SetGet(get);
        List<BusRecordData> getdetails = SetGetDetail(busGetDetail, groupserial);
        List<BusRecordData> nowdatas = SetTask(item);

        List<BusRecordDetail> rdetail = DatDao.GetMaxSpecSerial(session, item);

        nowdatas.addAll(getdetails);
        nowdatas.addAll(gets);

        for (BusTaskData data : datas) {
            if (data.getSampletran().equals(item.getSampletran())) {
                AddRecordData(nowdatas, ClassSource.Data.getClasssource(), data.getFieldcode(), data.getFieldtype(),
                        data.getSubmitvalue(), data.getDisplayvalue(), groupserial, data.getSpecserial());

                if (data.getSpecserial() > maxspecserial) {
                    maxspecserial = data.getSpecserial();
                }
            }
        }

        List<DatComputeData> sets = DatDao.GetComputeDataBySet(session, item);

        Pattern pa = Pattern.compile("\\{[^{}]*\\}");
        Matcher ma;
        String matchfield = "";
        String actformula = "";
        String actvalue = "";

        List<String> avelists = new ArrayList<String>();
        String aveact = "";

        String[] funcargs;
        int argindex = 0;
        int validx = 0;
        String lastresult = "";
        boolean hasjudge = false;
        int arglen = 0;
        String phjudge = "";
        // String getsubmitvalue = "";
        // boolean isget = false;

        for (DatComputeData comcd : sets) {

            // region计算取值
            comcd.setActformula(comcd.getActformula().trim());
            if (comcd.getDatasource().equals(DataSource.FuncData.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getActformula())) {
                    continue;
                }

                avelists.clear();
                for (BusTaskData datahl : datas) {
                    if (datahl.getFieldcode().equals(comcd.getClassfield()) && !datahl.getSubmitvalue().equals("")) {
                        avelists.add(datahl.getSubmitvalue());
                    }
                }

                if (avelists.size() == 0) {
                    int nowmaxspecserial = 0;

                    // if (comcd.isIsserial()) {
                    nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());
                    if (nowmaxspecserial == 0) {
                        nowmaxspecserial = 1;
                    }
                    nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                    for (i = 1; i <= nowmaxspecserial; i++) {
                        actformula = comcd.getActformula();
                        ma = pa.matcher(comcd.getActformula());

                        while (ma.find()) {
                            matchfield = ma.group().replace("{", "").replace("}", "");

                            actformula = actformula.replace(ma.group(),
                                    GetSubmitValue(nowdatas, sets, matchfield, groupserial, i));
                        }

                        try {
                            actvalue = String.valueOf(jse.eval(actformula));
                            BusTaskTest bTest = new BusTaskTest();
                            bTest.setTaskid(item.getTaskid());
                            bTest.setSampleid(comcd.getSampleid());
                            bTest.setParameterid(comcd.getParameterids());
                            bTest = LabDao.GetBusTaskTest(bTest);
                            // 获取标准值的小数位数
                            String judgevalue = bTest.getStandvalue();
                            if (judgevalue.contains("-")) {
                                String[] juvalues = judgevalue.split("-");
                                judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                            }
                            if (judgevalue.contains("~")) {
                                String[] juvalues = judgevalue.split("~");
                                judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                            }
                            int dcimalDigits = 5;// 判定值为空时，默然保留位数
                            int indexOf = judgevalue.indexOf(".");
                            if (indexOf > 0) {
                                if (item.getLabid().equals("8003")) {
                                    // dcimalDigits为标准值小数位数一致（三室要求规定）
                                    dcimalDigits = judgevalue.length() - indexOf - 1;
                                } else {
                                    // dcimalDigits为标准值小数位数多一位（检测标准规定）
                                    dcimalDigits = judgevalue.length() - indexOf;
                                }
                            }

                            actvalue = actvalue.substring(0, actvalue.indexOf(".") + dcimalDigits + 1);

                            // //计算结果保留两位小数
                            // if (actvalue.indexOf(".") >= 0) {
                            // if ((actvalue.length() - actvalue.indexOf(".") - 1) > 2)
                            // actvalue = actvalue.substring(0, actvalue.indexOf(".") + 3);
                            // }
                        } catch (Exception e) {
                            actvalue = "ND";
                        }

                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(),
                                actvalue, actvalue, groupserial, i);

                    }
                    // }
                }
            }

            // endregion计算取值

            // region 平均值

            // AveData代表的是该平均值用于判定是否合格，AveNotJudge代表平均值用于计算中，不用于判定中
            if (comcd.getDatasource().equals(DataSource.AveData.getDatasource())
                    || comcd.getDatasource().equals(DataSource.AveNotJudge.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))// avg
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))// {data.awjshl}
                    continue;

                avelists.clear();
                aveact = comcd.getActformula().replace("{", "").replace("}", "");
                int nowmaxspecserial = 0;
                nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());

                // if(maxspecserial==0){
                // maxspecserial = 1;
                // }

                nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                for (i = 1; i <= nowmaxspecserial; i++) {
                    for (BusRecordData data : nowdatas) {
                        if (aveact.equals(data.getClasssource() + "." + data.getFieldcode())
                                && (data.getSpecserial() == i)) {
                            if (!data.getSubmitvalue().equals("")) {
                                avelists.add(data.getSubmitvalue());
                            }

                        }
                    }
                }

                if (avelists.size() <= 0) {
                    actvalue = "";
                } else {
                    switch (comcd.getFuncformula().toLowerCase()) {
                    case "avg":
                        BusTaskTest bTest = new BusTaskTest();
                        bTest.setTaskid(item.getTaskid());
                        bTest.setSampleid(comcd.getSampleid());
                        bTest.setParameterid(comcd.getParameterids());
                        bTest = LabDao.GetBusTaskTest(bTest);
                        // 获取标准值的小数位数
                        String judgevalue = bTest.getStandvalue();
                        if (judgevalue.contains("-")) {
                            String[] juvalues = judgevalue.split("-");
                            judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                        }
                        if (judgevalue.contains("~")) {
                            String[] juvalues = judgevalue.split("~");
                            judgevalue = juvalues[0].length() > juvalues[1].length() ? juvalues[0] : juvalues[1];
                        }
                        int dcimalDigits = 0;
                        int indexOf = judgevalue.indexOf(".");
                        if (indexOf > 0) {
                            if (item.getLabid().equals("8003")) {
                                // dcimalDigits为标准值小数位数一致（三室要求规定）
                                dcimalDigits = judgevalue.length() - indexOf - 1;
                            } else {
                                // dcimalDigits为标准值小数位数多一位（检测标准规定）
                                dcimalDigits = judgevalue.length() - indexOf;
                            }

                            actvalue = MathService.Ave(avelists, comcd.getDefvalue(), dcimalDigits);
                        } else {
                            actvalue = MathService.Ave(avelists, comcd.getDefvalue());
                        }
                        break;

                    case "statmax":
                        actvalue = MathService.statmax(avelists, comcd.getDefvalue());
                        break;

                    case "statmin":
                        actvalue = MathService.statmin(avelists, comcd.getDefvalue());
                        break;

                    case "statcount":
                        actvalue = MathService.statcount(avelists, comcd.getDefvalue());
                        break;

                    case "avemean3":
                        actvalue = MathService.AveMean3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "avemiddle3":
                        actvalue = MathService.AveMiddle3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averetain3":
                        actvalue = MathService.AveRetain3(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averefre3":
                        actvalue = MathService.AveRefre3(avelists, comcd.getAvefactor(), comcd.getComparefactor(),
                                comcd.getDefvalue());
                        break;

                    case "avemean6":
                        actvalue = MathService.AveMean6(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "averetain6":
                        actvalue = MathService.AveRetain6(avelists, comcd.getAvefactor(), comcd.getDefvalue());
                        break;

                    case "statsum":
                        actvalue = MathService.statsum(avelists, comcd.getDefvalue());
                        break;

                    case "statmiddle":
                        actvalue = MathService.statmiddle(avelists, comcd.getDefvalue());
                        break;
                    case "choice":
                        actvalue = MathService.choice(avelists, comcd.getDefvalue());
                        break;

                    default:
                        actvalue = comcd.getDefvalue();
                        break;
                    }
                }

                AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(), actvalue,
                        actvalue, groupserial, 0);
            }

            // endregion 平均值

            // region 相对相差

            if (comcd.getDatasource().equals(DataSource.FormulaData.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))
                    continue;

                avelists.clear();
                aveact = comcd.getActformula().replace("{", "").replace("}", "");
                int nowmaxspecserial = 0;
                nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());

                nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                for (i = 1; i <= nowmaxspecserial; i++) {
                    for (BusRecordData data : nowdatas) {
                        if (aveact.equals(data.getClasssource() + "." + data.getFieldcode())
                                && (data.getSpecserial() == i)) {
                            if (!data.getSubmitvalue().equals("")) {
                                avelists.add(data.getSubmitvalue());
                            }
                        }
                    }
                }

                if (avelists.size() <= 0)
                    actvalue = "";
                else {
                    switch (comcd.getFuncformula().toLowerCase()) {
                    case "differ":
                        actvalue = MathService.Differ(avelists, comcd.getDefvalue());
                        break;
                    default:
                        actvalue = comcd.getDefvalue();
                        break;
                    }
                }

                AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(), actvalue,
                        actvalue, groupserial, 0);
            }

            // endregion 相对相差
        }

        // TwoJudgment 二次取值计算
        for (DatComputeData comcd : sets) {

            // region计算取值
            comcd.setActformula(comcd.getActformula().trim());
            if (comcd.getDatasource().equals(DataSource.TwoJudgment.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))
                    continue;

                avelists.clear();
                for (BusRecordData datahl : nowdatas) {
                    if (datahl.getFieldcode().equals(comcd.getClassfield()) && !datahl.getSubmitvalue().equals("")) {
                        avelists.add(datahl.getSubmitvalue());
                    }
                }

                if (avelists.size() == 0) {
                    int nowmaxspecserial = 0;

                    nowmaxspecserial = GetMaxSpecSerial(rdetail, comcd.getClasssource(), comcd.getClassfield());
                    if (nowmaxspecserial == 0) {
                        nowmaxspecserial = 1;
                    }
                    nowmaxspecserial = (nowmaxspecserial > 0) ? nowmaxspecserial : maxspecserial;

                    for (i = 1; i <= nowmaxspecserial; i++) {
                        actformula = comcd.getActformula();
                        ma = pa.matcher(comcd.getActformula());

                        while (ma.find()) {
                            matchfield = ma.group().replace("{", "").replace("}", "");

                            actformula = actformula.replace(ma.group(),
                                    GetSubmitValue(nowdatas, sets, matchfield, groupserial, i));
                        }

                        try {
                            actvalue = String.valueOf(jse.eval(actformula)).replace("-", "");

                            if (actvalue.indexOf(".") >= 0) {
                                if ((actvalue.length() - actvalue.indexOf(".") - 1) > 9)
                                    actvalue = actvalue.substring(0, actvalue.indexOf(".") + 6);
                            }
                        } catch (Exception e) {
                            actvalue = "ND";
                        }

                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(),
                                actvalue, actvalue, groupserial, i);

                    }

                }

            }

        }
        // FLJudgment 肥料总养分取值计算
        for (DatComputeData comcd : sets) {
            // region计算取值
            comcd.setActformula(comcd.getActformula().trim());
            if (comcd.getDatasource().equals(DataSource.FLJudgment.getDatasource())) {
                if (ToolUtils.StringIsEmpty(comcd.getFuncformula()))
                    continue;

                if (ToolUtils.StringIsEmpty(comcd.getActformula()))
                    continue;

                avelists.clear();
                for (BusRecordData datahl : nowdatas) {
                    if (datahl.getFieldcode().equals(comcd.getClassfield()) && !datahl.getSubmitvalue().equals("")) {
                        avelists.add(datahl.getSubmitvalue());
                    }
                }

                if (avelists.size() == 0) {
                    int nowmaxspecserial = 1;
                    for (i = 1; i <= nowmaxspecserial; i++) {
                        actformula = comcd.getActformula();
                        ma = pa.matcher(comcd.getActformula());

                        while (ma.find()) {
                            matchfield = ma.group().replace("{", "").replace("}", "");
                            actformula = actformula.replace(ma.group(),
                                    GetAVSubmitValue(nowdatas, sets, matchfield, 1, 0));

                        }

                        try {
                            actvalue = String.valueOf(jse.eval(actformula)).replace("-", "");

                            if (actvalue.indexOf(".") >= 0) {
                                if ((actvalue.length() - actvalue.indexOf(".") - 1) > 9)
                                    actvalue = actvalue.substring(0, actvalue.indexOf(".") + 6);
                            }
                        } catch (Exception e) {
                            actvalue = "ND";
                        }

                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield(), comcd.getFieldtype(),
                                actvalue, actvalue, 1, 1);
                        DeleteRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield() + "av",
                                comcd.getFieldtype(), actvalue, actvalue, 1, 0);
                        AddRecordData(nowdatas, comcd.getClasssource(), comcd.getClassfield() + "av",
                                comcd.getFieldtype(), actvalue, actvalue, 1, 0);

                    }

                }

            }

        }

        // region判定
        for (DatComputeData judgecd : sets) {
            if (judgecd.getDatasource().equals(DataSource.JudgeData.getDatasource())) {
                if (!ToolUtils.StringIsEmpty(judgecd.getFuncformula())) {
                    hasjudge = true;

                    try {
                        funcargs = judgecd.getActformula().split(",");

                        if (ToolUtils.StringIsEmpty(judgecd.getActformula()))
                            arglen = 0;
                        else {
                            arglen = funcargs.length;
                        }

                        Class judgeclass = Class.forName("com.alms.service.JudgeService");
                        Method[] methods = judgeclass.getMethods();

                        for (Method method : methods) {
                            if (method.getName().equals("testjudge")) {
                                Object[] margs = new Object[arglen];
                                argindex = 0;

                                for (String funcarg : funcargs) {
                                    if (ToolUtils.StringIsEmpty(funcarg))
                                        continue;

                                    if (funcarg.indexOf("[") >= 0) {
                                        funcarg = funcarg.replace("[", "").replace("]", "");

                                        avelists.clear();

                                        for (BusRecordData data : nowdatas) {
                                            if (funcarg.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                                avelists.add(data.getSubmitvalue());
                                            }
                                        }

                                        String[] vals = new String[avelists.size()];
                                        validx = 0;

                                        for (String valobject : avelists) {
                                            vals[validx++] = valobject;
                                        }

                                        margs[argindex] = vals;
                                    } else {
                                        funcarg = funcarg.replace("{", "").replace("}", "");

                                        for (BusRecordData data : nowdatas) {
                                            if (funcarg.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                                // margs[argindex+1] = data.getSubmitvalue();
                                                margs[argindex] = data.getSubmitvalue();
                                                break;
                                            }
                                        }
                                    }

                                    argindex++;
                                }

                                // 获取检测参数的标准判定值

                                BusTaskTest bTest = new BusTaskTest();
                                bTest.setTaskid(item.getTaskid());
                                bTest.setSampleid(judgecd.getSampleid());
                                bTest.setParameterid(judgecd.getParameterids());

                                if (judgecd.getSampleid().equals("001283")) {
                                    if (judgecd.getParameterids().equals("000864")) {
                                        double dv = Double.parseDouble(margs[0].toString());
                                        if (dv < 6.5) {
                                            phjudge = "0209";
                                            actvalue = "pH<6.5" + JudgeService.CN_LEVEL_1;
                                            break;
                                        } else if (dv > 7.5) {
                                            phjudge = "0212";
                                            actvalue = "pH>7.5" + JudgeService.CN_LEVEL_3;
                                            break;

                                        } else {
                                            phjudge = "0213";
                                            actvalue = "6.5≤pH≤7.5" + JudgeService.CN_LEVEL_2;
                                            break;
                                        }

                                    }
                                    bTest.setPhlevel(phjudge);
                                    bTest = LabDao.GetBusTaskTest(bTest);
                                    String judgevalue = bTest.getStandvalue();
                                    String testjudge = bTest.getTestjudge();
                                    String deteclimit = bTest.getDeteclimit();
                                    actvalue = (String) method.invoke(null, margs, judgevalue, testjudge, deteclimit);

                                } else {
                                    bTest = LabDao.GetBusTaskTest(bTest);
                                    String judgevalue = bTest.getStandvalue();
                                    String testjudge = bTest.getTestjudge();
                                    String deteclimit = bTest.getDeteclimit();
                                    actvalue = (String) method.invoke(null, margs, judgevalue, testjudge, deteclimit);
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        actvalue = JudgeService.JUDGE_INVALID;
                    }

                    AddRecordData(nowdatas, judgecd.getClasssource(), judgecd.getClassfield(), judgecd.getFieldtype(),
                            actvalue, actvalue, groupserial, 0);

                    switch (actvalue) {
                    case JudgeService.JUDGE_NOTOK:
                        lastresult = JudgeStatus.NOTOK.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_REVIEW:
                        lastresult = JudgeStatus.REVIEW.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_INVALID:
                        lastresult = JudgeStatus.INVALID.getJudgestatus();
                        break;

                    case JudgeService.JUDGE_NO:
                        lastresult = JudgeStatus.NO.getJudgestatus();
                        break;

                    default:
                        break;
                    }
                }
            }
        }

        if (hasjudge) {
            if (ToolUtils.StringIsEmpty(lastresult)) {
                lastresult = JudgeStatus.OK.getJudgestatus();
            }

            item.setJudgestatus(lastresult);
            BusTaskSample ubts = new BusTaskSample();
            ubts.setSampletran(item.getSampletran());
            ubts = LabDao.GetBusTaskSample(ubts);
            ubts.setJudgestatus(lastresult);
            ubts.setIsjudge(true);
            ubts.setAuditstatus("76");
            ubts.getDeal().setAction(DataAction.Modify.getAction());
            LabDao.SaveBusTaskSample(session, ubts);
        }

        String deletesql = "delete from t_bus_record_data where sampletran = ?";

        PreparedStatement ds = session.getConnection().prepareStatement(deletesql);

        ds.setString(1, item.getSampletran());
        ds.addBatch();
        ds.executeBatch();

        // 将相关数据保存到T_Bus_Record_Data中
        String insertsql = "insert into t_bus_record_data(tranid, samplecode, taskid, sampletran, sampleid, groupserial, "
                + "specserial, classsource, fieldcode, fieldtype, displayvalue, submitvalue, validcode) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = session.getConnection().prepareStatement(insertsql);
        for (BusRecordData data : nowdatas) {
            data.setTaskid(item.getTaskid());
            data.setTranid(item.getTranid());
            data.setSamplecode(item.getSamplecode());
            data.setSampleid(item.getSampleid());
            data.setSampletran(item.getSampletran());

            ps.setString(1, data.getTranid());
            ps.setString(2, data.getSamplecode());
            ps.setString(3, data.getTaskid());
            ps.setString(4, data.getSampletran());
            ps.setString(5, data.getSampleid());
            ps.setInt(6, data.getGroupserial());
            ps.setInt(7, data.getSpecserial());
            ps.setString(8, data.getClasssource());
            ps.setString(9, data.getFieldcode());
            ps.setString(10, data.getFieldtype());

            if (data.getDisplayvalue().equals("") || data.getDisplayvalue().equals("请选择")) {
                ps.setString(11, "/");
            } else {
                ps.setString(11, data.getDisplayvalue());
            }

            if (data.getSubmitvalue().equals("") || data.getSubmitvalue().equals("-2")) {
                ps.setString(12, "/");
            } else {
                ps.setString(12, data.getSubmitvalue());
            }

            ps.setString(13, data.getValidcode());
            ps.addBatch();
            data.getDeal().setAction(DataAction.Create.getAction());
        }
        ps.executeBatch();
        DatDao.SyncBusRecordDetail(session, item);
    }

    public static List<BusRecordData> SetGet(BusGet item) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();

        if (item != null) {
            String[] classfields = { "testedname", "tranusername", "getdate", "testtypename" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();

                    data.setClasssource(ClassSource.Consign.getClasssource());
                    data.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }

                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return datas;
    }

    // 获取肥料结果计算值
    public static String GetAVSubmitValue(List<BusRecordData> datas, List<DatComputeData> sets, String fieldcode,
            int groupserial, int specserial) {
        boolean isfind = true;
        for (DatComputeData set : sets) {
            if (fieldcode.equals(set.getClasssource() + "." + set.getClassfield())) {
                for (BusRecordData data : datas) {
                    if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                            && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode()) && isfind) {
                        isfind = false;
                        return data.getSubmitvalue();
                    }
                }

            }
        }
        return "/";
    }

    public static String GetSubmitValue(List<BusRecordData> datas, List<DatComputeData> sets, String fieldcode,
            int groupserial, int specserial) {

        boolean isfind = true;

        for (DatComputeData set : sets) {
            if (fieldcode.equals(set.getClasssource() + "." + set.getClassfield())) {

                if (!set.isIsgroup())
                    groupserial = 0;

                if (!set.isIsserial())
                    specserial = 0;

                for (BusRecordData data : datas) {
                    if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                            && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode())) {

                        isfind = false;
                        return data.getSubmitvalue();
                    }
                }

                // 判断计算公式里面的成分是不是通过计算获取的，04是计算,计算结果是单组试验的情况
                if (isfind) {
                    if (set.getDatasource().equals("04")) {
                        for (BusRecordData data : datas) {
                            int specserial2 = 1;

                            if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial2)
                                    && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode())) {
                                return data.getSubmitvalue();
                            }
                        }
                    }
                }
            }
        }

        for (BusRecordData data : datas) {
            if ((data.getGroupserial() == groupserial) && (data.getSpecserial() == specserial)
                    && fieldcode.equals(data.getClasssource() + "." + data.getFieldcode()))
                return data.getSubmitvalue();
        }
        return "/";
    }

    public static void DeleteRecordData(List<BusRecordData> datas, String classsource, String fieldcode,
            String fieldtype, String submitvalue, String displayvalue, int groupserial, int specserial) {

        Iterator<BusRecordData> iterator = datas.iterator();
        while (iterator.hasNext()) {
            BusRecordData obj = iterator.next();
            if (obj.getClasssource().equals(classsource) && obj.getFieldcode().equals(fieldcode)
                    && obj.getFieldtype().equals(fieldtype) && obj.getGroupserial() == groupserial
                    && obj.getSpecserial() == specserial) {
                iterator.remove();
            }
        }
    }

    public static int GetMaxSpecSerial(List<BusRecordDetail> datas, String classsource, String fieldcode) {
        int rtn = 0;

        for (BusRecordDetail data : datas) {
            if (data.getClasssource().equals(classsource) && data.getFieldcode().equals(fieldcode)) {
                if (data.getSpecserial() > rtn)
                    rtn = data.getSpecserial();
            }
        }
        return rtn;
    }

    public static List<BusRecordData> SetGetDetail(BusGetDetail item, int groupserial) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();

        if (item != null) {
            String[] classfields = { "samplecode", "tranid", "sampleid", "samplename", "factname", "trademark",
                    "samplestand", "samplelevel", "samplecount", "prdcode", "getaddr", "samplebase", "testitems",
                    "mainstandname", "tranidcn" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();
                    BusRecordData groupdata = new BusRecordData();

                    data.setClasssource(ClassSource.Sample.getClasssource());
                    data.setFieldcode(classfield);
                    groupdata.setClasssource(ClassSource.Sample.getClasssource());
                    groupdata.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }

                    groupdata.setSubmitvalue(data.getSubmitvalue());

                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);

                    groupdata.setDisplayvalue(groupdata.getSubmitvalue());
                    groupdata.setGroupserial(groupserial);
                    groupdata.setSpecserial(0);
                    groupdata.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(groupdata);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    public static void AddRecordData(List<BusRecordData> datas, String classsource, String fieldcode, String fieldtype,
            String submitvalue, String displayvalue, int groupserial, int specserial) {
        BusRecordData item = new BusRecordData();

        item.setClasssource(classsource);
        item.setFieldcode(fieldcode);
        item.setFieldtype(fieldtype);
        item.setSubmitvalue(submitvalue);
        item.setDisplayvalue(displayvalue);
        item.setGroupserial(groupserial);
        item.setSpecserial(specserial);

        datas.add(item);
    }

    public static List<BusRecordData> SetTask(BusTaskSingle item) {
        List<BusRecordData> datas = new ArrayList<BusRecordData>();
        item = LabDao.GetBusTaskSingle(item);
        if (item != null) {
            String[] classfields = { "taskid", "samplecode", "samplename", "begintestdate", "labid", "labname",
                    "testtemp", "testhum", "testenv", "devids", "devnames", "mainstandname", "samplestatus",
                    "teststandardname", "taskremark" };

            try {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                for (String classfield : classfields) {
                    Field f = item.getClass().getDeclaredField(classfield);
                    f.setAccessible(true);

                    BusRecordData data = new BusRecordData();

                    data.setClasssource(ClassSource.Task.getClasssource());
                    data.setFieldcode(classfield);

                    if (f.get(item) != null) {
                        if (f.get(item) instanceof java.util.Date)
                            data.setSubmitvalue(sp.format(f.get(item)));
                        else {
                            data.setSubmitvalue(f.get(item).toString());
                        }
                    }
                    data.setDisplayvalue(data.getSubmitvalue());
                    data.setGroupserial(0);
                    data.setSpecserial(0);
                    data.setFieldtype(FieldType.Text.getFieldtype());
                    datas.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    // endregion

    // region 原始记录表html
    @WebMethod
    public @WebResult String GetBusRecordHtml(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBusRecordHtml");

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setSamplecode(selparam);
            bustasksingle.setTestuser(userid);
            bustasksingle = LabAndroidDao.GetBusTaskSingleBySampleCode(bustasksingle);

            // 当前客户的任务单
            List<BusRecord> bRecords = LabAndroidDao.GetBusRecordBySampletran(bustasksingle.getSampletran());

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            for (BusRecord bRecord : bRecords) {
                SetBusRecord item = DatService.GetSetBusRecord(bRecord);
                sb.append(LabAndroidService.GetBusRecordHtml(item));
            }
            sb.append(LabAndroidService.GetInfoHtmlBottom());

            resultMap.put("success", true);
            resultMap.put("data", sb.toString());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // region 抽样单html
    @WebMethod
    public @WebResult String GetSamplePrintHtml(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBusRecordHtml");

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setSamplecode(selparam);
            bustasksingle.setTestuser(userid);
            bustasksingle = LabAndroidDao.GetBusTaskSingleBySampleCode(bustasksingle);

            // 当前客户的任务单
            List<BusRecord> bRecords = LabAndroidDao.GetBusRecordBySampletran(bustasksingle.getSampletran());

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            for (BusRecord bRecord : bRecords) {
                SetBusRecord item = DatService.GetSetBusRecord(bRecord);
                sb.append(LabAndroidService.GetBusRecordHtml(item));
            }
            sb.append(LabAndroidService.GetInfoHtmlBottom());

            resultMap.put("success", true);
            resultMap.put("data", sb.toString());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询被使用的设备
    @WebMethod
    public @WebResult String GetListBasDevForUse(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        OnlineUser ou = new OnlineUser();

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。GetListBasDevForUse");
            BusTaskDev item = new BusTaskDev();

            String search = "";
            search += ToolUtils.GetAndSearch(search) + "  c.labid = '" + ou.getDept().getDeptid() + "'";
            search += ToolUtils.GetAndSearch(search) + "  d.testuser = '" + ou.getUser().getUserid() + "'";
            item.getSearch().setSearch(search);

            // 查询所有受检单位
            List<BusTaskDev> lists = LabAndroidDao.SearchAllBasDevForUse(item);

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

    // region 设备结束试验确认
    @WebMethod
    public @WebResult String DevUsedConfirm(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        OnlineUser ou = new OnlineUser();

        try {
            System.out.println("调试。。。。。。。DevUsedConfirm");
            ou = UserDao.GetOnlineUser(session, userid);

            JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            DevUse dUse = (DevUse) JSONObject.toBean(jsonObject, DevUse.class);

            BasDev basDev = new BasDev();

            dUse.setTranuser(ou.getUser().getUserid());
            dUse.setTranusername(ou.getUser().getUsername());
            dUse.setTrandate(new Date());
            dUse.setFlowaction("01");
            dUse.setFlowstatus("01");
            dUse.getDeal().setAction(DataAction.Create.getAction());
            DevDao.SaveDevUse(session, dUse);

            basDev.setDevid(dUse.getDevid());
            basDev = DevDao.GetBasDev(basDev);
            basDev.setDevstatus(dUse.getAfterstatus());
            basDev.getDeal().setAction(DataAction.Modify.getAction());
            DevDao.SaveBasDev(session, basDev);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "设备使用记录确认成功！");

        } catch (Exception e) {
            session.rollback();
            resultMap.put("success", false);
            resultMap.put("message", "设备使用记录确认失败！");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 根据sampletran 查询设备
    @WebMethod
    public @WebResult String SearchBusTaskDevBySampletran(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SearchBusTaskDevBySampletran");

            BusTaskSingle bustasksingle = new BusTaskSingle();
            bustasksingle.setSamplecode(selparam);
            bustasksingle.setTestuser(userid);
            bustasksingle = LabAndroidDao.GetBusTaskSingleBySampleCode(bustasksingle);

            List<BusTaskDev> lists = LabAndroidDao.SearchBusTaskDevBySampletran(bustasksingle.getSampletran());

            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String dev = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", dev);
            resultMap.put("message", "成功");
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询检测室对应的设备
    @WebMethod
    public @WebResult String GetBasDevByLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        SqlSession session = DBUtils.getFactory();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        OnlineUser ou = new OnlineUser();
        ou = UserDao.GetOnlineUser(session, userid);

        try {

            System.out.println("调试。。。。。。。GetBasDevByLab");
            BasDev item = new BasDev();
            item.setLabid(ou.getUser().getDeptid());
            // 查询对应检测室的设备
            List<BasDev> lists = DevDao.GetListBasDevByLab(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String unitJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", unitJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询检测室对应的设备
    @WebMethod
    public @WebResult String GetBusSelectByCode(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        try {

            System.out.println("调试。。。。。。。GetBusSelectByCode");
            BusSelect item = new BusSelect();
            item.setSelectcode(selparam);

            List<BusSelect> lists = LabDao.GetBusSelectByCode(item);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            String unitJson = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", unitJson);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

}
