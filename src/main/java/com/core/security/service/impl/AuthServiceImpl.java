package com.core.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.security.service.AuthService;
import com.core.system.dao.UserMapper;
import com.core.system.model.User;
import com.core.system.model.UserExample;
import com.core.system.model.UserExample.Criteria;

@Service("authService")
public class AuthServiceImpl implements AuthService{

	@Resource(name="userMapper")
	private UserMapper userMapper;
	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
	private String userRealName = "";
	
	@Override
	public String validate(String username, String password) {
		UserExample ue = new UserExample();
		Criteria criteri = ue.createCriteria();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String passWordMd5 = md5.encodePassword(password, username);
		criteri.andLoginNameEqualTo(username);
		List<User> resultUserExcample = this.userMapper.selectByExample(ue);
        //List<User> resultUserExcample = Connector.getUserList(username);
		if(debug){
			log.debug("resultUserExcample: "+resultUserExcample);
		}
		
		if(!(resultUserExcample!=null&&resultUserExcample.size()>0)) return "Wrong Username";
		else if(!(resultUserExcample.get(0).getPassword().equals(passWordMd5))){
			return "Wrong Password";
		}
		else if(resultUserExcample.get(0).getStatus().equals("冻结态")){
			return "User Locked";
		}
		else if(resultUserExcample.get(0).getStatus().equals("废弃态")){
			return "User Expired";
		}
		if(debug){
			log.debug("service validate flag: "+"Success");
		}
		this.setUserRealName(resultUserExcample.get(0).getUserName());
		return "SUCCESS";
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
}
