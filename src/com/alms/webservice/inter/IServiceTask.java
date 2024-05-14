package com.alms.webservice.inter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IServiceTask {

    // 委托单提交
    @WebMethod
    public @WebResult String CommitBusConsign(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 抽样单保存
    @WebMethod
    public @WebResult String AddBusConsign(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 查询当前用户的任务单 已接单及试验中的明细
    @WebMethod
    public @WebResult String SearchBusTaskDetail(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询当前用户抽样单 明细HTML
    @WebMethod
    public @WebResult String SearchBusGetPrintDetail(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询当前用户的任务单 列表
    @WebMethod
    public @WebResult String SearchBusGetForPrint(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询当前用户的抽样单 列表
    @WebMethod
    public @WebResult String SearchBusTaskForUser(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询当前用户的任务单 列表
    @WebMethod
    public @WebResult String SearchBusTaskBySamplecode(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询试验样品编号实验准备
    @WebMethod
    public @WebResult String SearchLabBegin(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 查询试验样品编号做实验
    @WebMethod
    public @WebResult String SearchLabEnd(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac);

    // 查询试验样品编号查看记录表
    @WebMethod
    public @WebResult String SearchLabRecord(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 查询当前用户待接任务单
    @WebMethod
    public @WebResult String SearchToBeReceivedTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询任务单号
    @WebMethod
    public @WebResult String SearchBusTaskForTakeSample(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 保存领样记录
    @WebMethod
    public @WebResult String TakeSampleBusTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 领样人签名图片保存到服务器并返回地址
    @WebMethod
    public @WebResult String SaveTakeSignJpgOnServer(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 样品管理员签名图片保存到服务器并返回地址
    @WebMethod
    public @WebResult String SaveSendSignJpgOnServer(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 任务单接单
    @WebMethod
    public @WebResult String ReceiveBusTask(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 试验准备基础信息查询
    @WebMethod
    public @WebResult String SearchInfoBeforeLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询根据部门试验检测设备
    @WebMethod
    public @WebResult String SearchBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 添加试验检测设备
    @WebMethod
    public @WebResult String AddBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 删除试验检测设备
    @WebMethod
    public @WebResult String DeleteBasDevForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查看设备明细信息
    @WebMethod
    public @WebResult String GetBasDevInfo(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 保存试验准备信息
    @WebMethod
    public @WebResult String SavePrepareLabInfo(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询试验及试验检测参数
    @WebMethod
    public @WebResult String SearchParamForLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 保存试验数据，结束试验
    @WebMethod
    public @WebResult String FinishTest(@WebParam(name = "appid") String appid, @WebParam(name = "guid") String guid,
            @WebParam(name = "userid") String userid, @WebParam(name = "mac") String mac,
            @WebParam(name = "selparam") String selparam);

    // 计算试验数据
    @WebMethod
    public @WebResult String ComputeLabData(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 原始记录表html 查看
    public @WebResult String GetBusRecordHtml(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 设备结束确认
    @WebMethod
    public @WebResult String DevUsedConfirm(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 根据实验样品号 查询设备
    @WebMethod
    public @WebResult String SearchBusTaskDevBySampletran(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询检测室对应的设备
    @WebMethod
    public @WebResult String GetBasDevByLab(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

    // 根据代码获取单项下拉框的内容
    @WebMethod
    public @WebResult String GetBusSelectByCode(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac, @WebParam(name = "selparam") String selparam);

    // 查询被使用的设备
    @WebMethod
    public @WebResult String GetListBasDevForUse(@WebParam(name = "appid") String appid,
            @WebParam(name = "guid") String guid, @WebParam(name = "userid") String userid,
            @WebParam(name = "mac") String mac);

}
