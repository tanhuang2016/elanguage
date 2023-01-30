/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.edom.io;

import java.lang.reflect.Method;
import java.util.*;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.edom.dom.*;
import  com.edom.util.*;

/**
 * @author 王正权
 * 973598066@qq.com
 */

public class EdomSAXContentHandler extends DefaultHandler
  
{
	
	private ArrayStack<Node>  elmentStack=null;

	private Document document;
	
	public EdomSAXContentHandler(){
		this(new ArrayStack<Node>());
	}
	public EdomSAXContentHandler(ArrayStack<Node>  elmentStack){
		this.elmentStack=elmentStack;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		Element ele=(Element) elmentStack.peek();
		ele.setTextContent(new String(ch,start,length));
	}

	@Override
	public void endDocument() throws SAXException {
		elmentStack.pop();
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		elmentStack.pop();
	}

	@Override
	public void startDocument() throws SAXException {
		document=new StandardDocument();
		elmentStack.push(document);
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		Element ele=new StandardElement(localName);
		for(int i=0;i<attributes.getLength();i++){
			ele.setAttribute(attributes.getLocalName(i), attributes.getValue(i));
		}
		elmentStack.peek().appendChild(ele);
		this.elmentStack.push(ele);
	}

	
	public  Document getDocument(){
		return document;
	}
	 
}