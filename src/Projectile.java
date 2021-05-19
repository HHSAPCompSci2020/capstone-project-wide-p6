import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import processing.core.PImage;

public class Projectile extends MovingImage{
	private double xvel, yvel, distance;
	private int damage;
	
	
	public Projectile(PImage img, int x, int y, int w, int h, double vx, double vy, double dist, int dam) { // if i wanted an animation i could give this an arraylist but its kind of a pain for individual projectiles so i'll just make them all circles or something.
		super(img, x, y, w, h);
		xvel = vx;
		yvel = vy;
		distance = dist;
		damage = dam;
	}
	
	public void act(Map map, long timeElapsed, Player p) {
		
		
		x = x+ xvel*timeElapsed/17000000;
		y = y + yvel*timeElapsed/17000000;
		distance -= Math.sqrt(xvel*timeElapsed/17000000*xvel*timeElapsed/17000000 +  yvel*timeElapsed/17000000* yvel*timeElapsed/17000000);
		
		
		if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT)) || (new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT)).intersectsLine(new Line2D.Double((int)(x), (int)(y), (int)(x - xvel*timeElapsed/17000000), (int)(y - yvel*timeElapsed/17000000)))) {
				p.hit(damage);
		}
			
	}
	
	public boolean die(Map map) {
		for(int i = 0; i < map.getObstacles().size(); i++) {
			Shape block =  map.getObstacles().get(i);
			if (block.getBounds2D().intersects(x, y, width, height)) {
				return true;
			}
		}
		for(int i = 0; i < map.getHitboxes().size(); i++) {
			Hitbox list =  map.getHitboxes().get(i);
			if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x, y, width, height)) {
				return true;
			}
		}
		if (distance <= 0) {
			return true;
		}
		return false;
	}

}
