package com.zetton.thymeleaf.config;

import com.zetton.thymeleaf.webservice.ICommonService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    ICommonService commonService;

    @Bean
    public ServletRegistrationBean getCXFServlet(){
        return new ServletRegistrationBean(new CXFServlet() , "/services/*");
    }
    /**
     * JAX-WS
     **/
    @Bean
    public Endpoint endpoint(){
        Endpoint endpoint = new EndpointImpl(bus,commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }
}
