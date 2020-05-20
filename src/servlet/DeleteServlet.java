package servlet;

import dao.SystemRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteServlet extends HttpServlet {
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //设置request&response编码为utf-8
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            //获取输出对象out
            PrintWriter out = response.getWriter();
            //获取客户端提交的RecordId
            int recordId = Integer.parseInt(request.getParameter("recordId"));
            //控制台打印信息
            System.out.println("---删除记录信息---");
            System.out.println("---ID=" + recordId + "---");
            //调用SystemRecord的recordDelete方法,注册结果为result
            String result = SystemRecord.recordDelete(recordId);
            System.out.println("---返回客户端信息:" + result + "---");
            //利用out对象将结果发送至客户端
            out.println(result);
            out.flush();
            out.close();
        }
}
