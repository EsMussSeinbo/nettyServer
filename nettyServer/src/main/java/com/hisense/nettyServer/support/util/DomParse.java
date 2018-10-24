package com.hisense.nettyServer.support.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * <p>Title: DomParse</p>
 * <p>Description:org.dom4j.document和org.w3c.dom.document转换</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company:青岛海信网络科技有限公司</p>
 *@author     <a href="mailto:zhangsihai@hisense.com">Sileader</a>
 *@created    2008年07月03日
 *
 */

public class DomParse {
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	/**
	 * org.w3c.dom.Document -> org.dom4j.Document
	 * @param doc Document(org.w3c.dom.Document)
	 * @return Document
	 */
	public static org.dom4j.Document parse(org.w3c.dom.Document doc)
			throws Exception {
		if (doc == null) {
			return (null);
		}
		org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
		return (xmlReader.read(doc));
	}

	/**
	 * org.dom4j.Document -> org.w3c.dom.Document
	 * @param doc Document(org.dom4j.Document)
	 * @throws Exception
	 * @return Document
	 */
	public static org.w3c.dom.Document parse(org.dom4j.Document doc)
			throws Exception {
		if (doc == null) {
			return (null);
		}
		java.io.StringReader reader = new java.io.StringReader(doc.asXML());
		org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
		javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = javax.xml.parsers.DocumentBuilderFactory
				.newInstance();
		javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		return (documentBuilder.parse(source));
	}
	
	public static org.w3c.dom.Document parseXmlToDocument(String s) throws Exception {
		
    	DocumentBuilderFactory dbf	=	DocumentBuilderFactory.newInstance();
		DocumentBuilder df;
		try {
			df = dbf.newDocumentBuilder();
			StringReader reader			=	new StringReader(s.trim());    
			InputSource source 			=	new InputSource(reader); 
			return df.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			throw new Exception();
		}
    }
	 public static String getValueByTagName(org.w3c.dom.Document Doc,String tagName) {
	    	
	    	try{
	    		if(Doc!=null){
	    			org.w3c.dom.NodeList nl = Doc.getElementsByTagName(tagName);
		        	if(nl.getLength()>0) {
		        		return nl.item(0).getTextContent();
		        	}
	    		}
	        	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return "";
	    }
	 public static String[] getValuesByTagName(org.w3c.dom.Document Doc,String tagName) {   	
		   
	    	try{
	    		if(Doc!=null){
	    			org.w3c.dom.NodeList nl = Doc.getElementsByTagName(tagName);
		        	String[] returnStrs = new String[nl.getLength()]; 
		        	for(int i = 0;i<nl.getLength();i++){
		        		returnStrs[i] = nl.item(i).getTextContent();
		        	}
		        	return returnStrs;
	    		}
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return new String[0];
	    	
	    }
}
