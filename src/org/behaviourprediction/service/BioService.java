package org.behaviourprediction.service;

import org.behaviourprediction.model.BioModel;
import org.behaviourprediction.repository.BioRepository;

public class BioService {

	BioRepository brepo = new BioRepository();  // bio repository
	
	/*Add Bio*/
	public boolean isaddBio(BioModel bm,int registerid) {
		if(bm.getBio().isEmpty()) {
			return false;
		}else {
			return brepo.isaddBio(bm, registerid);
		}
		
	}
	
	/*search bio in database*/
	public int searchBio(int registerId) {
			return brepo.searchBio(registerId);
	}
	
	/*update bio*/
	public int updateBio(BioModel model) {
		if(model.getBio().isEmpty()) {
			return -1;
		}else {
			return brepo.updateBio(model);
		}
		
	}
	
}
