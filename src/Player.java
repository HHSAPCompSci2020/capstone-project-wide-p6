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
	public int lastCheck = 0;
	public int hp;
	public double stamina;
	
	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double friction;
	private double gravity;
	private double gravIgnore;
	private double invincible;
	private double jumpStrength;
	private double delay;
	private boolean direction; // true = right, false = left
	private int dive;
	private double dash;

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
		friction = .87;
		jumpStrength = 12;
		stamina = 100;
		delay = 0;
		gravIgnore = 0;
		direction = true;
		dive = 0;
		dash = 0;
		hp = 100;
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
					dash = 100000000;
				else 
					dash = 100000000;
				stamina -= 25;
				invincible = 200000000;
				delay = 200000000;
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
				delay = 200000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40 - PLAYER_WIDTH/2, (int)y-5, 75, 75, 10, 5, 1, -3)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-35 - PLAYER_WIDTH/2, (int)y-5, 75, 75, 10, 5, -1, -3)));
				}
			}else if (stamina >= 5) {
				stamina -= 5;
				xVelocity = 0;
				delay = 200000000;
				gravIgnore = 220000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40- PLAYER_WIDTH/2, (int)y-5, 75, 75, 10, 5, 1, -3)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-35- PLAYER_WIDTH/2, (int)y-5, 75, 75, 10, 5, -1, -3)));
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
				delay = 300000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40- PLAYER_WIDTH/2, (int)y-25, 100, 100, 15, 5, 10, -15)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-55- PLAYER_WIDTH/2, (int)y-25, 100, 100, 15, 5, -10, -15)));
				}
			}else if (stamina >= 5) {
				stamina -= 15;
				xVelocity = 0;
				delay = 300000000;
				gravIgnore = 325000000;
				if (direction) {
					map.addHitbox(new ArrayList(Arrays.asList((int)x+40- PLAYER_WIDTH/2, (int)y-25, 100, 100, 20, 5, 10, -15)));
				} else {
					map.addHitbox(new ArrayList(Arrays.asList((int)x-55- PLAYER_WIDTH/2, (int)y-25, 100, 100, 20, 5, -10, -15)));
				}
			}
		}
	}
	
	public void hit(int damage) {
		if (invincible <= 0) {
			invincible = 500000000;
			hp -= damage;
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
		dash -= timeElapsed;
		gravIgnore -= timeElapsed;
		invincible -= timeElapsed;
		if(dive == 1) {
			if (direction) {
				map.addHitbox(new ArrayList(Arrays.asList((int)x, (int)y+50, 50, 50, 10, 1, 5, -10)));
			} else {
				map.addHitbox(new ArrayList(Arrays.asList((int)x, (int)y+50, 50, 50, 10, 1, -5, -10)));
			}
			
			delay = 100000000;
		} else if (dive == 2) {
			if (direction) {
				map.addHitbox(new ArrayList(Arrays.asList((int)x-5, (int)y+60, 60, 60, 25, 1, 10, -15)));
			} else {
				map.addHitbox(new ArrayList(Arrays.asList((int)x-5, (int)y+60, 60, 60, 25, 1, -10, -15)));
			}
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
		} else {
			yVelocity = 0.5;
		}
		if (dive == 1) {
			yVelocity = 15;
		} else if (dive == 2) {
			yVelocity = 30;
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

		if(dash > 0) {
			
			if (direction) {
				
				xVelocity = 40;
			} else {
				
				xVelocity = -40;
			}
			map.addHitbox(new ArrayList(Arrays.asList((int)(x - PLAYER_WIDTH/2 +  xVelocity*timeElapsed/14000000), (int)y, 75, 50, 10, 1, 0, -10)));
		} 
		
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
		
		for(int i = 0; i < map.getCheckpoints().size(); i++) {
			if (map.getCheckpoints().get(i).intersects(strechX) || map.getCheckpoints().get(i).intersects(strechY)) {
				hp = 100;
				lastCheck = i;
			}
		}

	}


}
