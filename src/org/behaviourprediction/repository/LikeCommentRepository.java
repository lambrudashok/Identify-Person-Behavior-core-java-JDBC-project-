package org.behaviourprediction.repository;

import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.PostModel;

public class LikeCommentRepository extends DBHelper{

	
	/*get last added like id*/
	public int getLikeId() {
		try {
			ps =con.prepareStatement("select max(likeid) from likemaster");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("like repo error :"+e);
			return -1;
		}
	}
	
	/*get last added comment id*/
	public int getCommentId() {
		try {
			ps =con.prepareStatement("select max(commentid) from commentmaster");
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("like repo error :"+e);
			return -1;
		}
	}
	
	/*check like already done or not*/
	public int checkLikeUser(int postid,int registerId) {
		try {
			ps=con.prepareStatement("select lm.likeid from likemaster lm "
					+ "inner join likepostjoin lpj on lpj.likeid=lm.likeid "
					+ "inner join postmaster pm on pm.postid=lpj.postid "
					+ "where pm.postid=? and lm.registerid=?");
			ps.setInt(1, postid);
			ps.setInt(2, registerId);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("post master repo error: "+e);
			return -1;
		}
	}
	
	/*logic for like*/
	public boolean isAddLike(PostModel model) {
		try {
			ps =con.prepareStatement("insert into likemaster values('0',?)");
			ps.setInt(1, model.getRegisterid());
			int v=ps.executeUpdate();
			
			int likeID=this.getLikeId();
			ps=con.prepareStatement("insert into likepostjoin values(?,?)");
			ps.setInt(1, likeID);
			ps.setInt(2, model.getPostid());
			int c=ps.executeUpdate();
			
			return (c>0)?true:false;
		}catch(Exception e) {
			System.out.println("like comment repo error :"+e);
			return false;
		}
	}
	
	
	/*comment logic*/
	public boolean isAddComment(PostModel model) {
		try {
			ps = con.prepareStatement("insert into commentmaster values('0',?,(select curdate()),?)");
			ps.setString(1, model.getComment());
			ps.setInt(2, model.getRegisterid());
			int v=ps.executeUpdate();
			
			int commentID=this.getCommentId();
			ps=con.prepareStatement("insert into postcommentjoin values (?,?)");
			ps.setInt(1, model.getPostid());
			ps.setInt(2, commentID);
			int value=ps.executeUpdate();
			
			return (value>0)?true:false;
		}catch(Exception e) {
			System.out.println("like repo error :"+e);
			return false;
		}
	}
	
	
	
	
	
	
}
