package com.efile;

import java.util.*;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class ETable {

	/**
	 * 表名
	 */
	private String tableName;
	
	private String date;
	/**
	 * 列名
	 */
	private String columnNames[];
	/**
	 * 类型 
	 */
	private String types[];
	/**
	 * 单位 
	 */
	private String unites[];
	/**
	 * 限定值 
	 */
	private String limitValues[];
	
	
	private List<Object[]> datas=null;
	
	public ETable(){
		datas=new ArrayList<Object[]>();
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public List<Object[]> getDatas() {
		return datas;
	}
	public void setDatas(List<Object[]> datas) {
		this.datas = datas;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String[] getUnites() {
		return unites;
	}
	public void setUnites(String[] unites) {
		this.unites = unites;
	}
	public String[] getLimitValues() {
		return limitValues;
	}
	public void setLimitValues(String[] limitValues) {
		this.limitValues = limitValues;
	}
}
