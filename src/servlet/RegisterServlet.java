package servlet;

import dao.SystemUser;
import dao.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static dao.SystemUser.userRegister;

public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置request&response编码为utf-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取输出对象out
        PrintWriter out = response.getWriter();
        //获取客户端提交的各个参数
        String userId=request.getParameter("userId");
        String userPwd=request.getParameter("userPwd");
        String userEmail=request.getParameter("userEmail");
        int userIcon=Integer.parseInt(request.getParameter("userIcon"));
        //控制台打印信息
        System.out.println("---客户端提交注册信息---");
        System.out.println("---帐号="+userId+"---");
        System.out.println("---密码="+userPwd+"---");
        System.out.println("---邮箱="+userEmail+"---");
        System.out.println("---头像资源ID="+userIcon+"---");
        //根据获取的参数构造User对象
        User user=new User();
        user.setUserId(userId);
        user.setUserPwd(userPwd);
        user.setUserEmail(userEmail);
        user.setUserIcon(userIcon);
        //调用SystemUser的userRegister方法,注册结果为result
        String result= "";
        result=userRegister(user);
        System.out.println("---返回客户端信息:"+result+"---");
        //利用out对象将结果发送至客户端
        out.println(result);
        out.flush();
        out.close();
    }
}