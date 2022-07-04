package de.gds2.bricks;

import static de.gds2.bricks.Constants.*;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.synth.ColorType;

public class Brick {

	// Brick corners, starting top left clock wise
	private float ax, ay, bx, by, cx, cy, dx, dy;
	
	// Destroyed state
	private boolean isDestroyed = false;
	
	public Brick(float p_x, float p_y) {
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
			p_g.fillRect((int)ax, (int)ay, BRICK_WIDTH, BRICK_HEIGHT);
			p_g.setColor(Color.RED);
			p_g.drawLine((int)ax, (int)ay, (int)bx, (int)by);
			p_g.drawLine((int)bx, (int)by, (int)cx, (int)cy);
			p_g.drawLine((int)cx, (int)cy, (int)dx, (int)dy);
			p_g.drawLine((int)dx, (int)dy, (int)ax, (int)ay);
		}
	}
	
	public boolean isIntersecting(float ball_ax, float ball_ay, float ball_bx, float ball_by, float cx, float cy, float dx, float dy) {
		// Vektor AB
		float xab = ball_bx-ball_ax;
		float yab = ball_by-ball_ay;
		
		// Vektor CD
		float xcd = dx-cx;
		float ycd = dy-cy;
		
		float beta = (ball_ay+(cx*yab/xab)-(ball_ax*yab/xab)-cy) / 
				          (ycd-xcd*yab/xab);
		float alpha = (cx+beta*xcd-ball_ax)/xab;
		
		if (beta < 1 && beta > 0 && alpha < 1 && alpha > 0)
			return true;
		
		return false;
	}
	
	public Sides getCollisionSide(float p_ballX, float p_ballY, float p_ballVelX, float p_ballVelY) {
		if (isDestroyed()) {
			return Sides.NONE;
		}
		
		// Nächste Position Ball
		float ball_bx = p_ballX+p_ballVelX;
		float ball_by = p_ballY+p_ballVelY;	
		
		if (p_ballVelX > 0 && p_ballVelY > 0) { // Ball fliegt nach unten rechts
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax-BALL_SIZE, ay-BALL_SIZE, bx, by-BALL_SIZE))
				return Sides.TOP;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax-BALL_SIZE, ay-BALL_SIZE, dx, dy))
				return Sides.LEFT;
		} else if (p_ballVelX < 0 && p_ballVelY > 0) { // Ball fliegt nach unten links
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax-BALL_SIZE, ay-BALL_SIZE, bx, by-BALL_SIZE))
				return Sides.TOP;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, bx, by-BALL_SIZE, cx, cy))
				return Sides.RIGHT;
		} else if (p_ballVelX > 0 && p_ballVelY < 0) { // Ball fliegt nach oben rechts
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, cx, cy, dx-BALL_SIZE, dy))
				return Sides.BOTTOM;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, ax-BALL_SIZE, ay-BALL_SIZE, dx-BALL_SIZE, dy))
				return Sides.LEFT;
		} else if (p_ballVelX < 0 && p_ballVelY < 0) { // Ball fliegt nach oben links
			if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, cx+BALL_SIZE, cy, dx-BRICK_GAP, dy))
				return Sides.BOTTOM;
			else if (isIntersecting(p_ballX, p_ballY, ball_bx, ball_by, bx, by-BALL_SIZE, cx, cy))
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
