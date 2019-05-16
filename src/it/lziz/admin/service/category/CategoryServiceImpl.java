package it.lziz.admin.service.category;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.dao.category.CategoryDao;
import it.lziz.admin.dao.category.CategoryDaoImpl;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService{

    CategoryDao categoryDao=new CategoryDaoImpl();

    @Override
    public boolean addCategory(String cname) throws SQLException {
        return categoryDao.addCategory(cname);
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        return categoryDao.findAllCategory();
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(int cid) throws SQLException {
        return categoryDao.deleteCategoryById(cid);
    }

    @Override
    public Category getCategoryByCid(String cid) throws SQLException, Exception {
        int int_cid = Integer.parseInt(cid);
        return categoryDao.getCategoryByCid(int_cid);
    }

    /**
     * 按照num查找category
     * @param num 需要查找的页面
     * @return
     */
    @Override
    public PageHelper findpageCategory(String num){
        PageHelper ph = new PageHelper();
        int allRecords = 0;
        try {
            allRecords = categoryDao.findAllRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (allRecords==0)
        {
            ph.setCurrentPageNum(0);
            ph.setNextPageNum(0);
            ph.setPrevPageNum(0);
            ph.setTotalPageNum(0);
            return ph;
        }
        //总的记录数
        ph.setTotalRecordsNum(allRecords);

        int totalRecordsNum = ph.getTotalRecordsNum();
        //每个分页3个记录
        int countPerPage=3;
        //设置总的分页数 totalRecordsNum/3 + 1
        ph.setTotalPageNum((totalRecordsNum/3.0)-(totalRecordsNum/3)>0?totalRecordsNum/3+1:totalRecordsNum/3);
        //总页面
        int pageNum = ph.getTotalPageNum();

        //设置需要查找的页马
        int int_num = Integer.parseInt(num);
        //num值的合法性
        if (pageNum<=int_num) {
            //页面超出了最大范围的处理方式 统一显示最后一页
            ph.setCurrentPageNum(pageNum);
            ph.setPrevPageNum(pageNum-1);
            ph.setNextPageNum(pageNum+1);
            List<Category> partCategory = null;
            try {
                partCategory = categoryDao.findPartCategory(countPerPage, (pageNum-1)*3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ph.setCategoriesList(partCategory);
            return ph;
        }
        if (int_num<0) {
            //不合法的页(这里处理小于等于0的页面)处理   都统一显示第一页
            ph.setCurrentPageNum(1);
            ph.setPrevPageNum(0);
            ph.setNextPageNum(2);
            List<Category> partCategory = null;
            try {
                partCategory = categoryDao.findPartCategory(countPerPage, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ph.setCategoriesList(partCategory);
            return ph;
        }
        //设置当前页
        ph.setCurrentPageNum(int_num);
        //设置前一夜后一页
        ph.setNextPageNum((int_num+1));

        ph.setPrevPageNum((int_num-1));

        //(int limit,int offset)拿到当前页需要显示的记录
        List<Category> partCategory = null;
        try {
            partCategory = categoryDao.findPartCategory(3, (int_num-1)*3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //设置当前页要显示的记录
        ph.setCategoriesList(partCategory);
        return ph;
    }

    /**
     * 删除所有商品分类
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deletCategories() throws SQLException {
        return categoryDao.deleteCategories();
    }

}
