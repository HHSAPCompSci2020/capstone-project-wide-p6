import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

public class BallBoss extends MovingImage{

	private double xVelocity, yVelocity;
	private double hp;
	private double antiMulti;
	private int phase = 0;
	private double time = 0;
	private int attack;
	private double attackDelay = 5000000;
	private double shootDelay = 5000000;
	private double stagger = 0;
	private ArrayList<PImage> images;

	private ArrayList<PImage> redEye = new ArrayList<PImage>(Arrays.asList((new PApplet()).loadImage("imgs/Boss5.png"), (new PApplet()).loadImage("imgs/droneright.png"), (new PApplet()).loadImage("imgs/droneup.png"), (new PApplet()).loadImage("imgs/droneleft.png"), (new PApplet()).loadImage("imgs/dronedown.png")));
	private ArrayList<Drone> drones;
	private Drone[] mains ;
	
	private ArrayList<ArrayList<Integer>> lasers;
	private ArrayList<ArrayList<Integer>> warnings;
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
		warnings = new ArrayList<ArrayList<Integer>>();
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
	
	public double gethp() {
		return hp;
	}

	public void act(Map map, long timeElapsed, Player p) {
		antiMulti -= 1;
		attackDelay -= timeElapsed/1000;
		shootDelay -= timeElapsed/1000;
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
				attackDelay = 10000000;
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
				attackDelay = 5000000;
				time = 0;
				break;
			case(3):
				Drone drone = mains[attp];
				double locx = p.getCenterX();
				double locy = p.getCenterY();
				double movex = 3*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				double movey = 3*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				
				drone.x += movex;
				drone.y += movey;
				drone.checkCollision(map, p);
				if (time >= 1500000000) {
					time = 0;
					attp ++;
				}
				for (int i = 0; i < mains.length; i++) {
					mains[i].checkCollision(map, p);
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
				if (drone.hp <= 0) {
					drones.remove(drone);
				}
			}
		
			double width = getWidth();
			double height = getHeight();
	
			
			
			// -------------------- collision checking ---------------------
			
			for (int i = 0; i < mains.length; i++) {
				Drone drone = mains[i];
				drone.checkCollision(map, p);
			}
			
			
			
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
			
		
		} else if (phase <= 36){
			hp = 1000;
			drones.clear();
			mains = new Drone[10];
			
			if (phase% 8 == 0) {
				warnings.add(new ArrayList<Integer>(Arrays.asList(100, 8050, 1500, 50)));
			} else if (phase % 4 == 0) {
				warnings.clear();
			}
			
			mains[0] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[1] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[2] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[3] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[4] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[5] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[6] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[7] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[8] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			mains[9] = (new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, true));
			super.setImage(images.get((int)phase/9));
			phase++;
		} else {
			stagger -=  timeElapsed;
			warnings.clear();
			lasers.clear();
			lasers.add(new ArrayList<Integer>(Arrays.asList(100, 8050, 1500, 50)));
			
			// ------------MOVEMENT -------------
			
			
			
			if ((Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y)) > 400)) {
				if (stagger <= 0) {
			if (p.x >= x) {
				xVelocity = 1.5;
			} else {
				xVelocity = -1.5;
			}
			
			if (p.y >= y) {
				yVelocity = 1.5;
			} else {
				yVelocity = -1.5;
			}
				}
			}
			
			xVelocity *= 0.95;
			yVelocity *= 0.95;
			if (Math.abs(xVelocity) < .5)
				xVelocity = 0;
			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;
			moveToLocation(x+xVelocity,y + yVelocity);
			
			
			// ------------Attacking ---------------
			if (shootDelay <= 0) {
				double locx = p.getCenterX();
				double locy = p.getCenterY();
				double movex = 2*(locx - getCenterX())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())));
				double movey = 2*(locy - getCenterY())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())));
				drones.add(new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, movex, movey));
				locx = p.getCenterX();
				locy = p.getCenterY();
				movex = 2*Math.cos(Math.PI/4 + Math.acos((locx - getCenterX())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())))));
				movey = 2*Math.sin(Math.PI/4 + Math.asin((locx - getCenterX())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())))));
				drones.add(new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, movex, movey));
				
				locx = p.getCenterX();
				locy = p.getCenterY();
				movex = 2*Math.cos(-Math.PI/4 + Math.acos((locx - getCenterX())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())))));
				movey = 2*Math.sin(-Math.PI/4 + Math.asin((locx - getCenterX())/(Math.sqrt((locx- getCenterX())*(locx- getCenterX()) + (locy- getCenterY())*(locy- getCenterY())))));
				drones.add(new Drone(redEye, (int)getCenterX(), (int)getCenterY(), 50, 50, movex, movey));
				
				shootDelay = 5000000;
				
			}
			
			if (attackDelay <= 0) {
				attack = (int)(Math.random() * 1 + 1);
				attackDelay = 2000000000;
				time = 0;
			}
			switch(attack) {
			case (0):
				for (int i = 0; i < mains.length; i++) {
					Drone drone = mains[i];
					drone.setImage(redEye.get(0));
					double locx = getCenterX() + 300 * Math.cos(time/1000000000 + i*Math.PI/10) - 25;
					double locy = getCenterY() + 300 * Math.sin(time/1000000000 + i*Math.PI/10) - 25;
					double movex = 4*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					double movey = 4*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
					drone.x += movex;
					drone.y += movey;
					drone.checkCollision(map, p);
					attp = 0;
					
				}
				break;
			case(1):
				Drone drone = mains[attp];
				double locx = p.getCenterX();
				double locy = p.getCenterY();
				double movex = 5*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				double movey = 5*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
				
				drone.x += movex;
				drone.y += movey;
				drone.checkCollision(map, p);
				if (time >= 1000000000) {
					time = 0;
					attp ++;
				}
				for (int i = 0; i < mains.length; i++) {
					if (i != attp) {
						locx = getCenterX() + 300 * Math.cos(time/1000000000 + i*Math.PI/10) - 25;
						locy = getCenterY() + 300 * Math.sin(time/1000000000 + i*Math.PI/10) - 25;
						movex = 4*(locx - mains[i].x)/(Math.sqrt((locx- mains[i].x)*(locx- mains[i].x) + (locy- mains[i].y)*(locy- mains[i].y)));
						movey = 4*(locy - mains[i].y)/(Math.sqrt((locx- mains[i].x)*(locx- mains[i].x) + (locy- mains[i].y)*(locy- mains[i].y)));
						mains[i].x += movex;
						mains[i].y += movey;
					}
					mains[i].checkCollision(map, p);
				}
				attackDelay = 10000000;
				if (attp>= 11) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000;
				}
				break;
			case(2):
				if (attp == 0) {
					if (time >= (mains.length + 3) * 1000000000) {
						attp =1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						if (time >= i * 1000000000 && time <= i + 1.5 * 1000000000) {
							drone = mains[i];
							if (i %2 == 0) {
								drone.setImage(redEye.get(1));
								locx = 100;
							} else {
								drone.setImage(redEye.get(3));
								locx = 1550;
							}
							locy =  7025 + i * 100;
							movex = 5*(locx - drone.x)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
							movey = 5*(locy - drone.y)/(Math.sqrt((locx- drone.x)*(locx- drone.x) + (locy- drone.y)*(locy- drone.y)));
							drone.x += movex;
							drone.y += movey;
						}
						if (time >= i +1.5 * 1000000000 && time <= i + 2.5 * 1000000000) {
							drone = mains[i];
							if (time/1000000000 % 60 <= 30) {
								warnings.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y - 25, 1500, 100)));
							}
						}
						if (time >= i +2.5 * 1000000000 && time <= i + 3.5 * 1000000000) {
							drone = mains[i];
							lasers.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y - 25, 1500, 100)));
						}
						
					} 
				} else {
					if (time >= (mains.length + 3) * 1000000000) {
						attp =2;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						if (time <= (10 - i)  * 1000000000 && time >= (10 - i - 1.5) * 1000000000) {
							drone = mains[i];
							if (time/1000000000 % 60 <= 30) {
								warnings.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y - 25, 1500, 100)));
							}
						}
						if (time <= (10 - i -1.5)  * 1000000000 && time >= (10 - i  - 2.5) * 1000000000) {
							drone = mains[i];
							lasers.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y - 25, 1500, 100)));
						}
					}
				}

				if (attp>= 2) {
					attp = 0; 
					attack = 0;
					attackDelay = 20000000;
				}
				break;
			
			}
			
			
			for (int i = 0; i < drones.size(); i++) {
				Drone drone = drones.get(i);
				drone.act(map, p, timeElapsed);
				drone.checkCollision(map, p);
				if (drone.hp <= 0) {
					drones.remove(drone);
				}
			}
		
			double width = getWidth();
			double height = getHeight();
	
			
			
			// -------------------- collision checking ---------------------
			
			
			for (int i = 0; i < drones.size(); i++) {
				Drone drone = drones.get(i);
				drone.act(map, p, timeElapsed);
				drone.checkCollision(map, p);
				if (drone.hp <= 0) {
					drones.remove(drone);
				}
			}
			
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
						
						xVelocity = list.kx;
						yVelocity = list.ky;
						
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
	
	public ArrayList<Drone> getDrones(){
		ArrayList<Drone> returner = new ArrayList<Drone>();
		returner.addAll(drones);
		returner.addAll(Arrays.asList(mains));
		return returner;
	}
	
	
	public ArrayList<ArrayList<Integer>> getLasers(){
		return lasers;
	}
	public ArrayList<ArrayList<Integer>> getWarnings(){
		return warnings;
	}
	
	
}
