package fenetre;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fenetre(JPanel panneau) {
		// operation de fermeture
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// extraire les dimensions
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		// centrer l'image
		setSize(screenSize.width / 2, screenSize.height / 2);
		setLocation(screenSize.width / 4, screenSize.height / 4);

		// definir l'icone
		setTitle("Algorithmique g�ometrique");

		// ajouter le panneau au cadre
		Container contentPane = getContentPane();
		contentPane.add(panneau);
	}
}
