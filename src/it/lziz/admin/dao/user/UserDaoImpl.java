package it.lziz.admin.dao.user;

import it.lziz.admin.bean.user.User;
import it.lziz.admin.utils.MyC3p0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public boolean addUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int ret = qr.update("insert into s_user values(?,?,?,?,?,?,?);", user.getS_uid(), user.getS_username()
                ,user.getS_nickname(), user.getS_password(), user.getS_email(), user.getS_birthday(), user.getS_updatetime());
        return ret == 1;
    }

    @Override
    public int findAllRecords() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Long query = (Long) qr.query("select count(*) from s_user", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<User> findPartCategory(int page_count, int i) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<User> query = qr.query("select *from s_user limit ? offset ?",
                new BeanListHandler<>(User.class), page_count, i);

        return query;
    }

    @Override
    public boolean findUserExist(String s_username) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        User user = qr.query("SELECT *FROM s_user WHERE s_username = ?"
                , new BeanHandler<>(User.class), s_username);
        return user!=null;
    }

    @Override
    public User userLogin(String s_username, String s_password) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        User user = qr.query("select *from s_user where s_username = ? and s_password = ?",
                new BeanHandler<>(User.class), s_username, s_password);
        return user;
    }

    @Override
    public User updateUserInfo(User user) throws SQLException {
        //				 s_nickname
        //				 s_password
        //				 s_email
        //				 s_birthday
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("UPDATE s_user SET s_nickname = ?,s_password = ?,s_email = ? ,s_birthday = ? WHERE s_uid = ?",
                user.getS_nickname(), user.getS_password(), user.getS_email(), user.getS_birthday(), user.getS_uid());
        User query = null;
        if (update == 1)
            query = qr.query("select *from s_user where s_uid = ?", new BeanHandler<>(User.class), user.getS_uid());
        return query;
    }
}
