package it.lziz.admin.dao.category;

import it.lziz.admin.bean.category.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    boolean addCategory(String cname)throws SQLException;

    List<Category> findAllCategory() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategoryById(int cid) throws SQLException;

    Category getCategoryByCid(int cid) throws SQLException;

    List<Category> findPartCategory(int limit, int offset) throws SQLException;

    int findAllRecords() throws SQLException;

    boolean deleteCategories() throws SQLException;
}
