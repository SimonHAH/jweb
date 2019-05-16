package it.lziz.admin.service.admin;

import it.lziz.admin.bean.admin.Admin;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;

/**
 * 1.管理员注册
 * 2.管理员登陆
 * 3.管理员信息查询
 * 4.管理员信息修改
 */
public interface AdminService {

    boolean AdminRegister(Admin admin);

    boolean AdminLogin(Admin admin) throws SQLException;

    boolean addAdmin(String username,String password) throws SQLException;

    PageHelper findpageCategory(String num);
}
