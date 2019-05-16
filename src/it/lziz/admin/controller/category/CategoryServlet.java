package it.lziz.admin.controller.category;

import it.lziz.admin.bean.category.Category;
import it.lziz.admin.service.category.CategoryService;
import it.lziz.admin.service.category.CategoryServiceImpl;
import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;
import it.lziz.admin.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService categoryService = new CategoryServiceImpl();
    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        * CategoryServlet?op=findAllCategory&num=1
        */
/*        response.setCharacterEncoding("utf-8");*/
        //deleteMulti
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op!=null && !op.isEmpty())
        {
            switch (op){
                case "addCategory":
                    addCategory(request,response);
                    break;
                case "findAllCategory":
                    findAllCategory(request,response);
                    break;
                case "updateCategory":
                    updateCategory(request,response);
                    break;
                case "toupdateCategory":
                    toupdateCategory(request,response);
                    break;
                case "deleteCategory":
                    deleteCategory(request,response);
                    break;
                case "deleteMulti":
                    deleteMulti(request,response);
                case "findCategory":
                    findCategory(request,response);
                    break;
                case "findCategoryByUpdate":
                    findCategoryByUpdate(request,response);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * /CategoryServlet?op=findCategoryByUpdate&pid=${product.s_pid}
     * @param request
     * @param response
     */
    private void findCategoryByUpdate(HttpServletRequest request, HttpServletResponse response) {
        String s_pid = request.getParameter("pid");

        try {
            List<Category> categories = categoryService.findAllCategory();

            String url = productService.ProductUrl(s_pid);
            request.setAttribute("categories",categories);
            request.setAttribute("s_pid",s_pid);
            request.setAttribute("s_url",url);
            System.out.println(url);
            request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * /CategoryServlet?op=findCategory
     * @param request
     * @param response
     */
    private void findCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> categories = categoryService.findAllCategory();
            request.setAttribute("categories",categories);
            String next = request.getParameter("next");
            if ("add".equals(next)){
                request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request,response);
            }
            if ("search".equals(next)){
                request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除全部分类
     * @param request
     * @param response
     */
    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean ret = categoryService.deletCategories();
            if (ret){
                //删除成功跳转到findAllcategory()
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑修改商品类
     * @param request
     * @param response
     */
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {

        String cid = request.getParameter("cid");
        String cname = null;
        try {
            cname = new String(request.getParameter("cname").getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Category category = new Category(Integer.parseInt(cid),cname);
        try {
            boolean ret = categoryService.updateCategory(category);
            if (ret){
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除商品类
     * @param request
     * @param response
     */
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取商品类cid
        String cid = request.getParameter("cid");
        int int_cid = Integer.parseInt(cid);
        //调用Service方法
        boolean ret = false;
        try {
            ret = categoryService.deleteCategory(int_cid);
            response.setHeader("refresh","1;url='"+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1'");
        } catch (SQLException e) {
            response.getWriter().println("删除失败");
            response.setHeader("refresh","1;url='"+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1'");
        }
    }

    /**
     * 编辑修改商品
     * @param request
     * @param response
     */
    private void toupdateCategory(HttpServletRequest request, HttpServletResponse response) {
//        String cid = request.getParameter("cid");

        try {
/*            Category categoryByCid = categoryService.getCategoryByCid(cid);
            request.setAttribute("categoryByCid",categoryByCid);*/
            request.getRequestDispatcher("/admin/category/updateCategory.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新分类，成功则重定向到CategoryServlet?op=findAllCategory
     * @param request
     * @param response
     * @throws IOException
     */
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取分类名
        String cname = new String(request.getParameter("cname").getBytes("iso-8859-1"), "utf-8");
/*
        String cname = request.getParameter("cname");
*/

        try {
            //调用dao添加到数据库 返回true则成功
            boolean flag = categoryService.addCategory(cname);
            if (flag){
                response.getWriter().println("操作成功");
                //跳转
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1'");
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("服务器着火了..."+e.getCause());
        }

    }

    /**
     * 查找到所有的分类 转发给categoryList.jsp
     * @param request
     * @param response
     */
    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        //处理一下num值

        PageHelper ph = null;
        try {
            ph = categoryService.findpageCategory(num);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("page",ph);

/*        for (Category category : ph.getCategoriesList()) {
            System.out.println(category.getCid());
            System.out.println(category.getCname());
        }*/

        request.getRequestDispatcher("admin/category/categoryList.jsp").forward(request,response);
/*        try {
            List<Category> categoriesList = categoryService.findAllCategory();
            request.setAttribute("categoriesList",categoriesList);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
