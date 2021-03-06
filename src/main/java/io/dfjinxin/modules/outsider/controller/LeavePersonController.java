package io.dfjinxin.modules.outsider.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcraft.jsch.Buffer;

import io.dfjinxin.common.utils.ShiroUtils;
import io.dfjinxin.modules.outsider.entity.Constant;
import io.dfjinxin.modules.outsider.entity.LeavePerson;
import io.dfjinxin.modules.outsider.entity.LeavePersonEntity;
import io.dfjinxin.modules.outsider.entity.LogEntity;
import io.dfjinxin.modules.outsider.excel.Config;
import io.dfjinxin.modules.outsider.excel.DataTableInde;
import io.dfjinxin.modules.outsider.excel.EasyTemplate;
import io.dfjinxin.modules.outsider.excel.Pager;
import io.dfjinxin.modules.outsider.excel.Reader;
import io.dfjinxin.modules.outsider.excel.TemplateFactory;
import io.dfjinxin.modules.outsider.excel.model.ECell;
import io.dfjinxin.modules.outsider.excel.model.ERow;
import io.dfjinxin.modules.outsider.excel.model.ESheet;
import io.dfjinxin.modules.outsider.excel.templates.SimpleReaderTemplate;
import io.dfjinxin.modules.outsider.excel.templates.SimpleTemplate;
import io.dfjinxin.modules.outsider.service.LeavePersonService;
import io.dfjinxin.modules.outsider.service.LogService;
import io.dfjinxin.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.ApiOperation;
import io.dfjinxin.modules.outsider.entity.MyPager;

/**
 * 离开武汉的人 controller
 *
 * @author zhujiazhou2.0
 *
 */
@RestController
@RequestMapping("/leaveperson")
@Api(tags = "已离汉人员信息导出&导入")
public class LeavePersonController {

	@Autowired
	private LeavePersonService leavePersonService;
	
	@Autowired
	private LogService leaveLogService;

