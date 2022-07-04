package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

import de.gds2.bricks.BrickPanel.Direction;

public class Paddle {
	private int paddleX;
	private Graphics2D g2d;
	
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
