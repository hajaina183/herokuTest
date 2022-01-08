package com.cloudProject.cloud.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cloudProject.cloud.form.AdminLoginForm;
import com.cloudProject.cloud.services.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/home")
	public ModelAndView login(@ModelAttribute(name="adminLoginForm") AdminLoginForm adminLoginForm, Model model,HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
		String username = adminLoginForm.getUsername();
		String password = adminLoginForm.getPassword();	
		String hashedPassword = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8")));
		System.out.println(hashedPassword);
		if(adminService.checkLogin(username, hashedPassword.toLowerCase())) {
			System.out.println("login success");
			request.getSession().setAttribute("SESSION_ADMIN", "milaMilogVaoTafiditra");
			return new ModelAndView("redirect:/indexPage");
		}
		//information false
		model.addAttribute("falseInformation",true);
		return new ModelAndView("adminLogin");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("adminLogin");
	}
	
}
