package com.core.security.service;

public interface AuthService {
	String validate(String username,String password);
	String getUserRealName();
}
