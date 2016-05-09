package com.datagen.populate;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulatorUtil {

    private static Logger m_logger = LoggerFactory.getLogger(PopulatorUtil.class);
    
    /*
     * Type of value for the collection
     */
    public static Class getCollectionType( Class clazz, String fieldName) throws Exception {
        
        Field stringListField = clazz.getDeclaredField(fieldName);
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> collectionListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        
        
        return collectionListClass;
    }
    
    public static Collection createCollectionObject(Class clazz, String fieldName) throws Exception {

        Field stringListField = clazz.getDeclaredField(fieldName);
        Class collType = stringListField.getType();
        
        if ( List.class.isAssignableFrom(collType)) {
            return new ArrayList();
        } else if ( Set.class.isAssignableFrom(collType)) {

            return new HashSet();
        } else if ( Queue.class.isAssignableFrom(collType)) {
            return new SynchronousQueue();
        }

        throw new IllegalArgumentException("Not support collection type " + collType);
    }
    
    // based from http://stackoverflow.com/questions/3687766/how-to-get-value-type-of-a-map-in-java
    public static Class[] getMapTypes(Class clazz, String fieldName) throws Exception {

        ParameterizedType pt = (ParameterizedType)clazz.getDeclaredField(fieldName).getGenericType();
        
        
        Class[] ret = new Class[2];
        
        int idx = 0;
        for(Type type : pt.getActualTypeArguments()) {
            ret[idx++] = (Class) type;
            if ( idx == 2) break;
        }

        return ret;
    }
}
