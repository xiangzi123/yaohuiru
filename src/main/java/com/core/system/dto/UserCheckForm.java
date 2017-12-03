package com.core.system.dto;

public class UserCheckForm {

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 部门名
	 */
	private String organ;
	
	/**
	 * 角色名，可以是多个
	 */
	private String roleSelectR;
	
	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 电话号
	 */
	private String telephone;
	
	/**
	 *邮件
	 */
	private String email;
	
	/**
	 * 用户姓名
	 */
	private String permitNum;
	
	/**
	 * 出生日期
	 */
	private String birthday;
	
	/**
	 * 雇佣日期
	 */
	private String hireday;

	/**
	 * 用户状态
	 */
	private String status;
	
	/**
	 *用户类型
	 */
	private String type;
	
	/**
	 * 用户性别
	 */
	private boolean sex;
	
	/**
	 * 固定电话
	 */
	private String contactNum;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHireday() {
		return hireday;
	}

	public void setHireday(String hireday) {
		this.hireday = hireday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleSelectR() {
		return roleSelectR;
	}

	public void setRoleSelectR(String roleSelectR) {
		this.roleSelectR = roleSelectR;
	}

	public String getPermitNum() {
		return permitNum;
	}

	public void setPermitNum(String permitNum) {
		this.permitNum = permitNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}


}
