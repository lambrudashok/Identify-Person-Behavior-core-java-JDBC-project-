package org.behaviourprediction.config;

import java.sql.*;
public class DBHelper {
	
	protected DBConfig db = DBConfig.getDBInstance();
	protected Connection con = DBConfig.getConnection();
	protected PreparedStatement ps = DBConfig.getPreparedStatement();
	protected ResultSet rs = DBConfig.getResultSet();

}
