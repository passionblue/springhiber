package com.datagen.main;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datagen.FDataSourceAssembler;
import com.datagen.FDataTransformFactory;
import com.datagen.output.OutputChannelFactory;
import com.datagen.populate.ObjectPopulator;


public class PopulatorFaciliator {

    private static Logger m_logger = LoggerFactory.getLogger(PopulatorFaciliator.class);

    private FDataSourceAssembler        sourceAssembler = null;
    private FDataTransformFactory       transformerFactory = null; //
    private OutputChannelFactory        outputChannelFactory = null;
    private Map<Class, ObjectPopulator>     objectPopulatorMap;
    
    
    //Default Constructor
    public PopulatorFaciliator() {

        ApplicationContext c    = new ClassPathXmlApplicationContext("applicationContext-datagen.xml");
        sourceAssembler         = (FDataSourceAssembler) c.getBean("SourceAssembler");
        transformerFactory      = (FDataTransformFactory) c.getBean("TransformerFactory");
        outputChannelFactory    = (OutputChannelFactory) c.getBean("DeliveryFactory");
        
        objectPopulatorMap      = new HashMap();
    }



    public FDataSourceAssembler getSourceAssembler() {
        return new com.datagen.source.assembler.FDSourceAggregatorByXMLConfigImpl(false);
    }



    public void setSourceAssembler(FDataSourceAssembler sourceAssembler) {
        this.sourceAssembler = sourceAssembler;
    }



    public FDataTransformFactory getTransformerFactory() {
        return transformerFactory;
    }



    public void setTransformerFactory(FDataTransformFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }



    public OutputChannelFactory getOutputChannelFactory() {
        return outputChannelFactory;
    }



    public void setOutputChannelFactory(OutputChannelFactory outputChannelFactory) {
        this.outputChannelFactory = outputChannelFactory;
    }

    
    public void registerPopulator(Class clazz, ObjectPopulator populator) {
        objectPopulatorMap.put(clazz, populator);
    }
    
    public ObjectPopulator getPopulator(Class clazz){
        return objectPopulatorMap.get(clazz);
    }
    
}
