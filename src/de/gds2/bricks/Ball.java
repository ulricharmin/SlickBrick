package de.gds2.bricks;
import java.awt.Color;
import static de.gds2.bricks.Constants.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Ball {
	private int lastBallX = 960;
	private int lastBallY = 540;
	private int velX = 8;
	private int velY = 8;
	private int currentBallX, currentBallY;
	private int paddleX;
	private List<Brick> bricks;
	
	public void paintBall(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval(lastBallX, lastBallY, BALL_SIZE, BALL_SIZE);
		
		bounce();
		bounceBrick();
		
		currentBallX = lastBallX;
		currentBallY = lastBallY;
		lastBallX = currentBallX + velX;
		lastBallY = currentBallY + velY;
	}
	
	public void bounce() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if ((lastBallX > (int)screen.getWidth()- BALL_SIZE || lastBallX < 0) && (lastBallY > (int)screen.getHeight()-BALL_SIZE || lastBallY < 0)) {
			velX *= -1;
			velY *= -1;
		} else if (lastBallX > (int)screen.getWidth()- BALL_SIZE || lastBallX < 0) {
			velX *= -1;
		} else if (lastBallY < 0) {
			velY *= -1;
		} else if (lastBallX > paddleX - 75 && lastBallX < paddleX + 75 && lastBallY > paddleY - (BALL_SIZE+10)) {
			velY *= -1;
		} else if (lastBallY > paddleY -(BALL_SIZE+6)) {
			velY = 0;
			velX = 0;
		}
	}
	
	public void bounceBrick() {
		for (Brick brick : bricks) {
			if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.LEFT) {
				velX *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.RIGHT) {
				velX *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.TOP) {
				velY *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.BOTTOM) {
				velY *= -1;
				brick.setDestroyed(true);
			}
		}
	}
	
	// Getter
	public int getBallX() {
		return lastBallX;
	}
	
	public int getBallY() {
		return lastBallY;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public int getVelY() {
		return velY;
	}
	
	// Mutator
	public void setPaddleX(int p_paddleX) {
		this.paddleX = p_paddleX;
	}
	
	public void setBricks(List<Brick> p_bricks) {
		bricks = p_bricks;
	}
}
