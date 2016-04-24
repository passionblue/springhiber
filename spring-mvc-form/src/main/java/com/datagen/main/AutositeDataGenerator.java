package com.datagen.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datagen.FData;
import com.datagen.FDataGroup;
import com.datagen.FDataRow;
import com.datagen.FDataSource;
import com.datagen.FDataSourceAssembler;
import com.datagen.FDataTransformFactory;
import com.datagen.DataGenContext;
import com.datagen.data.row.FDataRowImpl;
import com.datagen.output.OutputChannel;
import com.datagen.output.OutputChannelFactory;

public class AutositeDataGenerator {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeDataGenerator.class);
    
    private FDataSourceAssembler    sourceAssembler = null;
    private FDataTransformFactory   transformerFactory = null; //
    private OutputChannelFactory    outputChannelFactory = null;
    
    /*
     * Application Configs
     */
    private int numDataToGenerate = 5;
    
    public void init() {
        
    }
    
    public void generate(String fileName) throws Exception {
        
        /*
         * Generate run-id
         */
        
        DataGenContext runContext = DataGenContext.getNewToken(fileName);
        
        m_logger.debug("##################################################################################");
        m_logger.debug("Run Context Created " + runContext);
        m_logger.debug("##################################################################################");
        
        /*
         * Check file first. And validate the basic fields. register to the system. 
         */
        
        
        /*
         *  get all sources
         */

        List<DataGenContext> preLoadConfigs = sourceAssembler.getPreLoadConfigs(runContext);
        
        for (DataGenContext pContext : preLoadConfigs) {
            List<FDataSource> fDataPreLoadSources = sourceAssembler.getPreLoadSources(pContext);
            List<OutputChannel> preLoadChannels = outputChannelFactory.getPreLoadChannels(pContext);
            runDataForSources(pContext, fDataPreLoadSources, preLoadChannels);
        }
        
        List<FDataSource> fDataSources = sourceAssembler.getSources(runContext);
        List<OutputChannel> channels = outputChannelFactory.getChannels(runContext);
        runDataForSources(runContext, fDataSources, channels);

        
    } 
    
    public void runDataForSources(DataGenContext runContext, List<FDataSource> fDataSources, List<OutputChannel> channels) throws Exception {
        
        /*
         *  get all sources
         */
//        List<FDataSource> fDataSources = sourceAssembler.getSources("configuration-pre.xml");
//        List<FDataSource> fDataPreLoadSources = sourceAssembler.getPreLoadSources("configuration-pre.xml");
        
        /*
         * Open the channels
         * 
         */
        

        for (OutputChannel deliveryChannel : channels) {
            deliveryChannel.open();
        }
        
        /*
         *  get data from sources   
         */

        for (FDataSource ds : fDataSources) {
            ds.reload(runContext);
        }
        
        for (int i = 0; i < runContext.getDataCount() ; i++) {
            
            FDataRow dataRow = new FDataRowImpl();
            
            for (FDataSource fDataSource : fDataSources) {

//                m_logger.debug("Data Generating for field [{}:{}] Source={}, Exclude={}", fDataSource.getFieldName(), i, fDataSource.getClass().getSimpleName(), fDataSource.excludeInOutput() );

                FData fData = fDataSource.generateNext();
                if ( fData instanceof FDataGroup ) {
                    FDataGroup dg = (FDataGroup) fData;
                    dataRow.addData(dg.getRawFormat());
                    m_logger.debug("--(G)-> field={}, Source={}, dataClass={}, size={}, underlying={}, exclude={}, Sexclude={}", dg.getFieldName(), fDataSource.getClass().getSimpleName(), fData.getClass().getSimpleName(), dg.getRawFormat().size(), dg.getUnderlyingData().size(), dg.excludeInOutput(), fDataSource.excludeInOutput());
                } else {
                    dataRow.addData(fData);
                    m_logger.debug("--(F)-> field={}, Source={}, dataClass={}, exclude={}, Sexclude={}", fData.getFieldName(), fDataSource.getClass().getSimpleName(), fData.getClass().getSimpleName(), fData.excludeInOutput(), fDataSource.excludeInOutput());
                }
            }
            
            /*
             *  transform data
             */
            
            FDataRow formattedRow = new FDataRowImpl();
            
            for (FData fData : dataRow.getData(false)) {
                FData result = transformerFactory.getTransformer(fData).transform(fData);
                if ( result instanceof FDataGroup ) {
                    formattedRow.addData(((FDataGroup)result).getRawFormat());
                } else {
                    formattedRow.addData(result);
                }
            }
            
            /*
             * Exclude output from excluded incidator
             */

//            FDataRow finalRow = new FDataRowImpl();
//            
//            for (FData fData : formattedRow.getData()) {
//                if (fData.excludeInOutput())
//                    continue;
//                finalRow.addData(fData);
//            }
            
            
            /*
             *  transform data for delivery format
             */
            
            for (OutputChannel deliveryChannel : channels) {
                deliveryChannel.write(formattedRow);
            }
        }        

        for (OutputChannel deliveryChannel : channels) {
            deliveryChannel.close();;
        }
        
        
    }


    public FDataSourceAssembler getSourceAssembler() {
        return sourceAssembler;
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

    public int getNumDataToGenerate() {
        return numDataToGenerate;
    }

    public void setNumDataToGenerate(int numDataToGenerate) {
        this.numDataToGenerate = numDataToGenerate;
    }

    public static void main(String[] args) throws Exception {
        
        ApplicationContext c = new ClassPathXmlApplicationContext("applicationContext-datagen.xml");
        AutositeDataGenerator gen = (AutositeDataGenerator) c.getBean("dataGenerator");

        long start = System.currentTimeMillis();
        gen.generate("configuration-main.xml");
        long end = System.currentTimeMillis();
        
        System.out.println("Time Taken " + (end-start));
        
        System.gc();

        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
            System.gc();
        }
        
    }
    
}
