import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class Enemy extends Entity{

	private ArrayList<MovingImage> enemy;
	public Enemy(PImage img, int x, int y, int w, int h) {
		super(img, x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	//moves on tope of platform until the edge then reverses direction. If player is nearby will move toward player
	public void move() {
		
		
	}
	
	
	//If player is within a certain distance it will turn toward it
	public void surroundingImage(ArrayList<MovingImage>a, int x, int y) {
		
	}
	
	//shoots a projectile that will disappear either on contact with another entity or after a certain distance
	public void projectile() {
		
	}
	
	public void act() {
		
	}
}
