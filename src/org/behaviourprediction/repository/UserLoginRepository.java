package org.behaviourprediction.repository;

import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.LoginModel;

public class UserLoginRepository extends DBHelper{
	
	/* when user login then login data added in database table*/
	public boolean isAddUserLogin(LoginModel login) {
		try {
			ps = con.prepareStatement("insert into loginmaster values('0',?,?,(select curdate()),(select curtime()),?)");
			ps.setString(1, login.getUsername());
			ps.setString(2, login.getPassword());
			ps.setInt(3, login.getLoginid());
			int v=ps.executeUpdate();
			return (v>0) ?true:false;
		}catch(Exception e) {
			System.out.println("user login repository error :"+e);
			return false;
		}
	}
}
