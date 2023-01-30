package com.edom.dom;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class StandardNodeList implements NodeList{

	private List<Node> nodes=new ArrayList<Node>();
	
	public void add(Node node){
		nodes.add(node);
	}
	
	public int getLength() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	public Node item(int index) {
		// TODO Auto-generated method stub
		return nodes.get(index);
	}

}
