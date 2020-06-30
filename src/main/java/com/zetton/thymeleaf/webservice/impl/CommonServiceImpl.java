package com.zetton.thymeleaf.webservice.impl;

import com.zetton.thymeleaf.entity.Teacher;
import com.zetton.thymeleaf.webservice.ICommonService;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://entity.thymeleaf.zetton.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.zetton.thymeleaf.webservice.ICommonService"// 接口地址
)
@Component
public class CommonServiceImpl implements ICommonService {

    @Override
    public String sayHello(String name) {
        return  "Hello ," + name;
    }

    @Override
    public Teacher getTeacher(String name) {
        Teacher t = new Teacher();
        t.setName(name);
        t.setAge(40);
        t.setSubject("奇门遁甲");
        return t;
    }
}
