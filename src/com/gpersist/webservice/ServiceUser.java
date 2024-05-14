package com.gpersist.webservice;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.ibatis.session.SqlSession;

import com.gpersist.dao.UserDao;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.utils.Consts;
import com.gpersist.utils.DBUtils;
import com.gpersist.utils.ToolUtils;
import com.gpersist.webservice.entity.IServiceUser;

@WebService
public class ServiceUser implements IServiceUser {

    @Resource
    private WebServiceContext context = new org.apache.cxf.jaxws.context.WebServiceContextImpl();

    @WebMethod
    public @WebResult OnlineUser Login(@WebParam(name = "userid") String userid,
            @WebParam(name = "password") String password, @WebParam(name = "mac") String mac) {

        OnlineUser ou = new OnlineUser();

        SqlSession session = DBUtils.getFactory();

        try {
            System.out.println("login__________");
            ou = UserDao.GetOnlineUser(session, userid);

            if (ToolUtils.StringIsEmpty(ou.getUser().getUserid()) || ToolUtils.StringIsEmpty(ou.getDept().getDeptid()))
                throw new Exception("该用户不存在!");

            if (!ou.getUser().getUserstatus().equals(Consts.DEFAULT_VALID_STATUS))
                throw new Exception("无效的部门状态!");

            if (!ou.getDept().getDeptstatus().equals(Consts.DEFAULT_VALID_STATUS))
                throw new Exception("无效的用户状态!");

            if (ou.getUser().getIslock())
                throw new Exception("该用户被锁定，不能登录系统，请联系系统管理员!");

            MessageContext ctx = context.getMessageContext();
            HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);

            ou.getUser().getDeal().setIp(request.getRemoteAddr());

            if (!ou.getUser().getUserpassword().equals(ToolUtils.GetMD5(password))) {
                String errmsg = "";

                switch (ou.getUser().getErrorpassword()) {
                case 2:
                    errmsg = "您已经连续输错3次密码，还有2次机会!";
                    break;

                case 3:
                    errmsg = "您已经连续输错4次密码，还有1次机会!";
                    break;

                default:
                    errmsg = "密码不正确!";
                    break;
                }

                if (ou.getUser().getErrorpassword() >= 4) {
                    UserDao.UserLock(session, ou.getUser());
                    errmsg = "您的帐号因为多次尝试错误密码而被锁定，请与系统管理员联系！";
                } else
                    UserDao.UserError(session, ou.getUser());

                session.commit();

                if (!ToolUtils.StringIsEmpty(errmsg))
                    throw new Exception(errmsg);
            }

            UserDao.UserLoginLog(session, ou.getUser());
            ou.getUser().setUserpassword("");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            ou.getUser().OnInit();
            ou.getDept().OnInit();
            ou.getSr().setMessage(e.getMessage());
            ou.getSr().setSuccess(false);
        } finally {
            session.close();
        }

        return ou;
    }

}
