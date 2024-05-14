package com.alms.webservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.ibatis.session.SqlSession;

import com.alms.dao.FlowDao;
import com.alms.dao.LabAndroidDao;
import com.alms.dao.LabDao;
import com.alms.entity.flow.BusTodo;
import com.alms.entity.lab.BusCatalogParam;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusGetNoticeDetail;
import com.alms.entity.lab.BusSampleParam;
import com.alms.enums.TodoType;
import com.alms.service.LabAndroidService;
import com.alms.webservice.inter.IServiceSamp;
import com.google.gson.reflect.TypeToken;
import com.gpersist.dao.UserDao;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.entity.user.SysUserRole;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.JsonDateValueProcessor;
import com.gpersist.utils.JsonUtils;
import com.gpersist.utils.ToolUtils;

@WebService
public class ServiceSamp implements IServiceSamp {

    @Resource
    private WebServiceContext context = new org.apache.cxf.jaxws.context.WebServiceContextImpl();

    // region 查询取样通知单
    @WebMethod
    public @WebResult String SearchBusNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            BusGetNotice item = new BusGetNotice();
            String search = "";
            String[] param = selparam.split(",");

            if (!ToolUtils.StringIsEmpty(param[0])) {
                search += ToolUtils.GetAndSearch(search) + "  a.tranid = '" + param[0] + "'  ";
            }
            if (!ToolUtils.StringIsEmpty(param[1])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate >= " + ToolUtils.GetBeginDate(param[1]); // 开始时间
            }
            if (!ToolUtils.StringIsEmpty(param[2])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate <= " + ToolUtils.GetBeginDate(param[2]); // 结束时间
            }

            search += ToolUtils.GetAndSearch(search) + "  a.getuser = " + userid;
            search += ToolUtils.GetAndSearch(search) + "  a.flowstatus = '02' ";

            item.getSearch().setSearch(search);

