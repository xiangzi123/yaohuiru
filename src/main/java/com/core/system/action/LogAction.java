package com.core.system.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.system.dto.ExportLogForm;
import com.core.system.dto.LogDTO;
import jxl.write.WriteException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.base.action.JqGridBaseAction;
import com.core.system.service.LogService;

@Controller
@RequestMapping("/system/log")
public class LogAction extends JqGridBaseAction<LogDTO>{
	
//	private final Log log = LogFactory.getLog(getClass());
//	private boolean debug = log.isDebugEnabled();
	
	//将spring 配置文件中的bean 通过setter注入进来
	@Resource(name="logService")
	private LogService logService=null;

	@Override
	public List<LogDTO> listResults(int start, int limit, String sortName,
			String sortOrder, HttpServletRequest request, Boolean search) {
		return this.logService.listResults(start, limit, sortName, sortOrder, request,search);
	}

	@Override
	public Integer getTotalRecords(HttpServletRequest request, Boolean search) {
		return logService.getTotalRecords(request,search);
	}	
	
	@RequestMapping("/exportLog.do")
	@ResponseBody
	public boolean exportLog(@ModelAttribute("exportLogForm") ExportLogForm logForm, HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException, ServletException{
		boolean validate = true;
		System.out.println("======================"+logForm.toString());
		if(!this.logService.exportLog (logForm,request,response)) validate=false;
		return validate;
	}

	
	public LogService getlogService() {
		return logService;
	}

	public void setlogService(LogService logService) {
		this.logService = logService;
	}


}
