package com.edom.parser;

import com.edom.dom.StandardAttributes;
import com.edom.util.CharChunk;
import org.xml.sax.*;

import java.io.*;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class EdomXMLReader implements    org.xml.sax.XMLReader{

	//存储当前读取的字符
	private char chr=0;
	
	private ContentHandler contentHandler;
	
	private Reader characterStream;
	
	
	private int pos=0;
	private int lastValid=0;
	
	private int previousPos=0;
	private int previousLastValid=0;
	
	 

	/**
	 * 当前缓冲区
	 */
	private char[] buf=null;
	/**
	 * 存储前一个缓冲区 
	 */
	private char[] previousBuf=null;
	/**
	 * 暂存缓冲区
	 */
	CharChunk tempBuf=null;
	
	/**
	 * 读入完成
	 */
	private boolean docReadEnd=false;
	
	public EdomXMLReader(){
		this(1024*8);
	}
	
	public EdomXMLReader(int bufSize){
		if(bufSize<0){
			bufSize=1024*8;
		}
		buf=new char[bufSize];
		previousBuf=new char[bufSize];
		tempBuf=new CharChunk(bufSize);
	}
	public ContentHandler getContentHandler() {
		return contentHandler;
	}

	public DTDHandler getDTDHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public EntityResolver getEntityResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorHandler getErrorHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getFeature(String name) throws SAXNotRecognizedException,
			SAXNotSupportedException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getProperty(String name) throws SAXNotRecognizedException,
			SAXNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(InputSource input)throws IOException{
		characterStream=input.getCharacterStream();
		if(characterStream==null){
			InputStream byteStream=input.getByteStream();
			if(byteStream!=null){
				String endoding="GBK";
				if(input.getEncoding()!=null){
					endoding=input.getEncoding();
				}
				System.out.println("endoding="+endoding);
				characterStream=new   InputStreamReader(byteStream,endoding);
				input.setCharacterStream(characterStream);
			}
			
		}
	}
	public void parse(InputSource input)throws IOException, SAXException{
		init(input);
		
		startDocument();
		
		skippingBlankLines();
		
		chr=this.readNext();
		while(!docReadEnd){
			if(chr=='/'){
				chr=this.readNext();
				if(chr=='/'){
					
					tempBuf.recycle();
					while(chr!='\n'){
						chr=this.readNext();
						tempBuf.append(chr);
					}
					tempBuf.pop();
					String annotation=tempBuf.toStringInternal();
					System.out.println("注释=="+annotation);
				}
			}
			else if(chr=='<'){
				chr=this.readNext();
				if(chr=='!'){
					//解析文件头
					parseHeader();
					
				}
				else if(chr=='/'){
					//一个节点结束
					parseEndElement();
					
				}
				else {
					//解析element
					
					parseStartElement();
					
				}
			}
			chr=this.readNext();
			
			
		}
		
		endDocument();
		
	}
	/**
	 * 解析头
	 */
	public void parseHeader()throws IOException, SAXException{
		chr=this.readNext();
		tempBuf.recycle();
		tempBuf.append(chr);
		
		 
		while(chr!='!' && !docReadEnd){
			
		
			chr=this.readNext();
			tempBuf.append(chr);
			
		}
		if(docReadEnd&&chr!='!'){
			throw new SAXException("<! 未正确的结束");
		}
		chr=this.readNext();
		if(chr!='>'){
			throw new SAXException("<! 未正确的结束");
		}
		tempBuf.pop();
		String headerStr=tempBuf.toStringInternal();
		//System.out.println("headerStr="+headerStr);
	}
	/**
	 * 解析开始element
	 * @throws IOException
	 * @throws SAXException
	 */
	public void parseStartElement() throws IOException, SAXException {

		//解析tagName start
		
		tempBuf.recycle();
		while((!isSpace(chr))&& !docReadEnd){
			//右标签算结束 todo th
			if(chr=='>'){
//				chr=this.readNext();
				//System.out.println(chr);
				break;
			}else{
				tempBuf.append(chr);
				chr=this.readNext();
			}

		}
		String tagName=tempBuf.toStringInternal();
		
		//解析tagName end
		
		//解析attributes start
		tempBuf.recycle();
		while(chr!='/'&& chr!='>' &&!docReadEnd){
			
			tempBuf.append(chr);
			chr=this.readNext();
			
		}
		
		String elmentTagContent=tempBuf.toStringInternal();

		StandardAttributes attrs=new StandardAttributes();
		//修改判断属性长度 todo th
		if(attrs.getLength()>0){
			parseAttributes(attrs, elmentTagContent, 0);
		}

		//解析attributes end
		this.startElement(null, tagName, tagName, attrs);
		
		if(chr=='/'){
			chr=this.readNext();
			if(chr=='>'){
				//一个节点结束
				this.endElement(null, tagName, tagName);
				return;
			}
			else{
				throw new SAXException(tagName+"未正确的结束 ！");
			}
		}
		else if(chr=='>'){
			
			chr=this.readNext();
			if(isSpace(chr)){
				skippingBlankLines();
				chr=this.readNext();
			}
			
			if(chr=='<'){
			
				chr=this.readNext();
				if(chr=='/'){
					//body text为空
					parseEndElement();
				}else{
					
					//解析下一个Element 
					parseStartElement();
				}
				
				
			}
			else{
				//解析body
				tempBuf.recycle();
				
				while( chr!='<'&&!docReadEnd){
					
					tempBuf.append(chr);
					chr=this.readNext();
					 
				}
				if(chr=='<'){
					
					
					chr=this.readNext();
					if(chr=='/'){
						this.characters(tempBuf.getChars(), tempBuf.getStart(), tempBuf.getLength());
						parseEndElement();
					}
					else{
						//To do
						/**
						 * 暂时不支持嵌套解析例如
						 * 
						 */
					}
					
				}
				
				 
				//this.readPrevious();
				
				
			}
			 
			
		}
		else{
			throw new SAXException(tagName+"未正确的开始 ！");
		}
		
	}
	/**
	 * 解析结束节点
	 * @throws IOException
	 * @throws SAXException
	 */
	public void parseEndElement()throws IOException, SAXException{
		tempBuf.recycle();
		chr=this.readNext();
		tempBuf.append(chr);
		while( chr!='>'&&!docReadEnd){
			
			chr=this.readNext();
			tempBuf.append(chr);
			 
		}
		if(chr=='>'){
			tempBuf.pop();
			String endTagName=tempBuf.toStringInternal();
			this.endElement(null, endTagName, endTagName);
		}else{
			throw new SAXException("未正确的结束 ！");
		}
	}
	
	private void startDocument() throws SAXException{
		//System.out.println("---start document---");
		if(contentHandler!=null)
			contentHandler.startDocument();
		
	}
	
	public void endDocument() throws SAXException{
		//System.out.println("---end document---");
		if(contentHandler!=null)
			contentHandler.endDocument();
	}
	
	public void startElement (String uri, String localName, String qName, Attributes atts)throws SAXException{
		//System.out.println("--start Element:"+localName);
		for(int i=0;i<atts.getLength();i++){
			//System.out.println("attr:"+atts.getLocalName(i)+"="+atts.getValue(i));
		}
		if(contentHandler!=null)
			contentHandler.startElement(uri, localName, qName, atts);
	}
	
	public void characters(char buffer[], int start, int length) throws SAXException {
		//System.out.println("content==\n"+new String(buffer,start,length));
		
		if(contentHandler!=null)
			contentHandler.characters(buffer, start, length);
		 
	}
	
	public void endElement(String uri, String localName,  String qName)throws SAXException{
		//System.out.println("--end element:"+localName);
		if(contentHandler!=null)
			contentHandler.endElement(uri, localName, qName);
	}

	public void parse(String systemId) throws IOException, SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setContentHandler(ContentHandler handler) {
		this.contentHandler=handler;
		
	}

	public void setDTDHandler(DTDHandler handler) {
		// TODO Auto-generated method stub
		
	}

	public void setEntityResolver(EntityResolver resolver) {
		// TODO Auto-generated method stub
		
	}

	public void setErrorHandler(ErrorHandler handler) {
		// TODO Auto-generated method stub
		
	}

	public void setFeature(String name, boolean value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		// TODO Auto-generated method stub
		
	}

	public void setProperty(String name, Object value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		// TODO Auto-generated method stub
		
	}


	
	/**
	 * 跳过空格和换行
	 * @throws IOException 
	 * @throws Exception
	 */
	private void skippingBlankLines() throws IOException  {
		
		chr=this.readNext();
		//System.out.println((int)chr +"|"+chr+"|");
		while (isSpace(chr)) {

			chr=this.readNext();

		}

		chr=this.readPrevious();
	}

	private boolean isSpace(char chr){
		return (chr == '\r') || (chr == '\n')|| (chr == ' ') || (chr == '\t');
	}
	
	
	
	
	/**
	 *读取下一个字符
	 * @return
	 * @throws IOException 
	 */
	private char readNext() throws IOException {
		if(isCurrentReadPre){
			if(previousPos>=(previousLastValid)){
				isCurrentReadPre=false;
				pos=0;
				return buf[pos];
			}else{
				return previousBuf[previousPos++];
			}
		}else{
			if (pos >= (lastValid)) {
				if (!fill()){
					return (char)-1;
				}
					 
			}
			return buf[pos++];
		}
		
		 
	}
	/**
	 * 当前正在读取的是否是备份缓冲区
	 */
	private boolean isCurrentReadPre=false;
	/**
	 * 读取前一个字符
	 * @return
	 * @throws IOException
	 */
	private char readPrevious() throws IOException{
		if(!isCurrentReadPre){
			if((pos-1)<0){
				isCurrentReadPre=true;
				return previousBuf[--previousPos];
			}else{
				return buf[--pos];
			}
		}else{
			return previousBuf[--previousPos];
		}
	}
	 
	/**
	 * 把流中的数据读取到缓冲区中
	 * @return
	 * @throws IOException
	 */
	protected boolean fill() throws IOException {
		//备份当前buf
		
		System.arraycopy(buf, 0, previousBuf, 0, lastValid);
		previousPos=pos;
		previousLastValid=lastValid;
		
		int nRead = 0;
		pos = 0;
		lastValid = 0;
		nRead = characterStream.read(buf);
		if (nRead > 0) {
			lastValid = nRead;
			//System.out.println("file=="+new String(buf,0,nRead));
		}
		else{
			lastValid = nRead;
			docReadEnd=true;
		}

		return (nRead > 0);

	}
	
	/**
	 * 单双引号
	 */
	private final static char[] wrappers=new char[]{'\'','\"'}; 
	/**
	 * 解析attribute
	 * @param elmentTagContent
	 */
	public void parseAttributes(StandardAttributes attrs,String elmentTagContent,int tagContentPos) {
		//标示标签头内容开始解析的起始位置
		int tagContentStart;
		//解析结束
		boolean tagContentEnd=false;
		
		char chr='0';
		//
		char wraper='\"';
		//
		boolean hasWraper=false;
		tagContentStart=tagContentPos;
		//找到“=”
		boolean findEqual=false;
		while(!findEqual&&!tagContentEnd){
			chr=elmentTagContent.charAt(tagContentPos++);
			if(chr=='='){
				findEqual=true;
			}
			if(chr=='>'||tagContentPos>=elmentTagContent.length()){
				tagContentEnd=true;
			}
		}
		if(!findEqual){
			return;
		}
		//获取属性名
		String attrName=elmentTagContent.substring(tagContentStart,tagContentPos-1).trim();
		//System.out.println("attrname="+attrName);
		
		/**
		 * 找到第一个“\‘”的位置
		 */
		boolean attrValueStart=false;
		while(!attrValueStart&&!tagContentEnd){
			chr=elmentTagContent.charAt(tagContentPos++);
			if(isWrapper(chr)){
				wraper=chr;
				hasWraper=true;
				attrValueStart=true;
			}
			if(chr=='>'||tagContentPos>=elmentTagContent.length()){
				tagContentEnd=true;
			}
		}
		tagContentStart=tagContentPos;
		/**
		 * 找到第二个“\‘”的位置
		 */
		boolean attrValueEnd=false;
		while(!attrValueEnd&&!tagContentEnd){
			chr=elmentTagContent.charAt(tagContentPos++);
			if(chr==wraper){
				attrValueEnd=true;
			}
			if(chr=='>'||tagContentPos>=elmentTagContent.length()){
				tagContentEnd=true;
			}
		}
		//获取属性内容
		String attrValue=elmentTagContent.substring(tagContentStart,tagContentPos-1).trim();
		//System.out.println("attrValue="+attrValue);
		
		attrs.put(attrName, attrValue);
		if(!tagContentEnd){
			//解析下一个属性
			parseAttributes(  attrs,elmentTagContent,  tagContentPos);
		}
		 
		
		 
	}
	
	/**
	 * 是否是引号
	 * @param chr
	 * @return
	 */
	private static boolean isWrapper(char chr){
		for(char c:wrappers){
			if(c==chr){
				return true;
			}
		}
		return false;
	}
	
	
	
	public static void main(String[] args){
	
		 try {
	            
			// String files="C:\\aa.txt";
			 String files="C:\\test\\testhtml.txt";
	         //String files="C:\\test\\单列式.txt";
			 File file=new File(files);
			 	
	            InputSource source = new InputSource(new FileInputStream(file));
	            source.setEncoding("GBK");
	            String path = file.getAbsolutePath();

	            if (path != null) {
	                // Code taken from Ant FileUtils
	                StringBuffer sb = new StringBuffer("file://");

	                // add an extra slash for filesystems with drive-specifiers
	                if (!path.startsWith(File.separator)) {
	                    sb.append("/");
	                }

	                path = path.replace('\\', '/');
	                sb.append(path);

	                source.setSystemId(sb.toString());
	            }

	            EdomXMLReader parse=new EdomXMLReader();
	            parse.parse(source);
	           
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	}
	
	
	 

	
}
