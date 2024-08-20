package org.behaviourprediction.repository;

import java.util.*;
import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.model.RegistrationModel;

public class AdminRepository extends DBHelper{

	/*check admin available or not in database*/
	public int checkAdminLogin(LoginModel login) {
		try {
			ps = con.prepareStatement("select adminid from adminmaster where username=? and password=?");
			ps.setString(1, login.getUsername());
			ps.setString(2, login.getPassword());
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("adminid");
			}else {
				return -1;
			}
		}catch(Exception e) {
			System.out.println("AdminRepository error :"+e);
			return -1;
		}
	}
	
	/*View All Login Users Info*/
	public List<LoginModel> getAllUserLoginInfo(){
		List<LoginModel> login = new LinkedList<LoginModel>();
		try {
			ps=con.prepareStatement("select * from loginmaster");
			rs=ps.executeQuery();
			while(rs.next()) {
				LoginModel model = new LoginModel();
				model.setLoginid(rs.getInt(1));
				model.setUsername(rs.getString(2));
				model.setPassword(rs.getString(3));
				model.setDate(rs.getDate(4));
				model.setTime(rs.getTime(5));
				login.add(model);
			}
			
			return (login.size()>0)?login:null;
		}catch(Exception e) {
			System.out.println("admin repo error: "+e);
			return null;
		}
	}
	
	
	/* View Particular User Login Info*/
	public List<LoginModel> getUserLoginInfo(String userUsername){
		List<LoginModel> list = new LinkedList<LoginModel>();
		try {
			ps=con.prepareStatement("select * from loginmaster where username=? order by date desc");
			ps.setString(1, userUsername);
			rs=ps.executeQuery();
			while(rs.next()) {
				LoginModel model = new LoginModel();
				model.setLoginid(rs.getInt(1));
				model.setUsername(rs.getString(2));
				model.setPassword(rs.getString(3));
				model.setDate(rs.getDate(4));
				model.setTime(rs.getTime(5));
				list.add(model);
			}
			return (list.size()>0)?list:null;
		}catch(Exception e) {
			System.out.println("admin repo error :"+e);
			return null;
		}
	}
	
	// View All User Accounts(register)
	public List<RegistrationModel> getAllUserAccountRegisterInfo(){
		List<RegistrationModel> registerUser = new LinkedList<RegistrationModel>();
		try {
			ps=con.prepareStatement("select * from registrationmaster");
			rs=ps.executeQuery();
			while(rs.next()) {
				RegistrationModel register = new RegistrationModel();
				register.setRegisterid(rs.getInt(1));
				register.setCustomername(rs.getString(2));
				register.setEmail(rs.getString(3));
				register.setUsername(rs.getString(4));
				register.setPassword(rs.getString(5));
				registerUser.add(register);
			}
			return (registerUser.size()>0)?registerUser:null;
		}catch(Exception e) {
			System.out.println("Admin repo error :"+e);
			return null;
		}
	}
	
	/*fetch account requests for delete*/
	public List<LoginModel> getAccountRequestsForDelete(){
		List<LoginModel> list= new LinkedList<LoginModel>();
		try{
			ps=con.prepareStatement("select * from deleterequest");
			rs=ps.executeQuery();
			while(rs.next()) {
				LoginModel lm= new LoginModel();
				lm.setLoginid(rs.getInt(1));
				lm.setDate(rs.getDate(2));
				list.add(lm);
			}
			return (list.size()>0)?list:null;
		}catch(Exception e) {
			System.out.println("admin repo :"+e);
			return null;
		}
	}
	
	
	/*delete account user*/
	public int delteUserAccountRequested(int userRegisterid) {
		try {
			ps=con.prepareStatement("delete from registrationmaster where registerid=?");
			ps.setInt(1, userRegisterid);
			int v=ps.executeUpdate();
			
			ps=con.prepareStatement("delete from deleterequest where registerid=?");
			ps.setInt(1, userRegisterid);
			v=ps.executeUpdate();
			
			return (v>0)?1:0;
			
		}catch(Exception e) {
			System.out.println("admin repo error :"+e);
			return -1;
		}
	}
	
	
	
}
