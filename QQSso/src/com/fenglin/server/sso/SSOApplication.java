package com.fenglin.server.sso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.fenglin.server.sso.service.SsoService;

public class SSOApplication {

	public static void main(String[] args) {
		SsoService service = new SsoService();
		System.out.println("sso单点认证服务器已启动.....");
		try {
			ServerSocket serverSocket = new ServerSocket(80);
			while (true) { 
				Socket socket = serverSocket.accept();
				service.dispathSevlet(socket) ;
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

}
