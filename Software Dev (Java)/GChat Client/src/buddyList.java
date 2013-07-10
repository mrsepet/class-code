import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.filter.ToContainsFilter;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;

public class buddyList {
	static JFrame frame;
	protected static XMPPConnection connection; 
	private static String user; 
	private static String buddyMail;
	private static Vector<String> onlineListData;
	private static Vector<String> offlineListData;
	private static JList onlineList;
	private static JList offlineList;
	private static Roster roster; 
	private static Collection<RosterEntry> buddies; 
	private static JScrollPane onlineListScrollPane;
	private static JScrollPane offlineListScrollPane; 
	private static RosterEntry chatBuddy; 
	private static ArrayList<String> openChats; 
	private JTextField removeFriend;
	/**
	 * Create the application.
	 */
	public buddyList(final XMPPConnection connection, String user) {
		openChats = new ArrayList<String>(); 
		//Pass in connection and username
		this.connection = connection; 
		this.user = user.substring(0, user.length() - 10); 
		//Sleep to make statuses work
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Error");
		}	
		//Packet filter
		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
		PacketCollector collector = connection.createPacketCollector(filter);
		PacketListener myListener = new PacketListener() {
			private Message msg; 
			public void processPacket(Packet packet) {
				if (packet instanceof Message && packet!=null) {
					msg = (Message)packet; 
					if (msg.getBody()!=null&&!buddyList.isChatOpen(msg.getFrom().split("/")[0])){
						final ChatManager chatManager = connection.getChatManager();
						String packetFrom = msg.getFrom().split("/")[0]; 
						for (RosterEntry ent : buddies) {
							if (ent.getUser().equals(packetFrom)&&ent.getUser()!=null) {
								chatBuddy = ent; 
							}
						}
						buddyList.addChatUser(msg.getFrom().split("/")[0]); 
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								chatWindow window = new chatWindow(chatBuddy, connection.getChatManager(), msg.getBody());
								window.frame.setVisible(true);
							}
						});		 

					}
				}
			}
		};
		connection.addPacketListener(myListener, filter);

		//Get the buddylist
		Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.accept_all);
		roster = connection.getRoster();
		buddies = roster.getEntries();

		//Make roster listener
		roster.addRosterListener(new RosterListener() {
			public void entriesDeleted(Collection<String> addresses) {updateBuddyList();}
			public void entriesUpdated(Collection<String> addresses) {updateBuddyList();}
			public void entriesAdded(Collection<String> addresses) {updateBuddyList();}
			public void presenceChanged(Presence presence) {updateBuddyList();}
		});


		//Make a vector of available buddies
		onlineListData = new Vector<String>(); 
		offlineListData = new Vector<String>();
		for (RosterEntry entry : buddies) {
			if (!roster.getPresence(entry.getUser()).toString().equals("unavailable")) {
				if (entry.getName() == null) {
					if (roster.getPresence(entry.getUser()).getStatus() == null ||
							roster.getPresence(entry.getUser()).getStatus() == "") {
						onlineListData.addElement(entry.getUser());
					}
					else {
						onlineListData.addElement(entry.getUser() + " (" + 
								roster.getPresence(entry.getUser()).getStatus() + ")");
					}
				}
				else {
					if (roster.getPresence(entry.getUser()).getStatus() == null ||
							roster.getPresence(entry.getUser()).getStatus() == "") {
						onlineListData.addElement(entry.getName());
					}
					else {
						onlineListData.addElement(entry.getName() + " (" +
								roster.getPresence(entry.getUser()).getStatus() + ")");
					}
				}
			} else {
				if (entry.getName() == null) {
					offlineListData.addElement(entry.getUser());
				} else {
					offlineListData.addElement(entry.getName());
				}
			}
		}			
		//Set up window
		initialize();
	}
	public static String getUserName() {
		return user; 
	}
	public static void removeChatUser(String user) {
		openChats.remove(user); 
	}
	public static void addChatUser(String user) {
		openChats.add(user); 
	}
	public static boolean isChatOpen(String user) {
		return openChats.contains(user); 
	}
	public static void updateBuddyList() {
		roster = connection.getRoster();
		buddies = roster.getEntries();
		onlineListData.clear(); 
		offlineListData.clear(); 
		for (RosterEntry entry : buddies) {
			if (!roster.getPresence(entry.getUser()).toString().equals("unavailable")) {
				if (entry.getName() == null) {
					if (roster.getPresence(entry.getUser()).getStatus() == null ||
							roster.getPresence(entry.getUser()).getStatus() == "") {
						onlineListData.addElement(entry.getUser());
					}
					else {
						onlineListData.addElement(entry.getUser() + " (" + 
								roster.getPresence(entry.getUser()).getStatus() + ")");
					}
				}
				else {
					if (roster.getPresence(entry.getUser()).getStatus() == null ||
							roster.getPresence(entry.getUser()).getStatus() == "") {
						onlineListData.addElement(entry.getName());
					}
					else {
						onlineListData.addElement(entry.getName() + " (" +
								roster.getPresence(entry.getUser()).getStatus() + ")");
					}
				}
			} else {
				if (entry.getName() == null) {
					offlineListData.addElement(entry.getUser());
				} else {
					offlineListData.addElement(entry.getName());
				}
			}
		}	
		onlineList = new JList(onlineListData);
		onlineListScrollPane.setViewportView(onlineList);
		offlineList = new JList(offlineListData);
		offlineListScrollPane.setViewportView(offlineList);
		onlineList.addMouseListener(new OnlineListClickAction3(connection, onlineList, roster)); 
		frame.repaint(); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Declarations
		frame = new JFrame();
		onlineListScrollPane = new JScrollPane();
		JButton btnLogout = new JButton("Logout");
		JLabel lblUsername = new JLabel(user);
		offlineListScrollPane = new JScrollPane();
		JList offlineList = new JList(offlineListData);
		JButton btnNewButton = new JButton("+");
		JButton removeButton = new JButton("-");
		JTextField txtOffline = new JTextField();
		JTextField txtOnline = new JTextField();
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 4, SpringLayout.SOUTH, offlineListScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, removeButton, 6, SpringLayout.EAST, btnNewButton);
		springLayout.putConstraint(SpringLayout.SOUTH, removeButton, 0, SpringLayout.SOUTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 4, SpringLayout.SOUTH, offlineListScrollPane);
		onlineList = new JList(onlineListData);

		//Frame modifications
		frame.setTitle("GChat Client");
		frame.setBounds(100, 100, 242, 547);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(onlineListScrollPane);
		frame.getContentPane().add(btnLogout);
		frame.getContentPane().add(offlineListScrollPane);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(lblUsername);
		frame.getContentPane().add(removeButton);

		//Listeners
		btnNewButton.setAction(new AddFriendAction()); 
		btnLogout.setAction(new LogoutAction());
		btnLogout.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		onlineList.addMouseListener(new OnlineListClickAction3(connection, onlineList, roster));
		removeButton.addActionListener(new removeAction());
		
		//Other mods
		//		offlineList.setEnabled(false);
		onlineList.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		offlineListScrollPane.setViewportView(offlineList);
		offlineListScrollPane.setColumnHeaderView(txtOffline);
		onlineListScrollPane.setColumnHeaderView(txtOnline);
		onlineListScrollPane.setViewportView(onlineList);
		txtOffline.setEditable(false);
		txtOffline.setText("Offline");
		txtOffline.setColumns(10);
		txtOnline.setForeground(Color.BLACK);
		txtOnline.setBackground(Color.WHITE);
		txtOnline.setEditable(false);
		txtOnline.setText("Online");
		txtOnline.setColumns(10);
		lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 13));

		//Spring Layout manager
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, onlineListScrollPane, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, onlineListScrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, onlineListScrollPane, 261, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, onlineListScrollPane, 239, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, offlineListScrollPane, 6, SpringLayout.SOUTH, onlineListScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, offlineListScrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, offlineListScrollPane, 229, SpringLayout.SOUTH, onlineListScrollPane);
		springLayout.putConstraint(SpringLayout.EAST, offlineListScrollPane, -10, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLogout, 0, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblUsername, -6, SpringLayout.NORTH, onlineListScrollPane);
		springLayout.putConstraint(SpringLayout.EAST, lblUsername, 141, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, onlineListScrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -212, SpringLayout.EAST, frame.getContentPane());
		
		removeFriend = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, removeFriend, 1, SpringLayout.SOUTH, offlineListScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, removeFriend, 62, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, removeButton, -6, SpringLayout.WEST, removeFriend);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, 0, SpringLayout.SOUTH, removeFriend);
		springLayout.putConstraint(SpringLayout.EAST, removeFriend, -79, SpringLayout.EAST, onlineListScrollPane);
		removeFriend.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		removeFriend.setText("friend name");
		frame.getContentPane().add(removeFriend);
		removeFriend.setColumns(10);
		
		JButton status = new JButton("Status");
		status.setAction(new statusAction());
		springLayout.putConstraint(SpringLayout.NORTH, status, 6, SpringLayout.SOUTH, offlineListScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, status, -75, SpringLayout.EAST, onlineListScrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, status, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, status, -7, SpringLayout.EAST, onlineListScrollPane);
		status.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		frame.getContentPane().add(status);
	}
	public class LogoutAction extends AbstractAction {
		public LogoutAction() {
			putValue(NAME, "Logout");
			putValue(SHORT_DESCRIPTION, "Logout of GChat");
		}
		public void actionPerformed(ActionEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(frame, 
					"Are you sure you want to logout?", "Logout",
					JOptionPane.YES_NO_OPTION); 
			//Close if user confirmed 
			if (confirmed == JOptionPane.YES_OPTION) 
			{                             
				//Close frame 
				connection.disconnect(); 
				frame.dispose(); 
				System.exit(confirmed); 
			} 
		}
	}
	public class AddFriendAction extends AbstractAction {
		public AddFriendAction() {
			putValue(NAME, "+");
			putValue(SHORT_DESCRIPTION, "Add Buddy");
		}
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					addBuddy window = new addBuddy(roster, connection); 
					window.frame.setVisible(true);
				}
			});
		}
	}
	class removeAction implements ActionListener {
		public void actionPerformed(ActionEvent e2) {
			removeFriend();
			frame.validate();
			frame.repaint();
		}
	}		
	private void removeFriend() {
		buddyMail = removeFriend.getText().trim();
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();

		for (RosterEntry entry : entries) {
			String ftr = entry.toString();
			if ( ftr.contains(":") ) {
				int start = 0;
				for ( int i = 0; i < ftr.length(); i ++ ) {
					if ( ftr.substring(i,i+1).equals(":") ) {
						start = i;
						break;
					}
				}
				ftr = ftr.substring(start+1, ftr.length()).trim();
			}
			String ftrFinal = ftr;
			try {
				if ( ftrFinal.equals(buddyMail) ) {
					int index = onlineListData.indexOf(entry);
					roster.removeEntry(entry);
					onlineListData.removeElement(entry);
					offlineListData.removeElement(entry);
				}
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}
	private class statusAction extends AbstractAction {
		public statusAction() {
			putValue(NAME, "Status");
			putValue(SHORT_DESCRIPTION, "Set new status");
		}
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					changeStatus window = new changeStatus(roster, connection); 
					window.frame.setVisible(true);
				}
			});
		}
	}
}


