package com.mkyong.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.digester.Digester;
import java.io.File;

public class AddressDigester {

    private static Logger m_logger = LoggerFactory.getLogger(AddressDigester.class);

//    public static void main(String[] args) {
//
//        try {
//            Digester digester = new Digester();
//            digester.setValidating(false);
//
//            digester.addObjectCreate("addresses", Addresses.class);
//
//            digester.addObjectCreate("addresses/address", Address.class);
//            digester.addBeanPropertySetter("addresses/address/addressLine1", "addressLine1");
//            digester.addBeanPropertySetter("addresses/address/addressLine2", "addressLine2");
//            digester.addSetNext("addresses/address", "addAddress");
//
//            digester.addObjectCreate("addresses/person", Person.class);
//            digester.addBeanPropertySetter("addresses/person/name", "name");
//
//            digester.addObjectCreate("addresses/person/detail", Detail.class);
//            digester.addSetProperties("addresses/person/detail", "age", "age");
//            digester.addBeanPropertySetter("addresses/person/detail/education");
//            digester.addSetNext("addresses/person/Detail", "addDetail");
//
//            digester.addSetNext("addresses/person", "addPerson");
//
//            File inputFile = new File(args[0]);
//            Addresses address = (Addresses) digester.parse(inputFile);
//
//        }
//        catch (Exception exc) {
//            exc.printStackTrace();
//        }
//
//    }
}
