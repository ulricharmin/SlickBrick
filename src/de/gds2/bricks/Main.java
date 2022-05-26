package de.gds2.bricks;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Main extends JPanel {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// JFrame erstellen und konfigurieren
		JFrame frame = new JFrame("Bricks v1.0");
		frame.getContentPane().add(new BrickPanel()).setBackground(Color.DARK_GRAY);
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// Cursor entfernen
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}
}