
package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * <p>Title: // TODO</p>
 * <p>Description: // TODO</p>
 * @author 王正权
 */

public class EfileUtils {
	/**
	 * 打开文件，返回String格式的文件内容
	 * @param path 文件路径
	 * @throws IOException
	 */
	public String OpenFile(String path) throws Exception
	{
		//判断文件格式是否正确
		if(this.checkFile(path)!=1){
			throw new  Exception("文件格式不正确");
		}
		
		//将文件转换为UTF-8格式
		try{
			EfileUtils.transferFile(path);
		}catch(IOException e){
			throw new  Exception( "文件转换为UTF-8格式中出错");
		}
		//解析文件为String类型
		String content_temp = null;
		try {
			content_temp = changeFileToString(path);
		} catch (IOException e) {
			throw new  Exception( "文件内容转换为String中出错");
		}
		//去除文件头信息
		String fileStr = this.removeHeadFormat(content_temp);
 		return fileStr;
	}


	private String changeFileToString(String path)
			throws Exception {
		BufferedReader read = null;
		StringBuilder  content_temp = null;
		InputStreamReader sr =null;
		try
		{
			content_temp = new StringBuilder();
			sr = new InputStreamReader(new FileInputStream(new File(path)),"UTF-8");
			read = new BufferedReader(sr);
			String temp = "";
			int i = 0;
		//将文件解析为字符串格式
			while ((temp = read.readLine()) != null)
			{
				if (temp.contains("\\") || temp.contains("//"))
				{
					content_temp.append(temp.replaceAll("\\\\", "").replaceAll("////", "") + "\n");//增加解析效率
				}
				else
				{
					content_temp.append(temp.trim() + "\n");//增加解析效率
				}
			}
		}
		catch (Exception e)
		{
			throw new Exception( "解析文件为String时出错");
		}
		finally
		{
			if(null != read)
					read.close();
			if(null != sr)
					sr.close();
		}
		return content_temp.toString();
	}
	
	
	/**
	 * 读取字符串并去掉模块表头；如：<!System=SGTMS type='缺陷影响业务情况' Version=1.0 Code=UTF-8 Data=1.0!>
	 */
	public String removeHeadFormat(String fileStr){
		StringBuffer eBuff = new StringBuffer("");
		while(fileStr.contains("<!") && fileStr.contains("!>")){
			int start = fileStr.indexOf("<!");
			int end = fileStr.indexOf("!>");
			if(start>0){
				int headendpos = start-1;
				eBuff.append(fileStr.substring(0, headendpos));
			}
			eBuff.append(fileStr.substring(end+2));
			fileStr = eBuff.toString().trim();
			eBuff = new StringBuffer("");	
		}
		return fileStr;
	}
	
	
	/**
	 * 判断源文件编码是否为utf-8,不是的话将源文件编码转换成utf-8
	 * @param srcFileName 文件路径+文件名
	 * @throws IOException
	 */
	public  static void transferFile(String srcFileName) throws IOException 
	{ 
		File file = new File(srcFileName);
		//获取源文件编码
		String charset = getCharset(file);
		if(null != charset && !charset.equals("UTF-8")){
			String line_separator = System.getProperty("line.separator"); 
			FileInputStream fis = new FileInputStream(srcFileName);
			StringBuffer content = new StringBuffer();
			DataInputStream in = new DataInputStream(fis);
			BufferedReader d = new BufferedReader(new InputStreamReader(in, charset));//"UTF-8"   
			String line = null;  while ((line = d.readLine()) != null) 
				content.append(line + line_separator); 
			d.close();
			in.close();
			fis.close();
			Writer ow = new OutputStreamWriter(new FileOutputStream(srcFileName), "utf-8"); 
			ow.write(content.toString());  ow.close();
		}
	}
	
	
	/**
	 * 根据文件得到该文件中文本内容的编码
	 * 
	 * @param file 要分析的文件
	 */
	public static  String getCharset(File file) {
	        String charset = "GBK"; // 默认编码
	        byte[] first3Bytes = new byte[3];
	        try {
	            boolean checked = false;
	           /* BufferedInputStream bis = new BufferedInputStream(
	                  new FileInputStream(file));*/
	            BufferedInputStream bis = new BufferedInputStream(
		                  new FileInputStream(file));
	            bis.mark(0);
	            int read = bis.read(first3Bytes, 0, 3);
	            if (read == -1)
	                return charset;
	            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
	                charset = "UTF-16LE";
	                checked = true;
	            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
	                == (byte) 0xFF) {
	                charset = "UTF-16BE";
	                checked = true;
	            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
	                    == (byte) 0xBB
	                    && first3Bytes[2] == (byte) 0xBF) {
	                charset = "UTF-8";
	                checked = true;
	            }
	            bis.reset();
	            if (!checked) {
	                int loc = 0;
	                while ((read = bis.read()) != -1) {
	                    loc++;
	                    if (read >= 0xF0)
	                        break;
	                    //单独出现BF以下的，也算是GBK
	                    if (0x80 <= read && read <= 0xBF)
	                        break;
	                    if (0xC0 <= read && read <= 0xDF) {
	                        read = bis.read();
	                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
	                            // (0x80 -
	                            // 0xBF),也可能在GB编码内
	                            continue;
	                        else
	                            break;
	                     // 也有可能出错，但是几率较小
	                    } else if (0xE0 <= read && read <= 0xEF) {
	                        read = bis.read();
	                        if (0x80 <= read && read <= 0xBF) {
	                            read = bis.read();
	                            if (0x80 <= read && read <= 0xBF) {
	                                charset = "UTF-8";
	                                break;
	                            } else
	                                break;
	                        } else
	                            break;
	                    }
	                }
	            //    System.out.println(loc + " " + Integer.toHexString(read));
	            }
	            bis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return charset;
	}	
	
	/**
	 * 判断文件是否符合解析条件
	 * 
	 * @param file 要分析的文件路径
	 * 该方法为空，后续如需判断文件正确性在此方法添加即可，返回1合格，返回0错误
	 */
	public int checkFile(String path) {
		
		
	        return 1;
	}	

	
	public static void main(String[] args){
		File file=new File("J:\\IdeaP\\elanguage\\data\\单列式.txt");
		String encode=EfileUtils.getCharset(file);
		System.out.println("encode="+encode);
	}
}
