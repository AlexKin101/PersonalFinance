package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemRecord {
	
	private static String ADD_OK="添加成功";
	private static String ADD_FAILURE="添加失败";
	private static String DElETE_OK="删除成功";
	private static String DElETE_FAILURE="删除失败";
	private static String MODIFY_OK="修改成功";
	private static String MODIFY_FAILURE="修改失败";
	//存储记录的ArrayList
	private static ArrayList<Record> recordList=new ArrayList<Record>();

	//Add方法
	public static String recordAdd(Record record){
		Connection conn = null;
		String tag = ADD_FAILURE;
		String url="jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";
		if(record.getRecordDate().isEmpty()||record.getRecordType().isEmpty()
				||record.getRecordAmount()==0.0||record.getRecordOwner().isEmpty()){
			tag=ADD_FAILURE;
		}
		try {
			conn = DriverManager.getConnection(url, "root", "root");
			String sql = "INSERT INTO records (rdata,rtype,ramount,rexplain,rowner)"
					+ " VALUES(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, record.getRecordDate());
			ps.setString(2, record.getRecordType());
			ps.setDouble(3,record.getRecordAmount());
			ps.setString(4,record.getRecordExplain());
			ps.setString(5,record.getRecordOwner());
			if (ps.executeUpdate()>0) tag = ADD_OK;
			else tag = ADD_FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tag;
	}
	
	//Delete方法
	public static String recordDelete(int recordId){
		Connection conn = null;
		String tag = DElETE_FAILURE;
		String url="jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn=DriverManager.getConnection(url, "root", "root");
			String sql="DELETE FROM records WHERE rid = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1,recordId);
			if(ps.executeUpdate()>0) tag=DElETE_OK;
			else tag=DElETE_FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tag;

//		//遍历recordList
//		for(int i=0;i<recordList.size();i++){
//			if(recordList.get(i).getRecordId()==recordId){
//				recordList.remove(recordId);
//				return DElETE_OK;
//			}
//		}
//		return DElETE_FAILURE;
	}
	
	//Modify方法
	public static String recordModify(Record record){
		Connection conn = null;
		String tag = MODIFY_FAILURE;
		String url="jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn=DriverManager.getConnection(url, "root", "root");
			String sql="UPDATE records SET rdata= ? , rtype = ? , ramount = ? , rexplain = ? WHERE rid = ? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,record.getRecordDate());
			ps.setString(2,record.getRecordType());
			ps.setDouble(3,record.getRecordAmount());
			ps.setString(4,record.getRecordExplain());
			ps.setInt(5,record.getRecordId());
			if(ps.executeUpdate()>0) tag=MODIFY_OK;
			else tag=MODIFY_FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tag;


//		//遍历recordList
//		for(int i=0;i<recordList.size();i++){
//			if(recordList.get(i).getRecordId()==record.getRecordId()){
//				recordList.get(i).setRecordDate(record.getRecordDate());
//				recordList.get(i).setRecordType(record.getRecordType());
//				recordList.get(i).setRecordAmount(record.getRecordAmount());
//				recordList.get(i).setRecordExplain(record.getRecordExplain());
//				return MODIFY_OK;
//			}
//		}
//		return MODIFY_FAILURE;
	}

	//查询的Query方法
	public static ArrayList recordQuery(String recordOwner) {
		Connection conn = null;
		String sql=" ";
		ArrayList<Record> list=new ArrayList<>();
		String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn = DriverManager.getConnection(url, "root", "root");
			sql = "SELECT * FROM records WHERE rowner = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1,recordType);
			ps.setString(1,recordOwner);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Record record1=new Record();
				record1.setRecordId((rs.getInt("rid")));
				record1.setRecordDate(rs.getString("rdata"));
				record1.setRecordType(rs.getString("rtype"));
				record1.setRecordAmount(Math.abs(rs.getDouble("ramount")));
				record1.setRecordExplain(rs.getString("rexplain"));
				list.add(record1);
				while (rs.next()) {
					Record record=new Record();
					record.setRecordId((rs.getInt("rid")));
					record.setRecordDate(rs.getString("rdata"));
					record.setRecordType(rs.getString("rtype"));
					record.setRecordAmount(Math.abs(rs.getDouble("ramount")));
					record.setRecordExplain(rs.getString("rexplain"));
					list.add(record);
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//根据Date进行查询的Query方法
	public static ArrayList recordQuery_date(Double Date_start, Double Date_end ,String recordOwner) {
		Connection conn = null;
		String sql=" ";
		int count = 1;
		ArrayList<Record> list=new ArrayList<>();
		String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn = DriverManager.getConnection(url, "root", "root");
			sql = "SELECT * FROM records WHERE rdata > ? and  rdata < ? and rowner = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1,recordType);
			ps.setDouble(1,Date_start);
			ps.setDouble(2,Date_end);
			ps.setString(3,recordOwner);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Record record1=new Record();
				record1.setRecordId((rs.getInt("rid")));
				record1.setRecordDate(rs.getString("rdata"));
				record1.setRecordType(rs.getString("rtype"));
				record1.setRecordAmount(Math.abs(rs.getDouble("ramount")));
				record1.setRecordExplain(rs.getString("rexplain"));
				list.add(record1);
				while (rs.next()) {
					Record record=new Record();
					record.setRecordId((rs.getInt("rid")));
					record.setRecordDate(rs.getString("rdata"));
					record.setRecordType(rs.getString("rtype"));
					record.setRecordAmount(Math.abs(rs.getDouble("ramount")));
					record.setRecordExplain(rs.getString("rexplain"));
					list.add(record);
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


//		String recordInfo="目前共有"+recordList.size()+"个符合"+recordType+"条件记录<br>";
//		//遍历recordList
//		for(int i=0;i<recordList.size();i++){
//			if(recordList.get(i).getRecordType().equals(recordType)){
//				recordInfo+=recordList.get(i)+"<br />";
//			}
//		}
//		return recordInfo;
	
	///Count方法(全部总收支)
	public static double[] recordCount_Total(String recordOwner) {
		//amount[0]为总收支，amount[1]为支出总计，amount[2]为收入总计
		double amount[] = {0.00d, 0.00d, 0.00d};
		String in="收入";
		String out="支出";
		Connection conn = null;
		String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn = DriverManager.getConnection(url, "root", "root");
			String sql = "SELECT SUM(ramount) AS SUM FROM records WHERE rtype = (SELECT rtype WHERE rowner = ?) ";	//总收支
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, recordOwner);
			ResultSet rs = ps.executeQuery();

			String sql_in = "SELECT SUM(ramount) AS SUM_IN FROM records WHERE ramount>0 AND rowner = ?";	//收入
			PreparedStatement ps_in = conn.prepareStatement(sql_in);
			//ps_in.setString(1, in);
			ps_in.setString(1, recordOwner);
			ResultSet rs_in = ps_in.executeQuery();

			String sql_out = "SELECT SUM(ramount) AS SUM_OUT FROM records WHERE ramount<0 AND rowner = ?";	//支出
			PreparedStatement ps_out = conn.prepareStatement(sql_out);
			//ps_in.setString(1, in);
			ps_out.setString(1, recordOwner);
			ResultSet rs_out = ps_out.executeQuery();

			if(rs.next()&&rs_in.next()&&rs_out.next()) {
				amount[0]=Math.abs(rs.getDouble("SUM"));
				//收入
				amount[1] = rs_in.getDouble("SUM_IN");
				//支出
				amount[2] = Math.abs(rs_out.getDouble("SUM_OUT"));
			}
			return amount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}

	///Count方法(全部总收支)
	public static double[] recordCount_Month(String recordOwner,String month) {
		//amount[0]为总收支，amount[1]为支出总计，amount[2]为收入总计
		double amount[] = {0.00d, 0.00d, 0.00d};
		String in="收入";
		String out="支出";
		Connection conn = null;
		String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn = DriverManager.getConnection(url, "root", "root");
			String sql = "SELECT SUM(ramount) AS SUM FROM records WHERE rdata like  ? AND rowner = ?";	//总收支
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+month+"%");
			ps.setString(2, recordOwner);
			ResultSet rs = ps.executeQuery();

			String sql_in = "SELECT SUM(ramount) AS SUM_IN FROM records WHERE rdata like ? AND ramount > 0 AND rowner = ?";	//收入
			PreparedStatement ps_in = conn.prepareStatement(sql_in);
			ps_in.setString(1, "%"+month+"%");
			ps_in.setString(2, recordOwner);
			ResultSet rs_in = ps_in.executeQuery();

			String sql_out = "SELECT SUM(ramount) AS SUM_OUT FROM records WHERE rdata like ? AND ramount < 0 AND rowner = ?";	//支出
			PreparedStatement ps_out = conn.prepareStatement(sql_out);
			ps_out.setString(1, "%"+month+"%");
			ps_out.setString(2, recordOwner);
			ResultSet rs_out = ps_out.executeQuery();

			if(rs.next()&&rs_in.next()&&rs_out.next()) {
				amount[0]=Math.abs(rs.getDouble("SUM"));
				//收入
				amount[1] = rs_in.getDouble("SUM_IN");
				//支出
				amount[2] = Math.abs(rs_out.getDouble("SUM_OUT"));
			}
			return amount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}



//		//遍历recordList
//		for(int i=1;i<recordList.size();i++){
//			if(recordList.get(i).getRecordType().equals("支出")){
//				amount[0]-=recordList.get(i).getRecordAmount();
//				amount[1]+=recordList.get(i).getRecordAmount();
//			} else{
//				amount[0]+=recordList.get(i).getRecordAmount();
//				amount[2]+=recordList.get(i).getRecordAmount();
//			}
//		}
//		return amount;
//	}

	//显示收支记录信息
	public static String getAllRecordInfo(String recordOwner){
		String defaultResult="目前没有任何记录";

		Connection conn = null;
		int count=1;
		String url = "jdbc:mysql://localhost/personalfinancedb?serverTimezone=GMT&characterEncoding=utf-8";

		try {
			conn = DriverManager.getConnection(url, "root", "root");
			String sql1 = "select * from records WHERE rowner = ?";
			String sql2="select count(*) rid from records WHERE rowner = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1,recordOwner);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1,recordOwner);
			ResultSet rs1 = ps1.executeQuery();
			ResultSet rs2 = ps2.executeQuery();

			int rowCount=0;
			if(rs2.next()){
				rowCount=rs2.getInt("rid");
			}
			String recordInfo = "目前共有" + rowCount + "个记录<br>";
			if (rs1.next()) {
				recordInfo += "--记录id:" + count+ "--<br />";
				recordInfo += "--记录类型:" + rs1.getString("rtype") + " ";
				recordInfo += "--记录时间:" + rs1.getString("rdata") + " ";
				recordInfo += "--记录金额:" + rs1.getDouble("ramount") + " ";
				recordInfo += "--记录说明:" + rs1.getString("rexplain") + "--<br />";
				while (rs1.next()) {
					count++;
					recordInfo += "--记录id:" + count+ "--<br />";
					recordInfo += "--记录类型:" + rs1.getString("rtype") + " ";
					recordInfo += "--记录时间:" + rs1.getString("rdata") + " ";
					recordInfo += "--记录金额:" + rs1.getDouble("ramount") + " ";
					recordInfo += "--记录说明:" + rs1.getString("rexplain") + "--<br />";
				}
				return recordInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return defaultResult;
	}
}
