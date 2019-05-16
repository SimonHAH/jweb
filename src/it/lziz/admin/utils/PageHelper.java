package it.lziz.admin.utils;

import it.lziz.admin.bean.admin.Admin;
import it.lziz.admin.bean.category.Category;
import it.lziz.admin.bean.product.Product;
import it.lziz.admin.bean.user.User;
import it.lziz.custom.bean.Order;

import java.util.List;

public class PageHelper implements Cloneable{

    List<Order> orderList;
    List<Product> productList;
    List<User> userList;
    List<Admin> adminList;
    List<Category> categoriesList;//
    int totalRecordsNum;//总的记录数量    从数据库获取
    int currentPageNum;//当前页码   从前端页面传过来
    int totalPageNum;//总页数  计算得出
    int prevPageNum;//前一页   相对于当前页
    int nextPageNum;//后一页   相对于当前页



    public PageHelper() {
    }

    public PageHelper(List<Order> orderList, List<Product> productList, List<User> userList, List<Admin> adminList, List<Category> categoriesList, int totalRecordsNum, int currentPageNum, int totalPageNum, int prevPageNum, int nextPageNum) {
        this.orderList = orderList;
        this.productList = productList;
        this.userList = userList;
        this.adminList = adminList;
        this.categoriesList = categoriesList;
        this.totalRecordsNum = totalRecordsNum;
        this.currentPageNum = currentPageNum;
        this.totalPageNum = totalPageNum;
        this.prevPageNum = prevPageNum;
        this.nextPageNum = nextPageNum;
    }

    public PageHelper(List<Product> productList, List<User> userList, List<Admin> adminList, List<Category> categoriesList, int totalRecordsNum, int currentPageNum, int totalPageNum, int prevPageNum, int nextPageNum) {
        this.productList = productList;
        this.userList = userList;
        this.adminList = adminList;
        this.categoriesList = categoriesList;
        this.totalRecordsNum = totalRecordsNum;
        this.currentPageNum = currentPageNum;
        this.totalPageNum = totalPageNum;
        this.prevPageNum = prevPageNum;
        this.nextPageNum = nextPageNum;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
//        return prevPageNum>0?prevPageNum:1;
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
//        return nextPageNum>totalPageNum?totalPageNum:nextPageNum;
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    @Override
    public String toString() {
        return "PageHelper{" +
                "categoriesList=" + categoriesList +
                ", totalRecordsNum=" + totalRecordsNum +
                ", currentPageNum=" + currentPageNum +
                ", totalPageNum=" + totalPageNum +
                ", prevPageNum=" + prevPageNum +
                ", nextPageNum=" + nextPageNum +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
