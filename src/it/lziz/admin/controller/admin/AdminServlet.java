package it.lziz.admin.controller.admin;

import it.lziz.admin.bean.admin.Admin;
import it.lziz.admin.service.admin.AdminService;
import it.lziz.admin.service.admin.AdminServiceImpl;
import it.lziz.admin.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // op ---> 注册 登陆 查询 修改
        String op = request.getParameter("op");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (op!=null && !op.isEmpty()){

            switch (op){
                case "login":
                    login(username,password,request,response);
                    break;
                case "addAdmin":
                    addAdmin(username,password,request,response);
                    break;
                case "findAllAdmin":
                    findAllAdmin(request,response,username,password);
                    break;
/*                case "":
                    break;
                case "":
                    break;
                case "":
                    break;*/
                default:
                    break;
            }
        }


    }

    /**
     * 查找所有管理员信息
     * @param request
     * @param response
     * @param username
     * @param password
     */
    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws ServletException, IOException {
        String num = request.getParameter("num");
        PageHelper ph = adminService.findpageCategory(num);
        request.setAttribute("page",ph);
        request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request,response);

    }

    /**
     * 增加一个管理员用户
     * @param username
     * @param password
     * @param request
     * @param response
     */
    private void addAdmin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        String s_username = request.getParameter("username");
        String s_password = request.getParameter("password");
        try {
            boolean ret = adminService.addAdmin(s_username, s_password);
            if (ret){
                //成功
                response.getWriter().println("更新管理员信息成功");
                response.setHeader("refresh","1;url='"+request.getContextPath()+"/AdminServlet?op=findAllAdmin&num=1'");
            }else {
                //失败
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 管理员登陆
     * @param username
     * @param password
     * @param request
     * @param response
     */
    private void login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //
        Admin admin = new Admin(null, username, password);
        //检查数据的完整性
        Boolean ret = IsLegal(admin);
        //合法
        if (ret){
            try {
                boolean flag = adminService.AdminLogin(admin);
                if (flag){
                    HttpSession session = request.getSession();
                    session.setAttribute("admin",admin);
                    request.getRequestDispatcher("/admin/main.jsp").forward(request,response);
                }else {
//                    response.getWriter().println("用户名或密码错误");
                    response.setHeader("refresh","1;url='"+request.getContextPath()+"/admin/index.jsp'");
                }

            } catch (SQLException e) {
                response.getWriter().println("服务器着火了"+e.getCause());
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else {
            //不合法跳转到登陆页面
            response.setHeader("refresh","1;url='"+request.getContextPath()+"/admin/index.jsp'");
        }
    }

    /**
     * 简单的检查输入数据的合法性
     * @param admin
     * @return
     */
    private Boolean IsLegal(Admin admin) {
        String s_username = admin.getS_username();
        String s_password = admin.getS_password();
        return (s_username!=null && !s_username.isEmpty()) && (s_password!=null && !s_password.isEmpty());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
