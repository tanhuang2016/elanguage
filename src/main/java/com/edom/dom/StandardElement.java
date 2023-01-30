package com.edom.dom;

import java.util.*;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class StandardElement implements Element {

	String tagName;
	
	StandardNodeList childNodes=new StandardNodeList ();
	
	String textContent;
	
	List<Attr> attrs=new ArrayList<Attr>();
	Node parentNode;
	public StandardElement(){
		
	}
	public StandardElement(String name){
		tagName=name;
	}
	public void setParentNode(Node parent){
		this.parentNode=parent;
	}
	public Node appendChild(Node newChild) throws DOMException {
		childNodes.add(newChild);
		  return newChild;
	}

	public Node cloneNode(boolean deep) {
		// TODO Auto-generated method stub
		return null;
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	public NamedNodeMap getAttributes() {
		StandardNamedNodeMap map=new StandardNamedNodeMap(attrs);
		return map;
	}

	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeList getChildNodes() {
		// TODO Auto-generated method stub
		return childNodes;
	}

	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	public Node getFirstChild() {
		// TODO Auto-generated method stub
		return childNodes.item(1);
	}

	public Node getLastChild() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNodeName() {
		// TODO Auto-generated method stub
		return tagName;
	}

	public short getNodeType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNodeValue() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Document getOwnerDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node getPreviousSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTextContent() throws DOMException {
		return this.textContent;
	}

	public Object getUserData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasAttributes() {
		if(this.attrs!=null &&this.attrs.size()>0 ){
			return true;
		}
		
		return true;
	}

	 
	public boolean hasChildNodes() {
		// TODO Auto-generated method stub
		return false;
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualNode(Node arg) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameNode(Node other) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSupported(String feature, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	public String lookupNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	public String lookupPrefix(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

	public void normalize() {
		// TODO Auto-generated method stub
		
	}

	public Node removeChild(Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setPrefix(String prefix) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	
	public void setTextContent(String textContent) throws DOMException {
		this.textContent=textContent;
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAttribute(String name) {
		Attr attr=getAttributeNode(name);
		if(attr!=null){
			return attr.getValue();
		}
		return null;
	}

	public String getAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Attr getAttributeNode(String name) {
		if(attrs!=null && attrs.size()>0){
			for(Attr attr:attrs){
				if(attr.getName().trim().equals(name))
					return attr;
			}
		}
		return null;
	}

	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeList getElementsByTagName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTagName() {
		// TODO Auto-generated method stub
		return tagName;
	}

	public boolean hasAttribute(String name) {
		if(hasAttributes()){
			for(Attr attr:attrs){
				if(attr.getName().equals(name)){
					return true;
				}
			}
		}		
		return false;
	}

	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAttribute(String name) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String name, String value) throws DOMException {
		// TODO Auto-generated method stub
		Attr attr=new StandardAttr(name,value);
		setAttributeNode(attr);
		
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		if(this.hasAttribute(newAttr.getName())){
			Attr oldAttr=this.getAttributeNode(newAttr.getName());
			oldAttr.setValue(newAttr.getValue());
			return oldAttr;
		}
		else{
			this.attrs.add(newAttr);
			return newAttr;
		}
	 
	}

	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