	@ApiOperation("已离汉人员信息导入")
	@PostMapping("/excel/import")
	@ResponseBody
	public String importData(@RequestParam("file") MultipartFile file,@RequestParam("username") String username) {
		Map<String,String>  resultMap=new HashMap<String,String>();
		StringBuffer buffer= new StringBuffer();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String errorStr="错误记录";
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		//String zoneCdStr = Constant.zoneCdKv.get(userEntity.getUsername());
		String zoneCdStr = Constant.zoneCdKv.get(username);
		Reader reader = SimpleReaderTemplate.newInstance();
		//									0			1				2					3				4						5
		String[] header = {"序号", "姓名", "电话", "证件类型", "身份证号", "离汉时间", 
				//6					7								8
				"户籍地", "滞汉居住方式", "滞汉居住地址",
				//		9				10
				"返回省份","返回市",
				//11				12		13
				"返程方式", "区", "创建时间"};
		try {
			InputStream is = file.getInputStream();
			List<ESheet> sheets = reader.read(is);
			List<LeavePerson> entityList = new ArrayList<LeavePerson>();
			for (final ESheet sheet : sheets) {
				DataTableInde tableIndex = getDataTableIndex(sheet);
				for (final ERow row : sheet.getRows()) {
					if(row.getRowIndex()>tableIndex.getRowIndex()){
						LeavePerson e =null;
						Object indexValue=null;
						buffer.delete(0,buffer.length()); 
					for (final ECell cell : row.getCells()) {
						//if (cell.getValue() != null && !"".equals(cell.getValue())) {
							Object value = cell.getValue();
							if (cell.getRowIndex() >= tableIndex.getRowIndex()) {
								int columIndex = cell.getColumnIndex() - tableIndex.getColumnIndex();
								ECell indexDataCell = row.getCell(tableIndex.getColumnIndex());
								indexValue=indexDataCell.getValue();
								if (!header[columIndex].equals(value)&&!value.toString().contains("注意：")) {
									if (cell.getValue() != null && !"".equals(cell.getValue())) {
									if(e==null){
									    e = new LeavePerson();
									    e.setZoneCd(zoneCdStr);
									    e.setCreateTime(new Date());
									    e.setIsUse(1);
									}
									if (columIndex == 0) {
										indexValue=value;
									} else if (columIndex == 1) {
										String name=value.toString();
										e.setName(name);
									} else if (columIndex == 2) {
										String phoneStr=null;
										if(value instanceof Double){
										DecimalFormat df = new DecimalFormat("0");
										phoneStr= df.format(value);
										}else if(value instanceof String){
											phoneStr=(String) value;
										}
										if(phoneStr.length()!=11){
											int indexOf = buffer.indexOf(errorStr);
											if(indexOf<0){
												String format = String.format(errorStr+"第%d条,", entityList.size()+1);
												buffer.append(format);
											}
											indexOf = buffer.indexOf(header[columIndex]);
											if(indexOf<0){
												buffer.append(header[columIndex]+"格式不对-长度必须为11,");
												}
										}
										e.setPhone(phoneStr);//
									} else if (columIndex == 3) {
										e.setCardType(value.toString());
									} else if (columIndex == 4) {
										ECell cardTypeDataCell = row.getCell(cell.getColumnIndex()-1);
										if("身份证".equals(cardTypeDataCell.getValue().toString())&&value.toString().trim().length()!=18){
											int indexOf = buffer.indexOf(errorStr);
											if(indexOf<0){
												String format = String.format(errorStr+"第%d条,", entityList.size()+1);
												buffer.append(format);
											}
											 indexOf = buffer.indexOf(header[columIndex]);
											if(indexOf<0){
												buffer.append(header[columIndex]+"格式不对-长度必须为18,");
												}
										}
										e.setCardNum(value.toString());//
									} else if (columIndex == 5) {
										String levTimeStr=null;
										if(value instanceof Date){
											Date levTime=(Date) value;
											levTimeStr=sdf.format(levTime);
										}else
											levTimeStr=value.toString();
										e.setLevTime(levTimeStr);
									} else if (columIndex == 6) {
										e.setHj(value.toString());
									} else if (columIndex == 7) {
										e.setLevLiveType(value.toString());
									} else if (columIndex == 8) {
										e.setLevLiveAddress(value.toString());
									} else if (columIndex == 9) {
										e.setBackProvince(value.toString());
									} else if (columIndex == 10) {
										e.setBackCity(value.toString());
									} else if (columIndex == 11) {
										e.setLevBy(value.toString());
									} else if (columIndex == 12) {
										e.setZoneCd(zoneCdStr);
									} else if (columIndex == 13) {
										e.setCreateTime(new Date());
									}
								}else if(indexValue!=null&&!"".equals(indexValue)&&!indexValue.toString().contains("注意")
										&&columIndex!=12&&columIndex!=13){//无需"区"和 "创建时间"
										int indexOf = buffer.indexOf(errorStr);
										if(indexOf<0){
											String format = String.format(errorStr+"第%d条,", entityList.size()+1);
											buffer.append(format);
										}
										indexOf = buffer.indexOf(header[columIndex]);
										if(indexOf<0){
											buffer.append(header[columIndex]+"为空,");
											}
									}
								}
							}
					}
					if(buffer.length()>0)
						break;
					if(e!=null&&indexValue!=null&&!"".equals(indexValue)&&!indexValue.toString().contains("注意"))
					      entityList.add(e);
				
				}}
				if(buffer.length()==0){
				resultMap.put("rows", Integer.toString(entityList.size()));
				leavePersonService.saveBatch(entityList, entityList.size());
				}
			}
		} catch (IOException e) {
			return JSONObject.toJSONString("上传失败");
		}
		if(buffer.length()==0){
		LogEntity log=new LogEntity(userEntity.getUsername(),new Date(),"成功导入"+resultMap.get("rows")+"条已离汉人员信息");
		leaveLogService.saveOrUpdate(log);
		resultMap.put("msg", "成功导入"+resultMap.get("rows")+"条已离汉人员信息");
		resultMap.put("code", "200");
		}else{
			resultMap.put("code", "400");
			resultMap.put("msg", buffer.toString());
			LogEntity log=new LogEntity(userEntity.getUsername(),new Date(),"导入已离汉人员信息失败:"+ buffer.toString());
			leaveLogService.saveOrUpdate(log);
		}
		return JSONObject.toJSONString(resultMap);
	}
	@ApiOperation(value = "已离汉人员信息Excel模板下载", httpMethod = "GET", notes = "已离汉人员信息Excel模板下载", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@RequestMapping(value = "/excel/exportExcelTemplate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportExcelTemplate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 接受的是UTF-8
		req.setCharacterEncoding("utf-8");
		// 获取文件名
		String filename = "excelTemplate.xlsx";
		HttpHeaders headers = null;
		String filePath = this.getClass().getClassLoader().getResource("static/excel/excelTemplate.xlsx").getPath();
		byte[] fileToBytes = fileToBytes(filePath);
		// 请求头
					headers = new HttpHeaders();
					String fileName1 = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 解决文件名乱码
					headers.setContentDispositionFormData("attachment", fileName1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					LogEntity log=new LogEntity(userEntity.getUsername(),new Date(),"下载Excel模板");
					leaveLogService.saveOrUpdate(log);
		return new ResponseEntity<byte[]>(fileToBytes, headers, HttpStatus.OK);
	}
	@ApiOperation(value = "已离汉人员信息下载", httpMethod = "GET", notes = "已离汉人员信息下载", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@RequestMapping(value = "/excel/export", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportData(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		req.setCharacterEncoding("utf-8");
		String filename = "result.xlsx";
		HttpHeaders headers = null;
		List<LeavePerson> list =null;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		QueryWrapper<LeavePerson> queryWrapper =new QueryWrapper<LeavePerson>();
		String zoneCdStr = Constant.zoneCdKv.get(userEntity.getUsername());
		//管理员名称由admin修改为wh_admin
		if("wh_admin".equals(userEntity.getUsername())){
			list = leavePersonService.list();
		}else{
	        queryWrapper.eq("zone_cd",zoneCdStr);
	       list = leavePersonService.list(queryWrapper);
		}
		//									0			1				2					3				4						5
		String[] header = {"序号", "姓名", "电话", "证件类型", "身份证号", "离汉时间", 
				//6					7								8
				"户籍地", "滞汉居住方式", "滞汉居住地址",
				//		9				10
				"返回省份","返回市",
				//11				12		13
				"返程方式", "区", "创建时间"};
		String tableTile = "已离汉人员信息清单";
		LeavePersonEntity head = new LeavePersonEntity(header);
		List<LeavePersonEntity> body = new ArrayList<LeavePersonEntity>();
		list.forEach(pp -> {
			Object[] data = new Object[14];
			data[0] = pp.getId();
			data[1] = pp.getName();
			data[2] =pp.getPhone();
			data[3] = pp.getCardType();
			data[4] = pp.getCardNum();
			data[5] =pp.getLevTime();
			data[6] =pp.getHj();
			data[7] =pp.getLevLiveType();
			data[8] =pp.getLevLiveAddress();
			data[9] =pp.getBackProvince();
			data[10] =pp.getBackCity();
			data[11] =pp.getLevBy();
			data[12] = pp.getZoneCd();
			data[13] =  pp.getCreateTime()==null?"":sdf.format(pp.getCreateTime());
			body.add(new LeavePersonEntity(data));
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
		config.addColumnWidth(9, Config.DEFAULT_WIDTH*3);
		try {
			headers = new HttpHeaders();
			String fileName1 = new String(filename.getBytes("UTF-8"), "iso-8859-1");// 解决文件名乱码
			headers.setContentDispositionFormData("attachment", fileName1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			t.build(bOut);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		LogEntity log=new LogEntity(userEntity.getUsername(),new Date(),"下载已经离汉人员信息列表");
		leaveLogService.saveOrUpdate(log);
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
		String tableTile = "已离汉人员信息清单";
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
