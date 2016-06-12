package com.datagen.source.adapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
public class DatabaseAdapter  extends AbstractDataSetAdapter<String>{

    private static Logger m_logger = LoggerFactory.getLogger(DatabaseAdapter.class);

    //Default Constructor
    public DatabaseAdapter() {
    }

    @Override
    public int getDataSize() {
        
        return 0;
    }

    @Override
    public String getByPosition(int pos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reload(DataGenContext context) throws Exception {
        // TODO Auto-generated method stub
        super.reload(context);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        super.close();
    }
    
    
    

}
