package com.efile.impl;

import com.edom.io.EdomSAXReader;
import com.efile.EFileParse;
import com.efile.ETable;
import com.util.Debug;
import com.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class DefaultEfileParse implements EFileParse {

	

	public List<ETable> parseFile(File file) throws Exception {
		
		EdomSAXReader reader=new EdomSAXReader();
		Document doc = reader.read(file);
		List<ETable> tableList = new ArrayList<ETable>();

		NodeList nodeList = doc.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element ele = (Element) nodeList.item(i);
			ETable table = new ETable();
			String tagName = ele.getTagName();
			if (tagName.contains("::")) {
				table.setTableName(tagName.split("::")[0]);
			} else {
				table.setTableName(tagName);
			}

			table.setDate(ele.getAttribute("date"));
			tableList.add(table);

			parseTableData(table, ele.getTextContent());

		}
		return tableList;
	}

	private void parseTableData(ETable table, String content) {
		// System.out.println("==context=="+content);
		String[] contentArr = content.split("\n");
		String headStr = contentArr[0].trim();
		if (headStr.startsWith("@@")) {
			// 单列式
			parseSingleColType(table, contentArr);
		} else if (headStr.startsWith("@#")) {
			// 多列式
			parseMultiColType(table, contentArr);
		} else if (headStr.startsWith("@")) {
			// 横表式
			parseRowType(table, contentArr);
		}

	}

	/**
	 * 解析单列式
	 * @@
	 * 
	 * @param table
	 * @param contentArr
	 */
	private void parseSingleColType(ETable table, String[] contentArr) {
		String headStr = contentArr[0].trim();
		String[] headerArr = splitString( headStr.substring("@@".length()) );
		if (contentArr.length > 1) {
			int propNo = 0;
			/**
			 * 存储属性name--value的Map
			 */
			Map<String, String> propMap = new LinkedHashMap<String,String>();
			int loopTime = 0;
			
			for (int i = 1; i < contentArr.length; i++) {
				String linestr = contentArr[i].trim();
				// System.out.println("linestr="+linestr);
				
				if(linestr.startsWith("#")){
					linestr=linestr.substring(1);
					String[] lineArr = splitString(linestr);
					String[] popArr=new String[]{"",""};
					if(headerArr.length==2){
						System.arraycopy(lineArr, 0, popArr, 0, lineArr.length);
					}
					else if(headerArr.length==3){
						System.arraycopy(lineArr, 1, popArr, 0, lineArr.length-1);
					}
					if(propMap.containsKey(popArr[0])){
						//把属性值 加入到table的数据列表 
						String[] data = new String[propMap.values().size()];
						table.getDatas().add(propMap.values().toArray(data));
						propMap.clear();
						
					}
					propMap.put(popArr[0], popArr[1]);
					
					
				}
				
			}
			//设置列名
			String[] columns = new String[propMap.keySet().size()];
			table.setColumnNames(propMap.keySet().toArray(columns));
			//把属性值 加入到table的数据列表 
			String[] data = new String[propMap.values().size()];
			table.getDatas().add(propMap.values().toArray(data));
			propMap.clear();
		}
	}

	/**
	 * 解析多列式
	 * 
	 * @param table
	 * @param contentArr
	 */
	private void parseMultiColType(ETable table, String[] contentArr) {
		if (contentArr.length > 1) {
			List<String[]> porpList = new ArrayList<String[]>();
			for (int i = 1; i < contentArr.length; i++) {
				String linestr = contentArr[i].trim();
				// System.out.println("linestr="+linestr);
				if(linestr.startsWith("#")){
					linestr=linestr.substring(1);
					String[] lineArr = splitString(linestr);
					porpList.add(lineArr);
				}
				
			}
			String[] columnNames = new String[porpList.size()];
			for (int col = 0; col < columnNames.length; col++) {
				columnNames[col] = porpList.get(col)[1];
			}
			table.setColumnNames(columnNames);

			for (int row = 2; row < porpList.get(0).length; row++) {
				String[] data = new String[columnNames.length];
				for (int col = 0; col < data.length; col++) {
					data[col] = porpList.get(col)[row];
				}
				table.getDatas().add(data);
			}

		}
	}

	/**
	 * 解析横表式
	 * 
	 * @param table
	 * @param contentArr
	 */
	private void parseRowType(ETable table, String[] contentArr) {
		String headStr = contentArr[0].trim();
		String[] headerArr = splitString( headStr.substring("@".length()) );
		String[] columnNames = new String[headerArr.length ];
		System.arraycopy(headerArr, 0, columnNames, 0, columnNames.length);
		table.setColumnNames(columnNames);
		if (contentArr.length > 1) {
			for (int i = 1; i < contentArr.length; i++) {
				String linestr = contentArr[i].trim();
//				System.out.println("datastr=" + linestr);

				
				if(linestr.startsWith("%")){
					//类型 
					String[] lineArr = splitString(linestr.substring(1));
					String[] type = new String[lineArr.length ];
					System.arraycopy(lineArr, 0, type, 0, type.length);
					table.setTypes(type);
				}else if(linestr.startsWith("$")){
					//梁纲引导符  单位
					String[] lineArr = splitString(linestr.substring(1));
					String[] unites = new String[lineArr.length ];
					System.arraycopy(lineArr, 0, unites, 0, unites.length);
					table.setUnites(unites);
					
				}else if(linestr.startsWith(":")){
					//限值引导符 
					String[] lineArr = splitString(linestr.substring(1));
					String[] limitValues = new String[lineArr.length ];
					System.arraycopy(lineArr, 0, limitValues, 0, limitValues.length);
					table.setLimitValues(limitValues);
					
				}else if(linestr.startsWith("#")){
					//数据值
					
					String[] lineArr = splitString(linestr.substring(1));
					String[] data = new String[lineArr.length ];
					System.arraycopy(lineArr, 0, data, 0, data.length);
					table.getDatas().add(data);
				}
				
				
			}
		}
	}
	/**
	 * 分割字符串为数组
	 * @param str
	 * @return
	 */
	private String[] splitString(String str){
		return StringUtils.splitLineWithSpace(str);
	}

	
	public static void main(String[] args){
		
		try {
			DefaultEfileParse parse=new DefaultEfileParse();
			//String file="C:\\test\\E语言\\调度运营指标日报20121114.RB";
			//String file="C:\\test\\E语言\\调度运营指标月报201210.YB";
			//String file="C:\\test\\testhtml.txt";
			//String file="C:\\test\\横表式.txt";
			String file="E:\\集成\\D 调度相关\\E语言\\单列式.txt";
			List<ETable> list=parse.parseFile(new File(file));
			Debug.debug(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
