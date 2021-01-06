package com.fenglin.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.fenglin.commons.entity.BaseEntity;
import com.fenglin.commons.utils.JacksonUtils;

/**
 * 套接字工具，主要用于处理请求
 */
public class SocketUtils {

	/**
	 * 通过套接字发送请求
	 * @param socket
	 * @param request
	 * @throws IOException
	 */
	public static void  sendRequest(Socket socket, Request request ) throws IOException {
		OutputStream out = socket.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(request);
	}
	
	public static void  farwardRequest(Socket socket, Request request ) throws IOException {
		OutputStream out = socket.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(request);
	}

	/**
	 * 通过套接字发送响应
	 * @param socket
	 * @param response
	 * @throws IOException
	 */
	public static void sendResponse(Socket socket, Response response) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
		objOutStream.writeObject(response);
	}

	/**
	 * 通过套接字读取接收到的消息
	 * @param socket
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Response readeResponse(Socket socket) throws IOException, ClassNotFoundException {
		InputStream input = socket.getInputStream();
		ObjectInputStream objInput = new ObjectInputStream(input);
	    return (Response) objInput.readObject();
	}

	/**
	 * 通过套接字读取接收到的请求
	 * @param socket
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Request readeRequest(Socket socket) throws IOException, ClassNotFoundException {
		InputStream input = socket.getInputStream();
		ObjectInputStream objInput = new ObjectInputStream(input);
	    return (Request) objInput.readObject();
	}

	/**
	 * 构造特定的请求并且发送给指定的ip和端口号
	 * @param entity
	 * @param ip
	 * @param port
	 * @param path
	 * @param method
	 * @param isCloseSocket
	 * @return
	 */
	public Response dispose(BaseEntity entity, String ip, int port, String path, String method, boolean isCloseSocket) {
		 Response result = null;
		 try {
			Socket socket = new Socket(ip,port);
		
			Request request = new Request();
			request.setToken(JacksonUtils.obj2json(entity));
			request.setData(entity);
			request.setPath(path);
			request.setMethod(method);
			sendRequest(socket, request);
			
		    result = (Response) readeResponse(socket);
		    
		    if(isCloseSocket) socket.close();
		} catch (Exception  e) {
			e.printStackTrace();
		} 
		 return result;
	 }
}
