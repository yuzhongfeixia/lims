package com.gpersist.interceptor;

import java.lang.reflect.Method;

import com.gpersist.entity.ReturnValue;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.enums.*;
import com.gpersist.utils.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        OnlineUser ou = (OnlineUser) ActionContext.getContext().getSession().get(Consts.DEFAULT_USER);
        ;
        ActionProxy proxy = invocation.getProxy();
        String methodName = proxy.getMethod();
        Object action = proxy.getAction();

        if ((ou == null) && !methodName.equals("ListSelects") && !methodName.equals("OnlineInfo")
                && !methodName.equals("Login") && !methodName.equals("MustChangePwd")) {
            ToolUtils.OutString("login");
        } else {
            Method method = action.getClass().getMethod(methodName);

            if (null != method) {
                AuthMethod authName = method.getAnnotation(AuthMethod.class);
                if (null != authName) {
                    String smenus = authName.Menus();

                    if (!ToolUtils.StringIsEmpty(smenus) && (ou.getMenus().size() > 0)) {
                        String[] menus = smenus.split(",");
                        boolean isValid = false;

                        for (String menu : menus) {
                            for (SysMenu item : ou.getMenus()) {
                                if ((item.getMcode().equals(menu))
                                        && ((item.getMauth() & authName.Auth().getAuth()) > 0)) {
                                    isValid = true;
                                    break;
                                }
                            }

                            if (isValid)
                                break;
                        }

                        if (isValid) {
                            return invocation.invoke();
                        } else {
                            if (authName.OutType() == ActionOutType.Bean)
                                ToolUtils.OutString("{}");
                            else if (authName.OutType() == ActionOutType.Array) {
                                ToolUtils.OutString("[]");
                            } else if (authName.OutType() == ActionOutType.Save) {
                                ReturnValue rtn = new ReturnValue();
                                rtn.setMsg("您没有操作该功能的权限");
                                rtn.setSuccess(false);
                                ToolUtils.OutString(rtn.toString());
                            } else {
                                ToolUtils.OutString("");
                            }
                        }
                    } else {
                        if (ToolUtils.StringIsEmpty(smenus) && (ou.getMenus().size() > 0))
                            return invocation.invoke();
                    }
                } else {
                    return invocation.invoke();
                }
            }

            return null;
        }

        return null;
    }
}
