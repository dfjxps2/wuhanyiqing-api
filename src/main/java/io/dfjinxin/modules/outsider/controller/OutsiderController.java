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
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * 下载Excel模板
	 * 
	 * @throws IOException
	 */
	@GetMapping("/excel/export")
	public void exportData(HttpServletResponse resp) throws IOException {
		String fileName = "导入用户模板";
		// 设置响应流文件进行下载
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		resp.setContentType("application/vnd.ms-excel");
		resp.setCharacterEncoding("utf8");
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		ServletOutputStream sos = resp.getOutputStream();
		List<DetainedPersonInfoEntity> list = personService.list();
		String[] header = { "序号", "姓名", "电话", "身份证号", "报告日期", "区县", "滞留人员类型", "当地居住地址", "诉求类型", "安置方式", "目的城市", "详情",
				"备注" };// text2.xlsx
		String tableTile = "（XX）区当日新增滞汉外地人明细反馈表";
		PersonEntity head = new PersonEntity(header);
		List<PersonEntity> body = new ArrayList<PersonEntity>();
		list.forEach(pp -> {
			Object[] data = new Object[13];
			data[1] = pp.getId();
			data[1] = pp.getDetainedName();
			data[2] = pp.getTelephone();
			data[3] = pp.getCardNumber();
			data[4] = pp.getSubmitDate();
			data[5] = pp.getAreaCd();
			data[6] = pp.getDetainedPersonTypeCd();
			data[7] = pp.getAddress();
			data[8] = pp.getAppealTypeCd();
			data[9] = pp.getResetMode();
			data[10] = pp.getDestCity();
			data[11] = pp.getDetainedInfo();
			data[12] = pp.getBz();
			body.add(new PersonEntity(data));
		});
		Pager pager = new MyPager(tableTile, head, body);
// EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, head, body);
		EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, pager);
		Config config = t.getSheets().get(0).getSheetStyle();
		EStyle style = EStyle.newBorderInstance();
		style.setFontName("等线");
		StyleDecorate.decorateBgYellow(style);
		style.setFontSize(EStyle.FONT_SIZE_NORMAL);// FONT_SIZE_BIG
		style.setFontBold(false);
		style.setAlign(CellStyle.ALIGN_CENTER);
		config.setStyle(new Region(0, 0, 0, 6), style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_MONEY_RMB);
		config.setStyle(new Region(1, 6, 4, 4), style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_PERCENTAGE);
		config.setStyle(new Region(1, 6, 3, 3), style);

		style = EStyle.newBorderInstance();
		style.setFormat(EStyle.FORMAT_DATE);
		config.setStyle(new Region(1, 6, 6, 6), style);

		config.addRowHeight(0, 30f);
		config.addColumnWidth(2, Config.DEFAULT_WIDTH * 3);
		config.addColumnWidth(6, Config.DEFAULT_WIDTH + 3);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			t.build(os);
			byte[] bytes = os.toByteArray();
			sos.write(bytes);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@ApiOperation("导入：同步读，单sheet")
	@PostMapping("/excel/import")
	@ResponseBody
	public String importData(@RequestParam("file") MultipartFile file) {
		Reader reader = SimpleReaderTemplate.newInstance();
		String[] header = { "序号", "姓名", "电话", "身份证号", "报告日期", "区县", "滞留人员类型", "当地居住地址", "诉求类型", "安置方式", "目的城市", "详情",
				"备注" };// text2.xlsx
		String tableTile = "（XX）区当日新增滞汉外地人明细反馈表";
		// 将文件传到C盘的Excel文件夹下
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
										e.setId(value.toString());
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
										e.setDetainedPersonTypeCd(value.toString());
									} else if (columIndex == 7) {
										e.setAddress(value.toString());
									} else if (columIndex == 8) {
										e.setAppealTypeCd(value.toString());
									} else if (columIndex == 9) {
										e.setResetMode(value.toString());
									} else if (columIndex == 10) {
										e.setDestCity(value.toString());
									} else if (columIndex == 11) {
										e.setDetainedInfo(value.toString());
									} else if (columIndex == 12) {
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
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> DownloadFile(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 接受的是UTF-8
		req.setCharacterEncoding("utf-8");
		// 获取文件名
		String filename = "result.xlsx";
		HttpHeaders headers = null;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		List<DetainedPersonInfoEntity> list = personService.list();
		String[] header = { "序号", "姓名", "电话", "身份证号", "报告日期", "区县", "滞留人员类型", "当地居住地址", "诉求类型", "安置方式", "目的城市", "详情",
				"备注" };// text2.xlsx
		String tableTile = "（XX）区当日新增滞汉外地人明细反馈表";
		PersonEntity head = new PersonEntity(header);
		List<PersonEntity> body = new ArrayList<PersonEntity>();
		list.forEach(pp -> {
			Object[] data = new Object[13];
			data[0] = pp.getId();
			data[1] = pp.getDetainedName();
			data[2] = pp.getTelephone();
			data[3] = pp.getCardNumber();
			data[4] = pp.getSubmitDate();
			data[5] = pp.getAreaCd();
			data[6] = pp.getDetainedPersonTypeCd();
			data[7] = pp.getAddress();
			data[8] = pp.getAppealTypeCd();
			data[9] = pp.getResetMode();
			data[10] = pp.getDestCity();
			data[11] = pp.getDetainedInfo();
			data[12] = pp.getBz();
			body.add(new PersonEntity(data));
		});
		Pager pager = new MyPager(tableTile, head, body);
		EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, pager);
		Config config = t.getConfig(0);
		config.setDefaultWidth(Config.DEFAULT_WIDTH + 5);
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

//@ApiOperation(value = "下载信息", httpMethod = "GET", notes = "下载符合条件的Excel",produces =MediaType.APPLICATION_OCTET_STREAM_VALUE)
//@RequestMapping(value = "/download-backup1", method = RequestMethod.GET)
//@ResponseBody
	public ResponseEntity<byte[]> DownloadFile1(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 接受的是UTF-8
		req.setCharacterEncoding("utf-8");
		// 获取项目根目录
		// String path="D:\\MyData\\WuHanReport\\山石云主机SSLvpn登录手册20200217.pdf";
		String path = "D:\\MyData\\WuHanReport\\write_text2.xlsx";
		// write_text2.xlsx
		// 获取文件名
//   String filename="反馈报告.pdf";
		String filename = "write_text2.xlsx";
		File file = null;
		HttpHeaders headers = null;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String[] header = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		Test1Model h = new Test1Model(header);
		List<Test1Model> list = new ArrayList<Test1Model>();
		for (int i = 0; i < 6; i++) {
			Object[] data = new Object[7];
			data[1] = "String" + i;
			data[2] = new Object();
			data[3] = 10 + i;
			data[4] = 20.5 + i;
			data[5] = true;
			data[6] = new Date();
			data[0] = null;
			list.add(new Test1Model(data));
		}

		Pager pager = new MyPager("测试", h, list);

		EasyTemplate t = TemplateFactory.createTemplate(SimpleTemplate.class, pager);

		Config config = t.getConfig(0);
		config.setDefaultWidth(Config.DEFAULT_WIDTH + 5);
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

//@ApiOperation(value = "下载信息", httpMethod = "GET", notes = "下载符合条件的Excel",produces =MediaType.APPLICATION_OCTET_STREAM_VALUE)
//@RequestMapping(value="/downloadEntity",method = RequestMethod.GET)
//@ResponseBody
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取输出流对象
		// ServletOutputStream out = response.getOutputStream();
		// 设置内容类型
		// response.setContentType("multipart/form-data");
		// 编码
		// response.setCharacterEncoding("utf-8");
		// 随机文件名
		String fileName = new String("test000001".getBytes(), "UTF-8");
		// 下载文件名
		// response.setHeader("Content-disposition",
		// "attachment;filename="+fileName+".xlsx");
		String filePath = "D:\\MyData\\WuHanReport\\write_text2.xlsx";
		byte[] fileToBytes = fileToBytes(filePath);
		// out.write(fileToBytes);
		// out.flush();
		// response.getWriter().write(fileToBytes);
		String title = "test333333333333";
		// response.getOutputStream().write(fileToBytes);
		// ByteArrayOutputStream os = new ByteArrayOutputStream();
		/*
		 * try { workBook.write(os); } catch (IOException e) { e.printStackTrace(); }
		 */
		title = title + ".xlsx";
//	if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//		//title = new String(title.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
//		title = "=?UTF-8?B?" + (new String(Base64Utils.encodeToString(title.getBytes("UTF-8")))) + "?=";
//	} else {
//		title = URLEncoder.encode(title, "UTF-8");
//	}
		title = URLEncoder.encode(title, "UTF-8");
		// byte[] bytes = os.toByteArray();
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setContentLength(fileToBytes.length);
		response.setHeader("Content-Disposition", "attachment;filename=" + title);
		response.getOutputStream().write(fileToBytes);
		response.flushBuffer();
		return JSONObject.toJSONString("正在下载文件");
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

	@ApiOperation(value = "Excel数据导出")
	@GetMapping("/ExcelData/download")
	public void download(String id, HttpServletResponse response) {
		String path = "D:\\MyData\\WuHanReport\\text2.xlsx";
		String name = "text22222222222222.xlsx";
		InputStream inputStream = null;
		try {
			// 将name 按照utf-8编码方式 拆成bytes数组, 再通过ISO8859-1方式 生成新的字符串
			String filename = new String(name.getBytes("UTF-8"), "ISO8859-1");
			// 设置以流的方式传送
			response.setContentType("application/octet-stream");
			// 设置响应头
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			File file = new File(path);
			FileInputStream fileInputStream = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			int len = 0;
			byte[] by = new byte[1024 * 10];
			while ((len = fileInputStream.read(by)) > 0) {
				out.write(by, 0, len);
			}
			out.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据表的行列索引
	 * 
	 * @param sheet
	 * @return
	 */
	private DataTableInde getDataTableIndex(ESheet sheet) {
		int dataTableIndex = 0;
		DataTableInde index = new DataTableInde();
		String tableTile = "区当日新增滞汉外地人明细反馈表";
		// String[] header = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };//text2.xlsx
		for (final ERow row : sheet.getRows()) {
			for (final ECell cell : row.getCells()) {
				if (cell.getValue() != null && !"".equals(cell.getValue())) {
					Object value = cell.getValue();
					if (value instanceof String) {
						String valueStr = (String) value;
						if (valueStr.contains(tableTile)) {
							dataTableIndex = cell.getColumnIndex();
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
