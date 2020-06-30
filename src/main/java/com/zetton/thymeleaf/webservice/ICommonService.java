package com.zetton.thymeleaf.webservice;

import com.zetton.thymeleaf.entity.Teacher;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "CommonService", // 暴露服务名称
        targetNamespace = "http://entity.thymeleaf.zetton.com/"// 命名空间,一般是接口的包名倒序
)
public interface  ICommonService {
    @WebMethod
    public String sayHello(@WebParam(name= "name",targetNamespace = "http://entity.thymeleaf.zetton.com/") String name);

    @WebMethod
    public Teacher getTeacher(@WebParam(name= "name",targetNamespace = "http://entity.thymeleaf.zetton.com/") String name);
}
