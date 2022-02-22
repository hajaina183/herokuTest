package com.springboot.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	public Boolean checkLogin(String username,String password) {
		List<AdminModel> userLogged = adminRepository.getAdminModelLogged(username, password); 
		if(!userLogged.isEmpty() && userLogged.size() == 1) return true;
		//List<User> users = userRepository.find
		return false;
	}
}
