package graphic;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author jimmy The swing interface class for a server. It mainly draws four
 *         columns and is uses as a terminal. It can send message back to the
 *         server.
 */
@SuppressWarnings("serial")
public class ServerInterface extends JFrame {

	// The four printers of the four JTextPanels
	private AbstractDocument[] docs = new AbstractDocument[4];
	private final int IP_AREA = 0;
	private final int DETAIL1 = 1;
	private final int DETAIL2 = 2;
	private final int MESSAGE_AREA = 3;

	// The ten styles possible for writing with a printer in a JTextPanel.
	private SimpleAttributeSet[] styles = new SimpleAttributeSet[10];
	public final int STYLE_MESSAGE_SIMPLE = 0;
	public final int STYLE_MESSAGE_CENTRE = 1;
	public final int STYLE_ERREUR_SIMPLE = 2;
	public final int STYLE_ERREUR_CENTRE = 3;
	public final int STYLE_SERVER_SIMPLE = 4;
	public final int STYLE_SERVER_CENTRE = 5;
	public final int STYLE_SUCCESS_SIMPLE = 6;
	public final int STYLE_SUCCESS_CENTRE = 7;
	public final int STYLE_MESS_SPE_SIMPLE = 8;
	public final int STYLE_MESS_SPE_CENTRE = 9;

	// The three other swing components of the interface.
	private JButton sendButton;
	private JTextField message;
	private ServerSendListener listener;