            // 前用户待接任务单
            List<BusGetNotice> lists = LabAndroidDao.SearchBusGetNotice(item);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            String notice = ToolUtils.GetJsonFromArray(lists, config);
            resultMap.put("success", true);
            resultMap.put("data", notice);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 抽样通知单html
    @WebMethod
    public @WebResult String GetBusGetNoticeHtml(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBusGetNoticeHtml");
            // 查询抽样通知单
            BusGetNotice item = new BusGetNotice();
            item.setTranid(selparam);
            item = LabDao.GetBusGetNotice(item);

            BusGetNoticeDetail bg = new BusGetNoticeDetail();
            bg.setTranid(selparam);
            List<BusGetNoticeDetail> lists = LabDao.GetBusGetNoticeDetail(bg);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            sb.append(LabAndroidService.GetBusGetNoticeHtml(item));
            for (BusGetNoticeDetail bgnd : lists) {
                sb.append(LabAndroidService.GetBusGetNoticeDetailHtml(bgnd));
            }
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

    // region 查询待接通知单
    @WebMethod
    public @WebResult String SearchToBeReceivedNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SearchToBeReceivedNotice");
            BusGetNotice item = new BusGetNotice();
            BusGetNotice collectinsect = (BusGetNotice) JSONObject.toBean(JSONObject.fromObject(selparam),
                    BusGetNotice.class);
            String search = "";
            // String[] param = selparam.split(",");
            // search += ToolUtils.GetAndSearch(search) + " a.flowstatus = '02' "; //
            // 02 提交状态
            search += ToolUtils.GetAndSearch(search) + "  a.getuser = '" + userid + "'";
            // if (!ToolUtils.StringIsEmpty(param[0])){
            // search += ToolUtils.GetAndSearch(search) + " a.tranid = '"+param[0]+"'
            // ";
            // }
            // if (!ToolUtils.StringIsEmpty(param[1])){
            // search += ToolUtils.GetAndSearch(search) + " a.trandate >= " +
            // ToolUtils.GetBeginDate(param[1]); //开始时间
            // }
            // if (!ToolUtils.StringIsEmpty(param[2])){
            // search += ToolUtils.GetAndSearch(search) + " a.trandate <= " +
            // ToolUtils.GetBeginDate(param[2]); //结束时间
            // }
            item.getSearch().setSearch(search);
            // item.getSearch().setUserid(userid);
            // 前用户待接任务单
            List<BusGetNotice> lists = LabAndroidDao.SearchBusGetNotice(collectinsect);

            System.out.println(lists);
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

    // region 通知单接单
    @WebMethod
    public @WebResult String ReceiveBusGetNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // OnlineUser ou = new OnlineUser();
        try {
            // ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。ReceiveBusGetNotice");
            // 查询抽样通知单
            BusGetNotice item = new BusGetNotice();
            item.setTranid(selparam);
            item = LabDao.GetBusGetNotice(item);
            item.setFlowstatus("03");// 03处理中
            item.setModifydate(new Date());
            item.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGetNotice(session, item);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "接单成功");

        } catch (Exception e) {
            session.rollback();
            resultMap.put("message", e);
        } finally {
            session.close();
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 通知单退单
    @WebMethod
    public @WebResult String BackBusGetNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // OnlineUser ou = new OnlineUser();
        try {
            // ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。ReceiveBusGetNotice");
            JSONArray jsonObject = JSONArray.fromObject(selparam);

            // 查询抽样通知单
            BusGetNotice item = new BusGetNotice();
            item.setTranid(jsonObject.get(0).toString());
            item = LabDao.GetBusGetNotice(item);
            item.setFlowstatus("86");// 86退单
            item.setModifydate(new Date());
            item.setBackdesc(jsonObject.get(1).toString());
            item.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGetNotice(session, item);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "退单成功");

        } catch (Exception e) {
            session.rollback();
            resultMap.put("message", e);
        } finally {
            session.close();
        }

        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 查询委托编号
    @WebMethod
    public @WebResult String SearchBusGetForSampling(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SearchBusGetForSampling");
            BusGet item = new BusGet();

            String search = "";
            search += ToolUtils.GetAndSearch(search) + " a.gettype = '10'and a.flowstatus ='02'  ";

            item.getSearch().setSearch(search);
            // 前用户待接任务单
            List<BusGet> lists = LabAndroidDao.SearchBusGetForSampling(item);

            System.out.println(lists);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String get = ToolUtils.GetJsonFromArray(lists, config);
            System.out.println(get);
            resultMap.put("success", true);
            resultMap.put("data", get);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }

