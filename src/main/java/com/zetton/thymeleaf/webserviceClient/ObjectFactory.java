
package com.zetton.thymeleaf.webserviceClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zetton.thymeleaf.webserviceClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayHelloResponse_QNAME = new QName("http://entity.thymeleaf.zetton.com/", "sayHelloResponse");
    private final static QName _GetTeacherResponse_QNAME = new QName("http://entity.thymeleaf.zetton.com/", "getTeacherResponse");
    private final static QName _GetTeacher_QNAME = new QName("http://entity.thymeleaf.zetton.com/", "getTeacher");
    private final static QName _SayHello_QNAME = new QName("http://entity.thymeleaf.zetton.com/", "sayHello");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zetton.thymeleaf.webserviceClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTeacher }
     * 
     */
    public GetTeacher createGetTeacher() {
        return new GetTeacher();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link GetTeacherResponse }
     * 
     */
    public GetTeacherResponse createGetTeacherResponse() {
        return new GetTeacherResponse();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link Teacher }
     * 
     */
    public Teacher createTeacher() {
        return new Teacher();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://entity.thymeleaf.zetton.com/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeacherResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://entity.thymeleaf.zetton.com/", name = "getTeacherResponse")
    public JAXBElement<GetTeacherResponse> createGetTeacherResponse(GetTeacherResponse value) {
        return new JAXBElement<GetTeacherResponse>(_GetTeacherResponse_QNAME, GetTeacherResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeacher }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://entity.thymeleaf.zetton.com/", name = "getTeacher")
    public JAXBElement<GetTeacher> createGetTeacher(GetTeacher value) {
        return new JAXBElement<GetTeacher>(_GetTeacher_QNAME, GetTeacher.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://entity.thymeleaf.zetton.com/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

}
