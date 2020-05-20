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

public class ModifyServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置request&response编码为utf-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取输出对象out
        PrintWriter out = response.getWriter();
        //获取客户端提交的各项参数
        int recordId = Integer.parseInt(request.getParameter("recordId"));
        String recordType = request.getParameter("recordType");
        String recordDate = request.getParameter("recordDate");
        Double recordAmount = Double.valueOf(request.getParameter("recordAmount"));
        String recordExplain = request.getParameter("recordExplain");
        //控制台打印信息
        System.out.println("---客户端提交修改信息---");
        System.out.println("---ID=" + recordId + "---");
        System.out.println("---日期=" + recordDate + "---");
        System.out.println("---类型=" + recordType + "---");
        System.out.println("---金额=" + recordAmount + "---");
        System.out.println("---说明=" + recordExplain + "---");
        //根据获取的参数构造Record对象
        if(recordType.equals("支出")){
            recordAmount=-recordAmount;
        }
        Record record = new Record();
        record.setRecordId(recordId);
        record.setRecordType(recordType);
        record.setRecordDate(recordDate);
        record.setRecordAmount(recordAmount);
        record.setRecordExplain(recordExplain);
        //调用SystemRecord的recordModify方法,注册结果为result
        String result = SystemRecord.recordModify(record);
        System.out.println("---返回客户端信息:" + result + "---");
        //利用out对象将结果发送至客户端
        out.println(result);
        out.flush();
        out.close();
    }
}