	// The listener uses for the send button.
	private ActionListener action_listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			send_action();
		}

	};

	/**
	 * Method call by the action_listener, it uses a callback to notify the
	 * send.
	 */
	private void send_action() {

		// Get the message from the text view
		String messageText = message.getText();

		// Add message to the message area
		try {
			docs[IP_AREA].insertString(docs[IP_AREA].getLength(), "\n-",
					styles[STYLE_SERVER_CENTRE]);
			docs[DETAIL1].insertString(docs[DETAIL1].getLength(), "\n-",
					styles[STYLE_SERVER_CENTRE]);
			docs[DETAIL2].insertString(docs[DETAIL2].getLength(), "\n-",
					styles[STYLE_SERVER_CENTRE]);
			docs[MESSAGE_AREA].insertString(docs[MESSAGE_AREA].getLength(),
					"\n" + messageText, styles[STYLE_SERVER_SIMPLE]);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// Notify the send
		listener.messageAdd(messageText);

		// Clear text
		message.setText("");

		// We repaint to consider the modification quickly.
		this.repaint();

	}

	/**
	 * Method call to add message log in the graphic interface.
	 * 
	 * @param ip
	 *            The first column information.
	 * @param detail1
	 *            The second column information.
	 * @param detail2
	 *            The third column information.
	 * @param message
	 *            The fourth column information.
	 * @param style
	 *            The style to use for the message.
	 */
	public void add_message(String ip, String detail1, String detail2,
			String message, int style) {

		try {

			// We use style + 1 to make the style from simple to center

			docs[IP_AREA].insertString(docs[IP_AREA].getLength(), "\n" + ip,
					styles[style + 1]);
			docs[DETAIL1].insertString(docs[DETAIL1].getLength(), "\n"
					+ detail1, styles[style + 1]);
			docs[DETAIL2].insertString(docs[DETAIL2].getLength(), "\n"
					+ detail2, styles[style + 1]);
			docs[MESSAGE_AREA].insertString(docs[MESSAGE_AREA].getLength(),
					"\n" + message, styles[style]);

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// We repaint to consider the modification.
		this.repaint();

	}

	/**
	 * Initialize the style. Private method.
	 */
	private void initialize_style() {
		SimpleAttributeSet style_parent = new SimpleAttributeSet();
		StyleConstants.setFontFamily(style_parent, "SansSerif");
		StyleConstants.setFontSize(style_parent, 16);
		StyleConstants.setForeground(style_parent, Color.white);

		SimpleAttributeSet style_parent_centre = new SimpleAttributeSet(
				style_parent);
		StyleConstants.setAlignment(style_parent, StyleConstants.ALIGN_CENTER);

		styles[STYLE_MESSAGE_SIMPLE] = new SimpleAttributeSet(style_parent);
		StyleConstants.setItalic(styles[STYLE_MESSAGE_SIMPLE], true);

		styles[STYLE_MESSAGE_CENTRE] = new SimpleAttributeSet(
				style_parent_centre);
		StyleConstants.setItalic(styles[STYLE_MESSAGE_CENTRE], true);

		styles[STYLE_ERREUR_SIMPLE] = new SimpleAttributeSet(style_parent);
		StyleConstants.setForeground(styles[STYLE_ERREUR_SIMPLE], Color.red);

		styles[STYLE_ERREUR_CENTRE] = new SimpleAttributeSet(
				style_parent_centre);
		StyleConstants.setForeground(styles[STYLE_ERREUR_CENTRE], Color.red);

		styles[STYLE_SERVER_SIMPLE] = new SimpleAttributeSet(style_parent);
		StyleConstants.setForeground(styles[STYLE_SERVER_SIMPLE], Color.blue);

		styles[STYLE_SERVER_CENTRE] = new SimpleAttributeSet(
				style_parent_centre);
		StyleConstants.setForeground(styles[STYLE_SERVER_CENTRE], Color.blue);

		styles[STYLE_SUCCESS_SIMPLE] = new SimpleAttributeSet(style_parent);
		StyleConstants.setForeground(styles[STYLE_SUCCESS_SIMPLE], Color.green);

		styles[STYLE_SUCCESS_CENTRE] = new SimpleAttributeSet(
				style_parent_centre);
		StyleConstants.setForeground(styles[STYLE_SUCCESS_CENTRE], Color.green);

		styles[STYLE_MESS_SPE_SIMPLE] = new SimpleAttributeSet(style_parent);
		StyleConstants.setForeground(styles[STYLE_MESS_SPE_SIMPLE],
				Color.orange);

		styles[STYLE_MESS_SPE_CENTRE] = new SimpleAttributeSet(
				style_parent_centre);
		StyleConstants.setForeground(styles[STYLE_MESS_SPE_CENTRE],
				Color.orange);
	}

	/**
	 * The Constructor of the swing interface.
	 * 
	 * @param listener
	 *            The listener is use to make the controller be in control of
	 *            the message launched by server.
	 */
	public ServerInterface(ServerSendListener listener) {
		super("ServerInterface");

		// Listener
		this.listener = listener;

		// We initialize the styles.
		initialize_style();

		// CREATE THE SWING COMPONENTS.

		// Panel component
		JPanel panel = new JPanel(); // The bottom panel.

		// Panel settings
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);

		// JTextPanes
		JTextPane[] texts_panes = new JTextPane[4];
		int[] width_dimensions = new int[] { 135, 380, 200, 400 };
		for (int i = 0; i < texts_panes.length; i++) {

			// Initialize the texts_panes.
			texts_panes[i] = new JTextPane();

			// Settings the texts_panes.
			texts_panes[i].setCaretPosition(0);
			texts_panes[i].setMargin(new Insets(5, 5, 5, 5));
			texts_panes[i].setEditable(false);
			texts_panes[i].setBackground(Color.DARK_GRAY);

			// We set the minimum width dimension
			texts_panes[i]
					.setMinimumSize(new Dimension(width_dimensions[i], 0));

			// We get the printers.
			docs[i] = (AbstractDocument) texts_panes[i].getDocument();
		}

		// JSplitPanes : we build three, and two go in one to make a Multiple
		// JSplitPane.
		JSplitPane fils_gauche = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				texts_panes[0], texts_panes[1]);
		fils_gauche.setOneTouchExpandable(true);
		JSplitPane fils_droit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				texts_panes[2], texts_panes[3]);
		fils_droit.setOneTouchExpandable(true);
		JSplitPane split_pere = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				fils_gauche, fils_droit);
		split_pere.setOneTouchExpandable(true);

		// We make the Multiple JSplitPane in a scrollpane.
		JScrollPane scrollpane = new JScrollPane(split_pere);

		// ScrollPane settings : scrollbar always visible.
		scrollpane.setMinimumSize(new Dimension(1200, 500));
		scrollpane.setPreferredSize(scrollpane.getMinimumSize());
		scrollpane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// The send JButton and the JTextField for the message to send.
		sendButton = new JButton("Send");
		sendButton.addActionListener(action_listener);
		message = new JTextField();
		message.setPreferredSize(new Dimension(600, 20));
		message.setMaximumSize(message.getPreferredSize());

		// Add the message field and the send button to the bottom panel.
		panel.add(message);
		panel.add(sendButton);

		// We add the two main panels with a BoxLayout on an y axis.
		getContentPane().add(scrollpane);
		getContentPane().add(panel);
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// Setting the Background.
		getContentPane().setBackground(Color.LIGHT_GRAY);

	}

	public interface ServerSendListener {
		public void messageAdd(String message);
	}

}