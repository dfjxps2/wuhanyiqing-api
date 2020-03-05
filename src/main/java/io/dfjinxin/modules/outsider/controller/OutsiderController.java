package io.dfjinxin.modules.outsider.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.outsider.entity.Constant;
import io.dfjinxin.modules.outsider.entity.DetainedPersonInfoEntity;
import io.dfjinxin.modules.outsider.entity.PersonEntity;
import io.dfjinxin.modules.outsider.entity.Test1Model;
import io.dfjinxin.modules.outsider.excel.Config;
import io.dfjinxin.modules.outsider.excel.DataTableInde;
import io.dfjinxin.modules.outsider.excel.EasyTemplate;
import io.dfjinxin.modules.outsider.excel.Pager;
import io.dfjinxin.modules.outsider.excel.Reader;
import io.dfjinxin.modules.outsider.excel.Region;
import io.dfjinxin.modules.outsider.excel.TemplateFactory;
import io.dfjinxin.modules.outsider.excel.model.ECell;
import io.dfjinxin.modules.outsider.excel.model.ERow;
import io.dfjinxin.modules.outsider.excel.model.ESheet;
import io.dfjinxin.modules.outsider.excel.model.EStyle;
import io.dfjinxin.modules.outsider.excel.templates.SimpleReaderTemplate;
import io.dfjinxin.modules.outsider.excel.templates.SimpleTemplate;
import io.dfjinxin.modules.outsider.excel.utils.StyleDecorate;
import io.dfjinxin.modules.outsider.service.DetainedPersonInfoService;
import io.swagger.annotations.ApiOperation;
import io.dfjinxin.modules.outsider.entity.MyPager;

/**
 * 滞汉外地人明细 controller
 *
 * @author zhujiazhou
 *
 */
@RestController
@RequestMapping("/outsider")
@Api(tags = "导出&导入")
public class OutsiderController {

	@Autowired
	private DetainedPersonInfoService personService;

