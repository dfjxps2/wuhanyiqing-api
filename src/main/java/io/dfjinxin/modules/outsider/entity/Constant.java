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

public final static Map<String,String> zoneCdValueOfKey=new HashMap<String,String>();// value of key
public final static Map<String,String> zoneCdKv=new HashMap<String,String>();// value of key

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
	
	zoneCdKv.put("jh-yy", "江汉区");
	zoneCdKv.put("dhkf-zhy", "东湖新技术开发区");
	zoneCdKv.put("xz-txf", "新洲区");
	zoneCdKv.put("xd-dl", "蔡甸区");
	zoneCdKv.put("dhfj-zxh", "东湖生态旅游风景区");
	zoneCdKv.put("qs-wj", "青山区");
	zoneCdKv.put("ja-hql", "江岸区");
	zoneCdKv.put("hy-zsn", "汉阳区");
	zoneCdKv.put("jx-hzy", "江夏区");
	zoneCdKv.put("dxh-lif", "东西湖区");
	zoneCdKv.put("hn-lhc", "汉南区");
	zoneCdKv.put("hp-wjl", "黄陂区");
	zoneCdKv.put("hs-zyq", "洪山区");
	zoneCdKv.put("qk-zh", "硚口区");
	zoneCdKv.put("wc-zgh", "武昌区");
	
	zoneCdValueOfKey.put("江汉区","jh-yy");
	zoneCdValueOfKey.put("东湖新技术开发区","dhkf-zhy");
	zoneCdValueOfKey.put("新洲区","xz-txf");
	zoneCdValueOfKey.put("蔡甸区","xd-dl");
	zoneCdValueOfKey.put("东湖生态旅游风景区","dhfj-zxh");
	zoneCdValueOfKey.put("青山区","qs-wj");
	zoneCdValueOfKey.put("江岸区","ja-hql");
	zoneCdValueOfKey.put("汉阳区","hy-zsn");
	zoneCdValueOfKey.put("江夏区","jx-hzy" );
	zoneCdValueOfKey.put("东西湖区","dxh-lif");
	zoneCdValueOfKey.put("汉南区","hn-lhc");
	zoneCdValueOfKey.put("黄陂区","hp-wjl");
	zoneCdValueOfKey.put("洪山区","hs-zyq");
	zoneCdValueOfKey.put("硚口区","qk-zh");
	zoneCdValueOfKey.put( "武昌区","wc-zgh");
}
}
