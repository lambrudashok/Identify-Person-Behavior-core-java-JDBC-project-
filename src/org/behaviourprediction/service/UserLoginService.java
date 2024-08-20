package org.behaviourprediction.service;

import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.repository.UserLoginRepository;

public class UserLoginService {
	
	UserLoginRepository userRepo = new UserLoginRepository();
	
	/*login table insert data*/
	public boolean isAddUserLogin(LoginModel login) {
		return userRepo.isAddUserLogin(login);
	}

}
