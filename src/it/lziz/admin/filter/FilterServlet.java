package it.lziz.admin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*Servlet")
public class FilterServlet implements Filter {
    List<String> list = new ArrayList<>();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        if (!uri.contains("Servlet") || uri.contains("MainServlet") || uri.contains("ajaxServlet")
                || uri.contains("checkcodeServlet") || uri.contains("CartServlet")){
            //不是请求Servlet的都放行 MainServlet也放行 ajaxServlet
            chain.doFilter(req, resp);
        }
        //如果是请求Servlet但请求参数是前端的放行
        //    目前已知的前端操作 findProByName byCid findProductById register


        if (list.contains(req.getParameter("op"))){
            chain.doFilter(req,resp);
        }
        if (request.getSession().getAttribute("admin")!=null){
            chain.doFilter(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        list.add("findProByName");
        list.add("byCid");
        list.add("findProductById");
        list.add("login");//后端管理登陆
        list.add("register");//用户注册
        list.add("upDateUserInfo");//前端更新用户信息
        list.add("lgout");//前端退出登陆
    }

}
