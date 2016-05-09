package com.datagen.messaging.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datagen.messaging.ConnectionNode;
import com.datagen.messaging.MessageSender;

public class ActiveJmsSender extends ConnectionNode implements MessageSender {

    private static Logger m_logger = LoggerFactory.getLogger(ActiveJmsSender.class);

    //Default Constructor
    public ActiveJmsSender() {
    }

}
