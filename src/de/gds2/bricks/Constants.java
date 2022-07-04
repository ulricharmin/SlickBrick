package de.gds2.bricks;
import java.awt.*;

public final class Constants {
	private static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int FPS = 60;
	public static final int paddleY = (int) dim.getHeight() - 150;
	public static final int BALL_SIZE = 25;
	public static final int BRICK_WIDTH = 145;
	public static final int BRICK_HEIGHT = 30;
	public static final int BRICK_GAP = 10;
	public static enum Sides {
		TOP,
		LEFT,
		BOTTOM,
		RIGHT,
		NONE
	};
}
