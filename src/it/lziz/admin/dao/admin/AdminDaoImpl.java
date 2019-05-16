package it.lziz.admin.dao.admin;

import it.lziz.admin.bean.admin.Admin;
import it.lziz.admin.utils.MyC3p0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao{

    /**
     * 管理员注册
     * @param admin
     * @return
     */
    @Override
    public boolean AdminRegister(Admin admin) {
        return false;
    }

    /**
     * 管理员登陆
     * @param admin
     */
    @Override
    public boolean AdminLogin(Admin admin) throws SQLException {

        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());//SQLException

        Admin query = qr.query("select *from s_admin where s_username = ? && s_password = ?",
                new BeanHandler<>(Admin.class), admin.getS_username(), admin.getS_password());

        System.out.println(query);

        return query!=null;
    }

    /**
     * 增加管理员
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public boolean addAdmin(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        //增加之前检测是否username重名
        Long query = (Long) qr.query("select count(*) from s_admin where s_username = ?", new ScalarHandler(), username);
        int ret = 0;
        if (query != 0){
            //说明存username管理员则更改密码
            ret = qr.update("update s_admin set s_password = ? where s_username=?",  password, username);
        }else {
            ret = qr.update("insert into s_admin values(?,?,?)", null, username, password);
        }

        return ret==1;
    }

    /**
     * 查找管理员记录条数
     */
    @Override
    public int findAllRecords() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Long query = (Long) qr.query("select count(*) from s_admin", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Admin> findPartAdmin(int limit, int offset) throws SQLException{
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Admin> query = qr.query("select *from s_admin limit ? offset ?",
                new BeanListHandler<>(Admin.class), limit, offset);
        return query;
    }
}
