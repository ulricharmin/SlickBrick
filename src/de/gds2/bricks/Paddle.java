package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;
import java.awt.Color;
import java.awt.Graphics;

public class Paddle  {
	private int paddleX;
	
	public void paintPaddle(Graphics g) {
		int sizeX = 150;
		int sizeY = 30;
		g.setColor(Color.GRAY);
		g.fillRect(paddleX - sizeX / 2, paddleY - sizeY / 2, sizeX, sizeY);
	}
	
	public int getPaddleX() {
		return this.paddleX;
	}
	
	public void setPaddleX(int p_paddleX) {
		this.paddleX = p_paddleX;
	}
}
