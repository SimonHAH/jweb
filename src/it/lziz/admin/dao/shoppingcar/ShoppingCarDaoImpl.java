package it.lziz.admin.dao.shoppingcar;

import it.lziz.admin.utils.MyC3p0DataSource;
import it.lziz.custom.bean.Order;
import it.lziz.custom.bean.OrderItem;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ShoppingCarDaoImpl implements ShoppingCarDao {
    @Override
    public ShoppingCar ShoppingCarExist(Integer s_uid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        ShoppingCar query = qr.query("select *from s_shoppingcar where s_uid = ?",
                new BeanHandler<>(ShoppingCar.class), s_uid);//这里的List<ShoppingItem> = null
        return query;
    }

    @Override
    public boolean SaveCar(ShoppingCar shoppingCar) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("insert into s_shoppingcar values(?,?)", shoppingCar.getS_sid(), shoppingCar.getS_uid());
        return update == 1;
    }

    @Override
    public ShoppingItem SaveItem(ShoppingItem shoppingItem) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("insert into s_shoppingitem values(?,?,?)", shoppingItem.getS_itemid(),
                shoppingItem.getS_sid(), shoppingItem.getS_pid());

        ShoppingItem query = null;
        if (update == 1){
            query = qr.query("select *from s_shoppingitem where s_pid = ?",
                    new BeanHandler<>(ShoppingItem.class), shoppingItem.getS_pid());
        }
        shoppingItem.setS_itemid(query.getS_itemid());
        return shoppingItem;
    }

    @Override
    public boolean ShoppingItemExist(Integer s_pid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        ShoppingItem query = qr.query("select *from s_shoppingitem where s_pid = ?", new BeanHandler<>(ShoppingItem.class), s_pid);

        return query != null;
    }

    @Override
    public List<ShoppingItem> findUserAllShoppingItem(Integer s_sid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<ShoppingItem> query = qr.query("select *from s_shoppingitem where s_sid = ?",
                new BeanListHandler<>(ShoppingItem.class), s_sid);
        return query;
    }

    @Override
    public Integer delItem(Integer s_itemid, Integer s_sid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("delete from s_shoppingitem where s_itemid = ?",s_itemid);
        return update;
    }

    @Override
    public boolean OrderExist(String orderNumber) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        Order query = qr.query("select *from s_Order where s_oid = ?",
                new BeanHandler<>(Order.class), orderNumber);

        return query != null;
    }

    @Override
    public boolean SaveOrder(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("insert into s_Order values(?,?,?,?,?,?,?,?)",
                order.getS_oid(),order.getS_money(),order.getS_recipients(),order.getS_tel(),order.getS_address()
        ,order.getS_state(),order.getS_ordertime(),order.getS_uid());
        return update==1;
    }

    @Override
    public boolean SaveOrderItem(OrderItem orderItem) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        int update = qr.update("insert into s_OrderItem values(?,?,?,?)", orderItem.getS_itemid(),
                orderItem.getS_oid(), orderItem.getS_pid(), orderItem.getS_buynum());

        return update == 1;
    }

    @Override
    public void delItemBySid(Integer s_sid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        qr.update("delete from s_shoppingitem where s_sid = ?",s_sid);
    }

    @Override
    public List<Order> findAllOrder(Integer s_uid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        List<Order> query = qr.query("select *from s_Order where s_uid = ?",
                new BeanListHandler<>(Order.class), s_uid);
        return query;
    }

    @Override
    public void updateOrder(String s_state, String s_oid) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3p0DataSource.getDataSource());
        qr.update("update s_Order set s_state = ? where s_oid = ?",s_state,s_oid);
    }
}
