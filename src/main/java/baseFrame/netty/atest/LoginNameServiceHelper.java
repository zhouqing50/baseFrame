package baseFrame.netty.atest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginNameServiceHelper {
/*	private static String rmDBUrl = PropertiesUtil.getProperty("db.rm.md.url");
	private static String rmDBUser = PropertiesUtil.getProperty("db.rm.md.username");
	private static String rmDBPassword = PropertiesUtil.getProperty("db.rm.md.password");
	private static String rmDBDrive = PropertiesUtil.getProperty("db.rm.md.driverClassName");*/
	
	
	static String rmDBUrl ="jdbc:mysql://127.0.0.1:3306/datamti?useUnicode=true&amp;characterEncoding=UTF-8";
	static String rmDBUser = "root";
	static String rmDBPassword = "zhouq";
	static String rmDBDrive = "com.mysql.jdbc.Driver";
	
	//sql语句
	private  final static String sql = "select name from user order by name asc";
	static{
		try {
			Class.forName(rmDBDrive).newInstance();	
		 } catch (Exception e) {
		 } 
	}
	public  List<String> findDomain(){
		List<String> dto = new ArrayList<String>();		
		try {
			
			Connection conn = DriverManager.getConnection(rmDBUrl,rmDBUser,rmDBPassword);
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();	
			int flag = 0;
			while(rs.next()){ 
				//得到域名
				String domainName = rs.getString(1);
				dto.add(domainName);
				flag ++;
			}
			if(flag == 0){
			}
			rs.close();
			stmt.close();
			conn.close();	
		} catch (Exception e) {
		}
		return dto;
	}
	
	public static void main(String[] args) {
		LoginNameServiceHelper ffHelper = new LoginNameServiceHelper();
		List<String> dto =  ffHelper.findDomain();
		for (String ss : dto) {
			System.out.println("--"+ss);
		}
	}
}
