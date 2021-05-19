import java.util.ArrayList;

import processing.core.PImage;

public class Hitbox extends MovingImage{

	public int x, y, w, h, dam, dur, kx, ky;
	
	private ArrayList<PImage> anims;
	
	
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

	public void setImage(int img) {
		super.setImage(anims.get(img));
	}
	
}
