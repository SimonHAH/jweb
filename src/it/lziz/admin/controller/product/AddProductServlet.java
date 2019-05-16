package it.lziz.admin.controller.product;


import it.lziz.admin.bean.product.Product;

import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;

import it.lziz.admin.utils.PageHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    Product product = new Product();
    ProductService productService = new ProductServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "addProduct":
                    doPartFile(request,response);
                    addProduct(request,response);
                    break;
                case "searchProduct":
                    searchProduct(request,response);
                    break;
            }
        }

        //        boolean ret = addProduct(request,response);
//        System.out.println(ret);
    }

    /**
     * 按照条件搜索商品
     * @param request
     * @param response
     */
    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
        //1.id(primary key)  cid(category primary key)   2.name  3.(minprice  maxprice)
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String[]> parameterMap = request.getParameterMap();

        try {
            PageHelper oldPage = productService.findProductByConditions(parameterMap);
            List<Product> productList = oldPage.getProductList();
            if (productList.size() > 3){
                oldPage.setProductList(productList.subList(0,3));
            }else {
                oldPage.setProductList(productList.subList(0,productList.size()));
            }

            getServletContext().setAttribute("page",oldPage);
            getServletContext().setAttribute("oldProductList",productList);
//            request.setAttribute("page",oldPage);
            //分割一下页面
//            PageHelper newPage = productService.segPage(oldPage);
//            request.setAttribute("oldPage",oldPage);
            request.getRequestDispatcher("/admin/product/productListByConditions.jsp").forward(request,response);
//            request.getRequestDispatcher("/ProductServlet.jsp?op=findAllProduct&num=1").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个商品
     * @param request
     * @param response
     * @return
     */
    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean ret = productService.addProduct(product);
            if (ret){
                //成功
                response.getWriter().println("更新商品成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/ProductServlet?op=findAllProduct&next=inquire&num=1'");
            }else{
                //失败
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/admin/product/addProduct.jsp'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doPartFile(HttpServletRequest request, HttpServletResponse response){
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //如果为true，则是不是说明请求中包含multipart content
        if(isMultipart){
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Configure a repository (to ensure a secure temp location is used)

            ServletContext servletContext = getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            //Sets the maximum allowed size of a single uploaded file,
            //upload.setFileSizeMax(1024);
            // Parse the request
            try {
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iterator = items.iterator();
                while (iterator.hasNext()){
                    FileItem item = iterator.next();
                    if(item.isFormField()){
                        //表示是普通的表单数据
                        processFormField(item);
                    }else{
                        processUploadedFile(item);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

        }
    }
    private void processUploadedFile(FileItem item) {
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        System.out.println("file fieldName = " + fieldName);
        System.out.println("file fileName = " + fileName);
        //D:\BaiduNetdiskDownload\王道java\项目\MyWebStore\out\artifacts\MyWebStore_war_exploded\
        System.out.println(getServletContext().getRealPath(""));

        if(!fileName.isEmpty()){
            //文件名不为空，则表示选择了文件
            String realPath = getServletContext().getRealPath("images/product");

            String url = getServletContext().getContextPath()+"/images/product/"+fileName;
            product.setS_imgurl(url);

            File file = new File(realPath+"/"+fileName);

            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                item.write(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void processFormField(FileItem item) {
        String name = item.getFieldName();
        String value = null;
        try {
            value = item.getString("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        switch (name){
            case "cid":
                product.setS_cid(Integer.parseInt(value));
                break;
            case "pid":
                product.setS_pid(Integer.parseInt(value));
                break;
            case "pnum":
                product.setS_pnum(Integer.parseInt(value));
                break;
            case "pname":
                product.setS_pname(value);
                break;
            case "estoreprice":
                product.setS_estoreprice(Double.parseDouble(value));
                break;
            case "markprice":
                product.setS_markprice(Double.parseDouble(value));
                break;
            case "description":
                product.setS_desc(value);
                break;
            default:
                break;
        }
        System.out.println("form name = " + name);
        System.out.println("form value = " + value);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
