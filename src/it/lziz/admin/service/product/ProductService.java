package it.lziz.admin.service.product;

import it.lziz.admin.bean.product.Product;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    /**
     * 增加一个商品
     * @param product
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
    PageHelper findPartProduct(int num) throws SQLException;

    /**
     * 按照商品主键删除商品
     * @return
     */
    boolean deleteByPid(String ... s_pid) throws SQLException;

    /**
     * 更新商品
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
    PageHelper findProductByConditions(Map<String, String[]> parameterMap) throws SQLException;

    /**
     * 分割页面
     * @return
     */
    PageHelper segPage(PageHelper ph);

    /**
     * 按照商品类别查找商品
     * @return
     * @param cid
     */
    List<Product> findProductByCid(String cid) throws SQLException;

    /**
     * 按照商品名查找
     * @return
     */
    List<Product> findProductByName(String s_pname) throws SQLException;

    Product findProductByPid(String s_pid) throws SQLException;

    /**
     * 随机查找num条记录
     * @param num 记录的条数
     * @return
     */
    PageHelper findProductRandom() throws SQLException;
}
