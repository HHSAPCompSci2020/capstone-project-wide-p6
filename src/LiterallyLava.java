import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class LiterallyLava extends MovingImage{
	public LiterallyLava(PImage img, int x, int y, int w, int h) {
		
		super(img, x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void checkCollision(Player p) {
		// -------------------- collision checking ---------------------
		if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
			p.hit(15);
			
		}
	}
	
	
}
