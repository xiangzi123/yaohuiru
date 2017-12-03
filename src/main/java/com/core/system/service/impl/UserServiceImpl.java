package com.core.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.core.base.util.Utils;
import com.core.security.model.SpringSecurityUser;
import com.core.system.dao.RoleMapper;
import com.core.system.dao.UserInfoMapper;
import com.core.system.dao.UserMapper;
import com.core.system.dao.UserRoleMapper;
import com.core.system.dto.DepartmentPrivilegeDTO;
import com.core.system.dto.UserAddForm;
import com.core.system.dto.UserCheckForm;
import com.core.system.dto.UserModifyForm;
import com.core.system.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.core.system.dao.DepartmentMapper;
import com.core.system.dto.UserDTO;
import com.core.system.model.Department;
import com.core.system.model.DepartmentExample;
import com.core.system.model.Role;
import com.core.system.model.RoleExample;
import com.core.system.model.User;
import com.core.system.model.UserExample;
import com.core.system.model.UserInfo;
import com.core.system.model.UserInfoExample;
import com.core.system.model.UserRole;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userMapper")
	private UserMapper userMapper;
	@Resource(name="departmentMapper")
	private DepartmentMapper departmentMapper;
	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	@Resource(name="userRoleMapper")
	private UserRoleMapper userRoleMapper;
	@Resource(name="userInfoMapper")
	private UserInfoMapper userInfoMapper;
	@Resource(name="sessionRegistry")
	private SessionRegistry sessionRegistry;

	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
	public static Map<String, List<User>> importUsers = new HashMap<String,List<User>>();
	
	
	//app登录验证
	public String validateLogin(String loginname, String password) {
		
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String passWordMd5 = md5.encodePassword(password,loginname);
		
		UserExample ue = new UserExample();
		UserExample.Criteria criteria = ue.createCriteria();
		criteria.andLoginNameEqualTo(loginname);
		List<User> resultUserExample = userMapper.selectByExample(ue);
		if(resultUserExample.size()==0) {
			return "0";			//用户不存在
		}
		else {
			if(!passWordMd5.equals(resultUserExample.get(0).getPassword())) {
				return "1";		//密码错误
			}
		}
		return "2";
	}
	
    //设置头像
	@Override
	public boolean setIcon(HttpServletRequest request) {
		
	    String loginName=request.getParameter("loginName"); 
		UserExample ue =  new UserExample(); 
		UserExample.Criteria criteria = ue.createCriteria();
		criteria.andLoginNameEqualTo(loginName);
		User user=this.userMapper.selectByExample(ue).get(0);//获取当前用户
		 
		int userId=user.getUserId();
		UserInfoExample userInfoExample =  new UserInfoExample(); 
		UserInfoExample.Criteria infoCriteria = userInfoExample.createCriteria();
		infoCriteria.andUserIdEqualTo(userId);   //获取userId对应的用户信息表userInfo
		UserInfo userInfo=this.userInfoMapper.selectByExample(userInfoExample).get(0);
		
		//将icon存储到userinfo表的icon字段
		String image=request.getParameter("image");
		String imageFileName=(new Date().getTime())+loginName+".jpg";
		

		
		String userDir=System.getProperty("user.dir");
		String tempdir=userDir.replace("bin", "webapps");  //把bin 文件夹变到 webapps文件里面 
		String path = tempdir +File.separator+"TRIMS"+File.separator+"images"+File.separator+"resou"+File.separator+"Icons"+File.separator;
				
		userInfo.setIcon(imageFileName);
		userInfoMapper.updateByPrimaryKey(userInfo);
		try {	
		File file = new File(path+imageFileName);
		if(!file.exists()){
		file.createNewFile();
		}
		FileOutputStream fos=new FileOutputStream(file);
		byte[] b=Base64.decode(image);
		fos.write(b);
		fos.close();		
			
		return true;
		} catch (Exception e) {
			return false;
		}
	
	} 
	
	
	
	
	//修改密码	
	public boolean modifyPassword(String loginName,String oldPassword,String newPassword){
		
		 UserExample ue =  new UserExample(); 
		 UserExample.Criteria criteria = ue.createCriteria();
		 criteria.andLoginNameEqualTo(loginName);
		 User user=this.userMapper.selectByExample(ue).get(0);//获取loginName对应的user
		
		 Md5PasswordEncoder md5Old = new Md5PasswordEncoder();
			String passWordMd5 = md5Old.encodePassword(oldPassword,loginName);//Md5加密
			
		 if(!passWordMd5.equals(user.getPassword())) {
				return false;		//原密码填写错误
			}
		 else { 
			 
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String passWordMd5New = md5.encodePassword(newPassword,loginName);//Md5加密

		  user.setPassword(passWordMd5New);
		  userMapper.updateByPrimaryKey(user);
		  return true;
		 }
	
		
	}
	
	
	
	@Override
	public boolean validate(String username, String password) {
		UserExample ue = new UserExample();
		UserExample.Criteria criteria = ue.createCriteria();
		criteria.andLoginNameEqualTo(username);
		List<User> resultUserExample = userMapper.selectByExample(ue);

		if(debug){
			log.debug("resultUserExcample: "+resultUserExample);
		}
		
		boolean flag = false;
		if(resultUserExample!=null&&resultUserExample.size()>0&&resultUserExample.get(0).getPassword().equals(password)){
			flag =  true;
		}
		if(debug){
			log.debug("service validate flag: "+flag);
		}
		return flag;
	}

	@Override
	public List<UserDTO> listResults(int start, int limit, String sortName,
			String sortOrder, HttpServletRequest request,Boolean search) {
		//TODO 需要修复百万级数据库查询效率，多索引
		UserExample ue = new UserExample();
		ue.setOrderByClause( sortName +" "+ sortOrder);
		ue.setStart(start);
		ue.setLimit(limit);
			
		if(search){
			addCriteria(request, ue);
		}
				
		List<UserDTO> userDTOList = userMapper.selectUserDTO(ue);

		for (UserDTO userDTO : userDTOList) {
			User user = this.userMapper.selectByPrimaryKey(userDTO.getUserId());
			if (user.getRoles().size()!=0) {
				String roleNames = "";
				int i  = 1;
				for (Role r : user.getRoles()) {
					roleNames += (i++)+"、"+r.getName()+" ";
				}
				userDTO.setRoleNames(roleNames);
			}
			if(userDTO.getType().equals("student"))
				userDTO.setType("学生");
			else if(userDTO.getType().equals("teacher"))
				userDTO.setType("教师");
		}
		
		return userDTOList;
	}


	/**
	 * 查询条件，通过Criteria设置到UserExample中
	 * @param request
	 * @param ue
	 * @author 李彤 2013-8-27 下午3:27:39
	 */
	private void addCriteria(HttpServletRequest request, UserExample ue) {
		String permitNum = request.getParameter("permitNum");
		String loginName = request.getParameter("loginName");
		String userName = request.getParameter("userName");
		String role = request.getParameter("role");
		String organ = request.getParameter("organ");
		String status = request.getParameter("status");
		UserExample.Criteria criteri = ue.createCriteria();
		if(permitNum!=null&&!"".equals(permitNum)) {
			ue.setPermitNum(permitNum);
			criteri.andUserPermitNumLike("%"+permitNum+"%");
		}
		if(loginName!=null&&!"".equals(loginName)) criteri.andLoginNameLike("%"+loginName+"%");
		if(userName!=null&&!"".equals(userName)) criteri.andUserNameLike("%"+userName+"%");
		if(role!=null&&!"".equals(role)) criteri.andUserRoleEqualTo(Integer.valueOf(role));
		if(organ!=null&&!"".equals(organ)) criteri.andDepartmentIdEqualTo(Integer.valueOf(organ));
		if(!Utils.isEmptyOrNull(status)) criteri.andStatusEqualTo(status);
	}
	
	public List<Role> getAllRoles(){
		RoleExample ex =  new RoleExample();
		List<Role> roles= roleMapper.selectByExample(ex);
		return roles;
	}
	
	public List<Department> getAllDepartments(){
		DepartmentExample de =  new DepartmentExample();
		DepartmentExample.Criteria criteri = de.createCriteria();
		criteri.andNameIsNotNull();
		List<Department> depts= departmentMapper.selectByExample(de);
		return depts;
	}
	
	@Override
	public User validateExist(String loginName) {
		UserExample ue = new UserExample();
		ue.createCriteria().andLoginNameEqualTo(loginName);
		User user = this.userMapper.selectByExample(ue).size()==0?null:this.userMapper.selectByExample(ue).get(0);
		return user;
	}

	@Override
	public UserDTO validatePNExist(String permitNum) {
		UserExample ue = new UserExample();
		ue.createCriteria().andUserPermitNumEqualTo(permitNum);
		List<UserDTO> uList= this.userMapper.selectUserDTO(ue);
		if(uList.size()==0) return null;
		else return uList.get(0); 
	}

	@Override
	public boolean insertUserAndRoles(UserAddForm userAddForm) {
		boolean result = true;
		try {
			/*
			 * 增加用户
			 */
			User user = new User();
			user.setUserName(userAddForm.getUserName());
			user.setLoginName(userAddForm.getLoginName());
			user.setDepartmentId(Integer.valueOf(userAddForm.getOrgan()));
			user.setStatus("正常态");
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			String passWordMd5 = md5.encodePassword("88888888", user.getLoginName());
			user.setPassword(passWordMd5);
			userMapper.insertSelective(user);
			
			int userId = user.getUserId();
			/*
			 * 增加用户信息
			 */
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);
			try {
				if(userAddForm.getBirthday()!=null&&!"".equals(userAddForm.getBirthday())) 
					userInfo.setBirthday(format.parse(userAddForm.getBirthday()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			userInfo.setEmail(userAddForm.getEmail());
			try {
				if(userAddForm.getHireday()!=null&&!"".equals(userAddForm.getHireday())) 
					userInfo.setHireday(format.parse(userAddForm.getHireday()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			userInfo.setPermitNum(userAddForm.getPermitNum());
			userInfo.setTelephone(userAddForm.getTelephone());
			userInfo.setContactNum(userAddForm.getContactNum());
			userInfo.setType(userAddForm.getType());
			userInfo.setSex(userAddForm.isSex());
			userInfoMapper.insertSelective(userInfo);
			if (userAddForm.getRoleSelectR()!=null) {
				/*
				 * 为该用户增加角色
				 */
				String[] roles = userAddForm.getRoleSelectR().split(",");
				for (int i = 0; i < roles.length; i++) {
					Integer roleId = Integer.valueOf(roles[i]);
					UserRole userRole = new UserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(userId);
					userRoleMapper.insert(userRole);
				}
			}
		} catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public boolean updateUserAndRoles(UserModifyForm userModifyForm) {
		boolean result = true;
		try {
			/*
			 * 修改用户，登录名唯一因此此处使用登录名判断，如果使用id需要前台hidden和修改userModifyForm property
			 */
			UserExample ue = new UserExample();
			ue.createCriteria().andLoginNameEqualTo(userModifyForm.getLoginName());
			User user = this.userMapper.selectByExample(ue).size()==0?null:this.userMapper.selectByExample(ue).get(0);
			user.setUserName(userModifyForm.getUserName());
			user.setDepartmentId(Integer.valueOf(userModifyForm.getOrgan()));
			userMapper.updateByPrimaryKey(user);
			Department dp = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
			int userId = user.getUserId();
			/*
			 * 修改用户信息
			 */
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			UserInfoExample uie = new UserInfoExample();
			uie.or().andUserIdEqualTo(userId);
			UserInfo userInfo = this.userInfoMapper.selectByExample(uie).size()==0?null:this.userInfoMapper.selectByExample(uie).get(0);
			try {
				if(userModifyForm.getBirthday()!=null&&!"".equals(userModifyForm.getBirthday())) 
					userInfo.setBirthday(format.parse(userModifyForm.getBirthday()));
				else if("".equals(userModifyForm.getBirthday())) userInfo.setBirthday(null);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				if(userModifyForm.getHireday()!=null&&!"".equals(userModifyForm.getHireday())) 
					userInfo.setHireday(format.parse(userModifyForm.getHireday()));
				else if("".equals(userModifyForm.getHireday())) userInfo.setHireday(null);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			userInfo.setEmail(userModifyForm.getEmail());
			userInfo.setPermitNum(userModifyForm.getPermitNum());
			userInfo.setTelephone(userModifyForm.getTelephone());
			userInfo.setContactNum(userModifyForm.getContactNum());
			userInfo.setType(userModifyForm.getType());
			userInfo.setSex(userModifyForm.isSex());
			userInfoMapper.updateByPrimaryKey(userInfo);
			/*
			 * 为该用户修改角色
			 */
			//处理session中特定用户已有roles
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			List<SpringSecurityUser> onlineAccounts = new ArrayList<SpringSecurityUser>();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					userDetails.setUserRealName(user.getUserName());
					userDetails.setPermitNum(userInfo.getPermitNum());
					userDetails.setDepartment(dp);
					userDetails.getAuthorities().clear();
					onlineAccounts.add(userDetails);
				}
			}
			String[] userRoles = userRoleMapper.getRoleIdsForUser(userId);
			if(userRoles.length!=0)
				userRoleMapper.deleteByUserId(userId);
			String[] roles = {};
			if(userModifyForm.getRoleSelectR()!=null)
				roles = userModifyForm.getRoleSelectR().split(",");
			for (int i = 0; i < roles.length; i++) {
				Integer roleId = Integer.valueOf(roles[i]);
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRoleMapper.insert(userRole);
				Role roleTemp = roleMapper.selectByPrimaryKey(roleId);
				for (Iterator<SpringSecurityUser> iterator = onlineAccounts.iterator(); iterator.hasNext();) {
					SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
					userDetails.getAuthorities().add(new SimpleGrantedAuthority(roleTemp.getName()));
				}
			}
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UserModifyForm getUserRolesAndInfo(String userId) {
		UserModifyForm userModifyForm = new UserModifyForm();
		Integer userid = Integer.valueOf(userId);
		User user = this.userMapper.selectByPrimaryKey(userid);
		UserInfoExample uie = new UserInfoExample();
		uie.or().andUserIdEqualTo(userid);
		UserInfo userInfo = this.userInfoMapper.selectByExample(uie).size()==0?null:this.userInfoMapper.selectByExample(uie).get(0);
		userModifyForm.setLoginName(user.getLoginName());
		userModifyForm.setUserName(user.getUserName());
		userModifyForm.setOrgan(user.getDepartmentId().toString());
		userModifyForm.setOrganName(departmentMapper.selectByPrimaryKey(user.getDepartmentId()).getName());
		if (userInfo!=null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			userModifyForm.setBirthday(userInfo.getBirthday()==null?"":format.format(userInfo.getBirthday()));
			userModifyForm.setEmail(userInfo.getEmail());
			userModifyForm.setHireday(userInfo.getHireday()==null?"":format.format(userInfo.getHireday()));
			userModifyForm.setPermitNum(userInfo.getPermitNum());
			userModifyForm.setTelephone(userInfo.getTelephone());
			userModifyForm.setContactNum(userInfo.getContactNum());
			if(userInfo.getSex()!=null)
				userModifyForm.setSex(userInfo.getSex()); 
			userModifyForm.setType(userInfo.getType());
		}
		/*填充Role ids*/
		String[] roleIds = this.userRoleMapper.getRoleIdsForUser(userid);
		if (roleIds.length!=0) {
			String combineIds = Arrays.toString(roleIds);
			String temp=combineIds.substring(1, combineIds.length()-1);
			temp = temp.replaceAll(" ", "");
			userModifyForm.setRoleSelectR(temp);
		}
		return userModifyForm;
	}

	@Override
	public UserCheckForm getInfoForCheck(String userId) {
		UserCheckForm userCheckForm = new UserCheckForm();
		Integer userid = Integer.valueOf(userId);
		User user = this.userMapper.selectByPrimaryKey(userid);
		UserInfoExample uie = new UserInfoExample();
		uie.or().andUserIdEqualTo(userid);
		UserInfo userInfo = this.userInfoMapper.selectByExample(uie).size()==0?null:this.userInfoMapper.selectByExample(uie).get(0);
		userCheckForm.setLoginName(user.getLoginName());
		userCheckForm.setUserName(user.getUserName());
		log.debug("!!departmentMapper"+departmentMapper);
		String organName = this.departmentMapper.selectByPrimaryKey(user.getDepartmentId()).getName();
		userCheckForm.setOrgan(organName);
		userCheckForm.setStatus(user.getStatus());
		if (userInfo!=null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			userCheckForm.setBirthday(userInfo.getBirthday()==null?"":format.format(userInfo.getBirthday()));
			userCheckForm.setEmail(userInfo.getEmail());
			userCheckForm.setHireday(userInfo.getHireday()==null?"":format.format(userInfo.getHireday()));
			userCheckForm.setPermitNum(userInfo.getPermitNum());
			userCheckForm.setTelephone(userInfo.getTelephone());
			userCheckForm.setContactNum(userInfo.getContactNum());
			if(userInfo.getSex()!=null)
				userCheckForm.setSex(userInfo.getSex()); 
			if(userInfo.getType().equals("student"))
				userCheckForm.setType("学生");
			else if(userInfo.getType().equals("teacher"))
				userCheckForm.setType("教师");
			else
				userCheckForm.setType(userInfo.getType());
		}
		/*填充Role ids*/
		String[] roleIds = this.userRoleMapper.getRoleIdsForUser(userid);
		if (roleIds.length!=0) {
			String combineIds = Arrays.toString(roleIds);
			String temp=combineIds.substring(1, combineIds.length()-1);
			temp = temp.replaceAll(" ", "");
			userCheckForm.setRoleSelectR(temp);
		}
		return userCheckForm;
	}
	
	@Override
	public boolean deleteUser(String userId) {
		boolean result = true;
		try {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, true);
					for (Iterator<SessionInformation> iterator2 = sessionInfos.iterator(); iterator2.hasNext();) {
						SessionInformation sessionInformation = (SessionInformation) iterator2.next();
						sessionInformation.expireNow();
					}
					break;
				}
			}
			this.userRoleMapper.deleteByUserId(Integer.valueOf(userId)); //数据库层的外键参照，级联，完全可以不用手动删除
			UserInfoExample uie = new UserInfoExample();
			uie.or().andUserIdEqualTo(Integer.valueOf(userId));
			this.userInfoMapper.deleteByExample(uie); //数据库层的外键参照，级联，完全可以不用手动删除
			this.userMapper.deleteByPrimaryKey(Integer.valueOf(userId));
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean recoverUserPass(String userId) {
		boolean result = true;
		try {
			User user = this.userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, true);
					for (Iterator<SessionInformation> iterator2 = sessionInfos.iterator(); iterator2.hasNext();) {
						SessionInformation sessionInformation = (SessionInformation) iterator2.next();
						sessionInformation.expireNow();
					}
					break;
				}
			}
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			String passWordMd5 = md5.encodePassword("88888888", user.getLoginName());
			user.setPassword(passWordMd5);
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getTotalRecords(HttpServletRequest request,Boolean search) {
		UserExample ue = new UserExample();
		if(search){
			addCriteria(request, ue);
		}
		return userMapper.countByExample(ue);
	}

	@Override
	public String validateExcel(InputStream is, String sessionId) {
		
/*		StringBuffer result = new StringBuffer("");// 字段是否为空验证信息
		Map<String,String> organs; //系统所有机构类型，以供校验
		Map<String, User> users;//系统所有用户，以供校验
		List<User> excelUsers = new ArrayList<User>(); //保存导入的用户
		ExcelReader reader = new ExcelReader();
		reader.init(is);

		if(reader.getNumberOfSheet()<1){
			result.append("请下载正确的模板!");
		}else{
			int rowNum = reader.getSheet(1).getLastRowNum();//用户
			if(rowNum<6)
				result.append("请下载正确的模板!");
			if(result.toString().equals("")){
				int colNum = reader.getSheet(1).getRow(1).getLastCellNum();//列数
				if(colNum!=9)
					result.append("请下载正确的模板!");
			}
			if(result.toString().equals("")){
				organs = this.getOrgans();
			
				users = this.getUsers();
				if(rowNum<3)
					result.append("导入的数据为空!");
				reader.getSheet(1);
				for (int i = 3; i <= rowNum; i++) {
					
					String user_ID = reader.readContent(i + 1, 1); //用户名
					String password = reader.readContent(i + 1, 2);//密码
					String name = reader.readContent(i + 1, 3);//姓名
					String sex = reader.readContent(i + 1, 4);//性别
					String role = reader.readContent(i + 1, 5);//角色
					String organ = reader.readContent(i + 1, 6);//所属机构
					String num = reader.readContent(i + 1, 7);//用户编号
					String phone = reader.readContent(i + 1, 8);//联系电话
					String e_mail = reader.readContent(i + 1, 9);//E-mail
					
					//去掉字符串两头的空格
					if(!Utils.isEmptyOrNull(user_ID)) user_ID = user_ID.trim();
					if(!Utils.isEmptyOrNull(password)) password = password.trim();
					if(!Utils.isEmptyOrNull(name)) name = name.trim();
					if(!Utils.isEmptyOrNull(sex)) sex = sex.trim();
					if(!Utils.isEmptyOrNull(role)) role = role.trim();
					if(!Utils.isEmptyOrNull(organ)) organ = organ.trim();
					if(!Utils.isEmptyOrNull(num)) num = num.trim();
					if(!Utils.isEmptyOrNull(phone)) phone = phone.trim();
					if(!Utils.isEmptyOrNull(e_mail)) e_mail = e_mail.trim();
					
					
					User q = new User();
					
					//用户名
					if(Utils.isEmptyOrNull(user_ID))
						result.append("第1sheet中第" + (i+1) + "行用户名为空<br />");
					else if(user_ID.getBytes().length>20)
						result.append("第1sheet中第" + (i+1) + "行用户名：" + user_ID + " 长度超过20字节<br />");
					else if(!RegExpValidator.IsEnString(user_ID))
						result.append("第1sheet中第"+(i+1) +"行用户名：" + user_ID + " 只能包含英文字母、数字和下划线<br />");
					else{
						//如果此user_ID的用户已存在，就找出已存在的用户来，并对其进行修改
						if(users.containsKey(user_ID))
							Utils.copyProperties(q, users.get(user_ID));
						else
							q.setUser_ID(user_ID);
					}
					
					//密码
					if(Utils.isEmptyOrNull(password))
						result.append("第1sheet中第" + (i+1) + "行密码为空<br />");
					else if(password.getBytes().length>50)
						result.append("第1sheet中第" + (i+1) + "行密码：" + password + " 长度超过50字节<br />");
					else if(password.getBytes().length<6)
						result.append("第1sheet中第" + (i+1) + "行密码：" + password + " 长度小于6字节<br />");
					else if(!RegExpValidator.IsEnString(password)){
						System.out.println("密码是："+password);
						result.append("第1sheet中第"+(i+1) +"行密码：" + password + " 只能包含英文字母、数字和下划线<br />");}
						
					else
						q.setPassword(CipherUtil.generatePassword(password));
					
						
					
					//姓名
					if(Utils.isEmptyOrNull(name)){
						result.append("第1sheet中第" + (i+1) + "行姓名为空<br />");
					}else if(name.getBytes().length>30)
						result.append("第1sheet中第" + (i+1) + "行姓名：" + name + " 长度超过30字节<br />");
					else
						q.setName(name);
					
					
					//性别
					if(Utils.isEmptyOrNull(sex))
						result.append("第1sheet中第" + (i+1) + "行性别为空<br />");
					else if((!sex.equals("男"))&&(!sex.equals("女")))
						result.append("第1sheet中第" + (i+1) + "行性别错误<br />");
					else
						q.setSex(sex);
					
					//角色
					if(Utils.isEmptyOrNull(role))
						result.append("第1sheet中第" + (i+1) + "行角色为空<br />");
					else if((!role.equals("管理员"))&&(!role.equals("教师"))&&(!role.equals("学生")))
						result.append("第1sheet中第" + (i+1) + "行角色：" + role + " 不存在<br />");
					else{
						String roleNo="";
							if(role.equals("管理员"))
								roleNo="0";
							else if(role.equals("教师"))
								roleNo="1";
							else if(role.equals("学生"))
								roleNo="2";
							q.setRole(roleNo);
						}
						
						
					
					//所属机构
					if(Utils.isEmptyOrNull(organ))
						result.append("第1sheet中第" + (i+1) + "行所属机构为空<br />");
					else if(!organs.containsKey(organ))
						result.append("第1sheet中第" + (i+1) + "行所属机构：" + organ + " 在系统中不存在<br />");
					else{
						q.setOrgan(organs.get(organ));
					}
					
					//用户编号
					if(Utils.isEmptyOrNull(num))
						result.append("第1sheet中第" + (i+1) + "行用户编号为空<br />");
					else if(num.getBytes().length>20)
						result.append("第1sheet中第" + (i+1) + "行用户编号：" + num + " 长度超过20字节<br />");
					else if(!RegExpValidator.IsEnString(num))
						result.append("第1sheet中第"+(i+1) +"行用户编号：" + num + " 只能包含英文字母、数字和下划线<br />");
					else
						q.setNum(num);
					
					//联系电话
					if(!Utils.isEmptyOrNull(phone)){
						if((!RegExpValidator.IsTelephone(phone))&&(!RegExpValidator.IsHandset(phone)))
							result.append("第1sheet中第"+(i+1) +"行联系电话号码错误<br />");
						else
							q.setPhone(phone);
					}
					
					//邮箱地址
					if(!Utils.isEmptyOrNull(e_mail)){
						if(!RegExpValidator.isEmail(e_mail))
							result.append("第1sheet中第"+(i+1) +"行邮箱地址错误<br />");
						else
							q.setE_mail(e_mail);
						
					}
					
					//注意不要加入重复的对象和忽略空的对象
					boolean flag = true;
					for(int j = 0; j<excelUsers.size();j++){
						User eq = excelUsers.get(j);
						if(eq.getUser_ID().equals(q.getUser_ID())){
							result.append("第1sheet中第" + (i+1) + "行与第" + (j+4) + " 行用户名重复<br />");
							flag = false;
							break;
						}
					}
					if(flag == true){
						excelUsers.add(q);
					}
				}
			}
		}
		if(result.toString().equals("")){
			importUsers.put(sessionId + "_USER" , excelUsers);
			return ServiceReturnResult.SERVICE_OP_SUCC;
		}else {
			return result.toString();
		}*/
		return null;
	}

	@Override
	public String[] importExcel(String sessionId) {
	/*	String[] result = new String[2];
		String userKey = sessionId + "_USER";
		
		if(!importUsers.containsKey(userKey)){
			result[0] = ServiceReturnResult.SERVICE_OP_FAIL;
			result[1] = "请求失败：系统找不到需要导入的资源";
		}else {
			try{
				int count = 0;
				//用户的导入
				List<User> users = importUsers.get(userKey);
				for(int i=0; i<users.size(); i++){
					User q = users.get(i);			
					this.userDAO.save(q);
					
					if(i != 0 && i%50 == 0 || i == users.size()-1){
						Session session = this.sessionFactory.getCurrentSession();
						session.flush();
						session.clear();
					}
					count ++;
				}

				result[0] = ServiceReturnResult.SERVICE_OP_SUCC;
				result[1] = count+"条记录成功导入,请等待页面刷新！";
			} catch(Exception e){
				e.printStackTrace();
				result[0] = ServiceReturnResult.SERVICE_OP_FAIL;
				result[1] = ServiceReturnResult.FAILURE_SERVER_ERROR;
			}
		}
		
		//清理内存
		importUsers.remove(userKey);

		return result;*/
		return null;
	}
	
	@Override
	public List<User> getUsersByRoleName(String roleName) {
		RoleExample re = new RoleExample();
		re.or().andNameEqualTo(roleName);
		List<Role> roleList = roleMapper.selectByExample(re);
		if(roleList==null||roleList.size()==0)
			return new ArrayList<User>();
		Role role = roleMapper.selectByExample(re).get(0)!=null?roleMapper.selectByExample(re).get(0):new Role();
		List<User> users = userRoleMapper.getUsersForRoleById(role.getRoleId());
		return users;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}
	
	//getter and setter
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public DepartmentMapper getDepartmentMapper() {
		return departmentMapper;
	}

	public void setDepartmentMapper(DepartmentMapper departmentMapper) {
		this.departmentMapper = departmentMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

	public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
	}

	public UserInfoMapper getUserInfoMapper() {
		return userInfoMapper;
	}

	public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public User getUserByLoginName(String loginName) {
		UserExample ue = new UserExample();
		ue.or().andLoginNameEqualTo(loginName);
		User user = null;
		List<User> temp = this.userMapper.selectByExample(ue);
		if(temp.size()!=0) user = temp.get(0);
		return user;
	}

	@Override
	public boolean changePassword(String userLoginName, String newPassWord) {
		boolean result = true;
		try {
			UserExample ue = new UserExample();
			ue.or().andLoginNameEqualTo(userLoginName);
			List<User> temp = this.userMapper.selectByExample(ue);
			User user = new User();
			if(temp.size()>0)  user= temp.get(0);	
			else return false;
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, true);
					for (Iterator<SessionInformation> iterator2 = sessionInfos.iterator(); iterator2.hasNext();) {
						SessionInformation sessionInformation = (SessionInformation) iterator2.next();
						sessionInformation.expireNow();
					}
					break;
				}
			}
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			String passWordMd5 = md5.encodePassword(newPassWord,userLoginName);
			user.setPassword(passWordMd5);
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean lockUser(String userId) {

		boolean result = true;
		try {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, true);
					for (Iterator<SessionInformation> iterator2 = sessionInfos.iterator(); iterator2.hasNext();) {
						SessionInformation sessionInformation = (SessionInformation) iterator2.next();
						sessionInformation.expireNow();
					}
					break;
				}
			}
			user.setStatus("冻结态");
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean expireUser(String userId) {

		boolean result = true;
		try {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				if(userDetails.getUsername().equals(user.getLoginName())){
					List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, true);
					for (Iterator<SessionInformation> iterator2 = sessionInfos.iterator(); iterator2.hasNext();) {
						SessionInformation sessionInformation = (SessionInformation) iterator2.next();
						sessionInformation.expireNow();
					}
					break;
				}
			}
			user.setStatus("废弃态");
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public boolean unLockUser(String userId) {
		
		boolean result = true;
		try {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			user.setStatus("正常态");
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean recoverUser(String userId) {
		
		boolean result = true;
		try {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
			user.setStatus("正常态");
			this.userMapper.updateByPrimaryKey(user);
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<DepartmentPrivilegeDTO> getUserByCriteria(String privilegeId) {
		UserExample ue = new UserExample();
		UserExample.Criteria criteria = ue.createCriteria();
		boolean flag = true;
		List<DepartmentPrivilegeDTO> dp =new ArrayList<DepartmentPrivilegeDTO>();
		/*if(departmentId != null && !departmentId.equals("")){
			criteria.andDepartmentIdEqualTo(Integer.valueOf(departmentId));
		}
		else{
			flag = false;
		}*/
		if(privilegeId != null && !privilegeId.equals("")){
			criteria.andUserPrivilegeEqualTo(privilegeId);
		}
		else{
			flag = false;
		}
		if(flag){
			List<User>  l = userMapper.selectByExample(ue);
			for(int i=0;i<l.size();i++){
				DepartmentPrivilegeDTO d = new DepartmentPrivilegeDTO();
				d.setUserId(l.get(i).getUserId().toString());
				d.setName(l.get(i).getUserName());
				dp.add(d);
			}
		}
		return dp;
	}

	@Override
	public boolean insertUserInfo(UserInfo ui) { 
		boolean result = true;
		try {
			userInfoMapper.insertSelective(ui);
		} catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	 
	@Override
	public boolean updateUserInfo(UserInfo ui) {
		boolean result = true;
		try {
			userInfoMapper.updateByPrimaryKey(ui);
		} catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insertUserRole(Integer userId,String type) {
		boolean result = true;
		RoleExample re = new RoleExample();
		if(type.equals("teacher")){	
			re.createCriteria().andNameEqualTo("教师");
			
		}
		else{
			re.createCriteria().andNameEqualTo("学生");
		}
		try{
			int roleId  = roleMapper.selectByExample(re).get(0).getRoleId();
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRoleMapper.insert(userRole);	
		}catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkOriginalPW(String originalPw) {
		SpringSecurityUser userDetails = (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String passWordMd5 = md5.encodePassword(originalPw, userDetails.getUsername());
		if(passWordMd5.equals(userDetails.getPassword())) return true;
		else return false;
	}



	
	
	
}
