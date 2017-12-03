package com.core.system.service;

import java.util.List;

import com.core.base.service.BaseService;
import com.core.system.dto.RoleAddForm;
import com.core.system.dto.RoleCheckForm;
import com.core.system.dto.RoleDTO;
import com.core.system.dto.RoleModifyForm;
import com.core.system.model.Privilege;
import com.core.system.model.Role;

public interface RoleService extends BaseService<Role, RoleDTO> {
	Role validateExist(Integer role_id);
	
	boolean modifyRole(RoleModifyForm roleModifyForm);
	
	RoleModifyForm getRolesAndInfo(Integer roleId);
	
	boolean insertRoles(RoleAddForm roleAddForm);
	
	RoleCheckForm getInfoForCheck(Integer roleId);
	
	String deleteRole(Integer roleId);
	
	List<Privilege> getAllPrivileges();
	//角色id和其拥有的权限id
	boolean updateRoles(Integer roleId, String privliege);
	
	List<Role> getAllRolesForPrivilege(Integer privliegeId);

	Role validateNameExist(String roleName); 
}
