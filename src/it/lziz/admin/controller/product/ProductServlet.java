package it.lziz.admin.controller.product;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.bean.product.Product;
import it.lziz.admin.dao.category.CategoryDao;
import it.lziz.admin.dao.category.CategoryDaoImpl;
import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;
import it.lziz.admin.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "findAllProduct":
                    findAllProduct(request,response);
                    break;
                case "deleteOne":
                    deleteOne(request,response);
                    break;
                case "updateProduct":
                    updateProduct(request,response);
                    break;
                case "byCid":
                    byCid(request,response);
                    break;
                case "findProByName":
                    findProByName(request,response);
                    break;
                case "findProductById":
                    findProductById(request,response);
                    break;
                default:
                    break;
            }
        }
    }

    private void findProductById(HttpServletRequest request, HttpServletResponse response) {
        String s_pid = request.getParameter("pid");
        try {
            Product productByCid = productService.findProductByPid(s_pid);
            request.setAttribute("product",productByCid);
            request.getRequestDispatcher("/productdetail.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    private void findProByName(HttpServletRequest request, HttpServletResponse response) {
        //ProductServlet?op=findProByName&pname=lotus莲花超跑&Search=+
        String s_pname = request.getParameter("pname");
//        PageHelper ph = productService.findProductByName(s_pname);
        try {
            List<Product> productsByName = productService.findProductByName(s_pname);
            request.setAttribute("products",productsByName);
//            PageHelper partProduct = productService.findPartProduct(1);
//            List<Product> products = partProduct.getProductList();
//            request.setAttribute("products",products);
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    private void byCid(HttpServletRequest request, HttpServletResponse response) {
        ///ProductServlet?op=byCid&cid=${category.cid}
        //返回Product
        String cid = request.getParameter("cid");
        try {
            List<Product> product = productService.findProductByCid(cid);
            request.setAttribute("products",product);
            request.getRequestDispatcher("/products.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 编辑更新商品
     * @param request
     * @param response
     */
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {

        String s_imgurl = request.getParameter("imgurl");
        String s_pid = request.getParameter("pid");
        String s_pnum = request.getParameter("pnum");
        String s_pname = request.getParameter("pname");
        String s_estoreprice = request.getParameter("estoreprice");
        String s_markprice = request.getParameter("markprice");
        String s_desc = request.getParameter("description");
        String s_cid = request.getParameter("cid");

        Category categoryByCid = null;

        try {
            categoryByCid = categoryDao.getCategoryByCid(Integer.parseInt(s_cid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Product product = new Product(Integer.parseInt(s_pid),s_pname,Double.parseDouble(s_estoreprice),
                Double.parseDouble(s_markprice),Integer.parseInt(s_pnum),Integer.parseInt(s_cid),s_imgurl,s_desc,categoryByCid);

        try {
            boolean ret = productService.updateProduct(product);
            if (ret){
                response.getWriter().println("编辑修改成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1'");
            }else {
                response.getWriter().println("编辑修改失败");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个商品
     * @param request
     * @param response
     */
    private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String s_pid = request.getParameter("pid");
        try {
            boolean ret = productService.deleteByPid(s_pid);
            if (ret){
                response.getWriter().println("删除成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1'");
            }else {
                response.setHeader("refresh","url='"+request.getContextPath()+"/admin/product/addProduct.jsp'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有商品
     * @param request
     * @param response
     */
    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) {
        int num = Integer.parseInt(request.getParameter("num"));
        String next = request.getParameter("next");
        if ("inquire".equals(next)){
            try {
                PageHelper ph = productService.findPartProduct(num);
                request.setAttribute("page",ph);
                request.getRequestDispatcher("/admin/product/productList.jsp").forward(request,response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            PageHelper page = (PageHelper) getServletContext().getAttribute("page");
            List<Product> oldProductList = (List<Product>) getServletContext().getAttribute("oldProductList");
            int totalPageNum = page.getTotalPageNum();
            if (totalPageNum == 0){

            }else if(totalPageNum == 1){

            }else {
                List<Product> products = oldProductList.subList((num - 1) * 3, num * 3 > oldProductList.size() ? oldProductList.size(): num * 3);
                page.setProductList(products);
                page.setCurrentPageNum(num);

                if (totalPageNum < num + 1)
                    page.setNextPageNum(num);
                else
                    page.setNextPageNum(num+1);
                if (num-1==0)
                    page.setPrevPageNum(num);
                else
                    page.setPrevPageNum(num-1);
                getServletContext().setAttribute("page",page);
                getServletContext().setAttribute("oldProductList",oldProductList);
            }

            try {
                request.getRequestDispatcher("/admin/product/productListByConditions.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
