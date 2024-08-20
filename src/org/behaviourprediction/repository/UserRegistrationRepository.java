package org.behaviourprediction.repository;

import java.util.*;
import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.AccountInformationModel;
import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.model.RegistrationModel;

public class UserRegistrationRepository extends DBHelper{

	/* new user insert data in registration master table*/
	public boolean isAddNewUserRegistration(RegistrationModel register) {
		
		try {
			ps = con.prepareStatement("insert into registrationmaster (customername,email,username,password) values (?,?,?,?)");
			ps.setString(1, register.getCustomername());
			ps.setString(2, register.getEmail());
			ps.setString(3, register.getUsername());
			ps.setString(4, register.getPassword());
			int v =ps.executeUpdate();
			return (v>0) ?true:false;
		}catch(Exception e) {
			System.out.println("user register repo :"+e);
			return false;
		}
	}
	
	/*check user login in database */
	public int checkUserLogin(LoginModel login) {
		try {
			ps = con.prepareStatement("select registerid from registrationmaster where username=? and password=?");
			ps.setString(1, login.getUsername());
			ps.setString(2, login.getPassword());
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("registerid");
			}else {
				return -1;
			}
		}catch(Exception e) {
			System.out.println("User Registration Repository error :"+e);
			return -1;
		}
	}
	
	/*check email duplicate or not */
	public int searchEmail(String email) {
		try {
			ps = con.prepareStatement("select registerid from registrationmaster where email=?");
			ps.setString(1, email);
			rs =ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("User Registration Repository error :"+e);
			return -1;
		}
	}
	
	/*check username duplicate or not */
	public int searchUsername(String username) {
		try {
			ps = con.prepareStatement("select registerid from registrationmaster where username=?");
			ps.setString(1, username);
			rs =ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("User Registration Repository error :"+e);
			return -1;
		}
	}
	
	
	/*show user according to its starting usernames */
	public List<String> showSearchUserNames(String username) {
		List<String> user = new LinkedList<String>();
		try {
			ps = con.prepareStatement("select username from registrationmaster where username like (?)");
			ps.setString(1, username+"%");
			rs =ps.executeQuery();
			while(rs.next()) {
				user.add(rs.getString(1));
			}
			return (user.size()>0)?user:null;
		}catch(Exception e) {
			System.out.println("User Registration Repository error :"+e);
			return null;
		}
	}
	
	/*forgot password*/
	public int forgotPasswordUser(LoginModel login) {
		try {
			ps = con.prepareStatement("update registrationmaster set password=? where username=?");
			ps.setString(1, login.getPassword());
			ps.setString(2, login.getUsername());
			int v=ps.executeUpdate();
			return v>0?1:0;
		}catch(Exception e) {
			System.out.println("user regisration repo error :"+e);
			return 0;
		}
	}
	
	/*update new name*/
	public int updateName(RegistrationModel model) {
		try {
			ps =con.prepareStatement("update registrationmaster set customername=? where registerid=?");
			ps.setString(1, model.getCustomername());
			ps.setInt(2, model.getRegisterid());
			int v=ps.executeUpdate();
			return v>0 ?1:0;
		}catch(Exception e) {
			System.out.println("register repo error :"+e);
			return -1;
		}
	}
	
	/*update new email*/
	public int updateEmail(RegistrationModel model) {
		try {
			ps = con.prepareStatement("update registrationmaster set email=? where registerid=?");
			ps.setString(1, model.getEmail());
			ps.setInt(2, model.getRegisterid());
			int v =ps.executeUpdate();
			return v>0 ? 1:0;
			
		}catch(Exception e) {
			System.out.println("register repo error :"+e);
			return -1;
		}
	}
	
	/*update new username*/
	public int updateUsername(RegistrationModel model) {
		try {
			ps = con.prepareStatement("update registrationmaster set username=? where registerid=?");
			ps.setString(1, model.getUsername());
			ps.setInt(2, model.getRegisterid());
			int v =ps.executeUpdate();
			return v>0 ? 1:0;
		}catch(Exception e) {
			System.out.println("register repo error :"+e);
			return -1;
		}
	}
	
	/*check request account delete*/
	public int checkRequestDelete(int registerId) {
		try {
			ps=con.prepareStatement("select registerid from deleterequest where registerid=?");
			ps.setInt(1, registerId);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("register repo :"+e);
			return -1;
		}
	}
	
	/*delete account user*/
	public int deleteUserAccount(int registerId) {
		try {
			ps=con.prepareStatement("insert into deleterequest values(?,(select curdate()))");
			ps.setInt(1, registerId);
			int v=ps.executeUpdate();
			return (v>0)?1:0;
		}catch(Exception e) {
			System.out.println("register repo error :"+e);
			return -1;
		}
	}
	
	/*recover delete requested account*/
	public int recoverAccount(int register) {
		try {
			ps=con.prepareStatement("delete from deleterequest where registerid=?");
			ps.setInt(1, register);
			int v=ps.executeUpdate();
			return (v>0)?1:0;
		}catch(Exception e) {
			System.out.println("register repo :"+e);
			return -1;
		}
	}
	
	/*account information show*/
	public List <AccountInformationModel>accountInformation(int registerid) {
		List<AccountInformationModel> list = new LinkedList<AccountInformationModel>();
		try {
			AccountInformationModel accountInfo = new AccountInformationModel();
			// store customer name and username in LinkedList
			ps = con.prepareStatement("select customername,username from registrationmaster where registerid=?");
			ps.setInt(1, registerid);
			rs=ps.executeQuery();
			if(rs.next()) {
				accountInfo.setCustomername(rs.getString("customername"));
				accountInfo.setUsername(rs.getString("username"));
			}
			
			// we store following count
			ps = con.prepareStatement("select count(fm.followingid) as 'following count' from followingmaster fm "
					+ "inner join userfollowingfollowerjoin uffj on uffj.followingid=fm.followingid "
					+ "inner join registrationmaster rm on rm.registerid=uffj.registerid "
					+ "where rm.registerid=?");
			ps.setInt(1, registerid);
			rs=ps.executeQuery();
			if(rs.next()) {
				accountInfo.setFollowingCount(rs.getInt("following count"));
			}
			
			// we store follower count
			ps = con.prepareStatement("select count(fm.followerid) as 'follower count' from followermaster fm "
					+ "inner join userfollowingfollowerjoin uffj on uffj.followerid=fm.followerid "
					+ "inner join registrationmaster rm on rm.registerid=uffj.registerid "
					+ "where rm.registerid=?");
			ps.setInt(1, registerid);
			rs=ps.executeQuery();
			if(rs.next()) {
				accountInfo.setFollowerCount(rs.getInt("follower count"));
			}
			
			// store post count
			ps = con.prepareStatement("select count(pm.postid) as 'post count' from postmaster pm "
					+ "inner join postregistrationjoin prj on prj.postid=pm.postid "
					+ "inner join registrationmaster rm on rm.registerid=prj.registerid "
					+ "where rm.registerid=?");
			ps.setInt(1, registerid);
			rs=ps.executeQuery();
			if(rs.next()) {
				accountInfo.setPostCount(rs.getInt("post count"));
			}
			
			// store bio
			ps=con.prepareStatement("select bm.bio from biomaster bm "
					+ "inner join bioregistrationjoin brj on brj.bioid=bm.bioid "
					+ "inner join registrationmaster rm on rm.registerid=brj.registerid "
					+ "where brj.registerid=?");
			ps.setInt(1, registerid);
			rs=ps.executeQuery();
			if(rs.next()) {
				accountInfo.setBio(rs.getString(1));
			}
			list.add(accountInfo);
			return list.size()>0 ?list:null;
			
		}catch(Exception e) {
			System.out.println("register repo error :"+e);
			return null;
		}
	}
	
	
	
}
