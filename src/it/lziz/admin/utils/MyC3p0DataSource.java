package it.lziz.admin.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyC3p0DataSource {

    private  static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource();

    }

    /**
     * 返回一个mysql Connection
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return  dataSource.getConnection();
    }

    /**
     * 返回mysql DataSource
     * @return DataSource
     * @throws SQLException
     */
    public static  DataSource getDataSource() throws SQLException {
        return dataSource;

    }

    /**
     * 关闭数据库的资源
     * @param rs 获取的数据库数据集
     * @param st 操作数据库的句柄
     * @param conn  数据库的连接
     */
    public static void releaseDBResource(ResultSet rs, Statement st, Connection conn) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
