package org.behaviourprediction.client;

import java.util.*;

import org.behaviourprediction.model.AccountInformationModel;
import org.behaviourprediction.model.BioModel;
import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.model.PredictionModel;
import org.behaviourprediction.model.RegistrationModel;
import org.behaviourprediction.model.TextDocumentDataModel;
import org.behaviourprediction.model.UserInfoModel;
import org.behaviourprediction.service.AdminService;
import org.behaviourprediction.service.BioService;
import org.behaviourprediction.service.FollowingService;
import org.behaviourprediction.service.LikeCommentService;
import org.behaviourprediction.service.PostMasterService;
import org.behaviourprediction.service.PredictionService;
import org.behaviourprediction.service.UserLoginService;
import org.behaviourprediction.service.UserRegistrationService;
public class BehaviourPredictionClientApp {

	public static void main(String[] args) {
		
		AdminService adminSer = new AdminService();                               // Admin service
		UserRegistrationService userRegiSer = new UserRegistrationService();        // user registration service
		UserLoginService userLoginSer = new UserLoginService();               // user login service 
		PostMasterService postSer = new PostMasterService();           // post master service
		FollowingService followingSer = new FollowingService();          // following service
		LikeCommentService likeSer = new LikeCommentService();        // like comment service
		BioService bSer = new BioService();                        // bio service
		PredictionService predictSer = new PredictionService();    // prediction service class object
		
		Scanner sc = new Scanner(System.in);
		int choice=1;
		
		do {
			System.out.println("\n1. Login New User");
			System.out.println("2. Registration New User");
			try {
				System.out.println("Enter your choice");
				choice=sc.nextInt();
				
			}catch(InputMismatchException e) {
				System.out.println("error :"+e);
				System.out.println("-----------------------------------------------------------");
				sc.nextLine();
				System.out.println("Enter your choice");
				choice=sc.nextInt();
			}
			
			switch(choice) {
			
			case 1:
				System.out.println("LOGIN HERE !");
				System.out.println("Enter Username");
				sc.nextLine();
				String username=sc.nextLine();
				System.out.println("Enter Password");
				String password=sc.nextLine();
				LoginModel login = new LoginModel();
				login.setUsername(username);
				login.setPassword(password);
				int adminId = adminSer.checkAdminLogin(login);
				
				// this section for admin 
				if(adminId!=-1) {
					System.out.println("Welcome Admin "+username);
				
					 // admin logic 
					
					int adminLogoutChoice=1;
					do {
						System.out.println("1. Delete user Account");
						System.out.println("2. View All User Accounts");
						System.out.println("3. View Login Info Users");
						System.out.println("4. Prediction");
						System.out.println("5. Logout");
						
						
						try {
							
							System.out.println("Enter choice");
							int adminCaseChoice=sc.nextInt();
							switch(adminCaseChoice) {
						
							case 1:
								// Delete user Account
								List<LoginModel> deleteList=adminSer.getAccountRequestsForDelete();
								if(deleteList!=null) {
									System.out.println("----------------------------------------------------");
									for(LoginModel loginShow:deleteList) {
										System.out.println("register id: "+loginShow.getLoginid()+"\tDate: "+loginShow.getDate());
										System.out.println("----------------------------------------------------");
									}
									System.out.println("Enter delete for register id");
									int registerDelete=sc.nextInt();
									int flag=0;
									for(LoginModel lg:deleteList) {
										if(lg.getLoginid()==registerDelete) {
											flag=1;
											break;
										}
									}
									if(flag!=0) {
										int deleteAc=adminSer.delteUserAccountRequested(registerDelete);
										if(deleteAc>0) {
											System.out.println("user account Deleted");
										}else {
											System.out.println("Not delete user account");
										}
									}else {
										System.out.println("wrong register id");
									}
								}else {
									System.out.println("Requests Not found");
								}
								break;
								
							case 2:
								// View All User Accounts
								List<RegistrationModel> listRegister=adminSer.getAllUserAccountRegisterInfo();
								if(listRegister.size()>0) {
									System.out.println("-----------------------------------------------------------");
									for(RegistrationModel registerInfo:listRegister) {
										System.out.println("register id: "+registerInfo.getRegisterid());
										System.out.println("Customer Name: "+registerInfo.getCustomername());
										System.out.println("Email: "+registerInfo.getEmail());
										System.out.println("Username: "+registerInfo.getUsername()+"\tPassword: "+registerInfo.getPassword());
										System.out.println("-----------------------------------------------------------");
									}
								}else {
									System.out.println("Users not found");
								}
								
								break;
								
							case 3:
								// View Login Info Users
								System.out.println("1. View All Login Users Info");
								System.out.println("2. View Particular User Login Info");
								System.out.println("Enter your choice");
								int userInfoChoi=sc.nextInt();
								switch(userInfoChoi) {
								case 1:
									// View All Login Users Info
									List<LoginModel> list =adminSer.getAllUserLoginInfo();
									if(list.size()>0) {
										System.out.println("------------------------------------------------");
										for(LoginModel loginInfo:list) {
											System.out.println("Login ID: "+loginInfo.getLoginid());
											System.out.println("UserName: "+loginInfo.getUsername()+"\tPassword: "+loginInfo.getPassword());
											System.out.println("Login Date: "+loginInfo.getDate()+"\tLogin Time: "+loginInfo.getTime());
											System.out.println("------------------------------------------------");
										}
									}else {
										System.out.println("Login Information not found");
									}
									break;
									
								case 2:
									// View Particular User Login Info
									System.out.println("Enter User username");
									sc.nextLine();
									String userUserName=sc.nextLine();
									List<LoginModel> loginList=adminSer.getUserLoginInfo(userUserName);
									if(loginList.size()>0) {
										System.out.println("------------------------------------------------");
										for(LoginModel loginInfo:loginList) {
											System.out.println("Login ID: "+loginInfo.getLoginid());
											System.out.println("UserName: "+loginInfo.getUsername()+"\tPassword: "+loginInfo.getPassword());
											System.out.println("Login Date: "+loginInfo.getDate()+"\tLogin Time: "+loginInfo.getTime());
											System.out.println("------------------------------------------------");
										}
									}else {
										System.out.println("User Login Not Found");
									}
									break;
								}
								break;
								
							case 4:
								// Prediction
								System.out.println("\nWelcome to Prediction Section !");
								
								System.out.println("Enter person Username");
								sc.nextLine();
								String predictionUsername=sc.nextLine();
								int predictUserID=predictSer.searchUser(predictionUsername);
								//person found
								if(predictUserID!=0) {
									System.out.println("select option");
									System.out.println("1.Post \t2.Over All");
									int postOverAllOption=sc.nextInt();
									if(postOverAllOption==1) {
										// post prediction logic
										
										// View All Posts person
										List<PostModel> list=postSer.ViewAllPosts(predictUserID);
									    if(list!=null) {
									    	System.out.println("----------------------------------------------");
									    	for(PostModel pm:list) {
									    		System.out.println("post ID: "+pm.getPostid());
									    		System.out.println("post: "+pm.getPost());
									    		System.out.println("like :"+pm.getLikeCount()+"\tcomments: "+pm.getCommentCount());
									    		System.out.println("----------------------------------------------");
									    	}
									    	//select post
									    	System.out.println("Enter post id");
									    	int postID=sc.nextInt();
									    	int personPostId=predictSer.getPostId(postID,predictUserID);
									    	if(personPostId!=0) {
									    		List <PostModel> plist= predictSer.ViewPost(personPostId);
									    		String predictionPost=null;
									    		for(PostModel lis:plist) {
									    			System.out.println("\n\nPredicted Person Behavior");
									    			System.out.println("=================================================================");
									    			System.out.println("post: "+lis.getPost());
									    			System.out.println("like: "+lis.getLikeCount()+"\tcomments: "+lis.getCommentCount());
									    			predictionPost=lis.getPost();
									    		}  		

								    			// prediction person using post
									    		String personBehavior=predictSer.predictPersonBehavior(predictionPost);
									    		System.out.println("\nPerson Behavior :"+personBehavior);
									    		System.out.println("=================================================================");
									    	}else {
									    		System.out.println("Post Not Found");
									    	}
									    	
									    }else {
									    	System.out.println("Post Not found");
									    }
										
									}else if(postOverAllOption==2){	
										
										// OverAll prediction logic
										// posts , comments, like of post
										String[] unlabelledInformation=predictSer.getAllPostsCommentsLikes(predictUserID);
										
										if(unlabelledInformation!=null) {
										System.out.println("\n\nPredicted Person Behavior");
						    			System.out.println("=================================================================\n");
						    			List<PredictionModel> personBehavior=predictSer.predictOverAllPersonBehavior(unlabelledInformation);
						    			for(PredictionModel pm:personBehavior) {
						    				String s[]= pm.getDocName();
						    				for(int i=0;i<s.length;i++) {
						    					System.out.print(s[i]+" ");
						    				}
						    				System.out.println("\tcluster: "+pm.getCluster());
						    			}
						    		    
						    		    System.out.println("=================================================================");
										}else {
											System.out.println("Posts Data Not Found");
										}
						    	
									}
									
								}else {
									System.out.println("person not found");
								}
								
								
								
								break;
								
							case 5:
								// Logout
								System.out.println("Do You Want Logout");
								System.out.println("0. yes | 1. no");
								adminLogoutChoice=sc.nextInt();
								if(adminLogoutChoice==0) {
									System.out.println("Logout Successfully");
								}
								break;
								
							default:
								System.out.println("wrong choice");
							}
						}catch(Exception e) {
							System.out.println(e);
							sc.nextLine();
						}
						
						
					}while(adminLogoutChoice!=0);
					
					
					 // below user logic 
					
				}else{
					
					// this section customer
					int registerId = userRegiSer.checkUserLogin(login);
					if(registerId!=-1) {
						System.out.println("Welcome Back "+username);
						login.setLoginid(registerId);
						boolean l=userLoginSer.isAddUserLogin(login);
						
						// home page 
						int homeProfileChoice=1;                                  // for do while loop
						do {
							System.out.println("\n!! HOME PAGE !!");
							System.out.println("1. User profile");
							System.out.println("2. View trending posts");
							System.out.println("3. search another user account");
							System.out.println("4. following friends all posts shows");
							
							try {
								System.out.println("Enter choice");
								int accountChoice=sc.nextInt();                            // for switch case
								switch(accountChoice) {
								case 1:
									// user profile logic
									System.out.println("1. Account Information");
									System.out.println("2. Update profile");
									System.out.println("3. create post today");
									System.out.println("4. View All posts"); 
									System.out.println("5. forgot password");
									System.out.println("6. Delete post");
									System.out.println("7. delete account");
									System.out.println("8. Logout");
									System.out.println("home page redirect press another number");
									
									System.out.println("Enter your choice");
									int profileChoice=sc.nextInt();
									switch(profileChoice) {
									case 1:
										// Account Information
										List <AccountInformationModel>accountList = userRegiSer.accountInformation(registerId);
										if(accountList!=null) {
											System.out.println("Account Information");
											for(AccountInformationModel list:accountList) {
												System.out.println("--------------------------------------------------");
												System.out.println("Account Name: "+list.getCustomername());
												System.out.println("username: "+list.getUsername());
												System.out.println("following: "+list.getFollowingCount()+"\tfollower: "+list.getFollowerCount());
												System.out.println("Total Posts: "+list.getPostCount());
												System.out.println("Bio: "+list.getBio());
												System.out.println("--------------------------------------------------");	
											}
										}
										break;
										
									case 2:
										// update profile
			
											//update your name
											System.out.println("update your name");
											System.out.println("Enter new first and last name");
											sc.nextLine();
											String newName=sc.nextLine();
											RegistrationModel rmodel = new RegistrationModel();
											rmodel.setCustomername(newName);
											rmodel.setRegisterid(registerId);
											int custNameRet = userRegiSer.updateName(rmodel);
											if(custNameRet==1) {
												System.out.println("Customer Name Updated Successfully\n");
											}
											
											//update your userName
											System.out.println("update your username");
											System.out.println("Enter new username");
											String uname=sc.nextLine();
											int usernameRet=userRegiSer.searchUsername(uname);
											if(usernameRet!=-1) {
												if(usernameRet!=0) {
													System.out.println("UserName already available\n");
												}else {
													RegistrationModel model = new RegistrationModel();
													model.setUsername(uname);
													model.setRegisterid(registerId);
													int usernameResult=userRegiSer.updateUsername(model);
													if(usernameResult==1) {
														System.out.println("Username Updated successfully\n");
													}
												}
											}
											
											
											
											//update your email
											System.out.println("Update your email");
											System.out.println("Enter new Email");
											String userEmail=sc.nextLine();
											int userEmailRet=userRegiSer.searchEmail(userEmail);
											if(userEmailRet!=-1) {
												if(userEmailRet!=0) {
													System.out.println("email already available choose another email\n");
												}else {
													RegistrationModel model = new RegistrationModel();
													model.setEmail(userEmail);
													model.setRegisterid(registerId);
													int emailResult=userRegiSer.updateEmail(model);
													if(emailResult==1) {
														System.out.println("Email Updated Successfully\n");
													}
												}	
											}
											
											
											
											// add | update bio
											int bioSearch=bSer.searchBio(registerId);
											if(bioSearch!=0) {
												// update bio
												System.out.println("Update Your Bio");
												System.out.println("Enter bio");
												String bio=sc.nextLine();
												BioModel bmodel=new BioModel();
												bmodel.setBio(bio);
												bmodel.setBioid(bioSearch);
												int updateBioResult=bSer.updateBio(bmodel);
												if(updateBioResult==1) {
													System.out.println("Bio updated successfully\n");
												}
											}else {
												// add bio
												System.out.println("Add new Bio");
												System.out.println("Enter new Bio");
												String bio=sc.nextLine();
												BioModel bm = new BioModel();
												bm.setBio(bio);
												boolean addBioResult=bSer.isaddBio(bm, registerId);
												if(addBioResult) {
													System.out.println("Bio Added successfully\n");
												}
											}
											
											
										break;
										
									case 3:
										// create post today
										System.out.println("Enter post today");
										sc.nextLine();
										String todayPost=sc.nextLine();
										RegistrationModel model= new RegistrationModel();
										model.setRegisterid(registerId);
										model.setPost(todayPost);
										boolean b=postSer.isaddUserNewPost(model);
										if(b) {
											System.out.println("Post Created Successully");
										}else {
											System.out.println("Post not created");
										}
										break;
									
									case 4:
										// View All Posts
										List<PostModel> list=postSer.ViewAllPosts(registerId);
									    if(list!=null) {
									    	System.out.println("----------------------------------------------");
									    	for(PostModel pm:list) {
									    		System.out.println("post: "+pm.getPost());
									    		System.out.println("like :"+pm.getLikeCount()+"\tcomments: "+pm.getCommentCount());
									    		System.out.println("----------------------------------------------");
									    	}
									    }else {
									    	System.out.println("Post Not found");
									    }
										break;
										
									case 5:
										// forgot password
										System.out.println("Enter new password");
										sc.nextLine();
										String newPass=sc.nextLine();
										LoginModel forgotPass = new LoginModel();
										forgotPass.setUsername(username);
										forgotPass.setPassword(newPass);
										int done=userRegiSer.forgotPasswordUser(forgotPass);
										String s=done>0?"your password changed successfully":"password not changed";
										System.out.println(s);
										break;
										
									case 6:
										// Delete post
										List<PostModel> Postlist=postSer.ViewAllPosts(registerId);
									    if(Postlist!=null) {
									    	System.out.println("----------------------------------------------");
									    	for(PostModel pm:Postlist) {
									    		System.out.println("post id: "+pm.getPostid());
									    		System.out.println("post: "+pm.getPost());
									    		System.out.println("like :"+pm.getLikeCount()+"\tcomments: "+pm.getCommentCount());
									    		System.out.println("----------------------------------------------");
									    	}
									    	System.out.println("Delete Post for\nEnter post id");
									    	int deletePostid=sc.nextInt();
									    	int flag=0;
									    	for(PostModel pm:Postlist) {
									    		if(pm.getPostid()==deletePostid) {
									    			flag=1;
									    			break;
									    		}
									    	}
									    	if(flag!=0) {
									    		int delete=postSer.deletePost(deletePostid);
									    		if(delete!=0) {
									    			System.out.println("Post Deleted successfully");
									    		}else {
									    			System.out.println("some problem ...");
									    		}
									    	}else {
									    		System.out.println("you choose wrong post id");
									    	}
									    	
									    }else {
									    	System.out.println("Post Not Found");
									    }
										break;
										
									case 7:
										// delete account
										System.out.println("1. delete account");
										System.out.println("2. recover account/cancel delete");
										System.out.println("Enter choice");
										int deleteChoice=sc.nextInt();
										switch(deleteChoice) {
										case 1:
											// delete account
											int deletereq=userRegiSer.checkRequestDelete(registerId);
											if(deletereq>0) {
												System.out.println("you are already requested for delete account");
											}else {
											int deleteAccount=userRegiSer.deleteUserAccount(registerId);
											if(deleteAccount>0) {
												System.out.println("delete request send");
											}else {
												System.out.println("some problem..");
											}
											}
											break;
											
										case 2:
											// recover account
											int recoverAcc=userRegiSer.checkRequestDelete(registerId);
											if(recoverAcc!=0) {
												int recoverUserAcc=userRegiSer.recoverAccount(registerId);
												if(recoverUserAcc!=0) {
													System.out.println("Your Account Recover");
												}
											}else {
												System.out.println("you are not send request for delete account");
											}
											System.out.println();
											
											break;
										default:
											System.out.println("wrong choice");
										}
											
										break;
									
										
									case 8:
										// Logout
										System.out.println("Do you want Logout");
										System.out.println("( 0. YES | 1. No ");
										homeProfileChoice=sc.nextInt();
										if(homeProfileChoice==0) {
											System.out.println("Logout Successful");
										}
										break;
												
										default:
										System.out.println("back to home page!");
									}//  user profile switch case
									break;
									
								case 2:
									// view trending posts logic
									List<PostModel> traindingPosts=postSer.FetchAllUsersApplicationPosts();
									if(traindingPosts!=null) {
										System.out.println("-------------------------------------------------");
										for(PostModel allPosts:traindingPosts) {
											System.out.println("post id: "+allPosts.getPostid());
											System.out.println("post: "+allPosts.getPost());
											System.out.println("like: "+allPosts.getLikeCount()+"\tcomment: "+allPosts.getCommentCount());
											System.out.println("-------------------------------------------------");
										}
										
										int likeCommentChoice=0;
								    	do {
								    		System.out.println("Do you want like or comment");
									    	System.out.println("1. like | 2. comment | 0. no");
									    	likeCommentChoice=sc.nextInt();
								    		if(likeCommentChoice==1 || likeCommentChoice==2) {
								    			System.out.println("Enter post id");
								    			int userPostID=sc.nextInt();
								    			int flag=0;
								    			for(PostModel Spostid:traindingPosts) {
								    				if(Spostid.getPostid()==userPostID) {
								    					flag=1;
								    					break;
								    				}
								    			}
								    			if(flag!=0) {
								    				switch(likeCommentChoice) {
								    				case 1:
								    					// like
								    					int like=likeSer.checkLikeUser(userPostID, registerId);                                       
								    					if(like!=0) {
								    						System.out.println("already liked");
								    					}else {
								    						PostModel pm = new PostModel();
								    						pm.setPostid(userPostID);
								    						pm.setRegisterid(registerId);
								    						boolean b= likeSer.isAddLike(pm);
								    						if(b) {
								    							System.out.println("liked");
								    						}else {
								    							System.out.println("some problem is there...");
								    						}
								    					}
								    					break;
								    					
								    				case 2:
								    					// comment
								    					System.out.println("Enter Your Comment");
								    					sc.nextLine();
								    					String userComment=sc.nextLine();
								    					PostModel pmodel = new PostModel();
								    					pmodel.setComment(userComment);
								    					pmodel.setRegisterid(registerId);
								    					pmodel.setPostid(userPostID);
								    					boolean b=likeSer.isAddComment(pmodel);
								    					if(b) {
								    						System.out.println("comment added successfully");
								    					}else {
								    						System.out.println("comment not add");
								    					}
								    					break;
								    				}
								    			}else {
								    				System.out.println("wrong post id");
								    			}
								    		}
								    	}while(likeCommentChoice!=0); // like comment
										
										
										
									}
									break;
									
								case 3:
									// search another user account
									System.out.println("Enter username for search");
									sc.nextLine();
									String userName=sc.nextLine();
									
									List<String> userSearch=userRegiSer.showSearchUserNames(userName);
									if(userSearch!=null) {
										System.out.println("Suggestion usernames");
										for(String userSearchName:userSearch) {
											System.out.println("-----------------------------------------");
											System.out.println(userSearchName);
										}
										System.out.println("-----------------------------------------");
										System.out.println("\nselect username");
										userName=sc.nextLine();
										int registerIDSearchUser=userRegiSer.searchUsername(userName);
										if(registerIDSearchUser!=0) {
											
											// search user account information
											List <AccountInformationModel>accountList = userRegiSer.accountInformation(registerIDSearchUser);
											if(accountList!=null) {
												System.out.println("Account Information");
												for(AccountInformationModel list:accountList) {
													System.out.println("--------------------------------------------------");
													System.out.println("Account Name: "+list.getCustomername());
													System.out.println("username: "+list.getUsername());
													System.out.println("following: "+list.getFollowingCount()+"\tfollower: "+list.getFollowerCount());
													System.out.println("Total Posts: "+list.getPostCount());
													System.out.println("Bio: "+list.getBio());
													System.out.println("--------------------------------------------------");	
												}
											}
											int searchUser=1;
											
											do {
												System.out.println("1. follow");
												System.out.println("2. unfollow");
												System.out.println("3. View All Posts");
												System.out.println("4. Back");
												
												System.out.println("Enter your choice");
												int searchChoice=sc.nextInt();
												
												switch(searchChoice) {
												case 1:
													// following
													int followingUserID=followingSer.checkFollowingUser(registerIDSearchUser,registerId);
													if(followingUserID!=0) {
														System.out.println("You are already following");
													}else {
														UserInfoModel umodel = new UserInfoModel();
														umodel.setRegisterid(registerId);
														umodel.setFollowerregisterid(registerIDSearchUser);
														umodel.setFollowingregisterid(registerIDSearchUser);
														
														boolean b = followingSer.isAddFollowingUser(umodel);
														if(b) {
															System.out.println("Following");
														}else {
															System.err.println("Not Following");
														}
														
													}
													break;
													
												case 2:
													// unFollow
													int unfollowingUserID=followingSer.checkFollowingUser(registerIDSearchUser,registerId);
													if(unfollowingUserID!=0) {
														int followingDelete=followingSer.unFollowUser(unfollowingUserID);
														if(followingDelete>0) {
															System.out.println("unfollow");	
														}else {
															System.out.println("some proble is there...");
														}	
													}else {
														System.out.println("You are already unfollowing");
													}
													break;
													
												case 3:
													// View All Posts
													
												    List<PostModel> list=postSer.ViewAllPosts(registerIDSearchUser);
												    if(list!=null) {
												    	System.out.println("----------------------------------------------");
												    	for(PostModel pm:list) {
												    		System.out.println("post id: "+pm.getPostid());
												    		System.out.println("post: "+pm.getPost());
												    		System.out.println("like :"+pm.getLikeCount()+"\tcomments: "+pm.getCommentCount());
												    		System.out.println("----------------------------------------------");
												    	}
												    	int likeCommentChoice=0;
												    	do {
												    		System.out.println("Do you want like or comment");
													    	System.out.println("1. like | 2. comment | 0. no");
													    	likeCommentChoice=sc.nextInt();
												    		if(likeCommentChoice==1 || likeCommentChoice==2) {
												    			System.out.println("Enter post id");
												    			int userPostID=sc.nextInt();
												    			int flag=0;
												    			for(PostModel Spostid:list) {
												    				if(Spostid.getPostid()==userPostID) {
												    					flag=1;
												    					break;
												    				}
												    			}
												    			if(flag!=0) {
												    				switch(likeCommentChoice) {
												    				case 1:
												    					// like
												    					int like=likeSer.checkLikeUser(userPostID, registerId);                                       
												    					if(like!=0) {
												    						System.out.println("already liked");
												    					}else {
												    						PostModel pm = new PostModel();
												    						pm.setPostid(userPostID);
												    						pm.setRegisterid(registerId);
												    						boolean b= likeSer.isAddLike(pm);
												    						if(b) {
												    							System.out.println("liked");
												    						}else {
												    							System.out.println("some problem is there...");
												    						}
												    					}
												    					break;
												    					
												    				case 2:
												    					// comment
												    					System.out.println("Enter Your Comment");
												    					sc.nextLine();
												    					String userComment=sc.nextLine();
												    					PostModel pmodel = new PostModel();
												    					pmodel.setComment(userComment);
												    					pmodel.setRegisterid(registerId);
												    					pmodel.setPostid(userPostID);
												    					boolean b=likeSer.isAddComment(pmodel);
												    					if(b) {
												    						System.out.println("comment added successfully");
												    					}else {
												    						System.out.println("comment not add");
												    					}
												    					break;
												    				}
												    			}else {
												    				System.out.println("wrong post id");
												    			}
												    		}
												    	}while(likeCommentChoice!=0); // like comment
												    }else {
												    	System.out.println("Post Not found");
												    }
													
													break;
													
												case 4:
													// Back
													if(searchChoice==4) {
														System.out.println("Back to home page");
														searchUser=0;
													}
													break;
												}
												
											}while(searchUser!=0); // search user loop
											
										}else {
											System.out.println("User not found");
										}
										
									}else {
										System.out.println("User not founds");
									}	
									break;
									
								case 4: 
									//following friends all posts shows
									List<Integer> list=followingSer.fetchAllFollowingUser(registerId);
									System.out.println(list);
									if(list!=null) {
										List<PostModel> followingUserData =postSer.fetchFollowingAllUserPost(list);
										if(followingUserData!=null) {
											System.out.println("-------------------------------------------------");
											for(PostModel userPosts:followingUserData) {
												System.out.println("post id: "+userPosts.getPostid());
												System.out.println("post: "+userPosts.getPost());
												System.out.println("like: "+userPosts.getLikeCount()+"\tcomment: "+userPosts.getCommentCount());
												System.out.println("-------------------------------------------------");
											}
											
											int likeCommentChoice=0;
									    	do {
									    		System.out.println("Do you want like or comment");
										    	System.out.println("1. like | 2. comment | 0. no");
										    	likeCommentChoice=sc.nextInt();
									    		if(likeCommentChoice==1 || likeCommentChoice==2) {
									    			System.out.println("Enter post id");
									    			int userPostID=sc.nextInt();
									    			int flag=0;
									    			for(PostModel Spostid:followingUserData) {
									    				if(Spostid.getPostid()==userPostID) {
									    					flag=1;
									    					break;
									    				}
									    			}
									    			if(flag!=0) {
									    				switch(likeCommentChoice) {
									    				case 1:
									    					// like
									    					int like=likeSer.checkLikeUser(userPostID, registerId);                                       
									    					if(like!=0) {
									    						System.out.println("already liked");
									    					}else {
									    						PostModel pm = new PostModel();
									    						pm.setPostid(userPostID);
									    						pm.setRegisterid(registerId);
									    						boolean b= likeSer.isAddLike(pm);
									    						if(b) {
									    							System.out.println("liked");
									    						}else {
									    							System.out.println("some problem is there...");
									    						}
									    					}
									    					break;
									    					
									    				case 2:
									    					// comment
									    					System.out.println("Enter Your Comment");
									    					sc.nextLine();
									    					String userComment=sc.nextLine();
									    					PostModel pmodel = new PostModel();
									    					pmodel.setComment(userComment);
									    					pmodel.setRegisterid(registerId);
									    					pmodel.setPostid(userPostID);
									    					boolean b=likeSer.isAddComment(pmodel);
									    					if(b) {
									    						System.out.println("comment added successfully");
									    					}else {
									    						System.out.println("comment not add");
									    					}
									    					break;
									    				}
									    			}else {
									    				System.out.println("wrong post id");
									    			}
									    		}
									    	}while(likeCommentChoice!=0); // like comment
											
										}else {
											System.out.println("Post not available");
										}
									}else {
										System.out.println("Posts Not available");
									}
									break;
									
								default:
									System.out.println("wrong choice");
								}
							}catch(Exception e) {
								System.out.println(e);
								sc.nextLine();
							}
							
							
						}while(homeProfileChoice!=0);                        // home trending profile
						
					}else {
						System.out.println("User not found");
					}
				}
				break;
				
			case 2:
				System.out.println("REGISTRATION HERE !");
				System.out.println("Enter first and last Name");
				sc.nextLine();
				String customerName=sc.nextLine();
				System.out.println("Enter email");
				String email=sc.nextLine();
				int registerID=userRegiSer.searchEmail(email);
				if(registerID==0) {
					System.out.println("Enter username");
					String userName=sc.nextLine();
					registerID=userRegiSer.searchUsername(userName);
					if(registerID==0) {
						System.out.println("Enter password");
						String passWord=sc.nextLine();
						RegistrationModel registration = new RegistrationModel();
						registration.setCustomername(customerName);
						registration.setEmail(email);
						registration.setUsername(userName);
						registration.setPassword(passWord);
						boolean b = userRegiSer.isAddNewUserRegistration(registration);
						if(b) {
							System.out.println("Registration Successfully");
							
						}else System.out.println("Registration Not Done");
					}else System.out.println("username already taken");
				}else System.out.println("email already taken by someone");
				
				break;
				
				default:
					System.out.println("Wrong choice");
			}
			
		}while(true);
	}

}
