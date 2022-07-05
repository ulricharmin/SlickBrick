package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.w3c.dom.ls.LSException;

import java.awt.event.KeyEvent;

import java.awt.Color;
import java.awt.event.KeyAdapter;

public class BrickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image image;
	private int paddleX;
	private Paddle paddle = new Paddle();
	private Ball ball = new Ball();
	private List<Brick> bricks = new ArrayList<Brick>();
	private int brickCount = 0;
	private enum GameState {
		RUNNING,
		PAUSE,
		GAMEOVER,
		START
	}
	
	private GameState state = GameState.START;

	public enum Direction {
		LEFT,
		RIGHT
	};
	
	private float prev_ballVelX = 9, prev_ballVelY = 9;
	
	public BrickPanel() {
		this.setFocusable(true);
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if (keyCode == KeyEvent.VK_ESCAPE) {
					prev_ballVelX = ball.getVelX();
					prev_ballVelY = ball.getVelY();
					state = GameState.PAUSE;
				} else if (keyCode == KeyEvent.VK_ENTER) {
					if (state != GameState.RUNNING && state != GameState.GAMEOVER) {
						state = GameState.RUNNING;
						ball.setBallVelX(prev_ballVelX * getRandom());
						ball.setBallVelY(prev_ballVelY);
					} else if (state == GameState.GAMEOVER) {
						ball.setGameOver(false);
						brickSetup();
						ball.setBrickCount(brickCount);
						ball.setBallX(940);
						ball.setBallY(540);
						state = GameState.START;
					}
				}
			}
		});

		setVisible(true);

		brickSetup();
		ball.setBrickCount(brickCount);
		
		paddleSetup();
		
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
						paddleX = (int) getMouseX().getX();
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
		
		if (ball.getGameOver()) {
			state = GameState.GAMEOVER;
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("Game Over", dim.width/2-150, dim.height/2-100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press ENTER to restart", dim.width/2-180, dim.height/2-50);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("Your score: " + (brickCount-ball.getBrickCount()) + "/" + brickCount, dim.width/2-100, dim.height/2);
			ball.setBallVelX(0);
			ball.setBallVelY(0);
		} else if (state == GameState.PAUSE) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("PAUSED", dim.width/2-100, dim.height/2-100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press ENTER to continue", dim.width/2-180, dim.height/2-50);
			ball.setBallVelX(0);
			ball.setBallVelY(0);
		} else if (ball.getBrickCount() == 0) {
			state = GameState.GAMEOVER;
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("You Win!", dim.width/2-100, dim.height/2-100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press ENTER to restart", dim.width/2-160, dim.height/2-50);
			ball.setBallVelX(0);
			ball.setBallVelY(0);
		} else if (state == GameState.START) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("SlickBrick v1.0", dim.width/2-165, dim.height/2-100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press ENTER to start", dim.width/2-140, dim.height/2-50);
			ball.setBallVelX(0);
			ball.setBallVelY(0);
		}
	}

	private void paintBricks(Graphics g) {
		for (Brick brick : bricks) {
			brick.paint(g);
		}
	}

	private void brickSetup() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int rows = (dim.height / 15) / (BRICK_HEIGHT + BRICK_GAP);
		int cols = dim.width / (BRICK_WIDTH + BRICK_GAP);
		brickCount = (rows * cols);
		System.out.println(rows + " " + cols + " " + brickCount);
		int row = 10;
		
		// für alle bricks
		for (int i = 0; i < brickCount; i++) {
			// Zeilenumbruch wenn eine Zeile voll ist
			if ((i % cols) == 0) {
				row += 40;
			}
			// Brick erstellen
			bricks.add(new Brick(33 + ((BRICK_WIDTH + BRICK_GAP) * (i % cols)), row));
		}
		
		// Bricks dem Ball übergeben
		ball.setBricks(bricks);
	}
	
	private void paddleSetup() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		paddleX = (int) b.getX();
		paddle.setPaddleX(paddleX);
	}

	private void checkImage() {
		Dimension dim = getSize();
		if (image == null || image.getWidth(null) != dim.width || image.getHeight(null) != dim.height) {
			image = createImage(dim.width, dim.height);
		}
	}
	
	private Point getMouseX() {
		Dimension dim = getSize();
		if (state == GameState.RUNNING) {
			PointerInfo info = MouseInfo.getPointerInfo();
			Point location = info.getLocation();
			return location;
		}
		Point defaultX = new Point();
		defaultX.setLocation(dim.width/2, paddleY);
		return defaultX;
	}
	
	public float getRandom() {
		Random rnd = new Random();
		int random = rnd.nextInt(2 + 1) - 1;
		if (random == 0) {
			return 1;
		}
	    return random;
	}
}



