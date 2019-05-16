package it.lziz.admin.service.user;

import it.lziz.admin.bean.user.User;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;

public interface UserService {

    boolean addUser(User user) throws SQLException;

    PageHelper findpageUser(String num) throws SQLException;

    /**
     * 用户名是否存在
     * @param s_username
     * @return
     */
    boolean findUserExist(String s_username) throws SQLException;

    /**
     * 用户登录验证
     * @param s_username
     * @param s_password
     * @return
     */
    User userLogin(String s_username, String s_password) throws SQLException;

    User updateUserInfo(User user) throws SQLException;
}
