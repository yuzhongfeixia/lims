package com.gpersist.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.gpersist.dao.WsPubDao;
import com.gpersist.enums.DataAction;
import com.gpersist.service.WsPubService;
import com.gpersist.utils.ToolUtils;
import com.gpersist.webservice.entity.*;

public class WsUtils {

    public static void CreateMac(WsGet in, String key) {
        in.setMac(ToolUtils.CryptSHA256(in.getAppid() + in.getItemid() + in.getUserid() + key));
    }

    public static void CreateMac(WsApp app, WsGet in) {
        in.setMac(ToolUtils.CryptSHA256(in.getAppid() + in.getItemid() + in.getUserid() + app.getAppkey()));
    }

    public static boolean CheckMac(WsGet in, String key) {
        return in.getMac().equals(ToolUtils.CryptSHA256(in.getAppid() + in.getItemid() + in.getUserid() + key));
    }

    public static boolean CheckMac(WsApp app, WsGet in) {
        return in.getMac()
                .equals(ToolUtils.CryptSHA256(in.getAppid() + in.getItemid() + in.getUserid() + app.getAppkey()));
    }

    public static String GetServiceIP(WebServiceContext context) {

        try {
            javax.xml.ws.handler.MessageContext ctx = context.getMessageContext();
            HttpServletRequest req = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
            return req.getRemoteAddr();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "";
    }

    public static void LogWsVisit(String appid, String func, String ip, String context) {
        WsVisit visit = new WsVisit();

        visit.setAppid(appid);
        visit.setVisitaddress(ip);
        visit.setVisitfunc(func);
        visit.setVisitcontent(context);

        WsPubService.SaveWsVisit(visit);
    }

    public static boolean CheckMac(String appid, String in, String mac) {

        try {
            WsApp app = WsPubDao.GetWsApp(appid);
            System.out.println("mac:" + ToolUtils.CryptSHA256(in + app.getAppkey()));
            return mac.equals(ToolUtils.CryptSHA256(in + app.getAppkey()));
        } catch (Exception e) {
            // TODO: handle exception
        }

        return false;
    }

    public static void LogWsVisit(String appid, String func, String userid, String content, WebServiceContext context) {
        try {
            javax.xml.ws.handler.MessageContext ctx = context.getMessageContext();
            HttpServletRequest req = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);

            WsVisit visit = new WsVisit();

            visit.setAppid(appid);
            visit.setVisitaddress(req.getRemoteAddr());
            visit.setVisitfunc(func);
            visit.setVisitdev(req.getHeader("user-agent"));
            visit.setVisitcontent(content);
            visit.setVisituser(userid);
            visit.getDeal().setAction(DataAction.Create.getAction());
            WsPubService.SaveWsVisit(visit);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
