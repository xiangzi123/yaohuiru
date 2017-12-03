package com.core.system.dto;

public class PrivilegeModifyForm {
	/**
	 * 权限ID
	 */
	private Integer privilegeId;

	/**
	 * 父权限ID
	 */
	private Integer parentId;

	/**
	 * 权限描述
	 */
	private String description;

	/**
	 * 权限名
	 */
	private String name;

	/**
	 * 显示权限名
	 */
	private String displayName;

	/**
	 * 权限级
	 */
	private Byte level;

	/**
	 * 是否子权限
	 */
	private Boolean isLeaf;

	/**
	 * 权限链接
	 */
	private String url;

	/**
	 * 目标
	 */
	private String target;

	/**
	 * 顺序编号
	 */
	private Integer orderNum;

	/**
	 * 是否显示
	 */
	private Boolean display;

	/**
	 * 权限类型
	 */
	private String type;

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Byte getLevel() {
		return level;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public String getUrl() {
		return url;
	}

	public String getTarget() {
		return target;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public Boolean getDisplay() {
		return display;
	}

	public String getType() {
		return type;
	}
}
