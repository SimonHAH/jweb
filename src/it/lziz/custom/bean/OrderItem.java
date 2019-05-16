package it.lziz.custom.bean;

public class OrderItem {
    private Integer s_itemid;//	int	主键，订单项id
    private String s_oid;//	varchar(32)	外键，订单id
    private String s_pid;//	varchar(32)	外键，商品id
    private Integer s_buynum;//	int	购买数量

    public Integer getS_itemid() {
        return s_itemid;
    }

    public void setS_itemid(Integer s_itemid) {
        this.s_itemid = s_itemid;
    }

    public String getS_oid() {
        return s_oid;
    }

    public void setS_oid(String s_oid) {
        this.s_oid = s_oid;
    }

    public String getS_pid() {
        return s_pid;
    }

    public void setS_pid(String s_pid) {
        this.s_pid = s_pid;
    }

    public Integer getS_buynum() {
        return s_buynum;
    }

    public void setS_buynum(Integer s_buynum) {
        this.s_buynum = s_buynum;
    }

    public OrderItem() {
    }

    public OrderItem(Integer s_itemid, String s_oid, String s_pid, Integer s_buynum) {
        this.s_itemid = s_itemid;
        this.s_oid = s_oid;
        this.s_pid = s_pid;
        this.s_buynum = s_buynum;
    }
}
