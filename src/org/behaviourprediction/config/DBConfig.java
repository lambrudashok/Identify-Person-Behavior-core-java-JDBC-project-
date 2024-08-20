package org.behaviourprediction.config;

import java.sql.*;
import java.util.*;
public class DBConfig {

	private static Connection con;
	private static PreparedStatement ps;
	private static ResultSet rs;
	private static DBConfig db = null;
	
	private DBConfig() {
		try {
			
			Properties p = new Properties();
			p.load(PathHelper.fin);
			String driverClassName = p.getProperty("driver.classname");
			String username = p.getProperty("db.username");
			String password = p.getProperty("db.password");
			String url = p.getProperty("db.url");
			
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url,username,password);
			if(con!=null) {
				System.out.println("Database Connected");
			}else {
				System.out.println("Database not connect");
			}
		}catch(Exception e) {
			System.out.println("db config :"+e);
		}
	}
	
	public static DBConfig getDBInstance() {
		if(db==null) {
			db = new DBConfig();
		}
		return db;
	}
	
	public static Connection getConnection() {
		return con;
	}
    public static PreparedStatement getPreparedStatement() {
    	return ps;
    }
    public static ResultSet getResultSet() {
    	return rs;
    }
	
}
