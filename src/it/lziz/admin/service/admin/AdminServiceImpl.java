package it.lziz.admin.service.admin;

import it.lziz.admin.bean.admin.Admin;
import it.lziz.admin.dao.admin.AdminDao;
import it.lziz.admin.dao.admin.AdminDaoImpl;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService{

    AdminDao adminDao = new AdminDaoImpl();
    //每个分页的记录条数
    public static final int page_Count = 3;
    /**
     * 管理员注册
     * @param admin
     * @return
     */
    @Override
    public boolean AdminRegister(Admin admin) {
        return adminDao.AdminRegister(admin);
    }

    /**
     * 管理员登陆
     * @param admin
     */
    @Override
    public boolean AdminLogin(Admin admin) throws SQLException {
        return adminDao.AdminLogin(admin);
    }

    /**
     * 更改管理员密码或者增加管理员
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public boolean addAdmin(String username, String password) throws SQLException {
        return adminDao.addAdmin(username,password);
    }

    @Override
    public PageHelper findpageCategory(String num) {
        PageHelper ph = new PageHelper();
        //要显示的页码
        int int_num = Integer.parseInt(num);
        //获取数据库所有的记录条数
        int allRecords = 0;
        try {
            allRecords = adminDao.findAllRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //如果记录数等于0
        if (allRecords==0){
            ph.setTotalPageNum(0);
            ph.setPrevPageNum(0);
            ph.setNextPageNum(0);
            ph.setCurrentPageNum(0);
            return ph;
        }
        //总记录不等于0
        //设置总记录
        ph.setTotalRecordsNum(allRecords);
        //获取总记录
        int totalRecordsNum = ph.getTotalRecordsNum();
        //设置总的分页数目
        ph.setTotalPageNum((totalRecordsNum*1.0)/page_Count-totalRecordsNum/page_Count>0?(totalRecordsNum/page_Count+1):totalRecordsNum/page_Count);
        //获取总的分页
        int totalPageNum = ph.getTotalPageNum();

        List<Admin> partAdmin = null;

        //判断请求的分页范围
        if (totalPageNum<=int_num){
            //请求分页大了
            ph.setCurrentPageNum(totalPageNum);
            ph.setNextPageNum(totalPageNum);
            ph.setPrevPageNum(totalPageNum-1);

            try {
                partAdmin = adminDao.findPartAdmin(page_Count, (totalPageNum - 1) * 3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ph.setAdminList(partAdmin);
            return ph;
        }
        if (int_num<=1){
            //请求小了
            ph.setCurrentPageNum(1);
            ph.setNextPageNum(2);
            ph.setPrevPageNum(1);

            try {
                partAdmin = adminDao.findPartAdmin(page_Count, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ph.setAdminList(partAdmin);
            return ph;
        }

        //设置当前分页
        ph.setCurrentPageNum(int_num);
        //设置下一分页
        ph.setNextPageNum(int_num+1);
        //设置前一分页
        ph.setPrevPageNum(int_num-1);

        try {
            partAdmin = adminDao.findPartAdmin(page_Count, (int_num-1)*3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ph.setAdminList(partAdmin);

        return ph;
    }
}
