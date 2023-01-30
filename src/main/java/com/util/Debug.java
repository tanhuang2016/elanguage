package com.util;

import java.io.*;
import java.util.*;
/**
 * @author 王正权
 * 973598066@qq.com
 */
public class Debug {

	public static void debug(Object o){
		debug(o,System.out);
	}
	public static void debug(Object o,PrintStream out) {

		if (o instanceof String) {
			out.print(o);
		} else if (o instanceof Object[]) {

			Object[] arr = (Object[]) o;
			if (arr != null && arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					if (i > 0) {
						System.out.print(",");
					}
					debug(arr[i],out);
				}
				out.println();
			}

		}else if (o instanceof List){
			List list=(List)o;
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					debug(list.get(i),out);
				}
				
			}
		}
		else{
			debug(o.toString());
		}

	}
}
