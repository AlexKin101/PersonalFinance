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
import java.math.BigDecimal;
import java.util.ArrayList;

public class QueryDateServlet extends HttpServlet {

    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置request&response编码为utf-8
        String result="";
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取输出对象out
        PrintWriter out = response.getWriter();
        //获取客户端提交的recordType参数
        String recordOwner = request.getParameter("recordOwner");
        Double Date_start = Double.parseDouble(request.getParameter("Date_start"));
        Double Date_end = Double.parseDouble(request.getParameter("Date_end"));
        //控制台打印信息
        System.out.println("---查询记录信息---");
        System.out.println("---起始时间="+Date_start+ "---");
        System.out.println("---起始时间="+Date_end+ "---");
        //System.out.println("---类型=" + recordType + "---");
        //调用SystemRecord的recordQuery方法,注册结果为result
        ArrayList<Record> list = new ArrayList<>();
        list = SystemRecord.recordQuery_date(Date_start,Date_end,recordOwner);
        System.out.println(list);
        if (list==null) {
            result = "查询失败";
        } else {
            JSONArray array=new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                Record record = list.get(i);
                //JSONObject转化为字符串
                JSONObject r = new JSONObject();
                r.put("recordId",record.getRecordId());
                String RecordDate=getPrettyNumber(record.getRecordDate());
                r.put("recordDate", RecordDate);
                r.put("recordType", record.getRecordType());
                r.put("recordAmount", record.getRecordAmount());
                r.put("recordExplain", record.getRecordExplain());
                array.add(r);
            }
            result += array.toString();
        }
        //利用out对象将结果发送至客户端
        out.println(result);
        out.flush();
        out.close();
    }
}
