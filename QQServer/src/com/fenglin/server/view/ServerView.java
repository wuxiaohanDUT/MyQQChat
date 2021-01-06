package com.fenglin.server.view;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import com.fenglin.server.service.UserService;
import com.fenglin.tcp.Request;
import com.fenglin.tcp.Response;
import com.fenglin.tcp.SocketUtils;

public class ServerView extends JFrame{

	private static final long serialVersionUID = 3483224641419876589L;
	
	private ServerSocket serverSocket;

	public void createFrame()  {

//		 this.setTitle("QQ服务器");
//		 this.setBounds(483,230,450,400);
//		 this.setVisible(true);
//    	 this.setResizable(false);
//		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 try {
			System.out.println("QQ-Server-启动了");
			DispathServlet();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 }

	/**
	 * 负责分派用户的请求
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void DispathServlet() throws IOException, ClassNotFoundException {
		  UserService service = new UserService();
		  serverSocket = new ServerSocket(9999);
		  Response response = null;
		  while(true) {
			  Socket socket = serverSocket.accept();
			  
			  Request request = (Request) SocketUtils.readeRequest(socket);
			  if("register".equals(request.getPath())) response =  service.register(request);
				
			  if("updateUserInfo".equals(request.getPath())) response =  service.updateUserInfo(request); 
				
			  if("getFirends".equals(request.getPath())) response =  service.getFirends(request); 
			  
			  SocketUtils.sendResponse(socket, response);
			  socket.close();
		  }
	 }
	 
	 
}
