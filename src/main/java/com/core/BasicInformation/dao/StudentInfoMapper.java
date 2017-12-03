package com.core.BasicInformation.dao;

import com.core.BasicInformation.model.StudentInfo;
import com.core.BasicInformation.model.StudentInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface StudentInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int countByExample(StudentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int deleteByExample(StudentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int insert(StudentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int insertSelective(StudentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	List<StudentInfo> selectByExample(StudentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	StudentInfo selectByPrimaryKey(Integer id);
	
	StudentInfo selectByStudentNo(String no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") StudentInfo record,
			@Param("example") StudentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") StudentInfo record,
			@Param("example") StudentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int updateByPrimaryKeySelective(StudentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_business_graduate_student_info
	 * @mbggenerated  Wed Jul 22 16:44:30 GMT+08:00 2015
	 */
	int updateByPrimaryKey(StudentInfo record);
}