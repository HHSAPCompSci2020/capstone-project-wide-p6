import java.util.ArrayList;

import processing.core.PImage;
/**
 * class representing hitboxes
 * @author Ethan Wang
 *
 */
public class Hitbox extends MovingImage{

	public int x, y, w, h, dam, dur, kx, ky;
	
	private ArrayList<PImage> anims;
	
	/**
	 * 
	 * @param img arraylist of images to be used, only uses 1
	 * @param x top left x
	 * @param y top left y
	 * @param w width
	 * @param h height
	 * @param dam damage
	 * @param dur duration of the hitbox in frames
	 * @param kx x knockback
	 * @param ky y knockback
	 */
	public Hitbox(ArrayList<PImage> img, int x, int y, int w, int h, int dam, int dur, int kx, int ky) {
		super(img.get(dur-1), x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.dam = dam;
		this.dur = dur;
		this.kx = kx;
		this.ky = ky;
		anims = img;
	}
	public Hitbox() {
		super(new PImage(), 0, 0, 0, 0);
	}
	/**
	 * 
	 * @param img sets image based on index from its arraylist of images
	 */
	public void setImage(int img) {
		super.setImage(anims.get(img));
	}
	
}
