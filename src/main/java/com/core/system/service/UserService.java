package com.core.system.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.core.base.service.BaseService;
import com.core.system.dto.DepartmentPrivilegeDTO;
import com.core.system.dto.UserAddForm;
import com.core.system.dto.UserCheckForm;
import com.core.system.dto.UserDTO;
import com.core.system.dto.UserModifyForm;
import com.core.system.model.Department;
import com.core.system.model.Role;
import com.core.system.model.User;
import com.core.system.model.UserInfo;

public interface UserService extends BaseService<User,UserDTO>{

	boolean validate(String username, String password);
	
	
	boolean insertUserAndRoles(UserAddForm userAddForm);
	
	boolean updateUserAndRoles(UserModifyForm userModifyForm);

	boolean deleteUser(String userId);
	
	boolean recoverUserPass(String userId);
	
	List<Role> getAllRoles();
	
	List<User> getUsersByRoleName(String roleName);

	List<Department> getAllDepartments();

	String  validateExcel(InputStream is, String sessionId);
	
	String[]  importExcel(String sessionId);
	
	User getUserByLoginName(String loginName);
	/**
	 * 检查用户名是否存在
	 * @param loginName
	 * @return
	 * @author 李彤 2013-8-20 下午3:05:50
	 */
	User validateExist(String loginName);
	
	UserModifyForm getUserRolesAndInfo(String userId);

	UserCheckForm getInfoForCheck(String userId);

	/**
	 * @param userLoginName
	 * @param newPassWord
	 * @return
	 * @author 贺群 2013-11-9 下午2:04:32
	 */
	boolean changePassword(String userLoginName, String newPassWord);

	/**
	 * @param userId
	 * @return
	 * @author 贺群 2013-11-10 下午1:40:04
	 */
	boolean lockUser(String userId);

	/**
	 * @param userId
	 * @return
	 * @author 贺群 2013-11-10 下午1:40:10
	 */
	boolean expireUser(String userId);

	/**
	 * @param userId
	 * @return
	 * @author 贺群 2013-11-10 下午3:24:21
	 */
	boolean unLockUser(String userId);

	/**
	 * @param userId
	 * @return
	 * @author 贺群 2013-11-10 下午3:24:24
	 */
	boolean recoverUser(String userId);
	
	List<DepartmentPrivilegeDTO> getUserByCriteria(String privilegeId);

	UserDTO validatePNExist(String permitNum);	
	boolean insertUserInfo(UserInfo ui);
	boolean updateUserInfo(UserInfo ui);
		
	//导入用户时，导入角色
    boolean insertUserRole(Integer userId,String type);


	/**
	 * @param originalPw
	 * @return
	 * @author 贺群 2014-3-9 上午10:09:05
	 */
	boolean checkOriginalPW(String originalPw);


	String validateLogin(String loginName, String password);


	boolean modifyPassword(String loginName, String oldPassword,String newPassword);


	boolean setIcon(HttpServletRequest request);
}
