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
	private int index;
	private int phase = 1;
	private double time = 0;
	private int attack;
	private double attackDelay = 50000000;
	private ArrayList<PImage> images;

	private ArrayList<Drone> drones;
	private Drone[] mains ;
	
	private ArrayList<ArrayList<Integer>> lasers;
	
	private ArrayList<Rectangle> laserDraw;
	
	public BallBoss(ArrayList<PImage> img, int x, int y, int w, int h) {
		
		super(img.get(0), x, y, w, h);
		xVelocity = 0;
		yVelocity = 0;
		hp = 1000;
		antiMulti = 5;
		images = img;
		this.index = index;
		mains = new Drone[8];
		mains[0] = (new Drone(images, x, y, 50, 50));
		mains[1] = (new Drone(images, x, y, 50, 50));
		mains[2] = (new Drone(images, x, y, 50, 50));
		mains[3] = (new Drone(images, x, y, 50, 50));
		mains[4] = (new Drone(images, x, y, 50, 50));
		mains[5] = (new Drone(images, x, y, 50, 50));
		mains[6] = (new Drone(images, x, y, 50, 50));
		mains[7] = (new Drone(images, x, y, 50, 50));
		
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		ArrayList<Shape> obstacles = map.getObstacles();
		antiMulti -= 1;
		attackDelay -= timeElapsed/1000;
		time += timeElapsed;
		if (phase == 0) {
			

			if (attackDelay <= 0) {
				attack = (int)(Math.random() * 1 + 1);
				attackDelay = 2000000000;
				time = 0;
			}
			switch(attack) {
			case (0):
				for (int i = 0; i < mains.length; i++) {
					Drone drone = mains[i];
					double locx = getCenterX() + 200 * Math.cos(time/100000000 + i*Math.PI/4) - 25;
					double locy = getCenterY() + 200 * Math.sin(time/100000000 + i*Math.PI/4) - 25;
					double movex = 2*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					double movey = 2*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					drone.moveToLocation(drone.x + movex, drone.xVelocity + movey);
					
				}
				
				
				break;
			case(1):
				drones.add(new Drone(images, (int)x+200, (int)y, 50, 50, 2));
				drones.add(new Drone(images, (int)x-200, (int)y, 50, 50, 2));
				drones.add(new Drone(images, (int)x+200, (int)y-50, 50, 50, 3));
				drones.add(new Drone(images, (int)x-200, (int)y-50, 50, 50, 3));
				drones.add(new Drone(images, (int)x, (int)y+50, 50, 50, 3));
				attack = 0;
				attackDelay = 150000000;
				break;
			case(2):

				attack = 0;
				attackDelay = 150000000;
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
