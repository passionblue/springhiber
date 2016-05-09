package com.datagen.test;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Person {

    private static Logger m_logger = LoggerFactory.getLogger(Person.class);
    
    private String name;
    private String lastName;
    private Integer age;
    private Date   registered;
    private String email;
    private Order  mainOrder;
    private List<Integer> intList;
    private List<Order> prveiousOrderes;
    private Map<Integer, Order> orderMap;
    private Map<Integer, String> orderIdMap;
    
    //Default Constructor
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    public Order getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(Order mainOrder) {
        this.mainOrder = mainOrder;
    }

    public List<Order> getPrveiousOrderes() {
        return prveiousOrderes;
    }

    public void setPrveiousOrderes(List<Order> prveiousOrderes) {
        this.prveiousOrderes = prveiousOrderes;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    
    
    public Map<Integer, Order> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Integer, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Map<Integer, String> getOrderIdMap() {
        return orderIdMap;
    }

    public void setOrderIdMap(Map<Integer, String> orderIdMap) {
        this.orderIdMap = orderIdMap;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", lastName=" + lastName + ", age=" + age + ", registered=" + registered + ", email=" + email + ", mainOrder=" + mainOrder + ", intList=" + intList
                + ", prveiousOrderes=" + prveiousOrderes + ", orderMap=" + orderMap + ", orderIdMap=" + orderIdMap + "]";
    }





}
