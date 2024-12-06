package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.LoginBean;
import com.example.demo.model.LoginRepository;

@Service
public class LoginService {

	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public LoginBean authenticate(String userEmail, String userPassword) {
		// 日誌輸出
	    System.out.println("Authenticating user: " + userEmail + " with password: " + userPassword);
	 // 查詢資料庫以驗證用戶
        LoginBean user = loginRepository.findByUserEmail(userEmail);// 根據 email 查詢用戶
        if (user != null) {
        	// 使用 passwordEncoder 比對明文密碼和加密密碼
            if(passwordEncoder.matches(userPassword, user.getUserPassword())) {
            	// 如果密碼匹配，輸出用戶信息
            	System.out.println("User found and password matches: " + user.getUserEmail());
            	return user;// 返回用戶資料
            
        } else {
        	// 密碼不匹配
            System.out.println("Password does not match for user: " + userEmail);
        }
        }else {
        	System.out.println("No user found with userEmail: " + userEmail);
        }
	
        return null;
	
	}
}