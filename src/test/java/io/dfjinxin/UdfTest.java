//package io.dfjinxin;
//
//import io.dfjinxin.modules.udf.service.UdfService;
//import io.dfjinxin.modules.wash.utils.ExpressionUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UdfTest {
//	@Autowired
//	private UdfService udfService;
//
//	@Test
//	public void test(){
//		try {
//			String name = ExpressionUtils.washJudge(1);
//			String source = "\"" + "var func1 = function(name) " +
//					" { return input is name;};" + "\"";
//
//			udfService.readWriteUdf(name, source, "\"" + name + "\"");
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//
//	}
//
//}
