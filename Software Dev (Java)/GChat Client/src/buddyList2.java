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
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTree;

public class buddyList2 {

	static JFrame frame;
	//private Collection<RosterEntry> buddies;
	protected static XMPPConnection connection; 
	private static String user; 
	private static Vector listData;
	private static Roster roster; 
	private static Collection<RosterEntry> buddies; 
	private final Action action = new SwingAction();
	private static JScrollPane scrollPane;
	private static JTree list; 
	private static DefaultTreeModel model; 
	private static DefaultMutableTreeNode[] nodes; 
	private static DefaultMutableTreeNode root; 
	/**
	 * Create the application.
	 */
	public buddyList2(XMPPConnection connection, String user) {
		//Pass in connection and username
		this.connection = connection; 
		this.user = user.substring(0, user.length() - 10); 
		//Sleep to make statuses work
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Error");
		}

		//Get the buddylist
		roster = connection.getRoster();
		buddies = roster.getEntries();

		//Make roster listener
		roster.addRosterListener(new RosterListener() {
			// Ignored events public void entriesAdded(Collection<String> addresses) {}
			public void entriesDeleted(Collection<String> addresses) {updateBuddyList();}
			public void entriesUpdated(Collection<String> addresses) {updateBuddyList();}
			public void entriesAdded(Collection<String> addresses) {updateBuddyList();}
			public void presenceChanged(Presence presence) {
				updateBuddyList(); 
			}
		});

		root = new DefaultMutableTreeNode("Buddies");  
		// implement arrayList with groups 
		// ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>(); 
		nodes = new DefaultMutableTreeNode[2]; 
		nodes[0] = new DefaultMutableTreeNode("Online"); 
		nodes[1] = new DefaultMutableTreeNode("Offline"); 
		root.insert(nodes[0], 0); 
		root.insert(nodes[1], 1); 
	
		//Make a vector of available buddies
		for (RosterEntry entry : buddies) {
			DefaultMutableTreeNode entry2 = new DefaultMutableTreeNode(entry); 
			if (!roster.getPresence(entry.getUser()).toString().equals("unavailable")) {
					nodes[0].add(entry2);
			}
			else {
				nodes[1].add(entry2);
			}
		}		
		
		model = new DefaultTreeModel(root);  
		
		//Set up window
		initialize();
	}

	public static void updateBuddyList() {
		roster = connection.getRoster();
		buddies = roster.getEntries();
		nodes = new DefaultMutableTreeNode[2]; 
		for (RosterEntry entry : buddies) {
			DefaultMutableTreeNode entry2 = new DefaultMutableTreeNode(entry); 
			if (!roster.getPresence(entry.getUser()).toString().equals("unavailable")) {
				nodes[0].add(entry2);
			}
			else {
				nodes[1].add(entry2);
			}
		}		
		
		list = new JTree(model);
		scrollPane.setViewportView(list);
		frame.repaint(); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 249, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 470, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 239, SpringLayout.WEST, frame.getContentPane());

		frame.getContentPane().add(scrollPane);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setAction(action);
		btnLogout.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		springLayout.putConstraint(SpringLayout.NORTH, btnLogout, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLogout, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnLogout);

		JLabel lblUsername = new JLabel(user);
		springLayout.putConstraint(SpringLayout.SOUTH, lblUsername, -6, SpringLayout.NORTH, scrollPane);

		list = new JTree(model);
		scrollPane.setViewportView(list);
		list.addMouseListener(new ListClickAction2(list, connection)); 
		lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblUsername);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Logout");
			putValue(SHORT_DESCRIPTION, "Some short description");
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
}

class ListClickAction2 extends MouseAdapter {
		protected JTree list; 
		protected XMPPConnection connection; 
		public ListClickAction2(JTree list, XMPPConnection connection){
			this.list = list; 
			this.connection = connection; 
		}
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				Object ent = list.getLastSelectedPathComponent();
				final RosterEntry chatBuddy = (RosterEntry)ent; 
				final ChatManager chatManager = connection.getChatManager();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						chatWindow window = new chatWindow(chatBuddy, chatManager);
						window.frame.setVisible(true);
					}
				});
			}
		}
}

