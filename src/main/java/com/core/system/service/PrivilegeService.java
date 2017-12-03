package com.core.system.service;

import java.util.List;

import com.core.base.service.BaseService;
//import PrivilegeAddForm;
import com.core.system.dto.PrivilegeDTO;
//import PrivilegeModifyForm;
import com.core.system.model.Privilege;

public interface PrivilegeService extends BaseService<Privilege, PrivilegeDTO> {

	public List<Privilege> getPrivilegeTree(String types);

	public Privilege getPrivilegeTreeById(int id);
	
	public String deletePrivilege(int privilegeid);
	
	public String modifyPrivilege(Privilege record);
	
	public String addPrivilege(Privilege record);
	
	public List<Privilege> getPrivilegeIdsUrls();
}
