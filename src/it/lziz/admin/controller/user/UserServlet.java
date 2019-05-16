package it.lziz.admin.controller.user;

import it.lziz.admin.bean.product.Product;
import it.lziz.admin.bean.user.User;
import it.lziz.admin.service.ShoppingCar.ShoppingCarService;
import it.lziz.admin.service.ShoppingCar.ShoppingCarServiceImpl;
import it.lziz.admin.service.product.ProductService;
import it.lziz.admin.service.product.ProductServiceImpl;
import it.lziz.admin.service.user.UserService;
import it.lziz.admin.service.user.UserServiceImpl;
import it.lziz.admin.utils.MailUtils;
import it.lziz.admin.utils.PageHelper;
import it.lziz.custom.bean.ShoppingCar;
import it.lziz.custom.bean.ShoppingItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();
    ShoppingCarService shoppingCarService = new ShoppingCarServiceImpl();
    ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String op = request.getParameter("op");
        if (op!=null && !op.isEmpty()){
            switch (op){
                case "adduser":
                    adduser(request,response);
                    break;
                case "findAllUser":
                    findAllUser(request,response);
                    break;
                case "register":
                    register(request,response);
                    break;
                case "login":
                    login(request,response);
                    break;
                case "upDateUserInfo":
                    upDateUserInfo(request,response);
                    break;
                case "lgout":
                    lgout(request,response);
                    break;
                case "activation":
                    activation(request,response);
                    break;
                default:
                    break;
            }
        }
    }

    private void activation(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 用户退出登陆
     * @param request
     * @param response
     */
    private void lgout(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 编辑更新用户信息
     */
    private void upDateUserInfo(HttpServletRequest request,HttpServletResponse response) {
        //uid	3
        //username	admin
        //nickname	root    v
        //password	123     v
        //email	123@qq.com      v
        //birthday	1995-11-24+00:00:00.0   v
        User user = generateUser(request);
        user.setS_uid(Integer.parseInt(request.getParameter("uid")));
        try {
            User updateUser = userService.updateUserInfo(user);
            if (updateUser!=null){
                //更新用户信息成功
                HttpSession session = request.getSession();
                session.setAttribute("user",updateUser);
                response.getWriter().println("更新成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/personal.jsp'");
            }else {
                //失败
                response.getWriter().println("更新失败");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/personal.jsp'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户登陆页
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        //op	login
        //username	admin
        //password	admin
        //checkcode
        //commit	登录
        String checkcode = request.getParameter("checkcode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if (!checkcode_session.equalsIgnoreCase(checkcode))
        {
            //验证码错误 跳转到登陆页面
            try {
                response.getWriter().println("验证码错误");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/login.jsp'");
//                request.getRequestDispatcher("/user/login.jsp").forward(request,response);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            //正确
            String s_username = request.getParameter("username");
            String s_password = request.getParameter("password");
            if ("".equals(s_username) || "null".equals(s_username) || s_username == null
                    || "".equals(s_password) || s_password == null){
                try {
                    response.getWriter().println("用户名或密码不合法");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/login.jsp'");
            }else {
                try {
                    User user = userService.userLogin(s_username,s_password);
                    if (user!=null){
                        //成功
                        //先检测此用户是否存在已经拥有的购物车
                        ShoppingCar shoppingCar = shoppingCarService.ShoppingCarExist(user.getS_uid());
                        //用户登陆成功后生成用户的购物车
                        if (shoppingCar == null){
                            //此前不存在购物车
                            shoppingCar = new ShoppingCar(null,user.getS_uid(),new ArrayList<>());
                            //将新购物车加入数据库存入数据库
                            boolean ret = shoppingCarService.SaveCar(shoppingCar);

                        }else {
                            //之前存在购物车
                            //读取该用户购物车之前的记录
                            List<ShoppingItem> oldUserItems = shoppingCarService.findUserAllShoppingItem(shoppingCar.getS_sid());
                            for (ShoppingItem oldUserItem : oldUserItems) {
                                Product product = productService.findProductByPid(oldUserItem.getS_pid().toString());
                                oldUserItem.setProduct(product);
                                //之前购物车有多少件这个商品
                                oldUserItem.setS_num(1);
                            }

                            //将这些记录添加到购物车
                            if (oldUserItems!=null)
                                shoppingCar.setShoppingItems(oldUserItems);

                            request.getSession().setAttribute("shoppingCar",shoppingCar);

                        }

                        HttpSession session = request.getSession();
                        session.setAttribute("user",user);
                        response.getWriter().println("登陆成功");
                        response.setHeader("refresh","1;url='"+request.getContextPath()+"/index.jsp'");
                    }else{
                        //失败
                        response.getWriter().println("用户名或密码错");
                        response.setHeader("refresh","1;url='"+request.getContextPath()+"/user/login.jsp'");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //最后都要移除验证码的session
        request.getSession().removeAttribute("checkcode_session");
    }

    /**
     * 包装生成User
     * @param request
     * @return
     */
    private User generateUser(HttpServletRequest request){
        //还有s_uid
        String s_username = request.getParameter("username");
        String s_nickname = request.getParameter("nickname");
        String s_password = request.getParameter("password");
        String s_email = request.getParameter("email");
        String s_birthday = request.getParameter("birthday");
        //注册时间s_updatetime
        Date date = new Date();
        long date_updatetime = date.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
        Date date1 = null;
        try {
            date1 = format.parse(s_birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long date_birthday = date1.getTime();

        User user = new User(null, s_username, s_nickname, s_password, s_email,new Timestamp(date_birthday) ,new Timestamp(date_updatetime));

        return user;
    }

    /**
     * 用户注册
     * @param request
     * @param response
     */
    private void register(HttpServletRequest request, HttpServletResponse response) {
        //拿到前端数据 前端校验过 可以注册
        User user = generateUser(request);
        System.out.println(user.toString());
        //
        try {
            boolean ret = userService.addUser(user);

            if (ret){
//                MailUtils.sendMail(user.getS_email(),"<a href=\"http://192.168.5.69/UserServlet?op=activation&\">点我验证注册</a>");
//                request.getRequestDispatcher("/user/activation.jsp").include(request,response);
//                response.getWriter().println("添加成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/index.jsp'");
            }else {
                request.getRequestDispatcher("/user/regist.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        PageHelper ph = null;
        //获取到数据
        try {
            ph = userService.findpageUser(num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //将数据放到域中
        request.setAttribute("page",ph);
        request.getRequestDispatcher("/admin/user/userList.jsp").forward(request,response);
    }

    /**
     * 增加一个用户
     * @param request
     * @param response
     */
    private void adduser(HttpServletRequest request, HttpServletResponse response){
        User user = generateUser(request);

        try {
            boolean ret = userService.addUser(user);
            if (ret){
                //添加成功
                response.getWriter().println("添加成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/UserServlet?op=findAllUser&num=1'");
//                request.getRequestDispatcher("/UserServlet?op=adduser&num=1").forward(request,response);
            }
            else {
                request.getRequestDispatcher("/admin/user/addUser.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
