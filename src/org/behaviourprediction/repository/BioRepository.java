package org.behaviourprediction.repository;

import org.behaviourprediction.config.DBHelper;
import org.behaviourprediction.model.BioModel;

public class BioRepository extends DBHelper{
	
	
	/*generate bio id end user*/
	public int bioIDGenerate() {
		try {
			ps=con.prepareStatement("select max(bioid) from biomaster");
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("bio reop error :"+e);
			return -1;
		}
	}
	
	/*Add Bio*/
	public boolean isaddBio(BioModel bm,int registerid) {
		try {
			ps=con.prepareStatement("insert into biomaster values('0',?)");
			ps.setString(1, bm.getBio());
			int v=ps.executeUpdate();
			
			// get bioid
			int bioid=this.bioIDGenerate();
			ps=con.prepareStatement("insert into bioregistrationjoin values (?,?)");
			ps.setInt(1, bioid);
			ps.setInt(2, registerid);
			v=ps.executeUpdate();
			return (v>0)?true:false;
		}catch(Exception e) {
			System.out.println("bio repo error :"+e);
			return false;
		}
	}
	
	/*search bio in database*/
	public int searchBio(int registerId) {
		try {
			ps=con.prepareStatement("select bm.bioid from biomaster bm "
					+ "inner join bioregistrationjoin brj on brj.bioid=bm.bioid "
					+ "inner join registrationmaster rm on rm.registerid=brj.registerid "
					+ "where brj.registerid=?");
			ps.setInt(1, registerId);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("bio repo error :"+e);
			return -1;
		}
	}
	
	/*update bio*/
	public int updateBio(BioModel model) {
		try {
			ps=con.prepareStatement("update biomaster set bio=? where bioid=?");
			ps.setString(1, model.getBio());
			ps.setInt(2, model.getBioid());
			int v=ps.executeUpdate();
			return v>0 ?1:0;
		}catch(Exception e) {
			System.out.println("bio repo error :"+e);
			return -1;
		}
	}

}