//class RemoveFriendAction extends AbstractAction {
//	XMPPConnection connection; 
//	JList onlineList;  
//	Roster roster; 
//	JList offlineList; 
//	Collection<RosterEntry> buddies; 
//	RosterEntry chatBuddy; 
//	public RemoveFriendAction(XMPPConnection connection, JList onlineList, JList offlineList, Roster roster){
//		this.connection = connection; 
//		this.onlineList = onlineList; 
//		this.roster = roster; 
//		this.offlineList = offlineList; 
//		this.buddies = roster.getEntries();
//		this.chatBuddy = null; 
//		putValue(NAME, "-");
//		putValue(SHORT_DESCRIPTION, "Remove Selected Buddy");
//	}
//	public void actionPerformed(ActionEvent e) {
//		String entry = "";
//		if(!onlineList.isSelectionEmpty()) entry = onlineList.getSelectedValue().toString(); 
//		else if(!offlineList.isSelectionEmpty()&&onlineList.isSelectionEmpty()) entry = offlineList.getSelectedValue().toString(); 
//		for (RosterEntry ent : buddies) {
//			if (ent.getUser().equals(entry)&&ent.getUser()!=null) {
//				chatBuddy = ent; 
//			}
//			if (ent.getName()!=null&&ent.getName().equals(entry)) {
//				chatBuddy = ent; 
//			}
//		}
//		Presence unsubscribed = new Presence(Presence.Type.unsubscribe); 
//		unsubscribed.setTo(chatBuddy.getUser());
//		connection.sendPacket(unsubscribed);
//		try {
//			roster.removeEntry(chatBuddy);
//		} catch (Exception e1) {
//			System.out.println("Removal error");
//		} 
//		buddyList.updateBuddyList(); 
//	}
//
//}
class OnlineListClickAction3 extends MouseAdapter {
	XMPPConnection connection; 
	JList onlineList;  
	Roster roster; 
	Collection<RosterEntry> buddies; 
	RosterEntry chatBuddy; 
	public OnlineListClickAction3(XMPPConnection connection, JList onlineList, Roster roster){
		this.connection = connection; 
		this.onlineList = onlineList; 
		this.roster = roster; 
		this.buddies = roster.getEntries();
		this.chatBuddy = null; 
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int i = onlineList.locationToIndex(e.getPoint());
			ListModel dlm = onlineList.getModel();
			String entry = (String)dlm.getElementAt(i);
			String[] array = entry.split("\\("); 
			String user = array[0].trim();
			for (RosterEntry ent : buddies) {
				if (ent.getUser().equals(user)&&ent.getUser()!=null) {
					chatBuddy = ent; 
				}
				if (ent.getName()!=null&&ent.getName().equals(user)) {
					chatBuddy = ent; 
				}
			}
			if (!buddyList.isChatOpen(chatBuddy.getUser())) {
				final ChatManager chatManager = connection.getChatManager();
				buddyList.addChatUser(chatBuddy.getUser()); 
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						chatWindow window = new chatWindow(chatBuddy, chatManager);
						window.frame.setVisible(true);
					}
				});		
			}
		}
	}}

