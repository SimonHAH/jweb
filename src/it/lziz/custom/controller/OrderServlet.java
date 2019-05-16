package it.lziz.custom.controller;

import it.lziz.admin.service.ShoppingCar.ShoppingCarService;
import it.lziz.admin.service.ShoppingCar.ShoppingCarServiceImpl;
import it.lziz.custom.bean.Order;
import it.lziz.custom.bean.OrderItem;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    ShoppingCarService shoppingCarService = new ShoppingCarServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//uid	4
//op	placeOrder
//recipients	李志荣
//tel	16673168312
//address	广东省广州市天河区珠江新城
//ids	[…]
//0	11
//1	21
//2	23
//money	2.0057776E7
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        if (op!=null && !"".equals(op))
        {
            switch (op){
                case "myoid":
                    myoid(request,response);
                    break;
                case "placeOrder":
                    try {
                        placeOrder(request,response);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "cancelOrder":
                    cancelOrder(request,response);
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 取消订单
     * @param request
     * @param response
     */
    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //OrderServlet?op=cancelOrder&oid=21101802260524150011799005870901&state=0
        String s_oid = request.getParameter("oid");
        String s_state = request.getParameter("state");
        List<Order> orders = (List) request.getSession().getAttribute("orders");
        for (Order order : orders) {
            if (order.getS_oid().equals(s_oid))
                order.setS_state(Integer.parseInt(s_state));
        }
        //然后改写数据库
        try {
            shoppingCarService.updateOrder(s_state,s_oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("orders",orders);
        request.getRequestDispatcher("/myOrders.jsp").forward(request,response);
    }

    /**
     * 下单
     * @param request
     * @param response
     */
    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String s_uid = request.getParameter("uid");//用户id
        String s_recipients = request.getParameter("recipients");//收件人
        String s_tel = request.getParameter("tel");//联系电话
        String s_address = request.getParameter("address");//地址
        String[] ids = request.getParameterValues("ids");//订单中的商品id
        String[] nums = request.getParameterValues("num");
        String s_money = request.getParameter("money");//订单总金额
        long l = System.currentTimeMillis();


        //生成32位的订单
        String orderNumber = RandomOrderNumber();
        //订单号存在吗
        while (shoppingCarService.OrderExist(orderNumber)) {
            orderNumber = RandomOrderNumber();
        }
        //生成订单
        Order order = new Order(orderNumber,Double.parseDouble(s_money),s_recipients,s_tel,
                s_address,1,new Timestamp(l),Integer.parseInt(s_uid));//生成订单
        boolean ret = shoppingCarService.SaveOrder(order);

        //生成订单项
        for (int i = 0; i < ids.length; i++) {
            OrderItem orderItem = new OrderItem(null,orderNumber,ids[i],Integer.parseInt(nums[i]));
            shoppingCarService.SaveOrderItem(orderItem);
        }
        //下单后应删除购物车数据库已经存入的数据  s_sid-购物车id
        ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
        Integer s_sid = shoppingCar.getS_sid();//购物车id
        shoppingCarService.delItemBySid(s_sid);
        List<ShoppingItem> userAllShoppingItem = shoppingCarService.findUserAllShoppingItem(s_sid);
        shoppingCar.getShoppingItems().clear();
        shoppingCar.getShoppingItems().addAll(userAllShoppingItem);
        //清空购物车
        request.getSession().setAttribute("shoppingCar",shoppingCar);
        request.getRequestDispatcher("/CartServlet?op=findCart").forward(request,response);
    }

    private String RandomOrderNumber() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(String.valueOf(randomNumber));
        }
        return sb.toString();
    }

    /**
     * 我的订单
     * @param request
     * @param response
     */
    private void myoid(HttpServletRequest request, HttpServletResponse response) {
        ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
        Integer s_uid = shoppingCar.getS_uid();

        try {
            List<Order> orders = shoppingCarService.findAllOrder(s_uid);
            request.getSession().setAttribute("orders",orders);
            request.getRequestDispatcher("/myOrders.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
