package com.gpersist.webservice.entity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.gpersist.entity.user.OnlineUser;

@WebService
public interface IServiceUser {
    @WebMethod
    public @WebResult OnlineUser Login(@WebParam(name = "userid") String userid,
            @WebParam(name = "password") String password, @WebParam(name = "mac") String mac);
}
