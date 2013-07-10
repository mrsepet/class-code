import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;


public class changeStatus {

	static JFrame frame;
	private JTextField textField;
	private Roster roster; 
	private XMPPConnection connection; 
	private JRadioButton available; 
	private JCheckBox away; 

	/**
	 * Create the application.
	 */
	public changeStatus(Roster roster, XMPPConnection connection) {
		initialize();
		this.roster = roster; 
		this.connection = connection; 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 184, 172);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(22, 38, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton update = new JButton("Update");
		update.addActionListener(new BuddyAdd());
		update.setBounds(32, 97, 117, 29);
		frame.getContentPane().add(update);
		update.setAction(new BuddyAdd()); 

		JLabel lblEnterEmail = new JLabel("Enter new status:");
		lblEnterEmail.setBounds(32, 18, 117, 16);
		frame.getContentPane().add(lblEnterEmail);
		
		away = new JCheckBox("Away");
		away.setBounds(52, 67, 71, 23);
		frame.getContentPane().add(away);
	}

	public static void disposeFrame() {
		frame.dispose(); 
	}
	private class BuddyAdd extends AbstractAction {
		private Presence presence; 
		private String status; 
		private String user; 
		public BuddyAdd() {
			putValue(NAME, "Update");
			putValue(SHORT_DESCRIPTION, "Add Buddy");
			this.user = buddyList.getUserName(); 
		}
		public void actionPerformed(ActionEvent e) {
			try {
				this.status = textField.getText(); 
//				presence = new Presence(Presence.Type.available);
//				presence.setMode(Presence.Mode.available);

				presence = new Presence(Presence.Type.available, null, 1, Presence.Mode.away);
		        presence.setStatus(status);
		        presence.setPriority(24); 
		        presence.setMode(Presence.Mode.available); 
				if (away.isSelected()) {
					presence.setMode(Presence.Mode.away); 
				}
		        connection.sendPacket(presence);

//				presence.setStatus(status);
//				presence.setPriority(24); 
//				connection.sendPacket(presence);
//				buddyList.updateBuddyList(); 
				changeStatus.disposeFrame(); 
			} catch (Exception e1) {
				e1.printStackTrace(); 
			} 
			changeStatus.disposeFrame(); 
		}
	}
}