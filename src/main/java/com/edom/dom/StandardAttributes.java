package com.edom.dom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class StandardAttributes implements org.xml.sax.Attributes{

	
	private  LinkedHashMap<String,String> attributes=new LinkedHashMap<String,String>();
	
	public StandardAttributes(){}
	
	
	
	public void put(String name,String value){
		attributes.put(name, value);
	}
	public int getIndex(String name) {
		int i=0;
		for(String key:attributes.keySet()){
			if(key.equals(name)){
				return i;
			}
			i++;
		}
		return -1;
	}

	public int getIndex(String uri, String localName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLength() {
		return attributes.size();
	}

	public String getLocalName(int index) {
		
		int i=0;
		for(String key:attributes.keySet()){
			if(i==index)
				return key;
			i++;
		}
		return null;
	}

	public String getQName(int index) {
		return getLocalName(index);
	}

	public String getType(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType(String uri, String localName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getURI(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValue(int index) {
		int i=0;
		for(String value:attributes.values()){
			if(i==index){
				return value;
			}
			i++;
		}
		return null;
	}

	public String getValue(String name) {
		return attributes.get(name);
	}

	public String getValue(String uri, String localName) {
		// TODO Auto-generated method stub
		return null;
	}
	 

}
