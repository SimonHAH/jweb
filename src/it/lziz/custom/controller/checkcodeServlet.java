package it.lziz.custom.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "checkcodeServlet",urlPatterns = "/checkcodeServlet")
public class checkcodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 100;
        int height = 50;
        //生成图片
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //加工图片
        Graphics pen = bi.getGraphics();//画笔
        pen.setColor(Color.WHITE);
        pen.fillRect(0,0,width,height);
        //边框
        pen.setColor(Color.yellow);
        pen.drawRect(0,0,width-1,height-1);
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        pen.setColor(Color.red);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            sb.append(c);
            pen.drawString(c+"",width/5*i,height/2);
        }
        String checkcode_session = sb.toString();
        request.getSession().setAttribute("checkcode_session",checkcode_session);
        //输出图片
        ImageIO.write(bi,"jpg",response.getOutputStream());
    }
}
