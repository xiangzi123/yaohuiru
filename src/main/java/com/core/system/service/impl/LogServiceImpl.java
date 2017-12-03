package com.core.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.system.dao.LogMapper;
import com.core.system.dto.ExportLogForm;
import com.core.system.dto.LogDTO;
import com.core.system.model.Log;
import com.core.system.model.LogExample;
import com.core.system.service.LogService;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl implements LogService {
	@Resource(name="logMapper")
	private LogMapper logMapper;

	public LogMapper getLogMapper() {
		return logMapper;
	}

	public void setLogMapper(LogMapper logMapper) {
		this.logMapper = logMapper;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Log record) {
		return logMapper.insert(record);
	}

	@Override
	public int insertSelective(Log record) {
		return logMapper.insertSelective(record);
	}

	@Override
	public Log selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Log record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean exportLog(ExportLogForm logForm, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, WriteException{
		boolean flag = true;
		String module = logForm.getModule();
		String starttime = logForm.getStarttime();
		String endtime = logForm.getEndtime();
		LogExample le = new LogExample();
		List<Log> logList;
		if (module.isEmpty()&&starttime.isEmpty()&&endtime.isEmpty()){
			logList = logMapper.selectByExample(le);
		}else{
		LogExample.Criteria criteri = le.createCriteria();
        
		if(starttime!=null&&!"".equals(starttime)) criteri.andDateGreaterThanOrEqualTo(starttime);
		if(endtime!=null&&!"".equals(endtime)) criteri.andDateLessThanOrEqualTo(endtime);
		if(module!=null&&!"".equals(module)) criteri.andModuleEqualTo(module);
		
		logList = logMapper.selectByExample(le);
		}
		//方案一
		if(logList != null && logList.size()>0){
			File fileMirk = new File("C:\\logExport");
			if(!fileMirk.exists()){
				fileMirk.mkdirs();
			}
			//OutputStream os = response.getOutputStream();// 取得输出流
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = dateformat.format(date);
			String fileName = "C:\\logExport\\"+time+".xls";
			String xlsName = time+".xls";
			OutputStream os = new FileOutputStream(fileName);
	        response.reset();// 清空输出流   
	        response.setHeader("Content-Disposition", "attachment; filename="+xlsName);// 设定输出文件头   
	        response.setContentType("application/x-download;charset=utf-8");// 定义输出类型 
			try { 
				WritableWorkbook wbook = Workbook.createWorkbook(os); //建立excel文件 
				WritableSheet wsheet = wbook.createSheet("日志信息表", 0); //工作表名称设置Excel字体 
				WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, 
				WritableFont.BOLD, false, 
				jxl.format.UnderlineStyle.NO_UNDERLINE, 
				jxl.format.Colour.BLACK); 
				WritableCellFormat titleFormat = new WritableCellFormat(wfont); 
				String[] title = { "操作人", "IP地址", "操作时间", "操作动作", "操作模块", "其他信息" }; 
				//设置Excel表头 
				for (int i=0; i < title.length; i++) { 
					Label excelTitle = new Label(i, 0, title[i], titleFormat); 
					wsheet.addCell(excelTitle); 
				} 
				int c = 1; //用于循环时Excel的行号 
				Iterator<Log> it = logList.iterator(); 
				while (it.hasNext()) { 
					Log crdto = (Log) it.next(); 
					Label content1 = new Label(0, c, crdto.getUser()); 
					Label content2 = new Label(1, c, crdto.getIp()); 
					Label content3 = new Label(2, c, crdto.getDate()); 
					Label content4 = new Label(4, c, crdto.getAction()); 
					Label content5 = new Label(3, c, crdto.getModule()); 
					Label content6 = new Label(5, c, crdto.getDiscription()); 
					wsheet.addCell(content1); 
					wsheet.addCell(content2); 
					wsheet.addCell(content3); 
					wsheet.addCell(content4); 
					wsheet.addCell(content5); 
					wsheet.addCell(content6);
					c++; 
				} 
				wbook.write(); //写入文件 
				wbook.close(); 
				os.close(); 
				System.out.println("Print OK!");
				//response.getWriter().print("{\"result\":\"success\"}");
				} catch (Exception e) { 
					e.printStackTrace();
					System.out.println("Print Error!");
					//response.getWriter().print("{\"result\":\"fail\"}");
					flag = false;
				} 
		}
		//方案二
/*		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateformat.format(date);				
		String xlsName = "导出日志"+time+".xls";
        response.reset();// 清空输出流   
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+xlsName);// 设定输出文件头   
		if(logList.size()>0){
		       
	        ExcelWriter writer=new ExcelWriter(response.getOutputStream());			//初始化输出类
	        writer.initSheet("日志信息表");
	        writer.join(1, 1, 1, 4);
	        writer.writeTitleData(1, 1,"日志", 4000);
			if (logList.size() != 0) {
				for(int i=0; i<6; i++){
					if(i==0) writer.writeColTitleData(2, i+1, "操作人", 6000); //表头
					else if(i==1) writer.writeColTitleData(2, i+1, "ip地址", 6000); //表头
					else if(i==2) writer.writeColTitleData(2, i+1, "操作时间", 6000); //表头
					else if(i==3) writer.writeColTitleData(2, i+1, "操作动作", 14000); //表头
					else if(i==4) writer.writeColTitleData(2, i+1, "操作模块", 6000); //表头
					else if(i==5) writer.writeColTitleData(2, i+1, "其他信息", 6000); //表头
				}
				
				for (int j = 0; j < logList.size(); j++) {			//将list中的数据，输出到excel中
					int row = j+3 ;
					for(int k=0; k<6; k++){
						switch (k) {
						case 0:
							writer.writeData(row, k+1, logList.get(j).getUser() );
							break;
						case 1:
							writer.writeData(row,k+1, logList.get(j).getIp() );
							break;
						case 2:
							writer.writeData(row, k+1, logList.get(j).getDate());
							break;
						case 3:
							writer.writeData(row,k+1, logList.get(j).getAction());
							break;
						case 4:
							writer.writeData(row,k+1, logList.get(j).getModule());
							break;
						case 5:
							writer.writeData(row,k+1, logList.get(j).getDiscription());
							break;
						default:
							break;
						}																
					}
				}
				try {
					writer.end();			
				} catch (IOException e) {
					e.printStackTrace();
					flag = false;
				}
			}
		}*/
		return flag;
	}

	@Override
	public int getTotalRecords(HttpServletRequest request, Boolean search) {
		// TODO Auto-generated method stub
		LogExample le = new LogExample();
		if(search){
			addCriteria(request, le);
		}
		return logMapper.countByExample(le);
	}

	private void addCriteria(HttpServletRequest request, LogExample le) {
		// TODO Auto-generated method stub
		String user = request.getParameter("user");
		String ip = request.getParameter("ip");
		String dateStart = request.getParameter("startTime");
		String dateEnd = request.getParameter("endTime");
		String action = request.getParameter("action");
		String module = request.getParameter("module");
		LogExample.Criteria criteri = le.createCriteria();
		if(user!=null&&!"".equals(user)) criteri.andDiscriptionLike("%"+user+"%");
		if(ip!=null&&!"".equals(ip)) criteri.andIdEqualTo(ip);
		if(dateStart!=null&&!"".equals(dateStart)) criteri.andDateGreaterThanOrEqualTo(dateStart);
		if(dateEnd!=null&&!"".equals(dateEnd)) criteri.andDateLessThanOrEqualTo(dateEnd);
		if(action!=null&&!"".equals(action)) criteri.andActionEqualTo(action);
		if(module!=null&&!"".equals(module)) criteri.andModuleEqualTo(module);
		
	}

	@Override
	public List<LogDTO> listResults(int start, int limit, String sortName,
                                    String sortOrder, HttpServletRequest request, Boolean search) {
		LogExample le = new LogExample();
		le.setOrderByClause( sortName +" "+ sortOrder);
		le.setStart(start);
		le.setLimit(limit);
		
		if(search){
			addCriteria(request, le);
		}

		/*End*/
		
		List<Log> logList = logMapper.selectByExample(le);
		
		List<LogDTO> logDTOList = new ArrayList<LogDTO>();
		LogDTO logDTO = null;
		for (Log log : logList) {
			logDTO = new LogDTO();
			
			logDTO.setUser(log.getUser());
			logDTO.setIp(log.getIp());
			logDTO.setDate(log.getDate());
			logDTO.setAction(log.getAction());
			logDTO.setModule(log.getModule());
			logDTO.setDiscription(log.getDiscription());
			
			logDTOList.add(logDTO);
		}
		return logDTOList;
	}

}
