package com.prize.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String url = "jdbc:mysql://localhost:3306/prize_server?useUnicode=true&characterEncoding=utf-8";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String name = "root";
	private static final String pass = "1234";
	private static Connection conn;
	private static PreparedStatement psst;
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection() throws SQLException{
		conn = DriverManager.getConnection(url,name,pass);
		return conn;
	}
	
	public static PreparedStatement getPreparedStatement(String sql) throws SQLException{
		psst = conn.prepareStatement(sql);
		return psst;
	}
	
	
	public static void closeConn(){
		
			try {
				if(conn!=null)
					conn.close();
				if(psst!=null)
					psst.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public static void close() {
		// TODO Auto-generated method stub
		
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
