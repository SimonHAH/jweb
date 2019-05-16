package it.lziz.admin.dao.user;

import it.lziz.admin.bean.user.User;


import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    /**
     * 增加一个客户
     * @param user
     * @return
     * @throws SQLException
     */
    boolean addUser(User user) throws SQLException;

    /**
     * 查询所有客户信息
     * @return
     */
    int findAllRecords() throws SQLException;

    /**
     * 查询部分分页
     * @param page_count
     * @param i
     * @return
     * @throws SQLException
     */
    List<User> findPartCategory(int page_count, int i) throws SQLException;

    /**
     * 用户是否存在
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

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    User updateUserInfo(User user) throws SQLException;
}
