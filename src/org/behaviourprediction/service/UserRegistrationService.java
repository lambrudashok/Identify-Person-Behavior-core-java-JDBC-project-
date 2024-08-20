package org.behaviourprediction.service;

import java.util.List;

import org.behaviourprediction.model.AccountInformationModel;
import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.model.RegistrationModel;
import org.behaviourprediction.repository.UserRegistrationRepository;

public class UserRegistrationService {
	
	UserRegistrationRepository userRegisterRepo = new UserRegistrationRepository();
	
	/*new user insert data in registration master*/
	public boolean isAddNewUserRegistration(RegistrationModel register) {
		return userRegisterRepo.isAddNewUserRegistration(register);
	}
	
	/*check user login in database */
	public int checkUserLogin(LoginModel login) {
		return userRegisterRepo.checkUserLogin(login); 
	}
	
	/*search email*/
	public int searchEmail(String email) {
		if(email.isEmpty()) {
			return -1;
		}else {
			return userRegisterRepo.searchEmail(email);
		}
		
	}
	
	/*search username*/
	public int searchUsername(String username) {
		if(username.isEmpty()) {
			return -1;
		}else {
			return userRegisterRepo.searchUsername(username);
		}
		
	}
	
	/*forgot password*/
	public int forgotPasswordUser(LoginModel login) {
		return userRegisterRepo.forgotPasswordUser(login);
	}
	
	/*update new name*/
	public int updateName(RegistrationModel model) {
		if(model.getCustomername().isEmpty()){
			return 0;
		}else {
			return userRegisterRepo.updateName(model);
		}
		
	}
	
	/*update new username*/
	public int updateUsername(RegistrationModel model) {
		return userRegisterRepo.updateUsername(model);
	}
	
	/*update new email*/
	public int updateEmail(RegistrationModel model) {
		return userRegisterRepo.updateEmail(model);
	}
	
	/*account information show*/
	public List<AccountInformationModel> accountInformation(int registerid) {
		return userRegisterRepo.accountInformation(registerid);
	}
	
	/*check request account delete*/
	public int checkRequestDelete(int registerId) {
		return userRegisterRepo.checkRequestDelete(registerId);
	}
	
	/*recover delete requested account*/
	public int recoverAccount(int register) {
		return userRegisterRepo.recoverAccount(register);
	}
	
	/*delete account user*/
	public int deleteUserAccount(int registerId) {
		return userRegisterRepo.deleteUserAccount(registerId);
	}
	
	/*show user according to its starting usernames */
	public List<String> showSearchUserNames(String username) {
		return userRegisterRepo.showSearchUserNames(username);
	}
}
