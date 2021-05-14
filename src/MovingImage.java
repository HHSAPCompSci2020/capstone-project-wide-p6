
import java.awt.geom.Rectangle2D;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a moving image.
 *
 * by: Shelby
 * on: 5/3/13
 * Edited by: Ethan Wang
 * on: 5/6/21
 */
 
public class MovingImage extends Rectangle2D.Double {
	
	// FIELDS
	private PImage image;
	
	// CONSTRUCTORS
	public MovingImage(PImage img, int x, int y, int w, int h) {
		super(x,y,w,h);
		image = img;
	}
	
	
	// METHODS	
	/**
	 * moves the image to a cooradnate
	 * @param x x coordinate of where to move
	 * @param y y coordinate of where to move
	 */
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	/**
	 * moves the image by a certain distance
	 * @param x x distance of where to move
	 * @param y y distance of where to move
	 */
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	/**
	 * 
	 * @param windowWidth how wide the window will be
	 * @param windowHeight how high the window will be
	 */
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	/**
	 * draws the screen
	 * @param g where it will be drawn
	 * @param camx where the camera will move
	 * @param camy where the camera will move
	 */
	public void draw(PApplet g, double camx, double camy) {
		g.image(image,(int)(x - camx),(int)(y - camy),(int)width,(int)height);
	}
	/**
	 * A method to change the moving image's image.
	 * @param img the image to set the moving image to.
	 */
	public void setImage(PImage img) {
		image = img;
	}
}










