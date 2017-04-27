package com.hh.usersystem;



public interface LoginUserServiceInf {

	String findUserId();
	
	String findOrgId();
	
	String findDeptId();
	
	String findJobId();
	
	String findUserName();
	
	IUser findLoginUser();
}
