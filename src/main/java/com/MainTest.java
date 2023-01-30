package com;

import com.efile.ETable;
import com.efile.impl.DefaultEfileParse;
import com.util.BeanUtils;

import java.io.File;
import java.util.List;

public class MainTest {

	public static void main(String[] args) {

		try {
			DefaultEfileParse parse = new DefaultEfileParse();
			// String file="C:\\test\\E语言\\调度运营指标日报20121114.RB";
			// String file="C:\\test\\E语言\\调度运营指标月报201210.YB";
			// String file="C:\\test\\testhtml.txt";
			// String file="C:\\test\\横表式.txt";
//			String file = "J:\\IdeaP\\elanguage\\data\\单列式.txt";
			//E文件路径
			String file = "data/LP_K8000_STAdfgYDB_20191105_113002.DT";
			//解析E文件
			List<ETable> list = parse.parseFile(new File(file));
			//解析为对象
			List<StandbyDbInfo> ls = BeanUtils.parseBean(list.get(0),StandbyDbInfo.class);
			System.out.println(ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
