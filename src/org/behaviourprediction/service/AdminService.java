package org.behaviourprediction.service;

import java.util.List;

import org.behaviourprediction.model.LoginModel;
import org.behaviourprediction.model.RegistrationModel;
import org.behaviourprediction.repository.AdminRepository;

public class AdminService {
	
	AdminRepository adminRepo = new AdminRepository();
	
	/*check admin login*/
	public int checkAdminLogin(LoginModel login) {
		return adminRepo.checkAdminLogin(login);
	}
	
	/*View All Login Users Info*/
	public List<LoginModel> getAllUserLoginInfo(){
		return adminRepo.getAllUserLoginInfo();
	}
	
	/* View Particular User Login Info*/
	public List<LoginModel> getUserLoginInfo(String userUsername){
		return adminRepo.getUserLoginInfo(userUsername);
	}
	
	/*View All User Accounts(register)*/
	public List<RegistrationModel> getAllUserAccountRegisterInfo(){
		return adminRepo.getAllUserAccountRegisterInfo();
	}
	
	/*fetch account requests for delete*/
	public List<LoginModel> getAccountRequestsForDelete(){
		return adminRepo.getAccountRequestsForDelete();
	}
	
	/*delete account user*/
	public int delteUserAccountRequested(int registerDelete) {
		return adminRepo.delteUserAccountRequested(registerDelete);
	}

}
