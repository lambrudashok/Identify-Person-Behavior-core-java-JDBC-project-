package org.behaviourprediction.model;

public class PostModel extends CommentModel{

	private int postid;
	private String post;
	
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
}
