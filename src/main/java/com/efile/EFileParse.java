package com.efile;

import java.io.File;
import java.util.List;

public interface EFileParse {

	
	/**
	 * 解析E语言文件总入口
	 * 
	 * @param file
	 * @return
	 * @throws Exception List<ETable>
	 * @author 王正权
	 * @Date Dec 11, 2012
	 */
	public List<ETable> parseFile(File file)  throws  Exception;
		
	
}
