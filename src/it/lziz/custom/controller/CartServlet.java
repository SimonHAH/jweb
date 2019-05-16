package it.lziz.custom.controller;

import it.lziz.admin.bean.product.Product;
import it.lziz.admin.bean.user.User;
import it.lziz.admin.service.ShoppingCar.ShoppingCarService;
import it.lziz.admin.service.ShoppingCar.ShoppingCarServiceImpl;
import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    ShoppingCarService shoppingCarService = new ShoppingCarServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ///CartServlet?op=addCart&pid=41&uid=4
        String op = request.getParameter("op");
        if (op!=null && !"".equals(op)){
            switch (op){
                case "findCart":
                    findCart(request,response);
                    break;
                case "addCart":
                    addCart(request,response);
                    break;
                case "delItem":
                    delItem(request,response);
                default:
                    break;
            }
        }
    }

    /**
     * 删除购物车的某项商品
     * @param request
     * @param response
     */
    private void delItem(HttpServletRequest request, HttpServletResponse response) {
        //CartServlet?op=delItem&uid=4&itemid=38
        String s_uid = request.getParameter("uid");//用户id
        String s_itemid = request.getParameter("itemid");//订单项id

        Integer s_sid = null;

        try {
            //找到此用户的购物车
            ShoppingCar sc = shoppingCarService.ShoppingCarExist(Integer.parseInt(s_uid));
            s_sid = sc.getS_sid();//此用户购物车id
            //从数据库删除此项
            shoppingCarService.delItem(Integer.parseInt(s_itemid),s_sid);
            //更新购物车的此项数据
            ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
            List<ShoppingItem> shoppingItems = shoppingCar.getShoppingItems();
            for (ShoppingItem shoppingItem : shoppingItems) {
                if(shoppingItem.getS_itemid().toString().equals(s_itemid) ) {
                    shoppingItems.remove(shoppingItem);
                    break;
                }
            }
            //将新的shoppingcar加入域
            request.getSession().setAttribute("shoppingCar",shoppingCar);
            //
            request.getRequestDispatcher("/shoppingcart.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加到购物车
     * @param request
     * @param response
     */
    private void addCart(HttpServletRequest request, HttpServletResponse response) {
        //CartServlet?op=addCart&pid=1&uid=4&snum=2
        String s_pid = request.getParameter("pid");//product表主键
        String s_uid = request.getParameter("uid");//user表主键
        String s_num = request.getParameter("s_num");
        //
        try {
            //shoppingCar是否以及存在
            ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");

            Product productByPid = productService.findProductByPid(s_pid);

            if (shoppingCar!=null) {
                boolean flag = false;
                //购物车已经存在这个物品
                for (ShoppingItem shoppingItem : shoppingCar.getShoppingItems()) {
                    if (s_pid.equals(shoppingItem.getS_pid().toString())) {
                        flag = true;//说明加入的物品已经存在
                        //证明这次要计入的物品已经存在了 只需要s_num+1
                        shoppingItem.setS_num(shoppingItem.getS_num() + 1);
                        break;
                    }
                }
                //购物车不存在这个物品
                if (!flag) {
                    //初始化购物项
                    ShoppingItem shoppingItem = new ShoppingItem();
                    shoppingItem.setProduct(productByPid);
                    shoppingItem.setS_num(Integer.parseInt(s_num));
                    shoppingItem.setS_pid(Integer.parseInt(s_pid));
                    shoppingItem.setS_sid(shoppingCar.getS_sid());
                    //存入数据库 并返回新的shoppingItem
                    shoppingItem = shoppingCarService.SaveItem(shoppingItem);

                    shoppingCar.getShoppingItems().add(shoppingItem);
                }
            }
//            }else{
//                //还没有购物车 这是第一次启动进入购物篮
//                //找出这个用户的购物车
//                shoppingCar = shoppingCarService.ShoppingCarExist(Integer.parseInt(s_uid));
//                //查找该用户之前的购物车记录
//                List<ShoppingItem> oldUserItems = shoppingCarService.findUserAllShoppingItem(shoppingCar.getS_sid());
//                for (ShoppingItem oldUserItem : oldUserItems) {
//                    Product product = productService.findProductByPid(oldUserItem.getS_pid().toString());
//                    oldUserItem.setProduct(product);
//                    //之前购物车有多少件这个商品
//                    oldUserItem.setS_num(1);
//                }
//
//                //将这些记录添加到购物车
//                if (oldUserItems!=null)
//                    shoppingCar.setShoppingItems(oldUserItems);
//
//                //初始化购物车物品列表List<ShoppingItem> shoppingItems
//                List<ShoppingItem> ShoppingItems = new ArrayList<>();
//                //初始化购物项
//                ShoppingItem shoppingItem = new ShoppingItem();
//                shoppingItem.setProduct(productByPid);
//                shoppingItem.setS_num(Integer.parseInt(s_num));
//                shoppingItem.setS_pid(Integer.parseInt(s_pid));
//                shoppingItem.setS_sid(shoppingCar.getS_sid());
//
//                //存入之前先检查是否有这项
//                boolean ret = shoppingCarService.ShoppingItemExist(shoppingItem.getS_pid());
//
//                if (ret){
//                    //存在这项就吧s_num+1
//                    shoppingItem.setS_num(shoppingItem.getS_num()+1);
//                    //更新数据库 数据库暂时没有记录物品条数的键
//
//                }else {
//                    //否则计入这项商品
//                    shoppingItem = shoppingCarService.SaveItem(shoppingItem);
//                    shoppingCar.getShoppingItems().add(shoppingItem);
//                }
//
//            }
            request.getSession().setAttribute("shoppingCar",shoppingCar);
            //
//            response.setHeader("refresh","1;url='"+request.getContextPath()+"/shoppingcart.jsp'");
            request.getRequestDispatcher("/shoppingcart.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 购物车
     * @param request
     * @param response
     */
    private void findCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //登陆了吗
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            //登陆了
            request.getRequestDispatcher("/shoppingcart.jsp").forward(request,response);
        }else {
            response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/login.jsp'");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
