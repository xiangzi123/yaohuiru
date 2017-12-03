package com.core.system.dto;

public class RoleAddForm {
	
	private Integer role_id;
	
	private String name;
	
	private String roleSelectR;
	
	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getRoleSelectR() {
		return roleSelectR;
	}

	public void setRoleSelectR(String roleSelectR) {
		this.roleSelectR = roleSelectR;
	}

	private String description;
	
	private Integer parentId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//public Integer getRole_id() {
	//	return role_id;
	//}

	//public void setRole_id(Integer role_id) {
	//	this.role_id = role_id;
	//}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



}
