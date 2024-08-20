package org.behaviourprediction.service;

import java.util.*;

import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.model.RegistrationModel;
import org.behaviourprediction.repository.PostMasterRepository;

public class PostMasterService {

	PostMasterRepository postRepo = new PostMasterRepository();
	
	/*add new post*/
	public boolean isaddUserNewPost(RegistrationModel model) {
		return postRepo.isaddUserNewPost(model);
	}
	
	
	/*view All posts or like,comment date wise decreasing order particular user*/
	public List<PostModel> ViewAllPosts(int registerIDSearchUser){
		return postRepo.ViewAllPosts(registerIDSearchUser);
	}
	
	
	/*Delete post from database*/
	public int deletePost(int postID) {
		return postRepo.deletePost(postID);
	}
	
	/*fetch following user posts all according to particular user*/
	public List<PostModel> fetchFollowingAllUserPost(List<Integer> userList){
		List<PostModel> data = new LinkedList<PostModel>();
		if(userList.size()>0) {
			for(Integer userFollowing:userList) {
				List<PostModel> li=postRepo.ViewAllPosts(userFollowing);
				if(li!=null) {
					for(PostModel pm:li) {
						data.add(pm);
					}
				}
			}
			return (data.size()>0)?data:null;
		}else {
			return null;
		}
	}
	
	
	/*fetch All user application posts or like,comment date wise decreasing order based on likes*/
	public List<PostModel> FetchAllUsersApplicationPosts(){
		return postRepo.FetchAllUsersApplicationPosts();
	}
	
}