    // region 查询取样单
    @WebMethod
    public @WebResult String SearchBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {
            System.out.println("调试。。。。。。。SearchBusGet");
            BusGet item = new BusGet();

            String search = "";
            String[] param = selparam.split(",");
            // search += ToolUtils.GetAndSearch(search) + " a.sampleuser =
            // '"+userid+"'";
            if (!ToolUtils.StringIsEmpty(param[0])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate >= " + ToolUtils.GetBeginDate(param[0]); // 开始时间
            }
            if (!ToolUtils.StringIsEmpty(param[1])) {
                search += ToolUtils.GetAndSearch(search) + "  a.trandate <= " + ToolUtils.GetBeginDate(param[1]); // 结束时间
            }
            item.getSearch().setSearch(search);
            // item.getSearch().setUserid(userid);
            // 前用户待接任务单
            List<BusGet> lists = LabAndroidDao.SearchBusGet(item);

            System.out.println(lists);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String get = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", get);

        } catch (Exception e) {
            resultMap.put("message", "查询失败");
        } finally {
            session.close();
        }
        // }
        resultJson = JSONObject.fromObject(resultMap).toString();
        return resultJson;
    }
    // endregion

    // region 抽样单html
    @WebMethod
    public @WebResult String GetBusGetHtml(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";

        // if (!WsUtils.CheckMac(appid, appid + guid + userid, mac)) {
        // resultMap.put("message", "查询失败，非法的查询");
        // } else {
        try {

            System.out.println("调试。。。。。。。GetBusGetHtml");
            // 查询抽样通知单
            BusGet item = new BusGet();
            item.setTranid(selparam);
            item = LabDao.GetBusGet(item);

            BusGetDetail bg = new BusGetDetail();
            bg.setTranid(selparam);
            List<BusGetDetail> lists = LabDao.GetListBusGetDetail(bg);

            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());

            StringBuilder sb = new StringBuilder();
            sb.append(LabAndroidService.GetInfoHtmlHeader());
            sb.append(LabAndroidService.GetBusGetHtml(item));
            for (BusGetDetail bgnd : lists) {
                sb.append(LabAndroidService.GetBusGetDetailHtml(bgnd));
            }
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

    // region 查询已接通知单
    @WebMethod
    public @WebResult String SearchHaveReceivedNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        try {
            System.out.println("调试。。。。。。。SearchHaveReceivedNotice");
            BusGetNotice item = new BusGetNotice();

            String search = "";
            search += ToolUtils.GetAndSearch(search) + "  a.flowstatus = '03'  "; // 03处理中
            search += ToolUtils.GetAndSearch(search) + " a.getuser like '%" + userid + "%' ";
            search += ToolUtils.GetAndSearch(search) + "  f.tranid is null ";
            item.getSearch().setSearch(search);
            // item.getSearch().setUserid(userid);
            // 前用户待接任务单
            List<BusGetNotice> lists = LabAndroidDao.SearchBusGetNotice(item);

            System.out.println(lists);
            JsonConfig config = new JsonConfig();
            config.registerPropertyExclusions(item.getClass(), item.OnExclusions());
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

            String notice = ToolUtils.GetJsonFromArray(lists, config);

            resultMap.put("success", true);
            resultMap.put("data", notice);

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

    // region 添加取样单
    @WebMethod
    public @WebResult String AddBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        OnlineUser ou = new OnlineUser();
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。AddBusGet");

            JSONArray jsonObject = JSONArray.fromObject(selparam);

            // JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusGet busGet = new BusGet();
            busGet = (BusGet) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), BusGet.class);

            List<BusGetDetail> getDetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(1), BusGetDetail.class);

            busGet.setTranuser(ou.getUser().getUserid());
            busGet.setTranusername(ou.getUser().getUsername());
            busGet.setSampleuser(ou.getUser().getUserid());
            busGet.setSampleusername(ou.getUser().getUsername());
            busGet.setTrandate(new Date());
            busGet.setSampleid(getDetails.get(0).getSampleid());
            busGet.setFlowaction("51");
            busGet.setFlowstatus("02");
            busGet.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusGet(session, busGet);

            BusTodo busTodo = new BusTodo();
            busTodo.setTranid(busGet.getTranid());
            busTodo.setBusflow("GetAcc");
            busTodo.setFlownode("create");
            busTodo.setSenddate(new Date());

            // 将收样提醒提交给业务室收样人，不提交给农药科
            SysUserRole sRole = new SysUserRole();
            sRole.setRoleid(17);
            List<SysUserRole> sysUserRoles = UserDao.GetListSysRoleByRole(sRole);
            for (SysUserRole sysUserRole : sysUserRoles) {
                busTodo.setTranuser(sysUserRole.getUserid());
                // busTodo.setTrandept("7000");
                busTodo.setIsnowflow(true);
                busTodo.setSampleid(String.valueOf(1));
                busTodo.setTodotype(TodoType.GetAcc.getTodotype());
                busTodo.getDeal().setAction(DataAction.Create.getAction());
                FlowDao.SaveBusTodo(session, busTodo);
            }

            BusGetDetail delgetdetail = new BusGetDetail();
            delgetdetail.setTranid(busGet.getTranid());
            delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetDetail(session, delgetdetail);

            if (getDetails.size() == 0) {
                BusSampleParam delsampleparam = new BusSampleParam();
                delsampleparam.setTranid(busGet.getTranid());
                delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusSampleParam(session, delsampleparam);

                BusCatalogParam delcatalogparam = new BusCatalogParam();
                delcatalogparam.setTranid(busGet.getTranid());
                delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusCatalogParam(session, delcatalogparam);
            } else {
                for (BusGetDetail busgetdetail : getDetails) {
                    String paramids = busgetdetail.getParameterids();
                    if (!paramids.equals("") && paramids != null) {
                        BusSampleParam delparam = new BusSampleParam();
                        delparam.setTranid(busGet.getTranid());
                        delparam.getDeal().setAction(DataAction.Delete.getAction());
                        LabDao.SaveBusSampleParam(session, delparam);
                    }
                }

                for (BusGetDetail busgetdetail : getDetails) {
                    busgetdetail.setTranid(busGet.getTranid());
                    busgetdetail.setTesttype(busGet.getTesttype());
                    busgetdetail.getDeal().setAction(DataAction.Create.getAction());
                    LabDao.SaveBusGetDetail(session, busgetdetail);
                    String paramids = busgetdetail.getParameterids();
                    String sampleid = busgetdetail.getSampleid();

                    if (!paramids.equals("") && paramids != null) {
                        String[] paramnames = paramids.split(",");
                        for (String params : paramnames) {
                            String[] param = params.split(":");
                            BusSampleParam bussampleparam = new BusSampleParam();
                            bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                            bussampleparam.setTranid(busGet.getTranid());
                            bussampleparam.setSampleid(sampleid);
                            bussampleparam.setParameterid(param[0]);
                            bussampleparam.setParametername(param[1]);
                            bussampleparam.setTeststandard(param[2]);
                            bussampleparam.setTeststandardcode(param[3]);
                            bussampleparam.setJudgestandard(param[4]);
                            bussampleparam.setJudgestandardcode(param[5]);
                            bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusSampleParam(session, bussampleparam);
                        }
                    }
                }
            }

            // 修改通知单及通知单明细
            BusGetNotice notice = new BusGetNotice();
            notice.setTranid(busGet.getNoticeid());
            notice.setFinishdate(new Date());
            notice.setFlowstatus("00"); // 00已完成
            notice.setFinishdatecn(ToolUtils.GetFmtDate(new Date(), "yyyyMMdd"));
            notice.setModifydate(new Date());
            notice.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGetNotice(session, notice);

            BusGetNoticeDetail noticeDetail = new BusGetNoticeDetail();
            noticeDetail.setTranid(busGet.getNoticeid());
            List<BusGetNoticeDetail> noticeDetails = LabDao.GetBusGetNoticeDetail(noticeDetail);

            noticeDetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetNoticeDetail(session, noticeDetail);

            for (BusGetNoticeDetail detail : noticeDetails) {
                detail.setIsfinish(true);
                detail.setGetdate(new Date());
                detail.getDeal().setAction(DataAction.Create.getAction());
                LabDao.SaveBusGetNoticeDetail(session, detail);
            }
            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "抽样单添加成功");
            resultMap.put("message", "保存成功");

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

    // region 添加取样单
    @WebMethod
    public @WebResult String AddBusGetEnv(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        OnlineUser ou = new OnlineUser();
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。AddBusGetEnv" + selparam);

            JSONArray jsonObject = JSONArray.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusGet busGet = new BusGet();
            busGet = (BusGet) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), BusGet.class);

            List<BusGetDetail> getDetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(1), BusGetDetail.class);

            busGet.setTranuser(ou.getUser().getUserid());
            busGet.setTranusername(ou.getUser().getUsername());
            busGet.setSampleuser(ou.getUser().getUserid());
            busGet.setSampleusername(ou.getUser().getUsername());
            busGet.setTrandate(new Date());
            busGet.setSampleid(getDetails.get(0).getSampleid());
            busGet.setFlowaction("51");
            busGet.setFlowstatus("00");
            busGet.getDeal().setAction(DataAction.Special01.getAction());
            LabDao.SaveBusGet(session, busGet);

            BusGetDetail delgetdetail = new BusGetDetail();
            delgetdetail.setTranid(busGet.getTranid());
            delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetDetail(session, delgetdetail);

            if (getDetails.size() == 0) {
                BusSampleParam delsampleparam = new BusSampleParam();
                delsampleparam.setTranid(busGet.getTranid());
                delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusSampleParam(session, delsampleparam);

                BusCatalogParam delcatalogparam = new BusCatalogParam();
                delcatalogparam.setTranid(busGet.getTranid());
                delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusCatalogParam(session, delcatalogparam);
            } else {
                for (BusGetDetail busgetdetail : getDetails) {
                    String paramids = busgetdetail.getParameterids();
                    if (!paramids.equals("") && paramids != null) {
                        BusSampleParam delparam = new BusSampleParam();
                        delparam.setTranid(busGet.getTranid());
                        delparam.getDeal().setAction(DataAction.Delete.getAction());
                        LabDao.SaveBusSampleParam(session, delparam);
                    }
                }

                for (BusGetDetail busgetdetail : getDetails) {
                    busgetdetail.setTranidcn(busGet.getTranidcn());
                    // busgetdetail.setSamplelevel(busgetdetail.getSamplecode());
                    busgetdetail.setTranid(busGet.getTranid());
                    busgetdetail.setTesttype(busGet.getTesttype());
                    busgetdetail.getDeal().setAction(DataAction.Special01.getAction());
                    LabDao.SaveBusGetDetail(session, busgetdetail);

                    String paramids = busgetdetail.getParameterids();
                    String sampleid = busgetdetail.getSampleid();

                    if (!paramids.equals("") && paramids != null) {
                        String[] paramnames = paramids.split(",");
                        for (String params : paramnames) {
                            String[] param = params.split(":");
                            BusSampleParam bussampleparam = new BusSampleParam();
                            bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                            bussampleparam.setTranid(busGet.getTranid());
                            bussampleparam.setSampleid(sampleid);
                            bussampleparam.setParameterid(param[0]);
                            bussampleparam.setParametername(param[1]);
                            bussampleparam.setTeststandard(param[2]);
                            bussampleparam.setTeststandardcode(param[3]);
                            bussampleparam.setJudgestandard(param[4]);
                            bussampleparam.setJudgestandardcode(param[5]);
                            bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusSampleParam(session, bussampleparam);
                        }
                    }
                }

            }
            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "抽样单添加成功");
            resultMap.put("message", "保存成功");

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

    // region 添加取样单
    @WebMethod
    public @WebResult String AddBusGetGreen(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();
        OnlineUser ou = new OnlineUser();
        try {
            ou = UserDao.GetOnlineUser(userid);
            System.out.println("调试。。。。。。。AddBusGetGreen" + selparam);

            JSONArray jsonObject = JSONArray.fromObject(selparam);

            // JSONObject jsonObject = JSONObject.fromObject(selparam);

            String[] dateFormats = new String[] { "yyyy-MM-dd" };
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            BusGet busGet = new BusGet();
            busGet = (BusGet) JSONObject.toBean(JSONObject.fromObject(jsonObject.get(0)), BusGet.class);

            List<BusGetDetail> getDetails = ToolUtils.GetArrayFromJson((String) jsonObject.get(1), BusGetDetail.class);

            busGet.setTranuser(ou.getUser().getUserid());
            busGet.setTranusername(ou.getUser().getUsername());
            busGet.setSampleuser(ou.getUser().getUserid());
            busGet.setSampleusername(ou.getUser().getUsername());
            busGet.setTrandate(new Date());
            busGet.setSampleid(getDetails.get(0).getSampleid());
            busGet.setFlowaction("51");
            busGet.setFlowstatus("00");
            busGet.getDeal().setAction(DataAction.Create.getAction());
            LabDao.SaveBusGet(session, busGet);

            // BusTodo busTodo = new BusTodo();
            // busTodo.setTranid(busGet.getTranid());
            // busTodo.setBusflow("GetAcc");
            // busTodo.setFlownode("create");
            // busTodo.setSenddate(new Date());
            //
            // //将收样提醒提交给业务室收样人，不提交给农药科
            // SysUserRole sRole = new SysUserRole();
            // sRole.setRoleid(17);
            // List<SysUserRole> sysUserRoles = UserDao.GetListSysRoleByRole(sRole);
            // for(SysUserRole sysUserRole:sysUserRoles ){
            // busTodo.setTranuser(sysUserRole.getUserid());
            //// busTodo.setTrandept("7000");
            // busTodo.setIsnowflow(true);
            // busTodo.setSampleid(String.valueOf(1));
            // busTodo.setTodotype(TodoType.GetAcc.getTodotype());
            // busTodo.getDeal().setAction(DataAction.Create.getAction());
            // FlowDao.SaveBusTodo(session, busTodo);
            // }
            //
            BusGetDetail delgetdetail = new BusGetDetail();
            delgetdetail.setTranid(busGet.getTranid());
            delgetdetail.getDeal().setAction(DataAction.Delete.getAction());
            LabDao.SaveBusGetDetail(session, delgetdetail);

            if (getDetails.size() == 0) {
                BusSampleParam delsampleparam = new BusSampleParam();
                delsampleparam.setTranid(busGet.getTranid());
                delsampleparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusSampleParam(session, delsampleparam);

                BusCatalogParam delcatalogparam = new BusCatalogParam();
                delcatalogparam.setTranid(busGet.getTranid());
                delcatalogparam.getDeal().setAction(DataAction.Delete.getAction());
                LabDao.SaveBusCatalogParam(session, delcatalogparam);
            } else {
                for (BusGetDetail busgetdetail : getDetails) {
                    String paramids = busgetdetail.getParameterids();
                    if (!paramids.equals("") && paramids != null) {
                        BusSampleParam delparam = new BusSampleParam();
                        delparam.setTranid(busGet.getTranid());
                        delparam.getDeal().setAction(DataAction.Delete.getAction());
                        LabDao.SaveBusSampleParam(session, delparam);
                    }
                }

                for (BusGetDetail busgetdetail : getDetails) {
                    busgetdetail.setTranid(busGet.getTranid());
                    busgetdetail.setTesttype(busGet.getTesttype());
                    busgetdetail.setSamplecode("");
                    busgetdetail.getDeal().setAction(DataAction.Special01.getAction());
                    LabDao.SaveBusGetDetail(session, busgetdetail);
                    String paramids = busgetdetail.getParameterids();
                    String sampleid = busgetdetail.getSampleid();

                    if (!paramids.equals("") && paramids != null) {
                        String[] paramnames = paramids.split(",");
                        for (String params : paramnames) {
                            String[] param = params.split(":");
                            BusSampleParam bussampleparam = new BusSampleParam();
                            bussampleparam.setSamplecode(busgetdetail.getSamplecode());
                            bussampleparam.setTranid(busGet.getTranid());
                            bussampleparam.setSampleid(sampleid);
                            bussampleparam.setParameterid(param[0]);
                            bussampleparam.setParametername(param[1]);
                            bussampleparam.setTeststandard(param[2]);
                            bussampleparam.setTeststandardcode(param[3]);
                            bussampleparam.setJudgestandard(param[4]);
                            bussampleparam.setJudgestandardcode(param[5]);
                            bussampleparam.getDeal().setAction(DataAction.Create.getAction());
                            LabDao.SaveBusSampleParam(session, bussampleparam);
                        }
                    }
                }
            }
            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "抽样单添加成功");
            resultMap.put("message", "保存成功");

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

    // region 取样单提交
    @WebMethod
    public @WebResult String SubmitBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultJson = "";
        SqlSession session = DBUtils.getFactory();

        try {

            System.out.println("调试。。。。。。。SubmitBusGet");

            BusGet busGet = new BusGet();
            busGet.setTranid(selparam);
            busGet = LabDao.GetBusGet(busGet);
            busGet.setFlowstatus("02");// 审核通过
            busGet.getDeal().setAction(DataAction.Deal.getAction());
            LabDao.SaveBusGet(session, busGet);

            session.commit();

            resultMap.put("success", true);
            resultMap.put("data", "抽样单提交成功");

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

}
