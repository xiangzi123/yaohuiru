package com.core.BasicInformation.model;

public class UserTeacherInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_user_teacher_info.user_id
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_user_teacher_info.user_name
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    private String userName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_user_teacher_info.user_id
     *
     * @return the value of v_user_teacher_info.user_id
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_user_teacher_info.user_id
     *
     * @param userId the value for v_user_teacher_info.user_id
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_user_teacher_info.user_name
     *
     * @return the value of v_user_teacher_info.user_name
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_user_teacher_info.user_name
     *
     * @param userName the value for v_user_teacher_info.user_name
     *
     * @mbggenerated Tue Jul 21 17:01:47 CST 2015
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}