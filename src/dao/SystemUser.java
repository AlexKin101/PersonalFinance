package dao;


import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class SystemUser {

    private static String REGISTER_OK = "注册成功";
    private static String REGISTER_FAILURE = "注册失败";
    private static String LOGIN_OK = "登录成功";
    private static String LOGIN_FAILURE = "登录失败";
    //存储用户数据的ArrayList
    private static ArrayList<User> userList = new ArrayList<User>();

    //注册方法
    public static String userRegister(User user) {
        Connection conn = null;
        String tag = REGISTER_FAILURE;
        String url="jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";
        if(user.getUserId().isEmpty()||user.getUserPwd().isEmpty()){
            return "用户名或密码不能为空";
        }
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            String sql = "INSERT INTO users (uid,upwd,uemail,uicon)"
                    + " VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserPwd());
            ps.setString(3, user.getUserEmail());
            ps.setInt(4, user.getUserIcon());
            if (ps.executeUpdate()>0) tag = REGISTER_OK;
            else tag = REGISTER_FAILURE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }


    public static String userLogin(String userId, String userPwd) {

        Connection con = null;
        String tag = "";
        PreparedStatement ps;
        String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";
        if(userId.isEmpty()&&userPwd.isEmpty()){
            return LOGIN_FAILURE;
        }
        try {
            con = DriverManager.getConnection(url, "root", "root");
            String sql = "select * from users where uid = ? and upwd = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,userId);
            ps.setString(2,userPwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tag = LOGIN_OK;
            } else {
                tag = LOGIN_FAILURE;
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    //登录方法
//    public static User userLogin(String userId){
//        //遍历userList
//        for(int i=0;i<userList.size();i++){
//            if(userList.get(i).getUserId().equals(userId)){
//                //返回user对象
//                return userList.get(i);
//            }
//        }
//        return null;
//    }

    //显示所有用户的Id
    public static String getAllUserInfo() {
        Connection con = null;
        Statement sql1;
        Statement sql2;
        String defaultResult = "目前没有任何用户";

        String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

        try {
            con = DriverManager.getConnection(url, "root", "root");
            String condition1 = "select uid from users";
            String condition2="select count(*) uid from users";
            sql1 = con.prepareStatement(condition1);
            sql2=con.prepareStatement(condition2);
            ResultSet rs1 = sql1.executeQuery(condition1);
            ResultSet rs2=sql2.executeQuery(condition2);

            int rowCount=0;
            if(rs2.next()){
                rowCount=rs2.getInt("uid");
            }
            String userInfo = "目前共有" + rowCount + "个用户<br>";
            if (rs1.next()) {
                userInfo += rs1.getString("uid")+"<br />";
                while (rs1.next()) {
                    userInfo += rs1.getString("uid")+"<br />";
                }
                return userInfo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultResult;
    }
}

//        String defaultResult = "目前没有任何用户";
//
//        if (userList.size() != 0) {
//            String userInfo = "目前共有" + userList.size() + "个用户<br>";
//            //遍历userList
//            for (int i = 0; i < userList.size(); i++) {
//                userInfo += userList.get(i).getUserId() + "<br />";
//            }
//            return userInfo;
//        }
//        return defaultResult;
//    }
