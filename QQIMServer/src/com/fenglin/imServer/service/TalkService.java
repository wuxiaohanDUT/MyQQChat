package com.fenglin.imServer.service;


import java.net.Socket;

import com.fenglin.commons.entity.User;
import com.fenglin.tcp.Request;
import com.fenglin.tcp.Response;

import com.fenglin.commons.utils.JacksonUtils;

public class TalkService {
	
	private Socket socket;
	private Response response = null;
	
	

	public TalkService(Socket s) {
		super();
		this.socket = s;
	}



	public Response talkDispose(Request request) {
		
		Response response = null; 
		try {
			
			User user = (User) JacksonUtils.json2pojo(request.getToken(), User.class);
			
			String talk = (String) request.getData();
			
			System.out.println("QQIM:"+talk);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return response;
	}

	 

	
}
