package com.mkyong.form.model;

import java.io.Serializable;
import java.util.List;

public class AngularJson implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3751043085942762902L;
    

    
    private List<Phone> phones;



    public List<Phone> getPhones() {
        return phones;
    }



    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    
    
    
}
