package io.dfjinxin.modules.outsider.entity;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	/**
	 * 诉求类型:
	 * 1-需要离汉
2-生活困难
3-救助居住
4-求助就医买药
5-就业意向
6-其他诉求
	 */
public final static Map<String,String> appealTypeCdValueOfKey=new HashMap<String,String>();//value of key
public final static Map<String,String> appealTypeCdKv=new HashMap<String,String>();// KV
/**
 * 滞留人员类型:
 *  0-全部
1-学生
2-务工经商人员
3-旅游
4-投亲靠友
5-来汉就医
6-居无定所
7-其它人员
 */
public final static Map<String,String> detainedPersonTypeCdValueOfKey=new HashMap<String,String>();// value of key
public final static Map<String,String> detainedPersonTypeCdKv=new HashMap<String,String>();//kv

static {
	appealTypeCdValueOfKey.put("需要离汉", "1");
	appealTypeCdValueOfKey.put("生活困难", "2");
	appealTypeCdValueOfKey.put("救助居住", "3");
	appealTypeCdValueOfKey.put("求助就医买药", "4");
	appealTypeCdValueOfKey.put("就业意向", "5");
	appealTypeCdValueOfKey.put("其他诉求", "6");
	
	appealTypeCdKv.put("1","需要离汉" );
	appealTypeCdKv.put("2", "生活困难");
	appealTypeCdKv.put("3","救助居住");
	appealTypeCdKv.put("4","求助就医买药");
	appealTypeCdKv.put("5","就业意向");
	appealTypeCdKv.put("6","其他诉求");

	detainedPersonTypeCdValueOfKey.put("全部", "0");
	detainedPersonTypeCdValueOfKey.put("学生", "1");
	detainedPersonTypeCdValueOfKey.put("务工经商人员", "2");
	detainedPersonTypeCdValueOfKey.put("旅游", "3");
	detainedPersonTypeCdValueOfKey.put("投亲靠友", "4");
	detainedPersonTypeCdValueOfKey.put("来汉就医", "5");
	detainedPersonTypeCdValueOfKey.put("居无定所", "6");
	detainedPersonTypeCdValueOfKey.put("其它人员", "7");
	
	detainedPersonTypeCdKv.put("0","全部");
	detainedPersonTypeCdKv.put("1","学生");
	detainedPersonTypeCdKv.put("2","务工经商人员" );
	detainedPersonTypeCdKv.put("3","旅游");
	detainedPersonTypeCdKv.put("4","投亲靠友");
	detainedPersonTypeCdKv.put("5","来汉就医");
	detainedPersonTypeCdKv.put("6","居无定所");
	detainedPersonTypeCdKv.put("7","其它人员");
}
}
