package it.lziz.admin.dao.product;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.bean.product.Product;
import it.lziz.admin.utils.MyC3p0DataSource;

import it.lziz.admin.utils.PageHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ProductDaoImpl implements ProductDao{

    @Override
    public boolean addProduct(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("insert into s_product values(?,?,?,?,?,?,?,?);", product.getS_pid()
                , product.getS_pname(), product.getS_estoreprice(), product.getS_markprice(),
                product.getS_pnum(), product.getS_cid(), product.getS_imgurl(), product.getS_desc());
//        qr.query("insert into s_product values(?,?,?,?,?,?,?,?);");
        return update == 1;
    }

    @Override
    public boolean findAllProduct() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Product> query = qr.query("select *from s_product", new BeanListHandler<>(Product.class));
        return query==null;
    }

    @Override
    public List<Product> findPartProduct(int limit,int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Product> query = qr.query("SELECT * FROM s_product LIMIT ? OFFSET ?;"
                , new BeanListHandler<>(Product.class), limit, offset);
        for (Product product : query) {
            Category category = qr.query("select *from category where cid=?",
                    new BeanHandler<>(Category.class), product.getS_cid());
            product.setCategory(category);
        }
        return query;
    }

    @Override
    public int findAllRecords() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Long query = (Long) qr.query("select count(*) from s_product", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public boolean deleteByPid(String... s_pid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = 0;
        for (String s : s_pid) {
            update = qr.update("DELETE FROM s_product WHERE s_pid = ?;", Integer.parseInt(s));
        }

        return update==1;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("update s_product set s_pname=?, s_estoreprice=?, s_markprice=?, s_pnum=?, s_cid=?, s_desc=? where s_pid=?",
                product.getS_pname(), product.getS_estoreprice(), product.getS_markprice(), product.getS_pnum(), product.getS_cid(),
                product.getS_desc(), product.getS_pid());
        return update == 1;
    }

    @Override
    public String ProductUrl(String s_pid) throws SQLException {
        int int_pid = Integer.parseInt(s_pid);
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Product query = qr.query("select * from s_product where s_pid = ?", new BeanHandler<>(Product.class), int_pid);
        return query.getS_imgurl();
    }

    @Override
    public List<Product> findProductByConditions(Map<String, String[]> parameterMap) throws SQLException {
        Set<String> keySet = parameterMap.keySet();

        //1.int id(primary key)  int cid(category primary key)   2.string name  3.double(minprice  maxprice)
        StringBuilder sb = new StringBuilder();
        sb.append("select *from s_product where ");

        int count = 0;
        for (String key : keySet) {


            switch (key){
                case "id":
                    if (parameterMap.get(key)[0].equals(""))
                        break;
                    if (count++ != 0){
                        sb.append("and ");
                    }
                    sb.append("s_pid=");
                    sb.append(parameterMap.get(key)[0]);
                    sb.append(" ");
                    break;
                case "cid":
                    if (parameterMap.get(key)[0].equals(""))
                        break;
                    if (count++ != 0){
                        sb.append("and ");
                    }
                    sb.append("s_cid=");
                    sb.append(parameterMap.get(key)[0]);
                    sb.append(" ");
                    break;
                case "name":
                    if (parameterMap.get(key)[0].equals(""))
                        break;
                    if (count++ != 0){
                        sb.append("and ");
                    }
                    sb.append("s_pname=");
                    sb.append("'"+parameterMap.get(key)[0]+"'");
                    sb.append(" ");
                    break;
                case "minprice":
                    if (count++ != 0){
                        sb.append("and ");
                    }
                    sb.append("s_estoreprice ");
                    sb.append("between ");
                    String s1 = parameterMap.get(key)[0];

                    if (!Pattern.compile("^[-\\+]?[\\d]*$").matcher(s1).matches()||
                            parameterMap.get(key)[0].equals("")){
                        //不是整数 下限按照0处理
                        sb.append("0 ");
                    }
                    else{
                        //是整数
                        sb.append(parameterMap.get(key)[0]);
                        sb.append(" ");
                    }
                    break;
                case "maxprice":
                    if (count++ != 0){
                        sb.append("and ");
                    }
                    String s2 = parameterMap.get(key)[0];//获取maxprice对应的值
                    //输入为空 按照无穷大处理
                    if (!Pattern.compile("^[-\\+]?[\\d]*$").matcher(s2).matches() ||
                            parameterMap.get(key)[0].equals("")){
                        //不是整数 下限按照无穷大处理
                        sb.append(Double.MAX_VALUE);
                    }
                    else{
                        //是整数
                        sb.append(parameterMap.get(key)[0]);
                    }

                    sb.append(" ");
                    break;
                default:
                    break;
            }
        }
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Product> query = qr.query(sb.toString(), new BeanListHandler<>(Product.class));

        for (Product product : query) {
            Category category = qr.query("select *from category where cid=?",
                    new BeanHandler<>(Category.class), product.getS_cid());
            product.setCategory(category);
        }

        return query;
    }

    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int int_cid = Integer.parseInt(cid);
        List<Product> product = qr.query("SELECT *FROM s_product WHERE s_cid = ?;",new BeanListHandler<>(Product.class), int_cid);

        return product;
    }

    @Override
    public List<Product> findProductByName(String s_pname) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Product> query = qr.query("SELECT *FROM s_product WHERE s_pname = ?;", new BeanListHandler<>(Product.class),s_pname);
        return query;
    }

    @Override
    public Product findProductByPid(String s_pid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Product query = qr.query("select *from s_product where s_pid = ?", new BeanHandler<>(Product.class), s_pid);

        return query;
    }

    @Override
    public PageHelper findProductRandom() throws SQLException {
        PageHelper ph = new PageHelper();
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Product> query = qr.query("SELECT *FROM s_product WHERE s_pid>= (SELECT FLOOR(RAND() * (SELECT MAX(s_pid) FROM s_product))) ORDER BY s_pid;",
                new BeanListHandler<>(Product.class));

        for (Product product : query) {
            Category category = qr.query("select *from category where cid=?",
                    new BeanHandler<>(Category.class), product.getS_cid());
            product.setCategory(category);
        }
        ph.setProductList(query);
        return ph;
    }

}
