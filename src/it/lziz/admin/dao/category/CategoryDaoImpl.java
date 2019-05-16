package it.lziz.admin.dao.category;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.utils.MyC3p0DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    /**
     * 增加一个商品分类
     * @param cname
     * @return
     * @throws SQLException
     */
    @Override
    public boolean addCategory(String cname) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3p0DataSource.getDataSource());

        int update = queryRunner.update("insert into category values (null,?);", cname);

        System.out.println(update);



        return update==1?true:false;
    }

    /**
     * 查找所有的商品分类 将其添加到list集合
     * @return
     * @throws SQLException
     */
    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        List<Category> query = qr.query("select *from category", new BeanListHandler<Category>(Category.class));

        return query;
    }

    /**
     * 编辑更新商品类 仅限商品类名
     * @param category
     * @return
     * @throws SQLException
     */
    @Override
    public boolean updateCategory(Category category) throws SQLException {

        boolean ret =false;

        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        try {
            int update = qr.update("update category set cname =? where cid =? ;", category.getCname(),category.getCid());

            if (update==1) {
                ret =true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("update  category error! ");
        }

        return ret;
    }

    /**
     * 删除商品类
     * @param cid 商品类主键cid
     * @return 如果删除成功返回true 否则返回false
     * @throws SQLException
     */
    @Override
    public boolean deleteCategoryById(int cid) throws SQLException {

        boolean ret =false;

        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        try {
            int update = qr.update("delete from category where cid =? ;", cid);

            System.out.println("CategoryDaoImpl.deleteCategoryById()"+update);
            if (update==1) {
                ret =true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("detete  category error! " + e.getMessage());
        }

        return ret;

    }

    /**
     * 通过商品类的主键获取此商品类
     * @param cid
     * @return bean.Category
     * @throws SQLException
     */
    @Override
    public Category getCategoryByCid(int cid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        Category query = qr.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);

        return query;
    }

    /**
     * 返回一部分页面的记录条数
     * @param limit 限制每一页显示的记录条数
     * @param offset 相对于总记录条数的偏移
     * @return
     * @throws SQLException
     */
    @Override
    public List<Category> findPartCategory(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        List<Category> query = qr.query("select *from category limit ? offset ?",
                new BeanListHandler<>(Category.class), limit, offset);

        return query;
    }

    @Override
    public int findAllRecords() throws SQLException{
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());

        Long query = (Long) qr.query("select count(*) from category", new ScalarHandler());

        return query.intValue();
    }

    /**
     * 删除所有商品类
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteCategories() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int ret = qr.update("truncate category");
        return ret==0;
    }
}
