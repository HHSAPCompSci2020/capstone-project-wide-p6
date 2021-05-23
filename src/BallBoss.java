import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class BallBoss extends MovingImage{

	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double gravity;
	private double hp;
	private double speed;
	private double senseRadius = 300;
	private double antiMulti;
	private int index;
	private int phase = 1;
	private int shootDelay = 2000000;
	private int attackDelay = 2000000;
	private ArrayList<PImage> images;

	private ArrayList<Drone> drones;
	private Drone[] mains ;
	
	public BallBoss(ArrayList<PImage> img, int x, int y, int w, int h) {
		
		super(img.get(0), x, y, w, h);
		xVelocity = 0;
		yVelocity = 0;
		gravity = 0.5;
		hp = 1000;
		speed = 1.5;
		antiMulti = 5;
		images = img;
		this.index = index;
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		ArrayList<Shape> obstacles = map.getObstacles();
		
		if (phase == 0) {
			shootDelay -= timeElapsed/1000;
			antiMulti -= 1;
			
			if (shootDelay <= 0) {
				map.shoot(0, (int)x, (int)y, 10, 10, (p.x - x)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, (p.y - y)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, 400, 10);
				shootDelay = 1500000;
			}
			
			
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();
	
			// *********** Movement ***********
		
			
			//-------------------Physics---------------------
			
			
			
			// -------------------- collision checking ---------------------
			
			
			
			
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if (new Ellipse2D.Double(x, y, width, height).intersects((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)) {
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
			
			
			if(hp <=0) {
				phase = 1;
			}
			
		
		} else if (phase <= 16){
			hp = 1500;
			super.setImage(images.get((int)phase/4));
			phase++;
		} else {
			
		}
		
	}
	
	public ArrayList<Drone> getDrones(){
		ArrayList<Drone> returner = new ArrayList<Drone>();
		returner.addAll(drones);
		returner.addAll(Arrays.asList(mains));
		return returner;
	}
	
	
}
