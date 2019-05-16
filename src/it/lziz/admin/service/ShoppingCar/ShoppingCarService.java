package it.lziz.admin.service.ShoppingCar;

import it.lziz.custom.bean.Order;
import it.lziz.custom.bean.OrderItem;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingCarService {

    ShoppingCar ShoppingCarExist(Integer s_uid) throws SQLException;

    boolean SaveCar(ShoppingCar shoppingCar) throws SQLException;

    ShoppingItem SaveItem(ShoppingItem shoppingItem) throws SQLException;

    boolean ShoppingItemExist(Integer s_pid) throws SQLException;

    List<ShoppingItem> findUserAllShoppingItem(Integer s_sid) throws SQLException;

    Integer delItem(Integer s_itemid, Integer s_sid) throws SQLException;

    boolean OrderExist(String orderNumber) throws SQLException;

    boolean SaveOrder(Order order) throws SQLException;

    boolean SaveOrderItem(OrderItem orderItem) throws SQLException;

    void delItemBySid(Integer s_sid) throws SQLException;

    List<Order> findAllOrder(Integer s_uid) throws SQLException;

    void updateOrder(String s_state, String s_oid) throws SQLException;

}
