package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;

import java.awt.Color;
import java.awt.Graphics;

public class Brick {

	// Brick corners, starting top left clock wise
	private int ax, ay, bx, by, cx, cy, dx, dy;
	
	// Destroyed state
	private boolean isDestroyed = false;
	
	public Brick(int p_x, int p_y) {
		ax = p_x;
		ay = p_y;
		
		bx = ax+BRICK_WIDTH;
		by = ay;
		
		cx = ax+BRICK_WIDTH;
		cy = ay+BRICK_HEIGHT;
		
		dx = ax;
		dy = ay+BRICK_HEIGHT;
	}
	
	public void paint(Graphics p_g) {
		if (!isDestroyed) {
			p_g.setColor(Color.CYAN);
			p_g.fillRect(ax, ay, BRICK_WIDTH, BRICK_HEIGHT);			
		}
	}
	
	public boolean isIntersecting(int ball_ax, int ball_ay, int ball_bx, int ball_by, int cx, int cy, int dx, int dy) {
		// Vektor AB
		int xab = ball_bx-ball_ax;
		int yab = ball_by-ball_ay;
		
		// Vektor CD
		int xcd = dx-cx;
		int ycd = dy-cy;
		
		float beta = (float)(ball_ay+(cx*yab/xab)-(ball_ax*yab/xab)-cy) / 
				          (float)(ycd-xcd*yab/xab);
		float alpha = (cx+beta*xcd-ball_ax)/xab;
		
		if (beta < 1 && beta > 0 && alpha < 1 && alpha > 0)
			return true;
		
		return false;
	}
	
	public Sides getCollisionSide(int p_ballX, int p_ballY, int p_ballVelX, int p_ballVelY) {
		if (isDestroyed()) {
			return Sides.NONE;
		}
		
		//Ball
		int ball_bx = p_ballX+p_ballVelX;
		int ball_by = p_ballY+p_ballVelY;	
		
		if (p_ballVelX > 0 && p_ballVelY > 0) { // Ball fliegt nach unten rechts
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax, ay, bx, by))
				return Sides.TOP;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax, ay, dx, dy))
				return Sides.LEFT;
		} else if (p_ballVelX < 0 && p_ballVelY > 0) { // Ball fliegt nach unten links
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax, ay, bx, by))
				return Sides.TOP;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, bx, by, cx, cy))
				return Sides.RIGHT;
		} else if (p_ballVelX > 0 && p_ballVelY < 0) { // Ball fliegt nach oben rechts
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, cx, cy, dx, dy))
				return Sides.BOTTOM;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax, ay, dx, dy))
				return Sides.LEFT;
		} else if (p_ballVelX < 0 && p_ballVelY < 0) { // Ball fliegt nach oben links
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, cx, cy, dx, dy))
				return Sides.BOTTOM;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, bx, by, cx, cy))
				return Sides.RIGHT;
		}
		return Sides.NONE;
	}
	
	public void setDestroyed(boolean state) {
		this.isDestroyed = state;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
