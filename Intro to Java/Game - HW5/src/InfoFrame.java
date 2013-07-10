import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * InfoFrame.java
 * 
 * This class provides a dialog window for status information about the game to
 * be displayed. It is used by creating an instance of the object and then
 * calling println and print just as you would with System.out.
 * 
 * See SurvivalField.java for examples.
 * 
 * @author Mark Sherriff
 * 
 */
public class InfoFrame {

	private JTextArea textArea = null;
	private JPanel panel;
	private JScrollPane scroll;
	private SurvivalField field;
	private JFrame frame;

	public InfoFrame(SurvivalField field_) {

		field = field_;
		frame = new JFrame();
		frame.setSize(400, 300);
		frame.setLocation(800, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		scroll = new JScrollPane(textArea);
		panel.add(scroll, BorderLayout.CENTER);

		frame.getContentPane().add(panel);

		frame.setVisible(true);

	}

	public void println(String str) {
		textArea.append(str + '\n');
	}

	public void print(String str) {
		textArea.append(str);
	}

}
