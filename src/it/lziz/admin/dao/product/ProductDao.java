package it.lziz.admin.dao.product;

import it.lziz.admin.bean.product.Product;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface ProductDao {
    /**
     * 增加一个商品
     * @return
     */
    boolean addProduct(Product product) throws SQLException;

    /**
     * 查询所有的商品
     * @return
     */
    boolean findAllProduct() throws SQLException;

    /**
     * 查询部分商品
     * @return
     */
    List<Product> findPartProduct(int limit , int offset) throws SQLException;

    int findAllRecords() throws SQLException;

    /**
     * 按照商品主键s_pid去删除
     * @return
     */
    boolean deleteByPid(String ... s_pid) throws SQLException;

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    boolean updateProduct(Product product) throws SQLException;

    /**
     * 通过商品主键获取商品图片路径
     * @param s_pid
     * @return
     */
    String ProductUrl(String s_pid) throws SQLException;

    /**
     * 搜索符合条件的商品
     * @param parameterMap
     * @return
     */
    List<Product> findProductByConditions(Map<String, String[]> parameterMap) throws SQLException;

    /**
     * 按照商品类搜索商品
     * @return
     * @param cid
     */
    List<Product> findProductByCid(String cid) throws SQLException;

    /**
     * 按照商品名称搜索商品
     * @return
     * @param s_pname
     */
    List<Product> findProductByName(String s_pname) throws SQLException;

    Product findProductByPid(String s_pid) throws SQLException;

    /**
     * 随机搜索num条记录
     * @param num
     * @return
     */
    PageHelper findProductRandom() throws SQLException;
}
