package com.core.security.service.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.core.system.model.*;
import com.core.security.filter.MyInvocationSecurityMetadataSource;
import com.core.system.dao.UserInfoMapper;
import com.core.system.dao.UserMapper;
import com.core.system.dao.UserRoleMapper;
import com.core.system.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.core.security.model.SpringSecurityUser;
import com.core.system.dao.DepartmentMapper;

public class MyUserDetailServiceImpl implements UserDetailsService {  
  
	private UserMapper userMapper;
	private UserRoleMapper userRoleMapper;
	private DepartmentMapper departmentMapper;
	private UserInfoMapper userInfoMapper;
	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
	
     @Override  
     //登陆默认会调到这里
    public UserDetails loadUserByUsername(String username)  
            throws UsernameNotFoundException, DataAccessException {
        if(debug){
         	log.debug("user detail: "+username);
        }
        SpringSecurityUser user = null;
        Set<Privilege> userPrivileges = new HashSet<Privilege>();
        Department department;
        String permitNum;
        String userRealName;
        
 		UserExample ue = new UserExample();
 		UserExample.Criteria criteri = ue.createCriteria();
 		criteri.andLoginNameEqualTo(username);
 		try{
	 		List<User> userList = userMapper.selectByExample(ue);
			//List<User> userList = Connector.getUserList(username);
	        if((debug)&&(userList!=null&&userList.size()>0)){
	         	log.debug("user detail : "+userList.get(0));
	        }
	 		if(userList!=null&&userList.size()>0){
	 	        Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();  
	 	        List<Role> roles = userRoleMapper.getRolesForUserById(userList.get(0).getUserId());
	 	        department = departmentMapper.selectByPrimaryKey(userList.get(0).getDepartmentId());
	 	        UserInfoExample uie = new UserInfoExample();
	 	        uie.or().andUserIdEqualTo(userList.get(0).getUserId());
	 	        List<UserInfo> userInfo = userInfoMapper.selectByExample(uie);
	 	        permitNum = userInfo.get(0).getPermitNum();
	 	        userRealName = userList.get(0).getUserName();
	 	         
	 	        if(roles.size()==0) {
	 	        	GrantedAuthority auth=new SimpleGrantedAuthority("NO_ROLE_OR_PRIVILEGE_USER");  
	 	 	        auths.add(auth);
	 	 	    }
	 	        for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext();) {
	 	        	Role role = (Role) iterator.next();
					GrantedAuthority auth=new SimpleGrantedAuthority(role.getName());  
		 	        auths.add(auth);
		 	        Collection<Privilege> temp = MyInvocationSecurityMetadataSource.getPrivilegeMap().get(role.getName());
		 	        if(temp!=null) userPrivileges.addAll(temp);
		 	        else {
		 	        	GrantedAuthority authForPrivilege=new SimpleGrantedAuthority("NO_ROLE_OR_PRIVILEGE_USER");  
		 	 	        auths.add(authForPrivilege);
		 	        }
		 	    }
	 	        user = new SpringSecurityUser(username,userList.get(0).getPassword(), true, true, true, true, auths,userPrivileges,department,permitNum,userRealName);
	 		}
 		}catch (Exception e) {
 			e.printStackTrace();
 		}
 		//在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等，我想这么简单的代码就不再多解释了。
        return user;  
     }

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

	public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
	}

	public DepartmentMapper getDepartmentMapper() {
		return departmentMapper;
	}

	public void setDepartmentMapper(DepartmentMapper departmentMapper) {
		this.departmentMapper = departmentMapper;
	}

	public UserInfoMapper getUserInfoMapper() {
		return userInfoMapper;
	}

	public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}        
} 