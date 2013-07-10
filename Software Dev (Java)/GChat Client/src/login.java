import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import de.javawi.jstun.attribute.Password;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Scanner;
import java.awt.Canvas;
import java.awt.Button;
import java.awt.Toolkit;

public class login {

	private JFrame frame;
	private JTextField login;
	private final Action action = new SwingAction();
	private JPasswordField pwdPass;
	private JLabel lblDisconnected;
	private JCheckBox remember; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		File file = new File("bin/login");
		Scanner keys = null; 
		String startlogin = "login";
		String startpass = "pass";
		boolean check = false; 
		if (file.exists()) {
			try {
				keys = new Scanner(file);
				startlogin = keys.nextLine(); 
				startpass = keys.nextLine(); 
				check = true; 
			}
			catch (Exception e) {
				System.out.println("Issue w reading file");
			}
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 199, 294);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		login = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, login, 94, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, login, -32, SpringLayout.EAST, frame.getContentPane());
		login.setText(startlogin);
		frame.getContentPane().add(login);
		login.setColumns(10);

		JButton submit = new JButton("Connect");
		springLayout.putConstraint(SpringLayout.WEST, submit, 44, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, submit, -54, SpringLayout.EAST, frame.getContentPane());
		submit.setAction(action);
		frame.getContentPane().add(submit);

		pwdPass = new JPasswordField();
		springLayout.putConstraint(SpringLayout.SOUTH, pwdPass, -116, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, login, -6, SpringLayout.NORTH, pwdPass);
		springLayout.putConstraint(SpringLayout.NORTH, pwdPass, 128, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pwdPass, 0, SpringLayout.WEST, login);
		springLayout.putConstraint(SpringLayout.EAST, pwdPass, 0, SpringLayout.EAST, login);
		pwdPass.setText(startpass);
		frame.getContentPane().add(pwdPass);

		lblDisconnected = new JLabel("Disconnected...");
		springLayout.putConstraint(SpringLayout.SOUTH, submit, -27, SpringLayout.NORTH, lblDisconnected);
		springLayout.putConstraint(SpringLayout.SOUTH, lblDisconnected, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblDisconnected, 0, SpringLayout.EAST, submit);
		frame.getContentPane().add(lblDisconnected);

		remember = new JCheckBox(" Remember Account");
		springLayout.putConstraint(SpringLayout.NORTH, submit, 6, SpringLayout.SOUTH, remember);
		if (check) remember.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, remember, 6, SpringLayout.SOUTH, pwdPass);
		springLayout.putConstraint(SpringLayout.WEST, remember, 0, SpringLayout.WEST, login);
		remember.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		frame.getContentPane().add(remember);
		
		JLabel lblNewLabel = new JLabel("Google Chat Client");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 21, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -34, SpringLayout.NORTH, login);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblCs = new JLabel("CS 2110");
		lblCs.setForeground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, lblCs, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblCs, 65, SpringLayout.WEST, frame.getContentPane());
		lblCs.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		frame.getContentPane().add(lblCs);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Login");
			putValue(SHORT_DESCRIPTION, "Click to log in");
		}
		public void actionPerformed(ActionEvent e) {
			lblDisconnected.setText(" Connecting...");
			frame.repaint(); 
			login(); 
		}
		public void login(){
			final String user = login.getText(); 
			char[] pass2 = pwdPass.getPassword(); 
			String pass3 = new String(pass2);
			//Save login information in file, if remember box is selected
			if (remember.isSelected()) {
				try {					 
					File file = new File("bin/login");
					FileWriter fw = new FileWriter(file);
					fw.write(user + "\n");
					fw.write(pass3);
					fw.close();
				} catch (Exception e) {
					System.out.println("Login save error");;
				} 
			}
			ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
			final XMPPConnection connection = new XMPPConnection(config);
			try {
				connection.connect();
				connection.login(user, pass3);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						buddyList window = new buddyList(connection, user);
						window.frame.setVisible(true);
					}
				});
				frame.dispose(); 
			}
			catch (XMPPException e1) {
				lblDisconnected.setText(" Incorrect Login");
			}

		}
	}
}
