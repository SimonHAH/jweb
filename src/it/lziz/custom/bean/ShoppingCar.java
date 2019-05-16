package it.lziz.custom.bean;

import java.util.List;

public class ShoppingCar {
    private Integer s_sid;//购物车id
    private Integer s_uid;//用户id

    private List<ShoppingItem> shoppingItems;

    public Integer getS_sid() {
        return s_sid;
    }

    public void setS_sid(Integer s_sid) {
        this.s_sid = s_sid;
    }

    public Integer getS_uid() {
        return s_uid;
    }

    public void setS_uid(Integer s_uid) {
        this.s_uid = s_uid;
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public ShoppingCar() {
    }

    public ShoppingCar(Integer s_sid, Integer s_uid, List<ShoppingItem> shoppingItems) {
        this.s_sid = s_sid;
        this.s_uid = s_uid;
        this.shoppingItems = shoppingItems;
    }
}
