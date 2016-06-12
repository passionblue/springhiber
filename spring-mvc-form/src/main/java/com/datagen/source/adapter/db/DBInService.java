package com.datagen.source.adapter.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBInService {

    private DBInDao inDao;

    public DBInDao getDbInDao() {
        return inDao;
    }

    @Autowired
    public void setDbInDao(DBInDao dao) {
        this.inDao = dao;
    }

}
