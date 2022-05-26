package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BrickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image image;
	private int paddleX;
	private Paddle paddle = new Paddle();
	private Ball ball = new Ball();
	private List<Brick> bricks = new ArrayList<Brick>();

	public BrickPanel() {
		setVisible(true);

		brickSetup();

		Thread animationThread = new Thread(new Runnable() {
			public void run() {
				// delta time game loop aus dem internet
				double drawInterval = 1000000000 / FPS; // 1 sec (in nanoseconds) / FPS
				double delta = 0;
				long lastTime = System.nanoTime();
				long currentTime;

				while (true) {
					currentTime = System.nanoTime();

					delta += (currentTime - lastTime) / drawInterval;
					lastTime = currentTime;

					if (delta >= 1) {
						PointerInfo a = MouseInfo.getPointerInfo();
						Point b = a.getLocation();
						paddleX = (int) b.getX();
						paddle.setPaddleX(paddleX);
						ball.setPaddleX(paddleX);
						
						repaint();

						delta--;
					}
				}
			}
		});
		animationThread.start();
	}

	public void update(Graphics graphics) {
		paint(graphics);
	}

	public void paint(Graphics g) {
		Dimension dim = getSize();
		checkImage();
		Graphics graphics = image.getGraphics();
		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, dim.width, dim.height);
		paddle.paintPaddle(image.getGraphics());
		ball.paintBall(image.getGraphics());
		paintBricks(image.getGraphics());
		g.drawImage(image, 0, 0, null);
	}

	private void paintBricks(Graphics g) {
		for (Brick brick : bricks) {
			brick.paint(g);
		}
	}

	private void brickSetup() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int rows = (dim.height / 3) / (BRICK_HEIGHT + BRICK_GAP);
		int cols = dim.width / (BRICK_WIDTH + BRICK_GAP);
		int brickCount = (rows * cols);
		System.out.println(rows + " " + cols + " " + brickCount);
		int row = 10;
		// für alle bricks
		for (int i = 0; i < brickCount; i++) {
			// Zeilenumbruch wenn eine Zeile voll ist
			if ((i % cols) == 0) {
				row += 40;
			}
			// Brick erstellen
			bricks.add(new Brick(10 + ((BRICK_WIDTH + BRICK_GAP) * (i % cols)), row));
		}
		
		// Bricks dem Ball übergeben
		ball.setBricks(bricks);

	}

	private void checkImage() {
		Dimension dim = getSize();
		if (image == null || image.getWidth(null) != dim.width || image.getHeight(null) != dim.height) {
			image = createImage(dim.width, dim.height);
		}
	}
}
