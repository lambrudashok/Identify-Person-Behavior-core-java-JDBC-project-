package org.behaviourprediction.service;

import java.util.List;

import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.model.PredictionModel;
import org.behaviourprediction.model.TextDocumentDataModel;
import org.behaviourprediction.repository.PredictionRepository;

public class PredictionService {

	PredictionRepository predictRepo = new PredictionRepository();  // prediction repository class object
	
	// search user
	public int searchUser(String username) {
		return predictRepo.searchUser(username);
	}
	
	// fetch post id 
	public int getPostId(int postid,int predictUserID) {
		return predictRepo.getPostId(postid,predictUserID);
	}
	
	// fetch single post , comment , like 
		public List<PostModel> ViewPost(int postid){
			return predictRepo.ViewPost(postid);
		}
		
	// prediction person behavior using  post
     	public String predictPersonBehavior(String post) {
     		int predicted=predictRepo.predictPersonBehavior(post);
     		if(predicted==1)
     			return "Openess To Experience";
     		else if(predicted==2)
     			return "Conscientiousness";
     		else if(predicted==3)
     			return "Extroversion";
     		else if(predicted==4)
     			return "agreeableness";
     		else
     			return "Neuroticism";
     	}
     	
     /* get All posts comments and like of posts particular user */
    	public String[] getAllPostsCommentsLikes(int predictUserID) { 
    		return predictRepo.getAllPostsCommentsLikes(predictUserID);
    	}
     	
     	
     // prediction person behavior overall
     	public List<PredictionModel> predictOverAllPersonBehavior(String[] unlabelledInformation){
     		return predictRepo.predictOverAllPersonBehavior(unlabelledInformation);
     	}
}
