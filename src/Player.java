/**@Author Shelby, Ethan Wang
 * A class representing the player character.
 */

import java.awt.*;

import java.awt.geom.Rectangle2D;
import java.util.*;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends MovingImage implements JayLayerListener {

	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 60;
	public int lastCheck = 0;
	public int hp;
	public double stamina;
	public int combo;
	private JayLayer sound;

	private PImage hitboxtemp;
	
	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double friction;
	private double gravity;
	private double gravIgnore;
	private double invincible;
	private double jumpStrength;
	private double delay;
	private boolean direction; // true = right, false = left
	private int dive;
	private double dash;
private String[] soundEffects;
	private ArrayList<PImage> images;
	/** The constructor of the player class, slightly modified
	 * 
	 * @param images A list of images which the character will use as sort of "animations"
	 * @param x The x coordinate to spawn the player at
	 * @param y The y coordinate to spawn the player at
	 */
	public Player(ArrayList<PImage> images, int x, int y) {
		super(images.get(0), x, y,PLAYER_WIDTH, PLAYER_HEIGHT);
		this.images = images;
		xVelocity = 0;
		yVelocity = 0;
		onASurface = false;
		gravity = 0.7;
		friction = .87;
		jumpStrength = 12;
		stamina = 100;
		delay = 0;
		gravIgnore = 0;
		direction = true;
		dive = 0;
		dash = 0;
		hp = 100;
		combo = 0;
		
		hitboxtemp = (new PApplet()).loadImage("imgs/hitboxtemp.png");
		soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
		sound=new JayLayer("audio/","audio/",false);
		sound.addPlayList();
		sound.addSoundEffects(soundEffects);
		sound.changePlayList(0);
		sound.addJayLayerListener(this);
	}

	// METHODS
	/*
	 * 
	 * Moves the player
	 * 
	 * @param dir initial direction of movement
	 */
	public void walk(int dir) {
		direction = dir > 0;
		if (xVelocity <= 10 && xVelocity >= -10) {
			xVelocity += dir;
		}
	}

	/*
	 * 
	 * Moves the player upward
	 * 
	 * 
	 */
	public void jump() {
		if (delay <= 0) {
			if (onASurface) {
				yVelocity = -jumpStrength;
				stamina -= 25;
				delay = 200000000;
				sound.playSoundEffect(0);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);
			}else if (stamina >= 25) {
				yVelocity = -jumpStrength;
				stamina -= 25;
				delay = 200000000;
				sound.playSoundEffect(0);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);
			}
			
		}
	}
	
	
	/**
	 * A method which causes the character to dash forwards, and cause the characters to mostly ignore gravity.
	 */
	public void dash() {
		if (delay <= 0) {
			/*if (onASurface) {
				if (direction)
					xVelocity = 30;
				else 
					xVelocity = -30;
				delay = 250000000;
				gravIgnore = 250000000;
			}else*/ if (!(onASurface) && stamina >= 20) {
				if (direction) {
					dash = 100000000;
				sound.playSoundEffect(1);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);
				}
				else {
					dash = 100000000;
					sound.playSoundEffect(1);
					soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
					sound=new JayLayer("audio/","audio/",false);
					sound.addPlayList();
					sound.addSoundEffects(soundEffects);
					sound.changePlayList(0);
					sound.addJayLayerListener(this);
				}
				stamina -= 20;
				invincible = 200000000;
				delay = 200000000;
				gravIgnore = 250000000;
			}
		}
	}
	/**
	 * A method which causes the character to dive downward
	 */
	public void dive() {
		
		if(!(onASurface) && (delay <= 0)) {
			dive = 1;
			sound.playSoundEffect(1);
			soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
			sound=new JayLayer("audio/","audio/",false);
			sound.addPlayList();
			sound.addSoundEffects(soundEffects);
			sound.changePlayList(0);
			sound.addJayLayerListener(this);
			
		}
	}
	/**
	 * A method which causes the character to use a low knockback fast attack
	 * @param map the map which the player sees
	 */
	
	public void lightAttack(Map map) {
		if (delay <= 0) {
			if (onASurface) {
				xVelocity = 0;
				delay = 300000000;
				if (direction) {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x+40- PLAYER_WIDTH/2), (int)y -5, 75, 75, 5, 5, 1, -1));
				} else {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x-35- PLAYER_WIDTH/2), (int)y -5, 75, 75, 5, 5, -1, -1));
				}
				sound.playSoundEffect(2);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);
			}else if (stamina >= 0) {
				stamina -= 8;
				xVelocity = 0;
				delay = 200000000;
				gravIgnore = 220000000;
				if (direction) {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x+40- PLAYER_WIDTH/2), (int)y -5, 75, 75, 7, 5, 2, -2));
				} else {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x-35- PLAYER_WIDTH/2), (int)y -5, 75, 75, 7, 5, -2, -2));
				}
				sound.playSoundEffect(2);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);}
		}
		

	}
	
	/**
	 * A method which causes the character to use a high knockback slow attack
	 * @param map the map which the player sees
	 */
	public void heavyAttack(Map map) {
		if (dive == 1) {
			dive = 2;
		}else if (delay <= 0) {
			if (onASurface) {
				xVelocity = 0;
				delay = 500000000;
				if (direction) {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x+40- PLAYER_WIDTH/2), (int)y -25, 100, 100, 10, 5, 10, -13));
				} else {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x-55- PLAYER_WIDTH/2), (int)y -25, 100, 100, 10, 5, -10, -13));
				}
				sound.playSoundEffect(2);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);
			}else if (stamina >= 15) {
				stamina -= 20;
				xVelocity = 0;
				delay = 300000000;
				gravIgnore = 325000000;
				if (direction) {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x+40- PLAYER_WIDTH/2), (int)y -25, 100, 100, 20, 5, 15, -10));
				} else {
					map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp, hitboxtemp)),
							(int)(x-55- PLAYER_WIDTH/2), (int)y -25, 100, 100, 20, 5, -15, -10));
				}
				sound.playSoundEffect(2);
				soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
				sound=new JayLayer("audio/","audio/",false);
				sound.addPlayList();
				sound.addSoundEffects(soundEffects);
				sound.changePlayList(0);
				sound.addJayLayerListener(this);}
		}
		

	}
	
	/**
	 * A method which indicates when the character takes damage
	 * @param damage how much damage the character takes
	 */
	
	public void hit(int damage) {
		if (invincible <= 0) {
			invincible = 500000000;
			hp -= damage;
			//stamina = 0;
			sound.playSoundEffect(3);
			soundEffects = new String[]{"Jump.mp3", "Dash.mp3", "slash.mp3","damage.mp3"};
			sound=new JayLayer("audio/","audio/",false);
			sound.addPlayList();
			sound.addSoundEffects(soundEffects);
			sound.changePlayList(0);
			sound.addJayLayerListener(this);
		}
		
		
	}
	/*
	 * @return the dive amount
	 */
	
	public int getDive() {
		return dive;
	}
	
	public void diveHop() {
		dive = 0;
		if (stamina < 100) {
			stamina = 100;
		}
		
		delay = 300000000;
		yVelocity = -jumpStrength;
	}
	/*
	 * @return the dash amount
	 */
	public double getDash() {
		return dash;
	}
	
	
	/**
	 * Modified act method. Uses time elapsed so that even on slower computers the game should run relatively the same.
	 * @param obstacles The arraylist of all the obstacles on the map used for collisions.
	 * @param timeElapsed How much time has passed since the last time the draw method in DrawingSurface was run in nanoseconds
	 */
	public void act(Map map, long timeElapsed) {
		ArrayList<Shape> obstacles = map.getObstacles();
		delay -= timeElapsed;
		dash -= timeElapsed;
		gravIgnore -= timeElapsed;
		invincible -= timeElapsed;
		if(stamina>=100)
			stamina = 100;
		if(dive == 1) {
			if (direction) {
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),
						(int)(x), (int)y + 50, 50, 50, 10, 2, 7, -13));
			} else {
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),
						(int)(x), (int)y + 50, 50, 50, 10, 2, -7, -13));
			}
			
			delay = 100000000;
		} else if (dive == 2) {
			if (direction) {
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),
						(int)(x - 5), (int)y + 60, 60, 60, 25, 2, 20, -15));
			} else {
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),
						(int)(x - 5), (int)y + 60, 60, 60, 25, 2, -20, -15));
			}
			delay = 100000000;
			invincible = 100000000;
		}
		
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// ***********Y AXIS***********
		if ((gravIgnore <= 0)) {
			yVelocity += gravity*timeElapsed/20000000; // GRAVITY
			if (yVelocity > 5) {
				yVelocity = 5;
			}
		} else {
			yVelocity = 0.5;
		}
		if (dive == 1) {
			yVelocity = 10;
		} else if (dive == 2) {
			yVelocity = 20;
		}
		double yCoord2 = yCoord + yVelocity*timeElapsed/17000000;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity*timeElapsed/17000000));

		onASurface = false;

		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					onASurface = true;
					stamina = 100;
					dive = 0;
					combo = 0;
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


		xVelocity *= friction;

		if(dash > 0) {
			
			if (direction) {
				
				xVelocity = 30;
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),(int)(x - PLAYER_WIDTH/2 +  xVelocity*timeElapsed/14000000), (int)y, 75, 50, 10, 2, 10, -12));
			} else {
				
				xVelocity = -30;
				map.addHitbox(new Hitbox(new ArrayList<PImage>(Arrays.asList(hitboxtemp, hitboxtemp)),(int)(x - PLAYER_WIDTH/2 +  xVelocity*timeElapsed/14000000), (int)y, 75, 50, 10, 2, -10, -12));
			}
			
		} 
		
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

		moveToLocation(xCoord2,yCoord2);
		
		if (!onASurface) {
			stamina -= (double)timeElapsed/800000000;
		}
		
		
		if(xVelocity>0&&yVelocity==0) {
			super.setImage(images.get(2));
		}
		else if(xVelocity<0&&yVelocity==0) {
			super.setImage(images.get(3));
		}
		else if(direction&&yVelocity>0) {
			super.setImage(images.get(6));

		}
		else if(!direction&&yVelocity>0) {
			super.setImage(images.get(7));

		}
		else if(direction&&yVelocity<0) {
			super.setImage(images.get(4));

		}
		else if(!direction&&yVelocity<0) {
			super.setImage(images.get(5));

		}
		else {
			if (direction) {
				super.setImage(images.get(0));
			} else {
				super.setImage(images.get(1));
			}
		}
		for(int i = 0; i < map.getCheckpoints().size(); i++) {
			if (map.getCheckpoints().get(i).intersects(strechX) || map.getCheckpoints().get(i).intersects(strechY)) {
				hp = 100;
				lastCheck = i;
			}
		}
		for(int i = 0; i < map.getLava().size(); i++) {
			if (map.getLava().get(i).intersects(strechX) || map.getLava().get(i).intersects(strechY)) {
				hit(15);
			}
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
