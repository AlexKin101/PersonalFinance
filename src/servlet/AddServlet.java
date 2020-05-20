package servlet;

import dao.Record;
import dao.SystemRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

public class AddServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置request&response编码为utf-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取输出对象out
        PrintWriter out = response.getWriter();
        //获取客户端提交的各项参数
        //int recordId=Integer.parseInt(request.getParameter("recordId"));
        String recordType=request.getParameter("recordType");
        String recordDate = request.getParameter("recordDate");
        Double recordAmount = parseDouble(request.getParameter("recordAmount"));
        String recordExplain = request.getParameter("recordExplain");
        String recordOwner=request.getParameter("recordOwner");
        //控制台打印信息
        System.out.println("---客户端提交添加信息---");
        System.out.println("---类型="+recordType+"---");
        System.out.println("---日期=" + recordDate + "---");
        System.out.println("---金额=" + recordAmount + "---");
        System.out.println("---说明=" + recordExplain + "---");
        System.out.println("---持有人=" + recordOwner + "---");
        //根据获取的参数构造Record对象
        Record record = new Record();
        record.setRecordType(recordType);
        if(recordType.equals("收入")){
            record.setRecordAmount(recordAmount);
        }else{
            record.setRecordAmount(-recordAmount);
        }
        record.setRecordDate(recordDate);
        record.setRecordExplain(recordExplain);
        record.setRecordOwner(recordOwner);
        //调用SystemRecord的recordAdd方法,注册结果为result
        String result = SystemRecord.recordAdd(record);
        System.out.println("---返回客户端信息:" + result + "---");
        //利用out对象将结果发送至客户端
        out.println(result);
        out.flush();
        out.close();
    }
}
