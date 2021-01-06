package com.fenglin.client;

import com.fenglin.client.view.HomePage;
import com.fenglin.client.view.LoginView;
import com.fenglin.client.view.RegisterView;
import com.fenglin.commons.entity.User;

public class ClientApplication {

	 public static void main(String[] args) {
		LoginView loginView = new LoginView();
		loginView.createFrame();
//		RegisterView loginView = new RegisterView();
//    	loginView.createFrame();
//		User user = new User(103,"wck","av4.png","106,107,108,109,110,113,114,115,116,117");
//		HomePage home = new HomePage(user);
//		home.createFrame();
	}
}
