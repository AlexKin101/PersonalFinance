package servlet;

import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
import dao.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import static dao.SystemUser.userLogin;


public class LoginServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public User select(String userId) {
        String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT";
        try {
            Connection con = DriverManager.getConnection(url, "root", "root");
            String sql = "select * from users where uid = '" + userId + "'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            User user = new User();
            if (rs.next()) {
                user.setUserId(rs.getString("uid"));
                user.setUserPwd(rs.getString("upwd"));
                user.setUserEmail(rs.getString("uemail"));
                user.setUserIcon(rs.getInt("uicon"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String result = "";
        String tag = "";
        //设置request&response编码为utf-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取输出对象out
        PrintWriter out = response.getWriter();
        //获取客户端提交的参数userId&userPwd
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        System.out.println("客户端登录帐号--" + userId);
        System.out.println("客户端登录密码--" + userPwd);
        //调用SystemUser类的userLogin方法查找是否有该用户
        tag = userLogin(userId, userPwd);
        if (tag.isEmpty()) {
            result="登陆失败";
        } else {
            if (tag.equals("登录成功")) {
                //帐号存在
                User user = select(userId);
                //JSONObject转化为字符串
                JSONObject u = new JSONObject();
                u.put("isLoginOK",1);
                u.put("userId", user.getUserId());
                u.put("userPwd", user.getUserPwd());
                u.put("userEmail", user.getUserEmail());
                u.put("userIcon", user.getUserIcon());
                result = u.toString();
                System.out.println("发送至客户端的JSONObject=" + result);
            }else{
                JSONObject u = new JSONObject();
                u.put("isLoginOK",0);
                result=u.toString();
                System.out.println("发送至客户端的错误信息=" + result);
            }
        }
        out.println(result);
        out.flush();
        out.close();
    }
}