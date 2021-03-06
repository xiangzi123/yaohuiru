package com.core.system.dao;

import com.core.system.model.RolePrivilege;
import com.core.system.model.RolePrivilegeExample;
import com.core.system.model.RolePrivilegeKey;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RolePrivilegeMapper {
	
	
	void delete(int roleId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int countByExample(RolePrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int deleteByExample(RolePrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int deleteByPrimaryKey(Integer integer);

    int deleteByPId(Integer integer);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int insert(RolePrivilege rolePrivilege);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int insertSelective(RolePrivilegeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    List<RolePrivilegeKey> selectByExample(RolePrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int updateByExampleSelective(@Param("record") RolePrivilegeKey record, @Param("example") RolePrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_security_role_privilege
     *
     * @mbggenerated Wed Sep 25 18:54:09 CST 2013
     */
    int updateByExample(@Param("record") RolePrivilegeKey record, @Param("example") RolePrivilegeExample example);

	String[] getPrivilegeIdsForRole(Integer roleid);
}