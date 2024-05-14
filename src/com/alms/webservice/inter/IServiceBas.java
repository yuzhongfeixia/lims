package com.alms.webservice.inter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IServiceBas {

    // 查询所有样品
    @WebMethod
    public @WebResult String GetAllBasSample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 查询所有受检单位
    @WebMethod
    public @WebResult String GetAllTestedUnit(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 根据单位编号查询受检单位
    @WebMethod
    public @WebResult String SearchTestedUnitById(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 根据检测参数查询判定依据判定依据
    @WebMethod
    public @WebResult String GetJudgeTestByParam(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 根据检测参数查询判定依据和判定依据
    @WebMethod
    public @WebResult String GetAllJudgeTestByParam(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 根据样品查询检测参数
    @WebMethod
    public @WebResult String GetParameterBySample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询耗材
    @WebMethod
    public @WebResult String GetBasPrdHtml(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 保存满意度调查
    @WebMethod
    public @WebResult String AddCrmSurvey(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    public @WebResult String GetStandCount(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    @WebMethod
    public @WebResult String GetSampleStand(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    public @WebResult String GetBasSampleCate(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    @WebMethod
    public @WebResult String GetSampleMainBySampleCate(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    @WebMethod
    public @WebResult String GetSampleBySampleMain(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    @WebMethod
    public @WebResult String GetSampleStandInfo(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询所有带安装人确认的设备
    @WebMethod
    public @WebResult String GetALLAcceptManage(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 查询所有带安装人确认的设备
    @WebMethod
    public @WebResult String DevAcceptConfirm(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询该样品对应的检测参数
    @WebMethod
    public @WebResult String GetAllBasSampleParameter(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

}
