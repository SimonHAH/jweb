package it.lziz.custom.controller;

import com.mysql.cj.Session;
import it.lziz.admin.bean.category.Category;
import it.lziz.admin.bean.product.Product;
import it.lziz.admin.service.category.CategoryService;
import it.lziz.admin.service.category.CategoryServiceImpl;
import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;
import it.lziz.admin.utils.PageHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

//@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    ProductService productService = new ProductServiceImpl();
    CategoryService categoryService = new CategoryServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //        response.setContentType("image/jpeg");
        System.out.println("MainServlet自启动...");
        ServletContext servletContext = config.getServletContext();
        try {
            List<Category> allCategory = categoryService.findAllCategory();

            servletContext.setAttribute("categories",allCategory);
//            request.setAttribute("categories",allCategory);
//            Random random = new Random();
//            int num = random.nextInt(4) + 3;
//            PageHelper pageHelper = productService.findProductRandom(num);
            PageHelper ph = productService.findPartProduct(6);
            PageHelper partProduct = productService.findPartProduct(7);
            List<Product> list = partProduct.getProductList();
            List<Product> productList = ph.getProductList();
            productList.addAll(list);
            servletContext.setAttribute("productTop",productList);
//            request.setAttribute("productTop",productList);

            servletContext.setAttribute("hotProducts",productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
