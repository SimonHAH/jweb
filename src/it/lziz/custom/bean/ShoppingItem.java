package it.lziz.custom.bean;

import it.lziz.admin.bean.product.Product;

public class ShoppingItem {
    private Integer s_itemid;//购物项id
    private Integer s_sid;//购物车id
    private Integer s_pid;//商品id

    private Integer s_num;
    private Product product;

    public Integer getS_num() {
        return s_num;
    }

    public void setS_num(Integer s_num) {
        this.s_num = s_num;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getS_itemid() {
        return s_itemid;
    }

    public void setS_itemid(Integer s_itemid) {
        this.s_itemid = s_itemid;
    }

    public Integer getS_sid() {
        return s_sid;
    }

    public void setS_sid(Integer s_sid) {
        this.s_sid = s_sid;
    }

    public Integer getS_pid() {
        return s_pid;
    }

    public void setS_pid(Integer s_pid) {
        this.s_pid = s_pid;
    }

    public ShoppingItem() {
    }

    public ShoppingItem(Integer s_itemid, Integer s_sid, Integer s_pid, Integer s_num, Product product) {
        this.s_itemid = s_itemid;
        this.s_sid = s_sid;
        this.s_pid = s_pid;
        this.s_num = s_num;
        this.product = product;
    }
}
