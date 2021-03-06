package template;

import org.apache.poi.ss.usermodel.CellStyle;

import io.dfjinxin.modules.outsider.excel.Config;
import io.dfjinxin.modules.outsider.excel.EasyTemplate;
import io.dfjinxin.modules.outsider.excel.Region;
import io.dfjinxin.modules.outsider.excel.TemplateFactory;
import io.dfjinxin.modules.outsider.excel.model.EStyle;
import io.dfjinxin.modules.outsider.excel.templates.SimpleTemplate;
import io.dfjinxin.modules.outsider.excel.utils.StyleDecorate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Test1 {
	public static void main(String[] args) {
		final Test1Model header = new Test1Model(new Object[]{"周一","周二","周三","周四","周五","周六","周日"});
		final List<Test1Model> body = new ArrayList<Test1Model>();
		for(int i=0; i<6; i++){
			final Object[] data = new Object[7];
			data[1] = "String"+i;
			data[2] = new Object();
			data[3] = 10+i;
			data[4] = 20.5+i;
			data[5] = true;
			data[6] = new Date();
			data[0] = null;
			body.add(new Test1Model(data));
		}
		final EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, header, body);
		final Config config = t.getSheets().get(0).getSheetStyle();
		EStyle style = EStyle.newBorderInstance();
		style.setFontName("等线");
		StyleDecorate.decorateBgYellow(style);
		style.setFontSize(EStyle.FONT_SIZE_NORMAL);//FONT_SIZE_BIG
		style.setFontBold(false);
		style.setAlign(CellStyle.ALIGN_CENTER);
		config.setStyle(new Region(0,0,0,6),style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_MONEY_RMB);
		config.setStyle(new Region(1,6,4,4), style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_PERCENTAGE);
		config.setStyle(new Region(1,6,3,3),style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_DATE);
		config.setStyle(new Region(1,6,6,6), style);

		config.addRowHeight(0, 30f);
		config.addColumnWidth(2, Config.DEFAULT_WIDTH*3);
		config.addColumnWidth(6, Config.DEFAULT_WIDTH+3);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("D:\\MyData\\WuHanReport\\write_text2.xlsx");
			t.build(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
