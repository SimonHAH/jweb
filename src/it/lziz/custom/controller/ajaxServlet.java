package it.lziz.custom.controller;

import it.lziz.admin.service.user.UserService;
import it.lziz.admin.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "ajaxServlet",urlPatterns = "/ajaxServlet")
public class ajaxServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doPost(request, response);
                    /*
        	s_username VARCHAR(32),-- 用户名
	        s_nickname VARCHAR(32),-- 昵称
	        s_email VARCHAR(32),-- 邮箱
         */
        String s_username = request.getParameter("username");
        System.out.println(s_username);
        //username合法性检测
        if (s_username == null || "".equals(s_username) ||"null".equals(s_username)){
            response.getWriter().print("repeat");
        }

        else {
            try {
                boolean ret = userService.findUserExist(s_username);
                if (ret){
                    //存在
                    response.getWriter().print("exist");
                }else{
                    //不存在
                    response.getWriter().print("ok");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
