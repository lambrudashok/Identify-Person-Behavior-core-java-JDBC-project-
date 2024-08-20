package org.behaviourprediction.service;

import java.util.List;

import org.behaviourprediction.model.UserInfoModel;
import org.behaviourprediction.repository.FollowingRepository;

public class FollowingService {

	FollowingRepository followingrepo = new FollowingRepository(); // following repository 
	
	/*check user following or not*/
	public int checkFollowingUser(int registerIDSearchUser, int registerId) {
		return followingrepo.checkFollowingUser(registerIDSearchUser,registerId);
	}
	
	/*logic for follow*/
	public boolean isAddFollowingUser(UserInfoModel model) {
		return followingrepo.isAddFollowingUser(model);
	}
	
	/*logic for unFollow */
	public int unFollowUser(int followingUser){
		return followingrepo.unFollowUser(followingUser);
	}
	
	/*fetch following all user*/
	public List<Integer> fetchAllFollowingUser(int registerId){
		return followingrepo.fetchAllFollowingUser(registerId);
	}
}
