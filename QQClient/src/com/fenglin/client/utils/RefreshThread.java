package com.fenglin.client.utils;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import javax.swing.JLabel;

import com.fenglin.client.view.FriendListView;
import com.fenglin.commons.entity.User;
  import com.fenglin.tcp.Response;
import com.fenglin.tcp.SocketUtils;

public class RefreshThread extends Thread {

	private Socket socket ;

	private Map<String, JLabel> friendsJLabelMap;
	
	private FriendListView friendListView;

	private boolean isColse = true;

	/**
	 * 初始化监听线程，复用和sso服务器建立的套接字
	 * 在此线程中建立于好友列表的关系，同时初始化好友条目
	 * @param socket
	 * @param friendListView
	 */
	public RefreshThread(Socket socket, FriendListView friendListView) {
		try {
			this.socket = socket;
			this.friendListView = friendListView;
			this.friendsJLabelMap = friendListView.friendsJLabelMap;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public void close() {
		this.isColse = false;
	}

	/**
	 * 当此线程处于运行的时候，不断轮询是否有来自服务器的消息
	 * 包括好友上线下线发送消息通知
	 */
	@Override
	public void run() {
		while (isColse) {
			try {
				    Response resp = SocketUtils.readeResponse(socket);
				    if("online-infrom".equals(resp.getPath())) {
				    	User user = (User) resp.getData();
						JLabel jLabel = this.friendsJLabelMap.get(user.getUsername());
						if(jLabel != null) {
							jLabel.setForeground(Color.black);
//					  		jLabel.setEnabled(true);
					  		this.friendListView.validate();
						}
				    }else  if("msg-inform".equals(resp.getPath())) {
				    	User user = (User) resp.getData();
						JLabel jLabel = this.friendsJLabelMap.get(user.getUsername());
						if(jLabel != null) {
							jLabel.setForeground(Color.red);
					  		this.friendListView.validate();
						}
				    }else  if("offline".equals(resp.getPath())) {
				    	User user = (User) resp.getData();
						JLabel jLabel = this.friendsJLabelMap.get(user.getUsername());
						if(jLabel != null) {
							jLabel.setForeground(Color.gray);
					  		this.friendListView.validate();
						}
						close() ;
				    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
