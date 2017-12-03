package com.core.system.dao;

import java.util.List;

import com.core.system.model.Role;
import com.core.system.model.User;
import com.core.system.model.UserRole;


public interface UserRoleMapper {
	List<Role> getRolesForUserById(int id);
	List<User> getUsersForRoleById(int id);
	void insert(UserRole userRole);
	String[] getRoleIdsForUser(int id);
	void deleteByUserId(int userId);
	void deleteByRoleId(int roleId);
	int getNumOfUsersForRole(int roleId);
}