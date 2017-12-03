package com.core.system.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.core.system.service.LogService;
import com.core.system.service.PrivilegeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.core.security.model.SpringSecurityUser;
import com.core.system.dao.PrivilegeMapper;
import com.core.system.model.Log;
import com.core.system.model.Privilege;
import com.core.system.service.*;

@Service("logFilter")
public class LogFilter implements Filter {
	@Resource(name="logService")
    com.core.system.service.LogService LogService ;
	@Resource(name="privilegeService")
    PrivilegeService ps;
	
	PrivilegeMapper pm;
		
	public LogFilter(PrivilegeMapper pm) {
		this.pm = pm;
	}
	
    public LogFilter() {
		super();
	}

	public PrivilegeService getPs() {
		return ps;
	}

	public void setPs(PrivilegeService ps) {
		this.ps = ps;
	}

	public void destroy() {

	}
	
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	public LogService getLogService() {
		return LogService;
	}

	public void setLogService(LogService LogService) {
		this.LogService = LogService;
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		List<Privilege> privileges = pm.getPrivilegeList();
		String path = request.getRequestURI();
		if (path.contains("/SDL/")){
			path = path.substring(path.indexOf("/SDL/")+"/SDL".length());
		}
		String ipAddr = request.getRemoteAddr();
		//还需添加方法得到对应的用户名
		SpringSecurityUser userDetails = null ;
		String name = "";
		String displayName = "";
		if(SecurityContextHolder.getContext().getAuthentication()!=null&&(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))){
			userDetails= (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			name = userDetails.getUsername();
			displayName = userDetails.getUserRealName();
		}
		
		String action = "";
		String module = "";
		//根据URL获取当前操作和当前模块
		for(Privilege p:privileges){
			
			if (path.contains(p.getUrl())){
				action = p.getName();
				module = p.getDisplayName();
			}
		}
		
		if(path.contains("application/modules/core/main.jsp"))
		{
			action = "登录";
			module = "";
		}
		
		
		Date date = new Date();
		SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (action!=""){
			Log log = new Log();
			log.setId(UUID.randomUUID().toString());
			log.setDate(today.format(date));
			log.setAction("进入"+action);
			log.setModule(module+"模块");
			log.setUser(name);
			log.setIp(ipAddr);
			log.setDiscription(displayName);
		
			try{
				LogService.insertSelective(log);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("======path:"+path);
		System.out.println("======module:"+module);
		System.out.println("======ip:"+request.getRemoteAddr());
		System.out.println("======user:"+name);

		fc.doFilter(req, res);
	}

}