	/**
	 * 最大导入数量
	 */
	// @Value("${user.import.max}")
	// private Integer MAX_USER_IMPORT;
	public static final int MAX_USER_IMPORT = 1000;
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 保存用户
	 */
	@PostMapping("/saveDetainedPerson")
	public R save(@RequestBody DetainedPersonInfoEntity person) {
		personService.save(person);
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@PostMapping("/updateDetainedPerson")
	public R update(@RequestBody DetainedPersonInfoEntity person) {
		personService.saveOrUpdate(person);
		return R.ok();
	}

	

	@ApiOperation("导入：同步读，单sheet")
	@PostMapping("/excel/import")
	@ResponseBody
	public String importData(@RequestParam("file") MultipartFile file) {
		Reader reader = SimpleReaderTemplate.newInstance();
		String[] header = { "序号", "姓名", "电话", "身份证号", "报告日期", "行政县", "滞留人员类型", "当地居住地址","户籍地","安置方式", "目的城市", "救助金额","救助开始日期","救助结束日期","经办人","负责人",
				"备注" };//"诉求类型"和“详情”废弃
		try {
			InputStream is = file.getInputStream();
			List<ESheet> sheets = reader.read(is);
			List<DetainedPersonInfoEntity> entityList = new ArrayList<DetainedPersonInfoEntity>();
			// int dataTableIndex=0;
			for (final ESheet sheet : sheets) {
				DataTableInde tableIndex = getDataTableIndex(sheet);
				// System.out.println("dataTableIndex2:"+tableIndex.getColumnIndex());
				for (final ERow row : sheet.getRows()) {
					DetainedPersonInfoEntity e = new DetainedPersonInfoEntity();
					for (final ECell cell : row.getCells()) {
						if (cell.getValue() != null && !"".equals(cell.getValue())) {
							Object value = cell.getValue();
							if (cell.getRowIndex() >= tableIndex.getRowIndex()) {
								int columIndex = cell.getColumnIndex() - tableIndex.getColumnIndex();
								if (!header[columIndex].equals(value)) {
									if (columIndex == 0) {
									//	String idStr = value.toString();
										//if(idStr==null||"".equals(idStr)) {
										//String	idStr = UUID.randomUUID().toString();
									        //去掉“-”符号 
										//	idStr=idStr.replaceAll("-", "");
										//}
									//	e.setId(idStr);
									} else if (columIndex == 1) {
										e.setDetainedName(value.toString());
									} else if (columIndex == 2) {
										e.setTelephone(value.toString());
									} else if (columIndex == 3) {
										e.setCardNumber(value.toString());
									} else if (columIndex == 4) {
										e.setSubmitDate((Date) value);
									} else if (columIndex == 5) {
										e.setAreaCd(value.toString());
									} else if (columIndex == 6) {
										String vStr = Constant.detainedPersonTypeCdValueOfKey.get(value.toString());
										if(vStr==null||"".equals(vStr)) {
											vStr ="7";
										}
										e.setDetainedPersonTypeCd(vStr);/////////////
									} else if (columIndex == 7) {
										e.setAddress(value.toString());
									} //else if (columIndex == 8) {
										//String vStr = Constant.appealTypeCdValueOfKey.get(value.toString());
										//if(vStr==null||"".equals(vStr)) {
										//	vStr ="6";
									//	}
									//	e.setAppealTypeCd(vStr);/////////////////
									//}
									else if (columIndex == 8) {///////////////////////////////////////////////////////////////添加 户籍地：
										//e.setResetMode(value.toString());
										e.setPlaceAreaCd(value.toString());
									} 
									else if (columIndex == 9) {
										e.setResetMode(value.toString());
									}else if (columIndex == 10) {
										e.setDestCity(value.toString());
									} else if (columIndex == 11) {
								//		e.setDetainedInfo(value.toString());
										e.setSalveAmount((Double)value);//救助金额
									} else if (columIndex == 12) {
										//e.setBz(value.toString());
										e.setSalveDateStat((Date)value);//救助开始日期
									}else if (columIndex == 13) {
//										e.setBz(value.toString());
										e.setSalveDateEnd((Date)value);//救助结束日期
									}else if (columIndex == 14) {
										//	e.setBz(value.toString());
										e.setSubmitUserId(value.toString());//经办人
									}else if (columIndex == 15) {
										e.setOrderName(value.toString());//负责人
									}else if (columIndex == 16) {
										e.setBz(value.toString());
									}
									entityList.add(e);
								}
							}
						}
					}
				}
				personService.saveBatch(entityList, entityList.size());
			}
			// 获取文件名称
			// 文件存储路径
		} catch (IOException e) {
			return JSONObject.toJSONString("上传失败");
		}

		return JSONObject.toJSONString("上传成功");
	}

	@ApiOperation(value = "滞汉外地人信息下载", httpMethod = "GET", notes = "滞汉外地人信息下载", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@RequestMapping(value = "/excel/export", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportData(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 接受的是UTF-8
		req.setCharacterEncoding("utf-8");
		// 获取文件名
		String filename = "result.xlsx";
		HttpHeaders headers = null;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		List<DetainedPersonInfoEntity> list = personService.list();
		String[] header = { "序号", "姓名", "电话", "身份证号", "报告日期", "行政县", "滞留人员类型", "当地居住地址","户籍地","安置方式", "目的城市", "救助金额","救助开始日期","救助结束日期","经办人","负责人",
				"备注" };//  "诉求类型"和"详情" 字段废弃
		String tableTile = "滞汉外地人清单";
		PersonEntity head = new PersonEntity(header);
		List<PersonEntity> body = new ArrayList<PersonEntity>();
		list.forEach(pp -> {
			Object[] data = new Object[17];
			data[0] = pp.getId();
			data[1] = pp.getDetainedName();
			data[2] = pp.getTelephone();
			data[3] = pp.getCardNumber();
			data[4] =pp.getSubmitDate()==null?"":sdf.format(pp.getSubmitDate());
			data[5] = pp.getAreaCd();
			String dptStr = Constant.detainedPersonTypeCdKv.get(pp.getDetainedPersonTypeCd());
			if(dptStr==null||"".equals(dptStr)) {
				dptStr ="其它人员";
			}
			//data[6] = pp.getDetainedPersonTypeCd();
			data[6] =dptStr;
			data[7] = pp.getAddress();
			//String acStr = Constant.appealTypeCdKv.get(pp.getAppealTypeCd());
		//	if(acStr==null||"".equals(acStr)) {
			//String	acStr ="其他诉求";
			//}
			//data[8] = pp.getAppealTypeCd();
			data[8] =  pp.getPlaceAreaCd();
			data[9] =  pp.getResetMode();
			data[10] =pp.getDestCity();
			data[11] =pp.getSalveAmount();//救助金额 //"此字段废弃";//pp.getDetainedInfo();
			data[12] =pp.getSalveDateStat()==null?"":sdf.format(pp.getSalveDateStat());//救助开始日期
			data[13]=pp.getSalveDateEnd()==null?"":sdf.format(pp.getSalveDateEnd());//救助结束日期
			data[14]=pp.getSubmitUserId();//经办人：取提交用户姓名
			data[15]=pp.getOrderName();//负责人
			data[16]=pp.getBz();//备注
			body.add(new PersonEntity(data));
		});
		Pager pager = new MyPager(tableTile, head, body);
		EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, pager);
		Config config = t.getConfig(0);
		config.setDefaultWidth(Config.DEFAULT_WIDTH *5);
		config.addRowHeight(0, 30f);
		config.addColumnWidth(0, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(1, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(2, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(3, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(4, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(5, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(6, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(7, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(8, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(9, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(10, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(11, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(12, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(3, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(14, Config.DEFAULT_WIDTH*2);
		config.addColumnWidth(15, Config.DEFAULT_WIDTH*2);
		try {
			System.out.println(filename);// myfiles
			// file =new File(path);
			// 请求头
			headers = new HttpHeaders();
			String fileName1 = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 解决文件名乱码
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", fileName1);
			// application/octet-stream二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			t.build(bOut);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(bOut.toByteArray(), headers, HttpStatus.OK);
	}

	public byte[] fileToBytes(String filePath) {
		byte[] buffer = null;
		File file = new File(filePath);
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		} finally {
			try {
				if (null != bos) {
					bos.close();
				}
			} catch (IOException ex) {
			} finally {
				try {
					if (null != fis) {
						fis.close();
					}
				} catch (IOException ex) {
				}
			}
		}
		return buffer;
	}

	

	/**
	 * 获取数据表的行列索引
	 *
	 * @param sheet
	 * @return
	 */
	private DataTableInde getDataTableIndex(ESheet sheet) {
		DataTableInde index = new DataTableInde();
		String tableTile = "滞汉外地人清单";
		for (final ERow row : sheet.getRows()) {
			for (final ECell cell : row.getCells()) {
				if (cell.getValue() != null && !"".equals(cell.getValue())) {
					Object value = cell.getValue();
					if (value instanceof String) {
						String valueStr = (String) value;
						if (valueStr.contains(tableTile)) {
							index.setColumnIndex(cell.getColumnIndex());
							index.setRowIndex(cell.getRowIndex() + 1);
						}
					}
				}

			}
		}
		return index;
	}
}
