package it.lziz.admin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/admin/*")
public class FilterAdmin implements Filter {
    List<String> uriList = new ArrayList<>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html;charset=utf-8");
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        //&& !uri.contains("Servlet")
        if (uri.contains("/admin/index.jsp")
                ||uri.contains("/admin/images/")||uri.contains("/admin/css/")||uri.contains("/admin/js/"))
        {
            //包含则放行
            chain.doFilter(req, resp);
        }else {
            //拦截
            if (request.getSession().getAttribute("admin")!=null){
                chain.doFilter(req,resp);
            }
            else {
                ((HttpServletResponse) resp).setHeader("refresh","0;url='"+request.getContextPath()+"/admin/index.jsp'");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {
        uriList.add("/admin");
        uriList.add("/admin/");
        uriList.add("/admin/index.jsp");
//        uriList.add("/admin/css/style.css");
//        uriList.add("/admin/images/dl.gif");
//        uriList.add("/admin/images/cz.gif");
//        uriList.add("/admin/js/js.js");
        uriList.add("/admin/images/");
        uriList.add("/admin/css/");
        uriList.add("/admin/js/");
//        uriList.add("login_03.gif");
//        uriList.add("login_04.gif");
//        uriList.add("login_06.gif");
//        uriList.add("login_07.gif");
//        uriList.add("login_08.gif");
//        uriList.add("login_09.gif");
//        uriList.add("login_10.gif");
//        uriList.add("login_11.gif");

    }

}
