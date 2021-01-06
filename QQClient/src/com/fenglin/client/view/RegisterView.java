package com.fenglin.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fenglin.client.service.UserService;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.utils.JacksonUtils;
import com.fenglin.commons.utils.ViewMessageBox;
import com.fenglin.tcp.Response;

/**
 * @auther 作者: wangchengkai
 * @Email 邮箱: 1137102347@qq.com
 * @date 创建时间: 2020年7月25日
 * @Description 类说明: 用户注册页面类
 */
public class RegisterView extends JFrame {

	private static final long serialVersionUID = 3024614666505474881L;

	JPanel northJPanel = null;

	JLabel loginBackJLabel = null;

	JPanel logoJPanel = null;
	JLabel logoJLabel = null;

	JLabel usernameJLabel = null;
	JTextField usernameJTextField = null;

	JLabel pwdJLabel = null;
	JPasswordField pwdJPasswordField = null;

	JLabel pwdConfirmJLabel = null;
	JPasswordField pwdConfirmJPasswordField = null;

	JLabel realnameJLabel = null;
	JTextField realnameJJTextField = null;

	JLabel avatarJLabel = null;
	JTextField avatarJTextField = null;

	JPanel southJPanel = null;
	JButton submitJButton = null;
	JButton resetJButton = null;
	JButton loginJButton = null;

	public void createFrame() {

		ImageIcon imageIcon = new ImageIcon(
				LoginView.class.getClassLoader().getResource("resources/static/images/login_back.png"));
		loginBackJLabel = new JLabel("", imageIcon, JLabel.CENTER);

		ImageIcon imageIcon1 = new ImageIcon(
				LoginView.class.getClassLoader().getResource("resources/static/images/qq.png"));
		logoJLabel = new JLabel("", imageIcon1, JLabel.CENTER);
		logoJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		logoJPanel.add(logoJLabel);
		this.add(logoJPanel, BorderLayout.WEST);

		northJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		northJPanel.add(loginBackJLabel);
		this.add(northJPanel, BorderLayout.NORTH);

		northJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		usernameJLabel = new JLabel("用户账号: ");
		usernameJLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		usernameJTextField = new JTextField(20);
		usernameJTextField.setText("QQ号码/手机号/邮箱");
		usernameJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

		pwdJLabel = new JLabel("用户密码: ");
		pwdJLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		pwdJPasswordField = new JPasswordField(20);
		pwdJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

		pwdConfirmJLabel = new JLabel("确认密码: ");
		pwdConfirmJLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		pwdConfirmJPasswordField = new JPasswordField(20);
		pwdConfirmJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

//		realnameJLabel = new JLabel("用户昵称: ");
//		realnameJJTextField = new JTextField(20);
//		realnameJJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20)); 
//
//		avatarJLabel = new JLabel("用户头像: ");
//		avatarJTextField = new JTextField(20);
//		avatarJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20)); 
		
		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		submitJButton = new JButton("注册");
		submitJButton.setBackground(new Color(113,191,234));
		Dimension preferredSize=new Dimension(160, 40);    //设置尺寸
		submitJButton.setPreferredSize(preferredSize);  
		submitJButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		
		resetJButton = new JButton("重置");
		resetJButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		
		loginJButton = new JButton("已有账号!登录");
		loginJButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		
		southJPanel.add(loginJButton);
		southJPanel.add(resetJButton);
		southJPanel.add(submitJButton);

		northJPanel.add(usernameJLabel);
		northJPanel.add(usernameJTextField);
		
		northJPanel.add(pwdJLabel);
		northJPanel.add(pwdJPasswordField);
		
		northJPanel.add(pwdConfirmJLabel);
		northJPanel.add(pwdConfirmJPasswordField);
		
		
		northJPanel.add(southJPanel);

//		northJPanel.add(realnameJLabel);
//		northJPanel.add(avatarJTextField);
//		
//		northJPanel.add(avatarJLabel);
//		northJPanel.add(avatarJTextField);

		this.add(northJPanel, BorderLayout.CENTER);
		
		loginJButtonListener(loginJButton);
		resetJButtonListener(resetJButton);
		registerJButtonListener(submitJButton);
		
		this.setTitle("QQ注册");
		this.setBounds(505, 305, 560, 450);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void resetJButtonListener(JButton resetJButton) {
		resetJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				usernameJTextField.setText("");
				pwdJPasswordField.setText("");
				pwdConfirmJPasswordField.setText("");
			}
		});

	}

	private void registerJButtonListener(JButton submitJButton) {
		submitJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				try {
					String username = usernameJTextField.getText();
					char[] pwdChar = pwdJPasswordField.getPassword();
					String password = new String(pwdChar);
					char[] pwdConfirmChar = pwdConfirmJPasswordField.getPassword();
					String pwdConfirm = new String(pwdConfirmChar);

					System.out.println("username=" + username + ", pwd=" + password + ", pwdConfirm=" + pwdConfirm
							+ ", 页面注册检查:" + fromIsNullCheck(username, password, pwdConfirm));

					if (fromIsNullCheck(username, password, pwdConfirm)) {
						ViewMessageBox.Warning(RegisterView.this, "用户名或密码不能为空!");
						return;
					} else if (!password.equals(pwdConfirm)) {
						ViewMessageBox.Warning(RegisterView.this, "密码确认错误!");
						return;
					}

					UserService service = new UserService();
					Response result = service.dispose(new User(username, password), "localhost", 9999, "register", "post",true);

					System.out.println("result=" + result.toString());

					
					// 登录成功 进入好友列表页面
					if (result.getState() == 200) {
						RegisterView.this.dispose();
						LoginView loginView = new LoginView();
						loginView.createFrame();
					} else {
						ViewMessageBox.Warning(RegisterView.this, result.getMessage());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void  loginJButtonListener(JButton regJButton) {
		regJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				RegisterView.this.dispose();
				LoginView login = new LoginView();
				login.createFrame(); 
			}
		});
	}
	
	private boolean fromIsNullCheck(String username, String password, String pwdConfirm) {
		if (username.isEmpty() && username == null)  return true;
		if (password.isEmpty() && password == null)	 return true;
		if (password.isEmpty() && pwdConfirm == null)return true;
		return false;
	}

}
