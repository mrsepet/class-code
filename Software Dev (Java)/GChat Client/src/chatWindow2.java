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
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
public class chatWindow2 {

	JFrame frame;
	private RosterEntry chatBuddy; 
	private ChatManager chatManager; 
	private String logText = "";
	private Chat chat;
	private String name; 
	private File file; 
	private FileWriter fw; 
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JTextField message;
	private JTextPane log; 

	/**
	 * Create the application.
	 */
	
	public chatWindow2(final RosterEntry chatBuddy, ChatManager chatManager) {
		
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


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(name);
		frame.setBounds(100, 100, 474, 469);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 437, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 464, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab(name, null, panel, null);
		tabbedPane.addTab(name, null, panel, null);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, 317, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, 423, SpringLayout.WEST, panel);
		panel.add(scrollPane);
		
		message = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, message, 6, SpringLayout.SOUTH, scrollPane);
		sl_panel.putConstraint(SpringLayout.WEST, message, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, message, 54, SpringLayout.SOUTH, scrollPane);
		sl_panel.putConstraint(SpringLayout.EAST, message, 340, SpringLayout.WEST, panel);
		panel.add(message);
		message.setColumns(10);
		
		JButton send = new JButton("Send");
		sl_panel.putConstraint(SpringLayout.NORTH, send, 6, SpringLayout.SOUTH, scrollPane);
		sl_panel.putConstraint(SpringLayout.WEST, send, 6, SpringLayout.EAST, message);
		sl_panel.putConstraint(SpringLayout.SOUTH, send, 0, SpringLayout.SOUTH, message);
		sl_panel.putConstraint(SpringLayout.EAST, send, 0, SpringLayout.EAST, scrollPane);
		
		log = new JTextPane();
		log.setText(logText); 
		scrollPane.setViewportView(log);
		panel.add(send);
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
