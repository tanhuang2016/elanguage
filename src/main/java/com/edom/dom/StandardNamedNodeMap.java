package com.edom.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class StandardNamedNodeMap implements NamedNodeMap{

	List<Attr> attrs=new ArrayList<Attr>();
	
	public   StandardNamedNodeMap(List<Attr> attrs){
		this.attrs=attrs;
	}
	public int getLength() {
		return attrs.size();
	}

	public Node getNamedItem(String name) {
		if(attrs!=null && attrs.size()>0){
			for(Attr attr:attrs){
				if(attr.getName().equals(name)){
					return attr;
				}
			}
		}
		return null;
	}

	public Node getNamedItemNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Node item(int index) {
		
		return attrs.get(index);
	}

	public Node removeNamedItem(String name) throws DOMException {
		
		return null;
	}

	public Node removeNamedItemNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Node setNamedItem(Node arg) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Node setNamedItemNS(Node arg) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

}
