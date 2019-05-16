package it.lziz.admin.service.product;

import it.lziz.admin.bean.product.Product;
import it.lziz.admin.dao.product.ProductDao;
import it.lziz.admin.dao.product.ProductDaoImpl;
import it.lziz.admin.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao = new ProductDaoImpl();

    private static final int page_count = 3;

    @Override
    public boolean addProduct(Product product) throws SQLException {
        return productDao.addProduct(product);
    }

    @Override
    public boolean findAllProduct() throws SQLException {
        return productDao.findAllProduct();
    }

    @Override
    public PageHelper findPartProduct(int num) throws SQLException {
        PageHelper ph = new PageHelper();

        int allRecords = productDao.findAllRecords();

        if (allRecords == 0){
            return ph;
        }

        ph.setTotalRecordsNum(allRecords);

        ph.setTotalPageNum((allRecords*1.0/page_count-allRecords/page_count)>0?allRecords/page_count+1:allRecords/page_count);

        int totalpage = ph.getTotalPageNum();

        if (num>=totalpage){
            ph.setCurrentPageNum(totalpage);
            ph.setPrevPageNum(totalpage-1);
            ph.setNextPageNum(totalpage);
            List<Product> partProduct = productDao.findPartProduct(page_count, (totalpage - 1) * 3);
            ph.setProductList(partProduct);
            return ph;
        }

        if (num<=1){
            ph.setCurrentPageNum(1);
            ph.setPrevPageNum(1);
            ph.setNextPageNum(2);
            List<Product> partProduct = productDao.findPartProduct(page_count, 0);
            ph.setProductList(partProduct);
            return ph;
        }

        ph.setCurrentPageNum(num);
        ph.setPrevPageNum(num-1);
        ph.setNextPageNum(num+1);

        List<Product> partProduct = productDao.findPartProduct(page_count, (num - 1) * 3);
        ph.setProductList(partProduct);

        return ph;
    }

    @Override
    public boolean deleteByPid(String ... s_pid) throws SQLException {
        return productDao.deleteByPid(s_pid);
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        return productDao.updateProduct(product);
    }

    @Override
    public String ProductUrl(String s_pid) throws SQLException {
        return productDao.ProductUrl(s_pid);
    }

    @Override
    public PageHelper findProductByConditions(Map<String, String[]> parameterMap) throws SQLException {
        List<Product> product = productDao.findProductByConditions(parameterMap);
        PageHelper ph = new PageHelper();
        int size = product.size();
        ph.setTotalRecordsNum(size);
        int totalPage = (size*1.0/page_count - size/page_count >0 ? size/page_count+1:size/page_count);
        ph.setTotalPageNum(totalPage);
        if (totalPage == 0){
            ph.setCurrentPageNum(0);
            ph.setPrevPageNum(0);
            ph.setNextPageNum(0);
        }
        else if (totalPage == 1){
            ph.setCurrentPageNum(1);
            ph.setPrevPageNum(1);
            ph.setNextPageNum(1);
        }
        else {
            ph.setCurrentPageNum(1);
            ph.setPrevPageNum(1);
            ph.setNextPageNum(2);
        }
        ph.setProductList(product);
        return ph;
    }

    @Override
    public PageHelper segPage(PageHelper ph) {
        PageHelper newPage = ph;

        List<Product> productList = newPage.getProductList();
        int size = productList.size();
        if (size > 3)
        {
            productList = productList.subList(0,3);
        }
        ph.setProductList(productList);

        return ph;
    }

    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {
        List<Product> product = productDao.findProductByCid(cid);

        return product;
    }

    @Override
    public List<Product> findProductByName(String s_pname) throws SQLException {
        return productDao.findProductByName(s_pname);
    }

    @Override
    public Product findProductByPid(String s_pid) throws SQLException {
        return productDao.findProductByPid(s_pid);
    }

    @Override
    public PageHelper findProductRandom() throws SQLException {
        /*

         */
        PageHelper phRandom = productDao.findProductRandom();
        return phRandom;
    }

}
