package com.mkyong.form.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkyong.form.model.AngularJson;
import com.mkyong.form.model.Phone;
import com.mkyong.form.model.PhoneList;
import com.mkyong.form.util.json.JSONUtil;


//http://www.mkyong.com/spring-mvc/spring-3-mvc-and-json-example/

@Controller
@RequestMapping("/angular") // setting root level request 
@Scope("session") // i dont know what this for
public class AngularController {
    
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /*
     * This is how to return json string to the client. 
     * 
     */
    
    @RequestMapping(value = "/get-json", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody List<Phone> getJson() {

        List<Phone> phones = new ArrayList<Phone>();
        try {
//            phones = JSONUtil.loadObjectFromJSONFile("phone.json", Phone.class);
            phones = JSONUtil.loadObjectFromJSONFile("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json", Phone.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        logger.debug("AngularController.getJson");
        return phones;
    }

    
    @RequestMapping(value = "/get-json2", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody AngularJson getJson2() {

        
        AngularJson json = new AngularJson();
        
        List<Phone> phones = new ArrayList<Phone>();
        try {
            phones = JSONUtil.loadObjectFromJSONFile("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json", Phone.class);
            json.setPhones(phones);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        
        logger.debug("AngularController.getJson");
        return json;
    }    
    
    /*
     * JSON and XML are all handled by reading annontations in the object if set @ResponseBy and produces="" in annontations. 
     * 
     * Spring contains list of handler
     * 
     * But not like JSON, XML implementation can't handle the list of marshallable objects. So if return plain List, it would not pickup the correct handler. 
     * 
     * So here as an sample, PhoneList class is created. Need to make sure @XmlElementWrapper annonation in the list field. 
     * 
     * for the producicible type, "text/xml" and "application/xml" both worked. 
     * 
     * In many example, ppl show with the sample codes with "<mvc:annotation-driven>" in the config file. For my case, it still worked without lines. 
     * Maybe it is supported by another spring framework directive. 
     * 
     * Also the media types can be controlled as below. I didn't have to test but these are showing how handler can be managed. 
     * 
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="supportedMediaTypes" value="application/xml;charset=UTF-8" />
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <bean class = "org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">
        <property name="messageConverters">
            <array>
                <bean class = "org.springframework.http.converter.StringHttpMessageConverter">
                    <property name="supportedMediaTypes" value = "text/plain;charset=UTF-8" />
                </bean>
            </array>
        </property>
    </bean>

    Top part is adding supported type in the list of supported Types. 
    Second section adds the converter itself. 
    

     * 
     * 
     */
    
    
    @RequestMapping(value = "/get-xmls", method = RequestMethod.GET, produces = "text/xml" )
    public @ResponseBody PhoneList getXmls() {

        PhoneList phoneList = new PhoneList();
        try {
//            phones = JSONUtil.loadObjectFromJSONFile("phone.json", Phone.class);
            List<Phone> s = JSONUtil.loadObjectFromJSONFile("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json", Phone.class);
            phoneList.setPhones(s);;
            
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        logger.debug("AngularController.getXml() " + phoneList.getPhones());
        return phoneList;
    }    
    
    @RequestMapping(value = "/get-xml", method = RequestMethod.GET, produces = "application/xhtml+xml" )
    public @ResponseBody Phone getXml() {

        List<Phone> phones = new ArrayList<Phone>();
        try {
//            phones = JSONUtil.loadObjectFromJSONFile("phone.json", Phone.class);
            phones = JSONUtil.loadObjectFromJSONFile("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json", Phone.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        logger.debug("AngularController.getXml() " + phones);
        return phones.get(0);
    }      
    
    @RequestMapping(value = "/get-string", method = RequestMethod.GET )
    public @ResponseBody String getString() {

        List<Phone> phones = new ArrayList<Phone>();
        try {
            phones = JSONUtil.loadObjectFromJSONFile("C:/Users/joshua/git/springhiber/spring-mvc-form/src/main/webapp/phones/phones.json", Phone.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        logger.debug("AngularController.getXml() " + phones);
        return phones.toString();
    }       
    
}
