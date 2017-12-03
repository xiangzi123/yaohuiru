package com.core.common.utils;

import com.core.system.model.Privilege;
import com.core.system.model.Role;
import com.core.system.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    public static Statement connectorDataBase(){
        String url="jdbc:mysql://localhost:3306/gsimbase?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true";
        String user="root";
        String password="123456";
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库！");
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }
    public static List<Privilege> getPrivilegeList(){
        List<Privilege> privileges=new ArrayList<>();
        String url="jdbc:mysql://localhost:3306/gsimbase?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true";
        String user="root";
        String password="123456";
        Statement stmt;
        String sql = "select * from t_security_privilege";    //要执行的SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while (rs.next()){
                Privilege privilege = new Privilege();
                privilege.setPrivilegeId(rs.getInt(1));
                privilege.setParentId(rs.getInt(2));
                privilege.setDescription(rs.getString(3));
                privilege.setName(rs.getString(4));
                privilege.setDisplayName(rs.getString(5));
                privilege.setLevel(rs.getByte(6));
                privilege.setIsLeaf(rs.getBoolean(7));
                privilege.setUrl(rs.getString(8));
                privilege.setTarget(rs.getString(9));
                privilege.setOrderNum(rs.getInt(10));
                privilege.setDisplay(rs.getBoolean(11));
                privilege.setType(rs.getString(12));
                privileges.add(privilege);
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return privileges;
    }

    public static List<Role> getRoleList(String privilegeId){
        List<Role> roles = new ArrayList<>();
        String url="jdbc:mysql://localhost:3306/gsimbase?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true";
        String user="root";
        String password="123456";
        Statement stmt;
        String sql = "select distinct * from t_security_role where role_id " +
                "in (select role_id from t_security_role_privilege where privilege_id = " +"'"+ privilegeId + "')";    //要执行的SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while (rs.next()){
                Role role = new Role();
                role.setRoleId(rs.getInt(1));
                role.setName(rs.getString(2));
                role.setDescription(rs.getString(3));
                role.setParentId(rs.getInt(4));
                roles.add(role);
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public static List<User> getUserList(String userName){
        List<User> users = new ArrayList<>();
        String url="jdbc:mysql://localhost:3306/gsimbase?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true";
        String user="root";
        String password="123456";
        Statement stmt;
        Statement stmt1;
        //String sql1 = "SELECT r.role_id as 'roleId',r.name,r.description,r.parent_id as 'parentId' FROM t_security_role r,t_security_user_role ur WHERE r.role_id=ur.role_id AND ur.user_id=#{id}";
        String sql = "SELECT distinct * from t_security_user where login_name = "+"'"+userName+"'";    //要执行的SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(); //创建Statement对象
            stmt1 = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            ResultSet rs1 = null;
            while (rs.next()){
                User user1 = new User();
                user1.setUserId(rs.getInt(1));
                user1.setLoginName(rs.getString(2));
                user1.setPassword(rs.getString(3));
                user1.setStatus(rs.getString(4));
                user1.setDepartmentId(rs.getInt(5));
                user1.setUserName(rs.getString(6));

                List<Role> roles = new ArrayList<>();
                String sql1 = "select distinct * from t_security_role where role_id in (select role_id from t_security_user_role where user_id ='"+rs.getInt(1)+"')";
                rs1 = stmt1.executeQuery(sql1);//创建数据对象
                while (rs1.next()){
                    Role role = new Role();
                    role.setRoleId(rs1.getInt(1));
                    role.setName(rs1.getString(2));
                    role.setDescription(rs1.getString(3));
                    role.setParentId(rs1.getInt(4));
                    roles.add(role);
                }
                user1.setRoles(roles);
                users.add(user1);
            }
            rs.close();
            rs1.close();
            stmt.close();
            stmt1.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
