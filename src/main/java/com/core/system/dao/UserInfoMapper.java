package com.core.system.dao;

import com.core.system.model.UserInfo;
import com.core.system.model.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int countByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int deleteByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int deleteByPrimaryKey(Integer userInfoId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int insert(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int insertSelective(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	List<UserInfo> selectByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	UserInfo selectByPrimaryKey(Integer userInfoId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int updateByExampleSelective(@Param("record") UserInfo record,
			@Param("example") UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int updateByExample(@Param("record") UserInfo record,
			@Param("example") UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int updateByPrimaryKeySelective(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_security_user_info
	 * @mbggenerated  Thu Jan 29 12:28:22 CST 2015
	 */
	int updateByPrimaryKey(UserInfo record);
}