package com.gpersist.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.gpersist.enums.*;
import com.gpersist.utils.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("rawtypes")
public class IllegalCharacterInterceptor extends AbstractInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String EVENTS = "(onload|onunload|onchange|onsubmit|onreset"
            + "|onselect|onblur|onfocus|onkeydown|onkeypress|onkeyup"
            + "|onclick|ondblclick|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup)";
    private static final String XSS_HTML_TAG = "\\n\\r|(%3C)|(%3E)|[<>]+";
    private static final String XSS_INJECTION = "(%22|')(\\s|%20)\\w.*|(\\s|%20)" + EVENTS + ".*|(%3D)|(%7C)";
    private static final String XSS_REGEX = XSS_HTML_TAG + "|" + XSS_INJECTION;
    private static final String SQL_REGEX = "('.+--)|(--)|(\\|)|(%7C)|(%20and%20)|(%20or%20)";

    private static Pattern xssPattern = Pattern.compile(XSS_REGEX, Pattern.CASE_INSENSITIVE);
    private static Pattern sqlPattern = Pattern.compile(SQL_REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        // 增加了上传文件时候的处理
        ActionProxy proxy = invocation.getProxy();
        String methodName = proxy.getMethod();
        Object action = proxy.getAction();
        Method method = action.getClass().getMethod(methodName);

        if (null != method) {
            AuthMethod authName = method.getAnnotation(AuthMethod.class);
            if (null != authName) {
                if (authName.Auth() == MenuAuth.Upload) {
                    return invocation.invoke();
                }
            }
        }

        int i = 0;

        // 通过核心调度器invocation来获得调度的Action上下文
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);

        // 获取上下文的请求参数
        Map<String, String[]> valueTreeMap = new HashMap<String, String[]>(request.getParameterMap());

        // 获得请求参数集合的迭代器
        Iterator iterator = valueTreeMap.entrySet().iterator();

        // 遍历组装请求参数
        while (iterator.hasNext()) {
            // 获得迭代的键值对
            Entry entry = (Entry) iterator.next();

            // 获得键值对中的键值
            String key = (String) entry.getKey();

            String[] oldValues = (String[]) entry.getValue();

            for (i = 0; i < oldValues.length; i++) {
                oldValues[i] = filterParamString(oldValues[i]);
            }

            valueTreeMap.put(key, oldValues);
        }

        ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request, valueTreeMap);

        request = wrapRequest;

        ServletActionContext.setRequest(wrapRequest);

        return invocation.invoke();
    }

    protected String filterParamString(String rawValue) {
        if (ToolUtils.StringIsEmpty(rawValue))
            return rawValue;

        rawValue = xssPattern.matcher(rawValue).replaceAll("");

        rawValue = sqlPattern.matcher(rawValue).replaceAll("");

        return rawValue;
    }
}
