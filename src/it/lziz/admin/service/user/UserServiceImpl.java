package it.lziz.admin.service.user;

import it.lziz.admin.bean.user.User;
import it.lziz.admin.dao.user.UserDao;
import it.lziz.admin.dao.user.UserDaoImpl;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();
    private static final int page_count = 3;

    @Override
    public boolean addUser(User user) throws SQLException {
        return userDao.addUser(user);
    }

    @Override
    public PageHelper findpageUser(String num) throws SQLException {
        PageHelper ph = new PageHelper();

        int allRecords = userDao.findAllRecords();

        if (allRecords == 0){return ph;}

        ph.setTotalRecordsNum(allRecords);

        ph.setTotalPageNum((allRecords*1.0/page_count-allRecords/page_count)>0?allRecords/page_count+1:allRecords/page_count);

        int curPage = Integer.parseInt(num);

        int totalPageNum = ph.getTotalPageNum();


        //显示第一页
        if (curPage <=1){
            ph.setCurrentPageNum(1);
            ph.setNextPageNum(2);
            ph.setPrevPageNum(1);
            List<User> partUser = userDao.findPartCategory(page_count,0);
            ph.setUserList(partUser);
            return ph;
        }
        //显示最后一页
        if (curPage >= totalPageNum){
            ph.setCurrentPageNum(totalPageNum);
            ph.setNextPageNum(totalPageNum);
            ph.setPrevPageNum(totalPageNum-1);
            List<User> partUser = userDao.findPartCategory(page_count,(totalPageNum-1)*3);
            ph.setUserList(partUser);
            return ph;
        }

        ph.setCurrentPageNum(curPage);

        ph.setPrevPageNum(curPage-1);

        ph.setNextPageNum(curPage+1);

        List<User> partUser = userDao.findPartCategory(page_count,(curPage-1)*3);

        ph.setUserList(partUser);

        return ph;
    }

    @Override
    public boolean findUserExist(String s_username) throws SQLException {
        return userDao.findUserExist(s_username);
    }

    @Override
    public User userLogin(String s_username, String s_password) throws SQLException {
        return userDao.userLogin(s_username,s_password);
    }

    @Override
    public User updateUserInfo(User user) throws SQLException {
        return userDao.updateUserInfo(user);
    }
}
