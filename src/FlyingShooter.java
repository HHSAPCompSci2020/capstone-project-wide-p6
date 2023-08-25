import java.util.ArrayList;

import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
/**
 * Flying shooter enemy
 * @author Ethan Wang, Justin Zhu
 *
 */
public class FlyingShooter extends Enemy implements JayLayerListener{

	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double gravity;
	private double hp;
	private double stagger;
	private int shootDelay;
	private int damage;
	private double speed;
	private double senseRadius = 500;
	private double antiMulti;
	private int index;
	private ArrayList<PImage> images;
	private JayLayer sound;

/**
 * 
 * @param img arraylist of images to be used, only uses 1
 * @param x top left x
 * @param y top left y
 * @param w width
 * @param h height
 * @param index index in the array of enemies
 */
	public FlyingShooter(ArrayList<PImage> img, int x, int y, int w, int h, int index) {
		
		super(img, x, y, w, h, index);
		xVelocity = 0;
		yVelocity = 0;
		onASurface = false;
		hp = 75*w*h/50/50;
		damage = 10*w*h/50/50;
		speed = 2;
		stagger = 0;
		antiMulti = 5;
		images = img;
		shootDelay = 2000000;
		this.index = index;
		String[] soundEffects = new String[]{"Pew.mp3"};
		sound=new JayLayer("audio/","audio/",false);
		sound.addPlayList();
		sound.addSoundEffects(soundEffects);
		sound.changePlayList(0);
		sound.addJayLayerListener(this);
		// TODO Auto-generated constructor stub
	}
	/**  Causes the enemy to act and do things within the specified time.
	 * 
	 * @param map	the map to be interacted with
	 * @param timeElapsed 	the time that is to be emulated
	 * @param p		the player character
	 */
	public void act(Map map, long timeElapsed, Player p) {
		ArrayList<Shape> obstacles = map.getObstacles();
		stagger -= timeElapsed;
		shootDelay -= timeElapsed/1000;
		antiMulti -= 1;
		
		if (shootDelay <= 0) {
			map.shoot(0, (int)x, (int)y, 10, 10, (p.x - x)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, (p.y - y)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, 400, 10);
			sound.playSoundEffect(0);
			String[] soundEffects = new String[]{"Pew.mp3"};
			sound=new JayLayer("audio/","audio/",false);
			sound.addPlayList();
			sound.addSoundEffects(soundEffects);
			sound.changePlayList(0);
			sound.addJayLayerListener(this);
			shootDelay = 1500000;
		}
		
		
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// *********** Movement ***********
		
		if(Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y)) < senseRadius) {
			
			if ((Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y)) > 300)) {
				if (p.x >= x) {
					xVelocity = speed;
				} else {
					xVelocity = -speed;
				}
				
				if (p.y >= y) {
					yVelocity = speed;
				} else {
					yVelocity = -speed;
				}
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
		
		
		
		
		// -------------------- collision checking ---------------------
		
		
		
		
		for(int i = 0; i < map.getHitboxes().size(); i++) {
			Hitbox list =  map.getHitboxes().get(i);
			if ((new Rectangle((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)).intersects(strechX) || (new Rectangle((int)list.x-7,(int)list.y-7,list.w + 15,list.h + 15)).intersects(strechY)) {
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
						p.stamina += 60;
					}
				}
			}
		}
		
		if ((new Ellipse2D.Double((int)(x), (int)(y), (int)super.width, (int)super.height)).intersects(new Rectangle ((int)p.x, (int)p.y, (int)p.PLAYER_WIDTH, (int)p.PLAYER_HEIGHT))) {
			
			if(onASurface) {
				if(stagger <= 0) {
					p.hit(damage);
				}
			} else {
				if(stagger <= -50000000) {
					p.hit(damage);
				}
			}
			
			
		}
		
		if(hp <=0) {
			map.getEnemies()[index] = null;
		}
		
		ArrayList<Integer> stuff = map.getEnemyInfo().get(index);
		if (Math.sqrt((stuff.get(1) - x)* (stuff.get(1) - x) + (stuff.get(2) -y) * (stuff.get(2) - y)) >= 3000) {
			map.getEnemies()[index] = null;
		}
		
	}

	@Override
	public void musicStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}
	
	
}
