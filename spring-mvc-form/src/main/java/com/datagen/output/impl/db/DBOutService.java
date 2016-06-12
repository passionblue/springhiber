package com.datagen.output.impl.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBOutService {

    private DBOutDao personDao;

    public DBOutDao getDbOutDao() {
        return personDao;
    }

    @Autowired
    public void setPersonDao(DBOutDao personDao) {
        this.personDao = personDao;
    }

}
