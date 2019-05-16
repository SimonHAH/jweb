package it.lziz.admin.dao.admin;

import it.lziz.admin.bean.admin.Admin;

import java.sql.SQLException;
import java.util.List;

/**
 * 1.管理员注册
 * 2.管理员登陆
 * 3.管理员信息查询
 * 4.管理员信息修改
 */
public interface AdminDao {

    boolean AdminRegister(Admin admin);

    boolean AdminLogin(Admin admin) throws SQLException;

    boolean addAdmin(String username,String password) throws SQLException;

    int findAllRecords() throws SQLException;

    List<Admin> findPartAdmin(int limit, int offset) throws SQLException;
}
