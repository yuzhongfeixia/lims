package com.alms.webservice.inter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IServiceSamp {

    // 查询通知单
    @WebMethod
    public @WebResult String SearchBusNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 通知单html
    @WebMethod
    public @WebResult String GetBusGetNoticeHtml(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询待接通知单
    @WebMethod
    public @WebResult String SearchToBeReceivedNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 通知单接单
    @WebMethod
    public @WebResult String ReceiveBusGetNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 通知单退单
    @WebMethod
    public @WebResult String BackBusGetNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 取样单查询
    @WebMethod
    public @WebResult String SearchBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 取样单查询
    @WebMethod
    public @WebResult String SearchBusGetForSampling(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 取样单html
    @WebMethod
    public @WebResult String GetBusGetHtml(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 已接单通知单查询
    @WebMethod
    public @WebResult String SearchHaveReceivedNotice(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 新增取样单
    @WebMethod
    public @WebResult String AddBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 新增环境采样单
    @WebMethod
    public @WebResult String AddBusGetEnv(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 新增绿色采样单
    @WebMethod
    public @WebResult String AddBusGetGreen(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 取样单提交
    @WebMethod
    public @WebResult String SubmitBusGet(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

}
