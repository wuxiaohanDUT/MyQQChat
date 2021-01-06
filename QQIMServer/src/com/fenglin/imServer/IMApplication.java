package com.fenglin.imServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fenglin.commons.entity.Message;
import com.fenglin.commons.entity.User;
import com.fenglin.imServer.service.TalkThread;

public class IMApplication {

	private static Map<String, User> map = new HashMap<String, User>();
	
	public static Map<String,List<Message>> talkCache = new HashMap<String,List<Message>>();

	public static void main(String[] args) {
		DispathServlet();
	}

	public static void DispathServlet() {
		
		ServerSocket serverSocket;
		try {
			System.out.println("MQ消息处理服务器已启动.....");
			serverSocket = new ServerSocket(9998);  
			while (true) { 
				Socket socket = serverSocket.accept();
				TalkThread talkThread = new TalkThread(socket,talkCache);
				talkThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	

}
