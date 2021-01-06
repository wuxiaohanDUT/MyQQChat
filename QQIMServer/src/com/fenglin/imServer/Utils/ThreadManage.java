package com.fenglin.imServer.Utils;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.fenglin.imServer.service.TalkThread;

public class ThreadManage {
  
	public static Map<String, TalkThread> threadMap = new HashMap<String, TalkThread>();
	
	public static void put(String userAndFried,TalkThread thread){
		threadMap.put(userAndFried,thread);
	}
	
	public static Socket getfriendSocket(Object object){
		TalkThread thlkThread = threadMap.get(object);
		if(thlkThread != null) return thlkThread.getSocket();
		else return null;
	}
	
}
