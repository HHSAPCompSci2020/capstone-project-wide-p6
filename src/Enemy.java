import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class Enemy extends MovingImage{

	private ArrayList<MovingImage> enemy;
	private double xVelocity, yVelocity;
	private double xposition,yposition;
	private boolean onASurface;
	private double gravity;
	private double hp;
	private double stagger;
	private int damage;
	private double speed;
	private double senseRadius = 25;
	private double health;
	private ArrayList<Integer> lastHitBy;
	private ArrayList<PImage> images;

	public Enemy(PImage img, int x, int y, int w, int h) {
		
		super(img, x, y, w, h);
		xVelocity = 0;
		xposition=x;
		yposition=y;
		yVelocity = 0;
		onASurface = false;
		gravity = 0.7;
		hp = 100;
		damage = 10;
		speed = 2;
		lastHitBy = new ArrayList<Integer>();
		stagger = 0;
		// TODO Auto-generated constructor stub
	}

	//moves on tope of platform until the edge then reverses direction. If player is nearby will move toward player
	public void move(int dir) {
		if (dir>=0 )
			xVelocity = 10;
		else if (dir<=0)
		xVelocity = -10;

	}
	
	public void act(Map map, long timeElapsed, Player p) {
		ArrayList<Shape> obstacles = map.getObstacles();
		
		stagger -= timeElapsed;
		
		
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// *********** Movement ***********
		
		
		if(stagger <= 0) {
			if (p.x >= x) {
				xVelocity = speed;
			} else {
				xVelocity = -speed;
			}
		
		}
		

		
		//-------------------Physics---------------------
		
		

		double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));
		
		onASurface = false;
		
		yVelocity += gravity*timeElapsed/20000000; // GRAVITY
		if (yVelocity > 10) {
			yVelocity = 10;
		}
		
		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					onASurface = true;
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


		xVelocity *= 0.9;

		
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
		
		
		
		
		// -------------------- collision checking ---------------------
		for(int i = 0; i < map.getHitboxes().size(); i++) {
			ArrayList<Integer> list =  map.getHitboxes().get(i);
			if ((new Rectangle((int)list.get(0),(int)list.get(1),list.get(2),list.get(3))).intersects(strechX) || (new Rectangle((int)list.get(0),(int)list.get(1),list.get(2),list.get(3))).intersects(strechY)) {
				if (!(lastHitBy.equals(list))) {
					hp -= list.get(4);
					xVelocity = list.get(6);
					yVelocity = list.get(7);
					stagger = 100000000 * (list.get(6) + list.get(7));
					lastHitBy = list;
				}
			}
		}
		
		
		if(hp <=0) {
			map.getEnemies().remove(this);
		}
		if (strechX.intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.height, (int)p.width))|| strechY.intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.height, (int)p.width)) && (stagger <= 0)) {
			p.hit(damage);
		}
		
		

	}
	
	
}
