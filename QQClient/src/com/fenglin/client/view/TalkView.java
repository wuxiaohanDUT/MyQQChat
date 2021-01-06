package com.fenglin.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.fenglin.client.utils.TalkThread;
import com.fenglin.commons.entity.Message;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.utils.JacksonUtils;
import com.fenglin.tcp.Request;
import com.fenglin.tcp.SocketUtils;

/**
 * 聊天框
 */
public class TalkView extends JFrame {
	
	private static final long serialVersionUID = -4013696325793566870L;

	private JPanel  southJPel;
	private JTextArea jTextArea;
	private JScrollPane jScrollPane;
	private JTextField jTextField;
	private JButton sendjButton;

	private User user;

	private User firends;
	
	private Socket socket;

	/**
	 * 使用对话的两个用户对象、和消息中转服务器建立的套接字初始化该对象
	 * @param u
	 * @param firends
	 * @param socket
	 */
	public TalkView(User u, User firends,Socket socket) {
		super();
		this.user = u;
		this.firends = firends;
		this.socket = socket;
	}

	/**
	 * 构造界面，启动对话线程
	 */
	public void createFrame() {
		jTextArea = new JTextArea();
		jTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		jTextArea.setEditable(false);
		jScrollPane = new JScrollPane(jTextArea);
		this.add(jScrollPane, BorderLayout.CENTER);

		southJPel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jTextField = new JTextField(20);
		jTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));

		sendjButton = new JButton("发送");
		sendjButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		sendjButton.setBackground(new Color(113, 191, 234));
		Dimension preferredSize = new Dimension(160, 40); // 设置尺寸
		sendjButton.setPreferredSize(preferredSize); // 设置按钮大小
		sendjButton.setVerticalAlignment(SwingConstants.CENTER);
		
		jButtonListener(sendjButton);
		
		southJPel.add(jTextField);
		southJPel.add(sendjButton);
		this.add(southJPel, BorderLayout.SOUTH);
		//启动对话线程
		TalkThread talkThead = new TalkThread(socket,jTextArea);
		talkThead.start();
		
		this.setTitle(user.getUsername() + "与" + firends.getUsername() + "的聊天窗口");
		this.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		this.setBounds(533, 180, 500, 450);
		this.setVisible(true);
		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		windowsClose( talkThead);
	}
	
	private void jButtonListener(JButton sendjButton){
		sendjButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				sendMSG();
				jTextField.setText(""); 
			}
		});
		
		jTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			//监听鼠标按下
			public void keyPressed(KeyEvent e) {
				 int KeyCode = e.getKeyCode();
				 if(10 == KeyCode) {
					 sendMSG();
					 jTextField.setText(""); 
				 }
			}
		});
	}
	
	private void sendMSG() {
		try {
			String keyTalk = jTextField.getText();
			jTextArea.append(keyTalk+"\n");
			Request request = new Request("post","talk",JacksonUtils.obj2json(user),
					new Message(user.getUsername()+"说:"+keyTalk, firends.getId()),
					"localhost",8888);
			SocketUtils.sendRequest(socket, request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void windowsClose(TalkThread talkThead) {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			    super.windowClosing(e);
				 //当聊天页面被关闭时需要将socket关闭否者会报错
				try {
					System.out.println(user.getUsername() + "与" + firends.getUsername() + "的聊天窗口被关闭了!");
					String token = JacksonUtils.obj2json(user);
					Request requ = new Request("post", "TallkViewClose", token, firends.getId());
					System.out.println("socketClose---requ--->"+requ);
					SocketUtils.sendRequest(socket, requ);
					talkThead.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
