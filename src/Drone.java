import java.util.ArrayList;

import processing.core.PImage;

public class Drone extends MovingImage{

	public Drone(ArrayList<PImage> img, int x, int y, int w, int h) {
		super(img.get(0), x, y, w, h);
	}

}
