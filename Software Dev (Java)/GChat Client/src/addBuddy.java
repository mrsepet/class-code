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


public class addBuddy {

	static JFrame frame;
	private JTextField textField;
	private Roster roster; 
	private XMPPConnection connection; 

	/**
	 * Create the application.
	 */
	public addBuddy(Roster roster, XMPPConnection connection) {
		initialize();
		this.roster = roster; 
		this.connection = connection; 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 181, 159);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(22, 46, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Add Buddy");
		btnNewButton.setBounds(32, 86, 117, 29);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setAction(new BuddyAdd()); 

		JLabel lblEnterEmail = new JLabel("Enter Email:");
		lblEnterEmail.setBounds(50, 25, 80, 16);
		frame.getContentPane().add(lblEnterEmail);
	}

	public static void disposeFrame() {
		frame.dispose(); 
	}
	private class BuddyAdd extends AbstractAction {
		private String email = "";
		public BuddyAdd() {
			putValue(NAME, "Add Buddy");
			putValue(SHORT_DESCRIPTION, "Add Buddy");
		}
		public void actionPerformed(ActionEvent e) {
			try {
				email = textField.getText(); 
				Presence subscription = new Presence(Presence.Type.subscribe); 
				subscription.setTo(email);
				subscription.setPriority(24);
				connection.sendPacket(subscription); 
				roster.createEntry(email, null, new String[]{});
				addBuddy.disposeFrame(); 
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			addBuddy.disposeFrame(); 
		}
	}
}