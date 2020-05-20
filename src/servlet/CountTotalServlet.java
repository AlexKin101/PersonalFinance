package servlet;

import dao.Record;
import dao.SystemRecord;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static dao.SystemRecord.recordCount_Total;

public class CountTotalServlet extends HttpServlet {
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //设置request&response编码为utf-8
            String result = "";
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            //获取输出对象out
            PrintWriter out = response.getWriter();
            //获取客户端提交的各项参数
            String recordOwner = request.getParameter("recordOwner_total");
            //控制台打印信息
            System.out.println("---统计记录信息中的金额总数---");
            //调用SystemRecord的recordCount方法,统计结果为result
            double[] variate = recordCount_Total(recordOwner);
            System.out.println("---返回客户端信息---"+recordOwner);
            //System.out.println("---收支总数为"+result[0]+"---");
            //System.out.println("---支出总数为"+result[1]+"---");
            //System.out.println("---收入总数为"+result[2]+"---");
            //利用out对象将结果发送至客户端
            if (variate.length == 0) {
                 result = "统计失败";
            } else {
                //JSONObject转化为字符串
                JSONObject r = new JSONObject();
                r.put("amount_total", variate[0]);
                r.put("amount_in", variate[1]);
                r.put("amount_out", variate[2]);
                result = r.toString();
                System.out.println("发送至客户端的JSONObject=" + result);
            }
            out.println(result);
            out.flush();
            out.close();
        }
    }

