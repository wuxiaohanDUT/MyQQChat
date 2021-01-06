package com.fenglin.client.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fenglin.commons.entity.User;
import com.fenglin.commons.utils.PropertiesUtils;

public class HomePage extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private  User user;
	private  JPanel userMenuJPanel;
	private  PropertiesUtils cfg ;
	
	private  String imagePath;
	
	public HomePage(User u) {
		super();
		this.user = u;
		this.cfg = PropertiesUtils.createPropertiesUtils("resources/application.properties");
		this.imagePath = this.cfg.getValue("imagePath");
	}

	public void createFrame() {
		
		this.add(createUserMenu());
//		FriendListView fiFriendListView = new FriendListView(this.user);
		
		this.setBounds(533, 155, 900, 750);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
	public JPanel createUserMenu () {
		
		userMenuJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		userMenuJPanel = new JPanel(new GridLayout(5,1,2,2));
		userMenuJPanel.setPreferredSize(new Dimension(100, 750));
		userMenuJPanel.setBorder(BorderFactory.createEtchedBorder());
		
		ImageIcon imageIcon = new ImageIcon(getResource(user.getAvatar()== null ? "defult_avatar.png" : user.getAvatar()));
		JLabel avatarJLabel = new JLabel("",imageIcon, JLabel.CENTER);
		userMenuJPanel.add(avatarJLabel);

		JLabel messageJLabel = new JLabel(new ImageIcon(getResource("message.png")));
		JLabel cloudJLabel = new JLabel(new ImageIcon(getResource("cloud.png")));
		userMenuJPanel.add(messageJLabel);
		userMenuJPanel.add(cloudJLabel);
		
		
		JLabel contactJLabel = new JLabel(new ImageIcon(getResource("contact.png")));
		JLabel setMenuJLabel = new JLabel(new ImageIcon(getResource("menu.png")));
		userMenuJPanel.add(contactJLabel);
		userMenuJPanel.add(setMenuJLabel);
		
		return userMenuJPanel;
	}
	
	
	private URL getResource(String name) {
		return HomePage.class.getClassLoader().getResource(this.imagePath+"/" + name);
	}
}
