package com.datagen.main;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.DataGenContext;
import com.datagen.FData;
import com.datagen.FDataSource;
import com.datagen.populate.PopulatorUtil;
import com.datagen.test.Person;

public class ObjectPopulator<T> {

    private static Logger m_logger = LoggerFactory.getLogger(ObjectPopulator.class);
    
    private PopulatorFaciliator     populatorFaciliator = null;
    
    private DataGenContext          runContext;
    
//    private String                  fileName = "datagen/populator/configuration-populator-default.xml";
    private Class<T>                modelClass;
//    private List<FDataSource>       fDataSources;
    
//    private String                  objectConfigName;
    
    /*
     * Runtime
     */
    private Map<String, PropertyDescriptor>             propertyDescritors;
    private Map<String, FDataSource>                    dataSourceMap;
    
    
    public ObjectPopulator(Class<T> modelClass) {
        super();
        this.modelClass = modelClass;
        
    }
    
    public void init() throws Exception {
        
        try {
            runContext = DataGenContext.getNewToken("datagen/populator/configuration-populator-" + modelClass.getSimpleName() +".xml");
        }
        catch (Exception e) {
            runContext = DataGenContext.getNewToken("datagen/populator/configuration-populator-DEFAULT.xml");
        }       

        dataSourceMap = new HashMap();
        
        List<DataGenContext> fDataConfigsFromImports = populatorFaciliator.getSourceAssembler().getPreLoadConfigs(runContext);
        
        for (Iterator iterator = fDataConfigsFromImports.iterator(); iterator.hasNext();) {
            DataGenContext dataGenContext = (DataGenContext) iterator.next();
            List<FDataSource> fDataSources = populatorFaciliator.getSourceAssembler().getPreLoadSources(dataGenContext);

            for (FDataSource fDataSource : fDataSources) {
                fDataSource.reload(dataGenContext);
                this.dataSourceMap.put(fDataSource.getFieldName(), fDataSource);
            }
            
        }
        
        List<FDataSource> fDataSourcesInMain = populatorFaciliator.getSourceAssembler().getSources(runContext);

        /*
         * Map dataSources for later Use
         */

        m_logger.info("Mapping dataSources to prepare");
        
        for (FDataSource fDataSource : fDataSourcesInMain) {
            fDataSource.reload(runContext);
            this.dataSourceMap.put(fDataSource.getFieldName(), fDataSource);
        }        
        
        populatorFaciliator.registerPopulator(modelClass, this);

        /*
         * Disect target class
         */
        
        propertyDescritors = createPropertyDescriptorsForDataClass(this.modelClass);
        
        for (PropertyDescriptor pd : propertyDescritors.values()) {

            m_logger.info("Creating populator for field class {} : {} -  {}", this.modelClass, pd.getName(), pd.getPropertyType());
            
            if ( !isJavaDataType(pd.getPropertyType()) && ! isCollectionType(pd.getPropertyType()) ) {
                
                ObjectPopulator popper = populatorFaciliator.getPopulator(pd.getPropertyType());
                if ( popper == null ) {
                    popper = new ObjectPopulator<>(pd.getPropertyType());
                    popper.setPopulatorFaciliator(populatorFaciliator);
                    popper.init();
                }
                
            } else if (isCollectionType(pd.getPropertyType())){
                
                if ( isMapType(pd.getPropertyType())) {
                    
                    Class clazzes[] = PopulatorUtil.getMapTypes(modelClass, pd.getName());
                    
                    for (int i = 0; i < clazzes.length; i++) {
                        if ( !isJavaDataType(clazzes[i]) ) {
                            ObjectPopulator popper = populatorFaciliator.getPopulator(clazzes[i]);
                            if ( popper == null ) {
                                popper = new ObjectPopulator<>(clazzes[i]);
                                popper.setPopulatorFaciliator(populatorFaciliator);
                                popper.init();
                            }
                        }
                    }
                    
                } else {
                
                    Class c = PopulatorUtil.getCollectionType(modelClass, pd.getName());
                    
                    if ( !isJavaDataType(c) ) {
                        ObjectPopulator popper = populatorFaciliator.getPopulator(c);
                        if ( popper == null ) {
                            popper = new ObjectPopulator<>(c);
                            popper.setPopulatorFaciliator(populatorFaciliator);
                            popper.init();
                        }
                    }
                }
            }
        }
        
        m_logger.info("Populator Initiated for class [{}]", this.modelClass);
        
    }
    
    public T generate() throws Exception {

        T obj = modelClass.newInstance();
        populateFields(obj);
        return obj;
    } 
    
