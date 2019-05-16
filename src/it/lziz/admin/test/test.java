package it.lziz.admin.test;

import it.lziz.admin.utils.MailUtils;

public class test {
    public static void main(String[] args) {
        boolean hello = MailUtils.sendMail("840432648@qq.com", "<a href=\"https://www.baidu.com/\">点我验证注册</a>");
//        System.out.println(hello);
    }
}
