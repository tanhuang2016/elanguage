package com.edom;

import com.edom.io.EdomSAXReader;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.StringTokenizer;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class DocumentHelper {

	/**
	 * 把字符串转换为edom
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseText(String text) throws DocumentException {
		Document result = null;
		EdomSAXReader reader = new EdomSAXReader();

		InputSource source = new InputSource(new StringReader(text));

		result = reader.read(source);

		return result;
	}

	private static String getEncoding(String text) {
		String result;
		label0: {
			result = null;
			String xml = text.trim();
			if (!xml.startsWith("<?xml"))
				break label0;
			int end = xml.indexOf("?>");
			String sub = xml.substring(0, end);
			StringTokenizer tokens = new StringTokenizer(sub, " =\"'");
			String token;
			do {
				if (!tokens.hasMoreTokens())
					break label0;
				token = tokens.nextToken();
			} while (!"encoding".equals(token));
			if (tokens.hasMoreTokens())
				result = tokens.nextToken();
		}
		return result;
	}
	
	public static String[] split(String str){
		String[] arr= str.split(" =\"'");
		return arr;
	}
	
	public static void main(String[] args) throws DocumentException{
		
		String s="<! Entity=铁心桥 type=测试2011-11-03 dataTime='20120423 13:34:20' !>\n"+
			"<DG::铁心桥 date='2012-04-23' DDMM='达梦' >\n"+
			"<DG::铁心桥 date='2012-04-23' DDMM='达梦' >\n"+
			"@@顺序 属性名 属性值\n"+
			"#1 单位名称 花花电网 \n"+
			"#2 发生时间 '2011-11-03 00:00:02.0' \n"+
			"#3 次数 32 \n"+
			"#1 单位名称 花花电网 \n"+
			"#2 发生时间 '2011-11-03 00:00:02.0' \n"+
			"#3 次数 32 \n"+
			"#1 单位名称 花花电网 \n"+
			"#2 发生时间 '2011-11-03 00:00:02.0'\n"+ 
			"#3 次数 32 \n"+
			"#1 单位名称 花花电网 \n"+
			"#2 发生时间 '2011-11-03 00:00:02.0' \n"+
			"#3 次数 32 \n"+
			"</DG::铁心桥>\n"+
			"</DG::铁心桥>\n";
		Document doc=parseText(s);
		//System.out.println("---------test begin-----");
		NodeList nodeList=doc.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			//System.out.println("---------element-----");
			Element ele=(Element) nodeList.item(i);
			
			//System.out.println("tagName=="+ele.getTagName());
			//System.out.println("body==\n"+ele.getTextContent());
			NamedNodeMap map=ele.getAttributes();
			for(int j=0;j<map.getLength();j++){
				Attr attr=(Attr) map.item(j);
				//System.out.println("attribute:"+attr.getName()+"=="+attr.getValue());
			}
			
			
			
		}
		//System.out.println(doc.getChildNodes().item(0).getTextContent());
	}

}
