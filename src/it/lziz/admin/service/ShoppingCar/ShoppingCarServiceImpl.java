package it.lziz.admin.service.ShoppingCar;

import it.lziz.admin.dao.shoppingcar.ShoppingCarDao;
import it.lziz.admin.dao.shoppingcar.ShoppingCarDaoImpl;
import it.lziz.custom.bean.Order;
import it.lziz.custom.bean.OrderItem;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;

import java.sql.SQLException;
import java.util.List;

public class ShoppingCarServiceImpl implements ShoppingCarService{
    ShoppingCarDao shoppingCarDao = new ShoppingCarDaoImpl();
    @Override
    public ShoppingCar ShoppingCarExist(Integer s_uid) throws SQLException {
        return shoppingCarDao.ShoppingCarExist(s_uid);
    }

    @Override
    public boolean SaveCar(ShoppingCar shoppingCar) throws SQLException {
        return shoppingCarDao.SaveCar(shoppingCar);
    }

    @Override
    public ShoppingItem SaveItem(ShoppingItem shoppingItem) throws SQLException {
        return shoppingCarDao.SaveItem(shoppingItem);
    }

    @Override
    public boolean ShoppingItemExist(Integer s_pid) throws SQLException {
        return shoppingCarDao.ShoppingItemExist(s_pid);
    }

    @Override
    public List<ShoppingItem> findUserAllShoppingItem(Integer s_sid) throws SQLException {
        return shoppingCarDao.findUserAllShoppingItem(s_sid);
    }

    @Override
    public Integer delItem(Integer s_itemid, Integer s_sid) throws SQLException {
        return shoppingCarDao.delItem(s_itemid,s_sid);
    }

    @Override
    public boolean OrderExist(String orderNumber) throws SQLException {
        return shoppingCarDao.OrderExist(orderNumber);
    }

    @Override
    public boolean SaveOrder(Order order) throws SQLException {
        return shoppingCarDao.SaveOrder(order);
    }

    @Override
    public boolean SaveOrderItem(OrderItem orderItem) throws SQLException {
        return shoppingCarDao.SaveOrderItem(orderItem);
    }

    @Override
    public void delItemBySid(Integer s_sid) throws SQLException {
        shoppingCarDao.delItemBySid(s_sid);
    }

    @Override
    public List<Order> findAllOrder(Integer s_uid) throws SQLException {
        return shoppingCarDao.findAllOrder(s_uid);
    }

    @Override
    public void updateOrder(String s_state, String s_oid) throws SQLException {
        shoppingCarDao.updateOrder(s_state,s_oid);
    }
}
