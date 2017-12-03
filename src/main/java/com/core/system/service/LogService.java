package com.core.system.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.system.dto.ExportLogForm;
import com.core.system.dto.LogDTO;
import com.core.system.model.Log;
import jxl.write.WriteException;

import com.core.base.service.BaseService;

public interface LogService extends BaseService<Log,LogDTO>{
	
	int insert(Log record);

	boolean exportLog(ExportLogForm logForm, HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, WriteException;

}
