package com.mkyong.form.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement
public class Phone {

    private String age;
    private String carrier;
    private String id;
    private String imageUrl;
    private String name;
    private String snippet;
    
    @XmlElement
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getCarrier() {
        return carrier;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSnippet() {
        return snippet;
    }
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
    @Override
    public String toString() {
        return "Phone [age=" + age + ", carrier=" + carrier + ", id=" + id + ", imageUrl=" + imageUrl + ", name=" + name + ", snippet=" + snippet + "]";
    }
    

    
}
