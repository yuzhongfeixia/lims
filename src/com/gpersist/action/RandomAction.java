package com.gpersist.action;

import java.io.ByteArrayInputStream;
import org.apache.struts2.ServletActionContext;
import com.gpersist.entity.system.RandomNum;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RandomAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private ByteArrayInputStream inputStream;

    public String execute() throws Exception {

        ServletActionContext.getResponse().setHeader("Pragma", "no-cache");
        ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
        ServletActionContext.getResponse().setDateHeader("Expires", -1);

        RandomNum rdnu = RandomNum.Instance();
        this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
        ActionContext.getContext().getSession().put("random", rdnu.getString());// 取得随机字符串放入HttpSession
        // ServletActionContext.getRequest().getSession().setAttribute("random",
        // rdnu.getString());
        return SUCCESS;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }
}