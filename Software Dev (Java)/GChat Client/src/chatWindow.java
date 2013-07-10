import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JScrollPane;
public class chatWindow {

	JFrame frame;
	private RosterEntry chatBuddy; 
	private ChatManager chatManager; 
	private JTextField message;
	private String logText = "";
	private Chat chat;
	private JTextArea log; 
	private String name; 
	private File file; 
	private FileWriter fw; 
	private JScrollPane scrollPane;

	/**
	 * Create the application.
	 */

	public chatWindow(RosterEntry chatBuddy, ChatManager chatManager) {
		this.chatBuddy = chatBuddy; 
		this.chatManager = chatManager; 
		if (chatBuddy.getName()!=null) {
			name = chatBuddy.getName(); 
		} else {
			name = chatBuddy.getUser(); 
		}
		file = new File("bin/"+name);
		Scanner keys = null; 
		if (file.exists()) {
			try {
				keys = new Scanner(file);
				while (keys.hasNext()) {
					logText = logText + keys.nextLine() + "\n";
				}
			}
			catch (Exception e) {
				System.out.println("Issue w reading file");
			}
		}
		chat = chatManager.createChat(chatBuddy.getUser(), new MessageListener() {
			public void processMessage(Chat chat, Message message) {
				log.setText(log.getText() + "\n" + name + ": " + message.getBody()); 
				try {
					fw = new FileWriter(file);
					fw.write(log.getText());
					fw.close(); 
				} catch (Exception e) {
					System.out.println("Filewriter error");
				} 
			}
		});
		initialize();
	}

	public chatWindow(RosterEntry chatBuddy, ChatManager chatManager, String msg) {
		this.chatBuddy = chatBuddy; 
		this.chatManager = chatManager; 
		if (chatBuddy.getName()!=null) {
			name = chatBuddy.getName(); 
		} else {
			name = chatBuddy.getUser(); 
		}
		file = new File("bin/"+name);
		Scanner keys = null; 
		if (file.exists()) {
			try {
				keys = new Scanner(file);
				while (keys.hasNext()) {
					logText = logText + keys.nextLine() + "\n";
				}
				logText = logText + name + ": " + msg + "\n";
			}
			catch (Exception e) {
				System.out.println("Issue w reading file");
			}
		}
		chat = chatManager.createChat(chatBuddy.getUser(), new MessageListener() {
			public void processMessage(Chat chat, Message message) {
				log.setText(log.getText() + "\n" + name + ": " + message.getBody()); 
				try {
					fw = new FileWriter(file);
					fw.write(log.getText());
					fw.close(); 
				} catch (Exception e) {
					System.out.println("Filewriter error");
				} 
			}
		});
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(name);
		frame.setBounds(100, 100, 474, 469);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 437, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 464, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		message = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, message, 348, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, message, -10, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, message, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, message, 370, SpringLayout.WEST, panel);
		panel.add(message);
		message.setColumns(10);

		JButton send = new JButton("Send");
		sl_panel.putConstraint(SpringLayout.NORTH, send, 348, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, send, -10, SpringLayout.SOUTH, panel);
		message.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10 && message.getText().trim() != "") {
					sendMessage(message.getText());
					message.setText("");
				}
			}
		});
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage(message.getText());
				message.setText("");
			}
		});
		sl_panel.putConstraint(SpringLayout.WEST, send, 6, SpringLayout.EAST, message);
		sl_panel.putConstraint(SpringLayout.EAST, send, 71, SpringLayout.EAST, message);
		panel.add(send);

		scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, message);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, 444, SpringLayout.WEST, panel);
		panel.add(scrollPane);

		log = new JTextArea();
		log.setText(logText); 
		log.setEditable(false);
		scrollPane.setViewportView(log); 
		frame.addWindowListener(new WindowAdapter(){
			  public void windowClosing(WindowEvent we){
				  buddyList.removeChatUser(chatBuddy.getUser());
			  }
		});
	}
	public void sendMessage(String message) {
		try {
			chat.sendMessage(message);
			log.setText(log.getText() + "\n" + "Me: " + message); 
		} catch (XMPPException e) {
			System.out.println("Error Delivering message");
		}	
		try {
			fw = new FileWriter(file);
			fw.write(log.getText());
			fw.close(); 
		} catch (Exception e) {
			System.out.println("Filewriter error");
		} 
	}
}
