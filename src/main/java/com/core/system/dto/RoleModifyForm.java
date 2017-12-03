package com.core.system.dto;

public class RoleModifyForm {

	/**
	 * 角色Id
	 */
	private Integer roleId;

	/**
	 * 角色名
	 */
	private String name;
	
	private String roleSelectR;
	
	public String getRoleSelectR() {
		return roleSelectR;
	}

	public void setRoleSelectR(String roleSelectR) {
		this.roleSelectR = roleSelectR;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 父节点Id
	 */
	private Integer parentId;
	
	
	



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
}
