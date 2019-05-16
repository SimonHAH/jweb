package it.lziz.custom.bean;

import java.sql.Timestamp;

public class Order {
    private String s_oid;//	varchar(32)	主键，订单id
    private Double s_money;//	double(10,2)	订单金额
    private  String s_recipients;//	varchar(32)	收件人
    private String s_tel;//	varchar(16)	电话
    private String s_address;//	varchar(255)	地址
    private Integer s_state;//	int	订单状态 1---下单 2---
    private Timestamp s_ordertime;//	datetime	下单时间
    private Integer s_uid;//	int	外键，用户id

    public String getS_oid() {
        return s_oid;
    }

    public void setS_oid(String s_oid) {
        this.s_oid = s_oid;
    }

    public Double getS_money() {
        return s_money;
    }

    public void setS_money(Double s_money) {
        this.s_money = s_money;
    }

    public String getS_recipients() {
        return s_recipients;
    }

    public void setS_recipients(String s_recipients) {
        this.s_recipients = s_recipients;
    }

    public String getS_tel() {
        return s_tel;
    }

    public void setS_tel(String s_tel) {
        this.s_tel = s_tel;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public Integer getS_state() {
        return s_state;
    }

    public void setS_state(Integer s_state) {
        this.s_state = s_state;
    }

    public Timestamp getS_ordertime() {
        return s_ordertime;
    }

    public void setS_ordertime(Timestamp s_ordertime) {
        this.s_ordertime = s_ordertime;
    }

    public Integer getS_uid() {
        return s_uid;
    }

    public void setS_uid(Integer s_uid) {
        this.s_uid = s_uid;
    }

    public Order() {
    }

    public Order(String s_oid, Double s_money, String s_recipients, String s_tel, String s_address, Integer s_state, Timestamp s_ordertime, Integer s_uid) {
        this.s_oid = s_oid;
        this.s_money = s_money;
        this.s_recipients = s_recipients;
        this.s_tel = s_tel;
        this.s_address = s_address;
        this.s_state = s_state;
        this.s_ordertime = s_ordertime;
        this.s_uid = s_uid;
    }
}
