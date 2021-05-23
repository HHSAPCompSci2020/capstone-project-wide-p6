import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class Enemy extends MovingImage{

	private int index;
	private ArrayList<PImage> images;

	public Enemy(ArrayList<PImage> img, int x, int y, int w, int h, int index) {
		
		super(img.get(0), x, y, w, h);
		images = img;
		this.index = index;
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		//stuff
	}
	
	
}
