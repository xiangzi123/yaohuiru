package com.core.system.dto;

public class PrivilegeCheckForm {
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

	public Integer getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName == null ? null : displayName.trim();
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target == null ? null : target.trim();
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}
}
