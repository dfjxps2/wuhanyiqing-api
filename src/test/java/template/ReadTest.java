package template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import io.dfjinxin.modules.outsider.entity.DetainedPersonInfoEntity;
import io.dfjinxin.modules.outsider.excel.DataTableInde;
import io.dfjinxin.modules.outsider.excel.Reader;
import io.dfjinxin.modules.outsider.excel.model.ECell;
import io.dfjinxin.modules.outsider.excel.model.ERow;
import io.dfjinxin.modules.outsider.excel.model.ESheet;
import io.dfjinxin.modules.outsider.excel.templates.SimpleReaderTemplate;

public class ReadTest {
    public static void main(final String[] args) throws IOException {
        final Reader reader = SimpleReaderTemplate.newInstance();
        String[] header = { "序号","姓名","电话","身份证号","报告日期","区县","滞留人员类型","当地居住地址","诉求类型","安置方式","目的城市","详情","备注" };//text2.xlsx
        String tableTile="区当日新增滞汉外地人明细反馈表";
        final InputStream is = new FileInputStream(new File("D:\\MyData\\WuHanReport\\武汉外地人滞留明细_江汉.xlsx"));//武汉外地人滞留明细.xlsx
        final List<ESheet> sheets = reader.read(is);
        //int dataTableIndex=0;
        for(final ESheet sheet: sheets){
        String name = sheet.getName();
        System.out.println(name);
        DataTableInde tableIndex = getDataTableIndex(sheet);
    	System.out.println("dataTableIndex2:"+tableIndex.getColumnIndex());

            for(final ERow row: sheet.getRows()){
            	DetainedPersonInfoEntity e=new DetainedPersonInfoEntity();
                for(final ECell cell: row.getCells()){
                    //System.out.print(cell);
                	//System.out.println("cellIndex=>"+cell.getColumnIndex());
             /*   if(header[cell.getColumnIndex()].equals(cell.getValue())){
               		 System.out.print(cell.getColumnIndex()+"----"+cell.getValue());
                	}*/
                	if(cell.getValue()!=null&&!"".equals(cell.getValue())){
                		Object value = cell.getValue();
                		// System.out.print(/*cell.getColumnIndex()+*/"\",\""+cell.getValue());
                		// if(header[cell.getColumnIndex()-dataTableIndex2]){}
                		 if(cell.getRowIndex()>=tableIndex.getRowIndex()){
                			 int columIndex=cell.getColumnIndex()-tableIndex.getColumnIndex();
                			 if(!header[columIndex].equals(value)) {
                				 System.out.println("列："+columIndex+"  "+header[columIndex]+" ==>"+value+"       值类型==========>"+value.getClass());
                				if(columIndex==0) {
                					//e.setId(value.toString());
                				}else if(columIndex==1) {
                					e.setDetainedName(value.toString());
                				}else if(columIndex==2) {
                					e.setTelephone(value.toString());
                				}else if(columIndex==3) {
                					e.setCardNumber(value.toString());
                				}else if(columIndex==4) {
                					e.setSubmitDate((Date)value);
                				}else if(columIndex==5) {
                					e.setAreaCd(value.toString());
                				}else if(columIndex==6) {
                					e.setDetainedPersonTypeCd(value.toString());
                				}else if(columIndex==7) {
                					e.setAddress(value.toString());
                				}/*else if(columIndex==8) {
                					e.setAppealTypeCd(value.toString());
                				}*/else if(columIndex==9) {
                					e.setResetMode(value.toString());
                				}else if(columIndex==10) {
                					e.setDestCity(value.toString());
                				}/*else if(columIndex==11) {
                					e.setDetainedInfo(value.toString());
                				}*/else if(columIndex==12) {
                					e.setBz(value.toString());
                				}


                			 }

                		 }

                	}

                }
            }
        }
    }
    public static DataTableInde getDataTableIndex(ESheet sheet){
    	  int dataTableIndex=0;
    	  DataTableInde index=new DataTableInde();
    	  String tableTile="区当日新增滞汉外地人明细反馈表";
    	     //String[] header = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };//text2.xlsx
    	     for(final ERow row: sheet.getRows()){
                 for(final ECell cell: row.getCells()){
                 	if(cell.getValue()!=null&&!"".equals(cell.getValue())){
                 		Object value = cell.getValue();
                 		if(value instanceof String){
                 			String valueStr=(String) value;
                 			if(valueStr.contains(tableTile)){
                 				dataTableIndex=cell.getColumnIndex();
                 				index.setColumnIndex(cell.getColumnIndex());
                 				index.setRowIndex(cell.getRowIndex()+1);
                 			}
                 		}
                 	}

                 }
             }
    	     return index;
    }
}
