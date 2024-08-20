package org.behaviourprediction.model;

public class AccountInformationModel {

	private String customername;
	private String username;
	private int followingCount;
	private int followerCount;
	private int postCount;
	private String bio;
	
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public int getPostCount() {
		return postCount;
	}
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}
	
}
