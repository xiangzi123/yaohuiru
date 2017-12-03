package com.core.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.core.base.util.SystemConstants;
import com.core.security.service.AuthService;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
	private AuthService authService;
	private SessionRegistry sessionRegistry;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		//checkValidateCode(request);
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		username = username.trim();
		password = password.trim();
		if(debug){
			log.debug("username:"+username);
			log.debug("password:"+password);
		}
		
		if(StringUtils.isEmpty(username)){
			throw new AuthenticationServiceException("用户名为空！"); 
		}
		if(StringUtils.isEmpty(password)){
			throw new AuthenticationServiceException("密码为空！"); 
		}
		
		//验证用户账号与密码是否对应
		String flag = authService.validate(username, password);
		if(flag.equals("Wrong Username")){
	        if(debug){
	        	log.debug("用户:"+username+"登陆失败");  //object is a URL.
	        }
			throw new AuthenticationServiceException("账号不存在！"); 
		}else if(flag.equals("Wrong Password")){
	        if(debug){
	        	log.debug("用户:"+username+"登陆失败");  //object is a URL.
	        }
			throw new AuthenticationServiceException("账号密码错误！"); 
		}else if(flag.equals("User Locked")){
	        if(debug){
	        	log.debug("用户:"+username+"登陆失败");  //object is a URL.
	        }
			throw new AuthenticationServiceException("账号被冻结！"); 
		}else if(flag.equals("User Expired")){
	        if(debug){
	        	log.debug("用户:"+username+"登陆失败");  //object is a URL.
	        }
			throw new AuthenticationServiceException("账号被废弃！"); 
		}
		
		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// 允许子类设置详细属性
        setDetails(request, authRequest);
        //通过验证，记录登录名到session中
        request = (HttpServletRequest)request;
        request.getSession().setAttribute(SystemConstants.FIRST_VISIT,true);
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
//	protected void checkValidateCode(HttpServletRequest request) { 
//		HttpSession session = request.getSession();
//		
//	    String sessionValidateCode = obtainSessionValidateCode(session); 
//	    //让上一次的验证码失效
//	    session.setAttribute(VALIDATE_CODE, null);
//	    String validateCodeParameter = obtainValidateCodeParameter(request);  
//	    if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {  
//	        throw new AuthenticationServiceException("验证码错误！");  
//	    }  
//	}
//	
//	private String obtainValidateCodeParameter(HttpServletRequest request) {
//		Object obj = request.getParameter(VALIDATE_CODE);
//		return null == obj ? "" : obj.toString();
//	}
//
//	protected String obtainSessionValidateCode(HttpSession session) {
//		Object obj = session.getAttribute(VALIDATE_CODE);
//		return null == obj ? "" : obj.toString();
//	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(SystemConstants.USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(SystemConstants.PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
