

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;
/**@author Ethan Wang
 * A class representing the ballboss
 */
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
	
	/**
	 * 
	 * @param img images to be used
	 * @param x top left x
	 * @param y top left y
	 * @param w width
	 * @param h height
	 */
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
	/**  Causes the boss to act and do things within the specified time.
	 * 
	 * @param map	the map to be interacted with
	 * @param timeElapsed 	the time that is to be emulated
	 * @param p		the player character
	 */
	public void act(Map map, long timeElapsed, Player p) {
		antiMulti -= 1;
		attackDelay -= timeElapsed/1000;
		shootDelay -= timeElapsed/1000;
		time += timeElapsed/1000;
		double scale = timeElapsed/10000000;
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
					double locx = getCenterX() + 300 * Math.cos(time/1000000.0 + i*Math.PI/4) - 25;
					double locy = getCenterY() + 300 * Math.sin(time/1000000.0 + i*Math.PI/4) - 25;
					drone.moveTo( scale * 4, locx, locy);
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
				drone.moveTo( scale * 3, locx, locy);
				drone.checkCollision(map, p);
				if (time >= 1500000) {
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
			attp = 0;
			attack = 0;
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
				attack = (int)(Math.random() * 6 + 1);
				attackDelay = 2000000000;
				time = 0;
				attp = 0;
			}
			switch(attack) {
			case (0):
				for (int i = 0; i < mains.length; i++) {
					Drone drone = mains[i];
					drone.setImage(redEye.get(0));
					double locx = getCenterX() + 300 * Math.cos(time/1000000.0 + i*Math.PI/5) - 25;
					double locy = getCenterY() + 300 * Math.sin(time/1000000.0 + i*Math.PI/5) - 25;
					drone.moveTo( scale * 6, locx, locy);
					drone.checkCollision(map, p);
					attp = 0;
					
				}
				break;
			case(1):
				Drone drone = mains[attp];
				double locx = p.getCenterX();
				double locy = p.getCenterY();
				
				drone.moveTo( scale * 5, locx, locy);
				
				drone.checkCollision(map, p);
				if (time >= 1000000.0) {
					time = 0;
					attp ++;
				}
				for (int i = 0; i < mains.length; i++) {
					if (i != attp) {
						drone = mains[i];
						locx = getCenterX() + 300 * Math.cos(time/1000000.0 + i*Math.PI/5) - 25;
						locy = getCenterY() + 300 * Math.sin(time/1000000.0 + i*Math.PI/5) - 25;
						drone.moveTo( scale * 4, locx, locy);
					}
					mains[i].checkCollision(map, p);
				}
				attackDelay = 10000000;
				if (attp>= 9) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000;
				}
				break;
			case(2):
				if (attp == 0) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 5) * 300000.0) {
						attp =1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						drone = mains[i];
						if (time >= i * 300000.0 && time <= (i + 3.5) * 300000.0) {
							
							if (i % 2 == 0) {
								drone.setImage(redEye.get(1));
								locx = 100;
							} else {
								drone.setImage(redEye.get(3));
								locx = 1550;
							}
							locy =  7125 + i * 100;
							drone.moveTo( scale * 15, locx, locy);
					
						}
						else if (time >= (i +3.5) * 300000.0 && time <= (i + 4.5) * 300000.0) {
							
							if (time/3000.0 % 8 <= 4) {
								warnings.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y-5, 1500, 60)));
							}
						}
						else if (time >= (i +4.5) * 300000.0 && time <= (i + 4.75) * 300000.0) {
							
							lasers.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y-5, 1500, 60)));
						}
						
					} 
				} else if (attp == 1) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 2) * 300000.0) {
						attp =2;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						if (time >= (9 - i)  * 300000.0 && time <= (9 - i + 1.5) * 300000.0) {
							drone = mains[i];
							if (time/3000.0 % 8 <= 4) {
								warnings.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y-5, 1500, 60)));
							}
						}
						else if (time >= (9 - i +1.5)  * 300000.0 && time <= (9 - i  + 1.75) * 300000.0) {
							drone = mains[i];
							lasers.add(new ArrayList<Integer>(Arrays.asList(100, (int)drone.y-5, 1500, 60)));
						}
					}
				}

				if (attp>= 2) {
					attp = 0; 
					attack = 0;
					attackDelay = 3000000.0;
					time = 0;
				}
				break;
			case(3):
				if (attp == 5) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000.0;
					time = 0;
					break;
				} else if (attp == 0) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 5) * 300000.0) {
						attp =1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						drone = mains[i];
						if (time >= i * 300000.0 && time <= (i + 4.5) * 300000.0) {
							
							locx =  125 + i * 150;
							locy =  7950;
							drone.moveTo( scale * 15, locx, locy);
						}
					} 
				} else if (attp % 2 == 0 && attp <= 4) {
					attackDelay = 1000000000.0;
					if (time >= (3) * 500000.0) {
						attp++;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i%2 == 0) {
							
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
							}
						} else {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y +50, 150, 150)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y + 50, 150, 150)));
							}
						}
					}
				} else {
					attackDelay = 1000000000.0;
					
					for (int i = 0; i < mains.length; i++) {
						if (i%2 != 0) {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								if (time/3000.0 % 16 < 8) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
							}
						} else {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								if (time/3000.0 % 16 < 6) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y +50, 150, 150)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y + 50, 150, 150)));
							}
						}
					}
					if (time >= (3) * 500000.0) {
						attp++;
						time = 0;
					}
				}
				break;
			case (4):
				if (attp == 5) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000.0;
					time = 0;
					break;
				} else if (attp == 0) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 5) * 300000.0) {
						attp =1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						drone = mains[i];
						if (time >= i * 300000.0 && time <= (i + 4.5) * 300000.0) {
							
							locx =  125 + i * 150;
							locy =  7950;
							drone.moveTo( scale * 15, locx, locy);
						}
					} 
				} else if (attp == 1) {
					attackDelay = 1000000000.0;
					if (time >= (3) * 500000.0) {
						attp++;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i < 5) {
							
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
							}
						} else {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y +50, 150, 150)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y + 50, 150, 150)));
							}
						}
					}
				} else if (attp == 2)  {
					attackDelay = 1000000000.0;
					
					for (int i = 0; i < mains.length; i++) {
						if (i >= 5) {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								if (time/3000.0 % 16 < 8) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
							}
						} else {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								if (time/3000.0 % 16 < 6) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y +50, 150, 150)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y + 50, 150, 150)));
							}
						}
					}
					if (time >= (3) * 500000.0) {
						attp++;
						time = 0;
					}
				} else {
					attackDelay = 1000000000.0;
					
					for (int i = 0; i < mains.length; i++) {
						if (i >= 3 && i <= 6) {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								if (time/3000.0 % 16 < 8) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(2));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y - 850, 150, 850)));
							}
						} else {
							if (time >=0 && time <= (1) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								if (time/3000.0 % 16 < 6) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y +50, 150, 150)));
								}
							}
							else if (time >= (1)  * 500000.0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(4));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 50, (int)drone.y + 50, 150, 150)));
							}
						}
					}
					if (time >= (3) * 500000.0) {
						attp++;
						time = 0;
					}
				}
				break;
				
			case (5):
				if (attp == 3) {
					attp = 0; 
					attack = 0;
					attackDelay = 10000000.0;
					time = 0;
					break;
				} else if (attp == 0) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 5) * 300000.0) {
						attp =1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						drone = mains[i];
						if (time >= i * 300000.0 && time <= (i + 4.5) * 300000.0) {
							if (i < 5) {
								locx =  100;
								locy =  7100 + i*125;
								drone.moveTo( scale * 15, locx, locy);
							} else {
								locx =  1550;
								locy =  8050 - (i-5)*100;
								drone.moveTo( scale * 15, locx, locy);
							}
						}
					} 
				} else if (attp == 1) {
					attackDelay = 1000000000.0;
					if (time >= (4) * 500000.0) {
						attp++;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i < 5) {
							
							if (time >=0 && time <= (2) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(1));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x +50, (int)drone.y -75, 1550, 150)));
								}
							}
							else if (time >= (2)  * 500000.0 && time <= (3) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(1));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x + 50, (int)drone.y - 75, 1550, 150)));
							}
						}
					}
				} else if (attp == 2) {
					attackDelay = 1000000000.0;
					if (time >= (9) * 500000.0) {
						attp++;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i >= 5) {
							
							if (time >=0 && time <= (4) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x-1550, (int)drone.y -75, 1550, 150)));
								}
							}
							else if (time >= (4)  * 500000.0 && time <= (8) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 1550, (int)drone.y -75, 1550, 150)));
							}
						}
					}
				}
				break;
			case (6):
				if (attp == 11) {
					attp = 0; 
					attack = 0;
					attackDelay = 5000000.0;
					time = 0;
					break;
				} else if (attp == 0) {
					attackDelay = 1000000000.0;
					if (time >= (mains.length + 5) * 300000.0) {
						attp = (int)(Math.random() * 10)+1;
						time = 0;
					}
					for (int i = 0; i < mains.length; i++) {
						drone = mains[i];
						if (time >= i * 300000.0 && time <= (i + 4.5) * 300000.0) {
							if (i < 5) {
								locx =  550 + 150 * i;
								locy =  7400;
								drone.moveTo( scale * 15, locx, locy);
							} else {
								locx =  1550;
								locy =  8050 - (i-5)*100;
								drone.moveTo( scale * 15, locx, locy);
							}
						}
					} 
				} else if (attp <= 5){
					attackDelay = 1000000000.0;
					if (time >= (11) * 500000.0) {
						attp = 11;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i >= 5) {
							if (time >=0 && time <= (4) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x-1550, (int)drone.y -75, 1550, 150)));
								}
							}
							else if (time >= (4)  * 500000.0 && time <= (10) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 1550, (int)drone.y -75, 1550, 150)));
							}
						} else {
							mains[i].x+= 1;
							if (i != attp - 1) {
								if (time >=0 && time <= (4) * 500000.0) {
									drone = mains[i];
									drone.setImage(redEye.get(2));
									if (time/3000.0 % 8 < 4) {
										warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x-75, (int)drone.y- 300, 200, 300)));
									}
								}
								else if (time >= (3)  * 500000.0 && time <= (20) * 500000.0) {
									drone = mains[i];
									drone.setImage(redEye.get(2));
									lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 75, (int)drone.y - 300, 200, 300)));
								}
							}
						}
					}
				} else {
					attackDelay = 1000000000.0;
					if (time >= (11) * 500000.0) {
						attp = 11;
						time = 0;
					}
					
					for (int i = 0; i < mains.length; i++) {
						if (i >= 5) {
							if (time >=0 && time <= (3) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								if (time/3000.0 % 8 < 4) {
									warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x-1550, (int)drone.y -75, 1550, 150)));
								}
							}
							else if (time >= (3)  * 500000.0 && time <= (10) * 500000.0) {
								drone = mains[i];
								drone.setImage(redEye.get(3));
								lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 1550, (int)drone.y -75, 1550, 150)));
							}
						} else {
							mains[i].x -= 1;
							if (i != attp - 6) {
								if (time >=0 && time <= (3) * 500000.0) {
									drone = mains[i];
									drone.setImage(redEye.get(2));
									if (time/3000.0 % 8 < 4) {
										warnings.add(new ArrayList<Integer>(Arrays.asList((int)drone.x-75, (int)drone.y- 300, 200, 300)));
									}
								}
								else if (time >= (3)  * 500000.0 && time <= (20) * 500000.0) {
									drone = mains[i];
									drone.setImage(redEye.get(2));
									lasers.add(new ArrayList<Integer>(Arrays.asList((int)drone.x - 75, (int)drone.y - 300, 200, 300)));
								}
							}
						}
					}
				}
				break;
			}
			
			
			for (int i = 0; i < mains.length; i++) {
				Drone drone = mains[i];
				drone.checkCollision(map, p);
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
			if(hp <=0) {
				hp = -1000;
			}
			
			
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if (new Ellipse2D.Double(x, y, width, height).intersects((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)) {
					if ((antiMulti <= 0)) {
						antiMulti = 6;
						
						if(p.combo >= 30) {
							hp -= list.dam*0.5;
							p.hp+= 1; 
						} else if (p.combo >= 20) {
							hp -= list.dam * 0.375;
						} else if (p.combo >= 10) {
							hp -= list.dam * 1.3/4;
						} else {
							hp -= list.dam*0.25;
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
	/**
	 * 
	 * @return returns the drones that the Boss controls
	 */
	public ArrayList<Drone> getDrones(){
		ArrayList<Drone> returner = new ArrayList<Drone>();
		returner.addAll(drones);
		returner.addAll(Arrays.asList(mains));
		return returner;
	}
	
	/**
	 * 
	 * @return returns the lasers that the boss or its drones shoot
	 */
	public ArrayList<ArrayList<Integer>> getLasers(){
		return lasers;
	}
	
	/**
	 * 
	 * @return returns the warnings that the boss or its drones have
	 */
	public ArrayList<ArrayList<Integer>> getWarnings(){
		return warnings;
	}
	
	
}
