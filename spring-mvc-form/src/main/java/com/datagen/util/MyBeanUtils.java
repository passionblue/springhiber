package com.datagen.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBeanUtils {

    private static Logger m_logger = LoggerFactory.getLogger(MyBeanUtils.class);

    public static void copy(Object bean, String name, Object value) throws Exception {

        PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(SampleData.class, "data");
        Method wm = pd.getWriteMethod();
        
        BeanUtilsBean.getInstance().setProperty(bean, wm.getName(), value);
    }

    public static void copy2(Object bean, String name, Object value) throws Exception {

        PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(SampleData.class, "data");
        Method wm = pd.getWriteMethod();
        
        wm.invoke(bean, value);
        
//        BeanUtilsBean.getInstance().setProperty(bean, wm.getName(), value);
    }

    
    public static void main(String[] args) throws Exception {
        
        SampleData d = new SampleData(null, null);
//        SampleData s = new SampleData();
//        
//        org.springframework.beans.BeanUtils.copyProperties(s, d);
//        
//        Method m = org.springframework.beans.BeanUtils.findDeclaredMethodWithMinimalParameters(SampleData.class, "data");
//        
//        
//        PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(SampleData.class, "data");
//        Method wm = pd.getWriteMethod();
        
//        copy2(d, "data", 1);
        
//        BeanUtils.setProperty(d,"Data","Paul Young");        
//        BeanUtils.setProperty(d,"data","Paul Young");        
//        BeanUtils.setProperty(d,"data",1);        
//        BeanUtils.setProperty(d,"value",1);   
//        
//        BeanUtils.setProperty(d,"value","2");   
//        copy(d, "data", 1);
//        copy(d, "value", 1);
//        copy(d, "value", "1");
        
        SampleData dest = new SampleData("1", 1);
        SampleData2 orig = new SampleData2(2, "2");
        
        BeanUtils.copyProperties(dest, orig);
        
        System.out.println(dest);
    }
    
    
}





