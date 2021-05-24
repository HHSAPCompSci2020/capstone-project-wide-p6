import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;

public class BallBoss extends MovingImage{

	private double xVelocity, yVelocity;
	private double hp;
	private double antiMulti;
	private int phase = 0;
	private double time = 0;
	private int attack;
	private double attackDelay = 10000000;
	private ArrayList<PImage> images;

	private ArrayList<Drone> drones;
	private Drone[] mains ;
	
	private ArrayList<ArrayList<Integer>> lasers;
	private int attp;
	
	
	public BallBoss(ArrayList<PImage> img, int x, int y, int w, int h) {
		
		super(img.get(0), x - w/2, y - h/2, w, h);
		xVelocity = 0;
		yVelocity = 0;
		hp = 1000;
		antiMulti = 5;
		images = img;
		attp = 0;
		drones = new ArrayList<Drone>();
		lasers = new ArrayList<ArrayList<Integer>>();
		mains = new Drone[8];
		mains[0] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[1] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[2] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[3] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[4] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[5] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[6] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		mains[7] = (new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50));
		
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		antiMulti -= 1;
		attackDelay -= timeElapsed/1000;
		time += timeElapsed;
		if (phase == 0) {
			

			if (attackDelay <= 0) {
				attack = (int)(Math.random() * 3 + 1);
				attackDelay = 2000000000;
				time = 0;
			}
			switch(attack) {
			case (0):
				for (int i = 0; i < mains.length; i++) {
					Drone drone = mains[i];
					double locx = getCenterX() + 300 * Math.cos(time/1000000000 + i*Math.PI/4) - 25;
					double locy = getCenterY() + 300 * Math.sin(time/1000000000 + i*Math.PI/4) - 25;
					double movex = 4*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					double movey = 4*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					drone.x += movex;
					drone.y += movey;
					drone.checkCollision(map, p);
					attp = 0;
					
				}
				
				
				break;
			case(1):
				drones.add(new Drone(images, (int)x+200, (int)y, 50, 50, 2));
				drones.add(new Drone(images, (int)x-200, (int)y, 50, 50, 2));
				drones.add(new Drone(images, (int)x+200, (int)y-50, 50, 50, 3));
				drones.add(new Drone(images, (int)x-200, (int)y-50, 50, 50, 3));
				drones.add(new Drone(images, (int)x, (int)y+50, 50, 50, 3));
				attack = 0;
				attackDelay = 20000000;
				time = 0;
				break;
			case(2):
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, -3, 0));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, 0, 3));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, -3, 3));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, -3, -3));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, 0, -3));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, 3, -3));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, 3, 0));
				drones.add(new Drone(images, (int)getCenterX(), (int)getCenterY(), 50, 50, 3, 3));
		
				attack = 0;
				attackDelay = 10000000;
				time = 0;
				break;
			case(3):
				Drone drone = mains[attp];
				double locx = p.getCenterX();
				double locy = p.getCenterY();
				double movex = 2*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				double movey = 2*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				drone.x += movex;
				drone.y += movey;
				drone.checkCollision(map, p);
				if (time >= 1500000000) {
					time = 0;
					attp ++;
				}
				attackDelay = 10000000;
				if (attp>= 7) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000;
				}
				break;
			
			}
			for (int i = 0; i < drones.size(); i++) {
				Drone drone = drones.get(i);
				drone.act(map, p, timeElapsed);
				drone.checkCollision(map, p);
			}
		
			double width = getWidth();
			double height = getHeight();
	
			
			
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
			drones.clear();
			mains = new Drone[16];
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
	public ArrayList<ArrayList<Integer>> getLasers(){
		return lasers;
	}
	
	
}
