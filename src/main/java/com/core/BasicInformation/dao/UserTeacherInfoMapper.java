package com.core.BasicInformation.dao;

import com.core.BasicInformation.model.UserTeacherInfo;
import com.core.BasicInformation.model.UserTeacherInfoExample;


import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTeacherInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int countByExample(UserTeacherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int deleteByExample(UserTeacherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int insert(UserTeacherInfo record);
    

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int insertSelective(UserTeacherInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    List<UserTeacherInfo> selectByExample(UserTeacherInfoExample example);
    
    UserTeacherInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserTeacherInfo record, @Param("example") UserTeacherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_user_teacher_info
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    int updateByExample(@Param("record") UserTeacherInfo record, @Param("example") UserTeacherInfoExample example);
}