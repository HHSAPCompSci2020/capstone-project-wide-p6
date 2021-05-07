

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
	private double jumpStrength;
	private double delay;
	private int health;
	private double stamina;

	public Player(PImage img, int x, int y) {
		super(img, x, y,PLAYER_WIDTH, PLAYER_HEIGHT);
		xVelocity = 0;
		yVelocity = 0;
		onASurface = false;
		gravity = 0.7;
		friction = .9;
		jumpStrength = 12;
		stamina = 100;
		delay = 0;
		gravIgnore = 0;
	}

	// METHODS
	public void walk(int dir) {
		if (xVelocity <= 10 && xVelocity >= -10)
			xVelocity += dir;
	}

	public void jump() {
		if (delay <= 0) {
			if (onASurface) {
				yVelocity = -jumpStrength;
				delay = 200000000;
			}else if (stamina >= 25) {
				System.out.println(stamina);
				yVelocity = -jumpStrength;
				stamina -= 25;
				delay = 200000000;
			}
		}
	}
	
	
	public void dash() {
		if (delay <= 0) {
			if (onASurface) {
				xVelocity = 25;
				delay = 250000000;
				gravIgnore = 250000000;
			}else if (stamina >= 25) {
				System.out.println(stamina);
				xVelocity = 25;
				stamina -= 25;
				delay = 250000000;
				gravIgnore = 250000000;
			}
		}
	}

	public void act(ArrayList<Shape> obstacles, long timeElapsed) {
		delay -= timeElapsed;
		gravIgnore -= timeElapsed;
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
		double yCoord2 = yCoord + yVelocity;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity));

		onASurface = false;

		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					onASurface = true;
					stamina = 100;
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

		double xCoord2 = xCoord + xVelocity;

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity),height);

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
			stamina -= (double)timeElapsed/100000000;
		}

	}


}