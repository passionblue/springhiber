package com.mkyong.form.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

    public static  String marshal(Object pObject) throws Exception {
        return marshal(pObject, null);
    }    
    public static  String marshal(Object pObject, String namespace) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(pObject.getClass());
        java.io.StringWriter sw = new StringWriter();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//      marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.mysite.com/abc.xsd");
        if (namespace != null)
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, namespace);

        
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
    
    
    public static String marshalDom(Document document) throws Exception {
     
        java.io.StringWriter sw = new StringWriter();

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        
        StreamResult result = new StreamResult(sw);
        t.transform(source, result);
        
        return sw.toString();
        
    }
    
    public static Document marshalToDoc(Object obj) throws Exception {
        
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        java.io.StringWriter sw = new StringWriter();

        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(obj, document);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(System.out);
        t.transform(source, result);
        
        return document;
        
    }
    
    public static Element marshalToElement(Object obj) throws Exception {
        
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        java.io.StringWriter sw = new StringWriter();

        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(obj, document);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        DOMResult  result = new DOMResult();
        t.transform(source, result);
        
        
        Element elt = ((Document)result.getNode()).getDocumentElement();
        
        return elt;
        
    }    
    
    public static void main(String[] args) throws Exception {
        
        // JAXB , object to standalone XML
        String data = marshal(new Sample(), "http://www.mysite.com/abc.xsd");
        System.out.println(data);
        
        // JAXB -> Document -> XML string
        Document d = marshalToDoc(new Sample());
        data = marshalDom(d);
        System.out.println("=============================");
        System.out.println("["  + data + "]");

        System.out.println("=============================");
    
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
        Document doc = icBuilder.newDocument();
        Element mainRootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "Companies");
        doc.appendChild(mainRootElement);

        
        Element e =  marshalToElement(new Child());
        doc.appendChild(e);

        
        
        data = marshalDom(d);
        System.out.println(data);
            
        
    }
    
    
    @XmlRootElement
    public static class Sample {
        
        private String data = "Thsi is just test";
        private Integer val = 1;
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }
    }

    @XmlRootElement
    public static class Child {
        
        private String data = "Thsi is just test";
        private Integer val = 1;
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }
    }

}
