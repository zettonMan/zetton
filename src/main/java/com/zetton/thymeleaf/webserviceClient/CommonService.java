
package com.zetton.thymeleaf.webserviceClient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CommonService", targetNamespace = "http://entity.thymeleaf.zetton.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CommonService {


    /**
     * 
     * @param name
     * @return
     *     returns com.zetton.thymeleaf.webserviceClient.Teacher
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTeacher", targetNamespace = "http://entity.thymeleaf.zetton.com/", className = "com.zetton.thymeleaf.webserviceClient.GetTeacher")
    @ResponseWrapper(localName = "getTeacherResponse", targetNamespace = "http://entity.thymeleaf.zetton.com/", className = "com.zetton.thymeleaf.webserviceClient.GetTeacherResponse")
    public Teacher getTeacher(
        @WebParam(name = "name", targetNamespace = "http://entity.thymeleaf.zetton.com/")
        String name);

    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://entity.thymeleaf.zetton.com/", className = "com.zetton.thymeleaf.webserviceClient.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://entity.thymeleaf.zetton.com/", className = "com.zetton.thymeleaf.webserviceClient.SayHelloResponse")
    public String sayHello(
        @WebParam(name = "name", targetNamespace = "http://entity.thymeleaf.zetton.com/")
        String name);

}
