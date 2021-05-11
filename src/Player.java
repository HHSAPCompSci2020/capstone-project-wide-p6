/**@Author Shelby, Ethan Wang
 * A class representing the player character.
 */

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

import processing.core.PImage;

public class Player extends MovingImage {

	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 60;

	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double friction;
	private double gravity;
	private double gravIgnore;
	private double invincible;
	private double jumpStrength;
	private double delay;
	private int health;
	private boolean direction; // true = right, false = left
	private double stamina;
	private int dive;

	private ArrayList<PImage> images;
	/** The constructor of the player class, slightly modified
	 * 
	 * @param images A list of images which the character will use as sort of "animations"
	 * @param x The x coordinate to spawn the player at
	 * @param y The y coordinate to spawn the player at
	 */
	public Player(ArrayList<PImage> images, int x, int y) {
		super(images.get(0), x, y,PLAYER_WIDTH, PLAYER_HEIGHT);
		this.images = images;
		xVelocity = 0;
		yVelocity = 0;
		onASurface = false;
		gravity = 0.7;
		friction = .9;
		jumpStrength = 12;
		stamina = 100;
		delay = 0;
		gravIgnore = 0;
		direction = true;
		dive = 0;
	}

	// METHODS
	public void walk(int dir) {
		direction = dir > 0;
		if (xVelocity <= 10 && xVelocity >= -10)
			xVelocity += dir;
	}

	public void jump() {
		if (delay <= 0) {
			if (onASurface) {
				yVelocity = -jumpStrength;
				delay = 200000000;
			}else if (stamina >= 25) {
				yVelocity = -jumpStrength;
				stamina -= 25;
				delay = 200000000;
			}
		}
	}
	
	/**
	 * A method which causes the character to dash forwards, and cause the characters to mostly ignore gravity.
	 */
	public void dash() {
		if (delay <= 0) {
			/*if (onASurface) {
				if (direction)
					xVelocity = 30;
				else 
					xVelocity = -30;
				delay = 250000000;
				gravIgnore = 250000000;
			}else*/ if (!(onASurface) && stamina >= 25) {
				if (direction)
					xVelocity = 30;
				else 
					xVelocity = -30;
				stamina -= 25;
				invincible = 200000000;
				delay = 250000000;
				gravIgnore = 250000000;
			}
		}
	}
	
	public void dive() {
		
		if(!(onASurface) && (delay <= 0)) {
			dive = 1;
		}
	}
	
	public void lightAttack(Map map) {
		if (delay <= 0) {
			if (onASurface) {
				xVelocity = 0;
				delay = 100000000;
				gravIgnore = 150000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40, (int)y, 50, 75, 10, 5)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-40, (int)y, 50, 75, 10, 5)));
				}
			}else if (stamina >= 5) {
				stamina -= 5;
				xVelocity = 0;
				delay = 100000000;
				gravIgnore = 150000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40, (int)y, 50, 75, 10, 5)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-40, (int)y, 50, 75, 10, 5)));
				}
			}
		}
	}
	
	public void heavyAttack(Map map) {
		if (dive == 1) {
			dive = 2;
		}else if (delay <= 0) {
			if (onASurface) {
				xVelocity = 0;
				delay = 200000000;
				gravIgnore = 250000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40, (int)y, 75, 100, 20, 5)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-40, (int)y, 75, 100, 20, 5)));
				}
			}else if (stamina >= 5) {
				stamina -= 15;
				xVelocity = 0;
				delay = 200000000;
				gravIgnore = 250000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40, (int)y, 75, 100, 20, 5)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-40, (int)y, 75, 100, 20, 5)));
				}
			}
		}
	}
	
	
	/**
	 * Modified act method. Uses time elapsed so that even on slower computers the game should run relatively the same.
	 * @param obstacles The arraylist of all the obstacles on the map used for collisions.
	 * @param timeElapsed How much time has passed since the last time the draw method in DrawingSurface was run in nanoseconds
	 */
	public void act(Map map, long timeElapsed) {
		ArrayList<Shape> obstacles = map.getObstacles();
		delay -= timeElapsed;
		gravIgnore -= timeElapsed;
		invincible -= timeElapsed;
		if(dive == 1) {
			map.addHitbox(new ArrayList(Arrays.asList((int)x, (int)y+50, 50, 50, 10, 1)));
			delay = 100000000;
		} else if (dive == 2) {
			map.addHitbox(new ArrayList(Arrays.asList((int)x, (int)y+50, 75, 75, 15, 1)));
			delay = 100000000;
			invincible = 100000000;
		}
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// ***********Y AXIS***********
		if ((gravIgnore <= 0)) {
			yVelocity += gravity*timeElapsed/20000000; // GRAVITY
			if (yVelocity > 6) {
				yVelocity = 6;
			}
		} else if (false){
			yVelocity = 10;
		} else {
			yVelocity = 0.5;
		}
		if (dive == 1) {
			yVelocity = 25;
		} else if (dive == 2) {
			yVelocity = 35;
		}
		double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));

		onASurface = false;

		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					onASurface = true;
					stamina = 100;
					dive = 0;
					standingSurface = s;
					yVelocity = 0;
				}
			}
			if (standingSurface != null) {
				Rectangle r = standingSurface.getBounds();
				yCoord2 = r.getY()-height;
			}
		} else if (yVelocity < 0) {
			Shape headSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					headSurface = s;
					yVelocity = 0;
				}
			}
			if (headSurface != null) {
				Rectangle r = headSurface.getBounds();
				yCoord2 = r.getY()+r.getHeight();
			}
		}

		if (Math.abs(yVelocity) < .5)
			yVelocity = 0;

		// ***********X AXIS***********


		xVelocity *= friction;

		double xCoord2 = xCoord + xVelocity*timeElapsed/17000000;

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity*timeElapsed/17000000),height);

		if (xVelocity > 0) {
			Shape rightSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					rightSurface = s;
					xVelocity = 0;
				}
			}
			if (rightSurface != null) {
				Rectangle r = rightSurface.getBounds();
				xCoord2 = r.getX()-width;
			}
		} else if (xVelocity < 0) {
			Shape leftSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					leftSurface = s;
					xVelocity = 0;
				}
			}
			if (leftSurface != null) {
				Rectangle r = leftSurface.getBounds();
				xCoord2 = r.getX()+r.getWidth();
			}
		}


		if (Math.abs(xVelocity) < .5)
			xVelocity = 0;

		moveToLocation(xCoord2,yCoord2);
		
		if (!onASurface) {
			stamina -= (double)timeElapsed/200000000;
		}
		
		if (direction) {
			super.setImage(images.get(1));
		} else {
			super.setImage(images.get(0));
		}

	}


}
