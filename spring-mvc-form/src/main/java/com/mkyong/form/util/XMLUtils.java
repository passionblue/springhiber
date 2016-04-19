package com.mkyong.form.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public class XMLUtils {

    public static <T> T unmarshal(String args, Class<T> clazz) throws Exception {
        args = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + args;
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T t = (T) unmarshaller.unmarshal(new StringReader(args));
        return t;
    }

    public String marshal(Object pObject) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(pObject.getClass());
        java.io.StringWriter sw = new StringWriter();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(pObject, sw);

        return sw.toString();
    }
    
    public static String beautifier(String xml) throws Exception {
        
        InputSource src = new InputSource(new StringReader(xml));
        Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
        
        Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));
        
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSSerializer writer = impl.createLSSerializer();
        
        writer.getDomConfig().setParameter("format-pretty-print",  Boolean.TRUE);
        writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
        
        return writer.writeToString(document);
    }
    
    public static void main(String[] args) {
        
    }
    
}
