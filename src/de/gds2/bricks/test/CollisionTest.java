package de.gds2.bricks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import de.gds2.bricks.Brick;
import de.gds2.bricks.Constants.*;

public class CollisionTest {

	// Setup Test Bricks
	private Brick brick = new Brick(1000, 300);
	
	// Test collision
	@Test
	void testGetCollisionSide() {
		assertEquals(Sides.LEFT, brick.getCollisionSide(1000-4, 310, 8, 8));		
	}
	
	// Test intersection
	@Test
	void testIsIntersecting() {
		assertTrue(brick.isIntersecting(4, 2, 6, 2, 5, 1, 5, 4));			
	}
	
	@Test
	void testIsNotIntersecting() {
		assertFalse(brick.isIntersecting(4, 2, 6, 2, 5, 4, 5, 5));			
	}

}
