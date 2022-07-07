package de.gds2.bricks;
import java.awt.Color;
import static de.gds2.bricks.Constants.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.awt.*;

public class Ball {
	private float lastBallX = 940;
	private float lastBallY = 540;
	// Random start direction
	private float velX;
	private float velY;
	private float currentBallX, currentBallY;
	private float paddleX;
	private int brickCount;
	private List<Brick> bricks;
	private boolean gameOver = false;
	
	public void paintBall(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval((int)lastBallX, (int)lastBallY, BALL_SIZE, BALL_SIZE);
		
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
		} else if (lastBallX > paddleX - 75 && lastBallX < paddleX + 75 && lastBallY > paddleY - (BALL_SIZE+20)) {
			velY *= -1;
		} else if (lastBallY > paddleY -(BALL_SIZE+6)) {
			velY = -1;
			velX = -1;
			lastBallX = 940;
			lastBallY = 540;
			gameOver = true;
		}
	}
	
	public void bounceBrick() {
		for (Brick brick : bricks) {
			if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.LEFT) {
				brickCount--;
				velX *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.RIGHT) {
				brickCount--;
				velX *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.TOP) {
				brickCount--;
				velY *= -1;
				brick.setDestroyed(true);
			} else if (brick.getCollisionSide(currentBallX, currentBallY, velX, velY) == Sides.BOTTOM) {
				brickCount--;
				velY *= -1;
				brick.setDestroyed(true);
			}
		}
	}
	
	// Getter
	public float getBallX() {
		return lastBallX;
	}
	
	public float getBallY() {
		return lastBallY;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public int getBrickCount() {
		return brickCount;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	// Mutator
	public void setPaddleX(int p_paddleX) {
		this.paddleX = p_paddleX;
	}
	
	public void setBricks(List<Brick> p_bricks) {
		bricks = p_bricks;
	}
	
	public void setBrickCount(int p_brickCount) {
		brickCount = p_brickCount;
	}
	
	public void setBallVelX(float p_velX) {
		velX = p_velX;
	}
	
	public void setBallVelY(float p_velY) {
		velY = p_velY;
	}
	
	public void setBallX(int p_ballX) {
		lastBallX = p_ballX;
	}
	
	public void setBallY(int p_ballY) {
		lastBallY = p_ballY;
	}
	
	public void setGameOver(boolean p_state) {
		gameOver = p_state;
	}
}
