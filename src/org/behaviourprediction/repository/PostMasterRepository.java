package org.behaviourprediction.repository;

import java.util.*;
import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.model.RegistrationModel;

public class PostMasterRepository extends DBHelper{
	
	/*generate postid*/
	public int autoIncrementPostID() {
		try {
			ps= con.prepareStatement("select max(postid) from postmaster");
			rs =ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
			
		}catch(Exception e) {
			System.out.println("autoIncrementPostID error :"+e);
			return -1;
		}
	}
	/*insert data in post master table*/
	public boolean isaddUserNewPost(RegistrationModel model) {
		try {
			int postID=this.autoIncrementPostID();
			postID=postID+1;
			
			ps = con.prepareStatement("insert into postmaster values(?,?,(select curdate()))");
			ps.setInt(1, postID);
			ps.setString(2, model.getPost());
			int v=ps.executeUpdate();
			
			if(v>0) {
				ps =con.prepareStatement("insert into postregistrationjoin values (?,?)");
				ps.setInt(1, postID);
				ps.setInt(2, model.getRegisterid());
				v=ps.executeUpdate();
			}
			return v>0?true:false;
		}catch(Exception e) {
			System.out.println("post master repository error :"+e);
			return false;
		}
	}
	
	
	/*view All posts or like,comment date wise decreasing order particular user*/
	public List<PostModel> ViewAllPosts(int registerIDSearchUser){
		List<PostModel> list= new LinkedList<PostModel>(); // store particular user post id ,post,like,comment count
		
		ArrayList<Integer> alPost =new ArrayList<Integer>();   // store post id particular user
		
		try {
			// we fetch post id and store in ArrayList
			ps =con.prepareStatement("select pm.postid from postmaster pm "
					+ "inner join postregistrationjoin prj on pm.postid=prj.postid "
					+ "inner join registrationmaster rm on rm.registerid=prj.registerid where rm.registerid=? "
					+ "order by pm.postdate desc");
			ps.setInt(1, registerIDSearchUser);
			rs=ps.executeQuery();
			while(rs.next()) {
				int postID=rs.getInt(1);
				alPost.add(postID);
			}
			
			// we fetch post id ,post , count like ,comment count for particular post
			if(alPost.size()>0) {               // we check user posts not found
				for(Integer lc:alPost) {
					
					PostModel pmodel = new PostModel();
				
					pmodel.setPostid(lc);      // set post id
					
					ps=con.prepareStatement("select post from postmaster where postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setPost(rs.getString(1));   // set post
					}
					
					// we fetch like count of post
					ps =con.prepareStatement("select count(lm.likeid) from likemaster lm "
							+ "inner join likepostjoin lpj on lpj.likeid=lm.likeid "
							+ "inner join postmaster pm on pm.postid=lpj.postid "
							+ "where pm.postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setLikeCount(rs.getInt(1));   // set count like post 
					}
					
					// we fetch comment count of post
					ps=con.prepareStatement("select count(cm.commentid) from commentmaster cm "
							+ "inner join postcommentjoin pcj on pcj.commentid=cm.commentid "
							+ "inner join postmaster pm on pm.postid=pcj.postid "
							+ "where pm.postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setCommentCount(rs.getInt(1)); //  set comment count of post
					}
				  list.add(pmodel);
				}
			}
			
			return (list.size()>0) ? list:null;
		}catch(Exception e) {
			System.out.println("post master repository error :"+e);
			return null;
		}
	}
	
	
	/*Delete post from database*/
	public int deletePost(int postID) {
		try {
			ps=con.prepareStatement("delete from postmaster where postid=?");
			ps.setInt(1, postID);
			int value=ps.executeUpdate();
			return (value>0)?1:0;
		}catch(Exception e) {
			System.out.println("post repo error :"+e);
			return -1;
		}
	}
	
	
	
	/*fetch All user application posts or like,comment date wise decreasing order based on likes*/
	public List<PostModel> FetchAllUsersApplicationPosts(){
		List<PostModel> list= new LinkedList<PostModel>(); // store particular user post id ,post,like,comment count
		
		ArrayList<Integer> alPost =new ArrayList<Integer>();   // store post id particular user
		
		try {
			// we fetch post id and store in ArrayList
			ps =con.prepareStatement("select pm.postid,count(lm.likeid) as 'like count' from postmaster pm "
					+ "left join likepostjoin lpj on lpj.postid=pm.postid "
					+ "left join likemaster lm on lm.likeid=lpj.likeid "
					+ "group by pm.postid "
					+ "order by count(lm.likeid) desc");
			rs=ps.executeQuery();
			while(rs.next()) {
				int postID=rs.getInt(1);
				alPost.add(postID);
			}
			
			// we fetch post id ,post , count like ,comment count for particular post
			if(alPost.size()>0) {               // we check user posts not found
				for(Integer lc:alPost) {
					
					PostModel pmodel = new PostModel();
				
					pmodel.setPostid(lc);      // set post id
					
					ps=con.prepareStatement("select post from postmaster where postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setPost(rs.getString(1));   // set post
					}
					
					// we fetch like count of post
					ps =con.prepareStatement("select count(lm.likeid) from likemaster lm "
							+ "inner join likepostjoin lpj on lpj.likeid=lm.likeid "
							+ "inner join postmaster pm on pm.postid=lpj.postid "
							+ "where pm.postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setLikeCount(rs.getInt(1));   // set count like post 
					}
					
					// we fetch comment count of post
					ps=con.prepareStatement("select count(cm.commentid) from commentmaster cm "
							+ "inner join postcommentjoin pcj on pcj.commentid=cm.commentid "
							+ "inner join postmaster pm on pm.postid=pcj.postid "
							+ "where pm.postid=?");
					ps.setInt(1, lc);
					rs=ps.executeQuery();
					if(rs.next()) {
						pmodel.setCommentCount(rs.getInt(1)); //  set comment count of post
					}
				  list.add(pmodel);
				}
			}
			
			return (list.size()>0) ? list:null;
		}catch(Exception e) {
			System.out.println("post master repository error :"+e);
			return null;
		}
	}
	
	
	
	

}