    private void populateFields(Object obj) throws Exception {

        for (Map.Entry<String, PropertyDescriptor> entry : propertyDescritors.entrySet()) {
            String fieldName = (String) entry.getKey();
            PropertyDescriptor pdtor = (PropertyDescriptor) entry.getValue();
            
            if ( "class".equals(fieldName))
                continue;
            
//            m_logger.debug(">> Populating field " + fieldName);
            
            // Java type should be handled by 
            if ( isJavaDataType(pdtor.getPropertyType()) ) {

                Object data = nextFromSourceGenerator(fieldName, pdtor);
                
                if ( data == null ) {
                    m_logger.warn("**WARNING** Data not generated for field " + fieldName + "/" + pdtor.getPropertyType().getName());
                    continue;
                }                
                
                try {
                    Object value = data;
                    if ( data instanceof FData ) {
                        value = ((FData)data).getRawFormat();
                    }
                    
                    BeanUtils.setProperty(obj, fieldName, value);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }
                
            } else if ( isCollectionType(pdtor.getPropertyType())) {
                
                if ( isMapType(pdtor.getPropertyType())) {

                    Class classes[] = PopulatorUtil.getMapTypes(modelClass, fieldName);
                        
                    Class keyClass = classes[0];
                    Class valueClass = classes[1];
                    Map map = new HashMap();
                    
                    for (int i = 0; i < 10; i++) {
                        Object keyData = nextFromSourceGenerator(fieldName, keyClass);
                        Object valueData = nextFromSourceGenerator(fieldName, valueClass);

                        if ( keyData != null && valueData != null)
                            map.put(keyData, valueData);
                    }
                    
                    try {
                        BeanUtils.setProperty(obj, fieldName, map);
                    }
                    catch (Exception e) {
                        m_logger.error(e.getMessage() ,e);
                    }  
                    
                } else {
                
                    Class underlyingValueClass  = PopulatorUtil.getCollectionType(modelClass, fieldName);
                    Collection collectionObject = PopulatorUtil.createCollectionObject(modelClass, fieldName);
                    
                    for (int i = 0; i < 10; i++) {
                        Object fieldObject = nextFromSourceGenerator(fieldName, underlyingValueClass);
                        collectionObject.add(fieldObject);
                    }
                    
                    try {
                        BeanUtils.setProperty(obj, fieldName, collectionObject);
                    }
                    catch (Exception e) {
                        m_logger.error(e.getMessage() ,e);
                    }  
                }
                
            } else { // Plain Object field which not JavaType nor Collection nor Map
                
                Object fieldObject = nextFromSourceGenerator(fieldName, pdtor);
                
                try {
                    BeanUtils.setProperty(obj, fieldName, fieldObject);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }                
            }
        }
    }
    
    private Object nextFromSourceGenerator (String fieldName, PropertyDescriptor pdtor) throws Exception {
        return nextFromSourceGenerator(fieldName, pdtor.getPropertyType());
    }    
    
    /*
     * Collection field can be defined with field name and java type. 
     * It will pikcup 
     */
    
    private Object nextFromSourceGenerator (String fieldName, Class clazz) throws Exception {

        FDataSource dataSource = dataSourceMap.get(fieldName);
        
        if ( dataSource != null ) {
            FData fdata = dataSource.generateNext();
            if ( fdata != null) {
                Object ret = fdata.getRawFormat();
                if ( ret.getClass() == clazz) 
                    return ret;
            }
        }                

        dataSource = dataSourceMap.get(clazz.getName());
        if ( dataSource != null ) {
            FData fdata = dataSource.generateNext();
            if ( fdata != null) {
                Object ret = fdata.getRawFormat();
                if ( ret.getClass() == clazz) 
                    return ret;
            }
        }                
        
        ObjectPopulator fieldObjectPopulator = populatorFaciliator.getPopulator(clazz);
        
        if ( fieldObjectPopulator != null) {
            Object fieldObject = fieldObjectPopulator.generate();
            return fieldObject;
        }
        
        return null;
    }
    
    
    private boolean isJavaDataType(Class clazz) {
        
        if ( clazz.isAssignableFrom(String.class) || 
                Number.class.isAssignableFrom(clazz) || 
                Boolean.class.isAssignableFrom(clazz) || 
                Class.class.isAssignableFrom(clazz) || 
                Date.class.isAssignableFrom(clazz) )
            return true;
        
        return false;
    }
    
    private boolean isCollectionType(Class clazz) {
        
        if (  Collection.class.isAssignableFrom(clazz) || isMapType(clazz) )
            return true;
        
        return false;
    }    

    private boolean isMapType(Class clazz) {
        
        if (  Map.class.isAssignableFrom(clazz) )
            return true;
        
        return false;
    }       
    
    private Map<String, PropertyDescriptor> createPropertyDescriptorsForDataClass(Class clazz){
        PropertyDescriptor[] pds = org.springframework.beans.BeanUtils.getPropertyDescriptors(this.modelClass);
        
        Map<String, PropertyDescriptor> dpMap = new HashMap();
        for (int i = 0; i < pds.length; i++) {
            dpMap.put(pds[i].getName(), pds[i]);
        }
        
        return dpMap;
    }
    
    public PopulatorFaciliator getPopulatorFaciliator() {
        return populatorFaciliator;
    }

    public void setPopulatorFaciliator(PopulatorFaciliator populatorFaciliator) {
        this.populatorFaciliator = populatorFaciliator;
    }

    public static void main(String[] args) throws Exception {
        
        ObjectPopulator<Person> dataGenerator = new ObjectPopulator(Person.class);
        PopulatorFaciliator populatorFaciliator = new PopulatorFaciliator();

        dataGenerator.setPopulatorFaciliator(populatorFaciliator);
        dataGenerator.init();
        
        for (int i = 0; i < 100 ; i++) {
            
            Person person = dataGenerator.generate();
            m_logger.debug(person.toString());
        }
    
    }

}
