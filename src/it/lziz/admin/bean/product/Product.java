package it.lziz.admin.bean.product;

import it.lziz.admin.bean.category.Category;


public class Product implements Cloneable{
    private Integer s_pid;
    private String s_pname;
    private Double s_estoreprice;
    private Double s_markprice;
    private Integer s_pnum;
    private Integer s_cid;
    private String s_imgurl;
    private String s_desc;
    private Category category;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("?????????");
        return super.clone();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {
    }

    public Integer getS_pid() {
        return s_pid;
    }

    public void setS_pid(Integer s_pid) {
        this.s_pid = s_pid;
    }

    public String getS_pname() {
        return s_pname;
    }

    public void setS_pname(String s_pname) {
        this.s_pname = s_pname;
    }

    public Double getS_estoreprice() {
        return s_estoreprice;
    }

    public void setS_estoreprice(Double s_estoreprice) {
        this.s_estoreprice = s_estoreprice;
    }

    public Double getS_markprice() {
        return s_markprice;
    }

    public void setS_markprice(Double s_markprice) {
        this.s_markprice = s_markprice;
    }

    public Integer getS_pnum() {
        return s_pnum;
    }

    public void setS_pnum(Integer s_pnum) {
        this.s_pnum = s_pnum;
    }

    public Integer getS_cid() {
        return s_cid;
    }

    public void setS_cid(Integer s_cid) {
        this.s_cid = s_cid;
    }

    public String getS_imgurl() {
        return s_imgurl;
    }

    public void setS_imgurl(String s_imgurl) {
        this.s_imgurl = s_imgurl;
    }

    public String getS_desc() {
        return s_desc;
    }

    public void setS_desc(String s_desc) {
        this.s_desc = s_desc;
    }

    public Product(Integer s_pid, String s_pname, Double s_estoreprice, Double s_markprice, Integer s_pnum, Integer s_cid, String s_imgurl, String s_desc, Category category) {
        this.s_pid = s_pid;
        this.s_pname = s_pname;
        this.s_estoreprice = s_estoreprice;
        this.s_markprice = s_markprice;
        this.s_pnum = s_pnum;
        this.s_cid = s_cid;
        this.s_imgurl = s_imgurl;
        this.s_desc = s_desc;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "s_pid=" + s_pid +
                ", s_pname='" + s_pname + '\'' +
                ", s_estoreprice='" + s_estoreprice + '\'' +
                ", s_markprice='" + s_markprice + '\'' +
                ", s_pnum='" + s_pnum + '\'' +
                ", s_cid='" + s_cid + '\'' +
                ", s_imgurl='" + s_imgurl + '\'' +
                ", s_desc='" + s_desc + '\'' +
                '}';
    }
}
