package com.matt.system.taxation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Auth extends JPanel{

	private JPanel pnAuth;
	private JPanel pnLoginDetail;
	private JPanel pnLog;
	private JLabel lblUsername;
	private JTextField txtUsername;
	private JPanel pnLogin;
	private JLabel lblPass;
	private JTextField txtPass;
	private JLabel lbSpace;
	private Container lbErrorUser;
	private JLabel lbErrorPass;
	private JButton btnLogin;
	private JPanel pnBtnLog;
	private boolean isFound;
	
	/**
	 * Create the panel.
	 */
	public Auth() {
		setBounds(0, 0, 1364, 705);
		setBackground(Color.decode("#323232"));
		pnAuth = new JPanel();
		pnAuth.setBackground(Color.decode("#323232"));
		pnAuth.setBounds(0, 0, 1376, 705);
		add(pnAuth);
		pnAuth.setLayout(new BorderLayout(0, 0));
		
		// 1st column
		lbSpace = new JLabel("Login");
		lbSpace.setHorizontalAlignment(SwingConstants.CENTER);
		lbSpace.setForeground(Color.WHITE);
		lbSpace.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnAuth.add(lbSpace, BorderLayout.NORTH);
		
		// 2nd column
		pnLog = new JPanel();
		pnLog.setBackground(Color.decode("#323232"));
		pnAuth.add(pnLog);
		pnLog.setLayout(new GridLayout(3, 1, 0, 0));
		
		// 1st row
		JPanel jpSpace = new JPanel();
		jpSpace.setBackground(Color.decode("#323232"));
		pnLog.add(jpSpace);
		// 2nd row
		pnLogin = new JPanel();
		pnLogin.setBackground(Color.decode("#323232"));
		pnLog.add(pnLogin);
		
		// add component to 2nd column
		pnLoginDetail = new JPanel();
		pnLogin.add(pnLoginDetail);
		pnLoginDetail.setLayout(new GridLayout(0,2,10,10));
		pnLoginDetail.setBackground(Color.decode("#323232"));
		
		// username
		lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pnLoginDetail.add(lblUsername);
		
		txtUsername = new JTextField();
		textField(txtUsername);
		pnLoginDetail.add(txtUsername);
		
		// error username
		lbSpace = new JLabel();
		pnLoginDetail.add(lbSpace);
		lbErrorUser = new JLabel("Invalid username");
		lbErrorUser.setForeground(Color.decode("#e74c3c"));
		lbErrorUser.setFont(new Font("Tahoma",Font.PLAIN,14));
		lbErrorUser.setVisible(false);
		pnLoginDetail.add(lbErrorUser);
		
		// password
		lblPass = new JLabel("Password");
		lblPass.setForeground(Color.WHITE);
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pnLoginDetail.add(lblPass);
		
		txtPass = new JTextField();
		textField(txtPass);
		pnLoginDetail.add(txtPass);
		
		// error password
		lbSpace = new JLabel();
		pnLoginDetail.add(lbSpace);
		lbErrorPass = new JLabel("Invalid password");
		lbErrorPass.setForeground(Color.decode("#e74c3c"));
		lbErrorPass.setFont(new Font("Tahoma",Font.PLAIN,14));
		lbErrorPass.setVisible(false);
		pnLoginDetail.add(lbErrorPass);
		
		// login button
		pnBtnLog = new JPanel();
		pnBtnLog.setLayout(new FlowLayout());
		pnBtnLog.setBackground(Color.decode("#323232"));
		pnLog.add(pnBtnLog);
		
		btnLogin = new JButton("Login");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				auth();
			}
		});
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				auth();
			}
		});
		pnBtnLog.add(btnLogin);
	}
	
	// text field
	public void textField(JTextField tf) {
		tf.setCaretColor(Color.decode("#1abc9c"));
		tf.putClientProperty("caretWidth", 3);
		tf.setForeground(Color.WHITE);
		tf.setBackground(Color.decode("#323232"));
		tf.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf.setColumns(10);
	}
	
	// login logic
	public boolean login(String user, String pass) {
		if(user.equals("admin") && pass.equals("123")) return true;
		return false;
	}
	// auth
	public void auth() {
		if(txtUsername.getText() !="" || txtPass.getText() !="") {
			isFound = login(txtUsername.getText(),txtPass.getText());
			if(isFound == false) {
				lbErrorUser.setVisible(true);
				lbErrorPass.setVisible(true);
				JOptionPane.showMessageDialog(null, "Wrong!", "Failed",JOptionPane.ERROR_MESSAGE);
			}else {
				setVisible(false);
				txtPass.setFocusable(true);
			}
		}
	}
}
