package com.hisense.nettyServer.support.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author qiush
 *
 */
public class XML2String {
	public static String XMLToString(String xmlFileName) {
		SAXReader saxReader = new SAXReader();
		Document document;
		String xmlString = "";
		try {
			document = saxReader.read(new File(xmlFileName));
			xmlString = document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			xmlString = "";
		}
		return xmlString;
	}

}
