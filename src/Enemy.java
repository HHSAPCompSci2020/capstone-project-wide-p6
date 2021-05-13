import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class Enemy extends Entity{

	private ArrayList<MovingImage> enemy;
	private double xVelocity, yVelocity;
	private double xposition,yposition;
	private boolean onASurface;
	private double gravity;
	private double gravIgnore;
	private double senseRadius = 25;
	private double bulletSpeed;
	private Entity bullet;
	private ArrayList<PImage> images;

	public Enemy(PImage img, int x, int y, int w, int h,int type) {
		
		super(img, x, y, w, h);
		xVelocity = 0;
		xposition=x;
		yposition=y;
		bulletSpeed = 1;
		yVelocity = 0;
		onASurface = false;
		gravity = 0;
		gravIgnore = 0;
		if(type==1) {
			gravity = 0.7;
		}
		else {
			gravIgnore= 250000000;
		}
		

		
		// TODO Auto-generated constructor stub
	}
	
	//moves on tope of platform until the edge then reverses direction. If player is nearby will move toward player
	public void move(int dir) {
		if (dir>=0 )
			xVelocity = 10;
		else if (dir<=0)
		xVelocity = -10;

	}
	
	
	
	public void surroundingImage(ArrayList<MovingImage>a, int x, int y) {
		
	}
	
	//if player is nearby, return true
	public boolean checkPlayer() {
		return false;
		
	}
	
	//shoots a projectile that will disappear either on contact with another entity or after a certain distance
	public void projectile() {
		
		bullet.moveToLocation(xposition+1, yposition);
		
	}
	
	public void act() {
		
	}
	
	
}
