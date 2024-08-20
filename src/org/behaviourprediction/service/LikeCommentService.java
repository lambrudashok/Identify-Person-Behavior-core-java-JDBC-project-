package org.behaviourprediction.service;

import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.repository.LikeCommentRepository;

public class LikeCommentService {

	LikeCommentRepository likerepo = new LikeCommentRepository();
	
	/*check like already done or not*/
	public int checkLikeUser(int postid,int registerId) {
		return likerepo.checkLikeUser(postid, registerId);
	}
	
	/*logic for like*/
	public boolean isAddLike(PostModel model) {
		return likerepo.isAddLike(model);
	}
	
	/*comment logic*/
	public boolean isAddComment(PostModel model) {
		return likerepo.isAddComment(model);
	}
}
