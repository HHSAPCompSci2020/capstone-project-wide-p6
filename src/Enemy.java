import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;
/**
 * Base Enemy
 * @author Ethan Wang
 *
 */
public class Enemy extends MovingImage{

	private int index;
	private ArrayList<PImage> images;
/**
 * 
 * @param img
 * @param x
 * @param y
 * @param w
 * @param h
 * @param index index# in the array of enemies
 */
	public Enemy(ArrayList<PImage> img, int x, int y, int w, int h, int index) {
		
		super(img.get(0), x, y, w, h);
		images = img;
		this.index = index;
		// TODO Auto-generated constructor stub
	}
	
	/**  Causes the enemy to act and do things within the specified time.
	 * 
	 * @param map	the map to be interacted with
	 * @param timeElapsed 	the time that is to be emulated
	 * @param p		the player character
	 */
	public void act(Map map, long timeElapsed, Player p) {
		//stuff
	}
	
	
}
