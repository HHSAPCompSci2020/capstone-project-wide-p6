import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PImage;

public class Drone extends MovingImage{

	public double xVelocity;
	public double yVelocity;
	private int antiMulti;
	private double stagger;
	private boolean onASurface;
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
		hp = 50;
		
	}
	
	public Drone(ArrayList<PImage> img, int x, int y, int w, int h,  double xv, double yv) {
		super(img.get(0), x, y, w, h);
		type = 1;
		hp = 20;
		xVelocity = xv;
		yVelocity = yv;
	}
	
	public void act(Map map, Player p, long timeElapsed) {
		if (type == 1) {
			
			ArrayList<Shape> obstacles = map.getObstacles();
			
			x += xVelocity;
			y += yVelocity;
			
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();
			
			double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));
			
			onASurface = false;
			
			
			if (yVelocity > 0) {
				Shape standingSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						onASurface = true;
						standingSurface = s;
						yVelocity = -3;
					}
				}
				if (standingSurface != null) {
					Rectangle r = standingSurface.getBounds();
					yCoord2 = r.getY()-height;
				}
			} else if (yVelocity < 0) {
				Shape headSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						headSurface = s;
						yVelocity = 3;
					}
				}
				if (headSurface != null) {
					Rectangle r = headSurface.getBounds();
					yCoord2 = r.getY()+r.getHeight();
				}
			}

			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;

			// ***********X AXIS***********

			

			
			double xCoord2 = xCoord + xVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity*timeElapsed/17000000),height);

			if (xVelocity > 0) {
				Shape rightSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						rightSurface = s;
						xVelocity = -3;
					}
				}
				if (rightSurface != null) {
					Rectangle r = rightSurface.getBounds();
					xCoord2 = r.getX()-width;
				}
			} else if (xVelocity < 0) {
				Shape leftSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						leftSurface = s;
						xVelocity = 3;
					}
				}
				if (leftSurface != null) {
					Rectangle r = leftSurface.getBounds();
					xCoord2 = r.getX()+r.getWidth();
				}
			}
			
			
		} else if (type == 2) {
			ArrayList<Shape> obstacles = map.getObstacles();
			stagger -= timeElapsed;
			shootDelay -= timeElapsed/1000;
			
			if (shootDelay <= 0) {
				map.shoot(0, (int)x, (int)y, 10, 10, (p.x - x)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, (p.y - y)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, 400, 10);
				shootDelay = 1500000;
			}
			
			
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();

			// *********** Movement ***********
			
			if ((Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y)) > 300)) {
				if (p.x >= x) {
					xVelocity = 2;
				} else {
					xVelocity = -2;
				}
				
				if (p.y >= y) {
					yVelocity = 2;
				} else {
					yVelocity = -2;
				}
				
			}

			
			//-------------------Physics---------------------
			
			yVelocity *= 0.95;
			
			double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));
			
			onASurface = false;
			
			
			if (yVelocity > 0) {
				Shape standingSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						onASurface = true;
						standingSurface = s;
						yVelocity = 0;
					}
				}
				if (standingSurface != null) {
					Rectangle r = standingSurface.getBounds();
					yCoord2 = r.getY()-height;
				}
			} else if (yVelocity < 0) {
				Shape headSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						headSurface = s;
						yVelocity = 0;
					}
				}
				if (headSurface != null) {
					Rectangle r = headSurface.getBounds();
					yCoord2 = r.getY()+r.getHeight();
				}
			}

			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;

			// ***********X AXIS***********

			
			xVelocity *= 0.95;

			
			double xCoord2 = xCoord + xVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity*timeElapsed/17000000),height);

			if (xVelocity > 0) {
				Shape rightSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						rightSurface = s;
						xVelocity = 0;
					}
				}
				if (rightSurface != null) {
					Rectangle r = rightSurface.getBounds();
					xCoord2 = r.getX()-width;
				}
			} else if (xVelocity < 0) {
				Shape leftSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						leftSurface = s;
						xVelocity = 0;
					}
				}
				if (leftSurface != null) {
					Rectangle r = leftSurface.getBounds();
					xCoord2 = r.getX()+r.getWidth();
				}
			}


			if (Math.abs(xVelocity) < .5)
				xVelocity = 0;
			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;
			moveToLocation(xCoord2,yCoord2);
		
		
		
		
		
		
		}else if (type == 3) {
			ArrayList<Shape> obstacles = map.getObstacles();
			stagger -= timeElapsed;
			
			
			
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();

			// *********** Movement ***********

			if (stagger <= 0) {
				if (p.x >= x) {
					xVelocity = 2;
				} else {
					xVelocity = -2;
				}
				
				if (p.y >= y) {
					yVelocity = 2;
				} else {
					yVelocity = -2;
				}
			}
			
			//-------------------Physics---------------------
			
			yVelocity *= 0.95;
			
			double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));
			
			onASurface = false;
			
			
			if (yVelocity > 0) {
				Shape standingSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						onASurface = true;
						standingSurface = s;
						yVelocity = 0;
					}
				}
				if (standingSurface != null) {
					Rectangle r = standingSurface.getBounds();
					yCoord2 = r.getY()-height;
				}
			} else if (yVelocity < 0) {
				Shape headSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechY)) {
						headSurface = s;
						yVelocity = 0;
					}
				}
				if (headSurface != null) {
					Rectangle r = headSurface.getBounds();
					yCoord2 = r.getY()+r.getHeight();
				}
			}

			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;

			// ***********X AXIS***********

			
			xVelocity *= 0.95;

			
			double xCoord2 = xCoord + xVelocity*timeElapsed/17000000;

			Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity*timeElapsed/17000000),height);

			if (xVelocity > 0) {
				Shape rightSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						rightSurface = s;
						xVelocity = 0;
					}
				}
				if (rightSurface != null) {
					Rectangle r = rightSurface.getBounds();
					xCoord2 = r.getX()-width;
				}
			} else if (xVelocity < 0) {
				Shape leftSurface = null;
				for (Shape s : obstacles) {
					if (s.intersects(strechX)) {
						leftSurface = s;
						xVelocity = 0;
					}
				}
				if (leftSurface != null) {
					Rectangle r = leftSurface.getBounds();
					xCoord2 = r.getX()+r.getWidth();
				}
			}


			if (Math.abs(xVelocity) < .5)
				xVelocity = 0;
			if (Math.abs(yVelocity) < .5)
				yVelocity = 0;
			moveToLocation(xCoord2,yCoord2);
		}
	}
	
	public void checkCollision(Map map, Player p) {
		antiMulti -= 1;
		if (type == 0) {
			if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
				p.hit(10);
			}
		} else if (type == 1) {
			if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
				p.hit(10);
			}
		} else if (type == 3) {
			if ((new Rectangle((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
				if(stagger <= -50000000) {
					p.hit(10);
				}
			}
		}
		
		
		
	
		if (type == 0) {
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x, y, width, height)) {
					if ((antiMulti <= 0)) {
						antiMulti = 6;
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 20;
						}
					}
				}
			}
			
			
		} else if (type == 1) {
			
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x, y, width, height)) {
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
							p.stamina += 20;
						}
					}
				}
			}
			
		} else if (type == 2) {
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x, y, width, height)) {
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
						stagger = 60000000 * (Math.sqrt(list.kx*list.kx + list.ky*list.ky));
						shootDelay +=  150000 * (Math.sqrt(list.kx*list.kx + list.ky*list.ky));;
						
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 20;
						}
					}
				}
			}
			
		}else if (type == 3) {
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(x, y, width, height)) {
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
						stagger = 60000000 * (Math.sqrt(list.kx*list.kx + list.ky*list.ky));
						
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 20;
						}
					}
				}
			}
			
		}
		
	}
}
