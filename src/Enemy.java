import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class Enemy extends MovingImage{

	private double xVelocity, yVelocity;
	private double hp;
	private int index;
	private ArrayList<PImage> images;

	public Enemy(ArrayList<PImage> img, int x, int y, int w, int h, int index) {
		
		super(img.get(0), x, y, w, h);
		xVelocity = 0;
		yVelocity = 0;
		hp = 10000;
		images = img;
		this.index = index;
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		//stuff
	}
	
	
}
