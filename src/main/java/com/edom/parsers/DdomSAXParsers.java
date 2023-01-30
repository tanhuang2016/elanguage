package com.edom.parsers;

import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import com.edom.parser.EdomXMLReader;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class DdomSAXParsers extends javax.xml.parsers.SAXParser{

	@Override
	public Parser getParser() throws SAXException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException,
			SAXNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XMLReader getXMLReader() throws SAXException {
		return new EdomXMLReader();
	}

	@Override
	public boolean isNamespaceAware() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidating() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setProperty(String name, Object value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		// TODO Auto-generated method stub
		
	}

}
