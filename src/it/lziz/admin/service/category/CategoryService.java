package it.lziz.admin.service.category;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    boolean addCategory(String cname) throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategory(int cid) throws SQLException;

    Category getCategoryByCid(String cid) throws SQLException, Exception;

    PageHelper findpageCategory(String num) throws SQLException;

    boolean deletCategories() throws SQLException;

}
