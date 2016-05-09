package com.datagen.messaging.jms;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ActiveJmsProvider {

    private static Logger m_logger = LoggerFactory.getLogger(ActiveJmsProvider.class);

    
    private InitialContext initialContext;
    
    //Default Constructor
    public ActiveJmsProvider(String jndiString, String user, String password, String clientId) {
        this.jndiProviderURL = jndiString;
        this.username = user;
        this.password = password;
      
    }

    private String jndiProviderURL;
    private String username;
    private String password;
    private String cfJNDIName;

    
    public Connection createConnection() throws Exception {
        
        
        Hashtable<String, Object> env = new Hashtable<String, Object> ();
        
        return null;
        
    }
    
    
}
