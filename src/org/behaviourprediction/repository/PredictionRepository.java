package org.behaviourprediction.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.PostModel;
import org.behaviourprediction.model.PredictionModel;
import org.behaviourprediction.model.TextDocumentDataModel;

import com.mysql.cj.exceptions.UnableToConnectException;

public class PredictionRepository extends DBHelper{
	
	// search user
	public int searchUser(String username) {
    	try {
		ps=con.prepareStatement("select registerid from registrationmaster where username=?");
		ps.setString(1, username);
		rs=ps.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}else {
			return 0;
		}
	}catch(Exception e) {
		System.out.println("Prediction error :"+e);
		return -1;
	   }
    }
	
	// fetch post id 
	public int getPostId(int postid,int predictUserID) {
		try {
			ps=con.prepareStatement("select pm.postid from postmaster pm "
					+ "inner join postregistrationjoin prj on prj.postid=pm.postid "
					+ "inner join registrationmaster rm on rm.registerid=prj.registerid "
					+ "where pm.postid=? and rm.registerid=?");
			ps.setInt(1, postid);
			ps.setInt(2, predictUserID);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("postid");
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("prediction error :"+e);
			return -1;
		}
	}
	
	// fetch single post , comment , like 
	public List<PostModel> ViewPost(int postid){
		List<PostModel> list = new LinkedList<PostModel>();
		try {
			// we fetch post id ,post , count like ,comment count for particular post
			PostModel pmodel = new PostModel();	
			
			ps=con.prepareStatement("select post from postmaster where postid=?");
			ps.setInt(1, postid);
			rs=ps.executeQuery();
			if(rs.next()) {
				pmodel.setPost(rs.getString(1));   // set post
			}
			// we fetch like count of post
			ps =con.prepareStatement("select count(lm.likeid) from likemaster lm "
					+ "inner join likepostjoin lpj on lpj.likeid=lm.likeid "
					+ "inner join postmaster pm on pm.postid=lpj.postid "
					+ "where pm.postid=?");
			ps.setInt(1, postid);
			rs=ps.executeQuery();
			if(rs.next()) {
				pmodel.setLikeCount(rs.getInt(1));   // set count like post 
			}	
			// we fetch comment count of post
			ps=con.prepareStatement("select count(cm.commentid) from commentmaster cm "
					+ "inner join postcommentjoin pcj on pcj.commentid=cm.commentid "
					+ "inner join postmaster pm on pm.postid=pcj.postid "
					+ "where pm.postid=?");
			ps.setInt(1, postid);
			rs=ps.executeQuery();
			if(rs.next()) {
				pmodel.setCommentCount(rs.getInt(1)); //  set comment count of post
			}
				  list.add(pmodel);
			
			return (list.size()>0) ? list:null;
		}catch(Exception e) {
			System.out.println("prediction error :"+e);
			return null;
		}
	}
	
	
	
	// text Tokenization 
    	public String[] textTokenization(String post) {
    		String[] postToken = post.split(" ");
    		return postToken;
    	}
    
    // remove symbols and white spaces 
        public String removeSpecialSymbols(String str) {
           String newStr="";
           boolean before=true;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if ((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {
                    newStr += ch;
                    before=false;
                }else if(ch==32){
                	if(!before) {
                		newStr += ch;
                        before=true;
                	}
                }
            }
            return newStr;
        }
        
             
        
     // remove stop words (means not meaningful for prediction)
		public String[] removeStopWords(String[] str) {
			int len=str.length; 
			// stop words
			String[] stopWords= {"a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on",
					"once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"
					};
			for(int i=0;i<len;i++) {
				for(int j=0;j<stopWords.length;j++) {
					if(str[i].compareTo(stopWords[j])==0){
						for(int k=i;k<len-1;k++) {
							str[k]=str[k+1];
						}
						--i;
						--len;
						break;
					}
				}
			}
			
			// after remove stop words in post 
			String[] newStr=new String[len];
			for(int i=0;i<len;i++) {
				newStr[i]=str[i];
			}
			return newStr;
		}
	
		
	
		
	// BOW (Bag Of Words) convert into vectors
		public int[] convertBOWVectors(String[] str) {
		    List<Integer> list=new LinkedList<Integer>();
		    int count=0;
		    int flag=0;
			for(int i=0;i<str.length;i++) {
				//check before word
				for(int k=0;k<i;k++) {
					if(str[i].compareTo(str[k])==0) {
						flag=1;
						break;
					}
				}
				if(flag!=1) {
					for(int j=i;j<str.length;j++) {
					    if(str[i].compareTo(str[j])==0) {
							count++;
						}
					}
					list.add(count);
					count=0;
				}else {
					flag=0;
				}
			}
			//convert List to array
			int arr[]=new int[list.size()];
			for(int i=0;i<list.size();i++) {
				arr[i]=(int)list.get(i);
			}
			
			return arr;
			
			
		}
	
		
		// 1.openness to experience
		String[] openessToExperience1={"adapt","adventure","adventurousness","analytical skills","analytic","art","artist","awareness","clever","cleverness","creative","creativity","curiosity","curious","daring","daringness","dream","dreaminess","emotion","excitement","experience","exploration","fantasy","flexible","free person","imagination","imagine","independent","individual","innovation","inspiration","intellect","intelligent","interest","invention","logical thinking","novelty","open mind","passion","perceptive","perceptiveness","self motivation","sensitivity","unique","uniqueness","understanding","vision"};
		    
		// 2. Conscientiousness
		String[] conscientiousness2= {"achievement","aim","ambition","ambitious","arranging","arrange","aspire","aspiration","careful","carefully","compliantly","conscientiously","consistency","control","coordinating","coordinated","determination","dutifully","energy","faithfully","goal","hard work","hard working","idealism","mastery","methodical","motivation","obediently","organization","organized","persistence","perseverance","planning","planned","prearranged","preparing","prepared","preplanned","predictability","problem solving","purpose","reliably","reliability","resolve","resourcefulness","responsible","restraint","self control","self discipline","self regulation","structured","success","thoroughness","willpower"};
			
		// 3.Extroversion
		String[] extroversion3= {"adventure","caring","cheerfulness","cheerfully","communication skills","confidence","conscientiousness","cordiality","dedication","diligence","dynamism","efficiency","engagement","enjoyment","enjoyment of company","enthusiasm","excitement","friend","friendliness","friendly","funny","geniality","great","hard work","happiness","happy","jolliness","joviality","joyfulness","kindness","leadership","lightheartedness","liveliness","loving nature","love","nature","novelty seeking","productivity","playing","play","risk taking","self assurance","self esteem","self reliance","social","social confidence","sociability","structure","systematic approach","vitality"};

		// 4. Agreeableness
		String[] agreeableness4={"accommodating","adversarial","aggressive","agreement","agree","altruism","allegiance","assistance","assistive","assurance","balance","belief","benevolence","brightness","calmness","caring","charitableness","cheerfulness","compassion","compassionate","composure","confidence","consideration","combative","collaborative","cooperation","devotion","demureness","dependability","dependence","endurance","empathy","faith","faithfulness","fidelity","friendliness","forbearance","generosity","gentleness","happiness","helping nature","helpfulness","humbleness","humility","joyfulness","kindness","lowliness","loyalty","meekness","mistrustful","modesty","moderation","nurturing","optimism","oppositional","patience","perseverance","philanthropy","politeness","positive","positivity","reliance","reserve","respectfulness","self-assurance","self-control","self-effacement","selflessness","sensitivity","service","simplicity","support","supportive","suspicious","sympathy","tact","team oriented","trust","trustworthiness","unassuming nature","unpretentiousness","unselfishness","understanding","usefulness","wary"};
		
		// 5. Neuroticism
		String[] neuroticism5={"agitation","anger","annoying","anxiety","apprehension","awkwardness","bad","brooding","clumsiness","compulsiveness","crankiness","crossness","depression","despair","despondency","diffidence","discomfort","distrust","dwelling","embarrassment","fear","fickleness","fixation","frustration","frustrated","fury","gloom","grumpiness","hopelessness","impulsiveness","insecurity","instability","irritability","jealousy","lack of confidence","madness","mania","melancholy","moodiness","mulling over","nervousness","negativity","not good","not trust","obsessing","obsessiveness","outrage","overthinking","panic","peevishness","pessimism","pettishness","pondering","rage","resentment","rumination","sadness","self-criticism","self doubt","suspicion","tension","testiness","terror","timidness","unease","urgency","vulnerability","wariness","weakness","worry","wrath"};

		// personality vectors
		public int[] personalityConvertsVector(String[] post,String[] personality) {
			List<Integer> list=new ArrayList<Integer>();
		    int flag=0;
			for(int i=0;i<post.length;i++) {
				//check before word
				for(int k=0;k<i;k++) {
					if(post[i].compareTo(post[k])==0) {
						flag=1;
						break;
					}
				}
				int flag2=0;
				if(flag!=1) {
					for(int j=0;j<personality.length;j++) {
					    if(post[i].compareTo(personality[j])==0) {
					    	list.add(1);
					    	flag2=1;
					    	break;
						}
					}
					if(flag2!=1) {
						list.add(0);
					}
				}else {
					flag=0;
				}
			}
			//convert List to array
			int arr[]=new int[list.size()];
			for(int i=0;i<list.size();i++) {
				arr[i]=list.get(i);
			}
			return arr;
		}
		
	// k means clustering
		public int kMeansClustering(int[] postVectors,String[] afterStopWord) {
			List<PredictionModel> list=new ArrayList<PredictionModel>(); // store personality vectors
			try {
			    int[] openess_1=this.personalityConvertsVector(afterStopWord, openessToExperience1);
			    int[] conscient_2=this.personalityConvertsVector(afterStopWord, conscientiousness2);
			    int[] extrover_3=this.personalityConvertsVector(afterStopWord, extroversion3);
			    int[] agreeble_4=this.personalityConvertsVector(afterStopWord, agreeableness4);
			    int[] neurot_5=this.personalityConvertsVector(afterStopWord, neuroticism5);
			
			    PredictionModel model = new PredictionModel();
			    model.setArr(openess_1);
			    list.add(model);
			    model = new PredictionModel();
			    model.setArr(conscient_2);
			    list.add(model);
			    model = new PredictionModel();
			    model.setArr(extrover_3);
			    list.add(model);
			    model = new PredictionModel();
			    model.setArr(agreeble_4);
			    list.add(model);
			    model = new PredictionModel();
			    model.setArr(neurot_5);
			    list.add(model);
	    
			//Euclidean Distance
									
			ArrayList<Double> cluster=new ArrayList<Double>(); // store predicted clusters
			int ED;
			for(PredictionModel mod:list) {
				int arr[]=mod.getArr();
				
				ArrayList<Integer> edsave= new ArrayList<Integer>();
				
				for(int j=0;j<postVectors.length;j++) {
					ED=postVectors[j]-arr[j];
					ED=Math.abs(ED*ED);
					edsave.add(ED); 
				}
				
				int sum=0;
				for(Integer es:edsave) {
					sum=sum+(int)es;
				}
				
				double square=Math.sqrt(sum);
				cluster.add(square);
			}
			double predict=(double)cluster.get(0);
			
			for(Double ls:cluster) {
				if(predict>ls) {
					predict=ls;
				}
			}
			if(predict==cluster.get(2)) {
				return 3;
			}else {
				double index=cluster.indexOf(predict);
				return (int)index+1; 
			}
			}catch(Exception e) {
				System.out.println("error :"+e);
				return 0;
			}
		}
		
		
			
	
	// prediction person behavior using  post
	public int predictPersonBehavior(String post) {
		try {
			// remove symbols and white spaces
			String removedSymbolsPost=this.removeSpecialSymbols(post);
			
			// convert text lowerCase
			String lowerCaseText=removedSymbolsPost.toLowerCase();
			
			// document to token
			String[] postToken=this.textTokenization(lowerCaseText);
			
			// remove stop words (means not meaningful for prediction)
			String[] afterStopWord=this.removeStopWords(postToken);
									
			// BOW (Bag Of Words) convert into vectors
			int postVectors[]=this.convertBOWVectors(afterStopWord);
			
			
			// k means clustering
			int cluster=this.kMeansClustering(postVectors, afterStopWord);
			
			return cluster;
			
		}catch(Exception e) {
			System.out.println("Prediction error: "+e);
			return -1;
		}
	}
	
	
	
	
	
	/* get All posts comments and like of posts particular user */
	public String[] getAllPostsCommentsLikes(int predictUserID) {
		List<TextDocumentDataModel> list = new LinkedList<TextDocumentDataModel>(); // store particular user posts
		try {
			// fetch all posts
			ps = con.prepareStatement("select pm.post from postmaster pm "
					+ "inner join postregistrationjoin prj on prj.postid=pm.postid "
					+ "inner join registrationmaster rm on rm.registerid=prj.registerid " 
					+ "where rm.registerid=?");
			ps.setInt(1, predictUserID);
			rs = ps.executeQuery();
			while (rs.next()) {
				TextDocumentDataModel info = new TextDocumentDataModel();
				info.setDocumentInformation(rs.getString(1)); // set posts
				list.add(info);
			}

			// fetch all comments user
			ps = con.prepareStatement("select comment from commentmaster where registerid=?");
			ps.setInt(1, predictUserID);
			rs = ps.executeQuery();
			while (rs.next()) {
				TextDocumentDataModel info = new TextDocumentDataModel();
				info.setDocumentInformation(rs.getString(1)); // set comments
				list.add(info);
			}
			
			// fetch liked posts (whenever user like posts all)
			ps = con.prepareStatement("select pm.post from likemaster lm "
			                + "inner join likepostjoin lpj on lpj.likeid=lm.likeid "
							+ "inner join postmaster pm on pm.postid=lpj.postid "
			                + "where lm.registerid=?");
			
			ps.setInt(1, predictUserID);
			rs = ps.executeQuery();
			while (rs.next()) {
				TextDocumentDataModel info = new TextDocumentDataModel();
				info.setDocumentInformation(rs.getString(1)); // set liked posts
				list.add(info);
			}
			
			String arr[]=new String[list.size()];
			int count=0;
			for(TextDocumentDataModel mod:list) {
				arr[count]=mod.getDocumentInformation();
				count++;
			}
			
			return (arr.length>0)?arr:null;
			
		} catch (Exception e) {
			System.out.println("error :" + e);
			return null;
		}
	}
	
	
	// TF-IDF (Term Frequency - Inverse Document Frequency) Algorithm convert document to vectors
	public List<PredictionModel> convertDocumentToVectorsIFIDF(String[][] str) {
		try {
			List<PredictionModel> list=new ArrayList<PredictionModel>();
		
			int flag=0;
			for(int i=0;i<str.length;i++) {
				
				double arr[]=new double[str[i].length]; // storing sentence vectors
				double count=0;
				for(int j=0;j<str[i].length;j++) { 
					
					//check before word
					for(int m=0;m<j;m++) {
						if(str[i][m].compareTo(str[i][j])==0) {
							flag=1;
							break;
						}
					}
					if(flag!=1) {
						for(int k=j;k<str[i].length;k++) {
							if(str[i][j].compareTo(str[i][k])==0) {
								count++;
							}	
						}
					}else {
						flag=0;
					}
					
					// F/T * log(TD/FD)
					// find term occurrence in all document (FD)
					int FD=0;
					for(int a=0;a<str.length;a++) {
						for(int b=0;b<str[a].length;b++) {
							if(str[i][j].compareTo(str[a][b])==0) {
								FD++;
								break;
							}
						}
					}
	
					double word=(count/str[i].length)*(Math.log(str.length/FD));
					arr[j]=word;
					count=0;
					
				}
				
				// store array in ArrayList
				PredictionModel model = new PredictionModel();
				model.setArrayVectors(arr);
				list.add(model);
				
			}
			
			return list.size()>0 ? list : null;
		}catch(Exception e) {
			System.out.println("error :"+e);
			return null;
		}
	}
	

	// k means clustering
			public List<PredictionModel> kMeansClustering(List<PredictionModel> TFIDFVectors, String[][] afterStopWord) {

				try {

					List<PredictionModel> predict = new ArrayList<PredictionModel>(); // store string and cluster

					int c=0;
					for (PredictionModel TFVect : TFIDFVectors) {
						double TFarr[] = TFVect.getArrayVectors();

							List<PredictionModel> list = new ArrayList<PredictionModel>(); // store personality vectors

							int[] openess_1 = this.personalityConvertsVector(afterStopWord[c], openessToExperience1);
							int[] conscient_2 = this.personalityConvertsVector(afterStopWord[c], conscientiousness2);
							int[] extrover_3 = this.personalityConvertsVector(afterStopWord[c], extroversion3);
							int[] agreeble_4 = this.personalityConvertsVector(afterStopWord[c], agreeableness4);
							int[] neurot_5 = this.personalityConvertsVector(afterStopWord[c], neuroticism5);
							
							PredictionModel model = new PredictionModel();
							model.setArr(openess_1);
							list.add(model);
							model = new PredictionModel();
							model.setArr(conscient_2);
							list.add(model);
							model = new PredictionModel();
							model.setArr(extrover_3);
							list.add(model);
							model = new PredictionModel();
							model.setArr(agreeble_4);
							list.add(model);
							model = new PredictionModel();
							model.setArr(neurot_5);
							list.add(model);

							// Euclidean Distance
							ArrayList<Double> edsave;
							ArrayList<Double> clust = new ArrayList<Double>(); // store sentence value clusters
							for (PredictionModel personVect : list) {
								edsave = new ArrayList<Double>();
								double ED;
								int perArr[] = personVect.getArr();

								for (int j = 0; j < perArr.length; j++) {
									ED = TFarr[j] - (double) perArr[j];
									ED = Math.abs(ED * ED);
									edsave.add(ED);
								}					

								double sum = 0;
								for (Double es : edsave) {
									sum = sum + (double) es;
								}

								double square = Math.sqrt(sum);
								clust.add(square);
							}

							double min = (double) clust.get(0);
							for (Double preM : clust) {
								
								double val = (double) preM;
								if (min > val) {
									min = val;
								}
							}

							if (min == clust.get(2)) {
								PredictionModel pm = new PredictionModel();
								pm.setCluster(3);
								pm.setDocName(afterStopWord[c]);
								predict.add(pm);
							} else {
								PredictionModel pm = new PredictionModel();
								int ind = clust.indexOf(min);
								pm.setCluster(ind + 1);
								pm.setDocName(afterStopWord[c]);
								predict.add(pm);
							}

							c++;
					}
					
					return predict.size() > 0 ? predict : null;

				} catch (Exception e) {
					System.out.println("error :" + e);
					return null;
				}
			}
	
	
	// prediction person behavior overall
	// posts, comments, like of posts
 	public List<PredictionModel> predictOverAllPersonBehavior(String[] unlabelledInformation){
 		try {
 			
 			// remove symbols and white spaces
 			String[] removedSymbolsInfo=new String[unlabelledInformation.length];
 			for(int i=0;i<unlabelledInformation.length;i++) {
 				removedSymbolsInfo[i]=this.removeSpecialSymbols(unlabelledInformation[i]);
 			}
 			
			
			// convert unlabeled text to lowerCase
 			String[] lowerCaseInfo = new String[removedSymbolsInfo.length];
 			for(int i=0;i<unlabelledInformation.length;i++) {
 				lowerCaseInfo[i]=removedSymbolsInfo[i].toLowerCase();
 			}
 			
			// document to token
			String[][] textToken= new String[lowerCaseInfo.length][];
			for(int i=0;i<textToken.length;i++) {
				textToken[i]=this.textTokenization(lowerCaseInfo[i]);
			}
			
			// remove stop words (means not meaningful for prediction)
			String afterStopWord[][]=new String[textToken.length][];
			for(int i=0;i<textToken.length;i++) {
				afterStopWord[i]=this.removeStopWords(textToken[i]);
			}				
			
			// TF- IDF (Term Frequency - inverse document frequency)
		   List<PredictionModel> TFIDFVectors=this.convertDocumentToVectorsIFIDF(afterStopWord);
		   
		   //// k means clustering
		   List<PredictionModel> clusters=this.kMeansClustering(TFIDFVectors,afterStopWord);
		   
			return clusters.size()>0 ?clusters :null;
 		}catch(Exception e) {
 			System.out.println("error :"+e);
 			return null;
 		}
 		
 	}

}
