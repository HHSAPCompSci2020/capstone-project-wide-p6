import java.awt.Rectangle;
import java.util.ArrayList;

import processing.core.PImage;

public class Drone extends MovingImage{

	public double xVel;
	public double yVel;
	private int antiMulti;
	private double stagger;
	private int shootDelay;
	public int hp;
	public int type;
	
	public Drone(ArrayList<PImage> img, int x, int y, int w, int h) {
		super(img.get(0), x, y, w, h);
		type = 0;
		hp = Integer.MAX_VALUE;
		
	}
	

	public Drone(ArrayList<PImage> img, int x, int y, int w, int h, int type) {
		super(img.get(0), x, y, w, h);
		this.type = type;
		hp = Integer.MAX_VALUE;
		
	}
	
	public Drone(ArrayList<PImage> img, int x, int y, int w, int h,  double xv, double yv) {
		super(img.get(0), x, y, w, h);
		type = 1;
		xVel = xv;
		yVel = yv;
	}
	
	public void act(Map map, Player p, long timeElapsed) {
		if (type == 1) {
			x += xVel;
			y += yVel;
		}
	}
	
	public void checkCollision(Map map, Player p) {
		antiMulti--;
		if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
			
				p.hit(10);
			
		}
		
		
		for(int i = 0; i < map.getHitboxes().size(); i++) {
			Hitbox list =  map.getHitboxes().get(i);
			if (type == 0) {
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x,y,width, height)) {
					if ((antiMulti <= 0)) {
						antiMulti = 6;
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 60;
						}
					}
				}
			} else if (type == 1) {
				
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x,y,width, height)) {
					if ((antiMulti <= 0)) {
						antiMulti = 6;
						
						if(p.combo >= 30) {
							hp -= list.dam * 2;
							p.hp+= list.dam/5; 
						} else if (p.combo >= 20) {
							hp -= list.dam * 1.5;
						} else if (p.combo >= 10) {
							hp -= list.dam * 1.3;
						} else {
							hp -= list.dam;
						}
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 60;
						}
					}
				}
			}
		}
	}

	public void shoot() {
		
	}
}
