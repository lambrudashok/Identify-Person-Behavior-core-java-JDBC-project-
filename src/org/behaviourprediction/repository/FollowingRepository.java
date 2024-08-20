package org.behaviourprediction.repository;

import java.util.*;
import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.UserInfoModel;

public class FollowingRepository extends DBHelper{
	
	/*fetch last following id*/
	public int getFollowingID() {
		try {
			ps = con.prepareStatement("select max(followingid) from followingmaster");
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("following repo error :"+e);
			return -1;
		}
		
	}
	
	/*fetch last follower id*/
	public int getFollowerID() {
		try {
			ps = con.prepareStatement("select max(followerid) from followermaster");
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("following repo error :"+e);
			return -1;
		}
		
	}
	
	/*check user following or not*/
	public int checkFollowingUser(int registerIDSearchUser ,int registerId) {
		try {
			ps = con.prepareStatement("select fm.followingid as 'followingid' from followingmaster fm "
					+ "inner join userfollowingfollowerjoin uffj on uffj.followingid=fm.followingid "
					+ "inner join registrationmaster rm on rm.registerid=uffj.registerid "
					+ "where fm.followingregisterid=? and rm.registerid=?");
			ps.setInt(1, registerIDSearchUser);
			ps.setInt(2, registerId);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("followingid");
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("following Repo error :"+e);
			return -1;
		}
	}
	
	/*insert data following user in database */
	public boolean isAddFollowingUser(UserInfoModel model) {
		try {
				ps = con.prepareStatement("insert into  followermaster values('0',?)");
				ps.setInt(1, model.getRegisterid());
				int c=ps.executeUpdate();
				
					int followeruserID=this.getFollowerID();
					ps = con.prepareStatement("insert into userfollowingfollowerjoin(registerid,followerid) values(?,?)");
					ps.setInt(1, model.getFollowerregisterid());
					ps.setInt(2, followeruserID);
					int c2=ps.executeUpdate();
					
			if(c2>0) {
			ps = con.prepareStatement("insert into followingmaster values('0',?)");
			ps.setInt(1, model.getFollowingregisterid());
			int v = ps.executeUpdate();
			
			int followinguserID=this.getFollowingID();
			ps = con.prepareStatement("insert into  userfollowingfollowerjoin (registerid,followingid) values(?,?)");
			ps.setInt(1, model.getRegisterid());
			ps.setInt(2, followinguserID);
			int c1=ps.executeUpdate();
			return (c1>0)?true:false;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.println("Following repo error :"+e);
			return false;
		}
	}
	
	/*logic for unFollow */
	public int unFollowUser(int followingUser) {
		try {
			ps=con.prepareStatement("delete from followingmaster where followingid=?");
			ps.setInt(1, followingUser);
			int v=ps.executeUpdate();
			return (v>0)?1:0;
		}catch(Exception e) {
			System.out.println("following repo error :"+e);
			return -1;
		}
	}
	
	
	/*fetch following all user*/
	public List<Integer> fetchAllFollowingUser(int registerId){
		List<Integer> al =new ArrayList<Integer>();
		try {
			ps = con.prepareStatement("select fm.followingregisterid as 'followingid' from followingmaster fm "
					+ "inner join userfollowingfollowerjoin uffj on uffj.followingid=fm.followingid "
					+ "inner join registrationmaster rm on rm.registerid=uffj.registerid "
					+ "where rm.registerid=?");
			ps.setInt(1, registerId);
			rs=ps.executeQuery();
			while(rs.next()) {
				al.add(rs.getInt(1));
			}
			
			return (al.size()>0)?al:null;
		}catch(Exception e) {
			System.out.println("following repo error :"+e);
			return null;
		}
	}

}
